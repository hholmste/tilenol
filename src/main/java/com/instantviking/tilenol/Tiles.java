package com.instantviking.tilenol;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Tiles
{
  public static final int CENTER = 0;
  public static final int EAST = 1;
  public static final int WEST = 2;
  public static final int NORTH = 4;
  public static final int SOUTH = 8;

  public static final List<Tile> all_the_tiles;
  static
  {
    all_the_tiles = IntStream
        .range(0, 16)
        .boxed()
        .map(i -> new Tile(i))
        .collect(Collectors.toList());
  }

  private Tiles()
  {
  }

  public static boolean fits(
      Tile candidate,
      Tile north,
      Tile south,
      Tile west,
      Tile east)
  {
    if (candidate instanceof Uninitialized)
    {
      return true;
    }
    return fits(candidate, west, WEST, EAST)
        && fits(candidate, east, EAST, WEST)
        && fits(candidate, south, SOUTH, NORTH)
        && fits(candidate, north, NORTH, SOUTH);
  }

  private static boolean fits(
      Tile candidate,
      Tile neighbour,
      int outbound,
      int inbound)
  {
    if (neighbour instanceof Uninitialized)
    {
      return true;
    }

    boolean candidateConnectsOut = (candidate.val & outbound) == outbound;
    boolean neighbourConnectsInbound = (neighbour.val & inbound) == inbound;
    return candidateConnectsOut == neighbourConnectsInbound;
  }
}
