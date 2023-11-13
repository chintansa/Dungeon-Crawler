package randomnumgen;

import java.util.Random;

/**
 * Represents the class to generate the numbers for implementation of game.
 */
public class RandomNumGenerator extends Random implements RandomNumInterface {

  private final Random r;

  /**
   * Constructor to assign the random object.
   */
  public RandomNumGenerator() {
    this.r = new Random();
  }

  @Override
  public int getRandomNumber(int bound) {
    return r.nextInt(bound);
  }

}
