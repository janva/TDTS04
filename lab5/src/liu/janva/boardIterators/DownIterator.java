package liu.janva.boardIterators;

import java.util.NoSuchElementException;

import liu.janva.gameboard.Position;

public class DownIterator extends AbstractBoardIterator
{

    public DownIterator(int[][] boardImpl, Position startPosition)
    {
	super(boardImpl, startPosition);
    }
//TODO tests for this class
    @Override //TODO template methodpattern a
    public Object next()
    {
	if (!insideBoard(row,col))
	{
	    throw new NoSuchElementException();
	}
	return  board[row++][col];
    }

}
