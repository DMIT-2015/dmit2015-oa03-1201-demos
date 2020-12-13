package dmit2015.view;

import dmit2015.service.TodoItemResourceJaxRsClient;
import dmit2015.model.TodoItem;
import lombok.Getter;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("currrentTodoIndexController")
@ViewScoped
public class TodoItemIndexController implements Serializable {

    TodoItemResourceJaxRsClient jaxRsClient = new TodoItemResourceJaxRsClient();

    @Getter
    List<TodoItem> todoItemList;

    @PostConstruct
    void init() {
        todoItemList = jaxRsClient.getAll();
    }

}
