package net.finch.calendar;

import android.content.*;
import android.database.sqlite.*;
import android.util.*;

class DBHelper extends SQLiteOpenHelper
 {
	public static final String DB_NAME = "mytable";

    public DBHelper(Context context) {
		// конструктор суперкласса
		super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
		//Log.d(LOG_TAG, "--- onCreate database ---");
		// создаем таблицу с полями
		db.execSQL("create table "+DB_NAME+" ("
				   + "id integer primary key autoincrement,"
				   + "date integer,"
				   + "month integer,"
				   + "year integer"
				   + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
