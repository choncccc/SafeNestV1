package com.example.safenest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.safenest.databinding.FragmentSecondTrimesterBinding


class SecondTrimester : Fragment() {
    private lateinit var binding: FragmentSecondTrimesterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding= FragmentSecondTrimesterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.weekThirteenDescrip.setOnClickListener{
           updateFragment(WeekThirteenDescrip())
        }
        binding.weekFourteenContents.setOnClickListener{
            updateFragment(WeekFourteenDescrip())
        }
        binding.weekFifteenContents.setOnClickListener{
            updateFragment(WeekFifteenDescrip())
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