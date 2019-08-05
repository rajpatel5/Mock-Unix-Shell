package test;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.Tree;
import system.FileSystem;
import system.InputHistory;

public class TreeTest {

  /**
   * Instantiate FileSystem
   */
  private FileSystem fileSystem;

  /**
   * Instantiate inputHistory
   */
  private InputHistory inputHistory;

  /**
   * Instantiate Tree
   */
  private Tree tree;

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
    tree = new Tree();
  }

  /**
   * Re-build FileSystem after each test case
   */
  @After
  public void tearDown() {
    fileSystem.rebuild();
  }

  /**
   * Test tree with nothing in the fileSystem
   */
  @Test
  public void testEmptyFileSystem() {
    expected = "\t/";

    actual = tree.execute(fileSystem, arguments, inputHistory);

    assertEquals(expected, actual);
  }

  /**
   * Test tree with only directories in fileSystem (no files)
   */
  @Test
  public void testOnlyDirInFileSystem() {

    fileSystem.createDirectory("A");
    fileSystem.createDirectory("B");
    fileSystem.createDirectory("A/A1");

    expected = "\t/\n" + "\t\tA\n" + "\t\t\tA1\n" + "\t\tB";

    actual = tree.execute(fileSystem, arguments, inputHistory);

    assertEquals(expected, actual);
  }

  /**
   * Test tree with only files in fileSystem (no directories)
   */
  @Test
  public void testOnlyFilesInFileSystem() {

    fileSystem.makeFileAtPath("/file1");
    fileSystem.makeFileAtPath("/file2");

    expected = "\t/\n" + "\t\tfile1\n" + "\t\tfile2";

    actual = tree.execute(fileSystem, arguments, inputHistory);

    assertEquals(expected, actual);
  }

  /**
   * Test tree with both files and directories in fileSystem
   */
  @Test
  public void testFilesAndDirInFileSystem() {

    fileSystem.createDirectory("A");
    fileSystem.createDirectory("A/A1");
    fileSystem.makeFileAtPath("/file1");
    fileSystem.makeFileAtPath("/A/file2");
    fileSystem.makeFileAtPath("/A/A1/file3");

    expected =
        "\t/\n" + "\t\tA\n" + "\t\t\tA1\n" + "\t\t\t\tfile3\n" + "\t\t\tfile2\n" + "\t\tfile1";

    actual = tree.execute(fileSystem, arguments, inputHistory);

    assertEquals(expected, actual);
  }
  
  /**
   * Test tree with current directory other than root directory
   */
  @Test
  public void testCurrDirNotRoot() {
    fileSystem.createDirectory("A");
    fileSystem.createDirectory("A/A1");
    fileSystem.makeFileAtPath("/file1");
    fileSystem.makeFileAtPath("/A/file2");
    fileSystem.makeFileAtPath("/A/A1/file3");
    fileSystem.setCurrDirWithPath("A/A1");
    fileSystem.setCurrPath("/A/A1");
    
    expected =
        "\t/\n" + "\t\tA\n" + "\t\t\tA1\n" + "\t\t\t\tfile3\n" + "\t\t\tfile2\n" + "\t\tfile1";

    actual = tree.execute(fileSystem, arguments, inputHistory);

    assertEquals(expected, actual);
    assertEquals("/A/A1", fileSystem.getCurrDirPath());
  }


}
