package randomnumgen;


/**
 * Represents the class to create a random numbers for testing.
 */
public class RandomNumTesting implements RandomNumInterface {


  @Override
  public int getRandomNumber(int bound) {
    return bound - 1;
  }
}
