package com.example.localapiapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.localapiapp.Api.ApiClient
import com.example.localapiapp.databinding.ActivityListDataBinding
import com.example.localapiapp.security.LoginResponse
import retrofit2.Response
import javax.security.auth.callback.Callback

class ListDataActivity : AppCompatActivity() {
    private var adapterData: AdapterData?=null

    private lateinit var binding: ActivityListDataBinding
    private lateinit var dataModel: Data
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityListDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        getDataApi()
        adapterData?.setOnClickItem {
            val intent= Intent(this,MainActivity::class.java)
            intent.putExtra("id",it.id)
            intent.putExtra("email",it.name)
            intent.putExtra("password",it.password)
            dataModel=it
            this.startActivity(intent)

        }
        adapterData?.setOnClickDeleteItem {
            deleteDataApi(it.id)
        }
    }
    private fun initRecyclerView(){
        binding.listData.layoutManager=LinearLayoutManager(this)
        adapterData= AdapterData()
        binding.listData.adapter=adapterData
    }
    private fun getDataApi(){
        val api = ApiClient.create()
        val call = api.getDataLaravel()
        call.enqueue(object :retrofit2.Callback<List<Data>>{
            override fun onResponse(
                call: retrofit2.Call<List<Data>>,
                response: Response<List<Data>>
            ) {
                if(response.isSuccessful){
                    val responseData=response.body()
                    adapterData?.addItems(responseData as ArrayList<Data>)
                }else{
                    Toast.makeText(this@ListDataActivity,"Response Fail",Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: retrofit2.Call<List<Data>>, t: Throwable) {
                Toast.makeText(this@ListDataActivity,"Connection Fail",Toast.LENGTH_LONG).show()
            }

        })

    }
    private fun deleteDataApi(id:Int){
        val builder=AlertDialog.Builder(this)
        builder.setMessage("Do you want to delete?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes"){
            dialog,_->
            dialog.dismiss()
            val api=ApiClient.create()
            val call=api.deleteData(id)
            call.enqueue(object :retrofit2.Callback<Void>{
                override fun onResponse(call: retrofit2.Call<Void>, response: Response<Void>) {
                    if(response.isSuccessful){
                        Toast.makeText(this@ListDataActivity,"Delete Successfully",Toast.LENGTH_LONG).show()
                        getDataApi()
                    }else{
                        Toast.makeText(this@ListDataActivity,response.message(),Toast.LENGTH_LONG).show()
                    }

                }

                override fun onFailure(call: retrofit2.Call<Void>, t: Throwable) {
                    Toast.makeText(this@ListDataActivity,t.message,Toast.LENGTH_LONG).show()
                }

            })
        }
        builder.setNegativeButton("No"){
            dialog,_->dialog.dismiss()
        }
        val alert=builder.create()
        alert.show()
    }
}