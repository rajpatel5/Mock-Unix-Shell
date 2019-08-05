package test;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.Cd;
import system.FileSystem;
import system.InputHistory;

public class CdTest {

  /**
   * Instantiate FileSystem
   */
  private FileSystem fileSystem;
  
  /**
   * Instantiate inputHistory
   */
  private InputHistory inputHistory;

  /**
   * Instantiate Cd
   */
  private Cd cd;

  /**
   * Create arguments string array
   */
  private String[] arguments;
  
  String actual;
  String expected;

  /**
   * Setup before each test case
   */
  @Before
  public void setUp() {
    inputHistory = new InputHistory();
    // Create new FileSystem
    fileSystem = FileSystem.createRoot();

    // Create Cd instance
    cd = new Cd();

    // Create directories
    fileSystem.createDirectory("/utsc");
    fileSystem.createDirectory("/utsc/testing");
  }

  /**
   * Re-build FileSystem after each test case
   */
  @After
  public void tearDown() {
    fileSystem.rebuild();
  }

  /**
   * Test Cd execute method valid arguments
   */
  @Test
  public void testValidArguments(){
    expected = "utsc";
    // Set arguments
    arguments = new String[] {"/utsc"};

    // Execute Cd
    cd.execute(fileSystem, arguments, inputHistory);

    // Check location in FileSystem
    assertEquals(expected, fileSystem.getCurrDir().getName());
    
    expected = "testing";
    // Set arguments
    arguments = new String[] {"/utsc/testing"};

    // Execute Cd
    cd.execute(fileSystem, arguments, inputHistory);

    // Check location in FileSystem
    assertEquals(expected, fileSystem.getCurrDir().getName());

  }
  
  /**
   * Test Cd execute command with no arguments
   */
  @Test()
  public void testCdWithNoArguments(){
    expected = null;
    
    // Set arguments
    arguments = new String[] {};

    // Execute Cd
    actual = cd.execute(fileSystem, arguments, inputHistory);
    assertEquals(expected, actual);
  }

  /**
   * Test Cd execute command with multiple arguments
   */
  @Test()
  public void testCdWithMultipleArguments(){
    expected = null;
    
    // Set arguments
    arguments = new String[] {"/utsc", "/utsc/testing"};

    // Execute Cd
    actual = cd.execute(fileSystem, arguments, inputHistory);
    assertEquals(expected, actual);
  }

  /**
   * Test Cd execute command with multiple parameters
   */
  @Test()
  public void testCdWithInvalidPath() {
    expected = "Error: Invalid Path Provided";
    
    // Set arguments
    arguments = new String[] {"/utsc/third"};

    // Execute Cd
    actual = cd.execute(fileSystem, arguments, inputHistory);
    
    // Check Error message
    assertEquals(expected, actual);
  }
}
