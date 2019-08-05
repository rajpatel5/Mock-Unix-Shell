package test;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.Mkdir;
import system.FileSystem;
import system.InputHistory;

public class MkdirTest {

  /**
   * Instantiate FileSystem
   */
  private FileSystem fileSystem;
  
  /**
   * Instantiate inputHistory
   */
  private InputHistory inputHistory;

  /**
   * Instantiate Mkdir
   */
  private Mkdir mkdir;

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

    // create Mkdir object
    mkdir = new Mkdir();

  }

  /**
   * After each test case
   */
  @After
  public void tearDown() {
    // Reset FileSystem
    fileSystem.rebuild();
  }

  /**
   * Test Mkdir execute command with valid arguments
   * 
   * @throws Exception 
   */
  @Test
  public void testMkdirValidArguments() throws Exception{

    // Set arguments
    arguments = new String[] {"/test"};

    // Execute mkdir
    mkdir.execute(fileSystem, arguments, inputHistory);

    // Check if it made a directory
    assertEquals("test", fileSystem.getContentFromPath("/test").getName());
    assertEquals("/test", fileSystem.getContentFromPath("/test").getPath());
  }

  /**
   * Test Mkdir execute command with multiple arguments
   * 
   * @throws Exception 
   */
  @Test
  public void testMkdirMultipleArguments() throws Exception{

    // Set arguments
    arguments = new String[] {"/test", "/test/test1"};

    // Execute mkdir
    mkdir.execute(fileSystem, arguments, inputHistory);

    // Check if it made a directory
    assertEquals("test", fileSystem.getContentFromPath("/test").getName());
    assertEquals("/test", fileSystem.getContentFromPath("/test").getPath());

    // Check if it made a directory
    assertEquals("test1", fileSystem.getContentFromPath("/test/test1").getName());
    assertEquals("/test/test1", fileSystem.getContentFromPath("/test/test1").getPath());

  }

  /**
   * Test Mkdir execute command with an invalid path
   */
  @Test()
  public void testMkdirWithInvalidPath(){
    expected = null;
    // Set arguments
    arguments = new String[] {"/test/test1"};

    // Execute mkdir
    actual = mkdir.execute(fileSystem, arguments, inputHistory);
    
    assertEquals(expected, actual);
  }
  
  /**
   * Test Mkdir execute command recursively
   * 
   * @throws Exception 
   */
  @Test
  public void testMkdirRecursively() throws Exception{

    // Set arguments
    arguments = new String[] {"/test/testing"};

    // Execute mkdir
    mkdir.execute(fileSystem, arguments, inputHistory);

    // Check if it made a directory
    assertEquals("testing", fileSystem.getContentFromPath("/test/testing").getName());
    assertEquals("/test/testing", fileSystem.getContentFromPath("/test/testing").getPath());
  }
}
