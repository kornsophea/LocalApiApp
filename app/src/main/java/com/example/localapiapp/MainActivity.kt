package com.example.localapiapp

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.localapiapp.Api.ApiClient
import com.example.localapiapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            val id:Int=intent.getIntExtra("id",0)
            val email:String?=intent.getStringExtra("email")
            val password:String?=intent.getStringExtra("password")
            edtEmail.setText(email)
            edtPassword.setText(password)
            test.text=id.toString()
            btnPut.setOnClickListener {
                val currentEmail=edtEmail.text.toString()
                val currentPassword=edtPassword.text.toString()
                val currentID=test.text.toString()
                if(currentEmail!=""&&currentPassword!=""&&currentID!=""){
//                    putDataApi(currentID,currentEmail,currentPassword)
                }else{
                    Toast.makeText(this@MainActivity,"Please Select Item ...", Toast.LENGTH_LONG).show()
                }

            }
            btnGet.setOnClickListener {
                startActivity(Intent(this@MainActivity,ListDataActivity::class.java))
            }
            btnPost.setOnClickListener {
                val email=edtEmail.text.toString()
                val password=edtPassword.text.toString()
                if(email!=""&&password!=""){
                    postDataApi(email,password)
                }else{
                    Toast.makeText(this@MainActivity,"Please Enter Your Information ...", Toast.LENGTH_LONG).show()
                }

            }
            btnDelete.setOnClickListener {
                val currentID=test.text.toString()
                if(currentID!=""||currentID!="0"){
                    deleteDataApi(currentID)
                }else{
                    Toast.makeText(this@MainActivity,"Please Select Item ...", Toast.LENGTH_LONG).show()
                }

            }
        }
    }
    private fun postDataApi(email:String,password:String){

        val api=ApiClient.create()
        val call=api.postData(email,password)
        call.enqueue(object :retrofit2.Callback<Data>{
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                if(response.isSuccessful){
                    startActivity(Intent(this@MainActivity,ListDataActivity::class.java))
                    clearText()
                }else{
                    Toast.makeText(this@MainActivity,response.message(), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Data>, t: Throwable) {
                Toast.makeText(this@MainActivity,t.message,Toast.LENGTH_LONG).show()
            }

        })
    }
//    private fun putDataApi(id:String,email: String,password: String){
//        val data=Data(id.toInt(),email,password)
//
//        val api=ApiClient.create()
//        val call=api.putData(id.toInt(),data)
//        call.enqueue(object:retrofit2.Callback<Data>{
//            override fun onResponse(call: Call<Data>, response: Response<Data>) {
//                if(response.isSuccessful){
//                    Toast.makeText(this@MainActivity,"Update Successfully",Toast.LENGTH_LONG).show()
//                    clearText()
//                }else{
//                    Toast.makeText(this@MainActivity,response.message(),Toast.LENGTH_LONG).show()
//                }
//            }
//
//            override fun onFailure(call: Call<Data>, t: Throwable) {
//                Toast.makeText(this@MainActivity,t.message,Toast.LENGTH_LONG).show()
//            }
//
//        })
//    }
    private fun deleteDataApi(id:String){
        val api=ApiClient.create()
        val call=api.deleteData(id.toInt())
        call.enqueue(object:Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful){
                    Toast.makeText(this@MainActivity,"Delete Successfully",Toast.LENGTH_LONG).show()
                    clearText()
                }else {
                    Toast.makeText(this@MainActivity, "Delete Fail", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@MainActivity,t.message,Toast.LENGTH_LONG).show()
            }

        })
    }
    private fun clearText(){
        binding.edtEmail.setText("")
        binding.edtPassword.setText("")
        binding.test.text=""

    }


}