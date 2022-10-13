package com.example.localapiapp.Node

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.localapiapp.databinding.ActivityImageUploadBinding
import okhttp3.*
import okhttp3.MultipartBody.Part.createFormData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Multipart
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class ImageUploadActivity : AppCompatActivity(),UploadRequestBody.UploadCallBack {
    private lateinit var binding:ActivityImageUploadBinding
    private var selectedImage: Uri?=null

    private var PICK_IMAGE=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityImageUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgShow.setOnClickListener {
            openImageChooser()
        }
        binding.btnUpload.setOnClickListener {
            uploadImage()
        }
    }
    private fun uploadImage(){
        if(selectedImage == null ){
            binding.layoutRoot.snackbar("Select a image first")
            return
        }
        val parcelFileDescriptor=contentResolver.openAssetFileDescriptor(selectedImage!!,"r",null)?:return
        val file= File(cacheDir,contentResolver.getFileName(selectedImage!!))
        val inputStream=FileInputStream(parcelFileDescriptor.fileDescriptor)
        val outputStream=FileOutputStream(file)
        inputStream.copyTo(outputStream)
        binding.progressBar.progress=0
        val body=UploadRequestBody(file,"image",this)
        MyAPI().uploadImage(
            MultipartBody.Part.createFormData("image",file.name,body),
            RequestBody.create(MediaType.parse("multipart/form-data"),"Image From My Device")
        ).enqueue(object: retrofit2.Callback<UploadResponse>{
            override fun onResponse(
                call: Call<UploadResponse>,
                response: Response<UploadResponse>
            ) {
                binding.progressBar.progress=100
                binding.layoutRoot.snackbar(response.body()?.message.toString())
            }

            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                binding.layoutRoot.snackbar(t.message!!)
            }

        })

    }
    private fun openImageChooser(){
        val intent  = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent,PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==PICK_IMAGE&&resultCode==Activity.RESULT_OK){
            selectedImage=data?.data
            binding.imgShow.setImageURI(selectedImage)
        }
    }

    override fun onProgressUpdate(percentage: Int) {
        binding.progressBar.progress=percentage
    }

}