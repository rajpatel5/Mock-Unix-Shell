package test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import system.Content;
import system.Directory;

public class DirectoryTest {

 private final String SLASH = "/";
 private final Directory ROOT = new Directory(SLASH, SLASH);

 public Directory testDirectory;
 public Directory testDirectory1;
 public Directory testDirectory2;
 public ArrayList<Content> testRootDirectory;


 /**
  * Create new directories before each test case
  */
 @Before
 public void setUpTest() {

   // Create test Directory objects
   testDirectory = new Directory("utsc", "/utsc");
   testDirectory1 = new Directory("b07", "/b07");
   // Use for exception catching
   testDirectory2 = new Directory("b07", "/b07");

   // Create an Array List of Contents
   testRootDirectory = new ArrayList<Content>();
 }

 /**
  * Test Directory Constructor
  */
 @Test
 public void testConstructor() {

   // Checks for the name of the Directory
   assertEquals("/", ROOT.getName());
   assertEquals("utsc", testDirectory.getName());

   // Checks for the path of the Directory
   assertEquals("/", ROOT.getPath());
   assertEquals("utsc", testDirectory.getName());

 }

 /**
  * Test addContent Method
  */
 @Test
 public void testAddContent() {

   // Adds test directories into ROOT
   ROOT.addContent(testDirectory);
   assertEquals(testDirectory, ROOT.getContent("utsc"));
   ROOT.addContent(testDirectory1);
   assertEquals(testDirectory1, ROOT.getContent("b07"));

 }

 /**
  * Test addContent with an existing Directory with the same name
  */
 @Test()
 public void testAddContentWithSameName(){

   ROOT.addContent(testDirectory1);
   // Throws an Exception
   ROOT.addContent(testDirectory2);
   assertEquals(1, ROOT.getContents().size());
 }


 /**
  * Test removeContent
  */
 @Test
 public void testRemoveContent() {

   // Adds testDirectory into ROOT
   ROOT.addContent(testDirectory);
   assertEquals(testDirectory, ROOT.getContent("utsc"));

   // Removes specific Directory
   ROOT.removeContent("utsc");
   assertEquals(null, ROOT.getContent("utsc"));

   // Adds all test directories into ROOT
   ROOT.addContent(testDirectory);
   ROOT.addContent(testDirectory1);

   // Removes all directories into ROOT
   ROOT.removeContent("utsc");
   ROOT.removeContent("b07");

   assertEquals(testRootDirectory, ROOT.getContents());

 }

 /**
  * Test getContents
  */
 @Test
 public void testGetContents() {

   // Adds testDirectorys into ROOT
   ROOT.addContent(testDirectory);
   ROOT.addContent(testDirectory1);

   // Create a testRootDirectory and add all testDirectorys as expected
   testRootDirectory.add(testDirectory);
   testRootDirectory.add(testDirectory1);

   assertEquals(testRootDirectory, ROOT.getContents());

 }
}
