package ca.nait.dmit.demo.validation;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class JobValidationTest {
	
	static Validator validator;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void testJobTitleRequiredValidation() {
		Job job1 = new Job();
		job1.setJobTitle(null);
		
		Set<ConstraintViolation<Job>> constraintViolations = validator.validateProperty(
				job1, "jobTitle", Default.class);
		assertEquals(1, constraintViolations.size());
		assertEquals("Job Title is required", constraintViolations.iterator().next().getMessage());
		
		job1.setJobTitle("");
		constraintViolations = validator.validateProperty(
				job1, "jobTitle", Default.class);
		assertEquals(1, constraintViolations.size());
		assertEquals("Job Title is required", constraintViolations.iterator().next().getMessage());
		
		job1.setJobTitle("          ");
		constraintViolations = validator.validateProperty(
				job1, "jobTitle", Default.class);
		assertEquals(1, constraintViolations.size());
		assertEquals("Job Title is required", constraintViolations.iterator().next().getMessage());
		
		job1.setJobTitle("\t\n");
		constraintViolations = validator.validateProperty(
				job1, "jobTitle", Default.class);
		assertEquals(1, constraintViolations.size());
		assertEquals("Job Title is required", constraintViolations.iterator().next().getMessage());
		
		job1.setJobTitle("Software Developer");
		constraintViolations = validator.validateProperty(
				job1, "jobTitle", Default.class);
		assertEquals(0, constraintViolations.size());
	}
	
	@Test
	void testJobTitleSizeValidation() {
		Job job1 = new Job();
		job1.setJobTitle("1234567890123456789012345678901234567890");
		
		Set<ConstraintViolation<Job>> constraintViolations = validator.validateProperty(
				job1, "jobTitle", Default.class);
		assertEquals(1, constraintViolations.size());
		assertEquals("Job Title must be less or equal to 35 characters", constraintViolations.iterator().next().getMessage());
		
		job1.setJobTitle("Software Developer");
		constraintViolations = validator.validateProperty(
				job1, "jobTitle", Default.class);
		assertEquals(0, constraintViolations.size());
		
	}
	
	
	@Test
	void testJobIdRequiredValidation() {
		Job job1 = new Job();
		job1.setJobId(null);
		
		Set<ConstraintViolation<Job>> constraintViolations = validator.validateProperty(
				job1, "jobId", Default.class);
		assertEquals(1, constraintViolations.size());
		assertEquals("JobId is required", constraintViolations.iterator().next().getMessage());
		
		job1.setJobId("");
		constraintViolations = validator.validateProperty(
				job1, "jobId", Default.class);
		assertEquals(1, constraintViolations.size());
		assertEquals("JobId is required", constraintViolations.iterator().next().getMessage());
		
		job1.setJobId("          ");
		constraintViolations = validator.validateProperty(
				job1, "jobId", Default.class);
		assertEquals(1, constraintViolations.size());
		assertEquals("JobId is required", constraintViolations.iterator().next().getMessage());
				
		job1.setJobId("HR_MANAGER");
		constraintViolations = validator.validateProperty(
				job1, "jobId", Default.class);
		assertEquals(0, constraintViolations.size());
	}
	
	@Test
	void testJobIdPatternValidation() {
		Job job1 = new Job();
		job1.setJobId("ABAAAAAAA");
		
		Set<ConstraintViolation<Job>> constraintViolations = validator.validateProperty(
				job1, "jobId", NewJobChecks.class);
		assertEquals(1, constraintViolations.size());
		assertEquals("JobId must be in the format AA_AAAAAAA", constraintViolations.iterator().next().getMessage());
		
		constraintViolations = validator.validateProperty(
				job1, "jobId", Default.class);
		assertEquals(0, constraintViolations.size());
				
		job1.setJobId("HR_MANAGER");
		constraintViolations = validator.validateProperty(
				job1, "jobId", NewJobChecks.class);
		assertEquals(0, constraintViolations.size());
	}
	
	
	@Test
	void testJobSalaryValidation() {
		Job job1 = new Job();
		job1.setJobId("IT_PROG");
		job1.setJobTitle("Software Engineer");
		job1.setMaximumSalary(BigDecimal.valueOf(2_000));
		job1.setMinimumSalary(BigDecimal.valueOf(5_000));
		
		Set<ConstraintViolation<Job>> constraintViolations = validator.validate(job1, Default.class);
		assertEquals(1, constraintViolations.size());
		assertEquals("Maximum salary must be greater than the minimum salary", constraintViolations.iterator().next().getMessage());
		
		job1.setMaximumSalary(BigDecimal.valueOf(2_000));
		job1.setMinimumSalary(BigDecimal.valueOf(2_000));
		
		constraintViolations = validator.validate(job1, Default.class);
		assertEquals(1, constraintViolations.size());
		assertEquals("Maximum salary must be greater than the minimum salary", constraintViolations.iterator().next().getMessage());
		
		job1.setMaximumSalary(BigDecimal.valueOf(5_000));
		job1.setMinimumSalary(BigDecimal.valueOf(2_000));
		constraintViolations = validator.validate(job1,Default.class);
		assertEquals(0, constraintViolations.size());
	}
	

}
