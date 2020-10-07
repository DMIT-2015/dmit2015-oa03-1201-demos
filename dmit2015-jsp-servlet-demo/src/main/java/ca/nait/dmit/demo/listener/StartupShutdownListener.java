package ca.nait.dmit.demo.listener;

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
         // TODO Auto-generated method stub
    	ServletContext application = sce.getServletContext();
    	application.log("My demo app has stopped");
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    	ServletContext application = sce.getServletContext();
    	application.log("My demo app has started");
    }
	
}
