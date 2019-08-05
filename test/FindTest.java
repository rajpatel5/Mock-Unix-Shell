package test;

import static org.junit.Assert.*;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import system.FileSystem;
import system.Validator;
import commands.Cat;
import commands.Find;
import system.FileSystem;
import system.InputHistory;
import system.Validator;

public class FindTest {
  /**
   * Instantiate FileSystem
   */
  private FileSystem fileSystem;
  
  /**
   * Instantiate inputHistory
   */
  private InputHistory inputHistory;

  /**
   * Instantiate find
   */
  private Find find;

  /**
   * Create arguments string array
   */
  private String[] arguments;
  
  String expected;
  String actual;

  @Before
  public void setUp() {
    //create find object
    find = new Find();
    inputHistory = new InputHistory();
    //reset expected and actual values to empty string
    expected = "";
    actual = "";
    fileSystem = FileSystem.createRoot();
    fileSystem.createDirectory("a");
    fileSystem.setCurrDirWithPath("/a");
    fileSystem.createDirectory("b");
    fileSystem.createDirectory("c");
    fileSystem.setCurrDirWithPath("/a/b");
    fileSystem.createDirectory("d");
    fileSystem.setCurrDirWithPath("/a/c");
    fileSystem.createDirectory("d");
    fileSystem.setCurrPath("/a");
  }
  @After 
  public void tearDown() {
    //re-build fileSystem
    fileSystem.rebuild();
  }
  //test execute with an invalid path
  @Test
  public void testInvalidPath() {
    arguments = new String[] {"bad", "-type", "d", "-name", "\"d\""};
    expected = "Requested directory name: \"d\" found at following requested path(s)\n"
        + "/a/bad: Error: Invalid path";
    actual = find.execute(fileSystem, arguments, inputHistory);
    assertEquals(expected, actual);
  }
  //test execute with multiple valid paths with valid directory
  @Test
  public void testMultipleValidPaths() {
    arguments = new String[] {"b", "/a/c", "-type", "d", "-name", "\"d\"" };
    expected = "Requested directory name: \"d\" found at following requested path(s)\n" +
        "/a/b: Error: Invalid path\n" + "/a/c: Error: Invalid path";
    actual = find.execute(fileSystem, arguments, inputHistory);
    assertEquals(expected, actual);
  }
  //test execute with invalid relative path
  @Test 
  public void testValidRelativePath() {
    arguments = new String[] {"b", "-type", "d", "-name", "\"d\""};
    expected = "Requested directory name: \"d\" found at following requested path(s)\n" + 
        "/a/b: Error: Invalid path";
    actual = find.execute(fileSystem, arguments, inputHistory);
    assertEquals(expected, actual);
  }
}
