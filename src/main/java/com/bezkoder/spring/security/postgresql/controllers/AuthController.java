package com.bezkoder.spring.security.postgresql.controllers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;

import antlr.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.multipart.MultipartHttpMessageReader;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.bezkoder.spring.security.postgresql.models.ERole;
import com.bezkoder.spring.security.postgresql.models.Role;
import com.bezkoder.spring.security.postgresql.models.User;
import com.bezkoder.spring.security.postgresql.payload.request.LoginRequest;
import com.bezkoder.spring.security.postgresql.payload.request.SignupRequest;
import com.bezkoder.spring.security.postgresql.payload.response.JwtResponse;
import com.bezkoder.spring.security.postgresql.payload.response.MessageResponse;
import com.bezkoder.spring.security.postgresql.repository.RoleRepository;
import com.bezkoder.spring.security.postgresql.repository.UserRepository;
import com.bezkoder.spring.security.postgresql.security.jwt.JwtUtils;
import com.bezkoder.spring.security.postgresql.security.services.UserDetailsImpl;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
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

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody String httpResponse)
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

		return ResponseEntity.ok(new JwtResponse(jwt,
				userDetails.getId(),
				userDetails.getUsername(),
				userDetails.getEmail(),
				roles));

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
