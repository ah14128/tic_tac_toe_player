package noughtsAndCrossesV3;


/**
 * very simple class to handle co-ordinate pairs, (row, column)
 * Provides two methods, returning column or row as required.
 * No error handling - e.g. checking values are in range - this is handled by NCGridV3
 *
 */
public class GridCoordinate  
{
	private int row;
	private int column;

	/**
	 * @param theRow row value for this co-ordinate
	 * @param theCol  column value for this co-ordinate
	 */
	public GridCoordinate(int theRow, int theCol)  
	{

		row = theRow;
		column = theCol;

	}

	/**
	 * Access method for row co-ordinate 
	 * @return the row co-ordinate
	 */
	public int getRow()
	{
		return row;
	}
	/**
	 * Access method for column co-ordinate 
	 * @return the column co-ordinate
	 */
	public int getCol()
	{
		return column;
	}
}
