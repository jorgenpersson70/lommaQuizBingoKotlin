package com.example.lommaquiz

import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lommaquiz.databinding.ActivityShowBingoChartsBinding
import com.example.lommaquiz.databinding.ActivityShowQuestions3Binding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

var waitToTheLastToShowLooser = false
var whoIsWinner = "0"
var ThreeSecondCountActive = false

var cellsThatAreGrayChart1: ArrayList<Int> = ArrayList(
    mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
    )
) as ArrayList<Int>

var cellsThatAreGrayChart2: ArrayList<Int> = ArrayList(
    mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
    )
) as ArrayList<Int>

var cellsThatAreGrayChart3: ArrayList<Int> = ArrayList(
    mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
    )
) as ArrayList<Int>

var prevNumbers: ArrayList<Int> = ArrayList(
    mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
    )
) as ArrayList<Int>


class ShowBingoCharts : AppCompatActivity() {

    private lateinit var binding: ActivityShowBingoChartsBinding
    private lateinit var database: DatabaseReference

    var minAdapter = BingoGridAdaptor1()
    var minAdapter3 = BingoGridAdaptor2()
    var minAdapter2 = BingoGridAdaptor3()
    var minAdapter4 = BingoAllNbrAdaptor()
    var QuestionNumberString = "0"
    var QuestionNumberInt = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = Firebase.database("https://fire1-95766-default-rtdb.europe-west1.firebasedatabase.app").reference

        // inte här sen väl ?
        bingoNumber2 = 0
        bingoNumber3 = 0

        binding = ActivityShowBingoChartsBinding.inflate(layoutInflater)
        setContentView(binding.root)

       // QuestionNumber = intent.getStringExtra("QuestionNumber")?.toInt() ?: 1
        QuestionNumberString = intent.getStringExtra("QuestionNumber").toString()
        QuestionNumberInt = QuestionNumberString.toInt()
        PutValuesToShow()

        var myRecyclerView = findViewById<RecyclerView>(R.id.nyRV)

        myRecyclerView.layoutManager = GridLayoutManager(this, 5)
        myRecyclerView.adapter = minAdapter

        var myRecyclerView2 = findViewById<RecyclerView>(R.id.nyRV2)

        myRecyclerView2.layoutManager = GridLayoutManager(this, 5)
        myRecyclerView2.adapter = minAdapter2

        var myRecyclerView3 = findViewById<RecyclerView>(R.id.nyRV3)

        myRecyclerView3.layoutManager = GridLayoutManager(this, 5)
        myRecyclerView3.adapter = minAdapter3

        var myRecyclerView4 = findViewById<RecyclerView>(R.id.showAllBingoValuesRV)

        myRecyclerView4.layoutManager = GridLayoutManager(this, 5)
        myRecyclerView4.adapter = minAdapter4


        val testref = database.child("BingoPlayerWinner").child(bingoName).child("TheWinner")



        if (testerLoggedIn){
            binding.testasmackBtn.visibility = View.VISIBLE
        }else{
            binding.testasmackBtn.visibility = View.INVISIBLE
        }

        binding.weHaveAWinnerTV.visibility = View.INVISIBLE
        binding.proofOfWinnerAlertTV.visibility = View.INVISIBLE

        // jaha, vi kommer in här hela tiden, vid 12 positioner !
        testref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //  val value = snapshot.getValue<ShopItem>()
                whoIsWinner = snapshot.getValue<String>().toString()
                if (whoIsWinner != "0") {
 //                   binding.weHaveAWinnerTV.text = whoIsWinner
                    if (whoIsWinner.toString().toInt() == youAreplayer){
                        binding.weHaveAWinnerTV.visibility = View.VISIBLE
                        binding.proofOfWinnerAlertTV.visibility = View.VISIBLE
                        binding.weHaveAWinnerTV.text = "Grattis, du har fått BINGO. Har ni satsat något, du och de andra deltagarna? En fika, kaffe, eller vem som skall diska eller dammsuga."
                        binding.proofOfWinnerAlertTV.text = "OBS! När du trycker NÄSTA så försvinner ditt bevis på att du vann."
                    }else{
                        waitToTheLastToShowLooser = true
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed ", error.toException())
            }
        })

        binding.bingoPosTV.text = showMarker.toString()

        binding.goToNextPosBtn.setOnClickListener{
                questionFound[showMarker] = true
                ThreeSecondCountActive = false
                if (showMarker == 12){
                    walkFiniched = true

                    // två ??
                    BingoPasswordFound = false
                    foundBingoPassword = false
                    takeAwayButtonStartBingo = true
                    takeAwayButtonToChooseRunda = false
                    waitToTheLastToShowLooser = false

                    // This jumps to main
                    val i = Intent(this, MainActivity::class.java)
                    countQuizWalks2 = 0
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                }
                else{
                    showMarker += 1
                    blockShowQuestion = false
                    questFound = 0
                }

                countdownSecond()
        }

        binding.quitBingoBtn.setOnClickListener {
            BingoPasswordFound = false
            foundBingoPassword = false
            walkFiniched = true
            ThreeSecondCountActive = false

            // två ??
            BingoPasswordFound = false
            foundBingoPassword = false
            takeAwayButtonStartBingo = true
            takeAwayButtonToChooseRunda = false
            waitToTheLastToShowLooser = false

            countQuizWalks2 = 0
         //   finish()

            val i = Intent(this, MainActivity::class.java)
// set the new task and clear flags
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        }

        if (debuggHomeTestGrey) {
            binding.testasmackBtn.setOnClickListener {
                smack()
            }
        }else{
            binding.testasmackBtn.visibility = View.INVISIBLE
        }
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onPostResume() {
        super.onPostResume()

        if (showMarker == 12) {
            countdownThreeSeconds()
            ThreeSecondCountActive = true
        }
    }

    // wait a little while before going back
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
        // wait a little while before going back
        fun countdownThreeSeconds() {
            object : CountDownTimer(3000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                }

                override fun onFinish() {

                    if (waitToTheLastToShowLooser) {
                        ThreeSecondCountActive = false
                        binding.weHaveAWinnerTV.visibility = View.VISIBLE
                        binding.proofOfWinnerAlertTV.visibility = View.VISIBLE
                        binding.weHaveAWinnerTV.text = "Deltagare " +whoIsWinner + " har fått BINGO"
                        binding.proofOfWinnerAlertTV.text = "OBS! När du trycker NÄSTA så kan du inte se bingobrickorna igen"
                    }
                    if (ThreeSecondCountActive) {
                        countdownThreeSeconds()
                    }

                }
            }.start()
        }

    fun tempFunc(){
     //   NumbersToShow
    }

    fun PutValuesToShow(){
        var showThreeValues = (QuestionNumberInt-1)*3

        prevNumbers[(QuestionNumberInt-1)*3 + 0] = NumbersToShow[(QuestionNumberInt-1)*3 + 0]
        prevNumbers[(QuestionNumberInt-1)*3 + 1] = NumbersToShow[(QuestionNumberInt-1)*3 + 1]
        // The last call fill only 2, not 3
        if (QuestionNumberInt < 12) {
            prevNumbers[(QuestionNumberInt - 1) * 3 + 2] =
                NumbersToShow[(QuestionNumberInt - 1) * 3 + 2]
        }

        if ((showThreeValues % 5) == 0){
            binding.winBTV.text = NumbersToShow[(QuestionNumberInt-1)*3 + 0].toString()
            binding.winITV.text = NumbersToShow[(QuestionNumberInt-1)*3 + 1].toString()
            binding.winNTV.text = NumbersToShow[(QuestionNumberInt-1)*3 + 2].toString()
            binding.winGTV.text = ""
            binding.winOTV.text = ""
        }

        if ((showThreeValues % 5) == 1){
            binding.winBTV.text = ""
            binding.winITV.text = NumbersToShow[(QuestionNumberInt-1)*3 + 0].toString()
            binding.winNTV.text = NumbersToShow[(QuestionNumberInt-1)*3 + 1].toString()
            binding.winGTV.text = NumbersToShow[(QuestionNumberInt-1)*3 + 2].toString()
            binding.winOTV.text = ""
        }

        if ((showThreeValues % 5) == 2){
            binding.winBTV.text = ""
            binding.winITV.text = ""
            binding.winNTV.text = NumbersToShow[(QuestionNumberInt-1)*3 + 0].toString()
            binding.winGTV.text = NumbersToShow[(QuestionNumberInt-1)*3 + 1].toString()
            binding.winOTV.text = NumbersToShow[(QuestionNumberInt-1)*3 + 2].toString()
        }

        if ((showThreeValues % 5) == 3){
            binding.winBTV.text = ""
            if (QuestionNumberInt < 12) {
                binding.winBTV.text = NumbersToShow[(QuestionNumberInt - 1) * 3 + 2].toString()
            }
            binding.winITV.text = ""
            binding.winNTV.text = ""
            binding.winGTV.text = NumbersToShow[(QuestionNumberInt-1)*3 + 0].toString()
            binding.winOTV.text = NumbersToShow[(QuestionNumberInt-1)*3 + 1].toString()
        }

        if ((showThreeValues % 5) == 4){
            binding.winBTV.text = NumbersToShow[(QuestionNumberInt-1)*3 + 1].toString()
            binding.winITV.text = NumbersToShow[(QuestionNumberInt-1)*3 + 2].toString()
            binding.winNTV.text = ""
            binding.winGTV.text = ""
            binding.winOTV.text = NumbersToShow[(QuestionNumberInt-1)*3 + 0].toString()
        }
    }
}


fun clearPrevNumbers(){
    for (i in 0..24){
        cellsThatAreGrayChart1[i] = 0
        cellsThatAreGrayChart2[i] = 0
        cellsThatAreGrayChart3[i] = 0
    }

    for (i in 0..34){
        prevNumbers[i] = 0
    }
}

// This is for test to se if there is a second winner, then there is a bug to find.
fun smack(){
    for (i in 1..12){
        prevNumbers[(i-1)*3 + 0] = NumbersToShow[(i-1)*3 + 0]
        prevNumbers[(i-1)*3 + 1] = NumbersToShow[(i-1)*3 + 1]
        // The last call fill only 2, not 3
        if (i < 12) {
            prevNumbers[(i - 1) * 3 + 2] =
                NumbersToShow[(i - 1) * 3 + 2]
        }
    }
}

fun CheckIfValueIsClickable2(position: Int, chart: Int) : Boolean{
    //  var u = 0
    var tempPosition = position


    if (chart == 2){
        tempPosition = tempPosition + 25
    }

    if (chart == 3){
        tempPosition = tempPosition + 50
    }

    var row = tempPosition / 5
    var column = tempPosition % 5

    for (r in 0..34) {
        when (column) {
            1 -> {
                if (prevNumbers[r] == BingoValueI[row]) {
                    return true
                }
            }
            2 -> {
                if (prevNumbers[r] == BingoValueN[row]) {
                    return true
                }
            }
            3 -> {
                if (prevNumbers[r] == BingoValueG[row]) {
                    return true
                }
            }
            4 -> {
                if (prevNumbers[r] == BingoValueO[row]) {
                    return true
                }
            }
            else -> {
                if (prevNumbers[r] == BingoValueB[row]) {
                    return true
                }
            }
        }
    }

    return false
}

fun checkIfWinner(){
}
















