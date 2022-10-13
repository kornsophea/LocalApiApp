package com.example.localapiapp.security

import android.os.Message
import android.provider.ContactsContract
import com.google.gson.annotations.SerializedName

data class LoginResponse (
    var email:String,
    var password:String
)

