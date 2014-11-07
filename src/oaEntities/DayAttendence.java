package oaEntities;

import java.util.Date;

public class DayAttendence {

	private Date date;
	private Date inHour;
	private Date outHour;
	private String dateType;//日期類型，休息日/工作日
	private boolean isMSpecial;//是否是特殊情況(上午)
	private boolean isASpecial;//是否是特殊情況（下午）
	private String mSpecialType;//特殊類型，遲到/早退/缺勤/年假/病假/事假/其他（上午）
	private String aSpecialType;//特殊類型，遲到/早退/缺勤/年假/病假/事假/其他（下午）
	
	
	public DayAttendence(Date date, Date inHour, Date outHour, String dateType,
			boolean isMSpecial, boolean isASpecial, String mSpecialType,
			String aSpecialType) {
		super();
		this.date = date;
		this.inHour = inHour;
		this.outHour = outHour;
		this.dateType = dateType;
		this.isMSpecial = isMSpecial;
		this.isASpecial = isASpecial;
		this.mSpecialType = mSpecialType;
		this.aSpecialType = aSpecialType;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public Date getInHour() {
		return inHour;
	}


	public void setInHour(Date inHour) {
		this.inHour = inHour;
	}


	public Date getOutHour() {
		return outHour;
	}


	public void setOutHour(Date outHour) {
		this.outHour = outHour;
	}


	public String getDateType() {
		return dateType;
	}


	public void setDateType(String dateType) {
		this.dateType = dateType;
	}


	public boolean isMSpecial() {
		return isMSpecial;
	}


	public void setMSpecial(boolean isMSpecial) {
		this.isMSpecial = isMSpecial;
	}


	public boolean isASpecial() {
		return isASpecial;
	}


	public void setASpecial(boolean isASpecial) {
		this.isASpecial = isASpecial;
	}


	public String getmSpecialType() {
		return mSpecialType;
	}


	public void setmSpecialType(String mSpecialType) {
		this.mSpecialType = mSpecialType;
	}


	public String getaSpecialType() {
		return aSpecialType;
	}


	public void setaSpecialType(String aSpecialType) {
		this.aSpecialType = aSpecialType;
	}
	
	
	
}
