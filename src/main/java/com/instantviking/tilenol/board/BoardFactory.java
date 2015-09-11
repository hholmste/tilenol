package com.instantviking.tilenol.board;

import java.util.Optional;
import java.util.Random;

import com.instantviking.tilenol.board.generation.GenerativeStyle;
import com.instantviking.tilenol.board.generation.StyleResolver;

/**
 * Yeah, I went there
 *
 */
public class BoardFactory
{
  private long usedSeed;
  private Optional<Long> userOverriddenSeed = Optional.empty();

  private GenerativeStyle style = GenerativeStyle.RANDOM_VALID;

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

  public BoardFactory withStyle(GenerativeStyle style)
  {
    this.style = style;
    return this;
  }

  /**
   * Generates a valid board, i.e. one where no tiles point a connection at a
   * neighbor without a matching connection.
   */
  public Board generateValidBoard(int width, int height)
  {
    return StyleResolver.resolve(style).generate(width, height, buildRandom());
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
