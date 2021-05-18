package ua.kpi.comsys.IP8415

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.room.Room
import com.google.android.material.bottomnavigation.BottomNavigationView
import ua.kpi.comsys.IP8415.Fragments.*
import ua.kpi.comsys.IP8415.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {
    private val model: SharedViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val db = Room.databaseBuilder(applicationContext,
            ua.kpi.comsys.IP8415.Database.Database::class.java, "books")
            .fallbackToDestructiveMigration()
            .build()
        model.setDatabase(db)

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