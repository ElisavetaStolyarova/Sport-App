package com.example.myproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AppointmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item)

        val backButton = findViewById<Button>(R.id.button3)
        backButton.setOnClickListener {
            val userLogin = intent.getStringExtra("user_login") ?: ""
            val intent = Intent(this, ItemsActivity::class.java)
            intent.putExtra("user_login", userLogin)
            startActivity(intent)
            finish()
        }

        try {
            val dbHelper = DBHelper(this, null)
            val userLogin = intent.getStringExtra("user_login") ?: ""

            val titleTextView = findViewById<TextView>(R.id.textView3)

            if (userLogin.isEmpty()) {
                titleTextView.text = "Ошибка: пользователь не авторизован"
                return
            }

            val userId = dbHelper.getUserIdByLogin(userLogin)

            if (userId == -1) {
                titleTextView.text = "Ошибка: пользователь не найден"
                return
            }

            val bookings = dbHelper.getUserBookings(userId)

            val list = findViewById<RecyclerView>(R.id.item_List)

            if (list == null) {
                titleTextView.text = "Ошибка: не найден RecyclerView"
                return
            }

            list.layoutManager = LinearLayoutManager(this)

            if (bookings.isEmpty()) {
                titleTextView.text = "Ваши записи\n\n У вас пока нет записей"
            } else {
                titleTextView.text = "Ваши записи\n\n У вас ${bookings.size} активн${if (bookings.size == 1) "ая запись" else "ых записи"}"
                list.adapter = BookingAdapter(bookings, this)
                list.visibility = RecyclerView.VISIBLE

            }
        } catch (e: Exception) {
            e.printStackTrace()
            val titleTextView = findViewById<TextView>(R.id.textView3)
            titleTextView.text = "Ошибка: ${e.message}"
        }
    }
}