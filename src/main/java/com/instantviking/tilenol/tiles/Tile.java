package com.instantviking.tilenol.tiles;

import com.instantviking.tilenol.board.Position;

public class Tile
{

  public final int val;

  public Tile(int val)
  {
    this.val = val;
  }

  private boolean pointsInDirection(int direction)
  {
    return (val & direction) == direction;
  }

  @Override
  public String toString()
  {
    return String.format(
        "{ w:%s n:%s s:%s e:%s (%2s)}",
        (val & Tiles.WEST) == Tiles.WEST ? "<" : " ",
        (val & Tiles.NORTH) == Tiles.NORTH ? "^" : " ",
        (val & Tiles.SOUTH) == Tiles.SOUTH ? "v" : " ",
        (val & Tiles.EAST) == Tiles.EAST ? ">" : " ",
        val);
  }

  public PositionedTile at(Position source)
  {
    return new PositionedTile(this, source);
  }

  public static class PositionedTile
  {
    private final Tile tile;
    private final Position position;

    public PositionedTile(Tile tile, Position position)
    {
      this.tile = tile;
      this.position = position;
    }

    public boolean pointsAt(Position target)
    {
      if (position.x == target.x)
      {
        if (position.y > target.y)
        {
          return tile.pointsInDirection(Tiles.NORTH);
        } else
        {
          return tile.pointsInDirection(Tiles.SOUTH);
        }
      } else
      {
        if (position.x > target.x)
        {
          return tile.pointsInDirection(Tiles.WEST);
        } else
        {
          return tile.pointsInDirection(Tiles.EAST);
        }
      }
    }
  }
}
