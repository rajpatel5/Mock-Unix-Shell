package commands;

import system.FileSystem;
import system.InputHistory;

/**
 * A command that removes the top most directory from the directory stack and makes it the current
 * working directory.
 */
public class Popd implements CommandExecutor{
  
  /** Error message for an empty stack */
  private final static String EMPTYSTACK = "Error: The directory stack is empty";
  
  /**
   * Executes popd command.
   */
  public String execute(FileSystem fileSystem, String[] arguments, InputHistory inputHistory) {
    // Check if the directory stack is empty
    if (Pushd.stackDirPushPop.size() == 0) {
      return EMPTYSTACK;
    } else {
      // Remove the top directory stored in the stack and store in the array
      String[] removed = {Pushd.stackDirPushPop.remove(Pushd.stackDirPushPop.size() - 1)};
      // Set the arguments for cd command to execute
      Cd changeDir = new Cd();
      changeDir.execute(fileSystem, removed, inputHistory);
    }
    
    return null;
  }
}
