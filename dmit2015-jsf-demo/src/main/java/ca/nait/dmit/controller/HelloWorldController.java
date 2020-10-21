package ca.nait.dmit.controller;

import org.omnifaces.util.Messages;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named  // This allows to access an object of this class named helloWorldController using Expression Language
        // To specify the object name explicity you can use @Named("instanceName")
@RequestScoped  // Create an object for one request and discard after the response has been sent
public class HelloWorldController {

    private String username;

    public String submit() {
        Messages.addInfo(null,"Welcome {0} to JSF World!", username);
        return "";
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
