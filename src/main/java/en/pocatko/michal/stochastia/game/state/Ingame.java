package en.pocatko.michal.stochastia.game.state;

import en.pocatko.michal.stochastia.game.Textdata;
import en.pocatko.michal.stochastia.game.TransitionData;
import en.pocatko.michal.stochastia.game.gamedata.GameData;
import en.pocatko.michal.stochastia.game.state.ingame.IngameState;
import en.pocatko.michal.stochastia.game.state.ingame.Overworld;

/**
 * A class representing an in-game state
 */
public class Ingame extends State {
    private GameData m_gamedata;
    private IngameState m_state;

    public Ingame(TransitionData transitionData){
        m_gamedata = GameData.generate(System.currentTimeMillis());
        m_state = new Overworld();
        transitionData.NewIngameState = new Overworld();
        transitionData.Output = String.format(Textdata.Get("gameIntro"));
    }
    @Override
    public boolean Transition(String input, TransitionData transitionData) {
        String command = input.toLowerCase().split(" ")[0];
        switch(command) {
            case "exit": {
                transitionData.NewState = new MainMenu();
                transitionData.Output = "Bye bye";
                break;
            }
            default: {
                boolean result = m_state.Transition(input, m_gamedata, transitionData);
                m_state = transitionData.NewIngameState;
                
                return result;
            }
        }
        return true;
    }
}