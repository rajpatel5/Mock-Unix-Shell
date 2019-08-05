package test;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.Cp;
import system.FileSystem;
import system.InputHistory;

public class CpTest {
  
  /**
   * Instantiate FileSystem
   */
  private FileSystem fileSystem;
  
  /**
   * Instantiate inputHistory
   */
  private InputHistory inputHistory;

  /**
   * Instantiate Cp
   */
  private Cp cp;
  
  /**
   * Create arguments string array
   */
  private String[] arguments;
  
  String expected;
  String actual;

  /**
   * Before each test case
   * 
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {
    inputHistory = new InputHistory();
    // Create new FileSystem
    fileSystem = FileSystem.createRoot();

    // Create Cp object
    cp = new Cp();

    // Create directories to test
    fileSystem.createDirectory("/copyingThis");
    fileSystem.createDirectory("/test");
    fileSystem.createDirectory("/test/test1");

    // Create files to test
    fileSystem.makeFileAtPath("/testFile");

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
   * Test Cp execute command with valid arguments
   */
  @Test
  public void testCpValidArguments() throws Exception{

    // Set arguments
    arguments = new String[] {"/copyingThis", "/test/test1"};

    // Execute cp command
    cp.execute(fileSystem, arguments, inputHistory);

    // Check original
    assertEquals("copyingThis", fileSystem.getContentFromPath("/copyingThis").getName());
    assertEquals("/copyingThis", fileSystem.getContentFromPath("/copyingThis").getPath());

    // Check copy
    assertEquals("copyingThis", fileSystem.getContentFromPath("/test/test1/copyingThis").getName());
    assertEquals("/test/test1copyingThis",
        fileSystem.getContentFromPath("/test/test1/copyingThis").getPath());
  }

  /**
   * Test Cp execute command with invalid number of arguments
   */
  @Test()
  public void testCpWithInvalidArguments() {
    expected = "Error: Invalid path provided";
    // Set arguments
    arguments = new String[] {"/test/test1"};

    // Execute cp command
    actual = cp.execute(fileSystem, arguments, inputHistory);
    
    assertEquals(expected, actual);
  }
  
  /**
   * Test Cp execute command with invalid number of arguments
   */
  @Test()
  public void testCpWithIncorrectContentType(){
    expected = "Error: Invalid path provided";
    
    // Set arguments
    arguments = new String[] {"/copyingThis", "/testFile"};

    // Execute cp command
    actual = cp.execute(fileSystem, arguments, inputHistory);
    
    assertEquals(expected, actual);
  }
  
}
