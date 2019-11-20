package en.pocatko.michal.stochastia.game.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A class representing a configuration of weapon modifiers
 */
public class WeaponModifierConf extends PConf {
    @JsonProperty
    public String name;
    @JsonProperty
    public int bonus;
}