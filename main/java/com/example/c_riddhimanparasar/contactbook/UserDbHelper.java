
package com.example.c_riddhimanparasar.contactbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class UserDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "USERINFO.db";
    private static final int DATABASE_VERSION = 2;
    private static final String CREATE_TABLE_TODO = "CREATE TABLE "
            + UserContract.TABLE_NAME + "(" + UserContract.USER_NAME + " TEXT," + UserContract.USER_CONTACT
            + " TEXT" + ")";
    private static UserDbHelper userDbHelper = null;
    boolean flag = false;
    private Cursor cursorRetrieve;

    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TODO);
        Log.d("ski002", "Database Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserContract.TABLE_NAME);
        onCreate(db);
    }

    public static UserDbHelper getInstance(Context context) {
        if (userDbHelper == null)
            return new UserDbHelper(context);
        else return userDbHelper;
    }

    public boolean blockContact(String name, String contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        cursorRetrieve = checkInfo();
        if (cursorRetrieve != null) {
            cursorRetrieve.moveToFirst();
            while (cursorRetrieve.moveToNext()) {
                if (cursorRetrieve.getString(cursorRetrieve.getColumnIndex(UserContract.USER_CONTACT)).contains(contact)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(UserContract.USER_NAME, name);
                contentValues.put(UserContract.USER_CONTACT, contact);
                long val = db.insert(UserContract.TABLE_NAME, null, contentValues);
                Log.d("ski002", "Info added with" + val);
                return true;
            }
        }
        return false;
    }

    public Cursor retrieveInfo() {

        SQLiteDatabase db = this.getReadableDatabase();
        String[] projections = {UserContract.USER_NAME, UserContract.USER_CONTACT};
        return db.query(UserContract.TABLE_NAME, projections, null, null, null, null, null);
    }

    public Cursor checkInfo() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + UserContract.USER_CONTACT + " FROM " + UserContract.TABLE_NAME + ";";
        return db.rawQuery(query, null);
    }
}
/*
package com.example.c_riddhimanparasar.contactbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import static com.example.c_riddhimanparasar.contactbook.UserContract.TABLE_NAME;
import static com.example.c_riddhimanparasar.contactbook.UserContract.USER_CONTACT;
import static com.example.c_riddhimanparasar.contactbook.UserContract.USER_NAME;

public class UserDbHelper extends SQLiteOpenHelper {

    private static UserDbHelper userDbHelper = null;
    private static final String DATABASE_NAME = "BLOCKCONTACT.db";
    private final static int DATABASE_VERSION = 5;
    private static final String CREATE_TABLE_TODO = "CREATE TABLE "
            + TABLE_NAME + "(" + USER_NAME + " TEXT," + USER_CONTACT
            + " TEXT)";
    private List<ContactDetails> list = new ArrayList<>();

    public static UserDbHelper getInstance(Context context) {
        if (userDbHelper == null)
            return new UserDbHelper(context);
        else return userDbHelper;
    }

    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TODO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void blockContact(String name, String contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.USER_NAME, name);
        contentValues.put(UserContract.USER_CONTACT, contact);
        db.insert(UserContract.TABLE_NAME, null, contentValues);
        db.endTransaction();
        db.rawQuery("select * from " + TABLE_NAME, null);
        //Log.d("skk002", "val is" + val);
    }

    public List<ContactDetails> retrieveInfo() {
        */
/*SQLiteDatabase db = this.getWritableDatabase();
        String[] projections = {UserContract.USER_NAME, UserContract.USER_CONTACT};
        return db.rawQuery("select * from " + TABLE_NAME, null);*//*

 */
/*SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);


        return cursor;*//*

        String countQuery = "SELECT  * FROM " + UserContract.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        //int count = cursor.getCount();
        cursor.moveToFirst();
        do {
            String name = cursor.getString(0);
            String contact = cursor.getString(1);
            ContactDetails contactDetails = new ContactDetails();
            contactDetails.setName(name);
            contactDetails.setContact(contact);
            list.add(contactDetails);
        } while (cursor.moveToNext());

        cursor.close();
        return list;
    }
}
*/
