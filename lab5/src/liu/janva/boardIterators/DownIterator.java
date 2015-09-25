package liu.janva.boardIterators;

import liu.janva.gameboard.Position;

public class DownIterator extends AbstractBoardIterator
{

    public DownIterator(int[][] boardImpl, Position startPosition)
    {
	super(boardImpl, startPosition);
    }

    @Override
    Object markAtNextSquare()
    {
	return  board[row++][col];
    }
    

}
