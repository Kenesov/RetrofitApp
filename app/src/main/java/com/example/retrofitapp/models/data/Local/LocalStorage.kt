package com.example.retrofitapp.models.data.Local

import android.content.Context
import android.content.SharedPreferences
import com.example.chatappwithfirebase.utils.BooleanPreference
import com.example.chatappwithfirebase.utils.StringPreference
import com.example.retrofitapp.App.App

class LocalStorage {

        companion object {
            val prefs: SharedPreferences =
                App.instance.getSharedPreferences("ChatAppSharedPrefs", Context.MODE_PRIVATE)
        }
        var token by StringPreference(prefs, "Nothing to show")

        var isLogin by BooleanPreference(prefs, false)
    }
