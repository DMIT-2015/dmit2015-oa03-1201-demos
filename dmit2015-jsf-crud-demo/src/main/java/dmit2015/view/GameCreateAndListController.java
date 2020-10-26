package dmit2015.view;

import dmit2015.model.Game;
import dmit2015.service.GameService;
import lombok.Getter;
import org.omnifaces.util.Messages;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Named
@ViewScoped
public class GameCreateAndListController implements Serializable {

    /** The Game to add to the system */
    @Getter
    private Game currentGame = new Game();

    /** The system for managing games */
    @Inject
    private GameService gameService;

    /** The list of games */
    public List<Game> getGames() {
        return gameService.list();
    }

    public String addGame() {
        // Add the game to the system
        gameService.create(currentGame);
        // Send a feedback message that indicates operation was successful
        Messages.addGlobalInfo("Successfully created new game.");
        // Create another Game to add
        currentGame = new Game();
        // Return navigation to the same page
        return "";
    }

    public void deleteGame(UUID gameId) {
        // Delete the game from the system
        gameService.remove(gameId);
        // Send a feedback message to indicates operation was successful
        Messages.addGlobalInfo("Successfully deleted game.");
    }


}
