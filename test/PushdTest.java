package test;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.Pushd;
import system.FileSystem;
import system.InputHistory;

public class PushdTest {

  /**
   * Instantiate FileSystem
   */
  private FileSystem fileSystem;
  
  /**
   * Instantiate inputHistory
   */
  private InputHistory inputHistory;
  
  /**
   * Instantiate Pushd
   */
  private Pushd pushd;
  
  /**
   * Create arguments string array
   */
  private String[] arguments;
  
  String expected;
  String actual;
  
  /**
   * Before each test case
   */
  @Before
  public void setUp() {
    inputHistory = new InputHistory();
    // Create new FileSystem
    fileSystem = FileSystem.createRoot();
    
    // Create new Pushd instance
    pushd = new Pushd();
    
    
    // Make directories
    fileSystem.createDirectory("/test");
    fileSystem.createDirectory("/test/test1");
  }

  /**
   * After each test case
   */
  @After
  public void tearDown(){
    // Rebuild fileSystem
    fileSystem.rebuild();
  }
  
  /**
   * Test execute
   */
  @Test
  public void testExecute() {
    expected = "test";
    // Set arguments
    arguments = new String[]{"/test"};
    
    // execute Pushd
    pushd.execute(fileSystem, arguments, inputHistory);
    
    assertEquals(expected, fileSystem.getCurrDir().getName());
    
    expected = "test1";
    // Set arguments
    arguments = new String[]{"/test/test1"};
    
    // execute Pushd
    pushd.execute(fileSystem, arguments, inputHistory);
    
    assertEquals(expected, fileSystem.getCurrDir().getName());
  }

  /**
   * Test Pushd execute command with no arguments
   */
  @Test()
  public void testPushdNoArguments() {
    expected = null;
    // Set arguments
    arguments = new String[]{};
    
    // Execute Pushd
    actual = pushd.execute(fileSystem, arguments, inputHistory);
    assertEquals(expected, actual);

  }
  
  /**
   * Test Pushd execute command with multiple arguments
   */
  @Test()
  public void testPushdMultipleArguments() {
    expected = null;
    // Set arguments
    arguments = new String[]{"/test", "/test/test1"};
    
    // Execute Pushd
    actual = pushd.execute(fileSystem, arguments, inputHistory);
    assertEquals(expected, actual);
  }

}
