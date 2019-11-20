package en.pocatko.michal.stochastia.game.state;

import en.pocatko.michal.stochastia.game.Textdata;
import en.pocatko.michal.stochastia.game.TransitionData;

/**
 * Class representing the game being in a main menu state
 */
public class MainMenu extends State {
    @Override
    public boolean Transition(String input, TransitionData actiondata) {
        return ParseInput(input, actiondata);
    }

    private boolean ParseInput(String action, TransitionData transitionData) {
        String[] boom = action.toLowerCase().split(" ");
        String command = boom[0];
        switch (command) {
            case "start": {
                transitionData.NewState = new Ingame(transitionData);
                break;
            }
            case "help": {
                transitionData.Output = String.format(Textdata.Get("mainMenuHelp"));
                break;
            }
            case "exit": {
                return false;
            }
            default: {
                transitionData.Output = String.format(Textdata.Get("menuUnknown"), command);
            }
        }
        return true;
    }
}