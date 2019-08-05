package commands;

import system.Directory;
import system.FileSystem;
import system.IO;
import system.InputHistory;
import system.Validator;

/**
 * A command that takes the user from the current working directory to a new directory given a path
 */
public class Cd implements CommandExecutor{
  
  /** Error message for an invalid path */
  private final static String ERRORMSG = "Error: Invalid Path Provided";
  
  /**
   * Executes cd command
   */
  public String execute(FileSystem fileSystem, String[] arguments, InputHistory inputHistory) {
    if (arguments.length > 0) {
      String path = Validator.formatPath(arguments[0]);

      if (Validator.pathCheck(path)) {
        // If it goes back to root with the path SLASH
        if (path.equals(FileSystem.SLASH)) {
          fileSystem.setCurrDir(fileSystem.getRoot());
        }
        // Otherwise it goes somewhere else
        else {
          Directory dir = fileSystem.getDir(path);
          fileSystem.setCurrDir((dir));
        }
        // Set the path
        try {
          fileSystem.setCurrPath(fileSystem.getCurrDir().getPath());
        } catch (NullPointerException e) {}
      } else {
        return ERRORMSG;
      }
    }
    
    return null;
  }
}
