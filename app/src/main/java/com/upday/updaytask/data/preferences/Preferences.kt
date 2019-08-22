package com.upday.updaytask.data.preferences

interface Preferences {
    fun writeString(key:String,value: String)
    fun readString(key:String): String
}