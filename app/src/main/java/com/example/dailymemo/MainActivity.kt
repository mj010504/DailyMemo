package com.example.dailymemo

import InsetsWithKeyboardCallback
import android.app.ProgressDialog
import android.graphics.Rect
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.dailymemo.Setting.Dialog.RemoveAdDialog
import com.example.dailymemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var navController : NavController

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            showFinishAppDialog()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

      this.onBackPressedDispatcher.addCallback(this, callback)

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

    private fun showFinishAppDialog() {
        val dialog = FinishAppDailog(this) {
            finishApp()
        }
        dialog.show()
    }

    private fun finishApp() {
        moveTaskToBack(true); // 태스크를 백그라운드로 이동
        finishAndRemoveTask(); // 액티비티 종료 + 태스크 리스트에서 지우기
        System.exit(0);
    }
}