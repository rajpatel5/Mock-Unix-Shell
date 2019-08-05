package commands;

import java.util.ArrayList;
import java.util.regex.Pattern;
import system.*;

/**
 * A command that Displays all the commands that the user has recently used, if no parameter is
 * passed to it. Otherwise gives up to the specified number of commands.
 */
public class History implements CommandExecutor {

  /** Error message for invalid arguments */
  private final static String ERRORMSG = "Error: Invalid arguments";

  /**
   * Executes history command
   */
  public String execute(FileSystem fileSystem, String[] arguments, InputHistory inputHistory) {
    String ret = "";
    // An ArrayList<String> of commands entered by user
    ArrayList<String> inputLog = inputHistory.getInputLog();
    // If no argument was passed with history command by the user or 
    if (arguments.length == 0
        || (arguments.length == 2 && (arguments[0].equals(">") || arguments[0].equals(">>")))) {
      for (String log : inputLog)
        ret += log + "\n";
    }
    // If a valid number is passed as an argument by the user
    else if (Pattern.matches("([0-9]*)", arguments[0])) {
      for (int logNumber = 1; logNumber <= inputLog.size(); logNumber++) {
        // Checking whether to print the log or not based on the passed argument
        if (inputLog.size() - logNumber < Integer.parseInt(arguments[0])) {
          ret += inputLog.get(logNumber - 1) + "\n";
        }
      }
    }
    // Anything but an integer number is passed as an argument by the user
    else {
      return ERRORMSG;
    }

    return ret.trim();
  }
}
