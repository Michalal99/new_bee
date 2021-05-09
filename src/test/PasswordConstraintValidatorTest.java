import com.bee.security.constraint.PasswordConstraintValidator;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.ConstraintValidatorContext;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class PasswordConstraintValidatorTest {
    private static final String NULL = "";
    @InjectMocks
    PasswordConstraintValidator passwordConstraintValidator;


    @Test
    public void shouldFailIfPasswordWithoutUpperCase() {
        String password = "ala123%xd";
        ConstraintValidatorContext context = getExampleConstraintValidatorContext();
//        when(context.buildConstraintViolationWithTemplate(password))
//                .thenReturn(new ConstraintValidatorContext.ConstraintViolationBuilder());
//        doNothing().when(passwordConstraintValidator).buildConstraintViolation(any(), any(), any());
        assertFalse(passwordConstraintValidator.isValid(password, context));
    }

    @Test
    public void shouldPassIfPassword() {
        String password = "aLa123%xd";
        ConstraintValidatorContext context = getExampleConstraintValidatorContext();
        assertTrue(passwordConstraintValidator.isValid(password, context));
    }

    @Test
    public void shouldFailIfPasswordToShort() {
        String password = "a1L%xd";
        ConstraintValidatorContext context = getExampleConstraintValidatorContext();
        assertFalse(passwordConstraintValidator.isValid(password, context));
    }

    @Test
    public void shouldFailIfPasswordToLong() {
        String password = "a1L%xd123456789123456789123456789";
        ConstraintValidatorContext context = getExampleConstraintValidatorContext();
        assertFalse(passwordConstraintValidator.isValid(password, context));
    }

    @Test
    public void shouldFailIfPasswordWithoutAnything() {
        String password = NULL;
        ConstraintValidatorContext context = getExampleConstraintValidatorContext();
        assertFalse(passwordConstraintValidator.isValid(password, context));
    }

    @Test
    public void shouldFailIfPasswordWithoutNumber() {
        String password = "alaLLL%xd";
        ConstraintValidatorContext context = getExampleConstraintValidatorContext();
        assertFalse(passwordConstraintValidator.isValid(password, context));
    }

    @Test
    public void shouldFailIfPasswordWithoutUpperAndLowerCase() {
        String password = "%1234567812";
        ConstraintValidatorContext context = getExampleConstraintValidatorContext();
        assertFalse(passwordConstraintValidator.isValid(password, context));
    }

    @Test
    public void shouldFailIfPasswordWithoutNumberAndSpecial() {
        String password = "alaLLLtrxd";
        ConstraintValidatorContext context = getExampleConstraintValidatorContext();
        assertFalse(passwordConstraintValidator.isValid(password, context));
    }

    @Test
    public void shouldFailIfPasswordWithoutSpecial() {
        String password = "alaLLL123xd";
        ConstraintValidatorContext context = getExampleConstraintValidatorContext();
        assertFalse(passwordConstraintValidator.isValid(password, context));
    }

    @Test
    public void shouldPassPassword2() {
        String password = "ABCD1234!@#$abcd";
        ConstraintValidatorContext context = getExampleConstraintValidatorContext();
        assertTrue(passwordConstraintValidator.isValid(password, context));
    }

    @Test
    public void shouldPassPassword3() {
        String password = "ABCD1234!@#$abcdABCD1234!@#$ab";
        ConstraintValidatorContext context = getExampleConstraintValidatorContext();
        assertTrue(passwordConstraintValidator.isValid(password, context));
    }

    @Test
    public void shouldPassPassword4() {
        String password = "abcde1B!";
        ConstraintValidatorContext context = getExampleConstraintValidatorContext();
        assertTrue(passwordConstraintValidator.isValid(password, context));
    }


    private ConstraintValidatorContextImpl getExampleConstraintValidatorContext() {
        return new ConstraintValidatorContextImpl(null, null, null, null, null);
    }

}