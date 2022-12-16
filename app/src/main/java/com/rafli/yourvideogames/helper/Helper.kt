package com.rafli.yourvideogames.helper

import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object Helper {

//    fun ByteArray.toHexString() =


    fun toHexString(data: ByteArray): String{
        return data.asUByteArray().joinToString("") {
            it.toString(16).padStart(2, '0')
        }
    }

    fun convertDate(date: String, from: String, to: String): String {
        var hasil = ""

        val dateFormat = SimpleDateFormat(from, Locale.getDefault())

        if (date.isEmpty()){
            return date
        }

        try {
            val dd = dateFormat.parse(date)
            dateFormat.applyPattern(to)
            hasil = dd?.let { dateFormat.format(it) }.toString()
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return hasil
    }

    fun fromHtml(source: String): Spanned? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(source)
        }
    }


    //MAKE LOG RESUCE
    fun log(place: String = "", msg: String){
        //CHECK RELEASE MODE [if release log not show]

        if (!Validasi().isRelease()){
            if (place.isBlank()){
                Log.e("MGS", msg)
            }else{
                Log.e("MGS " + place, msg)
            }
        }

    }

    fun getCurrentDateTime():String{
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }


}