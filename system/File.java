package system;

/**
 * A file content which contains text(String)
 */
public class File extends Content {

  /** Data in this file */
  private String fileData;

  /**
   * Default constructor
   * 
   * @param name The name of this File
   * @param path The full path to this File
   * @param fileFata The data of this file
   */
  public File(String name, String path, String fileData) {
    super(name, path);
    this.fileData = fileData;
  }
  
  /**
   * Returns the data stored in this file
   * 
   * @return The data of this file
   */
  public String toString() {
    return "Contents of " + this.getName() + ":\n" + this.readData().trim();
  }

  /**
   * Write the file's data with the newData
   * 
   * @param newData The new data for this file
   */
  public void writeData(String newData) {
    fileData = newData;
  }

  /**
   * Return the stored data in this file
   * 
   * @return fileData The stored data in this file
   */
  public String readData() {
    return fileData;
  }
}
