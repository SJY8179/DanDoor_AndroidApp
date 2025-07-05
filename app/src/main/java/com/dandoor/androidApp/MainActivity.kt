package com.dandoor.androidApp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dandoor.androidApp.databinding.ActivityMainBinding
import com.dandoor.library.data.LabRepository // library 모듈의 Repository를 import 합니다.

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbarAndDrawer()
        setupSidebarList()
    }

    private fun setupToolbarAndDrawer() {
        setSupportActionBar(binding.toolbar)
        drawerLayout = binding.drawerLayout
        toggle = ActionBarDrawerToggle(this, drawerLayout, binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    private fun setupSidebarList() {
        // 1. library 모듈에서 데이터 제공자를 생성합니다.
        val repository = LabRepository()
        // 2. 더미 데이터를 가져옵니다.
        val dummyLabs = repository.getDummyLabs()

        // 3. 사이드 바(NavigationView) 안에서 RecyclerView를 찾습니다.
        val recyclerView = binding.navView.findViewById<RecyclerView>(R.id.rv_lab_history)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 4. 어댑터를 생성하고, 클릭 시 동작을 정의합니다.
        recyclerView.adapter = LabHistoryAdapter(dummyLabs) { selectedLab ->
            // 아이템 클릭 시 ResultActivity로 이동합니다.
            val intent = Intent(this, ResultActivity::class.java).apply {
                // 선택된 Lab 객체 정보를 Intent에 담아 전달합니다.
                putExtra("EXTRA_LAB_DATA", selectedLab)
            }
            startActivity(intent)
            drawerLayout.closeDrawers()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}