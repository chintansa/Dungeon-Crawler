package dungeon;

import java.awt.event.KeyListener;

/**
 * Represents the interface for Game view for displaying the view to the user.
 * Shows the user the dungeon created updates as the state of the model changes.
 */
public interface GameViewInterface {

  /**
   * Gets the action taken by the user from the controller and displays it to the user.
   * @param stats the string output of each action taken by the user.
   */
  void getGameStats(String stats);

  /**
   * Used to refresh the view state as per the changes taken place in the model through
   * the controller.
   */
  void refresh();

  /**
   * Checks if the game is in any of the end states like killed by monster or reached exit and
   * shows a pop-up to the user accordingly.
   */
  void endGame();

  /**
   * Used to set the listeners to the view for keyboard and mouse clicks and send the inputs back
   * to the controller to handle them accordingly.
   * @param listener controller instance.
   * @param key KeyBoard listener.
   */
  void setActionListeners(GameSwingControlInterface listener, KeyListener key);


}
