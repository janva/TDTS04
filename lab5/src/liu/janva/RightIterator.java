package liu.janva;

import java.util.NoSuchElementException;

public class RightIterator extends AbstractBoardIterator
{
    
    public RightIterator(final int[][] boardImpl,Position startPosition)
    {
	super(boardImpl, startPosition);
    }

       
    @Override
    public Object next() throws NoSuchElementException
    {
	if (!insideBoard(row,col))
	{
	    throw new NoSuchElementException();
	}
	return  board[row][col++];
    }
    
}