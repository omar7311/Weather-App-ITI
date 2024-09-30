package com.example.weather_app_iti

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weather_app_iti.model.local.Alert
import com.example.weather_app_iti.model.local.FakeLocalDataSource
import com.example.weather_app_iti.model.local.FavouriteCity
import com.example.weather_app_iti.model.local.ILocalDataSource
import com.example.weather_app_iti.model.remote.FakeRemoteDataSource
import com.example.weather_app_iti.model.remote.IRemoteDataSource
import com.example.weather_app_iti.model.repo.Repository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepositoryTest {
    val fav= FavouriteCity("369", 12.2589, 50.2587, "cairo")
    val alert= Alert("123", "2024:2:1", "12:50:10")
    private  val list1= mutableListOf(fav)
    private  val list2= mutableListOf(alert)
    private  lateinit var local: ILocalDataSource
    private  lateinit var remote: IRemoteDataSource
    private  lateinit var repo: Repository

    @Before
    fun setup(){
        //given
        local= FakeLocalDataSource(list1,list2)
        remote= FakeRemoteDataSource()
        repo= Repository(remote,local)
    }
    @Test
    fun getFavourites()= runTest {
        repo.getFavourites().collectLatest {
            MatcherAssert.assertThat(it,IsEqual(list1))
        }
    }

    @Test
    fun getAlerts()= runTest {
        repo.getAlerts().collectLatest {
            MatcherAssert.assertThat(it,IsEqual(list2))
        }
    }

    @Test
    fun insertFavourite()= runTest {
        val fav= FavouriteCity("369369", 12.2589, 50.2587, "alex")
        repo.insertFavourite(fav)
        MatcherAssert.assertThat(list1.last(),IsEqual(fav))

    }

    @Test
    fun insertAlert()= runTest {
        val alert= Alert("123", "2024:2:1", "12:50:10")
        repo.insertAlert(alert)
        MatcherAssert.assertThat(list2.last(),IsEqual(alert))
    }
}