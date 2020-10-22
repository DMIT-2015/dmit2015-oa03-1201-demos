package ca.nait.dmit.controller;

import org.omnifaces.util.Messages;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named  // This allows you to use EL access an object of this class named the same as class name
        // except the first letter is in lower case
@SessionScoped  // Create the named object for one HTTP session and destroy it when the HTTP session expires
            // A SessionScoped class MUST implement Serializable interface
public class SessionScopedCounterController implements Serializable {

    private int counter = 0;

    public int getCounter() {
        return counter;
    }

    public String submit() {
        // Increment counter by one
        counter += 1;
        // Send a global message with the counter value
        Messages.addInfo(null,"SessionScoped Counter = {0}", counter);
        // Send a message to a component named result
        Messages.addInfo("result","SessionScoped Result Counter = {0}", counter);

        return "";
    }
}
