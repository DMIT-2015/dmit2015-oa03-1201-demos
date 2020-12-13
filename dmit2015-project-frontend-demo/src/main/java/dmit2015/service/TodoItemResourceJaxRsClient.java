package dmit2015.service;

import dmit2015.model.TodoItem;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TodoItemResourceJaxRsClient {

//    static final String BASE_URI = "http://localhost:8080/dmit2015-project-backend-start/webapi/";
    static final String BASE_URI_TODOITEM = "http://localhost:8080/dmit2015-project-backend-start/webapi/TodoItem";
    Client jaxrsClient = ClientBuilder.newClient();

    public List<TodoItem> getAll() {
        List<TodoItem> todoItemList = new ArrayList<>();    // return an emtpy list of there are items to return
        Response response = jaxrsClient
                .target(BASE_URI_TODOITEM)
                .request()
                .get();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            GenericType<List<TodoItem>> responseType = new GenericType<>() {};
            todoItemList = response.readEntity(responseType);
        }
        return todoItemList;
    }

    public Optional<TodoItem> getOneById(Long id) {
        Optional<TodoItem> optionalTodoItem = Optional.empty();
        Response response = jaxrsClient
                .target(BASE_URI_TODOITEM)
                .path("{id}")
                .resolveTemplate("id", id)
                .request()
                .get();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            TodoItem existingTodoItem = response.readEntity(TodoItem.class);
            optionalTodoItem = Optional.of(existingTodoItem);
        }
        return optionalTodoItem;
    }

    public Optional<TodoItem> getOneByLocation(String location) {
        Optional<TodoItem> optionalTodoItem = Optional.empty();
        Response response = jaxrsClient
                .target(location)
                .request()
                .get();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            TodoItem existingTodoItem = response.readEntity(TodoItem.class);
            optionalTodoItem = Optional.of(existingTodoItem);
        }
        return optionalTodoItem;
    }

    public String create(TodoItem newTodoItem) {
        String resourceLocation = "";   // The Location of the created resource
        Response response = jaxrsClient
                .target(BASE_URI_TODOITEM)
                .request()
                .post( Entity.json(newTodoItem) );
        if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
            resourceLocation = response.getLocation().toString();
        }
        return resourceLocation;
    }

    public boolean update(Long id, TodoItem existingTodoItem) {
        boolean success = false;
        Response response = jaxrsClient
                .target(BASE_URI_TODOITEM)
                .path("{id}")
                .resolveTemplate("id", id)
                .request()
                .put( Entity.json(existingTodoItem) );
        if (response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
            success = true;
        }
        return success;
    }

    public boolean delete(Long id) {
        boolean success = false;
        Response response = jaxrsClient
                .target(BASE_URI_TODOITEM)
                .path("{id}")
                .resolveTemplate("id", id)
                .request()
                .delete();
        if (response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
            success = true;
        }
        return success;
    }

}
