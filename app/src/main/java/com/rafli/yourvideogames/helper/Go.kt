package com.rafli.yourvideogames.helper

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import com.rafli.yourvideogames.helper.Config.extra_choose
import com.rafli.yourvideogames.helper.Config.extra_id
import com.rafli.yourvideogames.helper.Config.extra_model
import com.rafli.yourvideogames.helper.Config.extra_other
import com.rafli.yourvideogames.helper.Config.extra_type
import com.rafli.yourvideogames.helper.Config.extra_url

class Go(var ctx: Context) {


    fun move(
        target: Class<*>,
        type: String = "",
        id: Int = 0,
        listData: List<Parcelable> = ArrayList(),
        data: Parcelable? = null,
        url: String = "",
        other: String = "",
        choose: Boolean = false,
        clearPrevious: Boolean = false
    ) {
        val listParcelable: ArrayList<Parcelable> = ArrayList()
        listParcelable.addAll(listData)

        val go = Intent(ctx, target)
        go.putExtra(extra_id, id)
        go.putExtra(extra_type, type)
        go.putExtra(extra_other, other)
        go.putExtra(extra_choose, choose)
        go.putExtra(Config.extra_list, listParcelable)
        go.putExtra(extra_url, url)
        if (data != null) {
            go.putExtra(extra_model, data)
        }

        if (clearPrevious) {
            go.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        ctx.startActivity(go)

    }


}