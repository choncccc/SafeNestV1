package com.example.safenest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.safenest.databinding.FragmentFirstTrimesterDescripBinding


class FirstTrimesterDescrip : Fragment() {

    private lateinit var binding: FragmentFirstTrimesterDescripBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstTrimesterDescripBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.weekTwoContents.setOnClickListener {
            updateFragment(weekTwoDescrip())
        }
        binding.weekThreeContents.setOnClickListener {
            updateFragment(WeekThreeDescrip())
        }
        binding.weekFourContents.setOnClickListener {
            updateFragment(WeekFourDescrip())
        }
        binding.weekFiveContents.setOnClickListener {
            updateFragment(WeekFiveDescrip())
        }
        binding.weekSixContents.setOnClickListener {
            updateFragment(WeekSixDescip())
        }
        binding.weekSevenContents.setOnClickListener{
            updateFragment(WeekSevenDescrip())
        }
        binding.weekEightContents.setOnClickListener {
            updateFragment(WeekEightDescrip())
        }
        binding.weekNineContents.setOnClickListener {
            updateFragment(WeekNineDescrip())
        }
        binding.weekTenContents.setOnClickListener{
            updateFragment(WeekTenDescrip())
        }
        binding.weekElevenContents.setOnClickListener{
            updateFragment(WeekElevenDescrip())
        }
        binding.weekTwelveContents.setOnClickListener{
            updateFragment(WeekTwelveDescrip())
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
