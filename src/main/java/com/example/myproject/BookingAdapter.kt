package com.example.myproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookingAdapter(val bookings: List<DBHelper.BookingItem>, val context: Context) :
    RecyclerView.Adapter<BookingAdapter.BookingViewHolder>() {

    class BookingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.item_name)
        val date: TextView = view.findViewById(R.id.item_date)
        val text: TextView = view.findViewById(R.id.item_text)
        val image: ImageView = view.findViewById(R.id.imageView3)
        val backButton: TextView = view.findViewById(R.id.item_back)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.appointment_activity, parent, false)
        return BookingViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bookings.size
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        try {
            val booking = bookings[position]
            holder.name.text = booking.name
            holder.date.text = booking.time
            holder.text.visibility = View.GONE
            holder.backButton.visibility = View.GONE

            val imageId = context.resources.getIdentifier(
                booking.imageName,
                "drawable",
                context.packageName
            )
            if (imageId != 0) {
                holder.image.setImageResource(imageId)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}