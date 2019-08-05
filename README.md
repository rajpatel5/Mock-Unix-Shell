# Mock-Unix-Shell

This assignment was assigned by the B07(Software Design) course instructor at the University of Toronto. This is a small UNIX shell designed by a group of four. It offers a variety of different commands such as mkdir, cp, get, and many more. The design incorporates a file system which is used by all commands, and it stores all information about the program. The file system is the back bone of the program. The shell has been tested vigourously as seen from the test folder, coding has been completed with care and full documentation. The design can be clearly understood by an intermediate to an expert level developer.

It was coded in Java and eclipse was the used as the IDE for debugging and testing. This assignment allows the user to connect to the web to retrieve any files from online using the URL object and buffered stream provided by Java, it also displays the entire file system as a tree structure through recursive implementation, and it remains perfectly stable even with invalid user inputs. It uses interfaces which allow objects to be referenced by the methods they support without considering their location in the class hierarchy. The shell also clearly displays errors, telling exactly what the error was and how the user can go about fixing it and clear, thorough documentation is provided which can be accessed using the man command.

| Command and Input  | Documentation | Redirection?  |
| ------------- |:--------------| :------------:|
| exit          | Quit the program|YES          |
| mkdir DIR. . .  | Create directories, each of which may be relative to the current directory or maybe a full path.      | NO   |
| cd | Change directory to DIR, which may be relative to the current directory or maybe a full path.  As with Unix, ..  means a parent directory and a . means the current directory.  The directory must be /, the forward slash.  The foot of the file system is a single slash:  /. | NO|
| ls [-R] [PATH . . . ]  | If –R is present, recursively list all subdirectories.If no paths are given, print the contents (file or directory) of the current directory, with a new line followingeach of the content (file or directory).Otherwise, for each path p, the order listed: ..*If p specifies a file, print p 
..*If p specifies a directory, print p, a colon, then the contents of that directory, then an extra new line. 
..*If p does not exist, print a suitable message.|YES        |
| pwd          | Print the current working directory (including the whole path). | YES           |
| mv OLDPATH NEWPATH | Both OLD-PATH  and  NEWPATH  may  be  relative  to  the  current  directory  or  may  be  full  paths.   If  NEWPATH  is  adirectory, move the item into the directory. | NO         |
| cp OLDPATH NEWPATH |  Like mv, but don’t remove OLDPATH. If OLDPATHis a directory, recursively copy the contents. | NO           |
| cat FILE . . .     | If there are more than one file, you must display all their contents on the console. (assuming all are a valid path).  For any file that contains an invalid path, display an appropriate error for that path only. All other valid paths must still be shown on the console. Display the contents of FILE and other files on the console in the shell.| YES           |
| echo String          | Print String | YES           |
| man CMD          | Print documentation for CMD. | YES           |
| pushd DIR         | Saves  the  current  working  directory  by  pushing  onto  directory  stack  and  then  changes  the  new  currentworking  directory  to  DIR.  The  push  must  be  consistent  as  per  the  LIFO  behaviour  of  a  stack. The  pushd command saves the old current working directory in directory stack so that it can be returned to at any time (via the popd command). The size of the directory stack is dynamic and dependent on the pushd and the popdcommands. | NO           |
| popd          |Remove the top entry from the directory stack, and cd into it. The removal must be consistent as per theLIFO behaviour of a stack.  The popd command removes the topmost directory from the directory stack andmakes it the current working directory.  If there is no directory onto the stack, then give an appropriate errormessage. | NO   |
| history [number] | Print out recent commands, one command per line. | YES   |
| save FileName         | Interacts with the real file system on user's computer. Ensures that the entire state of the program is written to the file FileName.  The file FileName is some file that is stored on the actual filesystem of the user's computer.  The purpose of this command is to save the session of the JShell before the user closes it down.| NO    |
| load FileName         | The JShell will load the contents of the FileName and reinitialize everything that was saved previously into the FileName. This allows the user to start a new JShell session, type in load FileName and resume activity from where they left off previously. | NO   |
| find path ...  -type [f|d] -name expression | Attempts to find a specific type with specified values. Example: find /users/Desktop -type f -name "xyz".  This will search the directory Desktop and find all files (indicated by type f) that have the name exactly xyz. | NO   |
| tree         | Displays the entire file system in a tree structure. | YES   |
