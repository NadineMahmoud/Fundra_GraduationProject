package com.example.fundra.balance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.fundra.R

class CardsAdapter (private val cardsList: List<Int>) :
        RecyclerView.Adapter<CardsAdapter.ImageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.cardView.setImageResource(cardsList[position])
    }

    override fun getItemCount(): Int {
        return cardsList.size
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cardView: ImageView = itemView.findViewById(R.id.card_view)
    }
}
