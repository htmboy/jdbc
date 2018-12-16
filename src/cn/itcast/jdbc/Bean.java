package cn.itcast.jdbc;

public class Bean {
	
	private String name;
	
	public Bean() {
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Bean(String n) {
		this.name = n;
	}
	
	public String toString() {
		return this.name;
	}
}
