package Components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

public class Shape extends JPanel implements UIComponent{
	
	String name = "box";
	String shape;
	Color color;
	int width, height, x, y, lifeTime = 20;
	boolean fill, focus = false;;
	
	public Shape(String getShape, Color getColor, int getX, int getY, int getWidth, int getHeight){
		shape = getShape;
		color = getColor;
		width = getWidth;
		height = getHeight;
		x = getX;
		y = getY;
		fill = false;
	}

	public Shape(String getShape, Color getColor, int getX, int getY, int getWidth, int getHeight, boolean getFill){
		shape = getShape;
		color = getColor;
		width = getWidth;
		height = getHeight;
		x = getX;
		y = getY;
		fill = getFill;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		if (shape.equals("CIRCLE")) {
			if (fill) g.fillOval(x, y, width, height);
			else g.drawOval(x, y, width, height);
		} else if (shape.equals("SQUARE")) {
			if (fill) g.fillRect(x, y, width, height);
			else g.drawRect(x, y, width, height);
		}
	}
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public int getXPos() {
		return x;
	}

	@Override
	public int getYPos() {
		return y;
	}
}
