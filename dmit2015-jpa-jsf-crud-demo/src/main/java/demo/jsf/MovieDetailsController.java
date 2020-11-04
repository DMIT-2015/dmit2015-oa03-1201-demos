package demo.jsf;

import demo.jpa.Movie;
import demo.jpa.MovieRepository;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.ManagedProperty;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Optional;

@Named("currentMovieDetailsController")
@RequestScoped
public class MovieDetailsController {

    @Inject
    private MovieRepository movieRepository;

    @Inject @ManagedProperty("#{param.editId}")
    @Getter @Setter
    private Long editId;

    @Getter
    private Movie existingMovie;

    @PostConstruct
    public void init() {
        Optional<Movie> optionalMovie = movieRepository.findById(editId);
        if (optionalMovie.isPresent()) {
            existingMovie = optionalMovie.get();
        }
    }

}

