package com.example.moviesdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.moviesdb.presentation.PopularMoviesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        if (savedInstanceState == null) {
//            Log.d("proverka", "activity launch fragment")
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment_container, PopularMoviesFragment())
//                .commit()
//        }
    }
}