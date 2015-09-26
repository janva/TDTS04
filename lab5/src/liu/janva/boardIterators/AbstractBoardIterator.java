package liu.janva.boardIterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import liu.janva.gameboard.Position;

//https://web.archive.org/web/20130516014426/http://www.oracle.com/technetwork/java/codeconventions-141855.html#1852
//hmm order methods according to functionality rather than scope or
//or accessibility
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
    
    private boolean insideBoard(int row, int column )
    {
	//better to be paranoid
	return (column >=0 &&
		row >=0 &&
		column < getWidth()&&
		row < getHeight());
	
    }
    
    private int getWidth()
    {
	return board[0].length;
    }
    
    private int getHeight ()
    {
	return board.length; 

    }

    @Override
    final public Object next()
    {
	if (!insideBoard(row,col))
	{
	    throw new NoSuchElementException();
	}
	return markAtNextSquare();
    }

    abstract Object markAtNextSquare();

   
    
    @Override
    public void remove() throws UnsupportedOperationException
    {
	throw new UnsupportedOperationException();
		
    }
}
