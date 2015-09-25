package liu.janva.gameboard;

// TODO maybe depth first search instead graph implementation instead?
public class SimpleCheck implements Checker
{
    private final int WIN_LIMIT = 5;

    private Board board;
    private Position lastMove;
    private Counter counter;

    public SimpleCheck(){}

    @Override
    public boolean checkWin(Board board, Position lastMove)
    {
	// TODO maybe should be done by setter instead shouldn't should be
	// someone else responsibility) easier testing bonus as well cons?
	this.lastMove = lastMove;
	this.board = board;
	this.counter = new Counter(board, lastMove);
	
	if (atEmptyMark ()){return false;}
	//TODO could we do something weird with command pattern :-)	
	return (isHorizontalWin()|| 
		isVerticalWin() ||
		isDiagonalToUpperLeftWin()||
		isDiagonalToUpperRightWin());
    }

    private boolean isDiagonalToUpperRightWin()
    {
	return isFiveInARow(counter.countEqualsDiagonallyToUpperRight()) ? true : false;
    }


    private boolean isDiagonalToUpperLeftWin()
    {
	return isFiveInARow(counter.countEqualsDiagonallyToUpperLeft())? true:false;
    }


    private boolean isHorizontalWin()
    {
	return isFiveInARow(counter.countEqualshorizontally());
    }

    private boolean isVerticalWin()
    {
	return isFiveInARow(counter.countEqualsVertically());
    }


    private boolean isFiveInARow(int count)
    {
	return (count >= WIN_LIMIT);
    }
    
    private boolean atEmptyMark()
    {
        return  board.getMarkAtPosition(lastMove)==0 ? true:false;
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
