package Components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import javax.imageio.ImageIO;
import javax.swing.*;

//import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class Metadata {

	private static CMYKColor lightGreen = new CMYKColor(100, 0, 100, 7);
    private static CMYKColor darkGreen = new CMYKColor(100, 0, 100, 50);
    private static CMYKColor black = new CMYKColor(100, 100, 100, 100);
    
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

	public static void formatList() {
		ArrayList<Item> itemList = readItemMeta();		
		for (Item x : itemList) {
			if (x.getAddHex() == null) {
				System.out.println("Hex added for add " + x.getItemName());
				x.setAddHex(barcode.getHash("add" + " " + x.getItemName() + " " + x.getDescription()));
			}
			if (x.getMinusHex() == null) {
				System.out.println("Hex added for minus " + x.getItemName());
				x.setMinusHex(barcode.getHash("minus" + " " + x.getItemName() + " " + x.getDescription()));
			}
		}
		writeItemMeta(itemList);
	}
	
	public static ArrayList<Item> sortItem(String filter) {
		ArrayList<Item> itemList = readItemMeta();		
		Collections.sort(itemList);
		return itemList;		
	}

	public static Item getItemByID(int getID) 
	{
		ArrayList<Item> itemList = readItemMeta();
		for (Item x: itemList) {
			if (x.getId() == getID) return x;
		}
		return null;	
	}

	public static boolean removeCredential(Credential getCredential) 
	{
		ArrayList<Credential> credList = getCredentials();
		if (credList.contains(getCredential)) {
			credList.remove(getCredential);
			writeCredential(credList);
			return true;
		}
		return false;
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
			System.out.println("Onee");
			return true;
		}
		System.out.println("Twwoo");
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
            SimpleDateFormat displayFormatter = new SimpleDateFormat("MM-dd-yyyy hh:mm");
			if (getTransaction.getModifier().equals("add")) {
				ArrayList<Transaction> transactionList = readTransactionMeta();
				if (transactionList.size() == 0) getTransaction.setId(1);
				else getTransaction.setId(transactionList.get(transactionList.size()-1).getId()+1);
				getTransaction.setDate(displayFormatter.format(new Date()));
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
				getTransaction.setDate(displayFormatter.format(new Date()));
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

    public static boolean compileInventory(boolean sort) {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);


        try {
            SimpleDateFormat displayFormatter = new SimpleDateFormat("MM-dd-yyyy hh:mm");
            SimpleDateFormat localFormatter = new SimpleDateFormat("MM-dd-yyyy_hh_mm_ss");
            SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy");
            String path = "";
            if(getConfig() != null) path = getConfig().get("Dir");
            try{
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path + "Inventory/Inventory_Log (" + localFormatter.format(new Date()) + ").pdf"));
            } catch (FileNotFoundException e4){
            	new File(path + "Transaction").mkdir();
                try {
                    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path + "Inventory/Inventory_Log (" + localFormatter.format(new Date()) + ").pdf"));
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            document.open();

            document.add(new Paragraph("Cabrillo's Student Material Atheltic Renting Transaction", FontFactory.getFont(FontFactory.COURIER, 16, Font.BOLD, lightGreen)));
            document.add(new Paragraph("Outstanding summary renting report" , FontFactory.getFont(FontFactory.COURIER, 12, Font.BOLD, darkGreen)));
            document.add(new Paragraph(displayFormatter.format(new Date()) , FontFactory.getFont(FontFactory.COURIER, 12, Font.BOLD, darkGreen)));
    		ArrayList<Item> itemList;
    		if (!sort) itemList = Metadata.readItemMeta();
    		else itemList = Metadata.sortItem("Name");
			for (Item x : itemList) {
				;
				String str = String.format("%-8d %-10d %-20s %-30s", x.getId(), x.getAmount(), x.getItemName(), x.getDescription());
				document.add(new Paragraph(str , FontFactory.getFont(FontFactory.COURIER, 10, Font.BOLD, black)));
			}
            document.close();
            return true;
        } catch (DocumentException e3) {
            // TODO Auto-generated catch block
            e3.printStackTrace();
        }
        return false;
    }	

    public static boolean compileTransaction() {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        try {
            SimpleDateFormat displayFormatter = new SimpleDateFormat("MM-dd-yyyy hh:mm");
            SimpleDateFormat localFormatter = new SimpleDateFormat("MM-dd-yyyy_hh_mm_ss");
            SimpleDateFormat yearFormatter = new SimpleDateFormat("yyyy");
            String path = "";
            if(getConfig() != null) path = getConfig().get("Dir");
            try{
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path + "Transaction/Transaction_Log (" + localFormatter.format(new Date()) + ").pdf"));
            } catch (FileNotFoundException e4){
            	new File(path + "Transaction").mkdir();
                try {
                    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path + "Transaction/Transaction_Log (" + localFormatter.format(new Date()) + ").pdf"));
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            document.open();

            document.add(new Paragraph("Cabrillo's Student Material Atheltic Renting Transaction", FontFactory.getFont(FontFactory.COURIER, 16, Font.BOLD, lightGreen)));
            document.add(new Paragraph("Outstanding summary renting report" , FontFactory.getFont(FontFactory.COURIER, 12, Font.BOLD, darkGreen)));
            document.add(new Paragraph(displayFormatter.format(new Date()) , FontFactory.getFont(FontFactory.COURIER, 12, Font.BOLD, darkGreen)));
    		ArrayList<Transaction> transactionList = Metadata.readTransactionMeta();
			for (Transaction x : transactionList) {
				String str = String.format("%-8s %-20s %-8s %-20s %-30s", Integer.toString(x.getId()), x.getDate(), x.getModifier(), x.getItemName(), x.getDescription());
				document.add(new Paragraph(str , FontFactory.getFont(FontFactory.COURIER, 10, Font.BOLD, black)));
			}
            document.close();
            return true;
        } catch (DocumentException e3) {
            // TODO Auto-generated catch block
            e3.printStackTrace();
        }
        return false;
    }
}
