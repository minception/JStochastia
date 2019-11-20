package en.pocatko.michal.stochastia.game.state.ingame;

import en.pocatko.michal.stochastia.game.Textdata;
import en.pocatko.michal.stochastia.game.TransitionData;
import en.pocatko.michal.stochastia.game.gamedata.Area;
import en.pocatko.michal.stochastia.game.gamedata.GameData;
import en.pocatko.michal.stochastia.game.gamedata.item.Item;

/**
 * A class representing an in-game state inventory
 */
public class Inventory extends IngameState {

    @Override
    public boolean Transition(String input, GameData gameData, TransitionData transitionData) {
        String[] boom = input.toLowerCase().split(" ");
        String command = boom[0];
        switch(command) {
            case "help": {
                transitionData.Output = String.format(Textdata.Get("inventoryHelp"));
                break;
            }
            case "equipped": {
                transitionData.Output = gameData.Player.EquippedDescription();
                break;
            }
            case "list": {
                listItems(gameData,transitionData);
                break;
            }
            case "equip": {
                equip(boom, gameData, transitionData);
                break;
            }
            case "drop": {
                drop(boom, gameData, transitionData);
                break;
            }
            case "use": {
                use(boom, gameData, transitionData);
                break;
            }
            case "close": {
                transitionData.Output = String.format(Textdata.Get("exitInventory"));
                transitionData.NewIngameState = new Overworld();
                break;
            }
            default: {
                transitionData.Output = String.format(Textdata.Get("inventoryUnknown"), command);
                break;
            }
        }
        return true;
    }

    private void listItems(GameData gameData, TransitionData transitionData) {
        if(gameData.Player.inventory.size() == 0) {
            transitionData.Output = String.format(Textdata.Get("inventoryEmpty"));
            return;
        }
        String inventoryDesc = "";
        for(int i = 0; i < gameData.Player.inventory.size(); i++) {
            inventoryDesc += i + ": " + gameData.Player.inventory.get(i).Description() + '\n';
        }
        transitionData.Output = inventoryDesc;
    }

    private void equip(String[] boom, GameData gameData, TransitionData transitionData) {
        if(boom.length <= 1){
            transitionData.Output = String.format(Textdata.Get("equipWhat"));
        }
        else {
            int index = Integer.parseInt(boom[1]);
            if(index < 0 || index >= gameData.Player.inventory.size()){
                transitionData.Output = String.format(Textdata.Get("wrongEquipIndex"), index);
            }
            else{
                Item toEquip = gameData.Player.inventory.get(index);
                if(!toEquip.equip(gameData)) {
                    transitionData.Output = String.format(Textdata.Get("cantEquip"), toEquip.name);
                }
                else {
                    // If equip was successful, remove item from the inventory
                    gameData.Player.inventory.remove(index);
                    transitionData.Output = String.format(Textdata.Get("equipped"), toEquip.name);
                }
            }
        }
    }

    private void use(String[] boom, GameData gameData, TransitionData transitionData) {
        if(boom.length <= 1){
            transitionData.Output = String.format(Textdata.Get("useWhat"));
        }
        else {
            int index = Integer.parseInt(boom[1]);
            if(index < 0 || index >= gameData.Player.inventory.size()) {
                transitionData.Output = String.format(Textdata.Get("wrongItemIndex"), index);
            }
            else {
                Item toUse = gameData.Player.inventory.get(index);
                if(toUse.use(gameData)) {
                    // When used succesfully, remove item from inventory
                    gameData.Player.inventory.remove(index);
                    transitionData.Output = String.format(Textdata.Get("used"), gameData.Player.inventory.get(index).name);
                }
                else {
                    transitionData.Output = String.format(Textdata.Get("cantUse"), toUse.name);
                }
            }
        }
    }

    private void drop(String[] boom, GameData gameData, TransitionData transitionData) {
        if(boom.length <= 1){
            transitionData.Output = String.format(Textdata.Get("dropWhat"));
        }
        else {
            int index = Integer.parseInt(boom[1]);
            if(index < 0 || index >= gameData.Player.inventory.size()) {
                transitionData.Output = String.format(Textdata.Get("wrongItemIndex"), index);
            }
            else {
                Area currentPosition = gameData.GetCurrentArea();
                currentPosition.Items.add(gameData.Player.inventory.get(index));
                transitionData.Output = String.format(Textdata.Get("dropped"), gameData.Player.inventory.get(index).name);
                gameData.Player.inventory.remove(index);
            }
        }
    }

}