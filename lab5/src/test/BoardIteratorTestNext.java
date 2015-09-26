package test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import liu.janva.boardIterators.AbstractBoardIterator;
import liu.janva.boardIterators.DownIterator;
import liu.janva.boardIterators.LeftDownIterator;
import liu.janva.boardIterators.LeftIterator;
import liu.janva.boardIterators.LeftUpIterator;
import liu.janva.boardIterators.RightDownIterator;
import liu.janva.boardIterators.RightIterator;
import liu.janva.boardIterators.RightUpIterator;
import liu.janva.boardIterators.UpIterator;
import liu.janva.gameboard.Position;

@RunWith(Parameterized.class)
public class BoardIteratorTestNext
{
    static int[][] boardImpl;

    @Before
    public void setUp() throws Exception
    {
	boardImpl = new int[][]{
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

    public static int[][] getBoard()
    {
	return new int[][]{
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

    @Parameters
    public static Collection<Object[]>iterators()
    {
	return Arrays.asList(new Object[][] {
	    {new LeftIterator(getBoard(), new Position(8,1)),8,1}
	    ,{new RightIterator(getBoard(), new Position(8,1)),8,1}
	    ,{new UpIterator(getBoard(), new Position(8,1)),8,1}
	    ,{new DownIterator(getBoard(), new Position(8,1)),8,1}
	    ,{new RightUpIterator(getBoard(), new Position(8,1)),8,1}
	    ,{new RightDownIterator(getBoard(), new Position(8,1)),8,1}
	    ,{new LeftUpIterator(getBoard(), new Position(8,1)),8,1}
	    ,{new LeftDownIterator(getBoard(), new Position(8,1)),8,1}
	});
    }

    @Parameter
    public AbstractBoardIterator iter;

    @Parameter(value=1)
    public int row;

    @Parameter(value=2)
    public int col=2;

    @Test
    public void testIteratorNextAt22EqOne()
    {
	//boardImpl[2][2]
	assertIteratorNextAtEquals(boardImpl[row][col],iter);
    }

    private void assertIteratorNextAtEquals(int expected, AbstractBoardIterator iterator)
    {
	assertEquals(expected, (int)iterator.next());

    }

// depends on test above 
//    @Test
//    public void testIteratorHasNext() throws Exception
//    {
//	assertTrue(iter.hasNext());
//    }
}
