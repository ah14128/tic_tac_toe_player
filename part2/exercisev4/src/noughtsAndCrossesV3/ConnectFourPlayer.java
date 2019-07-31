package noughtsAndCrossesV3;

import java.util.ArrayList;

import ncErrors.outOfRangeError;
import noughtsAndCrossesV3.ExpertPlayer.winningSquares;
import noughtsAndCrossesV3.NCGridV3.GameStatus;
import noughtsAndCrossesV3.NCGridV3.SquareStatus;

public class ConnectFourPlayer extends GenericPlayer implements NCPlayer {

	public ConnectFourPlayer()
	{
		super();
	}
	/**
	 * This player is very much a work in progress and although it does work it doesn't play very well or do what it's suppose to do
	 * use at your own caution
	 */
	
	@Override
	public GridCoordinate getNextMove(NCGridV3 currentGrid) {
		int maxRow = currentGrid.getGridRowDimension();
		int maxCol = currentGrid.getGridColDimension();
		int theRow;
		int theCol;
		GridCoordinate theSquare = null; 
		try 
		{
			for(int counter = 0; counter < maxCol; counter++)
			{
				if(checkLines(0,counter,1,0,currentGrid) != 0)
				{
					for(int i = 0; i < maxRow; i++)
					{
						if(currentGrid.getSquareStatus(i, counter) == NCGridV3.SquareStatus.EMPTY)
						{
							theSquare = new GridCoordinate(i, counter);
							return theSquare;
						}
					}
				}
				if(checkLines(0,counter,1,1,currentGrid) !=0)
				{
					for(int i = 0; i < maxRow; i++)
					{
						if(currentGrid.getSquareStatus(i, counter) == NCGridV3.SquareStatus.EMPTY && currentGrid.getSquareStatus(i+1, counter) != NCGridV3.SquareStatus.EMPTY)
						{
							theSquare = new GridCoordinate(i, counter);
							return theSquare;
						}
					}
				}
				if(checkLines(0,counter,1,-1,currentGrid) !=0)
				{
					for(int i = 0; i < maxRow; i++)
					{
						if(currentGrid.getSquareStatus(i, counter) == NCGridV3.SquareStatus.EMPTY && currentGrid.getSquareStatus(i+1, counter) != NCGridV3.SquareStatus.EMPTY)
						{
							theSquare = new GridCoordinate(i, counter);
							return theSquare;
						}
					}
				}
			}
			for(int counter = 0; counter < maxRow; counter++)
			{
				if(checkLines(counter, 0, 0, 1,currentGrid) != 0)
				{
					for(int i = 0; i < maxCol; i++)
					{
						if(currentGrid.getSquareStatus(counter, i) == NCGridV3.SquareStatus.EMPTY)
						{
							theSquare = new GridCoordinate(counter, i);
							return theSquare;
						}
					}
				}
				if(checkLines(counter,0, 1,1,currentGrid) != 0)
				{
					for(int i = 0; i < maxCol; i++)
					{
						if(currentGrid.getSquareStatus(counter, i) == NCGridV3.SquareStatus.EMPTY && currentGrid.getSquareStatus(counter, i+1) != NCGridV3.SquareStatus.EMPTY)
						{
							theSquare = new GridCoordinate(counter, i);
							return theSquare;
						}
					}
				}
				if(checkLines(counter, maxCol-1, 1, -1,currentGrid) != 0)
				{
					for(int i = 0; i < maxCol; i++)
					{
						if(currentGrid.getSquareStatus(counter, i) == NCGridV3.SquareStatus.EMPTY && currentGrid.getSquareStatus(counter, i+1) != NCGridV3.SquareStatus.EMPTY)
						{
							theSquare = new GridCoordinate(counter, i);
							return theSquare;
						}
					}
				}
			}
			if(currentGrid.getSquareStatus(5,3) == NCGridV3.SquareStatus.EMPTY) //First move go in the middle bottom square if it's free
			{
				theSquare = new GridCoordinate(5,3);
				return theSquare;
			}
			for(int i = 0; i < 4; i++)
			{
				if(currentGrid.getSquareStatus(0,3+i) == NCGridV3.SquareStatus.EMPTY)
				{
					theSquare = new GridCoordinate(0,3+i);
					return theSquare;
				}
				if(currentGrid.getSquareStatus(0,3-i) == NCGridV3.SquareStatus.EMPTY)
				{
					theSquare = new GridCoordinate(0,3-i);
					return theSquare;
				}
			}
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
						//  Does not happen
					}
				}
			}
			return theSquare;
			
		}
		catch (outOfRangeError e) 
		{
			e.printStackTrace();
		}
		return theSquare;
	}
		
	private int checkLines(int startRow, int startCol, int incrementRow, int incrementCol, NCGridV3 currentGrid)
	{
		int counter = 1;
		int emptySquareChecker = 0;
		
		try
		{
			SquareStatus firstSquareContent = currentGrid.getSquareStatus(startRow, startCol);
			boolean lineOfN = (firstSquareContent != SquareStatus.EMPTY);
			for(int count=0; count < 7; count++)
			{
				if(firstSquareContent == SquareStatus.EMPTY )
				{
					emptySquareChecker++;
				}
					
				startRow += incrementRow;
				startCol += incrementCol;
				if(startRow >= 6 || startRow < 0 || startCol < 0 || startCol >= 7)
				{
					counter = 0;
					break;
				}
				lineOfN= ( currentGrid.getSquareStatusNoChecking(startRow, startCol) == firstSquareContent);
				
				if((!lineOfN && firstSquareContent != SquareStatus.EMPTY) || emptySquareChecker > 1)
				{
					counter = 0;
					emptySquareChecker = 0;
					firstSquareContent = currentGrid.getSquareStatusNoChecking(startRow, startCol);
				}
				counter++;
				
				if(counter == 4 && firstSquareContent != SquareStatus.EMPTY)
				{
					return counter;
				}
			}
		}
		catch(outOfRangeError e)
		{
			e.printStackTrace(); //Should not happen
		}
		return counter;
	}

}
