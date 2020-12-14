package dmit2015.resource;

import dmit2015.model.TodoItem;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TodoItemResourceJaxRsClientTest {

    TodoItemResourceJaxRsClient currentTodoItemResourceJaxRsClient = new TodoItemResourceJaxRsClient();
    String testDataResourceLocation;    // requires to add @TestInstance(TestInstance.Lifecycle.PER_CLASS) before class

    @Test
    @Order(2)
    void createTodoItem() {
        TodoItem newTodoItem = new TodoItem();
        newTodoItem.setName("Create a JUNIT Test Case method for POST");
        newTodoItem.setComplete(false);
        testDataResourceLocation = currentTodoItemResourceJaxRsClient.create(newTodoItem);
        assertFalse(testDataResourceLocation.equals(""));

        Optional<TodoItem> optionalTodoItem = currentTodoItemResourceJaxRsClient.getOneByLocation(testDataResourceLocation);
        assertTrue(optionalTodoItem.isPresent());
        TodoItem existingTodoItem = optionalTodoItem.get();
        assertEquals(newTodoItem.getName(), existingTodoItem.getName());
        assertEquals(newTodoItem.isComplete(), existingTodoItem.isComplete());
    }

    @Test
    @Order(1)
    void readOneTodoItem() {
        // Test for a valid id
        Long queryValidId = 2L;
        Optional<TodoItem> optionalTodoItem = currentTodoItemResourceJaxRsClient.getOneById(queryValidId);
        assertTrue(optionalTodoItem.isPresent());
        TodoItem existingTodoItem = optionalTodoItem.get();
        assertEquals(queryValidId, existingTodoItem.getId());
        assertEquals("Todo 2", existingTodoItem.getName());
        assertEquals(true, existingTodoItem.isComplete());

        //Test for an invalid id
        Long queryInvalidId = 9999L;
        optionalTodoItem = currentTodoItemResourceJaxRsClient.getOneById(queryInvalidId);
        assertTrue(optionalTodoItem.isEmpty());
    }

    @Test
    @Order(4)
    void readAll() {
        List<TodoItem> todoItemList = currentTodoItemResourceJaxRsClient.getAll();
        assertEquals(4, todoItemList.size());
        todoItemList.forEach(System.out::println);
    }

    @Test
    @Order(3)
    void updateTodoItem() {
        Optional<TodoItem> optionalTodoItem = currentTodoItemResourceJaxRsClient.getOneByLocation(testDataResourceLocation);
        assertTrue(optionalTodoItem.isPresent());
        TodoItem existingTodoItem = optionalTodoItem.get();
        existingTodoItem.setName("Change TodoItem name using PUT request");
        existingTodoItem.setComplete(true);
        boolean success = currentTodoItemResourceJaxRsClient.update(existingTodoItem.getId(), existingTodoItem);
        assertTrue(success);

        optionalTodoItem = currentTodoItemResourceJaxRsClient.getOneById(existingTodoItem.getId());
        assertTrue(optionalTodoItem.isPresent());
        TodoItem updatedTodoItem = optionalTodoItem.get();
        assertEquals(existingTodoItem.getId(), updatedTodoItem.getId());
        assertEquals(existingTodoItem.getName(), updatedTodoItem.getName());
        assertEquals(existingTodoItem.isComplete(), updatedTodoItem.isComplete());
    }

    @Test
    @Order(5)
    void deleteTodoItem() {
        Optional<TodoItem> optionalTodoItem = currentTodoItemResourceJaxRsClient.getOneByLocation(testDataResourceLocation);
        assertTrue(optionalTodoItem.isPresent());
        TodoItem existingTodoItem = optionalTodoItem.get();
        boolean success = currentTodoItemResourceJaxRsClient.delete(existingTodoItem.getId());
        assertTrue(success);

        success = currentTodoItemResourceJaxRsClient.delete(existingTodoItem.getId());
        assertFalse(success);

    }

    @Test
    @Order(6)
    void secureCreateTodoItem() {
        JwtResourceJaxRsClient jwtJaxRsClient = new JwtResourceJaxRsClient();
        Optional<String> optionalToken = jwtJaxRsClient.login("user2015","Password2015");
        assertTrue(optionalToken.isPresent());
        String token = optionalToken.get();

        TodoItem newTodoItem = new TodoItem();
        newTodoItem.setName("Create a JUNIT Test Case method for SECURE POST");
        newTodoItem.setComplete(true);
        testDataResourceLocation = currentTodoItemResourceJaxRsClient.secureCreate(newTodoItem, token);
        assertFalse(testDataResourceLocation.equals(""));

        Optional<TodoItem> optionalTodoItem = currentTodoItemResourceJaxRsClient.getOneByLocation(testDataResourceLocation);
        assertTrue(optionalTodoItem.isPresent());
        TodoItem existingTodoItem = optionalTodoItem.get();
        assertEquals(newTodoItem.getName(), existingTodoItem.getName());
        assertEquals(newTodoItem.isComplete(), existingTodoItem.isComplete());

    }
}