package dungeon;

/**
 * Class to create an edge between two locations in the dungeon.
 */
public class Edge {
  private final LocationInterface source;
  private final LocationInterface dest;


  /**
   * Constructor to create an edge.
   *
   * @param src the first location.
   * @param dst the final location.
   */
  Edge(LocationInterface src, LocationInterface dst) {
    if (src == null || dst == null) {
      throw new IllegalArgumentException("Invalid edge");
    }
    this.source = src;
    this.dest = dst;
  }


  /**
   * Gets the source location of the edge.
   *
   * @return source location.
   */
  LocationInterface getSource() {
    return this.source;
  }

  /**
   * Gets the end location of the edge.
   *
   * @return final location.
   */
  LocationInterface getDest() {
    return this.dest;
  }

  @Override
  public String toString() {
    return "Edge{"
            + "source=" + source.getId()
            + ", dest=" + dest.getId()
            + '}';
  }
}
