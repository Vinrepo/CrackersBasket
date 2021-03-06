package com.example.crackersbasket

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteException

//creating the database logic, extending the SQLiteOpenHelper base class
class DatabaseHandler(context: Context?): SQLiteOpenHelper(context,
    DATABASE_NAME,null,
    DATABASE_VERSION
) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "EmployeeDatabase"
        private val TABLE_CONTACTS = "EmployeeTable"
        private val TABLE_LATLONG = "LatlongTable"
        private val KEY_ID = "id"
        private val KEY_NAME = "name"
        private val KEY_EMAIL = "email"
        private val KEY_PHONE = "phone"
        private val KEY_LAT = "lat"
        private val KEY_LONG = "long"

    }
    override fun onCreate(db: SQLiteDatabase?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        //creating table with fields
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT,"+ KEY_PHONE + " TEXT,"+ KEY_LONG + " TEXT," + KEY_LAT + " TEXT" +")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
        val CREATE_LATLONG_TABLE = ("CREATE TABLE " + TABLE_LATLONG + "(" + KEY_LONG + " TEXT," + KEY_LAT + " TEXT" +")")
        db?.execSQL(CREATE_LATLONG_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS)
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_LATLONG)
        onCreate(db)
    }


    //method to insert data
    fun addEmployee(emp: EmpModelClass):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.userId)
        contentValues.put(KEY_NAME, emp.userName) // EmpModelClass Name
        contentValues.put(KEY_EMAIL,emp.userEmail )
        contentValues.put(KEY_PHONE,emp.userphone )
        contentValues.put(KEY_LAT,emp.userlat )
        contentValues.put(KEY_LONG,emp.userlong )// EmpModelClass Phone
        // Inserting Rowx
        val success = db.insert(TABLE_CONTACTS, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    fun addLatlong(emp: LatlongClass):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_LAT,emp.userlat )
        contentValues.put(KEY_LONG,emp.userlong )// EmpModelClass Phone
        // Inserting Rowx
        val success = db.insert(TABLE_LATLONG, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    //method to read data
    fun viewEmployee():List<EmpModelClass>{
        val empList:ArrayList<EmpModelClass> = ArrayList<EmpModelClass>()
        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var userId: Int
        var userName: String
        var userEmail: String
        var userphone: String
        var userlat: String
        var userlong: String
        if (cursor.moveToFirst()) {
            do {
                userId = cursor.getInt(cursor.getColumnIndex("id"))
                userName = cursor.getString(cursor.getColumnIndex("name"))
                userEmail = cursor.getString(cursor.getColumnIndex("email"))
                userphone = cursor.getString(cursor.getColumnIndex("phone"))
                userlat = cursor.getString(cursor.getColumnIndex("lat"))
                userlong = cursor.getString(cursor.getColumnIndex("long"))
                val emp= EmpModelClass(
                    userId = userId,
                    userName = userName,
                    userEmail = userEmail,
                    userphone = userphone,
                    userlat = userlat,
                    userlong = userlong
                )
                empList.add(emp)
            } while (cursor.moveToNext())
        }
        return empList
    }
    fun viewLatlong():List<LatlongClass>{
        val empList:ArrayList<LatlongClass> = ArrayList<LatlongClass>()
        val selectQuery = "SELECT  * FROM $TABLE_LATLONG"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var userlat: String
        var userlong: String
        if (cursor.moveToFirst()) {
            do {
                userlat = cursor.getString(cursor.getColumnIndex("lat"))
                userlong = cursor.getString(cursor.getColumnIndex("long"))
                val emp= LatlongClass(
                    userlat = userlat,
                    userlong = userlong
                )
                empList.add(emp)
            } while (cursor.moveToNext())
        }
        return empList
    }
    //method to update data
    fun updateEmployee(emp: EmpModelClass):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.userId)
        contentValues.put(KEY_NAME, emp.userName) // EmpModelClass Name
        contentValues.put(KEY_EMAIL,emp.userEmail )
        contentValues.put(KEY_PHONE,emp.userphone )
        contentValues.put(KEY_LAT,emp.userlat )
        contentValues.put(KEY_LONG,emp.userlong )// EmpModelClass Email

        // Updating Row
        val success = db.update(TABLE_CONTACTS, contentValues,"id="+emp.userId,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    fun updatelatlong(emp: LatlongClass):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_LAT,emp.userlat )
        contentValues.put(KEY_LONG,emp.userlong )// EmpModelClass Email

        // Updating Row
        val success = db.update(TABLE_LATLONG, contentValues,null,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    //method to delete data
    /*fun deleteEmployee(emp: EmpModelClass):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.userId) // EmpModelClass UserId
        // Deleting Row
        val success = db.delete(TABLE_CONTACTS,"id="+emp.userId,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }*/
}