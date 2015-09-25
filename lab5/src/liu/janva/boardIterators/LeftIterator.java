package liu.janva.boardIterators;

import java.util.NoSuchElementException;

import liu.janva.gameboard.Position;

public class LeftIterator extends AbstractBoardIterator
{
    public LeftIterator(int[][] boardImpl, Position startPosition)
    {
	super(boardImpl,startPosition);
    }

    //TODO template method? or just parameterize
    @Override 
    public Object next() throws NoSuchElementException
    {
	if (!insideBoard(row,col))
	{
	    throw new NoSuchElementException();
	}
	return  board[row][col--];
    }
}
