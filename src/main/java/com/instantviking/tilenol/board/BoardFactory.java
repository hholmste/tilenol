package com.instantviking.tilenol.board;

import java.util.Arrays;
import java.util.List;
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
  private Optional<List<Object>> transitionalAlphabet = Optional.empty();
  private GenerativeStyle style = GenerativeStyle.RANDOM_VALID;

  public BoardFactory withSeed(long provided_seed)
  {
    userOverriddenSeed = Optional.of(provided_seed);
    return this;
  }

  public BoardFactory withStyle(GenerativeStyle style)
  {
    if (style == null)
    {
      throw new IllegalArgumentException(
          "When providing a generative style, it is polite to actually provide one. Instead, you provided the null-reference.");
    }
    this.style = style;
    return this;
  }

  /**
   * Optional list of objects that can be used as transitions between tiles. By
   * default, we use booleans, but it is possible to create any size alphabet.
   * 
   * The first element is the "no-transition"-transition in case we need those
   * kind of semantics.
   * 
   * We use Objects to let clients put any kind of logic inside the transitions
   * themselves, but we will only use reference-equality in our generation.
   * 
   * @param transitionalAlphabet
   * @return
   */
  public BoardFactory withTransitions(Object... transitionalAlphabet)
  {
    if (transitionalAlphabet == null || transitionalAlphabet.length <= 1)
    {
      throw new IllegalArgumentException(
          "Must have at least two kinds of transitions, or the board will be a generate mess of the same time repeated infinitly.");
    }
    this.transitionalAlphabet = Optional
        .of(Arrays.asList(transitionalAlphabet));
    return this;
  }

  /**
   * Generates a valid board, i.e. one where no tiles point a connection at a
   * neighbor without a matching connection.
   */
  public Board generateValidBoard(int width, int height)
  {
    return StyleResolver
        .resolve(style)
        .usingTransitions(
            transitionalAlphabet.isPresent()
                ? transitionalAlphabet.get()
                : Arrays.asList(Boolean.FALSE, Boolean.TRUE))
        .usingRandom(buildRandom())
        .generate(width, height);
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
