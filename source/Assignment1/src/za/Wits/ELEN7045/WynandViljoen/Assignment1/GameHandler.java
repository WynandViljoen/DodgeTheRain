package za.Wits.ELEN7045.WynandViljoen.Assignment1;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;


public abstract class GameHandler extends JPanel implements KeyListener
{
	private static final long serialVersionUID = 1L;
	
	// Keyboard states - This variable will tell if a key is being pressed
    private static boolean[] keyboardState = new boolean[525];
    
	public GameHandler()
	{
		// Tells JVM how to draw this JPanel and sets focus to this JPanel 
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setBackground(Color.black);
        
        // Adds the keyboard listener to this JPanel to receive keyboard events when panel is selected/active.
        this.addKeyListener(this);
	}
	
	@Override
	public void keyPressed(KeyEvent event) 
	{
		keyboardState[event.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent event) 
	{
		keyboardState[event.getKeyCode()] = false;
        keyReleasedAction(event);
	}

	@Override
	public void keyTyped(KeyEvent event) {}
	
	// Will be overriden in child Class
	public abstract void keyReleasedAction(KeyEvent event);
	
	// Returns the current state of a key
    public static boolean keyboardKeyState(int key)
    {
        return keyboardState[key];
    }
}
