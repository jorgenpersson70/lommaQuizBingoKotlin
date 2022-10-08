package com.example.lommaquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.lommaquiz.databinding.ActivityChoseSeeWalksBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

var loggedIn2 = false

class choseSeeWalks : AppCompatActivity() {
    private lateinit var binding: ActivityChoseSeeWalksBinding
    var putExtraString = ""
    private lateinit var database: DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chose_see_walks)


        database = Firebase.database("https://fire1-95766-default-rtdb.europe-west1.firebasedatabase.app").reference

        binding = ActivityChoseSeeWalksBinding.inflate(layoutInflater)

        setContentView(binding.root)




        if (specialWalk) {
            binding.walk2Btn.visibility = View.INVISIBLE
            binding.walk3Btn.visibility = View.INVISIBLE
            binding.walk4Btn.visibility = View.INVISIBLE
            binding.walk5Btn.visibility = View.INVISIBLE
        } else {
            binding.walk2Btn.visibility = View.VISIBLE
            binding.walk3Btn.visibility = View.VISIBLE
            binding.walk4Btn.visibility = View.VISIBLE
            binding.walk5Btn.visibility = View.VISIBLE
        }

        binding.walk1Btn.setOnClickListener {

            // obs, detta måste vara fel
            if (specialWalk) {
                putExtraString = "malin"
            }
            else{
                putExtraString = "Walk1"
            }


            readMapCoordinates()

            startActivityNew()

        }

        binding.walk2Btn.setOnClickListener {

            putExtraString = "Walk2"
            readMapCoordinates()

            startActivityNew()
        }

        binding.walk3Btn.setOnClickListener {

            putExtraString = "Walk3"
            readMapCoordinates()

            startActivityNew()
        }

        binding.walk4Btn.setOnClickListener {

            putExtraString = "Walk4"
            readMapCoordinates()

            startActivityNew()
        }

        binding.walk5Btn.setOnClickListener {

            putExtraString = "Walk5"
            readMapCoordinates()

            startActivityNew()
        }

        binding.lookForWalkBtn.setOnClickListener {

            val intent = Intent(this, ListSpecialWalk::class.java)
            // intent.putExtra("walk", putExtraString)

            startActivity(intent)
        }

      //  binding.createQuizBtn.visibility = View.VISIBLE


    //    if (masterLoggedIn || authPassedForUser){

        // isRundacLoggedIn()


        val getLogIn = LoginAuthFirebase()
        loggedIn2 = getLogIn.isRundacLoggedIn()

        binding.changeQuizBtn.visibility = View.INVISIBLE
        binding.createWalkBtn.visibility = View.INVISIBLE
        binding.createQuizBtn.visibility = View.INVISIBLE




        binding.changeQuizBtn.visibility = View.INVISIBLE
        // Only master can change in every quiz
        if (masterLoggedIn){
            if (!loggedIn2) {
                binding.changeQuizBtn.visibility = View.VISIBLE
            }
        }

        if (masterLoggedIn || writePermission){
            binding.createWalkBtn.visibility = View.VISIBLE
            binding.createQuizBtn.visibility = View.VISIBLE
            if (loggedIn2){
                binding.createQuizBtn.visibility = View.INVISIBLE
            }
        }
        else{
            binding.createWalkBtn.visibility = View.INVISIBLE
            binding.createQuizBtn.visibility = View.INVISIBLE
        }


        binding.createWalkBtn.setOnClickListener {

            val intent = Intent(this, CreateWalk::class.java)
           // intent.putExtra("walk", putExtraString)

            startActivity(intent)
        }

        binding.createQuizBtn.setOnClickListener {

            val intent = Intent(this, CreateQuiz::class.java)
            // intent.putExtra("walk", putExtraString)
            intent.putExtra("from", "choseSeeWalks")

            startActivity(intent)
        }

        binding.changeQuizBtn.setOnClickListener {
            val intent = Intent(this, ListOfQuizes::class.java)
            intent.putExtra("from", "choseSeeWalks")
            startActivity(intent)
        }


    }

    override fun onRestart() {
        super.onRestart()
        if (masterLoggedIn || writePermission){
            binding.createWalkBtn.visibility = View.VISIBLE
            binding.createQuizBtn.visibility = View.VISIBLE
            if (loggedIn2){
                binding.createQuizBtn.visibility = View.INVISIBLE
            }
        }
        else{
            binding.createWalkBtn.visibility = View.INVISIBLE
            binding.createQuizBtn.visibility = View.INVISIBLE
        }
    }


    fun startActivityNew() {
        val intent = Intent(this, testActivity::class.java)
        intent.putExtra("walk", putExtraString)

        startActivity(intent)
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



                database.child("QuizWalks").child("maps").child(putExtraString).child(letter).get()
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
                        coordinatesReadCount += 1
                        if (letter == "L") {
                            coordinatesRead = true
                        }
                    }.addOnFailureListener {
                        Log.e("firebase", "Error getting data", it)
                    }
        }
    }

}