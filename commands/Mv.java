package commands;

import system.Content;
import system.Directory;
import system.FileSystem;
import system.InputHistory;
import system.Validator;

/**
 * A command that either moves a file into another directory or renames it
 */
public class Mv implements CommandExecutor{
  
  /** Error message for a moving a parent directory into it's child */
  final String ERRORMSG = "Error: Cannot move parent directory into sub directory";
  
  /** Error message for a non-existent directory */
  final String NOTFOUNDERRORMSG = "Error: Directory not found";
  
  /**
   * Executes mv command
   */
  public String execute(FileSystem fileSystem, String[] arguments, InputHistory inputHistory) {
    Content target = null;
    Directory newLocation = null;
    if (arguments.length > 0) {
      // Check if the first path is a parent
      int pathIndex = arguments[1].indexOf(arguments[0]);
      // Check if its moving parent into parent's sub directory
      if (pathIndex == 0 && arguments[1].equals(FileSystem.SLASH)) {
        return ERRORMSG;
      }
      // Get the Directory at the second path
      try {
        target = fileSystem.getContentFromPath(Validator.formatPath(arguments[0]));
        newLocation = fileSystem.getDir(Validator.formatPath(arguments[1]));
      } catch (Exception e) {
        return NOTFOUNDERRORMSG;
      }
      if (newLocation != null) {
        // Copy the content
        copyContent(fileSystem, target, newLocation);
        // Remove the old content from the old directory
        fileSystem.getParentDir(target).removeContent(target.getName());
      } else {
        // Rename the file
        target.setPath(arguments[1]);
        target.setName(arguments[1].substring(arguments[1].lastIndexOf("/") + 1));
      }
    }
    return null;
  }
  
  /**
   * Create a copy of the content and move the copy to the new location
   * 
   * @param target Content being copied
   * @param newLocation The destination of the copied content
   */
  protected void copyContent(FileSystem fileSystem, Content target, Directory newLocation) {
    if (target != null && newLocation != null) {
      // make a copy of the content
      Content copyTarget = target.clone();
      // Set the path for the copy
      copyTarget.setPath(newLocation.getPath() + FileSystem.SLASH + copyTarget.getName());
//      // Sets the path of the contents of copyTarget
//      for (Content next: ((Directory) copyTarget).getContents()) {
//        copyTarget.setPath(fileSystem.getParentDir(next) + FileSystem.SLASH + next.getName());
//      }
      // Add the copy to new location
      newLocation.addContent(copyTarget);
    }
  }
}
