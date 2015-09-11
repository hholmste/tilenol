package com.instantviking.tilenol.board.generation;


public final class StyleResolver
{
  public static Style resolve(GenerativeStyle styleHint)
  {
    switch (styleHint)
    {
    case FILL_CONNECTED:
      return new FillConnectedStyle();
    default:
      return new RandomValidStyle();
    }
  }
}
