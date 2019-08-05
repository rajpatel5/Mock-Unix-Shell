// **********************************************************
// Assignment2:
// Student1: Raj Patel
// UTORID user_name: patel732
// UT Student #: 1005240721
// Author: Raj Patel
//
// Student2: Harshit Patel
// UTORID user_name: patel564
// UT Student #: 1004779215
// Author: Harshit Patel
//
// Student3: Aaditya Dave
// UTORID user_name: daveaadi
// UT Student #: 1005238226
// Author: Aaditya Dave
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package driver;

import java.util.Arrays;
import commands.CommandMaster;
import commands.Load;
import system.FileSystem;
import system.IO;
import system.Validator;
import system.InputHistory;

/**
 * Simulates the UNIX shell
 */
public class JShell {

  // Create the root, and create the instances for InputHistory and CommandMaster
  private static InputHistory inputHistory = new InputHistory();
  public static FileSystem fileSystem = FileSystem.createRoot();
  private static CommandMaster commandMaster = new CommandMaster(fileSystem);
  final static String EXIT = "exit";
  
  public static void main(String[] args) {
    String input;
    String[] inputSplit = null;

    System.out.print(fileSystem.getCurrDirPath() + "# ");

    while (!(input = IO.getInput()).trim().equals(EXIT)) {
      // Checks if the first command is load
      if (!input.startsWith("load ")) {
        Load.enabled = false;
      }
      inputSplit = Validator.formatForRedirection(input.trim().split("\\s+"));
      
      // Add the input to the history log
      inputHistory.setInputLog(inputSplit);
      if (Validator.checkInput(inputSplit)) {
        commandMaster.setArguments(Arrays.copyOfRange(inputSplit, 1, inputSplit.length));
        commandMaster.executeCommand(inputSplit[0], inputHistory);
      }

      // Print the path of the current working directory
      System.out.print(fileSystem.getCurrDirPath() + "# ");
    }
  }
}

