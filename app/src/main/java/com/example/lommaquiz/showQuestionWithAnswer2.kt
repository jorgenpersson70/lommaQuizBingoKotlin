package com.example.lommaquiz

import android.content.ContentValues
import android.graphics.Color
import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.lommaquiz.databinding.ActivityMainBinding
import com.example.lommaquiz.databinding.ActivityShowQuestionWithAnswer2Binding
import com.example.lommaquiz.databinding.ActivityShowQuestionWithAnswerBinding
import com.example.lommaquiz.databinding.ActivityShowQuestionsBinding
import java.io.IOException

class showQuestionWithAnswer2 : AppCompatActivity() {
    private lateinit var Question : String
    var QuestionInt = 0
    private lateinit var binding: ActivityShowQuestionWithAnswer2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    //    setContentView(R.layout.activity_show_question_with_answer)

        binding = ActivityShowQuestionWithAnswer2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        var questionString = "Fråga "
        Question = intent.getStringExtra("Question").toString()
        questionString = questionString + Question
        binding.questionNbrTV2.text = questionString
        QuestionInt = Question.toInt()


        binding.answer1ShowResultBtn2.setBackgroundColor(Color.WHITE)
        binding.answer2ShowResultBtn2.setBackgroundColor(Color.WHITE)
        binding.answer3ShowResultBtn2.setBackgroundColor(Color.WHITE)


        var testa = findViewById<Button>(R.id.answer1ShowResultBtn)

        binding.questionShowResultTV2.text = quizQuestion[QuestionInt-1]
        binding.answer1ShowResultTV2.text = quizAnswer1[QuestionInt-1]
        binding.answer2ShowResultTV2.text = quizAnswer2[QuestionInt-1]
        binding.answer3ShowResultTV2.text = quizAnswer3[QuestionInt-1]

            when (quizCorrectAnswer[QuestionInt-1]) {
                "1" -> {
                    binding.answer1ShowResultTV2.setBackgroundColor(Color.GREEN)
                }
                "2" -> {
                    binding.answer2ShowResultTV2.setBackgroundColor(Color.GREEN)
                }
                "3" -> {
                    binding.answer3ShowResultTV2.setBackgroundColor(Color.GREEN)
                }
                else -> {

                }
            }


            if (quizCorrectAnswer[QuestionInt-1] != givenAnswer[QuestionInt-1]) {
                when (givenAnswer[QuestionInt-1]) {
                    "1" -> {
                        binding.answer1ShowResultBtn2.setBackgroundColor(Color.RED)
                    }
                    "2" -> {
                        binding.answer2ShowResultBtn2.setBackgroundColor(Color.RED)
                    }
                    "3" -> {
                        binding.answer3ShowResultBtn2.setBackgroundColor(Color.RED)
                    }
                        else -> {

                        }
                    }
            }

        if (quizCorrectAnswer[QuestionInt-1] == givenAnswer[QuestionInt-1]) {
            when (givenAnswer[QuestionInt-1]) {
                "1" -> {
                    binding.answer1ShowResultBtn2.setBackgroundColor(Color.GREEN)
                }
                "2" -> {
                    binding.answer2ShowResultBtn2.setBackgroundColor(Color.GREEN)
                }
                "3" -> {
                    binding.answer3ShowResultBtn2.setBackgroundColor(Color.GREEN)
                }
                else -> {

                }
            }
        }

        if (quizURLString[QuestionInt-1] == "")
        {
            binding.listenUrlBtn22.visibility = View.INVISIBLE
        }
        else
        {
            binding.listenUrlBtn22.visibility = View.VISIBLE
        }

        binding.listenUrlBtn22.setOnClickListener {
            //       playUrl("https://firebasestorage.googleapis.com/v0/b/fire1-95766.appspot.com/o/framling_tal.mp3?alt=media&token=b2f813f1-bae6-44d6-aba5-b2e5eb5dcff1")
            playUrl(quizURLString[QuestionInt-1])
            binding.listenUrlBtn22.setBackgroundColor(Color.DKGRAY)
        }

    }

    fun playUrl(urlIn : String) {

//        mediaPlayer.setDataSource("https://firebasestorage.googleapis.com/v0/b/fire1-95766.appspot.com/o/framling_tal.mp3?alt=media&token=b2f813f1-bae6-44d6-aba5-b2e5eb5dcff1")

        val mediaPlayer = MediaPlayer()
        mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build())
        try
        {
            mediaPlayer.setDataSource(urlIn)
            mediaPlayer.prepare()
            mediaPlayer.start()
        } catch (e: IOException)
        {
            e.printStackTrace()
        }


        Log.v(ContentValues.TAG,"Music is streaming")
    }
}