package dungeon;

/**
 * Represents the weapons that present in the dungeon and present with the player.
 */
public interface Weapons {

  /**
   * Getter for the location in which the arrow is present.
   * @return id of the location.
   */
  int getArrowLocation();

  /**
   * Sets the id of the location it's assigned to be present at.
   * @param id of the location.
   */
  void updateMovement(int id);
}
