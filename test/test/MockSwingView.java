package test;

import java.awt.event.KeyListener;
import java.io.IOException;


import dungeon.GameSwingControlInterface;
import dungeon.GameViewInterface;

/**
 * Test to mock the swing in isolation and see if it is running as expected.
 */
public class MockSwingView implements GameViewInterface {
  private final Appendable actions;

  public MockSwingView(Appendable actions) {
    this.actions = actions;
  }

  @Override
  public void getGameStats(String stats) {
    try {
      this.actions.append(stats);
      this.actions.append("getStats method called");
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @Override
  public void refresh() {
    try {
      this.actions.append("refresh method called");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void endGame() {
    try {
      this.actions.append("endGame called");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void setActionListeners(GameSwingControlInterface listener, KeyListener key) {
    try {
      this.actions.append("setActionListener method called");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
