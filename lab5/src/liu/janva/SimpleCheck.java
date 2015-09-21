package liu.janva;

public class SimpleCheck implements Checker
{
    private final int WIN_LIMIT = 5;
    // TODO This should probably be local or passed as parameter
    //variable problematic scatterd
    // everywhere
    private int nextIncrementer;
    // private boolean moreRight;

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
	// TODO maybe should be done by setter instead shouldn't should be
	// someone elses responsibility)
	this.lastMove = lastMove;
	this.mark = board.getMarkAtPosition(lastMove);
	this.board = board;

	// TODO make mark abstract type instead of int less dependencies
	// just check so that we're not standing on empty square
	// TODO blää get rid of me
	if (mark == 0)
	{
	    return false;
	}
	return (isHorizontalWin()|| 
		isVerticalWin() ||
		isDiagonalToUpperLeftWin()||
		isDiagonalToUpperRightWin()
		);
    }

    private boolean isDiagonalToUpperRightWin()
    {
	//counting the one we are on when starting
	//TODO a bit of waste here 
	int nrInRow = 1;
	nrInRow += countSameToUpperRight();
	nrInRow += countSameToLowerLeft();
	return isFiveInARow(nrInRow) ? true : false;
    }


    private boolean isDiagonalToUpperLeftWin()
    {
	// upperLeftToLowerRight
	int total =1;
	total+= countSameToLowerRight();
	total +=countSameToUpperLeft();
	return isFiveInARow(total)? true:false;
    }


    // TODO still smells
    private boolean isHorizontalWin()
    {
	int total =1;
	total+= countSameToLeft();
	total+=countSameToRight();
	return isFiveInARow(total)? true:false;

    }

    private boolean isVerticalWin()
    {
	int total =1;
	total+= countSameAbove();
	total +=countSameBelow();
	return isFiveInARow(total)? true:false;
    }

    private int countSameToUpperRight()
    {
	//TODO try to break this dependency
	nextIncrementer =1;
	int total = 0;
	while (isPosAboveInsideBoard() && isPosToRightInsideBoard() && isMarkAboveRightSame())
	{
	    ++nextIncrementer;
	    if (isFiveInARow(++total))
		break;
	}
	return total;
    }


    private int countSameToLowerLeft()
    {
	nextIncrementer=1;
	int total = 0;
	while (isPosBelowInsideBoard()
		&& isPosToLeftInsideBoard() 
		&& isMarkBelowLeftSame())
	{
	    ++nextIncrementer;
	    if (isFiveInARow(++total))
		break;
	}
	return total;
    }


    private int countSameAbove()
    {
	nextIncrementer=1;
	int totalInRow =0;
	while (isPosAboveInsideBoard() && isMarkAboveSame())
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
	nextIncrementer=1;
	int totalInRow =0;
	while (isPosBelowInsideBoard() && isMarkBelowSame())
	{
	    ++nextIncrementer;
	    if (isFiveInARow((++totalInRow) +1))
	    {
		break;
	    }
	}
	return totalInRow;
    }


    private int countSameToLeft()
    {
	nextIncrementer=1;
	int totalInRow =0;
	while (isPosToLeftInsideBoard() && isMarkToLeftSame())
	{
	    ++nextIncrementer;
	    if (isFiveInARow((++totalInRow) +1))
	    {
		break;
	    }
	}
	return totalInRow;
    }

    private int countSameToRight()
    {
	nextIncrementer=1;
	int totalInRow =0;
	while (isPosToRightInsideBoard() && isMarkToRightSame())
	{
	    ++nextIncrementer;
	    if (isFiveInARow((++totalInRow) +1))
	    {
		break;
	    }
	}
	return totalInRow;
    }

    private int countSameToLowerRight()
    {
	nextIncrementer=1;
	int totalNrInRow=0;
	while(isPosBelowInsideBoard() && isPosToRightInsideBoard() && isMarkBelowRightSame())
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
	nextIncrementer=1;
	int totalInRow =0;
	while (isPosAboveInsideBoard() && isPosToLeftInsideBoard() 
		&& isMarkAboveLeftSame())
	{
	    ++nextIncrementer;
	    if (isFiveInARow((++totalInRow) +1))
	    {
		break;
	    }
	}
	return totalInRow;
    }


    private boolean isMarkBelowRightSame()
    {
	return board.getMarkAtPosition(
		new Position(lastMove.getRow() + nextIncrementer, lastMove.getCol() + nextIncrementer)) == mark;
    }

    private boolean isMarkBelowLeftSame()
    {
	return board.getMarkAtPosition(
		new Position(lastMove.getRow() + nextIncrementer, lastMove.getCol() - nextIncrementer)) == mark;
    }

    private boolean isMarkBelowSame()
    {
	return markEqAtpos(board, new Position(lastMove.getRow() + nextIncrementer, lastMove.getCol()), mark);
    }

    private boolean isMarkAboveLeftSame()
    {
	return markEqAtpos(board,
		new Position((lastMove.getRow() - nextIncrementer), (lastMove.getCol() - nextIncrementer)), mark);

    }

    private boolean isMarkAboveRightSame()
    {
	return markEqAtpos(board,
		(new Position(lastMove.getRow() - nextIncrementer, lastMove.getCol() + nextIncrementer)), mark);
    }

    private boolean isMarkAboveSame()
    {
	return markEqAtpos(board, new Position((lastMove.getRow() - nextIncrementer), lastMove.getCol()), mark);
    }

    // conveys intention better
    private boolean markEqAtpos(Board board, Position pos, int mark)
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

    private boolean isMarkToLeftSame()
    {
	return board.getMarkAtPosition(new Position(lastMove.getRow(), lastMove.getCol() - nextIncrementer)) == mark;
    }

    private boolean isPosToLeftInsideBoard()
    {
	return (lastMove.getCol() - nextIncrementer) >= 0;
    }

    private boolean isFiveInARow(int count)
    {
	return (count >= WIN_LIMIT);
    }

    private boolean isMarkToRightSame()
    {
	return board
		.getMarkAtPosition(new Position((lastMove.getRow()), (lastMove.getCol() + nextIncrementer))) == mark;
    }

    private boolean isPosToRightInsideBoard()
    {
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
