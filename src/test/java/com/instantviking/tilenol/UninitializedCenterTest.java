package com.instantviking.tilenol;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;

import com.instantviking.tilenol.tiles.Tile;
import com.instantviking.tilenol.tiles.Tiles;
import com.instantviking.tilenol.tiles.Uninitialized;

public class UninitializedCenterTest
{
  private static final List<Tile> all_tiles = new ArrayList<Tile>();
  private static final Tile unitialized = new Uninitialized();
  static
  {
    IntStream.range(0, 16).forEach(i -> all_tiles.add(new Tile(i)));
  }

  @Test
  public void unitializedCenterFitsAllNeighbours()
  {
    // this'll be 16^4 tests :)
    all_tiles.stream().forEach(
        north -> all_tiles.stream().forEach(
            south -> all_tiles.stream().forEach(
                west -> all_tiles.stream().forEach(
                    east -> assertTrue(Tiles.fits(
                        unitialized,
                        north,
                        south,
                        west,
                        east))))));
  }
}
