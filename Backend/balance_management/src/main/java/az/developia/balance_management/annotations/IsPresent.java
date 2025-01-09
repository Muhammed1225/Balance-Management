package az.developia.balance_management.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = IsPresentValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IsPresent {

    String value() default "";

    String message() default "This username has already existed!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
