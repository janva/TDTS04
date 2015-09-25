package liu.janva.boardIterators;

import liu.janva.gameboard.Position;

public class LeftIterator extends AbstractBoardIterator
{
    public LeftIterator(int[][] boardImpl, Position startPosition)
    {
	super(boardImpl,startPosition);
    }

    @Override
    Object markAtNextSquare()
    {
	return  board[row][col--];
    }

}
