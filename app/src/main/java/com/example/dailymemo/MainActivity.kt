package com.example.dailymemo

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.example.dailymemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var navController : NavController

    private val PERMISSION_CODE = 123
    private val permissions = arrayOf(
        android.Manifest.permission.READ_MEDIA_IMAGES
    )

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

        checkPermission()


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

    // 1. Camera 권한 확인
    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED )
        {
            requestPermission()
        }
    }
    //READ_EXTERNAL_STORAGE는 런타임권한이므로 사용자에게 직접 권한 요청해야한다.
    private fun requestPermission() {
        // 권한 요청 다이얼로그 표시
           if(ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.READ_MEDIA_IMAGES)) {
           ActivityCompat.requestPermissions(
               this,
              permissions,
               PERMISSION_CODE
           )
       }
        else {
           ActivityCompat.requestPermissions(
               this,
               permissions,
                PERMISSION_CODE
           )
       }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                // 권한 요청 결과 처리
                if (grantResults.isNotEmpty()) {
                    if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                        Log.d("cursor", grantResults[0].toString())
                        Toast.makeText(this, "권한이 모두 허용되었습니다.", Toast.LENGTH_SHORT).show()
                    } else {

                        Toast.makeText(
                            this,
                            "모든 권한을 허용해야 기능을 사용할 수 있습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
                else -> {
                        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
                    }

        }
    }

}