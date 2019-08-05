package commands;

import java.util.ArrayList;
import system.FileSystem;
import system.InputHistory;
import system.Validator;

/**
 * A command that saves the old current working directory in directory stack so that it can be
 * returned to at any time.
 */
public class Pushd implements CommandExecutor{

  /** An ArrayList which acts as a directory stack */
  public static ArrayList<String> stackDirPushPop = new ArrayList<String>();
  
  /**
   * Executes the pushd command
   */
  public String execute(FileSystem fileSystem, String[] arguments, InputHistory inputHistory) {
    if (arguments.length > 0) {
      // Storing the given argument as a formated path
      String path = Validator.formatPath(arguments[0]);
      // Check if the provided path has no invalid characters and the given path exists
      if (Validator.containsInvalidChars(path) | !Validator.pathCheck(path)) {
        return null;
      }
      // Now we know provided path is valid
      else {
        // Adding current directory to the stack Directory stackDirPushPop
        stackDirPushPop.add(fileSystem.getCurrDirPath());
        // Changing working directory to the argument passed by the user
        Cd changeDir = new Cd();
        changeDir.execute(fileSystem, arguments, inputHistory);
      }
    }
    
    return null;
  }
}
