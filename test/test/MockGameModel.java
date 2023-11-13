package test;

import java.io.IOException;
import java.util.List;

import dungeon.GameInterface;
import dungeon.LocationInterface;

/**
 * A mock model to test the controller in isolation.
 */
public class MockGameModel implements GameInterface {
  private final Appendable appendable;

  public MockGameModel(Appendable appendable) {
    this.appendable = appendable;
  }

  @Override
  public int getRow() {
    try {
      appendable.append("GameInterface.getRows() method called");
    } catch (IOException e) {
      throw new AssertionError(e);
    }
    return 0;
  }

  @Override
  public int getColumn() {
    try {
      appendable.append("GameInterface.getColumns() method called");
    } catch (IOException e) {
      throw new AssertionError(e);
    }
    return 0;
  }

  @Override
  public int getInterconnectivity() {
    return 0;
  }

  @Override
  public int getMonsterNum() {
    return 0;
  }

  @Override
  public int getTreasurePercentage() {
    return 0;
  }

  @Override
  public boolean isWrapping() {
    return false;
  }

  @Override
  public String pickTreasure() {
    try {
      appendable.append("GameInterface.pickTreasure() method called");
    } catch (IOException e) {
      throw new AssertionError(e);
    }
    return "";
  }

  @Override
  public String pickArrows() {
    try {
      appendable.append("GameInterface.pickArrows() method called");
    } catch (IOException e) {
      throw new AssertionError(e);
    }
    return "";
  }

  @Override
  public String movePlayer(String move) {

    try {
      appendable.append("GameInterface.movePlayer() method called ").append(move).append("\n");
    } catch (IOException e) {
      throw new AssertionError(e);
    }
    return "";
  }

  @Override
  public String getPossiblePaths() {
    try {
      appendable.append("GameInterface.getPossiblePaths() method called\n");
    } catch (IOException e) {
      throw new AssertionError(e);
    }
    return "";
  }

  @Override
  public boolean canGameEnd() {
    try {
      appendable.append("GameInterface.canGameEnd() method called\n");
    } catch (IOException e) {
      throw new AssertionError(e);
    }
    return false;
  }

  @Override
  public String dungeonGrid() {
    try {
      appendable.append("GameInterface.dungeonGrid() method called\n");
    } catch (IOException e) {
      throw new AssertionError(e);
    }
    return "";
  }

  @Override
  public String getPlayerStats() {
    try {
      appendable.append("GameInterface.getPlayerStats() method called\n");
    } catch (IOException e) {
      throw new AssertionError(e);
    }
    return "";
  }

  @Override
  public String getStart() {
    try {
      appendable.append("GameInterface.canGameEnd() method called\n");
    } catch (IOException e) {
      throw new AssertionError(e);
    }
    return "";
  }

  @Override
  public String getEnd() {
    try {
      appendable.append("GameInterface.canGameEnd() method called\n");
    } catch (IOException e) {
      throw new AssertionError(e);
    }
    return "";
  }

  @Override
  public List<LocationInterface> getLocationList() {
    return null;
  }

  @Override
  public List<List<LocationInterface>> getAdjacencyList() {
    return null;
  }



  @Override
  public String moveArrow(String direction, int distance) {
    try {
      appendable.append("GameInterface.moveArrow() method called\n");
    } catch (IOException e) {
      throw new AssertionError(e);
    }
    return "";
  }

  @Override
  public int getPlayerLocation() {
    try {
      appendable.append("GameInterface.getPlayerLocation() method called\n");
    } catch (IOException e) {
      throw new AssertionError(e);
    }

    return 0;
  }

  @Override
  public boolean isPlayerAlive() {
    try {
      appendable.append("GameInterface.isPlayerAlive() method called\n");
    } catch (IOException e) {
      throw new AssertionError(e);
    }
    return false;
  }
}
