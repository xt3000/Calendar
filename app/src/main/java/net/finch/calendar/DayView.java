package net.finch.calendar;

import android.content.*;
import android.util.*;
import android.widget.*;
import android.graphics.*;
import android.view.*;

 
public class DayView extends TextView
{
	private Canvas offscreen;
	private String msg="";
	private boolean markedUp = false;
	private boolean markedDown = false;
	private int colorDown;
	private ViewGroup llweak;
	private Context context;
	
	public DayView(Context context){
		super(context);
		this.context = context;
		
	}
	
	public DayView(Context context, AttributeSet attrs) {
        super(context, attrs);
		this.context = context;
    }
	
	public void setDayText(String s){
		msg = s;
		setText(s);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		llweak = (ViewGroup)this.getParent();
		int side = llweak.getWidth()/7-20;
		setWidth(side);
		setHeight(side);
		// TODO: Implement this method
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		// TODO: Implement this method
		if(canvas==offscreen){
            super.onDraw(offscreen);
        }
        else if (markedUp || markedDown) {
			
			
			int x = getWidth();
			int y = getHeight();
			if(x==0 || y==0) return;
			Bitmap bitmap=Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
			offscreen=new Canvas(bitmap);
			super.draw(offscreen);
			
			
			
			if (markedUp) {
            	//Our offscreen image uses the dimensions of the view rather than the canvas
				Paint paint = new Paint();
            	int radius = 5;
				
            	paint.setStyle(Paint.Style.FILL);
            	paint.setColor(Color.parseColor("#CD5C5C"));
            	canvas.drawCircle(x/2, y/6, radius, paint);
				
			}
			if (markedDown) {
				int r = 3;
				Paint paint = new Paint();
				paint.setStyle(Paint.Style.FILL);
				paint.setColor(colorDown);
				canvas.drawLine(x/r, y-(y/5), x-(x/r), y-(y/5), paint);
				canvas.drawLine(x/r, y-(y/5)+1, x-(x/r), y-(y/5)+1, paint);
				canvas.drawLine(x/r, y-(y/5)+2, x-(x/r), y-(y/5)+2, paint);
			}
			setText(msg);
			super.onDraw(canvas);
		}
		else{
			super.onDraw(canvas);
		} 
		
	}
	
	
	
	public void markedUp(boolean m) {
		markedUp = m;
	}
	
	public void markedDown(boolean m, int color) {
		markedDown = m;
		colorDown = color;
	}
	
	
}
