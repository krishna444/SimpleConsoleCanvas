# Solution- Coding challenge

I have implemented the solution using Java programming language. I developed this application using Eclipse IDE. In my solution branch, you can find a project folder _'drawing'_ where the project files are located.  

Apart from JRE System libraries, the application needs Junit libraries which are placed in /libs folder. 


## Running the program

We need JRE to be installed in the system to run this application. This is Eclipse based project, we can directly import in Eclipse environment. From there we can direcly run startup file (Drawing.java).

In Eclipse, there is possibility to export this project as an executable jar file(drawing.jar), can be run from command line. (I have created one which is in dist folder.)

`java -jar Drawing.jar`

## Documentation

### Packages

I have created two packages
* *drawing* This package contains 
    * `Drawing.java` : This is startup file and this class takes user command and carries validation and executes the command.
    * `Canvas.java` : This is the main class which contains the canvas model(i.e. 2D array of characters) and operations. 
* *drawing.commands* This packages contains all user commmands. All commands should implement ICommand interface. If we need a new command, we just add it here. Currently, this package contains the following commands:
    * `CreateCommand` creates a new canvas with specified height and width. If this command is not executed, that means there exists no canvas.
    * `LineCommand` creates a vertical or horizontal line between two speficied points.
    * `RectangleCommand` creates a rectable from the spefied top-left and bottom-right points.
    * `BucketFillCommand` fills the area within a point with specified color.

### Validation of Input 

There are a couple of validations to be checkted.
* **Input format** should be correct. User can provide wrong input, and this should be identified. I have used reguar expressions to check if the provided input belongs to our commands.  If not, it just discards the user input. 

* **Existence of Canvas** is necessary to carry out operations on canvas. If user wants to draw a line without creating a canvas, it is not possible.
* **Points** should be within canvas. If we want to draw a rectangle having points outside of a canvas, that is not feasible.

### Command Design Pattern

This coding challenge, as per requirement, has been solved by creating two dimensional array of characters. I have implemented this using **Command Pattern**, which I found more clear to understand. Depending upon the command user provides, the corresponding command operations is carried out. All commands extend the interface ICommand.

### Operations

* **Canvas Creation** Creates a two dimensional array of defined width and height. Since canvas has boundaries, so, we actual _width_ and _height_ of canvas will be _width+2_ and _height+2_.

* **Line Creation** This allows to create either horizontal or vertical lines. 

* **Rectangle Creation** A rectangle has four vertical and horizontal lines which are created by using top-left and bottom-right points

* **Bucket Fill** To implement this operation, firstly I have used recursive search of neighbors and check if we can fill the point. Since recursive uses stack for every recursive calls that means for a large search area(large canvas), result "StackOverFlow" error. I tried with Tail-Recursive method was better, but could not improve signigicantly. 

    I then implemented _Bucket Fill_ by storing visited valid neighboring points in a queue. This method improve the performance significantlly and also worked for large canvas.   

### Time and Space Complexity

Time complexity of the operations above depends upon the number of points (i.e. area) of the canvas. We have to think about the space complexity because the canvas representation is array of characters. This is stored in java heap memory. So if we have canvas of size 100000x10000, then representation of canvas takes 
100000x10000=1000000000 bytes(ie 976.5 MB)  of heap memory, and if heap memory is not enough, the application crashes! This is extreme case, and normally, we do not need such a huge canvas!  
    


### Logging

The  running information of the application is written in a log file (canvas.log) created on the application location. 

### Testing

For testing purpose I have used JUnit library for unit testing. The unit test has been carried out on the canvas object and its operations, which is vital for testing. 











