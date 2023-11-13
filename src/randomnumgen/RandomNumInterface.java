package randomnumgen;


/**
 * Interface which defines the generation of the random number being used in the game.
 */
public interface RandomNumInterface {

  /**
   * Returns a number from 0 to the bound(exclusive).
   * @param bound max limit of the number to be generated.
   * @return a random number.
   */
  int getRandomNumber(int bound);
}
