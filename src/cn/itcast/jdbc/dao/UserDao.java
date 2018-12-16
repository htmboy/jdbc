package cn.itcast.jdbc.dao;
import java.sql.SQLException;

// dao �����ݷ��ʲ�
import cn.itcast.jdbc.domain.User;

public interface UserDao {

//	public void addUser(User user);
	public void addUser(User user);
	
	public User getUser(int userId);
	
	public void update(User user);
	
	public void delete(User user);

	public User findUser(String name, String password);
	
}
