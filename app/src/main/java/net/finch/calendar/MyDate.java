package net.finch.calendar;
import java.util.*;

public class MyDate
{
	int id;
	Calendar cal;
	int monthOffset; // (-1,0,1)
	//int month;
	//int year;
	Boolean mark;
	
	public MyDate(int id, Calendar cal, int monthOffset, Boolean mark) {
		this.id = id;
		this.cal = cal;
		this.monthOffset = monthOffset;
		//this.year = cal.get(GregorianCalendar.YEAR);
		//this.month = cal.get(GregorianCalendar.MONTH);
		this.mark = mark;
		
		//Calendar c = new GregorianCalendar(year, m
	}
	
	
	int getId() {
		return id;
	}
	
	int getDate() {
		return cal.get(GregorianCalendar.DATE);
	}
	
	String getDateString() {
		return String.valueOf(getDate());
	}
	
	int getMonthOffset() {
		return monthOffset;
	}
	
	int getMonth() {
		return cal.get(GregorianCalendar.MONTH);
	}
	
	String getMonthString() {
		return String.valueOf(getMonth());
	}
	
	int getYear() {
		return cal.get(GregorianCalendar.YEAR);
	}
	
	String getYearString() {
		return String.valueOf(getYear());
	}
	
	Calendar getCalendar() {
		return cal;
	}
	
	boolean isMarked(){
		return mark;
	}
}
