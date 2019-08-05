package commands;

import java.util.ArrayList;
import system.File;
import system.FileSystem;
import system.InputHistory;
import system.Validator;

/**
 * A command that displays the contents of a File.
 */
public class Cat implements CommandExecutor {

  /** Error message for a non-existent file */
  private final static String ERRORMSG = "Error: File not found";

  /**
   * Executes cat command
   */
  public String execute(FileSystem fileSystem, String[] arguments, InputHistory inputHistory) {
    String returnString = "";
    File fileContent = null;
    ArrayList<String> argumentsWithoutRedirection = new ArrayList<String>();
    if (arguments.length >= 2 && (arguments[arguments.length - 2].contains(">")
        || arguments[arguments.length - 2].contains(">>"))) {
      for (int i = 0; i < arguments.length - 2; i++)
        argumentsWithoutRedirection.add(arguments[i]);
    } else {
      for (int i = 0; i < arguments.length; i++)
        argumentsWithoutRedirection.add(arguments[i]);
    }
    for (String argument : argumentsWithoutRedirection) {
      // Check if the given file name has any invalid characters
      if (Validator.containsInvalidChars(argument))
        returnString += ERRORMSG;
      // Check if the file exists in the file system
      try {
        fileContent = (File) fileSystem.getContentFromPath(Validator.formatPath(argument));
        returnString += fileContent.readData() + "\n\n\n\n";
      } catch (Exception e) {
        returnString = argument + ": " + ERRORMSG + "\n" + returnString;
      }
    }
    return returnString.trim();
  }
}
