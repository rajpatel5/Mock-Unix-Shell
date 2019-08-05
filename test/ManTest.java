package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import commands.Man;
import system.FileSystem;
import system.InputHistory;

public class ManTest {
  
  /**
   * Instantiate FileSystem
   */
  private FileSystem fileSystem;
  
  /**
   * Instantiate inputHistory
   */
  private InputHistory inputHistory;
  
  /**
   * Instantiate Man
   */
  private Man man;
  
  private String[] arguments;
  String expected;
  String actual;

  /**
   * Setup before each test cases
   */
  @Before
  public void setUp() {
    inputHistory = new InputHistory();
    fileSystem = FileSystem.createRoot();
    man = new Man();
  }

  /**
   * Test the exit command manual
   */
  @Test
  public void testExit(){
    arguments = new String[] {"exit"};
    actual = man.execute(fileSystem, arguments, inputHistory);
    expected = man.MANEXIT;

    assertEquals(expected, actual);
  }

  /**
   * Test the mkdir command manual
   */
  @Test
  public void testMkdir(){
    arguments = new String[] {"mkdir"};
    actual = man.execute(fileSystem, arguments, inputHistory);
    expected = man.MANMKDIR;

    assertEquals(expected, actual);
  }

  /**
   * Test the cd command manual
   */
  @Test
  public void testCd() {
    arguments = new String[] {"cd"};
    actual = man.execute(fileSystem, arguments, inputHistory);
    expected = man.MANCD;

    assertEquals(expected, actual);
  }

  /**
   * Test the ls command manual
   */
  @Test
  public void testLs() {
    arguments = new String[] {"ls"};
    actual = man.execute(fileSystem, arguments, inputHistory);
    expected = man.MANLS;

    assertEquals(expected, actual);
  }

  /**
   * Test the pwd command manual
   */
  @Test
  public void testPwd(){
    arguments = new String[] {"pwd"};
    actual = man.execute(fileSystem, arguments, inputHistory);
    expected = man.MANPWD;

    assertEquals(expected, actual);
  }

  /**
   * Test the mv command manual
   */
  @Test
  public void testMv() {
    arguments = new String[] {"mv"};
    actual = man.execute(fileSystem, arguments, inputHistory);
    expected = man.MANMV;

    assertEquals(expected, actual);
  }

  /**
   * Test the cp command manual
   */
  @Test
  public void testCp(){
    arguments = new String[] {"cp"};
    actual = man.execute(fileSystem, arguments, inputHistory);
    expected = man.MANCP;

    assertEquals(expected, actual);
  }

  /**
   * Test the pushd command manual
   * 
   * @throws InvalidCommandHistorySizeException
   * @throws InvalidNumberOfArgument
   */
  @Test
  public void testPushd(){
    arguments = new String[] {"pushd"};
    actual = man.execute(fileSystem, arguments, inputHistory);
    expected = man.MANPUSHD;

    assertEquals(expected, actual);
  }

  /**
   * Test the popd command manual
   */
  @Test
  public void testPopd(){
    arguments = new String[] {"popd"};
    actual = man.execute(fileSystem, arguments, inputHistory);
    expected = man.MANPOPD;

    assertEquals(expected, actual);
  }

  /**
   * Test the history command manual
   */
  @Test
  public void testHistory(){
    arguments = new String[] {"history"};
    actual = man.execute(fileSystem, arguments, inputHistory);
    expected = man.MANHISTORY;

    assertEquals(expected, actual);
  }

  /**
   * Test the cat command manual
   */
  @Test
  public void testCat(){
    arguments = new String[] {"cat"};
    actual = man.execute(fileSystem, arguments, inputHistory);
    expected = man.MANCAT;

    assertEquals(expected, actual);
  }

  /**
   * Test the curl command manual
   */
  @Test
  public void testGet() {
    arguments = new String[] {"get"};
    actual = man.execute(fileSystem, arguments, inputHistory);
    expected = man.MANGET;

    assertEquals(expected, actual);
  }

  /**
   * Test the echo command manual
   */
  @Test
  public void testEcho(){
    arguments = new String[] {"echo"};
    actual = man.execute(fileSystem, arguments, inputHistory);
    expected = man.MANECHO;

    assertEquals(expected, actual);
  }

  /**
   * Test the man command manual
   */
  @Test
  public void testMan() {
    arguments = new String[] {"man"};
    actual = man.execute(fileSystem, arguments, inputHistory);
    expected = man.MANMAN;

    assertEquals(expected, actual);
  }

  /**
   * Test the save command manual
   */
  @Test
  public void testSave(){
    arguments = new String[] {"save"};
    actual = man.execute(fileSystem, arguments, inputHistory);
    expected = man.MANSAVE;

    assertEquals(expected, actual);
  }

  /**
   * Test the load command manual
   */
  @Test
  public void testLoad(){
    arguments = new String[] {"load"};
    actual = man.execute(fileSystem, arguments, inputHistory);
    expected = man.MANLOAD;

    assertEquals(expected, actual);
  }
  
  /**
   * Test the find command manual
   */
  @Test
  public void testFind(){
    arguments = new String[] {"find"};
    actual = man.execute(fileSystem, arguments, inputHistory);
    expected = man.MANFIND;

    assertEquals(expected, actual);
  }
  
  /**
   * Test the tree command manual
   */
  @Test
  public void testTree(){
    arguments = new String[] {"tree"};
    actual = man.execute(fileSystem, arguments, inputHistory);
    expected = man.MANTREE;

    assertEquals(expected, actual);
  }

}
