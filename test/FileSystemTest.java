package test;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.Cd;
import system.Directory;
import system.File;
import system.FileSystem;
import system.InputHistory;

public class FileSystemTest {

//Declaration Variables for FileSystem, ROOT Directory,
 // testDirectories and testFiles
 private final String SLASH = "/";
 private final Directory ROOT = new Directory(SLASH, SLASH);
 private final int numTest = 5;

 public FileSystem fileSystem;
 public Directory[] testDirectories;
 public File[] testFiles;
 String expected;
 String actual;
 
 private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
 private final PrintStream originalOut = System.out;
 
 /**
  * For each test case, create new FileSystem, Directory and File Objects
  */
 @Before
 public void setUp() {
   fileSystem = FileSystem.createRoot();
   System.setOut(new PrintStream(outContent));
   InputHistory inputHistory = new InputHistory();
   testFiles = new File[numTest - 1];
   testDirectories = new Directory[numTest];

   testDirectories[0] = new Directory("testDirectory1", "/testDirectory1");
   testDirectories[1] = new Directory("testDirectory2",  "/testDirectory1/testDirectory2");
   testDirectories[2] = new Directory("testDirectory3", "/testDirectory1/testDirectory2/testDirectory3");
   testDirectories[3] = new Directory("testDirectory4", "/testDirectory1/testDirectory4");
   testDirectories[4] = new Directory("testDirectory5", "/testDirectory5");

   testFiles[0] = new File("testFile1", "/testFile1", "test file 1");
   testFiles[1] = new File("testFile2", "/testDirectory1/testFile2", "test file 2");
   testFiles[2] = new File("testFile3", "/testDirectory1/testDirectory2/testFile3", "test file 3");
   testFiles[3] = new File("testFile4", "/testDirectory1/testDirectory2/testDirectory3/testFile4", "test file 4");

   if (fileSystem.getCurrDir().getContents().isEmpty()) {
     Cd cdtemp = new Cd();
     
     fileSystem.createDirectory(testDirectories[0].getName());
     fileSystem.createDirectory(testDirectories[4].getName());
     fileSystem.makeFileAtPath("/testFile1");
     
     cdtemp.execute(fileSystem, new String[] {"testDirectory1"}, inputHistory);
     
     fileSystem.createDirectory(testDirectories[1].getName());
     fileSystem.createDirectory(testDirectories[3].getName());
     fileSystem.makeFileAtPath("/testDirectory1/testFile2");
     
     cdtemp.execute(fileSystem, new String[] {"testDirectory2"}, inputHistory);
     
     fileSystem.createDirectory(testDirectories[2].getName());
     fileSystem.makeFileAtPath("/testDirectory1/testDirectory2/testFile3");
     
     cdtemp.execute(fileSystem, new String[] {"testDirectory3"}, inputHistory);
  
     fileSystem.makeFileAtPath("/testDirectory1/testDirectory2/testDirectory3/testFile4");
     
     cdtemp.execute(fileSystem, new String[] {"/"}, inputHistory);
   }
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
  * Test CreateRoot
  */
 @Test
 public void testCreateRoot() {
   // Checks for name and path
   assertEquals(ROOT.getName(), fileSystem.getCurrDir().getName());
   assertEquals(SLASH, fileSystem.getCurrDirPath());
 }

 /**
  * Test createDirectory
  */
 @Test
 public void testCreateDirectory() {

   // Create testROOT Directory and test Directories in testROOT
   Directory testROOT = new Directory(SLASH, SLASH);
   Directory test1 = new Directory(SLASH, "a");
   Directory test2 = new Directory(SLASH, "b");
   Directory test3 = new Directory(SLASH, "c");

   // Create directories in ROOT
   fileSystem.createDirectory("/a");
   fileSystem.createDirectory("/b");
   fileSystem.createDirectory("/c");

   // Check if the Contents are the same
   assertEquals(testROOT.getContents(), ROOT.getContents());

 }

 /**
  * Test createDirectory with an invalid name
  */
 @Test()
 public void testCreateDirectoryInvalidName() {
   expected = "Error: Invalid Characters\r\nName contains invalid characters\r\n";
   // Create directory
   fileSystem.createDirectory("/*&@^@");
   assertEquals(expected, outContent.toString());
 }

 /**
  * Test createDirectory with duplicate directories
  */
 @Test()
 public void testCreateDirectoryWithDuplicates() {
   // Create multiple directories
   fileSystem.createDirectory("/user");
   fileSystem.createDirectory("/user");
   assertEquals(4, fileSystem.getCurrDir().getContents().size());
 }
 
 /**
  * Test setCurrDirWithPath
  */
 @Test()
 public void testSetCurrDirWithPath() {
   expected = "testDirectory2";
   // Set the current directory
   fileSystem.setCurrDirWithPath("/testDirectory1/testDirectory2");
   
   assertEquals(expected, fileSystem.getCurrDir().getName());
 }
 
 /**
  * Test validatePath with valid and invalid arguments
  * 
  */
 @Test
 public void testValidatePath() {

   // Checks the existing paths within the file system
   assertTrue(fileSystem.validatePath("/"));
   assertTrue(fileSystem.validatePath("/testDirectory1"));
   assertTrue(fileSystem.validatePath("/testDirectory1/testDirectory2"));
   assertTrue(fileSystem.validatePath("/testDirectory1/testDirectory2/testDirectory3"));
   assertTrue(fileSystem.validatePath("/testDirectory1/testDirectory4"));
   assertTrue(fileSystem.validatePath("/testDirectory5"));

   // Checks the existing paths of files within the file system
   assertTrue(fileSystem.validatePath("/testFile1"));
   assertTrue(fileSystem.validatePath("/testDirectory1/testFile2"));
   assertTrue(fileSystem.validatePath("/testDirectory1/testDirectory2/testFile3"));
   assertTrue(fileSystem.validatePath("/testDirectory1/testDirectory2/testDirectory3/testFile4"));

   // Checks non-existent paths
   assertFalse(fileSystem.validatePath("/b07"));
   assertFalse(fileSystem.validatePath("/testDirectory1/testFile3"));

 }
 
 /**
  * Test getContentFromPath
  * 
  * @throws Exception 
  */
 @Test
 public void testGetContentFromPath() throws Exception {

   // Check the Directories
   assertEquals(testDirectories[0].getName(),
       fileSystem.getContentFromPath("/testDirectory1").getName());
   assertEquals(testDirectories[1].getName(),
       fileSystem.getContentFromPath("/testDirectory1/testDirectory2").getName());
   assertEquals(testDirectories[2].getName(), fileSystem
       .getContentFromPath("/testDirectory1/testDirectory2/testDirectory3").getName());
   assertEquals(testDirectories[3].getName(),
       fileSystem.getContentFromPath("/testDirectory1/testDirectory4").getName());
   assertEquals(testDirectories[4].getName(),
       fileSystem.getContentFromPath("/testDirectory5").getName());

   // Check the Files
   assertEquals(testFiles[0].getName(),
       fileSystem.getContentFromPath("/testFile1").getName());
   assertEquals(testFiles[1].getName(),
       fileSystem.getContentFromPath("/testDirectory1/testFile2").getName());
   assertEquals(testFiles[2].getName(),
       fileSystem.getContentFromPath("/testDirectory1/testDirectory2/testFile3").getName());
   assertEquals(testFiles[3].getName(),
       fileSystem.getContentFromPath("/testDirectory1/testDirectory2/testDirectory3/testFile4").getName());
 }

 /**
  * Test getContentFromPath with non-existent content
  * 
  * @throws Exception 
  */
 @Test()
 public void testGetContentFromPathNonExistentContent() {
   try {
     // Get a non-existing content
     fileSystem.getContentFromPath("/testfile252");
   }
   catch (Exception e) {
     return;
   }
 }

 /**
  * Test getParentDir
  */
 @Test()
 public void testGetParentDir(){
   expected = "testDirectory1";
   // Get a non-existing content
   actual = fileSystem.getParentDir(testDirectories[1]).getName();
   assertEquals(expected, actual);
 }
 
 /**
  * Test getDir with valid path
  */
 @Test
 public void testGetDir() {

   // checks Directory from tree above
   assertEquals(testDirectories[0].getName(),
       fileSystem.getDir("/testDirectory1").getName());
   assertEquals(testDirectories[1].getName(),
       fileSystem.getDir("/testDirectory1/testDirectory2").getName());
   assertEquals(testDirectories[2].getName(),
       fileSystem.getDir("/testDirectory1/testDirectory2/testDirectory3").getName());
   assertEquals(testDirectories[3].getName(),
       fileSystem.getDir("/testDirectory1/testDirectory4").getName());
   assertEquals(testDirectories[4].getName(),
       fileSystem.getDir("/testDirectory5").getName());
 }

 /**
  * Test getDir with invalid path
  */
 @Test()
 public void getDirWithInvalidPath(){

   // get Directory from incorrect path
   fileSystem.getDir("/testDirectory1/testDirectory5");

 }

 /**
  * Test getDirectory with incorrect Content Type
  */
 @Test()
 public void getDirWithIncorrectType() {

   // get file
   fileSystem.getDir("/testFile1");

 }
 
 /**
  * Test makeFileAtPath
  * 
  * @throws Exception 
  */
 @Test
 public void testMakeFileAtPath() throws Exception {
   //makeFileAtPath was already called in setup()
   
   // Find testFile1 in ROOT
   assertEquals(testFiles[0].getName(),
       fileSystem.getContentFromPath("/testFile1").getName());

   // Find testFile2 in testDirectory1
   assertEquals(testFiles[1].getName(),
       fileSystem.getContentFromPath("/testDirectory1/testFile2").getName());

   // Find testFile3 in testDirectory2
   assertEquals(testFiles[2].getName(),
       fileSystem.getContentFromPath("/testDirectory1/testDirectory2/testFile3").getName());

   // Find testFile4 and change directory to testDirectory4
   assertEquals(testFiles[3].getName(), fileSystem
       .getContentFromPath("/testDirectory1/testDirectory2/testDirectory3/testFile4").getName());
 }
}
