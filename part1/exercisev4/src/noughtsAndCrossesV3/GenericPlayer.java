package noughtsAndCrossesV3;

/**
 * The class GenericPlayer illustrates the use of interfaces and abstract classes.
 * The interface NCPlayer lists the methods that a noughts and crosses player
 * must implement. The class  GenericPlayer implements the two methods
 *   setMySymbol
 *   getMySymbol
 * but requires the programmer to implement the method getNextMove 
 *
 */
public abstract class GenericPlayer implements NCPlayer
{
	private NCGridV3.SquareStatus theSymbol;   // used to record this player's symbol ( O or X )

	public GenericPlayer()
	{
		theSymbol = NCGridV3.SquareStatus.EMPTY;   // initialise so we can detect error if it is not set O or X 
	}
	
	/**
	 * allows caller (such as GameManager) to set the symbol for this player to NOUGHT or CROSS
	 */
	public void setMySymbol(NCGridV3.SquareStatus sym)
	{
		theSymbol = sym;
	}

	/**
	 * reports current setting of the symbol for this player (should be NOUGHT or CROSS)
	 */
	public NCGridV3.SquareStatus getMySymbol()
	{
		if (theSymbol == NCGridV3.SquareStatus.EMPTY)
			System.err.println("GenericPlayer : symbol has not been set ");   //could throw an error here ?

		return theSymbol;
	}

	abstract public GridCoordinate getNextMove(NCGridV3 currentGrid) ;


}
