package za.Wits.ELEN7045.WynandViljoen.Assignment1;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import javax.imageio.ImageIO;

public class Rain implements IPhysicalObject
{
	private int[] Pos; // Pos[0] stores the x-axis position; Pos[1] stores the y-axis position
	private int fallingSpeed;
	private Random random;
	private BufferedImage rainImage;
	private int rows;
	private int[] imgDetails; //imgDetails[0] stores image width, imgDetails[1] stores image height
	
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////// START OF GETTERS AND SETTERS LIST  ////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
	public int[] getPos() { return Pos;}
	public int[] getImgDetails() { return imgDetails;}
	
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////// END OF GETTERS AND SETTERS LIST ///////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Rain()
	{
		initialiseRain();
		loadRainContent();
		
		this.Pos[0] = random.nextInt(rows) * imgDetails[0] + 2; // raindrops appear at a random row position on the screen
		this.Pos[1] = random.nextInt(GameFramework.frameHeight / 4); // raindrops appear at a random positions in top quater of screen
		fallingSpeed = random.nextInt(3) + 1; // randomize the falling speed of the raindrop so that they differ
	}
	

	private void initialiseRain()
	{
		random = new Random();
		Pos = new int[2];
		imgDetails = new int[2];
	}
	
	private void loadRainContent()
	{
		try
		{
			URL getRainImage = this.getClass().getResource("/Images/raindrop.gif");  
			rainImage = ImageIO.read(getRainImage);
				
			imgDetails[0] = rainImage.getWidth();
			imgDetails[1] = rainImage.getHeight();
			rows = GameFramework.frameWidth / imgDetails[0]; // Take rain width and calculate how many rows we can fit into the screen
		}
		catch (IOException ex) { ex.printStackTrace();}
	}
	
	public void Draw(Graphics2D g2d)
	{
		g2d.drawImage(rainImage, Pos[0], Pos[1], null);
	}
	
	public void Update()
	{
		Pos[1] += fallingSpeed;
	}
	
}
