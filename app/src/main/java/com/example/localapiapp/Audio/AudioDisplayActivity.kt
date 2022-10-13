package com.example.localapiapp.Audio

import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.localapiapp.AdapterData
import com.example.localapiapp.R

class AudioDisplayActivity : AppCompatActivity() {
    private lateinit var playIB: ImageView
    private lateinit var pauseIB: ImageButton
    private lateinit var mediaPlayer: MediaPlayer
    private  var totalTime:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_display)
        playIB = findViewById(R.id.idIBPlay)
        mediaPlayer = MediaPlayer()

        val audioUrl = intent.getStringExtra("audio")
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer.setVolume(10f,100f)
        mediaPlayer.setDataSource(audioUrl)
        mediaPlayer.prepare()


    }
     fun play(view:View){
        if(mediaPlayer.isPlaying){
            mediaPlayer.pause()
            playIB.setBackgroundResource(R.drawable.ic_play)

        }else{
            mediaPlayer.start()
            playIB.visibility
            playIB.setBackgroundResource(R.drawable.ic_pause)

        }
    }

}