package common.security;

import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.logging.Logger;

@WebListener
public class SecurityStartupListener implements ServletContextListener {

    private static final Logger logger = Logger.getLogger(SecurityStartupListener.class.getName());

    @Inject
    SecurityContext securityContext;

    @Inject
    CallerUserRepository callerUserRepository;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        if (callerUserRepository.findById("user2015").isEmpty()) {
            CallerUser user2015 = new CallerUser();
            user2015.setUsername("user2015");
            callerUserRepository.add(user2015, "Password2015", new String[] {"USER"});
            logger.info("Created user account for user2015");
        }
        if (callerUserRepository.findById("admin2015").isEmpty()) {
            CallerUser admin2015 = new CallerUser();
            admin2015.setUsername("admin2015");
            callerUserRepository.add(admin2015, "Password2015", new String[] {"ADMIN"});
            logger.info("Created user account for admin2015");
        }
        if (callerUserRepository.findById("useradmin2015").isEmpty()) {
            CallerUser useradmin2015 = new CallerUser();
            useradmin2015.setUsername("useradmin2015");
            callerUserRepository.add(useradmin2015, "Password2015", new String[] {"USER","ADMIN"});
            logger.info("Created user account for useradmin2015");
        }
   }
}
