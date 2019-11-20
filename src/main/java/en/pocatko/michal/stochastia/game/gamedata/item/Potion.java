package en.pocatko.michal.stochastia.game.gamedata.item;

import en.pocatko.michal.stochastia.game.Textdata;
import en.pocatko.michal.stochastia.game.configuration.PotionConf;
import en.pocatko.michal.stochastia.game.gamedata.GameData;

public class Potion extends Item {
    private int healthGain;
    @Override
    public boolean equip(GameData gamedata) {
        return false;
    }

    @Override
    public boolean use(GameData gameData) {
        return false;
    }

    @Override
    public String Description() {
        return String.format(Textdata.Get("potionDesc"), name, healthGain);
    }

    public static Potion GeneratePotion(PotionConf conf, GameData gameData) {
        Potion result = new Potion();
        result.name = conf.name;
        result.healthGain = conf.healthGain;
        return result;
    }
}