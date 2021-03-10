package net.finch.calendar;

import android.content.*;
import android.util.*;
import android.widget.*;
import android.graphics.*;

 
public class DayView extends TextView
{
	private Canvas offscreen;
	private String msg="";
	private boolean marked = false;
	
	public DayView(Context context){
		super(context);
	}
	
	public DayView(Context context, AttributeSet attrs) {
        super(context, attrs);
		
		msg = "13";
		//setText("23");
    }
	
	public void setTex(String s){
		msg = s;
		//setTextColor(0xff000000);
		setText(s);
		//invalidate();
		
	}
	
	public void marked(boolean m) {
		marked = m;
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		
		// TODO: Implement this method
		if(canvas==offscreen){
            super.onDraw(offscreen);
        }
        else if(marked) {
            //Our offscreen image uses the dimensions of the view rather than the canvas
            Bitmap bitmap=Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            offscreen=new Canvas(bitmap);
            super.draw(offscreen);
			Paint paint = new Paint();
			
			
			
			int x = getWidth();
            int y = getHeight();
            int radius;
            radius = 5;
            paint.setStyle(Paint.Style.FILL);
            //paint.setColor(Color.WHITE);
           // canvas.drawPaint(paint);
            // Use Color.parseColor to define HTML colors
            paint.setColor(Color.parseColor("#CD5C5C"));
            canvas.drawCircle(x/2, y/6, radius, paint);
			setText(msg);
			super.onDraw(canvas);
		}
		else super.onDraw(canvas);
		
	}
	
	
}
