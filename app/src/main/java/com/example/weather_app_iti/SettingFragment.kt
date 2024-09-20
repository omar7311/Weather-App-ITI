package com.example.weather_app_iti

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weather_app_iti.databinding.FragmentSettingBinding
import java.util.prefs.Preferences

class SettingFragment : Fragment() {
    lateinit var binding: FragmentSettingBinding
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        sharedPreferences=requireActivity().getPreferences(Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
       setupSetting()
       setupOnCheckedRadioGroups()
        return binding.root
    }
    private fun setupSetting(){
        binding.locationRG.check(sharedPreferences.getInt(Settings.locationKey,binding.gps.id))
        binding.languageRG.check(sharedPreferences.getInt(Settings.languageKey,binding.en.id))
        binding.alertRG.check(sharedPreferences.getInt(Settings.alertKey,binding.alarm.id))
        binding.unitsRG.check(sharedPreferences.getInt(Settings.unitsKey,binding.standard.id))
    }
    private fun setupOnCheckedRadioGroups(){
        binding.locationRG.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                binding.gps.id -> {
                    editor.putInt(Settings.locationKey,binding.gps.id)
                    editor.commit()
                }
                binding.map.id -> {
                    editor.putInt(Settings.locationKey,binding.map.id)
                    editor.commit()
                }
            }
        }
        binding.alertRG.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                binding.alarm.id -> {
                    editor.putInt(Settings.alertKey,binding.alarm.id)
                    editor.commit()
                }
                binding.notify.id -> {
                    editor.putInt(Settings.alertKey,binding.notify.id)
                    editor.commit()
                }
            }
        }
        binding.languageRG.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                binding.ar.id -> {
                    editor.putInt(Settings.languageKey,binding.ar.id)
                    editor.commit()
                }
                binding.en.id -> {
                    editor.putInt(Settings.languageKey,binding.en.id)
                    editor.commit()
                }
            }
        }
        binding.unitsRG.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                binding.standard.id -> {
                    editor.putInt(Settings.unitsKey,binding.standard.id )
                    editor.commit()
                }
                binding.metric.id -> {
                    editor.putInt(Settings.unitsKey,binding.metric.id)
                    editor.commit()
                }
                binding.imperial.id -> {
                    editor.putInt(Settings.unitsKey,binding.imperial.id)
                    editor.commit()
                }
            }
        }
    }
}