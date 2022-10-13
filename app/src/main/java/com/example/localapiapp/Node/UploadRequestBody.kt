package com.example.localapiapp.Node

import android.os.Looper
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okio.BufferedSink
import java.io.File
import java.io.FileInputStream
import java.io.PrintStream
import java.security.PrivateKey
import java.util.logging.Handler

data class UploadRequestBody (
    val file:File,
    val contentType:String,
    val callback:UploadCallBack
    ):RequestBody(){
    interface UploadCallBack{
        fun onProgressUpdate(percentage:Int)
    }

    inner class ProgressUpdate(
        private val upload:Long,
        private val total:Long
    ):Runnable{
        override fun run() {
            callback.onProgressUpdate((100*upload / total).toInt())
        }
    }
    override fun contentType()= MediaType.parse("$contentType/*")
    override fun contentLength()=file.length()
    override fun writeTo(sink: BufferedSink) {
        val length=file.length()
        val buffer=ByteArray(DEFAULT_BUFFER_SIZE)
        val fileInputStream=FileInputStream(file)
        var uploaded=0L
        fileInputStream.use {inputStream->
            var read:Int
            val handler= android.os.Handler(Looper.getMainLooper())
            while(inputStream.read(buffer).also {read=it}!=-1){
                handler.post(ProgressUpdate(uploaded,length))
                uploaded+=read
                sink.write(buffer,0,read)
            }
        }
    }
    companion object{
        private const val DEFAULT_BUFFER_SIZE=1048
    }
}
