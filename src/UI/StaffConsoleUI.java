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

import Components.*;

public class StaffConsoleUI implements UIFrame{

	GUIList frameComponents = new GUIList();
	Boolean search = true;
	private int page = 0;
	boolean sort = false;

	StaffConsoleUI(){
		refreshFrame();
	}

	private void refreshFrame() {
		frameComponents = new GUIList();
		frameComponents.add(new Shape("SQUARE", Color.WHITE, 25, 25, 725, 500, true));
		frameComponents.add(new Button("Logout", 650, 35, 80, 20, "Logout", "Click Logout"));

		frameComponents.add(new Label("QR Code Label", 50, 125, "QR Code:"));
		frameComponents.add(new TextField("QR Code", 150, 125, "QR Code", 300, false));
		frameComponents.add(new Button("Click Check Item", 140, 260, 0, 0, "Check Student", "Click Check Item"));

		if (!search) {
			frameComponents.add(new Label("Transaction Log", 25, 175, "Transaction Log"));
			frameComponents.add(new Label("Transaction ID", 25, 200, "Transaction ID"));
			frameComponents.add(new Label("Modifer", 150, 200, "Modifier"));
			frameComponents.add(new Label("Item", 225, 200, "Item"));
			frameComponents.add(new Label("Description", 375, 200, "Description"));
			frameComponents.add(new Label("Date", 550, 200, "Date"));
			displayInfo();

			frameComponents.add(new Button("Search Inventory", 480, 130, 100, 25, "Inventory", "Click Search Inventory"));

			frameComponents.setFocus("QR Code");
		} else {
			frameComponents.add(new Label("Inventory", 25, 175, "Inventory"));
			frameComponents.add(new Label("Amount", 25, 200, "Amount"));
			frameComponents.add(new Label("Item", 125, 200, "Item"));
			frameComponents.add(new Label("Description", 300, 200, "Description"));
			displayInfo();

			frameComponents.add(new Button("Checkout", 480, 130, 100, 25, "Checkout", "Click Checkout"));


			frameComponents.setFocus("QR Code");
		}

		frameComponents.add(new Button("Print Inventory", 590, 435, 100, 25, "Print Inventory", "Click Print Inventory"));
		frameComponents.add(new Button("Print Transaction", 480, 435, 100, 25, "Print Log", "Click Print Transaction"));
	}

	private void displayInfo() {
		ArrayList<Transaction> listOfTransaction = Metadata.readTransactionMeta();
		if (listOfTransaction != null) {
			for (int i = page*10; i < listOfTransaction.size(); i++) {
				if (i >= 10 * page && i < 10 * (page+1)) {
					Transaction transIndex = listOfTransaction.get(i);
					frameComponents.add(new Shape("SQUARE", Color.BLACK, 30, 225 + (i%10 * 20) , 700, 18, false));
					frameComponents.add(new Label("Tranasction ID " + i, 25, 220 + (i%10 *20), Integer.toString(transIndex.getId())));
					frameComponents.add(new Label("Modifier " + i, 150, 220 + (i%10 *20), transIndex.getModifier()));
					frameComponents.add(new Label("Item " + i, 225, 220 + (i%10 *20), transIndex.getItemName()));
					frameComponents.add(new Label("Description " + i, 375, 220 + (i%10 *20), transIndex.getDescription()));
					frameComponents.add(new Label("Rented Date " + i, 550, 220 + (i%10 *20), transIndex.getDate()));		
				}
			}
			if ((page+1)*10 < listOfTransaction.size()) frameComponents.add(new Button("Next Page", 700, 435, 25, 25, ">", "Click Next Page"));
		} else {
			ArrayList<Item> listOfItems = Metadata.readItemMeta();
			if (listOfItems != null) {
				for (int i = 0; i < listOfItems.size(); i++) {
					frameComponents.add(new Shape("SQUARE", Color.BLACK, 30, 225 + (i * 20) , 700, 18, false));
					frameComponents.add(new Label("Amount " + i, 25, 220 + (i*20), Integer.toString(listOfItems.get(i).getAmount())));
					frameComponents.add(new Label("Item " + i, 125, 220 + (i*20), listOfItems.get(i).getItemName()));
					frameComponents.add(new Label("Description " + i, 300, 220 + (i*20), listOfItems.get(i).getDescription()));
				}
			}
			if ((page+1)*10 < listOfItems.size()) frameComponents.add(new Button("Next Page", 700, 435, 25, 25, ">", "Click Next Page"));
		}
		if (page != 0) frameComponents.add(new Button("Back Page", 600, 435, 25, 25, "<", "Click Back Page"));
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
					frameComponents.alertConfirm();
					refreshFrame();
				}
			} else if (splitAction[0].equals("Click")) {
				if (splitAction[1].equals("Login")) {
					refreshFrame();
					return getAction;
				} else if (splitAction[1].equals("Next")) {
					page++;
					refreshFrame();
					return getAction;
				} else if (splitAction[1].equals("Back")) {
					page--;
					refreshFrame();	
					return getAction;
				} else if (splitAction[1].equals("Logout")) {
					return "Logout";
				}  else if (splitAction[1].equals("Print")) {
					if (splitAction[2].equals("Inventory")) {
						Metadata.compileInventory(sort);
						frameComponents.alertUser("Item Log", 200, 300, "Inventory log has been compiled in the Inventory folder", "Item Log", "confirm");
						frameComponents.setFocus("Item Log");
					} else if (splitAction[2].equals("Transaction")) {
						Metadata.compileTransaction();
						frameComponents.alertUser("Transaction Log", 200, 300, "Transaction log has been compiled into Transaction_log.png", "Transaction Log", "confirm");
						frameComponents.setFocus("Transaction Log");
					}
				} else if (splitAction[1].equals("Search")) {
					search = true;
					page = 0;
					refreshFrame();
					return getAction;
				} else if (splitAction[1].equals("Checkout")) {
					search = false;
					page = 0;
					refreshFrame();
					return getAction;
				} else if (splitAction[1].equals("Check")) {
					if (splitAction[2].equals("Student")) {
						refreshFrame();
						return getAction;
					} else if (splitAction[2].equals("Item")) {
						ArrayList<Item> itemList = Metadata.readItemMeta();
						if (itemList.contains(new Item(((TextField)frameComponents.get("QR Code")).getString())))
						{
							Item getItem = itemList.get(itemList.indexOf(new Item(((TextField)frameComponents.get("QR Code")).getString())));
							SimpleDateFormat formatter= new SimpleDateFormat("MM-dd-yyyy  HH:mm:ss z");  
							Date date = new Date(System.currentTimeMillis());    
							String mod = null;
							if (barcode.getHash("add " + Integer.toString(getItem.getId())).equals(((TextField)frameComponents.get("QR Code")).getString())) {
								mod = "add";
							} else if (barcode.getHash("minus " + Integer.toString(getItem.getId())).equals(((TextField)frameComponents.get("QR Code")).getString())) {
								mod = "minus";
							}
							Transaction newTran = new Transaction(getItem.getItemName(), getItem.getDescription(), mod, formatter.format(date));
							if (Metadata.addTransaction(newTran)) {
								refreshFrame();
								return null;
							} else {
								frameComponents.alertUser("Item Insufficient", 200, 300, "There are no more " + getItem.getDescription() + " " + getItem.getItemName() + " in stock", "Item not in stock", "confirm");
								frameComponents.setFocus("Item Insufficient");
								return null;						
							}
						} else 
						{
							frameComponents.alertUser("Confirm transaction", 200, 300, "Item does not exist", "Item does not exist", "confirm");
							frameComponents.setFocus("Confirm transaction");
							return null;						
						}
					}
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
