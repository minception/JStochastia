package en.pocatko.michal.stochastia.game.gamedata;

import java.util.ArrayList;
import java.util.Random;

import en.pocatko.michal.stochastia.game.Textdata;
import en.pocatko.michal.stochastia.game.configuration.Configuration;
import en.pocatko.michal.stochastia.game.configuration.PlayerConf;
import en.pocatko.michal.stochastia.game.gamedata.item.Armor;
import en.pocatko.michal.stochastia.game.gamedata.item.Item;
import en.pocatko.michal.stochastia.game.gamedata.item.Weapon;
import en.pocatko.michal.stochastia.utils.PairKey;

/**
 * A class containing data about player
 */
public class Player extends Character{
	private static final int LVL2EXP = 5;

	public ArrayList<Item> inventory;
	public PairKey position;
	public int level;
	public int m_exp;
	public int nextLvlExp;
	public int maxHp;
	private int m_hp;
	private String m_name;

	public int BaseAttack;
	public int BaseDefence;
	public Weapon equippedWeapon;
	public Armor equippedArmor;

	@Override
	public int GetHp() {
		return m_hp;
	}
	public void SetHp(int value) {
		m_hp = value > maxHp ? maxHp : value;
	}
	public int GetExp() {
		return m_exp;
	}

	public void SetExp(int value) {
		m_exp = value;
		// level up
		while (m_exp > nextLvlExp) {
			m_exp -= nextLvlExp;
			levelUp();
		}
	}
	
	private void levelUp() {
		level++;
		nextLvlExp += Math.pow(2, level);
		BaseAttack += level;
		BaseDefence += level/2;
		maxHp += level/3;
		SetHp(maxHp);
	}
	@Override
	public void Attack(Character whom, GameData gameData) {
		int minDamage = equippedWeapon.minDamage + BaseAttack;
		int maxDamage = equippedWeapon.maxDamage + BaseAttack;
		int damage = gameData.Rng.nextInt(maxDamage - minDamage + 1) + minDamage;
		whom.ReceiveAttack(damage);
		// a nonplayer character always retaliates when attacked by a player
		if(!whom.IsDead()){
			whom.Attack(this, gameData);
		}
	}

	/**
	 * A player constructor
	 * @param rng A random number generator used to generate the player
	 */
	public Player(Random rng) {
		PlayerConf conf = Configuration.getRandom(Configuration.players, rng);
		maxHp = conf.baseMaxHp;
		BaseAttack = conf.baseAttack;
		BaseDefence = conf.baseDefence;
		m_name = conf.name;
		equippedWeapon = Weapon.GenerateSimpleWeapon(Configuration.weapons.get(conf.baseWeaponIndex));
		equippedArmor = Armor.GenerateSimpleArmor(Configuration.armor.get(conf.baseArmorIndex));
		maxHp = conf.baseMaxHp;
		SetHp(maxHp);
		level = 1;
		nextLvlExp = LVL2EXP;
		position = new PairKey(0, 0);
		inventory = new ArrayList<>();
	}

	@Override
	public String Description() {
		int minAttack = BaseAttack + equippedWeapon.minDamage;
		int maxAttack = BaseAttack + equippedWeapon.maxDamage;
		int defence = BaseDefence + equippedArmor.defence;
		return String.format(Textdata.Get("playerDescription"), GetName(), level, m_exp, nextLvlExp, m_hp, maxHp, minAttack, maxAttack, defence);
	}
	/**
	 * Description of what the player curently has equipped
	 * @return String containing the description
	 */
	public String EquippedDescription() {
		return String.format(Textdata.Get("playerEquipDescription"), equippedWeapon.Description(), equippedArmor.Description());
	}

	@Override
	public void ReceiveAttack(int damage) {
		SetHp(GetHp() - Math.floorDiv(damage, (int)Math.log(BaseDefence)));
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
		// Killing yourself is not a very learning experience
		return 0;
	}
}