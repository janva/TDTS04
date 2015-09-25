package liu.janva.boardIterators;

import liu.janva.gameboard.Position;

public class UpIterator extends AbstractBoardIterator
{

    public UpIterator (int[][] boardImpl, Position startPosition)
    {
	super(boardImpl,startPosition);
    }

    @Override
    Object markAtNextSquare()
    {
	return  board[row--][col];
    }

}
