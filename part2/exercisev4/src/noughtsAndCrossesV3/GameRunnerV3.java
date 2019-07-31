package noughtsAndCrossesV3;

import java.util.Scanner;

import ncErrors.occupiedSquare;
import ncErrors.outOfRangeError;
import noughtsAndCrossesV3.NCGridV3.GameStatus;
import noughtsAndCrossesV3.NCGridV3.SquareStatus;

/**
 * 
 * GameRunnerV3 provides management of the noughts and crosses game for a variable sized grid (specified by int gridSize).
 * It is based on code provided in previous labs (GameRunner, GameRunnerV2) .
 * Note that all printing to the terminal (apart from error messages) is provided here  
 *
 */

public class GameRunnerV3 
{
	 static final String INITIAL_INSTRUCTIONS = "Welcome to Noughts and Crosses. First player is crosses, second player is noughts.\n";
	 static final String EACHROUND_INSTRUCTIONS= "\n enter row from 0 - %d and col from 0 - %d separated by a space (0 0 = top left)\n";
	 static final int gridSize = 4;
	 public enum players {HUMANPLAYER,SIMPLECOMPLAYER,RANDOMCOMPLAYER,EXPERTPLAYER}

	 public static void main(String[] args) 
	 {
		NCGridV3 theGrid = new NCGridV3(gridSize, gridSize);
		GameRunnerV3 theGame = new GameRunnerV3();
		Scanner sc = new Scanner(System.in);		// only needed if we include human players 
		int N = 1;	
		Scanner n = null;
		NCPlayer p1;
		NCPlayer p2;
		p1 = new ExpertPlayer(theGrid);
		p2 = new HumanPlayer(sc, theGame);
		if(args.length > 0)
		{
			N = Integer.parseInt(args[0]);
			for(int x = 1; x < args.length; x++)
			{
				if(args[x].equals("ExpertPlayer"))
				{
					if(x == 1)
					{
						p1 = new ExpertPlayer(theGrid);
						continue;
					}
					else
					{
						p2 = new ExpertPlayer(theGrid);
						continue;
						
					}
				}
				else if(args[x].equals("HumanPlayer"))
				{
					if(x == 1)
					{
						p1 = new HumanPlayer(sc, theGame);
						continue;
					}
					else
					{
						p2 = new HumanPlayer(sc, theGame);
						continue;
					}
				}
				else if(args[x].equals("SimpleComputerPlayer"))
				{
					if(x == 1)
					{
						p1 = new SimpleComputerPlayer();
						continue;
					}
					else
					{
						p2 = new SimpleComputerPlayer();
						continue;
					}
				}
				else if(args[x].equals("RandomComputerPlayer"))
				{
					if(x == 1)
					{
						p1 = new RandomComputerPlayer();
						continue;
					}
					else
					{
						p2 = new RandomComputerPlayer();
						continue;
					}
				}
			}
		}
		else
		{
			System.out.println("How many games do you want to play?"); // If no arguments are given
			n = new Scanner(System.in);
			p1 = new HumanPlayer(sc, theGame);
			p2 = new ExpertPlayer(theGrid);
			N = n.nextInt(); 
		}
		p1.setMySymbol(SquareStatus.CROSS);
		p2.setMySymbol(SquareStatus.NOUGHT);
		int p1wins = 0;
		int p2wins = 0;
		int numberOfDraws = 0;
		System.out.println(INITIAL_INSTRUCTIONS);
		NCPlayer nextToPlay = p1; // arbitrary decision that p1 goes first
		for(int i = 0; i < N; i++)
		{	
			if(i % 2 == 0 || i == 0)
			{
				nextToPlay = p1;
			}
			else
			{
				nextToPlay = p2;
			}
			theGrid = new NCGridV3(gridSize, gridSize); // reset grid
			while (theGrid.getGameStatus() == GameStatus.STILLPLAYING)
			{
				GridCoordinate nextMove = nextToPlay.getNextMove(theGrid) ;
				try
				{
					theGrid.setSquareStatus(nextMove.getRow(), nextMove.getCol(), nextToPlay.getMySymbol());
				} 
				catch (outOfRangeError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				catch (occupiedSquare e)
				{
					System.err.println("Occupied square\n");
					e.printStackTrace();
					break;
				}
	
				// take turns 
				if(nextToPlay == p1)
					nextToPlay = p2;
				else
					nextToPlay = p1;
			}
			//theGame.displayGrid(theGrid);
	
			switch(theGrid.getGameStatus())
			{
			case  CROSSWIN:
				//System.out.println("Cross wins\n");
				p1wins++;
				continue;
			case  NOUGHTWIN:
				//System.out.println("a win for Noughts\n");
				p2wins++;
				continue;
			case  DRAW:
				//System.out.println("honours even\n");
				numberOfDraws++;
				continue;
			}
		}
		System.out.println("(p1) Cross wins: " + p1wins);
		System.out.println("(p2) Nought wins: " + p2wins);
		System.out.println("Number of Draws: " + numberOfDraws);
	}
	/**
	 *  prints the message specified in  EACHROUND_INSTRUCTIONS. 
	 * @param rDim the number of rows in the grid
	 * @param cDim the number of columns in the grid
	 */ 
	public  void displayInstructions (int rDim, int cDim)
	{
		System.out.printf(EACHROUND_INSTRUCTIONS, rDim-1, cDim-1 );
	}
	
	/**
	 * provides the facility for displaying a grid on the terminal
	 * @param theGrid the grid to be displayed
	 */
	public  void displayGrid (NCGridV3 theGrid)
	{
		System.out.println(theGrid.getStringForGrid());
	}

}
