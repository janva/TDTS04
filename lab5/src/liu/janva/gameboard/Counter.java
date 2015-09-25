package liu.janva.gameboard;

import liu.janva.boardIterators.AbstractBoardIterator;

public class Counter
{
    private Board board;
    private Position startingPosition;
    private int mark;

    final int WIN_LIMIT =5;
    
    public Counter(Board board, Position startingPosition) 
    {
	this.board=board;
	this.startingPosition = startingPosition;
	this.mark= board.getMarkAtPosition(startingPosition);
    }

    ///////////////////////////// Count Equals ///////////////////////////////////    
    public int countEqualshorizontally()
    {
	int total =0;
	total+= countEqualsToLeft();
	//-1 to because we count the square we are standing in twice
	total+=countEqualsToRight()-1;
	return total;
    }

    public int countEqualsVertically()
    {
	int total =0;
	total+= countEqualsAbove();
	total +=countEqualsBelow()-1;
	return total;
    }

    public int countEqualsDiagonallyToUpperRight()
    {

	int total =0;
	total += countEqualsToUpperRight();
	total += countEqualsToLowerLeft()-1;
	return total;
    }

    public int countEqualsDiagonallyToUpperLeft()
    {
	int total =0;
	total+= countEqualsToLowerRight();
	total+= countEqualsToUpperLeft()-1;
	return total;
    }
   ///////////////////////Santas Helpers /////////////////////////////////////////////
    private int countEqualsToLeft()
    {
	return count(board.leftIterator(startingPosition));
    }

    private int countEqualsToRight()
    {
	return count(board.rightIterator(startingPosition));
    }


    private int countEqualsAbove()
    {
	return count(board.upIterator(startingPosition));
    }

    private int countEqualsBelow()
    {
	return count(board.downIterator(startingPosition));
    }

    private int countEqualsToUpperRight()
    {
	return count(board.rightUpIterator(startingPosition));
    }
    private int countEqualsToLowerLeft()
    {
	return count(board.leftDownIterator(startingPosition));
    }

    private int countEqualsToLowerRight()
    {
	return count(board.rightDownIterator(startingPosition));
    }

    private int countEqualsToUpperLeft()
    {
	return count(board.leftUpIterator(startingPosition));
    }

    private int count (AbstractBoardIterator iter)
    {
	int totalInRow =0;

	while (iter.hasNext() && markSame((int)iter.next()))
	{
	    ++totalInRow;
	    //FIXME Bug cuts off count to early or to late depending 
	    // on surrounding marks or borders 
//	    if (isFiveInARow((++totalInRow)+1))
//	    {
//	       ++totalInRow;
//		break;
//	    }
	}

	return totalInRow;
    }
    
    //used by count when bug has been fixed
    private boolean isFiveInARow(int count)
    {
	return (count >= WIN_LIMIT);
    }

    private boolean markSame(int mark)
    {
	return this.mark == mark;

    }

}