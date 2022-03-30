package com.google.newspaper.views.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.google.newspaper.R
import com.google.newspaper.databinding.ActivityMainBinding
import com.google.newspaper.views.fragments.FragmentMainDirections
import com.google.newspaper.views.fragments.NewsPaperPageFragmentDirections
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var local: NavDestination
    private val navController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        menuAction()
        checkDestination()
    }

    private fun menuAction(){
        binding.bottomAppBar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.bottomAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_favs -> {
                    goToFav()
                    true
                }
                R.id.menu_mock -> {
                    Toast.makeText(this@MainActivity, "click", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }

    private fun checkDestination() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.fragmentMain -> {
                    local = destination
                }
                R.id.fragmentNewsPaperPage -> {
                    local = destination
                }
            }
        }
    }

    private fun goToFav() {
        when (local.id) {
            R.id.fragmentMain -> {
                goToMainFav()
            }
            R.id.fragmentNewsPaperPage -> {
                goToPageFav()
            }
        }
    }

    private fun goToMainFav(){
        val directions = FragmentMainDirections.actionFragmentMainToFavoritosFragment()
        navController.navigate(directions)
    }

    private fun goToPageFav(){
        val directions = NewsPaperPageFragmentDirections.actionFragmentNewsPaperPageToFavoritosFragment()
        navController.navigate(directions)
    }
}