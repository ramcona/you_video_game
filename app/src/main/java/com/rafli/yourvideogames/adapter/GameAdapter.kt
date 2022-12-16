package com.rafli.yourvideogames.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.rafli.yourvideogames.R
import com.rafli.yourvideogames.adapter.holder.GameHolder
import com.rafli.yourvideogames.model.Game

class GameAdapter(var list:ArrayList<Game> = ArrayList()): RecyclerView.Adapter<GameHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHolder {
        return  GameHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_game, parent, false))
    }

    override fun onBindViewHolder(holder: GameHolder, position: Int) {
        holder.setData(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(newListData:List<Game>){
        list.addAll(newListData)
        notifyDataSetChanged()
    }

    fun clearData(){
        list.clear()
        notifyDataSetChanged()
    }

}