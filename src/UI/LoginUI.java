package UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Components.*;

public class LoginUI implements UIFrame{

	GUIList frameComponents = new GUIList();

	LoginUI(){
		//		Metadata.formatList();
		refreshFrame();
	}

	private void refreshFrame() {
		frameComponents.add(new Shape("SQUARE", Color.WHITE, 25, 25, 725, 500, true));
		frameComponents.add(new Label("Login", 350, 150, "Login"));
		frameComponents.add(new TextField("Username", 300, 180, "Username", 150, false));
		frameComponents.add(new TextField("Password", 300, 220, "Password", 150, false));
		frameComponents.add(new Button("Login Button", 300, 260, 150, 30, "LOGIN", "Click Login User"));
		frameComponents.setFocus("Username");
	}
	@Override
	public void draw(Graphics g) {
		frameComponents.draw(g);
	}

	@Override
	public String clickAction(int mouseX, int mouseY) {
		String getAction = frameComponents.mouseSelect(mouseX, mouseY);
		return action(getAction);
	}

	@Override
	public String keyPress(KeyEvent c) {
		String getAction = frameComponents.keyPress(c);
		return action(getAction);
	}

	private String action(String getAction) {
		if (frameComponents.getAlert() != null) {
			if (frameComponents.getAlertCmd().equals("Invalid credential")){
				if (getAction.equals("Ok")) {
					frameComponents = new GUIList();
					refreshFrame();
				}
			}
			return null;
		} else {
			if (getAction != null) {
				if (getAction.equals("Click Login User")) {
					ArrayList<Credential> credSet = Metadata.getCredentials();

					//If credential has already been set
					if (credSet != null) {
						for (Credential i : credSet) {

							if (((TextField)frameComponents.get("Username")).getString().equals(i.getUsername()) && ((TextField)frameComponents.get("Password")).getString().equals(i.getPassword())) {
								return "Goto " + i.getType() + " Console";
							}						
						}
						frameComponents.alertUser("Invalid credential", 200, 300, "Invalid username or password", "Invalid credential", "confirm");
		
					}
					return getAction;
				}
			}
			return null;
		}
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

}
