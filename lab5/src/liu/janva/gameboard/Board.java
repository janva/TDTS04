package liu.janva.gameboard;

import liu.janva.boardIterators.DownIterator;
import liu.janva.boardIterators.LeftDownIterator;
import liu.janva.boardIterators.LeftIterator;
import liu.janva.boardIterators.LeftUpIterator;
import liu.janva.boardIterators.RightDownIterator;
import liu.janva.boardIterators.RightIterator;
import liu.janva.boardIterators.RightUpIterator;
import liu.janva.boardIterators.UpIterator;

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
