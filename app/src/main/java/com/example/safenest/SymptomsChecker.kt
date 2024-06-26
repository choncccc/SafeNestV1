package com.example.safenest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.safenest.databinding.FragmentFirstTrimesterDescripBinding
import com.example.safenest.databinding.FragmentSymptomsCheckerBinding

class SymptomsChecker : Fragment() {
    private lateinit var binding: FragmentSymptomsCheckerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSymptomsCheckerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.FirstTrimesterCardSymp.setOnClickListener {
            updateFragment(FirstTrimesterDescrip())
        }
        binding.SecondTrimesterCardSymp.setOnClickListener {
            updateFragment(SecondTrimester())
        }
        binding.ThirdTrimesterCardSymp.setOnClickListener {
            updateFragment(ThirdTrimester())
        }
        binding.perSpecificSymp.setOnClickListener{
            updateFragment(SpecificSymptomsCheck())
        }
    }

    private fun updateFragment(fragment: Fragment) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}