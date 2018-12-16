package cn.itcast.jdbc.dao;

import java.util.Date;

import cn.itcast.jdbc.dao.impl.UserDaoJdbcImpl;
import cn.itcast.jdbc.domain.User;

public class UserDaoTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		UserDao userDao = DaoFactory.getInstance().getUserDao();
		
		User user = new User();
		user.setBirthday(new Date());
		user.setName("name3");
		user.setMoney(1000.0f);
		
		User u = userDao.getUser(6);
//		u.setMoney(3000.1f);
		//userDao.update(u);
		
		//userDao.addUser(user);
		User u2 = userDao.getUser(u.getId());
		System.out.println(u2.getName());
		
		User u1 = userDao.getUser(6);
//		userDao.delete(u1);
	}

}
