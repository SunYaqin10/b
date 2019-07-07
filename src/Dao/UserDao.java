package Dao;

import java.util.List;

import entity.User;

public interface UserDao {
	//���������ݿ��ڲ���һ����Ϣ
	public List<User> searchAllUser();
	public User queryOne(String username);
	public void insertOne(User user);
	public void deleteOne(User user);
	public void updateTime(User user);
	public void updateInfo(User user);
}
