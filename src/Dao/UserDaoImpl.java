package Dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.sun.net.httpserver.Filter;

import Util.MongoDBUtil;
import entity.User;
import jdk.internal.dynalink.beans.StaticClass;
import net.sf.json.JSONObject;

public class UserDaoImpl implements UserDao{
	//����һ������
	static List<User> list1 = new ArrayList<User>();
	String collectionName = "user";// ��������
	String databaseName = "EMS-DB";// ���ݿ�
    private static String host = "127.0.0.1";// mongo���ݿ��������ַ,����Ϊ127.0.0.1
    private static int port = 27017;// mongoĬ��Ϊ27017�˿�
    private static MongoClient mongoClient;
    MongoCollection<Document> mongoCollection;
	public UserDaoImpl() {
		super();
		// TODO Auto-generated constructor stub
		mongoCollection = MongoDBUtil.getConnect(host,port,databaseName).getCollection(collectionName);
	}
	@Override
	public void insertOne(User user) {
		// TODO Auto-generated method stub
		if(null == mongoCollection)
		{
			System.out.println("���ݿ�����ʧ��");
			return;
		}
		Document document = new Document("username",user.getUserName())
				.append("password", user.getPassword())
				.append("email", user.getEmail())
				.append("phone", user.getPhoneNum())
				.append("status", user.getStatus())
				.append("sourceSystem", user.getSystemSource())
				.append("registerDate", user.getRegisterDate())
				.append("finalip", user.getFinalip())
				.append("finalTime", user.getFinalTime());		
		mongoCollection.insertOne(document);
		System.out.println("���ݲ���ɹ�");
	}

	@Override
	public User queryOne(String username) {
		// TODO Auto-generated method stub
		//�Ȼ�ȡ�������û�����ļ��ϣ�����ƥ���user����
        BasicDBObject queryObject = new BasicDBObject("username", username);
//        queryObject.put();
        MongoCursor<Document> cursor = mongoCollection.find(queryObject).iterator();
        System.out.println(cursor.hasNext());
        try {
        	if(cursor.hasNext())
            {
            	JSONObject jsonObject = JSONObject.fromObject(cursor.next().toJson().toString());//cursor��next��һ�Σ�ָ�����һ�Σ���Ҫ��������
            	String user = jsonObject.getString("username");
            	String password = jsonObject.getString("password");
            	String email = jsonObject.getString("email");
            	String phone = jsonObject.getString("phone");
            	String status = jsonObject.getString("status");
            	String sourceSystem = jsonObject.getString("sourceSystem");
            	String registerDate = jsonObject.getString("registerDate");
            	String finalip = jsonObject.getString("finalip");
            	String finalTime = jsonObject.getString("finalTime");
            	User u = new User(user, password, email, phone, status, sourceSystem, registerDate, finalip,finalTime);
            	System.out.println("��ѯ���Ľ��Ϊ"+u);
            	return u;
            }
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("�Ҳ������û�");
			return null;
		}
        return null;
	}
	@Override
	public List<User> searchAllUser() {
		// TODO Auto-generated method stub
		List<User> users = new ArrayList<>();
		BasicDBObject queryObject = new BasicDBObject();
		MongoCursor<Document> cursor = mongoCollection.find(queryObject).iterator();
		 try {
	        	while(cursor.hasNext())
	            {
	            	JSONObject jsonObject = JSONObject.fromObject(cursor.next().toJson().toString());//cursor��next��һ�Σ�ָ�����һ�Σ���Ҫ��������
	            	String user = jsonObject.getString("username");
	            	String password = jsonObject.getString("password");
	            	String email = jsonObject.getString("email");
	            	String phone = jsonObject.getString("phone");
	            	String status = jsonObject.getString("status");
	            	String sourceSystem = jsonObject.getString("sourceSystem");
	            	String registerDate = jsonObject.getString("registerDate");
	            	String finalip = jsonObject.getString("finalip");
	            	String finalTime = jsonObject.getString("finalTime");
	            	User u = new User(user, password, email, phone, status, sourceSystem, registerDate, finalip,finalTime);
	            	System.out.println(u);
	            	users.add(u);
	            }
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("��ѯ������"+e);
				return null;
			}
		return users;
	}
	@Override
	public void deleteOne(User user) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		BasicDBObject queryObject = new BasicDBObject();
		queryObject.put("username",user.getUserName());
		System.out.println("username"+user.getUserName()+"status"+user.getStatus());
		queryObject.put("status", user.getStatus());
		try
		{
			mongoCollection.deleteOne(queryObject);
			System.out.println("ɾ���ɹ�");
		}
		catch(Exception exception){
			System.out.println("ɾ��ʧ��"+exception);
		}
	}
	@Override
	public void updateTime(User user) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String curDate = sdf.format(new Date());
		Bson filter = Filters.eq("username", user.getUserName());
		Bson update = new Document("$set",new Document("finalTime",curDate));
		try {
    		mongoCollection.updateOne(filter, update);
    		System.out.println("���³ɹ�");
		}
		catch(Exception exception)
		{
			System.out.println("����ʧ��"+exception);
		}
	}
	@Override
	public void updateInfo(User user) {
		// TODO Auto-generated method stub
		Bson filter = Filters.eq("username", user.getUserName());
		Bson update = new Document("$set",
				new Document("email",user.getEmail())
				.append("phone", user.getPhoneNum())
				.append("status", user.getStatus()));
		try {
    		mongoCollection.updateOne(filter, update);
    		System.out.println("���³ɹ�");
		}
		catch(Exception exception)
		{
			System.out.println("����ʧ��"+exception);
		}
	}

}
