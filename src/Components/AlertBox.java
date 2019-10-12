package Components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

import Components.Button;
import Components.GUIList;
import Components.Label;
import Components.Shape;
import Components.UIComponent;
import Components.UICompound;

public class AlertBox implements UICompound, InputInterface{
	GUIList compoundComponents = new GUIList();
	int posx, posy, lowX, lowY, highX, highY; 
	boolean alive = true, confirm = false, focus = false;
	String string, name, action;

	public AlertBox(String getName, int getInitX, int getInitY, String getString, String getAction, String alertType) {
		posx = getInitX;
		posy = getInitY;
		lowX = posx;
		highX = posx+200;
		lowY = posy;
		highY = posy+100;
		string = getString;
		name = getName;
		action = getAction;
		compoundComponents.add(new Shape("SQUARE", Color.WHITE, posx, posy, 400, 100, true));
		compoundComponents.add(new Shape("SQUARE", Color.GRAY, posx, posy, 400, 100));
		compoundComponents.add(new Label("Message", posx + 15, posy + 5, string));
		switch (alertType.toLowerCase()) {
			case "input":
				compoundComponents.add(new TextField("Input", posx + 15, posy + 30, "Input", 300, false));
				compoundComponents.add(new Button("Yes", posx +20, posy+70, 50, 25, "Submit", "Check Yes"));
				compoundComponents.add(new Button("No", posx +120, posy+70, 50, 25, "Cancel", "Check No"));
				compoundComponents.setFocus("Input");
				break;
			case "confirm":
//				compoundComponents.add(new TextField("Input", posx + 15, posy + 30, "", 300));
				confirm = true;
				compoundComponents.add(new Button("Ok", posx +20, posy+70, 50, 25, "Ok", "Check Ok"));
				compoundComponents.setFocus("Ok");
				break;
			case "yes/no":
//				compoundComponents.add(new TextField("Input", posx + 15, posy + 30, "", false));
				confirm = true;
				compoundComponents.add(new Button("Yes", posx +20, posy+70, 50, 25, "Yes", "Check Yes"));
				compoundComponents.add(new Button("No", posx +120, posy+70, 50, 25, "No", "Check No"));
				compoundComponents.setFocus("Yes");
				break;				
		}
	}
	
	public AlertBox(String getName, int getInitX, int getInitY, String getString, String getAction, boolean userInput) {
		posx = getInitX;
		posy = getInitY;
		lowX = posx;
		highX = posx+200;
		lowY = posy;
		highY = posy+100;
		string = getString;
		name = getName;
		action = getAction;
		compoundComponents.add(new Shape("SQUARE", Color.WHITE, posx, posy, 400, 100, true));
		compoundComponents.add(new Shape("SQUARE", Color.GRAY, posx, posy, 400, 100));
		compoundComponents.add(new Label("Message", posx + 15, posy + 5, string));
		compoundComponents.add(new TextField("Input", posx + 15, posy + 30, "Input", 300, false));
		compoundComponents.add(new Button("Yes", posx +20, posy+70, 50, 25, "Submit", "Check Yes"));
		compoundComponents.add(new Button("No", posx +120, posy+70, 50, 25, "Cancel", "Check No"));
		compoundComponents.setFocus("Input");
	}
	
	public AlertBox(String getName, int getInitX, int getInitY, String getString, String getAction) {
		posx = getInitX;
		posy = getInitY;
		lowX = posx;
		highX = posx+200;
		lowY = posy;
		highY = posy+100;
		confirm = true;
		string = getString;
		name = getName;
		action = getAction;
		compoundComponents.add(new TextField("Hidden", posx + 15, posy + 30, "Input", 300, false));
		compoundComponents.add(new Shape("SQUARE", Color.WHITE, posx, posy, 400, 100, true));
		compoundComponents.add(new Shape("SQUARE", Color.GRAY, posx, posy, 400, 100));
		compoundComponents.add(new Label("Message", posx + 15, posy + 5, string));
		compoundComponents.add(new Button("Yes", posx +20, posy+70, 50, 25, "Ok", "Check Yes " + action));
		compoundComponents.setFocus("Hidden");
	}
	
	@Override
	public void draw(Graphics g) {
		compoundComponents.draw(g);
	}

	@Override
	public boolean isClickedOn(int mouseX, int mouseY) {
		if (mouseX < highX && mouseX > lowX && mouseY < highY && mouseY > lowY)	return true;
		else return false;
	}

	@Override
	public String getName() {
		return name;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	@Override
	public String clickAction() {
		return null;
//		return "Confirm Yes " + action;
	}
	
	private String returnAction(String getAction) {
		if (getAction != null) {
			String[] splitText = getAction.split(" ");
			if (splitText[0].equals("Check")) {
				return splitText[1];
//				if (splitText[1].equals("Yes")) {
					
//					if (confirm) return "Confirm Yes " + action;
//					else return "Confirm Yes " + ((TextField)compoundComponents.get("Input")).getString() + " " + action;
//				} else if (splitText[1].equals("No")){
//					if (confirm) return "Confirm No " + action;
//					else return "Confirm No " + ((TextField)compoundComponents.get("Input")).getString() + " " + action;
//				}
			} 
		}
		return getAction;
	}


	public String keyPress(KeyEvent c) {
		return returnAction(compoundComponents.keyPress(c));
	}

	@Override
	public String clickAction(int mouseX, int mouseY) {
//		String temp = compoundComponents.mouseSelect(mouseX, mouseY);
//		if (temp != null) {
//			String[] splitText = temp.split(" ");
//			if (splitText[0].equals("Check")) {
//				if (splitText[1].equals("Yes")) {
//					if (confirm) return "Confirm Yes " + action;
//					else return "Confirm Yes " + ((TextField)compoundComponents.get("Input")).getString() + " " + action;
//				} else if (splitText[1].equals("No")){
//					if (confirm) return "Confirm No " + action;
//					else return "Confirm No " + ((TextField)compoundComponents.get("Input")).getString() + " " + action;
//				}
//			} 
//		}
//		return temp;
		return returnAction(compoundComponents.mouseSelect(mouseX, mouseY));
	}
	
	@Override
	public int getXPos() {
		return posx;
	}

	@Override
	public int getYPos() {
		return posy;
	}

	@Override
	public boolean isFocus() {
		return focus;
	}

	@Override
	public void getFocus(boolean getFocus) {
		focus = getFocus;
	}
}
