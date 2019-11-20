package en.pocatko.michal.stochastia.game.state.ingame;

import en.pocatko.michal.stochastia.game.Textdata;
import en.pocatko.michal.stochastia.game.TransitionData;
import en.pocatko.michal.stochastia.game.gamedata.Area;
import en.pocatko.michal.stochastia.game.gamedata.GameData;
import en.pocatko.michal.stochastia.game.gamedata.item.Item;
import en.pocatko.michal.stochastia.game.gamedata.Character;

/**
 * A class representing an ingame state 'overworld'
 */
public class Overworld extends IngameState {
    @Override
    public boolean Transition(String input, GameData gameData, TransitionData transitionData) {
        String[] boom = input.toLowerCase().split(" ");
        String command = boom[0];
        switch(command) {
            case "go": {
                go(boom, gameData, transitionData);
                break;
            }
            case "help": {
                transitionData.Output = String.format(Textdata.Get("overworldHelp"));
                break;
            }
            case "look": {
                look(boom, gameData, transitionData);
                break;
            }
            case "inventory": {
                transitionData.NewIngameState = new Inventory();
                transitionData.Output = String.format(Textdata.Get("goInventory"));
                break;
            }
            case "attack": {
                attack(boom, gameData, transitionData);
                break;
            }
            case "get": {
                get(boom, gameData, transitionData);
                break;
            }
            default: {
                transitionData.Output = String.format(Textdata.Get("overworldUnknown"), command);
            }
        }
        return true;
    }

    private void get(String[] boom, GameData gameData, TransitionData transitionData) {
        if(boom.length >= 2) {
            int index = Integer.parseInt(boom[1]);
            Area currentArea = gameData.GetCurrentArea();
            if(index >= 0 && index < currentArea.Items.size()) {
                Item toPickUp = currentArea.Items.get(index);
                // remove item from area and add it to player's inventory
                currentArea.Items.remove(index);
                gameData.Player.inventory.add(toPickUp);
                transitionData.Output = String.format(Textdata.Get("pickUp"), toPickUp.name);
            }
            else {
                transitionData.Output = String.format(Textdata.Get("wrongEquipIndex"), index);
            }
        }
        else {
            transitionData.Output = String.format(Textdata.Get("getNoIndex"));
        }
    }

    private void attack(String[] boom, GameData gameData, TransitionData transitionData) {
        if(boom.length >= 2) {
            int monsterIndex = Integer.parseInt(boom[1]);
            Area currentArea = gameData.GetCurrentArea();
            if(monsterIndex >= 0 && monsterIndex < currentArea.Mobs.size()) {
                Character toAttack = currentArea.Mobs.get(monsterIndex);
                if(toAttack.IsDead()) {
                    transitionData.Output = String.format(Textdata.Get("attackDead"), toAttack.GetName());
                }
                else {
                    transitionData.Output = String.format(Textdata.Get("startFight"), toAttack.GetName());
                }
            }
            else {
                transitionData.Output = String.format(Textdata.Get("wrongAttackIndex"), monsterIndex);
            }
        }
        else {
            transitionData.Output = String.format(Textdata.Get("attackWhom"));
        }
    }

    private void look(String[] args, GameData gameData, TransitionData transitionData) {
    Area currentArea = gameData.GetCurrentArea();
        if(args.length == 2 && args[1].equals("around")) {
            transitionData.Output = currentArea.Description();
        }
        else if(args.length >= 3 && args[1].equals("at")) {
            switch(args[2]) {
                case "yourself": {
                    transitionData.Output = gameData.Player.Description();
                    break;
                }
                case "monster":{
                    if(args.length <= 3) {
                        transitionData.Output = String.format(Textdata.Get("noIndex"));
                    }
                    else {
                        int index = Integer.parseInt(args[3]);
                        if(index < 0 || index >= currentArea.Mobs.size()) {
                            transitionData.Output = String.format(Textdata.Get("wrongMonsterIndex"), index);
                        }
                        else {
                            transitionData.Output = currentArea.Mobs.get(index).Description();
                        }
                    }
                    break;
                }
                case "item": {
                    if(args.length <= 3) {
                        transitionData.Output = String.format(Textdata.Get("noIndex"));
                    }
                    else {
                        int index = Integer.parseInt(args[3]);
                        if(index < 0 || index >= currentArea.Items.size()) {
                            transitionData.Output = String.format(Textdata.Get("wrongItemIndex"), index);
                        }
                        else {
                            transitionData.Output = currentArea.Items.get(index).Description();
                        }
                    }
                    break;
                }
                default: {
                    transitionData.Output = String.format(Textdata.Get("lookDoesntExist"), args[2]);
                }
            }
        }
        else {
            transitionData.Output = String.format(Textdata.Get("lookNoAt"));
        }
    }

    private void go(String[] boom, GameData gameData, TransitionData transitionData) {
        if(boom.length < 2) {
            transitionData.Output = String.format(Textdata.Get("noDirection"));
            return;
        }
        String direction = boom[1];
        switch(direction) {
            case "north": {
                gameData.Player.position.Y--;
                break;
            }
            case "west": {
                gameData.Player.position.X--;
                break;
            }
            case "south": {
                gameData.Player.position.Y++;
                break;
            }
            case "east": {
                gameData.Player.position.X++;
                break;
            }
            default: {
                transitionData.Output = String.format(Textdata.Get("unknownDirection"), direction);
                return;
            }
        }
        Area currentArea = gameData.Map.GetArea(gameData, gameData.Player.position);
        transitionData.Output = String.format(Textdata.Get("moveArea"), currentArea.Biome);
    }
}