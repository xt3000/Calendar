package net.finch.calendar;
import android.content.*;
import android.database.sqlite.*;
import android.view.*;
import java.util.*;
import net.finch.calendar.*;

public class DayOnClickListener implements View.OnClickListener
{
	private MainActivity ma;
	DBHelper dbhelper;
	
	public DayOnClickListener(MainActivity ma){
		this.ma = ma;
		dbhelper = new DBHelper(ma);
	}
	

	@Override
	public void onClick(View v)
	{
		
		
		MyDate day = MainActivity.frameOfDates.get(v.getId());
		boolean marked = day.isMarked();

		SQLiteDatabase db = dbhelper.getWritableDatabase();
		//String y = String.valueOf(c.get(GregorianCalendar.YEAR));
		//String m = String.valueOf(c.get(GregorianCalendar.MONTH));
		//String d = day.getDateString();

		if(!marked) {

			ContentValues cv = new ContentValues();

			cv.put("year", day.getYear());
			cv.put("month", day.getMonth());
			cv.put("date", day.getDate());

			db.insert(DBHelper.DB_NAME, null, cv);
		}else {
			String select = "year = ? and month = ? and date = ?";
			String[] selArgs = {day.getYearString(), day.getMonthString(), day.getDateString()};

			db.delete(dbhelper.DB_NAME, select, selArgs);
		}
		db.close();
		ma.updFrame();
	}
	
}
