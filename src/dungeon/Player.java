package dungeon;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player who will travel through the grid and visit the caves and tunnels
 * present in the dungeon and collect and store treasure and arrows if present in dungeon
 * and also kills the monsters with the weapons he has to reach the Quit and win.
 */
class Player implements PlayerInterface {
  private final List<Treasure> collectedTreasure;
  private final List<Weapons> armory;
  private LocationInterface currentLocation;

  /**
   * Constructor to create a player with a start node given.
   *
   * @param start start location.
   */
  Player(LocationInterface start) {
    if (start == null) {
      throw new IllegalArgumentException("Invalid start given");
    }
    this.currentLocation = start;
    this.collectedTreasure = new ArrayList<>();
    this.armory = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      this.armory.add(new Arrow(start.getId()));
    }
  }


  @Override
  public void updateCurrentLocation(LocationInterface current) {
    if (current == null) {
      throw new IllegalArgumentException("Invalid current location update");
    }
    this.currentLocation = current;
  }

  @Override
  public LocationInterface getCurrentLocation() {
    return new Location(this.currentLocation.getX(), this.currentLocation.getY(),
            this.currentLocation.getId(),
            this.currentLocation.isNorthAvailable(), this.currentLocation.isSouthAvailable(),
            this.currentLocation.isEastAvailable(),
            this.currentLocation.isWestAvailable(), this.currentLocation.getLocationType(),
            this.currentLocation.getTreasureChest(), this.currentLocation.getDirectionsAvailable(),
            this.currentLocation.checkMonsterPresent(), this.currentLocation.getArrowsStored());
  }

  @Override
  public void addTreasurePicked(List<Treasure> t) {
    if (t.isEmpty() || t.contains(null)) {
      throw new IllegalArgumentException("No treasure present");
    }
    this.collectedTreasure.addAll(t);
  }

  private String treasureCount() {
    int diamond = 0;
    int ruby = 0 ;
    int saph = 0;
    for (Treasure t:
         this.collectedTreasure) {
      if (t == Treasure.DIAMOND) {
        diamond++;
      } else if (t == Treasure.RUBY) {
        ruby++;
      } else {
        saph++;
      }
    }
    return "Diamond: " + diamond + ", Ruby: " + ruby + ", Sapphire: " + saph;
  }

  @Override
  public String toString() {
    return "Player at "
            + "Location = "
            + currentLocation.getId()
            + ", Arrows left " + (armory.size())
            + ", Collected Treasure = "
            + (collectedTreasure.size() > 0 ? this.treasureCount() : "Empty");
  }


  @Override
  public void pickArrow(Weapons a) {
    if (a == null) {
      throw new IllegalArgumentException("No arrows");
    }
    this.armory.add(a);
  }

  @Override
  public boolean hasArrows() {
    return this.armory.size() > 0;
  }

  @Override
  public void shootArrow() {
    if (this.hasArrows()) {
      this.armory.remove(this.armory.size() - 1);
    }
  }


}
