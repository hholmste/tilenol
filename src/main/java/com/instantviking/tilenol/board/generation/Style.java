package com.instantviking.tilenol.board.generation;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.instantviking.tilenol.board.Board;
import com.instantviking.tilenol.board.borders.WrappingRule;
import com.instantviking.tilenol.tiles.OffBoard;
import com.instantviking.tilenol.tiles.Tile;
import com.instantviking.tilenol.tiles.Uninitialized;

public abstract class Style
{

  protected Random rand;
  protected List<Object> transitionalAlphabet;
  protected List<Object> borderTransitions;
  protected WrappingRule wrappingRule;

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
    Tile northernTile = board.findTile(x, y - 1);
    Object transitionToTheNorth = northernTile.south;
    Object generatedNorth = (transitionToTheNorth != null)
        ? transitionToTheNorth
        : randomTransition(northernTile);

    Tile southernTile = board.findTile(x, y + 1);
    Object transitionToTheSouth = southernTile.north;
    Object generatedSouth = (transitionToTheSouth != null)
        ? transitionToTheSouth
        : randomTransition(southernTile);

    Tile easternTile = board.findTile(x + 1, y);
    Object transitionToTheEast = easternTile.west;
    Object generatedEast = (transitionToTheEast != null)
        ? transitionToTheEast
        : randomTransition(easternTile);

    Tile westernTile = board.findTile(x - 1, y);
    Object transitionToTheWest = westernTile.east;
    Object generatedWest = (transitionToTheWest != null)
        ? transitionToTheWest
        : randomTransition(westernTile);

    return new Tile(
        generatedNorth,
        generatedSouth,
        generatedEast,
        generatedWest);
  }

  private Object randomTransition(Tile neighbour)
  {
    if (neighbour instanceof Uninitialized)
    {
      return transitionalAlphabet
          .get(rand.nextInt(transitionalAlphabet.size()));
    }
    if (neighbour instanceof OffBoard)
    {
      return borderTransitions.get(rand.nextInt(borderTransitions.size()));
    }
    return transitionalAlphabet.get(0);
  }

  public Style validateOrDie()
  {
    if (transitionalAlphabet == null)
    {
      throw new IllegalStateException("Must have a transitional alphabet");
    }
    if (borderTransitions == null)
    {
      throw new IllegalStateException("Must have border transitions");
    }

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
