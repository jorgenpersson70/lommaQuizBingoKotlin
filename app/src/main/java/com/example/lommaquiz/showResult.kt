package com.example.lommaquiz

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.lommaquiz.databinding.ActivityMainBinding
import com.example.lommaquiz.databinding.ActivityShowResultBinding
import com.google.android.gms.maps.model.MarkerOptions

class showResult : AppCompatActivity() {
   private lateinit var binding:ActivityShowResultBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
   //     setContentView(R.layout.activity_show_result)

        binding = ActivityShowResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        for (i in 1..12)
        {

                when (i) {
                    1 -> {
                        binding.showResQuest1Btn.setBackgroundColor(Color.WHITE)
                    }
                    2 -> {
                        binding.showResQuest2Btn.setBackgroundColor(Color.WHITE)
                    }
                    3 -> {
                        binding.showResQuest3Btn.setBackgroundColor(Color.WHITE)
                    }
                    4 -> {
                        binding.showResQuest4Btn.setBackgroundColor(Color.WHITE)
                    }
                    5 -> {
                        binding.showResQuest5Btn.setBackgroundColor(Color.WHITE)
                    }
                    6 -> {
                        binding.showResQuest6Btn.setBackgroundColor(Color.WHITE)
                    }
                    7 -> {
                        binding.showResQuest7Btn.setBackgroundColor(Color.WHITE)
                    }
                    8 -> {
                        binding.showResQuest8Btn.setBackgroundColor(Color.WHITE)
                    }
                    9 -> {
                        binding.showResQuest9Btn.setBackgroundColor(Color.WHITE)
                    }
                    10 -> {
                        binding.showResQuest10Btn.setBackgroundColor(Color.WHITE)
                    }
                    11 -> {
                        binding.showResQuest11Btn.setBackgroundColor(Color.WHITE)
                    }
                    12 -> {
                        binding.showResQuest12Btn.setBackgroundColor(Color.WHITE)
                    }
                    else -> {

                    }
            }


        for (i in 1..12)
        {
            if (quizCorrectAnswer[i-1] == givenAnswer[i-1])
            {
                when (i) {
                    1 -> {
                        binding.showResQuest1Btn.setBackgroundColor(Color.GREEN)
                    }
                    2 -> {
                        binding.showResQuest2Btn.setBackgroundColor(Color.GREEN)
                    }
                    3 -> {
                        binding.showResQuest3Btn.setBackgroundColor(Color.GREEN)
                    }
                    4 -> {
                        binding.showResQuest4Btn.setBackgroundColor(Color.GREEN)
                    }
                    5 -> {
                        binding.showResQuest5Btn.setBackgroundColor(Color.GREEN)
                    }
                    6 -> {
                        binding.showResQuest6Btn.setBackgroundColor(Color.GREEN)
                    }
                    7 -> {
                        binding.showResQuest7Btn.setBackgroundColor(Color.GREEN)
                    }
                    8 -> {
                        binding.showResQuest8Btn.setBackgroundColor(Color.GREEN)
                    }
                    9 -> {
                        binding.showResQuest9Btn.setBackgroundColor(Color.GREEN)
                    }
                    10 -> {
                        binding.showResQuest10Btn.setBackgroundColor(Color.GREEN)
                    }
                    11 -> {
                        binding.showResQuest11Btn.setBackgroundColor(Color.GREEN)
                    }
                    12 -> {
                        binding.showResQuest12Btn.setBackgroundColor(Color.GREEN)
                    }
                    else -> {

                    }
                }
            }



                binding.showResQuest1Btn.setOnClickListener {
                    if (quizURLString[0] != "") {

                            if ((".jpg" in quizURLString[showMarker - 1]) || (".jpeg" in quizURLString[showMarker - 1])) {
                                val intent = Intent(this, showQuestionWithAnswer::class.java)
                                intent.putExtra("Question", "1")
                                startActivity(intent)
                            } else {
                                val intent = Intent(this, showQuestionsWithAnswer3::class.java)
                                intent.putExtra("Question", "1")
                                startActivity(intent)
                            }

                    }
                    else{
                        val intent = Intent(this, showQuestionsWithAnswer3::class.java)
                        intent.putExtra("Question", "1")
                        startActivity(intent)
                    }
                }

            binding.showResQuest2Btn.setOnClickListener {
                if (quizURLString[1] != "") {
                    if ((".jpg" in quizURLString[showMarker - 1]) || (".jpeg" in quizURLString[showMarker - 1])) {
                        val intent = Intent(this, showQuestionWithAnswer::class.java)
                        intent.putExtra("Question", "2")
                        startActivity(intent)
                    } else {
                        val intent = Intent(this, showQuestionsWithAnswer3::class.java)
                        intent.putExtra("Question", "2")
                        startActivity(intent)
                    }
                }
                else{
                    val intent = Intent(this, showQuestionsWithAnswer3::class.java)
                    intent.putExtra("Question", "2")
                    startActivity(intent)
                }
            }

            binding.showResQuest3Btn.setOnClickListener {
                if (quizURLString[2] != "") {
                    if ((".jpg" in quizURLString[showMarker - 1]) || (".jpeg" in quizURLString[showMarker - 1])) {
                        val intent = Intent(this, showQuestionWithAnswer::class.java)
                        intent.putExtra("Question", "3")
                        startActivity(intent)
                    } else {
                        val intent = Intent(this, showQuestionsWithAnswer3::class.java)
                        intent.putExtra("Question", "3")
                        startActivity(intent)
                    }
                }
                else{
                    val intent = Intent(this, showQuestionsWithAnswer3::class.java)
                    intent.putExtra("Question", "3")
                    startActivity(intent)
                }
            }

            binding.showResQuest4Btn.setOnClickListener {
                if (quizURLString[3] != "") {
                    if ((".jpg" in quizURLString[showMarker - 1]) || (".jpeg" in quizURLString[showMarker - 1])) {
                        val intent = Intent(this, showQuestionWithAnswer::class.java)
                        intent.putExtra("Question", "4")
                        startActivity(intent)
                    } else {
                        val intent = Intent(this, showQuestionsWithAnswer3::class.java)
                        intent.putExtra("Question", "4")
                        startActivity(intent)
                    }
                }
                else{
                    val intent = Intent(this, showQuestionsWithAnswer3::class.java)
                    intent.putExtra("Question", "4")
                    startActivity(intent)
                }
            }

            binding.showResQuest5Btn.setOnClickListener {
                if (quizURLString[4] != "") {
                    if ((".jpg" in quizURLString[showMarker - 1]) || (".jpeg" in quizURLString[showMarker - 1])) {
                        val intent = Intent(this, showQuestionWithAnswer::class.java)
                        intent.putExtra("Question", "5")
                        startActivity(intent)
                    } else {
                        val intent = Intent(this, showQuestionsWithAnswer3::class.java)
                        intent.putExtra("Question", "5")
                        startActivity(intent)
                    }
                }
                else{
                    val intent = Intent(this, showQuestionsWithAnswer3::class.java)
                    intent.putExtra("Question", "5")
                    startActivity(intent)
                }
            }

            binding.showResQuest6Btn.setOnClickListener {
                if (quizURLString[5] != "") {
                    if ((".jpg" in quizURLString[showMarker - 1]) || (".jpeg" in quizURLString[showMarker - 1])) {
                        val intent = Intent(this, showQuestionWithAnswer::class.java)
                        intent.putExtra("Question", "6")
                        startActivity(intent)
                    } else {
                        val intent = Intent(this, showQuestionsWithAnswer3::class.java)
                        intent.putExtra("Question", "6")
                        startActivity(intent)
                    }
                }
                else{
                    val intent = Intent(this, showQuestionsWithAnswer3::class.java)
                    intent.putExtra("Question", "6")
                    startActivity(intent)
                }
            }

            binding.showResQuest7Btn.setOnClickListener {
                if (quizURLString[6] != "") {
                    if ((".jpg" in quizURLString[showMarker - 1]) || (".jpeg" in quizURLString[showMarker - 1])) {
                        val intent = Intent(this, showQuestionWithAnswer::class.java)
                        intent.putExtra("Question", "7")
                        startActivity(intent)
                    } else {
                        val intent = Intent(this, showQuestionsWithAnswer3::class.java)
                        intent.putExtra("Question", "7")
                        startActivity(intent)
                    }
                }
                else{
                    val intent = Intent(this, showQuestionsWithAnswer3::class.java)
                    intent.putExtra("Question", "7")
                    startActivity(intent)
                }
            }

            binding.showResQuest8Btn.setOnClickListener {
                if (quizURLString[7] != "") {
                    if ((".jpg" in quizURLString[showMarker - 1]) || (".jpeg" in quizURLString[showMarker - 1])) {
                        val intent = Intent(this, showQuestionWithAnswer::class.java)
                        intent.putExtra("Question", "8")
                        startActivity(intent)
                    } else {
                        val intent = Intent(this, showQuestionsWithAnswer3::class.java)
                        intent.putExtra("Question", "8")
                        startActivity(intent)
                    }
                }
                else{
                    val intent = Intent(this, showQuestionsWithAnswer3::class.java)
                    intent.putExtra("Question", "8")
                    startActivity(intent)
                }
            }

            binding.showResQuest9Btn.setOnClickListener {
                if (quizURLString[8] != "") {
                    if ((".jpg" in quizURLString[showMarker - 1]) || (".jpeg" in quizURLString[showMarker - 1])) {
                        val intent = Intent(this, showQuestionWithAnswer::class.java)
                        intent.putExtra("Question", "9")
                        startActivity(intent)
                    } else {
                        val intent = Intent(this, showQuestionsWithAnswer3::class.java)
                        intent.putExtra("Question", "9")
                        startActivity(intent)
                    }
                }
                else{
                    val intent = Intent(this, showQuestionsWithAnswer3::class.java)
                    intent.putExtra("Question", "9")
                    startActivity(intent)
                }
            }

            binding.showResQuest10Btn.setOnClickListener {
                if (quizURLString[9] != "") {
                    if ((".jpg" in quizURLString[showMarker - 1]) || (".jpeg" in quizURLString[showMarker - 1])) {
                        val intent = Intent(this, showQuestionWithAnswer::class.java)
                        intent.putExtra("Question", "10")
                        startActivity(intent)
                    } else {
                        val intent = Intent(this, showQuestionsWithAnswer3::class.java)
                        intent.putExtra("Question", "10")
                        startActivity(intent)
                    }
                }
                else{
                    val intent = Intent(this, showQuestionsWithAnswer3::class.java)
                    intent.putExtra("Question", "10")
                    startActivity(intent)
                }
            }

            binding.showResQuest11Btn.setOnClickListener {
                if (quizURLString[10] != "") {
                    if ((".jpg" in quizURLString[showMarker - 1]) || (".jpeg" in quizURLString[showMarker - 1])) {
                        val intent = Intent(this, showQuestionWithAnswer::class.java)
                        intent.putExtra("Question", "11")
                        startActivity(intent)
                    } else {
                        val intent = Intent(this, showQuestionsWithAnswer3::class.java)
                        intent.putExtra("Question", "11")
                        startActivity(intent)
                    }
                }
                else{
                    val intent = Intent(this, showQuestionsWithAnswer3::class.java)
                    intent.putExtra("Question", "11")
                    startActivity(intent)
                }
            }

            binding.showResQuest12Btn.setOnClickListener {
                if (quizURLString[11] != "") {
                    if ((".jpg" in quizURLString[showMarker - 1]) || (".jpeg" in quizURLString[showMarker - 1])) {
                        val intent = Intent(this, showQuestionWithAnswer::class.java)
                        intent.putExtra("Question", "12")
                        startActivity(intent)
                    } else {
                        val intent = Intent(this, showQuestionsWithAnswer3::class.java)
                        intent.putExtra("Question", "12")
                        startActivity(intent)
                    }
                }
                else{
                    val intent = Intent(this, showQuestionsWithAnswer3::class.java)
                    intent.putExtra("Question", "12")
                    startActivity(intent)
                }
            }

            }
        }

        binding.goBackBtn.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
// set the new task and clear flags
            countQuizWalks2 = 0
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)

    // this jumped back, thge above jumped to main        finish()
        /*    Intent intent = new Intent(this, MainActivity.class)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)*/

         //
       // krasch     finishAffinity()
       /*     @Override
            public void onBackPressed() {
                finishAffinity();
                startActivity(new Intent(PaymentDoneActivity.this,Home.class));
            }*/
        }

    }
}