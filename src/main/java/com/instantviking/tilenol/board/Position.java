package com.instantviking.tilenol.board;

import java.util.Arrays;
import java.util.List;

public class Position
{
  public final int x;
  public final int y;

  public Position(int x, int y)
  {
    this.x = x;
    this.y = y;
  }

  public List<Position> neighbours()
  {
    return Arrays.asList(
        new Position(x - 1, y),
        new Position(x + 1, y),
        new Position(x, y - 1),
        new Position(x, y + 1));
  }

  @Override
  public String toString()
  {
    return String.format("Pos[x=%s, y=%s]", x, y);
  }

  @Override
  public boolean equals(Object o)
  {
    if (Position.class.isInstance(o))
    {
      Position p = (Position) o;
      return p.x == this.x && p.y == this.y;
    }
    return false;
  }
}