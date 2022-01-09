package com.example.notepad.ui

import android.os.Bundle
import android.service.controls.actions.FloatAction
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notepad.R
import com.example.notepad.database.NoteDatabase
import com.example.notepad.recyclerviewadapters.NotesRecyclerviewAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment() {

    lateinit var fbAdd: FloatingActionButton
    lateinit var notesRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
         super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notesRecyclerView = view.findViewById(R.id.notes_recyclerview)
        notesRecyclerView.setHasFixedSize(true)

        notesRecyclerView.layoutManager = LinearLayoutManager(context)


        //to fetch data from the room database

        launch {
            context?.let {
                val notes = NoteDatabase(it).getNoteDao().getAllNotes()

                notesRecyclerView.adapter = NotesRecyclerviewAdapter(notes)
            }
        }

        fbAdd = view.findViewById(R.id.fb_add)
        fbAdd.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAddNoteFragment()
            Navigation.findNavController(it).navigate(action)

        }
    }

}