package noughtsAndCrossesV3;

import ncErrors.outOfRangeError;
/**
 * SimpleComputerPlayer illustrates how to make a subclass of GenericPlayer.
 * We need to provide a constructor - which in this case just calls the constructor for the supertype (GenericPlayer).
 * We also need to provide an implementation of the GetNextMove method.  
 *
 */

public class SimpleComputerPlayer   extends GenericPlayer implements NCPlayer 
{

	public SimpleComputerPlayer()
	{
		super(); 		// no further initialisation required
	}



 
/**
 * Basic implementation of getNextMove - scan through grid until we find an empty square, choose it
 * 
 * @see noughtsAndCrossesV3.GenericPlayer#getNextMove(noughtsAndCrossesV3.NCGridV3)
 */

	@Override
	public GridCoordinate getNextMove(NCGridV3 currentGrid) 
	{
		int theRow;
		int theCol;
		GridCoordinate theSquare = null;
		
		for (theRow = 0; (theSquare == null) && (theRow < currentGrid.getGridRowDimension()); theRow++)
		{
			for (theCol = 0; (theSquare == null) && (theCol < currentGrid.getGridColDimension()); theCol++)
			{
				try
				{
				if(currentGrid.getSquareStatus(theRow, theCol)==NCGridV3.SquareStatus.EMPTY)
					theSquare = new GridCoordinate(theRow, theCol);
				}
				catch (outOfRangeError e)
				{
					//  ignore the error (should never happen). Processing is left as another exercise for the reader  
				}
			}
		}
		return theSquare;
	}


}
