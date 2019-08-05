package commands;

import system.FileSystem;
import system.InputHistory;

/**
 * An interface used to execute a command
 */
public interface CommandExecutor {
  public String execute(FileSystem fileSystem, String[] arguments, InputHistory inputHistory) throws Exception;
}
