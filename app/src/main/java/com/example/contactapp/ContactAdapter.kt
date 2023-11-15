package com.example.contactapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// ContactAdapter.kt
class ContactAdapter(private val context: Context, private val contacts: List<Contact>) :
    RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    interface OnContactClickListener {
        fun onContactClick(contact: Contact)
        fun onContactContextMenuClick(contact: Contact, view: View)

    }

    private var onContactClickListener: OnContactClickListener? = null

    fun setOnContactClickListener(listener: OnContactClickListener) {
        this.onContactClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ViewHolder(view)
    }

    override  fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = contacts[position]
        holder.nameTextView.text = contact.name

        // Chèn đoạn mã để thiết lập Context Menu Listener
        holder.itemView.setOnCreateContextMenuListener { menu, v, menuInfo ->
            onContactClickListener?.onContactContextMenuClick(contact, v)
        }

        holder.itemView.setOnClickListener {
            onContactClickListener?.onContactClick(contact)
        }
    }

    override fun getItemCount(): Int {
        return contacts.size
    }
    fun getContactAt(position: Int): Contact? {
        return if (position in 0 until contacts.size) {
            contacts[position]
        } else {
            null
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
    }
}
