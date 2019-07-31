package noughtsAndCrossesV3;

import noughtsAndCrossesV3.NCGridV3.SquareStatus;

/**
 * an interface specifies the methods a class must implement.
 * Here we require methods to set/get the symbol and find the next move
 */

public interface NCPlayer 
{	
	public SquareStatus getMySymbol();
	public void setMySymbol(SquareStatus theSymbol); 
	public GridCoordinate getNextMove(NCGridV3 currentGrid);
}
