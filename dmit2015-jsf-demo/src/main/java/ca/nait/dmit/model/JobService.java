package ca.nait.dmit.model;

import lombok.Getter;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;

@ApplicationScoped
public class JobService {

    @Getter
    private java.util.List<Job> jobs = new ArrayList<>();

    public void addJob(Job newJob) {
        jobs.add(newJob);
    }

}
