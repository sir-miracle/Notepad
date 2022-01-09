package com.example.notepad.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase: RoomDatabase() {
//inside this abstract class you will create functions to get all your DAOs

    abstract  fun getNoteDao(): NoteDao

    //we now build the room database with a companion object
    companion object{
         @Volatile//volatile means that this instance is immediately available for other threads
         private var instance: NoteDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) =  instance?: synchronized(LOCK){

            instance ?: buildRoomDatabase(context).also {

                instance = it

            }


        }

        private fun buildRoomDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NoteDatabase::class.java,
            "MyNoteDatabase"
        ).build()


    }



}