package com.example.lommaquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lommaquiz.databinding.ActivityListOfQuizesBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

var quizNameListID = ArrayList<String>()
var quizNameList = ArrayList<String>()
var quizNameListUser = ArrayList<String>()
var countQuizWalks = 0

var QuizPositionSelect = 1000



class ListOfQuizes : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var database2: DatabaseReference
    private lateinit var binding: ActivityListOfQuizesBinding

    var minAdapter = MyAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_quizes)

        binding = ActivityListOfQuizesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Firebase.database("https://fire1-95766-default-rtdb.europe-west1.firebasedatabase.app").reference
        database2 = Firebase.database("https://fire1-95766-default-rtdb.europe-west1.firebasedatabase.app").reference


        var myRecyclerView = findViewById<RecyclerView>(R.id.myRecycler)

            myRecyclerView.layoutManager = LinearLayoutManager(this)
            myRecyclerView.adapter = minAdapter




        loadQuizNames()
        countQuizWalks = 1
  //      minAdapter.names.add("hej")
  //      minAdapter.notifyDataSetChanged()

     /*   testa()*/



    }

    fun loadQuizNames() {
        var i = 0

        //      ref.child("QuizWalks").child("QuizNameList").getData

        //
        database.child("QuizWalks").child("QuizNameList").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            //  for (snapchild in it.children) {

            for (snapchild in it.children) {

                var tempshop = snapchild.getValue()!!

                quizNameListID.add(snapchild.key.toString())
                Log.i("MIN", "QuiznameID " + snapchild.key.toString())
                //   mapNames.add("Walk1")
                i += 1
                countQuizWalks += 1
            }

    //        database.child("QuizWalks").child("QuizNameList").child("-MnRBeG98PiILirwtJKW").get().addOnSuccessListener {
      //                 Log.i("firebase", "Got value ${it.value}")
        //    for (m in 0..(countQuizWalks-1)) {

     //       var hej = findViewById<TextView>(R.id.countQuizTV)
    //        hej.text = countQuizWalks.toString()
       //     hej.text = "vadå ?"
      //      binding.countQuizTV.text = countQuizWalks.toString()

            for (m in 0..(countQuizWalks-2)) {
                database.child("QuizWalks").child("QuizNameList").child(quizNameListID[m].toString()).get()
                    .addOnSuccessListener {
                        Log.i("firebase", "Got value ${it.value}")

                        var i = 0
                        for (snapchild in it.children) {

                            var tempshop = snapchild.getValue()!!

                            if (snapchild.key == "name") {
                                Log.i("MIN", "name " + tempshop.toString())
                                quizNameList.add(tempshop.toString())
                                //  Log.i("MIN", "name " + snapchild.key.toString())
                                minAdapter.names.add(tempshop.toString())
                                //              minAdapter.names.add("svejs")
                                minAdapter.notifyDataSetChanged()
                            }
                            if (snapchild.key == "user") {
                                Log.i("MIN", "user " + tempshop.toString())
                                quizNameListUser.add(tempshop.toString())
                                Log.i("MIN", "user " + tempshop.toString())
                            }
                            //   minAdapter.names.add("testar")

                            //     quizNameList.add(snapchild.key.toString())
                            //       Log.i("MIN", "Quizname " + snapchild.key.toString())
                        }
                    }
            }
   /*                 .addOnFailureListener {
                        Log.e("firebase", "Error getting data", it)
                    }*/


            }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }
     //   minAdapter.notifyDataSetChanged()
    }

    fun testa(){

        database.child("QuizWalks").child("QuizNameList").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            //  for (snapchild in it.children) {

            for (snapchild in it.children) {

                var tempshop = snapchild.getValue()!!

                quizNameListID.add(snapchild.key.toString())
                Log.i("MIN", "QuiznameID " + snapchild.key.toString())
                //   mapNames.add("Walk1")
      //          i += 1
                countQuizWalks += 1
            }

        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }



        for (j in 0..countQuizWalks){

  /*          database2.child("QuizWalks").child("QuizNameList").get().addOnSuccessListener {
                Log.i("firebase", "Got value ${it.value}")
                //  for (snapchild in it.children) {

                for (snapchild in it.children) {

                    var tempshop = snapchild.getValue()!!
                }
            }*/


  //          database.child("QuizWalks").child("QuizNameList").child("-MnRBeG98PiILirwtJKW").get().addOnSuccessListener {
  //              Log.i("firebase", "Got value ${it.value}")
                //  for (snapchild in it.children) {
            database.child("QuizWalks").child("QuizNameList").get().addOnSuccessListener {
                Log.i("firebase", "Got value ${it.value}")

                var i = 0
                for (snapchild in it.children) {

                    var tempshop = snapchild.getValue()!!

                    if (snapchild.key == "name") {
                        Log.i("MIN", "name " + tempshop.toString())
                        quizNameList.add(snapchild.key.toString())
                        Log.i("MIN", "name " + snapchild.key.toString())
                    }
                    if (snapchild.key == "user") {
                        Log.i("MIN", "user " + tempshop.toString())
                        quizNameListUser.add(snapchild.key.toString())
                        Log.i("MIN", "user " + snapchild.key.toString())
                    }

                    //     quizNameList.add(snapchild.key.toString())
                    //       Log.i("MIN", "Quizname " + snapchild.key.toString())
                }
            }
                .addOnFailureListener {
                    Log.e("firebase", "Error getting data", it)
                }

        }
        for (j in 0..countQuizWalks) {
            //    minAdapter.names.add(quizNameList[0].toString())
            //    minAdapter.names.add("hejsan")
            Log.i("MIN", "user " + quizNameList[j].toString())
        }
        minAdapter.notifyDataSetChanged()
    }

    fun countdownSecond() {
        object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                /*
if (QuizPositionSelect != 1000){
               val intent = Intent(this, showQuestions::class.java)
            intent.putExtra("QuizName", "hej")
            startActivity(intent)
}*/
                countdownSecond()
            }
        }.start()
    }
}