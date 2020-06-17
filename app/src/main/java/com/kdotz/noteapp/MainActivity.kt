package com.kdotz.noteapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = applicationContext.getSharedPreferences("com.kdotz.noteapp", Context.MODE_PRIVATE)
        listView = findViewById(R.id.listView)

        val set : HashSet<String>? = sharedPreferences.getStringSet("notes", null) as HashSet<String>?

        if(set == null){
            notes.add("Example note")
        } else {
            notes = ArrayList(set)
        }

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, notes)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, NoteEditorActivity::class.java)
            intent.putExtra("noteId", position)
            startActivity(intent)
        }

        listView.setOnItemLongClickListener { _, _, position, _ ->

            AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are you sure?")
                .setMessage("Do you want to delete this note?")
                .setPositiveButton("Yes") { _, _ ->
                    notes.removeAt(position)
                    adapter?.notifyDataSetChanged()
                    val set : HashSet<String> = HashSet(notes)
                    sharedPreferences.edit().putStringSet("notes", set).apply()
                }
                .setNegativeButton("No") { _, _ ->
                }.show()
            true
        }
    }

// MENU

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu
        // This adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_note -> {
                val intent = Intent(this, NoteEditorActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Kotlin Static Variable
    companion object {
        var notes: ArrayList<String> = arrayListOf()
        var adapter: ArrayAdapter<String>? = null
    }
}
