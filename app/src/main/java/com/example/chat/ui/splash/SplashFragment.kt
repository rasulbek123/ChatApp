package com.example.chat.ui.splash

import android.animation.Animator
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.chat.R
import com.example.chat.data.Settings
import com.example.chat.databinding.FragmentSplashBinding

class SplashFragment:Fragment(R.layout.fragment_splash) {
    private lateinit var binding: FragmentSplashBinding
    private lateinit var navController:NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().actionBar?.hide()
        binding = FragmentSplashBinding.bind(view)
        navController = Navigation.findNavController(view)
        binding.lottieView.setMaxProgress(0.6f)
        val settings = Settings(requireContext())
        binding.lottieView.addAnimatorListener(object: Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                if(settings.signedIn){
                    val action = SplashFragmentDirections.actionSplashFragmentToMainFragment()
                    navController.navigate(action)
                }else{
                    val action = SplashFragmentDirections.actionSplashFragmentToSignInFragment()
                    navController.navigate(action)
                }
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }
        })
    }
}