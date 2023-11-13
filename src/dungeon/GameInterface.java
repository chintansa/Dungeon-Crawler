package dungeon;


/**
 * Represents the game where the player moves around the grid and methods to collect available
 * treasure and travel the dungeon from the picked start and end location.
 */
public interface GameInterface extends ReadOnlyGameModel {


  /**
   * Method to pick treasure available in a given cave if present.
   *
   * @return a message stating if the treasure is picked or not.
   */
  String pickTreasure();

  /**
   * Method to pick arrow available at a given location if present.
   *
   * @return a message stating if the arrow is picked or not.
   */
  String pickArrows();

  /**
   * Moves the player around the dungeon based on the available list of directions
   * and one user selects.
   *
   * @param move (N,W,E,S) decides the direction the player moves.
   * @return String with the players' movement updates.
   */
  String movePlayer(String move);


  /**
   * Prints the dungeon in a 2D grid fashion with connections between caves and tunnels.
   *
   * @return string representation of the dungeon.
   */
  String dungeonGrid();


  /**
   * To fetch the start location of the dungeon for the player to enter.
   *
   * @return the start location.
   */
  String getStart();

  /**
   * To fetch the end location of the dungeon for the player to Quit.
   *
   * @return the end location.
   */
  String getEnd();


  /**
   * Used to move the arrow in the given direction and distance between 1 and 5 caves.
   *
   * @param direction direction to be shot from the available direction.
   * @param distance  the number of caves it should travel.
   * @return a message if it hit a monster or was wasted.
   */
  String moveArrow(String direction, int distance);


}
