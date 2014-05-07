package za.Wits.ELEN7045.WynandViljoen.Assignment1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Game implements IGame
{
	private ArrayList<IPlayer> myPlayer;
	private ArrayList<IPhysicalObject> myRainList;
	private Font font;
	private int[] highestScore;
	private int difficultyLevel; // 0 = hardcore, 1 = medium, 2 = easy
	private BufferedImage gameBackground;
	private AbstractGameStrategy myGameStrategy;
	private int FallingObjectsDodged;
	
	public Game(AbstractGameStrategy myGameStrategy)
	{
		initialiseGame(myGameStrategy);
		loadGameContent();
		resetGame();	
	}
	
	private void initialiseGame(AbstractGameStrategy myGameStrategy)
	{
		myPlayer = new ArrayList<IPlayer>();
		myRainList = new ArrayList<IPhysicalObject>();
		this.myGameStrategy =  myGameStrategy;
		highestScore = new int[3];
		highestScore[0] = 0; // highestScore[0] means hardcore.
		highestScore[1] = 0; // highestScore[1] means medium.
		highestScore[2] = 0; // highestScore[2] means easy.
	}
	
	private void loadGameContent()
	{
		font = new Font("monospaced", Font.BOLD, 18);
		 try
	        {
	            URL getgameBackground = this.getClass().getResource("/Images/background2.jpg");
	            gameBackground = ImageIO.read(getgameBackground);
	        }
	        catch (IOException ex) {ex.printStackTrace();}
	}
	
	public void resetGame()
	{
		difficultyLevel = myGameStrategy.getDifficulty() / 5 - 1; 
		FallingObjectsDodged = 0;
		myGameStrategy.reset(myPlayer, myRainList);
	}
	
	public void UpdateGame()
	{
		for(int i = 0; i < myPlayer.size(); i++)
			myPlayer.get(i).UpdatePlayer();
		
		for(int i = 0; i < myRainList.size(); i++)
			myRainList.get(i).Update();
		
		FallingObjectsDodged = myGameStrategy.Update(myPlayer, myRainList);
		
		if (FallingObjectsDodged > highestScore[difficultyLevel])
        	highestScore[difficultyLevel] = FallingObjectsDodged;
	}
	
	public void Draw(Graphics2D g2d)
	{
		g2d.setFont(font);
	    g2d.setColor(Color.white);
		g2d.drawImage(gameBackground, 0, 0, GameFramework.frameWidth, GameFramework.frameHeight, null);
		g2d.drawString("Dodged: " + FallingObjectsDodged, 10, 21);
		g2d.drawString("Score to beat: " + highestScore[difficultyLevel], 10, 36);
		
		for(int i = 0; i < myPlayer.size(); i++)
			myPlayer.get(i).Draw(g2d); // draw(s) all player(s) to the screen
		
		for(int i = 0; i < myRainList.size(); i++)
			myRainList.get(i).Draw(g2d); //draws all IPhysicalObjects to the screen
	}
	
	public void DrawGameOver(Graphics2D g2d)
	{
		Draw(g2d);
		
		g2d.setColor(Color.yellow);
		g2d.drawString(myGameStrategy.gameOver(), GameFramework.frameWidth / 2 - 80, GameFramework.frameHeight / 2 - 80);
		g2d.drawString("GAME OVER! Score: " + FallingObjectsDodged, GameFramework.frameWidth / 2 - 80, GameFramework.frameHeight / 2 - 50);
		g2d.drawString("Press enter to restart", GameFramework.frameWidth / 2 - 80, GameFramework.frameHeight / 2 - 20);
		g2d.drawString("HIGHSCORE LIST", GameFramework.frameWidth / 2 - 80, GameFramework.frameHeight / 2 + 20);
		g2d.drawString("Easy - " + highestScore[2], GameFramework.frameWidth / 2 - 80, GameFramework.frameHeight / 2 + 40);
		g2d.drawString("Medium - " + highestScore[1], GameFramework.frameWidth / 2 - 80, GameFramework.frameHeight / 2 + 60);
		g2d.drawString("HARDCORE! - " + highestScore[0], GameFramework.frameWidth / 2 - 80, GameFramework.frameHeight / 2 + 80);
	}
}
