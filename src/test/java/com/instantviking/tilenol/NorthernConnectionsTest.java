package com.instantviking.tilenol;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.instantviking.tilenol.tiles.Tile;
import com.instantviking.tilenol.tiles.Tiles;
import com.instantviking.tilenol.tiles.Uninitialized;

public class NorthernConnectionsTest
{
  private static List<Tile> north_connectors;
  private static List<Tile> south_connectors = new ArrayList<Tile>();
  private static List<Tile> south_blockers = new ArrayList<Tile>();
  private static Tile uninitialized = new Uninitialized();
  static
  {
    north_connectors = Arrays.asList(
        new Tile(Tiles.NORTH),
        new Tile(Tiles.NORTH + Tiles.EAST),
        new Tile(Tiles.NORTH + Tiles.WEST),
        new Tile(Tiles.NORTH + Tiles.SOUTH),
        new Tile(Tiles.NORTH + Tiles.EAST + Tiles.WEST),
        new Tile(Tiles.NORTH + Tiles.EAST + Tiles.SOUTH),
        new Tile(Tiles.NORTH + Tiles.WEST + Tiles.SOUTH),
        new Tile(Tiles.NORTH + Tiles.EAST + Tiles.WEST + Tiles.SOUTH));

    south_connectors = Arrays.asList(
        new Tile(Tiles.SOUTH),
        new Tile(Tiles.SOUTH + Tiles.EAST),
        new Tile(Tiles.SOUTH + Tiles.WEST),
        new Tile(Tiles.SOUTH + Tiles.NORTH),
        new Tile(Tiles.SOUTH + Tiles.EAST + Tiles.WEST),
        new Tile(Tiles.SOUTH + Tiles.EAST + Tiles.NORTH),
        new Tile(Tiles.SOUTH + Tiles.WEST + Tiles.NORTH),
        new Tile(Tiles.SOUTH + Tiles.EAST + Tiles.WEST + Tiles.NORTH));

    south_blockers = Arrays.asList(
        new Tile(Tiles.CENTER),
        new Tile(Tiles.EAST),
        new Tile(Tiles.WEST),
        new Tile(Tiles.NORTH),
        new Tile(Tiles.EAST + Tiles.WEST),
        new Tile(Tiles.EAST + Tiles.NORTH),
        new Tile(Tiles.WEST + Tiles.NORTH),
        new Tile(Tiles.EAST + Tiles.WEST + Tiles.NORTH));
  }

  @Test
  public
      void
      northConnectingTiles_shouldFitWithSouthConnectingTilesToTheNorth()
  {
    north_connectors.forEach(tile -> south_connectors
        .forEach(north -> assertTrue(Tiles.fits(
            tile,
            north,
            uninitialized,
            uninitialized,
            uninitialized))));
  }

  @Test
  public
      void
      northConnectingTiles_shouldNotFitWithNorthernNeighboursWithoutSouthConnections()
  {
    north_connectors.forEach(tile -> south_blockers
        .forEach(north -> assertFalse(Tiles.fits(
            tile,
            north,
            uninitialized,
            uninitialized,
            uninitialized))));
  }
}
