package ua.kpi.comsys.IP8415

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import ua.kpi.comsys.IP8415.Fragments.*
import ua.kpi.comsys.IP8415.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.home -> {
                    supportFragmentManager.commit {
                        replace(R.id.fragment_container, HomeFragment())
                    }
                    supportFragmentManager.commit {
                        replace(R.id.fragment_upper_container, EmptyFragment())
                    }
                    true
                }
                R.id.graphics_fragment -> {
                    supportFragmentManager.commit {
                        replace(R.id.fragment_container, GraphicFragment())
                    }
                    supportFragmentManager.commit {
                        replace(R.id.fragment_upper_container, UpperButtonsFragment())
                    }
                    true
                }
                R.id.books_fragment -> {
                    supportFragmentManager.commit {
                        replace(R.id.fragment_container, BooksFragment())
                    }
                    supportFragmentManager.commit {
                        replace(R.id.fragment_upper_container, EmptyFragment())
                    }
                    true
                }
                R.id.pictures_fragment -> {
                    supportFragmentManager.commit {
                        replace(R.id.fragment_container, PicturesFragment())
                    }
                    supportFragmentManager.commit {
                        replace(R.id.fragment_upper_container, EmptyFragment())
                    }
                    true
                }
                else -> true
            }
        }
    }
}