package system;

import java.util.ArrayList;

/**
 * Stores the commands entered by the user
 */
public class InputHistory {

  /**
   * An ArrayList to store all the commands
   */
  private ArrayList<String> inputLog;
  private Integer lenInputLog = 0;

  /**
   * Default Constructor
   */
  public InputHistory() {
    inputLog = new ArrayList<String>();
  }

  /**
   * Adds an input to inputLog
   * 
   * @param input The command entered by the user
   */
  public void setInputLog(String[] input) {
    String formatedString = "";
    for (String eachWord : input) {
      formatedString += eachWord + " ";
    }
    lenInputLog++;
    inputLog.add(Integer.toString(lenInputLog) + ". " + formatedString);
  }

  /**
   * Returns the inputLog
   * 
   * @return inputLog A list of the commands entered by the user
   */
  public ArrayList<String> getInputLog() {
    return inputLog;
  }
}
