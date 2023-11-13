package dungeon;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * Represents the frame to show the player description contains the number of arrows he has and the
 * treasure he's collected.
 */
public class PlayerInfoFrame extends JFrame {

  /**
   * Constructor to create the player info frame.
   * @param model Read-only model.
   */
  PlayerInfoFrame(ReadOnlyGameModel model) {
    setSize(800, 100);
    setLocation(300,300);
    JLabel end = new JLabel(model.getPlayerStats());
    this.add(end);
    this.setVisible(true);
    new Timer(5_000, (e) -> {
      this.setVisible(false);
      this.dispose();
    }).start();
  }
}
