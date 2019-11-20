package en.pocatko.michal.stochastia.game.gamedata;

import java.util.Random;

import en.pocatko.michal.stochastia.game.gamedata.Map;
import en.pocatko.michal.stochastia.game.gamedata.Player;
import en.pocatko.michal.stochastia.game.state.ingame.IngameState;

/**
 * A class containing all the data necessary for a single game instance
 */
public class GameData {
    /**
     * Area map
     */
    public Map Map;
    /**
     * Player
     */
    public Player Player;
    /**
     * Random number generator
     */
    public Random Rng;
    /**
     * Current in game state
     */
    public IngameState CurrentState;
    /**
     * Generate new game data using a random seed
     * @param seed seed used to generate game data
     * @return generated game data
     */
    public static GameData generate(long seed) {
        GameData result = new GameData();
        result.Map = new Map();
        result.Rng = new Random(seed);
        result.Player = new Player(result.Rng);

        // Call getarea on starting location to generate it
        result.Map.GetArea(result, result.Player.position);
        return result;
    }

    /**
     * Getter for the area that player currently occupies
     * @return current area
     */
    public Area GetCurrentArea() {
        return Map.GetArea(this, Player.position);
    }
}