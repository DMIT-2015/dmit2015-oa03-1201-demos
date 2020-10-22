package ca.nait.dmit.model;

import ca.nait.dmit.validation.NewJobChecks;
import ca.nait.dmit.validation.ValidJobSalary;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@ValidJobSalary
@Data
public class Job {

	// JobId is required and contains less than or equal to 10 characters
	// JobId must be in the format AA_AAAAAAA
	// JobId belongs to the validation group NewJobChecks
	@NotBlank(message = "JobId is required")
	@Pattern(regexp = "[a-zA-Z]{2}_[a-zA-Z]{2,7}", groups = {NewJobChecks.class}, message = "JobId must be in the format AA_AAAAAAA")
	private String jobId;
	
	// Job Title is required and contains less than or equal to 35 characters
	@NotBlank(message = "Job Title is required")
	@Size(max = 35, message = "Job Title must be less or equal to {max} characters")
	private String jobTitle;
	
	// Both minimumSalary and maximumSalary are optional.
	// minimumSalary must be less or equal to the maximumSalary 
	private BigDecimal minimumSalary;
	
	private BigDecimal maximumSalary;
}
