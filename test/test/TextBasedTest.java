package test;

import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import dungeon.Game;
import dungeon.GameControl;
import dungeon.GameControlInterface;
import dungeon.GameInterface;
import dungeon.LocationInterface;
import dungeon.LocationType;
import randomnumgen.RandomNumInterface;
import randomnumgen.RandomNumTesting;
import randomnumgen.RandomNumTestingTwo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * JUnit test class for testing GameControl, Game, Dungeon and Player functionalities.
 */
public class TextBasedTest {
  private RandomNumInterface rand;
  private GameInterface gameTestWrap;
  private GameInterface gameTestNonWrap;
  private GameInterface gameTestNonWrapNoInter;
  private GameInterface gameTestWrapNoInter;
  private String wrap;
  private String wrapNoInter;
  private String nonWrap;
  private String nonWrapNoInter;
  private List<LocationInterface> cavelist;

  private Readable read;
  private Appendable out;

  @Before
  public void setUp() {
    this.rand = new RandomNumTesting();

    wrap =    "  |      |      |      |      |    \n"
            + "  T      1      2      3      4    \n"
            + "  |                                \n"
            + "\n"
            + "  |                                \n"
            + "- 5      6      7      8      T  - \n"
            + "  |      |      |      |      |    \n"
            + "\n"
            + "  |      |      |      |      |    \n"
            + "- T      T      T      T      14 - \n"
            + "         |      |      |      |    \n"
            + "\n"
            + "         |      |      |      |    \n"
            + "- 15     T      T      T      19 - \n"
            + "         |      |      |      |    \n"
            + "\n"
            + "         |      |      |      |    \n"
            + "- T      21 - - 22 - - 23 - - 24 - \n"
            + "  |      |      |      |      |    \n"
            + "\n";


    wrapNoInter = "  |      |      |      |      |    \n"
                  +
                  "  0      1      2      3      4    \n"
                  +
                  "                                   \n"
                  +
                  "\n"
                  +
                  "                                   \n"
                  +
                  "- 5      6      7      8      T  - \n"
                  +
                  "         |      |      |      |    \n"
                  +
                  "\n"
                  +
                  "         |      |      |      |    \n"
                  +
                  "- 10     T      T      T      14 - \n"
                  +
                  "         |      |      |      |    \n"
                  +
                  "\n"
                  +
                  "         |      |      |      |    \n"
                  +
                  "- 15     T      T      T      19 - \n"
                  +
                  "         |      |      |      |    \n"
                  +
                  "\n"
                  +
                  "         |      |      |      |    \n"
                  +
                  "- T      21 - - 22 - - 23 - - 24 - \n"
                  +
                  "  |      |      |      |      |    \n"
                  +
                  "\n";


    nonWrap = "                                   \n"
            + "  0      1      2      3      4    \n"
            + "  |      |      |      |      |    \n"
            + "\n"
            + "  |      |      |      |      |    \n"
            + "  T      T      T      T      T    \n"
            + "  |      |      |      |      |    \n"
            + "\n"
            + "  |      |      |      |      |    \n"
            + "  T      T      T      T      T    \n"
            + "  |      |      |      |      |    \n"
            + "\n"
            + "  |      |      |      |      |    \n"
            + "  T      T      17 - - 18 - - 19   \n"
            + "  |      |      |      |      |    \n"
            + "\n"
            + "  |      |      |      |      |    \n"
            + "  T  - - 21 - - 22 - - 23 - - T    \n"
            + "                                   \n"
            + "\n";


    nonWrapNoInter = "                                   \n"
            + "  0      1      2      3      4    \n"
            + "  |      |      |      |      |    \n"
            + "\n"
            + "  |      |      |      |      |    \n"
            + "  T      T      T      T      T    \n"
            + "  |      |      |      |      |    \n"
            + "\n"
            + "  |      |      |      |      |    \n"
            + "  T      T      T      T      T    \n"
            + "  |      |      |      |      |    \n"
            + "\n"
            + "  |      |      |      |      |    \n"
            + "  T      T      T      T      T    \n"
            + "  |      |      |      |      |    \n"
            + "\n"
            + "  |      |      |      |      |    \n"
            + "  T  - - 21 - - 22 - - 23 - - T    \n"
            + "                                   \n"
            + "\n";

    gameTestWrap = new Game(5, 5, true, 2, 50,
            6, rand);
    gameTestWrapNoInter = new Game(5, 5, true, 0,
            50, 6, rand);
    gameTestNonWrap = new Game(5, 5, false, 2,
            100, 6, rand);
    gameTestNonWrapNoInter = new Game(5, 5, false, 0,
            100, 6, rand);

    this.cavelist = new ArrayList<>();

  }


  @Test(expected = IllegalStateException.class)
  public void testFailingAppendable() {
    // Testing when something goes wrong with the Appendable
    // Here we are passing it a mock of an Appendable that always fails
    StringReader input = new StringReader("M north S south 3 P T q");
    Appendable gameLog = new FailingAppendable();
    GameControlInterface c = new GameControl(input, gameLog,gameTestWrap);
    c.startGame();
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidRow() {
    new Game(-5, 5, false, 7, 100, 7);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidColumn() {
    new Game(5, -5, false, 7, 100, 7);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidInterconnectivity() {
    new Game(5, 5, false, -7, 100, 7);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidTreasurePercent() {
    new Game(5, 5, false, 7, -100, 7);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidMonsterNum() {
    new Game(5, 5, false, 7, 100, -7);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidModelPassed() {
    this.read = new StringReader("M west M west M north M north M north");
    this.out = new StringBuilder();
    GameControlInterface game = new GameControl(this.read, this.out,null);
    game.startGame();

  }

  //-----------------------------------------------------------------------
  @Test
  public void setRandomTest() {
    int randomNumber = this.rand.getRandomNumber(4);
    int randomNumber2 = this.rand.getRandomNumber(7);
    assertEquals(3, randomNumber);
    assertEquals(6, randomNumber2);

  }

  @Test
  public void createWrapDungeonTest() {
    assertEquals(wrap, gameTestWrap.dungeonGrid());
    assertEquals(wrapNoInter, gameTestWrapNoInter.dungeonGrid());

  }

  @Test
  public void createNonWrapDungeonTest() {

    assertEquals(nonWrap, gameTestNonWrap.dungeonGrid());
    assertEquals(nonWrapNoInter, gameTestNonWrapNoInter.dungeonGrid());
  }

  @Test
  public void testInterconnectivityWrapping() {
    assertNotEquals(wrap, wrapNoInter);
  }

  @Test
  public void testInterconnectivityNonWrapping() {
    assertNotEquals(nonWrap, nonWrapNoInter);
  }



  @Test
  public void testNotAllCavesWrapNoInter() {
    assertNotEquals( "  |      |      |      |      |    \n"
                              + "- T      1      2      3      T  - \n"
                              + "                                   \n"
                              + "\n"
                              + "                                   \n"
                              + "- 5      6      7      8      T  - \n"
                              + "         |      |      |      |    \n"
                              + "\n"
                              + "         |      |      |      |    \n"
                              + "- 10     T      T      T      14 - \n"
                              + "         |      |      |      |    \n"
                              + "\n"
                              + "         |      |      |      |    \n"
                              + "- 15     T      T      T      19 - \n"
                              + "         |      |      |      |    \n"
                              + "\n"
                              + "         |      |      |      |    \n"
                              + "- T      21 - - 22 - - 23 - - 24 - \n"
                              + "  |      |      |      |      |    \n"
                              + "\n", gameTestWrapNoInter.dungeonGrid());
  }


  @Test
  public void testTreasurePercentageInCaves() {
    for (LocationInterface l : gameTestWrap.getLocationList()) {
      if (l.getLocationType() == LocationType.CAVE) {
        cavelist.add(l);
      }
    }

    assertEquals(15, cavelist.size());
    int treasureCount = 0;

    int caveLimit = (int) Math.round(cavelist.size() * (50.0 / 100));
    int expectedCaveLimit = (int) Math.round(15 * (50.0 / 100));
    assertEquals(expectedCaveLimit, caveLimit);

    for (LocationInterface i : cavelist) {
      if (i.getTreasureChest().size() > 0) {
        treasureCount++;
      }
    }

    assertEquals(treasureCount, caveLimit);

  }

  @Test
  public void startEndLocationTest() {
    assertEquals("23", gameTestWrap.getStart());
    assertEquals("6", gameTestWrap.getEnd());

    assertEquals("1", gameTestNonWrap.getStart());
    assertEquals("2", gameTestNonWrap.getEnd());
  }


  @Test
  public void checkPlayerAtStartAndThreeArrows() {
    assertEquals("23", gameTestWrap.getStart());
    assertEquals("Player at Location = 23, Arrows left 3, "
            + "Collected Treasure = Empty", gameTestWrap.getPlayerStats());
  }

  @Test
  public void checkMonsterAtEndAndNotInStart() {
    assertFalse(gameTestWrap.getLocationList()
            .get(Integer.parseInt(gameTestWrap.getStart())).checkMonsterPresent());
    assertTrue(gameTestWrap.getLocationList()
            .get(Integer.parseInt(gameTestWrap.getEnd())).checkMonsterPresent());
  }

  @Test
  public void testMonsterOnlyInCavesNotInTunnel() {
    int tunnel = 0;
    int cave = 0;
    for (LocationInterface l : gameTestWrap.getLocationList()) {
      if (l.getLocationType() == LocationType.CAVE && l.checkMonsterPresent()) {
        cave++;
      }
      if (l.getLocationType() == LocationType.TUNNEL && l.checkMonsterPresent()) {
        tunnel++;
      }
    }

    assertEquals(0, tunnel);
    assertEquals(6, cave);
  }

  @Test
  public void locationDescriptionWithMonsters() {
    GameInterface testGame = new Game(5, 5, true, 8,
            100, 6, this.rand);

    for (LocationInterface l : testGame.getLocationList()) {
      if (l.getLocationType() == LocationType.CAVE) {
        cavelist.add(l);
      }
    }

    StringBuilder sb = new StringBuilder();

    assertEquals("21", testGame.getEnd());
    assertEquals("9", testGame.getStart());
    for (LocationInterface c :
            cavelist) {
      sb.append(c.locationMap()).append('\n');
    }

    assertEquals("Loc id=5, Paths=[WEST, NORTH, SOUTH], Type=CAVE, "
            +
            "Treasure = [SAPPHIRE, SAPPHIRE, SAPPHIRE], Monster present true Arrow count-> 2\n"
            +
            "Loc id=9, Paths=[EAST, SOUTH, NORTH], Type=CAVE, Treasure = [SAPPHIRE, SAPPHIRE, "
            +
            "SAPPHIRE],  Arrow count-> 2\n"
            +
            "Loc id=10, Paths=[WEST, NORTH, SOUTH], Type=CAVE, Treasure = [SAPPHIRE, SAPPHIRE, "
            +
            "SAPPHIRE],  Arrow count-> 2\n"
            +
            "Loc id=14, Paths=[EAST, SOUTH, NORTH], Type=CAVE, Treasure = [SAPPHIRE, SAPPHIRE, "
            +
            "SAPPHIRE], Monster present true Arrow count-> 2\n"
            +
            "Loc id=15, Paths=[WEST, NORTH, SOUTH], Type=CAVE, Treasure = [SAPPHIRE, SAPPHIRE, "
            +
            "SAPPHIRE],  Arrow count-> 2\n"
            +
            "Loc id=19, Paths=[EAST, SOUTH, NORTH], Type=CAVE, Treasure = [SAPPHIRE, SAPPHIRE, "
            +
            "SAPPHIRE], Monster present true Arrow count-> 2\n"
            +
            "Loc id=20, Paths=[SOUTH, WEST, NORTH], Type=CAVE, Treasure = [SAPPHIRE, SAPPHIRE, "
            +
            "SAPPHIRE], Monster present true Arrow count-> 2\n"
            +
            "Loc id=21, Paths=[SOUTH, NORTH, EAST], Type=CAVE, Treasure = [SAPPHIRE, SAPPHIRE, "
            +
            "SAPPHIRE], Monster present true Arrow count-> 2\n"
            +
            "Loc id=22, Paths=[SOUTH, NORTH, EAST, WEST], Type=CAVE, Treasure = [SAPPHIRE, "
            +
            "SAPPHIRE, SAPPHIRE],  Arrow count-> 2\n"
            +
            "Loc id=23, Paths=[SOUTH, NORTH, EAST, WEST], Type=CAVE, Treasure = [SAPPHIRE, "
            +
            "SAPPHIRE, SAPPHIRE], Monster present true Arrow count-> 2\n"
            +
            "Loc id=24, Paths=[SOUTH, EAST, NORTH, WEST], Type=CAVE, Treasure = [SAPPHIRE, "
            +
            "SAPPHIRE, SAPPHIRE],  Arrow count-> 2\n", sb.toString());

    StringBuilder sbb = new StringBuilder();

    for (LocationInterface c :
            cavelist) {
      c.hitMonster();
      c.hitMonster();
      sbb.append(c.locationMap()).append('\n');
    }


    assertEquals("Loc id=5, Paths=[WEST, NORTH, SOUTH], Type=CAVE, Treasure "
            +
            "= [SAPPHIRE, SAPPHIRE, SAPPHIRE],  Arrow count-> 2\n"
            +
            "Loc id=9, Paths=[EAST, SOUTH, NORTH], Type=CAVE, Treasure = [SAPPHIRE, "
            +
            "SAPPHIRE, SAPPHIRE],  Arrow count-> 2\n"
            +
            "Loc id=10, Paths=[WEST, NORTH, SOUTH], Type=CAVE, Treasure = [SAPPHIRE, "
            +
            "SAPPHIRE, SAPPHIRE],  Arrow count-> 2\n"
            +
            "Loc id=14, Paths=[EAST, SOUTH, NORTH], Type=CAVE, Treasure = [SAPPHIRE, "
            +
            "SAPPHIRE, SAPPHIRE],  Arrow count-> 2\n"
            +
            "Loc id=15, Paths=[WEST, NORTH, SOUTH], Type=CAVE, Treasure = [SAPPHIRE, "
            +
            "SAPPHIRE, SAPPHIRE],  Arrow count-> 2\n"
            +
            "Loc id=19, Paths=[EAST, SOUTH, NORTH], Type=CAVE, Treasure = [SAPPHIRE, "
            +
            "SAPPHIRE, SAPPHIRE],  Arrow count-> 2\n"
            +
            "Loc id=20, Paths=[SOUTH, WEST, NORTH], Type=CAVE, Treasure = [SAPPHIRE, "
            +
            "SAPPHIRE, SAPPHIRE],  Arrow count-> 2\n"
            +
            "Loc id=21, Paths=[SOUTH, NORTH, EAST], Type=CAVE, Treasure = [SAPPHIRE, "
            +
            "SAPPHIRE, SAPPHIRE],  Arrow count-> 2\n"
            +
            "Loc id=22, Paths=[SOUTH, NORTH, EAST, WEST], Type=CAVE, Treasure = "
            +
            "[SAPPHIRE, SAPPHIRE, SAPPHIRE],  Arrow count-> 2\n"
            +
            "Loc id=23, Paths=[SOUTH, NORTH, EAST, WEST], Type=CAVE, Treasure = "
            +
            "[SAPPHIRE, SAPPHIRE, SAPPHIRE],  Arrow count-> 2\n"
            +
            "Loc id=24, Paths=[SOUTH, EAST, NORTH, WEST], Type=CAVE, Treasure = ["
            +
            "SAPPHIRE, SAPPHIRE, SAPPHIRE],  Arrow count-> 2\n", sbb.toString());


    cavelist.clear();
    StringBuilder tt = new StringBuilder();
    for (LocationInterface l : gameTestWrap.getLocationList()) {
      if (l.getLocationType() == LocationType.CAVE) {
        cavelist.add(l);
        tt.append(l.locationMap()).append('\n');
      }
    }


    assertEquals("23", gameTestWrap.getStart());
    assertEquals("6", gameTestWrap.getEnd());


    assertEquals("Loc id=1, Paths=[NORTH], Type=CAVE,  Arrow count-> 2\n"
            +
            "Loc id=2, Paths=[NORTH], Type=CAVE, Treasure = [SAPPHIRE, SAPPHIRE, SAPPHIRE],  "
            +
            "Arrow count-> 2\n"
            +
            "Loc id=3, Paths=[NORTH], Type=CAVE, Treasure = [SAPPHIRE, SAPPHIRE, SAPPHIRE], "
            +
            "Monster present true Arrow count-> 2\n"
            +
            "Loc id=4, Paths=[NORTH], Type=CAVE, Treasure = [SAPPHIRE, SAPPHIRE, SAPPHIRE], "
            +
            "Monster present true\n"
            +
            "Loc id=5, Paths=[WEST, NORTH, SOUTH], Type=CAVE, Treasure = [SAPPHIRE, SAPPHIRE, "
            +
            "SAPPHIRE], Monster present true Arrow count-> 2\n"
            +
            "Loc id=6, Paths=[SOUTH], Type=CAVE, Treasure = [SAPPHIRE, SAPPHIRE, SAPPHIRE], "
            +
            "Monster present true Arrow count-> 2\n"
            +
            "Loc id=7, Paths=[SOUTH], Type=CAVE,  Arrow count-> 2\n"
            +
            "Loc id=8, Paths=[SOUTH], Type=CAVE, Monster present true\n"
            +
            "Loc id=14, Paths=[EAST, SOUTH, NORTH], Type=CAVE, Monster present true\n"
            +
            "Loc id=15, Paths=[WEST], Type=CAVE, \n"
            +
            "Loc id=19, Paths=[EAST, SOUTH, NORTH], Type=CAVE, Treasure = [SAPPHIRE, SAPPHIRE,"
            +
            " SAPPHIRE], \n"
            +
            "Loc id=21, Paths=[SOUTH, NORTH, EAST], Type=CAVE, Treasure = [SAPPHIRE, SAPPHIRE, "
            +
            "SAPPHIRE],  Arrow count-> 2\n"
            +
            "Loc id=22, Paths=[SOUTH, NORTH, EAST, WEST], Type=CAVE, \n"
            +
            "Loc id=23, Paths=[SOUTH, NORTH, EAST, WEST], Type=CAVE, \n"
            +
            "Loc id=24, Paths=[SOUTH, EAST, NORTH, WEST], Type=CAVE, Treasure = [SAPPHIRE, "
            +
            "SAPPHIRE, SAPPHIRE],  Arrow count-> 2\n", tt.toString());


  }

  @Test
  public void checkArrowsAssigned() {
    StringBuilder sb = new StringBuilder();
    int arrowCount = 0;
    int cave = 0;
    int tunnel = 0;
    for (LocationInterface l : gameTestWrap.getLocationList()) {
      sb.append(l.locationMap()).append("\n");
      if (l.hasArrows()) {
        arrowCount++;
        if (l.getLocationType() == LocationType.TUNNEL) {
          tunnel++;
        }
        if (l.getLocationType() == LocationType.CAVE) {
          cave++;
        }
      }

    }

    assertEquals(5, tunnel);
    assertEquals(8, cave);


    int locLimit = (int) Math.round(gameTestWrap.getLocationList().size() * (50.0 / 100));
    int expectedLimit = (int) Math.round(25 * (50.0 / 100));

    assertEquals(expectedLimit, locLimit);
    assertEquals(arrowCount, locLimit);

    assertEquals("Loc id=0, Paths=[NORTH, SOUTH], Type=TUNNEL,  Arrow count-> 2\n"
            +
            "Loc id=1, Paths=[NORTH], Type=CAVE,  Arrow count-> 2\n"
            +
            "Loc id=2, Paths=[NORTH], Type=CAVE, Treasure = [SAPPHIRE, SAPPHIRE, SAPPHIRE], "
            +
            " Arrow count-> 2\n"
            +
            "Loc id=3, Paths=[NORTH], Type=CAVE, Treasure = [SAPPHIRE, SAPPHIRE, SAPPHIRE], "
            +
            "Monster present true Arrow count-> 2\n"
            +
            "Loc id=4, Paths=[NORTH], Type=CAVE, Treasure = [SAPPHIRE, SAPPHIRE, SAPPHIRE], "
            +
            "Monster present true\n"
            +
            "Loc id=5, Paths=[WEST, NORTH, SOUTH], Type=CAVE, Treasure = [SAPPHIRE, SAPPHIRE, "
            +
            "SAPPHIRE], Monster present true Arrow count-> 2\n"
            +
            "Loc id=6, Paths=[SOUTH], Type=CAVE, Treasure = [SAPPHIRE, SAPPHIRE, SAPPHIRE],"
            +
            " Monster present true Arrow count-> 2\n"
            +
            "Loc id=7, Paths=[SOUTH], Type=CAVE,  Arrow count-> 2\n"
            +
            "Loc id=8, Paths=[SOUTH], Type=CAVE, Monster present true\n"
            +
            "Loc id=9, Paths=[EAST, SOUTH], Type=TUNNEL, \n"
            +
            "Loc id=10, Paths=[WEST, NORTH], Type=TUNNEL, \n"
            +
            "Loc id=11, Paths=[SOUTH, NORTH], Type=TUNNEL, \n"
            +
            "Loc id=12, Paths=[SOUTH, NORTH], Type=TUNNEL, \n"
            +
            "Loc id=13, Paths=[SOUTH, NORTH], Type=TUNNEL,  Arrow count-> 2\n"
            +
            "Loc id=14, Paths=[EAST, SOUTH, NORTH], Type=CAVE, Monster present true\n"
            +
            "Loc id=15, Paths=[WEST], Type=CAVE, \n"
            +
            "Loc id=16, Paths=[SOUTH, NORTH], Type=TUNNEL,  Arrow count-> 2\n"
            +
            "Loc id=17, Paths=[SOUTH, NORTH], Type=TUNNEL,  Arrow count-> 2\n"
            +
            "Loc id=18, Paths=[SOUTH, NORTH], Type=TUNNEL, \n"
            +
            "Loc id=19, Paths=[EAST, SOUTH, NORTH], Type=CAVE, Treasure = [SAPPHIRE, SAPPHIRE,"
            +
            " SAPPHIRE], \n"
            +
            "Loc id=20, Paths=[SOUTH, WEST], Type=TUNNEL,  Arrow count-> 2\n"
            +
            "Loc id=21, Paths=[SOUTH, NORTH, EAST], Type=CAVE, Treasure = [SAPPHIRE, SAPPHIRE,"
            +
            " SAPPHIRE],  Arrow count-> 2\n"
            +
            "Loc id=22, Paths=[SOUTH, NORTH, EAST, WEST], Type=CAVE, \n"
            +
            "Loc id=23, Paths=[SOUTH, NORTH, EAST, WEST], Type=CAVE, \n"
            +
            "Loc id=24, Paths=[SOUTH, EAST, NORTH, WEST], Type=CAVE, Treasure = [SAPPHIRE, "
            +
            "SAPPHIRE, SAPPHIRE],  Arrow count-> 2\n", sb.toString());
  }


  @Test
  public void testPlayerMovementInWrappingReachingEnd() {
    assertEquals("23", gameTestWrap.getStart());
    assertEquals("6", gameTestWrap.getEnd());

    this.read = new StringReader("M west S west 1 M west M west M north M "
            + "north S north 1 S north 1 M north");
    this.out = new StringBuilder();
    GameControlInterface game = new GameControl(this.read, this.out,gameTestWrap);
    game.startGame();

    assertEquals("\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> cave 23\n"
            +
            "\n"
            +
            "You smell something terrible nearby !!\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[SOUTH, NORTH, EAST, WEST]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Where to ? -> Player moved from: CAVE23 to: CAVE22, Direction moved -> WEST\n"
            +
            "\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> cave 22\n"
            +
            "\n"
            +
            "Something is lurking nearby !!\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[SOUTH, NORTH, EAST, WEST]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Enter direction to shoot -> Enter Distance Between 1 and 5\n"
            +
            "Shot an arrow into darkness\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> cave 22\n"
            +
            "\n"
            +
            "Something is lurking nearby !!\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[SOUTH, NORTH, EAST, WEST]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Where to ? -> Player moved from: CAVE22 to: CAVE21, Direction moved -> WEST\n"
            +
            "\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> cave 21\n"
            +
            "Treasure available here -> [SAPPHIRE, SAPPHIRE, SAPPHIRE]\n"
            +
            "2 Arrow available here\n"
            +
            "\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[SOUTH, NORTH, EAST]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Where to ? -> Invalid move try again\n"
            +
            "\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> cave 21\n"
            +
            "Treasure available here -> [SAPPHIRE, SAPPHIRE, SAPPHIRE]\n"
            +
            "2 Arrow available here\n"
            +
            "\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[SOUTH, NORTH, EAST]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Where to ? -> Player moved from: CAVE21 to: TUNNEL16, Direction moved -> NORTH\n"
            +
            "\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> tunnel 16\n"
            +
            "2 Arrow available here\n"
            +
            "\n"
            +
            "Something is lurking nearby !!\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[SOUTH, NORTH]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Where to ? -> Player moved from: TUNNEL16 to: TUNNEL11, Direction moved -> NORTH\n"
            +
            "\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> tunnel 11\n"
            +
            "\n"
            +
            "You smell something terrible nearby !!\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[SOUTH, NORTH]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Enter direction to shoot -> Enter Distance Between 1 and 5\n"
            +
            "You hear a great howl in the distance\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> tunnel 11\n"
            +
            "\n"
            +
            "You smell something terrible nearby !!\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[SOUTH, NORTH]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Enter direction to shoot -> Enter Distance Between 1 and 5\n"
            +
            "You hear a great howl in the distance\n"
            +
            "You killed the otyugh!!! \n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> tunnel 11\n"
            +
            "\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[SOUTH, NORTH]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Where to ? -> Player moved from: TUNNEL11 to: CAVE6, Direction moved -> NORTH\n"
            +
            "\n"
            +
            "----- Reached Exit ----- \n"
            +
            "\n"
            +
            "He survived to see another day!!!\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "Game ended !!! \n"
            +
            "Player stats after exit: Player at Location = 6, Arrows left 0, "
            +
            "Collected Treasure = Empty\n", this.out.toString());
  }


  @Test
  public void testPlayerMovementInWrappingReachingEndButDies() {
    assertEquals("23", gameTestWrap.getStart());
    assertEquals("6", gameTestWrap.getEnd());

    this.read = new StringReader("M west M west M north M north M north");
    this.out = new StringBuilder();
    GameControlInterface game = new GameControl(this.read, this.out,gameTestWrap);
    game.startGame();

    assertEquals("\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> cave 23\n"
            +
            "\n"
            +
            "You smell something terrible nearby !!\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[SOUTH, NORTH, EAST, WEST]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Where to ? -> Player moved from: CAVE23 to: CAVE22, Direction moved -> WEST\n"
            +
            "\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> cave 22\n"
            +
            "\n"
            +
            "Something is lurking nearby !!\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[SOUTH, NORTH, EAST, WEST]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Where to ? -> Player moved from: CAVE22 to: CAVE21, Direction moved -> WEST\n"
            +
            "\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> cave 21\n"
            +
            "Treasure available here -> [SAPPHIRE, SAPPHIRE, SAPPHIRE]\n"
            +
            "2 Arrow available here\n"
            +
            "\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[SOUTH, NORTH, EAST]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Where to ? -> Player moved from: CAVE21 to: TUNNEL16, Direction moved -> NORTH\n"
            +
            "\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> tunnel 16\n"
            +
            "2 Arrow available here\n"
            +
            "\n"
            +
            "Something is lurking nearby !!\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[SOUTH, NORTH]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Where to ? -> Player moved from: TUNNEL16 to: TUNNEL11, Direction moved -> NORTH\n"
            +
            "\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> tunnel 11\n"
            +
            "\n"
            +
            "You smell something terrible nearby !!\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[SOUTH, NORTH]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Where to ? -> Chomp, chomp, chomp, you are eaten by an Otyugh!\n"
            +
            "Better luck next time\n"
            +
            "\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "Game ended !!! \n"
            +
            "Player stats after exit: Player at Location = 6, Arrows left 3, "
            +
            "Collected Treasure = Empty\n", this.out.toString());
  }

  @Test
  public void testPlayerMovementInWrappingReachingEndSurvivalCheckWins() {
    assertEquals("23", gameTestWrap.getStart());
    assertEquals("6", gameTestWrap.getEnd());

    this.read = new StringReader("M west M west M west M north M north S north 1 M north");
    this.out = new StringBuilder();
    GameControlInterface game = new GameControl(this.read, this.out,gameTestWrap);
    game.startGame();

    assertEquals("\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> cave 23\n"
            +
            "\n"
            +
            "You smell something terrible nearby !!\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[SOUTH, NORTH, EAST, WEST]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Where to ? -> Player moved from: CAVE23 to: CAVE22, Direction moved -> WEST\n"
            +
            "\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> cave 22\n"
            +
            "\n"
            +
            "Something is lurking nearby !!\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[SOUTH, NORTH, EAST, WEST]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Where to ? -> Player moved from: CAVE22 to: CAVE21, Direction moved -> WEST\n"
            +
            "\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> cave 21\n"
            +
            "Treasure available here -> [SAPPHIRE, SAPPHIRE, SAPPHIRE]\n"
            +
            "2 Arrow available here\n"
            +
            "\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[SOUTH, NORTH, EAST]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Where to ? -> Invalid move try again\n"
            +
            "\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> cave 21\n"
            +
            "Treasure available here -> [SAPPHIRE, SAPPHIRE, SAPPHIRE]\n"
            +
            "2 Arrow available here\n"
            +
            "\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[SOUTH, NORTH, EAST]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Where to ? -> Player moved from: CAVE21 to: TUNNEL16, Direction moved -> NORTH\n"
            +

            "\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> tunnel 16\n"
            +
            "2 Arrow available here\n"
            +
            "\n"
            +
            "Something is lurking nearby !!\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[SOUTH, NORTH]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Where to ? -> Player moved from: TUNNEL16 to: TUNNEL11, Direction moved -> NORTH\n"
            +
            "\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> tunnel 11\n"
            +
            "\n"
            +
            "You smell something terrible nearby !!\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[SOUTH, NORTH]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Enter direction to shoot -> Enter Distance Between 1 and 5\n"
            +
            "You hear a great howl in the distance\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> tunnel 11\n"
            +
            "\n"
            +
            "You smell something terrible nearby !!\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[SOUTH, NORTH]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Where to ? -> Player moved from: TUNNEL11 to: CAVE6, Direction moved -> NORTH\n"
            +
            "\n"
            +
            "----- Reached Exit ----- \n"
            +
            "Encountered otyugh but the Lad survived, escaping...\n"
            +
            "\n"
            +
            "He survived to see another day!!!\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "Game ended !!! \n"
            +
            "Player stats after exit: Player at Location = 6, Arrows left 2, "
            +
            "Collected Treasure = Empty\n", this.out.toString());
  }


  @Test
  public void testPlayerMovementInWrappingPickingArrowAndTreasure() {
    assertEquals("23", gameTestWrap.getStart());
    assertEquals("6", gameTestWrap.getEnd());

    this.read = new StringReader("M west M west M west P T P A P T P A q");
    this.out = new StringBuilder();
    GameControlInterface game = new GameControl(this.read, this.out,gameTestWrap);
    game.startGame();

    assertEquals("\n"
                    +
                    "----------------------------------------------------------\n"
                    +
                    "\n"
                    +
                    "----  INFO! ----- \n"
                    +
                    "Player at -> cave 23\n"
                    +
                    "\n"
                    +
                    "You smell something terrible nearby !!\n"
                    +
                    "\n"
                    +
                    "Enter Direction from paths available :[SOUTH, NORTH, EAST, WEST]\n"
                    +
                    "------------------\n"
                    +
                    "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
                    +
                    "Where to ? -> Player moved from: CAVE23 to: CAVE22, Direction moved -> WEST\n"
                    +
                    "\n"
                    +
                    "\n"
                    +
                    "----------------------------------------------------------\n"
                    +
                    "\n"
                    +
                    "----  INFO! ----- \n"
                    +
                    "Player at -> cave 22\n"
                    +
                    "\n"
                    +
                    "Something is lurking nearby !!\n"
                    +
                    "\n"
                    +
                    "Enter Direction from paths available :[SOUTH, NORTH, EAST, WEST]\n"
                    +
                    "------------------\n"
                    +
                    "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
                    +
                    "Where to ? -> Player moved from: CAVE22 to: CAVE21, Direction moved -> WEST\n"
                    +
                    "\n"
                    +
                    "\n"
                    +
                    "----------------------------------------------------------\n"
                    +
                    "\n"
                    +
                    "----  INFO! ----- \n"
                    +
                    "Player at -> cave 21\n"
                    +
                    "Treasure available here -> [SAPPHIRE, SAPPHIRE, SAPPHIRE]\n"
                    +
                    "2 Arrow available here\n"
                    +
                    "\n"
                    +
                    "\n"
                    +
                    "Enter Direction from paths available :[SOUTH, NORTH, EAST]\n"
                    +
                    "------------------\n"
                    +
                    "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
                    +
                    "Where to ? -> Invalid move try again\n"
                    +
                    "\n"
                    +
                    "\n"
                    +
                    "----------------------------------------------------------\n"
                    +
                    "\n"
                    +
                    "----  INFO! ----- \n"
                    +
                    "Player at -> cave 21\n"
                    +
                    "Treasure available here -> [SAPPHIRE, SAPPHIRE, SAPPHIRE]\n"
                    +
                    "2 Arrow available here\n"
                    +
                    "\n"
                    +
                    "\n"
                    +
                    "Enter Direction from paths available :[SOUTH, NORTH, EAST]\n"
                    +
                    "------------------\n"
                    +
                    "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
                    +
                    "Pick Treasure(T) or Arrow(A)\n"
                    +
                    "Treasure Picked! at -> CAVE21->[SAPPHIRE, SAPPHIRE, SAPPHIRE]\n"
                    +
                    "\n"
                    +
                    "----------------------------------------------------------\n"
                    +
                    "\n"
                    +
                    "----  INFO! ----- \n"
                    +
                    "Player at -> cave 21\n"
                    +
                    "2 Arrow available here\n"
                    +
                    "\n"
                    +
                    "\n"
                    +
                    "Enter Direction from paths available :[SOUTH, NORTH, EAST]\n"
                    +
                    "------------------\n"
                    +
                    "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
                    +
                    "Pick Treasure(T) or Arrow(A)\n"
                    +
                    "2 Arrows Picked\n"
                    +
                    "\n"
                    +
                    "\n"
                    +
                    "----------------------------------------------------------\n"
                    +
                    "\n"
                    +
                    "----  INFO! ----- \n"
                    +
                    "Player at -> cave 21\n"
                    +
                    "\n"
                    +
                    "\n"
                    +
                    "Enter Direction from paths available :[SOUTH, NORTH, EAST]\n"
                    +
                    "------------------\n"
                    +
                    "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
                    +
                    "Pick Treasure(T) or Arrow(A)\n"
                    +
                    "No treasure here\n"
                    +
                    "\n"
                    +
                    "----------------------------------------------------------\n"
                    +
                    "\n"
                    +
                    "----  INFO! ----- \n"
                    +
                    "Player at -> cave 21\n"
                    +
                    "\n"
                    +
                    "\n"
                    +
                    "Enter Direction from paths available :[SOUTH, NORTH, EAST]\n"
                    +
                    "------------------\n"
                    +
                    "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
                    +
                    "Pick Treasure(T) or Arrow(A)\n"
                    +
                    "No arrows here\n"
                    +
                    "\n"
                    +
                    "----------------------------------------------------------\n"
                    +
                    "\n"
                    +
                    "----  INFO! ----- \n"
                    +
                    "Player at -> cave 21\n"
                    +
                    "\n"
                    +
                    "\n"
                    +
                    "Enter Direction from paths available :[SOUTH, NORTH, EAST]\n"
                    +
                    "------------------\n"
                    +
                    "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
                    +
                    "\n"
                    +
                    "Game is quit, Player stat at exit is: Player at Location = 21,"
                    +
                    " Arrows left 5, Collected Treasure = Diamond: 0, Ruby: 0, Sapphire: 3\n"
                    +
                    "\n"
                    +
                    "----------------------------------------------------------\n"
                    +

                    "Game ended !!! \n"
                    +
                    "Player stats after exit: Player at Location = 21, "
                    +
                    "Arrows left 5, Collected Treasure = Diamond: 0, Ruby: 0, Sapphire: 3\n",
            out.toString());

  }


  @Test
  public void testPlayerMovementInWrappingReachingEndSurvivalCheckDies() {
    wrap =    "  |      |      |      |           \n"
            + "- T      1      2      3      4  - \n"
            + "                                   \n"
            + "\n"
            + "                                   \n"
            + "- T      6      7      8      T  - \n"
            + "  |      |      |      |      |    \n"
            + "\n"
            + "  |      |      |      |      |    \n"
            + "- 10     T      T      T      14 - \n"
            + "  |      |      |      |      |    \n"
            + "\n"
            + "  |      |      |      |      |    \n"
            + "- T      T      T      T      19 - \n"
            + "         |      |      |      |    \n"
            + "\n"
            + "         |      |      |      |    \n"
            + "- T      21 - - 22 - - 23 - - 24 - \n"
            + "  |      |      |      |           \n"
            + "\n";

    gameTestWrap = new Game(5, 5, true, 2, 50,
            0, new RandomNumTestingTwo());

    assertEquals(wrap, gameTestWrap.dungeonGrid());
    assertEquals("8", gameTestWrap.getStart());
    assertEquals("14", gameTestWrap.getEnd());

    assertFalse(gameTestWrap.getLocationList()
            .get(Integer.parseInt(gameTestWrap.getStart())).checkMonsterPresent());

    assertTrue(gameTestWrap.getLocationList()
            .get(Integer.parseInt(gameTestWrap.getEnd())).checkMonsterPresent());

    this.read = new StringReader("M south M south M south M east M north S north 1 M north");
    this.out = new StringBuilder();
    GameControlInterface game = new GameControl(this.read, this.out,gameTestWrap);
    game.startGame();

    assertEquals("\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> cave 8\n"
            +
            "\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[SOUTH]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Where to ? -> Player moved from: CAVE8 to: TUNNEL13, Direction moved -> SOUTH\n"
            +
            "\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> tunnel 13\n"
            +
            "1 Arrow available here\n"
            +
            "\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[SOUTH, NORTH]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Where to ? -> Player moved from: TUNNEL13 to: TUNNEL18, Direction moved -> SOUTH\n"
            +
            "\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> tunnel 18\n"
            +
            "\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[SOUTH, NORTH]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Where to ? -> Player moved from: TUNNEL18 to: CAVE23, Direction moved -> SOUTH\n"
            +
            "\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> cave 23\n"
            +
            "\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[SOUTH, NORTH, EAST, WEST]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Where to ? -> Player moved from: CAVE23 to: CAVE24, Direction moved -> EAST\n"
            +
            "\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> cave 24\n"
            +
            "1 Arrow available here\n"
            +
            "\n"
            +
            "Something is lurking nearby !!\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[EAST, NORTH, WEST]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Where to ? -> Player moved from: CAVE24 to: CAVE19, Direction moved -> NORTH\n"
            +
            "\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> cave 19\n"
            +
            "\n"
            +
            "You smell something terrible nearby !!\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[EAST, SOUTH, NORTH]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Enter direction to shoot -> Enter Distance Between 1 and 5\n"
            +
            "You hear a great howl in the distance\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> cave 19\n"
            +
            "\n"
            +
            "You smell something terrible nearby !!\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[EAST, SOUTH, NORTH]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Where to ? -> Chomp, chomp, chomp, you are eaten by an Otyugh!\n"
            +
            "Better luck next time\n"
            +
            "\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "Game ended !!! \n"
            +
            "Player stats after exit: Player at Location = 14, "
            +
            "Arrows left 2, Collected Treasure = Empty\n", out.toString());


  }


  @Test
  public void testArrowMovement() {
    assertEquals("23", gameTestWrap.getStart());
    assertEquals("6", gameTestWrap.getEnd());

    assertTrue(gameTestWrap.getLocationList().get(5).checkMonsterPresent());

    this.read = new StringReader("S east 3 S east 3 q");
    this.out = new StringBuilder();
    GameControlInterface game = new GameControl(this.read, this.out,gameTestWrap);
    game.startGame();

    assertEquals("\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> cave 23\n"
            +
            "\n"
            +
            "You smell something terrible nearby !!\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[SOUTH, NORTH, EAST, WEST]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Enter direction to shoot -> Enter Distance Between 1 and 5\n"
            +
            "You hear a great howl in the distance\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> cave 23\n"
            +
            "\n"
            +
            "You smell something terrible nearby !!\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[SOUTH, NORTH, EAST, WEST]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Enter direction to shoot -> Enter Distance Between 1 and 5\n"
            +
            "You hear a great howl in the distance\n"
            +
            "You killed the otyugh!!! \n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> cave 23\n"
            +
            "\n"
            +
            "You smell something terrible nearby !!\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[SOUTH, NORTH, EAST, WEST]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "\n"
            +
            "Game is quit, Player stat at exit is: Player at Location = 23, Arrows left 1, "
            +
            "Collected Treasure = Empty\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "Game ended !!! \n"
            +
            "Player stats after exit: Player at Location = 23, Arrows left 1, "
            +
            "Collected Treasure = Empty\n", out.toString());


  }


  @Test
  public void testNoArrowsLeft() {
    assertEquals("23", gameTestWrap.getStart());
    assertEquals("6", gameTestWrap.getEnd());

    this.read = new StringReader("S east 3 S east 3 S south 3 S south 3 q");
    this.out = new StringBuilder();
    GameControlInterface game = new GameControl(this.read, this.out,gameTestWrap);
    game.startGame();

    assertEquals("\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> cave 23\n"
            +
            "\n"
            +
            "You smell something terrible nearby !!\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[SOUTH, NORTH, EAST, WEST]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Enter direction to shoot -> Enter Distance Between 1 and 5\n"
            +
            "You hear a great howl in the distance\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> cave 23\n"
            +
            "\n"
            +
            "You smell something terrible nearby !!\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[SOUTH, NORTH, EAST, WEST]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Enter direction to shoot -> Enter Distance Between 1 and 5\n"
            +
            "You hear a great howl in the distance\n"
            +
            "You killed the otyugh!!! \n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> cave 23\n"
            +
            "\n"
            +
            "You smell something terrible nearby !!\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[SOUTH, NORTH, EAST, WEST]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Enter direction to shoot -> Enter Distance Between 1 and 5\n"
            +
            "Shot an arrow into darkness\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> cave 23\n"
            +
            "\n"
            +
            "You smell something terrible nearby !!\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[SOUTH, NORTH, EAST, WEST]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Enter direction to shoot -> Enter Distance Between 1 and 5\n"
            +
            "Player has no arrows \n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> cave 23\n"
            +
            "\n"
            +
            "You smell something terrible nearby !!\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[SOUTH, NORTH, EAST, WEST]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "\n"
            +
            "Game is quit, Player stat at exit is: Player at Location = 23, "
            +
            "Arrows left 0, Collected Treasure = Empty\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "Game ended !!! \n"
            +
            "Player stats after exit: Player at Location = 23, Arrows left 0, "
            +
            "Collected Treasure = Empty\n", out.toString());


  }



  @Test
  public void testOtyughSmellStrong() {
    assertEquals("23", gameTestWrap.getStart());
    assertEquals("6", gameTestWrap.getEnd());


    assertTrue(gameTestWrap.getLocationList().get(8).checkMonsterPresent());
    assertTrue(gameTestWrap.getLocationList().get(3).checkMonsterPresent());


    this.read = new StringReader("M north M north M north");
    this.out = new StringBuilder();
    GameControlInterface game = new GameControl(this.read, this.out,gameTestWrap);
    game.startGame();

    assertEquals("\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> cave 23\n"
            +
            "\n"
            +
            "You smell something terrible nearby !!\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[SOUTH, NORTH, EAST, WEST]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Where to ? -> Player moved from: CAVE23 to: TUNNEL18, Direction moved -> NORTH\n"
            + "\n"
            + "\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> tunnel 18\n"
            +
            "\n"
            +
            "You smell something terrible nearby !!\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[SOUTH, NORTH]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Where to ? -> Player moved from: TUNNEL18 to: TUNNEL13, Direction moved -> NORTH\n"
            +
            "\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "\n"
            +
            "----  INFO! ----- \n"
            +
            "Player at -> tunnel 13\n"
            +
            "2 Arrow available here\n"
            +
            "\n"
            +
            "You smell something terrible nearby !!\n"
            +
            "\n"
            +
            "Enter Direction from paths available :[SOUTH, NORTH]\n"
            +
            "------------------\n"
            +
            "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
            +
            "Where to ? -> Chomp, chomp, chomp, you are eaten by an Otyugh!\n"
            +
            "Better luck next time\n"
            +
            "\n"
            +
            "\n"
            +
            "----------------------------------------------------------\n"
            +
            "Game ended !!! \n"
            +
            "Player stats after exit: Player at Location = 8, Arrows left 3, "
            +
            "Collected Treasure = Empty\n",out.toString());

  }



  @Test
  public void testOtyughSmellWeak() {
    assertEquals("23", gameTestWrap.getStart());
    assertEquals("6", gameTestWrap.getEnd());

    this.read = new StringReader("M west q");
    this.out = new StringBuilder();
    GameControlInterface game = new GameControl(this.read, this.out,gameTestWrap);
    game.startGame();
    assertTrue(gameTestWrap.getLocationList().get(8).checkMonsterPresent());
    assertTrue(gameTestWrap.getLocationList().get(3).checkMonsterPresent());

    assertFalse(gameTestWrap.getLocationList().get(22).checkMonsterPresent());
    assertEquals("\n"
                    +
                    "----------------------------------------------------------\n"
                    +
                    "\n"
                    +
                    "----  INFO! ----- \n"
                    +
                    "Player at -> cave 23\n"
                    +
                    "\n"
                    +
                    "You smell something terrible nearby !!\n"
                    +
                    "\n"
                    +
                    "Enter Direction from paths available :[SOUTH, NORTH, EAST, WEST]\n"
                    +
                    "------------------\n"
                    +
                    "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
                    +
                    "Where to ? -> Player moved from: CAVE23 to: CAVE22, Direction moved -> WEST\n"
                    +
                    "\n"
                    +
                    "\n"
                    +
                    "----------------------------------------------------------\n"
                    +
                    "\n"
                    +
                    "----  INFO! ----- \n"
                    +
                    "Player at -> cave 22\n"
                    +
                    "\n"
                    +
                    "Something is lurking nearby !!\n"
                    +
                    "\n"
                    +
                    "Enter Direction from paths available :[SOUTH, NORTH, EAST, WEST]\n"
                    +
                    "------------------\n"
                    +
                    "Move(M), Pickup(P), Shoot(S), PlayerInfo(I), Quit(Q)\n"
                    +
                    "\n"
                    +
                    "Game is quit, Player stat at exit is: Player at Location = 22, Arrows left 3,"
                    +
                    " Collected Treasure = Empty\n"
                    +
                    "\n"
                    +
                    "----------------------------------------------------------\n"
                    +
                    "Game ended !!! \n"
                    +
                    "Player stats after exit: Player at Location = 22, Arrows left 3, Collected "
                    +
                    "Treasure = Empty\n",out.toString());
  }



}
