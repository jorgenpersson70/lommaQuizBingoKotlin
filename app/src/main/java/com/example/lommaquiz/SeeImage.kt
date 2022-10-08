package com.example.lommaquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lommaquiz.databinding.ActivityMainBinding
import com.example.lommaquiz.databinding.ActivitySeeImageBinding
import com.squareup.picasso.Picasso

class SeeImage : AppCompatActivity() {
    private lateinit var binding: ActivitySeeImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeeImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var urlString = intent.getStringExtra("URL").toString()

        if ("https" in urlString) {

            // det går från firestore och unsplash men inte isstock
            var urlstr =
                "https://images.unsplash.com/photo-1653162266956-2793b89d6a87?crop=entropy&cs=tinysrgb&fm=jpg&ixlib=rb-1.2.1&q=80&raw_url=true&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687"
            //          Picasso.get().load("https://images.unsplash.com/photo-1653162266956-2793b89d6a87?crop=entropy&cs=tinysrgb&fm=jpg&ixlib=rb-1.2.1&q=80&raw_url=true&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687").into(binding.imageView)
            Picasso.get().load(urlstr).into(binding.imageView)
        }

        binding.backFromSeeImageBtn.setOnClickListener {
            finish()
        }

    }





}