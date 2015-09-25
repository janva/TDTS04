package liu.janva.boardIterators;

import liu.janva.gameboard.Position;

public class RightDownIterator extends AbstractBoardIterator
{

    public RightDownIterator(int[][] boardImpl, Position startPosition)
    {
	super(boardImpl, startPosition);
    }

    @Override
    Object markAtNextSquare()
    {
	return  board[row++][col++];
    }
}
