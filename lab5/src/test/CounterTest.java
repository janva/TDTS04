package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import liu.janva.gameboard.Board;
import liu.janva.gameboard.Checker;
import liu.janva.gameboard.Counter;
import liu.janva.gameboard.Position;
import liu.janva.gameboard.SimpleCheck;
import liu.janva.gameboard.SimpleGameBoard;

public class CounterTest
{
    class TestDoubleBoard extends SimpleGameBoard
   {
	public TestDoubleBoard(Checker checker, int boardSize)
	{
	    super(checker, boardSize);
	    this.board = new int[][]{
		{1,2,2,2,1,2,2,2,0},
		{1,1,2,2,1,1,2,2,1},
		{1,2,1,1,1,2,1,1,1},
		{1,0,0,1,1,0,0,1,2},
		{0,0,2,2,1,2,2,2,2},
		{0,1,2,2,2,2,2,2,1},
		{0,2,0,1,1,2,1,1,1},
		{0,0,0,1,2,0,0,1,1},
		{2,2,2,2,2,2,2,2,2}
	    };
	}
   }
    
    private Board board;
    private Counter counter;
    
    @Before
    public void setUp()
    {
	board= new TestDoubleBoard(new SimpleCheck(), 9);
	
    }
    //TODO at some point remove duplicate tests
//    @Test
//    public void testCounterCountThreeInARowHorisontally()
//    {
//	assertHorizontalCountEquals(3,new Position(0,1));
//    }
    
    @Test
    public void testCount9InARowHorisantallyTriangulated() throws Exception
    {
	assertHorizontalCountEquals(9,new Position(8,1));
    
    }
    @Test
    public void testCount3Vertically() throws Exception
    {
	assertVerticalCountEquals(3,new Position(5,5));
    }
    @Test
    public void testCount2Vertically() throws Exception
    {
	assertVerticalCountEquals (2,new Position(5,6));
    }
    @Test
    public void testCount3DiagonallyToUpperRight() throws Exception
    {
	assertDiagonalUpperRightCountEquals(3, new Position(1, 6));
    }
    
    @Test
    public void testCount3DiagonallyToUpperLeft() throws Exception
    {
	assertDiagonalUpperLeftCountEquals(3, new Position(6, 5));
    }
    
    @Test
    public void testCountOneInRowShouldReturn1 () throws Exception
    {
	
	assertVerticalCountEquals(1, new Position(5, 1));
	assertHorizontalCountEquals(1, new Position(5, 1));
	assertDiagonalUpperRightCountEquals(1, new Position(5, 1));
	assertDiagonalUpperLeftCountEquals(1, new Position(5, 1));
    }
    //TODO have a lot in common
    private void assertVerticalCountEquals(int excpectedCount,Position pos)
    {
	counter = new Counter(board, pos);
	assertEquals(excpectedCount,counter.countEqualsVertically());
    }
    
    private void assertHorizontalCountEquals(int excpectedCount,Position pos)
    {
	counter = new Counter(board,pos);
	assertEquals(excpectedCount,counter.countEqualshorizontally());
    }

    private void assertDiagonalUpperRightCountEquals(int excpectedCount,Position pos)
    {
	counter = new Counter(board,pos);
	assertEquals(excpectedCount,counter.countEqualsDiagonallyToUpperRight());
    }
    
    private void assertDiagonalUpperLeftCountEquals(int excpectedCount,Position pos)
    {
	counter = new Counter(board,pos);
	assertEquals(excpectedCount, counter.countEqualsDiagonallyToUpperLeft());
    }


}
