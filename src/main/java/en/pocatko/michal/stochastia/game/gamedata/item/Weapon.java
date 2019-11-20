package en.pocatko.michal.stochastia.game.gamedata.item;

import java.util.Random;

import en.pocatko.michal.stochastia.game.Textdata;
import en.pocatko.michal.stochastia.game.configuration.Configuration;
import en.pocatko.michal.stochastia.game.configuration.WeaponConf;
import en.pocatko.michal.stochastia.game.configuration.WeaponModifierConf;
import en.pocatko.michal.stochastia.game.gamedata.GameData;

/**
 * A class containing data about a specific weapon
 */
public class Weapon extends Item {
    public int minDamage;
    public int maxDamage;

    @Override
    public boolean equip(GameData gamedata) {
        gamedata.Player.equippedWeapon = this;
        return true;
    }

    @Override
    public boolean use(GameData gameData) {
        return false;
    }

    @Override
    public String Description() {
        return String.format(Textdata.Get("weaponDescription"), name, minDamage, maxDamage);
    }

    /**
     * Generates a weapon based on player's level and adds a little randomness and a modifier to it
     * @param conf Weapon configuration
     * @param gameData Game data
     * @return Generated weapon
     */
    public static Weapon GenerateWeapon(WeaponConf conf, GameData gameData) {
        Weapon result = new Weapon();
        WeaponModifierConf modifierConf = Configuration.getRandom(Configuration.weaponModifiers, gameData.Rng);
        result.name = modifierConf.name.isEmpty() ? conf.name : modifierConf.name + " " + conf.name;
        Random rng = gameData.Rng;
        int minDamage = (conf.baseMinAttack + modifierConf.bonus + rng.nextInt(1))*gameData.Player.level;
        int maxDamage = (conf.baseMaxAttack + modifierConf.bonus + rng.nextInt(1))*gameData.Player.level;
        if(minDamage > maxDamage) {
            int temp = minDamage;
            minDamage = maxDamage;
            maxDamage = temp;
        }
        result.minDamage = minDamage;
        result.maxDamage = maxDamage;
        return result;
    }

    /**
     * Generates level 1 weapon without randomness from configuration
     * @param conf Weapon configuration
     * @return
     */
    public static Weapon GenerateSimpleWeapon(WeaponConf conf) {
        Weapon result = new Weapon();
        result.name = conf.name;
        int minDamage = conf.baseMinAttack;
        int maxDamage = conf.baseMaxAttack;
        if(minDamage > maxDamage) {
            int temp = minDamage;
            minDamage = maxDamage;
            maxDamage = temp;
        }
        result.minDamage = minDamage;
        result.maxDamage = maxDamage;
        return result;
    }
}