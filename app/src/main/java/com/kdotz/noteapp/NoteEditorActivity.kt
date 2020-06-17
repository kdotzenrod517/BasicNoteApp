package com.kdotz.noteapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class NoteEditorActivity : AppCompatActivity() {

    private lateinit var editText: EditText

    var noteId : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_editor)

        editText = findViewById(R.id.editText)

        val intent = intent
        noteId = intent.getIntExtra("noteId", -1)

        if (noteId != -1) {
            editText.setText(MainActivity.notes[noteId])
        } else {
            MainActivity.notes.add("")
            noteId = MainActivity.notes.size - 1
        }

        editText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                MainActivity.notes[noteId] = s.toString()
                MainActivity.adapter?.notifyDataSetChanged()
                val sharedPreferences : SharedPreferences = applicationContext.getSharedPreferences("com.kdotz.noteapp", Context.MODE_PRIVATE)
                val set : HashSet<String> = HashSet(MainActivity.notes)
                sharedPreferences.edit().putStringSet("notes", set).apply()
            }
        })
    }
}
