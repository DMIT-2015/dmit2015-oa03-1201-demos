package common.config;

import org.eclipse.microprofile.auth.LoginConfig;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("webapi")
@LoginConfig(authMethod="MP-JWT", realmName="MP JWT Realm")
//@DeclareRoles({"USER","ADMIN"})
public class JAXRSConfiguration extends Application {

}
