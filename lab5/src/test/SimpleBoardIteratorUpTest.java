package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import liu.janva.boardIterators.UpIterator;
import liu.janva.gameboard.Position;

public class SimpleBoardIteratorUpTest
{
    private int[][] board;
    
    @Before
    public void setUp() throws Exception
    {
	board = new int[][]{
		{1,2,2,2,1,2,2,2,0},
		{1,1,2,2,1,1,2,2,1},
		{1,2,1,1,1,2,1,1,1},
		{1,0,0,1,1,0,0,1,2},
		{0,0,2,2,1,2,2,2,2},
		{0,1,2,2,2,2,2,2,1},
		{0,2,1,1,1,2,1,1,1},
		{0,0,0,1,2,0,0,1,1},
		{2,2,2,2,2,0,2,2,2}
	};
    }

    @Test
    public void upIteratorNextValidPositionReturnsWhatIsinSquare()
    {
	UpIterator iterator= new UpIterator(board, new Position(3,3));
	assertEquals(1, iterator.next());
    }

    @Test
    public void upIteratorNextInvalidPositionThrows()
    {
    }

}
