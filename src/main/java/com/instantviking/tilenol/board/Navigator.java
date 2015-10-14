package com.instantviking.tilenol.board;

import java.util.Optional;

import com.instantviking.tilenol.board.borders.WrappingRule;
import com.instantviking.tilenol.tiles.OffBoard;
import com.instantviking.tilenol.tiles.Tile;
import com.instantviking.tilenol.tiles.Uninitialized;

/**
 * a board will probably need to be able to create navigators. This means that a
 * board will need to know all the things that the style knows and that the
 * navigator will use for things, at least wrapping-rules, and probably
 * transitions for some simple validation.
 */
public class Navigator
{

  private final Board board;
  private final WrappingRule wrappingRule;
  private Position currentPosition;

  public Navigator(Board board, WrappingRule wrappingRule)
  {
    this.board = board;
    this.wrappingRule = wrappingRule;
  }

  /**
   * Finds out if the navigator can move in the given direction.
   * 
   * This will be true unless the navigator is on the board's edge, the
   * direction leads across that edge, and there are no legal wrappings.
   */
  public boolean canMove(Direction direction)
  {
    return calculateMove(direction).isPresent();
  }

  /**
   * Updates the navigator's current position by moving and possibly wrapping
   * around the edges of the board
   */
  public boolean move(Direction direction)
  {
    Optional<Position> nextPosition = calculateMove(direction);
    if (nextPosition.isPresent())
    {
      currentPosition = nextPosition.get();
      return true;
    }
    return false;
  }

  /**
   * Returns the Tile at the navigator's current position
   */
  public Tile currentTile()
  {
    return board.getTile(currentPosition.x, currentPosition.y);
  }

  public Position getCurrentPosition()
  {
    return this.currentPosition;
  }

  public void reposition(Position position)
  {
    if (position.x < 0
        || position.x >= board.getHeight() || position.y < 0
        || position.y >= board.getHeight())
    {
      throw new IllegalArgumentException("Position is outside the board.");
    }
    this.currentPosition = position;
  }

  /**
   * Look in the given direction and return the tile that lives there.
   * 
   * Returns {@link Uninitialized} if there are no tiles available
   * 
   * Returns {@link OffBoard} if the direction leads off board with no available
   * wrapping
   */
  public Tile scout(Direction direction)
  {
    Optional<Position> scoutedPosition = calculateMove(direction);
    if (scoutedPosition.isPresent())
    {
      Position realScoutedPosition = scoutedPosition.get();
      return board.getTile(realScoutedPosition.x, realScoutedPosition.y);
    }
    return new OffBoard();
  }

  /**
   * returns a Position that is the next step in the given direction, or
   * Optional.empty if it is not possible to move in that direction
   */
  public Optional<Position> calculateMove(Direction direction)
  {
    switch (direction)
    {
    case East:
      Position moveEast = new Position(
          this.currentPosition.x + 1,
          this.currentPosition.y);
      if (moveEast.x >= board.getWidth() && !wrappingRule.wrapsEastWest())
      {
        return Optional.empty();
      } else
      {
        return Optional.of(moveEast);
      }
    case West:
      Position moveWest = new Position(
          this.currentPosition.x - 1,
          this.currentPosition.y);
      if (moveWest.x < 0 && !wrappingRule.wrapsEastWest())
      {
        return Optional.empty();
      } else
      {
        return Optional.of(moveWest);
      }
    case North:
      Position moveNorth = new Position(
          this.currentPosition.x,
          this.currentPosition.y - 1);
      if (moveNorth.x < 0 && !wrappingRule.wrapsNorthSouth())
      {
        return Optional.empty();
      } else
      {
        return Optional.of(moveNorth);
      }
    case South:
      Position moveSouth = new Position(
          this.currentPosition.x,
          this.currentPosition.y + 1);
      if (moveSouth.x >= board.getHeight() && !wrappingRule.wrapsNorthSouth())
      {
        return Optional.empty();
      } else
      {
        return Optional.of(moveSouth);
      }

    default:
      return Optional.empty();
    }
  }
}
