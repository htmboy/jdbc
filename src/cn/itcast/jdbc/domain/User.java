package cn.itcast.jdbc.domain;

import java.util.Date;

public class User {

	private int id;
	private String name;
	private Date birthday;
	private float money;
	
	public User(String name) {
		this.name = name;
	}
	
	public User(float money) {
		this.money = money;
	}
	
	public String toString() {
		return "id=" + this.id + "name=" + this.name + "birthday=" + this.birthday + "money=" + this.money;
	}
	
	public User() {
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Float getMoney() {
		return money;
	}
	public void setMoney(Float money) {
		this.money = money;
	}
	
	private void test() {
		
	}
	
	public void showName() {
		System.out.println(this.name);
	}
	
}
