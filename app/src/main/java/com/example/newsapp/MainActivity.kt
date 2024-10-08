package com.example.newsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.newsapp.database.ArticleDatabase
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.ui.theme.NewsViewModel
import com.example.newsapp.ui.theme.NewsViewModelProviderFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
        lateinit var newsViewModel : NewsViewModel
        lateinit var binding : ActivityMainBinding
        private lateinit var auth : FirebaseAuth
        lateinit var drawerLayout: DrawerLayout


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            auth = FirebaseAuth.getInstance() // Initialize FirebaseAuth


            val newsRepository = NewsRepository(ArticleDatabase(this))
            val viewModelProviderFactory = NewsViewModelProviderFactory(application, newsRepository)
            newsViewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

            val navHostFragment = supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
            val navController = navHostFragment.navController


            if (auth.currentUser != null) {
                // User is logged in, navigate to HeadlineFragment
                navigateToHeadline(navController)
            } else {
                // User is not logged in, show LoginFragment
                navigateToLogin(navController)
            }

            // Set up the bottom navigation view with NavController
            binding.bottomNavigationView.setupWithNavController(navController)

            // Hide BottomNavigationView in login or signup fragment
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.loginFragment, R.id.signUpFragment -> {
                        binding.bottomNavigationView.visibility = android.view.View.GONE
                    }
                    else -> {
                        binding.bottomNavigationView.visibility = android.view.View.VISIBLE
                    }
                }
            }
            drawerLayout = binding.drawerLayout
        }


        // Function to navigate to LoginFragment
        private fun navigateToLogin(navController: androidx.navigation.NavController) {
            navController.navigate(
                R.id.loginFragment,
                null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.newsNavHostFragment, true)
                    .build()
            )
        }

        // Function to navigate to HeadlineFragment (home page) after login
        private fun navigateToHeadline(navController: androidx.navigation.NavController) {
            navController.navigate(
                R.id.headlineFragment,
                null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.newsNavHostFragment, true) // Clears backstack to avoid going back to login
                    .build()
            )
        }

}
