package com.example.lommaquiz

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.lommaquiz.databinding.ActivityShowMapBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


//import com.google.android.gms.location.*

//import com.google.android.gms.maps.model.Marker

// lägenhet 55.677360339457614, 13.075819363351659

// You are in a Fragment and you can't use this@MapsActivity. Use requireActivity(). Keep attention if the Fragment is not attached to the Activity, it will throw IllegalStateException
// A fragment has access to the activity it is attached to through its property activity this why (activity as MainActivity) works

var first = true
var showMarker = 1
var questFound = 0
var blockShowQuestion = false
var walkFiniched = false
var blockWalkFiniched = false
var cheat = false
var blockCountDown = false

var countReadtv = 0
var countRead2tv = 0

var countern = 0

var countOnceMore = 0

var lastCheckNearLat = ""
var lastCheckNearLong = ""

// class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerDragListener {
//class showMap : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerDragListener {

class showMap : AppCompatActivity(), OnMapReadyCallback {

  //  private var broadcastReceiver: BroadcastReceiver? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var mediaPlayer : MediaPlayer? = null

    private lateinit var QuizName : String
    private lateinit var binding: ActivityShowMapBinding
    private lateinit var database: DatabaseReference
    private lateinit var map: GoogleMap
    private lateinit var mMap3: GoogleMap

    private lateinit var fkip: LatLng
    private lateinit var monas: LatLng



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShowMapBinding.inflate(layoutInflater)
        setContentView(binding.root)


        blockWalkFiniched = false
        walkFiniched = false
        blockShowQuestion = false
        questFound = 0
        showMarker = 1
        cheat = false
        blockCountDown = false

        clearReadFlags()

        database = Firebase.database("https://fire1-95766-default-rtdb.europe-west1.firebasedatabase.app").reference

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        QuizName = intent.getStringExtra("walk").toString()

        if (BingoPasswordFound){
            if (connectToWalk != "")
            {
                walkName = connectToWalk
                binding.markerLongTV.text = bingoName + " " + walkName
            }
        }else{
            binding.markerLongTV.text = QuizName
        }

        fkip = LatLng(55.67734515844318, 13.976061398152657)
        monas = LatLng(55.675699664601616, 13.965890462255302)

        readMapCoordinates()

        // testar
        readQuestions()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map9) as SupportMapFragment
        mapFragment.getMapAsync(this)

        var testa = findViewById<Button>(R.id.nextQuestBtn)

        if (testerLoggedIn){
            testa.visibility = View.VISIBLE
            binding.jumpBtn.visibility = View.VISIBLE
            binding.longTV.visibility = View.VISIBLE
            binding.latTV.visibility = View.VISIBLE
        }
        else{
            testa.visibility = View.INVISIBLE
            binding.jumpBtn.visibility = View.INVISIBLE
            binding.longTV.visibility = View.INVISIBLE
            binding.latTV.visibility = View.INVISIBLE
        }


        testa.setOnClickListener {
            cheat = true
        }

        binding.jumpBtn.setOnClickListener {
            val intent = Intent(this, newfront::class.java)

            startActivity(intent)
        }

        binding.quitQuizBtn.setOnClickListener {
            blockCountDown = true
            clearReadFlags()
            finish()
        }

        countdownSecond()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onRestart() {
        super.onRestart()
            countdownSecond()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    override fun onPostResume() {
        super.onPostResume()
    }

    override fun onResume() {
        super.onResume()
    }


    ///
    fun readQuestions(){

        var questionStringText = ""
        var questionStringNumber = ""

        for (i in 1..12) {
            when (i) {
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
            //     test.text = questionStringNumber

            readFromFirebase += 1
            //     database.child("QuizWalks").child("QuizNames").child(QuizName).child("Fråga A").get().addOnSuccessListener {
            database.child("QuizWalks").child("QuizNames").child(QuizName).child(questionStringText)
                .get().addOnSuccessListener {
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

                    }
                    if (snapchild.key == "Answer 2") {
                        Log.i("MIN", "Answer 2 " + snapchild.value.toString())
                        quizAnswer2[questionNumber] = snapchild.getValue().toString()

                    }
                    if (snapchild.key == "Answer 3") {
                        Log.i("MIN", "Answer 3 " + snapchild.value.toString())
                        quizAnswer3[questionNumber] = snapchild.getValue().toString()

                    }
                    if (snapchild.key == "Correct Answer") {
                        Log.i("MIN", "Correct Answer " + snapchild.value.toString())
                        quizCorrectAnswer[questionNumber] = snapchild.getValue().toString()
                    }
                    if (snapchild.key == "Fråga") {
                        Log.i("MIN", "Question " + snapchild.value.toString())
                        quizQuestion[questionNumber] = snapchild.getValue().toString()

                    }
                    if (snapchild.key == "URLString") {
                        Log.i("MIN", "URLString " + snapchild.value.toString())
                        quizURLString[questionNumber] = snapchild.getValue().toString()
                        /*                if (quizURLString[showMarker-1] == "")
                                    {
                                        binding.listenUrlBtn.visibility = View.INVISIBLE
                                    }
                                    else
                                    {
                                        binding.listenUrlBtn.visibility = View.VISIBLE
                                    }*/
                    }
                }

            }.addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
            }

        }
    }

    ///


// block back otherwise we get into trouble
    override fun onBackPressed()
    {
        // have to stop the counter from running another loop
    //    blockCountDown = true
    //    finish()
    }

    // maybe not correct to clear flags here
    fun clearReadFlags(){
        for (i in 0..12){
            questionFound[i] = false
            // kanske
            givenAnswer[i] = ""
        }
    }



    override fun onMapReady(googleMap: GoogleMap) {
        mMap3 = googleMap
 //       mMap3.setOnInfoWindowClickListener(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        mMap3.isMyLocationEnabled = true
    }


    fun showWalk(){
        //      var themarker = MarkerOptions().position(fkip).title("Marker in Sydney").snippet("Titta lite text")
        var myPoswalkShow = LatLng(coordinatesLatitude[0].toDouble(), coordinatesLongitude[0].toDouble())

        val myPoswalk1 = LatLng(coordinatesLatitude[0].toDouble(), coordinatesLongitude[0].toDouble())
        val myPoswalk2 = LatLng(coordinatesLatitude[1].toDouble(), coordinatesLongitude[1].toDouble())
        val myPoswalk3 = LatLng(coordinatesLatitude[2].toDouble(), coordinatesLongitude[2].toDouble())
        val myPoswalk4 = LatLng(coordinatesLatitude[3].toDouble(), coordinatesLongitude[3].toDouble())
        val myPoswalk5 = LatLng(coordinatesLatitude[4].toDouble(), coordinatesLongitude[4].toDouble())
        val myPoswalk6 = LatLng(coordinatesLatitude[5].toDouble(), coordinatesLongitude[5].toDouble())
        val myPoswalk7 = LatLng(coordinatesLatitude[6].toDouble(), coordinatesLongitude[6].toDouble())
        val myPoswalk8 = LatLng(coordinatesLatitude[7].toDouble(), coordinatesLongitude[7].toDouble())
        val myPoswalk9 = LatLng(coordinatesLatitude[8].toDouble(), coordinatesLongitude[8].toDouble())
        val myPoswalk10 = LatLng(coordinatesLatitude[9].toDouble(), coordinatesLongitude[9].toDouble())
        val myPoswalk11 = LatLng(coordinatesLatitude[10].toDouble(), coordinatesLongitude[10].toDouble())
        val myPoswalk12 = LatLng(coordinatesLatitude[11].toDouble(), coordinatesLongitude[11].toDouble())



        when (showMarker) {
            1 -> {
            //    var themarker = MarkerOptions().position(myPoswalk1).title("Fråga 1").snippet("Fråga 1")
             //   var themarker = MarkerOptions().position(myPoswalk1).title("Fråga 1")
                var themarker = MarkerOptions().position(myPoswalk1).title("Fråga 1")
                mMap3.addMarker(themarker)
                myPoswalkShow = myPoswalk1
            }
            2 -> {
        //        mMap3 = googleMap
                mMap3.clear()
                var themarker = MarkerOptions().position(myPoswalk2).title("Fråga 2")
                mMap3.addMarker(themarker)
                myPoswalkShow = myPoswalk2
            }
            3 -> {
                mMap3.clear()
                var themarker = MarkerOptions().position(myPoswalk3).title("Fråga 3")
                mMap3.addMarker(themarker)
                myPoswalkShow = myPoswalk3
            }
            4 -> {
                mMap3.clear()
                var themarker = MarkerOptions().position(myPoswalk4).title("Fråga 4")
                mMap3.addMarker(themarker)
                myPoswalkShow = myPoswalk4
            }
            5 -> {
                mMap3.clear()
                var themarker = MarkerOptions().position(myPoswalk5).title("Fråga 5")
                mMap3.addMarker(themarker)
                myPoswalkShow = myPoswalk5
            }
            6 -> {
                mMap3.clear()
                var themarker = MarkerOptions().position(myPoswalk6).title("Fråga 6")
                mMap3.addMarker(themarker)
                myPoswalkShow = myPoswalk6
            }
            7 -> {
                mMap3.clear()
                var themarker = MarkerOptions().position(myPoswalk7).title("Fråga 7")
                mMap3.addMarker(themarker)
                myPoswalkShow = myPoswalk7
            }
            8 -> {
                mMap3.clear()
                var themarker = MarkerOptions().position(myPoswalk8).title("Fråga 8")
                mMap3.addMarker(themarker)
                myPoswalkShow = myPoswalk8
            }
            9 -> {
                mMap3.clear()
                var themarker = MarkerOptions().position(myPoswalk9).title("Fråga 9")
                mMap3.addMarker(themarker)
                myPoswalkShow = myPoswalk9
            }
            10 -> {
                mMap3.clear()
                var themarker = MarkerOptions().position(myPoswalk10).title("Fråga 10")
                mMap3.addMarker(themarker)
                myPoswalkShow = myPoswalk10
            }
            11 -> {
                mMap3.clear()
                var themarker = MarkerOptions().position(myPoswalk11).title("Fråga 11")
                mMap3.addMarker(themarker)
                myPoswalkShow = myPoswalk11
            }
            12 -> {
                mMap3.clear()
                var themarker = MarkerOptions().position(myPoswalk12).title("Fråga 12")
                mMap3.addMarker(themarker)
                myPoswalkShow = myPoswalk12
            }
            else -> {
                // testar
                mMap3.clear()
                var themarker = MarkerOptions().position(myPoswalk12).title("Fråga 12")
                mMap3.addMarker(themarker)
                myPoswalkShow = myPoswalk12

            }
        }

       // var camMove = CameraUpdateFactory.newLatLngZoom(myPoswalk1, 16.0F)
        var camMove = CameraUpdateFactory.newLatLngZoom(myPoswalkShow, 16.0F)

        mMap3.moveCamera(camMove)

    }

    fun showQuestion(){

        var showMarkerString = ""

        if (!blockShowQuestion) {
 // temp bort           playSound(true)
            blockShowQuestion = true

            // testar

            blockCountDown = true

 //           readQuestion()
            // Have to wait for answer

   // var bingo = true

            if (quizURLString[showMarker-1] == "") {
                // Only text, no picture
                if (BingoPasswordFound){
                    val intent = Intent(this, ShowBingoCharts::class.java)
                    showMarkerString = showMarker.toString()
                    intent.putExtra("QuestionNumber", showMarkerString)

                    startActivity(intent)
                }else {
                    val intent = Intent(this, showQuestions3::class.java)
                    intent.putExtra("QuizName", QuizName)
                    startActivity(intent)
                }
            }
            else{

                if ((".jpg" in quizURLString[showMarker - 1]) || (".jpeg" in quizURLString[showMarker - 1])) {
                    // Text and picture
                    val intent = Intent(this, showQuestions::class.java)
                    intent.putExtra("QuizName", QuizName)
                    intent.putExtra("URL", quizURLString[showMarker-1])
                    startActivity(intent)
                } else {
                    // Only text, no picture
                    val intent = Intent(this, showQuestions3::class.java)
                    intent.putExtra("QuizName", QuizName)
                    startActivity(intent)

                    print("hej")
                }

            }


            //intent.putExtra("walk", QuizName)
            // intent.putExtra("question", )

        }
    }



    fun showResultFunction(){
        val intent = Intent(this, showResult::class.java)

        //     intent.putExtra("walk", "Walk5")

     //   startActivity(intent)
            startActivity(intent)
    }

    fun countdownSecond() {
        object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                if (questFound == 0) {
                    checkNearQuestion()
                }
 /*               if (questFound != 0)
                {
                    playSoundSiren(true)
                    showQuestion()
                }*/

                if (!blockWalkFiniched) {
                    showWalk()
                }

                if (!blockWalkFiniched) {
                    if (walkFiniched) {
                        blockWalkFiniched = true
                        if (BingoPasswordFound){

                        }else {
                            showResultFunction()
                        }
                    }
                }

// aha här är det nog
                if (!blockCountDown) {
                    countdownSecond()
                }
            }
        }.start()
    }

    fun playSound(start : Boolean) {

        var resId = getResources().getIdentifier(
            R.raw.pling.toString(),
            "raw", this?.packageName
        )


        if (start) {
            if(mediaPlayer == null)
            {
                mediaPlayer = MediaPlayer.create(this, resId)
                mediaPlayer!!.start()
            } else {
                mediaPlayer!!.seekTo(0)
                mediaPlayer!!.start()
            }
        } else {

            if(mediaPlayer != null)
            {
                mediaPlayer!!.pause()
            }
        }
    }

    fun playSoundSiren(start : Boolean) {

        var resId = getResources().getIdentifier(
  //          R.raw.siren.toString(),
  //          R.raw.orn.toString(),
            R.raw.sparv.toString(),

            "raw", this?.packageName
        )


        if (start) {
            if(mediaPlayer == null)
            {
                mediaPlayer = MediaPlayer.create(this, resId)
                mediaPlayer!!.start()
            } else {
                mediaPlayer!!.seekTo(0)
                mediaPlayer!!.start()
            }
        } else {

            if(mediaPlayer != null)
            {
                mediaPlayer!!.pause()
            }
        }
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


            database.child("QuizWalks").child("maps").child(walkName.toString()).child(letter).get()
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

    fun checkNearQuestion() {

  //      countReadtv += 1
   //     binding.countReadTV.text = countReadtv.toString()
  //      binding.latTV.text = "latTV"
   //     binding.longTV.text = "longTV"

        if (cheat){
                hitCount += 1
                questFound = showMarker

                playSoundSiren(true)
                showQuestion()
        }


        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
        //    doLocationPermission()
     //       binding.longTV.text = "oj"
            return
        }else {
     //       binding.longTV.text = "oj2"

            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->


     //  fusedLocationClient.lastLocation
    //        .addOnSuccessListener { location ->
        //        binding.longTV.text = "oj3"
                        if (location == null) {
                            binding.infoTroubleTV.text = "Är access to my location off ?"
                        }
                if (location != null) {
                    binding.longTV.text = "oj4"
                    // use your location object
                    // get latitude , longitude and other info from this
                    //              binding.latTV.text = "1"
                    //              binding.longTV.text = "1"
                    //                binding.latTV.text = location!!.latitude.toFloat().toString()
                    //              binding.longTV.text = location!!.longitude.toFloat().toString()

       //             countRead2tv += 1
        //            binding.countRead2TV.text = countRead2tv.toString()

                    val minc = LatLng(location!!.latitude, location!!.longitude)

                    myPos = LatLng(location!!.latitude, location!!.longitude)

                    //            var themarker = MarkerOptions().position(minc).title("Minc")
                    //          mMap.addMarker(themarker)

                    var camMove = CameraUpdateFactory.newLatLngZoom(minc, 20.0F)

                    //            mMap.moveCamera(camMove)

                    var latitudeMin = coordinatesLatitude[0] - 0.00000001
                    var longitudeMin = coordinatesLongitude[0] - 0.00000001
                    var latitudeMax = coordinatesLatitude[0] + 0.00000001
                    var longitudeMax = coordinatesLongitude[0] + 0.00000001


                    var currentLatitude: Float = myPos.latitude.toFloat()
                    var currentLongitude: Float = myPos.longitude.toFloat()

                    var lastCheckNearLat = myPos.latitude.toString()
                    var lastCheckNearLong = myPos.longitude.toString()

                    var diffenLat: Float = (currentLatitude - coordinatesLatitude[0]) * 10.0f
                    var diffenLong: Float = (currentLongitude - coordinatesLongitude[0]) * 10.0f

       //             questFound = 0
                    binding.latTV.text = myPos.latitude.toString()
                    binding.longTV.text = myPos.longitude.toString()


  //                  binding.markerLatTV.text = coordinatesLatitude[showMarker-1].toString()
    //                binding.markerLongTV.text = coordinatesLongitude[showMarker-1].toString()


                    // test

              //      playSound(true)
          //          playSoundSiren(true)

                    for (i in 0..11) {
                        diffenLat = (currentLatitude - coordinatesLatitude[i]) * 100000.0f

                        if (diffenLat < 0) {
                            diffenLat *= -1
                        }
                        diffenLong = (currentLongitude - coordinatesLongitude[i]) * 100000.0f
                        if (diffenLong < 0) {
                            diffenLong *= -1
                        }

                        // have to take them in order.
                        if (showMarker == (i + 1)) {
                            if ((diffenLat < 30) && (diffenLong < 30) && (questionFound[showMarker] == false)) {
                                questionFound[showMarker] = true

                                hitCount += 1
                                questFound = showMarker

                                // testar

                                playSoundSiren(true)


                                // testar det här
         /// tar bort denna på prov                       blockCountDown = true
                                showQuestion()

               //                 binding.hitTV.text = "hit"

 //                               playSoundSiren(true)

                            }

        //                    countern += 1
                     //       binding.latTV.text = diffenLat.toString()
                        //    binding.longTV.text = diffenLong.toString()
             //               binding.counterTV.text = countern.toString()
                        }
                    }
                }
                else{
                    countern += 1
                    //       binding.latTV.text = diffenLat.toString()
                    //    binding.longTV.text = diffenLong.toString()
             //       binding.counterTV.text = countern.toString()
                }
            }
            }
    }

    fun doLocationPermission()
    {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // Precise location access granted.
                    Log.i("PIAXDEBUG", "PERMISSION ACCESS_FINE_LOCATION")
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                    Log.i("PIAXDEBUG", "PERMISSION ACCESS_COARSE_LOCATION")
                } else -> {
                // No location access granted.
                Log.i("PIAXDEBUG", "PERMISSION DENY!!")
            }
            }
        }

        // ...

        // Before you perform the actual permission request, check whether your app
        // already has the permissions, and whether your app needs to show a permission
        // rationale dialog. For more details, see Request permissions.
        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))
    }


}