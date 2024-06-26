import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.safenest.BloodPresssureData
import com.example.safenest.FoodSafetyContens
import com.example.safenest.NonStarchyContents
import com.example.safenest.ProteinContents
import com.example.safenest.R
import com.example.safenest.WholeGrainsContent
import com.example.safenest.databinding.FragmentNutritionGuidanceContentBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior



class NutritionGuidanceContent : Fragment() {

    private lateinit var binding: FragmentNutritionGuidanceContentBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var card1: CardView
    private lateinit var card2: CardView
    private lateinit var card3: CardView
    private lateinit var card4: CardView
    private lateinit var card5: CardView
    private lateinit var head: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentNutritionGuidanceContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomSheet = view.findViewById<View>(R.id.bottomSheet)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.peekHeight = 85
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        card1 = view.findViewById(R.id.bs1)
        card2 = view.findViewById(R.id.bs2)
        card3 = view.findViewById(R.id.bs3)
        card4 = view.findViewById(R.id.bs4)
        card5 = view.findViewById(R.id.bs5)
        head= view.findViewById(R.id.header)

        binding.NonStarchySection.setOnClickListener{
            updateFragment(NonStarchyContents())
        }
        binding.ProteinSection.setOnClickListener{
            updateFragment(ProteinContents())
        }
        binding.WholeGrainsSection.setOnClickListener{
            updateFragment(WholeGrainsContent())
        }
        binding.foodSafetyContents.setOnClickListener{
            updateFragment(FoodSafetyContens())
        }

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // TODO
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                updateCardViewsAlpha(slideOffset)
            }
        })
    }

    private fun updateCardViewsAlpha(slideOffset: Float) {
        val alpha = slideOffset
        applyAlphaAnimation(card1, alpha)
        applyAlphaAnimation(card2, alpha)
        applyAlphaAnimation(card3, alpha)
        applyAlphaAnimation(card4, alpha)
        applyAlphaAnimation(card5, alpha)
        applyAlphaAnimation(head, alpha)

    }

    private fun applyAlphaAnimation(view: View, alpha: Float) {
        val animation = AlphaAnimation(view.alpha, alpha)
        animation.duration = 200 // Set the duration of the fade animation
        animation.interpolator = DecelerateInterpolator()
        animation.fillAfter = true
        view.startAnimation(animation)
        view.alpha = alpha
    }
    private fun updateFragment(fragment: Fragment) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}
