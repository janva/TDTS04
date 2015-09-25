package test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import liu.janva.boardIterators.LeftIterator;
import liu.janva.gameboard.Position;

public class SimpleBoardIteratorLeftTest
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
    public void testLeftIteratorHasNext11Eq1()
    {
	LeftIterator iterator= new LeftIterator(board, new Position(1,1));
	assertTrue(iterator.hasNext());
    }
    
    @Test
    public void testLeftIteratorHasNextValidPos() throws Exception
    {
	LeftIterator iterator= new LeftIterator(board, new Position(2, 2));
	assertTrue(iterator.hasNext());
    }
    public void testLeftIteratorHasNextInvalidPos() throws Exception
    {
	LeftIterator iterator= new LeftIterator(board, new Position(1,-1 ));
	assertTrue(iterator.hasNext());
    }

}
