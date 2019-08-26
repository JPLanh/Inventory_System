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
	GUIList frameComponents = new GUIList();
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
		frameComponents = new GUIList();
		frameComponents.add(new Shape("SQUARE", Color.WHITE, 25, 25, 725, 500, true));
		frameComponents.add(new Button("Logout", 650, 35, 80, 20, "Logout", "Click Logout"));

		frameComponents.add(new Label("Database Directory", 50, 50, "Database Directory:   " + dataDir));
		frameComponents.add(new Button("Change Directory", 62, 75, 100, 20, "Change Directory", "Click Change Directory"));

		frameComponents.add(new Label("Staff Credential", 50, 110, "Staff credential"));
		frameComponents.add(new Label("Staff Username", 50, 130, "Username: " + staffUN));
		frameComponents.add(new Label("Staff Password", 50, 150, "Password: " + staffP));
		frameComponents.add(new Button("Change Staff Username", 50, 180, 120, 20, "Change Username", "Click Username Staff"));
		frameComponents.add(new Button("Change Staff Password", 50, 210, 120, 20, "Change Password", "Click Password Staff"));
		
		frameComponents.add(new Label("Admin Credential", 250, 110, "Admin credential"));
		frameComponents.add(new Label("Admin Username", 250, 130, "Username: " + adminUN));
		frameComponents.add(new Label("Admin Password", 250, 150, "Password: " + adminP));
		frameComponents.add(new Button("Change Admin Username", 250, 180, 120, 20, "Change Username", "Click Username Admin"));
		frameComponents.add(new Button("Change Admin Password", 250, 210, 120, 20, "Change Password", "Click Password Admin"));
		
		frameComponents.add(new Label("Tech Credential", 450, 110, "Tech credential"));
		frameComponents.add(new Label("Tech Username", 450, 130, "Username: " + techUN));
		frameComponents.add(new Label("Tech Password", 450, 150, "Password: " + techP));
		frameComponents.add(new Button("Change Tech Username", 450, 180, 120, 20, "Change Username", "Click Username Tech"));
		frameComponents.add(new Button("Change Tech Password", 450, 210, 120, 20, "Change Password", "Click Password Tech"));

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

	private String action(String getAction) {
		if (getAction != null) {
			String[] splitAction = getAction.split(" ");
			if (splitAction[0].equals("Confirm")) {
				if (splitAction[1].equals("Yes")) {
					if (splitAction[3].equals("Staff") && splitAction[4].equals("Username")) {
						staffUN = splitAction[2];
					} else if (splitAction[3].equals("Staff") && splitAction[4].equals("Password")) {
						staffP = splitAction[2];
					} else if (splitAction[3].equals("Admin") && splitAction[4].equals("Username")) {
						adminUN = splitAction[2];
					} else if (splitAction[3].equals("Admin") && splitAction[4].equals("Password")) {
						adminP = splitAction[2];
					} else if (splitAction[3].equals("Tech") && splitAction[4].equals("Username")) {
						techUN = splitAction[2];
					} else if (splitAction[3].equals("Tech") && splitAction[4].equals("Password")) {
						techP = splitAction[2];
					}
					ArrayList<Credential> credSet = new ArrayList<Credential>();
					credSet.add(new Credential(staffUN, staffP, "Staff"));
					credSet.add(new Credential(adminUN, adminP, "Admin"));
					credSet.add(new Credential(techUN, techP, "Tech"));
					Metadata.writeCredential(credSet);
					frameComponents.alertConfirm();
					refreshFrame();					
				} else if (splitAction[1].equals("No")){
					frameComponents.alertConfirm();
					refreshFrame();
				}
			} else if (splitAction[0].equals("Click")) {
				if (splitAction[1].equals("Login")) {
					refreshFrame();
					return getAction;
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
					frameComponents.alertUser("Change " + splitAction[1], 200, 300, "What would you want to change the " + splitAction[2] +"'s username to?", splitAction[2] + " " + splitAction[1], true);
				} else if (splitAction[1].equals("Password")) {
					frameComponents.alertUser("Change " + splitAction[1], 200, 300, "What would you want to change the " + splitAction[2] +"'s password to?", splitAction[2] + " " + splitAction[1], true);
				} else if (splitAction[1].equals("Logout")) {
					return "Logout";
				}
			}
		}
		return null;
	}


	
	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

}
