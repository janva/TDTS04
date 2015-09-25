package liu.janva.boardIterators;

import liu.janva.gameboard.Position;

public class RightIterator extends AbstractBoardIterator
{
    
    public RightIterator(final int[][] boardImpl,Position startPosition)
    {
	super(boardImpl, startPosition);
    }

    @Override
    Object markAtNextSquare()
    {
	return  board[row][col++];
    }

}
