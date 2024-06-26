package com.example.safenest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.safenest.databinding.FragmentReadingBinding


class ReadingFragment : Fragment() {

    private lateinit var binding: FragmentReadingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReadingBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.FirstTrimesterCard.setOnClickListener {
            updateFragment(FirstTrimesterDescrip())
        }

        binding.SecondTrimesterCard.setOnClickListener{
            updateFragment(SecondTrimester())
        }
        binding.ThirdTrimesterCard.setOnClickListener {
            updateFragment(ThirdTrimester())
        }
    }

    private fun updateFragment(fragment: Fragment) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.addToBackStack(null) // Add to back stack so the previous fragment can be restored
        fragmentTransaction.commit()
    }
}
