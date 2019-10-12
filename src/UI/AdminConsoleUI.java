package UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import Components.*;

public class AdminConsoleUI implements UIFrame{

	GUIList<Item> frameComponents = new GUIList<Item>();
	boolean item = false;
	boolean sort = false;
	private boolean search = false;
	private int page = 0;

	AdminConsoleUI(){
		refreshFrame();
	}

	private void refreshFrame() {
		frameComponents = new GUIList<Item>();
		frameComponents.add(new Shape("SQUARE", Color.WHITE, 25, 25, 725, 500, true));
		frameComponents.add(new Button("Logout", 650, 35, 80, 20, "Logout", "Click Logout"));

		//		frameComponents.add(new Label("Inventory", 75, 200, "Inventory", 16));
		//		frameComponents.add(new Label("Description Label", 250, 200, "Description", 16));
		//		displayInfo();
		if (!search) {
			frameComponents.add(new Label("QR Code Label", 50, 125, "QR Code:"));
			frameComponents.add(new TextField("QR Code", 150, 125, "QR Code", 200, false));
			frameComponents.add(new Button("Click Check Item", 140, 260, 0, 0, "Check Student", "Click Check Item"));
			frameComponents.add(new Button("Inventory List", 75, 175, 125, 20, "Inventory", "Click Change Inventory Button"));
			frameComponents.add(new Button("Transaction List", 200, 175, 125, 20, "Tranasction", "Click Change Transaction Button", new Color(188, 201, 155), new Color(255, 255, 255)));
			frameComponents.add(new Label("Transaction ID", 25, 200, "Transaction ID"));
			frameComponents.add(new Label("Modifer", 150, 200, "Modifier"));
			frameComponents.add(new Label("Item", 225, 200, "Item"));
			frameComponents.add(new Label("Description", 375, 200, "Description"));
			frameComponents.add(new Label("Date", 550, 200, "Date"));
			displayInfo();

			frameComponents.add(new Button("Print Transaction", 400, 435, 150, 25, "Print Transactions", "Click Print Transaction"));

			frameComponents.setFocus("QR Code");
		} else {
			frameComponents.add(new Label("Item Label", 50, 75, "Item:"));	
			frameComponents.add(new TextField("Item", 150, 75, "Item", 175, false));
			frameComponents.add(new Label("Size Label", 50, 125, "Size:"));
			frameComponents.add(new TextField("Description", 150, 125, "Description", 175, false));
			frameComponents.add(new Button("Add Item", 350, 125, 100, 20, "Add Item", "Click Add Item Button"));

			frameComponents.add(new Button("Inventory List", 75, 175, 125, 20, "Inventory", "Click Change Inventory Button", new Color(188, 201, 155), new Color(255, 255, 255)));
			frameComponents.add(new Button("Transaction List", 200, 175, 125, 20, "Tranasction", "Click Change Transaction Button"));
			frameComponents.add(new Label("Amount", 25, 200, "Amount"));
			frameComponents.add(new Label("Item", 200, 200, "Item"));
			frameComponents.add(new Label("Description", 400, 200, "Description"));

			frameComponents.add(new Label("Sort by", 550, 100, "Sort by:"));
			frameComponents.add(new Button("Sort name", 515, 125, 125, 20, "By name", "Click Sort Name"));
			frameComponents.add(new Button("Sort Description", 515, 150, 125, 20, "By description", "Click Sort Description"));
			displayInfo();

			frameComponents.add(new Button("Compile Barcode", 50, 435, 125, 20, "Compile Barcode", "Click Compile Barcode Button"));
			frameComponents.add(new Button("Print Inventory", 400, 435, 150, 25, "Print Inventory", "Click Print Inventory"));

			frameComponents.setFocus("Item");
		}
	}

	private void displayInfo() {
		if (!search) {
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
			}
			if ((page+1)*10 < listOfTransaction.size()) frameComponents.add(new Button("Next Page", 700, 435, 25, 25, ">", "Click Next Page"));
		} else {
			ArrayList<Item> listOfItems;
			if (!sort) listOfItems = Metadata.readItemMeta();
			else listOfItems = Metadata.sortItem("Name");
			if (listOfItems != null) {
				for (int i = page*10; i < listOfItems.size(); i++) {
					if (i >= 10 * page && i < 10 * (page+1)) {
						frameComponents.add(new Shape("SQUARE", Color.BLACK, 30, 225 + (i%10 * 20) , 700, 18, false));
						frameComponents.add(new Label("Amount " + i, 25, 220 + (i%10*20), Integer.toString(listOfItems.get(i).getAmount())));
						frameComponents.add(new Label("Item " + i, 200, 220 + (i%10*20), listOfItems.get(i).getItemName()));
						frameComponents.add(new Label("Description " + i, 400, 220 + (i%10*20), listOfItems.get(i).getDescription()));
						frameComponents.add(new Button("Remove " + i, 115, 227 + (i%10 * 20), 60, 15, "Remove", "Click Remove " + listOfItems.get(i).getId() + " " + sort));
					}
				}
			}
			if ((page+1)*10 < listOfItems.size()) frameComponents.add(new Button("Next Page", 700, 435, 25, 25, ">", "Click Next Page"));
		}

		if (page != 0) frameComponents.add(new Button("Previous Page", 600, 435, 25, 25, "<", "Click Previous Page"));
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
			if (frameComponents.getAlertCmd().equals("Remove Confirmation")){
				if (getAction.equals("Yes")) {
					Metadata.removeItem(frameComponents.getAlertItem());
					frameComponents = new GUIList<Item>();
					refreshFrame();
					return null;
				} else if (getAction.equals("No")) {
					refreshFrame();
					return null;
				}
//				if (getAction.equals("Yes"));
			}
		} else {
			if (getAction != null) {			
				String[] splitAction = getAction.split(" ");
				try {
					if (splitAction[0].equals("Click")) {
						if (splitAction[1].equals("Add")) {
							Item newItem = new Item(((TextField)frameComponents.get("Item")).getString(), ((TextField)frameComponents.get("Description")).getString());	
							System.out.println("new item: " + newItem);
							if (!Metadata.addItem(newItem)) {
								frameComponents.alertUser("Item already exist", 200, 300, "Item already exists", "Item already exist", "confirm");
							} else {
								frameComponents = new GUIList<Item>();
								refreshFrame();
							}
						} else if (splitAction[1].equals("Sort")) {
							sort = true;
							refreshFrame();
						} else if (splitAction[1].equals("Logout")) {
							return "Logout";
						} else if (splitAction[1].equals("Remove")) {
							Item getItem = Metadata.getItemByID(Integer.parseInt(splitAction[2]));
							if(getItem != null) {
								frameComponents.alertUser("Remove Confirmation", 200, 300, "Do you really want to remove " + getItem.toString(), "Remove Confirmation", "yes/no", getItem);							
							}
						}  else if (splitAction[1].equals("Compile")) {
							System.out.println("Compile");
							if(new barcode(sort).compile(10, 30, sort)) {
								frameComponents.alertUser("Barcode Confirmation", 200, 300, "Compilation of the barcode has been created.", "Barcode Confirmation", "confirm");
							}
						} else if (splitAction[1].equals("CurrentInv")) {
							if(new barcode(sort).compile(10, 30, sort)) {
								frameComponents.alertUser("Barcode Confirmation", 200, 300, "Compilation of the barcode has been created.", "Barcode Confirmation", "confirm");
							}
						} else if (splitAction[1].equals("Change")) {
							if (splitAction[2].equals("Inventory")) {
								search = true;
								page = 0;
								refreshFrame();
							} else if (splitAction[2].equals("Transaction")) {
								search = false;
								page = 0;
								refreshFrame();
							}
						} else if (splitAction[1].equals("Print")) {
							if (splitAction[2].equals("Inventory")) {
								Metadata.compileInventory(sort);
								frameComponents.alertUser("Item Log", 200, 300, "Inventory log has been compiled in the Inventory folder", "Item Log", "confirm");
								frameComponents.setFocus("Item Log");
							} else if (splitAction[2].equals("Transaction")) {
								Metadata.compileTransaction();
								frameComponents.alertUser("Transaction Log", 200, 300, "Transaction log has been compiled into Transaction_log.png", "Transaction Log", "confirm");
								frameComponents.setFocus("Transaction Log");
							}
						} else if (splitAction[1].equals("Next")){
							page++;
							refreshFrame();
						} else if (splitAction[1].equals("Previous")){
							page--;
							refreshFrame();
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
					}  else if (splitAction[0].equals("Remove")) {
						UIComponent grabComponent = frameComponents.get("Remove " + splitAction[1] + " Button");
						frameComponents.add(new Button("Accept " + splitAction[1] + " Button", grabComponent.getXPos() + 75, grabComponent.getYPos(), 65, 16, "Confirm", "Accept " + splitAction[1] + " Button", new Color(8, 81, 0), new Color(255, 255, 255), 14));
						frameComponents.add(new Button("Reject " + splitAction[1] + " Button", grabComponent.getXPos(), grabComponent.getYPos(), 65, 16, "Cancel", "Reject " + splitAction[1] + " Button", new Color(8, 81, 0), new Color(255, 255, 255), 14));
					}  else if (splitAction[0].equals("Reject")) {
						frameComponents.remove("Accept " + splitAction[1] + " Button");
						frameComponents.remove("Reject " + splitAction[1] + " Button");
					}  else if (splitAction[0].equals("Accept")) {
						ArrayList<Item> itemList = Metadata.readItemMeta();
						Item getItem = itemList.get(Integer.parseInt(splitAction[1]));
						frameComponents.remove("Accept " + splitAction[1] + " Button");
						frameComponents.remove("Reject " + getItem + " Button");
						Metadata.removeItem(getItem);
						frameComponents = new GUIList<Item>();
						refreshFrame();
					}
//					else if (splitAction[0].equals("Confirm")) {
//						if (splitAction[1].equals("Yes")) {
//							System.out.println(getAction);
//							frameComponents.alertConfirm();
//						}
//					}
					return null;
				} catch (StringIndexOutOfBoundsException e) {
					return getAction;
				} catch (NullPointerException e) {
					System.out.println("Null");
					return null;
				}
			}
			return null;
		}
		return null;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

}
