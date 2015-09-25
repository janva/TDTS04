package liu.janva.boardIterators;

import java.util.NoSuchElementException;

import liu.janva.gameboard.Position;

public class RightDownIterator extends AbstractBoardIterator
{

    public RightDownIterator(int[][] boardImpl, Position startPosition)
    {
	super(boardImpl, startPosition);
    }

    @Override
    public Object next()
    {
	if (!insideBoard(row,col))
	{
	    throw new NoSuchElementException();
	}
	return  board[row++][col++];
    }

}
