package common.security.config;

import org.glassfish.soteria.identitystores.annotation.Credentials;
import org.glassfish.soteria.identitystores.annotation.EmbeddedIdentityStoreDefinition;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;

@CustomFormAuthenticationMechanismDefinition(
    loginToContinue = @LoginToContinue(
        loginPage= "/security/customLogin.xhtml",
        useForwardToLogin = false,
        errorPage=""
    )
)

@EmbeddedIdentityStoreDefinition({
    @Credentials(callerName = "dev99@dmit2015.ca",password = "Password2015",groups = {"ADMIN","DEVELOPER"}),
})

@DatabaseIdentityStoreDefinition(
    dataSourceLookup="java:app/datasources/h2databaseDS",
    callerQuery="SELECT password FROM CallerUser WHERE username = ?",
    groupsQuery="SELECT groupname FROM CallerGroup WHERE username = ? "
)
@ApplicationScoped
public class ApplicationSecurityConfig {
}
