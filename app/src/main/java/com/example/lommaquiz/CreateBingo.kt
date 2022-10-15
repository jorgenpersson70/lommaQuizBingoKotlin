package com.example.lommaquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import com.example.lommaquiz.databinding.ActivityCreateBingoBinding
import com.example.lommaquiz.databinding.ActivityLoginBinding
import com.google.firebase.database.ktx.getValue
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database

import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/*

fick detta

Could not automatically detect an ADB binary. Some emulator functionality will not work until a custom path to ADB  is added. This can be done in Extended Controls (...) > Settings > General tab > 'Use detected ADB location'
 */

// på något sätt så var både player 1 och 2 vinnare. player 1 var den utvalda men player 2 fick diagonal
// x
//    x
//       x
//          x
//              x
// i chart 3


var Year = "1970"
var Month = "01"
var Day = "26"
var YearInt = 1970
var MonthInt = 1
var DayInt = 26

// var BingoNameString = "BingoNameKot"
var BingoNameString = "BingoName"

enum class FileAnswer {
    NoAnswer, SameName, NameOK
}
var existAlready = 0


var ValuesToPresentB: ArrayList<Int> = ArrayList(
    mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15
    )
).shuffled() as ArrayList<Int>

var ValuesToPresentI: ArrayList<Int> = ArrayList(
    mutableListOf(16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30
    )
).shuffled() as ArrayList<Int>

var ValuesToPresentN: ArrayList<Int> = ArrayList(
    mutableListOf(31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45
    )
).shuffled() as ArrayList<Int>

var ValuesToPresentG: ArrayList<Int> = ArrayList(
    mutableListOf(46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60
    )
).shuffled() as ArrayList<Int>

var ValuesToPresentO: ArrayList<Int> = ArrayList(
    mutableListOf(61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75
    )
).shuffled() as ArrayList<Int>

var BValuesTemp: ArrayList<Int> = ArrayList(
    mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15
    )
).shuffled() as ArrayList<Int>

var IValuesTemp: ArrayList<Int> = ArrayList(
    mutableListOf(16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30
    )
).shuffled() as ArrayList<Int>

var NValuesTemp: ArrayList<Int> = ArrayList(
    mutableListOf(31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45
    )
).shuffled() as ArrayList<Int>

var GValuesTemp: ArrayList<Int> = ArrayList(
    mutableListOf(46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60
    )
).shuffled() as ArrayList<Int>

var OValuesTemp: ArrayList<Int> = ArrayList(
    mutableListOf(61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75
    )
).shuffled() as ArrayList<Int>

// ---------- 2
var BValues2: ArrayList<Int> = ArrayList(
    mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15
    )
).shuffled() as ArrayList<Int>

var IValues2: ArrayList<Int> = ArrayList(
    mutableListOf(16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30
    )
).shuffled() as ArrayList<Int>

// ---------- 2
var NValues2: ArrayList<Int> = ArrayList(
    mutableListOf(31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45
    )
).shuffled() as ArrayList<Int>

var GValues2: ArrayList<Int> = ArrayList(
    mutableListOf(46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60
    )
).shuffled() as ArrayList<Int>

var OValues2: ArrayList<Int> = ArrayList(
    mutableListOf(61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75
    )
).shuffled() as ArrayList<Int>

// ---------- 3
var BValues3: ArrayList<Int> = ArrayList(
    mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15
    )
).shuffled() as ArrayList<Int>

var IValues3: ArrayList<Int> = ArrayList(
    mutableListOf(16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30
    )
).shuffled() as ArrayList<Int>

var NValues3: ArrayList<Int> = ArrayList(
    mutableListOf(31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45
    )
).shuffled() as ArrayList<Int>

var GValues3: ArrayList<Int> = ArrayList(
    mutableListOf(46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60
    )
).shuffled() as ArrayList<Int>

var OValues3: ArrayList<Int> = ArrayList(
    mutableListOf(61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75
    )
).shuffled() as ArrayList<Int>

// ---------- 4
var BValues4: ArrayList<Int> = ArrayList(
    mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15
    )
).shuffled() as ArrayList<Int>

var IValues4: ArrayList<Int> = ArrayList(
    mutableListOf(16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30
    )
).shuffled() as ArrayList<Int>

var NValues4: ArrayList<Int> = ArrayList(
    mutableListOf(31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45
    )
).shuffled() as ArrayList<Int>

var GValues4: ArrayList<Int> = ArrayList(
    mutableListOf(46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60
    )
).shuffled() as ArrayList<Int>

var OValues4: ArrayList<Int> = ArrayList(
    mutableListOf(61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75
    )
).shuffled() as ArrayList<Int>

// ---------- 5
var BValues5: ArrayList<Int> = ArrayList(
    mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15
    )
).shuffled() as ArrayList<Int>

var IValues5: ArrayList<Int> = ArrayList(
    mutableListOf(16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30
    )
).shuffled() as ArrayList<Int>

var NValues5: ArrayList<Int> = ArrayList(
    mutableListOf(31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45
    )
).shuffled() as ArrayList<Int>

var GValues5: ArrayList<Int> = ArrayList(
    mutableListOf(46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60
    )
).shuffled() as ArrayList<Int>

var OValues5: ArrayList<Int> = ArrayList(
    mutableListOf(61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75
    )
).shuffled() as ArrayList<Int>

// ---------- 6
var BValues6: ArrayList<Int> = ArrayList(
    mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15
    )
).shuffled() as ArrayList<Int>

var IValues6: ArrayList<Int> = ArrayList(
    mutableListOf(16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30
    )
).shuffled() as ArrayList<Int>

var NValues6: ArrayList<Int> = ArrayList(
    mutableListOf(31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45
    )
).shuffled() as ArrayList<Int>

var GValues6: ArrayList<Int> = ArrayList(
    mutableListOf(46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60
    )
).shuffled() as ArrayList<Int>

var OValues6: ArrayList<Int> = ArrayList(
    mutableListOf(61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75
    )
).shuffled() as ArrayList<Int>

// ---------- 7
var BValues7: ArrayList<Int> = ArrayList(
    mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15
    )
).shuffled() as ArrayList<Int>

var IValues7: ArrayList<Int> = ArrayList(
    mutableListOf(16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30
    )
).shuffled() as ArrayList<Int>

var NValues7: ArrayList<Int> = ArrayList(
    mutableListOf(31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45
    )
).shuffled() as ArrayList<Int>

var GValues7: ArrayList<Int> = ArrayList(
    mutableListOf(46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60
    )
).shuffled() as ArrayList<Int>

var OValues7: ArrayList<Int> = ArrayList(
    mutableListOf(61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75
    )
).shuffled() as ArrayList<Int>

// ---------- 8
var BValues8: ArrayList<Int> = ArrayList(
    mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15
    )
).shuffled() as ArrayList<Int>

var IValues8: ArrayList<Int> = ArrayList(
    mutableListOf(16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30
    )
).shuffled() as ArrayList<Int>

var NValues8: ArrayList<Int> = ArrayList(
    mutableListOf(31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45
    )
).shuffled() as ArrayList<Int>

var GValues8: ArrayList<Int> = ArrayList(
    mutableListOf(46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60
    )
).shuffled() as ArrayList<Int>

var OValues8: ArrayList<Int> = ArrayList(
    mutableListOf(61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75
    )
).shuffled() as ArrayList<Int>

// ---------- 9
var BValues9: ArrayList<Int> = ArrayList(
    mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15
    )
).shuffled() as ArrayList<Int>

var IValues9: ArrayList<Int> = ArrayList(
    mutableListOf(16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30
    )
).shuffled() as ArrayList<Int>

var NValues9: ArrayList<Int> = ArrayList(
    mutableListOf(31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45
    )
).shuffled() as ArrayList<Int>

var GValues9: ArrayList<Int> = ArrayList(
    mutableListOf(46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60
    )
).shuffled() as ArrayList<Int>

var OValues9: ArrayList<Int> = ArrayList(
    mutableListOf(61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75
    )
).shuffled() as ArrayList<Int>

// ---------- 10
var BValues10: ArrayList<Int> = ArrayList(
    mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15
    )
).shuffled() as ArrayList<Int>

var IValues10: ArrayList<Int> = ArrayList(
    mutableListOf(16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30
    )
).shuffled() as ArrayList<Int>

var NValues10: ArrayList<Int> = ArrayList(
    mutableListOf(31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45
    )
).shuffled() as ArrayList<Int>

var GValues10: ArrayList<Int> = ArrayList(
    mutableListOf(46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60
    )
).shuffled() as ArrayList<Int>

var OValues10: ArrayList<Int> = ArrayList(
    mutableListOf(61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75
    )
).shuffled() as ArrayList<Int>

// ---------- 11
var BValues11: ArrayList<Int> = ArrayList(
    mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15
    )
).shuffled() as ArrayList<Int>

var IValues11: ArrayList<Int> = ArrayList(
    mutableListOf(16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30
    )
).shuffled() as ArrayList<Int>

var NValues11: ArrayList<Int> = ArrayList(
    mutableListOf(31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45
    )
).shuffled() as ArrayList<Int>

var GValues11: ArrayList<Int> = ArrayList(
    mutableListOf(46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60
    )
).shuffled() as ArrayList<Int>

var OValues11: ArrayList<Int> = ArrayList(
    mutableListOf(61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75
    )
).shuffled() as ArrayList<Int>

var NumberInBChart1 = 0
var NumberInBChart2 = 0
var NumberInBChart3 = 0

var NumberInRow1Chart1 = 0
var NumberInRow2Chart1 = 0
var NumberInRow3Chart1 = 0
var NumberInRow4Chart1 = 0
var NumberInRow5Chart1 = 0

var NumberInIChart1 = 0
var NumberInIChart2 = 0
var NumberInIChart3 = 0

var NumberInRow1Chart2 = 0
var NumberInRow2Chart2 = 0
var NumberInRow3Chart2 = 0
var NumberInRow4Chart2 = 0
var NumberInRow5Chart2 = 0

var NumberInNChart1 = 0
var NumberInNChart2 = 0
var NumberInNChart3 = 0

var NumberInRow1Chart3 = 0
var NumberInRow2Chart3 = 0
var NumberInRow3Chart3 = 0
var NumberInRow4Chart3 = 0
var NumberInRow5Chart3 = 0

var NumberInGChart1 = 0
var NumberInGChart2 = 0
var NumberInGChart3 = 0

var NumberInOChart1 = 0
var NumberInOChart2 = 0
var NumberInOChart3 = 0


var WinnerNumbers : ArrayList<Int> = ArrayList(mutableListOf(0,0,0,0,0))

class CreateBingo : AppCompatActivity() {
    private lateinit var binding: ActivityCreateBingoBinding

    private lateinit var database: DatabaseReference

    var LatestPassword = ""
    var WhereToPutIt = (1..36).random()
 //   var WinnerNumbers : ArrayList<Int> = ArrayList(mutableListOf(0,0,0,0,0))






    var BValues: ArrayList<Int> = ArrayList(
        mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15
        )
    ).shuffled() as ArrayList<Int>

    var IValues: ArrayList<Int> = ArrayList(
        mutableListOf(16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30
        )
    ).shuffled() as ArrayList<Int>

    var NValues: ArrayList<Int> = ArrayList(
        mutableListOf(31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45
        )
    ).shuffled() as ArrayList<Int>

    var GValues: ArrayList<Int> = ArrayList(
        mutableListOf(46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60
        )
    ).shuffled() as ArrayList<Int>

    var OValues: ArrayList<Int> = ArrayList(
        mutableListOf(61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75
        )
    ).shuffled() as ArrayList<Int>










    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  setContentView(R.layout.activity_create_bingo)

        binding = ActivityCreateBingoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database =
            Firebase.database("https://fire1-95766-default-rtdb.europe-west1.firebasedatabase.app").reference
  //      database.child("BingoName2").child("BingoPassword2").setValue("29")
        getDate()
        checkIfWeShouldErase()

        // Because it could have been set if someone has seen the list and then goes to create bingo
        CatchChosenWalkWhenBack = ""
        loadPassword()
/*  It is enough if we do this only in IPhone      getDate()
        checkIfWeShouldErase()*/

        binding.createBingoCharts.setOnClickListener{
            CreateBingoChart()
            saveCharts()
        }

        binding.alert.visibility = View.INVISIBLE

        binding.seeWalkListBtn.setOnClickListener {
            CatchChosenWalkWhenBack = ""
            val intent = Intent(this, ListSpecialWalk::class.java)
            // intent.putExtra("walk", putExtraString)

            startActivity(intent)
        }

        binding.infoBingoName.setOnClickListener {

            val intent = Intent(this, about_namn_pa_bingo::class.java)
            // intent.putExtra("walk", putExtraString)

            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
 //       if (CatchChosenWalkWhenBack != ""){
        //    CatchChosenWalkWhenBack = "hej"
 //           writeSelectedWalk()
  //      }
        if (havePressedSelect) {
            binding.createBingoConnectTV.text = CatchChosenWalkWhenBack
        }
    }

    fun writeSelectedWalk(){

    }

    fun getNewRandom(){
        BValues.shuffle()
        IValues.shuffle()
        NValues.shuffle()
        GValues.shuffle()
        OValues.shuffle()

        GetWinnerNumbers()

    }

    fun CreateBingoChart()
    {
        var WinnerFound = true
    //    WhereToPutIt = Int.random(in: 1..<37)
        WhereToPutIt = (1..36).random()

        GetWinnerNumbers()

        while (WinnerFound){
            WinnerFound = GetNewValuesPlayer(2, false)
        }
        WinnerFound = true
        while (WinnerFound){
            WinnerFound = GetNewValuesPlayer(3, false)
        }
        WinnerFound = true
        while (WinnerFound){
            WinnerFound = GetNewValuesPlayer(4, false)
        }
        WinnerFound = true
        while (WinnerFound){
            WinnerFound = GetNewValuesPlayer(5, false)
        }
        WinnerFound = true
        while (WinnerFound){
            WinnerFound = GetNewValuesPlayer(6, false)
        }
        WinnerFound = true
        while (WinnerFound){
            WinnerFound = GetNewValuesPlayer(7, false)
        }
        WinnerFound = true
        while (WinnerFound){
            WinnerFound = GetNewValuesPlayer(8, false)
        }
        WinnerFound = true
        while (WinnerFound){
            WinnerFound = GetNewValuesPlayer(9, false)
        }
        WinnerFound = true
        while (WinnerFound){
            WinnerFound = GetNewValuesPlayer(10, false)
        }
        WinnerFound = true
        while (WinnerFound){
            WinnerFound = GetNewValuesPlayer(11, false)
        }

        if (testerLoggedIn) {
            // For test. Must check so there is no other winner than the winner.
            WinnerFound = true
            while (WinnerFound) {
                WinnerFound = GetNewValuesPlayer(2, true)
            }
            WinnerFound = true
            while (WinnerFound) {
                WinnerFound = GetNewValuesPlayer(3, true)
            }
            WinnerFound = true
            while (WinnerFound) {
                WinnerFound = GetNewValuesPlayer(4, true)
            }
            WinnerFound = true
            while (WinnerFound) {
                WinnerFound = GetNewValuesPlayer(5, true)
            }
            WinnerFound = true
            while (WinnerFound) {
                WinnerFound = GetNewValuesPlayer(6, true)
            }
            WinnerFound = true
            while (WinnerFound) {
                WinnerFound = GetNewValuesPlayer(7, true)
            }
            WinnerFound = true
            while (WinnerFound) {
                WinnerFound = GetNewValuesPlayer(8, true)
            }
            WinnerFound = true
            while (WinnerFound) {
                WinnerFound = GetNewValuesPlayer(9, true)
            }
            WinnerFound = true
            while (WinnerFound) {
                WinnerFound = GetNewValuesPlayer(10, true)
            }
            WinnerFound = true
            while (WinnerFound) {
                WinnerFound = GetNewValuesPlayer(11, true)
            }
        }
    }



    fun loadPassword() {

        database.child("BingoName").child("BingoPassword").get()
            .addOnSuccessListener {
                Log.i("firebase", "Got value ${it.value}")

                for (snapchild in it.children) {

                    LatestPassword = snapchild.getValue().toString()
                    Log.i("firebase", LatestPassword)
                    Log.i("firebase", LatestPassword)
                }

            }.addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
            }
    }



// I have produced the b-values to present but I wanted a winner at position 11 or 12. To do that,
// I put the winning numbers at position 0,2,4,5,6. position 1 and 3 must not contain a winner number
//fun testDoubleB1()->Bool{
    fun testDoubleB1(): Boolean{
  //  var New = Int.random(in: 1..<16)
    var New = (1..15).random()

    ValuesToPresentB[1] = New

    return ((ValuesToPresentB[1] == WinnerNumbers[0])
            || (ValuesToPresentB[1] == WinnerNumbers[1])
            || (ValuesToPresentB[1] == WinnerNumbers[2])
            || (ValuesToPresentB[1] == WinnerNumbers[3])
            || (ValuesToPresentB[1] == WinnerNumbers[4]))

}

// kanske att 1 och 3 har möjlighet att få samma värde ??
// ja jag tror att detta kan vara en brist, en bugg
fun testDoubleB3(): Boolean{

    var New = (1..15).random()

    ValuesToPresentB[3] = New

    return ((ValuesToPresentB[3] == WinnerNumbers[0])
            || (ValuesToPresentB[3] == WinnerNumbers[1])
            || (ValuesToPresentB[3] == WinnerNumbers[2])
            || (ValuesToPresentB[3] == WinnerNumbers[3])
            || (ValuesToPresentB[3] == WinnerNumbers[4])
            || (ValuesToPresentB[1] == ValuesToPresentB[3]))
}


fun testDoubleI1():Boolean{

    var New = (16..30).random()
    ValuesToPresentI[1] = New

    return ((ValuesToPresentI[1] == WinnerNumbers[0])
            || (ValuesToPresentI[1] == WinnerNumbers[1])
            || (ValuesToPresentI[1] == WinnerNumbers[2])
            || (ValuesToPresentI[1] == WinnerNumbers[3])
            || (ValuesToPresentI[1] == WinnerNumbers[4]))
}


fun testDoubleI3():Boolean{

    var New = (16..30).random()
    ValuesToPresentI[3] = New

    return ((ValuesToPresentI[3] == WinnerNumbers[0])
            || (ValuesToPresentI[3] == WinnerNumbers[1])
            || (ValuesToPresentI[3] == WinnerNumbers[2])
            || (ValuesToPresentI[3] == WinnerNumbers[3])
            || (ValuesToPresentI[3] == WinnerNumbers[4])
            || (ValuesToPresentI[1] == ValuesToPresentI[3]))
}



fun testDoubleN1():Boolean{

    var New = (31..45).random()
    ValuesToPresentN[1] = New

    return ((ValuesToPresentN[1] == WinnerNumbers[0])
            || (ValuesToPresentN[1] == WinnerNumbers[1])
            || (ValuesToPresentN[1] == WinnerNumbers[2])
            || (ValuesToPresentN[1] == WinnerNumbers[3])
            || (ValuesToPresentN[1] == WinnerNumbers[4]))
}

fun testDoubleN3():Boolean{

    var New = (31..45).random()
    ValuesToPresentN[3] = New

    return ((ValuesToPresentN[3] == WinnerNumbers[0])
            || (ValuesToPresentN[3] == WinnerNumbers[1])
            || (ValuesToPresentN[3] == WinnerNumbers[2])
            || (ValuesToPresentN[3] == WinnerNumbers[3])
            || (ValuesToPresentN[3] == WinnerNumbers[4])
            || (ValuesToPresentN[1] == ValuesToPresentN[3]))
}

fun testDoubleG1():Boolean{

    var New = (46..60).random()
    ValuesToPresentG[1] = New

    return ((ValuesToPresentG[1] == WinnerNumbers[0])
            || (ValuesToPresentG[1] == WinnerNumbers[1])
            || (ValuesToPresentG[1] == WinnerNumbers[2])
            || (ValuesToPresentG[1] == WinnerNumbers[3])
            || (ValuesToPresentG[1] == WinnerNumbers[4]))
}


fun testDoubleG3():Boolean{

    var New = (46..60).random()
    ValuesToPresentG[3] = New

    return ((ValuesToPresentG[3] == WinnerNumbers[0])
            || (ValuesToPresentG[3] == WinnerNumbers[1])
            || (ValuesToPresentG[3] == WinnerNumbers[2])
            || (ValuesToPresentG[3] == WinnerNumbers[3])
            || (ValuesToPresentG[3] == WinnerNumbers[4])
            || (ValuesToPresentG[1] == ValuesToPresentG[3]))
}


fun testDoubleO1():Boolean{

    var New = (61..75).random()
    ValuesToPresentO[1] = New

    return ((ValuesToPresentO[1] == WinnerNumbers[0])
            || (ValuesToPresentO[1] == WinnerNumbers[1])
            || (ValuesToPresentO[1] == WinnerNumbers[2])
            || (ValuesToPresentO[1] == WinnerNumbers[3])
            || (ValuesToPresentO[1] == WinnerNumbers[4]))
}


fun testDoubleO3():Boolean{

    var New = (61..75).random()
    ValuesToPresentO[3] = New

    return ((ValuesToPresentO[3] == WinnerNumbers[0])
            || (ValuesToPresentO[3] == WinnerNumbers[1])
            || (ValuesToPresentO[3] == WinnerNumbers[2])
            || (ValuesToPresentO[3] == WinnerNumbers[3])
            || (ValuesToPresentO[3] == WinnerNumbers[4])
            || (ValuesToPresentO[1] == ValuesToPresentO[3]))
}

fun GetNewValuesPlayer(PlayerNbr:Int, Testing:Boolean):Boolean {

    var BValues2Here: ArrayList<Int> = ArrayList(
        mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15
        )
    ).shuffled() as ArrayList<Int>

    var IValues2Here: ArrayList<Int> = ArrayList(
        mutableListOf(16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30
        )
    ).shuffled() as ArrayList<Int>

    var NValues2Here: ArrayList<Int> = ArrayList(
        mutableListOf(31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45
        )
    ).shuffled() as ArrayList<Int>

    var GValues2Here: ArrayList<Int> = ArrayList(
        mutableListOf(46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60
        )
    ).shuffled() as ArrayList<Int>

    var OValues2Here: ArrayList<Int> = ArrayList(
        mutableListOf(61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75
        )
    ).shuffled() as ArrayList<Int>

    if (Testing){
        when (PlayerNbr) {
            2 -> {
                BValues2Here = BValues2
                IValues2Here = IValues2
                NValues2Here = NValues2
                GValues2Here = GValues2
                OValues2Here = OValues2
            }
            3 -> {
                BValues2Here = BValues3
                IValues2Here = IValues3
                NValues2Here = NValues3
                GValues2Here = GValues3
                OValues2Here = OValues3
            }
            4 -> {
                BValues2Here = BValues4
                IValues2Here = IValues4
                NValues2Here = NValues4
                GValues2Here = GValues4
                OValues2Here = OValues4
            }
            5 -> {
                BValues2Here = BValues5
                IValues2Here = IValues5
                NValues2Here = NValues5
                GValues2Here = GValues5
                OValues2Here = OValues5
            }
            6 -> {
                BValues2Here = BValues6
                IValues2Here = IValues6
                NValues2Here = NValues6
                GValues2Here = GValues6
                OValues2Here = OValues6
            }
            7 -> {
                BValues2Here = BValues7
                IValues2Here = IValues7
                NValues2Here = NValues7
                GValues2Here = GValues7
                OValues2Here = OValues7
            }
            8 -> {
                BValues2Here = BValues8
                IValues2Here = IValues8
                NValues2Here = NValues8
                GValues2Here = GValues8
                OValues2Here = OValues8
            }
            9 -> {
                BValues2Here = BValues9
                IValues2Here = IValues9
                NValues2Here = NValues9
                GValues2Here = GValues9
                OValues2Here = OValues9
            }
            10 -> {
                BValues2Here = BValues10
                IValues2Here = IValues10
                NValues2Here = NValues10
                GValues2Here = GValues10
                OValues2Here = OValues10
            }
            11 -> {
                BValues2Here = BValues11
                IValues2Here = IValues11
                NValues2Here = NValues11
                GValues2Here = GValues11
                OValues2Here = OValues11
            }
            else -> {
                // testar

            }
        }

    }

    BValuesTemp = BValues2Here
    IValuesTemp = IValues2Here
    NValuesTemp = NValues2Here
    GValuesTemp = GValues2Here
    OValuesTemp = OValues2Here

    var FoundWinner = true

    if (Testing) {
        // we only get here if I have compiled with testerLoggedIn = true
        if (CheckColumns()) {
            binding.alert.visibility = View.VISIBLE
            return true
        }

        if (CheckRows()) {
            binding.alert.visibility = View.VISIBLE
            return true
        }

        if (CheckDiagonal()) {
            binding.alert.visibility = View.VISIBLE
            return true
        }
    }else{
        if (CheckColumns()) {
            return true
        }

        if (CheckRows()) {
            return true
        }

        if (CheckDiagonal()) {
            return true
        }

        when (PlayerNbr) {
            2 -> {
                BValues2 = BValues2Here
                IValues2 = IValues2Here
                NValues2 = NValues2Here
                GValues2 = GValues2Here
                OValues2 = OValues2Here
            }
            3 -> {
                BValues3 = BValues2Here
                IValues3 = IValues2Here
                NValues3 = NValues2Here
                GValues3 = GValues2Here
                OValues3 = OValues2Here
            }
            4 -> {
                BValues4 = BValues2Here
                IValues4 = IValues2Here
                NValues4 = NValues2Here
                GValues4 = GValues2Here
                OValues4 = OValues2Here
            }
            5 -> {
                BValues5 = BValues2Here
                IValues5 = IValues2Here
                NValues5 = NValues2Here
                GValues5 = GValues2Here
                OValues5 = OValues2Here
            }
            6 -> {
                BValues6 = BValues2Here
                IValues6 = IValues2Here
                NValues6 = NValues2Here
                GValues6 = GValues2Here
                OValues6 = OValues2Here
            }
            7 -> {
                BValues7 = BValues2Here
                IValues7 = IValues2Here
                NValues7 = NValues2Here
                GValues7 = GValues2Here
                OValues7 = OValues2Here
            }
            8 -> {
                BValues8 = BValues2Here
                IValues8 = IValues2Here
                NValues8 = NValues2Here
                GValues8 = GValues2Here
                OValues8 = OValues2Here
            }
            9 -> {
                BValues9 = BValues2Here
                IValues9 = IValues2Here
                NValues9 = NValues2Here
                GValues9 = GValues2Here
                OValues9 = OValues2Here
            }
            10 -> {
                BValues10 = BValues2Here
                IValues10 = IValues2Here
                NValues10 = NValues2Here
                GValues10 = GValues2Here
                OValues10 = OValues2Here
            }
            11 -> {
                BValues11 = BValues2Here
                IValues11 = IValues2Here
                NValues11 = NValues2Here
                GValues11 = GValues2Here
                OValues11 = OValues2Here
            }
            else -> {
                // testar
                BValues2 = BValues2Here
                IValues2 = IValues2Here
                NValues2 = NValues2Here
                GValues2 = GValues2Here
                OValues2 = OValues2Here
            }
        }
    }




    return false
}


// kan väl göras med



fun CheckColumns():Boolean{
    var Winner = false

    NumberInBChart1 = 0
    NumberInBChart2 = 0
    NumberInBChart3 = 0

    NumberInIChart1 = 0
    NumberInIChart2 = 0
    NumberInIChart3 = 0

    NumberInNChart1 = 0
    NumberInNChart2 = 0
    NumberInNChart3 = 0

    NumberInGChart1 = 0
    NumberInGChart2 = 0
    NumberInGChart3 = 0

    NumberInOChart1 = 0
    NumberInOChart2 = 0
    NumberInOChart3 = 0


    // det skall väl vara denna      ValuesToPresentB

    // Check all B, Chart1
    for (i in 0..4){
        for (m in 0..6){
        if (BValuesTemp[i] == ValuesToPresentB[m]){
            NumberInBChart1 += 1
        }
    }
    }

    // Check all B, Chart2
    for (i in 5..9){
        for (m in 0..6){
        if (BValuesTemp[i] == ValuesToPresentB[m]){
            NumberInBChart2 += 1
        }
    }
    }
    // Check all B, Chart3
    for (i in 10..14){
        for (m in 0..6){
        if (BValuesTemp[i] == ValuesToPresentB[m]){
            NumberInBChart3 += 1
        }
    }
    }

    // Check all I, Chart1
    for (i in 0..4){
        for (m in 0..6){
        if (IValuesTemp[i] == ValuesToPresentI[m]){
            NumberInIChart1 += 1
        }
    }
    }
    // Check all I, Chart2
    for (i in 5..9){
        for (m in 0..6){
        if (IValuesTemp[i] == ValuesToPresentI[m]){
            NumberInIChart2 += 1
        }
    }
    }
    // Check all I, Chart3
    for (i in 10..14){
        for (m in 0..6){
        if (IValuesTemp[i] == ValuesToPresentI[m]){
            NumberInIChart3 += 1
        }
    }
    }

    // Check all N, Chart1
    for (i in 0..4){
        for (m in 0..6){
        if (NValuesTemp[i] == ValuesToPresentN[m]){
            NumberInNChart1 += 1
        }
    }
    }
    // Check all N, Chart2
    for (i in 5..9){
        for (m in 0..6){
        if (NValuesTemp[i] == ValuesToPresentN[m]){
            NumberInNChart2 += 1
        }
    }
    }
    // Check all N, Chart3
    for (i in 10..14){
        for (m in 0..6){
        if (NValuesTemp[i] == ValuesToPresentN[m]){
            NumberInNChart3 += 1
        }
    }
    }
    // Check all G, Chart1
    for (i in 0..4){
        for (m in 0..6){
        if (GValuesTemp[i] == ValuesToPresentG[m]){
            NumberInGChart1 += 1
        }
    }
    }
    // Check all G, Chart2
    for (i in 5..9){
        for (m in 0..6){
        if (GValuesTemp[i] == ValuesToPresentG[m]){
            NumberInGChart2 += 1
        }
    }
    }
    // Check all G, Chart3
    for (i in 10..14){
        for (m in 0..6){
        if (GValuesTemp[i] == ValuesToPresentG[m]){
            NumberInGChart3 += 1
        }
    }
    }
    // Check all O, Chart1
    for (i in 0..4){
        for (m in 0..6){
        if (OValuesTemp[i] == ValuesToPresentO[m]){
            NumberInOChart1 += 1
        }
    }
    }
    // Check all O, Chart2
    for (i in 5..9){
        for (m in 0..6){
        if (OValuesTemp[i] == ValuesToPresentO[m]){
            NumberInOChart2 += 1
        }
    }
    }
    // Check all O, Chart3
    for (i in 10..14){
        for (m in 0..6){
        if (OValuesTemp[i] == ValuesToPresentO[m]){
            NumberInOChart3 += 1
        }
    }
    }


    if ((NumberInBChart1 == 5) || (NumberInBChart2 == 5) || (NumberInBChart3 == 5)){
        Winner = true
    }

    if ((NumberInIChart1 == 5) || (NumberInIChart2 == 5) || (NumberInIChart3 == 5)){
        Winner = true
    }

    if ((NumberInNChart1 == 5) || (NumberInNChart2 == 5) || (NumberInNChart3 == 5)){
        Winner = true
    }

    if ((NumberInGChart1 == 5) || (NumberInGChart2 == 5) || (NumberInGChart3 == 5)){
        Winner = true
    }
    if ((NumberInOChart1 == 5) || (NumberInOChart2 == 5) || (NumberInOChart3 == 5)){
        Winner = true
    }



    if (Winner){
        return true
    }else{
        return false
    }
}

fun CheckRows():Boolean{
    var NumberInRow1B = 0
    var NumberInRow1I = 0
    var NumberInRow1N = 0
    var NumberInRow1G = 0
    var NumberInRow1O = 0

    var SumNumberInRow = 0

    var Winner = false

    // Check all rows, Chart1
    for (i in 0..14){
        NumberInRow1B = 0
        NumberInRow1I = 0
        NumberInRow1N = 0
        NumberInRow1G = 0
        NumberInRow1O = 0

        for (m in 0..6)
        {
            if (BValuesTemp[i] == ValuesToPresentB[m])
            {
                NumberInRow1B = 1
            }
        }
        for (m in 0..6)
        {
            if (IValuesTemp[i] == ValuesToPresentI[m]){
                NumberInRow1B += 1
            }
        }
        for (m in 0..6)
        {
            if (NValuesTemp[i] == ValuesToPresentN[m]){
                NumberInRow1B += 1
            }
        }
        for (m in 0..6)
        {
            if (GValuesTemp[i] == ValuesToPresentG[m]){
                NumberInRow1B += 1
            }
        }
        for (m in 0..6)
        {
            if (OValuesTemp[i] == ValuesToPresentO[m]){
                NumberInRow1B += 1
            }
        }

        if (NumberInRow1B == 5){
            return true
        }

    }
    return false
}

fun CheckDiagonal():Boolean{
    var NumberInRow1B = 0
    var NumberInRow1I = 0
    var NumberInRow1N = 0
    var NumberInRow1G = 0
    var NumberInRow1O = 0

    var SumNumberInRow = 0

    var Winner = false

    // Check all rows, Chart1
    for (i in 0..14){
        NumberInRow1B = 0
        NumberInRow1I = 0
        NumberInRow1N = 0
        NumberInRow1G = 0
        NumberInRow1O = 0


/*


 */
        // chart 1
        for (m in 0..6)
        {
            if (BValuesTemp[0] == ValuesToPresentB[m]){
                NumberInRow1B = 1
            }
        }
        for (m in 0..6)
        {
            if (IValuesTemp[1] == ValuesToPresentI[m]){
                NumberInRow1B += 1
            }
        }
        for (m in 0..6)
        {
            if (NValuesTemp[2] == ValuesToPresentN[m]){
                NumberInRow1B += 1
            }
        }
        for (m in 0..6)
        {
            if (GValuesTemp[3] == ValuesToPresentG[m]){
                NumberInRow1B += 1
            }
        }
        for (m in 0..6)
        {
            if (OValuesTemp[4] == ValuesToPresentO[m]){
                NumberInRow1B += 1
            }
        }

        if (NumberInRow1B == 5){
            return true
        }


        for (m in 0..6)
        {
            if (BValuesTemp[4] == ValuesToPresentB[m]){
                NumberInRow1B = 1
            }
        }
        for (m in 0..6)
        {
            if (IValuesTemp[3] == ValuesToPresentI[m]){
                NumberInRow1B += 1
            }
        }
        for (m in 0..6)
        {
            if (NValuesTemp[2] == ValuesToPresentN[m]){
                NumberInRow1B += 1
            }
        }
        for (m in 0..6)
        {
            if (GValuesTemp[1] == ValuesToPresentG[m]){
                NumberInRow1B += 1
            }
        }
        for (m in 0..6)
        {
            if (OValuesTemp[0] == ValuesToPresentO[m]){
                NumberInRow1B += 1
            }
        }

        if (NumberInRow1B == 5){
            return true
        }

        // chart 2
        for (m in 0..6)
        {
            if (BValuesTemp[5] == ValuesToPresentB[m]){
                NumberInRow1B = 1
            }
        }
        for (m in 0..6)
        {
            if (IValuesTemp[6] == ValuesToPresentI[m]){
                NumberInRow1B += 1
            }
        }
        for (m in 0..6)
        {
            if (NValuesTemp[7] == ValuesToPresentN[m]){
                NumberInRow1B += 1
            }
        }
        for (m in 0..6)
        {
            if (GValuesTemp[8] == ValuesToPresentG[m]){
                NumberInRow1B += 1
            }
        }
        for (m in 0..6)
        {
            if (OValuesTemp[9] == ValuesToPresentO[m]){
                NumberInRow1B += 1
            }
        }

        if (NumberInRow1B == 5){
            return true
        }

        for (m in 0..6)
        {
            if (BValuesTemp[9] == ValuesToPresentB[m]){
                NumberInRow1B = 1
            }
        }
        for (m in 0..6)
        {
            if (IValuesTemp[8] == ValuesToPresentI[m]){
                NumberInRow1B += 1
            }
        }
        for (m in 0..6)
        {
            if (NValuesTemp[7] == ValuesToPresentN[m]){
                NumberInRow1B += 1
            }
        }
        for (m in 0..6)
        {
            if (GValuesTemp[6] == ValuesToPresentG[m]){
                NumberInRow1B += 1
            }
        }
        for (m in 0..6)
        {
            if (OValuesTemp[5] == ValuesToPresentO[m]){
                NumberInRow1B += 1
            }
        }

        if (NumberInRow1B == 5){
            return true
        }

        // chart 3

        for (m in 0..6)
        {
            if (BValuesTemp[10] == ValuesToPresentB[m]){
                NumberInRow1B = 1
            }
        }
        for (m in 0..6)
        {
            if (IValuesTemp[11] == ValuesToPresentI[m]){
                NumberInRow1B += 1
            }
        }
        for (m in 0..6)
        {
            if (NValuesTemp[12] == ValuesToPresentN[m]){
                NumberInRow1B += 1
            }
        }
        for (m in 0..6)
        {
            if (GValuesTemp[13] == ValuesToPresentG[m]){
                NumberInRow1B += 1
            }
        }
        for (m in 0..6)
        {
            if (OValuesTemp[14] == ValuesToPresentO[m]){
                NumberInRow1B += 1
            }
        }

        if (NumberInRow1B == 5){
            return true
        }

        for (m in 0..6)
        {
            if (BValuesTemp[14] == ValuesToPresentB[m]){
                NumberInRow1B = 1
            }
        }
        for (m in 0..6)
        {
            if (IValuesTemp[13] == ValuesToPresentI[m]){
                NumberInRow1B += 1
            }
        }
        for (m in 0..6)
        {
            if (NValuesTemp[12] == ValuesToPresentN[m]){
                NumberInRow1B += 1
            }
        }
        for (m in 0..6)
        {
            if (GValuesTemp[11] == ValuesToPresentG[m]){
                NumberInRow1B += 1
            }
        }
        for (m in 0..6)
        {
            if (OValuesTemp[10] == ValuesToPresentO[m]){
                NumberInRow1B += 1
            }
        }

        if (NumberInRow1B == 5){
            return true
        }

    }
    return false
}


    fun getDate(){

      //  val sdf = SimpleDateFormat("yyyy-MM-dd")
        var sdf = SimpleDateFormat("yyyy")
        var currentDate = sdf.format(Date())

        // get the current date and time
        var currentDateTime = Date()

        Year = currentDate.toString()
        YearInt = currentDate.toInt()
        sdf = SimpleDateFormat("MM")
        currentDate = sdf.format(Date())

        Month = currentDate.toString()
        MonthInt = currentDate.toInt()
        sdf = SimpleDateFormat("dd")
        currentDate = sdf.format(Date())

        Day = currentDate.toString()
        DayInt = currentDate.toInt()

    }


    // Forgot once more, I am not logged in with write auth.
    fun saveCharts() {

        var Exists = false
        var TempStr = "Player "

        if (binding.CreateBingoNameTV.text.toString() == "") {
            binding.alertTV.text = "SKRIV NAMN PÅ RUNDAN"
            return
        }

        if (binding.createBingoCountTV.text.toString() == "") {
            binding.alertTV.text = "SKRIV ANTAL"
            return
        }

        var count = binding.createBingoCountTV.text.toString()

        if ((count.toInt() < 0) || (count.toInt() > 10)){
            binding.alertTV.text = "MAX ANTAL 10"
            return
        }

        // jag fick inte rätt på detta så därför lade jag in en global flagga, existAlready
    //    existAlready =
        existAlready = 0
            rundaAlreadyExist()
// NoAnswer, SameName, NameOK
        Handler(Looper.getMainLooper()).postDelayed({
            if (existAlready == 2) {
                binding.alertTV.text = "INGEN KONTAKT MED DATABASEN"
            }

            if (existAlready == 1) {
                binding.alertTV.text = "NAMNET FINNS REDAN"
            }
            else {
                getDate()

                var shopref = database.child("BingoTest").child("test").push()

                var thekey = shopref.key

                // KOLLA SÅ ATT INTE BingGroupName.text REDAN FINNS

                var name2 = binding.CreateBingoNameTV.getText().toString()

                var personsPlaying = binding.createBingoCountTV.getText().toString()

                var CountInt = personsPlaying.toInt()
                // the winner must not be the first logged in always
                var TheChosenWinner = (1..CountInt).random()


             /*   if (!havePressedSelect) {
                    database.child("BingoNameKot").child(thekey.toString()).child("ConnectToWalk")
                        .setValue("")
                } else {
                    database.child("BingoNameKot").child(thekey.toString()).child("ConnectToWalk")
                        .setValue(CatchChosenWalkWhenBack)
                }*/
                database.child(BingoNameString).child(thekey.toString()).child("ConnectToWalk")
                    .setValue(binding.createBingoConnectTV.text.toString())


                database.child(BingoNameString).child(thekey.toString()).child("ChildByAuto")
                    .setValue(thekey.toString())
                database.child(BingoNameString).child(thekey.toString()).child("name")
                    .setValue(name2)

                database.child(BingoNameString).child(thekey.toString()).child("PersonsLoggedIn")
                    .setValue("0")
                database.child(BingoNameString).child(thekey.toString()).child("CountExpected")
                    .setValue(personsPlaying)
                database.child(BingoNameString).child(thekey.toString()).child("Year").setValue(Year)
                database.child(BingoNameString).child(thekey.toString()).child("Month")
                    .setValue(Month)
                database.child(BingoNameString).child(thekey.toString()).child("Day").setValue(Day)
                database.child(BingoNameString).child(thekey.toString()).child("SelectedWinner")
                    .setValue(TheChosenWinner.toString())

                var testInt = LatestPassword.toInt() + 1
               // LatestPassword = (LatestPassword.toInt() + 1).toString()
                LatestPassword = testInt.toString()
                binding.createBingoPasswordTV.text = testInt.toString()

                database.child("BingoName").child("BingoPassword").child("BingoPassword").setValue(LatestPassword)

                database.child(BingoNameString).child(thekey.toString()).child("Password")
                    .setValue(LatestPassword)

                //  AlertTextView.isHidden = false
                //     AlertTextView.text = "FÖR LÖSENORD, SE NEDAN"
                binding.alertTV.text = "FÖR LÖSENORD, SE NEDAN"

                database.child("BingoPlayersValuesToPresent").child(name2)
                    .child("B1ValuesToPresent")
                    .setValue(ValuesToPresentB)
                database.child("BingoPlayersValuesToPresent").child(name2)
                    .child("B2IValuesToPresent")
                    .setValue(ValuesToPresentI)
                database.child("BingoPlayersValuesToPresent").child(name2)
                    .child("B3NValuesToPresent")
                    .setValue(ValuesToPresentN)
                database.child("BingoPlayersValuesToPresent").child(name2)
                    .child("B4GValuesToPresent")
                    .setValue(ValuesToPresentG)
                database.child("BingoPlayersValuesToPresent").child(name2)
                    .child("B5OValuesToPresent")
                    .setValue(ValuesToPresentO)

                database.child("BingoPlayerWinner").child(name2).child("TheWinner").setValue("0")

                for (i in 1..11) {

                    when (i) {
                        1 -> {
                            BValuesTemp = BValues
                            IValuesTemp = IValues
                            NValuesTemp = NValues
                            GValuesTemp = GValues
                            OValuesTemp = OValues
                        }
                        2 -> {
                            BValuesTemp = BValues2
                            IValuesTemp = IValues2
                            NValuesTemp = NValues2
                            GValuesTemp = GValues2
                            OValuesTemp = OValues2
                        }
                        3 -> {
                            BValuesTemp = BValues3
                            IValuesTemp = IValues3
                            NValuesTemp = NValues3
                            GValuesTemp = GValues3
                            OValuesTemp = OValues3
                        }
                        4 -> {
                            BValuesTemp = BValues4
                            IValuesTemp = IValues4
                            NValuesTemp = NValues4
                            GValuesTemp = GValues4
                            OValuesTemp = OValues4
                        }
                        5 -> {
                            BValuesTemp = BValues5
                            IValuesTemp = IValues5
                            NValuesTemp = NValues5
                            GValuesTemp = GValues5
                            OValuesTemp = OValues5
                        }
                        6 -> {
                            BValuesTemp = BValues6
                            IValuesTemp = IValues6
                            NValuesTemp = NValues6
                            GValuesTemp = GValues6
                            OValuesTemp = OValues6
                        }
                        7 -> {
                            BValuesTemp = BValues7
                            IValuesTemp = IValues7
                            NValuesTemp = NValues7
                            GValuesTemp = GValues7
                            OValuesTemp = OValues7
                        }
                        8 -> {
                            BValuesTemp = BValues8
                            IValuesTemp = IValues8
                            NValuesTemp = NValues8
                            GValuesTemp = GValues8
                            OValuesTemp = OValues8
                        }
                        9 -> {
                            BValuesTemp = BValues9
                            IValuesTemp = IValues9
                            NValuesTemp = NValues9
                            GValuesTemp = GValues9
                            OValuesTemp = OValues9
                        }
                        10 -> {
                            BValuesTemp = BValues10
                            IValuesTemp = IValues10
                            NValuesTemp = NValues10
                            GValuesTemp = GValues10
                            OValuesTemp = OValues10
                        }
                        11 -> {
                            BValuesTemp = BValues11
                            IValuesTemp = IValues11
                            NValuesTemp = NValues11
                            GValuesTemp = GValues11
                            OValuesTemp = OValues11
                        }
                        else -> {
                            // testar
                            BValuesTemp = BValues
                            IValuesTemp = IValues
                            NValuesTemp = NValues
                            GValuesTemp = GValues
                            OValuesTemp = OValues
                        }
                    }

                    database.child("BingoPlayersCharts").child(name2).child(TempStr + i.toString())
                        .child("B1values").setValue(BValuesTemp)
                    database.child("BingoPlayersCharts").child(name2).child(TempStr + i.toString())
                        .child("B2Ivalues").setValue(IValuesTemp)

                    // när vi rätta i xcode så ändra
              //      database.child("BingoPlayersCharts").child(name2).child(TempStr + i.toString())
              //          .child("B3Nvalues").setValue(NValuesTemp)
                    database.child("BingoPlayersCharts").child(name2).child(TempStr + i.toString())
                        .child("B3Ivalues").setValue(NValuesTemp)

                    database.child("BingoPlayersCharts").child(name2).child(TempStr + i.toString())
                        .child("B4Gvalues").setValue(GValuesTemp)
                    database.child("BingoPlayersCharts").child(name2).child(TempStr + i.toString())
                        .child("B5Ovalues").setValue(OValuesTemp)


                    // Jag tror att det är korrekt att göra så
                    var logout = LoginAuthFirebase()
                    logout.logout()

                }
            }


        }, 2000)

    }
 //   Handler(Looper.getMainLooper()).postDelayed({}, 2000)
    suspend fun twoSec() {
        delay(2000)
    }

    /* varför misslyckas jag med att skriva ? kan jag använda
    usersRef.child(date).setValue(userMap)
            .addOnCompleteListener { task ->
                adminRef.child(date).setValue(userMap)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                baseContext, "Success",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                baseContext, "error.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
     */

    fun rundaAlreadyExist() {
        // NoAnswer, SameName, NameOK
        var tempInt = 0
   //     existAlready = FileAnswer.NoAnswer

        database.child("BingoPlayerWinner").child(binding.CreateBingoNameTV.text.toString()).get()
            .addOnSuccessListener {
                Log.i("firebase", "Got value ${it.value}")

                for (snapchild in it.children) {
         //           tempInt = 1
                    existAlready = 1
                }

            }.addOnFailureListener {

                // om jag kopplar bort wifi på datorn så kommer jag ändå inte här, varför
                existAlready = 2
                Log.e("firebase", "Error getting data", it)
            }

  //      return tempInt
    }

    fun checkIfWeShouldErase(){
        var ReadDay = 32
        var ReadMonth = 14
        var ReadYear = 0
        var erase = false
        var ChildByAutoErase = ""
        var BingoNameErase = ""

        database.child(BingoNameString).get()
            .addOnSuccessListener {
                Log.i("firebase", "Got value ${it.value}")
                for (snapchild in it.children) {
                    var key = snapchild.key.toString()
                    database.child(BingoNameString).child(key.toString()).get()
                        .addOnSuccessListener {
                            Log.i("firebase", "Got value ${it.value}")
                            Log.i("firebase", snapchild.value.toString())
                            for (snapchild in it.children)
                            {
                                BingoNameErase = ""
                                erase = false
                                if (snapchild.key == "Day") {
                                    var ReadDayString : String = snapchild.value as String
                                  //  ReadDay = snapchild.value.toString().toInt()
                                    ReadDay = ReadDayString.toInt()
                                    println(ReadDayString)
                                    println(ReadDayString.toInt())
                                }
                                if (snapchild.key == "Month") {
                                   // ReadMonth = snapchild.value.toString().toInt()
                                    var ReadMonthString : String = snapchild.value as String
                                    println(ReadMonthString)
                                    println(ReadMonthString.toInt())
                                    ReadMonth = ReadMonthString.toInt()
                                }
                                if (snapchild.key == "Year") {
                                    ReadYear = snapchild.value.toString().toInt()
                                }
                                if (snapchild.key == "name") {
                                    if (ReadDay.toInt() < DayInt.toInt()){
                                        erase = true
                                    }
                                    if (ReadMonth.toInt() < MonthInt.toInt()){
                                        erase = true
                                    }
                                    BingoNameErase = snapchild.value.toString()
                                }


                                if ((erase) && (BingoNameErase !="")){


                                    ChildByAutoErase = key.toString()
                                    Log.i("firebase", BingoNameErase)
                                    removeFromFirebase(BingoNameString, ChildByAutoErase)
                                    removeFromFirebase("BingoPlayerWinner", BingoNameErase)
                                    removeFromFirebase( "BingoPlayersCharts", BingoNameErase)
                                    removeFromFirebase( "BingoPlayersValuesToPresent", BingoNameErase)
                                }

                            }
                        }.addOnFailureListener {
                            Log.e("firebase", "Error getting data", it)
                        }
                }

        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }
    }

    fun removeFromFirebase(child1: String, child2: String)
    {
        var ref = database.child(child1).child(child2)
        print(child1)
        print(child2)
        print(child2)
// OK, cannot remove if we are not logged in as jorgen_raby@hotmail.com or jorgen@icloud.com or bingou.icloud.com or bingoc@icloud.com
        ref.removeValue()
    }

    // we pic a row or a column or diagonal to be the winning 5 numbers.
    // have a random number between 1 and 36 in WhereToPutIt
    // 1-15 is row, 16-30 is column, 31-36 is diagonal
    // we have three bingo-charts
    // we are going to present 5*7 values, these values are in ValuesToPresent.
    // If we have the winning numbers early in the walk, it is not so good because
    // someone can get bingo at half the way. This is why I put the last winning numbers
    // at ValuesToPresentB[6].
    fun GetWinnerNumbers(){
        var HowMany = 0
        var tempBool = true
        // rows
        if (WhereToPutIt < 16){
            WinnerNumbers[0] = BValues[WhereToPutIt-1]
            WinnerNumbers[1] = IValues[WhereToPutIt-1]
            WinnerNumbers[2] = NValues[WhereToPutIt-1]
            WinnerNumbers[3] = GValues[WhereToPutIt-1]
            WinnerNumbers[4] = OValues[WhereToPutIt-1]

            for (m in 0..6) {
                //    for m in 0...6{
                if (WinnerNumbers[0] == ValuesToPresentB[m]){
                    var temp = ValuesToPresentB[6]

                    // change so the they walk the hole round, pos 11 or pos 12 before there is a winner
                    ValuesToPresentB[6] = WinnerNumbers[0]

                    ValuesToPresentB[m] = temp

                    HowMany += 1
                    break
                }
            }
            // not found
            // if winner number is not present among 0 to 6, we must put the winner number at pos 6
            if (HowMany == 0){
                var temp = ValuesToPresentB[6]
                ValuesToPresentB[6] = WinnerNumbers[0]

                ValuesToPresentB[0] = temp

                HowMany += 1
            }

            // HowMany is now 1
            HowMany = 0
            //for m in 0...6{
            for (m in 0..6) {
                if (WinnerNumbers[1] == ValuesToPresentI[m]){
                    var temp = ValuesToPresentI[6]
                    ValuesToPresentI[6] = WinnerNumbers[1]
                    ValuesToPresentI[m] = temp

                    HowMany += 1
                    break
                }
            }
            // not found
            if (HowMany == 0){
                var temp = ValuesToPresentI[6]
                ValuesToPresentI[6] = WinnerNumbers[1]

                ValuesToPresentI[0] = temp

                HowMany += 1
            }

            HowMany = 0
            // for m in 0...6{
            for (m in 0..6) {
                if (WinnerNumbers[2] == ValuesToPresentN[m]){
                    var temp = ValuesToPresentN[6]
                    ValuesToPresentN[6] = WinnerNumbers[2]
                    ValuesToPresentN[m] = temp

                    HowMany += 1
                    break
                }
            }
            // not found
            if (HowMany == 0){
                var temp = ValuesToPresentN[6]
                ValuesToPresentN[6] = WinnerNumbers[2]

                ValuesToPresentN[0] = temp

                HowMany += 1
            }

            HowMany = 0
            // for m in 0...6{
            for (m in 0..6) {
                if (WinnerNumbers[3] == ValuesToPresentG[m]){
                    var temp = ValuesToPresentG[6]
                    ValuesToPresentG[6] = WinnerNumbers[3]
                    ValuesToPresentG[m] = temp

                    HowMany += 1
                    break
                }
            }
            // not found
            if (HowMany == 0){
                var temp = ValuesToPresentG[6]
                ValuesToPresentG[6] = WinnerNumbers[3]

                ValuesToPresentG[0] = temp

                HowMany += 1
            }

            HowMany = 0
            //  for m in 0...6{
            for (m in 0..6) {
                if (WinnerNumbers[4] == ValuesToPresentO[m]){
                    var temp = ValuesToPresentO[6]
                    ValuesToPresentO[6] = WinnerNumbers[4]
                    ValuesToPresentO[m] = temp

                    HowMany += 1
                    break
                }
            }
            // not found
            if (HowMany == 0){
                var temp = ValuesToPresentO[6]
                ValuesToPresentO[6] = WinnerNumbers[4]

                ValuesToPresentO[0] = temp

                HowMany += 1
            }
        }

        // columns
        if ((WhereToPutIt > 15) && (WhereToPutIt < 31))
        {
            if (WhereToPutIt == 16){
                WinnerNumbers[0] = BValues[0]
                WinnerNumbers[1] = BValues[1]
                WinnerNumbers[2] = BValues[2]
                WinnerNumbers[3] = BValues[3]
                WinnerNumbers[4] = BValues[4]
            }
            if (WhereToPutIt == 17){
                WinnerNumbers[0] = IValues[0]
                WinnerNumbers[1] = IValues[1]
                WinnerNumbers[2] = IValues[2]
                WinnerNumbers[3] = IValues[3]
                WinnerNumbers[4] = IValues[4]
            }
            if (WhereToPutIt == 18){
                WinnerNumbers[0] = NValues[0]
                WinnerNumbers[1] = NValues[1]
                WinnerNumbers[2] = NValues[2]
                WinnerNumbers[3] = NValues[3]
                WinnerNumbers[4] = NValues[4]
            }
            if (WhereToPutIt == 19){
                WinnerNumbers[0] = GValues[0]
                WinnerNumbers[1] = GValues[1]
                WinnerNumbers[2] = GValues[2]
                WinnerNumbers[3] = GValues[3]
                WinnerNumbers[4] = GValues[4]
            }
            if (WhereToPutIt == 20){
                WinnerNumbers[0] = OValues[0]
                WinnerNumbers[1] = OValues[1]
                WinnerNumbers[2] = OValues[2]
                WinnerNumbers[3] = OValues[3]
                WinnerNumbers[4] = OValues[4]
            }


            if (WhereToPutIt == 21){
                WinnerNumbers[0] = BValues[5]
                WinnerNumbers[1] = BValues[6]
                WinnerNumbers[2] = BValues[7]
                WinnerNumbers[3] = BValues[8]
                WinnerNumbers[4] = BValues[9]
            }
            if (WhereToPutIt == 22){
                WinnerNumbers[0] = IValues[5]
                WinnerNumbers[1] = IValues[6]
                WinnerNumbers[2] = IValues[7]
                WinnerNumbers[3] = IValues[8]
                WinnerNumbers[4] = IValues[9]
            }
            if (WhereToPutIt == 23){
                WinnerNumbers[0] = NValues[5]
                WinnerNumbers[1] = NValues[6]
                WinnerNumbers[2] = NValues[7]
                WinnerNumbers[3] = NValues[8]
                WinnerNumbers[4] = NValues[9]
            }
            if (WhereToPutIt == 24){
                WinnerNumbers[0] = GValues[5]
                WinnerNumbers[1] = GValues[6]
                WinnerNumbers[2] = GValues[7]
                WinnerNumbers[3] = GValues[8]
                WinnerNumbers[4] = GValues[9]
            }
            if (WhereToPutIt == 25){
                WinnerNumbers[0] = OValues[5]
                WinnerNumbers[1] = OValues[6]
                WinnerNumbers[2] = OValues[7]
                WinnerNumbers[3] = OValues[8]
                WinnerNumbers[4] = OValues[9]
            }

            if (WhereToPutIt == 26){
                WinnerNumbers[0] = BValues[10]
                WinnerNumbers[1] = BValues[11]
                WinnerNumbers[2] = BValues[12]
                WinnerNumbers[3] = BValues[13]
                WinnerNumbers[4] = BValues[14]
            }
            if (WhereToPutIt == 27){
                WinnerNumbers[0] = IValues[10]
                WinnerNumbers[1] = IValues[11]
                WinnerNumbers[2] = IValues[12]
                WinnerNumbers[3] = IValues[13]
                WinnerNumbers[4] = IValues[14]
            }
            if (WhereToPutIt == 28){
                WinnerNumbers[0] = NValues[10]
                WinnerNumbers[1] = NValues[11]
                WinnerNumbers[2] = NValues[12]
                WinnerNumbers[3] = NValues[13]
                WinnerNumbers[4] = NValues[14]
            }
            if (WhereToPutIt == 29){
                WinnerNumbers[0] = GValues[10]
                WinnerNumbers[1] = GValues[11]
                WinnerNumbers[2] = GValues[12]
                WinnerNumbers[3] = GValues[13]
                WinnerNumbers[4] = GValues[14]
            }
            if (WhereToPutIt == 30){
                WinnerNumbers[0] = OValues[10]
                WinnerNumbers[1] = OValues[11]
                WinnerNumbers[2] = OValues[12]
                WinnerNumbers[3] = OValues[13]
                WinnerNumbers[4] = OValues[14]
            }

            var n = (WhereToPutIt-1) % 5


            // risk för dubbel ?

            // column B
            if (n == 0){
                //    for m in 0...5{
            /*    for (m in 0..5) {
                    if (WinnerNumbers[0] == ValuesToPresentB[m]){

                        var temp = ValuesToPresentB[6]
                             ValuesToPresentB[6] = WinnerNumbers[0]

                        ValuesToPresentB[m] = temp

                        break
                    }
                }*/
                ValuesToPresentB[0] = WinnerNumbers[0]
                ValuesToPresentB[2] = WinnerNumbers[1]
                ValuesToPresentB[4] = WinnerNumbers[2]
                ValuesToPresentB[5] = WinnerNumbers[3]
                ValuesToPresentB[6] = WinnerNumbers[4]
                // check if 1 and 3 is duplicate winnernumber

                tempBool = true
                while (tempBool){
                    tempBool = testDoubleB1()
                }

                tempBool = true
                while (tempBool) {
                    tempBool = testDoubleB3()
                }


            }


            // column I
            if (n == 1){
                //     for m in 0...5{
           /*     for (m in 0..5) {
                    // all five must be here
                    if (WinnerNumbers[1] == ValuesToPresentI[m]){
                        var temp = ValuesToPresentI[6]


                        ValuesToPresentB[m] = temp

                        HowMany += 1
                        break
                    }
                }*/
                ValuesToPresentI[0] = WinnerNumbers[0]
                ValuesToPresentI[2] = WinnerNumbers[1]
                ValuesToPresentI[4] = WinnerNumbers[2]
                ValuesToPresentI[5] = WinnerNumbers[3]
                ValuesToPresentI[6] = WinnerNumbers[4]
                // check if 1 and 3 is duplicate winnernumber
                tempBool = true
                while (tempBool) {
                    tempBool = testDoubleI1()
                }

                tempBool = true
                while (tempBool) {
                    tempBool = testDoubleI3()
                }

            }

            // column N
            if (n == 2){

            /*    for (m in 0..5) {
                    if (WinnerNumbers[2] == ValuesToPresentN[m]){
                        var temp = ValuesToPresentN[6]


                        ValuesToPresentN[m] = temp

                        HowMany += 1
                        break
                    }
                }*/

                ValuesToPresentN[0] = WinnerNumbers[0]
                ValuesToPresentN[2] = WinnerNumbers[1]
                ValuesToPresentN[4] = WinnerNumbers[2]
                ValuesToPresentN[5] = WinnerNumbers[3]
                ValuesToPresentN[6] = WinnerNumbers[4]

                // check if 1 and 3 is duplicate winnernumber
                tempBool = true
                while (tempBool) {
                    tempBool = testDoubleN1()
                }

                tempBool = true
                while (tempBool) {
                    tempBool = testDoubleN3()
                }
            }

            // column G
            if (n == 3){

             /*   for (m in 0..5) {
                    if (WinnerNumbers[3] == ValuesToPresentG[m]){
                        var temp = ValuesToPresentG[6]

                        ValuesToPresentG[m] = temp

                        HowMany += 1
                        break
                    }
                }*/

                ValuesToPresentG[0] = WinnerNumbers[0]
                ValuesToPresentG[2] = WinnerNumbers[1]
                ValuesToPresentG[4] = WinnerNumbers[2]
                ValuesToPresentG[5] = WinnerNumbers[3]
                ValuesToPresentG[6] = WinnerNumbers[4]

                // check if 1 and 3 is duplicate winnernumber
                tempBool = true
                while (tempBool) {
                    tempBool = testDoubleG1()
                }

                tempBool = true
                while (tempBool) {
                    tempBool = testDoubleG3()
                }
            }

            // column O
            if (n == 4){
                //    for m in 0...5{
             /*   for (m in 0..5) {
                    if (WinnerNumbers[4] == ValuesToPresentO[m]){
                        var temp = ValuesToPresentO[6]


                        ValuesToPresentO[m] = temp

                        HowMany += 1
                        break
                    }
                }*/
                ValuesToPresentO[0] = WinnerNumbers[0]
                ValuesToPresentO[2] = WinnerNumbers[1]
                ValuesToPresentO[4] = WinnerNumbers[2]
                ValuesToPresentO[5] = WinnerNumbers[3]
                ValuesToPresentO[6] = WinnerNumbers[4]

                // check if 1 and 3 is duplicate winnernumber
                tempBool = true
                while (tempBool) {
                    tempBool = testDoubleO1()
                }

                tempBool = true
                while (tempBool) {
                    tempBool = testDoubleO3()
                }
            }
        }

        // Diagonal
        if ((WhereToPutIt > 30) && (WhereToPutIt < 37)){
            // chart 1
            // X
            //   X
            //     X
            if (WhereToPutIt == 31){
                WinnerNumbers[0] = BValues[0]
                WinnerNumbers[1] = IValues[1]
                WinnerNumbers[2] = NValues[2]
                WinnerNumbers[3] = GValues[3]
                WinnerNumbers[4] = OValues[4]
            }
            // chart 1
            //     X
            //   X
            //  X
            if (WhereToPutIt == 32){
                WinnerNumbers[0] = BValues[4]
                WinnerNumbers[1] = IValues[3]
                WinnerNumbers[2] = NValues[2]
                WinnerNumbers[3] = GValues[1]
                WinnerNumbers[4] = OValues[0]
            }
            // chart 2
            // X
            //   X
            //     X
            if (WhereToPutIt == 33){
                WinnerNumbers[0] = BValues[5]
                WinnerNumbers[1] = IValues[6]
                WinnerNumbers[2] = NValues[7]
                WinnerNumbers[3] = GValues[8]
                WinnerNumbers[4] = OValues[9]
            }
            // chart 2
            //     X
            //   X
            //  X
            if (WhereToPutIt == 34){
                WinnerNumbers[0] = BValues[9]
                WinnerNumbers[1] = IValues[8]
                WinnerNumbers[2] = NValues[7]
                WinnerNumbers[3] = GValues[6]
                WinnerNumbers[4] = OValues[5]
            }
            // chart 3
            // X
            //   X
            //     X
            if (WhereToPutIt == 35){
                WinnerNumbers[0] = BValues[10]
                WinnerNumbers[1] = IValues[11]
                WinnerNumbers[2] = NValues[12]
                WinnerNumbers[3] = GValues[13]
                WinnerNumbers[4] = OValues[14]
            }
            // chart 3
            //     X
            //   X
            //  X
            if (WhereToPutIt == 36){
                WinnerNumbers[0] = BValues[14]
                WinnerNumbers[1] = IValues[13]
                WinnerNumbers[2] = NValues[12]
                WinnerNumbers[3] = GValues[11]
                WinnerNumbers[4] = OValues[10]
            }

            // for m in 0...5{
            for (m in 0..5) {
                if (WinnerNumbers[0] == ValuesToPresentB[m]){
                    var temp = ValuesToPresentB[6]
                    //           ValuesToPresentB[6] = WinnerNumbers[0]

                    ValuesToPresentB[m] = temp

                    HowMany += 1
                    break
                }
            }
            ValuesToPresentB[6] = WinnerNumbers[0]

            //   for m in 0...5{
            for (m in 0..5) {
                if (WinnerNumbers[1] == ValuesToPresentI[m]){
                    var temp = ValuesToPresentI[6]
                    //           ValuesToPresentB[6] = WinnerNumbers[0]

                    ValuesToPresentI[m] = temp

                    HowMany += 1
                    break
                }
            }
            ValuesToPresentI[6] = WinnerNumbers[1]

            //    for m in 0...5{
            for (m in 0..5) {
                if (WinnerNumbers[2] == ValuesToPresentN[m]){
                    var temp = ValuesToPresentN[6]
                    //           ValuesToPresentB[6] = WinnerNumbers[0]

                    ValuesToPresentN[m] = temp

                    HowMany += 1
                    break
                }
            }
            ValuesToPresentN[6] = WinnerNumbers[2]

            //   for m in 0...5{
            for (m in 0..5) {
                if (WinnerNumbers[3] == ValuesToPresentG[m]){
                    var temp = ValuesToPresentG[6]
                    //           ValuesToPresentB[6] = WinnerNumbers[0]

                    ValuesToPresentG[m] = temp

                    HowMany += 1
                    break
                }
            }
            ValuesToPresentG[6] = WinnerNumbers[3]

            //   for m in 0...5{
            for (m in 0..5) {
                if (WinnerNumbers[4] == ValuesToPresentO[m]){
                    var temp = ValuesToPresentO[6]
                    //           ValuesToPresentB[6] = WinnerNumbers[0]

                    ValuesToPresentO[m] = temp

                    HowMany += 1
                    break
                }
            }
            ValuesToPresentO[6] = WinnerNumbers[4]
        }
    }

    // we pic a row or a column or diagonal to be the winning 5 numbers.
    // have a random number between 1 and 36 in WhereToPutIt
    // 1-15 is row, 16-30 is column, 31-36 is diagonal
    // we have three bingo-charts
    // we are going to present 5*7 values, these values are in ValuesToPresent.
    // If we have the winning numbers early in the walk, it is not so good because
    // someone can get bingo at half the way. This is why I put the last winning numbers
    // at ValuesToPresentB[6].
    fun GetWinnerNumbersOLD(){
        var HowMany = 0
        var tempBool = true
        // rows
        if (WhereToPutIt < 16){
            WinnerNumbers[0] = BValues[WhereToPutIt-1]
            WinnerNumbers[1] = IValues[WhereToPutIt-1]
            WinnerNumbers[2] = NValues[WhereToPutIt-1]
            WinnerNumbers[3] = GValues[WhereToPutIt-1]
            WinnerNumbers[4] = OValues[WhereToPutIt-1]

            for (m in 0..5) {
                //    for m in 0...6{
                if (WinnerNumbers[0] == ValuesToPresentB[m]){
                    var temp = ValuesToPresentB[6]

                    // change so the they walk the hole round, pos 11 or pos 12 before there is a winner
                    ValuesToPresentB[6] = WinnerNumbers[0]

                    ValuesToPresentB[m] = temp

                    HowMany += 1
                    break
                }
            }
            // not found
            if (HowMany == 0){
                var temp = ValuesToPresentB[6]
                ValuesToPresentB[6] = WinnerNumbers[0]

                ValuesToPresentB[0] = temp

                HowMany += 1
            }
            //for m in 0...6{
            for (m in 0..5) {
                if (WinnerNumbers[1] == ValuesToPresentI[m]){
                    var temp = ValuesToPresentI[6]
                    ValuesToPresentI[6] = WinnerNumbers[1]
                    ValuesToPresentI[m] = temp

                    HowMany += 1
                    break
                }
            }
            // not found
            if (HowMany == 1){
                var temp = ValuesToPresentI[6]
                ValuesToPresentI[6] = WinnerNumbers[1]

                ValuesToPresentI[0] = temp

                HowMany += 1
            }
            // for m in 0...6{
            for (m in 0..5) {
                if (WinnerNumbers[2] == ValuesToPresentN[m]){
                    var temp = ValuesToPresentN[6]
                    ValuesToPresentN[6] = WinnerNumbers[2]
                    ValuesToPresentN[m] = temp

                    HowMany += 1
                    break
                }
            }
            // not found
            if (HowMany == 2){
                var temp = ValuesToPresentN[6]
                ValuesToPresentN[6] = WinnerNumbers[2]

                ValuesToPresentN[0] = temp

                HowMany += 1
            }
            // for m in 0...6{
            for (m in 0..5) {
                if (WinnerNumbers[3] == ValuesToPresentG[m]){
                    var temp = ValuesToPresentG[6]
                    ValuesToPresentG[6] = WinnerNumbers[3]
                    ValuesToPresentG[m] = temp

                    HowMany += 1
                    break
                }
            }
            // not found
            if (HowMany == 3){
                var temp = ValuesToPresentG[6]
                ValuesToPresentG[6] = WinnerNumbers[3]

                ValuesToPresentG[0] = temp

                HowMany += 1
            }
            //  for m in 0...6{
            for (m in 0..5) {
                if (WinnerNumbers[4] == ValuesToPresentO[m]){
                    var temp = ValuesToPresentO[6]
                    ValuesToPresentO[6] = WinnerNumbers[4]
                    ValuesToPresentO[m] = temp

                    HowMany += 1
                    break
                }
            }
            // not found
            if (HowMany == 4){
                var temp = ValuesToPresentO[6]
                ValuesToPresentO[6] = WinnerNumbers[4]

                ValuesToPresentO[0] = temp

                HowMany += 1
            }
        }

        // columns
        if ((WhereToPutIt > 15) && (WhereToPutIt < 31))
        {
            if (WhereToPutIt == 16){
                WinnerNumbers[0] = BValues[0]
                WinnerNumbers[1] = BValues[1]
                WinnerNumbers[2] = BValues[2]
                WinnerNumbers[3] = BValues[3]
                WinnerNumbers[4] = BValues[4]
            }
            if (WhereToPutIt == 17){
                WinnerNumbers[0] = IValues[0]
                WinnerNumbers[1] = IValues[1]
                WinnerNumbers[2] = IValues[2]
                WinnerNumbers[3] = IValues[3]
                WinnerNumbers[4] = IValues[4]
            }
            if (WhereToPutIt == 18){
                WinnerNumbers[0] = NValues[0]
                WinnerNumbers[1] = NValues[1]
                WinnerNumbers[2] = NValues[2]
                WinnerNumbers[3] = NValues[3]
                WinnerNumbers[4] = NValues[4]
            }
            if (WhereToPutIt == 19){
                WinnerNumbers[0] = GValues[0]
                WinnerNumbers[1] = GValues[1]
                WinnerNumbers[2] = GValues[2]
                WinnerNumbers[3] = GValues[3]
                WinnerNumbers[4] = GValues[4]
            }
            if (WhereToPutIt == 20){
                WinnerNumbers[0] = OValues[0]
                WinnerNumbers[1] = OValues[1]
                WinnerNumbers[2] = OValues[2]
                WinnerNumbers[3] = OValues[3]
                WinnerNumbers[4] = OValues[4]
            }


            if (WhereToPutIt == 21){
                WinnerNumbers[0] = BValues[5]
                WinnerNumbers[1] = BValues[6]
                WinnerNumbers[2] = BValues[7]
                WinnerNumbers[3] = BValues[8]
                WinnerNumbers[4] = BValues[9]
            }
            if (WhereToPutIt == 22){
                WinnerNumbers[0] = IValues[5]
                WinnerNumbers[1] = IValues[6]
                WinnerNumbers[2] = IValues[7]
                WinnerNumbers[3] = IValues[8]
                WinnerNumbers[4] = IValues[9]
            }
            if (WhereToPutIt == 23){
                WinnerNumbers[0] = NValues[5]
                WinnerNumbers[1] = NValues[6]
                WinnerNumbers[2] = NValues[7]
                WinnerNumbers[3] = NValues[8]
                WinnerNumbers[4] = NValues[9]
            }
            if (WhereToPutIt == 24){
                WinnerNumbers[0] = GValues[5]
                WinnerNumbers[1] = GValues[6]
                WinnerNumbers[2] = GValues[7]
                WinnerNumbers[3] = GValues[8]
                WinnerNumbers[4] = GValues[9]
            }
            if (WhereToPutIt == 25){
                WinnerNumbers[0] = OValues[5]
                WinnerNumbers[1] = OValues[6]
                WinnerNumbers[2] = OValues[7]
                WinnerNumbers[3] = OValues[8]
                WinnerNumbers[4] = OValues[9]
            }

            if (WhereToPutIt == 26){
                WinnerNumbers[0] = BValues[10]
                WinnerNumbers[1] = BValues[11]
                WinnerNumbers[2] = BValues[12]
                WinnerNumbers[3] = BValues[13]
                WinnerNumbers[4] = BValues[14]
            }
            if (WhereToPutIt == 27){
                WinnerNumbers[0] = IValues[10]
                WinnerNumbers[1] = IValues[11]
                WinnerNumbers[2] = IValues[12]
                WinnerNumbers[3] = IValues[13]
                WinnerNumbers[4] = IValues[14]
            }
            if (WhereToPutIt == 28){
                WinnerNumbers[0] = NValues[10]
                WinnerNumbers[1] = NValues[11]
                WinnerNumbers[2] = NValues[12]
                WinnerNumbers[3] = NValues[13]
                WinnerNumbers[4] = NValues[14]
            }
            if (WhereToPutIt == 29){
                WinnerNumbers[0] = GValues[10]
                WinnerNumbers[1] = GValues[11]
                WinnerNumbers[2] = GValues[12]
                WinnerNumbers[3] = GValues[13]
                WinnerNumbers[4] = GValues[14]
            }
            if (WhereToPutIt == 30){
                WinnerNumbers[0] = OValues[10]
                WinnerNumbers[1] = OValues[11]
                WinnerNumbers[2] = OValues[12]
                WinnerNumbers[3] = OValues[13]
                WinnerNumbers[4] = OValues[14]
            }

            var n = (WhereToPutIt-1) % 5


            // risk för dubbel ?

            // column B
            if (n == 0){
                //    for m in 0...5{
                for (m in 0..5) {
                    if (WinnerNumbers[0] == ValuesToPresentB[m]){

                        var temp = ValuesToPresentB[6]
                        //     ValuesToPresentB[6] = WinnerNumbers[0]

                        ValuesToPresentB[m] = temp

                        HowMany += 1
                        break

                    }
                }
                ValuesToPresentB[0] = WinnerNumbers[0]
                ValuesToPresentB[2] = WinnerNumbers[1]
                ValuesToPresentB[4] = WinnerNumbers[2]
                ValuesToPresentB[5] = WinnerNumbers[3]
                ValuesToPresentB[6] = WinnerNumbers[4]
                // check if 1 and 3 is duplicate winnernumber

                tempBool = true
                while (tempBool){
                    tempBool = testDoubleB1()
                }

                tempBool = true
                while (tempBool) {
                    tempBool = testDoubleB3()
                }


            }


            // column I
            if (n == 1){
                //     for m in 0...5{
                for (m in 0..5) {
                    // all five must be here
                    if (WinnerNumbers[1] == ValuesToPresentI[m]){
                        var temp = ValuesToPresentI[6]
                        //           ValuesToPresentB[6] = WinnerNumbers[0]

                        ValuesToPresentB[m] = temp

                        HowMany += 1
                        break
                    }
                }
                ValuesToPresentI[0] = WinnerNumbers[0]
                ValuesToPresentI[2] = WinnerNumbers[1]
                ValuesToPresentI[4] = WinnerNumbers[2]
                ValuesToPresentI[5] = WinnerNumbers[3]
                ValuesToPresentI[6] = WinnerNumbers[4]
                // check if 1 and 3 is duplicate winnernumber
                tempBool = true
                while (tempBool) {
                    tempBool = testDoubleI1()
                }

                tempBool = true
                while (tempBool) {
                    tempBool = testDoubleI3()
                }

            }

            // column N
            if (n == 2){
                //      for m in 0...5{
                for (m in 0..5) {
                    if (WinnerNumbers[2] == ValuesToPresentN[m]){
                        var temp = ValuesToPresentN[6]
                        //           ValuesToPresentB[6] = WinnerNumbers[0]

                        ValuesToPresentN[m] = temp

                        HowMany += 1
                        break
                    }
                }

                ValuesToPresentN[0] = WinnerNumbers[0]
                ValuesToPresentN[2] = WinnerNumbers[1]
                ValuesToPresentN[4] = WinnerNumbers[2]
                ValuesToPresentN[5] = WinnerNumbers[3]
                ValuesToPresentN[6] = WinnerNumbers[4]

                // check if 1 and 3 is duplicate winnernumber
                tempBool = true
                while (tempBool) {
                    tempBool = testDoubleN1()
                }

                tempBool = true
                while (tempBool) {
                    tempBool = testDoubleN3()
                }
            }

            // column G
            if (n == 3){
                //      for m in 0...5{
                for (m in 0..5) {
                    if (WinnerNumbers[3] == ValuesToPresentG[m]){
                        var temp = ValuesToPresentG[6]
                        //           ValuesToPresentB[6] = WinnerNumbers[0]

                        ValuesToPresentG[m] = temp

                        HowMany += 1
                        break
                    }
                }
                ValuesToPresentG[0] = WinnerNumbers[0]
                ValuesToPresentG[2] = WinnerNumbers[1]
                ValuesToPresentG[4] = WinnerNumbers[2]
                ValuesToPresentG[5] = WinnerNumbers[3]
                ValuesToPresentG[6] = WinnerNumbers[4]

                // check if 1 and 3 is duplicate winnernumber
                tempBool = true
                while (tempBool) {
                    tempBool = testDoubleG1()
                }

                tempBool = true
                while (tempBool) {
                    tempBool = testDoubleG3()
                }
            }

            // column O
            if (n == 4){
                //    for m in 0...5{
                for (m in 0..5) {
                    if (WinnerNumbers[4] == ValuesToPresentO[m]){
                        var temp = ValuesToPresentO[6]
                        //           ValuesToPresentB[6] = WinnerNumbers[0]

                        ValuesToPresentO[m] = temp

                        HowMany += 1
                        break
                    }
                }
                ValuesToPresentO[0] = WinnerNumbers[0]
                ValuesToPresentO[2] = WinnerNumbers[1]
                ValuesToPresentO[4] = WinnerNumbers[2]
                ValuesToPresentO[5] = WinnerNumbers[3]
                ValuesToPresentO[6] = WinnerNumbers[4]

                // check if 1 and 3 is duplicate winnernumber
                tempBool = true
                while (tempBool) {
                    tempBool = testDoubleO1()
                }

                tempBool = true
                while (tempBool) {
                    tempBool = testDoubleO3()
                }
            }
        }

        // Diagonal
        if ((WhereToPutIt > 30) && (WhereToPutIt < 37)){
            // chart 1
            // X
            //   X
            //     X
            if (WhereToPutIt == 31){
                WinnerNumbers[0] = BValues[0]
                WinnerNumbers[1] = IValues[1]
                WinnerNumbers[2] = NValues[2]
                WinnerNumbers[3] = GValues[3]
                WinnerNumbers[4] = OValues[4]
            }
            // chart 1
            //     X
            //   X
            //  X
            if (WhereToPutIt == 32){
                WinnerNumbers[0] = BValues[4]
                WinnerNumbers[1] = IValues[3]
                WinnerNumbers[2] = NValues[2]
                WinnerNumbers[3] = GValues[1]
                WinnerNumbers[4] = OValues[0]
            }
            // chart 2
            // X
            //   X
            //     X
            if (WhereToPutIt == 33){
                WinnerNumbers[0] = BValues[5]
                WinnerNumbers[1] = IValues[6]
                WinnerNumbers[2] = NValues[7]
                WinnerNumbers[3] = GValues[8]
                WinnerNumbers[4] = OValues[9]
            }
            // chart 2
            //     X
            //   X
            //  X
            if (WhereToPutIt == 34){
                WinnerNumbers[0] = BValues[9]
                WinnerNumbers[1] = IValues[8]
                WinnerNumbers[2] = NValues[7]
                WinnerNumbers[3] = GValues[6]
                WinnerNumbers[4] = OValues[5]
            }
            // chart 3
            // X
            //   X
            //     X
            if (WhereToPutIt == 35){
                WinnerNumbers[0] = BValues[10]
                WinnerNumbers[1] = IValues[11]
                WinnerNumbers[2] = NValues[12]
                WinnerNumbers[3] = GValues[13]
                WinnerNumbers[4] = OValues[14]
            }
            // chart 3
            //     X
            //   X
            //  X
            if (WhereToPutIt == 36){
                WinnerNumbers[0] = BValues[14]
                WinnerNumbers[1] = IValues[13]
                WinnerNumbers[2] = NValues[12]
                WinnerNumbers[3] = GValues[11]
                WinnerNumbers[4] = OValues[10]
            }

            // for m in 0...5{
            for (m in 0..5) {
                if (WinnerNumbers[0] == ValuesToPresentB[m]){
                    var temp = ValuesToPresentB[6]
                    //           ValuesToPresentB[6] = WinnerNumbers[0]

                    ValuesToPresentB[m] = temp

                    HowMany += 1
                    break
                }
            }
            ValuesToPresentB[6] = WinnerNumbers[0]

            //   for m in 0...5{
            for (m in 0..5) {
                if (WinnerNumbers[1] == ValuesToPresentI[m]){
                    var temp = ValuesToPresentI[6]
                    //           ValuesToPresentB[6] = WinnerNumbers[0]

                    ValuesToPresentI[m] = temp

                    HowMany += 1
                    break
                }
            }
            ValuesToPresentI[6] = WinnerNumbers[1]

            //    for m in 0...5{
            for (m in 0..5) {
                if (WinnerNumbers[2] == ValuesToPresentN[m]){
                    var temp = ValuesToPresentN[6]
                    //           ValuesToPresentB[6] = WinnerNumbers[0]

                    ValuesToPresentN[m] = temp

                    HowMany += 1
                    break
                }
            }
            ValuesToPresentN[6] = WinnerNumbers[2]

            //   for m in 0...5{
            for (m in 0..5) {
                if (WinnerNumbers[3] == ValuesToPresentG[m]){
                    var temp = ValuesToPresentG[6]
                    //           ValuesToPresentB[6] = WinnerNumbers[0]

                    ValuesToPresentG[m] = temp

                    HowMany += 1
                    break
                }
            }
            ValuesToPresentG[6] = WinnerNumbers[3]

            //   for m in 0...5{
            for (m in 0..5) {
                if (WinnerNumbers[4] == ValuesToPresentO[m]){
                    var temp = ValuesToPresentO[6]
                    //           ValuesToPresentB[6] = WinnerNumbers[0]

                    ValuesToPresentO[m] = temp

                    HowMany += 1
                    break
                }
            }
            ValuesToPresentO[6] = WinnerNumbers[4]
        }
    }
}

