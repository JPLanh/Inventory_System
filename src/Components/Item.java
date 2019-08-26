package Components;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class Item  implements Comparable<Item>{

	private int id;
	private String itemName;
	private String description;
	private String modifier;

	private int amount;
	private String hex;
	
	public Item(JsonObject json){
		amount = json.get("amount").getAsInt();
		id = json.get("id").getAsInt();
		itemName = json.get("item").getAsString();
		description = json.get("description").getAsString();
		modifier = json.get("modifier").getAsString();
	}
	
	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	
	public Item(String getMD5) {
		hex = getMD5;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Item(String name, String description){
		itemName = name;
		this.description = description;
	}
	
	public String getItemName() {
		return itemName;
	}

	public String getDescription() {
		return description;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String toJson()
	{
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting().serializeNulls();
		Gson gson = builder.create();
		return gson.toJson(this);
	}
	
	public Item fromJson(String content) {
		return new Gson().fromJson(content.toString(), Item.class);
	}

	@Override
	public int hashCode(){
		return id;
	}

	@Override
	public String toString() {
		return itemName + ", " + description;
	}
	
	public String getHex() {
		return hex;
	}
	
	@Override
	public boolean equals(Object o) {
		return (((Item) o).getId() == this.getId()) 
				|| (((Item) o).toString().equals(this.toString())) 
				|| barcode.getHash("add " + Integer.toString(((Item)o).getId())).equals(this.getHex())
				|| barcode.getHash("minus " + Integer.toString(((Item)o).getId())).equals(this.getHex());
	}

	@Override
	public int compareTo(Item o) {
//		System.out.println(this.itemName.compareTo(o.getItemName()));
		String thisComp = this.itemName + " " + this.description;
		String toComp = o.getItemName() + " " + o.getDescription();
		return thisComp.compareTo(toComp);
	}
}
