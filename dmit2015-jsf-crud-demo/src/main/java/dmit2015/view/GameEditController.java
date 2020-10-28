package dmit2015.view;

import dmit2015.model.Game;
import dmit2015.service.GameService;
import lombok.Getter;
import org.omnifaces.util.Messages;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.ManagedProperty;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@Named
@ViewScoped
public class GameEditController implements Serializable {

    @Inject @ManagedProperty("#{param.editId}")
    private String editId;

    @Getter
    private Game existingGame;

    @Inject
    private GameService gameService;

    @PostConstruct  // executes after all @Inject is completed
    public void init() {
        if (editId != null) {
            UUID gameId = UUID.fromString(editId);
            Optional<Game> optionalGame = gameService.findById(gameId);
            if (optionalGame.isPresent()) {
                existingGame = optionalGame.get();
            } else {
                Messages.addGlobalFatal("A valid gameId parameter is required");
            }
        } else {
            Messages.addGlobalWarn("Missing parameter for editId");
        }

    }

    public String updateGame() {
        gameService.update(existingGame);
        Messages.addFlashGlobalInfo("Successfully updated game.");
        return "manageGames?faces-redirect=true";
    }

    public String deleteGame() {
        gameService.remove(existingGame.getGameId());
        Messages.addFlashGlobalInfo("Successfully deleted game.");
        return "manageGames?faces-redirect=true";
    }
}
