package entities;
/*
 * SavedConfiguration class - adds an indentifier name to Configuration objects
 */
public class SavedConfiguration extends Configuration{
	String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SavedConfiguration(String name) {
		this.name = name;
	}
	public SavedConfiguration(String name, String ip, String port, String username, String password, String path) {
		super();
		this.name = name;
		this.setIp(ip);
		this.setPort(port);
		this.setUsername(username);
		this.setPassword(password);
		this.setPath(path);
	}
	public SavedConfiguration() {}
	
	@Override
	public String toString() {
		return this.name + " - " + this.getIp();
	}
}
