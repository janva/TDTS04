package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CounterTest.class, SimpleBoardIteratorLeftTest.class, 
                SimpleBoardIteratorUpTest.class,
                SimpleBoardRightIteratorTest.class, 
                SimpleCheckTest.class,
                SimpleGameBoardTest.class})
public class AllTests
{

}
