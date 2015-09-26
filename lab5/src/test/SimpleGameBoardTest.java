package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import liu.janva.gameboard.Board;
import liu.janva.gameboard.Checker;
import liu.janva.gameboard.Position;
import liu.janva.gameboard.SimpleCheck;
import liu.janva.gameboard.SimpleGameBoard;

public class SimpleGameBoardTest
{
    class TestDoubleFullBoard extends SimpleGameBoard
    {
	public TestDoubleFullBoard(Checker checker, int boardSize)
	{
	    super(checker, boardSize);
	    this.board = new int[][]{
		{0,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1}
	    };
	}

	public int[][] getBoard()
	{
	    return board;
	}
    }
    
    Board gameBoard;
        
    @Before
    public void setUp() throws Exception
    {
	gameBoard=new SimpleGameBoard(new SimpleCheck(), 9);
    }

    @Test
    public void getMarkAtPositionSetByMarkpositionReturnsCorrectMark()
    {
	Position position=new Position(1, 3);
	int mark =1;
	gameBoard.markPosition(position , mark);
	assertEquals(mark, gameBoard.getMarkAtPosition(position));
    }

    @Test
    public void getMarkAtPositionSetByMarkpositionReturnsCorrectMarkTriangulate()
    {
	Position position=new Position(1, 3);
	int mark =2;
	gameBoard.markPosition(position , mark);
	assertEquals(mark, gameBoard.getMarkAtPosition(position));
    }
    
    @Test
    public void TestClearBoardEmptysTheboard() throws Exception
    {
	TestDoubleFullBoard fullBoard = new TestDoubleFullBoard(new SimpleCheck(), 9);
	fullBoard.clearBoard();
	int[][] boardImpl=fullBoard.getBoard();
	//hmmm not a very good test 
	for (int[] arr :boardImpl)
	{
	    for (int mark : arr)
	    {
		if (mark!=0)
		{
		    fail("Board should be empty after clear has been called");
		}
	    }
	}
    }
    
    @Test
    public void TestFullboardReturnsTrueWhenBoardIsFull() throws Exception
    {
	Board fullBoard = new TestDoubleFullBoard(new SimpleCheck(), 9);
	assertFalse(fullBoard.full());
	fullBoard.markPosition(new Position(0, 0), 1);
	assertTrue(fullBoard.full());
    }
    @Test
    public void testGetWidthReturnWidhtOfBoard() throws Exception
    {
	assertEquals(9, gameBoard.getWidth());
    }
    
    @Test
    public void testGetHeightReturnHeghtOfBoard() throws Exception
    {
	assertEquals(9, gameBoard.getHeight());
    }
}
