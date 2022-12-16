package com.rafli.yourvideogames.ui.fragment.home

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
import com.rafli.yourvideogames.databinding.FragmentHomeBinding
import com.rafli.yourvideogames.helper.SweetAlert

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var gamesListViewModel: GamesListViewModel
    private lateinit var gameAdapter: GameAdapter
    private lateinit var gameListLayoutManager: LinearLayoutManager

    private var searchQuery = ""

    //pagination variable
    private var page = 1
    private val viewThreshold = 18
    private var pastVisibleitem: Int = 0
    private var visibleItemCount:Int = 0
    private var totalItemCount:Int = 0
    private var previousTotal = 0
    private var isLoading = true
    private var isSearch = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
//        binding.svGame.isIconified = false

        gamesListViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[GamesListViewModel::class.java]

        gameAdapter = GameAdapter()



        //setup UI recyclerview
        gameListLayoutManager = LinearLayoutManager(requireContext())
        binding.rvGame.layoutManager = gameListLayoutManager
        binding.rvGame.adapter = gameAdapter

        observeData()

        resetPagiantionValue()
        setupPageination()
        gamesListViewModel.getListGames(page)

        action()

        return binding.root
    }




    private fun action(){
        binding.svGame.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                gameAdapter.clearData()
                resetPagiantionValue()
                gamesListViewModel.searchGames(query, page)
                searchQuery = query
                isSearch = true

                return false
            }
        })


        val closeBtn: View = binding.svGame.findViewById(androidx.appcompat.R.id.search_close_btn)
        closeBtn.setOnClickListener {
            resetPagiantionValue()
            binding.svGame.setQuery("", false) // reset Query text to be empty without submition
            binding.svGame.isIconified = true

            gamesListViewModel.getListGames(page)
        }

    }

    private fun resetPagiantionValue() {
        pastVisibleitem = 0
        visibleItemCount = 0
        totalItemCount = 0
        previousTotal = 0
        page = 1
        isSearch = false
    }

    private fun setupPageination() {
        binding.rvGame.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                visibleItemCount = gameListLayoutManager.childCount
                totalItemCount = gameListLayoutManager.itemCount
                pastVisibleitem = gameListLayoutManager.findFirstVisibleItemPosition()

                if (dy > 0) {
                    if (isLoading) {
                        if (totalItemCount > previousTotal) {
                            isLoading = false
                            previousTotal = totalItemCount
                        }
                    }
                    if (!isLoading && totalItemCount - visibleItemCount <= pastVisibleitem + viewThreshold) {
                        page += 1
                        if(isSearch){
                            gamesListViewModel.searchGames(searchQuery, page)
                        }else{
                            gamesListViewModel.getListGames(page)
                        }

                        isLoading = true
                    }
                }

            }
        })
    }


    private fun observeData(){
        gamesListViewModel.isLoading.observe(requireActivity()) {
            if (page > 1){
                if (it) {
                    binding.progressBar.visibility = View.VISIBLE
                }else{
                    binding.progressBar.visibility = View.GONE
                }
            }else{
                if (it) {
                    binding.shimmer.visibility = View.VISIBLE
                    binding.shimmer.startShimmer()
                }else{
                    binding.shimmer.visibility = View.GONE
                    binding.shimmer.startShimmer()
                }
            }

        }

        gamesListViewModel.errorMsg.observe(requireActivity()){
            if (page == 1){
                SweetAlert.onFailure(requireActivity(), it)
            }

        }

        gamesListViewModel.gameList.observe(requireActivity()){
            gameAdapter.setData(it)
        }
    }
}