package oaUUM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import com.google.gson.Gson;

import restService.Example;

public class UserAuthenticate {

	private static HashMap<String,UserProfileInfo> userTable= new HashMap<String,UserProfileInfo>();
	
	public static boolean validateUser(String accessToken){
		if (userTable.get(accessToken) == null ){
			return false;
		}
		else {
			return true;
		}
	}
	
	public static UserProfileInfo getUserProfileInfo(String accessToken){
		return userTable.get(accessToken);
	}
	
	public static UserProfileInfo setUserLogin(String accessToken,UserProfileInfo userInfo){
		return userTable.put(accessToken,userInfo);
	}
	
	public static void logout(String accessToken){
		userTable.remove(accessToken);
	}
}
