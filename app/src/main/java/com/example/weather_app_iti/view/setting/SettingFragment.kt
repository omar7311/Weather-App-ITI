package com.example.weather_app_iti.view.setting

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.weather_app_iti.R
import com.example.weather_app_iti.databinding.FragmentSettingBinding
import com.example.weather_app_iti.view.maps.MapsFragment

class SettingFragment : Fragment() {
    lateinit var binding: FragmentSettingBinding
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences=requireActivity().getSharedPreferences("setup_setting",Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
       setupSetting()
       setupOnCheckedRadioGroups()
        return binding.root
    }
    private fun setupSetting(){
        when(sharedPreferences.getString(Setting.locationKey,getString(R.string.Gps)))
        {
            getString(R.string.Map)->binding.locationRG.check(binding.map.id)
            getString(R.string.Gps)->binding.locationRG.check(binding.gps.id)

        }
        when(sharedPreferences.getString(Setting.alertKey,getString(R.string.Alarm)))
        {
            getString(R.string.Alarm)->binding.alertRG.check(binding.alarm.id)
            getString(R.string.Notify)->binding.alertRG.check(binding.notify.id)

        }
        when(sharedPreferences.getString(Setting.languageKey,getString(R.string.en)))
        {
            getString(R.string.en)->binding.languageRG.check(binding.en.id)
            getString(R.string.ar)->binding.languageRG.check(binding.ar.id)

        }
        when(sharedPreferences.getString(Setting.unitsKey,getString(R.string.standard)))
        {
            getString(R.string.standard)->binding.unitsRG.check(binding.standard.id)
            getString(R.string.metric)->binding.unitsRG.check(binding.metric.id)
            getString(R.string.imperial)->binding.unitsRG.check(binding.imperial.id)

        }

    }
    private fun setupOnCheckedRadioGroups(){
        binding.locationRG.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                binding.gps.id -> {
                    editor.putString(Setting.locationKey,getString(R.string.Gps))
                    editor.commit()
                }
                binding.map.id -> {
                    editor.putString(Setting.locationKey,getString(R.string.Map))
                    editor.commit()
                    val fragment= MapsFragment()
                    fragment.label="Setting"
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment,fragment).commit()
                    (activity as AppCompatActivity).supportActionBar?.title=getString(R.string.google_map)
                }
            }
        }
        binding.alertRG.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                binding.alarm.id -> {
                    editor.putString(Setting.alertKey,getString(R.string.Alarm))
                    editor.commit()
                }
                binding.notify.id -> {
                    editor.putString(Setting.alertKey,getString(R.string.Notify))
                    editor.commit()
                }
            }
        }
        binding.languageRG.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                binding.ar.id -> {
                    editor.putString(Setting.languageKey,getString(R.string.ar))
                    editor.commit()
                    Setting.setLocale(requireContext(),"ar")
                    requireActivity().recreate()
                    requireActivity().window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
                }
                binding.en.id -> {
                    editor.putString(Setting.languageKey,getString(R.string.en))
                    editor.commit()
                    Setting.setLocale(requireContext(),"en")
                    requireActivity().recreate()
                    requireActivity().window.decorView.layoutDirection = View.LAYOUT_DIRECTION_LTR

                }
            }
        }
        binding.unitsRG.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                binding.standard.id -> {
                    editor.putString(Setting.unitsKey,getString(R.string.standard) )
                    editor.commit()
                }
                binding.metric.id -> {
                    editor.putString(Setting.unitsKey,getString(R.string.metric))
                    editor.commit()
                }
                binding.imperial.id -> {
                    editor.putString(Setting.unitsKey,getString(R.string.imperial))
                    editor.commit()
                }
            }
        }
    }
}