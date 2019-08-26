package Components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import javax.imageio.ImageIO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class Metadata {
	
	public static HashMap<String, String> getConfig(){
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting().serializeNulls();
		Gson gson = builder.create();
		try {
			return (HashMap<String, String>) gson.fromJson(new FileReader("Config.data"), new TypeToken<HashMap<String, String>>(){}.getType());
		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			BufferedWriter writer;
			try {
				writer = new BufferedWriter(new FileWriter("Config.data"));
				writer.append(gson.toJson(new HashMap<String, String>().put("Dir", "")));
				writer.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
			}
			return null;
		}
	}
	
	public static void writeConfig(HashMap<String, String> getConfig) {
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting().serializeNulls();
		Gson gson = builder.create();
		try 
		{
			ArrayList<Credential> credSet = getCredentials();
			BufferedWriter writer;
			writer = new BufferedWriter(new FileWriter("Config.data"));	
			writer.append(gson.toJson(getConfig));
			writer.close();
			if (credSet != null)writeCredential(credSet);
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void writeCredential(ArrayList<Credential> credSet) {
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting().serializeNulls();
		Gson gson = builder.create();
		try 
		{
			BufferedWriter writer;
			String path = "";
			if (getConfig() != null) path = getConfig().get("Dir");
			writer = new BufferedWriter(new FileWriter(path + "Credential.data"));	
			writer.append(gson.toJson(credSet));
			writer.close();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Credential> getCredentials(){
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting().serializeNulls();
		Gson gson = builder.create();
		String path = "";
		if(getConfig() != null) path = getConfig().get("Dir");
		try {
			return (ArrayList<Credential>) gson.fromJson(new FileReader(path + "Credential.data"), new TypeToken<ArrayList<Credential>>(){}.getType());
		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			BufferedWriter writer;
			try {
				writer = new BufferedWriter(new FileWriter(path + "Credential.data"));
				writer.append(gson.toJson(new ArrayList<Credential>()));
				writer.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Item> readItemMeta() {
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting().serializeNulls();
		Gson gson = builder.create();
		String path = "";
		if(getConfig() != null) path = getConfig().get("Dir");
		try {
			return (ArrayList<Item>) gson.fromJson(new FileReader(path + "Item.data"), new TypeToken<ArrayList<Item>>(){}.getType());
		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			BufferedWriter writer;
			try {
				writer = new BufferedWriter(new FileWriter(path + "Item.data"));
				writer.append(gson.toJson(new ArrayList<Transaction>()));
				writer.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
			return null; 
		}
	}
	
	public static ArrayList<Item> sortItem(String filter) {
		ArrayList<Item> itemList = readItemMeta();		
		Collections.sort(itemList);
		return itemList;		
	}

	public static boolean removeItem(Item getItem) 
	{
		ArrayList<Item> itemList = readItemMeta();
		if (itemList.contains(getItem)) {
			itemList.remove(getItem);
			writeItemMeta(itemList);
			return true;
		}
		return false;
	}

	public static boolean addItem(Item getItem)
	{
		ArrayList<Item> itemList = readItemMeta();
		if (!itemList.contains(getItem)) {
			if (itemList.size() == 0) getItem.setId(1);
			else getItem.setId(itemList.get(itemList.size()-1).getId()+1);
			getItem.setAmount(0);
			itemList.add(getItem);
			writeItemMeta(itemList);
			return true;
		}
		return false;
	}

	private static void writeItemMeta(ArrayList<Item> getList) {
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting().serializeNulls();
		Gson gson = builder.create();
		try 
		{
			BufferedWriter writer;
			String path = "";
			if(getConfig() != null) path = getConfig().get("Dir");
			writer = new BufferedWriter(new FileWriter(path + "Item.data"));	
			writer.append(gson.toJson(getList));
			writer.close();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}		
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Transaction> readTransactionMeta() {
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting().serializeNulls();
		Gson gson = builder.create();
		String path = "";
		if(getConfig() != null) path = getConfig().get("Dir");
		try {
			return (ArrayList<Transaction>) gson.fromJson(new FileReader(path + "Transaction.data"), new TypeToken<ArrayList<Transaction>>(){}.getType());
		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			BufferedWriter writer;
			ArrayList<Transaction> newTrans = new ArrayList<Transaction>();
			try {
				writer = new BufferedWriter(new FileWriter(path +"Transaction.data"));
				writer.append(gson.toJson(newTrans));
				writer.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
			return newTrans; 
		}
	}

	public static boolean addTransaction(Transaction getTransaction)
	{
		ArrayList<Item> itemList = readItemMeta();
		if (itemList.contains(getTransaction.getItem())) {
			int itemIndex = itemList.indexOf(getTransaction.getItem());
			Item getItem = itemList.get(itemIndex);
			if (getTransaction.getModifier().equals("add")) {
				ArrayList<Transaction> transactionList = readTransactionMeta();
				if (transactionList.size() == 0) getTransaction.setId(1);
				else getTransaction.setId(transactionList.get(transactionList.size()-1).getId()+1);
				getTransaction.setItem(getItem);
				transactionList.add(getTransaction);
				writeTransactionMeta(transactionList);
				getItem.setAmount(getItem.getAmount()+1);			
				itemList.set(itemIndex, getItem);

				writeItemMeta(itemList);
				return true;
			}
			
			if (getTransaction.getModifier().equals("minus") && getItem.getAmount() > 0) {
				ArrayList<Transaction> transactionList = readTransactionMeta();
				if (transactionList.size() == 0) getTransaction.setId(0);
				else getTransaction.setId(transactionList.get(transactionList.size()-1).getId()+1);
				getTransaction.setItem(getItem);
				transactionList.add(getTransaction);
				writeTransactionMeta(transactionList);
				getItem.setAmount(getItem.getAmount()-1);			
				itemList.set(itemIndex, getItem);

				writeItemMeta(itemList);
				return true;			
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private static void writeTransactionMeta(ArrayList<Transaction> getList) {
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting().serializeNulls();
		Gson gson = builder.create();
		try 
		{
			BufferedWriter writer;
			String path = "";
			if(getConfig() != null) path = getConfig().get("Dir");
			writer = new BufferedWriter(new FileWriter(path + "Transaction.data"));	
			writer.append(gson.toJson(getList));
			writer.close();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}		
	}
	
	public static boolean compileInventory(boolean sort){

		//Good is xPad = 10, yPad = 30
		int vertPadding = 10;
		int horiPadding = 30;

		ArrayList<Item> itemList;
		if (!sort) itemList = Metadata.readItemMeta();
		else itemList = Metadata.sortItem("Name");
		BufferedImage compList = new BufferedImage(2480 , 1000 , BufferedImage.TYPE_INT_ARGB);
		Graphics g1 = compList.getGraphics();
		g1.setFont(new Font(g1.getFont().getFontName(), Font.PLAIN, 16)); 
		g1.setColor(new Color(0, 0, 0));
		SimpleDateFormat display= new SimpleDateFormat("MM-dd-yyyy   HH:mm:ss z");  
		SimpleDateFormat formatter= new SimpleDateFormat("MM-dd-yyyy HH_mm_ss");  
		Date date = new Date(System.currentTimeMillis());    
		String path = "";
		if(getConfig() != null) path = getConfig().get("Dir");

		g1.drawLine(25, 25, 1240, 25);
		g1.drawString("Item ID", 25, 23);
		g1.drawString("Amount", 125, 23);
		g1.drawString("Item Name", 250, 23);
		g1.drawString("Description", 375, 23);
		g1.drawString(display.format(date), 800, 23);
		int count = 0;
		try {
			for (Item x : itemList) {
				g1.drawLine(25, 53 + count*30, 1240, 53 + count*30);
				g1.drawString(Integer.toString(x.getId()), 25, 50 + count*30);
				g1.drawString(Integer.toString(x.getAmount()), 125, 50 + count*30);
				g1.drawString(x.getItemName(), 250, 50 + count*30);
				g1.drawString(x.getDescription(), 375, 50 + count*30);
				ImageIO.write(compList, "PNG", new File(path + "Inventory/Inventory_Log (" + formatter.format(date) + ").png"));
				count++;
			}
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public static boolean compileTransaction(){

		//Good is xPad = 10, yPad = 30
		int vertPadding = 10;
		int horiPadding = 30;

		ArrayList<Transaction> transactionList = Metadata.readTransactionMeta();
		BufferedImage compList = new BufferedImage(2480 , 1000 , BufferedImage.TYPE_INT_ARGB);
		Graphics g1 = compList.getGraphics();
		g1.setFont(new Font(g1.getFont().getFontName(), Font.PLAIN, 16)); 
		g1.setColor(new Color(0, 0, 0));
		SimpleDateFormat display= new SimpleDateFormat("MM-dd-yyyy   HH:mm:ss z");  
		SimpleDateFormat formatter= new SimpleDateFormat("MM-dd-yyyy HH_mm_ss");  
		Date date = new Date(System.currentTimeMillis());    		
		String path = "";
		if(getConfig() != null) path = getConfig().get("Dir");
		new File(path + "Transaction").mkdir();

		g1.drawLine(25, 25, 1240, 25);
		g1.drawString("Transaction", 25, 23);
		g1.drawString("Date", 125, 23);
		g1.drawString("Modifier", 375, 23);
		g1.drawString("Item Name", 475, 23);
		g1.drawString("Description", 600, 23);
		g1.drawString(display.format(date), 800, 23);
		int count = 0;
		try {
			for (Transaction x : transactionList) {
				g1.drawLine(25, 53 + count*30, 1240, 53 + count*30);
				g1.drawString(Integer.toString(x.getId()), 25, 50 + count*30);
				g1.drawString(x.getDate(), 125, 50 + count*30);
				g1.drawString(x.getModifier(), 375, 50 + count*30);
				g1.drawString(x.getItemName(), 475, 50 + count*30);
				g1.drawString(x.getDescription(), 600, 50 + count*30);
				ImageIO.write(compList, "PNG", new File(path + "Transaction/Transaction_Log (" + formatter.format(date) + ").png"));
				count++;
			}
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}
