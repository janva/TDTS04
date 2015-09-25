package liu.janva.boardIterators;

import java.util.Iterator;

import liu.janva.gameboard.Position;

public abstract class AbstractBoardIterator implements Iterator
{
    protected final int[][] board;
    protected int row;
    protected int col;
    
    public AbstractBoardIterator (final int[][] boardImpl,
	    Position startPosition)
    {
	row = startPosition.getRow();
	col = startPosition.getCol();
	this.board = boardImpl;
    }
    
    @Override
    public boolean hasNext()
    {
	return insideBoard(row,col);
    }

    protected int getWidth()
    {
	return board[0].length;
    }
    
    protected int getHeight ()
    {
	return board.length; 

    }
    
    protected boolean insideBoard(int row, int column )
    {
	//better to be paranoid
	return (column >=0 &&
		row >=0 &&
		column < getWidth()&&
		row < getWidth());
    }
    
    @Override
    public void remove() throws UnsupportedOperationException
    {
	throw new UnsupportedOperationException();
		
    }


}
