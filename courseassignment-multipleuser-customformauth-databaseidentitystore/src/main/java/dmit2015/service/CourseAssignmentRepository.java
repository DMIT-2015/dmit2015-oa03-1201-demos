package dmit2015.service;

import common.security.interceptor.AnonymousUserSecurityInterceptor;
import dmit2015.model.CourseAssignment;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.SecurityContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional      // Every method in class requires a new transaction
@Interceptors({AnonymousUserSecurityInterceptor.class})
public class CourseAssignmentRepository {

    @Inject
    private CourseAssignmentProgrammaticTimers assignmentProgrammaticTimers;

    @Inject
    private SecurityContext securityContext;

    @PersistenceContext(unitName = "h2database-jpa-pu")     // The unitUnit is only needed when there are multiple persistence units defined in persistence.xml
    private EntityManager em;

    public void add(CourseAssignment newCourseAssignment) {
        String username = securityContext.getCallerPrincipal().getName();
        newCourseAssignment.setUsername(username);

//        assignmentProgrammaticTimers.createCalendarTimer(newCourseAssignment,
//                newCourseAssignment.getDueDateTime().getYear(),
//                newCourseAssignment.getDueDateTime().getMonth().getValue(),
//                newCourseAssignment.getDueDateTime().getDayOfMonth(),
//                newCourseAssignment.getDueDateTime().getHour(),
//                newCourseAssignment.getDueDateTime().getMinute());

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
        List<CourseAssignment> courseAssignments;
        String username = securityContext.getCallerPrincipal().getName();
        if (securityContext.isCallerInRole("INSTRUCTOR")) {
            courseAssignments = em.createQuery("SELECT ca FROM CourseAssignment ca ORDER BY ca.assignmentName", CourseAssignment.class).getResultList();
        } else {
            courseAssignments =  em.createQuery(
                    "SELECT ca FROM CourseAssignment ca WHERE ca.username = :usernameValue ORDER BY ca.dueDateTime "
                    , CourseAssignment.class).setParameter("usernameValue", username).getResultList();
        }
        return courseAssignments;
    }

}