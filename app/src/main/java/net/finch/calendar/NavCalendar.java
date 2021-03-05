package net.finch.calendar;
import android.content.*;
import android.database.sqlite.*;
import java.util.*;
import android.database.*;
import android.widget.*;



public class NavCalendar
{
	static DBHelper dbHelper;
	static Context context;
	static Calendar c;
	static int month;
	static int year;
	static ArrayList<Integer> markDates;
	
	public NavCalendar(Context context) {
		dbHelper = new DBHelper(context);
		this.context = context;
		c = new GregorianCalendar();
		init();
	}
	
	public NavCalendar(Context context,int y, int m, int d) {
		dbHelper = new DBHelper(context);
		this.context = context;
		c = new GregorianCalendar(y, m, d);
		init();
	}
	
	
	static void init() {
		year = c.get(GregorianCalendar.YEAR);
		month = c.get(GregorianCalendar.MONTH);
		//markDates = dbReadMarkedDates();
		//frameOfDates();
	}
	
	static void nextMonth() {
		c.add(GregorianCalendar.MONTH, 1);
		init();
	}
	
	static void previousMonth() {
		c.add(GregorianCalendar.MONTH, -1);
		init();
	}
	
	
	String getMonth() {
		return Month.getString(c.get(GregorianCalendar.MONTH));
	}
	
	static String getYear() {
		return String.valueOf(c.get(GregorianCalendar.YEAR));
	}
	
	static int firstWeakDayOfMonth() {
		Calendar c1 = new GregorianCalendar(year, month, 1);
		int day = c1.get(GregorianCalendar.DAY_OF_WEEK);
		if(day > 1) day--;
		else day = 7;
		
		return day;
	}
	
	static int maxDateInPreviousMonth() {
		Calendar cpm = new GregorianCalendar(year, month-1, 1);
		
		return cpm.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
	}
	
	static ArrayList<Date> frameOfDates() {
		//SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		
		Boolean mark = false;
		int cnt = 0;
		int fwd = firstWeakDayOfMonth();
		int mda = c.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		int mdp = maxDateInPreviousMonth();
		ArrayList<Date> fod = new ArrayList<>();
		
		dbReadMarkedDates(-1);
	/*	String str = "";
		for(int i=0; i<markDates.size(); i++) 
			str += "|"+markDates.get(i);
		Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
		*/
		if (fwd > 1) {
			int firstDate = mdp-fwd+2;
			for (int i=0; i<fwd-1; i++) {
				mark = false;
				for (int m=0; m<markDates.size(); m++) {
					if (firstDate+i == markDates.get(m)) mark = true;
				}
				fod.add(new Date(cnt, String.valueOf(firstDate+i), -1, mark));
				cnt++;
			}
		}
		
		dbReadMarkedDates(0);
		
		for (int i=1; i<=mda; i++) {
			mark=false;
			for (int m=0; m<markDates.size(); m++) {
				if (i == markDates.get(m)) mark = true;
			}
			fod.add(new Date(cnt, String.valueOf(i), 0, mark));
			cnt++;
		}
		
		dbReadMarkedDates(1);
		for (int i=1; i<=(42-cnt); i++) {
			mark = false;
			for (int m=0; m<markDates.size(); m++) {
				if (i == markDates.get(m)) mark = true;
			}
			fod.add(new Date(cnt+i, String.valueOf(i), 1, mark));
		}
		return fod;
	}
	
	static void dbReadMarkedDates(int offset) {
		markDates = new ArrayList<>();
		Calendar mc = new GregorianCalendar(year, month+offset,1);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		//String toast = "";
		String select = "year = ? and month = ?";
		String[] selArgs = {String.valueOf(mc.get(GregorianCalendar.YEAR)), String.valueOf(mc.get(GregorianCalendar.MONTH))};
		Cursor cur = db.query(DBHelper.DB_NAME, null, select, selArgs, null, null, null);
		
		int n;
		if (cur.moveToFirst()) {
			do {
				n = cur.getInt(cur.getColumnIndex("date"));
				//toast += " "+n;
				markDates.add(n);
			}while(cur.moveToNext());

		}
		cur.close();
		db.close();

		/*if(offset == -1)
			Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
		*/
		//return markDates;
	}
}
