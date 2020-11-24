package common.security.interceptor;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.security.enterprise.SecurityContext;

public class AdminOnlyPermissionSecurityInterceptor {

    @Inject
    private SecurityContext securityContext;

    @AroundInvoke
    public Object verifyAccess(InvocationContext ic) throws Exception {
        // Only authenticated users with the ADMIN role can access these methods
        boolean isAdminRole = securityContext.isCallerInRole("ADMIN");
        if (!isAdminRole) {
            throw new RuntimeException("Access denied! You do not have permission to execute this method");
        }
        return ic.proceed();
    }
}
