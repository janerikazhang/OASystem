package oaEntities;

import java.util.List;

public class ItemAttendence {

	private String employeeName;
	private String departmentName;
	private List<DayAttendence> attendenceList;
	private String numberOfSpecial;
	private String numberOfALeave;
	private String numberOfIllegalLeave;
	private String numberOfEventLeave;
	private String numberOfSickLeave;
	private String numberOfDayOnline;
	private boolean isFullAttendence;
	private boolean isHardWorking;
	private String remark;
	private String punishment;
	public ItemAttendence(String employeeName, String departmentName,
			List<DayAttendence> attendenceList, String numberOfSpecial,
			String numberOfALeave, String numberOfIllegalLeave,
			String numberOfEventLeave, String numberOfSickLeave,
			String numberOfDayOnline, boolean isFullAttendence,
			boolean isHardWorking, String remark, String punishment) {
		super();
		this.employeeName = employeeName;
		this.departmentName = departmentName;
		this.attendenceList = attendenceList;
		this.numberOfSpecial = numberOfSpecial;
		this.numberOfALeave = numberOfALeave;
		this.numberOfIllegalLeave = numberOfIllegalLeave;
		this.numberOfEventLeave = numberOfEventLeave;
		this.numberOfSickLeave = numberOfSickLeave;
		this.numberOfDayOnline = numberOfDayOnline;
		this.isFullAttendence = isFullAttendence;
		this.isHardWorking = isHardWorking;
		this.remark = remark;
		this.punishment = punishment;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public List<DayAttendence> getAttendenceList() {
		return attendenceList;
	}
	public void setAttendenceList(List<DayAttendence> attendenceList) {
		this.attendenceList = attendenceList;
	}
	public String getNumberOfSpecial() {
		return numberOfSpecial;
	}
	public void setNumberOfSpecial(String numberOfSpecial) {
		this.numberOfSpecial = numberOfSpecial;
	}
	public String getNumberOfALeave() {
		return numberOfALeave;
	}
	public void setNumberOfALeave(String numberOfALeave) {
		this.numberOfALeave = numberOfALeave;
	}
	public String getNumberOfIllegalLeave() {
		return numberOfIllegalLeave;
	}
	public void setNumberOfIllegalLeave(String numberOfIllegalLeave) {
		this.numberOfIllegalLeave = numberOfIllegalLeave;
	}
	public String getNumberOfEventLeave() {
		return numberOfEventLeave;
	}
	public void setNumberOfEventLeave(String numberOfEventLeave) {
		this.numberOfEventLeave = numberOfEventLeave;
	}
	public String getNumberOfSickLeave() {
		return numberOfSickLeave;
	}
	public void setNumberOfSickLeave(String numberOfSickLeave) {
		this.numberOfSickLeave = numberOfSickLeave;
	}
	public String getNumberOfDayOnline() {
		return numberOfDayOnline;
	}
	public void setNumberOfDayOnline(String numberOfDayOnline) {
		this.numberOfDayOnline = numberOfDayOnline;
	}
	public boolean isFullAttendence() {
		return isFullAttendence;
	}
	public void setFullAttendence(boolean isFullAttendence) {
		this.isFullAttendence = isFullAttendence;
	}
	public boolean isHardWorking() {
		return isHardWorking;
	}
	public void setHardWorking(boolean isHardWorking) {
		this.isHardWorking = isHardWorking;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPunishment() {
		return punishment;
	}
	public void setPunishment(String punishment) {
		this.punishment = punishment;
	}
	
}
