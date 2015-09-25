package liu.janva.boardIterators;

import liu.janva.gameboard.Position;

public class RightUpIterator extends AbstractBoardIterator
{

    public RightUpIterator(int[][] boardImpl, Position startPosition)
    {
	super(boardImpl, startPosition);
    }

    @Override
    Object markAtNextSquare()
    {
	return  board[row--][col++];
    }

}
