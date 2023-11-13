package dungeon;

import java.util.List;

/**
 * Represents a player who will travel through the grid and visit the caves and tunnels
 * present in the dungeon and collect and store treasure and arrows if present in dungeon
 * and also kills the monsters with the weapons he has to reach the Quit and win.
 */
public interface PlayerInterface {

  /**
   * To update the location of the player as he moves thorough the dungeon.
   * @param current the present location of the player.
   */
  void updateCurrentLocation(LocationInterface current);

  /**
   * To fetch the current location where the player is situated in the grid.
   * @return location of player.
   */
  LocationInterface getCurrentLocation();

  /**
   * To add all the treasures picked by the player to his bag of picked treasure.
   * @param treasurePicked list of treasure fetched.
   */
  void addTreasurePicked(List<Treasure> treasurePicked);


  /**
   * To pick the arrows present in the caves and tunnels.
   * @param a arrow picked.
   */
  void pickArrow(Weapons a);

  /**
   * Check to see if he has any arrows left.
   * @return true if he has arrows.
   */
  boolean hasArrows();

  /**
   * To shoot an arrow and hope it hits a monster and kill it.
   */
  void shootArrow();
}
