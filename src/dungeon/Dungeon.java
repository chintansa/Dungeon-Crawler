package dungeon;

import randomnumgen.RandomNumInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Vector;




/**
 * Defines the methods for Dungeon which has Caves and Tunnels of the game.
 * The caves contain treasures and player enters one of them to start the game.
 * Tunnels are ones with only two paths in and out and contain no treasures.
 * Dungeons are formed such that there is at least one path from one cave to another
 * and its minimum size is a 5X5 grid and can also wrap.
 */
public class Dungeon implements DungeonInterface {

  private final List<Edge> allEdges;
  private final LocationInterface[][] dungeon;
  private final int row;
  private final int col;
  private final int vertices;
  private final RandomNumInterface r;
  private final List<Edge> minSpanTree;
  private final List<Edge> removedEdges;
  private final int[] parent;
  private final double treasurePercentage;
  private final List<LocationInterface> locationList;
  private final boolean wrapping;
  private final List<List<LocationInterface>> adjacencyList;
  private final List<Integer>[] adj;
  private final List<Edge> startEnd;
  private final List<LocationInterface> caveList;
  private int interconnectivity;
  private int count;
  private int edgeCount;
  private int minEdges;
  private LocationInterface start;
  private LocationInterface end;


  /**
   * Constructor to create the dungeon with wrapping and non-wrapping and interconnectivity.
   *
   * @param row                number of rows.
   * @param col                number of columns.
   * @param wrapping           wrapping condition true or false.
   * @param interconnectivity  number of interconnecting edges.
   * @param treasurePercentage the percentage of caves to contain treasure.
   * @param r                  random object.
   */
  public Dungeon(int row, int col, boolean wrapping, int interconnectivity,
                 double treasurePercentage, RandomNumInterface r) {

    if (treasurePercentage > 100 || treasurePercentage < 0) {
      throw new IllegalArgumentException("Invalid treasure percentage, keep it within 0 to 100");
    }
    if (row < 5 || col < 5 || interconnectivity < 0 || r == null) {
      throw new IllegalArgumentException("Invalid arguments");
    }
    this.allEdges = new ArrayList<>();
    this.row = row;
    this.col = col;
    this.vertices = this.row * this.col;
    this.wrapping = wrapping;
    this.dungeon = new Location[row][col];
    this.locationList = new ArrayList<>();
    this.interconnectivity = interconnectivity;
    this.createDungeon(row, col);
    this.createAllEdges(row, col);
    this.r = r;
    this.removedEdges = new ArrayList<>();
    this.minSpanTree = new ArrayList<>();
    this.parent = new int[vertices];
    this.treasurePercentage = Math.round(treasurePercentage);
    this.adjacencyList = new ArrayList<>();
    this.startEnd = new ArrayList<>();
    this.adj = new Vector[vertices];
    caveList = new ArrayList<>();


    this.createParentAdj();
    this.kruskalAlgorithm();
    this.setInterconnectivity();
    this.setDirections();
    this.allCaveList();
    this.setTreasureToCaves(r);
    this.setArrowsToCaves(r);

    this.createAdjacencyList();
    this.createAdjacencyListDfs();
    this.minPathFinderDfs();

    this.pickStartEnd();

  }

  @Override
  public double getTreasurePercentage() {
    return treasurePercentage;
  }

  @Override
  public boolean isWrapping() {
    return wrapping;
  }

  @Override
  public int getInterconnectivity() {
    return interconnectivity;
  }


  /**
   * Method to create dungeon.
   *
   * @param row num of rows.
   * @param col num of cols.
   */
  private void createDungeon(int row, int col) {
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        dungeon[i][j] = new Location(i, j, this.count++);
        this.locationList.add(dungeon[i][j]);

      }
    }
  }

  private void createParentAdj() {
    for (int i = 0; i < vertices; i++) {
      adj[i] = new Vector<>();
    }
  }

  /**
   * Helper to create edges.
   *
   * @param source      source location.
   * @param destination destination location.
   */
  private void addEdge(LocationInterface source, LocationInterface destination) {

    Edge edge = new Edge(source, destination);
    allEdges.add(edge); //add to total edges
  }


  /**
   * Creates all the possible edges in the dungeon.
   *
   * @param row rows in 2D dungeon array.
   * @param col columns in 2D dungeon array.
   */
  private void createAllEdges(int row, int col) {
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col - 1; j++) {
        addEdge(dungeon[i][j], dungeon[i][j + 1]);
      }
    }

    for (int i = 0; i < col; i++) {
      for (int j = 0; j < row - 1; j++) {
        addEdge(dungeon[j][i], dungeon[j + 1][i]);
      }
    }

    if (this.wrapping) {
      for (int i = 0; i < this.row; i++) {
        addEdge(dungeon[i][col - 1], dungeon[i][0]);
      }
      for (int j = 0; j < this.col; j++) {
        addEdge(dungeon[row - 1][j], dungeon[0][j]);
      }
    }

  }


  //-------------------------------------------------------------

  /**
   * Kruskal's algo to create a dungeon with no cycles in paths.
   */
  private void kruskalAlgorithm() {
    createLocationSet(this.parent);

    //process vertices – 1 edges
    int index = 0;
    int v = vertices - 1;


    while (index < v) {
      Edge edge = allEdges.get(this.r.getRandomNumber(allEdges.size()));
      //check if adding this edge creates a cycle
      int x_set = find(this.parent, edge.getSource().getId());
      int y_set = find(this.parent, edge.getDest().getId());

      if (x_set == y_set) {
        this.removedEdges.add(edge);
      } else {
        //add it to our final result
        this.minSpanTree.add(edge);
        index++;
        union(this.parent, x_set, y_set);
      }
      this.allEdges.remove(edge);
    }
    this.allEdges.addAll(this.removedEdges);
  }

  private void createLocationSet(int[] parent) {
    for (int i = 0; i < vertices; i++) {
      parent[i] = i;
    }
  }

  private int find(int[] parent, int vertex) {
    if (parent[vertex] != vertex) {
      return find(parent, parent[vertex]);
    }
    return vertex;
  }

  private void union(int[] parent, int x, int y) {
    int x_set_parent = find(parent, x);
    int y_set_parent = find(parent, y);
    parent[y_set_parent] = x_set_parent;
  }


  //-------------------------------------------------------------


  /**
   * Used to set the number of interconnecting edges in min spanning tree.
   */
  private void setInterconnectivity() {
    if (interconnectivity > allEdges.size()) {
      this.interconnectivity = allEdges.size();
    }

    for (int i = 0; i < interconnectivity; i++) {
      Edge edge = allEdges.get(this.r.getRandomNumber(allEdges.size()));
      minSpanTree.add(edge);
      allEdges.remove(edge);
    }

  }


  /**
   * to set every possible path from each location to another.
   */
  private void setDirections() {

    for (Edge e : minSpanTree) {
      int srcX = e.getSource().getX();
      int srcY = e.getSource().getY();

      int destX = e.getDest().getX();
      int destY = e.getDest().getY();

      if (srcX == destX) {
        e.getSource().setEastTrue();
        e.getDest().setWestTrue();
      }

      if (srcY == destY) {
        e.getSource().setSouthTrue();
        e.getDest().setNorthTrue();
      }

      e.getSource().setLocationType();
      e.getDest().setLocationType();

    }


  }


  @Override
  public String dumpDungeonLocationMap() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        sb.append(dungeon[i][j].locationMap()).append('\n');
      }
    }
    return sb.toString();
  }

  //-------------------------------------------------------------

  /**
   * Helper to generate treasure.
   *
   * @return treasure generated.
   */
  private Treasure generateTreasure() {
    int rand = this.r.getRandomNumber(3);
    switch (rand) {
      case 0:
        return (Treasure.DIAMOND);
      case 1:
        return (Treasure.RUBY);
      case 2:
        return (Treasure.SAPPHIRE);
      default:
        throw new IllegalArgumentException("Invalid case");
    }
  }

  /**
   * Assign every selected cave with treasure.
   *
   * @param r random object
   */
  private void setTreasureToCaves(RandomNumInterface r) {

    int caveLimit = (int) Math.round(caveList.size() * (treasurePercentage / 100));

    Collections.shuffle(caveList, new Random(2));

    for (int caveIndex = 0; caveIndex < caveLimit; caveIndex++) {
      for (int treasureCount = 0; treasureCount < r.getRandomNumber(3) + 1; treasureCount++) {
        caveList.get(caveIndex).holdTreasure(generateTreasure());
      }

    }

  }

  /**
   * To place arrows around the dungeon for the player to pick up.
   *
   * @param r random num generator.
   */
  private void setArrowsToCaves(RandomNumInterface r) {

    List<LocationInterface> tempLocList = new ArrayList<>(this.locationList);
    int locationLimit = (int) Math.round(tempLocList.size() * (treasurePercentage / 100));

    Collections.shuffle(tempLocList, new Random(2));
    for (int locIndex = 0; locIndex < locationLimit; locIndex++) {
      for (int arrowCount = 0; arrowCount < r.getRandomNumber(2) + 1; arrowCount++) {
        tempLocList.get(locIndex).storeArrows(new Arrow(locIndex));
      }
    }
  }
  //-------------------------------------------------------------

  /**
   * to create adjacency list.
   */
  private void createAdjacencyList() {
    for (int i = 0; i < this.vertices; i++) {
      this.adjacencyList.add(i, new ArrayList<>());
    }
    for (Edge p : this.minSpanTree) {
      this.adjacencyList.get(p.getSource().getId()).add(p.getDest());
      this.adjacencyList.get(p.getDest().getId()).add(p.getSource());
    }
  }

  @Override
  public List<List<LocationInterface>> getAdjacencyList() {
    return new ArrayList<>(this.adjacencyList);
  }

  //-------------------------------------------------------------

  /**
   * to get a min path of 5 between start and end.
   */
  private void createAdjacencyListDfs() {
    for (Edge edge : this.minSpanTree) {
      adj[edge.getSource().getId()].add(edge.getDest().getId());
      adj[edge.getDest().getId()].add(edge.getSource().getId());
    }
  }


  private void minPathFinderDfs() {
    List<Integer> caveIdList = new ArrayList<>();

    for (LocationInterface n : caveList) {
      caveIdList.add(n.getId());
    }

    for (int k = 0; k < caveIdList.size(); k++) {

      for (Integer integer : caveIdList) {


        boolean[] visited = new boolean[this.vertices];
        Arrays.fill(visited, false);

        minEdges = Integer.MAX_VALUE;

        edgeCount = 0;
        int u = caveIdList.get(k);

        int v = integer;
        minPathFinderHelper(visited, u, v);

        if (minEdges >= 5) {
          Edge edge = new Edge(locationList.get(u), locationList.get(v));
          startEnd.add(edge);
        }

      }
    }
  }

  private void minPathFinderHelper(boolean[] visited, int src, int des) {
    visited[src] = true;

    if (src == des) {
      if (minEdges > edgeCount) {
        minEdges = edgeCount;
      }
    } else {
      for (int i : adj[src]) {
        if (!visited[i]) {
          edgeCount++;
          minPathFinderHelper(visited, i, des);
        }
      }
    }

    visited[src] = false;
    edgeCount--;
  }

  //-------------------------------------------------------------


  private void pickStartEnd() {
    Edge se = startEnd.get(r.getRandomNumber(this.startEnd.size()));
    this.start = se.getSource();
    this.end = se.getDest();

  }

  @Override
  public LocationInterface getStart() {
    return new Location(this.start.getX(), this.start.getY(), this.start.getId(),
            this.start.isNorthAvailable(),
            this.start.isSouthAvailable(), this.start.isEastAvailable(),
            this.start.isWestAvailable(),
            this.start.getLocationType(), this.start.getTreasureChest(),
            this.start.getDirectionsAvailable(), this.start.checkMonsterPresent(),
            this.start.getArrowsStored());
  }

  @Override
  public LocationInterface getEnd() {
    return new Location(this.end.getX(), this.end.getY(), this.end.getId(),
            this.end.isNorthAvailable(), this.end.isSouthAvailable(), this.end.isEastAvailable(),
            end.isWestAvailable(), end.getLocationType(),
            end.getTreasureChest(), end.getDirectionsAvailable(), this.start.checkMonsterPresent(),
            this.end.getArrowsStored());
  }

  @Override
  public List<LocationInterface> getLocationList() {
    return new ArrayList<>(this.locationList);
  }

  @Override
  public String dumpBetterDungeon() {
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < row; i++) {
      for (int k = 0; k < 3; k++) {
        for (int j = 0; j < col; j++) {
          LocationInterface temp = dungeon[i][j];
          if (k == 0) {
            if (temp.isNorthAvailable()) {
              sb.append("  ").append("|").append("   ");
            } else {
              sb.append("      ");
            }
          } else if (k == 1) {
            if (temp.isWestAvailable()) {
              sb.append("- ");
            } else {
              sb.append("  ");
            }
            if (temp.getLocationType() == LocationType.CAVE) {
              if (temp.getId() < 10) {
                sb.append(temp.getId()).append(" ");
              } else {
                sb.append(temp.getId());
              }
            } else {
              sb.append("T ");
            }
            if (temp.isEastAvailable()) {
              sb.append(" -");
            } else {
              sb.append("  ");
            }
          } else {
            if (temp.isSouthAvailable()) {
              sb.append("  ").append("|").append("   ");
            } else {
              sb.append("      ");
            }
          }
          sb.append(" ");
        }
        sb.append("\n");
      }
      sb.append("\n");
    }

    return sb.toString();
  }

  @Override
  public String getStartAndEndLocation() {
    return "Start: " + this.start.getId() + " " + "End: " + this.end.getId();

  }

  private void allCaveList() {
    for (LocationInterface l : this.locationList) {
      if (l.getLocationType() == LocationType.CAVE) {
        caveList.add(l);
      }
    }
  }

  @Override
  public List<LocationInterface> getAllCaveList() {
    return new ArrayList<>(caveList);
  }


}
