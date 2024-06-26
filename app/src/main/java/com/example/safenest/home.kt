package com.example.safenest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.safenest.databinding.ActivityHomeBinding
import fragment_home


class home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val username = intent.getStringExtra("username") ?: ""
        val password = intent.getStringExtra("password") ?: ""
        val fragment = fragment_home.newInstance(username)
        updateFragment(fragment)

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> updateFragment(fragment_home.newInstance(username))
                R.id.book -> updateFragment(ReadingFragment())
                R.id.search -> updateFragment(FragmentSelfAssessment())
                R.id.heart -> updateFragment(GuidedSelfCareEffiency())
                R.id.menu -> updateFragment(Overview())
                else -> {
                }
            }
            true
        }
    }

    private fun updateFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}
