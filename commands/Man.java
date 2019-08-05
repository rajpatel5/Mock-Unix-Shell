package commands;

import java.util.Hashtable;
import system.FileSystem;
import system.InputHistory;

/**
 * A command that displays the manual of every other command.
 */
public class Man implements CommandExecutor {
  public final String OUTFILE_OPTIOPN = "[> OUTFILE , >> OUTFILE]";
  public final String OUTFILE = "\nIf OUTFILE is not provided, then output displayed on shell.\r\n"
      + "If OUTFILE is provided and if there is an output to display, then redirect the output to\n"
      + "the OUTFILE instead of displaying it on the screen.\r\n"
      + "\t> OUTFILE means overwrite if OUTFILE present otherwise create new OUTFILE\r\n"
      + "\t>> OUTFILE means append if OUTFILE present otherwise create new OUTFILE\r\n\n";

  /** Manual for cat command */
  public final String MANCAT = "cat FILE1 [FILE2 …] " + OUTFILE_OPTIOPN + "\n\n"
      + "cat outputs the contents of FILE1 and other files if specified (i.e. File2 ….) "
      + "concatenated in the shell.\n" + "Each file is seperated by 3 line breaks.\n" + OUTFILE
      + "EXAMPLE:\n\t" + "File1 is a text file" + " containing 'HELLO WORLD' and\n\t"
      + "File2 is a text file containing 'MY name is XYZ.'\n\n\t"
      + "Command: cd File1 File2\n\n\tOutput:\n\t\t" + "HELLO WORLD\n\n\n\n\t\tMy name is XYZ.";

  /** Manual for cd command */
  public final String MANCD = "cd DIR\n\n"
      + "Changes the current directory to DIR, which may be relative to the current directory or "
      + "may be a full path.\n" + ".. means a parent directory\n"
      + ". means the current directory\n"
      + "Root of the file system is a single forward slash ‘/’.\n\n" + "EXAMPLE:\n\t"
      + "1) cd hello        ->  Changes the current working directory to currentWorkingDir/hello"
      + "\r\n"
      + "\t2)  cd ..       ->  Changes the current working directory to parent directory\r\n"
      + "\t3)  cd ./hello      ->  Changes the current working directory to currentWorkingDir/hello"
      + "\r\n" + "\t4)  cd /            ->  Changes the current working directory to root\r\n"
      + "\t5)  cd /abc     ->  (Full  path) Changes current working directory to root/abc\r\n" + "";

  /** Manual for echo command */
  public final String MANECHO = "echo STRING " + OUTFILE_OPTIOPN + "\r\n" + "\r\n"
      + "Displays the STRING on the shell if OUTFILE is not provided.\r\n"
      + "[> OUTFILE] if provided then a new file OUTFILE is created with STRING in it if OUTFILE "
      + "doesn’t exist and erases the old contents if OUTFILE already exists.\r\n"
      + "[>> OUTFILE]] if provided then a new file OUTFILE is created with STRING in it if OUTFILE"
      + " doesn’t exist and appends the STRING to old contents if OUTFILE already exists.\r\n"
      + "\r\n" + "EXAMPLE:\r\n" + "\t1)  echo hello      ->  output ‘hello’ to the console\r\n"
      + "\t2)  echo hello > abc    ->  creates a new file named abc in current working directory"
      + " with content ‘hello’\n\t\t\t if file abc doesn’t exist. If the file abc already exists "
      + "in the current working directory then the old content of file is replaced with ‘hello’\r\n"
      + "\t3)  echo hello >> abc   ->  creates a new file named abc in current working directory "
      + "with content ‘hello’\n\t\t\t if file abc doesn’t exist. If the file abc already exists "
      + "in the current working directory then the ‘hello’ is appended to the old content of the "
      + "file abc.\r\n" + "";

  /** Manual for history command */
  public final String MANHISTORY = "history [number] " + OUTFILE_OPTIOPN + "\r\n" + "\r\n"
      + "Prints out the recent commands, one command per line, where the oldest command is "
      + "numbered one \nand printed on the very first line. If [number] is given and is >= 0"
      + " then only last [number] \ninput commands are printed.\r\n" + OUTFILE + "EXAMPLE:\r\n"
      + "    Lets say user has previously typed in ‘ls’, ‘sdfsf sdfs’,’mkdir hello’,’echo abc’\r\n"
      + "\t1)  history\r\n" + "\tOUTPUT: 1. ls\r\n" + "        \t2. sdfsf sdfd\r\n"
      + "        \t3. mkdir hello\r\n" + "        \t4. echo abc\r\n" + "        \t5. history\r\n"
      + "    Lets say user has previously typed in ‘ls’, ‘sdfsf sdfs’,’mkdir hello’,’echo abc’\r\n"
      + "\tb)  history 2\r\n" + "\tOUTPUT: 4. echo abc\r\n" + "        \t5. history 3\r\n" + "";

  /** Manual for ls command */
  public final String MANLS = "ls [-R] [PATH …] " + OUTFILE_OPTIOPN + "\r\n" + "\r\n"
      + "Outputs the contents (file or directory) of the current directory, with a new line "
      + "following each of the content (file of directory) if no paths are given.\r\n"
      + "Otherwise, for every path p, the order listed:\r\n"
      + "\t-   If p specifies a file, print p\r\n"
      + "\t-   If p specifies a directory, print p, a colon, then the contents of that directory,"
      + " then an extra new line.\r\n" + "\t-   If p does not exist, print a suitable message.\r\n"
      + "-R option will recursively list all subdirectories.\n" + OUTFILE;

  /** Manual for man command */
  public final String MANMAN = "man CMD " + OUTFILE_OPTIOPN + "\r\n" + "\r\n"
      + "Prints documentation for CMD. CMD stands for Command\r\n" + OUTFILE + "EXAMPLE:\r\n"
      + "    man cd  ->  displays manual for the command cd\r\n\n" + "AVAILABLE COMMANDS:\r\n"
      + "•   cat        •   cd      •   echo\n" + "•   history    •   ls      •   mv\n"
      + "•   cp         •   get     •   save\n" + "•   load       •   find    •   tree\n"
      + "•   mkdir      •   popd    •   pushd\n" + "•   pwd        •   exit\n";

  /** Manual for mkdir command */
  public final String MANMKDIR = "mkdir DIR …\r\n" + "\r\n"
      + "Creates directories. DIR can be just the name of new directory to create directory \n"
      + "in the working directory. DIR can be a FULLPATH/newDirectoryName \n"
      + "or RELATIVEPATH/newDirectoryName.\r\n" + "\r\n" + "EXAMPLE:\r\n"
      + "\t1)  mkdir hello     ->  creates directory hello in the current directory\r\n"
      + "\t2)  mkdir /root/hello/a ->  creates directory a in /root/hello\r\n"
      + "\t3)  mkdir hello/b   ->  creates directory b in currentDirectory/hello\r\n" + "";

  /** Manual for popd comma */
  public final String MANPOPD =
      "popd\n\nRemoves the top entry from the directory stack and makes it the current working "
          + "directory.\nThe removal is as per the LIFO behavior of a stack. \r\n" + "\r\n"
          + "EXAMPLE:\r\n"
          + "    Let’s assume the directory /A/hello is saved on the top of the directory stack\r\n"
          + "    INPUT:\r\n" + "        popd \r\n" + "    OUTPUT:\r\n"
          + "        The directory /A/hello is removed from the top of the directory stack and "
          + "/A/hello has \nbecome the working directory.\r\n" + "";

  /** Manual for pushd command */
  public final String MANPUSHD = "pushd DIR\r\n" + "\r\n"
      + "Saves the current working directory by pushing onto directory stack and then changes "
      + "the new current working directory to DIR. \nThe pushd command saves the old current "
      + "working directory in directory stack so that it can be returned \nto at any time"
      + " (via the popd command).\r\n" + "\r\n" + "EXAMPLE:\r\n"
      + "    Let’s assume the working directory is /A/hello and there is a directory /A/world in"
      + " the file system.\r\n" + "    INPUT:\r\n" + "        pushd /A/world\r\n"
      + "    OUTPUT:\r\n"
      + "        The directory /A/hello is saved on the top of the directory stack and /A/world"
      + " has become the working directory.\r\n" + "";

  /** Manual for pwd command */
  public final String MANPWD = "pwd " + OUTFILE_OPTIOPN
      + "\n\nPrints the full path of the current working directory\r\n" + OUTFILE + "EXAMPLE:\r\n"
      + "\tLets assume user’s current working directory is:    /root/hello/abc\r\n"
      + "\tpwd ->  Prints ‘/root/hello/abc’\r\n" + "";

  /** Manual for exit command */
  public final String MANEXIT = "exit\n\nQuit the program\n";

  /** Manual for get command */
  public final String MANGET = "\r\n" + "get URL\r\n" + "\r\n"
      + "Retrieve the file at that URL and add it to the current working directory.\r\n" + "\r\n"
      + "EXAMPLE:\r\n" + "\tget http://www.cs.cmu.edu/ spok/grimmtmp/073.txt\r\n" + "\r\n"
      + "\tWill get the contents of the file, i.e. 073.txt and create a file \n\tcalled 073 "
      + "with the contents in the current working directory.\r\n" + "\r\n" + "EXAMPLE:\r\n"
      + "\tget http://www.ub.edu/gilcub/SIMPLE/simple.html\r\n" + "\r\n"
      + "\tWill get the contents of the file, i.e. simple (raw HTML) and \n\tcreate a file "
      + "called simple.html with the\r\n" + "\tcontents in the current working directory.\r\n";

  /** Manual for save command */
  public final String MANSAVE = "\r\n" + "save FileName\r\n" + "\r\n"
      + "save command will interact with your real file system on your computer. \n"
      + "This stores the entire state of the program and is written to the file FileName. \n"
      + "The file FileName is some file that is stored on the actual filesystem of your computer. "
      + "\nThe purpose of this command is to save the session of the JShell \n"
      + "before the user closes it down. You can type in the command load FileName to \n"
      + "reinitialize the last saved session and begin from where they left off. \r\n" + "\r\n"
      + "EXAMPLE: \r\n" + "\tsave /Users/User1/Desktop/save, \n"
      + "\tThis will create a file save on your computer that will save the session of \n"
      + "\tthe JShell. If the above file save exists on your computer, then you must overwrite \n"
      + "\tthe file save completely.\r\n";

  /** Manual for load command */
  public final String MANLOAD = "\nload FileName\r\n" + "\r\n"
      + "load command will load the contents of the FileName and reinitialize everything that was\n"
      + "saved previously into the FileName. This allows the you to start a new JShell session, \n"
      + "type in load FileName and resume activity from where you left off previously. \r\n"
      + "We hope that this command is the very first thing that the you type in when starting a \n"
      + "new JShell, however, if the you were to type in the load command at any point after \n"
      + "any of the other commands have been executed, then load command will be disabled.\r\n";

  /** Manual for find command */
  public final String MANFIND = "find path … -type [f|d] -name expression " + OUTFILE_OPTIOPN
      + "\r\n" + "\r\n" + "find command recursively search for file/directory with name exactly as "
      + "expression in the \n" + "path(s) provided.\r\n" + "\r\n" + "-type f looks for file\r\n"
      + "-type d looks for directory\r\n" + OUTFILE;

  /** Manual for tree command */
  public final String MANTREE = "Represents the entire file system as a tree staring from the root "
      + "directory. \n" + OUTFILE + "EXAMPLE:\r\n"
      + "For instance, if the root directory contains two subdirectories as ‘A’ and ‘B’, then\n"
      + " tree command will output the following:\r\n" + "    \\\r\n" + "        A\r\n"
      + "        B\r\n" + "\r\n"
      + "For instance if the root directory contains two sub directories as ‘A’, ‘B’, ‘C’ and ‘A’ "
      + "in turn contains\r\n" + "‘A1’ and ‘A2’, then tree command will output the following:\r\n"
      + "    \\\r\n" + "        A\r\n" + "            A1\r\n" + "            A2\r\n"
      + "        B\r\n" + "        C\r\n" + "\r\n" + "";

  /** Manual for mv command */
  public final String MANMV = "\nmv OLDPATH NEWPATH\n\n"
      + "Move item OLDPATH to NEWPATH. Both OLDPATH and NEWPATH may be \n"
      + "relative to the current directory or may be full paths.\r\n" + "\r\n" + "EXAMPLE:\r\n"
      + "    For instance if the root directory contains two sub directories as ‘A’, ‘B’ and ‘A’ \n"
      + "\tin turn contains ‘A1’ and root is the current directory, then\r\n" + "\r\n"
      + "\tmv \\A\\A1 B\r\n" + "\r\n" + "\twill move the directory A1 from A to B.\r\n";

  /** Manual for cp command */
  public final String MANCP = "\ncp OLDPATH NEWPATH\n\n"
      + "Copy item OLDPATH to NEWPATH. Both OLDPATH and NEWPATH may be \n"
      + "relative to the current directory or may be full paths.\r\n" + "\r\n" + "EXAMPLE:\r\n"
      + "    For instance if the root directory contains two sub directories as ‘A’, ‘B’ and ‘A’ \n"
      + "\tin turn contains ‘A1’ and root is the current directory, then\r\n" + "\r\n"
      + "\tcp \\A\\A1 B\r\n" + "\r\n" + "\twill copy the directory A1 from A to B.\r\n";



  /** Stores the manual for each command */
  private static Hashtable<String, String> manHashtable = new Hashtable<String, String>();

  /** Error message for an invalid parameter */
  private final String ERRORMSG =
      "Error: The command for which the manual" + " is requested is not a valid command";

  /**
   * Executes man command
   */
  public String execute(FileSystem fileSystem, String[] arguments, InputHistory inputHistory) {
    initializeManHashtable(manHashtable);
    String manualString = manHashtable.get(arguments[0]);
    if (manualString != null) {
      return manualString;
    } else {
      return ERRORMSG;
    }
  }

  /**
   * Initializes the manHastable with the manuals for every command
   * 
   * @param manHashtable The HashTable that stores the manuals for every command
   */
  private void initializeManHashtable(Hashtable<String, String> manHashtable) {
    manHashtable.put("cat", MANCAT);
    manHashtable.put("cd", MANCD);
    manHashtable.put("echo", MANECHO);
    manHashtable.put("history", MANHISTORY);
    manHashtable.put("ls", MANLS);
    manHashtable.put("man", MANMAN);
    manHashtable.put("mkdir", MANMKDIR);
    manHashtable.put("popd", MANPOPD);
    manHashtable.put("pushd", MANPUSHD);
    manHashtable.put("pwd", MANPWD);
    manHashtable.put("exit", MANEXIT);
    manHashtable.put("get", MANGET);
    manHashtable.put("save", MANSAVE);
    manHashtable.put("load", MANLOAD);
    manHashtable.put("find", MANFIND);
    manHashtable.put("tree", MANTREE);
    manHashtable.put("mv", MANMV);
    manHashtable.put("cp", MANCP);
  }
}
