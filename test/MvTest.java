package test;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.Mv;
import system.FileSystem;
import system.InputHistory;

public class MvTest {
  /**
   * Instantiate FileSystem
   */
  private FileSystem fileSystem;

  /**
   * Instantiate Mv
   */
  private Mv mvTest;
  
  /**
   * Instantiate inputHistory
   */
  private InputHistory inputHistory;

  /**
   * Create arguments string array
   */
  private String[] arguments;

  /**
   * Before each test case
   */
  @Before
  public void setUp(){
    inputHistory = new InputHistory();
    // Create new FileSystem
    fileSystem = FileSystem.createRoot();

    // Create Mv instance
    mvTest = new Mv();

    // Create directories at root to test
    fileSystem.createDirectory("/utsc");
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
    // Re-build FileSystem
    fileSystem.rebuild();
  }

  /**
   * Test with a directory
   */
  @Test
  public void testWithDirectory() throws Exception {

    // Set arguments
    arguments = new String[] {"/utsc", "/test/test1"};

    // Execute command
    mvTest.execute(fileSystem, arguments, inputHistory);
    
    // Check original
    assertFalse(fileSystem.validatePath("/utsc"));

    // Check new location
    assertEquals("utsc", fileSystem.getContentFromPath("/test/test1/utsc").getName());
    assertEquals("/test/test1/utsc",
        fileSystem.getContentFromPath("/test/test1/utsc").getPath());

  }

  /**
   * Test with File
   */
  @Test
  public void testWithFile() throws Exception {

    // Set arguments
    arguments = new String[] {"/testFile", "/test/test1"};

    // Execute command
    mvTest.execute(fileSystem, arguments, inputHistory);

    // Check original
    assertFalse(fileSystem.validatePath("/testFile"));

    // Check new location
    assertEquals("testFile",
        fileSystem.getContentFromPath("/test/test1/testFile").getName());
    assertEquals("/test/test1/testFile",
        fileSystem.getContentFromPath("/test/test1/testFile").getPath());
  }

  /**
   * Test with an invalid number of arguments
   */
  @Test()
  public void testInvalidNumOfArgs() throws Exception {

    // Set arguments
    arguments = new String[] {"/testFile", "/test/test1", "/test"};

    // Execute command
    mvTest.execute(fileSystem, arguments, inputHistory);
  }

  /**
   * Test with no parameters
   */
  @Test()
  public void testNoArgs() throws Exception {

    // Set arguments
    arguments = new String[] {};

    // Execute command
    mvTest.execute(fileSystem, arguments, inputHistory);
  }

  /**
   * Test moving 'utsc' into a File
   */
  @Test()
  public void testIncorrectContentType() throws Exception {

    // Set arguments
    arguments = new String[] {"/utsc", "/testFile"};

    // Execute command
    mvTest.execute(fileSystem, arguments, inputHistory);
  }

  /**
   * Test with an invalid path
   */
  @Test()
  public void testInvalidPath() throws Exception {

    // Set arguments
    arguments = new String[] {"/test6", "/testFile"};

    // Execute command
    mvTest.execute(fileSystem, arguments, inputHistory);
  }

  /**
   * Test moving 'utsc' into a directory with another directory of
   * the same directory name
   */
  @Test()
  public void testSameContentName() throws Exception {

    // Create a utsc directory
    fileSystem.createDirectory("/test/test1/utsc");

    // Set arguments
    arguments = new String[] {"/utsc", "/test/test1"};

    // Execute command
    mvTest.execute(fileSystem, arguments, inputHistory);
  }
}
