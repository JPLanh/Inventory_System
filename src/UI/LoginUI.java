package UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Components.*;

public class LoginUI implements UIFrame{

	private static String staffUserPass = "staff";
	private static String adminUserPass = "admin";
	private static String techUserPass = "tech";
	GUIList frameComponents = new GUIList();

	LoginUI(){
		refreshFrame();
	}

	private void refreshFrame() {
		frameComponents.add(new Shape("SQUARE", Color.WHITE, 25, 25, 725, 500, true));
		frameComponents.add(new Label("Login", 350, 150, "Login"));
		frameComponents.add(new TextField("Username", 300, 180, "Username"));
		frameComponents.add(new TextField("Password", 300, 220, "Password"));
		frameComponents.add(new Button("Login", 300, 260, 150, 30, "LOGIN", "Click Login User"));
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
		if (getAction != null) {
			if (getAction.equals("Click Login User")) {
				ArrayList<Credential> credSet = Metadata.getCredentials();
				if (credSet != null) {
					boolean staff = false, admin = false, tech = false;
					for (Credential i : credSet) {
						if (i.getType().equals("Staff")) staff = true;
						if (i.getType().equals("Admin")) admin = true;
						if (i.getType().equals("Tech")) tech = true;

						if (((TextField)frameComponents.get("Username")).getString().equals(i.getUsername()) && ((TextField)frameComponents.get("Password")).getString().equals(i.getPassword())) {
							return "Goto " + i.getType() + " Console";
						}						
					}
					if (!staff)
						if (((TextField)frameComponents.get("Username")).getString().equals(staffUserPass) && ((TextField)frameComponents.get("Password")).getString().equals(staffUserPass)) {
							return "Goto Staff Console";
						}
					if (!admin)
						if (((TextField)frameComponents.get("Username")).getString().equals(adminUserPass) && ((TextField)frameComponents.get("Password")).getString().equals(adminUserPass)) {
							return "Goto Admin Console";
						}
					if (!tech)
						if (((TextField)frameComponents.get("Username")).getString().equals(techUserPass) && ((TextField)frameComponents.get("Password")).getString().equals(techUserPass)) {
							return "Goto Tech Console";
						}					
				} else {
					if (((TextField)frameComponents.get("Username")).getString().equals(staffUserPass) && ((TextField)frameComponents.get("Password")).getString().equals(staffUserPass)) {
						return "Goto Staff Console";
					} else if (((TextField)frameComponents.get("Username")).getString().equals(adminUserPass) && ((TextField)frameComponents.get("Password")).getString().equals(adminUserPass)) {
						return "Goto Admin Console";
					} else if (((TextField)frameComponents.get("Username")).getString().equals(techUserPass) && ((TextField)frameComponents.get("Password")).getString().equals(techUserPass)) {
						return "Goto Tech Console";
					}					
				}
				return getAction;
			}
		}
		return null;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

}
