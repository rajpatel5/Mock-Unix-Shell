package system;

import java.util.Arrays;

/**
 * Provides a way to access different directories and files. Also keeps track of the current working
 * directory and helps with validation of path.
 */
public class FileSystem {
  
  private static FileSystem thisSystem = null;
  
  /** Current working directory */
  private Directory currDir;

  /** Current working directory path */
  private String currDirPath;

  /** Root directory */
  private Directory root;

  /** Directory separator */
  public final static String SLASH = "/";
  
  /** Error message for an invalid path */
  private final String PATHERRORMSG = "Error: Invalid path provided";
  
  /** Error message for a non-existent content */
  private final String NOCONTENTMSG = "Error: Content does not exist";
  
  /** Error message for a non-existent directory */
  private final String NODIRECTORYMSG = "Error: No directory found";
  
  /**
   * Default Constructor
   */
  public FileSystem() {
    root = new Directory(SLASH, SLASH);
    currDir = root;
    currDirPath = SLASH;
  }

  /**
   * Creates the root of the FileSystem
   * 
   * @return The root of the FileSystem
   */
  public static FileSystem createRoot() {
    if(thisSystem == null){
      thisSystem = new FileSystem();
    }
    return thisSystem;
  }

  /**
   * Creates a new directory
   * 
   * @param name Name of the new directory
   */
  public String createDirectory(String name) {
    if (name.equals("/"))
    {
      return "";
    }
    String path = Validator.formatPath(name);
    String dirName = path.substring(path.lastIndexOf(SLASH) + 1, path.length());
    String truePath = path.substring(0, path.lastIndexOf(SLASH));
    boolean containsSlash = name.contains("/");
    boolean containsCurrDir = name.contains(".");
    boolean containsParentDir = name.contains("..");

    if (containsSlash == false && containsCurrDir == false && containsParentDir == false) {
      currDir.addContent(new Directory(name, path));
    } else {
      try {
        (getDir(truePath)).addContent(new Directory(dirName, path));
      } catch (NullPointerException e) {
        IO.Output(PATHERRORMSG);
      }
    }
    return "";
    /*
    String path = Validator.formatPath(name);
    //System.out.println(path);
    String cumulativePath = "/";
    String[] subPaths = Arrays.copyOfRange(path.split("/"), 1, path.split("/").length);
    Directory startDir = root;
    boolean flag;
    for (String paths : subPaths)
    {
      flag = false;
      System.out.println("This: " + paths);
      for (Content content : startDir.getContents())
      {
        if (content.getName().equals(paths))
        {
          flag = true;
          startDir = ((Directory) content);
          break;
        }
      }
      cumulativePath += paths;
      //System.out.println("CPATH: " + cumulativePath);
      if (!flag)
      {
        startDir.addContent(new Directory(paths, cumulativePath));
      }
      cumulativePath += "/";
    }
    return "";*/
  }

  /**
   * Sets the current directory given a path
   * 
   * @param path Path to the current directory
   */
  public void setCurrDirWithPath(String path) {
    String[] pathSplit = path.split(SLASH);
    Directory temp = root;

    if (pathSplit.length < 2) {
      setCurrDir(root);
    } else {
      for (String dirName : pathSplit) {
        if (!dirName.isEmpty()) {
          try {
            temp = (Directory) temp.getContent(dirName);
          } catch (ClassCastException e) {
          }
          setCurrDir((Directory) temp);
        }
      }
    }
  }

  /**
   * Checks if the path content exists within the file system
   * 
   * @param path Full path to be validated
   * @return true if path exists, otherwise false
   */
  public boolean validatePath(String path) {
    if (!(path.substring(0, 1).equals(SLASH))) {
      return false;
    }
    // If the path is the root, return true
    if (path.equals(SLASH)) {
      return true;
    }

    // Get the root folder
    Directory root = getRoot();
    Content content = null;
    String[] paths = path.split(SLASH);
    int i = 1;

    // Keep looking for the next directory, and check if it is a directory
    while (root instanceof Directory && i < paths.length - 1) {
      root = (Directory) root.getContent(paths[i]);
      i++;
    }
    // Check if an content is found
    if (paths.length != 1) {
      try {
        content = root.getContent(paths[i]);
      } catch (NullPointerException e) {}
    }

    return content != null;
  }

  /**
   * Return a content object at the given path
   * 
   * @param path The full path of the content wanted
   * @return Content at the given path
   * @throws Exception
   */
  public Content getContentFromPath(String path) throws Exception {
    String parentPath = path.substring(0, path.lastIndexOf(SLASH));
    String contentName = path.substring(path.lastIndexOf(SLASH) + 1);
    Directory parent = getDir(parentPath);
    Content wanted;

    // If the parent is at root and there is nothing else to search
    if (parent.equals(getRoot()) && contentName.equals("")) {
      wanted = parent;
    } else {
      // Otherwise get the content in the parent folder
      wanted = parent.getContent(contentName);
    }
    // If nothing is found, throw an invalid path exception
    if (wanted == null) {
      IO.Output(NOCONTENTMSG);
      throw new Exception();
    }
    return wanted;
  }
  
  /**
   * Return the parent directory of a given content.
   * 
   * @param content The child(file/directory) of the wanted parent
   * @return wanted The parent directory
   */
  public Directory getParentDir(Content content){
    String name = content.getName();
    String path = content.getPath().substring(0, content.getPath().indexOf(name) - 1);
    Directory wanted;
    
    // If the path is not empty, get the folder at path
    if (!path.equals("")) {
      wanted = getDir(path);
    }
    // Otherwise return the root
    else {
      wanted = getRoot();
    }

    return wanted;
  }

  /**
   * Return the Directory object at the given path,
   * 
   * @param path The full path of the desired Directory
   * @return directory Directory at the give path
   */
  public Directory getDir(String path) {
    Directory directory = getRoot();
    if (path.equals(SLASH)) return directory;
    // If it is a full path
    if (!path.equals("")) {
      String[] directories = path.substring(1).split(SLASH);
      directory = getSubDir(root, directories, 0);
    }
    // Otherwise if it is a relative path
    else if (path.length() > currDirPath.length() && path
        .equals(path.substring(0, currDirPath.length()))) {
      int pathModifier = 1;
      if (currDirPath.equals(SLASH)) {
        pathModifier = 0;
      }
      // get the path after the current path
      String subPath = path.substring(currDirPath.length() + pathModifier);
      String[] subPathDirs = subPath.split(SLASH);

      if (subPathDirs.length == 1) {
        directory = (Directory) currDir.getContent(subPath);
      }
      // If it moves into directories
      else {
        directory = getSubDir(currDir, subPathDirs, 0);
      }
    }
    return directory;
  }

  /**
   * Return the directory with a given path starting at current directory.
   * 
   * @param currDir The current working Directory
   * @param path The path to change to
   * @param pathModifier 1 if current directory is at root, 0 other wise
   * @return destination A directory if found otherwise the current directory
   */
  private Directory getSubDir(Directory currDir, String[] path, int pathModifier) {
    Directory destination = currDir;
    for (int i = 0; i < path.length - pathModifier; i++) {
      try {
        destination = (Directory) destination.getContent(path[i]);
      }
      // If the content is not a Directory object
      catch (ClassCastException e) {}

      // If there is no directory object found
      if (destination == null) {
        IO.Output(NODIRECTORYMSG);
      }
    }
    return destination;
  }

  /**
   * Create a file at the current directory with the name of file_name
   * 
   * @param path The full path of where the file will be created
   */
  public void makeFileAtPath(String path) {
    String name = path.substring(path.lastIndexOf("/") + 1);
    if (Validator.containsInvalidChars(name)) {
      IO.Output("Invalid file name");
      return;
    }
    // Get the parent directory and path
    String parentPath = path.substring(0, path.lastIndexOf(SLASH));
    Directory parent = getDir(parentPath);
    // Create the new file with empty data
    File file = new File(name, path, "");
    parent.addContent(file);
  }
  
  /**
   * Re-build the fileSystem, used for unit testing 
   */
  public void rebuild(){
    root = new Directory(SLASH, SLASH);
    setCurrDir(root);
    setCurrPath(root.getPath());
  }

  /**
   * Set current working path to the given path
   * 
   * @param path The full path to the current working directory
   */
  public void setCurrPath(String path) {
    currDirPath = path;
  }

  /**
   * Return the current working path
   * 
   * @return currPath The full path to the current working directory
   */
  public String getCurrDirPath() {
    return currDirPath;
  }

  /**
   * Set current working directory to the given directory
   * 
   * @param dir The current working directory
   */
  public void setCurrDir(Directory dir) {
    currDir = dir;
  }

  /**
   * Return the current working directory object
   * 
   * @return currDir The current working directory
   */
  public Directory getCurrDir() {
    return currDir;
  }

  /**
   * Return the root of the FileSystem
   * 
   * @return root The root of the FileSystem
   */
  public Directory getRoot() {
    return root;
  }
}
