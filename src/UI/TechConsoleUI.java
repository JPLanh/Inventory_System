package UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import Components.*;

public class TechConsoleUI implements UIFrame{

	File dataDir, credDir;
	String staffUN = "", staffP = "";
	String adminUN = "", adminP = "";
	String techUN = "", techP = "";
	GUIList<Credential> frameComponents = new GUIList<Credential>();
	Boolean search = true;
	private int page = 0;
	boolean sort = false;

	TechConsoleUI(){
		refreshFrame();
	}

	private void refreshFrame() {
		HashMap<String, String> configSet = Metadata.getConfig();
		if (configSet != null) dataDir = new File(configSet.get("Dir"));
		for (Credential i : Metadata.getCredentials()) {
			if (i.getType().equals("Staff")) {
				staffUN = i.getUsername();
				staffP = i.getPassword();
			} else if (i.getType().equals("Admin")) {
				adminUN = i.getUsername();
				adminP = i.getPassword();
			} else if (i.getType().equals("Tech")) {
				techUN = i.getUsername();
				techP = i.getPassword();
			}
		}
		frameComponents = new GUIList<Credential>();
		frameComponents.add(new Shape("SQUARE", Color.WHITE, 25, 25, 725, 500, true));
		frameComponents.add(new Button("Logout", 650, 35, 80, 20, "Logout", "Click Logout"));

		frameComponents.add(new Label("Database Directory", 50, 50, "Database Directory:   " + dataDir));
		frameComponents.add(new Button("Change Directory", 62, 75, 100, 20, "Change Directory", "Click Change Directory"));


		frameComponents.add(new Label("Add Username Label", 50, 110, "Username:"));	
		frameComponents.add(new TextField("Username", 150, 110, "Username", 175, false));
		frameComponents.add(new Label("Add Password Label", 50, 145, "Password:"));
		frameComponents.add(new TextField("Password", 150, 145, "Password", 175, false));
		frameComponents.add(new Button("Pick Staff Button", 50, 190, 80, 20, "Staff", "Click Role Staff"));
		frameComponents.add(new Button("Pick Admin Button", 140, 190, 80, 20, "Admin", "Click Role Admin"));
		frameComponents.add(new Button("Pick Tech Button", 230, 190, 80, 20, "Tech", "Click Role Tech"));

		//		frameComponents.add(new Label("Staff Credential", 50, 110, "Staff credential"));
		//		frameComponents.add(new Label("Staff Username", 50, 130, "Username: " + staffUN));
		//		frameComponents.add(new Label("Staff Password", 50, 150, "Password: " + staffP));
		//		frameComponents.add(new Button("Change Staff Username", 50, 180, 120, 20, "Change Username", "Click Username Staff"));
		//		frameComponents.add(new Button("Change Staff Password", 50, 210, 120, 20, "Change Password", "Click Password Staff"));
		//		
		//		frameComponents.add(new Label("Admin Credential", 250, 110, "Admin credential"));
		//		frameComponents.add(new Label("Admin Username", 250, 130, "Username: " + adminUN));
		//		frameComponents.add(new Label("Admin Password", 250, 150, "Password: " + adminP));
		//		frameComponents.add(new Button("Change Admin Username", 250, 180, 120, 20, "Change Username", "Click Username Admin"));
		//		frameComponents.add(new Button("Change Admin Password", 250, 210, 120, 20, "Change Password", "Click Password Admin"));
		//		
		//		frameComponents.add(new Label("Tech Credential", 450, 110, "Tech credential"));
		//		frameComponents.add(new Label("Tech Username", 450, 130, "Username: " + techUN));
		//		frameComponents.add(new Label("Tech Password", 450, 150, "Password: " + techP));
		//		frameComponents.add(new Button("Change Tech Username", 450, 180, 120, 20, "Change Username", "Click Username Tech"));
		//		frameComponents.add(new Button("Change Tech Password", 450, 210, 120, 20, "Change Password", "Click Password Tech"));

		displayInfo();
		//		frameComponents.add(new Button("Change Credential", 62, 75, 100, 20, "Change Credential", "Click Change Directory"));

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

	private void displayInfo() {
		frameComponents.add(new Label("Username", 25, 230, "Username"));
		frameComponents.add(new Label("Password", 150, 230, "Password"));
		frameComponents.add(new Label("Role", 300, 230, "Role"));

		ArrayList<Credential> credSet = Metadata.getCredentials();
		if (credSet != null) {
			for (int i = page*10; i < credSet.size(); i++) {
				if (i >= 10 * page && i < 10 * (page+1)) {
					Credential credIndex = credSet.get(i);
					frameComponents.add(new Shape("SQUARE", Color.BLACK, 30, 275 + (i%10 * 20) , 700, 18, false));
					frameComponents.add(new Label("Username " + i, 25, 270 + (i%10 *20), credIndex.getUsername()));
					frameComponents.add(new Label("Password " + i, 150, 270 + (i%10 *20), credIndex.getPassword()));
					frameComponents.add(new Label("Role " + i, 300, 270 + (i%10 *20), credIndex.getType()));	
					//					frameComponents.add(new Label("Description " + i, 400, 220 + (i%10*20), listOfItems.get(i).getDescription()));
					frameComponents.add(new Button("Remove " + i, 400, 277 + (i%10 * 20), 60, 15, "Remove", "Click Remove " + credIndex.getUsername()));	
				}
			}
		}
	}

	private String action(String getAction) {
		if (frameComponents.getAlert() != null) {
			if (frameComponents.getAlertCmd().equals("Remove User")){
				if (getAction.equals("Yes")) {
					Metadata.removeCredential(frameComponents.getAlertItem());
					frameComponents = new GUIList<Credential>();
					refreshFrame();
				} else if (getAction.equals("No")) {
					refreshFrame();
				}
			}
			return null;
		} else {
			if (getAction != null) {
				String[] splitAction = getAction.split(" ");
				if (splitAction[0].equals("Click")) {
					if (splitAction[1].equals("Login")) {
						refreshFrame();
						return getAction;
					} else if (splitAction[1].equals("Remove")){
						String[] params = getAction.split("Click Remove ");
						ArrayList<Credential> credSet = Metadata.getCredentials();
						for (Credential x : credSet) {
							if (x.getUsername().equals(params[1])) 
								frameComponents.alertUser("Remove User", 200, 300, "Do you want to remove " + params[1] + "?", "Remove User", "yes/no", x);					
						}
					} else if (splitAction[1].equals("Role")){
						ArrayList<Credential> credSet = Metadata.getCredentials();
						Credential newCred = new Credential(((TextField)frameComponents.get("Username")).getString(), ((TextField)frameComponents.get("Password")).getString(), splitAction[2]);
						if (credSet.contains(newCred)) credSet.remove(newCred);
						credSet.add(newCred);
						Metadata.writeCredential(credSet);
						refreshFrame();
					} else if (splitAction[1].equals("Change")) {
						if (splitAction[2].equals("Directory")) {
							JFileChooser fileChooser = new JFileChooser();
							fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							fileChooser.setAcceptAllFileFilterUsed(false);
							int returnValue = fileChooser.showOpenDialog(null);
							if (returnValue == JFileChooser.APPROVE_OPTION) {
								System.out.println("getCurrentDirectory(): " + fileChooser.getCurrentDirectory());
								System.out.println("getSelectedFile() : " + fileChooser.getSelectedFile());
								dataDir = fileChooser.getSelectedFile();
								HashMap<String, String> configPath = new HashMap<String, String>();
								configPath.put("Dir", dataDir.toString() + "\\");
								Metadata.writeConfig(configPath);
								refreshFrame();
							} else {
								System.out.println("No Selection ");
							}
						}
					} else if (splitAction[1].equals("Username")) {
						frameComponents.alertUser("Change " + splitAction[1], 200, 300, "What would you want to change the " + splitAction[2] +"'s username to?", splitAction[2] + " " + splitAction[1], "confirm");
					} else if (splitAction[1].equals("Password")) {
						frameComponents.alertUser("Change " + splitAction[1], 200, 300, "What would you want to change the " + splitAction[2] +"'s password to?", splitAction[2] + " " + splitAction[1], "confirm");
					} else if (splitAction[1].equals("Logout")) {
						return "Logout";
					}
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
