package noughtsAndCrossesV3;

import java.util.Arrays;

import ncErrors.occupiedSquare;
import ncErrors.outOfRangeError;

public class NCGridV3 {
	
	
	/**
	 * Represents the allowed content of squares in the grid as an enum
	 *
	 */
	public enum SquareStatus {NOUGHT,CROSS,EMPTY} 
	/**
	 * Represents the state of the game using an enum
	 *
	 */
	public enum GameStatus {NOUGHTWIN,CROSSWIN,DRAW,STILLPLAYING}
	
	protected int gridSize;		// number of rows / columns in the grid
	protected SquareStatus theGrid[][];
	protected int numberOccupiedSquares;
	int maxCol;
	int maxRow;
	
	/**
	 * NCGridV3 represents a noughts and crosses grid.
	 * The code is adapted from NCGrid used in earlier labs. The constructor requires two arguments (to allow for rectangular grids,
	 * in case an m by n game is implemented).
	 * This implementation is restricted to square grids. If the specified number of rows  differs from the number of columns, 
	 * the larger value is taken as the dimension of the square array and an error message is printed,
	 * @param rowSize the number of rows in the grid
	 * @param colSize the number of columns in the grid
	 */
	public  NCGridV3 (int rowSize, int colSize)			// row and col sizes might be different in extended versions; here they are forced to be the same
	{
		numberOccupiedSquares=0;
		maxRow = rowSize;
		maxCol = colSize;
		gridSize = java.lang.Math.max(rowSize, colSize);
		theGrid = new SquareStatus[maxRow][maxCol];
		for(int row=0; row < maxRow; row++)
		{
			for(int col=0; col < maxCol; col++)
				theGrid[row][col] = SquareStatus.EMPTY;
		}
	}
	/**
	 * Provides access to the grid size (NB needs to be changed if rectangular grids are implemented)
	 * @return dimension of the square grid
	 */
	public int getGridRowDimension()
	{
		return maxRow;
	}
	/**
	 * Provides access to the grid size (NB needs to be changed if rectangular grids are implemented)
	 * For square grids, this method is superfluous and is equivalent to getGridRowDimension().
	 * @see getGridRowDimension()
	 * @return dimension of the square grid
	 */
	public int getGridColDimension()
	{
		return maxCol;
	}
	
	/**
	 * @param value  the row / column co-ordinate to be checked
	 * @param max	 the dimension of the grid (row or column), value can range from zero to max-1
	 * @return true if the value is in the specified range
	 */
	private boolean inRange(int value, int max)
	{
		return (value >= 0) && (value < max);
	}

	/**
	 * Finds the content of a grid square
	 * @param row  the row coordinate of the square
	 * @param col the column coordinate of the square
	 * @return EMPTY, CROSS or NOUGHT according to the square content
	 * @throws outOfRangeError   if row or col is out of range
	 */
	public SquareStatus getSquareStatus(int row, int col) throws outOfRangeError
	{
		if(inRange(row, maxRow) && inRange(col, maxCol))
			return theGrid[row][col];
		else
			throw new outOfRangeError(); // row,col);
	}
	
	/**
	 * Finds the content of a grid square without checking for OutOfRange values
	 * Intended for use within the class and subclasses (hence the protected declaration) as we can (or should be able to)
	 *  guarantee that only valid coordinates are passed in.
	 * @param row the row coordinate of the square
	 * @param col the column coordinate of the square
	 * @return EMPTY, CROSS or NOUGHT according to the square content
	 */
	protected SquareStatus getSquareStatusNoChecking(int row, int col) // same as above without error checking
	{
		return theGrid[row][col];
	}
	
	/**
	 * Changes an empty square to have content specifed by value (CROSS or NOUGHT).
	 * If the square is not empty, it returns false but takes no other action
	 * @param row  the row coordinate of the square
	 * @param col the column coordinate of the square
	 * @param value the requested new content of the square 
	 * @return true if the square was empty and is now set to the required value, false is the square was not empty
	 * @throws outOfRangeError if row or col is out of range
	 */
	public boolean  setSquareStatus(int row, int col, SquareStatus value) throws outOfRangeError, occupiedSquare
	{
		boolean ret=inRange(row, maxRow) && inRange(col, maxCol)
				;
		if(ret)
		{
			switch( theGrid[row][col])
			{
			case EMPTY:
				theGrid[row][col] = value;
				numberOccupiedSquares++;
				break;

			case NOUGHT:
			case CROSS:
				throw new occupiedSquare();		// This should this be an error; currently we silently forfeit the turn
			}
		}
		else
			throw new outOfRangeError(); // if row or col is not valid;
		return ret;
	}
	/**
	 * A function that sets the square and ignore the row input, and sets the the symbol in the lowest symbol in the column
	 * @param col the input column
	 * @param value symbol of the player
	 * @return
	 * @throws outOfRangeError
	 */
	
	public boolean setSquareConnect4(int col, SquareStatus value) throws outOfRangeError
	{
		boolean ret = inRange(col, 7);
		if(ret)
		{
			for(int i =0; i < 6; i++)
			{
				if(getSquareStatus(5-i, col) == NCGridV3.SquareStatus.EMPTY)
				{
						
					theGrid[5-i][col] = value;
					numberOccupiedSquares++;
					break;
				}
			}
			
		}
		return ret;
		
	}
	
	/**
	 * getNumberOfOccupiedSquares reports the number of grid squares that have been set to non-empty by the setSquareStatus method.
	 * It can be used to determine that a game is drawn, if there are no unoccupied squares left.
	 * @return the number of grid squares that are occupied
	 */
	public int getNumberOfOccupiedSquares()
	{
		return numberOccupiedSquares;		// this is updated in successful calls to setSquareStatus
	}
	/**
	 * getGameStatus checks whether the game is over or not.
	 * @return CROSSWIN or NOUGHTWIN if either player has won,
	 * otherwise STILLPLAYING if neither player has won and there are empty squares in the grid
	 * or DRAW if there are no empty squares in the grid   
	 * 
	 * 
	 */
	public GameStatus getGameStatus() 
	{
		GameStatus theStatus = GameStatus.STILLPLAYING;
		/* otherwise check each row and column */
		for(int counter =0; (theStatus == GameStatus.STILLPLAYING ) && (counter < gridSize); counter++)
		{
			theStatus = checkForLineOfN(counter, 0, 0, 1);		// check row
			if(theStatus == GameStatus.STILLPLAYING)
				theStatus = checkForLineOfN(0, counter, 1, 0);		// check column

		}
		if(theStatus == GameStatus.STILLPLAYING)
			theStatus = checkForLineOfN(0, 0, 1, 1);		// check diag
		if(theStatus == GameStatus.STILLPLAYING)
			theStatus = checkForLineOfN(0, gridSize-1, 1, -1);		// check other diag
			
		// Made a small edit here because some games were finishing in a draw even when a player wins on the last move of the game
		if(getNumberOfOccupiedSquares() == maxRow*maxCol && theStatus == GameStatus.STILLPLAYING) 
			theStatus = GameStatus.DRAW;
		return theStatus;
	}
	
	public GameStatus getGameStatusConnect4()
	{
		GameStatus theStatus = GameStatus.STILLPLAYING;
		
		for(int counter = 0; counter < maxCol && theStatus == GameStatus.STILLPLAYING; counter++)
		{
			theStatus = lineConnectFour(0,counter,1,0);
			if(theStatus == GameStatus.STILLPLAYING)
				theStatus = lineConnectFour(0,counter,1,1);
			if(theStatus == GameStatus.STILLPLAYING)
				theStatus = lineConnectFour(0,counter,1,-1);
		}
		if(theStatus == GameStatus.STILLPLAYING)
		{
			for(int counter = 0; counter < maxRow && theStatus == GameStatus.STILLPLAYING; counter++)
			{
				theStatus = lineConnectFour(counter, 0, 0, 1);
				if(theStatus == GameStatus.STILLPLAYING)
					theStatus = lineConnectFour(counter,0, 1,1);
				if(theStatus == GameStatus.STILLPLAYING)
					theStatus = lineConnectFour(counter, maxCol-1, 1, -1);
			}
		}
		if(getNumberOfOccupiedSquares() == maxRow*maxCol && theStatus == GameStatus.STILLPLAYING) 
			theStatus = GameStatus.DRAW;
		return theStatus;
	}
	
	/**
	 * check to see whether there is a line of <gridSize>  identical symbols (NOUGHT or CROSS)
	 * starting at square (startRow, startCol) and moving in direction specified 
	 * by incrementRow, incrementCol. These are added to startRow/Col values to find the next square
	 * in the series. Hardcoded to look for lines of length gridSize (stored as a class variable). 
	 * Returns GameStatus if gridSize CROSS or NOUGHT symbols are found, false otherwise.
	 */
	
	
	private GameStatus checkForLineOfN(int startRow, int startCol, int incrementRow, int incrementCol)
	{
		SquareStatus firstSquareContent = getSquareStatusNoChecking(startRow, startCol);
		GameStatus ret;
		boolean lineOfN = (firstSquareContent != SquareStatus.EMPTY);
		for(int count=1; lineOfN && (count < gridSize);count++)
		{
			startRow += incrementRow;
			startCol += incrementCol;
			
			lineOfN= ( getSquareStatusNoChecking(startRow, startCol) == firstSquareContent);
			
		}
		if(!lineOfN)
			ret = GameStatus.STILLPLAYING;
		else if(firstSquareContent == SquareStatus.CROSS) 
			ret = GameStatus.CROSSWIN;
		else
			ret = GameStatus.NOUGHTWIN;

	
			
			return ret;
	}
	/**
	 * A slight edit of the checkForLineOfN which has a counter which counts the number of the same symbols. When it reaches 4 it
	 * stops the loop and returns either a cross or nought win depending on what was the first square content. Also if checks a
	 * coordinate outside the grid it will stop the loop. 
	 * @param startRow
	 * @param startCol
	 * @param incrementRow
	 * @param incrementCol
	 * @return
	 */
	private GameStatus lineConnectFour(int startRow, int startCol, int incrementRow, int incrementCol)
	{
		SquareStatus firstSquareContent = getSquareStatusNoChecking(startRow, startCol);
		GameStatus ret;
		int counter = 1;
		boolean lineOfN = (firstSquareContent != SquareStatus.EMPTY);
		for(int count=0;(count < 7);count++)
		{
			startRow += incrementRow;
			startCol += incrementCol;
			if(startRow < 0 || startRow >= maxRow || startCol < 0 || startCol >= maxCol)
			{
				break;
			}
			lineOfN= ( getSquareStatusNoChecking(startRow, startCol) == firstSquareContent);
			if(!lineOfN)
			{
				counter = 0;
				firstSquareContent = getSquareStatusNoChecking(startRow, startCol);
			}
			counter++;
			if(counter == 4)
			{
				break;
			}
		}
		ret = GameStatus.STILLPLAYING;
		if(counter == 4)
		{
			if(firstSquareContent == SquareStatus.CROSS) 
				ret = GameStatus.CROSSWIN;
			else if(firstSquareContent == SquareStatus.NOUGHT)
				ret = GameStatus.NOUGHTWIN;
		}
		
		return ret;
	}
	/**
	 * getStringForGrid builds a string representation of the grid, illustrates use of  StringBuffer
	 * to accumulate the string
	 * @return a string representing the grid (using simple ascii characters) and content of each square (empty, X, or O)
	 */
public String getStringForGrid ()
{
	StringBuffer ret = new StringBuffer();
	int limit =  gridSize;
	
	for(int row=0; row < maxRow; row++)
	{
		if(row > 0)
			{
			ret.append( "___");
			for (int col = 1; col < maxCol; col++)
				ret.append( "|___");
			ret.append("\n   ");
			for (int col = 1; col < maxCol; col++)
				ret.append("|   ");
			ret.append("\n");
			}
		for (int col = 0; col < maxCol; col++)
		{
			if(col > 0 )
				ret.append("|");
			try {
				switch(getSquareStatus(row, col))
				{
				case CROSS:
					ret.append(" X ");
					break;
				case NOUGHT:
					ret.append(" O ");
					break;
				case EMPTY:
					ret.append("   ");
					break;
				}
			}
			catch (outOfRangeError e) 
			{
				System.err.println("Row or col out of range - should not happen " + e.toString());
				e.printStackTrace();
				System.exit(1);
			}	
				
				
			}
		ret.append("\n");
		}
	return ret.toString();
	}


}
