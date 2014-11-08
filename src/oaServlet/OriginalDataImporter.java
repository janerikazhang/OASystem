/************************************************************************
日  期：		2011/11/23
作  者:		李融
版  本：     4.5
描  述:	    用户数据导入导出
历  史：		
 ************************************************************************/
package oaServlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;





import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import restService.Example;

/**
 * 
 * @author LiRong
 */
@SuppressWarnings("serial")
public class OriginalDataImporter extends HttpServlet
{
	/**
	 * 构造函数
	 */
	public OriginalDataImporter() 
	{
		                                                             
	}

	/**
	 * 操作日志
	 * 
	 * @param message
	 */
	private void logOperator(String message,String token)
	{
		
	}
	
	public void destory() 
	{
		super.destroy();
	}

	/**
	 * 初始化器
	 * 
	 * @throws ServletException Servlet异常
	 */
	public void init() throws ServletException 
	{
	}

	/**
	 * 执行http的Post请求
	 * 
	 * @param request 请求内容
	 * @param response 响应内容
	 * @throws ServletException Servlet异常
	 * @throws IOException 输入输出异常
	 */
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		Example example = new Example();
		example.getAttendenceResult();
		//String token = request.getSession().getAttribute("usertoken").toString();
		String result = "";
		
		String url = request.getRequestURL().toString();
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		try 
		{
			InputStream importFile = null;
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = upload.parseRequest(request);
			Iterator iter = items.iterator();
			
			//解析http请求取出excel文件流
			while (iter.hasNext())
			{
				FileItem item = (FileItem) iter.next();
				
				String typeName = item.getFieldName();
				//只取文件
				if (item.isFormField())
				{	
					
				}
				else
				{
					
					importFile = item.getInputStream();
					//result = importData(importFile);
					
				}
			}
			
			PrintWriter out = response.getWriter();
			//result = "{\"content\":\""+result+"\"}";
			out.println(result);
			//分情况返回添加结果(有无错误/有无重复行)
			
		}
		catch (Exception e) 
		{
			
		}
		
	}

	/**
	 * 执行http的Get请求
	 * 
	 * @param request 请求内容
	 * @param response 响应内容
	 * @throws ServletException Servlet异常
	 * @throws IOException 输入输出异常
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
	}

	/**
	 * 导入用户
	 * 
	 * @param excelFile 人员所对应的excel文档
	 * @return String[] 导入的结果(成功的行数/错误行/已存在行)
	 */
	@SuppressWarnings("unchecked")
	private String importData(InputStream excelFile)
	{
		Workbook workbook =null;
		Sheet sheet = null;
		try
		{
			workbook = Workbook.getWorkbook(excelFile);
			sheet = workbook.getSheet(0); 
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "fail";
		}
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://192.168.5.101:3306/OASystem", "danlihome","ld7vd6yt");
			Statement statamentMySQL = con.createStatement();
			con.setAutoCommit(false);
			for (int i = 0;i<sheet.getRows();i++){
				if (i >= 1){
					Cell[] cells = sheet.getRow(i);
					if (!"".equalsIgnoreCase(cells[0].getContents().trim())){
						String yearMonthDate = cells[2].getContents().trim();
						String [] ymd = yearMonthDate.split("/");
						String newYearMonthDate = ymd[2]+"-"+ymd[1]+"-"+ymd[0];
						String time = cells[3].getContents().trim();
						String dateTime = newYearMonthDate+" "+time;
						statamentMySQL.executeUpdate("INSERT INTO OA_ATTENDENCE  (USER_NAME,GROUP_NAME,ATTENDENCE_TIME) values ('"+cells[1].getContents().trim()+"','"+cells[0].getContents()+"','"+dateTime+"')");
					}
				}
				//cells[1].getContents().trim();
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
}