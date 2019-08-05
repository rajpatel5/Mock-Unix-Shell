# Mock-Unix-Shell

This assignment was assigned by the B07(Software Design) course instructor at the University of Toronto. This is a small UNIX shell designed by a group of four. It offers a variety of different commands such as mkdir, cp, get, and many more. The design incorporates a file system which is used by all commands, and it stores all information about the program. The file system is the back bone of the program. The shell has been tested vigourously as seen from the test folder, coding has been completed with care and full documentation. The design can be clearly understood by an intermediate to an expert level developer.

It was coded in Java and eclipse was the used as the IDE for debugging and testing. This assignment allows the user to connect to the web to retrieve any files from online using the URL object and buffered stream provided by Java, it also displays the entire file system as a tree structure through recursive implementation, and it remains perfectly stable even with invalid user inputs. It uses interfaces which allow objects to be referenced by the methods they support without considering their location in the class hierarchy. The shell also clearly displays errors, telling exactly what the error was and how the user can go about fixing it and clear, thorough documentation is provided which can be accessed using the man command.

| Command       | Documentation | Input         |Redirection?  |
| ------------- |:--------------| :-------------|:------------:|
| exit          | Quit the program | None          |YES           |
| mkdir         | Create directories, each of which may be relative to the current directory or maybe a full path.      | DIR...        |NO   |
| cd | Change directory to DIR, which may be relative to the current directory or maybe a full path.  As withUnix, ..  means a parent directory and a .  means the current directory.  The directory must be /, the forwardslash.  The foot of the file system is a single slash:  /.   | DIR | NO|
| ls          | if –R is present, recursively list all subdirectories.If no paths are given, print the contents (file or directory) of the current directory, with a new line followingeach of the content (file or directory).Otherwise, for each path p, the order listed:If p specifies a file, print pIf p specifies a directory, print p, a colon, then the contents of that directory, then an extra new line.If p does not exist, print a suitable message.| [-R] [PATH . . . ] |YES        |
| exit          | Quit the program | None          |YES           |
| exit          | Quit the program | None          |YES           |
| exit          | Quit the program | None          |YES           |
| exit          | Quit the program | None          |YES           |
| exit          | Quit the program | None          |YES           |
