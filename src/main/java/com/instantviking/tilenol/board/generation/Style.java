package com.instantviking.tilenol.board.generation;

import java.util.Random;

import com.instantviking.tilenol.board.Board;

public interface Style
{

  Board generate(int width, int height, Random rand);

}
