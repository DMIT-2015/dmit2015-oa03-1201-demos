package dmit2015.service;

import common.services.MailBean;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timer;
import javax.inject.Inject;
import javax.mail.MessagingException;

@Singleton
@Startup
public class CourseAssignmentAutomaticTimers {
    /**
     * Assuming you have define the following entries in your web.xml file
     *     <env-entry>
     *         <env-entry-name>ca.dmit2015.config.SYSADMIN_EMAIL</env-entry-name>
     *         <env-entry-type>java.lang.String</env-entry-type>
     *         <env-entry-value>yourUsername@yourEmailServer</env-entry-value>
     *     </env-entry>
     */
    @Resource(name="ca.dmit2015.config.SYSADMIN_EMAIL")
    private String mailToAddress;

    @Inject
    private MailBean mailBean;

    // Create an automatic timer expires at 5pm every weekday.
//    @Schedule(hour = "17", dayOfWeek = "Mon-Fri")
//    public void sendNotification(Timer timer) {
//        if (!StringUtils.isBlank(mailToAddress)) {
//            // Send email notification
//            try {
//                mailBean.sendMail(mailToAddress,"mail subject","mail text");
//            } catch (MessagingException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}

