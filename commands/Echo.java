package commands;

import system.FileSystem;
import system.InputHistory;

/**
 * A command that either appends or overwrites the data in a file or prints the content to the
 * screen
 */
public class Echo implements CommandExecutor{
  
  /** Error message for an invalid string value */
  private final static String INVALIDSTRINGERRORMSG = "Error: Missing a String value";
  
  /** Error message for extra quotes */
  private final static String QUOTESERRORMSG = "Error: Cannot have double quotes inside double quotes";
  
  /**
   * Executes echo command
   */
  public String execute(FileSystem fileSystem, String[] arguments, InputHistory inputHistory) {
    String arg = null;
    
    // Joins the strings together to create one string value
    if (arguments.length >= 1) {
      arg = "";
      for (String argument: arguments) {
        // Breaks if redirection symbol is found
        if (argument.equals(CommandMaster.APPENDSYMBOL) || 
            argument.equals(CommandMaster.OVERWRITESYMBOL)) {
          break;
        }
        arg += argument + " ";
      }
    }
    
    // Checks if there is a string value given
    if (arg.charAt(0) != '\"' || arg.trim().charAt(arg.trim().length() - 1) != '\"'){
      return INVALIDSTRINGERRORMSG;
    }
    
    // Checks for extra quotes in the string
    arg = arg.trim().substring(arg.indexOf("\"") + 1, arg.lastIndexOf("\""));
      // Checks if the STRING contains any extra double quotes
    if (arg.contains("\"")) {
      return QUOTESERRORMSG;
    } 
    
    return arg;
  }
}
