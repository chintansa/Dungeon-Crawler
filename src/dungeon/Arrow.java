package dungeon;

/**
 * Represents the arrow weapon used by the player to slay otyughs.
 */
public class Arrow implements Weapons {
  private int id;

  /**
   * Constructor for the arrow class.
   * @param id stores the location id of where it is present.
   */
  public Arrow(int id) {
    this.id = id;
  }


  @Override
  public int getArrowLocation() {
    return this.id;
  }


  @Override
  public void updateMovement(int id) {
    this.id = id;
  }

}
