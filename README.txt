=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: jackke12
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

2D Arrays - The 2D arrays are the grids that the minesweeper game will be played on. The things stored will be whether or not the square is clicked, whether or not there is a mine there, whether there is a flag there, or the number of mines that are adjacent to that square (if there isn’t a mine there). We use -1 for a mine, 0 for an empty square which when clicked will open all other empty squares, and a number for adjacent mines.

Recursion - I would like to implement a recursive function to reveal all connected empty cells when a player clicks on an empty cell. This will traverse all squares adjacent to the empty cell and continue to reveal them if they are empty.

File I/0 - The game uses IO in order to save the state of the game as well as load the state of the game. This can be used to resume the user's progress

JUnit Testable Component - The state of my game lies in a 2D array. Given the coordinates of the square that is clicked, the game will have a method that will process these coordinates. The functionality of my game will be tested using JUnit test cases. This will make sure that the array is being updated correctly and when the game ends. Edge cases are if an already clicked square is being clicked again (nothing will happen). When a square with a flag is clicked again, the flag will disappear.
===============================
=: File Structure Screenshot :=
===============================

- Include a screenshot of your project's file structure. This should include
  all of the files in your project, and the folders they are in. You can
  upload this screenshot in your homework submission to gradescope, named
  "file_structure.png".

=========================
=: Your Implementation :=
=========================

Board
The Board class handles the GUI rendering of the game grid and user interaction through mouse clicks. It paints the board, displays the adjacent bomb counts, and manages user actions like uncovering squares or flagging them.

GameSetup
This class contains the core game logic, including initializing the grid, placing bombs, uncovering cells, flagging, and saving/loading game states. It acts as the bridge between the Board and the underlying game state, ensuring all operations are synchronized.

Square
Represents an individual cell on the grid, storing its state (e.g., whether it is a bomb, flagged, covered, or revealed) and other properties like the number of adjacent bombs. The class encapsulates all operations related to individual squares.

RunMinesweeper
This is the entry point of the game and sets up the main game window. It creates the Board, initializes the game setup, and manages the status label for displaying messages to the user.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

I think that overall, the stumbling blocks was trying to logically think out the game

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

The design maintains a good separation of functionality, with game logic and GUI clearly divided. Private state is encapsulated effectively, ensuring direct user interactions through the GUI do not compromise the game state. With more time, I would refactor the paintComponent method in Board to better organize rendering logic and consider improving test coverage for edge cases.

========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used
  while implementing your game.