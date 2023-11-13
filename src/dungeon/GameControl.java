package dungeon;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Represents the class which acts as the controller for the game.
 */
public class GameControl implements GameControlInterface {
  private final Appendable output;
  private final Scanner read;
  private final GameInterface game;

  /**
   * Constructor to create a controller.
   *
   * @param read   Readable param which takes in the input from the user.
   * @param output Appendable param which gives the suitable messages to the user.
   */
  public GameControl(Readable read, Appendable output, GameInterface game) {
    if (read == null || output == null || game == null) {
      throw new IllegalArgumentException("Invalid model passed");
    }

    this.read = new Scanner(read);
    this.output = output;
    this.game = game;
  }


  @Override
  public void startGame() {
    String action;
    int distance;
    try {
      output.append("\n----------------------------------------------------------\n");
      do {
        output.append(game.toString());
        output.append("Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n");
        action = this.read.next();
        if (action.equalsIgnoreCase("Q")) {                            //Quit method
          output.append("\nGame is quit, Player stat at exit is: ").append(game.getPlayerStats())
                  .append("\n");
          this.read.close();
          break;
        } else if (action.equalsIgnoreCase("I")) {       // get player stats method
          output.append(game.getPlayerStats()).append("\n");
        } else if (action.equalsIgnoreCase("M")) {               //Moving player method
          output.append("Where to ? -> ");
          action = read.next();
          try {

            output.append(game.movePlayer(action)).append("\n");
            if (game.canGameEnd()) {
              output.append("He survived to see another day!!!\n");
              this.read.close();
              break;
            }
          } catch (IllegalArgumentException e) {
            output.append(e.getMessage()).append("\n");
          } catch (IllegalStateException e) {
            output.append(e.getMessage()).append("\n");
            this.read.close();
            break;
          }

        } else if (action.equalsIgnoreCase("S")) {              //Shooting arrow method
          try {
            output.append("Enter direction to shoot -> ");
            action = this.read.next();
            output.append("Enter Distance Between 1 and 5").append("\n");
            distance = this.read.nextInt();
            output.append(game.moveArrow(action, distance));
          } catch (IllegalArgumentException | InputMismatchException e) {
            output.append(e.getMessage());
          }

        } else if (action.equalsIgnoreCase("P")) {           // Picking Treasure method

          output.append("Pick Treasure(T) or Arrow(A)").append("\n");
          action = this.read.next();
          if (action.equalsIgnoreCase("T")) {
            try {
              output.append(game.pickTreasure());
            } catch (IllegalArgumentException e) {
              output.append(e.getMessage());
            }
          } else if (action.equalsIgnoreCase("A")) {
            output.append(game.pickArrows()).append("\n");
          } else {
            output.append("Invalid try again");
          }
        } else {
          output.append("Invalid try again").append("\n");
        }
        output.append("\n----------------------------------------------------------\n");
      }
      while (!game.canGameEnd());
      output.append("\n----------------------------------------------------------\n");
      output.append("Game ended !!! \n");
      output.append("Player stats after exit: ").append(game.getPlayerStats()).append("\n");
    } catch (IOException e) {
      throw new IllegalStateException("Append failed", e);
    }

  }


}
