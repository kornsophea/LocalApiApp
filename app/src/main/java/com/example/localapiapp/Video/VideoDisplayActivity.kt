package com.example.localapiapp.Video

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import com.example.localapiapp.Api.ApiClient
import com.example.localapiapp.Node.User
import com.example.localapiapp.R
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback


class VideoDisplayActivity : AppCompatActivity() {
    lateinit var videoView: VideoView

    private lateinit var idtv:TextView
    private lateinit var list:ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_display)
        val videoUrl=intent.getStringExtra("videoUrl")
        idtv=findViewById(R.id.idTVHeading)
        videoView = findViewById(R.id.videoView);
        val uri: Uri = Uri.parse(videoUrl)
        videoView.setVideoURI(uri)
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        mediaController.setMediaPlayer(videoView)
        videoView.setMediaController(mediaController)
        videoView.start()

    }

}