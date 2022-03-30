package com.google.newspaper.utils

import android.app.Activity
import android.content.Context

const val RESIDENT_PAGE = "resident_page"
const val IPHONE_PAGE = "iphone_page"
const val ANONIMOS_PAGE = "anonimos_page"
const val GP_TINDER_PAGE = "gp_tinder_page"
const val BANIMENTO_PAGE = "banimento_page"


class Preference(private val activity: Activity) {

    fun salveFavoritoResident(favoritarPage: Boolean) {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putBoolean(RESIDENT_PAGE, favoritarPage)
            apply()
        }
    }

    fun getFavoritoResident(): Boolean {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getBoolean(RESIDENT_PAGE, false)
    }

    fun salveFavoritoIphone(favoritarPage: Boolean) {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putBoolean(IPHONE_PAGE, favoritarPage)
            apply()
        }
    }

    fun getFavoritoIphone(): Boolean {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getBoolean(IPHONE_PAGE, false)
    }

    fun salveFavoritoAnonimos(favoritarPage: Boolean) {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putBoolean(ANONIMOS_PAGE, favoritarPage)
            apply()
        }
    }

    fun getFavoritoAnonimos(): Boolean {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getBoolean(ANONIMOS_PAGE, false)
    }

    fun salveFavoritoGpTinder(favoritarPage: Boolean) {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putBoolean(GP_TINDER_PAGE, favoritarPage)
            apply()
        }
    }

    fun getFavoritoGpTinder(): Boolean {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getBoolean(GP_TINDER_PAGE, false)
    }

    fun salveFavoritoBanimento(favoritarPage: Boolean) {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putBoolean(BANIMENTO_PAGE, favoritarPage)
            apply()
        }
    }

    fun getFavoritoBanimento(): Boolean {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getBoolean(BANIMENTO_PAGE, false)
    }
}