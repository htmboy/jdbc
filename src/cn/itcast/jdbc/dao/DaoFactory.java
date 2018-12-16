package cn.itcast.jdbc.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DaoFactory {

	private static UserDao userDao = null;
	private static DaoFactory instance = new DaoFactory();
	// private static UserDao userDao = null; // 这个顺序必须放在第一
	// 为什么呢?
	// 此类一加载 private static DaoFactory instance = new DaoFactory();就执行了, userDao就赋值了.  
	// 然后在调用private static UserDao userDao = null; user又赋值一次, 赋值为 null;
	private DaoFactory() {
		try {
			Properties prop = new Properties();
			//InputStream inStream = new FileInputStream(new File("src/daoconfig.properties")); // 还是不够灵活
			InputStream inStream = DaoFactory.class.getClassLoader().getResourceAsStream("daoconfig.properties"); 
			// 这样就不会把文件绑定死了.这个文件必须在class path 目录里面的任意地方
			prop.load(inStream); 
			String userDaoClass = prop.getProperty("userDaoClass"); // key值可以随便命名, 但是不能重复
			Class clazz = Class.forName(userDaoClass);
			userDao = (UserDao) clazz.getDeclaredConstructor().newInstance();
			
		} catch(Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	
	public static DaoFactory getInstance() {
		return instance;
	}
	
	public UserDao getUserDao() {
		return userDao;
	}
}
