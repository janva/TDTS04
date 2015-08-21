package liu.janva;

public interface Board {
	public boolean checkWin(Position lastMove);
	public boolean markPosition(Position pos, int mark);
	public void clearBoard();
	public String tosString();

}
