package liu.janva;

public class SimpleGameBoard implements Board{
	private Checker checker;
	//TODO howto mock?
	int[][] board  ={
		{1,2,2,2,1},
		{0,1,2,2,1},
		{0,2,1,1,1},
		{0,0,0,1,1},
		{2,2,2,2,2}};
	
	public SimpleGameBoard(Checker checker, int boardSize)
	{
		this.checker = checker;
		//this.board = new int[boardSize][boardSize];
	}
	
	//TODO mark  abstract type
	//TODO this will be part of interface
	public void markPosition(Position pos, int mark) {
		board[pos.getRow()][pos.getCol()] = mark;
		if(checkWin(pos))
		{
			System.out.println("announce winner");
		}
	}
	
	public int getHeight ()
	{
		return board.length; 
		
	}
	//TODO this restricts board to rectangular boards
	public int getWidth()
	{
		return board[0].length;
	}
	//TODO this will be part of interface
	public void clearBoard()
	{
		for (int i = 0; i < board.length; ++i)
		{
			for (int j= 0; j < board[i].length; ++j)
			{
				//for now we use integer marks but should 
				//really be abstract mark type(or generic)
				board[i][j]= 0;
			}
		}
	}
	//TODO this will be part of interface
	//Todo change interface to return a generic value type
	public int getMarkAtPosition(Position pos)
	{
		//Maybe could have an special exception but not
		//necesarially good idea better never to throw?
		return board[pos.getRow()][pos.getCol()];
	}
	
	//TODO this will be part of interface
	public boolean checkWin(Position lastMove)
	{
		return checker.checkWin (this,lastMove);
	}
}
