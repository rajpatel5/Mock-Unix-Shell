package commands;

import java.util.ArrayList;
import java.util.List;
import system.Content;
import system.Directory;
import system.File;
import system.FileSystem;
import system.InputHistory;

/**
 * A command that prints the contents of the directory at a specified location.
 */
public class Ls implements CommandExecutor {


  /** Error message for an invalid path */
  private final static String ERRORMSG = "Error: Invalid Path Provided";

  /**
   * Executes ls command
   */
  public String execute(FileSystem fileSystem, String[] arguments, InputHistory inputHistory) {
    StringBuffer ret = new StringBuffer("");// ret is the return string. methods append to ret
    ArrayList<String> paths = new ArrayList<>();
    Directory curDirectory = fileSystem.getCurrDir();

    boolean Recursion = isRecursive(fileSystem, paths, arguments);
    if (paths.size() == 0) {
      listing(fileSystem, ret, Recursion,paths);
    } else {
      for (String path : paths) {
        if (fileSystem.validatePath(path)) {
          fileSystem.setCurrDirWithPath(path);
          if (fileSystem.getCurrDir().getPath().equals(path))
            listing(fileSystem, ret, Recursion,paths);
          else
            ret.append(path + ": " + ERRORMSG+"\n");
        } else
          ret.append(path + ": " + ERRORMSG+"\n");
        fileSystem.setCurrDir(curDirectory);
      }
    }
    fileSystem.setCurrDir(curDirectory);
    if(ret.toString().trim().equals("")) return null;
    return ret.toString().trim();
  }

  /**
   * function for listing directories and files
   * 
   * @param fileSystem State of the file system
   * @param ret Formatted string
   * @param recursion True if recursion is needed, otherwise it is not
   * @param paths List of paths
   */
  private void listing(FileSystem fileSystem, StringBuffer ret, boolean recursion, ArrayList<String> paths) {
    List<Content> contentTempDir = fileSystem.getCurrDir().getContents();
    if(!recursion && paths.size()==0) {
      for(Content content : contentTempDir) {
        ret.append(content.getName()+"\n");
      }
      return;
    }
    ret.append(fileSystem.getCurrDir().getPath()+": ");
    for(Content content : contentTempDir) {
      ret.append(content.getName()+" ");
    }
    ret.append("\n");
    if(recursion) {
      for(Content content : contentTempDir) {
        if (content instanceof File)
          continue;
        Directory tempCurrDirectory = fileSystem.getCurrDir();
        fileSystem.setCurrDirWithPath(content.getPath());
        listing(fileSystem, ret, recursion, paths);
        fileSystem.setCurrDir(tempCurrDirectory);
        
      }
    }
  }

  /**
   * Checks if recursion is required
   * 
   * @param fileSystem State of the file system
   * @param paths List of paths
   * @param arguments List of given arguments
   */
  private boolean isRecursive(FileSystem fileSystem, ArrayList<String> paths, String[] arguments) {
    int len = arguments.length;
    if (len == 0)
      return false;
    boolean result = false;
    int redirection = 0;
    if (len >= 2 && (arguments[len - 2].equals(">") || arguments[len - 2].equals(">>")))
      redirection = 2;
    int i = 0;
    if (arguments[0].equals("-R")) {
      result = true;
      i = 1;
    }
    for (; i < len - redirection; i++) {
      if (arguments[i].startsWith("/"))
        paths.add(arguments[i]);
      else {
        paths.add(fileSystem.getCurrDirPath() + arguments[i]);
      }
    }

    return result;
  }
}