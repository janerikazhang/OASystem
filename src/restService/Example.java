package restService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oaEntities.AttendenceList;
import oaEntities.DayAttendence;
import oaEntities.ItemAttendence;
import oaEntities.ResultVal;
import oaEntities.UserLoginEntity;
import oaUUM.UserAction;
import oaUUM.UserAuthenticate;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("")
public class Example {

	public static final String bigMonth = "1,3,5,7,8,10,12";

	public Example() {

	}

	/**
	 * resteasy example
	 * 
	 * @param requestStr
	 * @return
	 */
	@POST
	@Path("/helloworld")
	@Produces("text/plain; charset=utf-8")
	public String getClueList(String requestJson) throws Exception {
		//JSONObject jo = JSONObject.fromObject(requestJson);
		
		ResultVal retVal = new ResultVal("success", "");
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://danlihome.wicp.net:3308/OASystem",
					"danlihome", "ld7vd6yt");
			Statement statamentMySQL = con.createStatement();
			con.setAutoCommit(true);
			String configXml = generateXML(statamentMySQL
					.executeQuery("select * from oa_attendence"));
			Document doc = DocumentHelper.parseText(configXml);
			List<Node> configList = doc.getRootElement().selectNodes("Record");
			JSONArray rows = new JSONArray();
			for (Node row : configList){
				JSONObject rowObj = new JSONObject();
				if (row.selectSingleNode("USER_NAME") != null){
					rowObj.put("UserName", row.selectSingleNode("USER_NAME").getText());
				}
				else{
					rowObj.put("UserName", "");
				}
				if (row.selectSingleNode("GROUP_NAME") != null){
					rowObj.put("DepartmentName", row.selectSingleNode("GROUP_NAME").getText());
				}
				else{
					rowObj.put("DepartmentName", "");
				}
				if (row.selectSingleNode("ATTENDENCE_TIME") != null){
					rowObj.put("DateTime", row.selectSingleNode("ATTENDENCE_TIME").getText());
				}
				else{
					rowObj.put("DateTime", "");
				}
				if (row.selectSingleNode("SPECIAL_TYPE") != null){
					String type = "";
					if (row.selectSingleNode("SPECIAL_TYPE").getText().equalsIgnoreCase("1")){
						
					}
					else if (row.selectSingleNode("SPECIAL_TYPE").getText().equalsIgnoreCase("2")){
						
					}
					else if (row.selectSingleNode("SPECIAL_TYPE").getText().equalsIgnoreCase("3")){
						
					}
					else if (row.selectSingleNode("SPECIAL_TYPE").getText().equalsIgnoreCase("4")){
						type = "年假";
					}
					else if (row.selectSingleNode("SPECIAL_TYPE").getText().equalsIgnoreCase("5")){
						type = "事假";
					}
					else if (row.selectSingleNode("SPECIAL_TYPE").getText().equalsIgnoreCase("6")){
						type = "病假";
					}
					else if (row.selectSingleNode("SPECIAL_TYPE").getText().equalsIgnoreCase("7")){
						
					}
					rowObj.put("SpecialType", type);
				}
				else{
					rowObj.put("SpecialType", "");
				}
				
				if (row.selectSingleNode("REMARK") != null){
					rowObj.put("Remark", "");
				}
				else{
					rowObj.put("Remark", "");
				}
				rows.add(rowObj);
			}
			return rows.toString();
			
		} catch (Exception e) {
			if (con != null){
				con.close();
			}
			retVal = new ResultVal("fail", e.getMessage());
		} finally {
			if (con != null){
				con.close();
			}
		}
		
		
		
		
		return "";
	}

	@POST
	@Path("/login")
	@Produces("text/plain; charset=utf-8")
	public String login(String requestJson) {
		Gson gs = new Gson();
		UserLoginEntity ule = gs.fromJson(requestJson, UserLoginEntity.class);
		UserAction ua = new UserAction();
		String returnString = ua
				.userLogin(ule.getUsername(), ule.getPassword());
		return returnString;
	}

	@POST
	@Path("/logout")
	@Produces("text/plain; charset=utf-8")
	public String logout(String accessToken) {
		UserAction ua = new UserAction();
		ua.userLogout(accessToken);
		ResultVal retVal = new ResultVal("success", "");
		Gson gson = new Gson();
		return gson.toJson(retVal);
	}
	
	@POST
	@Path("/addAttendence")
	@Produces("text/plain; charset=utf-8")
	public String addAttendence(String jsonRequest) {
		ResultVal retVal = new ResultVal("success", "");
		Connection con = null;
		try {
			JSONObject requestJson = JSONObject.fromObject(jsonRequest);
			String date = requestJson.getString("date");
			String user = requestJson.getString("user");
			String department = requestJson.getString("department");
			String ma = requestJson.getString("morningAtten");
			String aa = requestJson.getString("afternoonAtten");
			
			String morningTime = getSysconfig("late");
			String afternoonTime = getSysconfig("early");
			
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://danlihome.wicp.net:3308/OASystem",
					"danlihome", "ld7vd6yt");
			Statement statamentMySQL = con.createStatement();
			
			con.setAutoCommit(false);
			
			if (!ma.equals("0")){
				
				statamentMySQL.executeUpdate("INSERT INTO OA_ATTENDENCE  (USER_NAME,GROUP_NAME,ATTENDENCE_TIME,SPECIAL_TYPE) values ('"+user+"','"+department+"','"+date+" "+morningTime+"',"+ma+")");
				
			}
			if (!aa.equals("0")){
				statamentMySQL.executeUpdate("INSERT INTO OA_ATTENDENCE  (USER_NAME,GROUP_NAME,ATTENDENCE_TIME,SPECIAL_TYPE) values ('"+user+"','"+department+"','"+date+" "+afternoonTime+"',"+aa+")");
			}
			
			con.commit();
	
		}
		catch (Exception e){
			try {
				con.rollback();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			return "fail";
		}
		finally {
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
		return "success";
	}
	
	
	
	

	@POST
	@Path("/validateUser")
	@Produces("text/plain; charset=utf-8")
	public String validateUser(String accessToken) {
		boolean validateResult = UserAuthenticate.validateUser(accessToken);
		ResultVal retVal = new ResultVal("success", "");
		if (validateResult == false) {
			retVal = new ResultVal("fail", "");
		}
		Gson gson = new Gson();
		return gson.toJson(retVal);
	}

	@GET
	@Path("/systemConfig")
	@Produces("text/plain; charset=utf-8")
	public String getConfig() throws Exception {
		ResultVal retVal = new ResultVal("success", "");
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://danlihome.wicp.net:3308/OASystem",
					"danlihome", "ld7vd6yt");
			Statement statamentMySQL = con.createStatement();
			con.setAutoCommit(true);
			String configXml = generateXML(statamentMySQL
					.executeQuery("select * from oa_sysconfig"));
			Document doc = DocumentHelper.parseText(configXml);
			List<Node> configList = doc.getRootElement().selectNodes("Record");
			HashMap<String, String> model = new HashMap<String, String>();
			for (Node configNode : configList) {
				if (configNode.selectSingleNode("CONFIG_NAME") != null) {
					if (configNode.selectSingleNode("CONFIG_VALUE") != null) {
						model.put(configNode.selectSingleNode("CONFIG_NAME")
								.getText(),
								configNode.selectSingleNode("CONFIG_VALUE")
										.getText());
					} else {
						model.put(configNode.selectSingleNode("CONFIG_NAME")
								.getText(), "");
					}

				}
			}
			retVal = new ResultVal("success", JSONObject.fromObject(model)
					.toString());

		} catch (Exception e) {
			if (con != null){
				con.close();
			}
			retVal = new ResultVal("fail", e.getMessage());
		} finally {
			if (con != null){
				con.close();
			}
		}
		Gson gson = new Gson();
		return gson.toJson(retVal);
	}

	@POST
	@Path("/holodayConfig")
	@Produces("text/plain; charset=utf-8")
	public String getHolodayConfig(String jsonRequest) throws Exception {
		ResultVal retVal = new ResultVal("success", "");
		Connection con = null;
		try {
			JSONObject requestJson = JSONObject.fromObject(jsonRequest);
			String year = requestJson.getString("year");
			String month = requestJson.getString("month");
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://danlihome.wicp.net:3308/OASystem",
					"danlihome", "ld7vd6yt");
			Statement statamentMySQL = con.createStatement();
			con.setAutoCommit(false);
			
			String holidayRecordXml = generateXML(statamentMySQL
					.executeQuery("select  HOLIDAY,ARRENGEMENT,WORKSAT from oa_holiday_arrengement where year = '"
							+ year + "' AND month = '" + month + "'"));

			Document holiday = DocumentHelper.parseText(holidayRecordXml);

			List<Element> holidayList = holiday.getRootElement().selectNodes("Record");
			String holidayString = "";
			String arrengement = "";
			String satWork = "";
			for (Element elt : holidayList) {
				if (elt.selectSingleNode("HOLIDAY") != null) {
					holidayString = elt.selectSingleNode("HOLIDAY").getText();
				}
				if (elt.selectSingleNode("ARRENGEMENT") != null) {
					arrengement = elt.selectSingleNode("ARRENGEMENT").getText();
				}
				if (elt.selectSingleNode("WORKSAT") != null) {
					satWork = elt.selectSingleNode("WORKSAT").getText();
				}
			}
			
			HashMap<String,String> model = new HashMap<String,String>();
			model.put("holiday", holidayString);
			model.put("arrangement", arrengement);
			model.put("satWork", satWork);
			
			
			retVal = new ResultVal("success", JSONObject.fromObject(model).toString());
			
			
		}
		catch (Exception e){
			if (con != null){
				con.close();
			}
			retVal = new ResultVal("fail", e.getMessage());
		}
		finally{
			if (con != null){
				con.close();
			}
		}
		
		Gson gson = new Gson();
		return gson.toJson(retVal);
	}

	@POST
	@Path("/systemConfig")
	@Produces("text/plain; charset=utf-8")
	public String saveConfig(String jsonRequest) throws Exception {
		ResultVal retVal = new ResultVal("success", "");
		Connection con = null;
		try {
			JSONObject requestJson = JSONObject.fromObject(jsonRequest);

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://danlihome.wicp.net:3308/OASystem",
					"danlihome", "ld7vd6yt");
			Statement statamentMySQL = con.createStatement();
			con.setAutoCommit(false);
			statamentMySQL.execute("delete from OA_SYSCONFIG");
			statamentMySQL
					.execute("INSERT INTO OA_SYSCONFIG VALUES ('daySeparator','"
							+ requestJson.getString("daySeparator") + "')");
			statamentMySQL
					.execute("INSERT INTO OA_SYSCONFIG VALUES ('midSeparator','"
							+ requestJson.getString("midSeparator") + "')");
			statamentMySQL.execute("INSERT INTO OA_SYSCONFIG VALUES ('late','"
					+ requestJson.getString("late") + "')");
			statamentMySQL.execute("INSERT INTO OA_SYSCONFIG VALUES ('early','"
					+ requestJson.getString("early") + "')");
			statamentMySQL
					.execute("INSERT INTO OA_SYSCONFIG VALUES ('earlySat','"
							+ requestJson.getString("earlySat") + "')");
			statamentMySQL.execute("INSERT INTO OA_SYSCONFIG VALUES ('late1','"
					+ requestJson.getString("late1") + "')");
			statamentMySQL.execute("INSERT INTO OA_SYSCONFIG VALUES ('late2','"
					+ requestJson.getString("late2") + "')");
			statamentMySQL
					.execute("INSERT INTO OA_SYSCONFIG VALUES ('leave1','"
							+ requestJson.getString("leave1") + "')");
			statamentMySQL
					.execute("INSERT INTO OA_SYSCONFIG VALUES ('leave2','"
							+ requestJson.getString("leave2") + "')");
			statamentMySQL
					.execute("INSERT INTO OA_SYSCONFIG VALUES ('priceTime','"
							+ requestJson.getString("priceTime") + "')");

			String userRecordXml = generateXML(statamentMySQL
					.executeQuery("select ID from oa_holiday_arrengement where YEAR = '"
							+ requestJson.getString("year")
							+ "' AND MONTH = '"
							+ requestJson.getString("month") + "'"));

			Document doc = DocumentHelper.parseText(userRecordXml);

			Node elt = doc.getRootElement().selectSingleNode("Record/ID");
			String newArrengementId = "";
			if (elt != null) {
				newArrengementId = elt.getText();
			}

			if (!newArrengementId.equalsIgnoreCase("")) {
				statamentMySQL
						.execute("UPDATE oa_holiday_arrengement SET HOLIDAY = '"
								+ requestJson.getString("holiday")
								+ "', ARRENGEMENT = '"
								+ requestJson.getString("arrangement")
								+ "', WORKSAT = '"
								+ requestJson.getString("satWork")
								+ "' WHERE ID = '" + newArrengementId + "'");
			} else {
				newArrengementId = UUID.randomUUID().toString();
				statamentMySQL
						.execute("INSERT INTO oa_holiday_arrengement VALUES ('"
								+ newArrengementId + "','"
								+ requestJson.getString("year") + "','"
								+ requestJson.getString("month") + "','"
								+ requestJson.getString("holiday") + "','"
								+ requestJson.getString("arrangement") + "','"
								+ requestJson.getString("satWork") + "')");

			}
			con.commit();
		} catch (Exception e) {
			if (con != null) {
				con.rollback();
				con.close();
			}
			e.printStackTrace();
			retVal = new ResultVal("fail", e.getMessage());
		} finally {
			if (con != null) {
				con.close();
			}
		}
		Gson gson = new Gson();
		return gson.toJson(retVal);
	}

	public String getSysconfig(String configName) throws Exception {
		Connection con = null;
		String value = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://danlihome.wicp.net:3308/OASystem",
					"danlihome", "ld7vd6yt");
			Statement statamentMySQL = con.createStatement();
			con.setAutoCommit(true);

			String configXml = generateXML(statamentMySQL
					.executeQuery("select * from oa_sysconfig where config_name = '"
							+ configName + "'"));

			Document doc = DocumentHelper.parseText(configXml);
			Node configNode = doc.getRootElement().selectSingleNode(
					"Record/CONFIG_VALUE");
			if (configNode != null) {
				value = configNode.getText();
			}
		} catch (Exception e) {
			if (con != null) {
				con.close();
			}
		} finally {
			if (con != null) {
				con.close();
			}
		}
		return value;
	}

	@SuppressWarnings("deprecation")
	@POST
	@Path("/attendenceResult")
	@Produces("text/plain; charset=utf-8")
	public String getAttendenceResult(String jsonRequest) throws Exception {
		JSONObject requestJson = JSONObject.fromObject(jsonRequest);

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		String year = requestJson.getString("year");
		String month = requestJson.getString("month");
		String holidayString = "";
		String arrengement = "";

		String temp = getSysconfig("daySeparator");
		String daySaparator = "07:00:00";// 日分割时间
		if (!temp.equalsIgnoreCase("")) {
			daySaparator = temp;
		}

		temp = getSysconfig("midSeparator");
		String midSeparator = "12:30:00";
		if (!temp.equalsIgnoreCase("")) {
			midSeparator = temp;// 中午分割时间
		}
		temp = getSysconfig("late1");
		String late1 = "10:00:00";// 头一天工作到leave1，第二天迟到时间推迟到late1
		if (!temp.equalsIgnoreCase("")) {
			late1 = temp;// 中午分割时间
		}
		temp = getSysconfig("priceTime");
		String priceTime = "21:30:00";// 获奖的离开工作时间
		if (!temp.equalsIgnoreCase("")) {
			priceTime = temp;// 中午分割时间
		}

		temp = getSysconfig("late2");
		String late2 = "14:00:00";// 头一天工作到leave2，第二天迟到时间推迟到late2
		if (!temp.equalsIgnoreCase("")) {
			late2 = temp;// 中午分割时间
		}

		temp = getSysconfig("leave1");
		String leave1 = "22:30:00";// 头一天离开时间leave1
		if (!temp.equalsIgnoreCase("")) {
			leave1 = temp;// 中午分割时间
		}

		temp = getSysconfig("leave2");
		String leave2 = "02:00:00";// 头一天离开时间leave2
		if (!temp.equalsIgnoreCase("")) {
			leave2 = temp;// 中午分割时间
		}

		temp = getSysconfig("late");
		String late = "09:30:00";// 标准迟到时间
		if (!temp.equalsIgnoreCase("")) {
			late = temp;// 中午分割时间
		}

		temp = getSysconfig("early");
		String early = "18:30:00";// 标准早退时间
		if (!temp.equalsIgnoreCase("")) {
			early = temp;// 中午分割时间
		}

		temp = getSysconfig("earlySat");
		String earlySat = "14:00:00";// 周六半天早退时间
		if (!temp.equalsIgnoreCase("")) {
			earlySat = temp;// 中午分割时间
		}

		String satWork = "";// 周六工作日
		int numOfDay = 30;
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://danlihome.wicp.net:3308/OASystem",
					"danlihome", "ld7vd6yt");
			Statement statamentMySQL = con.createStatement();
			con.setAutoCommit(false);

			String configXml = generateXML(statamentMySQL
					.executeQuery("select * from oa_sysconfig"));

			Document doc = DocumentHelper.parseText(configXml);

			String userRecordXml = generateXML(statamentMySQL
					.executeQuery("select distinct USER_NAME from oa_attendence"));

			String holidayRecordXml = generateXML(statamentMySQL
					.executeQuery("select  HOLIDAY,ARRENGEMENT,WORKSAT from oa_holiday_arrengement where year = '"
							+ year + "' AND month = '" + month + "'"));

			Document holiday = DocumentHelper.parseText(holidayRecordXml);

			List<Element> holidayList = holiday.getRootElement().selectNodes(
					"Record");

			for (Element elt : holidayList) {
				if (elt.selectSingleNode("HOLIDAY") != null) {
					holidayString = elt.selectSingleNode("HOLIDAY").getText();
				}
				if (elt.selectSingleNode("ARRENGEMENT") != null) {
					arrengement = elt.selectSingleNode("ARRENGEMENT").getText();
				}
				if (elt.selectSingleNode("WORKSAT") != null) {
					satWork = elt.selectSingleNode("WORKSAT").getText();
				}
			}

			Document userList = DocumentHelper.parseText(userRecordXml);

			List<Element> users = userList.getRootElement().selectNodes(
					"Record");

			if (bigMonth.contains(month)) {
				numOfDay = 31;
			}
			if (month.equalsIgnoreCase("2")) {
				int yearInt = Integer.parseInt(year);
				if (yearInt % 4 == 0) {
					numOfDay = 29;
				} else {
					numOfDay = 28;
				}

			}
			List<ItemAttendence> iaL = new ArrayList<ItemAttendence>();
			double non = 0;
			for (Element elt : users) {
				if (elt.selectSingleNode("USER_NAME") != null) {
					String user = elt.selectSingleNode("USER_NAME").getText();
					String group = "";
					List<DayAttendence> dayA = new ArrayList<DayAttendence>();
					for (int i = 1; i <= numOfDay; i++) {
						String dayRecord = "";
						if (i != numOfDay) {
							dayRecord = generateXML(statamentMySQL
									.executeQuery("select * from oa_attendence where USER_NAME = '"
											+ user
											+ "' AND ATTENDENCE_TIME < '"
											+ year
											+ "-"
											+ month
											+ "-"
											+ Integer.toString(i + 1)
											+ " "
											+ daySaparator
											+ "' AND ATTENDENCE_TIME >'"
											+ year
											+ "-"
											+ month
											+ "-"
											+ Integer.toString(i)
											+ " "
											+ daySaparator
											+ "' order by ATTENDENCE_TIME"));
						} else {
							if (month.equalsIgnoreCase("12")) {
								dayRecord = generateXML(statamentMySQL
										.executeQuery("select * from oa_attendence where USER_NAME = '"
												+ user
												+ "' AND ATTENDENCE_TIME < '"
												+ Integer.toString(Integer
														.parseInt(year) + 1)
												+ "-"
												+ "1-1"
												+ " "
												+ daySaparator
												+ "' AND ATTENDENCE_TIME >'"
												+ year
												+ "-"
												+ month
												+ "-"
												+ Integer.toString(i)
												+ " "
												+ daySaparator
												+ "' order by ATTENDENCE_TIME"));
							} else {
								dayRecord = generateXML(statamentMySQL
										.executeQuery("select * from oa_attendence where USER_NAME = '"
												+ user
												+ "' AND ATTENDENCE_TIME < '"
												+ year
												+ "-"
												+ Integer.toString(Integer
														.parseInt(month) + 1)
												+ "-1 "
												+ daySaparator
												+ "' AND ATTENDENCE_TIME >'"
												+ year
												+ "-"
												+ month
												+ "-"
												+ Integer.toString(i)
												+ " "
												+ daySaparator
												+ "' order by ATTENDENCE_TIME"));
							}
						}
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd");
						Date date = sdf.parse(year + "-" + month + "-"
								+ Integer.toString(i));
						boolean isHoliday = false;
						if (holidayString.contains(Integer.toString(i))) {
							isHoliday = true;
						} else if (date.getDay() == 0 || date.getDay() == 6) {
							if (!arrengement.contains(Integer.toString(i))
									&& !satWork.contains(Integer.toString(i))) {
								isHoliday = true;
							}
						}
						Document dayDoc = DocumentHelper.parseText(dayRecord);
						List<Element> dayList = dayDoc.getRootElement()
								.selectNodes("Record");
						DayAttendence item = null;
						if (dayList.size() == 0) {

							SimpleDateFormat sdf1 = new SimpleDateFormat(
									"yyyy-MM-dd");
							Date date1 = sdf1.parse(year + "-" + month + "-"
									+ Integer.toString(i));
							if (dayA.size() > 0) {
								if (dayA.get(dayA.size() - 1).getOutHour() != null) {
									SimpleDateFormat sdf2 = new SimpleDateFormat(
											"yyyy-MM-dd HH:mm:ss");
									String time = year + "-" + month + "-"
											+ Integer.toString(i) + " "
											+ leave2;
									Date date2 = sdf2.parse(time);
									int c = date2.compareTo(dayA.get(
											dayA.size() - 1).getOutHour());
									if (c >= 0) {
										item = new DayAttendence(date1, null,
												null, isHoliday, true, true,
												"1", "1");
									} else {
										// todo:判断是否入职
										if (isHoliday) {
											item = new DayAttendence(date1,
													null, null, isHoliday,
													false, false, "", "");
										} else {
											item = new DayAttendence(date1,
													null, null, isHoliday,
													true, true, "1", "1");
										}
									}
								} else {
									if (isHoliday) {
										item = new DayAttendence(date1, null,
												null, isHoliday, false, false,
												"0", "0");
									} else {
										item = new DayAttendence(date1, null,
												null, isHoliday, true, true,
												"1", "1");
									}
								}

							} else {// todo:跨月代码
								if (isHoliday) {
									item = new DayAttendence(date1, null, null,
											isHoliday, false, false, "0", "0");
								} else {
									item = new DayAttendence(date1, null, null,
											isHoliday, true, true, "1", "1");
								}
							}
						} else if (dayList.size() == 1) {
							group = dayList.get(0)
									.selectSingleNode("GROUP_NAME").getText();
							SimpleDateFormat sdf2 = new SimpleDateFormat(
									"yyyy-MM-dd");
							Date date3 = sdf2.parse(year + "-" + month + "-"
									+ Integer.toString(i));
							String time = dayList.get(0)
									.selectSingleNode("ATTENDENCE_TIME")
									.getText();
							SimpleDateFormat sdf1 = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss");
							Date date1 = sdf1.parse(time);
							String midTime = year + "-" + month + "-"
									+ Integer.toString(i) + " " + midSeparator;
							Date date2 = sdf1.parse(midTime);
							int c = date1.compareTo(date2);
							if (c < 0) {
								String lateTime = "";
								if (dayA.size() > 0) {

									String lateTime1 = year + "-" + month + "-"
											+ Integer.toString(i - 1) + " "
											+ leave1;
									String lateTime2 = year + "-" + month + "-"
											+ Integer.toString(i) + " "
											+ leave2;
									Date d = sdf1.parse(lateTime1);
									Date d1 = sdf1.parse(lateTime2);
									int c3 = -1;
									if (dayA.get(dayA.size() - 1).getOutHour() != null) {
										c3 = dayA.get(dayA.size() - 1)
												.getOutHour().compareTo(d);
									}
									if (c3 < 0) {
										lateTime = year + "-" + month + "-"
												+ Integer.toString(i) + " "
												+ late;
									} else {
										lateTime = year + "-" + month + "-"
												+ Integer.toString(i) + " "
												+ late1;
									}

									if (dayA.get(dayA.size() - 1).getOutHour() != null) {
										c3 = dayA.get(dayA.size() - 1)
												.getOutHour().compareTo(d1);
									}
									if (c3 >= 0) {
										lateTime = year + "-" + month + "-"
												+ Integer.toString(i) + " "
												+ late2;
									}

								} else {// todo:跨月代码
									lateTime = year + "-" + month + "-"
											+ Integer.toString(i) + " " + late;
								}
								Date date4 = sdf1.parse(lateTime);
								int c1 = date1.compareTo(date4);
								if (c1 <= 0) {
									item = new DayAttendence(date3, date1,
											null, isHoliday, false, true, "",
											"1");
								} else {
									item = new DayAttendence(date3, date1,
											null, isHoliday, true, true, "2",
											"1");
								}
							} else {
								Date date4 = null;
								if (!satWork.contains(Integer.toString(i))) {
									String earlyTime = year + "-" + month + "-"
											+ Integer.toString(i) + " " + early;
									date4 = sdf1.parse(earlyTime);
								} else {
									String earlyTime = year + "-" + month + "-"
											+ Integer.toString(i) + " "
											+ earlySat;
									date4 = sdf1.parse(earlyTime);
								}
								int c1 = date1.compareTo(date4);
								if (c1 < 0) {
									item = new DayAttendence(date3, null,
											date1, isHoliday, true, true, "1",
											"3");
								} else {
									item = new DayAttendence(date3, null,
											date1, isHoliday, true, false, "1",
											"");
								}
							}
						} else {
							SimpleDateFormat sdf2 = new SimpleDateFormat(
									"yyyy-MM-dd");
							Date date3 = sdf2.parse(year + "-" + month + "-"
									+ Integer.toString(i));
							String timeIn = dayList.get(0)
									.selectSingleNode("ATTENDENCE_TIME")
									.getText();
							String timeOut = dayList.get(dayList.size() - 1)
									.selectSingleNode("ATTENDENCE_TIME")
									.getText();
							String lateTime = year + "-" + month + "-"
									+ Integer.toString(i) + " " + late;
							String earlyTime = year + "-" + month + "-"
									+ Integer.toString(i) + " " + early;
							String earlyTimeSat = year + "-" + month + "-"
									+ Integer.toString(i) + " " + earlySat;
							SimpleDateFormat sdf1 = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss");
							Date date1 = sdf1.parse(timeIn);
							Date date2 = sdf1.parse(timeOut);

							Date date5 = null;
							if (dayA.size() > 0) {

								String lateTime1 = year + "-" + month + "-"
										+ Integer.toString(i - 1) + " "
										+ leave1;
								String lateTime2 = year + "-" + month + "-"
										+ Integer.toString(i) + " " + leave2;
								Date d = sdf1.parse(lateTime1);
								Date d1 = sdf1.parse(lateTime2);
								int c3 = -1;
								if (dayA.get(dayA.size() - 1).getOutHour() != null) {
									c3 = dayA.get(dayA.size() - 1).getOutHour()
											.compareTo(d);
								}
								if (c3 < 0) {
									lateTime = year + "-" + month + "-"
											+ Integer.toString(i) + " " + late;
								} else {
									lateTime = year + "-" + month + "-"
											+ Integer.toString(i) + " " + late1;
								}

								if (dayA.get(dayA.size() - 1).getOutHour() != null) {
									c3 = dayA.get(dayA.size() - 1).getOutHour()
											.compareTo(d1);
								}

								if (c3 >= 0) {
									lateTime = year + "-" + month + "-"
											+ Integer.toString(i) + " " + late2;
								}

							} else {// todo:跨月代码
								lateTime = year + "-" + month + "-"
										+ Integer.toString(i) + " " + late;
							}
							Date date4 = sdf1.parse(lateTime);
							if (!satWork.contains(Integer.toString(i))) {
								date5 = sdf1.parse(earlyTime);
							} else {
								date5 = sdf1.parse(earlyTimeSat);
							}
							int c1 = date1.compareTo(date4);
							int c2 = date2.compareTo(date5);
							if (c1 <= 0 && c2 < 0) {
								item = new DayAttendence(date3, date1, date2,
										isHoliday, false, true, dayList
												.get(0)
												.selectSingleNode(
														"SPECIAL_TYPE")
												.getText(), "3");
							} else if (c1 <= 0 && c2 >= 0) {
								item = new DayAttendence(date3, date1, date2,
										isHoliday, false, false, dayList
												.get(0)
												.selectSingleNode(
														"SPECIAL_TYPE")
												.getText(), dayList
												.get(dayList.size() - 1)
												.selectSingleNode(
														"SPECIAL_TYPE")
												.getText());
							} else if (c1 > 0 && c2 < 0) {
								item = new DayAttendence(date3, date1, date2,
										isHoliday, true, true, "2", "3");
							} else {
								item = new DayAttendence(date3, date1, date2,
										isHoliday, true, false, "2", dayList
												.get(dayList.size() - 1)
												.selectSingleNode(
														"SPECIAL_TYPE")
												.getText());
							}
						}
						dayA.add(item);
					}
					double numOfNormalDay = 0;
					int numOfSpecial = 0;
					int numOfALeave = 0;
					int numOfSLeave = 0;
					int numOfELeave = 0;
					int numOfLeave = 0;
					double workingDayReally = 0;
					boolean isHardWorking = false;
					boolean isFullWorking = false;
					boolean isFull930 = true;
					int counter = 0;
					for (int j = 0; j < dayA.size(); j++) {
						if (dayA.get(j).getIsHoliday() != true) {
							numOfNormalDay = numOfNormalDay + 1;
						}
						if (dayA.get(j).getOutHour() != null) {
							String work = year + "-" + month + "-"
									+ Integer.toString(j + 1) + " " + priceTime;
							SimpleDateFormat sdf1 = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss");
							Date date1 = sdf1.parse(work);
							int c = date1.compareTo(dayA.get(j).getOutHour());
							if (c <= 0) {
								counter++;
							}
							if (!satWork.contains(Integer.toString(j + 1))) {
								if (dayA.get(j).getIsHoliday() == false) {
									String work1 = year + "-" + month + "-"
											+ Integer.toString(j + 1) + " "
											+ priceTime;
									SimpleDateFormat sdf2 = new SimpleDateFormat(
											"yyyy-MM-dd HH:mm:ss");
									Date date2 = sdf2.parse(work1);
									int c1 = date2.compareTo(dayA.get(j)
											.getOutHour());
									if (c1 > 0) {
										isFull930 = false;
									}
								}
							}
						} else {
							if (dayA.get(j).getIsHoliday() == false) {
								isFull930 = false;
							}
						}
						if (dayA.get(j).getIsHoliday() == false
								&& (dayA.get(j).getASpecialType()
										.equalsIgnoreCase("1")
										|| dayA.get(j).getASpecialType()
												.equalsIgnoreCase("2") || dayA
										.get(j).getASpecialType()
										.equalsIgnoreCase("3"))) {
							numOfSpecial++;
						}
						if (dayA.get(j).getIsHoliday() == false
								&& (dayA.get(j).getMSpecialType()
										.equalsIgnoreCase("1")
										|| dayA.get(j).getMSpecialType()
												.equalsIgnoreCase("2") || dayA
										.get(j).getMSpecialType()
										.equalsIgnoreCase("3"))) {
							numOfSpecial++;
						}
						if (dayA.get(j).getASpecialType().equalsIgnoreCase("0")
								&& dayA.get(j).getInHour() != null) {
							workingDayReally = workingDayReally + 0.5;
						}
						if (dayA.get(j).getMSpecialType().equalsIgnoreCase("0")
								&& dayA.get(j).getOutHour() != null) {
							workingDayReally = workingDayReally + 0.5;
						}
						if (dayA.get(j).getASpecialType().equalsIgnoreCase("4")) {
							numOfALeave++;
						}
						if (dayA.get(j).getMSpecialType().equalsIgnoreCase("4")) {
							numOfALeave++;
						}
						if (dayA.get(j).getASpecialType().equalsIgnoreCase("5")) {
							numOfELeave++;
						}
						if (dayA.get(j).getMSpecialType().equalsIgnoreCase("5")) {
							numOfELeave++;
						}
						if (dayA.get(j).getASpecialType().equalsIgnoreCase("6")) {
							numOfSLeave++;
						}
						if (dayA.get(j).getMSpecialType().equalsIgnoreCase("6")) {
							numOfSLeave++;
						}
						if (dayA.get(j).getASpecialType().equalsIgnoreCase("7")) {
							numOfLeave++;
						}
						if (dayA.get(j).getMSpecialType().equalsIgnoreCase("7")) {
							numOfLeave++;
						}

					}
					int punishment = ((numOfSpecial - (numOfSpecial % 3)) / 3) * 50;
					non = numOfNormalDay;
					if (numOfSLeave == 0 && numOfELeave == 0 && counter >= 15
							&& numOfSpecial == 0) {
						isHardWorking = true;
					}
					if (isHardWorking && isFull930 && numOfALeave == 0) {
						isHardWorking = false;
						isFullWorking = true;
					}
					// System.out.println(satWork);
					if (!satWork.split(",")[0].equalsIgnoreCase("")) {
						numOfNormalDay = numOfNormalDay
								- (satWork.split(",").length * 0.5);
						// System.out.println(numOfNormalDay);
					}
					ItemAttendence ia = new ItemAttendence(user, group, dayA,
							Integer.toString(numOfSpecial),
							Integer.toString(numOfALeave),
							Integer.toString(numOfLeave),
							Integer.toString(numOfELeave),
							Integer.toString(numOfSLeave),
							Double.toString(workingDayReally), isFullWorking,
							isHardWorking, "", Integer.toString(punishment));
					iaL.add(ia);
				}

			}

			AttendenceList al = new AttendenceList(month, year,
					Double.toString(non), iaL);
			
			return gson.toJson(al);
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			return "fail";
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "fail";
			}
		}

	}

	@POST
	@Path("/testGrid")
	@Produces("text/plain; charset=utf-8")
	public String getgriddata(String data) {
		
		return "{\"page\":1,\"total\":239,\"rows\":[{\"id\":\"ZW\",\"cell\":[\"<input type='checkbox'/>\",\"Zimbabwe\",\"Zimbabwe\",\"ZWE\",\"716\"]},{\"id\":\"ZM\",\"cell\":[\"ZM\",\"Zambia\",\"Zambia\",\"ZMB\",\"894\"]},{\"id\":\"YE\",\"cell\":[\"YE\",\"Yemen\",\"Yemen\",\"YEM\",\"887\"]},{\"id\":\"EH\",\"cell\":[\"EH\",\"Western Sahara\",\"Western Sahara\",\"ESH\",\"732\"]},{\"id\":\"WF\",\"cell\":[\"WF\",\"Wallis and Futuna\",\"Wallis and Futuna\",\"WLF\",\"876\"]},{\"id\":\"VI\",\"cell\":[\"VI\",\"Virgin Islands, U.s.\",\"Virgin Islands, U.s.\",\"VIR\",\"850\"]},{\"id\":\"VG\",\"cell\":[\"VG\",\"Virgin Islands, British\",\"Virgin Islands, British\",\"VGB\",\"92\"]},{\"id\":\"VN\",\"cell\":[\"VN\",\"Viet Nam\",\"Viet Nam\",\"VNM\",\"704\"]},{\"id\":\"VE\",\"cell\":[\"VE\",\"Venezuela\",\"Venezuela\",\"VEN\",\"862\"]},{\"id\":\"VU\",\"cell\":[\"VU\",\"Vanuatu\",\"Vanuatu\",\"VUT\",\"548\"]}]}";
	}

	public String generateXML(ResultSet rs) throws SQLException {
		final StringBuffer buffer = new StringBuffer(1024 * 4);
		if (rs == null)
			return "";

		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"); // XML的头部信息
		buffer.append("<RecordSet>");

		ResultSetMetaData rsmd = rs.getMetaData(); // 得到结果集的定义结构
		int colCount = rsmd.getColumnCount(); // 得到列的总数

		// 对放回的全部数据逐一处理
		for (int id = 1; rs.next(); id++) {
			// 格式为row id , col name, col context
			buffer.append("<Record>");
			for (int i = 1; i <= colCount; i++) {
				String type = rsmd.getColumnTypeName(i); // 获取字段类型
				buffer.append("<" + rsmd.getColumnName(i).toUpperCase() + ">");
				buffer.append(getValue(rs, i, type));
				buffer.append("</" + rsmd.getColumnName(i).toUpperCase() + ">");
			}
			buffer.append("</Record>");
		}
		buffer.append("</RecordSet>");
		rs.close();
		return buffer.toString();
	}

	private String getValue(final ResultSet rs, int colNum, String type)
			throws SQLException {
		Object value = null;

		if (type.equalsIgnoreCase("nchar") || type.equalsIgnoreCase("nvarchar"))
			value = rs.getString(colNum);
		else
			value = rs.getObject(colNum);

		if (value != null)
			return value.toString().trim();
		return "null";
	}

}