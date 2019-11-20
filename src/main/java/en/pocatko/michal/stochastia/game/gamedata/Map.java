package en.pocatko.michal.stochastia.game.gamedata;

import java.util.HashMap;
import en.pocatko.michal.stochastia.utils.PairKey;

/**
 * A class representing the current game map
 */
public class Map {
    private HashMap<PairKey, Area> m_map;
    public Area GetArea(GameData gamedata, PairKey location) {
        if(!m_map.containsKey(location)) {
            m_map.put(location, Area.GenerateArea(gamedata));
        }
        return m_map.get(location);
    }
    public Map() {
        m_map = new HashMap<>();
    }
}