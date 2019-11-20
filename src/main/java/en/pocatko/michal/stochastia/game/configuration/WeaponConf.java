package en.pocatko.michal.stochastia.game.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeaponConf extends PConf {
    @JsonProperty
    public String name;
    @JsonProperty
    public int baseMinAttack;
    @JsonProperty
    public int baseMaxAttack;
}