package common.security.service;

import common.security.model.LoginUser;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.security.enterprise.SecurityContext;
import java.util.logging.Logger;

public class LoginUserSecurityInterceptor {
	
	private Logger logger = Logger.getLogger(LoginUserSecurityInterceptor.class.getName());

	@Inject
	private SecurityContext securityContext;
	
	@AroundInvoke
	public Object verifyAccess(InvocationContext context) throws Exception {
		String methodName = context.getMethod().getName();
		logger.info("Intercepting invoke to method: " + methodName);

		// The ADMIN and DEVELOPER roles can manage any user account
		if (methodName.matches("^delete.*$") || methodName.matches("^list.*$")) {
			boolean isDeveloper = securityContext.isCallerInRole(SecurityRoles.DEVELOPER);
			boolean isInstructor = securityContext.isCallerInRole(SecurityRoles.INSTRUCTOR);
			if (!isDeveloper && !isInstructor) {
				if (securityContext.getCallerPrincipal() != null) {
					// user has been authenticated
					String username = securityContext.getCallerPrincipal().getName();
					String systemMessage = String.format("Unauthorized access to method \"%s\" from username \"%s\".", methodName, username);
					logger.warning(systemMessage);
					String userMessage = String.format("Access denied! User \"%s\" do not have permission to execute this method", username);
					throw new RuntimeException(userMessage);
				} else {
					// user has not been authenticated
					String userMessage = String.format("Access denied! You do not have permission to execute this method");
					throw new RuntimeException(userMessage);
				}
			}
		} else if (methodName.matches("^update.*$")) {
			boolean isDeveloper = securityContext.isCallerInRole(SecurityRoles.DEVELOPER);
			boolean isInstructor = securityContext.isCallerInRole(SecurityRoles.INSTRUCTOR);
			if (!isDeveloper && !isInstructor) {
				if (securityContext.getCallerPrincipal() != null) {
					LoginUser existingUser = (LoginUser) context.getParameters()[0];
					String username = securityContext.getCallerPrincipal().getName();
					if (!existingUser.getUsername().equals(username)) {
						String systemMessage = String.format("Unauthorized access to method \"%s\" from username \"%s\".", methodName, username);
						logger.warning(systemMessage);
						String userMessage = String.format("Access denied! You do not have permission to update information on other users", username);
						throw new RuntimeException(userMessage);
					}
				} else {
					// user has not been authenticated
					String userMessage = String.format("Access denied! You do not have permission to execute this method");
					throw new RuntimeException(userMessage);
				}
			}
		} else if (methodName.matches("changePassword")) {
			if (securityContext.isCallerInRole("*")) {
				String username = securityContext.getCallerPrincipal().getName();
				String updateUsername = (String) context.getParameters()[0];
				if (!username.equals(updateUsername)) {
					String systemMessage = String.format("Unauthorized access to method \"%s\" from username \"%s\".", methodName, username);
					logger.warning(systemMessage);
					String userMessage = String.format("Access denied! You do not have permission to change password for another user", username);
					throw new RuntimeException(userMessage);
				}
			} else {
				// user has not been authenticated
				String userMessage = String.format("Access denied! You do not have permission to execute this method");
				throw new RuntimeException(userMessage);
			}
		}

		Object result = context.proceed();
		logger.info("Return from invoking method: " + methodName);
		return result;
	}
}