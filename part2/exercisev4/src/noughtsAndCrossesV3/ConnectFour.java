package noughtsAndCrossesV3;

import java.util.Scanner;

import ncErrors.occupiedSquare;
import ncErrors.outOfRangeError;
import noughtsAndCrossesV3.NCGridV3.GameStatus;
import noughtsAndCrossesV3.NCGridV3.SquareStatus;

public class ConnectFour {
	public static void main(String[] args) {
		NCGridV3 theGrid = new NCGridV3(6,7);
		GameRunnerV3 theGame = new GameRunnerV3();
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to Connect Four\n\n");
		int N = 1;
		NCPlayer p1 = new HumanPlayer(sc, theGame);
		NCPlayer p2 = new RandomComputerPlayer();
		if(args.length > 0)
		{
			N = Integer.parseInt(args[0]);
			for(int x = 1; x < args.length; x++)
			{
				if(args[x].equals("HumanPlayer"))
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
				else if(args[x].equals("ConnectFourPlayer"))
				{
					if(x == 1)
					{
						p1 = new ConnectFourPlayer();
						continue;
					}
					else
					{
						p2 = new ConnectFourPlayer();
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
		if(args.length == 0)
		{
			p1 = new HumanPlayer(sc, theGame);
			p2 = new RandomComputerPlayer();
			p1.setMySymbol(SquareStatus.CROSS);
			p2.setMySymbol(SquareStatus.NOUGHT);
			System.out.println("How many games do you want to play?\n");
			Scanner n = new Scanner(System.in);
			N = n.nextInt();
		}
		NCPlayer nextToPlay = p1;
		p1.setMySymbol(SquareStatus.CROSS);
		p2.setMySymbol(SquareStatus.NOUGHT);
		int p1wins = 0;
		int p2wins = 0;
		for(int i = 0; i < N; i++)
		{
			theGrid = new NCGridV3(6,7);
			while(theGrid.getGameStatusConnect4() == NCGridV3.GameStatus.STILLPLAYING)
			{
				GridCoordinate nextMove = nextToPlay.getNextMove(theGrid);
				try
				{
					theGrid.setSquareConnect4(nextMove.getCol(), nextToPlay.getMySymbol());
				} 
				catch (outOfRangeError e) {
					e.printStackTrace();
				}
				
				// take turns 
				if(nextToPlay == p1)
					nextToPlay = p2;
				else
					nextToPlay = p1;
			}
		
			//theGame.displayGrid(theGrid);
		
			switch(theGrid.getGameStatusConnect4())
			{
			case  CROSSWIN:
			{
				//System.out.println("Cross wins\n");
				p1wins++;
				break;
			}
			case  NOUGHTWIN:
			{
				//System.out.println("a win for Noughts\n");
				p2wins++;
				break;
			}
			case DRAW: //Doesn't happen, but just in case
			{
				System.out.println("Draw\n");
				break;
			}
		}
		}
		System.out.println("Cross wins: " + p1wins);
		System.out.println("Nought wins: " + p2wins);
	}
	public  void displayGrid (NCGridV3 theGrid)
	{
		System.out.println(theGrid.getStringForGrid());
	}

}
