package com.example.safenest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import com.example.safenest.databinding.FragmentPersonalizedNotifBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior


class PersonalizedNotif : Fragment() {
    private lateinit var binding: FragmentPersonalizedNotifBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var header: TextView
    private lateinit var maincontainer: FrameLayout
    private lateinit var button: ImageButton
    private lateinit var btnSaveNotification: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentPersonalizedNotifBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomSheet = view.findViewById<View>(R.id.bottomSheet)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.peekHeight = 85
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        header = view.findViewById(R.id.header)
        maincontainer = view.findViewById(R.id.maincontainer)
        button = view.findViewById(R.id.addNotification)
        btnSaveNotification = view.findViewById(R.id.btnSaveNofication)

        button.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        btnSaveNotification.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

            //TODO ADD CODE HERE TO SAVE THE INPUT TO THE DATABASE
        }

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
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
        applyAlphaAnimation(header, alpha)
        applyAlphaAnimation(maincontainer, alpha)
    }

    private fun applyAlphaAnimation(view: View, alpha: Float) {
        val animation = AlphaAnimation(view.alpha, alpha)
        animation.duration = 200 // Set the duration of the fade animation
        animation.interpolator = DecelerateInterpolator()
        animation.fillAfter = true
        view.startAnimation(animation)
        view.alpha = alpha
    }
}