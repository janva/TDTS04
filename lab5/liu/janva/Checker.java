package liu.janva;


public interface Checker 
{
	//TODO abstract type for position
//	boolean checkWin(int[][] board, Position lastPosition);
	//TODO consider wheter to force passing board through constructor
	//instead
	boolean checkWin(Board board, Position lastMove);
}
