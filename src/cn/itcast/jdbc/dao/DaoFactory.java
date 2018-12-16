package cn.itcast.jdbc.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DaoFactory {

	private static UserDao userDao = null;
	private static DaoFactory instance = new DaoFactory();
	// private static UserDao userDao = null; // ���˳�������ڵ�һ
	// Ϊʲô��?
	// ����һ���� private static DaoFactory instance = new DaoFactory();��ִ����, userDao�͸�ֵ��.  
	// Ȼ���ڵ���private static UserDao userDao = null; user�ָ�ֵһ��, ��ֵΪ null;
	private DaoFactory() {
		try {
			Properties prop = new Properties();
			//InputStream inStream = new FileInputStream(new File("src/daoconfig.properties")); // ���ǲ������
			InputStream inStream = DaoFactory.class.getClassLoader().getResourceAsStream("daoconfig.properties"); 
			// �����Ͳ�����ļ�������.����ļ�������class path Ŀ¼���������ط�
			prop.load(inStream); 
			String userDaoClass = prop.getProperty("userDaoClass"); // keyֵ�����������, ���ǲ����ظ�
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
