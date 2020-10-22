package ca.nait.dmit.controller;

import org.omnifaces.util.Messages;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named  // This allows you to use EL access an object of this class named the same as class name
        // except the first letter is in lower case
@ViewScoped  // Create the named object for one HTTP request and destroy it when the next request is for a different page
            // A ViewScoped class MUST implement Serializable interface
public class ViewScopedCounterController implements Serializable {

    private int counter = 0;

    public int getCounter() {
        return counter;
    }

    public String submit() {
        // Increment counter by one
        counter += 1;
        // Send a global message with the counter value
        Messages.addInfo(null,"ViewScoped Counter = {0}", counter);
        // Send a message to a component named result
        Messages.addInfo("result","ViewScoped Result Counter = {0}", counter);

        return "";
    }
}
