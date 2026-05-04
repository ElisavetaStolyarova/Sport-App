package com.example.myproject

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(val context: Context, val factory: SQLiteDatabase.CursorFactory?) :
  SQLiteOpenHelper(context, "app", factory, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, login TEXT, email TEXT, password TEXT)"
        db!!.execSQL(query)

        val queryBookings = "CREATE TABLE bookings (id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, service_id INTEGER, service_name TEXT, service_time TEXT, service_price INTEGER, date TIMESTAMP DEFAULT CURRENT_TIMESTAMP)"
        db.execSQL(queryBookings)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS users")
        db.execSQL("DROP TABLE IF EXISTS bookings")
        onCreate(db)
    }

    fun addUser(user: User){
        val values = ContentValues()
        values.put("login", user.login)
        values.put("email", user.email)
        values.put("password", user.password)

        val db = this.writableDatabase
        db.insert("users", null, values)

        db.close()
    }

    fun getUser(login: String, password: String) : Boolean{
        val db = this.readableDatabase
        val result = db .rawQuery("SELECT * FROM users WHERE login = '$login' AND password = '$password'", null )
        return result.moveToFirst()
    }



    fun getUserIdByLogin(login: String): Int {
    val db = this.readableDatabase
    val cursor = db.rawQuery("SELECT id FROM users WHERE login = ?", arrayOf(login))
    var userId = -1
    if (cursor.moveToFirst()) {
        userId = cursor.getInt(0)
    }
    cursor.close()
    db.close()
    return userId
    }

     fun isUserExists(login: String): Boolean {
    val db = this.readableDatabase
    val cursor = db.rawQuery("SELECT * FROM users WHERE login = ?", arrayOf(login))
    val exists = cursor.moveToFirst()
    cursor.close()
    db.close()
    return exists }



    fun addBooking(userId: Int, serviceId: Int, serviceName: String, serviceTime: String, servicePrice: Int) {
        val values = ContentValues()
        values.put("user_id", userId)
        values.put("service_id", serviceId)
        values.put("service_name", serviceName)
        values.put("service_time", serviceTime)
        values.put("service_price", servicePrice)

        val db = this.writableDatabase
        db.insert("bookings", null, values)
        db.close()
    }

    fun getUserBookings(userId: Int): List<BookingItem> {
        val bookings = mutableListOf<BookingItem>()
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT service_name, service_time, service_price, date, service_id FROM bookings WHERE user_id = ? ORDER BY date DESC",
            arrayOf(userId.toString())
        )

        while (cursor.moveToNext()) {
            val serviceId = cursor.getInt(4)

            val imageName = when (serviceId) {
                1 -> "ioga"
                2 -> "helfy"
                3 -> "pilates"
                4 -> "ras"
                else -> "ioga"
            }

            bookings.add(
                BookingItem(
                    name = cursor.getString(0),
                    time = cursor.getString(1),
                    price = cursor.getInt(2),
                    date = cursor.getString(3),
                    imageName = imageName
                )
            )
        }
        cursor.close()
        db.close()
        return bookings
    }

    fun isAlreadyBooked(userId: Int, serviceId: Int): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM bookings WHERE user_id = ? AND service_id = ?",
            arrayOf(userId.toString(), serviceId.toString())
        )
        val exists = cursor.moveToFirst()
        cursor.close()
        db.close()
        return exists
    }


    data class BookingItem(
        val name: String,
        val time: String,
        val price: Int,
        val date: String,
        val imageName: String
    )
}
