package dungeon;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Represents the controller which handles the keyboard and mouse clicks.
 */
public class GameSwingControl implements GameSwingControlInterface, KeyListener {
  private final Set<Integer> events;
  private GameInterface model;
  private GameViewInterface view;

  /**
   * Constructor to create the controller.
   *
   * @param model Game model.
   * @param view  View object.
   */
  public GameSwingControl(GameInterface model, GameViewInterface view) {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Invalid model or view sent to controller");
    }
    this.model = model;
    this.view = view;
    events = new HashSet<>();

  }

  @Override
  public void startGame() {
    this.view.setActionListeners(this, this);
  }

  @Override
  public void moveOnMouseClick(int row, int col) {
    this.view.refresh();
    int x = model.getLocationList().get(model.getPlayerLocation()).getX();
    int y = model.getLocationList().get(model.getPlayerLocation()).getY();

    int calX = row - x;
    int calY = col - y;
    if (!model.canGameEnd() && model.isPlayerAlive()) {
      try {
        if (model.isWrapping()) {
          if (calX == (-1 * (model.getRow() - 1)) && calY == 0) {
            this.view.getGameStats(this.model.movePlayer("south"));
          } else if (calX == model.getRow() - 1 && calY == 0) {
            this.view.getGameStats(this.model.movePlayer("north"));
          } else if (calX == 0 && calY == (-1 * (this.model.getColumn() - 1))) {
            this.view.getGameStats(this.model.movePlayer("east"));
          } else if (calX == 0 && calY == this.model.getColumn() - 1) {
            this.view.getGameStats(this.model.movePlayer("west"));
          }
          this.view.refresh();
        }

        if (calX == 1 && calY == 0) {
          this.view.getGameStats(this.model.movePlayer("south"));
        } else if (calX == -1 && calY == 0) {
          this.view.getGameStats(this.model.movePlayer("north"));
        } else if (calX == 0 && calY == 1) {
          this.view.getGameStats(this.model.movePlayer("east"));
        } else if (calX == 0 && calY == -1) {
          this.view.getGameStats(this.model.movePlayer("west"));
        }
        this.view.refresh();
      } catch (IllegalStateException e) {
        this.view.getGameStats(e.getMessage());
        this.view.refresh();
        this.view.endGame();
      }

    }
    this.view.refresh();

  }

  @Override
  public void keyTyped(KeyEvent e) {
    this.view.refresh();
  }

  @Override
  public void keyPressed(KeyEvent e) {
    this.events.add(e.getKeyCode());
    try {
      if (!model.canGameEnd() && model.isPlayerAlive()) {
        switch (e.getKeyCode()) {
          case KeyEvent.VK_I:
            new PlayerInfoFrame(this.model);
            break;
          case KeyEvent.VK_W:
            this.view.getGameStats(this.model.movePlayer("north"));
            this.events.clear();
            break;
          case KeyEvent.VK_S:
            this.view.getGameStats(this.model.movePlayer("south"));
            this.events.clear();
            break;
          case KeyEvent.VK_A:
            this.view.getGameStats(this.model.movePlayer("west"));
            this.events.clear();
            break;
          case KeyEvent.VK_D:
            this.view.getGameStats(this.model.movePlayer("east"));
            this.events.clear();
            break;

          case KeyEvent.VK_P:
            String info = this.model.pickTreasure()
                    + "\n"
                    + this.model.pickArrows();
            this.view.getGameStats(info);
            this.view.refresh();
            break;

          case KeyEvent.VK_SPACE:
            String direction = "";
            int distance = 0;
            if (!this.events.isEmpty()) {
              for (Integer event : events) {
                switch (event) {
                  case KeyEvent.VK_UP:
                    direction = "north";
                    break;
                  case KeyEvent.VK_DOWN:
                    direction = "south";
                    break;
                  case KeyEvent.VK_LEFT:
                    direction = "west";
                    break;
                  case KeyEvent.VK_RIGHT:
                    direction = "east";
                    break;
                  case KeyEvent.VK_1:
                    distance = 1;
                    break;
                  case KeyEvent.VK_2:
                    distance = 2;
                    break;
                  case KeyEvent.VK_3:
                    distance = 3;
                    break;
                  case KeyEvent.VK_4:
                    distance = 4;
                    break;
                  case KeyEvent.VK_5:
                    distance = 5;
                    break;
                  default:
                    this.view.refresh();
                }
              }
              try {
                this.view.getGameStats(this.model.moveArrow(direction, distance));
              } catch (IllegalArgumentException r) {
                ////
              }
              this.events.clear();
            }
            break;
          default:
            this.view.refresh();
        }
      }
    } catch (IllegalStateException b) {
      this.view.getGameStats(b.getMessage());
      this.view.refresh();
      this.view.endGame();
    }
  }


  @Override
  public void keyReleased(KeyEvent e) {
    try {
      this.view.refresh();
      this.view.endGame();
    } catch (IllegalArgumentException i) {
      // empty to let player move
    }


  }

  @Override
  public void getAction(boolean value) {
    this.events.clear();
    if (value) {
      GameDetails details = new GameDetails();
      try {
        this.model = new Game(details.getRow(), details.getCol(), details.isWrapping(),
                details.getInterconnect(), details.getTreasure(), details.getOtyughCount());
      } catch (IllegalArgumentException e) {
        JFrame jFrame = new JFrame();
        JOptionPane.showMessageDialog(jFrame, e.getMessage());
      }
    } else {
      try {
        this.model = new Game(this.model.getRow(), this.model.getColumn(), this.model.isWrapping(),
                this.model.getInterconnectivity(),
                this.model.getTreasurePercentage(), this.model.getMonsterNum());
      } catch (IllegalArgumentException e) {
        JFrame jFrame = new JFrame();
        JOptionPane.showMessageDialog(jFrame, e.getMessage());
      }
    }
    this.view = new GameView(this.model);
    this.startGame();

  }

  @Override
  public void updateRowsGame(int upRow) {
    this.events.clear();
    try {
      this.model = new Game(upRow, this.model.getColumn(), this.model.isWrapping(),
              this.model.getInterconnectivity(),
              this.model.getTreasurePercentage(), this.model.getMonsterNum());
    } catch (IllegalArgumentException e) {
      JFrame jFrame = new JFrame();
      JOptionPane.showMessageDialog(jFrame, e.getMessage());
    }
    this.view = new GameView(this.model);
    this.startGame();
  }

  @Override
  public void updateWrapping(boolean wrapping) {
    this.events.clear();
    try {
      this.model = new Game(this.model.getRow(), this.model.getColumn(), wrapping,
              this.model.getInterconnectivity(),
              this.model.getTreasurePercentage(), this.model.getMonsterNum());
    } catch (IllegalArgumentException e) {
      JFrame jFrame = new JFrame();
      JOptionPane.showMessageDialog(jFrame, e.getMessage());
    }
    this.view = new GameView(this.model);
    this.startGame();
  }

  @Override
  public void updateTreasure(int treasure) {
    this.events.clear();
    try {
      this.model = new Game(this.model.getRow(), this.model.getColumn(), this.model.isWrapping(),
              this.model.getInterconnectivity(),
              treasure, this.model.getMonsterNum());
    } catch (IllegalArgumentException e) {
      JFrame jFrame = new JFrame();
      JOptionPane.showMessageDialog(jFrame, e.getMessage());
    }
    this.view = new GameView(this.model);
    this.startGame();
  }

  @Override
  public void updateInterConnectivity(int interconnect) {
    this.events.clear();
    try {
      this.model = new Game(this.model.getRow(), this.model.getColumn(), this.model.isWrapping(),
              interconnect,
              this.model.getTreasurePercentage(), this.model.getMonsterNum());
    } catch (IllegalArgumentException e) {
      JFrame jFrame = new JFrame();
      JOptionPane.showMessageDialog(jFrame, e.getMessage());
    }
    this.view = new GameView(this.model);
    this.startGame();
  }

  @Override
  public void updateOtyugh(int otyughCount) {
    this.events.clear();
    try {
      this.model = new Game(this.model.getRow(), this.model.getColumn(), this.model.isWrapping(),
              this.model.getInterconnectivity(),
              this.model.getTreasurePercentage(), otyughCount);
    } catch (IllegalArgumentException e) {
      JFrame jFrame = new JFrame();
      JOptionPane.showMessageDialog(jFrame, e.getMessage());
    }
    this.view = new GameView(this.model);
    this.startGame();
  }

  @Override
  public void updateColumns(int upCol) {
    this.events.clear();
    try {
      this.model = new Game(this.model.getRow(), upCol, this.model.isWrapping(),
              this.model.getInterconnectivity(),
              this.model.getTreasurePercentage(), this.model.getMonsterNum());
    } catch (IllegalArgumentException e) {
      JFrame jFrame = new JFrame();
      JOptionPane.showMessageDialog(jFrame, e.getMessage());
    }
    this.view = new GameView(this.model);
    this.startGame();
  }



}
