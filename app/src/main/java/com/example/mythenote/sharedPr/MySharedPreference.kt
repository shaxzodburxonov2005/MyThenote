package com.example.mythenote.sharedPr

import android.content.Context
import android.content.SharedPreferences
import android.view.Display.Mode

object MySharedPreference {
    private const val NAME="nameSplash"
    private const val MODE=Context.MODE_PRIVATE
    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context){
        sharedPreferences=context.getSharedPreferences(NAME, MODE)
    }
    private inline fun SharedPreferences.edit(operation:(SharedPreferences.Editor)->Unit){
        val editor:SharedPreferences.Editor=edit()
        operation(editor)
        editor.apply()
    }

    var darkMode:Boolean?
    get()= sharedPreferences.getBoolean("key1",false)
    set(value) = sharedPreferences.edit{
        if (value!=null){
            it.putBoolean("key1",value)
        }
    }

    var editSplash:Boolean?
    get() = sharedPreferences.getBoolean("name",false)
    set(value) = sharedPreferences.edit {
        if (value!=null){
            it.putBoolean("name",value)
        }
    }
}