package liu.janva.gameboard;

public interface Checker 
{
	boolean checkWin(Board board, Position lastMove);
}
