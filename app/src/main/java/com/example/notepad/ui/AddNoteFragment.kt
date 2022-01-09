package com.example.notepad.ui

import android.os.AsyncTask
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import com.example.notepad.R
import com.example.notepad.database.Note
import com.example.notepad.database.NoteDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class AddNoteFragment : BaseFragment() {
    lateinit var btnSave: FloatingActionButton
    lateinit var edTitle: EditText
    lateinit var body: EditText

    private var note: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//used to display options so we can have our delete option
        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {

            note = AddNoteFragmentArgs.fromBundle(it).note

            edTitle = view.findViewById(R.id.title)
            body = view.findViewById(R.id.notes)

            edTitle.setText(note?.title)

            body.setText(note?.note)



        }

        btnSave = view.findViewById(R.id.fb_save)
        btnSave.setOnClickListener {view2 ->

            edTitle = view.findViewById(R.id.title)
            body = view.findViewById(R.id.notes)

            val notetitle = edTitle.text.toString().trim()
            val noteBody = body.text.toString().trim()

            //check that the title is not blank
            if (notetitle.isBlank()){
                edTitle.error = "Title required"
                edTitle.requestFocus()
                return@setOnClickListener
            }
            // check that the note body is not blank so that you don't save empty text
            if (noteBody.isBlank()){
                body.error = "Note body required"
                body.requestFocus()
                return@setOnClickListener
            }
            //coroutine asynchronous call
            launch {

                // now you instantiate your note entity inside the coroutine scope

                context?.let {
                    val mNote = Note(notetitle, noteBody)

                    if (note == null){

                        NoteDatabase(it).getNoteDao().addNote(mNote)
                        it.toast("Note saved successfully")
                    }else{

                        mNote.id = note!!.id

                        NoteDatabase(it).getNoteDao().updateNote(mNote)
                        it.toast("Note updated successfully")
                    }

                    //navigate back to the home fragment where the notes are displayed after saving it
                    val action = AddNoteFragmentDirections.actionAddNoteFragmentToHomeFragment()
                    Navigation.findNavController(view2).navigate(action)
                }
            }

        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu, menu)
    }


    private fun deleteNote(){
        AlertDialog.Builder(requireContext()).apply {

            setTitle("Are you sure")
            setMessage("You cannot undo this delete operation")
            setPositiveButton("yes"){_,_ ->

                launch { NoteDatabase(context).getNoteDao().deleteNote(note!!) }

                val action = AddNoteFragmentDirections.actionAddNoteFragmentToHomeFragment()
                Navigation.findNavController(requireView()).navigate(action)
            }

            setNegativeButton("No"){_,_ ->
            //if the user clicks no, then nothing happens
            }
        }.create().show()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.delete -> {

                if (note != null){
                    deleteNote()
                }else{
                    context?.toast("Cannot delete note")
                }

            }
        }
        return super.onOptionsItemSelected(item)


    }

}
























//saveNote(note)

//this function will save the note asynchronously since adding data to Room cannot be done in the main thread
//    private fun saveNote(note: Note){
//                //this  SaveNote class is extending AsyncTask so the task will run asynchronously
//        class SaveNote: AsyncTask<Void, Void, Void>(){
//            override fun doInBackground(vararg params: Void?): Void? {
//
//                NoteDatabase(requireActivity()).getNoteDao().addNote(note)
//
//                return null
//            }
//
//            override fun onPostExecute(result: Void?) {
//                super.onPostExecute(result)
//
//                Toast.makeText(requireActivity(), "Note added Successfully", Toast.LENGTH_SHORT).show()
//            }
//
//        }
//
//        SaveNote().execute()
//    }