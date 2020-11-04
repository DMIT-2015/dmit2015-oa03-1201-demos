package demo.jsf;

import demo.jpa.Movie;
import demo.jpa.MovieRepository;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.ManagedProperty;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Optional;

@Named("currentMovieDeleteController")
@ViewScoped
public class MovieDeleteController implements Serializable {

    @Inject
    private MovieRepository movieRepository;

    @Inject @ManagedProperty("#{param.editId}")
    @Getter @Setter
    private Long editId;

    @Getter
    private Movie existingMovie;

    @PostConstruct
    public void init() {
        if (!Faces.isPostback() && editId != null) {
            Optional<Movie> optionalMovie = movieRepository.findById(editId);
            if (optionalMovie.isPresent()) {
                existingMovie = optionalMovie.get();
            } else {
                Messages.addGlobalFatal("There is no record with an id of {0} to delete.", editId);
            }
        }
    }

    public String onDelete() {
        String nextPage = "";
        try {
            movieRepository.remove(existingMovie.getId());
            Messages.addFlashGlobalInfo("Delete was successful.");
            nextPage = "index?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            Messages.addGlobalError("Delete not successful.");
        }
        return nextPage;
    }
}

