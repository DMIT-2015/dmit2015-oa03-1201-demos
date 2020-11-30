package dmit2015.resource;

import dmit2015.model.Movie;
import dmit2015.repository.MovieRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 curl -i -X GET http://localhost:8080/webapi/movies

 curl -i -X GET http://localhost:8080/webapi/movies/1

 curl -i -X POST http://localhost:8080/webapi/movies \
    -H 'Content-Type:application/json' \
    -d '{"title": "COVID19 strikes again", "releaseDate": "2020-11-30", "price": 1.99, "genre": "Action", "rating": "PG"}'

 curl -i -X POST http://localhost:8080/webapi/movies/5 \
    -H 'Content-Type:application/json' \
    -d '{"title": "COVID19 strikes again", "releaseDate": "2020-11-30", "price": 2.99, "genre": "Action", "rating": "PG"}'

 curl -i -X GET http://localhost:8080/webapi/movies/5

 *
 */

@ApplicationScoped
@Path("movies")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MovieResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private MovieRepository currentMovieRepository;

    @GET
    public Response getAllMovies() {
        List<Movie> movies = currentMovieRepository.findAll();
        // Send an OK status and embed in the body the list of movies
        return Response.ok(movies).build();
    }

    @GET
    @Path("{id}")
    public Response getMovie(@PathParam("id") Long movieId) {
        if (movieId == null) {
            throw new BadRequestException();
        }
        Optional<Movie> optionalMovie = currentMovieRepository.findById(movieId);
        if (optionalMovie.isEmpty()) {
            throw new NotFoundException();
        }
        Movie currentMovie = optionalMovie.get();
        return Response.ok(currentMovie).build();
    }

    @POST
    public Response createMovie(@Valid Movie newMovie){
        currentMovieRepository.add(newMovie);
        URI locationUri = uriInfo.getAbsolutePathBuilder().path(newMovie.getId().toString()).build();
        return Response.created(locationUri).build();
    }

    @PUT
    @Path("{id}")
    public Response updateMovie(@PathParam("id") Long movieId, Movie updatedMovie) {
        if (movieId == null || !movieId.equals(updatedMovie.getId())) {
            throw new BadRequestException();
        }
        currentMovieRepository.update(updatedMovie);
        return Response.noContent().build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteMovie(@PathParam("id") Long movieId) {
        if (movieId == null) {
            throw new BadRequestException();
        }
        currentMovieRepository.remove(movieId);
        return Response.noContent().build();
    }

}
