package dungeon;

/**
 * Represents the interface for implementing the controller specific to the view game.
 * It handles the functionality of the keyboard listener and the movement based on mouse
 */
public interface GameSwingControlInterface extends GameControlInterface {


  /**
   * Represents the method which gets the position of the click on the panel and uses it to move the
   * player if the operation was valid.
   *
   * @param row the x position clicked on screen.
   * @param col the y position clicked on screen.
   */
  void moveOnMouseClick(int row, int col);

  /**
   * Used to get the input from the view to and based on that restart or create a new game.
   *
   * @param value true for new game else restart the game.
   */
  void getAction(boolean value);

  /**
   * Method to handle the update row request from JMenu to change the number of rows.
   *
   * @param upRow rows
   */
  void updateRowsGame(int upRow);

  /**
   * Method to handle the update row request from JMenu to change the wrapping condition.
   *
   * @param wrapping wrapping boolean.
   */
  void updateWrapping(boolean wrapping);

  /**
   * Method to handle the update row request from JMenu to change the treasure percentage.
   *
   * @param treasure treasure percent.
   */
  void updateTreasure(int treasure);

  /**
   * Method to handle the update row request from JMenu to change the interconnecting edges.
   *
   * @param interconnect interconnecting edges number.
   */
  void updateInterConnectivity(int interconnect);

  /**
   * Method to handle the update row request from JMenu to change the number of Otyughs.
   *
   * @param otyughCount otyugh count.
   */
  void updateOtyugh(int otyughCount);

  /**
   * Method to handle the update row request from JMenu to change the number of columns.
   *
   * @param upCol columns.
   */
  void updateColumns(int upCol);
}
