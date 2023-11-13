package dungeon;

import java.util.List;

/**
 * Represents the Dungeon which has Caves and Tunnels of the game.
 * The caves contain treasures and player enters one of them to start the game.
 * Tunnels are ones with only two paths in and out and contain no treasures.
 * Dungeons are formed such that there is at least one path from one cave to another
 * and its minimum size is a 5X5, max of 100x100 grid and can also wrap.
 */
public interface DungeonInterface {

  /**
   * Getter for the percentage of treasure present in the dungeon.
   * @return percentage of treasure.
   */
  double getTreasurePercentage();

  /**
   * Getter to check if the dungeon is wrapping or not.
   * @return true if wrapping else false
   */
  boolean isWrapping();

  /**
   * Getter for the number of interconnecting edges added to the dungeon.
   * @return number of interconnecting edges.
   */
  int getInterconnectivity();

  /**
   * To fetch the properties of every location, its id, paths ,treasure, monster and arrows
   * present if any.
   *
   * @return property of every location.
   */
  String dumpDungeonLocationMap();

  /**
   * Getter for the adjacency list used to detect smell of otyughs.
   * @return list of lists of neighbouring locations of the current location.
   */
  List<List<LocationInterface>> getAdjacencyList();

  /**
   * To fetch the randomly selected start location in dungeon.
   *
   * @return start location.
   */
  LocationInterface getStart();

  /**
   * To fetch the randomly selected end location in dungeon.
   *
   * @return end location.
   */
  LocationInterface getEnd();

  /**
   * Gives the all the location objects present in the grid.
   *
   * @return list of locations.
   */
  List<LocationInterface> getLocationList();

  /**
   * Prints the dungeon in a 2D grid fashion.
   *
   * @return grid in a sting format.
   */
  String dumpBetterDungeon();

  /**
   * Fetch the start and end as a string to display.
   *
   * @return start and end location as a string.
   */
  String getStartAndEndLocation();

  /**
   * To fetch the list of all caves present in the dungeon.
   * @return list of cave location.
   */
  List<LocationInterface> getAllCaveList();
}
