package test;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.History;
import system.FileSystem;
import system.InputHistory;

public class HistoryTest {
  
  /**
   * Instantiate fileSystem
   */
  private FileSystem fileSystem;
  
  /**
   * Instantiate inputHistory
   */
  private InputHistory inputHistory;
  
  /**
   * Instantiate history
   */
  private History history;

  /**
   * Create arguments string array
   */
  private String[] arguments;

  private String expected;
  private String actual;

  /**
   * Setup before each test case
   */
  @Before
  public void setUp() {
    fileSystem = FileSystem.createRoot();
    
    // Add command strings into inputHistory
    inputHistory = new InputHistory();
    inputHistory.setInputLog("mkdir test".split(" "));
    inputHistory.setInputLog("cd test".split(" "));
    inputHistory.setInputLog("utsc".split(" "));
    inputHistory.setInputLog("".split(" "));

    // Create new history object
    history = new History();
  }
  
  /**
   * Re-build FileSystem after each test case
   */
  @After
  public void tearDown() {
    fileSystem.rebuild();
  }

  /**
   * Test execute history command with no arguments
   */
  @Test
  public void testHistoryNoArguments(){
    expected = "1. mkdir test \n2. cd test \n3. utsc \n4.";
    
    // Create a new arguments string array
    arguments = new String[] {};

    // Execute history
    actual = history.execute(fileSystem, arguments, inputHistory);

    assertEquals(expected, actual);
  }


  /**
   * Test execute history command with one valid integer arguments
   */
  @Test
  public void testHistoryValidArgument() {
    expected = "2. cd test \n3. utsc \n4.";
    
    // Create a new arguments string array
    arguments = new String[] {"3"};

    // Execute history
    actual = history.execute(fileSystem, arguments, inputHistory);

    assertEquals(expected, actual);
  }


  /**
   * Test execute history command with a large positive integer argument
   */
  @Test()
  public void testHistoryLargePositiveInteger() {
    expected = "1. mkdir test \n2. cd test \n3. utsc \n4.";
    
    // Create a new arguments string array
    arguments = new String[] {"100"};

    // Execute history
    actual = history.execute(fileSystem, arguments, inputHistory);
    
    assertEquals(expected, actual);
  }

  /**
   * Test execute history command with a negative integer arguments
   */
  @Test()
  public void testHistoryNegIntegerArgument(){
    expected = "Error: Invalid arguments";
    
    // Create a new arguments string array
    arguments = new String[] {"-100"};

    // Execute history
    actual = history.execute(fileSystem, arguments, inputHistory);
    
    assertEquals(expected, actual);
  }

  /**
   * Test execute history command with invalid number of arguments
   */
  @Test()
  public void testHistoryInvalidNumberOfArguments() {
    expected = "Error: Invalid arguments";
    
    // Create a new arguments string array
    arguments = new String[] {"-100", "10"};

    // Execute history
    actual = history.execute(fileSystem, arguments, inputHistory);
    
    assertEquals(expected, actual);
  }

  /**
   * Test execute history command with a non-integer arguments
   */
  @Test()
  public void testHistoryNonIntegerArguments(){
    expected = "Error: Invalid arguments";
    
    // Create a new arguments string array
    arguments = new String[] {"abc"};

    // Execute history
    actual = history.execute(fileSystem, arguments, inputHistory);
    
    assertEquals(expected, actual);
  }

  /**
   * Test execute history command with multiple non-integer arguments
   */
  @Test()
  public void testHistoryMultipleNonIntegerArguments() {
    expected = "Error: Invalid arguments";
    
    // Create a new arguments string array
    arguments = new String[] {"abc", "xyz"};

    // Execute history
    actual = history.execute(fileSystem, arguments, inputHistory);
    
    assertEquals(expected, actual);
  }

}
