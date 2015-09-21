package liu.janva;

public class SimpleCheck implements Checker
{
    private final int WIN_LIMIT = 5;
    private int nextIncrementer;
    private boolean moreRight;

    public SimpleCheck()
    {
    }

    @Override
    public boolean checkWin(Board board, Position lastMove)
    {
	// hmm should this be here
	nextIncrementer = 0;
	int horizontalCount = 1;
	int verticalCount = 1;

	int diagonalUpperLeftToLowerRightCount = 1;
	int diagonalUpperRightToLowerLeftCount = 1;

	// TODO make mark abstract type instead of int less dependencies
	int mark = board.getMarkAtPosition(lastMove);
	if (mark == 0)
	{
	    return false;
	}
	boolean moreLeft = true;
	moreRight = true;
	boolean moreDown = true;
	boolean moreUp = true;

	boolean moreDiagonallyToLowerRight = true;
	boolean moreDiagonallyToUpperLeft = true;

	boolean moreDiagonallyToUpperRight = true;
	boolean moreDiagonallyToLowerLeft = true;

	// TODO depth first search graph?
	// horizontal
	while (moreLeft || moreRight || moreDown || moreUp ||
		moreDiagonallyToLowerRight || moreDiagonallyToUpperLeft|| 
		moreDiagonallyToUpperRight ||moreDiagonallyToLowerLeft)

	{
	    ++nextIncrementer;
	    if (moreRight)
	    {
		if (isPosToRightInsideBoard(board, lastMove) && 
			isMarkToRightSame(board, lastMove, mark))
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
		if (isPosToLeftInsideBoard(lastMove) && 
			isMarkToLeftSame(board, lastMove, mark))
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

	    // vertical
	    if (moreDown)
	    {
		if (isPosBelowInsideBoard(board, lastMove) && 
			isMarkBelowSame(board, lastMove, mark))
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
		if (isPosAboveInsideBoard(lastMove) && isMarkAboveSame(board, lastMove, mark))
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
	    // diagonals
	    // upperLeftToLowerRight
	    if (moreDiagonallyToUpperLeft)
	    {
		if (isPosAboveInsideBoard(lastMove) && 
			isPosToLeftInsideBoard(lastMove) && 
			isMarkUpAndLeftSame(board, lastMove, mark))
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
		if (isPosBelowInsideBoard(board, lastMove)
			&& isPosToRightInsideBoard(board, lastMove)
			&& board.getMarkAtPosition(new Position
				(lastMove.getRow() + nextIncrementer,
					lastMove.getCol() + nextIncrementer)) == mark)
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
	    // upperRightToLowerLeft
	    if (moreDiagonallyToUpperRight)
	    {
		//FIXME Might be bug dependence on wheter order of execution is well defined?
		if (isPosAboveInsideBoard(lastMove) && 
			isPosToRightInsideBoard(board, lastMove)
			&& isMarkUpRightSame(board, lastMove, mark))
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
		if (isPosBelowInsideBoard(board, lastMove)
			&& isPosToLeftInsideBoard(lastMove)
			&& isMarkDownLeftSame(board, lastMove, mark))
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

    private boolean isMarkDownLeftSame(Board board, Position lastMove, int mark)
    {
	return board.getMarkAtPosition(new Position(lastMove.getRow() + nextIncrementer,
		lastMove.getCol() - nextIncrementer)) == mark;
    }

    private boolean isMarkUpRightSame(Board board, Position lastMove, int mark)
    {
	return board.getMarkAtPosition(new Position(lastMove.getRow() - nextIncrementer,
		lastMove.getCol() + nextIncrementer)) == mark;
    }

    private boolean isMarkUpAndLeftSame(Board board, Position lastMove, int mark)
    {
	return board.getMarkAtPosition(new Position((lastMove.getRow() - nextIncrementer),
		(lastMove.getCol() - nextIncrementer))) == mark;
    }

    private boolean isMarkAboveSame(Board board, Position lastMove, int mark)
    {
	return board.getMarkAtPosition(
		new Position((lastMove.getRow() - nextIncrementer), (lastMove.getCol()))) == mark;
    }

    private boolean isPosAboveInsideBoard(Position lastMove)
    {
	return lastMove.getRow() - nextIncrementer >= 0;
    }

    private boolean isMarkBelowSame(Board board, Position lastMove, int mark)
    {
	return board.getMarkAtPosition(
	new Position(lastMove.getRow() + nextIncrementer, lastMove.getCol())) == mark;
    }

    private boolean isPosBelowInsideBoard(Board board, Position lastMove)
    {
	return nextIncrementer + lastMove.getRow() < board.getHeight();
    }

    private boolean isMarkToLeftSame(Board board, Position lastMove, int mark) {
	return board.getMarkAtPosition(
		new Position(lastMove.getRow(), lastMove.getCol() - nextIncrementer)) == mark;
    }

    private boolean isPosToLeftInsideBoard(Position lastMove) {
	return (lastMove.getCol() - nextIncrementer) >= 0;
    }

    private boolean isFiveInARow(int count)
    {
	return (count >= WIN_LIMIT);
    }
    private boolean isMarkToRightSame(Board board, Position lastMove, int mark) {
	return board.getMarkAtPosition(new Position((lastMove.getRow()),
		(lastMove.getCol() + nextIncrementer))) == mark;
    }

    private boolean isPosToRightInsideBoard(Board board, Position lastMove) {
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
