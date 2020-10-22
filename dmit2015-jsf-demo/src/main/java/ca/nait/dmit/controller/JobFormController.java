package ca.nait.dmit.controller;

import ca.nait.dmit.model.Job;
import ca.nait.dmit.model.JobService;
import org.omnifaces.util.Messages;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class JobFormController implements Serializable {

    @Inject
    private JobService currentJobService;

    private Job currentJob = new Job();

    public Job getCurrentJob() {
        return currentJob;
    }

    public String addJob() {
        // Send a global message that the job was created
        Messages.addInfo(null,"Successfully added the following job: {0}", currentJob.toString());
        currentJobService.addJob(currentJob);
        // Clear the form by creating a new Job
        currentJob = new Job();
        return "";
    }

    public List<Job> getJobs() {
        return currentJobService.getJobs();
    }
}
