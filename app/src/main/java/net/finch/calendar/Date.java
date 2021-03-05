package net.finch.calendar;
import java.util.*;

public class Date
{
	int id;
	String date;
	int monthOffset; // (-1,0,1)
	int month;
	//int year;
	Boolean mark;
	
	public Date(int id, String date, int monthOffset, Boolean mark) {
		this.id = id;
		this.date = date;
		this.monthOffset = monthOffset;
		//this.year = year;
		this.mark = mark;
		
		//Calendar c = new GregorianCalendar(year, m
	}
	
	
	int getId() {
		return id;
	}
	
	String getDate() {
		return date;
	}
	
	int getMonthOffset() {
		return monthOffset;
	}
	
	
	
	boolean isMarked(){
		return mark;
	}
}
