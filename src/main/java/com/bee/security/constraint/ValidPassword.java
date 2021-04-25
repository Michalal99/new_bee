package com.bee.security.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy=PasswordConstraintValidator.class)
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface ValidPassword {
    String message() default "Invalid password! Password should have:\n" +
            "- from 8 to 30 characters\n- at least one upper case character\n" +
            "- at least one lower case character\n- at least one digit\n" +
            "- at least one special character\n-no whitespaces\n";
    Class<?>[] groups() default {};
    Class<? extends Payload> [] payload() default {};
}
