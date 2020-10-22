package ca.nait.dmit.controller;

import org.omnifaces.util.Messages;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named  // This allows you to use EL access an object of this class named the same as class name
        // except the first letter is in lower case
@RequestScoped  // Create the named object for one HTTP request and destroy it after sending the HTTP response
public class RequestedScopedCounterController {

    private int counter = 0;

    public int getCounter() {
        return counter;
    }

    public String submit() {
        // Increment counter by one
        counter += 1;
        // Send a global message with the counter value
        Messages.addInfo(null,"RequestScoped Counter = {0}", counter);
        // Send a message to a component named result
        Messages.addInfo("result","RequestScoped Result Counter = {0}", counter);

        return "";
    }
}
