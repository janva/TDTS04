package liu.janva;

public class SimpleCheck implements Checker
{
    private final int WIN_LIMIT = 5;
    //TODO This should probably be local variable
    private int nextIncrementer;
    //private boolean moreRight;

    private Board board;
    private Position lastMove;
    private int mark;

    public SimpleCheck()
    {
    }
    // TODO maybe depth first search instead graph implementation instead?
    @Override
    public boolean checkWin(Board board, Position lastMove)
    {
	this.lastMove= lastMove;
	this.mark = board.getMarkAtPosition(lastMove);
	this.board =board;

	// TODO make mark abstract type instead of int less dependencies
	//just check so that we're not standing on empty square
	if (mark == 0)
	{
	    return false;
	}
	//TODO hmmm tell don't ask possible or something similar?
	if (exploreHorizontalWin())
	{
	    return true;
	}
	if(exploreVerticalWin())
	{
	    return true;
	}
	if (exploreDiagonalUpperLeftToLowerRightWin())
	{
	    return true;
	}
	if(exploreDiagonalUpperRightToLowerLeftWin())
	{
	    return true;
	}
	return false;
    }
    
    private boolean exploreDiagonalUpperRightToLowerLeftWin()
    {
	nextIncrementer=0;
	int diagonalUpperRightToLowerLeftCount = 1;
	boolean moreDiagonallyToUpperRight = true;
	boolean moreDiagonallyToLowerLeft = true;

	while (	moreDiagonallyToUpperRight ||moreDiagonallyToLowerLeft)
	{
	    ++nextIncrementer;

	    // upperRightToLowerLeft
	    if (moreDiagonallyToUpperRight)
	    {
		//FIXME Might be bug dependence on wehter order of execution is well defined?
		if (isPosAboveInsideBoard() && 
			isPosToRightInsideBoard()
			&& isMarkAboveRightSame())
		{
		    if (isFiveInARow( ++diagonalUpperRightToLowerLeftCount) )
		    {
			return true;
		    }
		} else
		{
		    moreDiagonallyToUpperRight = false;
		}
	    }
	    if (moreDiagonallyToLowerLeft)
	    {
		if (isPosBelowInsideBoard()
			&& isPosToLeftInsideBoard()
			&& isMarkBelowLeftSame())
		{
		    if (isFiveInARow(++diagonalUpperRightToLowerLeftCount))
		    {
			return true;
		    }
		} else
		{
		    moreDiagonallyToLowerLeft = false;
		}
	    }
	}
	return false;
    }

    private boolean exploreDiagonalUpperLeftToLowerRightWin()
    {
	nextIncrementer=0;
	int diagonalUpperLeftToLowerRightCount = 1;
	boolean moreDiagonallyToUpperLeft = true;
	boolean moreDiagonallyToLowerRight = true;

	// upperLeftToLowerRight
	while (	moreDiagonallyToLowerRight || moreDiagonallyToUpperLeft)
	{
	    ++nextIncrementer;
	    if (moreDiagonallyToUpperLeft)
	    {
		if (isPosAboveInsideBoard() && 
			isPosToLeftInsideBoard() && 
			isMarkAboveLeftSame())
		{
		    if (isFiveInARow(++diagonalUpperLeftToLowerRightCount))
		    {
			return true;
		    }
		} else
		{
		    moreDiagonallyToUpperLeft = false;
		}
	    }

	    if (moreDiagonallyToLowerRight)
	    {
		if (isPosBelowInsideBoard()
			&& isPosToRightInsideBoard()
			&& isMarkBelowRightSame())
		{
		    if (isFiveInARow(++diagonalUpperLeftToLowerRightCount ))
		    {
			return true;
		    }
		} else
		{
		    moreDiagonallyToLowerRight = false;
		}
	    }
	}
	return false;
    }

    private boolean exploreVerticalWin()
    {
	nextIncrementer = 0;
	int verticalCount = 1;
	boolean moreDown = true;
	boolean moreUp = true;

	while (moreDown || moreUp)
	{
	    if (moreDown)
	    {
		if (isPosBelowInsideBoard() && 
			isMarkBelowSame())
		{
		    if (isFiveInARow(++verticalCount))
		    {
			return true;
		    }
		} else
		{
		    moreDown = false;
		}
	    }
	    if (moreUp)
	    {
		if (isPosAboveInsideBoard() && isMarkAboveSame())
		{
		    if (isFiveInARow(++verticalCount))
		    {
			return true;
		    }
		} else
		{
		    moreUp = false;
		}
	    }
	}
	return false;
    }

    //TODO still smells
    private boolean exploreHorizontalWin()
    {
	nextIncrementer =0;
	int horizontalCount = 1;
	boolean moreLeft = true;
	boolean moreRight = true;
	while (moreLeft || moreRight)
	{
	    ++nextIncrementer;
	    if (moreRight)
	    {
		if (isPosToRightInsideBoard() && 
			isMarkToRightSame())
		{
		    if (isFiveInARow (++horizontalCount ))
		    {
			return true;
		    }
		} else
		{
		    moreRight = false;
		}
	    }
	    if (moreLeft)
	    {
		if (isPosToLeftInsideBoard() && 
			isMarkToLeftSame())
		{
		    if (isFiveInARow(++horizontalCount))
		    {
			return true;
		    }
		} else
		{
		    moreLeft = false;
		}
	    }
	}
	return false;
    }

    private boolean isMarkBelowRightSame()
    {
	return board.getMarkAtPosition(new Position
		(lastMove.getRow() + nextIncrementer,
			lastMove.getCol() + nextIncrementer)) == mark;
    }

    private boolean isMarkBelowLeftSame()
    {
	return board.getMarkAtPosition(new Position(lastMove.getRow() + nextIncrementer,
		lastMove.getCol() - nextIncrementer)) == mark;
    }

    private boolean isMarkBelowSame()
    {
	return markEqAtpos(board,new Position(lastMove.getRow() + nextIncrementer, lastMove.getCol()),mark);
    }

    private boolean isMarkAboveLeftSame()
    {
	return markEqAtpos(board, new Position((lastMove.getRow() - nextIncrementer),
		(lastMove.getCol() - nextIncrementer)), mark);

    }

    private boolean isMarkAboveRightSame()
    {
	return markEqAtpos(board, (new Position(lastMove.getRow() - nextIncrementer,
		lastMove.getCol() + nextIncrementer)), mark);
    }


    private boolean isMarkAboveSame()
    {
	return markEqAtpos(board, new Position((lastMove.getRow() - nextIncrementer),lastMove.getCol()), mark);
    }

    //conveys intention better
    private boolean markEqAtpos(Board board,Position pos, int mark)
    {
	return board.getMarkAtPosition(pos) == mark;
    }

    private boolean isPosAboveInsideBoard()
    {
	return lastMove.getRow() - nextIncrementer >= 0;
    }


    private boolean isPosBelowInsideBoard()
    {
	return nextIncrementer + lastMove.getRow() < board.getHeight();
    }

    private boolean isMarkToLeftSame() {
	return board.getMarkAtPosition(
		new Position(lastMove.getRow(), lastMove.getCol() - nextIncrementer)) == mark;
    }

    private boolean isPosToLeftInsideBoard() {
	return (lastMove.getCol() - nextIncrementer) >= 0;
    }

    private boolean isFiveInARow(int count)
    {
	return (count >= WIN_LIMIT);
    }
    private boolean isMarkToRightSame() {
	return board.getMarkAtPosition(new Position((lastMove.getRow()),
		(lastMove.getCol() + nextIncrementer))) == mark;
    }

    private boolean isPosToRightInsideBoard() {
	return (nextIncrementer + lastMove.getCol()) < board.getWidth();
    }

    // For simple test because lazy and don't want to use Junit for now
    public static class test
    {
	public static void main(String[] args)
	{
	    // int[][] arr = {{1,1,1,1,1,1},
	    // {1,1,1,1,1},
	    // {1,1,1,1,1},
	    // {1,1,1,1,1},
	    // {1,1,1,1,1}};
	    Checker check = new SimpleCheck();

	    SimpleGameBoard board = new SimpleGameBoard(new SimpleCheck(), 5);
	    System.out.println(board.toString());

	    System.out.println("------------------------------------");
	    System.out.println(check.checkWin(board, new Position(1, 1)) ? "win" : "lose");
	    System.out.println(check.checkWin(board, new Position(1, 2)) ? "win" : "lose");
	    System.out.println(check.checkWin(board, new Position(4, 1)) ? "win" : "lose");
	    System.out.println(check.checkWin(board, new Position(2, 4)) ? "win" : "lose");
	    System.out.println(check.checkWin(board, new Position(3, 4)) ? "win" : "lose");
	    System.out.println(check.checkWin(board, new Position(3, 2)) ? "win" : "lose");
	    System.out.println(board.getMarkAtPosition(new Position(0, 4)));
	    System.out.println(board.getMarkAtPosition(new Position(1, 4)));
	    System.out.println(board.getMarkAtPosition(new Position(2, 4)));
	    System.out.println(board.getMarkAtPosition(new Position(3, 4)));
	    System.out.println(board.getMarkAtPosition(new Position(2, 3)));
	    System.out.println(board.getMarkAtPosition(new Position(2, 3)));
	    System.out.println(board.getMarkAtPosition(new Position(2, 2)));
	    // System.out.println( check.checkWin(arr4,1,1)? "win":"lose");
	    System.out.println(check.checkWin(board, new Position(4, 1)) ? "win" : "lose");
	}
    }
}
