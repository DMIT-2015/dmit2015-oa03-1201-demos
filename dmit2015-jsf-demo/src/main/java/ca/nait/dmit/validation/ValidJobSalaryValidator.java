package ca.nait.dmit.validation;

import ca.nait.dmit.model.Job;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class ValidJobSalaryValidator implements ConstraintValidator<ValidJobSalary, Job> {

	@Override
	public boolean isValid(Job value, ConstraintValidatorContext context) {
		if (Objects.isNull(value) 
			|| Objects.isNull(value.getMinimumSalary())
			|| Objects.isNull(value.getMaximumSalary())) {
			return true;
		}
		
		// Maximum salary must be greater than minimum salary if both are set
		return value.getMaximumSalary().compareTo(value.getMinimumSalary()) > 0;
	}

}
