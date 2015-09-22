package liu.janva;

public class SimpleGameBoard implements Board{
    private Checker checker;
    //TODO protected to simplify testing think of better solution 
    protected int[][] board;
    public SimpleGameBoard(Checker checker, int boardSize)
    {
	this.checker = checker;
	this.board = new int[boardSize][boardSize];
    }

    //TODO mark  needs to be abstract type not int
    public boolean markPosition(Position pos, int mark) {
	if(!((pos.getRow() <= board.length ) && (pos.getCol() <= board[0].length)))
	{
	    return false;
	}
	int current = board[pos.getRow()][pos.getCol()] ;
	if(current == 0)
	{
	    board[pos.getRow()][pos.getCol()] = mark;
	    return true;
	}
	return false;
    }

    public int getHeight ()
    {
	return board.length; 

    }

    public int getWidth()
    {
	return board[0].length;
    }

    public void clearBoard()
    {
	for (int i = 0; i < board.length; ++i)
	{
	    for (int j= 0; j < board[i].length; ++j)
	    { 
		//0 =-, 1=X, 2=O  
		board[i][j]= 0;
	    }
	}
    }

    public int getMarkAtPosition(Position pos)
    {
	return board[pos.getRow()][pos.getCol()];
    }

    public boolean checkWin(Position lastMove)
    {
	return checker.checkWin (this,lastMove);
    }


    public String toString()
    {
	StringBuilder builder = new StringBuilder();
	//TODO me not like
	for(int i =0; i < getHeight(); ++i)
	{
	    for(int j= 0; j < getWidth(); ++j)
	    {
		builder.append("[");

		int mark = board[i][j];
		if (mark == 0)
		{
		    builder.append('-');

		}else
		{
		    //TODO Again mark abstract type now we have dependencies scattered all over the place
		    builder.append(mark == 1? "X":"O");
		}
		builder.append("]");
	    }
	    builder.append("\n");
	}
	return builder.toString();

    }
    public static class test
    {
	public static void main(String[] args) {
	    SimpleGameBoard board = new SimpleGameBoard(new SimpleCheck(), 5);
	    board.toString();
	    System.out.println(
		    board.getMarkAtPosition(new Position(3, 2)));
	}
    }

    // TODO bläää thought of this at late stage know  sucks
    @Override
    public boolean full() {
	for(int i =0; board.length > i; ++i)
	{
	    for(int j=0; board.length > j; ++j)
	    {
		if (board[i][j]== 0)
		    return false;
	    }
	}
	return false;
    }
}
