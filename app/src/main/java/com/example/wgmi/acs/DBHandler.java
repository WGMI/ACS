package com.example.wgmi.acs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WGMI on 03/11/2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB = "acs";

    private static final String ITEMS_TABLE = "items";

    private static final String SNO = "serial_no";
    private static final String NAME = "name";
    private static final String SPECIFICATIONS = "specifications";
    private static final String IMAGE = "image";
    private static final String CATEGORY = "category";
    private static final String STATUS = "status";
    private static final String AVAILABLE = "available";

    public DBHandler(Context context) {
        super(context, DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table items(" +
                "serial_no text primary key," +
                "name text," +
                "specifications text," +
                "image text," +
                "category text," +
                "status int," +
                "available int)";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ITEMS_TABLE);
        onCreate(db);
    }

    public void addItem(Item item){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SNO,item.getSno());
        values.put(NAME,item.getName());
        values.put(SPECIFICATIONS,item.getSpecifications());
        values.put(IMAGE,item.getImage());
        values.put(CATEGORY,item.getCategory());
        values.put(STATUS,item.getStatus());
        values.put(AVAILABLE,item.getAvailable());
        db.insert(ITEMS_TABLE,null,values);
    }

    public Item getItem(String sno){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                ITEMS_TABLE,
                null,
                SNO + " =?",
                new String[]{sno},
                null,null,null,null
        );
        if (cursor != null){
            cursor.moveToFirst();
        }
        Item item = new Item(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getInt(5),
                cursor.getInt(6)
        );
        cursor.close();
        return item;
    }

    public List<Item> getAllItems(){
        SQLiteDatabase db = getReadableDatabase();
        List<Item> items = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from " + ITEMS_TABLE,null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        for(int i=0;i<this.countItems();i++){
            Item item = new Item(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5),
                    cursor.getInt(6)
            );
            cursor.moveToNext();
            items.add(item);
            if(cursor.isAfterLast()){
                break;
            }
        }
        return items;
    }

    public void updateURLItem(String url){
        SQLiteDatabase db = getWritableDatabase();
        /*ContentValues values = new ContentValues();
        values.put(NAME,item.getName());
        return db.update(ITEMS_TABLE,values,SNO + " = ?",new String[]{item.getSno()});*/
        Cursor c = db.rawQuery("update items set name = " + url + " where serial_no = URL",null);
        c.moveToFirst();
        c.close();
    }

    public void deleteAll(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(ITEMS_TABLE,"1",null);
    }

    public void deleteItem(String sno){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(ITEMS_TABLE,SNO + " = ?",new String[] {sno});
    }

    public int countItems(){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + ITEMS_TABLE;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount() > 0){
            return cursor.getCount();
        } else{
            return 0;
        }
    }
}
