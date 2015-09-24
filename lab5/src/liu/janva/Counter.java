package liu.janva;

public class Counter
{
    private Board board;
    private Position startingPosition;
    private int mark;
    
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
	total+=countEqualsToRight()-1;
	return total;
    }
    
    public int countEqualsVertically()
    {
	//
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

    private int countEqualsToLeft()
    {
	return count(board.leftIterator(startingPosition));
//
//	int nextIncrementer=1;
//	int totalInRow =0;
//	while (isPosToLeftInsideBoard(nextIncrementer) && 
//		isMarkToLeftSame(nextIncrementer))
//	{
//	    ++nextIncrementer;
//	    if (isFiveInARow((++totalInRow) +1))
//	    {
//		break;
//	    }
//	}
//	return totalInRow;
    }
    
    private int countEqualsToRight()
    {
	return count(board.rightIterator(startingPosition));
    }

    
    private int countEqualsAbove()
    {
	return count(board.upIterator(startingPosition));
//	int nextIncrementer=1;
//	int totalInRow =0;
//	while (isPosAboveInsideBoard(nextIncrementer)
//		&& isMarkAboveSame(nextIncrementer))
//	{
//	    ++nextIncrementer;
//	    if (isFiveInARow((++totalInRow) +1))
//	    {
//		break;
//	    }
//	}
//	return totalInRow;
    }

    private int countEqualsBelow()
    {
	return count(board.downIterator(startingPosition));
//	int nextIncrementer=1;
//	int totalInRow =0;
//	while (isPosBelowInsideBoard(nextIncrementer) 
//		&& isMarkBelowSame(nextIncrementer))
//	{
//	    ++nextIncrementer;
//	    if (isFiveInARow((++totalInRow) +1))
//	    {
//		break;
//	    }
//	}
//	return totalInRow;
    }
   
    
    private int countEqualsToUpperRight()
    {
	//TODO try to break this dependency
	return count(board.rightUpIterator(startingPosition));
//	int nextIncrementer =1;
//	int total = 0;
//	while (isPosAboveInsideBoard(nextIncrementer) && isPosToRightInsideBoard(nextIncrementer) && isMarkAboveRightSame(nextIncrementer))
//	{
//	    ++nextIncrementer;
//	    if (isFiveInARow(++total))
//		break;
//	}
//	return total;
    }
    private int countEqualsToLowerLeft()
    {
	return count(board.leftDownIterator(startingPosition));
//	int nextIncrementer=1;
//	int total = 0;
//	while (isPosBelowInsideBoard(nextIncrementer)
//		&& isPosToLeftInsideBoard(nextIncrementer) 
//		&& isMarkBelowLeftSame(nextIncrementer))
//	{
//	    ++nextIncrementer;
//	    if (isFiveInARow(++total))
//		break;
//	}
//	return total;
    }
   
    private int countEqualsToLowerRight()
    {
	return count(board.rightDownIterator(startingPosition));
//	int nextIncrementer=1;
//	int totalNrInRow=0;
//	while(isPosBelowInsideBoard(nextIncrementer) && 
//		isPosToRightInsideBoard(nextIncrementer) &&
//		isMarkBelowRightSame(nextIncrementer))
//	{
//	    nextIncrementer++;
//	    if (isFiveInARow(++totalNrInRow))
//	    {
//		break;
//	    }
//	}
//	return totalNrInRow;
    }

    //TODO the only thing differing is the while condition
    private int countEqualsToUpperLeft()
    {
	return count(board.leftUpIterator(startingPosition));
//	int nextIncrementer=1;
//	int totalInRow =0;
//	//while (posInsideBoard(new Position((startingPosition.getRow() - nextIncrementer), startingPosition.getCol())) && isPosToLeftInsideBoard(nextIncrementer) 
//	while (isPosAboveInsideBoard(nextIncrementer) && isPosToLeftInsideBoard(nextIncrementer) 
//		&& isMarkAboveLeftSame(nextIncrementer))
//	{
//	    ++nextIncrementer;
//	    if (isFiveInARow((++totalInRow) +1))
//	    {
//		break;
//	    }
//	}
//	return totalInRow;
    }
    private int count (AbstractBoardIterator iter)
    {
	int totalInRow =0;
	
	while (iter.hasNext() && markSame((int)iter.next()))
	{
	    if (isFiveInARow((++totalInRow) +1))
	    {
			break;
	    }
	}

	return totalInRow;
    }
    /////////////////////////////////////isInsideBoard//////////////////////////////
    //TODO consider pasing positon instead intention more clear that way
//    private boolean isPosAboveInsideBoard(int nextIncrementer)
//    {
//	return startingPosition.getRow() - nextIncrementer >= 0;
//    }

//    private boolean isPosBelowInsideBoard(int nextIncrementer)
//    {
//	return nextIncrementer + startingPosition.getRow() < board.getHeight();
//    }
//    private boolean isPosToRightInsideBoard(int nextIncrementer)
//    {
//	return (nextIncrementer + startingPosition.getCol()) < board.getWidth();
//    }


//    private boolean isPosToLeftInsideBoard(int nextIncrementer)
//    {
//	return (startingPosition.getCol() - nextIncrementer) >= 0;
//    }
    
    ////////////////////////////////////isMarkSame//////////////////////////////////
//    private boolean isMarkAboveRightSame(int nextIncrementer)
//    {
//	return markEqAtpos(board,
//		(new Position(startingPosition.getRow() - nextIncrementer, 
//			startingPosition.getCol() + nextIncrementer)), mark);
//    }
    
//    private boolean isMarkBelowLeftSame(int nextIncrementer)
//    {
//	return board.getMarkAtPosition(
//		new Position(startingPosition.getRow() + nextIncrementer, 
//			startingPosition.getCol() - nextIncrementer)) == mark;
//    }
    
//    private boolean isMarkToRightSame(int nextIncrementer)
//    {
//	return board
//		.getMarkAtPosition(new Position((startingPosition.getRow()),
//			(startingPosition.getCol() + nextIncrementer))) == mark;
//    }

//    private boolean isMarkToLeftSame(int nextIncrementer)
//    {
//	return board.getMarkAtPosition(new Position(startingPosition.getRow(), startingPosition.getCol() - nextIncrementer)) == mark;
//    }

//    private boolean isMarkBelowRightSame(int nextIncrementer)
//    {
//	return board.getMarkAtPosition(
//		new Position(startingPosition.getRow() + nextIncrementer, startingPosition.getCol() + nextIncrementer)) == mark;
//    }

//    private boolean isMarkAboveLeftSame(int nextIncrementer)
//    {
//	return markEqAtpos(board,
//		new Position((startingPosition.getRow() - nextIncrementer), (startingPosition.getCol() - nextIncrementer)), mark);
//
//    }

//    private boolean isMarkBelowSame(int nextIncrementer)
//    {
//	return markEqAtpos(board, new Position(startingPosition.getRow() + nextIncrementer, startingPosition.getCol()), mark);
//    }

//    private boolean isMarkAboveSame(int nextIncrementer)
//    {
//	return markEqAtpos(board, new Position((startingPosition.getRow() - nextIncrementer), startingPosition.getCol()), mark);
//    }

//////////////////////////////COMMON///////////////////////////////////////
    // conveys intention better
//    private boolean markEqAtpos(Board board, Position pos, int mark)
//    {
//	return board.getMarkAtPosition(pos) == mark;
//    }
    //TODO cutOffBehaviour class maybe strategy maybe a bit over board
    final int WIN_LIMIT =5;
    private boolean isFiveInARow(int count)
    {
	return (count >= WIN_LIMIT);
    }
    
    private boolean markSame(int mark)
    {
	return this.mark == mark;
	
    }

}