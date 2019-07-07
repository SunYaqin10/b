package service;

import java.util.List;

import Dao.UserDao;
import Dao.UserDaoImpl;
import entity.User;

public class UserServiceiImpl implements UserService{
	UserDao dao;
	public UserServiceiImpl() {
		// TODO Auto-generated constructor stub
		dao = new UserDaoImpl();
	}
	@Override
	public boolean Login(String username,String password) {
		
		User user = dao.queryOne(username);
		//������ص�������Ϣ�ж�
		if(null==user)
		{
			System.out.println("���û�������˻�������");
			return false;
		}
		else
		{
			 if(user.getPassword().equals(password))
			 {
				 System.out.println("��½�ɹ�");
				 dao.updateTime(user);
				 return true;
			 }
			 else{
				 System.out.println("���û��������������");
				 return false;
			 }
		}
	}
	
	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		List<User> users =  dao.searchAllUser();
		return users;
	}
	@Override
	public void insertOne(User u) {
		// TODO Auto-generated method stub
		dao.insertOne(u);
	}
	@Override
	public void deleteOne(User u) {
		// TODO Auto-generated method stub
		dao.deleteOne(u);
	}
	@Override
	public User queryOne(String username) {
		// TODO Auto-generated method stub
		User user = dao.queryOne(username);
		return user;
	}
	@Override
	public void updateOne(User u) {
		// TODO Auto-generated method stub
		dao.updateInfo(u);
	}
}
