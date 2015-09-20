package test;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import liu.janva.Board;
import liu.janva.Checker;
import liu.janva.Position;
import liu.janva.SimpleCheck;
import liu.janva.SimpleGameBoard;

public class SimpleCheckTest {

    
    class TestDoubleBoard extends SimpleGameBoard
    {
	public TestDoubleBoard(Checker checker, int boardSize)
	{
	    super(checker, boardSize);
	    this.board = new int[][]{
		{1,2,2,2,1,2,2,2,0},
		{0,1,2,2,1,1,2,2,1},
		{0,2,1,1,1,2,1,1,1},
		{0,0,0,1,1,0,0,1,2},
		{0,0,2,2,1,2,2,2,2},
		{0,1,2,2,2,2,2,2,1},
		{0,2,1,1,1,2,1,1,1},
		{0,0,0,1,2,0,0,1,1},
		{2,2,2,2,2,2,2,2,2}
	    };
	}
    }

    private Board board; 
    private SimpleCheck checker;
    
    @Before
    public void setUp() throws Exception {
	board = new TestDoubleBoard(new SimpleCheck(), 9);
	this.checker = new SimpleCheck();
    }
    
    @Test 
    public void testCheckWinFiveInRowHorisontaly() {
	assertTrue(checker.checkWin(board, new Position (8, 0)));
    }
    @Test
    public void testCheckWinFiveInRowVertically() {
	assertTrue(checker.checkWin(board, new Position (1, 4)));
    }

    @Test
    public void testCheckWinFiveInRowRowDiagonalTopRightToLowerLeft() {
	assertTrue(checker.checkWin(board, new Position (4, 7)));
    }
    @Test
    public void testCheckWinFiveInRowDiagonalTopLeftToLowRight() {
	assertTrue(checker.checkWin(board, new Position (0, 0)));
    }
}
