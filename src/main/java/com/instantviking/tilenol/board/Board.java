package com.instantviking.tilenol.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.instantviking.tilenol.board.borders.WrappingRule;
import com.instantviking.tilenol.tiles.OffBoard;
import com.instantviking.tilenol.tiles.Tile;
import com.instantviking.tilenol.tiles.Uninitialized;

public class Board
{
  private final int width;
  private final int height;
  private final Tile[][] tiles;
  private final List<Position> generatedOrder;
  private final WrappingRule wrappingRule;

  public Board(int width, int height, WrappingRule wrappingRule)
  {
    this.wrappingRule = wrappingRule;
    this.width = width;
    this.height = height;
    tiles = new Tile[width][height];
    for (int i = 0; i < width; i++)
    {
      for (int j = 0; j < height; j++)
      {
        tiles[i][j] = new Uninitialized();
      }
    }
    generatedOrder = new ArrayList<Position>();
  }

  public int getWidth()
  {
    return width;
  }

  public int getHeight()
  {
    return height;
  }

  /**
   * Retrieves tile at given coordinate, not respecting wrapping-rules, instead
   * dying with {@link IndexOutOfBoundsException} on coordinates outside the
   * board.
   */
  public Tile getTile(int x, int y)
  {
    return tiles[x][y];
  }

  /**
   * Finds tile at given coordinates, respecting wrapping rules
   */
  public Tile findTile(int x, int y)
  {
    int adjustedX = adjustXForWrapping(x);
    int adjustedY = adjustYForWrapping(y);

    if (adjustedX < 0
        || adjustedX >= this.width || adjustedY < 0 || adjustedY >= this.height)
    {
      return new OffBoard();
    }
    return tiles[adjustedX][adjustedY];
  }

  private int adjustYForWrapping(int y)
  {
    if (y < 0 && wrapsNorthSouth())
    {
      return this.height - 1;
    }
    if (y >= this.height && wrapsNorthSouth())
    {
      return 0;
    }
    return y;
  }

  private int adjustXForWrapping(int x)
  {
    if (x < 0 && wrapsEastWest())
    {
      return this.width - 1;
    }
    if (x >= this.width && wrapsEastWest())
    {
      return 0;
    }
    return x;
  }

  private boolean wrapsEastWest()
  {
    return wrappingRule == WrappingRule.WRAP_ALWAYS
        || wrappingRule == WrappingRule.WRAP_EAST_WEST;
  }

  private boolean wrapsNorthSouth()
  {
    return wrappingRule == WrappingRule.WRAP_ALWAYS
        || wrappingRule == WrappingRule.WRAP_NORTH_SOUTH;
  }

  public boolean isInitialized(Position position)
  {
    return !Uninitialized.class.isInstance(findTile(position.x, position.y));
  }

  public List<Position> getGeneratedOrder()
  {
    return Collections.unmodifiableList(generatedOrder);
  }

  public void putTile(Tile tile, Position position)
  {
    tiles[position.x][position.y] = tile;
    generatedOrder.add(position);
  }

  @Override
  public String toString()
  {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < height; i++)
    {
      for (int j = 0; j < width; j++)
      {
        builder.append(String.format("%24s", tiles[j][i])).append(" ");
      }
      builder.append("\n");
    }
    return builder.toString();
  }
}
