package driver;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;

import dungeon.Game;
import dungeon.GameControl;
import dungeon.GameDetails;
import dungeon.GameInterface;
import dungeon.GameSwingControl;
import dungeon.GameSwingControlInterface;
import dungeon.GameView;
import dungeon.GameViewInterface;

import java.io.InputStreamReader;


/**
 * Driver class to represent the movement of the player around the dungeon and
 * display all the treasure he's picked and the locations he has travelled before reaching the exit.
 */
public class Driver {

  /**
   * Main method of the driver to start the program.
   * If no arguments are given then text-based game is initiated else if 6 valid arguments
   * are given then view based game is initiated.
   * @param args contains the arguments to be used to run the game for text-based-game
   *             else view-based if empty.
   */
  public static void main(String[] args) {


    //Text based game logic
    if (args.length == 6) {
      if (!(args[2].equalsIgnoreCase("false")
              || args[2].equalsIgnoreCase("true"))) {
        throw new IllegalArgumentException("Illegal wrapping values enter true/false");
      }
      Readable input = new InputStreamReader(System.in);
      Appendable output = System.out;

      try {
        GameInterface game = new Game(parseInt(args[0]), parseInt(args[1]),
                parseBoolean(args[2]), parseInt(args[3]), parseInt(args[4]), parseInt(args[5]));
        new GameControl(input, output, game).startGame();

      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }

    } else if (args.length == 0) {
      try {

        GameDetails details = new GameDetails();

        GameInterface game = new Game(details.getRow(), details.getCol(), details.isWrapping(),
                details.getInterconnect(), details.getTreasure(), details.getOtyughCount());


        GameViewInterface view = new GameView(game);

        GameSwingControlInterface controlInterface = new GameSwingControl(game, view);
        controlInterface.startGame();

      } catch (IllegalArgumentException | NullPointerException e) {
        System.out.println("Invalid number of arguments are entered or program is closed");
        System.exit(0);
      }

    } else {
      throw new IllegalArgumentException("Invalid number of arguments are passed\n "
              + "Please enter a valid row, column, wrapping condition, interconnectivity value"
              + ",treasure percentage value and Otyugh number in order.");
    }


  }

}
