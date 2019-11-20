package en.pocatko.michal.stochastia.game.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A class containing a single mob configuration, used in area generation
 */
public class MobConf extends PConf{
    @JsonProperty
    public int id;
    @JsonProperty
    public String name;
    @JsonProperty
    public int hp;
    @JsonProperty
    public int attack;
    @JsonProperty
    public int expGain;
}