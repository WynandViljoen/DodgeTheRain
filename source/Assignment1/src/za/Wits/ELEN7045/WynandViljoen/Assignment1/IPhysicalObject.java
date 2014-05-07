package za.Wits.ELEN7045.WynandViljoen.Assignment1;

import java.awt.Graphics2D;

interface IPhysicalObject 
{
	public int[] getPos();
	public int[] getImgDetails();
	public void Draw(Graphics2D g2d);
	public void Update();
}
