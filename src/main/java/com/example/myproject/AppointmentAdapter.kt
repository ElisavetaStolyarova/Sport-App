package com.example.myproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AppointmentAdapter(val bookings: List<DBHelper.BookingItem>, var context: Context) :
    RecyclerView.Adapter<AppointmentAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.item_name)
        val date: TextView = view.findViewById(R.id.item_date)
        val text: TextView = view.findViewById(R.id.item_text)
        val image: ImageView = view.findViewById(R.id.imageView3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = bookings.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val booking = bookings[position]
        holder.name.text = booking.name
        holder.date.text = booking.time
        holder.text.text = "Запись от ${booking.date}"

        holder.image.setImageResource(android.R.drawable.ic_menu_info_details)
    }
}