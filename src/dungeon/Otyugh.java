package dungeon;

/**
 * Represents the monster Otyugh which crawls around in caves and eats the player if found.
 */
public class Otyugh implements Monster {

  private final int id;
  private int health;
  private int hitCount;

  /**
   * Constructor to create the otyugh.
   *
   * @param id of the location it's present.
   */
  public Otyugh(int id) {
    this.health = 100;
    this.id = id;
    this.hitCount = 0;
  }


  @Override
  public int getHealth() {
    return this.health;
  }

  @Override
  public void decreaseHealth(int health) {
    if (hitCount < 2) {
      this.health -= health;
      hitCount++;
    }
  }


  @Override
  public String toString() {
    return "Otyugh "
            + "at -> " + id
            + " health= " + health;
  }
}
