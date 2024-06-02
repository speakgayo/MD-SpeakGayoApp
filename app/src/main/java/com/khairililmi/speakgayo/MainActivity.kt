package com.khairililmi.speakgayo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.khairililmi.speakgayo.databinding.ActivityMainBinding
import com.khairililmi.speakgayo.ui.favorite.FavoriteFragment
import com.khairililmi.speakgayo.ui.history.HistoryFragment
import com.khairililmi.speakgayo.ui.home.HomeFragment
import com.khairililmi.speakgayo.ui.news.NewsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToHomeFragment()

        binding.navView.background = null

        binding.navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> replaceFragment(HomeFragment())
                R.id.navigation_news -> replaceFragment(NewsFragment())
                R.id.navigation_favorite -> replaceFragment(FavoriteFragment())
                R.id.navigation_history -> replaceFragment(HistoryFragment())
            }
            true
        }
    }
    private fun navigateToHomeFragment() {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, HomeFragment())
        fragmentTransaction.commit()
    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}
