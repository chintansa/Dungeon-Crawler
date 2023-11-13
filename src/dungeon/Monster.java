package dungeon;

/**
 * Represents the properties for the monster otyugh.
 * Monster with full health (100) will kill the player if encountered and its
 * heath is reduced by 50 everytime its hit with an arrow.
 */
public interface Monster {
  /**
   * Getter for the health of the monster.
   * @return the health.
   */
  int getHealth();

  /**
   * Setter for the health to change it if the monster is hit by a weapon.
   * @param health decreased amount.
   */
  void decreaseHealth(int health);
}
