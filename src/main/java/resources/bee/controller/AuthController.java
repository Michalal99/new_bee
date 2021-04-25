package resources.bee.controller;

import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import resources.bee.models.ERole;
import resources.bee.models.Role;
import resources.bee.models.User;
import resources.bee.payload.request.LoginRequest;
import resources.bee.payload.request.SignupRequest;
import resources.bee.payload.response.MessageResponse;
import resources.bee.repository.RoleRepository;
import resources.bee.repository.UserRepository;
import resources.bee.security.jwt.JwtUtils;
import resources.bee.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/api/auth")

public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;


    //public ResponseEntity<?> logout ()
    @RequestMapping(value="/logout", method=RequestMethod.GET)
    public String logoutPage(HttpSession session ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        var test = session.getId();
        session.invalidate();


        return "redirect:/api/auth/login";
    }
    @RequestMapping(value="/welcome", method = RequestMethod.GET)
    public String printWelcome(ModelMap model   ) {
        //User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //jwtUtils.getUserNameFromJwtToken()
        //	var x = SecurityContextHolder.getContext().getAuthentication();


        var y = model.get("token");


        var user = jwtUtils.getUserNameFromJwtToken((String)y);
        //String name = user.getUsername(); //get logged in username

        //authenticationManager.authenticate()
        //model.addAttribute("username", name);
        return "hello";
    }


    @PostMapping("/signin")
    //public ResponseEntity<?> authenticateUser(@Valid @RequestBody String httpResponse, ModelMap user)
    public String authenticateUser(@Valid @RequestBody String httpResponse, RedirectAttributes redirectAttributes,HttpSession session)
    {
        String username = httpResponse.substring(httpResponse.indexOf('=')+1,httpResponse.indexOf('&'));
        String password =  httpResponse.substring(httpResponse.indexOf('=',httpResponse.indexOf('&'))+1,httpResponse.lastIndexOf('&'));




        var loginRequest = new LoginRequest();
        loginRequest.setPassword(password);
        loginRequest.setUsername(username);
        // brzydki kod, ale nie umiem odebrać bezposrednio odpowiedzi http do obiektu LoginRequest

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        //redirectAttributes.addAttribute("username",username);
        redirectAttributes.addFlashAttribute("token",jwt);
        redirectAttributes.addFlashAttribute("username",username);
        session.setAttribute("username",username);
        session.setAttribute("token",jwt);
        return "redirect:/api/auth/welcome";
        //return new ModelAndView("redirect:/api/auth/welcome", user);
//		return ResponseEntity.ok(new JwtResponse(jwt,
//				userDetails.getId(),
//				userDetails.getUsername(),
//				userDetails.getEmail(),
//				roles));

    }
    //	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
//
//		Authentication authentication = authenticationManager.authenticate(
//				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//		String jwt = jwtUtils.generateJwtToken(authentication);
//
//		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//		List<String> roles = userDetails.getAuthorities().stream()
//				.map(item -> item.getAuthority())
//				.collect(Collectors.toList());
//
//		return ResponseEntity.ok(new JwtResponse(jwt,
//												 userDetails.getId(),
//												 userDetails.getUsername(),
//												 userDetails.getEmail(),
//												 roles));
//	}
//@POST
//@Consumes("multipart/form-data")
//@PostMapping("/signup")
//public void put(@RequestBody  Map<String, String> customers)
//{
//	var x = customers;
//	System.out.println("dupa");
//}
//public String echo( @RequestBody String requestBody) throws Exception {
//		var x = requestBody;
//	return requestBody;
//}
    @PostMapping("/signup")
//	@RequestMapping(path = "/signup", method = RequestMethod.POST,
//			consumes = {"multipart/form-data"})
    public ResponseEntity<?> registerUser(@Valid @RequestBody String response) {
//		public ResponseEntity<?> registerUser(@RequestBody  Object signUpRequest) {
//
//
        String a = response;
        Map<String,String> args = new HashMap<>();

        while(a.contains("&"))
        {

            String key = a.substring(0, a.indexOf("="));
            String val = a.substring(a.indexOf("=") + 1, a.indexOf("&"));
            args.put(key, val);

            a = a.substring(a.indexOf("&") + 1, a.length() - 1);

        }
        var email = args.get("email").replace("%40","@");
        args.replace("email",email);
        //	args.replace("email",args.get("mail").replace("%40","@"));
        SignupRequest signUpRequest = new SignupRequest();
        signUpRequest.setUsername(args.get("username"));

        if(args.get("password").compareTo(args.get("password_confirm"))!=0)
        {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Password must be the same!"));
        }

        signUpRequest.setPassword(args.get("password"));
        signUpRequest.setEmail(args.get("email"));
        Set<String> role = new HashSet<>() ;



        if(args.containsKey("user"))
            role.add("user");
        if(args.containsKey("mod"))
            role.add("mod");
        if(args.containsKey("admin"))
            role.add("admin");

        signUpRequest.setRole(role);
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
//
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
//
        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {

            strRoles.forEach(rol -> {
                switch (rol) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
