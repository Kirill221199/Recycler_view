package ru.kirill.recycler_view.activity

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import ru.kirill.recycler_view.R
import ru.kirill.recycler_view.databinding.ActivityMainBinding
import ru.kirill.recycler_view.databinding.FragmentRecyclerViewWithDiffUtilBinding
import ru.kirill.recycler_view.recyclerviewWithDiffutils.RecyclerViewWithDiffUtilFragment
import ru.kirill.recycler_view.simplerecyclerview.RecyclerViewFragment

class MainActivity : AppCompatActivity() {

    private val KEY_SP = "sp"
    private val KEY_ITEM = "current_item"

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigation()
    }

    private fun bottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_recyclerview -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container_to_fragment, RecyclerViewFragment.newInstance()).addToBackStack("")
                        .commit()
                    setCurrentItem(R.id.action_recyclerview)
                }
                R.id.action_recyclerview_with_diffutils -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container_to_fragment, RecyclerViewWithDiffUtilFragment.newInstance()).addToBackStack("")
                        .commit()
                    setCurrentItem(R.id.action_recyclerview_with_diffutils)
                }
            }
            true
        }
        binding.bottomNavigationView.selectedItemId = getCurrentItem()
    }

    private fun setCurrentItem(currentItem: Int) {
        val sharedPreferences = getSharedPreferences(KEY_SP, Activity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_ITEM, currentItem)
        editor.apply()
    }

    private fun getCurrentItem(): Int {
        val sharedPreferences = getSharedPreferences(KEY_SP, Activity.MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_ITEM, R.id.action_recyclerview)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}