package system;

/**
 * A content(Directory/File) in the file system.
 */
public class Content {

  /** Name of the content */
  private String name;

  /** Full path of this content */
  protected String path;

  /**
   * Default Constructor.
   * 
   * @param name The name of the content
   * @param path The location of the content in the FileSystem
   */
  public Content(String name, String path) {
    this.name = name;
    this.path = path;
  }

  /**
   * Set the name of this content
   * 
   * @param newName
   */
  public void setName(String newName) {
    this.name = newName;
  }

  /**
   * Return the name of this content
   * 
   * @return name of content
   */
  public String getName() {
    return name;
  }

  /**
   * Set the path of this object
   * 
   * @param newPath for this content
   */
  public void setPath(String newPath) {
    this.path = newPath;
  }

  /**
   * Return the path of this object
   * 
   * @return The path of this content
   */
  public String getPath() {
    return path;
  }
  
  /**
   * Return the exact copy this object
   * 
   * @return The copy of this content
   */
  public Content clone(){
    return new Content(this.getName(), this.getPath());
  }
}
