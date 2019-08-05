package commands;

import java.util.ArrayList;
import java.util.List;
import system.FileSystem;
import system.InputHistory;

/**
 * A command that creates a directory in another directory specified by a path.
 */
public class Mkdir implements CommandExecutor {

  /**
   * Executes mkdir command
   */
  public String execute(FileSystem fileSystem, String[] arguments, InputHistory inputHistory) {
    ArrayList<String> newArgs = new ArrayList<>();
    for (String argument : arguments) {
      if(argument.equals("/")) continue;
      if (argument.contains("/")) {
        for (int i = 0; i < argument.length(); i++) {
          if (argument.charAt(i) == '/') {
            if(!argument.substring(0, i).equals("/"))
              newArgs.add(argument.substring(0, i));
          }
        }
      }
      if(!argument.equals("/"))
        newArgs.add(argument);

    }
    while(newArgs.contains(""))
      newArgs.remove("");

    for (String argument : newArgs) {
      fileSystem.createDirectory(argument);
    }

    return null;
  }

}
