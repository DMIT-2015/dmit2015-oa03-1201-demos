package dmit2015.resource;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import dmit2015.model.TodoItem;
import org.junit.jupiter.api.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    @Order(1)
    void createTodoItemUsingJavaObject() {
        TodoItem newTodoItem = new TodoItem();
        newTodoItem.setName("POST TodoItem using Java object");
        newTodoItem.setComplete(true);
        Response postResponse = baseTarget
                .register(JacksonJsonProvider.class)
                .request()
                .post(Entity.json(newTodoItem));
        assertEquals(postResponse.getStatus(), Response.Status.CREATED.getStatusCode());
        assertEquals("http://localhost:8080/dmit2015-project-backend-start/webapi/TodoItem/4", postResponse.getLocation().toString());
        postResponse.close();
    }

    @Test
    @Order(2)
    void readOneTodoItemAsJavaObject() {
        final Long itemId = 4L;
        Response readResponse = baseTarget
                .register(JacksonJsonProvider.class)
                .path("{id}")
                .resolveTemplate("id", itemId)
                .request()
                .get();
        assertEquals(readResponse.getStatus(), Response.Status.OK.getStatusCode());
        TodoItem singleTodoItem = readResponse.readEntity(TodoItem.class);
        assertEquals(itemId, singleTodoItem.getId());
        assertEquals("POST TodoItem using Java object", singleTodoItem.getName());
        assertEquals(true, singleTodoItem.isComplete());
        readResponse.close();
    }

    @Test
    @Order(4)
    void readAllAsJavaObjects() {
        GenericType<List<TodoItem>> responseType = new GenericType<>() {};
        Response readResponse = baseTarget
                .register(JacksonJaxbJsonProvider.class)
                .request()
                .get();
        List<TodoItem> todoItemList = readResponse.readEntity(responseType);
        assertEquals(4, todoItemList.size());
        readResponse.close();
    }

    @Test
    @Order(3)
    void updateTodoItem() {
        final Long itemId = 4L;
        Response readResponse = baseTarget
                .register(JacksonJsonProvider.class)
                .path("{id}")
                .resolveTemplate("id", itemId)
                .request()
                .get();
        assertEquals(readResponse.getStatus(), Response.Status.OK.getStatusCode());
        TodoItem existingTodoItem = readResponse.readEntity(TodoItem.class);
        existingTodoItem.setName("PUT TodoItem using JavaO bject");
        existingTodoItem.setComplete(false);

        Response putResponse = baseTarget
                .path("{id}")
                .resolveTemplate("id",itemId)
                .request()
                .put(Entity.json(existingTodoItem));
        assertEquals(putResponse.getStatus(), Response.Status.NO_CONTENT.getStatusCode());
        putResponse.close();
    }

    @Test
    @Order(5)
    void deleteTodoItem() {
        final Long itemId = 4L;
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