package com.example.localapiapp.security

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.localapiapp.Api.ApiClient
import com.example.localapiapp.MainActivity
import com.example.localapiapp.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var image:Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            test.setOnClickListener {
                var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, 3)

            }

            btnLogin.setOnClickListener {

                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()
                if(email==""&&password==""){
                    Toast.makeText(baseContext, "Please enter information...", Toast.LENGTH_LONG)
                        .show()
                }else{
                    loginWithLocalApi(email, password)
                }


            }
            btnSignIn.setOnClickListener {
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()
                if(email==""&&password==""){
                    Toast.makeText(baseContext, "Please enter information...", Toast.LENGTH_LONG)
                        .show()
                }else{
                    signInWithLocalApi(email, password)
                }
            }
        }

    }

    private fun loginWithLocalApi(email: String, password: String) {
        var boolean:Boolean
        val api = ApiClient.create()
        val call = api.login()
        call.enqueue(object : Callback<List<LoginResponse>> {
            override fun onResponse(
                call: Call<List<LoginResponse>>,
                response: Response<List<LoginResponse>>
            ) {
                val responsedata = response.body()!!
                for (data in responsedata) {
                    if(email==data.email&&password==data.password){
                        //store data in sharePreference
                        val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
                        val myEdit = sharedPreferences.edit()
                        myEdit.putString("currentEmail",data.email)
                        myEdit.putString("currentPassword",data.password)
                        myEdit.apply()
                        startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                    }

                }
            }

            override fun onFailure(call: Call<List<LoginResponse>>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun signInWithLocalApi(email: String, password: String) {

        val post = LoginResponse(email, password)

        val api = ApiClient.create().signIn(post)
        api.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(baseContext, "Sign In Successfully...", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(baseContext, "Sign In Fail...", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            image = data.data!!
            binding.edtEmail.setText(image.toString())

        }
    }
}