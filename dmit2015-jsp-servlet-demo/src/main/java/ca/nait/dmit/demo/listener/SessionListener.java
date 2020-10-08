package ca.nait.dmit.demo.listener;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class SessionListener
 *
 */
@WebListener
public class SessionListener implements HttpSessionListener {

    /**
     * Default constructor. 
     */
    public SessionListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent se)  { 
    	 ServletContext application = se.getSession().getServletContext();
         application.log("DMIT2015 Demo: New HTTP session created");
         
         // Get sessionCount from application scope
         Integer sessionCount = (Integer) application.getAttribute(StartupShutdownListener.SESSION_COUNT);
         // Increment sessionCount by one
         sessionCount += 1;
         application.log("DMIT2015 Demo: Active session count: " + sessionCount);
         // Store sessionCount to application scope
         application.setAttribute(StartupShutdownListener.SESSION_COUNT, sessionCount);
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent se)  { 
    	ServletContext application = se.getSession().getServletContext();
        application.log("DMIT2015 Demo: HTTP session destroyed");
        
        // Get sessionCount from application scope
        Integer sessionCount = (Integer) application.getAttribute(StartupShutdownListener.SESSION_COUNT);
        // Decrement sessionCount by one
        sessionCount -= 1;
        application.log("DMIT2015 Demo: Active session count: " + sessionCount);
        // Store sessionCount to application scope
        application.setAttribute(StartupShutdownListener.SESSION_COUNT, sessionCount);
        
    }
	
}
