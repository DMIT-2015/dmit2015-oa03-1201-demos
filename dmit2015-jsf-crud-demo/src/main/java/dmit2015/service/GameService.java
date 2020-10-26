package dmit2015.service;

import dmit2015.model.Game;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class GameService {

    /** The list of games in the system */
    private List<Game> games = new ArrayList<>();

    public void create(Game newGame) {
        // Assign a new gameId
        newGame.setGameId(UUID.randomUUID());
        // Add the newGame to the system
        games.add(newGame);
    }

    public List<Game> list() {
        // Return all games
        return games;
    }

    public Optional<Game> findById(UUID gameId) {
        // Find and return the first game that matches the gameId value
        return games
                .stream()
                .filter(item -> item.getGameId().equals(gameId))
                .findFirst();
    }

    public void remove(UUID gameId) {
        Optional<Game> optionalQueryResult = findById(gameId);
        if (optionalQueryResult.isPresent()) {
            Game gameToRemove = optionalQueryResult.get();
            // Remove the game from the system
            games.remove(gameToRemove);
        }
    }

    public void update(Game existingGame) {
        Optional<Game> optionalQueryResult = findById(existingGame.getGameId());
        if (optionalQueryResult.isPresent()) {
            // Get the actual game to update
            Game gameToUpdate = optionalQueryResult.get();
            // Update the properties of the game
            gameToUpdate.setPlatform(existingGame.getPlatform());
            gameToUpdate.setPurchaseDate(existingGame.getPurchaseDate());
            gameToUpdate.setTitle(existingGame.getTitle());
            gameToUpdate.setPurchasePrice(existingGame.getPurchasePrice());
        }
    }

}
