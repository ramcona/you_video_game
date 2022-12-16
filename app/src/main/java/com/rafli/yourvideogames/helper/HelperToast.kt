package com.rafli.yourvideogames.helper

import android.content.Context
import android.widget.Toast

open class HelperToast {
    fun show(msg: String, context: Context){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

}