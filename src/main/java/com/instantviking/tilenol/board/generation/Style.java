package com.instantviking.tilenol.board.generation;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.instantviking.tilenol.board.Board;
import com.instantviking.tilenol.board.borders.WrappingRule;
import com.instantviking.tilenol.tiles.Tile;

public abstract class Style
{

  protected Random rand;
  protected List<Object> transitionalAlphabet;
  private List<Object> borderTransitions;
  private WrappingRule wrappingRule;

  public abstract Board generate(int width, int height);

  public Style usingRandom(Random rand)
  {
    this.rand = rand;
    return this;
  }

  public Style usingTransitions(List<Object> transitionalAlphabet)
  {
    this.transitionalAlphabet = Collections
        .unmodifiableList(transitionalAlphabet);
    return this;
  }

  public Style usingBorderTransitions(List<Object> borderTransitions)
  {
    this.borderTransitions = borderTransitions;
    return this;
  }

  public Style usingWrappingRule(WrappingRule rule)
  {
    this.wrappingRule = rule;
    return this;
  }

  /**
   * Generates a Tile that fits the given board at the given position, with
   * transitions taken from the available transition-alphabet.
   */
  protected Tile generateTileAt(int x, int y, Board board)
  {
    Object transitionToTheNorth = board.findTile(x, y - 1).south;
    Object transitionToTheSouth = board.findTile(x, y + 1).north;
    Object transitionToTheEast = board.findTile(x + 1, y).west;
    Object transitionToTheWest = board.findTile(x - 1, y).east;

    Object generatedNorth = (transitionToTheNorth != null)
        ? transitionToTheNorth
        : randomTransition();
    Object generatedSouth = (transitionToTheSouth != null)
        ? transitionToTheSouth
        : randomTransition();
    Object generatedEast = (transitionToTheEast != null)
        ? transitionToTheEast
        : randomTransition();
    Object generatedWest = (transitionToTheWest != null)
        ? transitionToTheWest
        : randomTransition();
    return new Tile(
        generatedNorth,
        generatedSouth,
        generatedEast,
        generatedWest);
  }

  private Object randomTransition()
  {
    return transitionalAlphabet.get(rand.nextInt(transitionalAlphabet.size()));
  }

  public Style validateOrDie()
  {
    if (!transitionalAlphabet.containsAll(borderTransitions))
    {
      throw new IllegalStateException(
          String
              .format(
                  "The collection of legal border-transitions contains items that are not present in the collection of legal transitions. This is bad and must not be repeated.\nLegal borders: %s\nLegal transitions: %s",
                  transitionalAlphabet,
                  borderTransitions));
    }
    return this;
  }

}
