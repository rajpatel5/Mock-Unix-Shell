package test;

import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.Save;
import system.FileSystem;
import system.InputHistory;

public class SaveTest {
  
  /**
   * Instantiate FileSystem
   */
  private FileSystem fileSystem;
  
  /**
   * Instantiate inputHistory
   */
  private InputHistory inputHistory;
  
  /**
   * Instantiate Save
   */
  private Save save;
  
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
    // Create new FileSystem
    fileSystem = FileSystem.createRoot();

    // Create save instance
    save = new Save();
    
    // Create new InputHistory instance and set values for it's log
    inputHistory = new InputHistory();
    inputHistory.setInputLog(new String[] {"mkdir", "test"});
    inputHistory.setInputLog(new String[] {"cd", "test"});
    inputHistory.setInputLog(new String[] {"mkdir", "testing"});
    inputHistory.setInputLog(new String[] {"cd", ".."});
    inputHistory.setInputLog(new String[] {"mkdir", "test1"});
    inputHistory.setInputLog(new String[] {"echo", "\"This is a test\"", ">", "testFile"});
    inputHistory.setInputLog(new String[] {"cd", "testFile"});
    inputHistory.setInputLog(new String[] {"echo", "hello"});
    inputHistory.setInputLog(new String[] {"pwd"});
    inputHistory.setInputLog(new String[] {"tree"});
    inputHistory.setInputLog(new String[] {"save" , "testSave"});
  }
  
  /**
   * Re-build FileSystem after each test case
   */
  @After
  public void tearDown() {
    fileSystem.rebuild();
  }
  
  /**
   * Test execute method
   * 
   * @throws IOException 
   */
  @Test
  public void testExecute() throws IOException {
    expected = null;
    
    // Set the arguments
    arguments = new String[] {"testSave"};
    
    // Deleted the old file
    File file = new File(arguments[0]);
    file.delete();
    
    // Execute save
    actual = save.execute(fileSystem, arguments, inputHistory);
    
    assertEquals(expected, actual);
    
    // Testing if the content of testSave is correct
    actual = "";
    BufferedReader reader;
    expected = "1. mkdir test 2. cd test 3. mkdir testing 4. cd .. " + 
        "5. mkdir test1 6. echo \"This is a test\" > testFile 7. cd testFile " + 
        "8. echo hello 9. pwd 10. tree 11. save testSave ";
    
    // Creating a string of the data of testSave
    try {
      reader = new BufferedReader(new FileReader(arguments[0]));
      String line = reader.readLine();
      while (line != null) {
        actual += line;
        line = reader.readLine();
      }
      reader.close();
    } catch (IOException e) {}
    
    assertEquals(expected, actual);
  }
  
  /**
   * Test output of command if a file already exists
   * 
   * @throws IOException 
   */
  @Test
  public void testDuplicateFile() throws IOException {
    expected = "Error: File already exists.";
    
    // Set the arguments
    arguments = new String[] {"duplicateTestSave"};
    
    // Execute save
    actual = save.execute(fileSystem, arguments, inputHistory);
    
    assertEquals(expected, actual);
  }
}
