package za.Wits.ELEN7045.WynandViljoen.Assignment1;

import java.awt.Graphics2D;

public interface IGameSettings 
{
	public AbstractGameStrategy getMyGameStrategy();
	public int getGameDifficulty();
	public int getNumberOfPlayers();
	public int[][] getPlayerControls();
	public void setKeyPressed(int keyCode);
	public void Draw(Graphics2D g2d);
}