package com.instantviking.tilenol.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.instantviking.tilenol.tiles.Tile;
import com.instantviking.tilenol.tiles.Tiles;
import com.instantviking.tilenol.tiles.Uninitialized;

public class Board
{
  private final int width;
  private final int height;
  private final Tile[][] tiles;
  private final List<Position> generatedOrder;

  public Board(int width, int height)
  {
    this.width = width;
    this.height = height;
    tiles = new Tile[width][height];
    for (int i = 0; i < width; i++)
    {
      for (int j = 0; j < height; j++)
      {
        tiles[i][j] = new Uninitialized();
      }
    }
    generatedOrder = new ArrayList<Position>();
  }

  public int getWidth()
  {
    return width;
  }

  public int getHeight()
  {
    return height;
  }

  /**
   * Retrieves tiles that can fit at the given cell on the board.
   * 
   * @param x
   * @param y
   * @return
   */
  public List<Tile> tileOptionsAt(int x, int y)
  {
    Tile north = findTile(x, y - 1);
    Tile south = findTile(x, y + 1);
    Tile east = findTile(x + 1, y);
    Tile west = findTile(x - 1, y);
    return Tiles.all_the_tiles
        .stream()
        .filter(candidate -> Tiles.fits(candidate, north, south, west, east))
        .collect(Collectors.toList());
  }

  public Tile findTile(int x, int y)
  {
    if (x < 0 || x >= this.width || y < 0 || y >= this.height)
    {
      return new Uninitialized();
    }
    return tiles[x][y];
  }

  public List<Position> getGeneratedOrder()
  {
    return Collections.unmodifiableList(generatedOrder);
  }

  public void putTile(Tile tile, Position position)
  {
    tiles[position.x][position.y] = tile;
    generatedOrder.add(position);
  }

  @Override
  public String toString()
  {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < height; i++)
    {
      for (int j = 0; j < width; j++)
      {
        builder.append(String.format("%24s", tiles[j][i])).append(" ");
      }
      builder.append("\n");
    }
    return builder.toString();
  }
}
