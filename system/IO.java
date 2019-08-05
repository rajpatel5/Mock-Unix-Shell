package system;

import java.util.Scanner;

/**
 * Handles input and output of the Shell
 */
public class IO {

  /** Scanner for getting input from user and the user's input command */
  private static Scanner userInput = new Scanner(System.in);
  static String input;
  private final static String EMPTYSTRING = "";

  /**
   * Receive input from user
   * 
   * @return input Command entered by the user
   */
  public static String getInput() {
    input = userInput.nextLine();

    return input;
  }

  /**
   * Displays output from the program
   * 
   * @param output Output from the program
   */
  public static void Output(Object output) {
    if (!output.equals(null) || !output.equals(EMPTYSTRING)) {
      System.out.println(output);
    }
  }
}
