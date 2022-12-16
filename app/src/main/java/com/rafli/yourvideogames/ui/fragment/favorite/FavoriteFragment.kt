package com.rafli.yourvideogames.ui.fragment.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rafli.yourvideogames.adapter.GameAdapter
import com.rafli.yourvideogames.databinding.FragmentFavoriteBinding
import com.rafli.yourvideogames.databinding.FragmentHomeBinding
import com.rafli.yourvideogames.helper.SweetAlert

class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var gameAdapter: GameAdapter
    private lateinit var gameListLayoutManager: LinearLayoutManager



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
//        binding.svGame.isIconified = false

        favoriteViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FavoriteViewModel::class.java]

        gameAdapter = GameAdapter()



        //setup UI recyclerview
        gameListLayoutManager = LinearLayoutManager(requireContext())
        binding.rvGame.layoutManager = gameListLayoutManager
        binding.rvGame.adapter = gameAdapter

        observeData()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        favoriteViewModel.getFavorite()
    }


    private fun observeData(){
        favoriteViewModel.isLoading.observe(requireActivity()) {
            if (it) {
                binding.shimmer.visibility = View.VISIBLE
                binding.shimmer.startShimmer()
                binding.relEmpty.visibility = View.GONE
            }else{
                binding.shimmer.visibility = View.GONE
                binding.shimmer.startShimmer()
            }
        }

        favoriteViewModel.errorMsg.observe(requireActivity()){
//            SweetAlert.onFailure(requireActivity(), it)
            binding.relEmpty.visibility = View.VISIBLE
        }

        favoriteViewModel.gameList.observe(requireActivity()){
            gameAdapter.clearData()
            gameAdapter.setData(it)

            if (it.isEmpty()){
                binding.relEmpty.visibility = View.VISIBLE
            }
        }
    }
}