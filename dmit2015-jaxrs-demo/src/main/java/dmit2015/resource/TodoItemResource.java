package dmit2015.resource;

import dmit2015.model.TodoItem;
import dmit2015.repository.TodoItemRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Optional;


/**
 * * Web API with CRUD methods for managing TodoItem.
 *
 *  URI						Http Method     Request Body		                        Description
 * 	----------------------  -----------		------------------------------------------- ------------------------------------------
 *	/TodoItems				POST			{"name":"Finish DMIT2015 assignment 7",     Create a new TodoItem
 *                                         	"complete":false}
 * 	/TodoItems/{id}			GET			                                                Find one TodoItem with a id value
 * 	/TodoItems		        GET			                                                Find all TodoItem
 * 	/TodoItems/{id}         PUT             {"id":5,
 * 	                                        "name":"Submitted DMIT2015 assignment 7",      Update the TodoItem
 *                                          "complete":true}
 *
 * 	/TodoItems/{id}			DELETE			                                            Remove the TodoItem
 *

 curl -i -X GET http://localhost:8080/api/TodoItems

 curl -i -X GET http://localhost:8080/api/TodoItems/1

 curl -i -X POST http://localhost:8080/api/TodoItems \
 -d '{"name":"Submit DMIT2015 Assignment 7","complete":false}' \
 -H 'Content-Type:application/json'

 curl -i -X POST http://localhost:8080/api/TodoItems \
 -d '{"name":"Submit DMIT2015 Assignment 8","complete":false}' \
 -H 'Content-Type:application/json'

 curl -i -X POST http://localhost:8080/api/TodoItems \
 -d '{"name":"Submit DMIT2015 Assignment 9","complete":false}' \
 -H 'Content-Type:application/json'

 curl -i -X POST http://localhost:8080/api/TodoItems \
 -d '{"name":"Submit DMIT2015 Assignment 10","complete":false}' \
 -H 'Content-Type:application/json'

 curl -i -X GET http://localhost:8080/api/TodoItems/5

 curl -i -X PUT http://localhost:8080/api/TodoItems/1 \
 -d '{"id":1,"name":"Submitted DMIT2015 assignment 7","complete":true}' \
 -H 'Content-Type:application/json'

 curl -i -X GET http://localhost:8080/api/TodoItems/5

 curl -i -X DELETE http://localhost:8080/api/TodoItems/4

 curl -i -X GET http://localhost:8080/api/TodoItems/5

 *
 */

@ApplicationScoped
// This is a CDI-managed bean that is created only once during the life cycle of the application
@Path("TodoItems")	        // All methods of this class are associated this URL path
@Consumes(MediaType.APPLICATION_JSON)	// All methods this class accept only JSON format data
@Produces(MediaType.APPLICATION_JSON)	// All methods returns data that has been converted to JSON format
public class TodoItemResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private TodoItemRepository todoItemRepository;

    @POST   // POST: api/TodoItems
    public Response postTodoItem(TodoItem newTodoItem) {
        if (newTodoItem == null) {
            throw new BadRequestException();
        }
        todoItemRepository.add(newTodoItem);
        // http://localhost:8080/api/TodoItems/5
        URI todoItemsUri = uriInfo.getAbsolutePathBuilder().path(newTodoItem.getId().toString()).build();
        return Response.created(todoItemsUri).build();
    }

    @GET    // GET: api/TodoItems/5
    @Path("{id}")
    public Response getTodoItem(@PathParam("id") Long id) {
        Optional<TodoItem> optionalTodoItem = todoItemRepository.findById(id);

        if (optionalTodoItem.isEmpty()) {
            throw new NotFoundException();
        }
        TodoItem existingTodoItem = optionalTodoItem.get();

        return Response.ok(existingTodoItem).build();
    }


    @GET    // GET: api/TodoItems
    public Response getTodoItems() {
        return Response.ok(todoItemRepository.findAll()).build();
    }

    @PUT    // PUT: api/TodoItems/5
    @Path("{id}")
    public Response updateTodoItem(@PathParam("id") Long id, TodoItem updatedTodoItem) {
        if (!id.equals(updatedTodoItem.getId())) {
            throw new BadRequestException();
        }

        Optional<TodoItem> optionalTodoItem = todoItemRepository.findById(id);

        if (optionalTodoItem.isEmpty()) {
            throw new NotFoundException();
        }
        todoItemRepository.update(updatedTodoItem);

        return Response.ok(updatedTodoItem).build();
    }

    @DELETE // DELETE: api/TodoItems/5
    @Path("{id}")
    public Response deleteTodoItem(@PathParam("id") Long id) {
        Optional<TodoItem> optionalTodoItem = todoItemRepository.findById(id);

        if (optionalTodoItem.isEmpty()) {
            throw new NotFoundException();
        }

        todoItemRepository.remove(id);

        return Response.noContent().build();
    }
}
