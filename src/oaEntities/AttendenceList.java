package oaEntities;

import java.util.List;

public class AttendenceList {
	public String monthOdAttendence;
	public String yearOdAttendence;
	public String fullDays;
	public List<ItemAttendence> listItems;
	
	
	


	public String getYearOdAttendence() {
		return yearOdAttendence;
	}


	public void setYearOdAttendence(String yearOdAttendence) {
		this.yearOdAttendence = yearOdAttendence;
	}


	public AttendenceList(String monthOdAttendence, String yearOdAttendence,
			String fullDays, List<ItemAttendence> listItems) {
		super();
		this.monthOdAttendence = monthOdAttendence;
		this.yearOdAttendence = yearOdAttendence;
		this.fullDays = fullDays;
		this.listItems = listItems;
	}


	public String getMonthOdAttendence() {
		return monthOdAttendence;
	}


	public void setMonthOdAttendence(String monthOdAttendence) {
		this.monthOdAttendence = monthOdAttendence;
	}


	public String getFullDays() {
		return fullDays;
	}


	public void setFullDays(String fullDays) {
		this.fullDays = fullDays;
	}


	public List<ItemAttendence> getListItems() {
		return listItems;
	}


	public void setListItems(List<ItemAttendence> listItems) {
		this.listItems = listItems;
	}
	
	
}
