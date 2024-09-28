package com.example.weather_app_iti

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.widget.ImageView
import java.util.Locale


object Setting {
        const val alertKey="alert_key"
        const val locationKey="location_key"
        const val languageKey="language_key"
        const val unitsKey="units_key"

        fun getImage(image: ImageView,icon:String){
                when(icon){
                        "01d"->image.setImageResource(R.drawable._01d)
                        "01n"->image.setImageResource(R.drawable._01n)
                        "02d"->image.setImageResource(R.drawable._02d)
                        "02n"->image.setImageResource(R.drawable._02n)
                        "03d","03n"->image.setImageResource(R.drawable._03d)
                        "04n","04d"->image.setImageResource(R.drawable._04n)
                        "09d","09n"->image.setImageResource(R.drawable._09n)
                        "10d"->image.setImageResource(R.drawable._10d)
                        "10n"->image.setImageResource(R.drawable._10n)
                        "11d","11n"->image.setImageResource(R.drawable._11d)
                        "13d","13n"->image.setImageResource(R.drawable._13d)
                        "50d","50n"->image.setImageResource(R.drawable._50d)

                }
        }
        private const val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"

        // Call this in your Application or Activity on launch to apply saved language
        fun onAttach(context: Context): Context {
                val lang = getPersistedData(context, Locale.getDefault().language)
                return setLocale(context, lang)
        }

        // Call this method to change the language
        fun setLocale(context: Context, language: String): Context {
                persist(context, language)

                return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        updateResources(context, language)
                } else {
                        updateResourcesLegacy(context, language)
                }
        }

        // Save selected language to SharedPreferences
        private fun persist(context: Context, language: String) {
                val prefs = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
                val editor = prefs.edit()
                editor.putString(SELECTED_LANGUAGE, language)
                editor.apply()
        }

        // Retrieve the saved language from SharedPreferences
        private fun getPersistedData(context: Context, defaultLanguage: String): String {
                val preferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
                return preferences.getString(SELECTED_LANGUAGE, defaultLanguage) ?: defaultLanguage
        }

        // For Android N and above
        private fun updateResources(context: Context, language: String): Context {
                val locale = Locale(language)
                Locale.setDefault(locale)

                val config = Configuration(context.resources.configuration)
                config.setLocale(locale)
                config.setLayoutDirection(locale)

                return context.createConfigurationContext(config)
        }

        // For Android below N
        @Suppress("deprecation")
        private fun updateResourcesLegacy(context: Context, language: String): Context {
                val locale = Locale(language)
                Locale.setDefault(locale)

                val resources: Resources = context.resources
                val config: Configuration = resources.configuration
                config.locale = locale
                config.setLayoutDirection(locale)

                resources.updateConfiguration(config, resources.displayMetrics)
                return context
        }
}