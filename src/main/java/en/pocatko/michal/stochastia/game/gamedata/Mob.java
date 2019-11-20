package en.pocatko.michal.stochastia.game.gamedata;

import java.util.Random;
import en.pocatko.michal.stochastia.game.Textdata;
import en.pocatko.michal.stochastia.game.configuration.MobConf;

/**
 * A class that contains data about mob
 */
public class Mob extends Character {
    public int lvl;
    private String m_name;
    private int m_attack;
    public int expGain;
    private int m_hp;
    @Override
    public int GetHp() {
        return m_hp;
    }
    public void SetHp(int value) {
        m_hp = value;
    }
    @Override
    public void Attack(Character whom, GameData gameData) {
        Random rng = gameData.Rng;
        int damage = m_attack + rng.nextInt(m_attack/2);
        whom.ReceiveAttack(damage);
    }

    @Override
    public String Description() {
        return String.format(Textdata.Get("mobDesc"), GetName(), lvl);
    }
    
    public static Mob GenerateMob(MobConf conf, GameData gameData) {
        Mob result = new Mob();
        result.lvl = gameData.Player.level;
        result.SetName(conf.name);
        result.SetHp(conf.hp *gameData.Player.level);
        result.m_attack = (conf.attack + gameData.Rng.nextInt(1))*result.lvl;
        result.expGain = conf.expGain*result.lvl;
        return result;
    }

    @Override
    public void ReceiveAttack(int damage) {
        SetHp(GetHp() - damage);
    }

    @Override
    public boolean IsDead() {
        return GetHp() <= 0;
    }

    @Override
    public String GetName() {
        return m_name;
    }
    public void SetName(String value) {
        m_name = value;
    }

    @Override
    public int GetExpGain() {
        return expGain;
    }
}