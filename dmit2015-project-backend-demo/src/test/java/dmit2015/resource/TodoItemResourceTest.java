package dmit2015.resource;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import dmit2015.model.TodoItem;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TodoItemResourceTest {

    static final String BASE_URI_TODOITEM = "http://localhost:8080/dmit2015-project-backend-start/webapi/TodoItem";
    static Client jaxrsClient = ClientBuilder.newClient();
    static WebTarget baseTarget;

    @BeforeAll
    static void setUpBeforeAll() {
        baseTarget = jaxrsClient.target(BASE_URI_TODOITEM);
    }

    @BeforeEach
    void setUp() {

    }

    @Test
    void createTodoItem() {
        JsonObject postTodoItemJsonObject = Json.createObjectBuilder()
                .add("name","POST TodoItem JsonObject")
                .add("complete", true)
                .build();
        Response postResponse = baseTarget
                .request()
                .post(Entity.json(postTodoItemJsonObject.toString()));
        assertEquals(postResponse.getStatus(), Response.Status.CREATED.getStatusCode());
        assertEquals("http://localhost:8080/dmit2015-project-backend-start/webapi/TodoItem/6", postResponse.getLocation().toString());
        postResponse.close();
    }

    @Test
    void createTodoItemUsingJsonString() {
        String postBodyContent = "{\"name\":\"POST TodoItem using JsonString\",\"complete\":true}";
        Response postResponse = baseTarget
                .request()
                .post(Entity.json(postBodyContent));
        assertEquals(postResponse.getStatus(), Response.Status.CREATED.getStatusCode());
        assertEquals("http://localhost:8080/dmit2015-project-backend-start/webapi/TodoItem/7", postResponse.getLocation().toString());
        postResponse.close();
    }

    @Test
    void createTodoItemUsingJavaObject() {
        TodoItem newTodoItem = new TodoItem();
        newTodoItem.setName("POST TodoItem using Java object");
        newTodoItem.setComplete(true);
        Response postResponse = baseTarget
                .register(JacksonJsonProvider.class)
                .request()
                .post(Entity.json(newTodoItem));
        assertEquals(postResponse.getStatus(), Response.Status.CREATED.getStatusCode());
        assertEquals("http://localhost:8080/dmit2015-project-backend-start/webapi/TodoItem/8", postResponse.getLocation().toString());
        postResponse.close();
    }

    @Test
    void readOneTodoItem() {
        final Long itemId = 2L;
        Response readResponse = baseTarget
                .path("{id}")
                .resolveTemplate("id", itemId)
                .request()
                .get();
        assertEquals(readResponse.getStatus(), Response.Status.OK.getStatusCode());
        String responseBody = readResponse.readEntity(String.class);
        Jsonb jsonb = JsonbBuilder.create();
        JsonObject todoItemJsonObject = jsonb.fromJson(responseBody, JsonObject.class);
        assertEquals("Todo 2", todoItemJsonObject.getString("name"));
        assertEquals(false, todoItemJsonObject.getBoolean("complete"));
        assertEquals(itemId, todoItemJsonObject.getInt("id"));
        readResponse.close();
    }

    @Test
    void readOneTodoItemAsJavaObject() {
        final Long itemId = 2L;
        Response readResponse = baseTarget
                .register(JacksonJsonProvider.class)
                .path("{id}")
                .resolveTemplate("id", itemId)
                .request()
                .get();
        assertEquals(readResponse.getStatus(), Response.Status.OK.getStatusCode());
        TodoItem singleTodoItem = readResponse.readEntity(TodoItem.class);
        assertEquals(itemId, singleTodoItem.getId());
        assertEquals("Todo 2", singleTodoItem.getName());
        assertEquals(false, singleTodoItem.isComplete());
        readResponse.close();
    }

    @Test
    void readAllAsJavaObjects() {
        GenericType<List<TodoItem>> responseType = new GenericType<>() {};
        Response readResponse = baseTarget
                .register(JacksonJaxbJsonProvider.class)
                .request()
                .get();
        List<TodoItem> todoItemList = readResponse.readEntity(responseType);
        assertEquals(3, todoItemList.size());
//        todoItemList.forEach(System.out::println);
        readResponse.close();
    }

    @Test
    void readAll() {
        Response readResponse = baseTarget
                .request()
                .get();
        assertEquals(Response.Status.OK.getStatusCode(), readResponse.getStatus());
        String responseBody = readResponse.readEntity(String.class);
//        System.out.println(responseBody);
        Jsonb jsonb = JsonbBuilder.create();
        JsonArray todoItemArray = jsonb.fromJson(responseBody, JsonArray.class);
        assertEquals(3, todoItemArray.size());
//        todoItemArray.forEach(System.out::println);
        readResponse.close();
    }

    @Test
    void updateTodoItem() {
        final Long itemId = 7L;
        String bodyContent = "{\"id\":7,\"name\":\"PUT TodoItem using JsonString\",\"complete\":false}";
        Response putResponse = baseTarget
                .path("{id}")
                .resolveTemplate("id",itemId)
                .request()
                .put(Entity.json(bodyContent));
        assertEquals(putResponse.getStatus(), Response.Status.NO_CONTENT.getStatusCode());
        putResponse.close();

        Response readResponse = baseTarget
                .register(JacksonJsonProvider.class)
                .path("{id}")
                .resolveTemplate("id", itemId)
                .request()
                .get();
        assertEquals(readResponse.getStatus(), Response.Status.OK.getStatusCode());
        TodoItem singleTodoItem = readResponse.readEntity(TodoItem.class);
        assertEquals(itemId, singleTodoItem.getId());
        assertEquals("PUT TodoItem using JsonString", singleTodoItem.getName());
        assertEquals(false, singleTodoItem.isComplete());
        readResponse.close();
    }

    @Test
    void deleteTodoItem() {
        final Long itemId = 5L;
        Response deleteResponse = baseTarget
                .path("{id}")
                .resolveTemplate("id", itemId)
                .request()
                .delete();
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), deleteResponse.getStatus());
        deleteResponse = baseTarget
                .path("{id}")
                .resolveTemplate("id", itemId)
                .request()
                .delete();
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), deleteResponse.getStatus());
    }

}