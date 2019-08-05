package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import commands.Pwd;
import system.FileSystem;
import system.InputHistory;

public class PwdTest {
  /**
   * Instantiate FileSystem
   */
  private FileSystem fileSystem;
  
  /**
   * Instantiate inputHistory
   */
  private InputHistory inputHistory;
  
  /**
   * Instantiate Pwd
   */
  private Pwd pwd;
  
  private String[] arguments;
  String expected;
  String actual;

  @Before
  public void setUp() {
    inputHistory = new InputHistory();
    fileSystem = FileSystem.createRoot();
    pwd = new Pwd();
  }

  /**
   * Test if pwd prints the correct working directory.
   */
  @Test
  public void testExecute() {
    expected = "/";
    // Set arguments
    arguments = new String[]{};
    
    // Should start off in root directory.
    actual = pwd.execute(fileSystem, arguments, inputHistory);
    assertEquals(expected, actual);
    
    expected = "/folder1";
    
    fileSystem.createDirectory("/folder1");
    fileSystem.createDirectory("/folder1/folder2");

    // If entering a folder, should print path of that folder
    fileSystem.setCurrPath("/folder1");
    actual = pwd.execute(fileSystem, arguments, inputHistory);
    assertEquals(expected, actual);
    
    expected = "/folder1/folder2";
    
    // If entering a nested folder, should print path of that folder
    fileSystem.setCurrPath("/folder1/folder2");
    actual = pwd.execute(fileSystem, arguments, inputHistory);
    assertEquals(expected, actual);
  }
}
