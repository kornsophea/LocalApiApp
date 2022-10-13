package com.example.localapiapp.Image

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.localapiapp.R
import com.squareup.picasso.Picasso

class ImageActivity : AppCompatActivity() {
    private lateinit var image:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        image=findViewById(R.id.image)
        val url="https://cdn.pixabay.com/photo/2013/07/21/13/00/rose-165819__480.jpg"
        Picasso.get().load(url).into(image)
    }
}