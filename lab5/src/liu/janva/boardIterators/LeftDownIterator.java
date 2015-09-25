package liu.janva.boardIterators;

import liu.janva.gameboard.Position;

public class LeftDownIterator extends AbstractBoardIterator
{
    public LeftDownIterator(int[][] boardImpl, Position startPosition)
    {
	super(boardImpl, startPosition);
    }

    @Override
    Object markAtNextSquare()
    {
	return  board[row--][col--];
    }
}
