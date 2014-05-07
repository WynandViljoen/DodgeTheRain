package za.Wits.ELEN7045.WynandViljoen.Assignment1;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import javax.imageio.ImageIO;


public class StickMan implements IPlayer 
{
	private int[] Pos;
	private Random random;
	private BufferedImage playerImage;
	private int[] imgDetails;//imgDetails[0] stores image width, imgDetails[1] stores image height
	private int movementSpeed;
	private int goLeftKey;
	private int goRightKey;
	private int myPlayerNumber;
	
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////// START OF GETTERS AND SETTERS LIST  ////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
	public int[] getPos() { return Pos;}
	public int[] getImgDetails(){ return imgDetails;}
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////// END OF GETTERS AND SETTERS LIST ///////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
	

	public StickMan(int[] playerKeys, int playerNumber)
	{
		this.myPlayerNumber = playerNumber;
		this.goLeftKey = playerKeys[0];
		this.goRightKey = playerKeys[1];
		initialisePlayer();
		loadPlayerContent();
		ResetPlayer();
	}
	
	private void initialisePlayer()
	{
	   random = new Random();
	   Pos = new int[1];
	   imgDetails = new int[2];
	   movementSpeed = 2; // Set player movement speed
	}
	
	private void loadPlayerContent()
	{
		try
		{
			URL getplayerImage;
			if (myPlayerNumber == 1)
			getplayerImage = this.getClass().getResource("/Images/stickman.jpg");
			else
			getplayerImage = this.getClass().getResource("/Images/stickman2.jpg");
			playerImage = ImageIO.read(getplayerImage);
			imgDetails[0] = playerImage.getWidth();
			imgDetails[1] = playerImage.getHeight();
			
		}
		catch (IOException ex) { ex.printStackTrace();}
	}
	
	public void ResetPlayer()
	{
		Pos[0] = random.nextInt(GameFramework.frameWidth - playerImage.getWidth());
	}
	
	public void Draw(Graphics2D g2d)
	{
		g2d.drawImage(playerImage, Pos[0], GameFramework.frameHeight - imgDetails[1], null);
	}
	
	public void UpdatePlayer()
	{
        if(GameHandler.keyboardKeyState(goLeftKey))
        	if (Pos[0] > 0) // Move the player left
           Pos[0] -= movementSpeed; 
      
        if(GameHandler.keyboardKeyState(goRightKey))
        	if (Pos[0] < GameFramework.frameWidth - playerImage.getWidth()) // Move the player right
        	Pos[0] += movementSpeed;

	}
}
