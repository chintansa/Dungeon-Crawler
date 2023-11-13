package test;

import org.junit.Before;
import org.junit.Test;

import dungeon.GameInterface;
import dungeon.GameSwingControl;

import static org.junit.Assert.assertEquals;

/**
 * Represents the testing of view in isolation and test its functionality.
 */
public class SwingViewTest {

  Appendable read;
  private MockSwingView view;
  private GameSwingControl controlInterface;

  @Before
  public void setUp() {
    this.read = new StringBuilder();
    view = new MockSwingView(read);
    GameInterface model = new MockGameModel(read);
    controlInterface = new GameSwingControl(model, view);
  }


  @Test
  public void testGameStats() {
    view.getGameStats("Player stats");
    assertEquals("Player stats"
            + "getStats method called", read.toString());
  }


  @Test
  public void testRefresh() {
    view.refresh();
    assertEquals("refresh method called", read.toString());
  }

  @Test
  public void testEndGame() {
    view.endGame();
    assertEquals("endGame called", read.toString());
  }

  @Test
  public void testActionListener() {
    view.setActionListeners(controlInterface, controlInterface);
    assertEquals("setActionListener method called", read.toString());
  }


}
