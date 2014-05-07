package za.Wits.ELEN7045.WynandViljoen.Assignment1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JButton;

public class GameFramework extends GameHandler
{
	private static final long serialVersionUID = 1L;
	private static GameFramework myGameFramework; // Singleton instance variable
	private boolean gameInitialised; // Variable that will check that an IGame object exists
	private BufferedImage frameworkBackground; // Image for menu
	private int gameSpeed;
	private IGame game; // The actual game
    private IGameSettings myGameSettings; // GameSettings object reference variable
    private JButton startGameButton = new JButton("Start New Game");
    private JButton gameSettingsButton = new JButton("Game Settings");
    
    public static int frameWidth;
    public static int frameHeight;
    public static enum GameState{LOADING, MAIN_MENU, OPTIONS, PLAYING, GAMEOVER} // Possible states of the game
    public static GameState gameState;
    
    private GameFramework() 
    {	
    	// Set default game state to Loading
    	gameState = GameState.LOADING; 
        
        // We start the game in a new thread
        Thread gameThread = new Thread() {
            @Override
            public void run(){
                gameLoop();
            }
        };
        gameThread.start();
    }
    
    // Singleton Class getInstance method
	public static GameFramework getInstance()	
	{
		if (myGameFramework == null)
			myGameFramework = new GameFramework();
		return myGameFramework;
	}
	
	// Create an inner class for the start game button
	class StartGameButtonClass implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent event) 
		{  
			if (!gameInitialised)
			newGame();
			else restartGame();
		}
	}
		
	// Create an inner class for the game configuration settings button
	class ConfigureGameButtonClass implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent event) 
		{
			options();	
		}
	}
	
	// Set background image
	private void loadFrameworkContent()
	{
        try
        {
            URL getMenuBackground = this.getClass().getResource("/Images/background.jpg");
            frameworkBackground = ImageIO.read(getMenuBackground);
        }
        catch (IOException ex) { ex.printStackTrace();}
	}
	
	// This function will continuously loop in the thread until game exits 
	private void gameLoop()
	{
		// Add buttons to JPanel
		this.add(startGameButton);
		this.add(gameSettingsButton);
		
		// Let ActionListener check buttons for events
		startGameButton.addActionListener(new StartGameButtonClass());
		gameSettingsButton.addActionListener(new ConfigureGameButtonClass());
		
		 while(true)
	     {
	            switch (gameState)
	            {
	                case LOADING:
	                	try 
	                	{
	    	            	Thread.sleep(500); // Let thread sleep otherwise frameWidth & frameHeight will be zero
	    	            } 
	                	catch (InterruptedException ex) { ex.printStackTrace();}
	                	
	                	frameWidth = this.getWidth();
                        frameHeight = this.getHeight();
	            		loadFrameworkContent();   
	            		
	            		// Creates a concrete IGameSettings object. Can be replaced by Dependency Injection
	            		myGameSettings = new GameSettings();
	            		gameSpeed =  myGameSettings.getGameDifficulty();
	            		gameState = GameState.MAIN_MENU;  
                    break;
                    
	                case MAIN_MENU:
		            break;
		                
		            case OPTIONS:
		               gameSpeed =  myGameSettings.getGameDifficulty();
		               this.requestFocus();
		            break;
		                
	                case PLAYING:          	
	                	game.UpdateGame();		
	                break;
	                
	                case GAMEOVER:
	                	this.add(startGameButton);
	                	this.requestFocus();
	                break;
	                
	                default:
					break;
	            }
	            
	            // Repaint the screen by calling the paintComponent(Graphics g) method
	            this.repaint();
	            
	            try 
	            {
	            	Thread.sleep(gameSpeed);
	            } 
	            catch (InterruptedException ex) { ex.printStackTrace();}
	     }
	}

	// This function will cast the Graphics object into a Graphics2D object and call the Draw method
	public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g; 
		Draw(g2d);
	}
	
	// This function will either draw the correct things on screen, or call the objects that needs to do the drawing
	private void Draw(Graphics2D g2d)
	{
		 switch (gameState)
	     {
	     	case MAIN_MENU:
	     		g2d.setColor(Color.white);
	     		g2d.drawImage(frameworkBackground, 0, 0, GameFramework.frameWidth, GameFramework.frameHeight, null);
	     		g2d.drawString("Use a and d to control the stickman.", frameWidth / 2 - 130, frameHeight / 2);
	     		g2d.drawString("Press Enter or click 'Start New Game' button to start the game.", frameWidth / 2 - 200, frameHeight / 2 + 50);
	     	break;
	     	
	     	case OPTIONS:
	     		myGameSettings.Draw(g2d);
	     	break;
	     	
	     	case PLAYING:
	                game.Draw(g2d);
	                g2d.drawString("Handicap: " + gameSpeed, frameWidth - 150, 20);
	     	break;

	     	case GAMEOVER:
	                game.DrawGameOver(g2d);
	     	break;
   
	     	default:
	     	break;
	     }
	 }
	
	@Override
	public void keyReleasedAction(KeyEvent event)
	{
		 if(event.getKeyCode() == KeyEvent.VK_ESCAPE)
	 			System.exit(0);
		 
		 switch (gameState)
		 {
		 	case MAIN_MENU:
		 		if(event.getKeyCode() == KeyEvent.VK_ENTER)
		 			newGame();
			 break;
			 
	         case OPTIONS:
	        	 // Pass keyPressed to the myGameSettings object
	        	 myGameSettings.setKeyPressed(event.getKeyCode());
	         break;
	         
	         case GAMEOVER:
	        	 if(event.getKeyCode() == KeyEvent.VK_ENTER)
	        		 restartGame();
             break;
           
	         case PLAYING:
	        	 // Cheats for the Game, page-up and page-down can change gamespeed
	        	 if((event.getKeyCode() == KeyEvent.VK_PAGE_UP) && (gameSpeed > 1))
	        		 gameSpeed -= 1;
	        	 else if((event.getKeyCode() == KeyEvent.VK_PAGE_DOWN) && (gameSpeed < 20))
	        		 gameSpeed += 1;
	        break;

           default:
			break;
       }
		
	}
	
	private void newGame()
	{
		game = new Game(myGameSettings.getMyGameStrategy()); // Get the game object from the GameSettings object
		this.requestFocus();
		this.remove(startGameButton);
		gameInitialised = true;
		gameState = GameState.PLAYING;	
	}
	
	private void restartGame()
	{
		game.resetGame();
		this.remove(startGameButton);
		this.requestFocus();
		gameState = GameState.PLAYING;
	}
	
	private void options()
	{
		gameState = GameState.OPTIONS;
		this.add(startGameButton);
	}	

}
