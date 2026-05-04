package com.example.myproject

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(var items: List<Item>, var context: Context, var userLogin: String = ""):
    RecyclerView.Adapter<ItemAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.imageView3)
        val title: TextView = view.findViewById(R.id.item_name)
        val date: TextView = view.findViewById(R.id.item_date)
        val text: TextView = view.findViewById(R.id.item_text)
        val price: TextView = view.findViewById(R.id.item_price)
        val btn: Button = view.findViewById(R.id.button2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]
        holder.title.text = item.name
        holder.date.text = item.date
        holder.text.text = item.text
        holder.price.text = "${item.price} ₽"

        val imageId = context.resources.getIdentifier(
            item.image,
            "drawable",
            context.packageName
        )
        holder.image.setImageResource(imageId)

        holder.btn.setOnClickListener {
            val intent = Intent(context, ItemActivity::class.java)
            intent.putExtra("service_id", item.id)
            intent.putExtra("item_name", item.name)
            intent.putExtra("item_date", item.date)
            intent.putExtra("item_text", item.text)
            intent.putExtra("service_price", item.price ?: 0)
            intent.putExtra("itemImage", imageId)
            intent.putExtra("user_login", userLogin)
            context.startActivity(intent)
        }
    }
}