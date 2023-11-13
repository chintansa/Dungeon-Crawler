package dungeon;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


class GameSwingClickHandler extends MouseAdapter {

  private final GameSwingControlInterface control;

  public GameSwingClickHandler(GameSwingControlInterface listener) {
    if (listener == null) {
      throw new IllegalArgumentException("Invalid controller sent to click handler");
    }
    control = listener;
  }


  @Override
  public void mouseReleased(MouseEvent e) {
    super.mouseReleased(e);
    control.moveOnMouseClick(e.getY() / 100, e.getX() / 100);
  }
}




