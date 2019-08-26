package Components;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class Transaction {

	private int id;
	private Item item;
	private String modifier;
	private String date;
	
	public Transaction(JsonObject json){
		System.out.println("Transaction test");
		id = json.get("id").getAsInt();
		item = new Item(json.get("itemName").getAsString(), json.get("description").getAsString());
		modifier = json.get("modifier").getAsString();
		date = json.get("date").getAsString();
	}
	
	public Transaction(String name, String description, String modifier, String date){
		item = new Item(name, description);
		this.modifier = modifier;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public String getItemName() {
		return item.getItemName();
	}

	public String getDescription() {
		return item.getDescription();
	}

	public String getModifier() {
		return modifier;
	}

	public String getDate() {
		return date;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setItemName(String itemName) {
		this.item.setItemName(itemName);
	}

	public void setDescription(String description) {
		this.item.setDescription(description);
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String toJson()
	{
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting().serializeNulls();
		Gson gson = builder.create();
		return gson.toJson(this);
	}
	
	public Transaction fromJson(String content) {
		return new Gson().fromJson(content.toString(), Transaction.class);
	}

	@Override
	public String toString() {
		return item.getItemName() + ", " + item.getDescription();
	}
	
	@Override
	public boolean equals(Object o) {
		return (((Transaction) o).getId() == this.getId());
	}
}
