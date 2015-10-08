package com.is3261.splurge.db;

import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.is3261.splurge.model.MenuCategory;

/**
 * Created by junwen29 on 10/7/2015.
 */
public class SplurgeDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "SplurgeDatabaseHelper";
    private static final String DB_NAME = "splurge";
    private static final String DB_SUFFIX = ".db";
    private static final int DB_VERSION = 1;

    private static SplurgeDatabaseHelper mInstance;

    private SplurgeDatabaseHelper(Context context) {
        //prevents external instance creation
        super(context, DB_NAME + DB_SUFFIX, null, DB_VERSION);
    }

    private static SplurgeDatabaseHelper getInstance(Context context) {
        if (null == mInstance) {
            mInstance = new SplurgeDatabaseHelper(context);
        }
        return mInstance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(OwnerTable.CREATE);
        //TODO Expenses Types
        //TODO Trip Types
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /* no-op */
    }

    private static SQLiteDatabase getReadableDatabase(Context context) {
        return getInstance(context).getReadableDatabase();
    }

    private static SQLiteDatabase getWritableDatabase(Context context) {
        return getInstance(context).getWritableDatabase();
    }
}
