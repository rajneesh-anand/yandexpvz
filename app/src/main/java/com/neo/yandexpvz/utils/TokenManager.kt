package com.neo.yandexpvz.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.neo.yandexpvz.model.User
import com.neo.yandexpvz.utils.Constants.FCM_TOKEN
import com.neo.yandexpvz.utils.Constants.PREFS_TOKEN_FILE
import com.neo.yandexpvz.utils.Constants.USER_EMAIL
import com.neo.yandexpvz.utils.Constants.USER_IMAGE
import com.neo.yandexpvz.utils.Constants.USER_MOBILE
import com.neo.yandexpvz.utils.Constants.USER_NAME
import com.neo.yandexpvz.utils.Constants.USER_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class TokenManager @Inject constructor(@ApplicationContext context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE)

    fun saveToken(token: String, user: User) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.putString(USER_NAME, user.username)
        editor.putString(USER_MOBILE, user.mobile)
        editor.putString(USER_EMAIL, user.email)
        editor.putString(USER_IMAGE, user.image)
        editor.apply()
    }

    fun updateUser(image: String, name:String) {
        val editor = prefs.edit()
        editor.putString(USER_IMAGE, image)
        editor.putString(USER_NAME, name)
        editor.apply()
    }

    fun fcmToken(token:String) {
        val editor = prefs.edit()
        editor.putString(FCM_TOKEN, token)
        editor.apply()
    }



    fun deleteToken(){
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }


    fun getToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    fun getUserImage(): String? {
        return prefs.getString(USER_IMAGE, null)
    }

    fun getUserName(): String? {
        return prefs.getString(USER_NAME, null)
    }

    fun getUserMobile(): String? {
        return prefs.getString(USER_MOBILE, null)
    }

    fun getUserEmail(): String? {
        return prefs.getString(USER_EMAIL, null)
    }


    fun getFCMToken(): String? {
        return prefs.getString(FCM_TOKEN, null)
    }
}