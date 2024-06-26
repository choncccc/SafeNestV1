package com.example.safenest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.safenest.databinding.ActivitySelectLanguageBinding

class SelectLanguage : AppCompatActivity() {
    private lateinit var btnNext: Button
    private lateinit var bind : ActivitySelectLanguageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_language)
        bind = ActivitySelectLanguageBinding.inflate(layoutInflater)
        setContentView(bind.root)

        updateFragment(fragment_selectLanguage())
    }

    private fun updateFragment(fragment: Fragment) {
        val fragmentManage = supportFragmentManager
        val fragmentTransac = fragmentManage.beginTransaction()
        fragmentTransac.replace(R.id.frame_selectLang, fragment)
        fragmentTransac.commit()
    }
}
