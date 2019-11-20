package en.pocatko.michal.stochastia.game.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A class containing a single biome configuration, used in area generation
 */
public class BiomeConf extends PConf{
    
    @JsonProperty
    public String name;
    @JsonProperty
    public int[] spawnableMobs;
    @JsonProperty
    public int mobsAmount;
    @JsonProperty
    public int[] spawnableWeapons;
    @JsonProperty
    public int weaponsAmount;
    @JsonProperty
    public int[] spawnableArmor;
    @JsonProperty
    public int armorAmount;
    @JsonProperty
    public int[] spawnablePotions;
    @JsonProperty
    public int potionsAmount;
}