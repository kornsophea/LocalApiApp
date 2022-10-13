package com.example.localapiapp.Speech

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.EditText
import android.widget.TextSwitcher
import android.widget.Toast
import com.example.localapiapp.R
import java.util.*

class TextToSpeechActivity : AppCompatActivity() {
    private lateinit var textConvert:TextToSpeech
    private lateinit var edtText: EditText
    private lateinit var btnConvert:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_to_speech)

        edtText=findViewById(R.id.edtText)
        btnConvert=findViewById(R.id.btn_convert)

        btnConvert.setOnClickListener {
            textConvert= TextToSpeech(applicationContext,TextToSpeech.OnInitListener {
                if(it==TextToSpeech.SUCCESS){
                    textConvert.language= Locale.ENGLISH
                    textConvert.setSpeechRate(1.0f)
                    textConvert.speak(edtText.text.toString(),TextToSpeech.QUEUE_ADD,null)
                }else{
                    Toast.makeText(this,"False",Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}