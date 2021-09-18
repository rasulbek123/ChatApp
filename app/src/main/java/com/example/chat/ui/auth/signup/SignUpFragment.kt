package com.example.chat.ui.auth.signup

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.chat.R
import com.example.chat.data.ResourceState
import com.example.chat.databinding.FragmentSignUpBinding
import org.koin.android.ext.android.inject
import java.io.ByteArrayOutputStream

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;

        //Permission code
        private val PERMISSION_CODE = 1001;
    }

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by inject()
    private lateinit var navController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding = FragmentSignUpBinding.bind(view)

        binding.apply {
            userPhoto.setOnClickListener {
                pickImageFromGallery()
            }
            btnRegister.setOnClickListener {
                var success = true
                if (username.text.isNullOrEmpty()) {
                    username.error = getString(R.string.fill_the_field)
                    success = false
                }
                if (userEmail.text.isNullOrEmpty()) {
                    userEmail.error = getString(R.string.fill_the_field)
                    success = false
                }
                if (userPassword.text.isNullOrEmpty()) {
                    userPassword.error = getString(R.string.fill_the_field)
                    success = false
                }
                if (!success) return@setOnClickListener
                else {
                    val bitmap = (binding.userPhoto.drawable as BitmapDrawable).bitmap
                    binding.userPhoto.isDrawingCacheEnabled = true
                    binding.userPhoto.buildDrawingCache()
                    val baos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos)
                    val data = baos.toByteArray()
                    viewModel.signUp(
                        userEmail.text.toString(),
                        userPassword.text.toString(),
                        username.text.toString(),
                        data
                    )
                }
            }
        }
        setObservers()
    }

    private fun setObservers() {
        viewModel.signUpStatus.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.LOADING -> {
                    isLoading(true)
                }
                ResourceState.SUCCESS -> {
                    navController.navigate(R.id.action_signUpFragment_to_mainFragment)
                }
                ResourceState.ERROR -> {
                    isLoading(false)
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun isLoading(Loading: Boolean) {
        binding.apply {
            loading.isVisible = Loading
            userEmail.isEnabled = !Loading
            userPassword.isEnabled = !Loading
            username.isEnabled = !Loading
            userPhoto.isEnabled = !Loading
        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            binding.userPhoto.setImageURI(data?.data)
        }
    }
}