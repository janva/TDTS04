package liu.janva;

public interface Board {
	public boolean checkWin(Position lastMove);
	public boolean markPosition(Position pos, int mark);
	public int getMarkAtPosition(Position pos);
	public int getWidth();
	public int getHeight();
	public void clearBoard();

	public String toString();
}
