package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import system.File;
import system.FileSystem;
import system.InputHistory;

/**
 * A command that retrieves a file from a given URL
 */
public class Get implements CommandExecutor{
  /** a URL Object for accessing a file from the Internet */
  private URL url;

  /** a String to hold the contents of a file read from the Internet */
  private String fileContent;

  /** a String to hold the name of a file read from the Internet */
  private String fileName;

  /** BufferedReader for reading characters from an online file efficiently */
  private BufferedReader input;
  
  /** Error message for unable to write to file */
  private final static String ERRORMSG = "Error: Cannot write to the file";
  
  /** 
   * Executes get command 
   */
  public String execute(FileSystem fileSystem, String[] arguments, InputHistory inputHistory) {
    fileContent = "";
    fileName = "";
    
    // Split in two methods. Retrieving URL, and local fileSystem work.
    getFileFromURL(arguments);

    // If getFileFromURL() completed without throwing any Exceptions, then
    // it should be safe to write the data to a File.
    return writeToFile(fileSystem);
  }
  
  /**
   * Connects to the URL and reads from the file, then stores the file data.
   * Returns true if the file was successfully retrieved, otherwise false
   */
  private void getFileFromURL(String[] arguments){
    try {
      url = new URL(arguments[0]);
      /*
       * Establishes connection to the Internet, and reads a byte stream and decodes them 
       * to a char stream, then  reads characters from an input stream, and buffers
       * stream for efficiency
       */
      input = new BufferedReader(new InputStreamReader(url.openStream()));
      // Get the name of the file (keep only characters to the right of the
      // right-most slash (not including the slash))
      fileName = url.getFile();
      fileName = fileName.substring(fileName.lastIndexOf(FileSystem.SLASH) + 1);
      // Holds the current line.
      String curLine;
      // Keep reading lines and appending to local String until End Of File
      while ((curLine = input.readLine()) != null) {
        // Add a newline character at the end of every line read.
        fileContent += curLine + "\n";
      }
      // Close the stream
      input.close();
      // Remove the extra newline character at the end of the file
      if (fileContent.length() > 0) {
        fileContent = fileContent.substring(0, fileContent.length() - 1);
      }
    } catch (MalformedURLException e) {} 
      catch (IOException e) {}
  }

  /**
   * Writes a new File to the FileSystem, using data retrieved from the URL
   */
  private String writeToFile(FileSystem fileSystem) {
    // Create the filePath, based on current location in the fileSystem
    String newFilePath;
    boolean fileExists;
    File currFile;
    // Formats path
    if (fileSystem.getCurrDirPath() != FileSystem.SLASH) {
      newFilePath = fileSystem.getCurrDirPath() + FileSystem.SLASH + fileName;
    } else {
      newFilePath = fileSystem.getCurrDirPath() + fileName;
    }
    // Check if there is a file already exists, otherwise create it
    fileExists = fileSystem.validatePath(newFilePath);
    if (!fileExists) {
      fileSystem.makeFileAtPath(newFilePath);
    }
    // Write the data to the file, otherwise overwrites data if the file already exists
    try {
      currFile = (File)fileSystem.getContentFromPath(newFilePath);
      currFile.writeData(fileContent);
    } catch (Exception e) {
      return ERRORMSG;
    }
    return null;
  }
}
