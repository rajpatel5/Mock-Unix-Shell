package test;

import static org.junit.Assert.*;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import system.FileSystem;
import system.Validator;

public class ValidatorTest {
  private String[] testInputSplit0;
  private String[] testInputSplit1;
  private String[] testInputSplit2;
  private String[] testInput1;
  private String[] testInput2;
  private String[] testInput3;
  private String[] testInput4;
  private String path1;
  private String path2;
  private String path3;
  private String path4;
  private FileSystem fileSystem;
  private String testInvalidChars1;
  private String testInvalidChars2;
  private String testInvalidChars3;
  private String testInvalidChars4;
  private final PrintStream originalOut = System.out;
  
  @Before
  public void setUp(){
    testInputSplit0 = new String[] {"cd", "CSCB07/Assignment2/PartB"};
    testInputSplit1 = new String[]{"man", "cd", "ls", "mkdir", "too", "many", "arguments"};
    testInputSplit2 = new String[]{"MAN", "man"};
    testInput1 = new String[]{"inv", "blah", "blah"};
    testInput2 = new String[] {"man", "cat"};
    testInput3 = new String[] {"cd", "..", ">", "OUTFILE"};
    testInput4 = new String[] {"cd", ".."};
    fileSystem = FileSystem.createRoot();
    fileSystem.createDirectory("a");
    fileSystem.setCurrDirWithPath("/a");
    fileSystem.createDirectory("b");
    fileSystem.setCurrDirWithPath("/a/b");
    fileSystem.createDirectory("c");
    fileSystem.setCurrDirWithPath("/a/b/c");
    fileSystem.createDirectory("d");
    fileSystem.setCurrDirWithPath("/a/b");
    path1 = "c/d";
    path2 = "/..";
    path3 = "/.";
    path4 = "/a/b/c/d";
    testInvalidChars1 = "regularDirectory";
    testInvalidChars2 = "!bad";
    testInvalidChars3 = "stillbad@";
    testInvalidChars4 = "!ver$yba!d";
  }
  
  /**
   * Re-build FileSystem after each test case
   */
  @After
  public void tearDown() {
    fileSystem.rebuild();
    System.setOut(originalOut);
  }
  
  /**
   * Test if path exists or not
   */
  @Test
  public void testPathCheck() {
    assertTrue(Validator.pathCheck(path4));
    // Relative path check
    assertFalse(Validator.pathCheck(path1));
    assertFalse(Validator.pathCheck(path2));
  }
  
  /**
   * Test arguments containing invalid characters
   */
  @Test
  public void testContainsInvalidChars() {
    // String with no invalid chars
    assertFalse(Validator.containsInvalidChars(testInvalidChars1));
    // String with invalid char in start
    assertTrue(Validator.containsInvalidChars(testInvalidChars2));
    // String with invalid char at end
    assertTrue(Validator.containsInvalidChars(testInvalidChars3));
    // Multiple invalid chars
    assertTrue(Validator.containsInvalidChars(testInvalidChars4));
  }
  
  /**
   * Test arguments containing invalid characters
   */
  @Test
  public void testFormatPath() {
    // Relative path.
    assertEquals("/c/d", Validator.formatPath(path1));
    // Path with ..
    assertEquals("/", Validator.formatPath(path2));
    // Path with .
    assertEquals("/", Validator.formatPath(path3));
  }
  
  /**
   * Test redirection format
   */
  @Test
  public void testFormatForRedirection() {
    String[] expected;
    
    assertArrayEquals(testInput1, Validator.formatForRedirection(testInput1));
    // Input command that has redirection
    assertArrayEquals(testInput2, Validator.formatForRedirection(testInput2));
    
    expected = new String[] {"cd", ".."};
    
    // Input Command that shouldn't have redirection
    assertArrayEquals(expected, Validator.formatForRedirection(testInput3));
    // Input command that doesn't have redirection, and doesn't use it in its input.
    assertArrayEquals(testInput4, Validator.formatForRedirection(testInput4));
    
  }
  
  /**
   * Test if input is valid or not
   */
  @Test
  public void testCheckInput() {
    assertTrue(Validator.checkInput(testInputSplit0));
    // Too many arguments
    assertFalse(Validator.checkInput(testInputSplit1));
    // Case sensitive input
    assertFalse(Validator.checkInput(testInputSplit2));
  }
}
