package com.example.osasproject.models

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import android.content.ContentValues


class AirCouchDBStore(context: Context, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int)
    : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION), AirCouchStore {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_AIRCOUCH_TABLE = ("CREATE TABLE " +
                TABLE_AIRCOUCHS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_TITLE
                + " TEXT," + COLUMN_ADDRESS + " TEXT" + COLUMN_SPACES + "TEXT" + COLUMN_TYPE + ")")
        db.execSQL(CREATE_AIRCOUCH_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_AIRCOUCHS")
        onCreate(db)
    }

    companion object {

        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "aircouchDB.db"
        val TABLE_AIRCOUCHS = "aircouchs"

        val COLUMN_ID = "_id"
        val COLUMN_TITLE = "title"
        val COLUMN_ADDRESS = "address"
        val COLUMN_SPACES = "spaces"
        val COLUMN_TYPE = " type"
    }

    override fun findAll(): List<AirCouchModel> {
        val query = "SELECT * FROM $TABLE_AIRCOUCHS"
        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)

        val aircouchs = ArrayList<AirCouchModel>()

        if (cursor.moveToFirst()) {
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                val id = Integer.parseInt(cursor.getString(0)).toLong()
                val title = cursor.getString(1)
                val address = cursor.getString(2)
                val spaces = cursor.getString(3)
                val type =  cursor.getString(4)
                aircouchs.add(AirCouchModel(id, title = title, address = address, spaces = spaces, type = type))
                cursor.moveToNext()
            }
            cursor.close()
        }
        db.close()
        return aircouchs
    }

    override fun create(aircouch: AirCouchModel) {
        val values = ContentValues()
        values.put(COLUMN_TITLE, aircouch.title)
        values.put(COLUMN_ADDRESS, aircouch.address)
        values.put(COLUMN_SPACES,aircouch.spaces)
        values.put(COLUMN_TYPE, aircouch.type)

        val db = this.writableDatabase

        db.insert(TABLE_AIRCOUCHS, null, values)
        db.close()
    }

    override fun update(aircouch: AirCouchModel) {
        val values = ContentValues()
        values.put(COLUMN_TITLE, aircouch.title)
        values.put(COLUMN_ADDRESS, aircouch.address)
        values.put(COLUMN_SPACES,aircouch.spaces)
        values.put(COLUMN_TYPE, aircouch.type)

        val db = this.writableDatabase

        db.insert(TABLE_AIRCOUCHS, null, values)
        db.close()
    }

   override fun delete(aircouch: AirCouchModel) {
        //("Not yet implemented")
    }
}