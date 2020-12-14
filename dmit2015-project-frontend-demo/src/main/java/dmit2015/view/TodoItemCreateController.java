package dmit2015.view;

import dmit2015.model.TodoItem;
import dmit2015.service.TodoItemResourceJaxRsClient;
import lombok.Getter;
import org.omnifaces.util.Messages;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("currentTodoItemCreateController")
@ViewScoped
public class TodoItemCreateController implements Serializable {
    private static final long serialVersionUID = 1L;

    TodoItemResourceJaxRsClient jaxRsClient = new TodoItemResourceJaxRsClient();

    @Getter
    private TodoItem newTodoItem = new TodoItem();

    public String onCreateNew() {
        String nextPage = "";
        try {
            String location = jaxRsClient.create(newTodoItem);
            if (location.equals("")) {
                Messages.addGlobalWarn("Create was not successful.");
            } else {
                Messages.addGlobalInfo("Create was successful.");
                nextPage = "index.xhtml?faces-redirect=true";
                newTodoItem = new TodoItem();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Messages.addGlobalError("Create was not successful with error " + e.getMessage());
        }

        return nextPage;
    }
}
