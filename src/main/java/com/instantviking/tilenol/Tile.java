package com.instantviking.tilenol;

public class Tile
{
  public final int val;

  public Tile(int val)
  {
    this.val = val;
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
}
