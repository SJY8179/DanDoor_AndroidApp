package com.dandoor.androidApp

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dandoor.androidApp.database.AppDatabase
import com.dandoor.androidApp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    // ViewBinding 객체. activity_main.xml의 뷰들을 직접 참조할 수 있게 해줍니다.
    private lateinit var binding: ActivityMainBinding
    // 사이드 바(드로어) 레이아웃 객체
    private lateinit var drawerLayout: DrawerLayout
    // 툴바의 햄버거 아이콘과 드로어의 열고 닫힘을 연결해주는 토글 버튼
    private lateinit var toggle: ActionBarDrawerToggle
    // 사이드 바의 RecyclerView를 위한 어댑터
    private lateinit var labHistoryAdapter: LabHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ViewBinding을 사용하여 레이아웃을 로드하고 화면에 표시합니다.
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 각 UI 요소를 설정하는 함수들을 순서대로 호출합니다.
        setupToolbarAndDrawer()
        setupRecyclerView()
        setupBottomNav()
        loadLabHistory()
    }

    // 툴바와 사이드 바(드로어)를 설정하는 함수
    private fun setupToolbarAndDrawer() {
        // XML의 Toolbar를 이 액티비티의 공식 액션바로 지정합니다.
        setSupportActionBar(binding.toolbar)
        drawerLayout = binding.drawerLayout
        // ActionBarDrawerToggle을 생성하여 툴바와 드로어를 연결합니다.
        toggle = ActionBarDrawerToggle(this, drawerLayout, binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        // 드로어에 토글 리스너를 추가합니다.
        drawerLayout.addDrawerListener(toggle)
        // 토글의 현재 상태(아이콘 모양 등)를 동기화합니다.
        toggle.syncState()
        // 툴바에 '홈' 버튼(햄버거 아이콘)을 표시하고 활성화합니다.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    // 사이드 바의 RecyclerView를 설정하는 함수
    private fun setupRecyclerView() {
        // LabHistoryAdapter를 생성합니다. 아이템 클릭 시 동작을 람다 함수로 정의합니다.
        labHistoryAdapter = LabHistoryAdapter { lab ->
            // NavController를 찾아서 화면 이동을 처리합니다.
            val navController = findNavController(R.id.fragmentContainer)
            // Bundle 객체를 만들어 전달할 데이터를 담습니다.
            val bundle = Bundle().apply {
                putLong("SELECTED_LAB_ID", lab.labID)
            }
            // nav_graph에 정의된 action을 실행하여 화면을 이동시킵니다. (데이터 포함)
            navController.navigate(R.id.action_home_to_result, bundle)
            // 화면 이동 후 사이드 바를 닫습니다.
            drawerLayout.closeDrawers()
        }
        // NavigationView에서 RecyclerView를 찾아와 어댑터와 레이아웃 매니저를 설정합니다.
        val recyclerView = binding.navView.findViewById<RecyclerView>(R.id.rv_lab_history)
        recyclerView.adapter = labHistoryAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    // 하단 네비게이션 바를 설정하는 함수
    private fun setupBottomNav() {
        // NavHostFragment에서 NavController를 가져옵니다.
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController
        // BottomNavigationView와 NavController를 연결하여, 메뉴 클릭 시 자동으로 화면이 전환되게 합니다.
        binding.bottomNavigation.setupWithNavController(navController)
    }

    // 데이터베이스에서 실험 기록을 로드하는 함수
    private fun loadLabHistory() {
        // 데이터베이스 인스턴스와 DAO(Data Access Object)를 가져옵니다.
        val labDao = AppDatabase.getDB(applicationContext).labDao()
        // lifecycleScope를 사용하여 UI(액티비티)의 생명주기에 안전한 코루틴을 실행합니다.
        lifecycleScope.launch {
            // 백그라운드 스레드에서 DB 작업을 수행하여 UI 멈춤을 방지합니다.
            val labs = labDao.getAllLabs()
            // DB에서 가져온 데이터를 어댑터에 전달하여 화면에 표시합니다.
            labHistoryAdapter.submitList(labs)
        }
    }

    // 툴바의 메뉴 아이템(햄버거 아이콘 등)이 클릭되었을 때 호출됩니다.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 클릭 이벤트를 ActionBarDrawerToggle에게 먼저 전달하여 처리하도록 합니다.
        // (햄버거 아이콘 클릭 시 드로어를 열고 닫는 동작)
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
