package liu.janva.boardIterators;

import liu.janva.gameboard.Position;

public class LeftUpIterator extends AbstractBoardIterator
{

    public LeftUpIterator(int[][] boardImpl, Position startPosition)
    {
	super(boardImpl, startPosition);
    }

    @Override
    Object markAtNextSquare()
    {
	return  board[row--][col--];
    }

}
