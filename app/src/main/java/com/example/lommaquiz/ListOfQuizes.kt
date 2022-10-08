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
var countQuizWalks2 = 0

//var QuizPositionSelect = 1000
var finich = false



class ListOfQuizes : AppCompatActivity() {
    private lateinit var database: DatabaseReference
  //  private lateinit var database2: DatabaseReference
    private lateinit var binding: ActivityListOfQuizesBinding

    var minAdapter = MyAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_quizes)

      //  intent.putExtra("from", "choseSeeWalks")
        var FromWhere = intent.getStringExtra("from").toString()

        // want to change
        if ( FromWhere == "choseSeeWalks"){
            minAdapter.change(true)
        }

        // want to start walk
        if ( FromWhere == "MainActivity"){
            minAdapter.change(false)
        }


        binding = ActivityListOfQuizesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Firebase.database("https://fire1-95766-default-rtdb.europe-west1.firebasedatabase.app").reference
    //    database2 = Firebase.database("https://fire1-95766-default-rtdb.europe-west1.firebasedatabase.app").reference



        var myRecyclerView = findViewById<RecyclerView>(R.id.myRecycler)

            myRecyclerView.layoutManager = LinearLayoutManager(this)
            myRecyclerView.adapter = minAdapter

      //  myRecyclerView.adapter.ch

        loadQuizNames()
        countdownSecond()

    }

    override fun onBackPressed() {
        super.onBackPressed()
     //   finich = false
    }
    fun loadQuizNames() {
        var i = 0
        readFromFirebase += 1

        database.child("QuizWalks").child("QuizNameList").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            //  for (snapchild in it.children) {

            quizNameListID.clear()
            countQuizWalks2 = 0
            for (snapchild in it.children) {

                var tempshop = snapchild.getValue()!!

                quizNameListID.add(snapchild.key.toString())
                Log.i("MIN", "QuiznameID " + snapchild.key.toString())
                //   mapNames.add("Walk1")
                i += 1
                countQuizWalks2 += 1
            }
      //     quizNameListID.add("testa")
            Log.i("MIN", "count walks"+ countQuizWalks2.toString())
            quizNameList.clear()
            quizNameListUser.clear()

            for (m in 0..(countQuizWalks2-1)) {
                database.child("QuizWalks").child("QuizNameList").child(quizNameListID[m].toString()).get()
                    .addOnSuccessListener {
                        Log.i("firebase", "Got value ${it.value}")

                        var i = 0

                        for (snapchild in it.children) {

                            var tempshop = snapchild.getValue()!!

                            if (snapchild.key == "name") {
                                Log.i("MIN", "name " + tempshop.toString())
                                quizNameList.add(tempshop.toString())

                      //          minAdapter.names.add(tempshop.toString())

         //                       minAdapter.notifyDataSetChanged()
                            }
                            if (snapchild.key == "user") {
                                Log.i("MIN", "user " + tempshop.toString())
                                quizNameListUser.add(tempshop.toString())
                                Log.i("MIN", "user " + tempshop.toString())
                            }

                        }
             //           finich = true
                    }
            }
            finich = true


        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)

        }

    }





    fun countdownSecond() {
        object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                if (finich) {
//                    finich = false
                    minAdapter.names.clear()

                    if ((loggedIn != "") || testerLoggedIn){
                        Log.i("MIN", "name hej hej"+ loggedIn.toString())
                        for (m in 0..(countQuizWalks2 - 1)) {
                            //                          Log.i("MIN", "name hej hej"+ quizNameListUser[m].toString())
                            if ((quizNameListUser[m] == loggedIn) || testerLoggedIn) {
                                minAdapter.names.add(quizNameList[m])
                                if (specialWalk){
                                    walkName = quizNameList[m]
                                }
                            }
                        }

                        //                      checkIfSpecialWalk()
                    }
                    for (m in 0..(countQuizWalks2 - 1)) {
                        if (quizNameListUser[m] == "everyone") {
                            minAdapter.names.add(quizNameList[m])
                        }

                    }
                    minAdapter.notifyDataSetChanged()
                }

            }
        }.start()
    }

    fun checkIfSpecialWalk(){
        // loggedIn
        var testa = "malin"
        readFromFirebase += 1

  //      database.child("QuizWalks").child("maps").child(walkName.toString()).get()
        database.child("QuizWalks").child("maps").child(testa).get()
            .addOnSuccessListener {

                val tempCoordinatelist = mutableListOf<mapCoordinates>()

               // If special walk is there, then leave it up to showMap to read coordinates
                walkName = testa
 /*               for (snapchild in it.children) {
                    Log.i("MIN", "special")
                }*/

            }
    }
}