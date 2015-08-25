package liu.janva;


public interface Checker 
{
	//TODO abstract type for position
//	boolean checkWin(int[][] board, Position lastPosition);
	boolean checkWin(Board board, Position lastMove);
}
