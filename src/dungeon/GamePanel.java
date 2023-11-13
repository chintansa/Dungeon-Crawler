package dungeon;

import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * Represents the panel which displays the actual dungeon that is created as a part of the view
 * to the user and updates as the model is updated.
 */
class GamePanel extends JPanel {
  private final ReadOnlyGameModel model;
  private final Set<Integer> visitedLocs;
  private BufferedImage monster;
  private BufferedImage diamond;
  private BufferedImage ruby;
  private BufferedImage sapphire;
  private BufferedImage player;
  private BufferedImage hide;
  private BufferedImage weakSmell;
  private BufferedImage strongSmell;
  private BufferedImage arrow;

  /**
   * Constructor to create the game panel to display the dungeon view to the user.
   * @param model a ReadOnly model of the game.
   */
  GamePanel(ReadOnlyGameModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Invalid read only model sent");
    }

    this.model = model;
    this.visitedLocs = new HashSet<>();
    this.setSize(500, 600);
    GridLayout grid = new GridLayout(model.getRow(), model.getColumn());
    setLayout(grid);

    try {
      InputStream hideStream = getClass().getResourceAsStream("/images/black.png");
      hide = ImageIO.read(hideStream);
      InputStream playerStream = getClass().getResourceAsStream("/images/player.png");
      player = ImageIO.read(playerStream);
      InputStream monsterStream = getClass().getResourceAsStream("/images/otyugh.png");
      monster = ImageIO.read(monsterStream);
      InputStream diamondStream = getClass().getResourceAsStream("/images/diamond.png");
      diamond = ImageIO.read(diamondStream);
      InputStream rubyStream = getClass().getResourceAsStream("/images/ruby.png");
      ruby = ImageIO.read(rubyStream);
      InputStream saphStream = getClass().getResourceAsStream("/images/emerald.png");
      sapphire = ImageIO.read(saphStream);
      InputStream weakStream = getClass().getResourceAsStream("/images/stench01.png");
      weakSmell = ImageIO.read(weakStream);
      InputStream strongStream = getClass().getResourceAsStream("/images/stench02.png");
      strongSmell = ImageIO.read(strongStream);
      InputStream arrowStream = getClass().getResourceAsStream("/images/arrow-white.png");
      arrow = ImageIO.read(arrowStream);
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.createDungeonView();
  }


  void getListener(GameSwingControlInterface control) {
    this.addMouseListener(new GameSwingClickHandler(control));
  }

  void createDungeonView() {
    for (LocationInterface loc : model.getLocationList()) {
      BufferedImage combinedImage = new BufferedImage(64 * 2, 64 * 2,
              BufferedImage.TYPE_INT_ARGB);
      Graphics2D g = combinedImage.createGraphics();
      BufferedImage img = null;
      InputStream imageStream;
      if (loc.getDirectionsAvailable().contains(Direction.NORTH)   // Only North
              && !loc.getDirectionsAvailable().contains(Direction.SOUTH)
              && !loc.getDirectionsAvailable().contains(Direction.EAST)
              &&
              !loc.getDirectionsAvailable().contains(Direction.WEST)) {
        try {
          imageStream = getClass().getResourceAsStream("/images/N.png");
          img = ImageIO.read(imageStream);
        } catch (IOException e) {
          e.printStackTrace();
        }


      } else if (!loc.getDirectionsAvailable().contains(Direction.NORTH)  // Only South
              && loc.getDirectionsAvailable().contains(Direction.SOUTH)
              && !loc.getDirectionsAvailable().contains(Direction.EAST)
              && !loc.getDirectionsAvailable().contains(Direction.WEST)) {
        try {
          imageStream = getClass().getResourceAsStream("/images/S.png");
          img = ImageIO.read(imageStream);
        } catch (IOException e) {
          e.printStackTrace();
        }


      } else if (!loc.getDirectionsAvailable().contains(Direction.NORTH)  // Only East
              && !loc.getDirectionsAvailable().contains(Direction.SOUTH)
              && loc.getDirectionsAvailable().contains(Direction.EAST)
              && !loc.getDirectionsAvailable().contains(Direction.WEST)) {
        try {
          imageStream = getClass().getResourceAsStream("/images/E.png");
          img = ImageIO.read(imageStream);
        } catch (IOException e) {
          e.printStackTrace();
        }


      } else if (!loc.getDirectionsAvailable().contains(Direction.NORTH)    // Only West
              && !loc.getDirectionsAvailable().contains(Direction.SOUTH)
              && !loc.getDirectionsAvailable().contains(Direction.EAST)
              && loc.getDirectionsAvailable().contains(Direction.WEST)) {
        try {
          imageStream = getClass().getResourceAsStream("/images/W.png");
          img = ImageIO.read(imageStream);
        } catch (IOException e) {
          e.printStackTrace();
        }


      } else if (loc.getDirectionsAvailable().contains(Direction.NORTH)   // Only North and South
              && loc.getDirectionsAvailable().contains(Direction.SOUTH)
              && !loc.getDirectionsAvailable().contains(Direction.EAST)
              && !loc.getDirectionsAvailable().contains(Direction.WEST)) {
        try {
          imageStream = getClass().getResourceAsStream("/images/NS.png");
          img = ImageIO.read(imageStream);
        } catch (IOException e) {
          e.printStackTrace();
        }


      } else if (loc.getDirectionsAvailable().contains(Direction.NORTH)    // Only North and East
              && !loc.getDirectionsAvailable().contains(Direction.SOUTH)
              && loc.getDirectionsAvailable().contains(Direction.EAST)
              && !loc.getDirectionsAvailable().contains(Direction.WEST)) {
        try {
          imageStream = getClass().getResourceAsStream("/images/NE.png");
          img = ImageIO.read(imageStream);
        } catch (IOException e) {
          e.printStackTrace();
        }


      } else if (loc.getDirectionsAvailable().contains(Direction.NORTH)  // Only North and west
              && !loc.getDirectionsAvailable().contains(Direction.SOUTH)
              && !loc.getDirectionsAvailable().contains(Direction.EAST)
              && loc.getDirectionsAvailable().contains(Direction.WEST)) {
        try {
          imageStream = getClass().getResourceAsStream("/images/NW.png");
          img = ImageIO.read(imageStream);
        } catch (IOException e) {
          e.printStackTrace();
        }


      } else if (loc.getDirectionsAvailable().contains(Direction.NORTH)  // North South East
              && loc.getDirectionsAvailable().contains(Direction.SOUTH)
              && loc.getDirectionsAvailable().contains(Direction.EAST)
              && !loc.getDirectionsAvailable().contains(Direction.WEST)) {
        try {
          imageStream = getClass().getResourceAsStream("/images/NES.png");
          img = ImageIO.read(imageStream);
        } catch (IOException e) {
          e.printStackTrace();
        }


      } else if (loc.getDirectionsAvailable().contains(Direction.NORTH)   // North South West
              && loc.getDirectionsAvailable().contains(Direction.SOUTH)
              && !loc.getDirectionsAvailable().contains(Direction.EAST)
              && loc.getDirectionsAvailable().contains(Direction.WEST)) {
        try {
          imageStream = getClass().getResourceAsStream("/images/NSW.png");
          img = ImageIO.read(imageStream);
        } catch (IOException e) {
          e.printStackTrace();
        }


      } else if (loc.getDirectionsAvailable().contains(Direction.NORTH)   // North East West
              && !loc.getDirectionsAvailable().contains(Direction.SOUTH)
              && loc.getDirectionsAvailable().contains(Direction.EAST)
              && loc.getDirectionsAvailable().contains(Direction.WEST)) {
        try {
          imageStream = getClass().getResourceAsStream("/images/NEW.png");
          img = ImageIO.read(imageStream);
        } catch (IOException e) {
          e.printStackTrace();
        }


      } else if (!loc.getDirectionsAvailable().contains(Direction.NORTH)     // East and West
              && !loc.getDirectionsAvailable().contains(Direction.SOUTH)
              && loc.getDirectionsAvailable().contains(Direction.EAST)
              && loc.getDirectionsAvailable().contains(Direction.WEST)) {
        try {
          imageStream = getClass().getResourceAsStream("/images/EW.png");
          img = ImageIO.read(imageStream);
        } catch (IOException e) {
          e.printStackTrace();
        }


      } else if (!loc.getDirectionsAvailable().contains(Direction.NORTH)  // East South West
              && loc.getDirectionsAvailable().contains(Direction.SOUTH)
              && loc.getDirectionsAvailable().contains(Direction.EAST)
              && loc.getDirectionsAvailable().contains(Direction.WEST)) {
        try {
          imageStream = getClass().getResourceAsStream("/images/ESW.png");
          img = ImageIO.read(imageStream);
        } catch (IOException e) {
          e.printStackTrace();
        }


      } else if (!loc.getDirectionsAvailable().contains(Direction.NORTH)   // East South
              && loc.getDirectionsAvailable().contains(Direction.SOUTH)
              && loc.getDirectionsAvailable().contains(Direction.EAST)
              && !loc.getDirectionsAvailable().contains(Direction.WEST)) {
        try {
          imageStream = getClass().getResourceAsStream("/images/ES.png");
          img = ImageIO.read(imageStream);
        } catch (IOException e) {
          e.printStackTrace();
        }


      } else if (!loc.getDirectionsAvailable().contains(Direction.NORTH)    // South WEST
              && loc.getDirectionsAvailable().contains(Direction.SOUTH)
              && !loc.getDirectionsAvailable().contains(Direction.EAST)
              && loc.getDirectionsAvailable().contains(Direction.WEST)) {
        try {
          imageStream = getClass().getResourceAsStream("/images/SW.png");
          img = ImageIO.read(imageStream);
        } catch (IOException e) {
          e.printStackTrace();
        }

      } else if (loc.getDirectionsAvailable().contains(Direction.NORTH)   // North South East WEST
              && loc.getDirectionsAvailable().contains(Direction.SOUTH)
              && loc.getDirectionsAvailable().contains(Direction.EAST)
              && loc.getDirectionsAvailable().contains(Direction.WEST)) {
        try {
          imageStream = getClass().getResourceAsStream("/images/NESW.png");
          img = ImageIO.read(imageStream);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      g.drawImage(img, 0, 0, 64 * 2, 64 * 2, null);

      if (loc.checkMonsterPresent()) {
        g.drawImage(monster, 15, 30, 100, 64, null);

      }

      if (loc.getTreasureChest().size() > 0) {
        List<Treasure> temp = new ArrayList<>(loc.getTreasureChest());
        if (temp.contains(Treasure.DIAMOND)) {
          g.drawImage(diamond, 16, 16, 32, 32, null);
        }
        if (temp.contains(Treasure.RUBY)) {
          g.drawImage(ruby, 16, 18, 32, 32, null);
        }
        if (temp.contains(Treasure.SAPPHIRE)) {
          g.drawImage(sapphire, 16, 20, 32, 32, null);
        }
      }


      if (loc.hasArrows()) {
        for (int i = 0; i < loc.getArrowsStored().size(); i++) {
          g.drawImage(arrow, 36, 34, null);
        }
      }

      if (loc.getId() == model.getPlayerLocation()) {
        this.visitedLocs.add(loc.getId());
        g.drawImage(player, 15, 30, 100, 64, null);

        if (loc.getSmellCount() == 2) {
          g.drawImage(strongSmell, 0, 0, 64 * 2, 64 * 2, null);
        }
        if (loc.getSmellCount() == 1) {
          g.drawImage(weakSmell, 0, 0, 64 * 2, 64 * 2, null);
        }
      }
      if (!this.visitedLocs.contains(loc.getId())) {
        g.drawImage(hide, 0, 0, 64 * 2, 64 * 2, null);
      }
      JLabel l = new JLabel();
      l.setIcon(new ImageIcon(new ImageIcon(combinedImage).getImage()
              .getScaledInstance(100, 100, BufferedImage.TYPE_INT_ARGB)));
      this.add(l);
      this.revalidate();
    }

  }

}




