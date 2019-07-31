package noughtsAndCrossesV3;

import ncErrors.outOfRangeError;
import noughtsAndCrossesV3.NCGridV3.GameStatus;
import noughtsAndCrossesV3.NCGridV3.SquareStatus;
import java.util.ArrayList;


public class ExpertPlayer extends GenericPlayer implements NCPlayer 
{
	int maxCol;
	int maxRow;
	public ExpertPlayer(NCGridV3 currentGrid)
	{
		super();
		maxCol = currentGrid.getGridColDimension();
		maxRow = currentGrid.getGridRowDimension();
	}


	/**
	 * In a game of Tic Tac Toe, the best you can hope to do is draw against a perfect player. Therefore, this player makes it's priority to block
	 * the opponent from getting any lines in as few moves as possible
	 */
	SquareStatus GetMySymbol(NCGridV3 currentGrid) // Check what symbol this player is and if it went first or second
	{
		SquareStatus mySymbol = NCGridV3.SquareStatus.EMPTY;
		if(currentGrid.getNumberOfOccupiedSquares() % 2 == 0 || currentGrid.getNumberOfOccupiedSquares() == 0)
		{
			mySymbol = NCGridV3.SquareStatus.CROSS;
		}
		else
		{
			mySymbol = NCGridV3.SquareStatus.NOUGHT;
		}
		return mySymbol;
	}
	/**
	 * This class is composed of an integer and a grid coordinate and is used in finding the squares that result in a player winning
	 * The grid coordinate gives the square to place it in and the integer gives it a rank which identifies if it is a row of noughts or crosses
	 * @author Administrator
	 *
	 * @param <GridCoordinate>
	 */
	public class winningSquares<GridCoordinate>
	{
		private int NoughtOrCross;
		private noughtsAndCrossesV3.GridCoordinate winningSquare;
		public winningSquares(int NoughtOrCross, noughtsAndCrossesV3.GridCoordinate winningSquare)
		{
			this.NoughtOrCross = NoughtOrCross;
			this.winningSquare = winningSquare;
		}
		public noughtsAndCrossesV3.GridCoordinate getWinningSquare()
		{
			return winningSquare;
		}
		public int checkForNoughtOrCross()
		{
			return NoughtOrCross;
		}
	}
	/**
	 * This player main plan is to block the other player from winning before going for a win itself. It starts by choosing one of the top corners and then following a plan
	 * based on which of the corners it takes to block as many of the lines as possible in as few moves as possible. The player also uses a function that checks for a line
	 * of three and will prioritise lines of its own symbol.
	 */
	
	@Override
	public GridCoordinate getNextMove(NCGridV3 currentGrid) 
	{

		SquareStatus mySymbol = getMySymbol();
		GridCoordinate theSquare = null;
		int plan;
		ArrayList<winningSquares> listOfSquares = new ArrayList<winningSquares>();
		try 
		{
			for(int i = 0; i < maxRow; i++)
			{
				if(checkLines(0,i,1,0,currentGrid) != 0) //Check Rows
				{
					for(int n = 0; n < maxRow; n++)
					{
						if(currentGrid.getSquareStatus(n,i) == NCGridV3.SquareStatus.EMPTY)
						{
							listOfSquares.add(new winningSquares(checkLines(0,i,1,0,currentGrid),new GridCoordinate(n,i)));
						}
					}
				}
				if(checkLines(i,0,0,1,currentGrid) != 0) //Check Columns
				{
					for(int n = 0; n < maxRow; n++)
					{
						if(currentGrid.getSquareStatus(i,n) == NCGridV3.SquareStatus.EMPTY)
						{
							listOfSquares.add(new winningSquares(checkLines(i,0,0,1,currentGrid),new GridCoordinate(i,n)));
						}
					}
				}
				
			}
			if(checkLines(0,0,1,1,currentGrid) != 0) // Check first diagonal
			{
				for(int n = 0; n < maxRow; n++)
				{
					if(currentGrid.getSquareStatus(n,n) == NCGridV3.SquareStatus.EMPTY)
					{
						listOfSquares.add(new winningSquares(checkLines(0,0,1,1,currentGrid),new GridCoordinate(n,n)));
					}
				}
			}
			if(checkLines(0,maxRow -1,1,-1,currentGrid) != 0) // Check second diagonal
			{
				for(int n = 0; n < maxRow; n++)
				{
					if(currentGrid.getSquareStatus(n,(maxRow-1) - n) == NCGridV3.SquareStatus.EMPTY)
					{
						listOfSquares.add(new winningSquares(checkLines(0,maxRow-1,1,-1,currentGrid),new GridCoordinate(n,(maxRow-1)-n)));
					}
				}
			}
			//Takes all the squares that would result in a winning move for a player
			if(listOfSquares.size() > 0)
			{
				int x;
				// This prioritizes any lines which have 3 of the player's symbol rather than 3 of the other player's symbol
				if(mySymbol == NCGridV3.SquareStatus.CROSS)
				{
					x = 1;
				}
				else
				{
					x = 2;
				}
				for(int i = 0; i < listOfSquares.size(); i++)
				{
					if(x == listOfSquares.get(i).checkForNoughtOrCross())
					{
						theSquare = listOfSquares.get(i).getWinningSquare();
						return theSquare;
					}
				}
				for(int i = 0; i < listOfSquares.size();)
				{
					theSquare = listOfSquares.get(i).getWinningSquare();
					return theSquare;
				}
				
			}
			// Checks which is the occupied square and returns which plan to use
			if(currentGrid.getNumberOfOccupiedSquares() == 1)
			{
				plan = checkFirstMove(maxRow, maxCol, currentGrid);
			}
			
			
			else
			{
				if(currentGrid.getSquareStatus(0,maxCol-1) == mySymbol) //Decides which plan to use base on which corner was taken
				{
					plan = 1;
				}
				else
				{
					plan = 2;
				}
			}
			if(plan == 1)
			{
				if(currentGrid.getSquareStatus(0,3) == NCGridV3.SquareStatus.EMPTY) // Top Right Corner
				{
					theSquare = new GridCoordinate(0,3);
					return theSquare;
				}
				
				if(currentGrid.getSquareStatus(3,0) == NCGridV3.SquareStatus.EMPTY) // Bottom left corner
				{
					theSquare = new GridCoordinate(3,0);
					return theSquare;
				}
				
				if(currentGrid.getSquareStatus(1,1) == NCGridV3.SquareStatus.EMPTY) //Top left middle
				{
					theSquare = new GridCoordinate(1,1);
					return theSquare;
				}
				if(currentGrid.getSquareStatus(2,2) == NCGridV3.SquareStatus.EMPTY) // Bottom right middle
				{
					theSquare = new GridCoordinate(2,2);
					return theSquare;
				}
				if(currentGrid.getSquareStatus(3,0) != mySymbol)
				{
					if(currentGrid.getSquareStatus(2,0) == NCGridV3.SquareStatus.EMPTY)
					{
						theSquare = new GridCoordinate(2,0);
						return theSquare;
					}
					for(int i = 1 ; i < 3; i++) 
					{
							if(currentGrid.getSquareStatus(3,i) == NCGridV3.SquareStatus.EMPTY)
							{
								theSquare = new GridCoordinate(3,i);
								return theSquare;
							}
					}
				}
				
				
			}
			if(plan == 2 && plan != 1)
			{
				if(currentGrid.getSquareStatus(0,0) == NCGridV3.SquareStatus.EMPTY) // Top left Corner
				{
					theSquare = new GridCoordinate(0,0);
					return theSquare;
				}
				if(currentGrid.getSquareStatus(3,3) == NCGridV3.SquareStatus.EMPTY) // Bottom right corner
				{
					theSquare = new GridCoordinate(3,3);
					return theSquare;
				}
				
				if(currentGrid.getSquareStatus(1,2) == NCGridV3.SquareStatus.EMPTY) //Top right middle
				{
					theSquare = new GridCoordinate(1,2);
					return theSquare;
				}
				if(currentGrid.getSquareStatus(2,1) == NCGridV3.SquareStatus.EMPTY) // Bottom left middle
				{
					theSquare = new GridCoordinate(2,1);
					return theSquare;
				}
				if(currentGrid.getSquareStatus(3,3) != mySymbol)
				{
					if(currentGrid.getSquareStatus(2,3) == NCGridV3.SquareStatus.EMPTY)
					{
						theSquare = new GridCoordinate(2,3);
						return theSquare;
					}
					for(int i = 2 ; i > 0; i--) 
					{
							if(currentGrid.getSquareStatus(3,i) == NCGridV3.SquareStatus.EMPTY)
							{
								theSquare = new GridCoordinate(3,i);
								return theSquare;
							}
					}
				}
				
			}
			for(int Row = 2; Row > 0; Row--) //Start filling in the centre squares
			{
				for (int Col = 2; Col > 0; Col--)
				{
					if(currentGrid.getSquareStatus(Row, Col) == NCGridV3.SquareStatus.EMPTY)
					{
						theSquare = new GridCoordinate(Row, Col);
						return theSquare;
					}
				}
			}
			
			if(currentGrid.getSquareStatus(3,0) == NCGridV3.SquareStatus.EMPTY) // fill in the bottom corners if not done already
			{
				theSquare = new GridCoordinate(3,0);
				return theSquare;
			}
			if(currentGrid.getSquareStatus(3,3) == NCGridV3.SquareStatus.EMPTY)
			{
				theSquare = new GridCoordinate(3,3);
				return theSquare;
			}
			
			if(checkCorners(0,0,0,3,currentGrid))
			{
				if(currentGrid.getSquareStatus(0,1) == NCGridV3.SquareStatus.EMPTY && currentGrid.getSquareStatus(0,2) == NCGridV3.SquareStatus.EMPTY)
				{
					theSquare = new GridCoordinate(0,1);
					return theSquare;
				}
			}
			if(checkCorners(0,0,3,0,currentGrid))
			{
				if(currentGrid.getSquareStatus(1,0) == NCGridV3.SquareStatus.EMPTY && currentGrid.getSquareStatus(0,2) == NCGridV3.SquareStatus.EMPTY)
				{
					theSquare = new GridCoordinate(1,0);
					return theSquare;
				}
			}
			if(checkCorners(3,0,3,3,currentGrid))
			{
				if(currentGrid.getSquareStatus(3,1) == NCGridV3.SquareStatus.EMPTY && currentGrid.getSquareStatus(3,2) == NCGridV3.SquareStatus.EMPTY)
				{
					theSquare = new GridCoordinate(3,1);
					return theSquare;
				}
			}
			if(checkCorners(0,3,3,3,currentGrid))
			{
				if(currentGrid.getSquareStatus(1,3) == NCGridV3.SquareStatus.EMPTY && currentGrid.getSquareStatus(2,3) == NCGridV3.SquareStatus.EMPTY)
				{
					theSquare = new GridCoordinate(1,3);
					return theSquare;
				}
			}
			for (int Col = 0; Col < currentGrid.getGridRowDimension(); Col++)	// This is just to fill in any empty spaces if it runs out of other moves to do
			{
				for (int Row = 0; Row < currentGrid.getGridColDimension(); Row++)
				{
					if(currentGrid.getSquareStatus(Row, Col) == NCGridV3.SquareStatus.EMPTY)
					{
						theSquare = new GridCoordinate(Row, Col);
						return theSquare;
					}
						
				}
			}
		}
		catch (outOfRangeError e) {
			e.printStackTrace();
		}
		return theSquare;
	}
	/**
	 * If the player goes second it will check if the other player has blocked one of the key squares for one of the plans and goes for the other plan
	 * @param maxRow the maximum number of rows, 4 in a 4x4 grid
	 * @param maxCol the maximum number of columns, 4 in a 4x4 grid
	 * @param currentGrid the current grid of this round, used to check if squares are empty or not
	 * @return returns the plan number
	 */
	private int checkFirstMove(int maxRow, int maxCol, NCGridV3 currentGrid)
	{
		try 
		{
			if(currentGrid.getSquareStatus(0, maxCol-1) != NCGridV3.SquareStatus.EMPTY || currentGrid.getSquareStatus(1,1) != NCGridV3.SquareStatus.EMPTY || currentGrid.getSquareStatus(2,2) != NCGridV3.SquareStatus.EMPTY ||currentGrid.getSquareStatus(maxRow-1, 0) != NCGridV3.SquareStatus.EMPTY )
			{
				return 2;
			}
			return 1;
		} catch (outOfRangeError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * A simple check if two corners have two of the same symbols and puts a symbol between them
	 * @param Row1 the first row coordinate
	 * @param Col1 the first column coordinate
	 * @param Row2 the second row coordinate
	 * @param Col2 the second column coordinate
	 * @param currentGrid the current grid, used for check if a square is empty usually
	 * @return
	 */
	private boolean checkCorners(int Row1, int Col1, int Row2, int Col2, NCGridV3 currentGrid)
	{
		
		try 
		{
			SquareStatus firstSquareContent = currentGrid.getSquareStatus(Row1, Col1);
			{
				if(firstSquareContent == currentGrid.getSquareStatus(Row2, Col2))
				{
					return true;
				}
			}
			
		} 
		catch (outOfRangeError e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * Checks all the rows, columns and diagonals in the game and counts the number of symbols in each of them. If there are three of the same symbols
	 * are found in a line it will return a positive integer, 1 for 3 crosses and 2 for 3 noughts.
	 */
	
	private int checkLines(int startRow, int startCol, int incrementRow, int incrementCol, NCGridV3 currentGrid)
	{
		int crossCounter = 0;
		int noughtCounter = 0;
		int counters = 0;
		try
		{
			for(int count=0; count < 4; count++)
			{
				SquareStatus SquareContent = currentGrid.getSquareStatus(startRow, startCol);
				if(SquareContent == SquareStatus.CROSS)
				{
					crossCounter++;
				}
				if(SquareContent == SquareStatus.NOUGHT)
				{
					noughtCounter++;
				}
				startRow += incrementRow;
				startCol += incrementCol;
				if(noughtCounter == 3)
				{
					counters = 2;
					break;
				}
				if(crossCounter == 3)
				{
					counters = 1;
					break;
				}
			}
		}
		catch(outOfRangeError e)
		{
			e.printStackTrace(); //Should not happen
		}
		return counters;
	}
}
