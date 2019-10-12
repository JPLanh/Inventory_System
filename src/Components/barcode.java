package Components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;


public class barcode {

	private static int VERTICALMINPAD = 15;
	private static int HORIZONTALMINPAD = 20;

	public barcode(boolean sort){
		Code39Bean bean = new Code39Bean();
		final int dpi = 150;
		bean.setModuleWidth(UnitConv.in2mm(2.0f / dpi));
		bean.setWideFactor(3);
		bean.doQuietZone(false);
		
		String path = Metadata.getConfig().get("Dir");
		ArrayList<Item> itemList;
		if (!sort) itemList = Metadata.readItemMeta();
		else itemList = Metadata.sortItem("Name");
		new File("resource").mkdir();
		BitmapCanvasProvider canvas = null;
		OutputStream out = null;
		for (Item x : itemList) {
				try {
					System.out.println(x.getItemName() + " compiled");
					//Add
					out = new FileOutputStream(new File("resource/" + "add_" + x.getId() + ".png"));
					canvas = new BitmapCanvasProvider(out, "image/x-png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
					bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);
					bean.generateBarcode(canvas, x.getAddHex());
					canvas.finish();
					out.close();
					
					//Minus
					out = new FileOutputStream(new File("resource/" + "minus_" + x.getId() + ".png"));
					canvas = new BitmapCanvasProvider(out, "image/x-png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
					bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);
					bean.generateBarcode(canvas, x.getMinusHex());
					canvas.finish();
					out.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					System.out.println("File not found");
//					e.printStackTrace();

				} catch (IOException e) {
					System.out.println("IO EXception 1");
					// TODO Auto-generated catch block
//					e.printStackTrace();
				} finally {
//					try {
//						out.close();
//						canvas.finish();
//					} catch (IOException e) {
//						System.out.println("IO EXception 2");
						// TODO Auto-generated catch block
//						e.printStackTrace();
//				}
			}	       
		}
	}

	public static String getHash(String toHash) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(toHash.getBytes());
			byte[] digest = md.digest();
			return DatatypeConverter.printHexBinary(digest).toUpperCase().substring(0, 10);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}		
	}

	public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
		Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		return dimg;
	}  

	public static boolean deleteDirectory(File dir) 
	{ 
		if (dir.isDirectory()) 
		{ 
			File[] children = dir.listFiles(); 
			for (int i = 0; i < children.length; i++) 
			{ 
				boolean success = deleteDirectory(children[i]); 
				if (!success) 
				{ 
					return false; 
				} 
			} 
		}
		//		System.out.println("removing file or directory : " + dir.getName()); 
		return dir.delete();
	}

	public boolean compile(int xPad, int yPad, boolean sort){

		//Good is xPad = 10, yPad = 30
		int vertPadding = VERTICALMINPAD + xPad;
		int horiPadding = HORIZONTALMINPAD + yPad;

		ArrayList<Item> itemList;
		if (!sort) itemList = Metadata.readItemMeta();
		else itemList = Metadata.sortItem("Name");
		BufferedImage addCombined = new BufferedImage(279*2 + 100, 110 + (itemList.size()/2)*110, BufferedImage.TYPE_INT_ARGB);
		BufferedImage minusCombined = new BufferedImage(279*2 + 100, 110 + (itemList.size()/2)*110, BufferedImage.TYPE_INT_ARGB);
		int count = 0;
		BufferedImage image1;
		BufferedImage image2;
		String path = "";
		if(Metadata.getConfig() != null) path = Metadata.getConfig().get("Dir");
		try {
			for (Item x: itemList) {
				System.out.println(x.getItemName());
				image1 = ImageIO.read(new File("resource/" + "add_"+x.getId()+".png"));
				BufferedImage img1 = resize(image1, (int)(image1.getWidth()*.75), (int)(image1.getHeight()*.75));
				image2 = ImageIO.read(new File("resource/" + "minus_"+x.getId()+".png"));
				BufferedImage img2 = resize(image2, (int)(image2.getWidth()*.75), (int)(image2.getHeight()*.75));

				Graphics g1 = addCombined.getGraphics();
				g1.setColor(new Color(0, 0, 0));
				g1.drawImage(img1, (count%2) * (img1.getWidth() + horiPadding), (count/2) * (img1.getHeight() + vertPadding), null);
				g1.drawString(x.getItemName() + " " + x.getDescription(), (count%2) * (img1.getWidth() + horiPadding), (img1.getHeight() + 13) + ((count/2)) * (img1.getHeight() + vertPadding));
				ImageIO.write(addCombined, "PNG", new File(path + "addInventory.png"));
				
				Graphics g2 = minusCombined.getGraphics();
				g2.setColor(new Color(0, 0, 0));
				g2.drawImage(img2, (count%2) * (img1.getWidth() + horiPadding), (count/2) * (img1.getHeight() + vertPadding), null);
				g2.drawString(x.getItemName() + " " + x.getDescription(), (count%2) * (img1.getWidth() + horiPadding), (img1.getHeight() + 13) + ((count/2)) * (img1.getHeight() + vertPadding));
				ImageIO.write(minusCombined, "PNG", new File(path + "minusInventory.png"));
				count++;
			}
			deleteDirectory(new File("resource"));
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

}
