package test;
import liu.janva.*;
import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

//TODO refactor test isolate test to only test simplecheck in isolation (that is can i mock the behaviour of board)
public class SimpleCheckTest {

	Board board =  new SimpleGameBoard(new SimpleCheck(), 5);
//	int[][] board = {
//			{1,2,2,2,1},
//			{0,1,2,2,1},
//			{0,2,1,1,1},
//			{0,0,0,1,1},
//			{2,2,2,2,2}}; 
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testCheckWinThreeInRowHorisontaly() {
		SimpleCheck checker = new SimpleCheck();
		assertTrue(checker.checkWin(board, new Position (1, 4)));
	}
	@Test
	public void testCheckWinThreeInRowVertically() {
		SimpleCheck checker = new SimpleCheck();
		assertTrue(checker.checkWin(board, new Position (4, 1)));
	}
	@Test
	public void testCheckWinThreeInRowHorisontalyFromMiddle() {
		SimpleCheck checker = new SimpleCheck();
		assertTrue(checker.checkWin(board, new Position (2, 1)));
		//fail("not yet implemented");
	}
	@Test
	public void testCheckWinThreeInRowDiagonalTopLeftToLowRight() {
		SimpleCheck checker = new SimpleCheck();
		assertTrue(checker.checkWin(board, new Position (1, 1)));
		//fail("not yet implemented");
	}
//	@Test
//	public void testCheckWinThreeInRowHorisontaly() {
//		SimpleCheck checker = new SimpleCheck();
//		assertTrue(checker.checkWin(board, 1, 4));
//	}

}
