package demo.jsf;

import demo.jpa.Movie;
import demo.jpa.MovieRepository;
import lombok.Getter;
import org.omnifaces.util.Messages;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("currentMovieCreateController")
@RequestScoped
public class MovieCreateController {

    @Inject
    private MovieRepository movieRepository;

    @Getter
    private Movie newMovie = new Movie();

    public String onCreateNew() {
        String nextPage = "";
        try {
            movieRepository.add(newMovie);
            Messages.addFlashGlobalInfo("Create was successful.");
            nextPage = "index?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            Messages.addGlobalError("Create was not successful.");
        }
        return nextPage;
    }

}

