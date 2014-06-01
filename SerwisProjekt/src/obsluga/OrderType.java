package obsluga;

public enum OrderType {
	REPAIR("repair"),
	NEW("new"),
	UNDEFINED("undefined");
	
	public String type = "";
	
	private OrderType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return this.type;
	}

}
