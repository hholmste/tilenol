package com.instantviking.tilenol;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;

public class UnitializedNeighboursTest
{

  private static final List<Tile> all_tiles = new ArrayList<Tile>();
  private static final Tile unitialized = new Uninitialized();
  static
  {
    IntStream.range(0, 16).forEach(i -> all_tiles.add(new Tile(i)));
  }

  @Test
  public void everything_fits_when_all_neighbours_are_unitialized()
  {
    all_tiles.stream().forEach(
        tile -> assertTrue(Tiles.fits(
            tile,
            unitialized,
            unitialized,
            unitialized,
            unitialized)));
  }
}
