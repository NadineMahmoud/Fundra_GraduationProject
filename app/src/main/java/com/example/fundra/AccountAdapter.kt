package com.example.account

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.fundra.AccountItem
import com.example.fundra.R

class AccountAdapter(
    private val items: List<AccountItem>,
    private val onClick: (AccountItem) -> Unit
) : RecyclerView.Adapter<AccountAdapter.ViewHolder>() {

    private var expandedPosition: Int = -1
    private var isSaveIconPerson: Boolean = true

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardItem: CardView = itemView.findViewById(R.id.card_item)
        val mainCardContent: View = itemView.findViewById(R.id.main_card_content)
        val icon: ImageView = itemView.findViewById(R.id.itemIcon)
        val text: TextView = itemView.findViewById(R.id.itemText)
        val arrow: ImageView = itemView.findViewById(R.id.itemArrow)
        val contactDetailsContent: View? = itemView.findViewById(R.id.contact_details_content)
        val saveIcon: ImageView? = itemView.findViewById(R.id.save_icon)

        fun bind(item: AccountItem, position: Int, adapter: AccountAdapter) {
            icon.setImageResource(item.icon)
            text.text = item.text

            val isExpanded = position == adapter.expandedPosition
            if (item.text == "Personal details") {
                contactDetailsContent?.visibility = if (isExpanded) View.VISIBLE else View.GONE
                arrow.setImageResource(if (isExpanded) R.drawable.arrow_down else R.drawable.arrow)

                mainCardContent.setOnClickListener {
                    val previousExpandedPosition = adapter.expandedPosition
                    adapter.expandedPosition = if (isExpanded) -1 else position
                    adapter.notifyItemChanged(previousExpandedPosition)
                    adapter.notifyItemChanged(position)
                }

                saveIcon?.setImageResource(if (adapter.isSaveIconPerson) R.drawable.person else R.drawable.saved)

                saveIcon?.setOnClickListener {
                    adapter.isSaveIconPerson = !adapter.isSaveIconPerson
                    saveIcon.setImageResource(if (adapter.isSaveIconPerson) R.drawable.person else R.drawable.saved)
                }
            } else {
                contactDetailsContent?.visibility = View.GONE
                arrow.setImageResource(R.drawable.arrow)

                cardItem.setOnClickListener {
                    adapter.onClick(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_account, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, position, this)
    }

    override fun getItemCount(): Int = items.size
}