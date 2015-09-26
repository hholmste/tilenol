package com.instantviking.tilenol.board.generation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import com.instantviking.tilenol.board.Board;
import com.instantviking.tilenol.board.Position;
import com.instantviking.tilenol.board.borders.WrappingRule;
import com.instantviking.tilenol.tiles.Tile;

final class RandomValidStyle extends Style
{

  @Override
  public Board generate(int width, int height)
  {
    Board b = new Board(width, height, wrappingRule);

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

  public static void main(String... args)
  {
    RandomValidStyle style = new RandomValidStyle();
    style.transitionalAlphabet = Arrays.asList(" ", "a", "=", "0");
    style.rand = new Random(1L);
    style.wrappingRule = WrappingRule.WRAP_ALWAYS;
    Board b = style.generate(6, 6);

    IntStream.range(0, b.getHeight()).forEach(row ->
    {
      IntStream.range(0, b.getWidth()).forEach(column ->
      {
        Tile t = b.getTile(column, row);
        System.err.printf("  %s  ", t.north);
      });
      System.out.println(" ");
      IntStream.range(0, b.getWidth()).forEach(column ->
      {
        Tile t = b.getTile(column, row);
        System.err.printf(" %sX%s ", t.west, t.east);
      });
      System.out.println(" ");
      IntStream.range(0, b.getWidth()).forEach(column ->
      {
        Tile t = b.getTile(column, row);
        System.err.printf("  %s  ", t.south);
      });
      System.out.println("\n");
    });
  }

}
