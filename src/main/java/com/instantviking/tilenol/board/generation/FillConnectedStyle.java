package com.instantviking.tilenol.board.generation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.stream.Collectors;

import com.instantviking.tilenol.board.Board;
import com.instantviking.tilenol.board.Position;
import com.instantviking.tilenol.tiles.Tile;

final class FillConnectedStyle implements Style
{

  @Override
  public Board generate(int width, int height, Random rand)
  {
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
      List<Tile> options = board
          .tileOptionsAt(currentSource.x, currentSource.y);
      Tile nextGeneratedTile = options.get(rand.nextInt(options.size()));
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
        options = board.tileOptionsAt(
            connectedNeighbour.x,
            connectedNeighbour.y);
        nextGeneratedTile = options.get(rand.nextInt(options.size()));
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

  private List<Position> connectedNeighbouringPositions(
      Tile tile,
      Position position,
      Board board)
  {
    return position
        .neighbours()
        .stream()
        .filter(neighbour -> validPosition(neighbour, board))
        .filter(neighbour -> !board.isInitialized(neighbour))
        .filter(neighbour -> tilePointsAt(position, neighbour, board))
        .collect(Collectors.toList());
  }

  private boolean tilePointsAt(Position source, Position target, Board board)
  {
    return board.findTile(source.x, source.y).at(source).pointsAt(target);
  }

  private boolean validPosition(Position position, Board board)
  {
    return position.x >= 0
        && position.x < board.getWidth() && position.y >= 0
        && position.y < board.getHeight();
  }

  public static void main(String... args)
  {
    Board b = new FillConnectedStyle().generate(4, 2, new Random(1L));
    System.out.println(b);
  }
}
