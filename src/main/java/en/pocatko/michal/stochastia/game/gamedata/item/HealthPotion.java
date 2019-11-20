package en.pocatko.michal.stochastia.game.gamedata.item;

import en.pocatko.michal.stochastia.game.gamedata.GameData;

/**
 * A class containing data about a health potion
 */
public class HealthPotion extends Item {
    private int m_healthGain;
    @Override
    public boolean equip(GameData gamedata) {
        return false;
    }

    @Override
    public boolean use(GameData gameData) {
        gameData.Player.SetHp(gameData.Player.GetHp() + m_healthGain);
        return true;
    }

    @Override
    public String Description() {
        return null;
    }

}