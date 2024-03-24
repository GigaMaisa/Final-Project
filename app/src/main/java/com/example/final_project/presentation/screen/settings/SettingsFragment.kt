package com.example.final_project.presentation.screen.settings

import android.app.LocaleManager
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.final_project.R
import com.example.final_project.databinding.FragmentSettingsBinding
import com.example.final_project.presentation.base.BaseFragment
import com.example.final_project.presentation.event.SettingsEvents
import com.example.final_project.presentation.state.SettingsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    private val viewModel: SettingsViewModel by viewModels()

    override fun setUp() {
        viewModel.onEvent(SettingsEvents.GetLanguageEvent)
        binding.switchMaterial.isChecked = isSystemDarkModeOn()
        setUpSpinner()
    }

    override fun setUpListeners() {
        binding.switchMaterial.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onEvent(SettingsEvents.IsDarkModeChecked(isChecked))
        }

        binding.btnGoBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.settingsStateFlow.collect {
                    handleState(it)
                }
            }
        }
    }

    private fun handleState(state: SettingsState) {
        state.isDarkMode?.let {
            changeDarkMode(it)
        }

        state.language?.let {
            applyLanguageSetting(it)
        }
    }

    private fun applyLanguageSetting(language: String) {
        val lang = if (language == "English" || language == "ინგლისური") "en" else "ka"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireContext().getSystemService(LocaleManager::class.java)
                .applicationLocales = LocaleList.forLanguageTags(lang)
        } else {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(lang))
        }
    }

    private fun setUpSpinner() {
        val languages = listOf(getStringResource(R.string.english), getStringResource(R.string.georgian))
        val adapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerLanguages.adapter = adapter

        binding.spinnerLanguages.setSelection(languages.indexOf(viewModel.settingsStateFlow.value.language))
        val isSpinnerSelectionProgrammatic = false

        binding.spinnerLanguages.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (!isSpinnerSelectionProgrammatic) {
                    val selectedLanguage = languages[position]
                    viewModel.onEvent(SettingsEvents.ChangeLanguageEvent(selectedLanguage))
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun changeDarkMode(isChecked: Boolean) {
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun getStringResource(resourceId: Int): String {
        return resources.getString(resourceId)
    }

    private fun isSystemDarkModeOn(): Boolean {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }
}