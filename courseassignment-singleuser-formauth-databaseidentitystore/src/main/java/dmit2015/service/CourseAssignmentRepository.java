package dmit2015.service;

import dmit2015.model.CourseAssignment;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional      // Every method in class requires a new transaction
public class CourseAssignmentRepository {

    @PersistenceContext(unitName = "h2database-jpa-pu")     // The unitUnit is only needed when there are multiple persistence units defined in persistence.xml
    private EntityManager em;

    public void add(CourseAssignment newCourseAssignment) {
        em.persist(newCourseAssignment);
    }

    public void update(CourseAssignment updatedCourseAssignment) {

        Optional<CourseAssignment> optionalCourseAssignment = findById(updatedCourseAssignment.getId());
        if (optionalCourseAssignment.isPresent()) {
            CourseAssignment existingCourseAssignment = optionalCourseAssignment.get();
            existingCourseAssignment.setAssignmentName(updatedCourseAssignment.getAssignmentName());
            existingCourseAssignment.setDueDateTime(updatedCourseAssignment.getDueDateTime());
            existingCourseAssignment.setGrade(updatedCourseAssignment.getGrade());
            existingCourseAssignment.setSubmittedDateTime(LocalDateTime.now());
            em.merge(existingCourseAssignment);
            em.flush();
        }
    }

    public void remove(Long id) {

        Optional<CourseAssignment> optionalCourseAssignment = findById(id);
        if (optionalCourseAssignment.isPresent()) {
            CourseAssignment existingCourseAssignment = optionalCourseAssignment.get();
            em.remove(existingCourseAssignment);
            em.flush();
        }

    }

    public Optional<CourseAssignment> findById(Long id) {
        Optional<CourseAssignment> optionalCourseAssignment = Optional.empty();
        try {
            CourseAssignment querySingleResult = em.find(CourseAssignment.class, id);
            if (querySingleResult != null) {
                optionalCourseAssignment = Optional.of(querySingleResult);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return optionalCourseAssignment;
    }

    public List<CourseAssignment> list() {
        return em.createQuery(
                "FROM CourseAssignment ca ORDER BY ca.assignmentName"
                , CourseAssignment.class)
                .getResultList();
    }

}