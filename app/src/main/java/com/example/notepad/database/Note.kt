package com.example.notepad.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.ParcelField
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
@Entity
data class Note(
    //the attributes are the columns of the table,
    // while the name of the class is the the name of the table as well

        //if you want different column names, you can use this annotation @ColumnInfo(name = "note_title"),
        // else the column names will be same as the variable names
        val title: String,
         val note: String
):Parcelable{

    @PrimaryKey
        (autoGenerate = true)
    var id: Int = 0 //the id is a primary key, and will be generated automatically
}
