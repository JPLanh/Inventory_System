package Components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;


public class Button extends JPanel implements UIComponent, InputInterface{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6017453879784009002L;
	private int xPos, yPos, width, height, fontSize = 12;
	private int lowX, highX, lowY, highY;
	private String name, string, action = "";
	private Color fontColor = Color.WHITE, bkColor = new Color(8, 81, 0);//(188, 201, 155);
	private boolean hidden = false, focus = false;

	public Button(String getName, int getXPos, int getYPos, int getWidth, int getHeight, String getString, String getAction, Color getBKColor, Color getFontColor, int getSize)
	{
		name = getName;
		xPos = getXPos;
		yPos = getYPos;
		width = getWidth;
		height = getHeight;
		lowX = xPos;
		highX = xPos+getWidth;
		lowY = yPos;
		highY = yPos+getHeight;
		string = getString;
		action = getAction;
		fontColor = getFontColor;
		bkColor = getBKColor;
		fontSize = getSize;
	}
	
	public Button(String getName, int getXPos, int getYPos, int getWidth, int getHeight, String getString, String getAction, Color getBKColor, Color getFontColor)
	{
		name = getName;
		xPos = getXPos;
		yPos = getYPos;
		width = getWidth;
		height = getHeight;
		lowX = xPos;
		highX = xPos+getWidth;
		lowY = yPos;
		highY = yPos+getHeight;
		string = getString;
		action = getAction;
		fontColor = getFontColor;
		bkColor = getBKColor;
		
	}

	public Button(String getName, int getXPos, int getYPos, int getWidth, int getHeight, String getString)
	{
		name = getName;
		xPos = getXPos;
		yPos = getYPos;
		width = getWidth;
		height = getHeight;
		lowX = xPos;
		highX = xPos+getWidth;
		lowY = yPos;
		highY = yPos+getHeight;
		string = getString;
	}
	
	public Button(String getName, int getXPos, int getYPos, int getWidth, int getHeight, String getString, String getAction)
	{
		name = getName;
		xPos = getXPos;
		yPos = getYPos;
		width = getWidth;
		height = getHeight;
		lowX = xPos;
		highX = xPos+getWidth;
		lowY = yPos;
		highY = yPos+getHeight;
		string = getString;
		action = getAction;
	}
	@Override
	public void draw(Graphics g) 
	{
//		g.setColor(Color.BLACK);
//		g.fillRect(xPos-1, yPos-1, width+2, height+2);
		g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, fontSize)); 
		g.setColor(bkColor);
		g.fillRect(xPos, yPos, width, height);
		g.setColor(fontColor);
		g.drawString(string, xPos+(width/2)-(string.length()*3), yPos+(height/2)+4);
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setString(String getString) {
		this.string = getString;
	}
	
	@Override
	public String getName() 
	{
		return name;
	}
	

	@Override
	public boolean isClickedOn(int mouseX, int mouseY)
	{ 
		if (mouseX < highX && mouseX > lowX && mouseY < highY && mouseY > lowY)	return true;
		else return false;
	}

	@Override
	public String clickAction() {
		
		return action;
	}

	@Override
	public int getXPos() {
		return xPos;
	}

	@Override
	public int getYPos() {
		return yPos;
	}
	
	public boolean isFocus() {
		return focus;
	}

	@Override
	public void getFocus(boolean getFocus) {
		focus = getFocus;
		
	}

	@Override
	public String keyPress(KeyEvent c) {
		if (c.getKeyCode() == 10) return action;
		return null;
	}
}
