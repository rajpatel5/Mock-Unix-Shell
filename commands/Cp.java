package commands;

import system.Content;
import system.Directory;
import system.File;
import system.FileSystem;
import system.InputHistory;
import system.Validator;

/**
 * A command that copies a file into another directory
 */
public class Cp implements CommandExecutor {

  /** Error message for unable to copy file */
  private final static String FILEERRORMSG = "Error: Cannot copy to a file";

  /** Error message for invalid path */
  private final static String INVALIDPATHERRORMSG = "Error: Invalid path provided";
  
  /**
   * Check the first case of Cp where the second path is not a setter for the new file content 
   * to be copied.
   * 
   * @param contentPathOne contents of the first path provided.
   * @param contentPathTwo contents of the second path provided.
   * @param pathTwoTrue true full path of second parameter.
   */
  private void checkCpFirstTrue(Content contentPathOne, Content contentPathTwo, String pathTwoTrue)
  {
    if (contentPathTwo instanceof Directory) 
    {
      if (contentPathOne instanceof File) {
        ((Directory)contentPathTwo).addContent(new File(contentPathOne.getName(),
            pathTwoTrue + contentPathOne.getName(),
            ((File) contentPathOne).readData()));
      }
      else {
        for (Content content : ((Directory) contentPathTwo).getContents()) {
          if (content.getName().equals(contentPathOne.getName()))
          {
            ((Directory) contentPathTwo).removeContent(content.getName());
            break;
          }
        }
        recursiveCopy((Directory) contentPathOne, (Directory)contentPathTwo);
      }
    }
    else
    {
      if (contentPathOne instanceof File)
      {
        ((File) contentPathTwo).writeData(((File) contentPathOne).readData());
      }
    }
  }
  
  /**
   * Check the second case of Cp where the second path is a setter for the new file content 
   * to be copied.
   * 
   * @param contentPathOne contents of the first path provided.
   * @param contentPathTwo contents of the second path provided.
   * @param pathTwoTrue true full path of second parameter.
   */
  private void checkCpSecondTrue(Content contentPathOne, Content contentPathTwo, String pathTwoTrue)
  {
    String name = pathTwoTrue.substring(pathTwoTrue.lastIndexOf('/') + 1, pathTwoTrue.length());
    if (contentPathTwo instanceof Directory) {        
      if (contentPathOne instanceof File) {
        ((Directory)contentPathTwo).addContent(new File(name, pathTwoTrue,
            ((File) contentPathOne).readData()));
      }
      else {
        Directory newDir = new Directory(name, pathTwoTrue);
        ((Directory) contentPathTwo).addContent(newDir);
        recursiveCopy((Directory) contentPathOne, newDir);
      }            
    }
  }

  /**
   * Executes cp command
   */
  public String execute(FileSystem fileSystem, String[] arguments, InputHistory inputHistory) {
    if (arguments.length != 2)
    {
      return INVALIDPATHERRORMSG;
    }
    boolean pathOne = fileSystem.validatePath(Validator.formatPath(arguments[0]));
    boolean pathTwo = fileSystem.validatePath(Validator.formatPath(arguments[1])); 
    if (pathOne){
      try {
        String pathTwoTrue = Validator.formatPath(arguments[1]);
        String pathTwoExist = pathTwoTrue.substring(0, pathTwoTrue.lastIndexOf('/'));
        Content contentPathOne = fileSystem.getContentFromPath(Validator.formatPath(arguments[0]));
        if (pathTwo)
        {
          Content contentPathTwo = fileSystem.getContentFromPath(Validator.formatPath(arguments[1]));
          checkCpFirstTrue(contentPathOne, contentPathTwo, pathTwoTrue);
          
        }
        else if (fileSystem.validatePath(pathTwoExist))
        {
          Content contentPathTwo = fileSystem.getContentFromPath(pathTwoExist);
          checkCpSecondTrue(contentPathOne, contentPathTwo, pathTwoTrue);
        }
        else {
          return INVALIDPATHERRORMSG;
        }
      } catch (Exception e) {
        return FILEERRORMSG;
      }
    }
    return INVALIDPATHERRORMSG;
  }

  /**
   * Implements copy by recursively copying the contents
   * 
   * @param copyNode Directory which is to be copied
   * @param copyToNode Directory location where copy should be sent
   */
  private void recursiveCopy(Directory copyNode, Directory copyToNode)
  {
    // This assumes that you do not already have a directory with the same name in the 
    // copyToNode directory.
    Directory newDirectory = new Directory(copyNode.getName(), copyToNode.getPath() + copyNode.getName());
    copyToNode.addContent(newDirectory);
    for (Content content : copyNode.getContents()) {
      if (content instanceof File) {
        newDirectory.addContent(new File(content.getName(), 
            newDirectory.getPath() + content.getName(),
            ((File) content).readData()));
      }
      else {
        recursiveCopy((Directory) content, newDirectory);
      }
    }
  }
}
