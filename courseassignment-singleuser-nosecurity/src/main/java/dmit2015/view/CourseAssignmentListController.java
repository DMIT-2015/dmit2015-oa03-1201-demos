package dmit2015.view;

import dmit2015.model.CourseAssignment;
import dmit2015.service.CourseAssignmentRepository;
import org.omnifaces.util.Messages;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("currentCourseAssignmentListController")
@ViewScoped
public class CourseAssignmentListController implements Serializable {

    @Inject
    private CourseAssignmentRepository assignmentRepository;

    private List<CourseAssignment> assignments;

    @PostConstruct  // After @Inject is complete
    public void init() {
        // Fetch the list of assignments only once
        try {
            assignments = assignmentRepository.list();
        } catch (RuntimeException ex) {
            Messages.addGlobalError(ex.getMessage());
        }
    }

    public List<CourseAssignment> list() {
        return assignments;
    }

}
