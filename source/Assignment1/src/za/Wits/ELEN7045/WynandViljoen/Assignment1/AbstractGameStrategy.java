package za.Wits.ELEN7045.WynandViljoen.Assignment1;

import java.util.ArrayList;


public abstract class AbstractGameStrategy 
{
	private int[][] playerControls;
	private int gameDifficulty;
	private int numberOfPlayers;
	
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////// START OF GETTERS AND SETTERS LIST  ////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
	public void setDifficulty(int gameDifficulty) { this.gameDifficulty = gameDifficulty;}
	public int getDifficulty() { return gameDifficulty;}
	public void setNumberOfPlayers(int numberOfPlayers) { this.numberOfPlayers = numberOfPlayers;}
	
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////// END OF GETTERS AND SETTERS LIST ///////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public AbstractGameStrategy(int[][] playerControls, int gameDifficulty, int numberOfPlayers)
	{
		this.playerControls = playerControls;
		this.gameDifficulty = gameDifficulty;
		this.numberOfPlayers = numberOfPlayers;
	}
	
	public void reset(ArrayList<IPlayer> myPlayer, ArrayList<IPhysicalObject> myRainList)
	{
		{	
			myPlayer.clear(); // clears all player objects
			myRainList.clear(); // clears all rain objects
			
			myPlayer.add(new StickMan(playerControls[0], 1));
			if (numberOfPlayers == 2)
				myPlayer.add(new StickMan(playerControls[1], 2));
		}
	}

	// The player keys can be change anytime in the game, this method enables us to store it outside of the constructor
	public void setKeys(int[] playerOne, int[] playerTwo) 
	{
		playerControls[0] = playerOne;
		playerControls[1] = playerTwo;
	}
	
	// This method will check if the current game is still valid or if someone was hit - will be implemented by Subclass
	public abstract int Update(ArrayList<IPlayer> myPlayer, ArrayList<IPhysicalObject> myRainList);
	
	// This method will return a game over message to the Game object - will be implemented by Subclass
	public abstract String gameOver();

}
