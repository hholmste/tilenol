package com.instantviking.tilenol.board.borders;

/**
 * 
 */
public enum WrappingRule
{
  /**
   * FREE_BORDERS mean that border-tiles may pick transitions freely from the
   * list of border-transitions.
   */
  FREE_BORDERS,
  /**
   * WRAP_NORTH_SOUTH mean that border-tiles on the northern or southern edge
   * must match the corresponding tile on the southern or northern edge.
   */
  WRAP_NORTH_SOUTH,
  /**
   * WRAP_EAST_WEST mean that border-tiles on the eastern or western edge must
   * match the corresponding tile on the western or eastern edge.
   */
  WRAP_EAST_WEST,
  /**
   * WRAP_ALWAYS mean that both WRAP_NORTH_SOUTH and WRAP_EAST_WEST is in
   * effect.
   */
  WRAP_ALWAYS
}
