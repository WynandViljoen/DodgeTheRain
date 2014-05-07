package za.Wits.ELEN7045.WynandViljoen.Assignment1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class GameSettings implements IGameSettings
{
	private Font font;
	private int gameDifficulty;
	private int numberOfPlayers;
	public int[][] playerControls;
	private int keyCode;
    private String strDifficulty; // String name assigned to game dificulty eg. Easy
    private BufferedImage gameSettingsBackground;
    
    private AbstractGameStrategy myGameStrategy; // GameStrategy object reference variable

    private enum OptionsState{DIFFICULTY, PLAYERS, KEYS_1_LEFT, KEYS_1_RIGHT, KEYS_2_LEFT, KEYS_2_RIGHT, COMPLETE}
    private OptionsState optionsState;
	
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////// START OF GETTERS AND SETTERS LIST  ////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
	public AbstractGameStrategy getMyGameStrategy() { return myGameStrategy;}
	public int getGameDifficulty() { return gameDifficulty;}
	public int getNumberOfPlayers() { return numberOfPlayers;}
	public int[][] getPlayerControls() { return playerControls;}
	public void setKeyPressed(int keyCode) { this.keyCode = keyCode;}
	
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////// END OF GETTERS AND SETTERS LIST ///////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
	public GameSettings()
	{
		initialiseSettings();
		loadSettingsContent();
		
		// Creates a concrete GameStrategy object with an instance variable of type AbstractGameStrategy. 
		myGameStrategy = new GameStrategy(playerControls,gameDifficulty,numberOfPlayers);
		optionsState = OptionsState.DIFFICULTY;
	}
	
	private void initialiseSettings()
	{
		font = new Font("monospaced", Font.BOLD, 20);
		numberOfPlayers = 1; // Set default number of players to 1
		gameDifficulty = 10;
		
		// Set default keyboard keys for players
		playerControls = new int[2][2];
		playerControls[0][0] = KeyEvent.VK_A;
		playerControls[0][1] = KeyEvent.VK_D;
		playerControls[1][0] = KeyEvent.VK_J;
		playerControls[1][1] = KeyEvent.VK_L;
	}
	
	// Set background image
	private void loadSettingsContent()
	{
        try
        {
            URL getGameSettingsBackground = this.getClass().getResource("/Images/background3.jpg");
            gameSettingsBackground = ImageIO.read(getGameSettingsBackground);
        }
        catch (IOException ex) { ex.printStackTrace();}
	}

	public void Draw(Graphics2D g2d) 
	{
 		g2d.setColor(Color.black);
 		g2d.setFont(font);
 		g2d.drawImage(gameSettingsBackground, 0, 0, GameFramework.frameWidth, GameFramework.frameHeight, null);
 		g2d.drawString("This is the options window!", GameFramework.frameWidth / 2 - 300, GameFramework.frameHeight / 2 - 70);
 		
 		switch (optionsState)
	     {
	     	case DIFFICULTY: // Get the difficulty settings from the user
	     		g2d.setColor(Color.red);
	     		g2d.drawString("Select difficulty: (1 (easy) ...... 3 (hardcore)) ", GameFramework.frameWidth / 2 - 300, GameFramework.frameHeight / 2 - 40);
	     		validateKeyPress();
	     	break;

	     	case PLAYERS: // Get the number of players from the user
	     		g2d.setColor(Color.black);
	     		g2d.drawString("Difficulty selected: " + strDifficulty, GameFramework.frameWidth / 2 - 300, GameFramework.frameHeight / 2 - 40);
	     		g2d.setColor(Color.red);
	     		g2d.drawString("Select number of players: (1 or 2)", GameFramework.frameWidth / 2 - 300, GameFramework.frameHeight / 2 - 10);
	     		validateKeyPress();
	     	break;
	     
	     	case KEYS_1_LEFT: // Get the keyboard key that player 1 will use to move left
	     		g2d.setColor(Color.black);
	     		g2d.drawString("Difficulty selected: " + strDifficulty, GameFramework.frameWidth / 2 - 300, GameFramework.frameHeight / 2 - 40);	
	     		g2d.drawString("Select number of players: " + numberOfPlayers, GameFramework.frameWidth / 2 - 300, GameFramework.frameHeight / 2 - 10);
	     		g2d.setColor(Color.red);
	     		g2d.drawString("Press key to make Player 1 move to the left: ", 0, GameFramework.frameHeight / 2 + 20);
	     		validateKeyPress();
	     	break;
	     
	     	case KEYS_1_RIGHT: // Get the keyboard key that player 1 will use to move right
	     		g2d.setColor(Color.black);
	     		g2d.drawString("Difficulty selected: " + strDifficulty, GameFramework.frameWidth / 2 - 300, GameFramework.frameHeight / 2 - 40);	
	     		g2d.drawString("Select number of players: " + numberOfPlayers, GameFramework.frameWidth / 2 - 300, GameFramework.frameHeight / 2 - 10);
	     		g2d.drawString("Player 1 moves left with key: " + (char)playerControls[0][0], GameFramework.frameWidth / 2 - 300, GameFramework.frameHeight / 2 + 20);
	     		g2d.setColor(Color.red);
	     		g2d.drawString("Press key to make Player 1 move to the right: ", 0, GameFramework.frameHeight / 2 + 50);
	     		validateKeyPress();
	     	break;
	     	
	     	case KEYS_2_LEFT: // Get the keyboard key that player 2 will use to move left
	     		g2d.setColor(Color.black);
	     		g2d.drawString("Difficulty selected: " + strDifficulty, GameFramework.frameWidth / 2 - 300, GameFramework.frameHeight / 2 - 40);	
	     		g2d.drawString("Select number of players: " + numberOfPlayers, GameFramework.frameWidth / 2 - 300, GameFramework.frameHeight / 2 - 10);
	     		g2d.drawString("Player 1 moves with : " + (char)playerControls[0][0] + " and " + (char)playerControls[0][1], GameFramework.frameWidth / 2 - 300, GameFramework.frameHeight / 2 + 20);
	     		g2d.setColor(Color.red);
	     		g2d.drawString("Press key to make Player 2 move to the left: ", 0, GameFramework.frameHeight / 2 + 50);
	     		validateKeyPress();   	
	     	break;
	     	
	     	case KEYS_2_RIGHT: // Get the keyboard key that player 2 will use to move right
	     		g2d.setColor(Color.black);
	     		g2d.drawString("Difficulty selected: " + strDifficulty, GameFramework.frameWidth / 2 - 300, GameFramework.frameHeight / 2 - 40);	
	     		g2d.drawString("Select number of players: " + numberOfPlayers, GameFramework.frameWidth / 2 - 300, GameFramework.frameHeight / 2 - 10);
	     		g2d.drawString("Player 1 moves with : " + (char)playerControls[0][0] + " and " + (char)playerControls[0][1], GameFramework.frameWidth / 2 - 300, GameFramework.frameHeight / 2 + 20);
	     		g2d.drawString("Player 2 moves left with key: " + (char)playerControls[1][0], GameFramework.frameWidth / 2 - 300, GameFramework.frameHeight / 2 + 50);
	     		g2d.setColor(Color.red);
	     		g2d.drawString("Press key to make Player 2 move to the right: ", 0, GameFramework.frameHeight / 2 + 80);
	     		validateKeyPress();
	     	break;
	     	    	
	     	case COMPLETE: // Display all selected settings to the user
	     		g2d.setColor(Color.red);
	     		g2d.drawString("All configurations done!", GameFramework.frameWidth / 2 - 300, GameFramework.frameHeight / 2 - 100);	
	     		g2d.setColor(Color.black);
	     		g2d.drawString("Difficulty selected: " + strDifficulty, GameFramework.frameWidth / 2 - 300, GameFramework.frameHeight / 2 - 40);	
	     		g2d.drawString("Select number of players: " + numberOfPlayers, GameFramework.frameWidth / 2 - 300, GameFramework.frameHeight / 2 - 10);
	     		g2d.drawString("Player 1 moves with : " + (char)playerControls[0][0] + " and " + (char)playerControls[0][1], GameFramework.frameWidth / 2 - 300, GameFramework.frameHeight / 2 + 20);
	     		
	     		if (getNumberOfPlayers() == 2)
	     		g2d.drawString("Player 2 moves with : " + (char)playerControls[1][0] + " and " + (char)playerControls[1][1], GameFramework.frameWidth / 2 - 300, GameFramework.frameHeight / 2 + 50);
	     		
	     		g2d.setColor(Color.red);
	     		g2d.drawString("Please start a new game or press Enter to modify settings ", GameFramework.frameWidth / 2 - 350, GameFramework.frameHeight / 2 + 80);
	     		validateKeyPress();
	     	break;
	      
	     	default:	
	     	break;
	     }	
	}
	
	// This function validates the user input for the Settings window
	private void validateKeyPress()
	{
		switch (optionsState)
	     {
	     	case DIFFICULTY:
	     		if (keyCode == KeyEvent.VK_1)
	     		{
	     			gameDifficulty = 15;
	     			strDifficulty = "Easy";
	     			keyCode = 0;
	     			optionsState = OptionsState.PLAYERS;
	     		}
	     		else if (keyCode == KeyEvent.VK_2)
	     		{
	     			gameDifficulty = 10;
	     			strDifficulty = "Medium";
	     			keyCode = 0;
	     			optionsState = OptionsState.PLAYERS;
	     		}
	     		else if (keyCode == KeyEvent.VK_3)
	     		{
	     			gameDifficulty = 5;
	     			strDifficulty = "HardCore!";
	     			keyCode = 0;
	     			optionsState = OptionsState.PLAYERS;
	     		}	
	     	break;
	     	
	     	case PLAYERS: // Get the number of players from user
	    		myGameStrategy.setDifficulty(gameDifficulty);
	    		
	     		if (keyCode == KeyEvent.VK_1) // Check if '1' was pressed
	     		{	
	     			numberOfPlayers = 1;
	     			keyCode = 0;
	     			optionsState = OptionsState.KEYS_1_LEFT;	
	     		}
	     		else if (keyCode == KeyEvent.VK_2) // Check if '2' was pressed
	     		{	
	     			numberOfPlayers = 2;
	     			keyCode = 0;
	     			optionsState = OptionsState.KEYS_1_LEFT;	
	     		}     		
	     	break;
	     	
	     	case KEYS_1_LEFT: // Get the keyboard key that player 1 will use to move left
	     		myGameStrategy.setNumberOfPlayers(numberOfPlayers);
	     		
	     		if (keyCode != 0)
	     		{
	     			playerControls[0][0] = keyCode;
	     			optionsState = OptionsState.KEYS_1_RIGHT;
	     			keyCode = 0;
	     		}  		
	     	break;
	     	
	     	case KEYS_1_RIGHT: // Get the keyboard key that player 1 will use to move right
	     		if ((keyCode != 0) && (keyCode != playerControls[0][0]))
	     		{
	     			playerControls[0][1] = keyCode;
	     			myGameStrategy.setKeys(playerControls[0], playerControls[1]);
	     			keyCode = 0;
	     			
	     			if (numberOfPlayers == 1)
	     				optionsState = OptionsState.COMPLETE;
	     			else 
	     				optionsState = OptionsState.KEYS_2_LEFT;	
	     		}
	     	break; 
	     	
	     	case KEYS_2_LEFT: // Get the keyboard key that player 2 will use to move left
	     		if ((keyCode != 0) && (keyCode != playerControls[0][0]) && (keyCode != playerControls[0][1]))
	     		{
	     			playerControls[1][0] = keyCode;
	     			keyCode = 0;
	     			optionsState = OptionsState.KEYS_2_RIGHT;	
	     		}
	     	break; 
	     	
	     	case KEYS_2_RIGHT: // Get the keyboard key that player 2 will use to move right
	     		if ((keyCode != 0) && (keyCode != playerControls[0][0]) && (keyCode != playerControls[0][1]) && (keyCode != playerControls[1][0]))
	     		{
	     			playerControls[1][1] = keyCode;
	     			keyCode = 0;
	     			myGameStrategy.setKeys(playerControls[0], playerControls[1]);
	     			optionsState = OptionsState.COMPLETE;
	     		}
	     	break;
	     	
	    	case COMPLETE:
	    		if (keyCode == KeyEvent.VK_ENTER)
	     		{
	    			optionsState = OptionsState.DIFFICULTY;	
	     		}
	     	break;  
	     	
	     	default:	
	     	break;
	     }	
	}
}
