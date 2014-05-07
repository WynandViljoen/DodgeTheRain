package za.Wits.ELEN7045.WynandViljoen.Assignment1;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;



public class Main extends JFrame
{
	
	private static final long serialVersionUID = 1L;

	private Main()
    {

        this.setTitle("Dodge the Rain!");
        this.setSize(800, 600);
        
        // Puts frame to center of the screen.
        this.setLocationRelativeTo(null);
        
        // So that frame cannot be resizable by the user.
        this.setResizable(false);
        
        // When the frame is closed, the application should exit as well
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Add the Singleton JPanel Class to this JFrame 
        this.add(GameFramework.getInstance());
     
        this.setVisible(true);
    }

	public static void main(String[] args)
    {
		
        // Spawn an Event Dispatcher Thread to run the GUI in
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}
