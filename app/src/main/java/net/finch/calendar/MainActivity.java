package net.finch.calendar;

//import android.*;
import android.annotation.*;
import android.content.*;
import android.database.sqlite.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.os.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.view.View.*;
import android.view.ViewTreeObserver.*;
import android.widget.*;
import java.util.*;

import android.support.v7.widget.Toolbar;

//import android.support.v7.widget.*;


public class MainActivity extends AppCompatActivity
{
	final boolean DEBUG = true;
	String debugTxt="";
	
	OnClickListener onDayClick;
	LinearLayout[] llWeaks = new LinearLayout[6];
	LinearLayout ll;
	
	TextView tv;
	TextView tvMonth;
	TextView tvYear;
	DayView tvDebag;
	DayView dv;
	
	
	
	
	int width;
	int padding;
	int count = 0;
	
	
	NavCalendar nCal = new NavCalendar(this);
	ArrayList<MyDate> frameOfDates;
	DBHelper dbhelper = new DBHelper(this);
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
		
		toolbar.setTitle("Factory Calendar");
		toolbar.setSubtitle("Калкндарь потребления воды");
		
		setSupportActionBar(toolbar);
		
			
		llWeaks[0] = (LinearLayout)findViewById(R.id.LL_w1);
		llWeaks[1] = (LinearLayout) findViewById(R.id.LL_w2);
		llWeaks[2] = (LinearLayout) findViewById(R.id.LL_w3);
		llWeaks[3] = (LinearLayout) findViewById(R.id.LL_w4);
		llWeaks[4] = (LinearLayout) findViewById(R.id.LL_w5);
		llWeaks[5] = (LinearLayout) findViewById(R.id.LL_w6);
			
		TextView tvNext = (TextView) findViewById(R.id.tv_nextMonth);
		TextView tvPrev = (TextView) findViewById(R.id.tv_prevMonth);
		
		tvDebag = (DayView) findViewById(R.id.tv_debag);
		if (DEBUG == false)
			tvDebag.setVisibility(View.GONE);
		tvDebag.setTex("tvDebag");
		tvDebag.marked(true);
		
			
		dv = (DayView) findViewById(R.id.dv_debag);
		dv.setTex("spasibo");
		dv.marked(true);
		dv.setTex("dildo");
		
		tvMonth = (TextView) findViewById(R.id.tv_month);
		tvYear = (TextView) findViewById(R.id.tv_year);
		
		LinearLayout llp= (LinearLayout) findViewById(R.id.LL_wdays);
		ll = (LinearLayout) findViewById(R.id.LL_w1);
		width = llWeaks[0].getWidth()/7;
		
		llWeaks[0].getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener(){

				
				@SuppressLint("NewApi")
				@SuppressWarnings("deprecation")
				@Override
				public void onGlobalLayout() {

					// Ensure you call it only once :
					if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
						llWeaks[0].getViewTreeObserver().removeOnGlobalLayoutListener(this);
					}
					else {
						llWeaks[0].getViewTreeObserver().removeGlobalOnLayoutListener(this);
					}

					width = (llWeaks[0].getWidth()/7)-20;
					updFrame();
				}
		});
				 
			
		
		
		padding = llp.getPaddingTop();
		
		
		
		/// Слушатель смены месяца
		OnClickListener onChengeMonth = new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch(v.getId()) {
					case R.id.tv_prevMonth:
						nCal.previousMonth();
						updFrame();
						break;
					case R.id.tv_nextMonth:
						nCal.nextMonth();
						updFrame();
						
						break;
				}
			}
		};
		
		tvNext.setOnClickListener(onChengeMonth);
		tvPrev.setOnClickListener(onChengeMonth);
		
    }
	
	void inflateWeak(LinearLayout ll) {
		
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.setMargins(10,10,10,10);
		
		for (int i=1; i<=7; i++) {
			tv = new TextView(this);
			if (count<frameOfDates.size()) 
				tv.setText(frameOfDates.get(count).getDateString());
			tv.setId(count);
			tv.setLayoutParams(lp);
			tv.setGravity(Gravity.CENTER);
			tv.setWidth(width);
			tv.setHeight(width);
			
		/*	/// Шахматная доска
			if(count%2==0) tv.setBackgroundColor(0xfff0f0f0);
			else tv.setBackgroundColor(0xfff6f6f6);
			*/
			/// Выделение текущего месяца
			if (frameOfDates.get(count).getMonthOffset() == 0){
				tv.setTypeface(Typeface.DEFAULT_BOLD);
			}else tv.setTextColor(0x55808080);
			
			/// Выделение отмеченных дат
			if (frameOfDates.get(count).isMarked()) {
				Drawable markDrawable = getDrawable(R.drawable.circle);
				int markColorText = 0xffffffff;
				if (frameOfDates.get(count).getMonthOffset() != 0) {
					markDrawable.setAlpha(80);
					markColorText = 0x55808080;
				}
				tv.setTextColor(markColorText);
				tv.setBackground(markDrawable);
				//tv.setBackground(getDrawable(android.R.drawable.ic_menu_search
			}
				
			//debag
			
			debugTxt += ""+nCal.getNow().getTime().getHours()+".";
			debugTxt += nCal.getNow().getTime().getMinutes()+".";
			debugTxt += nCal.getNow().getTime().getSeconds();
			debugTxt += "("+frameOfDates.get(count).getId()+")\n";
			
			/// Выделение сегодняшней даты
			Calendar now = nCal.getNow();
			if (now.get(Calendar.YEAR) == frameOfDates.get(count).getCalendar().get(Calendar.YEAR)
				&& now.get(Calendar.DAY_OF_YEAR) == frameOfDates.get(count).getCalendar().get(Calendar.DAY_OF_YEAR)
				&& frameOfDates.get(count).getMonthOffset() == 0)
				tv.setTextColor(0xffff6670);
			
			/// Слушатель нажатия на дату
			tv.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v)
					{
						
						MyDate day = frameOfDates.get(v.getId());
						boolean marked = day.isMarked();
						
						SQLiteDatabase db = dbhelper.getWritableDatabase();
						Calendar c = new GregorianCalendar(nCal.year, nCal.month+day.getMonthOffset(), 1);
						String y = String.valueOf(c.get(GregorianCalendar.YEAR));
						String m = String.valueOf(c.get(GregorianCalendar.MONTH));
						String d = day.getDateString();
						
						if(!marked) {
							
							ContentValues cv = new ContentValues();
							
							cv.put("year", Integer.valueOf(y));
							cv.put("month", Integer.valueOf(m));
							cv.put("date", Integer.valueOf(day.getDate()));

							db.insert(DBHelper.DB_NAME, null, cv);
						}else {
							String select = "year = ? and month = ? and date = ?";
							String[] selArgs = {y, m, d};
							
							db.delete(dbhelper.DB_NAME, select, selArgs);
						}
						db.close();
						updFrame();
					}		
			});
			
			//tvDebag.setTex(""+debugTxt);
			
			ll.addView(tv);
			count++;
		}
		
		if (count>=42) count=0;
	}
	
	
	
	void updFrame() {
		debugTxt = "";
		frameOfDates = nCal.frameOfDates();
		tvYear.setText(nCal.getYear()+"г.");
		tvMonth.setText(nCal.getMonth());
		for (int i=0; i<6; i++) {
			llWeaks[i].removeAllViews();
			inflateWeak(llWeaks[i]);
		}
	}
	
	
	
	
}
