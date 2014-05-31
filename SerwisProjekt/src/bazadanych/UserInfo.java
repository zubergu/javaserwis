package bazadanych;

import obsluga.UserRole;

public class UserInfo {
	
	private String userName;
	private String password;
	private UserRole role;
	
	public UserInfo(String userName, String password, UserRole role) {
		this.userName = userName;
		this.password = password;
		this.role = role;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public UserRole getRole() {
		return this.role;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setRole(UserRole role) {
		this.role = role;
	}
	
	

}
