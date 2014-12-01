package oaUUM;

import com.google.gson.Gson;
import oaEntities.ResultVal;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import restService.Example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

public class UserAction {
	public String userLogin(String loginName,String password){
		Connection con = null;
		Gson gs = new Gson();
		ResultVal retVal = new ResultVal("fail","用户名密码不正确！");;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/oasystem", "devuser","dev_user123");
			Statement statamentMySQL = con.createStatement();
			con.setAutoCommit(false);
			Example example = new Example();
			String userRecordXml = example.generateXML(statamentMySQL.executeQuery("select PASSWORD from oa_uum_user where username = '"+loginName+"'"));
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
				
				retVal = new ResultVal("success",gs.toJson(upi));
				
			}
			else{
				retVal= new ResultVal("fail","用户名密码不正确！");
			}
		
		}catch (Exception e){
			try {
				con.rollback();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				retVal= new ResultVal("fail","系统错误，请稍后再试！");
				
			}
			e.printStackTrace();
			retVal= new ResultVal("fail","系统错误，请稍后再试！");
			
		}finally {
			try {
				if (con != null){
					con.close();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				retVal= new ResultVal("fail","系统错误，请稍后再试！");
			}
			
		}
		return gs.toJson(retVal);
	}
	
	public void userLogout(String accessToken){
		UserAuthenticate.logout(accessToken);
	}
}
