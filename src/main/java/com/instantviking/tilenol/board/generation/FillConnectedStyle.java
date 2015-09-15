package com.instantviking.tilenol.board.generation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.instantviking.tilenol.board.Board;
import com.instantviking.tilenol.board.Position;
import com.instantviking.tilenol.tiles.Tile;

final class FillConnectedStyle extends Style
{

  @Override
  public Board generate(int width, int height)
  {
    if (transitionalAlphabet.size() != 2)
    {
      throw new IllegalStateException(
          "Can't use FILL_CONNECTED-generation with alphabets with more than two elements.");
    }

    Board board = new Board(width, height);

    List<Position> unvisitedPositions = new ArrayList<Position>();
    for (int i = 0; i < width; i++)
    {
      for (int j = 0; j < height; j++)
      {
        unvisitedPositions.add(new Position(i, j));
      }
    }

    while (!unvisitedPositions.isEmpty())
    {
      int index = rand.nextInt(unvisitedPositions.size());

      Position currentSource = unvisitedPositions.get(index);
      unvisitedPositions.remove(index);
      Tile nextGeneratedTile = generateTileAt(
          currentSource.x,
          currentSource.y,
          board);
      board.putTile(nextGeneratedTile, currentSource);

      Stack<Position> connectedNeighbours = new Stack<>();
      connectedNeighbouringPositions(nextGeneratedTile, currentSource, board)
          .stream()
          .forEach(connectedNeighbour ->
          {
            connectedNeighbours.push(connectedNeighbour);
            unvisitedPositions.remove(connectedNeighbour);
          });

      while (!connectedNeighbours.isEmpty())
      {
        Position connectedNeighbour = connectedNeighbours.pop();
        nextGeneratedTile = generateTileAt(
            connectedNeighbour.x,
            connectedNeighbour.y,
            board);
        board.putTile(nextGeneratedTile, connectedNeighbour);

        connectedNeighbouringPositions(
            nextGeneratedTile,
            connectedNeighbour,
            board).stream().forEach(n ->
        {
          connectedNeighbours.push(n);
          unvisitedPositions.remove(n);
        });
      }
    }

    return board;
  }

  /*
   * find uninitialized neighbors that the tile has an open connection to.
   */
  private List<Position> connectedNeighbouringPositions(
      Tile tile,
      Position position,
      Board board)
  {
    Object transitionToTheNorth = board.findTile(position.x, position.y - 1).south;
    Object transitionToTheSouth = board.findTile(position.x, position.y + 1).north;
    Object transitionToTheEast = board.findTile(position.x + 1, position.y).west;
    Object transitionToTheWest = board.findTile(position.x - 1, position.y).east;

    return Arrays
        .asList(
            (transitionToTheNorth == null) ? new Position(
                position.x,
                position.y - 1) : null,
            (transitionToTheSouth == null) ? new Position(
                position.x,
                position.y + 1) : null,
            (transitionToTheEast == null) ? new Position(
                position.x + 1,
                position.y) : null,
            (transitionToTheWest == null) ? new Position(
                position.x - 1,
                position.y) : null)
        .stream()
        .filter(n -> n != null)
        .filter(n -> isValidPosition(n, board))
        .collect(Collectors.toList());
  }

  private boolean isValidPosition(Position n, Board board)
  {
    return n.x >= 0
        && n.y >= 0 && n.x < board.getWidth() && n.y < board.getHeight();
  }

  public static void main(String... args)
  {
    FillConnectedStyle style = new FillConnectedStyle();
    style.transitionalAlphabet = Arrays.asList(" ", "=");
    style.rand = new Random(1L);
    Board b = style.generate(3, 2);

    IntStream.range(0, b.getHeight()).forEach(row ->
    {
      IntStream.range(0, b.getWidth()).forEach(column ->
      {
        Tile t = b.findTile(column, row);
        System.err.printf("  %s  ", t.north);
      });
      System.out.println(" ");
      IntStream.range(0, b.getWidth()).forEach(column ->
      {
        Tile t = b.findTile(column, row);
        System.err.printf(" %sX%s ", t.west, t.east);
      });
      System.out.println(" ");
      IntStream.range(0, b.getWidth()).forEach(column ->
      {
        Tile t = b.findTile(column, row);
        System.err.printf("  %s  ", t.south);
      });
      System.out.println("\n");
    });
  }
}
