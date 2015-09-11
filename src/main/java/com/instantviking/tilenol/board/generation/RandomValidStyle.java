package com.instantviking.tilenol.board.generation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.instantviking.tilenol.board.Board;
import com.instantviking.tilenol.board.Position;
import com.instantviking.tilenol.tiles.Tile;

final class RandomValidStyle implements Style
{

  @Override
  public Board generate(int width, int height, Random rand)
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

    while (!unusedCells.isEmpty())
    {
      int index = rand.nextInt(unusedCells.size());
      Position current = unusedCells.get(index);
      unusedCells.remove(current);
      List<Tile> options = b.tileOptionsAt(current.x, current.y);
      Tile nextGeneratedTile = options.get(rand.nextInt(options.size()));
      b.putTile(nextGeneratedTile, current);
    }
    return b;
  }

}
