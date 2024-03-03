package com.example.final_project.presentation.screen.profile.fragment

import android.util.Log.d
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_project.R
import com.example.final_project.databinding.FragmentProfileBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.extension.loadImage
import com.example.final_project.presentation.screen.profile.adapter.ProfileFavouritesRecyclerViewAdapter
import com.example.final_project.presentation.screen.profile.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val viewModel: ProfileViewModel by viewModels()
    private val favouritesAdapter = ProfileFavouritesRecyclerViewAdapter()
    override fun setUp() {
        binding.imageViewProfile.loadImage("https://scontent.xx.fbcdn.net/v/t1.15752-9/426069272_937110570961099_3747853385258191894_n.jpg?stp=dst-jpg_p403x403&_nc_cat=101&ccb=1-7&_nc_sid=510075&_nc_eui2=AeHTN1l17yXk_HpWCJhXRVAVVsVeJOGQTFFWxV4k4ZBMUe5dNRao6611hdhTO0VbhEj6gMT1G4bFFjK-NARb-nqY&_nc_ohc=X8mqeTVb9zUAX-pgvzY&_nc_ad=z-m&_nc_cid=0&_nc_ht=scontent.xx&oh=03_AdQzVinLUrOkMn-7TI7DYq2fyU_ok_M-nKnSheyWN39-9g&oe=660BCC02")
        setUpRecycler()
        setUpSpinner()
        binding.switchMaterial.isChecked = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
    }

    override fun setUpListeners() {
        binding.imageViewProfile.setOnClickListener {
            d("itInteracts", "CLICKED")
        }

        binding.switchMaterial.setOnCheckedChangeListener { _, isChecked ->
            changeDarkMode(isChecked)
        }
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.profileStateFlow.collect {
                    it.favourites?.let { favourites ->
                        favouritesAdapter.submitList(favourites)
                    }
                }
            }
        }
    }

    private fun setUpRecycler() {
        with(binding.recyclerViewFavourites) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favouritesAdapter
        }
    }

    private fun changeDarkMode(isChecked: Boolean) {
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun setUpSpinner() {
        val languages = listOf(getStringResource(R.string.english), getStringResource(R.string.georgian))
        val adapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerLanguages.adapter = adapter

        binding.spinnerLanguages.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedLanguage = languages[position]

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun getStringResource(resourceId: Int): String {
        return resources.getString(resourceId)
    }
}