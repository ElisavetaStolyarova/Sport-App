package com.example.myproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ItemsActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_in_list)

        val userLogin = intent.getStringExtra("user_login") ?: ""

        val itemsList: RecyclerView = findViewById(R.id.itemList)
        val items = arrayListOf<Item>()
        val btnMy = findViewById<Button>(R.id.appointment_button)

        btnMy.setOnClickListener {
            val intent = Intent(this, AppointmentActivity::class.java)
            intent.putExtra("user_login", userLogin)
            startActivity(intent)
        }

        items.add(Item(1, "ioga", "Йога", "10:00", "Занятия йогой в приятной, душевной атмосфере", 700))
        items.add(Item(2, "helfy", "Здоровая спина", "12:00", "Без боли. Без напряжения. Свободно", 800))
        items.add(Item(3, "pilates", "Пилатес", "14:00", "Пилатес для тех, кто выбирает здоровье с умом", 1000))
        items.add(Item(4, "ras", "Растяжка", "16:30", "Растяжка, расслабься и вытянись", 850))

        itemsList.layoutManager = LinearLayoutManager(this)
        itemsList.adapter = ItemAdapter(items, this, userLogin)
    }
}