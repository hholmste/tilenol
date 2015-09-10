package com.instantviking.tilenol;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Board
{
  private final int width;
  private final int height;
  private final Tile[][] tiles;

  public static void main(String... args)
  {
    Board b = Board.generateConnectedBoard(3, 5);
    System.out.println(b.toString());
  }

  public static Board generateConnectedBoard(int width, int height)
  {
    Board b = new Board(width, height);
    b.generateConnected();
    return b;
  }

  public int getWidth()
  {
    return width;
  }

  public int getHeight()
  {
    return height;
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

  private void generateConnected()
  {
    List<Cell> unusedCells = new ArrayList<Board.Cell>();
    for (int i = 0; i < width; i++)
    {
      for (int j = 0; j < height; j++)
      {
        unusedCells.add(new Cell(i, j));
      }
    }

    Random rand = new Random();
    while (!unusedCells.isEmpty())
    {
      int index = rand.nextInt(unusedCells.size());
      Cell current = unusedCells.get(index);
      unusedCells.remove(current);
      List<Tile> options = tileOptionsAt(current.x, current.y);
      tiles[current.x][current.y] = options.get(rand.nextInt(options.size()));
    }
  }

  private Board(int width, int height)
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
  }

  /**
   * retrieves tiles that can fit at the given cell on the board.
   * 
   * @param x
   * @param y
   * @return
   */
  private List<Tile> tileOptionsAt(int x, int y)
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

  private class Cell
  {
    final int x;
    final int y;

    Cell(int x, int y)
    {
      this.x = x;
      this.y = y;
    }
  }
}
