package com.example.lommaquiz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.lommaquiz.databinding.ActivityLoginBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
// bara för test, bort sen. fundera hur vi skall hantera att testar får hitta alla koordinater
var debuggHomeTest = false
var debuggHomeTestGrey = false

var logins = ArrayList(mutableListOf(String))
//var loginKeys = ArrayList(mutableListOf(String))
var loginKeys = ArrayList<String>()
var bingoKeys = ArrayList<String>()
var found = false

var countLogins = 0
var countBingoNames = 0
var loggedIn = ""
var questionUser = ""
var specialWalk = false
var testerLoggedIn = false


var  BingoPasswordFound = false
var connectToWalk = ""
var bingoName = ""
var bingoSelectedWinner = ""
var bingoCountExpected = ""
var bingoPersonsLoggedIn = ""

var foundBingoPassword = false
//var BingoName = ""
var ChildByAuto = ""
var PersonsLoggedIn = ""
var youAreplayer = 0
var questUserBingo = ""

//= ArrayList(mutableListOf("","","","","","","","","","","",""))
/*var IValues4: ArrayList<Int> = ArrayList(
    mutableListOf(16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30
    )
).shuffled() as ArrayList<Int>*/
//var ValuesToPresentBRead = [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
var ValuesToPresentBRead: ArrayList<Int> = ArrayList(
    mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
    )
) as ArrayList<Int>
var ValuesToPresentIRead: ArrayList<Int> = ArrayList(
    mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
    )
) as ArrayList<Int>
var ValuesToPresentNRead: ArrayList<Int> = ArrayList(
    mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
    )
) as ArrayList<Int>
var ValuesToPresentGRead: ArrayList<Int> = ArrayList(
    mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
    )
) as ArrayList<Int>
var ValuesToPresentORead: ArrayList<Int> = ArrayList(
    mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
    )
) as ArrayList<Int>

var NumbersToShow: ArrayList<Int> = ArrayList(
    mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
    )
) as ArrayList<Int>

// BValuesRead


class login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var database: DatabaseReference
    var loginString = ""

 //   var login = loginClass()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    //    setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)




        database = Firebase.database("https://fire1-95766-default-rtdb.europe-west1.firebasedatabase.app").reference

        readLogins()
// bäst att vi gör detta när man trycker LOGIN        readBingos()

        binding.loginBtn.setOnClickListener {

         //   binding.loginLogoutTV.text = "klick"
            readBingos()
            if (binding.bingoLoginTV.text.toString() != ""){
        //        Handler(Looper.getMainLooper()).postDelayed({}, 2000)
                Handler(Looper.getMainLooper()).postDelayed({
                readBingoLogins()
                }, 1000)
       //         checkIfBingoLogin()
                hideSoftKeyboard(this)
            }
            else {
                checkIfLogin()
                finich = false
                hideSoftKeyboard(this)
            }
        }

        binding.logoutBtn.setOnClickListener {
            binding.loginLogoutTV.text = "Du är utloggad"
            loggedIn = ""
            walkName = "Walk1"
            specialWalk = false
            testerLoggedIn = false
            finich = false
            // två ?
            BingoPasswordFound = false
            foundBingoPassword = false

            takeAwayButtonToChooseRunda = false
            takeAwayButtonStartBingo = true

            questionUser = ""

            hideSoftKeyboard(this)
        }

        if (loggedIn != ""){
            binding.loginLogoutTV.text = "Du är inloggad"
        }
        if (testerLoggedIn) {
            binding.loginLogoutTV.text = "Du är inloggad som testare"
        }

        binding.writePermitBtn.setOnClickListener {
            val intent = Intent(this, LoginAuthFirebase::class.java)

            startActivity(intent)
        }

        readLoginText()
    }

    fun hideSoftKeyboard(activity: Activity) {

    }

    fun readLogins() {
        var testar = ""

        readFromFirebase += 1

        database.child("QuizWalks").child("Login").get()
            .addOnSuccessListener {
                Log.i("firebase", "Got value ${it.value}")

          //      var i = 0
                loginKeys.clear()
                countLogins = 0

                for (snapchild in it.children) {


                    var tempshop = it.getValue<question>()

                    var questionNumber = 0

                    when (it.key) {
                        "Fråga A" -> {
                            questionNumber = 0
                        }

                        else -> {
                            // testar
                            questionNumber = 11

                        }
                    }

                    loginKeys.add(snapchild.key.toString())
       //             Log.i("MIN", "Malin " + loginKeys[i])
                    countLogins += 1
                }

            }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }
    }


    fun readIfSomeoneAlreadyWon()
    {
        database.child("BingoPlayerWinner").child(bingoName).child("TheWinner").get()
            .addOnSuccessListener {
                Log.i("firebase", "Got value ${it.value}")
                if (it.value.toString() != "0") {
                    whoIsWinner = it.value.toString()
                    waitToTheLastToShowLooser = true
                }
            }.addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
            }
    }

    fun checkIfLogin() {
        var found = false
        var checkWritePermission = false

        readFromFirebase += 1
        // minus 2 ??
        // kanske lägga unnanför i andra läsningen
        for (i in 0..(countLogins-1)) {
            loginKeys[i].toString()
            Log.i("MIN", "Malin " + loginKeys[i])
            Log.i("MIN", "Malin " + loginKeys[i].toString())
        }

        for (i in 0..(countLogins-1)) {
            if (!found) {
                database.child("QuizWalks").child("Login").child(loginKeys[i].toString()).get()
                    .addOnSuccessListener {
                        Log.i("firebase", "Got value ${it.value}")


                        for (snapchild in it.children) {

                            if (!found) {
                                if (snapchild.key == "questionCreator") {
                                    if (snapchild.value == binding.loginET.text.toString()) {
                                        Log.i("MIN", "Malin Hurra")
                                        binding.loginLogoutTV.text = "Du är inloggad"

                                        checkIfSpecialWalk()

                                        loggedIn = snapchild.value.toString()
                                    }

                                    if (binding.loginET.text.toString() == "testare") {
                                        testerLoggedIn = true
                                        binding.loginLogoutTV.text = "Du är inloggad som testare"
                                    }


                                }
                                // I change here from jörgen to jorgen
                                if ((loggedIn == "jorgen") && (masterLoggedIn) && (snapchild.key == "questionUser")) {
                                    questionUser = snapchild.value.toString()
                                    found = true
                                    return@addOnSuccessListener
                                } else {
                                    // denna är nog inte korrekt, springer den kanske till sista
                                    if ((loggedIn != "") && (snapchild.key == "questionUser")) {
                                      //  found = true
                                        checkWritePermission = true
                                        questionUser = snapchild.value.toString()
   //                                     return@addOnSuccessListener
                                    }

                                }
                                if ((loggedIn != "") && (snapchild.key == "writePermission") && checkWritePermission) {

                                    // check if we logged in with writepermission but then logged in without
                                    if (snapchild.value.toString() == "Nej"){
                                        writePermission = false
                                        authPassedForUser = false
                                        masterLoggedIn = false
                                        val auth = Firebase.auth
                                        auth.signOut()
                                    }
                                    found = true
                                }

                            }
                        }

                    }.addOnFailureListener {
                        Log.e("firebase", "Error getting data", it)
                    }
            }
        }
    }

    fun checkIfSpecialWalk(){

        readFromFirebase += 1

        database.child("QuizWalks").child("maps").child(loggedIn).get()
            .addOnSuccessListener {

                val tempCoordinatelist = mutableListOf<mapCoordinates>()

                // If special walk is there, then leave it up to showMap to read coordinates
                walkName = loggedIn
                specialWalk = true
                readMapCoordinates()
            }
    }

    fun readBingos() {
        var testar = ""

        readFromFirebase += 1

        database.child(BingoNameString).get()
            .addOnSuccessListener {
                Log.i("firebase", "Got value ${it.value}")

                //      var i = 0
                bingoKeys.clear()
                countBingoNames = 0

                for (snapchild in it.children) {


                    var tempshop = it.getValue<question>()

                    var questionNumber = 0



                    bingoKeys.add(snapchild.key.toString())
                    //             Log.i("MIN", "Malin " + loginKeys[i])
                    countBingoNames += 1
                }

            }.addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
            }

    }

fun readBingoLogins(){

    var checkWritePermission = false


    readFromFirebase += 1
    // minus 2 ??
    // kanske lägga unnanför i andra läsningen
    for (i in 0..(countBingoNames-1)) {
        bingoKeys[i].toString()
        Log.i("MIN", "Malin " + bingoKeys[i])
        Log.i("MIN", "Malin " + bingoKeys[i].toString())
    }

    found = false

    for (i in 0..(countBingoNames-1)) {


        if (!found) {
            // tror att denna skall vara här
            BingoPasswordFound = false

            database.child(BingoNameString).child(bingoKeys[i].toString()).get()
                .addOnSuccessListener {
                    Log.i("firebase", "Got value ${it.value}")

                    var j = 0
                    for (snapchild in it.children) {

                        if (!found) {
                            if (snapchild.key == "Password") {
                                if (snapchild.value.toString() == binding.bingoLoginTV.text.toString()) {
                                    Log.i("firebase", "Got value ${it.value}")
                                    BingoPasswordFound = true
                             //       binding.loginLogoutTV.text = "Du är på " +

                                    if (debuggHomeTest) {
                                        testerLoggedIn = true
                                    }
                                    takeAwayButtonStartBingo = false
                                    waitToTheLastToShowLooser = false

                                    // I do this because the user must have writing-access
     //                               val getLogIn = LoginAuthFirebase()
     //                               getLogIn.forceBingouLogin()

                                }

                            }
                            if (snapchild.key == "ConnectToWalk") {
                             //   connectToWalk = it.value.toString()
                                connectToWalk = snapchild.value.toString()
                        //        connectToWalk = it.key.toString()
                            }
                            if (snapchild.key == "name") {
                                bingoName = snapchild.value.toString()
                            }
                            if (snapchild.key == "CountExpected") {
                                bingoCountExpected = snapchild.value.toString()
                            }
                            if (snapchild.key == "SelectedWinner") {
                                bingoSelectedWinner = snapchild.value.toString()
                            }
                            if (snapchild.key == "PersonsLoggedIn") {
                                bingoPersonsLoggedIn = snapchild.value.toString()
                            }
                            if (snapchild.key == "ChildByAuto") {
                                ChildByAuto = snapchild.value.toString()
                            }

                        }
                    }

                    // check if maximum participants have been reached
                    if (BingoPasswordFound){
                        if (bingoCountExpected.toInt() == bingoPersonsLoggedIn.toInt()){
                            binding.loginLogoutTV.text = "Max antal deltagare har redan loggat in"
                            // vet inte med return
                           // return@addOnSuccessListener
                            BingoPasswordFound = false
                        }else {
                            var bingoPersonsLoggedInInt = bingoPersonsLoggedIn.toInt() + 1
                            binding.loginLogoutTV.text =
                                "Du är på " + bingoName + " som deltagare " + bingoPersonsLoggedInInt.toString()
                            val getLogIn = LoginAuthFirebase()
                            getLogIn.forceBingouLogin()
                        }
                    }

                    if (connectToWalk != ""){
                       //  questUserBingo = quizNameChildInfo["ConnectToWalk"]!
                            takeAwayButtonToChooseRunda = true
                    }else{
                        takeAwayButtonToChooseRunda = false
                    }


                    if (BingoPasswordFound){

//                        readWhatPlayer()
                        countdownSecond()
                        found = true


                        var stringToRead = ""

                        for (r in 0..4)
                        {
                            when (r) {
                                1 -> {
                                    stringToRead = "B2IValuesToPresent"
                                }
                                2 -> {
                                    stringToRead = "B3NValuesToPresent"
                                }
                                3 -> {
                                    stringToRead = "B4GValuesToPresent"
                                }
                                4 -> {
                                    stringToRead = "B5OValuesToPresent"
                                }
                                else -> {
                                    stringToRead = "B1ValuesToPresent"
                                }
                            }

                            database.child("BingoPlayersValuesToPresent").child(bingoName)
                                .child(stringToRead).get()
                                .addOnSuccessListener {
                                    Log.i("firebase", "Got value ${it.value}")

                                    // database.child("BingoPlayersValuesToPresent").child(name2).child("B1ValuesToPresent").setValue(ValuesToPresentB)
                                    var breakOut = false
                                    for (snapchild in it.children)
                                    {
                                        //    var quizNameChildSnap = snapchild as! DataSnapshot
                                        var hej = it.children
                                        //   let quizNameChildInfo = quizNameChildSnap.value as! [Int]
                                     /*   if (breakOut){
                                            break
                                        }*/
                                        var teststring = ""


                                        teststring = it.value.toString()

                                        val result = teststring.removeSurrounding("[", "]")
                                            .replace(" ", "").split(",").map { it.toInt() }

                                        when (r) {
                                            1 -> {
                                                ValuesToPresentIRead = result as ArrayList<Int>
                                                Log.i("firebase", result[0].toString())
                                                Log.i("firebase", result[1].toString())
                                            }
                                            2 -> {
                                                ValuesToPresentNRead = result as ArrayList<Int>
                                                Log.i("firebase", result[0].toString())
                                                Log.i("firebase", result[1].toString())
                                            }
                                            3 -> {
                                                ValuesToPresentGRead = result as ArrayList<Int>
                                                Log.i("firebase", result[0].toString())
                                                Log.i("firebase", result[1].toString())
                                            }
                                            4 -> {
                                                ValuesToPresentORead = result as ArrayList<Int>
                                                Log.i("firebase", result[0].toString())
                                                Log.i("firebase", result[1].toString())
                                                LoadValuesToShow()
                                            }
                                            else -> {
                                                ValuesToPresentBRead = result as ArrayList<Int>
                                                Log.i("firebase", result[0].toString())
                                                Log.i("firebase", result[1].toString())
                                            }
                                        }

                                        break
                                    }


                                }.addOnFailureListener {
                                    Log.e("firebase", "Error getting data", it)
                                }
                        }


                    }


                }.addOnFailureListener {
                    Log.e("firebase", "Error getting data", it)
                }


        }
    }
}

    fun readWhatPlayer(){
        // If someone has already got bingo before this start, there will be no read change while walking and the the winner alert is missed.
        readIfSomeoneAlreadyWon()

        youAreplayer = bingoPersonsLoggedIn.toInt()
        youAreplayer += 1

        var PointToPlayer = youAreplayer + 1

        if (bingoSelectedWinner.toInt() == youAreplayer){
            print("vinnaren har loggat in")
            PointToPlayer = 1
        }

        database.child(BingoNameString).child(ChildByAuto).child("PersonsLoggedIn").setValue(youAreplayer.toString())

        // read what player you are

        var stringToRead = ""

        // self.ref.child("BingoPlayersCharts").child(BingoName).child("Player " + String(PointToPlayer))
        for (r in 0..4) {
            when (r) {
                1 -> {
                    stringToRead = "B2Ivalues"
                }
                2 -> {
       // när vi ändrat i xcode             stringToRead = "B3Nvalues"
                    stringToRead = "B3Ivalues"
                }
                3 -> {
                    stringToRead = "B4Gvalues"
                }
                4 -> {
                    stringToRead = "B5Ovalues"
                }
                else -> {
                    stringToRead = "B1values"
                }
            }

            var testar = "Player " + PointToPlayer.toString()
            database.child("BingoPlayersCharts").child(bingoName)
                .child("Player " + PointToPlayer.toString()).child(stringToRead)
                .get()
                .addOnSuccessListener {
                    Log.i("firebase", "Got value ${it.value}")

                    // database.child("BingoPlayersValuesToPresent").child(name2).child("B1ValuesToPresent").setValue(ValuesToPresentB)
                    for (snapchild in it.children)
                    {
                        //    var quizNameChildSnap = snapchild as! DataSnapshot
                        var hej = it.children
                        //   let quizNameChildInfo = quizNameChildSnap.value as! [Int]
                        /*   if (breakOut){
                               break
                           }*/
                        var teststring = ""


                        teststring = it.value.toString()

                        val result = teststring.removeSurrounding("[", "]")
                            .replace(" ", "").split(",").map { it.toInt() }

                        when (r) {
                            1 -> {
                                BingoValueI = result as ArrayList<Int>
                                Log.i("firebase", result[0].toString())
                                Log.i("firebase", result[1].toString())
                            }
                            2 -> {
                                BingoValueN = result as ArrayList<Int>
                                Log.i("firebase", result[0].toString())
                                Log.i("firebase", result[1].toString())
                            }
                            3 -> {
                                BingoValueG = result as ArrayList<Int>
                                Log.i("firebase", result[0].toString())
                                Log.i("firebase", result[1].toString())
                            }
                            4 -> {
                                BingoValueO = result as ArrayList<Int>
                                Log.i("firebase", result[0].toString())
                                Log.i("firebase", result[1].toString())
                                LoadValuesToShow()
                            }
                            else -> {
                                BingoValueB = result as ArrayList<Int>
                                Log.i("firebase", result[0].toString())
                                Log.i("firebase", result[1].toString())
                            }
                        }

                        break
                    }

                }.addOnFailureListener {
                    Log.e("firebase", "Error getting data", it)
                }
        }
    }

    fun LoadValuesToShow(){
      //  var u = 0
        for (r in 0..6)
        {
                    NumbersToShow[r*5 + 0] = ValuesToPresentBRead[r]
                    NumbersToShow[r*5 + 1] = ValuesToPresentIRead[r]
                    NumbersToShow[r*5 + 2] = ValuesToPresentNRead[r]
                    NumbersToShow[r*5 + 3] = ValuesToPresentGRead[r]
                    NumbersToShow[r*5 + 4] = ValuesToPresentORead[r]
        }
    }

    fun readMapCoordinates(){
        var count = 0
        var letter : String = "A"

        readFromFirebase += 1

        for (i in 0..11) {

            if (i == 0){
                letter = "A"
            }

            if (i == 1){
                letter = "B"
            }

            if (i == 2){
                letter = "C"
            }

            if (i == 3){
                letter = "D"
            }

            if (i == 4){
                letter = "E"
            }

            if (i == 5){
                letter = "F"
            }

            if (i == 6){
                letter = "G"
            }

            if (i == 7){
                letter = "H"
            }

            if (i == 8){
                letter = "I"
            }

            if (i == 9){
                letter = "J"
            }

            if (i == 10){
                letter = "K"
            }

            if (i == 11){
                letter = "L"
            }

            database.child("QuizWalks").child("maps").child(walkName).child(letter).get()
                .addOnSuccessListener {

                    val tempCoordinatelist = mutableListOf<mapCoordinates>()

                    for (snapchild in it.children) {

                        var tempshop = snapchild.getValue()!!


                        if (snapchild.key == "posLatitude") {
                            //                         binding.latTV.text = snapchild.value.toString()
                            //                          binding.latTV.text = tempshop.toString()
                            coordinatesLatitude[i] = tempshop.toString().toFloat()

                            //   binding.latTV.text = "HEJ"
                            Log.i("MIN", "LAT " + tempshop.toString())
                        }

                        if (snapchild.key == "posLongitude") {
                            //                binding.longTV.text = snapchild.value.toString()
                            //                      binding.longTV.text = tempshop.toString()
                            coordinatesLongitude[i] = tempshop.toString().toFloat()

                            //     binding.longTV.text = "SVEJS"
                            Log.i("MIN", "LONG " + tempshop.toString())
                        }

                        count += 1

                    }

                }.addOnFailureListener {
                    Log.e("firebase", "Error getting data", it)
                }

        }
    }

    fun readLoginText(){
        readFromFirebase += 1

        database.child("QuizWalks").child("LoginText").child("LoginTextKotlin").get()
            .addOnSuccessListener {

                    binding.loginTextTV.text = it.value.toString()

            }
    }

    fun CheckIfBingoPassword():Boolean {
        foundBingoPassword = false

  /*      database.child("BingoName").get()
            .addOnSuccessListener {
                for (snapchild in it.children) {
                    var quizNameChildSnap = snapchild as! DataSnapshot

                    var quizNameChildInfo = quizNameChildSnap.value as! [String : String]

                    if (quizNameChildInfo["Password"] == writtenPasswordBingo.text){}

                }

            }*/

        // kan vara att svar inte kommit utan måste utvärderas lite senare

        // kan vara att om max antal redan var inloggade så bör vi kanske returnera false ??
        return foundBingoPassword
    }

    // Jag hade något problem som jag inte kan förklara, därav denna timer.
    // Problemet var att jag kom till kod för att skriva ner antalet spelare som loggats in mn det verkade aldrig bli skrivet.
    // var inne i läsning samtidigt som jag skrev, ken det vara det som spökade på något sätt?
    fun countdownSecond() {
        object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                if (found){
                    readWhatPlayer()
                }
            }
        }.start()
    }

}
