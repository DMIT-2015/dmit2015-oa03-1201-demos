package ca.nait.dmit.controller;

import org.omnifaces.util.Messages;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named  // This allows you to use EL access an object of this class named the same as class name
        // except the first letter is in lower case
@ApplicationScoped  // Create the named object the first time it is accessed and is destroyed the when app restarts
public class ApplicationScopedCounterController {

    private int counter = 0;

    public int getCounter() {
        return counter;
    }

    public String submit() {
        // Increment counter by one
        counter += 1;
        // Send a global message with the counter value
        Messages.addInfo(null,"ApplicationScoped Counter = {0}", counter);
        // Send a message to a component named result
        Messages.addInfo("result","ApplicationScoped Result Counter = {0}", counter);

        return "";
    }
}
