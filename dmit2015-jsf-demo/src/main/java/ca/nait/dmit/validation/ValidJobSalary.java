package ca.nait.dmit.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ValidJobSalaryValidator.class })
@Documented
public @interface ValidJobSalary {

	// Default error message
	String message() default "Maximum salary must be greater than the minimum salary";
	
	// Default validation group
	Class<?>[] groups() default {};
	
	// Associate metadata with a given validation constraint
	Class<? extends Payload>[] payload() default {};
	
}
