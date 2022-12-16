package com.rafli.yourvideogames.ui.activity.detailGame

import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.rafli.yourvideogames.R
import com.rafli.yourvideogames.app.App
import com.rafli.yourvideogames.app.App.Companion.db
import com.rafli.yourvideogames.app.App.Companion.helper
import com.rafli.yourvideogames.databinding.ActivityDetailGameBinding
import com.rafli.yourvideogames.helper.BaseActivity
import com.rafli.yourvideogames.helper.Config.extra_id
import com.rafli.yourvideogames.helper.SweetAlert
import com.rafli.yourvideogames.helper.viewBinding
import com.rafli.yourvideogames.model.Game

class DetailGameActivity : BaseActivity() {
    private val binding by viewBinding(ActivityDetailGameBinding::inflate)
    private var idGame = 0
    private lateinit var detailGameViewModel: DetailGameViewModel
    private var currentGame:Game = Game()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setToolbar(getString(R.string.teks_detail), binding.toolbar)

        /**/
        detailGameViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailGameViewModel::class.java]

        /*get id game from intent data*/
        idGame = intent.getIntExtra(extra_id, 0)


        action()
        observeData()

        val savedGame = db?.gameDao()?.getById(idGame)
        if(savedGame != null){
            setDataDetail(savedGame)
        }else{
            /*request detail data*/
            detailGameViewModel.getDetail(idGame)
        }

    }

    /*all action in view handle in this function*/
    private fun action(){
        binding.toolbar.toolbarBack.setOnClickListener {
            onBackPressed()
        }

        binding.toolbar.ivMenu.setOnClickListener {
            if (detailGameViewModel.isSavedToLocal.value == true){
                detailGameViewModel.removeFromFavorite(currentGame)
            }else{
                detailGameViewModel.addFavorite(currentGame)
            }
        }

    }

    /*set data Game to UI*/
    private fun setDataDetail(data:Game){
        currentGame = data
        Glide.with(this).load(data.background_image).placeholder(R.drawable.ic_loading_image).error(R.drawable.ic_loading_image).into(binding.ivThumb)
        binding.tvTitle.text = data.name
        binding.tvReleaseDate.text = getString(R.string.teks_release_date_, App.helper.convertDate(data.released, "yyyy-MM-dd", "dd MMM yyyy"))
        binding.tvRating.text = data.rating.toString()
        binding.tvPlayer.text = ""
        binding.tvDescription.text = helper.fromHtml(data.description)

        detailGameViewModel.checkSaved(data.id)
    }

    private fun observeData(){
        detailGameViewModel.isLoading.observe(this) {
            if (it) {
                binding.toolbar.ivMenu.visibility = View.INVISIBLE
                binding.shimmer.visibility = View.VISIBLE
                binding.shimmer.startShimmer()
            }else{
                binding.toolbar.ivMenu.visibility = View.VISIBLE
                binding.shimmer.visibility = View.GONE
                binding.shimmer.startShimmer()
            }
        }

        detailGameViewModel.errorMsg.observe(this){
            binding.toolbar.ivMenu.visibility = View.GONE
            SweetAlert.onFailure(this, it)
        }

        detailGameViewModel.detailGame.observe(this){
            setDataDetail(it)
        }

        detailGameViewModel.isSavedToLocal.observe(this){
            if (it){
                binding.toolbar.ivMenu.setImageResource(R.drawable.ic_heart_fill)
            }else{
                binding.toolbar.ivMenu.setImageResource(R.drawable.ic_heart_outline)
            }
        }
    }
}