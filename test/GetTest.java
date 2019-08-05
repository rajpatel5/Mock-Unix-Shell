package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import commands.Get;
import system.File;
import system.FileSystem;
import system.InputHistory;

public class GetTest {

  /**
   * Instantiate FileSystem
   */
   private FileSystem fileSystem;
   
  /**
   * Instantiate inputHistory
   */
  private InputHistory inputHistory;
  
  private Get get;
  String[] arguments;
  String expected;
  String actual;

  @Before
  public void setUp() {
    inputHistory = new InputHistory();
    fileSystem = FileSystem.createRoot();
    get = new Get();
  }

  /**
   * Test with a text file
   */
  @Test
  public void testGetTextFile() {
    arguments = new String[] {"http://www.cs.cmu.edu/~spok/grimmtmp/188.txt"};
    expected =
        "Three women were transformed into flowers which grew in\n"
            + "the field, but one of them was allowed to be in her own home "
            + "at\n"
            + "night.  Then once when day was drawing near, and she was "
            + "forced to\n"
            + "go back to her companions in the field and become a flower "
            + "again,\n"
            + "she said to her husband, if you will come this afternoon and\n"
            + "gather me, I shall be set free and henceforth stay with you.  "
            + "And\n"
            + "he did so.  Now the question is, how did her husband know her, "
            + "for\n"
            + "the flowers were exactly alike, and without any difference.\n"
            + "Answer - as she was at her home during the night and not in "
            + "the\n"
            + "field, no dew fell on her as it did on the others, and by "
            + "this\n" + "her husband knew her.";
    actual = null;
    
    get.execute(fileSystem, arguments, inputHistory);
    
    try {
      actual = ((File)fileSystem.getContentFromPath("/188.txt")).readData();
    } catch (Exception e) {}
    
    assertEquals(expected, actual);
  }

  /**
   * Test with an HTML file
   */
  @Test
  public void testGetHTMLFile() {
    arguments = new String[] {"http://www.utsc.utoronto.ca/~nick/cscB36/index.html"};
    expected = "Boo!";
    actual = null;
    
    get.execute(fileSystem, arguments, inputHistory);
    
    try {
      actual = ((File)fileSystem.getContentFromPath("/index.html")).readData();
    } catch (Exception e) {}
    
    assertEquals(expected, actual);
  }

  /**
   * Test get on an invalid URL
   */
  @Test()
  public void testURLDoesNotExist() {
    expected = "Error: Cannot write to the file";
    
    arguments = new String[] {"abcdefghijklmnopqrstuvwxyz.com"};
    
    actual = get.execute(fileSystem, arguments, inputHistory);
    
    assertEquals(expected, actual);
  }

}
