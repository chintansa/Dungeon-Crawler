package dungeon;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Represents the pop-up frame that is shown at the end of the game.
 */
class GameEnd extends JFrame {
  public GameEnd() {

    setSize(400, 100);
    setLocation(700,300);
    this.setVisible(true);
  }


  /**
   * this pop-up is called when the player reaches the end and wins the game.
   */
  void winGame() {
    JLabel win  = new JLabel("Game ended !!!-> Reached Exit!! You Won!!");
    this.add(win);
  }

  /**
   * This pop-up is called when the player gets eaten by an otyugh.
   */
  void gotKilled() {
    JLabel kill  = new JLabel("Game ended !!!-> Got eaten by a otyugh, Better luck next time");
    this.add(kill);
  }

}
