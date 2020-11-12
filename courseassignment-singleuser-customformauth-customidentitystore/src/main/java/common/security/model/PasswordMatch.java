package common.security.model;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = {PasswordMatchValidator.class})
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PasswordMatch {

	String message() default "Password fields must match";
	
	Class<?>[] groups() default { };
	
	Class<? extends Payload>[] payload() default { };
}