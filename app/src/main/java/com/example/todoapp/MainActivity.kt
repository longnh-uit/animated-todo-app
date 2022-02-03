package com.example.todoapp

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.drawerlayout.widget.DrawerLayout
import com.dolatkia.animatedThemeManager.AppTheme
import com.dolatkia.animatedThemeManager.ThemeActivity
import com.dolatkia.animatedThemeManager.ThemeManager
import com.google.android.material.navigation.NavigationView
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private val taskArrayList = ArrayList<TaskModel>()
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var navHeader: View
    private lateinit var menuBtn: ImageButton
    private lateinit var menuCloseBtn: ImageButton
    private lateinit var container: FrameLayout
//
//    override fun getStartTheme(): AppTheme {
//        return LightTheme()
//    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        populateRecyclerView()

        setContentView(R.layout.activity_main)

        // Views
        drawerLayout = findViewById(R.id.drawer)
        navigationView = findViewById(R.id.navigation_view)
        menuBtn = findViewById(R.id.menu_btn)
        container = findViewById(R.id.fragment_container)

        navHeader = navigationView.getHeaderView(0)
        menuCloseBtn = navHeader.findViewById(R.id.nav_close_btn)

        ViewCompat.setOnApplyWindowInsetsListener(menuBtn) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.statusBars())

            view.updateLayoutParams<ViewGroup.MarginLayoutParams>{
                leftMargin = insets.left
                topMargin = insets.top
                rightMargin = insets.right
            }

            WindowInsetsCompat.CONSUMED
        }

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO)
            container.setBackgroundResource(R.drawable.rounded_shape_light)
        else
            container.setBackgroundResource(R.drawable.rounded_shape_dark)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, MainFragment(taskArrayList)).commit()

        // Implement listeners
        navigationView.setNavigationItemSelectedListener { menuItem ->
            if (menuItem.isChecked) {
                false
            } else {
                // Handle menu item selected
                when (menuItem.itemId) {
                    R.id.nav_main -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container, MainFragment(taskArrayList)).commit()
                    R.id.nav_about -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container, AboutFragment()).commit()
                }

                menuItem.isChecked = true
                drawerLayout.close()
                true
            }
        }

        menuBtn.setOnClickListener {
            drawerLayout.open()
        }

        menuCloseBtn.setOnClickListener {
            drawerLayout.close()
        }
    }

    private fun populateRecyclerView() {
        taskArrayList.add(TaskModel(UUID.randomUUID().toString(), "Buy movie tickets for Friday", false))
        taskArrayList.add(TaskModel(UUID.randomUUID().toString(), "Make a Kotlin tutorial", false))
    }

//    override fun syncTheme(appTheme: AppTheme) {
//        val myAppTheme = appTheme as MyAppTheme
//
//        drawerLayout.rootView.setBackgroundColor(myAppTheme.firstActivityBackgroundColor(this))
//    }
}