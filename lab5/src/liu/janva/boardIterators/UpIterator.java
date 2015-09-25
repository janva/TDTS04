package liu.janva.boardIterators;

import java.util.NoSuchElementException;

import liu.janva.gameboard.Position;

public class UpIterator extends AbstractBoardIterator
{

    public UpIterator (int[][] boardImpl, Position startPosition)
    {
	super(boardImpl,startPosition);
    }
    
    @Override 
    public Object next() throws NoSuchElementException
    {
	if (!insideBoard(row,col))
	{
	    throw new NoSuchElementException();
	}
	return  board[row--][col];
    }
}
