package commands;

import system.FileSystem;
import system.InputHistory;
import system.Directory;
import system.File;
import java.util.ArrayList;
import system.Content;

/**
 * A command that finds a file or a set of files
 */
public class Find implements CommandExecutor {
  /** Error message for invalid path */
  private final static String INVALIDPATHERRORMSG = "Error: Invalid path";
  /** Error message for invalid arguments */
  private final static String INVALIDARGUMENTS = "Error: Invalid Arguments";

  /**
   * Executes find command
   */
  public String execute(FileSystem fileSystem, String[] arguments, InputHistory inputHistory) {
    StringBuffer ret = new StringBuffer("");// ret is the return string. methods append to ret
    ArrayList<String> paths = new ArrayList<>();
    //1st index represent search type file/directory; 2nd index represent name of search expression.
    ArrayList<String> searchingFor = new ArrayList<>();
    Directory curDirectory = fileSystem.getCurrDir();// storing current directory to get back at end
    parseArguments(fileSystem, paths, arguments, searchingFor);
    if (paths.size() == 0)
      return INVALIDARGUMENTS;
    // setting original current Directory as current directory after search is over
    for (String path : paths) {
      if (fileSystem.validatePath(path)) {
        fileSystem.setCurrDirWithPath(path);
        if (fileSystem.getCurrDir().getPath().equals(path)) search(fileSystem, ret, searchingFor);
        else ret.append(path + ": " + INVALIDPATHERRORMSG);
      } else ret.append(path + ": " + INVALIDPATHERRORMSG);
      fileSystem.setCurrDir(curDirectory);
    }
    fileSystem.setCurrDir(curDirectory);
    if (ret.toString().trim().equals("")) {
      if (searchingFor.get(0).equals("f"))
        return "File with Name: \"" + searchingFor.get(1) + "\" not found in requested path(s)";
      else
        return "Directory with Name: \""+searchingFor.get(1) + "\" not found in requested path(s)";
    }
    if (searchingFor.get(0).equals("f")) return "Requested file name: \"" + searchingFor.get(1)
          + "\" found at following requested path(s)\n" + ret.toString().trim();
    else return "Requested directory name: \"" + searchingFor.get(1)
          + "\" found at following requested path(s)\n" + ret.toString().trim();
  }

  /**
   * function for recursive searching
   */
  private void search(FileSystem fileSystem, StringBuffer ret, ArrayList<String> searchingFor) {

    // get the contents of the currentDirectory
    java.util.List<Content> contentTempDir = fileSystem.getCurrDir().getContents();

    // if the directory has content in it
    if (contentTempDir.size() != 0) {
      for (Content content : contentTempDir) {
        Directory tempCurrDirectory = fileSystem.getCurrDir();
        if (content instanceof File && searchingFor.get(0).equals("f")
            && content.getName().equals(searchingFor.get(1))) {
          ret.append(tempCurrDirectory.getPath() + "\n");
          continue; // continue to the next loop
        }
        if (content instanceof File)
          continue;
        if (searchingFor.get(0).equals("d") && content.getName().equals(searchingFor.get(1))) {
          ret.append(tempCurrDirectory.getPath() + "\n");
        }
        // if the content is a directory
        fileSystem.setCurrDirWithPath(content.getPath());
        // recursive call with new current directory
        search(fileSystem, ret, searchingFor);
        fileSystem.setCurrDir(tempCurrDirectory);
      }
    }


  }

  /**
   * Helper function to separate arguments
   */
  private void parseArguments(FileSystem fileSystem, ArrayList<String> paths, String[] arguments,
      ArrayList<String> searching) {
    int len = arguments.length;
    int redirection = 0;
    if (arguments[len - 2].equals(">") || arguments[len - 2].equals(">>"))
      redirection = 2;

    if (!arguments[len - 2 - redirection].equals("-name")
        || !arguments[len - 4 - redirection].equals("-type")
        || !(arguments[len - 3 - redirection].equals("f")
            || arguments[len - 3 - redirection].equals("d"))) {
      return;
    }

    if (!(arguments[len - 1 - redirection].startsWith("\"")
        && arguments[len - 1 - redirection].endsWith("\"")))
      return;

    searching.add(arguments[len - 3 - redirection]);
    searching.add(arguments[len - 1 - redirection].substring(1,
        arguments[len - 1 - redirection].length() - 1));
    for (int i = 0; i < (len - 4 - redirection); i++) {
      if (arguments[i].startsWith("/"))
        paths.add(arguments[i]);
      else {
        paths.add(fileSystem.getCurrDirPath() + arguments[i]);
      }
    }
  }
}


