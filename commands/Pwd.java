package commands;

import system.FileSystem;
import system.InputHistory;

/**
 * A command that prints out the full path of the current working directory.
 */
public class Pwd implements CommandExecutor{

  /**
   * Executes pwd command
   */
  public String execute(FileSystem fileSystem, String[] arguments, InputHistory inputHistory) {
    return fileSystem.getCurrDirPath();
  }

}
