package com.example.weather_app_iti.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.weather_app_iti.R
import com.example.weather_app_iti.view.setting.Setting
import com.example.weather_app_iti.view.setting.SettingFragment
import com.example.weather_app_iti.databinding.ActivityMainBinding
import com.example.weather_app_iti.view.alert.AlertFragment
import com.example.weather_app_iti.view.favourite.FavouriteFragment
import com.example.weather_app_iti.view.home.HomeFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener{
    lateinit var binding:ActivityMainBinding
    lateinit var sharedPreferences: SharedPreferences
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences=getSharedPreferences("setup_setting",Context.MODE_PRIVATE)
        val lang=sharedPreferences.getString(Setting.languageKey,getString(R.string.en))
        if(lang!=null){
            Setting.setLocale(this, lang)
        }
        else{
            Setting.setLocale(this, getString(R.string.en))
        }
        setSupportActionBar(binding.toolBar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.baseline_menu_24)
            setDisplayShowHomeEnabled(true)
        }
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, HomeFragment()).commit()
        supportActionBar?.title = getString(R.string.Home)
        binding.navView.setNavigationItemSelectedListener(this)
    }
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(Setting.onAttach(newBase))
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.homeFragment -> {
             supportFragmentManager.beginTransaction().replace(
                 R.id.nav_host_fragment,
                 HomeFragment()
             ).commit()
                supportActionBar?.title = getString(R.string.Home)
            }
            R.id.favouriteFragment -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.nav_host_fragment,
                    FavouriteFragment()
                ).commit()
                supportActionBar?.title = getString(R.string.Favourite)
            }
            R.id.alertFragment -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.nav_host_fragment,
                    AlertFragment()
                ).commit()
                supportActionBar?.title =getString(R.string.Alert)
            }
            R.id.settingFragment -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.nav_host_fragment,
                    SettingFragment()
                ).commit()
                supportActionBar?.title = getString(R.string.Setting)
            }
        }
        binding.drawer.closeDrawer(GravityCompat.START)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId== R.id.reload) recreate()

        if (item.itemId == android.R.id.home) {
            if (binding.drawer.isDrawerOpen(GravityCompat.START)) {
                binding.drawer.closeDrawer(GravityCompat.START)
            } else {
                binding.drawer.openDrawer(GravityCompat.START)
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu,menu)
        return true
    }

}





