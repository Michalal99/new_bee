//import com.bee.controller.AuthController;
//import com.bee.payload.request.SignupRequest;
//import com.bee.repository.PasswordResetTokenRepository;
//import com.bee.repository.RoleRepository;
//import com.bee.repository.UserRepository;
//import com.bee.security.jwt.JwtUtils;
//import com.bee.service.UserService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.ApplicationContext;
//import org.springframework.http.MediaType;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//
//import static org.mockito.Mockito.doReturn;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class AuthControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Mock
//    private AuthenticationManager authenticationManager;
//
//    @Mock
//    private RoleRepository roleRepository;
//
//    @MockBean
//    private UserRepository userRepository;
//
//    @Mock
//    private PasswordEncoder encoder;
//
//    @Mock
//    private JwtUtils jwtUtils;
//
//    @Mock
//    private UserService userService;
//
//    @Mock
//    private JavaMailSenderImpl mailSender;
//
////    @Autowired
////    PasswordTokenSecurity passwordTokenSecurity;
//
//    @Mock
//    private PasswordResetTokenRepository passwordResetTokenRepository;
//
//    @Autowired
//    private ApplicationContext context;
//
//    //public MockitoAnnotations();
//
//
//    @Test
//    public void shouldFailRegistrationIfUserExists() throws Exception {
//
//        //given
//        ObjectMapper objectMapper = new ObjectMapper();
//        String pasozyd = "pasozyd";
//        Mockito.doReturn(false).when(userRepository).existsByUsername(pasozyd);
//
//        SignupRequest signupRequest = new SignupRequest();
//        signupRequest.setUsername(pasozyd);
//
//        //when & then
//        MvcResult mvcResult = mockMvc.perform(post("/signup")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(signupRequest))
//        )
//                .andExpect(status().isBadRequest())
//                .andReturn();
//
//        System.out.println(mvcResult.getResponse());
//    }
//}