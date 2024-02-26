package com.example.final_project.presentation.screen.profile.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_project.databinding.ProfileBottomSheetBinding
import com.example.final_project.presentation.model.cart.CartItem
import com.example.final_project.presentation.screen.profile.adapter.ProfileFavouritesRecyclerViewAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ProfileBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: ProfileBottomSheetBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private val profileFavouritesAdapter = ProfileFavouritesRecyclerViewAdapter()

    var switchListener: ((isChecked: Boolean) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProfileBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.switchMaterial.isChecked = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        setUpRecycler()
        setUpNightModeSwitchListener()


        bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior.skipCollapsed = true
        bottomSheetBehavior.peekHeight = 800
        bottomSheetBehavior.isHideable = false
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {

            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
    }

    fun submitDataToRecycler(list: List<CartItem>) {
        profileFavouritesAdapter.submitList(list)
    }

    private fun setUpRecycler() = with(binding.recyclerViewFavourites) {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = profileFavouritesAdapter
    }

    private fun setUpNightModeSwitchListener() {
        binding.switchMaterial.setOnCheckedChangeListener { _, isChecked ->
            switchListener?.invoke(isChecked)
        }
    }

    companion object {
        const val Profile_BOTTOM_SHEET = "ProfileBottomSheet"
    }
}