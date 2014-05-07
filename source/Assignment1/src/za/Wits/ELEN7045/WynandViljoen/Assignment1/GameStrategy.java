package za.Wits.ELEN7045.WynandViljoen.Assignment1;

import java.util.ArrayList;


public class GameStrategy extends AbstractGameStrategy
{
	private int maxFallingObjects; // Sets number of falling objects allowed on the screen at once
	private int FallingObjectsDodged; // Counts falling objects dodged
	private int tempScore; 
	private int winner; // In a 2 player game this variable will say who won
	
	public GameStrategy(int[][] playerControls, int gameDifficulty, int numberOfPlayers)
	{
		super(playerControls, gameDifficulty, numberOfPlayers);
		winner = 0;
	}
	
	@Override
	public void reset(ArrayList<IPlayer> myPlayer, ArrayList<IPhysicalObject> myRainList)
	{
		super.reset(myPlayer, myRainList);
		FallingObjectsDodged = 0; // reset score
		winner = 0; // reset winner in case of a 2 player game
		maxFallingObjects = 25 - this.getDifficulty();
	}

	
	// This method will check if the current game is still valid or if someone was hit
	public int Update(ArrayList<IPlayer> myPlayer, ArrayList<IPhysicalObject> myRainList)
	{
		while (myRainList.size() < maxFallingObjects)
		{
			 myRainList.add(new Rain());
		}
	        
		int rainImgHeight = 0;
		int rainImgWidth = 0;
		boolean player1Hit = false;
		boolean player2Hit = false;
		
	    for(int i = 0; i < myRainList.size(); i++)
	    {
	    	rainImgWidth = myRainList.get(i).getImgDetails()[0];
	    	rainImgHeight = myRainList.get(i).getImgDetails()[1];   
	        
	        for(int j = 0; j < myPlayer.size(); j++)
		    {
	        	if (myRainList.get(i).getPos()[1] + rainImgHeight >  GameFramework.frameHeight - myPlayer.get(j).getImgDetails()[1])			
			        if ( ((myRainList.get(i).getPos()[0] < myPlayer.get(j).getPos()[0]) && (myRainList.get(i).getPos()[0] + rainImgWidth >= myPlayer.get(j).getPos()[0])) 
			        		|| ((myRainList.get(i).getPos()[0] >= myPlayer.get(j).getPos()[0]) && (myRainList.get(i).getPos()[0] < myPlayer.get(j).getPos()[0] + myPlayer.get(j).getImgDetails()[0]))
			        		|| ((myRainList.get(i).getPos()[0] > myPlayer.get(j).getPos()[0]) && (myRainList.get(i).getPos()[0] + rainImgWidth < myPlayer.get(j).getPos()[0] + myPlayer.get(j).getImgDetails()[0])))
			        {
			        	// This code will run if the raindrop image touches either of the player's image
			        	
			        	tempScore = FallingObjectsDodged;
			        	if (myPlayer.size() > 1) // If it is a 2 player game
			        	{
			        		if (j == 0) player1Hit = true;
			        		else if (j == 1) player2Hit = true;
			        		
			        		if (player1Hit && player2Hit) winner = 3; // If both players are hit, it's a draw.
			        		else if (player1Hit && !player2Hit) winner = 2;
			        		else if (!player1Hit && player2Hit) winner = 1;
			        	}
			        	else winner = -1; // if winner = -1, then it was a 1-player game
			        }
		    }
	        
	        // Remove a raindrop from the array when it goes of the screen
	        if(myRainList.get(i).getPos()[1] > GameFramework.frameHeight)
	        {
	            myRainList.remove(i);
	            FallingObjectsDodged += 1;
	        }
	
	    }
	    
	    if (winner == 0)
	    return FallingObjectsDodged; // Game not over yet, return current score
	    
	    else 
	    {
	    	GameFramework.gameState = GameFramework.GameState.GAMEOVER;
	    	return tempScore;
	    }
	}
	
	// This method will return a game over message to the Game object
	public String gameOver()
	{
		if (winner == 1) return "The winner is Player 1!"; 
		else if (winner == 2) return "The winner is Player 2!"; 
		else if (winner == 3) return "It is a draw!";
		else return "";
	}
}
