package Components;

import java.awt.Graphics;

public interface UIComponent {
	
	public void draw(Graphics g);
	public String getName();
	public int getXPos();
	public int getYPos();
}
