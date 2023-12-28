package com.example.dailymemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.dailymemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //네비게이션 컨트롤러
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        NavigationUI.setupWithNavController(binding.bottmNav, navController)
        binding.bottmNav.itemIconTintList = null
        setBottomNavigationVisiblity()


    }

    private fun setBottomNavigationVisiblity() {
        val hideBottomNavigationFragments = mutableListOf<Int>()
        val typedArray = resources.obtainTypedArray(R.array.hide_bottomNavigation_fragments)
        for (index in 0..typedArray.length()) {
            hideBottomNavigationFragments.add(typedArray.getResourceId(index, 0))
        }

        typedArray.recycle()
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottmNav.isVisible =
                (destination.id !in hideBottomNavigationFragments)
        }
    }

}