package Components;

public class Credential implements Comparable<Credential> {

	private String username, password, type;

	public Credential(String getUsername, String getPassword, String getType){
		username = getUsername;
		password = getPassword;
		type = getType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

	@Override
	public boolean equals(Object o) {
		return (((Credential) o).getUsername()).equals(this.getUsername());
	}

	@Override
	public int compareTo(Credential o) {
//		System.out.println(this.itemName.compareTo(o.getItemName()));
		return this.username.compareTo(o.getUsername());
	}
	
}
