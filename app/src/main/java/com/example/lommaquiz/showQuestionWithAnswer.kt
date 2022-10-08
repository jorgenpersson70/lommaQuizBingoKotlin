package com.example.lommaquiz

import android.content.ContentValues
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.lommaquiz.databinding.ActivityMainBinding
import com.example.lommaquiz.databinding.ActivityShowQuestionWithAnswerBinding
import com.example.lommaquiz.databinding.ActivityShowQuestionsBinding
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.io.IOException

class showQuestionWithAnswer : AppCompatActivity() {
    private lateinit var Question : String
    var QuestionInt = 0
    private lateinit var binding: ActivityShowQuestionWithAnswerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    //    setContentView(R.layout.activity_show_question_with_answer)

        binding = ActivityShowQuestionWithAnswerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var questionString = "Fråga "
        Question = intent.getStringExtra("Question").toString()



        questionString = questionString + Question
        binding.questionNbrTV.text = questionString
        QuestionInt = Question.toInt()


        val storageRef = FirebaseStorage.getInstance().reference.child(quizURLString[QuestionInt-1])

        val localfile = File.createTempFile("tempImage", "jpg")

        storageRef.getFile(localfile).addOnSuccessListener {

            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)

            binding.showQuestWithAnswImage.setImageBitmap(bitmap)
        }.addOnFailureListener{
            Toast.makeText(this, "Failed to get image", Toast.LENGTH_SHORT).show()
        }





        binding.answer1ShowResultBtn.setBackgroundColor(Color.WHITE)
        binding.answer2ShowResultBtn.setBackgroundColor(Color.WHITE)
        binding.answer3ShowResultBtn.setBackgroundColor(Color.WHITE)


        var testa = findViewById<Button>(R.id.answer1ShowResultBtn)

        binding.questionShowResultTV.text = quizQuestion[QuestionInt-1]
        binding.answer1ShowResultTV.text = quizAnswer1[QuestionInt-1]
        binding.answer2ShowResultTV.text = quizAnswer2[QuestionInt-1]
        binding.answer3ShowResultTV.text = quizAnswer3[QuestionInt-1]

            when (quizCorrectAnswer[QuestionInt-1]) {
                "1" -> {
                    binding.answer1ShowResultTV.setBackgroundColor(Color.GREEN)
                }
                "2" -> {
                    binding.answer2ShowResultTV.setBackgroundColor(Color.GREEN)
                }
                "3" -> {
                    binding.answer3ShowResultTV.setBackgroundColor(Color.GREEN)
                }
                else -> {

                }
            }


            if (quizCorrectAnswer[QuestionInt-1] != givenAnswer[QuestionInt-1]) {
                when (givenAnswer[QuestionInt-1]) {
                    "1" -> {
                        binding.answer1ShowResultBtn.setBackgroundColor(Color.RED)
                    }
                    "2" -> {
                        binding.answer2ShowResultBtn.setBackgroundColor(Color.RED)
                    }
                    "3" -> {
                        binding.answer3ShowResultBtn.setBackgroundColor(Color.RED)
                    }
                        else -> {

                        }
                    }
            }

        if (quizCorrectAnswer[QuestionInt-1] == givenAnswer[QuestionInt-1]) {
            when (givenAnswer[QuestionInt-1]) {
                "1" -> {
                    binding.answer1ShowResultBtn.setBackgroundColor(Color.GREEN)
                }
                "2" -> {
                    binding.answer2ShowResultBtn.setBackgroundColor(Color.GREEN)
                }
                "3" -> {
                    binding.answer3ShowResultBtn.setBackgroundColor(Color.GREEN)
                }
                else -> {

                }
            }
        }

    /*    if (quizURLString[QuestionInt-1] == "")
        {
            binding.listenUrlBtn2.visibility = View.INVISIBLE
        }
        else
        {
            binding.listenUrlBtn2.visibility = View.VISIBLE
        }*/

        if (quizURLString[QuestionInt-1] == "")
        {
            binding.listenUrlBtn2.visibility = View.INVISIBLE
        }
        else {
            if ((".jpg" in quizURLString[showMarker - 1]) || (".jpeg" in quizURLString[showMarker - 1])) {
                binding.listenUrlBtn2.visibility = View.INVISIBLE
            } else {
                binding.listenUrlBtn2.visibility = View.VISIBLE
            }
        }

        binding.listenUrlBtn2.setOnClickListener {
            //       playUrl("https://firebasestorage.googleapis.com/v0/b/fire1-95766.appspot.com/o/framling_tal.mp3?alt=media&token=b2f813f1-bae6-44d6-aba5-b2e5eb5dcff1")
            playUrl(quizURLString[QuestionInt-1])
            binding.listenUrlBtn2.setBackgroundColor(Color.DKGRAY)
        }

        // showQuestWithAnswImage


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