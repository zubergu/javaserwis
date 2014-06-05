package obsluga;

public enum OrderStatus {
	INPROGRESS("in_progress"),
	FINISHED("finished"),
	NOTSTARTED("not_started"),
	IMPOSSIBLE("impossible"),
	UNKNOWN("unknown");
	
	private String status = "";
	
	private OrderStatus(String status) {
		this.status = status;
	}
	
	public String toString() {
		return this.status;
	}

}
