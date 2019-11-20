package en.pocatko.michal.stochastia.game.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An abstract class representing a configuration of something with probability
 * to spawn
 */
public abstract class PConf {
    @JsonProperty
    public double p;
}