package dungeon;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Creates a series of option panes which takes in the input from the user which is used to
 * create the model to play the game.
 */
public class GameDetails extends JFrame {
  private int row;
  private int col;
  private boolean wrapping;
  private int interconnect;
  private int treasure;
  private int otyughCount;

  /**
   * Constructor that call the series of option panes to get the inputs.
   */
  public GameDetails() {
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.createModel();
  }


  /**
   * Represents the creation of option panes.
   */
  private void createModel() {
    try {
      row = Integer.parseInt(JOptionPane.showInputDialog(null,
              "Enter the number of rows",
              "Dungeon game", JOptionPane.QUESTION_MESSAGE));

      col = Integer.parseInt(JOptionPane.showInputDialog(null,
              "Enter the number of cols",
              "Dungeon game", JOptionPane.QUESTION_MESSAGE));

      Boolean[] options = {true, false};
      wrapping = (Boolean) JOptionPane.showInputDialog(null,
              "Should the dungeon be wrapping",
              "Dungeon game", JOptionPane.QUESTION_MESSAGE, null, options,
              true);

      interconnect = Integer.parseInt(JOptionPane.showInputDialog(null,
              "Enter the inter-connectivity",
              "Dungeon game", JOptionPane.QUESTION_MESSAGE));


      treasure = Integer.parseInt(JOptionPane.showInputDialog(null,
              "Enter the percentage of treasure",
              "Dungeon game", JOptionPane.QUESTION_MESSAGE));


      otyughCount = Integer.parseInt(JOptionPane.showInputDialog(null,
              "Enter the number of otyughs",
              "Dungeon game", JOptionPane.QUESTION_MESSAGE));
      this.setVisible(false);
      this.dispose();
    } catch (IllegalArgumentException e) {
      JFrame jFrame = new JFrame();
      jFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
      JOptionPane.showMessageDialog(jFrame, "Invalid values entered !!! run again");
      this.dispose();
    }
  }

  /**
   * Getter for the entered num of otyughs.
   *
   * @return number of otyughs.
   */
  public int getOtyughCount() {
    return otyughCount;
  }

  /**
   * Getter for the entered interconnectivity.
   *
   * @return interconnectivity.
   */
  public int getInterconnect() {
    return interconnect;
  }

  /**
   * Getter for wrapping condition.
   *
   * @return true if wrapping else false.
   */
  public boolean isWrapping() {
    return wrapping;
  }

  /**
   * Getter for the treasure percentage.
   *
   * @return treasure percentage.
   */
  public int getTreasure() {
    return treasure;
  }

  /**
   * Getter for the rows entered for the dungeon.
   *
   * @return rows.
   */
  public int getRow() {
    return row;
  }

  /**
   * Getter for the columns entered for the dungeon.
   *
   * @return columns.
   */
  public int getCol() {
    return col;
  }
}
