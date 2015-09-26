package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import liu.janva.boardIterators.RightIterator;
import liu.janva.gameboard.Position;

public class SimpleBoardRightIteratorTest
{
    private int[][] board;
    
    @Before
    public void setUp() throws Exception
    {
//	   board = new SimpleGameBoard(new SimpleCheck(),9);
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
    public void tesRightIteratorHasNextAt89ReturnsFalse()
    {
	RightIterator iterator= new RightIterator(board, new Position(8, 9));
	assertFalse(iterator.hasNext());
    }
    
    @Test
    public void testRightIteratorNextPos88shouldReturn2() throws Exception
    {
	assertMarkEqualsMarkReturnByNextOpAtPosition(2 ,new Position(8, 8));

    }
    
    @Test
    public void testRightIteratorNextPos00shouldReturn1() throws Exception
    {
	assertMarkEqualsMarkReturnByNextOpAtPosition(1 ,new Position(0, 0));
    }
    
    @Test
    public void hasNextThrowNoSuchElementExceptionIfNextIsOutside() throws Exception
    {
	try
	{
		Position startFrom = new Position(9,9);
		RightIterator iterator= new RightIterator(board, startFrom);
		iterator.next();
		fail("next() should throw NoSuchELementException if "
			+ "position is outside the board");
	} catch (NoSuchElementException excpected)
	{
	}catch (Exception e) {
	    fail("next() should throw CORRECT exception namly NoSuchElementException");
	} 
    }
    
    private void assertMarkEqualsMarkReturnByNextOpAtPosition(int expected, Position position)
    {
	RightIterator iterator= new RightIterator(board, position);
	assertEquals(expected, iterator.next());
    }
}
    
