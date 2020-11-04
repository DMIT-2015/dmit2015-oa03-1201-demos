package demo.jsf;

import demo.jpa.Movie;
import demo.jpa.MovieRepository;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import javax.annotation.PostConstruct;
import javax.faces.annotation.ManagedProperty;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Optional;

@Named("currentMovieEditController")
@ViewScoped
public class MovieEditController implements Serializable {

    @Inject
    private MovieRepository movieRepository;

    @Inject @ManagedProperty("#{param.editId}")
    @Getter @Setter
    private Long editId;

    @Getter
    private Movie existingMovie;

    @PostConstruct
    public void init() {
        if (!Faces.isPostback()) {
            Optional<Movie> optionalMovie = movieRepository.findById(editId);
            if (optionalMovie.isPresent()) {
                existingMovie = optionalMovie.get();
            }
        }
    }

    public String onSave() {
        String nextPage = "";
        try {
            movieRepository.update(existingMovie);
            Messages.addFlashGlobalInfo("Update was successful.");
            nextPage = "index?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            Messages.addGlobalError("Update was not successful.");
        }
        return nextPage;
    }
}

