package dmit2015.view;

import org.omnifaces.util.Messages;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.validation.constraints.NotBlank;

@Named
@RequestScoped
public class HelloUsernameController {

    @NotBlank(message="Please enter a username.")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String submit() {
//        FacesContext.getCurrentInstance().addMessage(null,
//                new FacesMessage("Hello " + username + " and welcome to JSF world!"));

        Messages.addGlobalInfo("Hello {0} and welcome to OmniFaces world!", username);

        return "";
    }
}
