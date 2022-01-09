package com.example.notepad.database

import androidx.room.*


@Dao
interface NoteDao {
    //this interface will hold the functions you will use to access your Entities
    @Insert
    suspend fun addNote(note: Note)

    @Query("SELECT * FROM Note ORDER BY id DESC" )
    suspend fun getAllNotes(): List<Note>

    @Insert
    suspend fun addMultipleNotes(vararg note: Note)

        @Update
     suspend fun updateNote(note: Note)

     @Delete
     suspend fun deleteNote(note: Note)


}