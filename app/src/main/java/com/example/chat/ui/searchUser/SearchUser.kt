package com.example.chat.ui.searchUser

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.chat.R
import com.example.chat.data.ResourceState
import com.example.chat.data.helper.FirebaseHelper
import com.example.chat.data.model.User
import com.example.chat.databinding.FragmentSearchUserBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import org.koin.android.ext.android.inject

class SearchUser() : Fragment(R.layout.fragment_search_user) {
    private lateinit var navController: NavController
    private lateinit var binding: FragmentSearchUserBinding
    private val adapter = SearchUserAdapter()
    private val viewModel: SearchUserViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding = FragmentSearchUserBinding.bind(view)
        navController = Navigation.findNavController(view)
        binding.recyclerViewNewSearchUser.adapter = adapter
        setObservers()
        viewModel.getAllUsers()
        //val adapter = GroupAdapter<ViewHolder>()
        //adapter.add(UserItem())
    }

    private fun setObservers() {
        viewModel.statusImageUser.observe(viewLifecycleOwner) {
            when (it.status) {
                ResourceState.LOADING -> {
                    isLoading(true)
                }
                ResourceState.SUCCESS -> {
                    isLoading(false)
                    adapter.models = it.data!!

                }
                ResourceState.ERROR -> {
                    isLoading(false)
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun isLoading(Loading: Boolean) {
        binding.apply {
            SearchFragmentLoading.isVisible = Loading
            search.isEnabled = !Loading
        }
    }
}

//class UserItem: Item<ViewHolder>() {
//    override fun bind(viewHolder: ViewHolder, position: Int) {
//        TODO("Not yet implemented")
//    }
//
//    override fun getLayout(): Int {
//        return R.layout.user_row_new_message
//    }
//}