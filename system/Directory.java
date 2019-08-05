package system;

import java.util.ArrayList;

/**
 * A directory content which contains a list of other contents.
 */
public class Directory extends Content {

  /** The list of contents in this directory */
  private ArrayList<Content> contents;
  
  private final String INVALIDCHARERRORMSG = "Name contains invalid characters";

  /**
   * Default Constructor.
   * 
   * @param name The name of the directory
   * @param path The full path to this directory
   */
  public Directory(String name, String path) {
    super(name, path);
    contents = new ArrayList<Content>();
  }

  /**
   * @return contents Contents of the directory.
   */
  public ArrayList<Content> getContents() {
    return contents;
  }

  /**
   * @return returnContents A String of the contents in this directory
   */
  public String toString() {
    String returnContents = this.getName() + ": ";
    for (Content content : this.contents) {
      returnContents += " " + content.getName();
    }
    return returnContents;
  }

  /**
   * Return a specific content(Directory or File) in this directory
   * 
   * @param contentName The name of the content to return
   * @return wanted A content(Directory or File) with the given name
   */
  public Content getContent(String contentName) {
    Content wanted = null;
    
    // Finding the content
    for (Content nextContent : contents) {
      // Return it when it's found
      if (nextContent.getName().equals(contentName)) {
        return nextContent;
      }
    }
    
    return wanted;
  }

  /**
   * Add a new content to this directory, new content will not be added if there is another content
   * with the same name.
   * 
   * @param newContent The new content to be added
   */
  public void addContent(Content newContent) {
    // Check if the list already has a content with the same name
    boolean hasSameName = false;
    
    // Validate the content name
    if (Validator.containsInvalidChars(newContent.getName())) {
      IO.Output(INVALIDCHARERRORMSG);
      return;
    }

    for (Content next : contents) {
      hasSameName = (next.getName()).equals(newContent.getName());
      if (hasSameName)
        break;
    }

    if (!hasSameName) {
      // Add the new content to the list
      contents.add(newContent);
    }
  }

  /**
   * Remove a content in this directory
   * 
   * @param contentName The name of the content to be removed
   */
  public void removeContent(String contentName) {
    // Finding the content
    for (Content nextContent : contents) {

      // Remove it from the list when found
      if ((nextContent.getName()).equals(contentName)) {
        contents.remove(nextContent);
        break;
      }
    }
  }
}
