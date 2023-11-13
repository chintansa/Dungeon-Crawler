package dungeon;

/**
 * Interface for the game controller which handles the flow of the game.
 * 1. The user can Use M to move the player and enter a direction from (North, South, East, West)
 *    to make him move if that direction is available
 * 2. The user can enter S to shoot an arrow in a distance of  1 and 5 caves and direction
 *    to shoot. They get message if they hit and kill a monster
 * 3. The user can press P to pickup treasure and arrows by entering T or A respectively.
 * 4. They can enter I for Player Stats and Q to quit the game aat any time.
 */
public interface GameControlInterface {

  /**
   * Method to start the game to move the player around the dungeon.
   * For text based game ->
   * 1. The user can press M to move the player and enter a direction from (North,South,East,West)
   *    to make him move if that direction is available
   * 2. The user can enter S to shoot an arrow in a distance of  1 and 5 caves and direction
   *    to shoot. They get message if they hit and kill a monster
   * 3. The user can press P to pickup treasure and arrows by entering T or A respectively.
   * 4. They can enter I for Player Stats and Q to quit the game aat any time.
   * For view based game ->
   * 0. It sets the mouse and keyboard listeners.
   * 1. Press W, A, S, D to move the player North, East, South and West respectively in the dungeon.
   * 2. Press a number between 1 and 5 on the num-pad for the distance and one of the arrow keys
   *    and press SPACE to shoot the arrow.
   * 3. Press I to get the player info as a pop-up displaying the number of arrows he has and the
   *    treasure he has collected.
   * 4. Press P to pick-up arrows or treasure present at the current location of the player.
   */
  void startGame();



}
