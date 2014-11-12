package oaUUM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import restService.Example;

import com.google.gson.Gson;

public class UserAction {
	public String userLogin(String loginName,String password){
		Connection con = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://danlihome.wicp.net:3308/OASystem", "danlihome","ld7vd6yt");
			Statement statamentMySQL = con.createStatement();
			con.setAutoCommit(false);
			Example example = new Example();
			String userRecordXml = example.generateXML(statamentMySQL.executeQuery("select PASSWORD from oa_uum_user where LOGIN_ID = '"+loginName+"'"));
			Document userDoc = DocumentHelper.parseText(userRecordXml);
			List<Element> userList = userDoc.getRootElement().selectNodes("Record");
			String passwordDB = "";
			if (userList.size()>0){
				Node passwordNode = userList.get(0).selectSingleNode("PASSWORD");
				if (passwordNode != null){
					passwordDB = passwordNode.getText();
				}
			}
			if (password.equalsIgnoreCase(passwordDB)){
				String token = UUID.randomUUID().toString();
				UserProfileInfo upi = new UserProfileInfo();
				upi.setUserName(loginName);
				upi.setPassword(password);
				upi.setAccessToken(token);
				UserAuthenticate.setUserLogin(token,upi);
				Gson gs = new Gson();
				return gs.toJson(upi);
			}
			else{
				return "fail";
			}
		
		}catch (Exception e){
			try {
				con.rollback();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			return "fail";
		}finally {
			try {
				if (con != null){
					con.close();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "fail";
			}
		}
	}
	
	public void userLogout(String accessToken){
		UserAuthenticate.logout(accessToken);
	}
}
