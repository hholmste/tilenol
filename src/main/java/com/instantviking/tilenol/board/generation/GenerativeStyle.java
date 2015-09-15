package com.instantviking.tilenol.board.generation;

public enum GenerativeStyle
{
  /**
   * pick a random, unoccupied position, places a valid tile and repeats until
   * the board is filled
   */
  RANDOM_VALID,

  /**
   * Picks a random, unoccupied position, a places a tile While there are open
   * connections on the board, place tiles on the positions pointed to by the
   * connections.
   * 
   * Repeat until the board is filled
   * 
   * Really only makes sense with a two-element alphabet
   */
  FILL_CONNECTED
}
