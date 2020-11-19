package dmit2015.interceptor;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.security.enterprise.SecurityContext;

public class AnonymousUserSecurityInterceptor {
    @Inject
    private SecurityContext securityContext;

    @AroundInvoke
    public Object verifyAccess(InvocationContext ic) throws Exception {
        String methodName = ic.getMethod().getName();
// Only authenticated users can access this method
        if (securityContext.getCallerPrincipal() == null) {
            throw new RuntimeException("Access denied! Only authenticated users have permission to execute this method ");
        }
        return ic.proceed();
    }
}