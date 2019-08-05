package system;

import java.util.Arrays;
import java.util.List;
import driver.JShell;

/**
 * Validates the input received to the Shell.
 */
public class Validator {

  /** List of valid commands */
  static List<String> validCommands = Arrays.asList("cat", "cd", "echo", "exit", "history", "ls",
      "man", "mkdir", "popd", "pushd", "pwd", "mv", "cp", "get", "save", "load", "find", "tree");

  /** List of valid commands that includes a path string as an argument */
  static List<String> commandWithPaths =
      Arrays.asList("mkdir", "cd", "ls", "cat", "pushd", "mv", "cp", "get", "save", "load", "find");

  /** List of minimum number of parameters required for each command */
  static List<Integer> minParameters =
      Arrays.asList(2, 2, 2, 1, 1, 1, 2, 2, 1, 2, 1, 3, 3, 2, 2, 2, 6, 1);

  /** List of maximum number of parameters that can be entered for each command */
  static List<Integer> maxParameters =
      Arrays.asList(-1, 4, -1, 1, 4, -1, 4, -1, 3, 4, 3, 5, 5, 4, 4, 4, -1, 3);

  /** List that represents whether the command can redirect or not */
  static List<Boolean> hasRedirection = Arrays.asList(true, false, true, false, true, true, true,
      false, false, false, true, false, false, false, false, false, true, true);

  /** String of invalid characters */
  public final static String INVALIDCHARS = "!@$&*()?:[]\"<>'`|={}\\,;";

  /** Error message for an invalid command */
  private final static String INVALIDCOMMANDERRORMSG = "Error: Not a valid command";

  /** Error message for an invalid path */
  private final static String INVALIDPATHERRORMSG = "Error: Invalid path";

  /** Error message for an invalid characters */
  private final static String INVALIDCHARSERRORMSG = "Error: Invalid Characters";

  /**
   * Receives input from IO class(user input). Checks if the first word is a valid command or not.
   * Sets the commandName if found.
   * 
   * @param str string entered by the user
   * @return true if command is valid or else return false
   */
  private static boolean checkCommand(String command) {
    boolean isCommand = false;

    isCommand = validCommands.contains(command);
    if (!isCommand)
      IO.Output(INVALIDCOMMANDERRORMSG);

    return isCommand;
  }

  /**
   * Checks whether or not the user input is valid and no errors arise
   * 
   * @param inputSplit An array of the input parameters
   * @return true if input is valid otherwise false
   */
  public static boolean checkInput(String[] inputSplit) {
    return checkCommand(inputSplit[0]) && checkNumParameters(inputSplit);
  }

  /**
   * Receives valid command input from IO class(user input). Checks if the number of input
   * parameters are valid or not for the commandName.
   * 
   * @param str string entered by the user
   * @return true if valid number of parameters entered or else return false
   */
  private static boolean checkNumParameters(String[] input) {

    int indexOfValidCommand = validCommands.indexOf(input[0]);
    boolean correctNumOfParameters = input.length >= minParameters.get(indexOfValidCommand)
        & (maxParameters.get(indexOfValidCommand) == -1
            | input.length <= maxParameters.get(indexOfValidCommand));

    if (!correctNumOfParameters)
      IO.Output("Error: Invalid number of parameters \nTry command 'man " + input[0] + "' for the "
          + "manual");

    return correctNumOfParameters;
  }

  /**
   * Removes ">" or ">>" from the commands which do not need any redirection.
   * 
   * @param input An array of string which is the user input in form of array
   * @return array of string with ">" or ">>" removed for the commands that do not need any
   *         redirection.
   */
  public static String[] formatForRedirection(String[] input) {
    if (validCommands.contains(input[0])) {
      int indexOfValidCommand = validCommands.indexOf(input[0]);
      try {
        if (!hasRedirection.get(indexOfValidCommand)
            && (input[input.length - 2].equals(">") || input[input.length - 2].equals(">>"))) {
          return Arrays.copyOf(input, input.length - 2);
        }
      } catch (Exception e) {}
    }
    return input;
  }

  /**
   * Checks if the provided path exists or not
   * 
   * @param path A String storing a path
   * @return true If exists otherwise false
   */
  public static boolean pathCheck(String path) {
    if (!JShell.fileSystem.validatePath(path)) {
      return false;
    }

    return true;
  }

  /**
   * Checks if the name contains any invalid characters
   * 
   * @param name Name of a directory or file
   * @return true If it contains invalid chars otherwise false
   */
  public static boolean containsInvalidChars(String name) {
    for (int i = 0; i < name.length(); i++) {
      if (INVALIDCHARS.contains(Character.toString(name.charAt(i)))) {
        IO.Output(INVALIDCHARSERRORMSG);
        return true;
      }
    }
    return false;
  }

  /**
   * Formats a given path to taking into consideration of '.' and '..'
   * 
   * @param path The unformatted path
   * @return path A formatted full path
   */
  public static String formatPath(String path) {
    // Check if the path is a full path, if not then turn it into a full path
    path = convertToFullPath(path);

    // Replace all occurrences of the '..' character,
    // special character denoting to parent directory.
    path = replaceParentDirectoryNotation(path);

    // Remove all occurrences of the '.' character, denoting to current directory.
    path = replaceCurrentDirectoryNotation(path);

    // Remove the last slash character from the path if it is a directory
    try {
      if (!(path.equals("/")) && path.endsWith("/") && JShell.fileSystem
          .getContentFromPath(path.substring(0, path.length() - 1)) instanceof Directory) {
        path = path.substring(0, path.length() - 1);
      }
    } catch (Exception e) {
    }

    // If the path is empty, add '/' (root)
    if (path.equals("")) {
      path = "/";
    }

    return path;
  }

  /**
   * Converts a given path to a full path without taking to account '.' and '..' symbols in a path
   * 
   * @param path The unformatted path
   * @return path A formatted absolute path
   */
  private static String convertToFullPath(String path) {
    // Get the path of the current working directory
    String currDir = JShell.fileSystem.getCurrDirPath();

    // Check if the path is a full path if not turn it into a full path
    if (path.startsWith("/") == false) {
      if (currDir.equals("/")) {
        path = currDir + path;
      } else {
        path = currDir + "/" + path;
      }
    }
    return path;
  }


  /**
   * Formats all occurrences of '.' string inside with an equivalent path
   * 
   * @param path The unformatted path
   * @return path A formatted path
   */
  private static String replaceCurrentDirectoryNotation(String path) {
    while (path.contains("/./")) {
      path = path.replace("/./", "/");
    }
    // Remove the '.' character found at the end of a path
    if (path.endsWith("/.")) {
      path = path.substring(0, path.length() - 2);
    }
    return path;
  }


  /**
   * Formats occurrences of '..' string inside with an equivalent path
   * 
   * @param path The unformatted path
   * @return path A formatted path
   */
  private static String replaceParentDirectoryNotation(String path) {
    String subpath;
    boolean validPath = true;
    // Denotes the indexes of directory before '..'
    int end, start;
    // Format the '..' special character with the parent of current sub-path
    while (path.contains("/..") == true && validPath) {
      // If the path begins with "/.." simplify it to ""
      if (path.startsWith("/..")) {
        path = path.replaceFirst("/..", "");
      } else {
        // Let the sub-path be the path up to the first instance of '/..'
        end = path.indexOf("/..");
        subpath = path.substring(0, end);
        start = subpath.lastIndexOf("/");
        validPath = JShell.fileSystem.validatePath(subpath);
        // If the sub-path is valid, then remove the directory before the '..'
        if (validPath) {
          path = path.substring(0, start) + path.substring(end + 3);
        }
      }
      // If the sub-path is not a valid one, make the path invalid also
      if (validPath == false) {
        path = INVALIDPATHERRORMSG;
      }
    }
    return path;
  }
}


