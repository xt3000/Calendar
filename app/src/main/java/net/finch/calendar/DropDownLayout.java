package net.finch.calendar;
import android.widget.*;
import android.os.*;
import android.view.View.*;
import android.view.*;
import android.widget.AdapterView.*;
import android.support.design.widget.*;
//import net.finch.calendar.R.*;

public class DropDownLayout
{
	protected MainActivity ma;
	LinearLayout ddLayout;
	BottomSheetBehavior ddBehavor;
	
	DropDownLayout(MainActivity ma){
		this.ma = ma;
	}

	public boolean onLongClickDay(View v)
	{
		ddLayout = (LinearLayout) ma.findViewById(R.id.bottom_sheet);
		ddBehavor = BottomSheetBehavior.from(ddLayout);
	
		
		ddBehavor.setState(BottomSheetBehavior.STATE_EXPANDED);
		
		return true;
	}
}
