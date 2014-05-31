package obsluga;

public enum UserRole {
	
	CLERK("CLERK"),
	ADMIN("ADMIN"),
	SERVICE("SERVICE");

	public String role;
	
	private UserRole(String role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		return this.role;
	}
}
