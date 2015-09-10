package com.instantviking.tilenol.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.instantviking.tilenol.tiles.Tile;

/**
 * Yeah, I went there
 *
 */
public class BoardFactory
{
  private long usedSeed;
  private Optional<Long> userOverriddenSeed = Optional.empty();

  public static void main(String... args)
  {
    Board b = new BoardFactory().generateValidBoard(3, 5);
    System.out.println(b.toString());
  }

  public BoardFactory withSeed(long provided_seed)
  {
    userOverriddenSeed = Optional.of(provided_seed);
    return this;
  }

  /**
   * Generates a valid board, i.e. one where no tiles point a connection at a
   * neighbor without a matching connection.
   */
  public Board generateValidBoard(int width, int height)
  {
    Random rand = buildRandom();

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

  private Random buildRandom()
  {
    if (userOverriddenSeed.isPresent())
    {
      usedSeed = userOverriddenSeed.get();
    } else
    {
      usedSeed = new Random().nextLong();
    }
    return new Random(usedSeed);
  }
  
  public long getUsedSeed()
  {
    return usedSeed;
  }

}
