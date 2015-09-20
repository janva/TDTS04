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

		// TODO depth first search tree and graph?
		// horizontal
		while (moreLeft || moreRight || moreDown || moreUp ||
				moreDiagonallyToLowerRight || moreDiagonallyToUpperLeft|| 
				moreDiagonallyToUpperRight ||moreDiagonallyToLowerLeft)

		{
			++nextIncrementer;
			if (moreRight)
			{
				if (isPosToRightInsideBoard(board, lastMove) && 
						isMarkToRIghtSame(board, lastMove, mark))
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
				if (isPosToLeftInsideBoard(lastMove) && isMarkToLeftSame(board, lastMove, mark))
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
				if ((nextIncrementer + lastMove.getRow() < board.getWidth()) && (board.getMarkAtPosition(
						new Position(lastMove.getRow() + nextIncrementer, lastMove.getCol())) == mark))
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
				if (lastMove.getRow() - nextIncrementer >= 0 && board.getMarkAtPosition(
						new Position((lastMove.getRow() - nextIncrementer), (lastMove.getCol()))) == mark)
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
				if (lastMove.getRow() - nextIncrementer >= 0 && lastMove.getCol() - nextIncrementer >= 0
						&& board.getMarkAtPosition(new Position((lastMove.getRow() - nextIncrementer),
								(lastMove.getCol() - nextIncrementer))) == mark)
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
				if (lastMove.getRow() + nextIncrementer < board.getHeight()
						&& lastMove.getCol() + nextIncrementer < board.getWidth()
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
				if (lastMove.getRow() - nextIncrementer >= 0 && lastMove.getCol() + nextIncrementer < board.getWidth()
						&& board.getMarkAtPosition(new Position(lastMove.getRow() - nextIncrementer,
								lastMove.getCol() + nextIncrementer)) == mark)
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
				if (lastMove.getRow() + nextIncrementer < board.getHeight()  
						&& lastMove.getCol() - nextIncrementer >= 0
						&& board.getMarkAtPosition(new Position(lastMove.getRow() + nextIncrementer,
								lastMove.getCol() - nextIncrementer)) == mark)
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
	private boolean isMarkToRIghtSame(Board board, Position lastMove, int mark) {
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
