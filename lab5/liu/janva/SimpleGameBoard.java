package liu.janva;

import java.util.Arrays;

public class SimpleGameBoard implements Board{
	private Checker checker;
	//TODO howto mock?
	int[][] board  ={
			{1,2,2,2,1},
			{1,1,2,2,1},
			{1,0,1,0,1},
			{0,0,0,1,1},
			{2,2,2,2,2}};

	//TODO consider making this class singelton but be aware of threadsafty
	public SimpleGameBoard(Checker checker, int boardSize)
	{
		this.checker = checker;
		//this.board = new int[boardSize][boardSize];
	}

	//TODO mark  needs to be abstract type not int
	//TODO this will be part of interface
	public boolean markPosition(Position pos, int mark) {
		int current = board[pos.getRow()][pos.getCol()] ;
		if(current == 0)
		{
			board[pos.getRow()][pos.getCol()] = mark;
			return true;
		}
		return false;
		//better not call checkwin here
		//		if(checkWin(pos))
		//		{
		//			System.out.println("announce winner");
		//			return true;
		//		}
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


	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		//TODO me not like
		for(int i =0; i < getHeight(); ++i)
		{
			//builder.append(Arrays.toString(board[i]));

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
		String stringBoard =builder.toString();
		System.out.println(stringBoard);
		return stringBoard;

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
	
	// TODO bläää thought of this at late stage know it sucks
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
