package com.rafli.yourvideogames.adapter.holder

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rafli.yourvideogames.R
import com.rafli.yourvideogames.app.App.Companion.helper
import com.rafli.yourvideogames.databinding.ItemGameBinding
import com.rafli.yourvideogames.helper.Go
import com.rafli.yourvideogames.model.Game
import com.rafli.yourvideogames.ui.activity.detailGame.DetailGameActivity

class GameHolder(var item:ItemGameBinding): RecyclerView.ViewHolder(item.root) {

    val context: Context = itemView.context

    @SuppressLint("SetTextI18n")
    fun setData(data: Game){
        Glide.with(context).load(data.background_image).placeholder(R.drawable.ic_loading_image).error(R.drawable.ic_loading_image).into(item.ivThumb)
        item.tvTitle.text = data.name
        item.tvReleaseDate.text = itemView.context.getString(R.string.teks_release_date_, helper.convertDate(data.released, "yyyy-MM-dd", "dd MMM yyyy"))
        item.tvRating.text = data.rating.toString()

        item.tvTitle.rootView.setOnClickListener {
            Go(itemView.context).move(DetailGameActivity::class.java, id = data.id)
        }
    }
}