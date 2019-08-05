package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import commands.Echo;
import system.FileSystem;
import system.InputHistory;

public class EchoTest {
  
   /**
    * Instantiate FileSystem
    */
    private FileSystem fileSystem;
    
    /**
     * Instantiate inputHistory
     */
    private InputHistory inputHistory;
    
   /**
    * Instantiate Echo
    */
    private Echo echo;
    
   /**
    * Create arguments string array
    */
    private String[] arguments;
 
   String actual;
   String expected;
  
   /**
    * Initialize a FileSystem and Echo command before testing
    */
   @Before
   public void setUpTest() {
     inputHistory = new InputHistory();
     fileSystem = FileSystem.createRoot();
     echo = new Echo();
   }

 /**
  * Test execute method with valid arguments
  */
 @Test
 public void testValidArguments() {
   expected = "hello there";
   
   arguments = new String[] {"\"hello", "there\""};

   actual = echo.execute(fileSystem, arguments, inputHistory);

   assertEquals(expected, actual);
 }
 
 /**
  * Test execute to check if there is an argument size mismatch
  */
 @Test()
 public void testInvalidNumberOfArgs() {
   expected = "Error: Cannot have double quotes inside double quotes";
   
   // Create a parameter of with two elements
   arguments = new String[] {"\"hello", "there\"", "\"hi\""};
   actual = echo.execute(fileSystem, arguments, inputHistory);
   
   assertEquals(expected, actual);
 }

 /**
  * Test execute with an empty string in argument
  */
 @Test
 public void testEmptyString() {
   expected = "";

   // Create a parameter with an empty string
   arguments = new String[] {"\"\""};
   
   actual = echo.execute(fileSystem, arguments, inputHistory);

   assertEquals(expected, actual);
 }
 
 /**
  * Test execute with an empty string in argument
  */
 @Test
 public void testStringWithExtraQuotes() {
   expected = "Error: Cannot have double quotes inside double quotes";

   // Create a parameter with an empty string
   arguments = new String[] {"\"Hello there\"\""};
   
   actual = echo.execute(fileSystem, arguments, inputHistory);

   assertEquals(expected, actual);
 }
}
