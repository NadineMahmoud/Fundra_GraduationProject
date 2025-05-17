package com.example.account

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.fundra.menu.AccountItem
import com.example.fundra.R

class AccountAdapter(
    private val items: List<AccountItem>,
    private val onItemClick: (AccountItem) -> Unit
) : RecyclerView.Adapter<AccountAdapter.ViewHolder>() {

    private var expandedPosition = -1

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon: ImageView = itemView.findViewById(R.id.itemIcon)
        private val text: TextView = itemView.findViewById(R.id.itemText)
        private val arrow: ImageView = itemView.findViewById(R.id.itemArrow)
        private val contactDetailsContent: LinearLayout? = itemView.findViewById(R.id.contact_details_content)
        private val nameTextView: TextView? = itemView.findViewById(R.id.nameTextView)
        private val emailTextView: TextView? = itemView.findViewById(R.id.email)
        private val phoneTextView: TextView? = itemView.findViewById(R.id.phone_number)
        private val social : TextView? = itemView.findViewById(R.id.social_media)


        fun bind(item: AccountItem, isExpanded: Boolean) {
            icon.setImageResource(item.icon)
            text.text = item.text

            if (item.text == "Personal details") {
                contactDetailsContent?.visibility = if (isExpanded) View.VISIBLE else View.GONE
                arrow.setImageResource(if (isExpanded) R.drawable.arrow_downward else R.drawable.arrow_android)

                nameTextView?.text = item.name ?: "Unknown"
                emailTextView?.text = item.email ?: "No email"
                phoneTextView?.text = item.phone ?: "No phone"
                social?.text = item.social ?: "No social"

                itemView.setOnClickListener {
                    val previousExpandedPosition = expandedPosition
                    expandedPosition = if (adapterPosition == expandedPosition) -1 else adapterPosition
                    notifyItemChanged(previousExpandedPosition)
                    notifyItemChanged(adapterPosition)
                }
            } else {
                contactDetailsContent?.visibility = View.GONE
                arrow.setImageResource(R.drawable.arrow_android)

                itemView.setOnClickListener {
                    onItemClick(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_account, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, position == expandedPosition)
    }

    override fun getItemCount(): Int = items.size
}
