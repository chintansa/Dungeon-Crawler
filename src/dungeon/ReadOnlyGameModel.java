package dungeon;

import java.util.List;

/**
 * Read-Only game model used to create the view.
 * It consists of the major getters needed to create the view.
 */
public interface ReadOnlyGameModel {
  /**
   * Getter for the number of rows in dungeon.
   *
   * @return rows.
   */
  int getRow();

  /**
   * Getter for the number of columns in dungeon.
   *
   * @return columns.
   */
  int getColumn();

  /**
   * Getter for the inter-connectivity present in dungeon.
   *
   * @return number of interconnecting edges added.
   */
  int getInterconnectivity();

  /**
   * Getter for the number of Otyughs in dungeon.
   *
   * @return otyugh count.
   */
  int getMonsterNum();

  /**
   * Getter for the percentage of treasure present in dungeon.
   *
   * @return treasure percent.
   */
  int getTreasurePercentage();

  /**
   * Getter for wrapping condition in dungeon.
   *
   * @return true if wrapping else false.
   */
  boolean isWrapping();


  /**
   * Getter for the player's current location in dungeon.
   *
   * @return id of player's current location.
   */
  int getPlayerLocation();

  /**
   * Getter to check if the player is alive or not in dungeon.
   *
   * @return true if alive else false.
   */
  boolean isPlayerAlive();

  /**
   * To fetch the player stats of where he's currently located and the treasure he's collected.
   *
   * @return stats as a string.
   */
  String getPlayerStats();

  /**
   * Prints the available directions from the current location.
   *
   * @return Sting of the list of possible paths' player can move.
   */
  String getPossiblePaths();

  /**
   * To fetch the list of all locations present in the dungeon.
   *
   * @return list of locations.
   */
  List<LocationInterface> getLocationList();


  /**
   * Gets the adjacency list for the dungeon created, used to check the smell if otyughs are present
   * in neighbouring locations.
   *
   * @return List containing list of adjacent locations for each location.
   */
  List<List<LocationInterface>> getAdjacencyList();

  /**
   * Checks if the player is in the end location or not.
   *
   * @return true if he's at end location else false.
   */
  boolean canGameEnd();

}
