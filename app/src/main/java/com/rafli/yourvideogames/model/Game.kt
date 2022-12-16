package com.rafli.yourvideogames.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class Game() : java.io.Serializable, Parcelable {

    @PrimaryKey(autoGenerate = true)
    var id:Int = 0

    @ColumnInfo(name = "name")
    var name:String = ""

    @ColumnInfo(name = "background_image")
    var background_image:String = ""

    @ColumnInfo(name = "rating")
    var rating:Double = 0.0

    @ColumnInfo(name = "released")
    var released:String = ""

    @ColumnInfo(name = "playtime")
    var playtime:Int = 0

    @ColumnInfo(name = "description")
    var description:String = ""


    //error value
    var error:String = ""

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        name = parcel.readString() ?: ""
        background_image = parcel.readString() ?: ""
        rating = parcel.readDouble()
        released = parcel.readString() ?: ""
        playtime = parcel.readInt()
        description = parcel.readString() ?: ""
        error = parcel.readString() ?: ""
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(background_image)
        parcel.writeDouble(rating)
        parcel.writeString(released)
        parcel.writeInt(playtime)
        parcel.writeString(description)
        parcel.writeString(error)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Game> {
        override fun createFromParcel(parcel: Parcel): Game {
            return Game(parcel)
        }

        override fun newArray(size: Int): Array<Game?> {
            return arrayOfNulls(size)
        }
    }
}