package com.example.lommaquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lommaquiz.databinding.ActivityChoseSeeWalksBinding


class choseSeeWalks : AppCompatActivity() {
    private lateinit var binding: ActivityChoseSeeWalksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chose_see_walks)



        binding = ActivityChoseSeeWalksBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.walk1Btn.setOnClickListener {

         //       val intent = Intent(this, showWalk::class.java)


            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("walk", "Walk1")

                startActivity(intent)

        }

        binding.walk2Btn.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("walk", "Walk2")

            startActivity(intent)
        }

        binding.walk3Btn.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("walk", "Walk3")

            startActivity(intent)
        }

        binding.walk4Btn.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("walk", "Walk4")

            startActivity(intent)
        }

        binding.walk5Btn.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("walk", "Walk5")

            startActivity(intent)
        }

        binding.recyclerJumpBtn.setOnClickListener {
            val intent = Intent(this, ListOfQuizes::class.java)
       //     intent.putExtra("walk", "Walk5")

            startActivity(intent)
        }



    }
}