package com.instantviking.tilenol.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.instantviking.tilenol.tiles.Tile;

/**
 * Yeah, I went there
 *
 */
public class BoardFactory
{
  public static void main(String... args)
  {
    Board b = BoardFactory.generateValidBoard(3, 5);
    System.out.println(b.toString());
  }

  /**
   * Generates a valid board, i.e. one where no tiles point a connection at a
   * neighbor without a matching connection.
   */
  public static Board generateValidBoard(int width, int height)
  {
    Board b = new Board(width, height);
    List<Position> unusedCells = new ArrayList<Position>();
    for (int i = 0; i < width; i++)
    {
      for (int j = 0; j < height; j++)
      {
        unusedCells.add(new Position(i, j));
      }
    }

    Random rand = new Random(new Random().nextLong());
    while (!unusedCells.isEmpty())
    {
      int index = rand.nextInt(unusedCells.size());
      Position current = unusedCells.get(index);
      unusedCells.remove(current);
      List<Tile> options = b.tileOptionsAt(current.x, current.y);
      Tile nextGeneratedTile = options.get(rand.nextInt(options.size()));
      b.putTile(nextGeneratedTile, current.x, current.y);

    }
    return b;
  }

  private static class Position
  {
    final int x;
    final int y;

    Position(int x, int y)
    {
      this.x = x;
      this.y = y;
    }
  }

}
