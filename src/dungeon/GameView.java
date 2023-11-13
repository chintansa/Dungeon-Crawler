package dungeon;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


/**
 * Represents the main view which holds together different components to create the display for the
 * user to interact with. Has a view for the dungeon , the actions taken by the player and the
 * description of each location the player is present or moves to.
 */
public class GameView extends JFrame implements GameViewInterface {
  private final GamePanel panel;
  private final ReadOnlyGameModel model;
  private final JMenuItem quit;
  private final JMenuItem newGame;
  private final JMenuItem restart;
  private final JPanel gameInfo;
  private final JPanel gameStats;
  private final JMenuItem rows;
  private final JMenuItem cols;
  private final JMenuItem inter;
  private final JMenuItem monCount;
  private final JMenuItem treasureCnt;
  private final JMenuItem wrap;

  /**
   * Constructor for the view component where the panels and description components are created.
   * @param model read-only game model used to create the view.
   */
  public GameView(ReadOnlyGameModel model) {
    super("Dungeon And Monsters Game");
    this.model = model;
    this.setSize(500, 800);
    this.setResizable(false);
    this.setLocation(150,200);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.panel = new GamePanel(model);


    BorderLayout border = new BorderLayout();
    this.setLocationRelativeTo(this.panel);
    this.setLayout(border);

    //Info panel of every move
    gameInfo = new JPanel();
    gameInfo.setSize(new Dimension(500,100));
    this.updateInfo();
    gameInfo.setVisible(true);
    this.add(new JScrollPane(gameInfo),BorderLayout.SOUTH);

    //added game panel to view
    this.add(new JScrollPane(this.panel),BorderLayout.CENTER);


    //added game stats update panel to view
    gameStats = new JPanel();
    gameStats.setVisible(true);
    this.getGameStats("Hello, Welcome to the dungeon");
    gameStats.setSize(new Dimension(500,300));
    this.add(new JScrollPane(gameStats),BorderLayout.NORTH);

    // J menu and addition of Settings.
    JMenuBar menu = new JMenuBar();
    JMenu settings = new JMenu("Settings");
    menu.add(settings);
    quit = new JMenuItem("Quit");
    newGame =  new JMenuItem("New Game");
    restart = new JMenuItem("Restart");
    settings.add(quit);
    settings.add(newGame);
    settings.add(restart);

    //Game configs are added to menu.
    JMenu modelInfo = new JMenu("Game Configs");
    menu.add(modelInfo);
    rows = new JMenuItem("Rows: " + this.model.getRow());
    modelInfo.add(rows);
    cols = new JMenuItem("Columns: " + this.model.getColumn());
    modelInfo.add(cols);
    inter = new JMenuItem("Interconnectivity: " + this.model.getInterconnectivity());
    modelInfo.add(inter);
    wrap = new JMenuItem("Wrapping: " + this.model.isWrapping());
    modelInfo.add(wrap);
    treasureCnt = new JMenuItem("Treasure Percent: " + this.model.getTreasurePercentage());
    modelInfo.add(treasureCnt);
    monCount = new JMenuItem("Otyugh Number: " + this.model.getMonsterNum());
    modelInfo.add(monCount);
    this.setJMenuBar(menu);

    setVisible(true);

  }


  @Override
  public void getGameStats(String stats) {
    if (stats == null) {
      throw new IllegalArgumentException("Invalid stats sent");
    }
    gameStats.removeAll();
    JTextArea gameText = new JTextArea(stats);
    gameText.setPreferredSize(new Dimension(500,50));
    gameText.enable(false);
    gameStats.add(gameText);
    gameStats.revalidate();
  }


  /**
   * Panel to show the location description where the player is present.
   */
  private void updateInfo() {
    gameInfo.removeAll();
    JTextArea gameText = new JTextArea(model.toString());
    gameText.setPreferredSize(new Dimension(500,200));
    gameText.enable(false);
    gameInfo.add(gameText);
    gameInfo.revalidate();
  }


  @Override
  public void refresh() {
    panel.removeAll();
    panel.createDungeonView();
    panel.revalidate();
    this.updateInfo();
    gameStats.revalidate();
  }


  @Override
  public void endGame() {
    if (model.canGameEnd()) {
      if (model.isPlayerAlive()) {
        new GameEnd().winGame();
      } else {
        new GameEnd().gotKilled();
      }
      this.setFocusable(false);
    } else if (!model.isPlayerAlive()) {
      new GameEnd().gotKilled();
    }

  }




  @Override
  public void setActionListeners(GameSwingControlInterface listener, KeyListener key) {
    if (listener == null || key == null) {
      throw new IllegalArgumentException("Invalid action listeners");
    }
    panel.getListener(listener);
    this.addKeyListener(key);

    quit.addActionListener((e) -> System.exit(0));
    newGame.addActionListener((e) -> this.disposeView(listener));
    restart.addActionListener((e) -> this.disposeViewRestart(listener));
    rows.addActionListener((e) -> this.updateRows(listener));
    cols.addActionListener((e) -> this.updateCols(listener));
    monCount.addActionListener((e) -> this.updateMon(listener));
    inter.addActionListener((e) -> this.updateInter(listener));
    treasureCnt.addActionListener((e) -> this.updateTre(listener));
    wrap.addActionListener((e) -> this.updateWrap(listener));
  }

  private void updateWrap(GameSwingControlInterface listener) {
    Boolean[] options = {true, false};
    boolean wrapping = (Boolean) JOptionPane.showInputDialog(null,
              "Update dungeon wrapping condition",
              "Dungeon game", JOptionPane.QUESTION_MESSAGE, null, options,
              true);
    listener.updateWrapping(wrapping);
    this.dispose();
  }

  private void updateTre(GameSwingControlInterface listener) {
    try {
      int treasure = Integer.parseInt(JOptionPane.showInputDialog(null,
              "Enter the percentage of treasure",
              "Dungeon game", JOptionPane.QUESTION_MESSAGE));
      listener.updateTreasure(treasure);
      this.dispose();
    } catch (NumberFormatException e) {
      // invalid value
    }
  }

  private void updateInter(GameSwingControlInterface listener) {
    try {
      int interconnect = Integer.parseInt(JOptionPane.showInputDialog(null,
              "Enter the inter-connectivity",
              "Dungeon game", JOptionPane.QUESTION_MESSAGE));
      listener.updateInterConnectivity(interconnect);
      this.dispose();
    } catch (NumberFormatException e) {
      // invalid value
    }
  }

  private void updateMon(GameSwingControlInterface listener) {
    int otyughCount = Integer.parseInt(JOptionPane.showInputDialog(null,
            "Enter the number of otyughs",
            "Dungeon game", JOptionPane.QUESTION_MESSAGE));
    listener.updateOtyugh(otyughCount);
    this.dispose();
  }

  private void updateCols(GameSwingControlInterface listener) {
    int upCol = Integer.parseInt(JOptionPane.showInputDialog(null,
            "Enter the number of cols to update",
            "Dungeon game", JOptionPane.QUESTION_MESSAGE));
    listener.updateColumns(upCol);
    this.dispose();
  }


  private void updateRows(GameSwingControlInterface controlInterface) {
    try {
      int upRow = Integer.parseInt(JOptionPane.showInputDialog(null,
              "Enter the number of rows to update",
              "Dungeon game", JOptionPane.QUESTION_MESSAGE));
      controlInterface.updateRowsGame(upRow);
      this.dispose();
    } catch (NumberFormatException e) {
      // invalid value
    }
  }


  /**
   * Method to send action for new game to controller.
   * @param controlInterface controller object
   */
  private void disposeView(GameSwingControlInterface controlInterface) {
    controlInterface.getAction(true);
    this.setVisible(false);
    this.dispose();

  }

  /**
   * Method to send action for restart to controller.
   * @param controlInterface controller object
   */
  private void disposeViewRestart(GameSwingControlInterface controlInterface) {
    controlInterface.getAction(false);
    this.setVisible(false);
    this.dispose();

  }
}
