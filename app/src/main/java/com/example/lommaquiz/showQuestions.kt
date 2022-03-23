package com.example.lommaquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.lommaquiz.databinding.ActivityMainBinding
import com.example.lommaquiz.databinding.ActivityShowQuestionsBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

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
var givenAnswer : ArrayList<String> = ArrayList(mutableListOf("","","","","","","","","","","",""))

class showQuestions : AppCompatActivity() {
    private lateinit var walkName : String
    private lateinit var database: DatabaseReference

    private lateinit var binding: ActivityShowQuestionsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_questions)

        binding = ActivityShowQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Firebase.database("https://fire1-95766-default-rtdb.europe-west1.firebasedatabase.app").reference


        walkName = intent.getStringExtra("walk").toString()
        var test = findViewById<TextView>(R.id.showChosenQuizTV)

        test.text = walkName

 //       database.child("QuizWalks").child("QuizNames").child(walkName).child("Fråga A").child("Answer 1").get().addOnSuccessListener {
  //          Log.i("firebase", "Got value ${it.value}")

            database.child("QuizWalks").child("QuizNames").child(walkName).child("Fråga A").get().addOnSuccessListener {
                Log.i("firebase", "Got value ${it.value}")

            var i = 0
            for (snapchild in it.children) {

             //   var tempshop = snapchild.getValue<question>()!!
       //         var tempshop = DataSnapshot<question>

                var tempshop = it.getValue<question>()

                if (it.key == "Fråga A")
                {
                    if (snapchild.key == "Answer 1") {
                        Log.i("MIN", "Answer 1 " + snapchild.value.toString())
                    //    quizAnswer1.add(snapchild.value.toString())

                        quizAnswer1[0] = snapchild.getValue().toString()
                    //    quizAnswer1.add(snapchild.getValue().toString())
                        binding.answer1TV.text = quizAnswer1[0]
                    }
                    if (snapchild.key == "Answer 2") {
                        Log.i("MIN", "Answer 2 " + snapchild.value.toString())
                    //    quizAnswer2.add(snapchild.value.toString())
                        quizAnswer2[0] = snapchild.getValue().toString()
                        binding.answer2TV.text = quizAnswer2[0]
                    }
                    if (snapchild.key == "Answer 3") {
                        Log.i("MIN", "Answer 3 " + snapchild.value.toString())
                      //  quizAnswer3.add(snapchild.value.toString())
                        quizAnswer3[0] = snapchild.getValue().toString()
                        binding.answer3TV.text = quizAnswer3[0]
                    }
                    if (snapchild.key == "Correct Answer") {
                        Log.i("MIN", "Correct Answer " + snapchild.value.toString())
                        quizCorrectAnswer[0] = snapchild.getValue().toString()
                      //  quizCorrectAnswer.add(snapchild.value.toString())
                    }
                    if (snapchild.key == "Fråga") {
                        Log.i("MIN", "Question " + snapchild.value.toString())
                        quizQuestion[0] = snapchild.getValue().toString()
                    //    quizQuestion.add(snapchild.value.toString())
                        binding.questionTV.text = quizQuestion[0].toString()
                    }
                    if (snapchild.key == "URLString") {
                        Log.i("MIN", "URLString " + snapchild.value.toString())
                        quizURLString[0] = snapchild.getValue().toString()
                     //   quizURLString.add(snapchild.value.toString())
                    }

                }

            }

        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }


        binding.answerBtn1.setOnClickListener {
            givenAnswer[0] = "1"
        }

        binding.answerBtn1.setOnClickListener {
            givenAnswer[0] = "X"
        }

        binding.answerBtn1.setOnClickListener {
            givenAnswer[0] = "2"
        }

     //   readMapCoordinates()
    }

    fun readMapCoordinates() {
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