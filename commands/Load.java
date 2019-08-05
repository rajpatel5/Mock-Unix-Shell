package commands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import system.FileSystem;
import system.InputHistory;

/**
 * A command that loads a saved file system from a file
 */
public class Load implements CommandExecutor{
  
  public static boolean enabled = true;
  private CommandMaster commandMaster;
  private String[] lineSplit = null;
  
  /** Error message for a non-existent file */
  private final String INVALIDFILEERRORMSG = "Error: Could not find the file";
  
  /** Error message for a new file system has already begun */
  private final String NEWFILESYSTEMERRORMSG = "Error: New file system has already begun";
  
  /**
   * Executes load command
   */
  public String execute(FileSystem fileSystem, String[] arguments, InputHistory inputHistory) {
    if (enabled) {
      BufferedReader reader;
      commandMaster = new CommandMaster(fileSystem);
      
      try {
        reader = new BufferedReader(new FileReader(arguments[0]));
        String line = reader.readLine();
        while (line != null) {
          lineSplit = line.split("\\s+");
          commandMaster.setArguments(Arrays.copyOfRange(lineSplit, 2, lineSplit.length));
          commandMaster.executeCommand(lineSplit[1], inputHistory);
          line = reader.readLine();
        }
        reader.close();
      } catch (IOException e) {
        enabled = false;
        return INVALIDFILEERRORMSG;
      }
    } else {
      enabled = false;
      return NEWFILESYSTEMERRORMSG;
    }
    
    enabled = false;
    return null;
  }
}
