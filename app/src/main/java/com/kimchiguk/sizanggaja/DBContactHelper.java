package com.kimchiguk.sizanggaja;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kimchiguk.sizanggaja.model.StoreForm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MinJae on 2015-10-27.
 */
public class DBContactHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME  = "Test.db";
    private static final String TABLE_NAME = "sizangtest";


    public DBContactHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+"(ID TEXT PRIMARY KEY,NAME TEXT,PHONE_NUMBER TEXT,LATITUDE TEXT,LONGITUDE TEXT,BLOG_URL TEXT,MAIN_IMG TEXT,MAIN_SALE TEXT)";

        db.execSQL(CREATE_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void addContact(StoreForm m_form) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id", m_form.getId());
        values.put("name", m_form.getName());
        values.put("phone_number", m_form.getPhone_number());
        values.put("latitude", m_form.getLatitude());
        values.put("longitude", m_form.getLongitude());
        values.put("blog_url", m_form.getBlog_url());
        values.put("main_img", m_form.getMain_img());
        values.put("main_sale", m_form.getMain_sale());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<StoreForm> getAllMenu() {
        List<StoreForm> Menu_List = new ArrayList<StoreForm>();

        String selectQuery = "SELECT * FROM "+TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()) {
            do {
                StoreForm new_menu= new StoreForm(
                        cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7)
                );
                Menu_List.add(new_menu);
            } while(cursor.moveToNext());

        }
        return Menu_List;
    }

    public void deleteMenu(StoreForm del_menu) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id"+" = ?",new String[] { String.valueOf(del_menu.getId())});
        db.close();

    }



    public Boolean preventDup(StoreForm check_Menu) {

        String selectQuery = "SELECT * FROM "+TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        while(cursor.moveToNext()) {
            if(cursor.getString(0).equals(check_Menu.getId()))
                return true;

        }

        return false;
    }


    public void deleteAll() {
        String selectQuery = "delete from "+TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(selectQuery);
    }


}
