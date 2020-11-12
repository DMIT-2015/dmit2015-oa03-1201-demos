package dmit2015.view;

import dmit2015.model.CourseAssignment;
import dmit2015.service.CourseAssignmentRepository;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import javax.annotation.PostConstruct;
import javax.faces.annotation.ManagedProperty;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Optional;

@Named("currentCourseAssignmentDeleteController")
@ViewScoped
public class CourseAssignmentDeleteController implements Serializable {

    @Inject
    private CourseAssignmentRepository assignmentRepository;

    @Inject @ManagedProperty("#{param.editId}")
    @Getter @Setter
    private Long editId;

    @Getter
    private CourseAssignment existingCourseAssignment;

    @PostConstruct
    public void init() {
        if (!Faces.isPostback() && editId != null) {
            Optional<CourseAssignment> optionalCourseAssignment = assignmentRepository.findById(editId);
            if (optionalCourseAssignment.isPresent()) {
                existingCourseAssignment = optionalCourseAssignment.get();
            } else {
                Messages.addGlobalFatal("There is no record with an id of {0} to delete.", editId);
            }
        }
    }

    public String onDelete() {
        String nextPage = "";
        try {
            assignmentRepository.remove(existingCourseAssignment.getId());
            Messages.addFlashGlobalInfo("Delete completed successfully.");
            nextPage = "index?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            Messages.addGlobalError("Delete completed with the error: {0}", e.getMessage());
        }
        return nextPage;
    }
}
