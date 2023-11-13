package test;

import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;


import dungeon.GameControl;
import dungeon.GameControlInterface;
import dungeon.GameInterface;

import static org.junit.Assert.assertTrue;

/**
 * Testing the controller with the mock model to check if the right methods are being called to
 * maintain the flow of the program.
 */
public class TestMockModel {
  private GameInterface mockModel;
  private GameControlInterface controller;

  private Readable read;
  private Appendable out;

  @Before
  public void setup() {
    out = new StringBuilder();
    mockModel = new MockGameModel(out);

  }

  @Test
  public void testPickTreasure() {
    read = new StringReader("P T q");
    controller = new GameControl(read,out,mockModel);
    controller.startGame();
    String test = "\n"
            +
            "----------------------------------------------------------\n"
            +
            "test.MockGameModel@4fb64261"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Pick Treasure(T) or Arrow(A)\n"
            +
            "GameInterface.pickTreasure() method called\n"
            +
            "----------------------------------------------------------\n"
            +
            "GameInterface.canGameEnd() method called\n"
            +
            "test.MockGameModel@4fb64261"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "\n"
            +
            "Game is quit, Player stat at exit is: GameInterface.getPlayerStats() method called\n"
            +
            "\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "Game ended !!! \n"
            +
            "Player stats after exit: GameInterface.getPlayerStats() method called\n"
            +
            "\n";

    assertTrue(out.toString().contains("GameInterface.pickTreasure() method called"));
  }



  @Test
  public void testPickArrow() {
    read = new StringReader("P A q");
    controller = new GameControl(read,out,mockModel);
    controller.startGame();

    String test = "\n"
            +
            "----------------------------------------------------------\n"
            +
            "test.MockGameModel@4fb6426"
            +
            "1Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Pick Treasure(T) or Arrow(A)\n"
            +
            "GameInterface.pickArrows() method called\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "GameInterface.canGameEnd() method called\n"
            +
            "test.MockGameModel@4fb64261Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "\n"
            +
            "Game is quit, Player stat at exit is: GameInterface.getPlayerStats() method called\n"
            +
            "\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "Game ended !!! \n"
            +
            "Player stats after exit: GameInterface.getPlayerStats() method called\n"
            +
            "\n";
    assertTrue(out.toString().contains("GameInterface.pickArrows() method called"));
  }






  @Test
  public void testMovePlayer() {
    read = new StringReader("M west q");
    controller = new GameControl(read,out,mockModel);
    controller.startGame();

    String test = "\n"
            +
            "----------------------------------------------------------\n"
            +
            "test.MockGameModel@782663d3"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Where to ? -> GameInterface.movePlayer() method called west\n"
            +
            "\n"
            +
            "GameInterface.canGameEnd() method called\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "GameInterface.canGameEnd() method called\n"
            +
            "test.MockGameModel@782663d3"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "\n"
            +
            "Game is quit, Player stat at exit is: GameInterface.getPlayerStats() method called\n"
            +
            "\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "Game ended !!! \n"
            +
            "Player stats after exit: GameInterface.getPlayerStats() method called\n"
            +
            "\n";
    assertTrue(out.toString().contains("Where to ? -> GameInterface.movePlayer() method"
            + " called west"));

  }


  @Test
  public void testMoveArrow() {

    read = new StringReader("S west 1 q");
    controller = new GameControl(read,out,mockModel);
    controller.startGame();

    String test = "\n"
            +
            "----------------------------------------------------------\n"
            +
            "test.MockGameModel@782663d3"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Enter direction to shoot -> Enter Distance Between 1 and 5\n"
            +
            "GameInterface.moveArrow() method called\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "GameInterface.canGameEnd() method called\n"
            +
            "test.MockGameModel@782663d3"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "\n"
            +
            "Game is quit, Player stat at exit is: GameInterface.getPlayerStats() method called\n"
            +
            "\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "Game ended !!! \n"
            +
            "Player stats after exit: GameInterface.getPlayerStats() method called\n"
            +
            "\n";
    assertTrue(out.toString().contains("GameInterface.moveArrow() method called"));


  }


}



