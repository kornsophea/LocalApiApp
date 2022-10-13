package com.example.localapiapp.Speech

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import com.example.localapiapp.R
import com.example.localapiapp.databinding.ActivityLoginBinding
import com.example.localapiapp.databinding.ActivityVoiceToTextBinding
import java.util.*
import kotlin.collections.ArrayList

class VoiceToTextActivity : AppCompatActivity() {

    private val RQ_SPEECH_REC=102
    private lateinit var binding: ActivityVoiceToTextBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityVoiceToTextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSpeech.setOnClickListener {
            askSpeechInput()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==RQ_SPEECH_REC&&resultCode==Activity.RESULT_OK){
            val result:ArrayList<String>? =data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            binding.tvResult.text=result?.get(0).toString()
        }
    }
    private fun askSpeechInput(){
        if(!SpeechRecognizer.isRecognitionAvailable(this)){
            Toast.makeText(this,"Speech recognition is not available",Toast.LENGTH_LONG).show()
        }else{
            val i=Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault())
            i.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say Something!")
            startActivityForResult(i,RQ_SPEECH_REC)
        }
    }
}