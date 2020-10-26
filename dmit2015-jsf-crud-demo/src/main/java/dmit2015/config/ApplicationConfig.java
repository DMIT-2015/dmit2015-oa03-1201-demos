package dmit2015.config;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;

@ApplicationScoped  // an object of this class is create just once in the application
@FacesConfig        // enable JSF 2.3 version support
public class ApplicationConfig {
}
