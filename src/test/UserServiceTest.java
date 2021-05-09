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

    @Test
    public void testIfTextToIsEqualToUserText() {
        User user = new User("test", "test@wp.pl", "aaa");
        SimpleMailMessage simpleMailMessage = userService.constructResetTokenEmail("contextPath", Locale.ENGLISH, "token", user);
        assertNotNull(simpleMailMessage);
        assertNotNull(simpleMailMessage.getText());
        assertEquals(simpleMailMessage.getText().length(), 48);
        assertEquals(simpleMailMessage.getText(), "message \r\ncontextPath/changePassword?token=token");
    }

    @Test
    public void testIfLengthTextToIsNotEqualToUserLengthText() {
        User user = new User("test", "test@wp.pl", "aaa");
        SimpleMailMessage simpleMailMessage = userService.constructResetTokenEmail("contextPath", Locale.ENGLISH, "token", user);
        assertNotNull(simpleMailMessage);
        assertNotNull(simpleMailMessage.getText());
        assertNotEquals(simpleMailMessage.getText().length(), 21);
    }

    @Test
    public void testIfTextToIsNotEqualToUserText() {
        User user = new User("test", "test@wp.pl", "aaa");
        SimpleMailMessage simpleMailMessage = userService.constructResetTokenEmail("contextPath", Locale.ENGLISH, "token3", user);
        assertNotNull(simpleMailMessage);
        assertNotNull(simpleMailMessage.getText());
        assertNotEquals(simpleMailMessage.getText().length(), 21);
        assertEquals(simpleMailMessage.getText().length(), 49);
        assertNotEquals(simpleMailMessage.getText(), "message\r\ncontextPath/changePassword?token=token");
        assertNotEquals(simpleMailMessage.getText(), "message\r\ncontextPath/changePassword?token=token3");
        assertEquals(simpleMailMessage.getText(), "message \r\ncontextPath/changePassword?token=token3");
    }

    @Test
    public void testIfgetFromIsWorking() {
        User user = new User("test", "test@wp.pl", "aaa");
        SimpleMailMessage simpleMailMessage = userService.constructResetTokenEmail("contextPath", Locale.ENGLISH, "token", user);
        assertNotNull(simpleMailMessage);
        assertNotNull(simpleMailMessage.getFrom());
        assertNotEquals(simpleMailMessage.getFrom().length(), 332);
        assertEquals(simpleMailMessage.getFrom().length(), 13);
        assertNotEquals(simpleMailMessage.getFrom(), "contextPath");
        assertEquals(simpleMailMessage.getFrom(), "support.email");
    }

    @Test
    public void testIfGetSubjectIsWorking1() {
        User user = new User("test", "test@wp.pl", "aaa");
        SimpleMailMessage simpleMailMessage = userService.constructResetTokenEmail("contextPath", Locale.ENGLISH, "token", user);
        assertNotNull(simpleMailMessage);
        assertNotNull(simpleMailMessage.getFrom());
        assertNotEquals(simpleMailMessage.getSubject().length(), 332);
        assertEquals(simpleMailMessage.getSubject().length(), 14);
        assertNotEquals(simpleMailMessage.getSubject(), "REset Password");
        assertEquals(simpleMailMessage.getSubject(), "Reset Password");
    }

}