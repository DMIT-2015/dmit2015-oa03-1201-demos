package dmit2015.resource;

import dmit2015.model.TodoItem;
import dmit2015.service.TodoItemService;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Optional;

/**
 * Get all TodoItem
 curl -i -X GET http://localhost:8080/dmit2015-project-backend-demo/webapi/TodoItem

 * Get one TodoItem
 curl -i -X GET http://localhost:8080/dmit2015-project-backend-demo/webapi/TodoItem/2

 * Post a new TodoItem
 curl -i -X POST http://localhost:8080/dmit2015-project-backend-demo/webapi/TodoItem \
 -d '{"name":"post TodoItem using curl command", "complete":true}' \
 -H 'Content-Type: application/json'

 curl -i -X POST http://localhost:8080/dmit2015-project-backend-demo/webapi/TodoItem/secure \
 -d '{"name":"post secure TodoItem using curl command", "complete":true}' \
 -H 'Content-Type: application/json' \
 -H 'Authorization: Bearer eyJraWQiOiJxdWlja3N0YXJ0LWp3dC1pc3N1ZXIiLCJ0eXAiOiJqd3QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyMjAxNSIsInVwbiI6InVzZXIyMDE1IiwiaXNzIjoicXVpY2tzdGFydC1qd3QtaXNzdWVyIiwiYXVkIjoiand0LWF1ZGllbmNlIiwiZ3JvdXBzIjpbIlVTRVIiXSwianRpIjoiOWFhZTNhMjctNzY4Yi00ODU1LTk5YzEtYmRlMTIwYmRhNGI1IiwiaWF0IjoxNjA3OTgxMzEyLCJleHAiOjE2MDc5OTU3MTJ9.yTYCI8Gm137E-NhIC7K_Uw4Z7LDTTxDhqmHPYQZ_8MgcV5ktIeAY4mS8PAUPlHsIUW2VXuuiPCvBbQwWitfU1ZWYjsmsWFPBkePAwPF58mnXPQXXndiFNUpGAcu4RNFeRDQBEo76hWSd9TzuBtwc9Sy0eh-LV5kxxq_Tii5iuT-MYdElc9umjJGqRRuWUoh2LzvWKJHFWUfiGF2x-osvaJMgfaOuDwiDfYCUOnAhQZY9U4uPVN-1Pb2RKVptSmQMD6NdF09tNko5JgJJVy21Q7TxU9V7rhinmka77AAZlIQvcBUjiJRUE56Y3ypDg9Y85B5SMiFsGAzSOjsQtjl05A'


 * Get the posted TodoItem
 curl -i -X GET http://localhost:8080/dmit2015-project-backend-demo/webapi/TodoItem/4

 * Update the TodoItem
 curl -i -X PUT http://localhost:8080/dmit2015-project-backend-demo/webapi/TodoItem/4 \
 -d '{"id":4, "name":"put TodoItem using curl command", "complete":false}' \
 -H 'Content-Type: application/json'

 * Get the updated TodoItem
 curl -i -X GET http://localhost:8080/dmit2015-project-backend-demo/webapi/TodoItem/4

 * Delete the TodoItem
 curl -i -X DELETE http://localhost:8080/dmit2015-project-backend-demo/webapi/TodoItem/4
 curl -i -X DELETE http://localhost:8080/dmit2015-project-backend-demo/webapi/TodoItem/4

 * Get the updated TodoItem
 curl -i -X GET http://localhost:8080/dmit2015-project-backend-demo/webapi/TodoItem/4

 *
 *
 *
 *
 */


@Path("TodoItem")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TodoItemResource {

    @Inject
    private TodoItemService currentTodoItemService;

    @Context
    private UriInfo currentUriInfo;

    @RolesAllowed("USER")
    @Path("secure")
    @POST
    public Response secureCreateTodoItem(TodoItem newTodoItem) {
        if (newTodoItem == null) {
            throw new BadRequestException();
        }
        currentTodoItemService.createTodoItem(newTodoItem);
        URI locationUri = currentUriInfo
                .getAbsolutePathBuilder()
                .path(newTodoItem.getId().toString())
                .build();
        return Response.created(locationUri).build();
    }

    @POST
    public Response createTodoItem(TodoItem newTodoItem) {
        if (newTodoItem == null) {
            throw new BadRequestException();
        }
        currentTodoItemService.createTodoItem(newTodoItem);
        URI locationUri = currentUriInfo
                .getAbsolutePathBuilder()
                .path(newTodoItem.getId().toString())
                .build();
        return Response.created(locationUri).build();
    }

    @GET
    @Path("{id}")
    public Response readOneTodoItem(@PathParam("id") Long id) {
        if (id == null) {
            throw new BadRequestException();
        }

        Optional<TodoItem> optionalTodoItem = currentTodoItemService.readTodoItem(id);
        if (optionalTodoItem.isEmpty()) {
            throw new NotFoundException();
        }
        TodoItem existingTodoItem = optionalTodoItem.get();
        return Response.ok(existingTodoItem).build();
    }

    @GET
    public Response readAll() {
        return Response.ok(currentTodoItemService.readAll()).build();
    }

    @PUT
    @Path("{id}")
    public Response updateTodoItem(@PathParam("id") Long id, TodoItem updatedTodoItem) {
        if (id == null || !id.equals(updatedTodoItem.getId())) {
            throw new BadRequestException();
        }

        Optional<TodoItem> optionalTodoItem = currentTodoItemService.readTodoItem(id);
        if (optionalTodoItem.isEmpty()) {
            throw new NotFoundException();
        }
        currentTodoItemService.updateTodoItem(updatedTodoItem);
        return Response.noContent().build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteTodoItem(@PathParam("id") Long id) {
        if (id == null) {
            throw new BadRequestException();
        }
        Optional<TodoItem> optionalTodoItem = currentTodoItemService.readTodoItem(id);
        if (optionalTodoItem.isEmpty()) {
            throw new NotFoundException();
        }
        currentTodoItemService.deleteTodoItem(id);
        return Response.noContent().build();
    }
}
