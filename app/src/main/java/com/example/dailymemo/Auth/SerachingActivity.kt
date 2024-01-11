package com.example.dailymemo.Auth

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.dailymemo.databinding.ActivitySearchingBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class SerachingActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var binding: ActivitySearchingBinding

    lateinit var searchingIdFragment: SearchingIdFragment
    lateinit var searchingPasswordFragment: SearchingPasswordFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = binding.searchingTabTb
        setSupportActionBar(toolbar)

        if(supportActionBar != null){
            var actionBar : ActionBar = supportActionBar as ActionBar
            actionBar.setDisplayShowTitleEnabled(false)
        }

        searchingIdFragment = SearchingIdFragment()
        searchingPasswordFragment = SearchingPasswordFragment()

        var tabs : TabLayout = binding.searchingTabsTl
        tabs.addTab(tabs.newTab().setText("아이디 찾기"))
        tabs.addTab(tabs.newTab().setText("비밀번호 찾기"))

        tabs.addOnTabSelectedListener(object : OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                lateinit var fragment : Fragment
                when(tab!!.position){
                    0 -> fragment = searchingIdFragment
                    1 -> fragment = searchingPasswordFragment
                }
                supportFragmentManager.beginTransaction().replace(binding.searchingContainerFl.id, fragment).commit()
            }
        })
    }
}