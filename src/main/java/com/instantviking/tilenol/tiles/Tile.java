package com.instantviking.tilenol.tiles;


public class Tile
{

  public final Object east;
  public final Object west;
  public final Object north;
  public final Object south;

  public Tile(
      Object generatedNorth,
      Object generatedSouth,
      Object generatedEast,
      Object generatedWest)
  {
    this.north = generatedNorth;
    this.south = generatedSouth;
    this.east = generatedEast;
    this.west = generatedWest;
  }

  public Object getEast()
  {
    return east;
  }

  public Object getWest()
  {
    return west;
  }

  public Object getNorth()
  {
    return north;
  }

  public Object getSouth()
  {
    return south;
  }

}
