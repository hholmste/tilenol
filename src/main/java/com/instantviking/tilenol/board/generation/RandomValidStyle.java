package com.instantviking.tilenol.board.generation;

import java.util.ArrayList;
import java.util.List;

import com.instantviking.tilenol.board.Board;
import com.instantviking.tilenol.board.Position;
import com.instantviking.tilenol.tiles.Tile;

final class RandomValidStyle extends Style
{

  @Override
  public Board generate(int width, int height)
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
      Tile nextGeneratedTile = generateTileAt(current.x, current.y, b);
      b.putTile(nextGeneratedTile, current);
    }
    return b;
  }

}
