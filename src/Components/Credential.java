package Components;

public class Credential {

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
	
}
