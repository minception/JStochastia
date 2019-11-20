package en.pocatko.michal.stochastia.game.state;

import en.pocatko.michal.stochastia.game.TransitionData;

/**
 * Abstract class representing the state of the game
 */
public abstract class State {
    public abstract boolean Transition(String input, TransitionData actiondata);
}