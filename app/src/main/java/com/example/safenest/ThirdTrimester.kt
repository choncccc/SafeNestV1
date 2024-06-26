package com.example.safenest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.safenest.databinding.FragmentThirdTrimesterBinding


class ThirdTrimester : Fragment() {

    private lateinit var binding: FragmentThirdTrimesterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding= FragmentThirdTrimesterBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.weekTwentyEightDescrip.setOnClickListener {
            updateFragment(WeekTwentyEightDescrip())
        }
        binding.weekTwentyNineDescrip.setOnClickListener {
            updateFragment(WeekTwentyNineDescrip())
        }
        binding.weekThirtyDescrip.setOnClickListener {
            updateFragment(WeekThirtyDescrip())
        }
        binding.weekThirtyOneDescrip.setOnClickListener {
            updateFragment(WeekThirtyOneDescrip())
        }
        binding.weekThirtyTwoDescrip.setOnClickListener {
            updateFragment(WeekThirtyTwoDescrip())
        }
        binding.weekThirtyThreeDescrip.setOnClickListener {
            updateFragment(WeekThirtyThreeDescrip())
        }
        binding.weekThirtyFourDescrip.setOnClickListener {
            updateFragment(WeekThirtyFourDescrip())
        }
        binding.weekThirtyFiveDescrip.setOnClickListener {
            updateFragment(WeekThirtyFiveDescrip())
        }
        binding.weekThirtySixDescrip.setOnClickListener {
            updateFragment(WeekThirtySixDescrip())
        }
        binding.weekThirtySevenDescrip.setOnClickListener {
            updateFragment(WeekThirtySevenDescrip())
        }
        binding.weekThirtyEightDescrip.setOnClickListener {
            updateFragment(WeekThirtyEightDescrip())
        }
        binding.weekThirtyNineDescrip.setOnClickListener {
            updateFragment(WeekThirtyNineDesrip())
        }
        binding.weekFortyDescrip.setOnClickListener {
            updateFragment(WeekFortyDescrip())
        }
        binding.weekFortyOneDescrip.setOnClickListener {
            updateFragment(WeekFortyOneDescrip())
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