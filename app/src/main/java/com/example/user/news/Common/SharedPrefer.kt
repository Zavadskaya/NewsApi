package com.example.user.news.Common

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.widget.Toast
import com.example.user.news.Common.FilterISO.getCountry
import java.util.*

object SharedPrefer {
   fun saveData(position: Int,context: Context) {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val prefEditor = prefs.edit()
        prefEditor.putInt("saved", position)
        prefEditor.apply()
        Toast.makeText(context, "Data saved", Toast.LENGTH_LONG).show()

    }
    fun loadData(context: Context):String
    {
        val defValue = getCountry()!!.indexOf("RU")
        val savedCountryPos = PreferenceManager.getDefaultSharedPreferences(context).getInt("saved", defValue)
        val country = getCountry()!!.get(savedCountryPos)
        return country
    }
}