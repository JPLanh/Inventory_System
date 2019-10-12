package Components;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;

public class GUIList<T> {
	private ArrayList<UIComponent> listOfGui;
	private AlertBox alert;
	private String alertCmd;
	private T alertItem;
	private InputInterface focus = null;

	public <T> GUIList()
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
		//If there is an alert currently active
		if (alert != null) {
			if (alert.isClickedOn(mouseX, mouseY)) {
				return alert.clickAction(mouseX, mouseY);
			} else {
				return null;
			}
			//If an alert is not active
		} else {
			//Scans through the list of components
			for (UIComponent x : listOfGui)
			{
				//Picks out any that is an input.
				if (x instanceof InputInterface) {
					if (((InputInterface) x).isClickedOn(mouseX, mouseY))
					{				
						if (focus != null) focus.getFocus(false);
						focus = ((InputInterface) x);
						focus.getFocus(true);
						//If an input was clicked on it will do it's action
						return ((InputInterface) x).clickAction();
					}
				}
			}
			//If the click did not find anything
			return null;
		}
	}

	public void setFocus(String componentName) {
		for (UIComponent x : listOfGui)
		{
			if (x.getName().equals(componentName) && x != focus) {
				if (focus != null) focus.getFocus(false);
				focus = ((InputInterface) x);
				focus.getFocus(true);
				break;
			}
		}
	}

	public String keyPress(KeyEvent keyPress)
	{
		if (alert == null) {
			//If tab is pressed
			if (keyPress.getKeyChar() == 9) {
				tabFocus();
			}
			else
				//If anything else is pressed
			{
				String getPress = focus.keyPress(keyPress);
				if (getPress != null) {
					if (getPress.equals("\n")) {
						tabFocus();
					} else {
						return getPress;
					}
				}
			}
			return null;
		} else {
			//If there is an alert active
			return alert.keyPress(keyPress);
		}
	}

	private void tabFocus() {
		//If there was not an alert active.
		int focusIndex = 9999;

		InputInterface firstInput = null;
		for (int i = 0; i < listOfGui.size(); i++) {
			if (listOfGui.get(i) instanceof InputInterface) {
				//Set the first input just incase tabs cycles.
				if (firstInput == null) firstInput = ((InputInterface) listOfGui.get(i));

				//If there was a focus
				if (listOfGui.get(i) == focus) {
					focusIndex = i;
				}

				//Initialize a focus, or set a new focus
				if (i > focusIndex || focus == null) {
					if (focus != null) focus.getFocus(false);
					focus = ((InputInterface) listOfGui.get(i));
					focus.getFocus(true);
					break;
				}
			}

			//Once the focus cycles to the end of the list, it will set focus to the first input.
			if (i == listOfGui.size()-1 && focus != null) {
				focus.getFocus(false);
				focus = ((InputInterface) firstInput);
				focus.getFocus(true);
				break;
			}
		}		
	}

	public void alertUser(String getName, int getInitX, int getInitY, String getString, String getAction, String actionType) {
		alert = new AlertBox(getName, getInitX, getInitY, getString, getAction, actionType);
		alertCmd = getAction;
	}

	public void alertUser(String getName, int getInitX, int getInitY, String getString, String getAction, String actionType, T getItem) {
		alert = new AlertBox(getName, getInitX, getInitY, getString, getAction, actionType);
		alertItem = getItem;
		alertCmd = getAction;
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

	public AlertBox getAlert() {
		return alert;
	}
	
	public String getAlertCmd() {
		return alertCmd;
	}
	
	public T getAlertItem() {
		return alertItem;
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
