package com.example.notesapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.gtappdevelopers.noteapplication.Note
import java.text.SimpleDateFormat
import java.util.Date

class AddEditNoteActivity : AppCompatActivity() {
    lateinit var noteTitleEdit: EditText
    lateinit var noteDescriptionEdit: EditText
    lateinit var addupdateBtn: Button
    lateinit var viewModel: NoteViewModel
    var noteID = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)
        noteTitleEdit = findViewById(R.id.idEdtNoteName)
        noteDescriptionEdit = findViewById(R.id.idEdtNoteDesc)
        addupdateBtn = findViewById(R.id.idBtn)
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)

        val noteType = intent.getStringExtra("noteType")
        if(noteType.equals("Edit")){
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDesc = intent.getStringExtra("noteDescription")
            noteID = intent.getIntExtra("noteID", -1)
            addupdateBtn.setText("Обновить записку")
            noteTitleEdit.setText(noteTitle)
            noteDescriptionEdit.setText(noteDesc)
        } else{
            addupdateBtn.setText("Сохранить записку")
        }

        addupdateBtn.setOnClickListener{
            val noteTitle = noteTitleEdit.text.toString()
            val noteDescription = noteDescriptionEdit.text.toString()
            if(noteType.equals("Edit")){
                if(noteTitle.isNotEmpty() && noteDescription.isNotEmpty()){
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDate:String = sdf.format(Date())
                    val updatenote = Note(noteTitle, noteDescription, currentDate)
                    updatenote.id = noteID
                    viewModel.updateNote(updatenote)
                    Toast.makeText(this, "Записка обновлена...", Toast.LENGTH_LONG).show()
                }
            }else{
                if(noteTitle.isNotEmpty() && noteDescription.isNotEmpty()){
                        val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                        val currentDate:String = sdf.format(Date())
                        viewModel.addNote(Note(noteTitle, noteDescription, currentDate))
                        Toast.makeText(this, "Записка добавлена...", Toast.LENGTH_LONG).show()
                    }
            }
                startActivity(Intent(applicationContext, MainActivity::class.java))
                this.finish()
        }
    }
}