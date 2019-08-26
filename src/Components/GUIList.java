package Components;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GUIList {
	private ArrayList<UIComponent> listOfGui;
	private AlertBox alert;

	public GUIList()
	{
		listOfGui = new ArrayList<UIComponent>();
	}

	public void add(UIComponent getComponent)
	{
		if (getComponent instanceof AlertBox) {
			listOfGui.add(0, getComponent);
		}
		else listOfGui.add(getComponent);
	}

	public UIComponent get(String componentName)
	{
		UIComponent tempUI = null;
		for (UIComponent x : listOfGui)
		{
			if (x.getName().equals(componentName)) return x;
		}
		return null;
	}

	public void draw(Graphics g)
	{
		for (UIComponent x : listOfGui)
		{
			x.draw(g);
		}
		if (alert != null) alert.draw(g);
	}
	public String mouseSelect(int mouseX, int mouseY)
	{
		UIComponent tempUI = null;
		if (alert != null) {
			if (alert.isClickedOn(mouseX, mouseY)) {
				return alert.clickAction(mouseX, mouseY);
			} else {
				return null;
			}
		} else {
			for (UIComponent x : listOfGui)
			{
				if (x.isClickedOn(mouseX, mouseY))
				{
						tempUI = x;
				}
			}
			if (tempUI == null) return null;
			return tempUI.clickAction();
		}
	}

	public void setFocus(String componentName) {
		for (UIComponent x : listOfGui)
		{
			if (x.getName().equals(componentName)) {
				if (x instanceof TextField) ((TextField) x).activeToggle(true);
			}
		}
	}

	public String keyPress(KeyEvent keyPress)
	{
		if (alert == null) {
			TextField tempField = null;
			TextField firstField = null;
			boolean flag = false;
			for (UIComponent x : listOfGui)
			{
				if (keyPress.getKeyCode() == 10) {
					if (x instanceof AlertBox) {
						return ((AlertBox) x).keyPress(keyPress);
					}
					if (x instanceof Button)
					{
						if (tempField != null) {
							return x.clickAction();
						}
					}
				}
				if (x instanceof TextField)
				{
					if (firstField == null) firstField = (TextField)x;
					if (tempField != null) {
						tempField.activeToggle(false);
						((TextField) x).activeToggle(true);
						tempField = null;
						break;
					} 
					else 
					{ 
						if (x.isActive()) 
						{
							if (keyPress.getKeyCode() == 10 || keyPress.getKeyCode() == 9) {
								tempField = (TextField) x;
							}
							else ((TextField) x).keyPress(keyPress);
						}
					}
				}
			}
			if (tempField != null) {
				tempField.activeToggle(false);
				firstField.activeToggle(true);
			} 
			return null;
		} else {
			return alert.keyPress(keyPress);
		}
	}
	
	public void alertUser(String getName, int getInitX, int getInitY, String getString, String getAction, boolean getInput) {
		if (!getInput) alert = new AlertBox(getName, getInitX, getInitY, getString, getAction);
		else alert = new AlertBox(getName, getInitX, getInitY, getString, getAction, true);
	}
	
	public void alertConfirm() {
		alert = null;
	}

	public void remove(String componentName){
		UIComponent getUI = null;
		for (UIComponent x : listOfGui)
		{
			if (x.getName().equals(componentName)) {
				getUI = x;
				break;
			}
		}
		listOfGui.remove(getUI);
	}	

	public boolean getAlert() {
		return (alert!=null);
	}
	
	public void tick() {
		UIComponent removal = null;
		for (UIComponent x : listOfGui) {
			if (x instanceof Label) {
				if (((Label) x).getLifeSpan() > 0) {
					((Label) x).setLifeSpan(((Label) x).getLifeSpan()-1);
				} else if (((Label) x).getLifeSpan() == 0) {
					removal = x;
				}
			}
		}
		if (removal != null) {
			listOfGui.remove(removal);
		}
	}
}
