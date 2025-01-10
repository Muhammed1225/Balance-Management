package az.developia.balance_management.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = IsOldPasswordValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IsOldPassword {

    String value() default "";

    String message() default "The password is incorrect!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
