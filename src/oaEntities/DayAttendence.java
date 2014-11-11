package oaEntities;

import java.util.Date;

public class DayAttendence {

	private Date date;
	private Date inHour;
	private Date outHour;
	private boolean isHoliday;//日期類型，休息日/工作日
	private boolean isMSpecial;//是否是特殊情況(上午)
	private boolean isASpecial;//是否是特殊情況（下午）
	private String mSpecialType;//特殊類型，遲到/早退/缺勤/年假/病假/事假/其他（上午）
	private String aSpecialType;//特殊類型，遲到/早退/缺勤/年假/病假/事假/其他（下午）
	
	
	public DayAttendence(Date date, Date inHour, Date outHour, boolean isHoliday,
			boolean isMSpecial, boolean isASpecial, String mSpecialType,
			String aSpecialType) {
		super();
		this.date = date;
		this.inHour = inHour;
		this.outHour = outHour;
		this.isHoliday = isHoliday;
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


	public boolean getIsHoliday() {
		return isHoliday;
	}


	public void setIsHoliday(boolean isHoliday) {
		this.isHoliday = isHoliday;
	}


	public boolean getIsMSpecial() {
		return isMSpecial;
	}


	public void setMSpecial(boolean isMSpecial) {
		this.isMSpecial = isMSpecial;
	}


	public boolean getIsASpecial() {
		return isASpecial;
	}


	public void setASpecial(boolean isASpecial) {
		this.isASpecial = isASpecial;
	}


	public String getMSpecialType() {
		return mSpecialType;
	}


	public void setMSpecialType(String mSpecialType) {
		this.mSpecialType = mSpecialType;
	}


	public String getASpecialType() {
		return aSpecialType;
	}


	public void setASpecialType(String aSpecialType) {
		this.aSpecialType = aSpecialType;
	}
	
	
	
}
