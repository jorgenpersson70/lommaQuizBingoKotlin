package com.example.lommaquiz

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lommaquiz.databinding.ActivityListOfQuizesBinding
import com.example.lommaquiz.databinding.ActivityListSpecialWalkBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

var finichListSpecialWalk = false
var WalkList = ArrayList<String>()
var countWalks = 0
var blockTimerListWalk = false
var oldselectedWalk = ""
var CatchChosenWalkWhenBack = ""
var havePressedSelect = false

class ListSpecialWalk : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    //  private lateinit var database2: DatabaseReference
    private lateinit var binding: ActivityListSpecialWalkBinding


    var minAdapter = SpecialWalkAdaptor(this)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_list_special_walk)
        binding = ActivityListSpecialWalkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Firebase.database("https://fire1-95766-default-rtdb.europe-west1.firebasedatabase.app").reference
        // var FromWhere = intent.getStringExtra("from").toString()
        havePressedSelect = false

        loadWalks()

        binding.seeWalkBtn.setOnClickListener{
            val intent = Intent(this, testActivity::class.java)

         /*   if (selectedWalk == "") {
                intent.putExtra("walk", selectedWalk)
            }else{*/
  //              connectToWalk = selectedWalk
                    readMapCoordinates()
                    intent.putExtra("walk", selectedWalk)
         //   }

            startActivity(intent)
        }

        binding.selectWalkBtn.setOnClickListener{
            havePressedSelect = true
            if (selectedWalk == ""){
            //    CatchChosenWalkWhenBack = WalkList[0]
            //    selectedWalk = WalkList[0]
                CatchChosenWalkWhenBack = ""
            }else {
                // CatchChosenWalkWhenBack is found in createbingo
                CatchChosenWalkWhenBack = selectedWalk
                walkName = selectedWalk
                readMapCoordinates()
            }

        /*    if (oldselectedWalk != selectedWalk){
                oldselectedWalk = selectedWalk

                readMapCoordinates()
      */

            finish()
        }

        binding.goBackListSpecialWalkBtn.setOnClickListener {
            finish()
        }

        var myRecyclerView = findViewById<RecyclerView>(R.id.specialWalkRV)

        myRecyclerView.layoutManager = LinearLayoutManager(this)
        myRecyclerView.adapter = minAdapter

   //     loadWalks()
     //   countdownSecond()
    }

   /* override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)

    }*/

    fun resetAdapterState() {
     //  val myAdapter = recyclerView.adapter
   //     recyclerView.adapter = myAdapter

        minAdapter = SpecialWalkAdaptor(this)

  //      myRecyclerView.setAdapter(myAdapter)


    //    myRecyclerView.adapter = minAdapter
    }

    fun loadWalks() {
        var i = 0
        readFromFirebase += 1

        database.child("QuizWalks").child("maps").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            //  for (snapchild in it.children) {

            WalkList.clear()
            countWalks = 0
            for (snapchild in it.children) {
                var tempshop = snapchild.getValue()!!
                WalkList.add(snapchild.key.toString())

                selectedWalk = snapchild.key.toString()
                readMapCoordinates()

                minAdapter.names.add(snapchild.key.toString())
                minAdapter.marked.add(false)

                Log.i("MIN", "QuiznameID " + snapchild.key.toString())
                //   mapNames.add("Walk1")
                i += 1
                countWalks += 1
            }
            //     quizNameListID.add("testa")
            Log.i("MIN", "count walks"+ countQuizWalks2.toString())
  //          finichListSpecialWalk = true

            minAdapter.notifyDataSetChanged()

        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }
    }





    fun countdownSecond() {
        object : CountDownTimer(1000, 500) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {


   /*             if (oldselectedWalk != selectedWalk){
                    oldselectedWalk = selectedWalk

                    readMapCoordinates()
                }*/

                countdownSecond()

            }
        }.start()
    }



    fun readMapCoordinates() {
        var count = 0

        readFromFirebase += 1

        coordinatesRead = false

        coordinatesReadCount = 0
        var letter: String = "A"

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


                database.child("QuizWalks").child("maps").child(selectedWalk).child(letter).get()
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
       /*                 coordinatesReadCount += 1
                        if (letter == "L") {
                            coordinatesRead = true
                        }*/

/*                        val intent = Intent(this, testActivity::class.java)
                        intent.putExtra("walk", selectedWalk)

                        startActivity(intent)
                        Log.e("firebase", "Error getting data")*/

                    }.addOnFailureListener {
                        Log.e("firebase", "Error getting data", it)
                    }

        }
    }


}

