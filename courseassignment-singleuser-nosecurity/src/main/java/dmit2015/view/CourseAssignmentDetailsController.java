package dmit2015.view;

import dmit2015.model.CourseAssignment;
import dmit2015.service.CourseAssignmentRepository;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.ManagedProperty;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Optional;

@Named("currentCourseAssignmentDetailsController")
@RequestScoped
public class CourseAssignmentDetailsController {

    @Inject
    private CourseAssignmentRepository assignmentRepository;

    @Inject @ManagedProperty("#{param.editId}")
    @Getter @Setter
    private Long editId;

    @Getter
    private CourseAssignment existingCourseAssignment;

    @PostConstruct
    public void init() {
        Optional<CourseAssignment> optionalCourseAssignment = assignmentRepository.findById(editId);
        if (optionalCourseAssignment.isPresent()) {
            existingCourseAssignment = optionalCourseAssignment.get();
        }
    }

}
