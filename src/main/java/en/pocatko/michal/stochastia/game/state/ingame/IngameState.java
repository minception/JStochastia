package en.pocatko.michal.stochastia.game.state.ingame;

import en.pocatko.michal.stochastia.game.TransitionData;
import en.pocatko.michal.stochastia.game.gamedata.GameData;

/**
 * An abstract class representing an in game state
 */
public abstract class IngameState {
    public abstract boolean Transition(String input, GameData gameData, TransitionData transitionData);
}