package Components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Label extends JPanel implements UIComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2570709091349969835L;
	private int xPos, yPos, width, height, fontSize = 12;
	private int lowX, highX, lowY, highY, lifeSpan = -1;
	private String name, string = "";
	private boolean hidden = false;

	public Label(String getName, int getXPos, int getYPos)
	{
		name = getName;
		xPos = getXPos;
		yPos = getYPos;
		width = 150;
		height = 30;
		lowX = xPos;
		highX = xPos+width;
		lowY = yPos;
		highY = yPos+height;
	}

	public Label(String getName, int getXPos, int getYPos, String getString)
	{
		name = getName;
		xPos = getXPos;
		yPos = getYPos;
		width = 150;
		height = 30;
		lowX = xPos;
		highX = xPos+width;
		lowY = yPos;
		highY = yPos+height;
		string = getString;
	}

	public Label(String getName, int getXPos, int getYPos, String getString, int getSize)
	{
		name = getName;
		xPos = getXPos;
		yPos = getYPos;
		width = 150;
		height = 30;
		lowX = xPos;
		highX = xPos+width;
		lowY = yPos;
		highY = yPos+height;
		string = getString;
		fontSize = getSize;
	}

	public Label(String getName, int getXPos, int getYPos, String getString, int getSize, int getLengthSize)
	{
		name = getName;
		xPos = getXPos;
		yPos = getYPos;
		width = getLengthSize;
		height = 30;
		lowX = xPos;
		highX = xPos+width;
		lowY = yPos;
		highY = yPos+height;
		string = getString;
		fontSize = getSize;
	}
	public Label(String getName, int getXPos, int getYPos, String getString, Boolean getFlag)
	{
		name = getName;
		xPos = getXPos;
		yPos = getYPos;
		width = 150;
		height = 30;
		lowX = xPos;
		highX = xPos+width;
		lowY = yPos;
		highY = yPos+height;
		string = getString;
		lifeSpan = 15;
	}
	
	public void draw(Graphics g) 
	{
		g.setColor(Color.BLACK); 
		g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, fontSize)); 
		g.drawString(string, xPos+12, yPos+20);
	}

	public void setString(String getString) 
	{
		string = getString;
	}
	
	public String getName() 
	{
		return name;
	}

	public int getLifeSpan() {
		return lifeSpan;
	}

	public void setLifeSpan(int lifeSpan) {
		this.lifeSpan = lifeSpan;
	}
	@Override
	public int getXPos() {
		return xPos;
	}

	@Override
	public int getYPos() {
		return yPos;
	}
}
