package en.pocatko.michal.stochastia.game.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import en.pocatko.michal.stochastia.game.configuration.BiomeConf;
import en.pocatko.michal.stochastia.utils.IstreamUtils;

/**
 * A class containing information loaded from configure file neccessary for area
 * generation
 */
public class Configuration {
    public static HashMap<Integer, BiomeConf> biomes;
    public static HashMap<Integer, MobConf> mobs;
    public static HashMap<Integer, WeaponConf> weapons;
    public static HashMap<Integer, ArmorConf> armor;
    public static HashMap<Integer, PotionConf> potions;
    public static HashMap<Integer, WeaponModifierConf> weaponModifiers;
    public static HashMap<Integer, PlayerConf> players;

    public static void init() {
        JSONParser parser = new JSONParser();
        ObjectMapper mapper = new ObjectMapper();
        try {
            InputStream confFile = Configuration.class.getClassLoader().getResourceAsStream("Conf.json");
            String confFileString = IstreamUtils.convertStreamToString(confFile);
            JSONObject jsonObject = (JSONObject) parser.parse(confFileString);

            String biomesJSON = jsonObject.get("biomes").toString();
            String mobsJSON = jsonObject.get("mobs").toString();
            String weaponsJSON = jsonObject.get("weapons").toString();
            String armorJSON = jsonObject.get("armor").toString();
            String potionsJSON = jsonObject.get("health_potions").toString();
            String weaponModifiersJSON = jsonObject.get("weaponModifiers").toString();
            String playersJSON = jsonObject.get("players").toString();

            biomes = mapper.readValue(biomesJSON, new TypeReference<HashMap<Integer, BiomeConf>>() {
            });
            mobs = mapper.readValue(mobsJSON, new TypeReference<HashMap<Integer, MobConf>>() {
            });
            weapons = mapper.readValue(weaponsJSON, new TypeReference<HashMap<Integer, WeaponConf>>() {
            });
            armor = mapper.readValue(armorJSON, new TypeReference<HashMap<Integer, ArmorConf>>() {
            });
            potions = mapper.readValue(potionsJSON, new TypeReference<HashMap<Integer, PotionConf>>() {
            });
            weaponModifiers = mapper.readValue(weaponModifiersJSON,
                    new TypeReference<HashMap<Integer, WeaponModifierConf>>() {
                    });
            players = mapper.readValue(playersJSON, new TypeReference<HashMap<Integer, PlayerConf>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Get random configuration from a map of configurations using a rulette wheel method
     * @param map map from which to choose a random configuration
     * @param gameData gamedata required to use rng
     * @return a random configuration
     */
    public static <T extends PConf> T getRandom(HashMap<Integer,T> map, Random rng) {
        T result = null;
        Collection<T> configurations = map.values();
        double pSum = 0.0;
        for (T conf : configurations) {
            pSum += conf.p;
        }
        // Generate random value between 0 and psum
        double rand = rng.nextDouble()*pSum;
        double choose = 0.0;
        for (T conf : configurations) {
            choose += conf.p;
            result = conf;
            if(choose >= rand){
                break;
            }
        }
        return result;
    }
    /**
     * Get random configuration from a map of configurations using a rulette wheel method
     * @param map map from which to choose a random configuration
     * @param gameData gamedata required to use rng
     * @param filter a filter for which configurations should be used
     * @return a random configuration
     */
    public static <T extends PConf> T getRandom(HashMap<Integer,T> map, Random rng, int[] filter) {
    T result = null;
    ArrayList<T> configurations = new ArrayList<>();
    for (int i : filter) {
        configurations.add(map.get(i));
    }
    double pSum = 0.0;
    for (T conf : configurations) {
        pSum += conf.p;
    }
    // Generate random value between 0 and psum
    double rand = rng.nextDouble()*pSum;
    double choose = 0.0;
    for (T conf : configurations) {
        choose += conf.p;
        result = conf;
        if(choose >= rand){
            break;
        }
    }
    return result;
}
}