package noughtsAndCrossesV3;

import java.util.ArrayList;
import java.util.Random;

import ncErrors.outOfRangeError;
import noughtsAndCrossesV3.NCGridV3.SquareStatus;

public class RandomComputerPlayer extends GenericPlayer implements NCPlayer 
{
	Random theGenerator;	// java.util.Random is a system class that allows us to generate a stream of random numbers

	/**
	 * RandomComputerPlayer illustrates how to make a subclass of GenericPlayer.
	 * It has an instance of java.util.Random, to generate random numbers.
	 * It chooses its moves by finding all empty squares (stored in an ArrayList)
	 * and picking one at random
	 *
	 */
	public RandomComputerPlayer()
	{
		super();
		theGenerator = new Random();
	}


	/**
	 * Implementation of getNextMove - scan through grid, storing each empty square in an arraylist.
	 * Once all are found, use the random number generator to pick an integer  between 0 and the number of available squares,
	 * return the empty square at that position in the ArrayList
	 * 
	 * @see noughtsAndCrossesV3.GenericPlayer#getNextMove(noughtsAndCrossesV3.NCGridV3)
	 */

@Override
	public GridCoordinate getNextMove(NCGridV3 currentGrid) 
	{
		int theRow;
		int theCol;
		ArrayList<GridCoordinate> freeSquares = new ArrayList<GridCoordinate>();
		int randomSelection;

		for (theRow = 0; theRow < currentGrid.getGridRowDimension(); theRow++)
		{
			for (theCol = 0;  theCol < currentGrid.getGridColDimension(); theCol++)
			{
				try
				{
				if(currentGrid.getSquareStatus(theRow, theCol)==NCGridV3.SquareStatus.EMPTY)
					freeSquares.add(new GridCoordinate(theRow, theCol));
				}
				catch (outOfRangeError e)
				{
					// and ignore it - another exercise for the reader  
				}
			}
		}
		// pick a number between 0 and the number of available squares
		randomSelection = theGenerator.nextInt(freeSquares.size());
		
		
		return freeSquares.get(randomSelection);
	}


}
