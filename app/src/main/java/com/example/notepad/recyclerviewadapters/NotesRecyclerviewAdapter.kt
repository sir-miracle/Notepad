package com.example.notepad.recyclerviewadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.R
import com.example.notepad.database.Note
import com.example.notepad.ui.HomeFragmentDirections

class NotesRecyclerviewAdapter(private val notes: List<Note>): RecyclerView.Adapter<NotesRecyclerviewAdapter.NotesViewHolder>() {

     class NotesViewHolder(val view: View): RecyclerView.ViewHolder(view){
           lateinit var theTitle: TextView
           lateinit var theBody: TextView

         init{
            theTitle = view.findViewById(R.id.tv_title) as TextView
             theBody = view.findViewById(R.id.tv_note_description) as TextView

         }

     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.notes_recyclerview_layout, parent, false ))
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.theTitle.text = notes[position].title
        holder.theBody.text = notes[position].note

        //make the recyclerview clickable
        holder.view.setOnClickListener {


            val action = HomeFragmentDirections.actionHomeFragmentToAddNoteFragment()

                // action.note = notes[position].note

            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun getItemCount(): Int {
       return notes.size
    }


}