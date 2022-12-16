package com.rafli.yourvideogames.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.rafli.yourvideogames.model.Game
import com.rafli.yourvideogames.network.response.GameListResponse
import com.rafli.yourvideogames.repositories.GamesRepo
import com.rafli.yourvideogames.ui.fragment.home.GamesListViewModel
import com.rafli.yourvideogames.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.*
import retrofit2.Response
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GameListViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var apiRepo: GamesRepo


    @Mock
    private lateinit var apiGameListObserver: Observer<List<Game>>


    @Before
    fun setUp() {
        // do something if required
    }

    @Test
    fun givenServerResponse200_whenFetchGameList_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {

            val viewModel = GamesListViewModel()
            viewModel.gameList.observeForever(apiGameListObserver)

            Mockito.`when`(apiRepo.getListGames(1, 20)).thenReturn(Response.success(GameListResponse()))

            viewModel.getListGames(1)

            viewModel.gameList.removeObserver(apiGameListObserver)
        }
    }


    @After
    fun tearDown() {
        // do something if required
    }

}