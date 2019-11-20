package en.pocatko.michal.stochastia.game;

import en.pocatko.michal.stochastia.game.state.State;
import en.pocatko.michal.stochastia.game.state.ingame.IngameState;

/**
 * A class of data passed between states
 */
public class TransitionData {
    public State NewState;
    public IngameState NewIngameState;
    public String Output;
}