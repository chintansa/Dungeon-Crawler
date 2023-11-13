package dungeon;

import randomnumgen.RandomNumGenerator;
import randomnumgen.RandomNumInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;




/**
 * Provides the method definitions to create a dungeon with its properties
 * and create a player and move around in the dungeon and collect the treasure.
 */
public class Game implements GameInterface {
  private final DungeonInterface dungeon;
  private final PlayerInterface player;
  private final RandomNumInterface r;
  private final int row;
  private final int column;
  private final List<LocationInterface> caves;
  private int monsterNum;
  private LocationInterface current;
  private boolean playerAlive;


  /**
   * Constructor to create the game.
   *
   * @param row                the rows in dungeon.
   * @param col                the columns in dungeon.
   * @param wrapping           to make dungeon connected through ends.
   * @param interconnectivity  to add more interconnecting edges.
   * @param treasurePercentage percentage of caves to have treasure.
   * @throws IllegalArgumentException for invalid values.
   */
  public Game(int row, int col, boolean wrapping, int interconnectivity,
              double treasurePercentage, int monsterNum) throws IllegalArgumentException {

    if (row < 5 || col < 5 || row > 100 || col > 100 || interconnectivity < 0) {
      throw new IllegalArgumentException("Invalid row, column or Interconnectivity Arguments"
              + " Keep row and col count between 5 to 100 and Interconnectivity more than 0");
    }

    if (treasurePercentage > 100 || treasurePercentage < 0) {
      throw new IllegalArgumentException("Invalid treasure percentage, keep it within 0 to 100");
    }

    if (monsterNum < 0) {
      throw new IllegalArgumentException("Invalid monster number entered, keep it 0 or more");
    }
    this.row = row;
    this.column = col;
    this.r = new RandomNumGenerator();

    this.dungeon = new Dungeon(row, col, wrapping, interconnectivity, treasurePercentage, this.r);

    this.player = new Player(this.dungeon.getStart());
    this.playerAlive = true;
    this.caves = this.dungeon.getAllCaveList();
    this.monsterNum = monsterNum;
    this.assignOtyughs();
    this.current = this.player.getCurrentLocation();
    this.getMonsterInfo(getPlayerLocation());
  }

  /**
   * Constructor for testing the game.
   *
   * @param row                the rows in dungeon.
   * @param col                the columns in dungeon.
   * @param wrapping           to make dungeon connected through ends.
   * @param interconnectivity  to add more interconnecting edges.
   * @param treasurePercentage percentage of caves to have treasure.
   * @param r                  random object.
   * @throws IllegalArgumentException for invalid values.
   */
  public Game(int row, int col, boolean wrapping, int interconnectivity,
              double treasurePercentage, int monsterNum, RandomNumInterface r) {

    if (row < 5 || col < 5 || interconnectivity < 0) {
      throw new IllegalArgumentException("Invalid arguments");
    }

    if (treasurePercentage > 100 || treasurePercentage < 0) {
      throw new IllegalArgumentException("Invalid treasure percentage, keep it within 0 to 100");
    }

    if (monsterNum < 0) {
      throw new IllegalArgumentException("Invalid monster number entered");
    }
    if (r == null) {
      throw new IllegalArgumentException("Invalid random object");
    }
    this.row = row;
    this.column = col;
    this.r = r;
    dungeon = new Dungeon(row, col, wrapping, interconnectivity, treasurePercentage, r);

    player = new Player(dungeon.getStart());
    this.playerAlive = true;

    this.caves = this.dungeon.getAllCaveList();

    this.monsterNum = monsterNum;
    this.assignOtyughs();
    this.current = this.player.getCurrentLocation();
    this.getMonsterInfo(getPlayerLocation());
  }

  @Override
  public int getRow() {
    return this.row;
  }

  @Override
  public int getColumn() {
    return this.column;
  }

  @Override
  public int getInterconnectivity() {
    return this.dungeon.getInterconnectivity();
  }

  @Override
  public int getMonsterNum() {
    return monsterNum;
  }

  @Override
  public int getTreasurePercentage() {
    return (int) this.dungeon.getTreasurePercentage();
  }

  @Override
  public boolean isWrapping() {
    return this.dungeon.isWrapping();
  }

  /**
   * Helper method to move player and arrow in the dungeon.
   *
   * @param d       direction to move.
   * @param current current location of the object.
   * @return id of the location that it moved to.
   */
  private int moveHelper(Direction d, LocationInterface current) {
    int id = current.getId();

    if (current.getDirectionsAvailable().contains(d)) {
      if (d == Direction.NORTH) {
        if (current.getX() == 0) {
          id = id + (this.row - 1) * this.column;
        } else {
          id = id - this.column;
        }
      } else if (d == Direction.SOUTH) {
        if (current.getX() == row - 1) {
          id = id - (this.row - 1) * this.column;
        } else {
          id = id + this.column;
        }
      } else if (d == Direction.EAST) {
        if (current.getY() == this.column - 1) {
          id = id - this.column + 1;
        } else {
          id = id + 1;
        }

      } else {
        if (current.getY() == 0) {
          id = id + this.column - 1;
        } else {
          id = id - 1;
        }
      }
    }
    return id;
  }


  @Override
  public String pickTreasure() {
    StringBuilder tre = new StringBuilder();
    if (checkTreasureAvailable()) {
      player.addTreasurePicked(dungeon.getLocationList().get(this.current.getId())
              .getTreasureChest());
      tre.append("Treasure Picked! at -> ").append(dungeon.getLocationList()
                      .get(this.current.getId()).getLocationType())
              .append(dungeon.getLocationList().get(this.current.getId()).getId()).append("->")
              .append(dungeon.getLocationList().get(this.current.getId()).getTreasureChest()
                      .toString()).append("\n");
      this.dungeon.getLocationList().get(this.current.getId()).clearTreasureChest();
    } else {
      tre.append("No treasure here\n");
    }

    return tre.toString();
  }

  @Override
  public String pickArrows() {
    int count = 0;
    for (Weapons a : dungeon.getLocationList().get(this.current.getId()).getArrowsStored()) {
      player.pickArrow(a);
      count++;
    }
    this.dungeon.getLocationList().get(this.current.getId()).clearArrowList();

    if (count > 0) {
      return count + " Arrows Picked\n";
    } else {
      return "No arrows here";
    }
  }


  private boolean checkTreasureAvailable() {
    return this.current.getLocationType() == LocationType.CAVE
            && dungeon.getLocationList().get(this.current.getId()).getTreasureChest().size() > 0;
  }


  private boolean checkArrowAvailable() {
    return dungeon.getLocationList().get(this.current.getId()).hasArrows();
  }

  private String printTreasureAvailable() {
    if (checkTreasureAvailable()) {
      return ("Treasure available here -> " + (dungeon.getLocationList().get(this.current.getId())
              .getTreasureChest()) + "\n");
    }
    return "";
  }

  private String printArrowsAvailable() {
    if (checkArrowAvailable()) {
      return dungeon.getLocationList()
              .get(this.current.getId()).getArrowsStored().size() + " Arrow available here\n";
    }
    return "";
  }

  /**
   * method to move the player.
   */
  private String move(Direction d) {
    StringBuilder mp = new StringBuilder();
    this.current = player.getCurrentLocation();

    if (d != null && current.getDirectionsAvailable().contains(d)) {
      int id = this.moveHelper(d, current);
      player.updateCurrentLocation(dungeon.getLocationList().get(id));
      mp.append("Player moved from: ").append(current.getLocationType()).append(current.getId())
              .append(" to: ").append(player.getCurrentLocation().getLocationType()).append(id)
              .append(", Direction moved -> ")
              .append(d)
              .append('\n');

      this.current = this.player.getCurrentLocation();
      this.getMonsterInfo(getPlayerLocation());
      if (this.canGameEnd()) {
        mp.append("\n----- Reached Exit ----- ").append("\n");
      }


      if (this.isMonsterPresent()) {
        if (this.canPlayerSurvive()) {
          mp.append("Encountered otyugh but the Lad survived, escaping..." + "\n");
        } else {
          this.playerAlive = false;
          throw new IllegalStateException("Chomp, chomp, chomp, you are eaten by an Otyugh!\n"
                  + "Better luck next time\n");
        }
      }

    } else {
      throw new IllegalArgumentException("Invalid move try again\n");
    }
    return mp.toString();
  }


  @Override
  public String movePlayer(String move) {
    Direction sendMove = null;
    if (move.equalsIgnoreCase("North")) {
      sendMove = Direction.NORTH;
    } else if (move.equalsIgnoreCase("South")) {
      sendMove = Direction.SOUTH;
    } else if (move.equalsIgnoreCase("West")) {
      sendMove = Direction.WEST;
    } else if (move.equalsIgnoreCase("East")) {
      sendMove = Direction.EAST;
    }
    try {
      return this.move(sendMove);
    } catch (IllegalArgumentException e) {
      return e.getMessage();
    }
  }

  @Override
  public String getPossiblePaths() {
    StringBuilder sb = new StringBuilder();
    List<Direction> turn = this.player.getCurrentLocation().getDirectionsAvailable();
    sb.append("Enter Direction from paths available :").append(turn.toString()).append('\n');

    return sb.toString();
  }

  @Override
  public boolean canGameEnd() {
    return (player.getCurrentLocation().getId() == dungeon.getEnd().getId());
  }


  @Override
  public String dungeonGrid() {
    return dungeon.dumpBetterDungeon();
  }

  @Override
  public String getPlayerStats() {
    return player.toString();
  }


  @Override
  public String getStart() {
    return "" + dungeon.getStart().getId();
  }

  @Override
  public String getEnd() {
    return "" + dungeon.getEnd().getId();
  }

  @Override
  public List<LocationInterface> getLocationList() {
    return new ArrayList<>(this.dungeon.getLocationList());
  }

  @Override
  public List<List<LocationInterface>> getAdjacencyList() {
    return new ArrayList<>(this.dungeon.getAdjacencyList());
  }


  /**
   * Method to assign otyughs to the caves in the dungeon.
   */
  private void assignOtyughs() {
    List<LocationInterface> monsterList = new ArrayList<>();
    for (LocationInterface l : caves) {
      if (l.getId() != this.dungeon.getEnd().getId() && l.getId()
              != this.dungeon.getStart().getId()) {
        monsterList.add(l);
      }
    }

    this.getLocationList().get(this.dungeon.getEnd().getId()).assignMonster();


    Collections.shuffle(monsterList, new Random(2));

    if (this.monsterNum >= monsterList.size()) {
      this.monsterNum = monsterList.size();
    }

    for (int i = 0; i < monsterNum - 1; i++) {
      monsterList.get(i).assignMonster();
    }

  }


  private boolean isMonsterPresent() {
    return dungeon.getLocationList().get(this.current.getId()).checkMonsterPresent();
  }


  private boolean canPlayerSurvive() {
    if (isMonsterPresent()) {
      LocationInterface temp = dungeon.getLocationList().get(this.current.getId());
      if (temp.isOtyughHalfDead()) {
        return this.r.getRandomNumber(2) == 1;
      } else {
        return false;
      }
    }
    return true;
  }


  private String getMonsterInfo(int current) {
    int nearSmell = 0;
    int farSmell = 0;
    List<List<LocationInterface>> tempAdj = this.getAdjacencyList();
    for (LocationInterface n : tempAdj.get(current)) {
      if (n.checkMonsterPresent()) {
        nearSmell += 1;
      } else {
        for (LocationInterface adjNode : tempAdj.get(n.getId())) {
          if (adjNode.checkMonsterPresent()) {
            farSmell += 1;
          }
        }
      }
    }
    if (nearSmell > 0 || farSmell > 1) {
      dungeon.getLocationList().get((player.getCurrentLocation().getId())).setSmellCount(2);
      return "You smell something terrible nearby !!\n";
    } else if (nearSmell == 0 && farSmell == 1) {
      dungeon.getLocationList().get((player.getCurrentLocation().getId())).setSmellCount(1);
      return "Something is lurking nearby !!\n";
    } else {
      dungeon.getLocationList().get((player.getCurrentLocation().getId())).setSmellCount(0);
      return "";
    }
  }


  @Override
  public String moveArrow(String direction, int distance) {
    boolean notHitWall = false;
    if (direction == null) {
      throw new IllegalArgumentException("Invalid direction");
    }

    if (distance < 1 || distance > 5) {
      throw new IllegalArgumentException("Invalid Distance");
    }

    Direction arrowDir;
    switch (direction.toLowerCase()) {
      case "north":
        arrowDir = Direction.NORTH;
        break;
      case "south":
        arrowDir = Direction.SOUTH;
        break;
      case "east":
        arrowDir = Direction.EAST;
        break;
      case "west":
        arrowDir = Direction.WEST;
        break;
      default:
        throw new IllegalArgumentException("Invalid Direction to shoot");
    }

    if (this.player.hasArrows()) {
      this.player.shootArrow();

      Direction tempDir = arrowDir;

      if (!this.player.getCurrentLocation().getDirectionsAvailable().contains(tempDir)) {
        throw new IllegalArgumentException("Shot an arrow into darkness\n");
      }

      int nextId = this.moveHelper(arrowDir, this.player.getCurrentLocation());
      Arrow arrow = new Arrow(nextId);
      if (this.dungeon.getLocationList().get(nextId).getLocationType() == LocationType.CAVE) {
        --distance;
      }

      LocationInterface arrowCurrent = this.dungeon.getLocationList().get(arrow.getArrowLocation());

      while (distance != 0) {

        if (arrowCurrent.getLocationType() == LocationType.CAVE) {
          if (arrowCurrent.getDirectionsAvailable().contains(tempDir)) {
            arrow.updateMovement(this.moveHelper(tempDir, arrowCurrent));
            arrowCurrent = this.dungeon.getLocationList().get(arrow.getArrowLocation());
            if (arrowCurrent.getLocationType() == LocationType.CAVE) {
              --distance;
            }
          } else {
            notHitWall = true;
            break;
          }

        }
        if (arrowCurrent.getLocationType() == LocationType.TUNNEL) {

          List<Direction> temp = arrowCurrent.getDirectionsAvailable();

          if (tempDir == Direction.NORTH) {
            tempDir = Direction.SOUTH;
          } else if (tempDir == Direction.SOUTH) {
            tempDir = Direction.NORTH;
          } else if (tempDir == Direction.WEST) {
            tempDir = Direction.EAST;
          } else {
            tempDir = Direction.WEST;
          }


          temp.remove(tempDir);


          tempDir = temp.get(temp.size() - 1);
          arrow.updateMovement(this.moveHelper(tempDir, arrowCurrent));
          arrowCurrent = this.dungeon.getLocationList().get(arrow.getArrowLocation());
          if (arrowCurrent.getLocationType() == LocationType.CAVE) {
            --distance;
          }
        }

      }

      if (!notHitWall) {
        LocationInterface hit = this.dungeon.getLocationList().get(arrow.getArrowLocation());
        if (hit.checkMonsterPresent()) {
          return (hit.hitMonster() + "\n");
        }
      }
      return "Shot an arrow into darkness\n";
    } else {
      return "Player has no arrows \n";
    }


  }

  @Override
  public String toString() {
    return "\n----  INFO! ----- \n"
            + "Player at -> " + player.getCurrentLocation().getLocationType()
            .toString().toLowerCase() + " "
            + player.getCurrentLocation().getId() + "\n"
            + this.printTreasureAvailable()
            + this.printArrowsAvailable()
            + "\n"
            + this.getMonsterInfo(player.getCurrentLocation().getId())
            + "\n"
            + this.getPossiblePaths()
            + "------------------\n";
  }


  @Override
  public int getPlayerLocation() {
    return this.player.getCurrentLocation().getId();
  }

  @Override
  public boolean isPlayerAlive() {
    return playerAlive;
  }

}
