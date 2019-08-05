// You are now asked to implement the tree command. The the tree command takes in no input
// parameter.
//  When the user types in the tree you must starting, from the root directory (`n') display the
// entire le
// system as a tree. For every level of the tree, you must indent by a tab character.
// { For instance if the root directory contains two subdirectories as `A' and `B,' then you will
// display the
// following:
// \
// A
// B
// { For instance if the root directory contains two sub directories as `A', `B', `C' and `A' in
// turn contains
// `A1' and `A2', then you will display the following:
// \
// A
// A1
// A2
// B
// C
// { When the user types in tree and the only directory present is the root directory, then you
// simply
// show the root directory.
// \

package commands;

import java.util.List;
import system.Content;
import system.Directory;
import system.File;
import system.FileSystem;
import system.InputHistory;



/**
 * A command that displays the entire file system as a tree diagram
 */
public class Tree implements CommandExecutor {

  /** Error message for */
  final String ERRORMSG = "Error: ";


  /**
   * Executes tree command
   */
  public String execute(FileSystem fileSystem, String[] arguments, InputHistory inputHistory) {
    // ret is the return string. methods keep appending to ret
    StringBuffer ret = new StringBuffer("");

    // storing current directory as we will need to change directories
    Directory curDirectory = fileSystem.getCurrDir();

    // setting root as current directory as tree always prints from the root directory
    fileSystem.setCurrDir(fileSystem.getRoot());

    // 1 is the depth number. root has depth of 1
    printPreorder(fileSystem, ret, 1);

    // setting original current Directory as current directory after printing the entire fileSystem
    fileSystem.setCurrDir(curDirectory);

    return "\t" + (ret.toString().trim());
  }

  /**
   * printPreOrder function appends the content of fileSystem recursively to the StringBuffer ret.
   * 
   * @param fileSystem is the instance of the FileSystem
   * @param ret is the StringBuffer to which the name of contents will be appended
   * @param depth is the depth of the current directory. root has depth of 1.
   */
  private void printPreorder(FileSystem fileSystem, StringBuffer ret, int depth) {
    // append the name of the content in new line with appropriate number of tabs
    ret.append("\n");
    for (int i = 0; i < depth; i++)
      ret.append("\t");
    ret.append(fileSystem.getCurrDir().getName());
    // get the contents of the currentDirectory
    List<Content> contentTempDir = fileSystem.getCurrDir().getContents();
    // if the directory has content in it
    if (contentTempDir.size() != 0) {
      for (Content content : contentTempDir) {
        Directory tempCurrDirectory = fileSystem.getCurrDir();
        if (content instanceof File) {
          ret.append("\n");
          for (int i = 0; i < depth + 1; i++)
            ret.append("\t");
          ret.append(content.getName());
          continue; // continue to the next loop
        }
        // if the content is a directory
        fileSystem.setCurrDirWithPath(content.getPath());
        // recursive call with new current directory
        printPreorder(fileSystem, ret, depth + 1);
        fileSystem.setCurrDir(tempCurrDirectory);
      }
    }
  }

}
