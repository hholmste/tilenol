package com.instantviking.tilenol;

public enum Border
{
  EAST, WEST, NORTH, SOUTH;

  public Border opposite()
  {
    if (this == EAST)
      return WEST;
    if (this == WEST)
      return EAST;
    if (this == NORTH)
      return SOUTH;
    return NORTH;
  }
}
