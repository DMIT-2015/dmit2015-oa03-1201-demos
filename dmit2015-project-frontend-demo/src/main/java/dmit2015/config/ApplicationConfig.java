package dmit2015.config;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;

@ApplicationScoped
@FacesConfig        // enable JSF 2.3 features such as using @Inject with @ManagedProperty
public class ApplicationConfig {
}
