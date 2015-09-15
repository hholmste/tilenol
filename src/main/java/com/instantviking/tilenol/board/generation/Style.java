package com.instantviking.tilenol.board.generation;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.instantviking.tilenol.board.Board;
import com.instantviking.tilenol.tiles.Tile;

public abstract class Style
{

  protected Random rand;
  protected List<Object> transitionalAlphabet;

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
}
