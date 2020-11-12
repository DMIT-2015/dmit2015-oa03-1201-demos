package common.security.service;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.security.enterprise.SecurityContext;

public class LoginGroupSecurityInterceptor {

	private final Logger logger = Logger.getLogger(LoginGroupSecurityInterceptor.class.getName());

	@Inject
	private SecurityContext securityContext;
		
	@AroundInvoke
	public Object verifyAccess(InvocationContext context) throws Exception {
		String methodName = context.getMethod().getName();
		logger.info("Intercepting invoke to method: " + methodName);

		if (securityContext.getCallerPrincipal() == null) {
			// user has not been authenticated
			String userMessage = String.format("Access denied! You do not have permission to execute this method");
			throw new RuntimeException(userMessage);
		} else {
			boolean isInstructor = securityContext.isCallerInRole(SecurityRoles.INSTRUCTOR);
			if (!isInstructor) {
				String username = securityContext.getCallerPrincipal().getName();
				String systemMessage = String.format("Unauthorized access to method \"%s\" from username \"%s\".", methodName, username);
				logger.warning(systemMessage);
				String userMessage = String.format("Access denied! User \"%s\" does not have permission to execute this method", username);
				throw new RuntimeException(userMessage);
			}
		}

		Object result = context.proceed();
		logger.info("Return from invoking method: " + methodName);
		return result;
	}
}