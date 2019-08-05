package test;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.Ls;
import system.FileSystem;
import system.InputHistory;

public class LsTest {

  /**
   * Instantiate FileSystem
   */
  private FileSystem fileSystem;

  /**
   * Instantiate inputHistory
   */
  private InputHistory inputHistory;

  /**
   * Instantiate Ls
   */
  private Ls ls;
  /** Error message for an invalid path */
  private final static String ERRORMSG = "Error: Invalid Path Provided";
  /**
   * Create arguments string array
   */
  private String[] arguments;

  String expected;
  String actual;

  /**
   * Setup before each test case
   */
  @Before
  public void setUp() {
    inputHistory = new InputHistory();
    // Create new FileSystem
    fileSystem = FileSystem.createRoot();
    // initializing arguments array
    arguments = new String[] {};

    // Create Tree instance
    ls = new Ls();
  }

  /**
   * Re-build FileSystem after each test case
   */
  @After
  public void tearDown() {
    fileSystem.rebuild();
  }

  /**
   * Testing ls with empty fileSystem
   */
  @Test
  public void testEmptyFileSystem() {
    expected = null;
    actual = ls.execute(fileSystem, arguments, inputHistory);
    assertEquals(expected, actual);
  }

  /**
   * Testing ls with only directories in fileSystem
   */
  @Test
  public void testOnlyDirsInFileSystem() {
    fileSystem.createDirectory("A");
    fileSystem.createDirectory("B");

    expected = "A\nB";
    actual = ls.execute(fileSystem, arguments, inputHistory);
    assertEquals(expected, actual);
  }

  /**
   * Testing ls with only files in fileSystem
   */
  @Test
  public void testOnlyFilesInFileSystem() {
    fileSystem.makeFileAtPath("/file1");
    fileSystem.makeFileAtPath("/file2");

    expected = "file1\nfile2";
    actual = ls.execute(fileSystem, arguments, inputHistory);
    assertEquals(expected, actual);
  }

  /**
   * Testing ls with both files and directories in fileSystem
   */
  @Test
  public void testWithFilesAndDirsInFileSystem() {
    fileSystem.createDirectory("A");
    fileSystem.createDirectory("B");
    fileSystem.makeFileAtPath("/file1");
    fileSystem.makeFileAtPath("/file2");
    expected = "A\nB\nfile1\nfile2";
    actual = ls.execute(fileSystem, arguments, inputHistory);
    assertEquals(expected, actual);
  }

  /**
   * Testing ls with Arguments of file and directories
   */
  @Test
  public void testWithArguments() {
    fileSystem.makeFileAtPath("/file1");
    fileSystem.createDirectory("A");
    fileSystem.createDirectory("A/A1");
    fileSystem.makeFileAtPath("/A/file2");
    arguments = new String[] {"A","file1"};
    expected = "/A: A1 file2 \n/file1: "+ERRORMSG;
    actual = ls.execute(fileSystem, arguments, inputHistory);
    
    assertEquals(expected, actual);
  }

  /**
   * Testing ls with Recursion
   */
  @Test
  public void testWithRecursion() {
    fileSystem.createDirectory("A");
    fileSystem.createDirectory("A/A1");
    fileSystem.makeFileAtPath("/A/file2");
    fileSystem.makeFileAtPath("/file1");
    
    arguments = new String[] {"-R"};
    
    expected = "/: A file1 \n"
        + "/A: A1 file2 \n"
        + "/A/A1:";
    actual = ls.execute(fileSystem, arguments, inputHistory);
    assertEquals(expected, actual);
  }

  /**
   * Testing ls with Recursion with Arguments
   */
  @Test
  public void testWithRecursionWithArguments() {
    fileSystem.createDirectory("/A1");
    fileSystem.createDirectory("/A1/1");
    fileSystem.createDirectory("/A2");
    fileSystem.createDirectory("/A2/2");
    
    arguments = new String[] {"-R", "/A1", "/A2"};
    
    expected = "/A1: 1 \n"
        + "/A1/1: \n"
        + "/A2: 2 \n"
        + "/A2/2:";
    
    actual = ls.execute(fileSystem, arguments, inputHistory);
    
    assertEquals(expected, actual);
    
  }

}
