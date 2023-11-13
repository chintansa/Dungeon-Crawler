package dungeon;

import java.util.List;

/**
 * Represents the location as a property of every point in the dungeon.
 * Holds the property if it is a cave or tunnel and all the possible paths to the neighbouring
 * locations and holds treasures and monsters only if it's a cave and arrows if assigned.
 */
public interface LocationInterface {

  /**a
   * String representation of the location which gets the paths, it's type and treasure if present.
   *
   * @return location attributes as string.
   */
  String locationMap();

  /**
   * gets its row location.
   *
   * @return row position.
   */
  int getX();

  /**
   * gets its column location.
   *
   * @return column position.
   */
  int getY();

  /**
   * Gets the location id.
   *
   * @return id.
   */
  int getId();

  /**
   * To check if a path to north is available.
   *
   * @return true if present else false.
   */
  boolean isNorthAvailable();

  /**
   * To check if a path to south is available.
   *
   * @return true if present else false.
   */
  boolean isSouthAvailable();

  /**
   * To check if a path to west is available.
   *
   * @return true if present else false.
   */
  boolean isWestAvailable();

  /**
   * To check if a path to east is available.
   *
   * @return true if present else false.
   */
  boolean isEastAvailable();

  /**
   * Set the direction attribute if there is an edge present in north of the location.
   */
  void setNorthTrue();

  /**
   * Set the direction attribute if there is an edge present in south of the location.
   */
  void setSouthTrue();

  /**
   * Set the direction attribute if there is an edge present in west of the location.
   */
  void setWestTrue();

  /**
   * Set the direction attribute if there is an edge present in east of the location.
   */
  void setEastTrue();

  /**
   * Sets the location type based on the number of paths it has, if 2 then tunnel else cave.
   */
  void setLocationType();

  /**
   * To get the location type if it's a tunnel or cave.
   *
   * @return Location type enum.
   */
  LocationType getLocationType();

  /**
   * Get the list of directions available from that location.
   *
   * @return list of direction enums.
   */
  List<Direction> getDirectionsAvailable();

  /**
   * Stores the generated treasure if the location is a cave.
   *
   * @param t treasure sent to store.
   */
  void holdTreasure(Treasure t);

  /**
   * To fetch treasure present in the location if it's a cave.
   *
   * @return the list of treasures stored.
   */
  List<Treasure> getTreasureChest();

  /**
   * Method to clear the treasure once picked by player.
   */
  void clearTreasureChest();


  /**
   * To clear the list of arrows after its picked.
   */
  void clearArrowList();

  /**
   * To assign a monster to the location if it's a cave.
   */
  void assignMonster();

  /**
   * To see if a monster is present and alive in the location.
   * @return true if alive else false.
   */
  boolean checkMonsterPresent();

  /**
   * To check if the monster is hit and half dead.
   * @return true if its health is half.
   */
  boolean isOtyughHalfDead();

  /**
   * Reduces the health by half of the monster if hit by arrow.
   * @return Message that the monster is hit or empty if not hit.
   */
  String hitMonster();

  /**
   * Store the arrows assigned to the location for the player to pickup.
   * @param arrow the arrow assigned.
   */
  void storeArrows(Arrow arrow);

  /**
   * To check if there are any arrows present in the location.
   * @return true if arrows are present.
   */
  boolean hasArrows();

  /**
   * Getter for the list of arrows stored for the player to pickup.
   * @return List of arrows.
   */
  List<Weapons> getArrowsStored();

  /**
   * Setter to update the smell condition of the location if monster is dead or alive.
   * @param smellCount 0 if otyugh is dead, 1 if two locations away and 2 if one location away.
   */
  void setSmellCount(int smellCount);

  /**
   * Getter for the smell count of that location.
   * @return smell count.
   */
  int getSmellCount();
}
