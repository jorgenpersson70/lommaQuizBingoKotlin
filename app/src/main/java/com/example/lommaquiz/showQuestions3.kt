package com.example.lommaquiz

import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import com.example.lommaquiz.databinding.ActivityShowQuestions2Binding
import com.example.lommaquiz.databinding.ActivityShowQuestions3Binding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.io.IOException

class showQuestions3 : AppCompatActivity() {
    private lateinit var QuizName : String
    private lateinit var database: DatabaseReference

    private lateinit var binding: ActivityShowQuestions3Binding
    var questionStringText = ""
    var questionStringNumber = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
   //     setContentView(R.layout.activity_show_questions)

        binding = ActivityShowQuestions3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Firebase.database("https://fire1-95766-default-rtdb.europe-west1.firebasedatabase.app").reference


        QuizName = intent.getStringExtra("QuizName").toString()

        readQuestions2()

        binding.listenUrlBtn23.setOnClickListener {
            //       playUrl("https://firebasestorage.googleapis.com/v0/b/fire1-95766.appspot.com/o/framling_tal.mp3?alt=media&token=b2f813f1-bae6-44d6-aba5-b2e5eb5dcff1")
            playUrl(quizURLString[showMarker-1])
            binding.listenUrlBtn23.setBackgroundColor(Color.DKGRAY)
        }

        binding.answerBtn123.setOnClickListener {
            givenAnswer[showMarker-1] = "1"
            questionFound[showMarker] = true
            if (showMarker == 12){
                walkFiniched = true
            }
            else{
                showMarker += 1
                blockShowQuestion = false
                questFound = 0
            }
            binding.answerBtn123.setBackgroundColor(Color.DKGRAY)

            countdownSecond()
            //       finish()
        }

        binding.answerBtn223.setOnClickListener {
            givenAnswer[showMarker-1] = "2"
            questionFound[showMarker] = true
            if (showMarker == 12){
                walkFiniched = true
            }
            else{
                showMarker += 1
                blockShowQuestion = false
                questFound = 0
            }

            binding.answerBtn223.setBackgroundColor(Color.DKGRAY)

            countdownSecond()

            //          finish()

        }

        binding.answerBtn323.setOnClickListener {
            givenAnswer[showMarker-1] = "3"
            questionFound[showMarker] = true
            if (showMarker == 12){
                walkFiniched = true
            }
            else{
                showMarker += 1
                blockShowQuestion = false
                questFound = 0
            }

            binding.answerBtn323.setBackgroundColor(Color.DKGRAY)

            countdownSecond()

            //          finish()

        }

        /*     binding.testBtn2.setOnClickListener {
                 val intent = Intent(this, showResult::class.java)
                 startActivity(intent)
             }*/

        //   readMapCoordinates()
    }

    fun readQuestions2(){
        when (showMarker) {
            1 -> {
                questionStringNumber = "Fråga 1"
            }
            2 -> {
                questionStringNumber = "Fråga 2"
            }
            3 -> {
                questionStringNumber = "Fråga 3"
            }
            4 -> {
                questionStringNumber = "Fråga 4"
            }
            5 -> {
                questionStringNumber = "Fråga 5"
            }
            6 -> {
                questionStringNumber = "Fråga 6"
            }
            7 -> {
                questionStringNumber = "Fråga 7"
            }
            8 -> {
                questionStringNumber = "Fråga 8"
            }
            9 -> {
                questionStringNumber = "Fråga 9"
            }
            10 -> {
                questionStringNumber = "Fråga 10"
            }
            11 -> {
                questionStringNumber = "Fråga 11"
            }
            12 -> {
                questionStringNumber = "Fråga 12"
            }
            else -> {
                // testar
                questionStringNumber = "Fråga 12"
            }
        }
        //       test.text = questionStringNumber
        binding.showChosenQuizTV23.text = questionStringNumber

        //  for (i in 0..11){
        binding.answer1TV23.text = quizAnswer1[showMarker-1]
        binding.answer2TV23.text = quizAnswer2[showMarker-1]
        binding.answer3TV23.text = quizAnswer3[showMarker-1]
        binding.questionTV2123.text = quizQuestion[showMarker-1].toString()

        if (quizURLString[showMarker-1] == "")
        {
            binding.listenUrlBtn23.visibility = View.INVISIBLE
        }
        else {
            if ((".jpg" in quizURLString[showMarker - 1]) || (".jpeg" in quizURLString[showMarker - 1])) {
                binding.listenUrlBtn23.visibility = View.INVISIBLE
            } else {
                binding.listenUrlBtn23.visibility = View.VISIBLE
            }
        }

   /*     if (quizURLString[showMarker-1] == "")
        {
            binding.listenUrlBtn23.visibility = View.INVISIBLE
        }
        else
        {
            binding.listenUrlBtn23.visibility = View.VISIBLE
        }*/
        //  }
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

    // disable back here, otherwise we get into problems
    override fun onBackPressed() {
        /*       if (shouldAllowBack()) {
                   super.onBackPressed()*/
    }

    fun countdownSecond() {
        object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }
//Om vi svarar på fråga 1 så poppar fråga 2 upp före vi gör finish på denna om de har samma koordinater
            override fun onFinish() {
                // testar
                blockCountDown = false

                finish()
            }
        }.start()
    }


    fun showResultFunction(){

        val intent = Intent(this, showResult::class.java)
        //
        // intent.putExtra("walk", com.example.lommaquiz.walkName)
        // intent.putExtra("question", )
        startActivity(intent)


    }

    fun readMapCoordinates() {
        readFromFirebase += 1
        var count = 0
        //     for (i in 1..12) {
        var letter: String = "A"

        //    Log.i("MIN", "mapname " + mapNames[0])

        for (i in 0..11) {

            if (i == 0) {
                letter = "A"
            }

            if (i == 1) {
                letter = "B"
            }

            if (i == 2) {
                letter = "C"
            }

            if (i == 3) {
                letter = "D"
            }

            if (i == 4) {
                letter = "E"
            }

            if (i == 5) {
                letter = "F"
            }

            if (i == 6) {
                letter = "G"
            }

            if (i == 7) {
                letter = "H"
            }

            if (i == 8) {
                letter = "I"
            }

            if (i == 9) {
                letter = "J"
            }

            if (i == 10) {
                letter = "K"
            }

            if (i == 11) {
                letter = "L"
            }

            //    database.child("QuizWalks").child("maps").child(walkName).child(letter).get()
            //            .addOnSuccessListener {

            database.child("QuizWalks").child("maps").child("Walk5").child(letter).get()
                .addOnSuccessListener {


                    val tempCoordinatelist = mutableListOf<mapCoordinates>()

                    for (snapchild in it.children) {

                        var tempshop = snapchild.getValue()!!

                        if (snapchild.key == "posLatitude") {

                            coordinatesLatitude[i] = tempshop.toString().toFloat()

                            Log.i("MIN", "LAT " + tempshop.toString())
                        }

                        if (snapchild.key == "posLongitude") {

                            coordinatesLongitude[i] = tempshop.toString().toFloat()

                            Log.i("MIN", "LONG " + tempshop.toString())
                        }

                        count += 1

                    }

                }.addOnFailureListener {
                    Log.e("firebase", "Error getting data", it)
                }

        }
    }
}