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
    /////////////////////////////HORIZONTAL///////////////////////////////////
    public int countEqualshorizontally()
    {
	int total =1;
	total+= countSameToLeft();
	total+=countSameToRight();
	return total;
    }
    
    private int countSameToLeft()
    {
	int nextIncrementer=1;
	int totalInRow =0;
	while (isPosToLeftInsideBoard(nextIncrementer) && isMarkToLeftSame(nextIncrementer))
	{
	    ++nextIncrementer;
	    if (isFiveInARow((++totalInRow) +1))
	    {
		break;
	    }
	}
	return totalInRow;
    }
    //TODO cutOffBehaviour class maybe strategy maybe a bit over board
    final int WIN_LIMIT =5;
    private boolean isFiveInARow(int count)
    {
	return (count >= WIN_LIMIT);
    }
    
    private int countSameToRight()
    {
	int nextIncrementer=1;
	int totalInRow =0;
	while (isPosToRightInsideBoard(nextIncrementer) && isMarkToRightSame(nextIncrementer))
	{
	    ++nextIncrementer;
	    if (isFiveInARow((++totalInRow) +1))
	    {
		break;
	    }
	}
	return totalInRow;
    }
    private boolean isPosToRightInsideBoard(int nextIncrementer)
    {
	return (nextIncrementer + startingPosition.getCol()) < board.getWidth();
    }

    private boolean isMarkToRightSame(int nextIncrementer)
    {
	return board
		.getMarkAtPosition(new Position((startingPosition.getRow()), (startingPosition.getCol() + nextIncrementer))) == mark;
    }

    private boolean isMarkToLeftSame(int nextIncrementer)
    {
	return board.getMarkAtPosition(new Position(startingPosition.getRow(), startingPosition.getCol() - nextIncrementer)) == mark;
    }

    private boolean isPosToLeftInsideBoard(int nextIncrementer)
    {
	return (startingPosition.getCol() - nextIncrementer) >= 0;
    }
    /////////////////////////////VERTICALLY///////////////////////////////////
    public int countEqualsVertically()
    {
	int total =1;
	total+= countSameAbove();
	total +=countSameBelow();
	return total;
    }
    private int countSameAbove()
    {
	int nextIncrementer=1;
	int totalInRow =0;
	while (isPosAboveInsideBoard(nextIncrementer) && isMarkAboveSame(nextIncrementer))
	{
	    ++nextIncrementer;
	    if (isFiveInARow((++totalInRow) +1))
	    {
		break;
	    }
	}
	return totalInRow;
    }

    private int countSameBelow()
    {
	int nextIncrementer=1;
	int totalInRow =0;
	while (isPosBelowInsideBoard(nextIncrementer) && isMarkBelowSame(nextIncrementer))
	{
	    ++nextIncrementer;
	    if (isFiveInARow((++totalInRow) +1))
	    {
		break;
	    }
	}
	return totalInRow;
    }
    private boolean isMarkBelowSame(int nextIncrementer)
    {
	return markEqAtpos(board, new Position(startingPosition.getRow() + nextIncrementer, startingPosition.getCol()), mark);
    }

    private boolean isMarkAboveSame(int nextIncrementer)
    {
	return markEqAtpos(board, new Position((startingPosition.getRow() - nextIncrementer), startingPosition.getCol()), mark);
    }

    private boolean isPosAboveInsideBoard(int nextIncrementer)
    {
	return startingPosition.getRow() - nextIncrementer >= 0;
    }

    private boolean isPosBelowInsideBoard(int nextIncrementer)
    {
	return nextIncrementer + startingPosition.getRow() < board.getHeight();
    }
    //////////////////////////DIAGONALTOLOWERRIGHT/////////////////////////
    public int countEqualsDiagonallyToUpperRight()
    {

	int total =1;
	total += countSameToUpperRight();
	total += countSameToLowerLeft();
	return total;
    }
    private int countSameToUpperRight()
    {
	//TODO try to break this dependency
	int nextIncrementer =1;
	int total = 0;
	while (isPosAboveInsideBoard(nextIncrementer) && isPosToRightInsideBoard(nextIncrementer) && isMarkAboveRightSame(nextIncrementer))
	{
	    ++nextIncrementer;
	    if (isFiveInARow(++total))
		break;
	}
	return total;
    }
    private int countSameToLowerLeft()
    {
	int nextIncrementer=1;
	int total = 0;
	while (isPosBelowInsideBoard(nextIncrementer)
		&& isPosToLeftInsideBoard(nextIncrementer) 
		&& isMarkBelowLeftSame(nextIncrementer))
	{
	    ++nextIncrementer;
	    if (isFiveInARow(++total))
		break;
	}
	return total;
    }

    private boolean isMarkAboveRightSame(int nextIncrementer)
    {
	return markEqAtpos(board,
		(new Position(startingPosition.getRow() - nextIncrementer, startingPosition.getCol() + nextIncrementer)), mark);
    }
    private boolean isMarkBelowLeftSame(int nextIncrementer)
    {
	return board.getMarkAtPosition(
		new Position(startingPosition.getRow() + nextIncrementer, startingPosition.getCol() - nextIncrementer)) == mark;
    }
//////////////////////////////////////////DIAGONALTOUPPERLEFT////////////////////
    public int countEqualsDiagonallyToUpperLeft()
    {
	int total =1;
	total+= countSameToLowerRight();
	total +=countSameToUpperLeft();
	return total;
    }

    private int countSameToLowerRight()
    {
	int nextIncrementer=1;
	int totalNrInRow=0;
	while(isPosBelowInsideBoard(nextIncrementer) && isPosToRightInsideBoard(nextIncrementer) && isMarkBelowRightSame(nextIncrementer))
	{
	    nextIncrementer++;
	    if (isFiveInARow(++totalNrInRow))
	    {
		break;
	    }
	}
	return totalNrInRow;
    }

    //TODO the only thing differing is the while condition
    private int countSameToUpperLeft()
    {
	int nextIncrementer=1;
	int totalInRow =0;
	while (isPosAboveInsideBoard(nextIncrementer) && isPosToLeftInsideBoard(nextIncrementer) 
		&& isMarkAboveLeftSame(nextIncrementer))
	{
	    ++nextIncrementer;
	    if (isFiveInARow((++totalInRow) +1))
	    {
		break;
	    }
	}
	return totalInRow;
    }

    private boolean isMarkBelowRightSame(int nextIncrementer)
    {
	return board.getMarkAtPosition(
		new Position(startingPosition.getRow() + nextIncrementer, startingPosition.getCol() + nextIncrementer)) == mark;
    }

    private boolean isMarkAboveLeftSame(int nextIncrementer)
    {
	return markEqAtpos(board,
		new Position((startingPosition.getRow() - nextIncrementer), (startingPosition.getCol() - nextIncrementer)), mark);

    }

//////////////////////////////COMMON///////////////////////////////////////
    // conveys intention better
    private boolean markEqAtpos(Board board, Position pos, int mark)
    {
	return board.getMarkAtPosition(pos) == mark;
    }

}