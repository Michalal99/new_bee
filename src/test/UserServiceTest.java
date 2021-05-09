import com.bee.models.PasswordResetToken;
import com.bee.models.User;
import com.bee.repository.PasswordResetTokenRepository;
import com.bee.repository.UserRepository;
import com.bee.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepo;

    @Mock
    private PasswordResetTokenRepository passwordTokenRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    ArgumentCaptor<PasswordResetToken> tokenArgumentCaptor;

    @Test
    public void testCreatePasswordResetTokenForUser() {
        User user = new User("test", "test@wp.pl", "aaa");
        String token = "token";
        PasswordResetToken resetToken = new PasswordResetToken(token, user);
        userService.createPasswordResetTokenForUser(user, token);
        verify(passwordTokenRepository).deleteByUser(user);
        verify(passwordTokenRepository).save(tokenArgumentCaptor.capture());
        PasswordResetToken passwordResetToken = tokenArgumentCaptor.getValue();
        assertEquals(passwordResetToken.getUser(), user);
    }

    @Test
    public void testIfMailToIsEqualToUserMail() {
        User user = new User("test", "test@wp.pl", "aaa");
        SimpleMailMessage simpleMailMessage = userService.constructResetTokenEmail("contextPath", Locale.ENGLISH, "token", user);
        assertNotNull(simpleMailMessage);
        assertNotNull(simpleMailMessage.getTo());
        assertEquals(simpleMailMessage.getTo().length, 1);
        assertEquals(simpleMailMessage.getTo()[0], user.getEmail());
    }

    @Test
    public void testIfMailToIsNotEqualToUserMail() {
        User user = new User("test", "test@wp.pl", "aaa");
        SimpleMailMessage simpleMailMessage = userService.constructResetTokenEmail("contextPath", Locale.ENGLISH, "token", user);
        assertNotNull(simpleMailMessage);
        assertNotNull(simpleMailMessage.getTo());
        assertEquals(simpleMailMessage.getTo().length, 1);
        assertNotEquals("simpleMailMessage.getTo()[0]", user.getEmail());
    }




}