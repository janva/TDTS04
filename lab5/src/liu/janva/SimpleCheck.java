package liu.janva;

public class SimpleCheck implements Checker
{
    private final int WIN_LIMIT = 5;
    // TODO This should probably be local or passed as parameter
    //variable problematic scatterd
    // everywhere
//    private int nextIncrementer;
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
	// someone else responsibility) easier testing bonus as well cons?
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
	Counter counter = new Counter(board, lastMove);
	return isFiveInARow(counter.countEqualsDiagonallyToUpperRight()) ? true : false;
    }


    private boolean isDiagonalToUpperLeftWin()
    {
	// upperLeftToLowerRight
	Counter counter = new Counter(board, lastMove);
	return isFiveInARow(counter.countEqualsDiagonallyToUpperLeft())? true:false;
    }


    private boolean isHorizontalWin()
    {
	//TODO consider factory method or abstract factory but try to avoid
	//static create method (read somewhere that static complicates testing)
	// maybe better if countEqualshorizontally() takes arg lastMove
	Counter counter = new Counter(board, lastMove);
	return isFiveInARow(counter.countEqualshorizontally());
    }

    private boolean isVerticalWin()
    {
	Counter counter = new Counter(board, lastMove);
	return isFiveInARow(counter.countEqualsVertically());
    }


    private boolean isFiveInARow(int count)
    {
	return (count >= WIN_LIMIT);
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
