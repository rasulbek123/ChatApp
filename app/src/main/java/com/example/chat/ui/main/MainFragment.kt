package com.example.chat.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.chat.R
import com.example.chat.data.Settings
import com.example.chat.databinding.FragmentMainBinding
import org.koin.android.ext.android.inject

class MainFragment : Fragment(R.layout.fragment_main) {
    private val settings: Settings by inject()
    private lateinit var navController: NavController
    private lateinit var binding: FragmentMainBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settings.signedIn = true
        navController = Navigation.findNavController(view)
        binding = FragmentMainBinding.bind(view)
        binding.newMessege.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_searchUser)
        }
    }
}