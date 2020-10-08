package ca.nait.dmit.demo.listener;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class StartupShutdownListener
 *
 */
@WebListener
public class StartupShutdownListener implements ServletContextListener {

	public static final String SESSION_COUNT = "sessionCount";
	
    /**
     * Default constructor. 
     */
    public StartupShutdownListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
    	ServletContext application = sce.getServletContext();
		application.log("DMIT2015 Demo: webapp shutdown");
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
    	ServletContext application = sce.getServletContext();
		application.log("DMIT2015 Demo: webapp started");
		
		// Store the number of session in application scope
		application.setAttribute(SESSION_COUNT, 0);
		
		// Create a new Properties object
		Properties userProps = new Properties();
		try {
			// Load the Properties object with data from "/users.properties"
			userProps.load(getClass().getResourceAsStream("/users.properties"));
			// Store the appProperties in application scope
			application.setAttribute("userProps", userProps);
		} catch (IOException e) {
			application.log("Server loading user.properties");
			e.printStackTrace();
		}
    }
	
}
