package en.pocatko.michal.stochastia.game;

import java.io.PrintStream;
import java.util.Scanner;

import en.pocatko.michal.stochastia.game.configuration.Configuration;
import en.pocatko.michal.stochastia.game.state.MainMenu;
import en.pocatko.michal.stochastia.game.state.State;

/**
 * Main wrapper for the app
 */
public class Game {
    Scanner input;
    PrintStream output;

    private State m_gameState;
    /**
     * Initialize the game
     */
	public void init() {
        Textdata.Init();
        Configuration.init();
        input = new Scanner(System.in);
        output = System.out;
        m_gameState = new MainMenu();
        output.println(String.format(Textdata.Get("intro")));
	}
    /**
     * Start execution of the game
     */
	public void start() {
        TransitionData transitionData = new TransitionData();
        transitionData.NewState = m_gameState;
        String command = "";
        while(input.hasNextLine()) {
            String lastCommand = command;
            command = input.nextLine();
            if(command.isEmpty()) continue;
            // execute last command
            if(command.equals(";")) { command = lastCommand; }
            try {
                if(!m_gameState.Transition(command, transitionData)){
                    output.println(String.format(Textdata.Get("gameExit")));
                    break;
                };
            }
            catch(NumberFormatException e) {
                transitionData.Output = String.format(Textdata.Get("notNumber"));
            }
            m_gameState = transitionData.NewState;
            System.out.println(transitionData.Output);
        }
        end();
    }
    private void end() {
        System.out.println("Have a nice day!");
    }
    
}