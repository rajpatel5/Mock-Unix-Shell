package commands;

import java.util.Hashtable;
import system.File;
import system.FileSystem;
import system.IO;
import system.InputHistory;
import system.Validator;

/**
 * Commands that the user can execute.
 */
public class CommandMaster {

  public final static String APPENDSYMBOL = ">>";
  public final static String OVERWRITESYMBOL = ">";
  private final static String PARAMERRORMSG = "Error: Invalid number of parameters";

  /**
   * Array of Strings which are the input arguments
   */
  protected static String[] arguments;

  /** Stores the command classes */
  private static Hashtable<String, String> commandHashTable = new Hashtable<String, String>();

  /** Error message for a non-existent command */
  private final static String ERRORMSG = "Error: Command does not exist";

  /** Instance of FileSystem */
  private FileSystem fileSystem;

  /**
   * Default empty constructor
   */
  public CommandMaster(FileSystem fileSystem) {
    this.fileSystem = fileSystem;
  }

  /**
   * Execute the commands
   */
  public void executeCommand(String command, InputHistory inputHistory) {
    // Stores all the commands and their classes as String
    initializeCommandHashtable(commandHashTable);

    try {
      String commandClassName = commandHashTable.get(command);

      if (commandClassName != null) {
        Class<?> commandClass = Class.forName(commandClassName);
        try {
          // Executes the commands and formats their outputs
          CommandExecutor commandExecutorInstance = (CommandExecutor) commandClass.newInstance();
          String output = commandExecutorInstance.execute(fileSystem, arguments, inputHistory);
          String redirectIndicator = "NONE";
          formatOutput(fileSystem, arguments, output, redirectIndicator);
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        } catch (Exception e) {
        }
      }
    } catch (ClassNotFoundException e) {
      IO.Output(ERRORMSG);
    }
  }

  /**
   * Initializes the commandHastable with the class names for every command
   * 
   * @param commandHashTable The HashTable that stores the manuals for every command
   */
  private void initializeCommandHashtable(Hashtable<String, String> commandHashTable) {
    commandHashTable.put("cat", "commands.Cat");
    commandHashTable.put("cd", "commands.Cd");
    commandHashTable.put("cp", "commands.Cp");
    commandHashTable.put("echo", "commands.Echo");
    commandHashTable.put("find", "commands.Find");
    commandHashTable.put("get", "commands.Get");
    commandHashTable.put("history", "commands.History");
    commandHashTable.put("load", "commands.Load");
    commandHashTable.put("ls", "commands.Ls");
    commandHashTable.put("man", "commands.Man");
    commandHashTable.put("mkdir", "commands.Mkdir");
    commandHashTable.put("mv", "commands.Mv");
    commandHashTable.put("popd", "commands.Popd");
    commandHashTable.put("pushd", "commands.Pushd");
    commandHashTable.put("pwd", "commands.Pwd");
    commandHashTable.put("save", "commands.Save");
    commandHashTable.put("tree", "commands.Tree");
  }

  /**
   * Formats the output to the console
   * 
   * @param fileSystem The state of the fileSystem
   * @param arguments Array of arguments for the commands
   * @param output The String to be displayed
   * @param redirectIndicator Indicates redirection or not
   */
  private void formatOutput(FileSystem fileSystem, String[] arguments, String output,
      String redirectIndicator) {
    if (arguments.length >= 2) {
      redirectIndicator = arguments[arguments.length - 2];
    }

    if (output.contains("Error") && !Load.enabled) {
      IO.Output(output);
    } else if (!Load.enabled
        && !(redirectIndicator.equals(APPENDSYMBOL) || redirectIndicator.equals(OVERWRITESYMBOL))) {
      IO.Output(output);
    }

    if (redirectIndicator.equals(APPENDSYMBOL)
        || redirectIndicator.equals(OVERWRITESYMBOL) && !output.contains("Error")) {
      // Check if the file given in path exists
      boolean validPath = Validator.pathCheck(arguments[arguments.length - 1]);
      String trueFilePath = Validator.formatPath(arguments[arguments.length - 1]);

      // If file does not exist then make a new file given the full path and name
      if (!validPath && output != null) {
        fileSystem.makeFileAtPath(trueFilePath);
      }

      insertData(fileSystem, output, trueFilePath, redirectIndicator);
    }
  }

  /**
   * Write the output to a file
   * 
   * @param text The data to be written
   * @param path The location of the file
   * @param symbol Append or overwrite option
   */
  private void insertData(FileSystem fileSystem, String inputData, String path, String symbol) {
    // Get the file from the FileSystem
    File file = null;
    try {
      file = (File) fileSystem.getContentFromPath(path);
    } catch (Exception e) {}

    // If the symbol matches with '>>' append contents in text to the file
    if (symbol.equals(APPENDSYMBOL)) {
      try {
        file.writeData(file.readData() + "\n" + inputData);
      } catch (NullPointerException e) {
        IO.Output(PARAMERRORMSG);
      }
    }
    // If the symbol is '>' overwrite its contents instead
    else if (symbol.equals(OVERWRITESYMBOL)) {
      try {
        file.writeData(inputData);
      } catch (NullPointerException e) {
        IO.Output(PARAMERRORMSG);
      }
    }
  }

  /**
   * Set the arguments to be executed
   * 
   * @param param An array of arguments
   */
  public void setArguments(String[] param) {
    arguments = param;
  }

  /**
   * get the arguments to be executed
   * 
   * @return the array of arguments
   */
  public String[] getArguments() {
    return arguments;
  }
}
