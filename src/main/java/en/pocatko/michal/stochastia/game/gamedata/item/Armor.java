package en.pocatko.michal.stochastia.game.gamedata.item;

import en.pocatko.michal.stochastia.game.Textdata;
import en.pocatko.michal.stochastia.game.configuration.ArmorConf;
import en.pocatko.michal.stochastia.game.gamedata.GameData;

/**
 * A class containing data about a specific armor
 */
public class Armor extends Item {
    public int defence;
    @Override
    public boolean equip(GameData gamedata) {
        gamedata.Player.equippedArmor = this;
        return true;
    }

    @Override
    public boolean use(GameData gameData) {
        return false;
    }

    @Override
    public String Description() {
        return String.format(Textdata.Get("armorDescription"), name, defence);
    }

    public static Armor GenerateArmor(ArmorConf conf, GameData gameData) {
        Armor result = new Armor();
        result.name = conf.name;
        result.defence = (conf.defence + gameData.Rng.nextInt(1))*gameData.Player.level;
        return result;
    }

	public static Armor GenerateSimpleArmor(ArmorConf conf) {
        Armor result = new Armor();
        result.name = conf.name;
        result.defence = conf.defence;
        return result;
	}
}