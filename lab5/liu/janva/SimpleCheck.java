package liu.janva;

//TODO abstract factory pattern
public class SimpleCheck implements Checker {

	public SimpleCheck() {}

	//TODO create board interface. (Program to interface not to implementation)
	@Override
	//public boolean checkWin(int[][] board,int col, int row) {
	//public boolean checkWin(int[][] board, Position lastMove) {
	public boolean checkWin(Board boardTemp, Position lastMove) {
		
		//TODO might become a problem the we need to pas checker to gameboard
		//SimpleGameBoard board = new SimpleGameBoard(this, 5);
		//TODO no need to assign  above was used for testing 
		Board board = boardTemp;
		final int WIN_LIMIT = 3;
		//if (checkHorizontal (int[][] board,int xPos, int yPos));
		//Chekhorizontal win
		//TODO assuming correct x and y for now
		//vertical
		int nextIncrementer= 0;
		int horizontalCount = 1;
		int verticalCount = 1;

		int diagonalUpperLeftToLowerRightCount= 1;
		int diagonalUpperRightToLowerLeftCount= 1;

		//TODO make mark abstract type instead of int less dependencies
		int mark= board.getMarkAtPosition(lastMove);
		if(mark == 0 )
		{
			return false;
		}
		//[lastMove.getRow()][lastMove.getCol()];

		boolean moreLeft= true;
		boolean moreRight= true;
		boolean moreDown= true;
		boolean moreUp= true;
	
		boolean moreDiagonallyToLowerRight = true;
		boolean moreDiagonallyToUpperLeft= true;

		boolean moreDiagonallyToUpperRight = true;
		boolean moreDiagonallyToLowerLeft= true;

		
		//TODO better solution had a crazy idea with involving command
		//pattern anyway refactor this part and junit test 
		//iterator pattern maybe refactor anyway
		// this part would have been good for testdriven probably statepattern
		//depth first search tree or graph?
		//horizontal
		while (moreLeft || moreRight|| moreDown||moreUp)
		{
			++nextIncrementer;
			if(moreRight)
			{
				if(( (nextIncrementer + lastMove.getCol()) < board.getWidth())&&
						(board.getMarkAtPosition(new Position((lastMove.getRow()),
								(lastMove.getCol() + nextIncrementer) )) == mark))
						//(board[lastMove.getRow()][lastMove.getCol() + nextIncrementer] == mark ))
				{
					if (++horizontalCount >= WIN_LIMIT)
					{
						return true;
					}
				}else
				{
					moreRight =false;
				}
			}
			if(moreLeft){
				if(((lastMove.getCol() -nextIncrementer ) >= 0) &&
						board.getMarkAtPosition(new Position(lastMove.getRow(),lastMove.getCol() - nextIncrementer))== mark)
						//board[lastMove.getRow()][lastMove.getCol() - nextIncrementer] == mark )
				{
					if (++horizontalCount >= WIN_LIMIT)
					{
						return true;
					}
				}else 
				{
					moreLeft= false;
				}
			}

			//vertical
			if(moreDown)
			{//TODO THIS LINE SEEMS TO BE PROBLEM
				if(	( nextIncrementer + lastMove.getRow() < board.getWidth()) 
						&&
						(board.getMarkAtPosition(new Position(lastMove.getRow()+ nextIncrementer,
								lastMove.getCol()))== mark))
//				if(( nextIncrementer + lastMove.getRow() < board.length ) &&
//						(board[lastMove.getRow() + nextIncrementer ][lastMove.getCol()] == mark))
				{
 					if (++verticalCount >= WIN_LIMIT)
					{
						return true;
					}
				}	
				else
				{
					moreDown =false;
				}
			}
			if(moreUp)
			{
				if(lastMove.getRow() -nextIncrementer >= 0 &&
						board.getMarkAtPosition(new Position (
								(lastMove.getRow() - nextIncrementer),(lastMove.getCol()))) == mark)
				{
							//board[lastMove.getRow() - nextIncrementer ][lastMove.getCol()] == mark )
					if (++verticalCount >= WIN_LIMIT)
					{
						return true;
					}
				}
				else
				{
					moreUp=false;
				}
			}
			//diagonals
			//upperLeftToLowerRight
			if(moreDiagonallyToUpperLeft)
			{
				if(lastMove.getRow() -nextIncrementer >= 0 && lastMove.getCol() - nextIncrementer>=0 &&
						board.getMarkAtPosition(
								new Position((lastMove.getRow() - nextIncrementer),
										(lastMove.getCol()-nextIncrementer))) == mark)
								
						//board[lastMove.getRow() - nextIncrementer ][lastMove.getCol()-nextIncrementer] == mark)
				{
					if (++diagonalUpperLeftToLowerRightCount >= WIN_LIMIT)
					{
						return true;
					}
				}
				else
				{
					moreDiagonallyToUpperLeft=false;
				}
			}
			if(moreDiagonallyToLowerRight)
			{
				//TODO board is square so this works but fix it anyway in case future changes
				if(lastMove.getRow() +nextIncrementer < board.getHeight()  && lastMove.getCol()+nextIncrementer < board.getWidth()  &&
						board.getMarkAtPosition(
								new Position(lastMove.getRow() + nextIncrementer,
										    lastMove.getCol()+ nextIncrementer))
								 == mark)
								{
					if (++diagonalUpperLeftToLowerRightCount  >= WIN_LIMIT)
					{
						return true;
					}
				}
				else
				{
					moreDiagonallyToLowerRight=false;
				}
			}
			//upperRightToLowerLeft
			if(moreDiagonallyToUpperRight)
			{
				if(lastMove.getRow() -nextIncrementer >= 0 && lastMove.getCol() + nextIncrementer < board.getWidth() &&
						board.getMarkAtPosition(new Position(lastMove.getRow() - nextIncrementer,
								lastMove.getCol()+ nextIncrementer))
						== mark)
						{
					if (++diagonalUpperRightToLowerLeftCount>= WIN_LIMIT)
					{
						return true;
					}
				}
				else
				{
					moreDiagonallyToUpperRight=false;
				}
			}
			if(moreDiagonallyToLowerLeft)
			{
				//TODO board is square so this works but fix it anyway in case future changes
				if(lastMove.getRow() +nextIncrementer < board.getHeight()  && lastMove.getCol()-nextIncrementer >=0   &&
						board.getMarkAtPosition(new Position(lastMove.getRow() + nextIncrementer,
								lastMove.getCol()-nextIncrementer)) == mark )
						{
					if (++diagonalUpperRightToLowerLeftCount  >= WIN_LIMIT)
					{
						return true;
					}
				}
				else
				{
					moreDiagonallyToLowerLeft=false;
				}
			}
		}
		return false;
	}	

	//For simple test because lazy and don't want to use Junit for now
	public static class test {
		public static void main(String[] args) 
		{
			int[][] arr = {{1,1,1,1,1,1},
					{1,1,1,1,1},
					{1,1,1,1,1},
					{1,1,1,1,1},
					{1,1,1,1,1}};
			Checker check = new SimpleCheck();
			//SimpleGameBoard board = new SimpleGameBoard(check,5);
			//System.out.println( check.checkWin(arr,1, 1)? "win":"lose");

			
//			int[][] arr2 = 
//				  { {2,2,2,2,0},
//					{1,2,1,2,1},
//					{2,2,1,1,1},
//					{2,1,0,2,1},
//					{1,1,2,1,1}};
//			int[][] arr3 = 
//				{{2,2,2,2,0},
//					{1,1,1,1,1},
//					{2,1,2,1,2},
//					{1,2,1,1,1},
//					{1,1,1,1,1}};
//			int[][] arr4 = 
//				{{2,2,2,2,0},
//					{1,1,1,1,1},
//					{1,1,1,1,1},
//					{1,1,1,1,1},
//					{1,1,1,1,1}};
//			int[][] board = {
//					{1,2,2,2,1},
//					{0,1,2,2,1},
//					{0,2,1,1,1},
//					{0,0,0,0,1},
//					{2,2,2,2,2}};

			SimpleGameBoard board = 
					new SimpleGameBoard(new SimpleCheck(), 5);
			System.out.println(board.toString());
			
			System.out.println( "------------------------------------");
			System.out.println( check.checkWin(board, new Position(1, 1))? "win":"lose");
			System.out.println( check.checkWin(board,new Position(1,2))? "win":"lose");
			System.out.println( check.checkWin(board,new Position(4,1))? "win":"lose");
			System.out.println( check.checkWin(board,new Position(2,4))? "win":"lose");
			System.out.println( check.checkWin(board,new Position(3,4))? "win":"lose");
			System.out.println( check.checkWin(board,new Position(3,2))? "win":"lose");
			System.out.println(board.getMarkAtPosition(new Position(0, 4)));
			System.out.println(board.getMarkAtPosition(new Position(1, 4)));
			System.out.println(board.getMarkAtPosition(new Position(2, 4)));
			System.out.println(board.getMarkAtPosition(new Position(3, 4)));
			System.out.println(board.getMarkAtPosition(new Position(2, 3)));
			System.out.println(board.getMarkAtPosition(new Position(2, 3)));
			System.out.println(board.getMarkAtPosition(new Position(2, 2)));
			//System.out.println( check.checkWin(arr4,1,1)? "win":"lose");
			System.out.println( check.checkWin(board,new Position(4,1))? "win":"lose");
		}

	}
}
