package noughtsAndCrossesV3;

import java.util.Scanner;

/**
 * An implementation of  GenericPlayer which uses a scanner to read a move input at the keyboard, from a human player.
 * No error checks are made on the move, so if it is out of range or already occupied, this must be handled by the caller
 * The class displays instructions and the current grid before reading the next move. Access to the game manager is required,
 * so the current game manager is stored (see constructor code) 
 *
 */
public class HumanPlayer extends GenericPlayer implements NCPlayer 
{
	Scanner theInput ;
	GameRunnerV3 theGame;

	/**
	 * @param inputScanner gives access to the keyboard for inout
	 * @param gameRunner allows HumanPlayer to display messages and the grid
	 */
	public HumanPlayer(Scanner inputScanner, GameRunnerV3 gameRunner)
	{
		super();				// constructor for superclass  (GenericPlayer) MUST be called first
		theInput = inputScanner ;
		theGame = gameRunner;
	}


	/**
	 * Displays the grid and reads the move from the keyboard
	 * @see noughtsAndCrossesV3.GenericPlayer#getNextMove(noughtsAndCrossesV3.NCGridV3)
	 */
	public GridCoordinate getNextMove(NCGridV3 currentGrid) 
	{
		theGame.displayInstructions(currentGrid.getGridRowDimension(), currentGrid.getGridColDimension());
		theGame.displayGrid(currentGrid);
		int theRow = theInput.nextInt();
		int theCol  = theInput.nextInt(); 
		return new GridCoordinate(theRow, theCol);
	}

}
