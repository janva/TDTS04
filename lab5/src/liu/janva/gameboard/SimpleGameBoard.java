package liu.janva.gameboard;

import liu.janva.boardIterators.DownIterator;
import liu.janva.boardIterators.LeftDownIterator;
import liu.janva.boardIterators.LeftIterator;
import liu.janva.boardIterators.LeftUpIterator;
import liu.janva.boardIterators.RightDownIterator;
import liu.janva.boardIterators.RightIterator;
import liu.janva.boardIterators.RightUpIterator;
import liu.janva.boardIterators.UpIterator;

//TODO some cleaning needed here as wellS
public class SimpleGameBoard implements Board{
    private Checker checker;
    //TODO protected to simplify testing think of better solution 
    protected int[][] board;
    public SimpleGameBoard(Checker checker, int boardSize)
    {
	this.checker = checker;
	this.board = new int[boardSize][boardSize];
    }

    //TODO mark  needs to be abstract type not int
    public boolean markPosition(Position pos, int mark) {
	if(outsideBoard(pos))
	{
	    return false;
	}
	int current = board[pos.getRow()][pos.getCol()] ;
	if(current == 0)
	{
	    board[pos.getRow()][pos.getCol()] = mark;
	    return true;
	}
	return false;
    }

    public int getHeight ()
    {
	return board.length; 

    }

    public int getWidth()
    {
	return board[0].length;
    }

    public void clearBoard()
    {
	for (int i = 0; i < board.length; ++i)
	{
	    for (int j= 0; j < board[i].length; ++j)
	    { 
		//0 =-, 1=X, 2=O  
		board[i][j]= 0;
	    }
	}
    }

    public int getMarkAtPosition(Position pos)
    {
	return board[pos.getRow()][pos.getCol()];
    }

    public boolean checkWin(Position lastMove)
    {
	return checker.checkWin (this,lastMove);
    }


    public String toString()
    {
	StringBuilder builder = new StringBuilder();
	//TODO me not like
	for(int i =0; i < getHeight(); ++i)
	{
	    for(int j= 0; j < getWidth(); ++j)
	    {
		builder.append("[");

		int mark = board[i][j];
		if (mark == 0)
		{
		    builder.append('-');

		}else
		{
		    //TODO Again mark abstract type now we have dependencies scattered all over the place
		    builder.append(mark == 1? "X":"O");
		}
		builder.append("]");
	    }
	    builder.append("\n");
	}
	return builder.toString();
    }
    
    
    // TODO bläää thought of this at late stage know  sucks
    @Override
    public boolean full() {
	for(int i =0; board.length > i; ++i)
	{
	    for(int j=0; board.length > j; ++j)
	    {
		if (board[i][j]== 0)
		    return false;
	    }
	}
	return false;
    }

    @Override
    public RightIterator rightIterator(Position startFrom)
    {
	return new RightIterator(board,startFrom);
    }
    @Override
    public LeftIterator leftIterator(Position startFrom)
    {
	return new LeftIterator(board,startFrom);
    }
    @Override
    public UpIterator upIterator(Position startFrom)
    {
	return new UpIterator(board,startFrom);
    }
    @Override
    public DownIterator downIterator(Position startFrom)
    {
	return new DownIterator(board,startFrom);
    }

    @Override
    public RightUpIterator rightUpIterator(Position startFrom)
    {
	return new RightUpIterator(board, startFrom);
    }

    @Override
    public LeftUpIterator leftUpIterator(Position startFrom)
    {
	return new LeftUpIterator(board, startFrom);
    }

    @Override
    public RightDownIterator rightDownIterator(Position startFrom)
    {
	return new RightDownIterator(board, startFrom);
    }

    @Override
    public LeftDownIterator leftDownIterator(Position startFrom)
    {
	return new LeftDownIterator(board, startFrom);
    }
    
  //TODO code duplication iterators
    private boolean outsideBoard(Position pos)
    {
	int row = pos.getRow(); 
	int col =pos.getCol();
	return !((row <= board.length) && (col <= board[0].length)
		&& row >= 0 && col>=0);
    }
    
    public static class test
    {
	public static void main(String[] args) {
	    SimpleGameBoard board = new SimpleGameBoard(new SimpleCheck(), 5);
	    board.toString();
	    System.out.println(
		    board.getMarkAtPosition(new Position(3, 2)));
	}
    }
    
    


}
