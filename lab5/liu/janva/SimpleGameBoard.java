package liu.janva;

import java.util.Arrays;

public class SimpleGameBoard implements Board{
	private Checker checker;
	//TODO here for testing not relevant any more
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
				//for now we use integer marks but should 
				//really be abstract mark type(or generic)
				board[i][j]= 0;
			}
		}
	}
	
	//Todo change interface to return a moe generic value type
	public int getMarkAtPosition(Position pos)
	{
		//Maybe could have an special exception but not
		//necesarially good idea better never to throw?
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
