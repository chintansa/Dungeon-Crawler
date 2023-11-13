
# Project - GRAPHICAL ADVENTURE GAME 
### Overview

The world for our game consists of a dungeon, a network of tunnels and caves that are interconnected so that a player can explore the entire world created by traveling from cave to cave through the tunnels that connect them. If lucky the player can find treasures like Diamond, Ruby and Sapphires in some or all of the caves present arrows all around the dungeon with the same frequncy as the treasure.Also there are monsters lurking around the caves in the dungeon which the player has to kill with the arrows he has and reach the end location to win.


### Features

- The dungeon can be represented as a 2D grid of sorts and every point on the grid has a location object.
- The location contains properties like if its a cave or tunnel based on the number of paths from it. If it has two paths from it to other locations then it is a tunnel else it's a cave.
- Only caves can contain treasure and only certain percentage of caves as given by client  will have it.
- The dungeon is initially created such that there is minimum of one path from one cave to every other cave and then the interconnectivity is added as given by adding edges randomly.
- The dungeon has a property of wrapping and non wrapping as given by the client where the top border locations of the grid will have a conneting path to the other end on the row or column.These wrapping edges are selected at random as well.
- A random start and end location is selected at random such that there is atleast a minimum path of 5 between them.
- The game begins when the player enters the dungeon at the start location and will be displayed the options to move from the avaliable directions. 
- A message is displayed for every move the player makes with the treasure and arrows available and if any otyughs are nearby.
- For a Graphical based game ->
   * Press W, A, S, D to move the player North, East, South and West respectively in the dungeon.
   * Press a number between 1 and 5 on the num-pad for the distance and one of the arrow keys for direction and press SPACE   to shoot the arrow.
   * Press I to get the player info as a pop-up displaying the number of arrows he has and the treasure he has collected.
   * Press P to pick-up arrows or treasure present at the current location of the player.
   * From the JMenu the user can start a new game with different configurations or restart the game.
   * The user will get a pop-up if he wins the game or get's killed by a otyugh after that he can restart or press new game     from the menu options.
- The view will be mostly black at first for all the nodes the player has not visited and the locations are seen for each       node the player visits.

- For a text-based-adventure game, a promt is shown where the user can enter 
    * "M" to move and then enter the direction from available paths.
    * "S" to shoot arrow which then asks the direction and distance(1 to 5) to shoot.
    * "P" to pickup treasure or arrows. enter T for treasure or A for arrow to pick up after that. 
    * "Q" to quit the game at anytime.

- A player at beginning will have 3 arrows to start with.
- A player has to shoot 2 arrows to the exact location of the otyugh to kill it. A message will be shown if you succesfully hit a otyugh.
- Player can smell a otyugh upto two distance from his current location.
- If a otyugh is at a distace of 1 or multiple otyughs are present at a distace of 2 then the player will get a strong smell.
- If one monster is present at a distace of two then a light smell is found and he can sense that something is lurking around.
- If player enters a cave with a otyugh which is hit only once then he has a 50 percent chacnce of survival.
 

### How to Run

- This project contains a file called driver class that has a model and controller. 
- It also has JAR file in the res/ folder of the zip can be used to run the driver for this project using the command -> 
- For text-based game ->
    * "java -jar name.jar rowSize columnSize true/false(wrapping) interconnetivitySize treasurePercentage otyugh_number".
- For Graphical based game ->
   * "java -jar name.jar" 
   * and enter the values for each as mentioned above attributes within the option panes.
    
   
### How to Use the Program

 - When the program is run, first you'll have to give arguments for number of rows and columns for the grid, if wrapping has to be true or false, number of interconnectivity to be present and the percetage of caves to have the treasure and the number of monsters that should be present.
 - The dungeon is created based on the inputs provided and the player enters the start location that is selected randomly. 
 - A info box is printed which tells the available treasure and arrows and the current location of the player and if there are any monsters nearby.
 - The controller will take in inputs from the user and runs the game smoothly.
 - ##### For text based game ->
     - One can enter "M" and enter the directions(north,south,east,west) avaliable from that path.
     - if there is a treasure or arrows then enter "P" and  select which one you want to pick.
     - Enter "S" to shoot a arrow in a direction and a distance (between 1-5) it has to travel.
     - User can press "P" for player info and "Q" to quit at anytime.
      
 - ##### For a Graphical based game ->
   * Press W, A, S, D to move the player North, East, South and West respectively in the dungeon.
   * Press a number between 1 and 5 on the num-pad (check for num-lock) for the distance and one of the arrow keys and press SPACE to shoot the arrow.
   * Press I to get the player info as a pop-up displaying the number of arrows he has and the treasure he has collected.
   * Press P to pick-up arrows or treasure present at the current location of the player.
   * From the JMenu the user can start a new game with different configurations or restart the game.
   * By clicking each of the enteries in GameConfig present in the JMenu user can update only one of the entries and restart the game with the new configs given only if valid arguments/values are given. 
   * The user will get a pop-up if he wins the game or get's killed by a otyugh after that he can restart or press new game     from the menu options.
 
 

### Description of Examples
 - For Text-based-game ->
    - First run shows the player's movement in a 5 x 5 non wrapping dungeon where the player moves around and picks arrows and smells if there are any otyughs around and kills some. But he dies at the hands of one of the otyughs.
    - Second run shows the player navigating his way in a 5 x 5 non wrapping dungeon while collecting treasure and arrows to the end location by killing few otyughs on the way and also at the end location and wins the game. 
- Graphical based game -> [Game snapshots](https://docs.google.com/document/d/1sOjoBXD6VtARONDPQQ7DDnbVw1qDjt7yh8rDgcvD9d4/edit?usp=sharing).
- Please see this doc to refer to images which show the player moving in the dungeon, killin and getting killed by a otyugh, shooting a arrow to kill a otyugh, description of the player, and location and player action descriptions.

### Design/Model Changes

- Added the view components to the previous structure which includes a main view which contains a panel to display the dungeon part and a panel on top which shows the action taken by the player and a panel to the south which shows the locaiton details of where the player is currently present.
- Added a controller class and interface to handle the view and the model.
- Created a interface for Readonly model to be used by the view.

### Assumptions

-  The minimum possible row and column size has to be 5 and max 100 both to avoid possiblities where a dungeon might not have a minimum of 5 path to select a start and end.
-  When very high interconnectivity is given (more than the number of reserve edges present), the interconnectivity is set to the maximum(the size of the reserve edge list).
-  Same goes for monster number also where if the number is more than the number of caves then max of both will be assigned.
-  Caves can have a min of 1 to max of 3 treasure if that cave is selected to hold treasure. Same goes for arrows but they can present in both caves and tunnels.
-  Monsters are present only in caves and only zero or one is present at a given cave.


### Limitations

- Considered a min of  5 X 5 grid as a minimum of 5 path may not be found and as no option is present to reconstruct the grid again progarm might fail.
- Grid more than 20x20 will take some time to build based on the selection of start and end node.

### Citations

- Referred to geeksforgeeks.com and stackoverflow.com to understand concepts and debugging.
- Reffered to [Kruskal's algorithm](https://algorithms.tutorialhorizon.com/kruskals-algorithm-minimum-spanning-tree-mst-complete-java-implementation/) for implementing a minimum spaning tree which has a path from one location to every other locationn in the dungeon.
- Reffered to [DFS algorithm](https://www.geeksforgeeks.org/minimum-number-of-edges-between-two-vertices-of-a-graph-using-dfs/?ref=rp) to find a minimum path of distance 5 between 2 locations to select a start and end location for the dungeon for the player to enter and exit.
- GridLayout for the dungeon view and scrollpane integration: [Bro Code tutorials](https://www.youtube.com/watch?v=ohNqQagkDDY&ab_channel=BroCode) to create a grid layout to hold the dungeon view.

- Adding images using getResource(): [Youtube tutorials](https://www.youtube.com/watch?v=1jOFpn4H-Iw&ab_channel=GnytongnonWilfriedBlouh)

- Arrow shooting using multiple Keys input from keyboard: [StackOverFlow](https://stackoverflow.com/questions/2623995/swings-keylistener-and-multiple-keys-pressed-at-the-same-time)

- BorderLayout: [How to use a border layout](https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/layout/BorderLayoutDemoProject/src/layout/BorderLayoutDemo.java) Oracle Docs.

- JOptionPanel integration to the game: [How to use JOption panel to take inputs from user](https://mkyong.com/swing/java-swing-joptionpane-showinputdialog-example/).

- JMenu integration to the game: [Bro Code tutorials](https://www.youtube.com/watch?v=7nEal9SJ6oI&ab_channel=BroCode)

- MouseClick movement: Refered to the lectures and Tic-Tac-Toe code structure of Prof. Clark Freifeld.

- ActionListener, Lambda functions, KeyListener and MVC concepts from Prof. Maria Jump's lectures present on Canvas.

