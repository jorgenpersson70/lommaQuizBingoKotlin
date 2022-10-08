package com.example.lommaquiz

import android.R
import android.content.ContentValues
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lommaquiz.databinding.ActivityShowQuestionsBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.net.URL


/*
var quizAnswer1 = ArrayList<String>()
var quizAnswer2 = ArrayList<String>()
var quizAnswer3 = ArrayList<String>()
var quizCorrectAnswer = ArrayList<String>()
var quizQuestion = ArrayList<String>()
var quizURLString = ArrayList<String>()*/

var quizAnswer1 : ArrayList<String> = ArrayList(mutableListOf("","","","","","","","","","","",""))
var quizAnswer2 : ArrayList<String> = ArrayList(mutableListOf("","","","","","","","","","","",""))
var quizAnswer3 : ArrayList<String> = ArrayList(mutableListOf("","","","","","","","","","","",""))
var quizCorrectAnswer : ArrayList<String> = ArrayList(mutableListOf("","","","","","","","","","","",""))
var quizQuestion : ArrayList<String> = ArrayList(mutableListOf("","","","","","","","","","","",""))
var quizURLString : ArrayList<String> = ArrayList(mutableListOf("","","","","","","","","","","",""))
var givenAnswer : ArrayList<String> = ArrayList(mutableListOf("","","","","","","","","","","","",""))
var questionFound: ArrayList<Boolean> = ArrayList(mutableListOf(false,false,false,false,false,false,false,false,false,false,false,false,false))

class showQuestions : AppCompatActivity() {
    private lateinit var QuizName : String
    private lateinit var database: DatabaseReference

    private lateinit var binding: ActivityShowQuestionsBinding
    var questionStringText = ""
    var questionStringNumber = ""
var urlString = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_show_questions)

        binding = ActivityShowQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Firebase.database("https://fire1-95766-default-rtdb.europe-west1.firebasedatabase.app").reference


        QuizName = intent.getStringExtra("QuizName").toString()
        urlString = intent.getStringExtra("URL").toString()

     //   var test = findViewById<TextView>(R.id.showChosenQuizTV)

        if ("https" in urlString) {

        // det går från firestore och unsplash men inte isstock

            Picasso.get().load("https://images.unsplash.com/photo-1653162266956-2793b89d6a87?crop=entropy&cs=tinysrgb&fm=jpg&ixlib=rb-1.2.1&q=80&raw_url=true&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687").into(binding.showQuestionImage)
        }
        else {
            val storageRef = FirebaseStorage.getInstance().reference.child(urlString)

            val localfile = File.createTempFile("tempImage", "jpg")

            storageRef.getFile(localfile).addOnSuccessListener {

                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)

                binding.showQuestionImage.setImageBitmap(bitmap)
            }.addOnFailureListener {
                Toast.makeText(this, "Failed to get image", Toast.LENGTH_SHORT).show()
            }
        }

        readQuestions2()



        binding.listenUrlBtn.setOnClickListener {
     //       playUrl("https://firebasestorage.googleapis.com/v0/b/fire1-95766.appspot.com/o/framling_tal.mp3?alt=media&token=b2f813f1-bae6-44d6-aba5-b2e5eb5dcff1")
            playUrl(quizURLString[showMarker-1])
            binding.listenUrlBtn.setBackgroundColor(Color.DKGRAY)
        }

        binding.answerBtn1.setOnClickListener {
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
            binding.answerBtn1.setBackgroundColor(Color.DKGRAY)

            countdownSecond()
     //       finish()
        }

        binding.answerBtn2.setOnClickListener {
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

            binding.answerBtn2.setBackgroundColor(Color.DKGRAY)

            countdownSecond()

  //          finish()

        }

        binding.answerBtn3.setOnClickListener {
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

            binding.answerBtn3.setBackgroundColor(Color.DKGRAY)

            countdownSecond()

  //          finish()

        }

        binding.testBtn.setOnClickListener {
            val intent = Intent(this, showResult::class.java)
            startActivity(intent)
        }

     //   readMapCoordinates()
    }

    fun LoadImageFromWebOperations(url: String?): Drawable? {
        return try {
            val `is`: InputStream = URL(url).getContent() as InputStream
            Drawable.createFromStream(`is`, "src name")
        } catch (e: Exception) {
            null
        }
    }

    fun readQuestions(){
        when (showMarker) {
            1 -> {
                questionStringNumber = "Fråga 1"
                questionStringText = "Fråga A"
            }
            2 -> {
                questionStringNumber = "Fråga 2"
                questionStringText = "Fråga B"
            }
            3 -> {
                questionStringNumber = "Fråga 3"
                questionStringText = "Fråga C"
            }
            4 -> {
                questionStringNumber = "Fråga 4"
                questionStringText = "Fråga D"
            }
            5 -> {
                questionStringNumber = "Fråga 5"
                questionStringText = "Fråga E"
            }
            6 -> {
                questionStringNumber = "Fråga 6"
                questionStringText = "Fråga F"
            }
            7 -> {
                questionStringNumber = "Fråga 7"
                questionStringText = "Fråga G"
            }
            8 -> {
                questionStringNumber = "Fråga 8"
                questionStringText = "Fråga H"
            }
            9 -> {
                questionStringNumber = "Fråga 9"
                questionStringText = "Fråga I"
            }
            10 -> {
                questionStringNumber = "Fråga 10"
                questionStringText = "Fråga J"
            }
            11 -> {
                questionStringNumber = "Fråga 11"
                questionStringText = "Fråga K"
            }
            12 -> {
                questionStringNumber = "Fråga 12"
                questionStringText = "Fråga L"
            }
            else -> {
                // testar
                questionStringNumber = "Fråga 12"
                questionStringText = "Fråga L"
            }
        }
 //       test.text = questionStringNumber
        binding.showChosenQuizTV.text = questionStringNumber

        readFromFirebase += 1
        //     database.child("QuizWalks").child("QuizNames").child(QuizName).child("Fråga A").get().addOnSuccessListener {
        database.child("QuizWalks").child("QuizNames").child(QuizName).child(questionStringText).get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")

            var i = 0
            for (snapchild in it.children) {

                //   var tempshop = snapchild.getValue<question>()!!
                //         var tempshop = DataSnapshot<question>

                var tempshop = it.getValue<question>()

                var questionNumber = 0

                when (it.key) {
                    "Fråga A" -> {
                        questionNumber = 0
                    }
                    "Fråga B" -> {
                        questionNumber = 1
                    }
                    "Fråga C" -> {
                        questionNumber = 2
                    }
                    "Fråga D" -> {
                        questionNumber = 3
                    }
                    "Fråga E" -> {
                        questionNumber = 4
                    }
                    "Fråga F" -> {
                        questionNumber = 5
                    }
                    "Fråga G" -> {
                        questionNumber = 6
                    }
                    "Fråga H" -> {
                        questionNumber = 7
                    }
                    "Fråga I" -> {
                        questionNumber = 8
                    }
                    "Fråga J" -> {
                        questionNumber = 9
                    }
                    "Fråga K" -> {
                        questionNumber = 10
                    }
                    "Fråga L" -> {
                        questionNumber = 11
                    }
                    else -> {
                        // testar
                        questionNumber = 11

                    }
                }

                //        if (it.key == "Fråga A")
                //      {
                if (snapchild.key == "Answer 1") {
                    Log.i("MIN", "Answer 1 " + snapchild.value.toString())
                    quizAnswer1[questionNumber] = snapchild.getValue().toString()
                    binding.answer1TV.text = quizAnswer1[questionNumber]
                }
                if (snapchild.key == "Answer 2") {
                    Log.i("MIN", "Answer 2 " + snapchild.value.toString())
                    quizAnswer2[questionNumber] = snapchild.getValue().toString()
                    binding.answer2TV.text = quizAnswer2[questionNumber]
                }
                if (snapchild.key == "Answer 3") {
                    Log.i("MIN", "Answer 3 " + snapchild.value.toString())
                    quizAnswer3[questionNumber] = snapchild.getValue().toString()
                    binding.answer3TV.text = quizAnswer3[questionNumber]
                }
                if (snapchild.key == "Correct Answer") {
                    Log.i("MIN", "Correct Answer " + snapchild.value.toString())
                    quizCorrectAnswer[questionNumber] = snapchild.getValue().toString()
                }
                if (snapchild.key == "Fråga") {
                    Log.i("MIN", "Question " + snapchild.value.toString())
                    quizQuestion[questionNumber] = snapchild.getValue().toString()
                    binding.questionTV.text = quizQuestion[questionNumber].toString()
                }
                if (snapchild.key == "URLString") {
                    Log.i("MIN", "URLString " + snapchild.value.toString())
                    quizURLString[questionNumber] = snapchild.getValue().toString()
                    if (quizURLString[showMarker-1] == "")
                    {
                        binding.listenUrlBtn.visibility = View.INVISIBLE
                    }
                    else
                    {
                        if ((".jpg" in quizURLString[showMarker-1]) || (".jpeg" in quizURLString[showMarker-1])) {
                            binding.listenUrlBtn.visibility = View.INVISIBLE
                        }
                        else{
                            binding.listenUrlBtn.visibility = View.VISIBLE
                        }
                    }
                }
            }

        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }

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
        binding.showChosenQuizTV.text = questionStringNumber

    //    for (i in 0..11){
            binding.answer1TV.text = quizAnswer1[showMarker-1]
            binding.answer2TV.text = quizAnswer2[showMarker-1]
            binding.answer3TV.text = quizAnswer3[showMarker-1]
            binding.questionTV.text = quizQuestion[showMarker-1].toString()

        if (quizURLString[showMarker-1] == "")
        {
            binding.listenUrlBtn.visibility = View.INVISIBLE
        }
        else {
            if ((".jpg" in quizURLString[showMarker - 1]) || (".jpeg" in quizURLString[showMarker - 1])) {
                binding.listenUrlBtn.visibility = View.INVISIBLE
            } else {
                binding.listenUrlBtn.visibility = View.VISIBLE
            }
        }

    /*        if (quizURLString[showMarker-1] == "")
            {
                binding.listenUrlBtn.visibility = View.INVISIBLE
            }
            else
            {
                binding.listenUrlBtn.visibility = View.VISIBLE
            }*/
   //     }
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