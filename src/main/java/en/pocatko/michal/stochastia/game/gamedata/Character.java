package en.pocatko.michal.stochastia.game.gamedata;

/**
 * Abstract class representing all player characters, non-player characters and mobs
 */
public abstract class Character {
    /**
     * Perform a single attack against other character
     * @param whom a character against whom to preform an attack
     * @param gameData game data
     * @return 
     */
    public abstract void Attack(Character whom, GameData gameData);
    /**
     * Receive an attack of a given damage
     * @param damage a damage dealt in the attack
     */
    public abstract void ReceiveAttack(int damage);
    /**
     * Returns true if a character is dead
     */
    public abstract boolean IsDead();
    /**
     * A desscription of a character
     * @return a string representing character description
     */
    public abstract String Description();
    /**
     * Getter for the health of a character
     */
    public abstract int GetHp();
    /**
     * Getter for the name of a character
     */
    public abstract String GetName();
    /**
     * Getter for the experience gained from killing a character
     */
    public abstract int GetExpGain();
}