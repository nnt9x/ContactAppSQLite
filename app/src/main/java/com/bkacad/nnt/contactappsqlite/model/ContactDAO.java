package com.bkacad.nnt.contactappsqlite.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bkacad.nnt.contactappsqlite.database.DAO;
import com.bkacad.nnt.contactappsqlite.database.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class ContactDAO implements DAO<Contact> {

    private DBHelper dbHelper;

    public  ContactDAO(DBHelper dbHelper) {
       this.dbHelper = dbHelper;
    }

    @Override
    public List<Contact> all() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM contacts";
        Cursor cursor = db.rawQuery(sql, null);
        List<Contact> list = new ArrayList<>();
        if(cursor.moveToFirst()){
            int indexId  = cursor.getColumnIndex("id");
            int indexName = cursor.getColumnIndex("name");
            int indexPhone = cursor.getColumnIndex("phone");
            int indexAddress = cursor.getColumnIndex("address");

            do{
                long id = cursor.getLong(indexId);
                String name = cursor.getString(indexName);
                String phone = cursor.getString(indexPhone);
                String address = cursor.getString(indexAddress);
                Contact contact = new Contact(id, name, address, phone);
                list.add(contact);
            }
            while(cursor.moveToNext());
        }

        cursor.close();
        return list;
    }

    @Override
    public Contact get(long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM contacts WHERE id="+id;
        Cursor cursor = db.rawQuery(sql, null);
        List<Contact> list = new ArrayList<>();
        Contact contact = null;
        if(cursor.moveToFirst()){
            int indexId  = cursor.getColumnIndex("id");
            int indexName = cursor.getColumnIndex("name");
            int indexPhone = cursor.getColumnIndex("phone");
            int indexAddress = cursor.getColumnIndex("address");

            String name = cursor.getString(indexName);
            String phone = cursor.getString(indexPhone);
            String address = cursor.getString(indexAddress);
            contact = new Contact(id, name, address, phone);;
        }

        cursor.close();
        return contact;
    }

    @Override
    public long create(Contact item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", item.getName());
        contentValues.put("phone", item.getPhone());
        contentValues.put("address", item.getAddress());
        long id = db.insert("contacts",null, contentValues );
        return id;
    }

    @Override
    public int edit(Contact item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", item.getName());
        contentValues.put("address", item.getAddress());
        contentValues.put("phone", item.getPhone());
        int rs = db.update("contacts",contentValues,"id = "+item.getId(), null );
        return rs;
    }

    @Override
    public int delete(Contact item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rs = db.delete("contacts","id = "+item.getId(), null);
        return rs;
    }
}
