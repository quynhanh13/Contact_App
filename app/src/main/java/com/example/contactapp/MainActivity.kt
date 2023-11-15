package com.example.contactapp

// MainActivity.kt
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), ContactAdapter.OnContactClickListener {

    private lateinit var contactAdapter: ContactAdapter
    private lateinit var recyclerView: RecyclerView
    private var isContextMenuVisible: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val contacts = getContacts()

        contactAdapter = ContactAdapter(this, contacts)
        contactAdapter.setOnContactClickListener(this)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = contactAdapter

        // Đăng ký RecyclerView cho Context Menu
        registerForContextMenu(recyclerView)
//        recyclerView.setOnCreateContextMenuListener(null)
    }

    override fun onContactClick(contact: Contact) {
        Log.d("ContextMenu", "Context 2 created")
        showContactDetailPopup(contact)
    }

    override fun onContactContextMenuClick(contact: Contact, view: View) {
        // Hiển thị context menu khi nhấn giữ vào đối tượng
        // Chỉ đăng ký RecyclerView cho OnContactClickListener

//        if (isContextMenuVisible) {
//            // Hiển thị context menu khi nhấn giữ vào đối tượng
//            recyclerView.showContextMenu()
//        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        Log.d("ContextMenu", "Context Menu created")
        super.onCreateContextMenu(menu, v, menuInfo)

        menuInflater.inflate(R.menu.context_menu, menu)
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        showToast("hello")
//        Log.d("ContextMenu", "click")
        val  menuInfo = item.menuInfo
        if(menuInfo is AdapterView.AdapterContextMenuInfo){
            Log.d("ContextMenu", "click")
            val position = menuInfo.position
            val contact = contactAdapter.getContactAt(position)

            return when (item.itemId) {
                R.id.menu_call -> {
                    if (contact != null) {
                        showToast("Calling ${contact.name}")
                    }
                    true
                }
                R.id.menu_sms -> {
                    if (contact != null) {
                        showToast("Sending SMS to ${contact.name}")
                    }
                    true
                }
                R.id.menu_email -> {
                    if (contact != null) {
                        showToast("Sending email to ${contact.name}")
                    }
                    true
                }
                else -> super.onContextItemSelected(item)
            }

        }
        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun showContactDetailPopup(contact: Contact) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(contact.name)
            .setMessage("Phone: ${contact.phoneNumber}\nEmail: ${contact.email}")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }

        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun getContacts(): List<Contact> {
        // Hàm này trả về danh sách liên lạc của bạn
        return listOf(
            Contact(1, "John Doe", "123-456-7890", "john@example.com"),
            Contact(2, "Jane Sam", "987-654-3210", "jane@example.com"),
            Contact(3, "John Thomson", "123-456-7890", "john@example.com"),
            Contact(4, "Jane Mai", "987-654-3210", "jane@example.com"),
            Contact(5, "John Mike", "123-456-7890", "john@example.com"),
            Contact(6, "Jane Phillip", "987-654-3210", "jane@example.com"),
            Contact(7, "John Ben", "123-456-7890", "john@example.com"),
            Contact(8, "Jane Mie", "987-654-3210", "jane@example.com"),
            Contact(9, "John Son", "123-456-7890", "john@example.com"),
            Contact(10, "Jane Kate", "987-654-3210", "jane@example.com"),
            // Thêm các đối tượng liên lạc khác nếu cần
        )
    }
}
