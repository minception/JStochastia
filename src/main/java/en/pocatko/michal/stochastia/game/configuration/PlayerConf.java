package en.pocatko.michal.stochastia.game.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerConf extends PConf {
    @JsonProperty
    public String name;
    @JsonProperty
    public int baseMaxHp;
    @JsonProperty
    public int baseAttack;
    @JsonProperty
    public int baseDefence;
    @JsonProperty
    public int baseWeaponIndex;
    @JsonProperty
    public int baseArmorIndex;
}