package com.example.moviesdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.moviesdb.presentation.main.MainFragment
import com.example.moviesdb.presentation.discover.DiscoverFragment
import com.example.moviesdb.presentation.popular_searchmovies.PopularMoviesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainFragment = MainFragment()
        val discoverFragment = DiscoverFragment()
        val popularMoviesFragment = PopularMoviesFragment()

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)

        bottomNavigationView?.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.main_fragment -> {
                    setCurrentFragment(mainFragment)
                    true
                }

                R.id.discover_fragment -> {
                    setCurrentFragment(discoverFragment)
                    true
                }

                R.id.popular_fragment -> {
                    setCurrentFragment(popularMoviesFragment)
                    true
                }

                else -> false
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()
        }
    }
}