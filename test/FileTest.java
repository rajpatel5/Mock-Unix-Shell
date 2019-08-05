package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import system.File;

public class FileTest {

  public File testingFile;
  String expected;

  /**
   * Before each test case, create new File
   */
  @Before
  public void setUp() {

    // Create new File
    testingFile = new File("testingFile", "/", "");

  }

  /**
   * Test Constructor
   */
  @Test
  public void testConstructor() {
    expected = "testingFile";
    // Checks for testingFile name
    assertEquals("testingFile", testingFile.getName());
    expected = "/";
    // Check for testingFile path
    assertEquals("/", testingFile.getPath());
    
    expected = "";
    // Check for testingFile data
    assertEquals("", testingFile.readData());

  }

  /**
   * Test writeData
   */
  @Test
  public void testWriteData() {
    expected = "File testing is happening!";
    // Overwrite current data in testingFile
    testingFile.writeData("File testing is happening!");
    // Check for new data in testingFile
    assertEquals(expected, testingFile.readData());

  }
}
