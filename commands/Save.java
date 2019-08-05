package commands;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import system.FileSystem;
import system.InputHistory;

/**
 * A command that saves file system in a file
 */
public class Save implements CommandExecutor{
  
  /** Error message for an existent file */
  private final String ERRORMSG = "Error: File already exists.";
  
  /**
   * Executes save command
   */
  public String execute(FileSystem fileSystem, String[] arguments, InputHistory inputHistory) throws IOException {
    File file = new File(arguments[0]);
    if (file.createNewFile())
    {
      ArrayList<String> inputLog = inputHistory.getInputLog(); // Change later to valid log.
      FileOutputStream stream = new FileOutputStream(arguments[0]);
      for (String command : inputLog)
      {
        stream.write((command + "\n").getBytes());
      }
      stream.close();
    }
    else
    {
      return ERRORMSG;
    }
    return null;
  }
}
