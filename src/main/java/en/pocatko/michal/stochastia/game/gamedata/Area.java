package en.pocatko.michal.stochastia.game.gamedata;

import java.util.ArrayList;
import java.util.Random;

import en.pocatko.michal.stochastia.game.Textdata;
import en.pocatko.michal.stochastia.game.configuration.ArmorConf;
import en.pocatko.michal.stochastia.game.configuration.BiomeConf;
import en.pocatko.michal.stochastia.game.configuration.Configuration;
import en.pocatko.michal.stochastia.game.configuration.MobConf;
import en.pocatko.michal.stochastia.game.configuration.PotionConf;
import en.pocatko.michal.stochastia.game.configuration.WeaponConf;
import en.pocatko.michal.stochastia.game.gamedata.item.Armor;
import en.pocatko.michal.stochastia.game.gamedata.item.Item;
import en.pocatko.michal.stochastia.game.gamedata.item.Potion;
import en.pocatko.michal.stochastia.game.gamedata.item.Weapon;

/**
 * A class representing a single area in the overworld
 */
public class Area{
    // Area generation constants
    private final static int MOBS_COUNT_DEVIATION = 1;
    private final static int WEAPONS_COUNT_DEVIATION = 1;
    private final static int ARMOR_COUNT_DEVIATION = 1;
    private final static int POTIONS_COUNT_DEVIATION = 1;

    public String Biome;
    public ArrayList<Character> Mobs = new ArrayList<>();
    public ArrayList<Item> Items = new ArrayList<>();

    public static Area GenerateArea(GameData gameData){
        Area result = new Area();
        BiomeConf biomeConf = Configuration.getRandom(Configuration.biomes, gameData.Rng);
        result.Biome = biomeConf.name;
        Random rng = gameData.Rng;
        // Calculate amount of spawning objects with a given deviation
        int mobsCount = biomeConf.mobsAmount + rng.nextInt(MOBS_COUNT_DEVIATION * 2 + 1) - MOBS_COUNT_DEVIATION;
        int weaponsCount = biomeConf.weaponsAmount + rng.nextInt(WEAPONS_COUNT_DEVIATION * 2 + 1) - WEAPONS_COUNT_DEVIATION;
        int armorCount = biomeConf.armorAmount + rng.nextInt(ARMOR_COUNT_DEVIATION * 2 + 1) - ARMOR_COUNT_DEVIATION;
        int potionsCount = biomeConf.potionsAmount + rng.nextInt(POTIONS_COUNT_DEVIATION * 2 + 1) - POTIONS_COUNT_DEVIATION;
        for(int i = 0; i < mobsCount; ++i) {
            MobConf conf = Configuration.getRandom(Configuration.mobs, gameData.Rng, biomeConf.spawnableMobs);
            result.Mobs.add(Mob.GenerateMob(conf, gameData));
        }
        for(int i = 0; i < weaponsCount; ++i) {
            WeaponConf conf = Configuration.getRandom(Configuration.weapons, gameData.Rng, biomeConf.spawnableWeapons);
            result.Items.add(Weapon.GenerateWeapon(conf, gameData));
        }
        for(int i = 0; i < armorCount; ++i) {
            ArmorConf conf = Configuration.getRandom(Configuration.armor, gameData.Rng, biomeConf.spawnableArmor);
            result.Items.add(Armor.GenerateArmor(conf, gameData));
        }
        for(int i = 0; i < potionsCount; ++i) {
            PotionConf conf = Configuration.getRandom(Configuration.potions, gameData.Rng, biomeConf.spawnablePotions);
            result.Items.add(Potion.GeneratePotion(conf, gameData));
        }
        return result;
    }
	public String Description() {
		return String.format(Textdata.Get("areaDesc"), Biome, mobDescriptions(), itemDescriptions());
	}

    private Object itemDescriptions() {
        if(Items.size() == 0) {
            return Textdata.Get("noItems");
        }
        else {
            String itemDesc = String.format(Textdata.Get("itemsAreaDesc"));

            for (int i = 0; i < Items.size(); i++) {
                itemDesc += i + ":" + Items.get(i).name + '\n';
            }
            return itemDesc;
        }
    }

    private String mobDescriptions() {
        if(Mobs.size() == 0) {
            return Textdata.Get("noMonsters");
        }
        else {
            String mobDesc = String.format(Textdata.Get("mobsAreaDesc"));

            for (int i = 0; i < Mobs.size(); i++) {
                mobDesc += i + ":" + Mobs.get(i).GetName() + '\n';
            }
            return mobDesc;
        }
    }
}
