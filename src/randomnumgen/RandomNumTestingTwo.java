package randomnumgen;


/**
 * Represents the class to create a random numbers for testing returning a value of two less
 * than the bound.
 */
public class RandomNumTestingTwo implements RandomNumInterface {


  @Override
  public int getRandomNumber(int bound) {
    return bound - 2;
  }
}
