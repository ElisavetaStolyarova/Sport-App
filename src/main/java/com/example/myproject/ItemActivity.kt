package com.example.myproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ItemActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_list)

        val image: ImageView = findViewById(R.id.imageView3)
        val title: TextView = findViewById(R.id.item_name)
        val date: TextView = findViewById(R.id.item_date)
        val text: TextView = findViewById(R.id.item_text)
        val price: TextView = findViewById(R.id.item_price)
        val btn: Button = findViewById(R.id.button2)

        val userLogin = intent.getStringExtra("user_login") ?: ""
        val serviceId = intent.getIntExtra("service_id", -1)
        val serviceName = intent.getStringExtra("item_name") ?: ""
        val serviceTime = intent.getStringExtra("item_date") ?: ""
        val servicePrice = intent.getIntExtra("service_price", 0)

        title.text = serviceName
        date.text = serviceTime
        text.text = intent.getStringExtra("item_text")
        price.text = "${servicePrice} ₽"
        image.setImageResource(intent.getIntExtra("itemImage", -1))

        val dbHelper = DBHelper(this, null)
        val userId = dbHelper.getUserIdByLogin(userLogin)

        if (userId != -1 && serviceId != -1) {
            if (dbHelper.isAlreadyBooked(userId, serviceId)) {
                btn.isEnabled = false
                btn.text = "Вы уже записаны"
            }
        }

        btn.setOnClickListener {
            if (userId == -1) {
                Toast.makeText(this, "Ошибка: пользователь не найден", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            dbHelper.addBooking(userId, serviceId, serviceName, serviceTime, servicePrice)
            Toast.makeText(this, "Вы успешно записаны на $serviceName", Toast.LENGTH_LONG).show()

            finish()
        }
    }
}