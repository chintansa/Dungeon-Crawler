package dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents every position of the 2D dungeon.
 * Every point in the dungeon holds this location object which
 * contains the path to other neighbouring locations and treasure if it's a cave.
 */
public class Location implements LocationInterface {

  private final int id;
  private final int x;
  private final int y;
  private final List<Treasure> treasureList;
  private final List<Direction> directionSet;
  private final List<Weapons> arrowsStored;
  private boolean north;
  private boolean south;
  private boolean west;
  private boolean east;
  private LocationType locationType;
  private int inDegree;
  private Monster monster;
  private boolean isOtyughPresent;
  private int arrowCount;
  private int smellCount;


  /**
   * Constructor to create the location objects to store in dungeon.
   *
   * @param x  the row location in the grid.
   * @param y  the column location in the grid.
   * @param id unique identifier for the location.
   */
  Location(int x, int y, int id) {
    if (x < 0 || y < 0 || id < 0) {
      throw new IllegalArgumentException("Invalid location points");
    }
    this.id = id;
    this.x = x;
    this.y = y;
    this.north = false;
    this.south = false;
    this.west = false;
    this.east = false;
    this.locationType = LocationType.CAVE;
    this.inDegree = 0;
    this.treasureList = new ArrayList<>();
    this.directionSet = new ArrayList<>();
    this.isOtyughPresent = false;
    this.arrowCount = 0;
    this.arrowsStored = new ArrayList<>();
    this.smellCount = 0;
  }

  /**
   * Constructor to create a copy of the object for returns.
   *
   * @param x               the row location in the grid.
   * @param y               the column location in the grid.
   * @param id              unique identifier for the location.
   * @param north           direction boolean.
   * @param south           direction boolean.
   * @param east            direction boolean.
   * @param west            direction boolean.
   * @param type            of the location.
   * @param treasureList    list of treasures present if any.
   * @param directionSet    paths available from this location.
   * @param isOtyughPresent boolean to see if otyugh is present.
   * @param arrows          the arrows present at the location if any.
   */
  Location(int x, int y, int id, boolean north, boolean south, boolean east, boolean west,
           LocationType type, List<Treasure> treasureList, List<Direction> directionSet,
           boolean isOtyughPresent, List<Weapons> arrows) {
    if (x < 0 || y < 0 || id < 0 || type == null) {
      throw new IllegalArgumentException("Invalid location points");
    }
    this.id = id;
    this.x = x;
    this.y = y;
    this.north = north;
    this.south = south;
    this.west = east;
    this.east = west;
    this.locationType = type;
    this.treasureList = treasureList;
    this.directionSet = directionSet;
    this.isOtyughPresent = isOtyughPresent;
    this.arrowsStored = arrows;
    this.arrowCount = arrows.size();

  }


  @Override
  public String toString() {
    return "Loc["
            + "id=" + id
            + ']';
  }

  @Override
  public String locationMap() {
    return "Loc "
            +
            "id=" + id
            +
            ", Paths=" + this.directionSet
            +
            ", Type=" + locationType
            +
            (this.locationType
                    == LocationType.CAVE && (!this.treasureList.isEmpty()) ? ", Treasure = "
                    + this.treasureList : "")
            + ", "
            + (checkMonsterPresent() ? "Monster present " + checkMonsterPresent() : "")
            + (this.arrowsStored.size() > 0 ? " Arrow count-> " + this.arrowCount : "")
            ;
  }

  @Override
  public int getX() {
    return this.x;
  }

  @Override
  public int getY() {
    return this.y;
  }

  @Override
  public int getId() {
    return this.id;
  }

  @Override
  public boolean isNorthAvailable() {
    return this.north;

  }

  @Override
  public boolean isSouthAvailable() {
    return this.south;

  }

  @Override
  public boolean isWestAvailable() {
    return this.west;

  }

  @Override
  public boolean isEastAvailable() {
    return this.east;
  }


  @Override
  public void setNorthTrue() {
    this.north = true;
    this.directionSet.add(Direction.NORTH);
    this.inDegree++;
  }


  @Override
  public void setSouthTrue() {
    this.south = true;
    this.directionSet.add(Direction.SOUTH);
    this.inDegree++;
  }

  @Override
  public void setWestTrue() {
    this.west = true;
    this.directionSet.add(Direction.WEST);
    this.inDegree++;
  }

  @Override
  public void setEastTrue() {
    this.east = true;
    this.directionSet.add(Direction.EAST);
    this.inDegree++;
  }

  @Override
  public void setLocationType() {
    if (inDegree == 2) {
      this.locationType = LocationType.TUNNEL;
    } else {
      this.locationType = LocationType.CAVE;
    }

  }

  @Override
  public LocationType getLocationType() {
    return this.locationType;
  }

  @Override
  public List<Direction> getDirectionsAvailable() {
    return new ArrayList<>(this.directionSet);
  }

  @Override
  public void holdTreasure(Treasure t) {
    if (t == null) {
      throw new IllegalArgumentException("invalid treasure passed");
    }
    this.treasureList.add(t);
  }

  @Override
  public List<Treasure> getTreasureChest() {
    return new ArrayList<>(this.treasureList);
  }

  @Override
  public void clearTreasureChest() {
    this.treasureList.clear();
  }

  @Override
  public void clearArrowList() {
    this.arrowCount = 0;
    this.arrowsStored.clear();
  }

  @Override
  public void assignMonster() {
    if (!checkMonsterPresent()) {
      this.monster = new Otyugh(this.id);
    }
    this.isOtyughPresent = true;
  }

  @Override
  public boolean checkMonsterPresent() {
    return this.isOtyughPresent;
  }

  @Override
  public boolean isOtyughHalfDead() {
    if (checkMonsterPresent()) {
      return this.monster.getHealth() == 50;
    }
    return false;
  }

  private void updateMonsterStatus() {
    if (checkMonsterPresent() && this.monster.getHealth() == 0) {
      this.smellCount = 0;
      this.isOtyughPresent = false;
    }
  }

  @Override
  public String hitMonster() {
    if (checkMonsterPresent()) {
      monster.decreaseHealth(50);
      this.updateMonsterStatus();
      if (monster.getHealth() == 0) {
        this.smellCount = 0;
        return "You hear a great howl in the distance\n"
                + "You killed the otyugh!!! ";
      } else {
        return "You hear a great howl in the distance";
      }
    }
    return "";
  }

  @Override
  public void storeArrows(Arrow arrow) {
    if (arrow == null) {
      throw new IllegalArgumentException("Null arrow passed");
    }
    arrow.updateMovement(this.id);
    this.arrowsStored.add(arrow);
    this.arrowCount++;
  }

  @Override
  public boolean hasArrows() {
    return this.arrowCount > 0;
  }

  @Override
  public List<Weapons> getArrowsStored() {
    return new ArrayList<>(this.arrowsStored);
  }

  @Override
  public int getSmellCount() {
    return this.smellCount;
  }

  @Override
  public void setSmellCount(int smellCount) {
    this.smellCount = smellCount;
  }

}
