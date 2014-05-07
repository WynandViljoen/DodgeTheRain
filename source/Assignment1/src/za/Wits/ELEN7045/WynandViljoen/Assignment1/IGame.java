package za.Wits.ELEN7045.WynandViljoen.Assignment1;

import java.awt.Graphics2D;

public interface IGame 
{
	public void resetGame();
	public void UpdateGame();
	public void Draw(Graphics2D g2d);
	public void DrawGameOver(Graphics2D g2d);
}