package Components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

public class TextField extends JPanel implements UIComponent, InputInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5011944768362330368L;
	private int xPos, yPos, width, height;
	private int lowX, highX, lowY, highY, counter;
	private String name, string = "";
	private ArrayList<String> text = new ArrayList<String>();
	private boolean editable = false, multiline = false, focus = false;

	public TextField(String getName, int getXPos, int getYPos, String setString, boolean getEditable)
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
		string = setString;
		editable = getEditable;
	}

	public TextField(String getName, int getXPos, int getYPos, String setString, int textLength, boolean muli)
	{
		name = getName;
		xPos = getXPos;
		yPos = getYPos;
		width = textLength;
		height = 30;
		lowX = xPos;
		highX = xPos+width;
		lowY = yPos;
		highY = yPos+height;
		string = setString;
		multiline = muli;
	}

	public TextField(String getName, int getXPos, int getYPos, String setString, int getLength, int getHeight)
	{
		name = getName;
		xPos = getXPos;
		yPos = getYPos;
		width = getLength;
		height = getHeight;
		lowX = xPos;
		highX = xPos+width;
		lowY = yPos;
		highY = yPos+height;
		string = setString;
	}

	public void draw(Graphics g) 
	{
		g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 12)); 
		g.setColor(new Color(242, 242, 242));
		g.fillRect(xPos, yPos, width, height);
		if (string.equals(name)) g.setColor(new Color(188, 201, 155));
		else g.setColor(Color.BLACK);
		for (int x = 0; x < text.size(); x++) {
			g.drawString(text.get(x), xPos+12, yPos+20 + (x*20));
		}
		g.drawString(string, xPos+12, yPos+20 + (text.size()*20));
	}

	public void setString(String getString) {
		string = getString;
	}
	public String getString() {
		String tempstring = "";
		for (String x : text) {
			tempstring += x + "\n";
		}
		tempstring += string;
		if (tempstring.charAt(tempstring.length()-1) == '|') tempstring = tempstring.substring(0, tempstring.length()-1);
		return tempstring;
	}

	@Override
	public String getName() 
	{
		return name;
	}

	public String keyPress(KeyEvent c)
	{
		//If the text field is editable
		if (!editable) {
			//User press backspace
			if (c.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE) 
			{
				//If a text exist in the text field
				if (string.length() > 1) string = string.substring(0, string.length()-2) + "|";
				else {
					//If multiline was enabled
					if (text.size()>0 && multiline) string = text.remove(text.size()-1) + "|";
				}
			} else 
			{
				//If user press enter
				if (c.getKeyCode() == 10) {
					//If multiline was exist while pressing enter
					if (multiline)
					{
						text.add(string.substring(0,string.length()-1));
						string = "|";
					} else {
						return "\n";
					}
				}
				else if (c.getKeyCode() >= 32 && c.getKeyCode() <= 127) {
					if (string.length() > 120) {
						text.add(string.substring(0,string.length()-1)+c.getKeyChar());
						string = "|";
					} else {
						string = string.substring(0,string.length()-1) + c.getKeyChar() + "|";
					}
				}
			}
		}
		//		System.out.println(c.getKeyCode());
		//		if (c.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE && string.length() > 1) string = string.substring(0, string.length()-2) + "|";
		//		else if (Character.toString(c.getKeyChar()).matches("[a-zA-Z0-9@.\\s]")) {  
		//				string = string.substring(0,string.length()-1) + c.getKeyChar() + "|";
		//		}
		return null;
	}

	@Override
	public boolean isClickedOn(int mouseX, int mouseY)
	{ 
		if (mouseX < highX && mouseX > lowX && mouseY < highY && mouseY > lowY)	{
//			if (!active) {
//				if (string.equals(name)) string = "";
//				string += "|";
//				active = true;
//			}
			return true;
		}
		else {
//			if (active)
//			{
//				string = string.substring(0,string.length()-1);
//				if (string.equals("")) {
//					string = name;
//				}
//			}
//			active = false;
			return false;		
		}
	}

	@Override	
	public String clickAction() {
		return "Activate";
	}

	@Override
	public int getXPos() {
		return xPos;
	}

	@Override
	public int getYPos() {
		return yPos;
	}

	@Override
	public boolean isFocus() {
		return focus;
	}

	@Override
	public void getFocus(boolean getFocus) {
		if (getFocus) {
			if (string.equals(name)) string = "|";
			else string += "|";
		} else {
			string = string.substring(0,string.length()-1);
			if (string.equals("")) {
				string = name;
			}
		}
	}
}
