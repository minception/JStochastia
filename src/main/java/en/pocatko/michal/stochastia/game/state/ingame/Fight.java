package en.pocatko.michal.stochastia.game.state.ingame;

import en.pocatko.michal.stochastia.game.Textdata;
import en.pocatko.michal.stochastia.game.TransitionData;
import en.pocatko.michal.stochastia.game.gamedata.GameData;
import en.pocatko.michal.stochastia.game.state.MainMenu;
import en.pocatko.michal.stochastia.game.gamedata.Character;

/**
 * A class representing an in-game state 'fight'
 */
class Fight extends IngameState {
    private static final double RUN_PROBABILITY = 0.5;
    private int m_opponentIndex;
    @Override
    public boolean Transition(String input, GameData gameData, TransitionData transitionData) {
        Character opponent = gameData.GetCurrentArea().Mobs.get(m_opponentIndex);
        String[] boom = input.split(" ");
        String command = boom[0];
        int playerHealthBefore = gameData.Player.GetHp();
        int damageDealt = 0;
        int damageReceived = 0;
        switch(command) {
            case "attack": {
                int opponentHealthBefore = opponent.GetHp();
                gameData.Player.Attack(opponent, gameData);
                int opponentHealthAfter = opponent.GetHp();
                damageDealt = opponentHealthAfter - opponentHealthBefore;
                transitionData.Output = String.format(Textdata.Get("attack"), opponent.GetName());
                break;
            }
            case "defend": {
                int playerDefence = gameData.Player.BaseDefence;
                gameData.Player.BaseDefence = playerDefence * 2;
                // player is attacked when defending and has double damage
                opponent.Attack(gameData.Player, gameData);
                gameData.Player.BaseDefence = playerDefence;
                transitionData.Output = String.format(Textdata.Get("defend"));
                break;
            }
            case "run": {
                if(gameData.Rng.nextDouble() <= RUN_PROBABILITY) {
                    transitionData.NewIngameState = new Overworld();
                    transitionData.Output = String.format(Textdata.Get("runSuccess"));
                }
                else {
                    opponent.Attack(gameData.Player, gameData);
                    transitionData.Output = String.format(Textdata.Get("unsuccessfulRun"));
                }
                break;
            }
            case "help": {
                transitionData.Output = String.format(Textdata.Get("fightHelp"));
                break;
            }
            default: {
                transitionData.Output = String.format(Textdata.Get("fightUnknown"), command);
            }
        }
        damageReceived = playerHealthBefore - gameData.Player.GetHp();
        if(damageDealt > 0) {
            transitionData.Output += '\n' + String.format(Textdata.Get("damageDealt"), damageDealt, opponent.GetName(), damageReceived);
        } else if(damageReceived > 0) {
            transitionData.Output += '\n' + String.format(Textdata.Get("damageReceived"), damageReceived);
        }
        // fight was won
        if(opponent.IsDead()){
            transitionData.NewIngameState = new Overworld();
            gameData.Player.SetExp(gameData.Player.GetExp() + opponent.GetExpGain());
            gameData.GetCurrentArea().Mobs.remove(m_opponentIndex);
            transitionData.Output += '\n' + String.format(Textdata.Get("killOpponent"), opponent.GetName(), opponent.GetExpGain());
        }
        // fight was lost
        if(gameData.Player.IsDead()){
            transitionData.Output += '\n' + String.format(Textdata.Get("gameOver"), gameData.Player.level);
            transitionData.NewState = new MainMenu();
        }
        return true;
    }

    /**
     * A fight state constructor
     * @param opponentIndex index of an opponent you wish to fight
     */
    public Fight(int opponentIndex) {
        m_opponentIndex = opponentIndex;
    }
}