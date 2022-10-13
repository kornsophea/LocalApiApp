package com.example.localapiapp.Api

import com.example.localapiapp.Data
import com.example.localapiapp.DataModel
import com.example.localapiapp.JsonData
import com.example.localapiapp.Node.User
import com.example.localapiapp.security.LoginResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("/user")
    fun login(): Call<List<LoginResponse>>
//get data from node js
    @GET("/user")
    fun getDataFromNodeJs(): Call<List<User>>

    @POST("/user")
     fun signIn(@Body loginResponse: LoginResponse): Call<LoginResponse>
//Api as Laravel
    @GET("/api/posts")
    fun getDataLaravel(): Call<List<Data>>

     @FormUrlEncoded
    @POST("/api/post/add")
    fun postData(
        @Field("name") email:String,
        @Field("password") password:String
    ): Call<Data>

     @PUT("/api/post/{id}/update")
     fun putData(@Path("id") id:Int,@Body data: Data):Call<Data>

     @PATCH("/user/{id}")
     fun pathData(@Path("id")id:Int,@Body loginResponse: LoginResponse):Call<LoginResponse>

     @DELETE("/api/post/{id}/delete")
     fun deleteData(@Path("id") id:Int):Call<Void>

     @Multipart
     @POST("/user")
     fun uploadImage(
         @Part image:MultipartBody.Part,

     ):Call<User>
}