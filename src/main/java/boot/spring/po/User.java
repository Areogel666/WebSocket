package boot.spring.po;

public class User {
	String uid;
	
	String name;
	
	public User() {
		super();
	}

	public User(String uid, String name) {
		super();
		this.uid = uid;
		this.name = name;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
