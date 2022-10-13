package com.example.localapiapp.Node

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.localapiapp.Api.ApiClient
import com.example.localapiapp.R
import com.example.localapiapp.databinding.ActivityUserListDataBinding
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class UserListDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserListDataBinding
    private lateinit var nodeRecyclerView: RecyclerView
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityUserListDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nodeRecyclerView=findViewById(R.id.node_recyclerview)
        getDataFromNode()
    }
    private fun getDataFromNode(){
        val apiInterface=ApiClient.create()
        val call=apiInterface.getDataFromNodeJs()
        call.enqueue(object:retrofit2.Callback<List<User>>{
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if(response.isSuccessful){
                    val data=response.body()
                    nodeRecyclerView.layoutManager=LinearLayoutManager(baseContext)
                    adapter=UserAdapter(data as ArrayList<User>, this@UserListDataActivity)
                    nodeRecyclerView.adapter=adapter
                }else{
                    Toast.makeText(baseContext,"Fail Response",Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Toast.makeText(baseContext,"Can not Connect",Toast.LENGTH_LONG).show()
            }

        })
    }
}