package liu.janva;

public interface Board {
    //TODO maybe a bit to much in interface
	public boolean checkWin(Position lastMove);
	public boolean markPosition(Position pos, int mark);
	public boolean full();

	public int getMarkAtPosition(Position pos);
	public int getWidth();
	public int getHeight();
	public void clearBoard();
	
	//iterators
	public RightIterator rightIterator(Position startFrom);
	public LeftIterator leftIterator(Position startFrom);
	public UpIterator upIterator(Position startFrom);
	public DownIterator downIterator(Position startFrom);
	//Diagonals
	public RightUpIterator rightUpIterator(Position startFrom);
	public LeftUpIterator leftUpIterator(Position startFrom);
	public RightDownIterator rightDownIterator(Position startFrom);
	public LeftDownIterator leftDownIterator(Position startFrom);
	
	public String toString();
}
