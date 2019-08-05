package test;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.Load;
import commands.Mkdir;
import system.FileSystem;
import system.InputHistory;

public class LoadTest {
  
  /**
   * Instantiate FileSystem
   */
  private FileSystem fileSystem;
  
  /**
   * Instantiate inputHistory
   */
  private InputHistory inputHistory;
  
  /**
   * Instantiate Load
   */
  private Load load;
  
  /**
   * Create arguments string array
   */
  private String[] arguments;
  
  String actual;
  String expected;
  String FileName = "testLoad";
  
  /**
   * Setup before each test case
   */
  @Before
  public void setUp() {
    inputHistory = new InputHistory();
    // Create new FileSystem
    fileSystem = FileSystem.createRoot();

    // Create Load instance
    load = new Load();
    load.enabled = true;
  }
  
  /**
   * Re-build FileSystem after each test case
   */
  @After
  public void tearDown() {
    fileSystem.rebuild();
  }
  
  /**
   * Test a file that exists
   * 
   * @throws Exception 
   */
  @Test
  public void testValidFile() throws Exception {
    expected = null;
    // Set the arguments
    arguments = new String[] {"testLoad"};
    
    // Execute load
    actual = load.execute(fileSystem, arguments, inputHistory);
    
    assertEquals(expected, actual);
    
    // Testing if the commands ran from the file
    expected = "test:  testing";
    assertEquals(expected, fileSystem.getContentFromPath("/test").toString());
    
    expected = "/:  test test1 testFile";
    assertEquals(expected, fileSystem.getContentFromPath("/").toString());
  }
  
  /**
   * Test a file that does not exists
   */
  @Test
  public void testInvalidFile() {
    expected = "Error: Could not find the file";
    
    // Set the arguments
    arguments = new String[] {"testing"};
    
    // Execute load
    actual = load.execute(fileSystem, arguments, inputHistory);
    
    assertEquals(expected, actual);
  }
  
  /**
   * Test after a new file system has been created
   */
  @Test
  public void testAfterNewFileSystem() {
    Mkdir mkdir = new Mkdir();
    expected = "Error: New file system has already begun";
    
    // Set the arguments for mkdir
    arguments = new String[] {"testingLoad"};
    // Execute mkdir 
    mkdir.execute(fileSystem, arguments, inputHistory);
    load.enabled = false; // Simulating a command has executed
    
    // Execute load
    actual = load.execute(fileSystem, arguments, inputHistory);
    
    assertEquals(expected, actual);
  }
}
