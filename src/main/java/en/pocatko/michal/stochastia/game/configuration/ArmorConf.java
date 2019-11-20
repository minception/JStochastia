package en.pocatko.michal.stochastia.game.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A class containing a single armor configuration, used in area generation
 */
public class ArmorConf extends PConf {
    @JsonProperty
    public String name;
    @JsonProperty
    public int defence;

}