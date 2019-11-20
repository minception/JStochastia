package en.pocatko.michal.stochastia.game.gamedata.item;

import en.pocatko.michal.stochastia.game.gamedata.GameData;

/**
 * Abstract class representing an in-game item
 */
public abstract class Item {
    public String name;
    public abstract boolean equip(GameData gamedata);
    public abstract boolean use(GameData gameData);
    public abstract String Description();
}