package liu.janva;

public interface Board {
	public boolean checkWin(Position lastMove);
	public void markPosition(Position pos, int mark);
	public void clearBoard();
	public String tosString();

}