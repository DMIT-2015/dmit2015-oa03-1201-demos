package dmit2015.service;

import common.services.MailBean;
import dmit2015.model.CourseAssignment;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;

@Stateless
public class CourseAssignmentProgrammaticTimers {

    @Inject
    private MailBean mailBean;

    @Resource    // This is a container created resource
    TimerService timerService;

    @Timeout
    public void timeout(Timer timer) {
        @SuppressWarnings("unchecked")
        CourseAssignment currentAssignment = (CourseAssignment) timer.getInfo();
        String message = String.format("%s is due on %s",
                currentAssignment.getAssignmentName(),
                currentAssignment.getDueDateTime());
        System.out.println(message);

    }

    public Timer createCalendarTimer(CourseAssignment info, int year, int month, int dayOfMonth, int hourOfDay, int minute) {
        // Create a Serializable object to pass information
        TimerConfig timerConfig = new TimerConfig();
        timerConfig.setInfo(info);
        ScheduleExpression scheduleExpression = new ScheduleExpression();
        scheduleExpression.year(year);
        scheduleExpression.month(month);
        scheduleExpression.dayOfMonth(dayOfMonth);
        scheduleExpression.hour(hourOfDay);
        scheduleExpression.minute(minute);
        return timerService.createCalendarTimer(scheduleExpression, timerConfig);
    }
}
