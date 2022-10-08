package com.example.lommaquiz

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.lommaquiz.databinding.ActivityMapsBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.math.roundToInt

var justOnce = true
var walkName : String = "Walk1"
var oneTimerStarted = false
private lateinit var mMap: GoogleMap

var coordinatesReadCount = 0

var onCreateVar = 0
var onMapReadyVar = 0
var coordinatesRead = false

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private var timerStarted = false
    private lateinit var serviceIntent : Intent
  //  private  var serviceIntent? : Intent? = null
    private var time = 0.0

    private lateinit var database: DatabaseReference

//    private lateinit var mMap: GoogleMap
 //   private lateinit var binding: Ac

    private lateinit var fkip: LatLng
    private lateinit var fkip2: LatLng

    var countSeconds = 0
    var walkNameHere = ""

  //  private CountDownTimer : mytimer



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        serviceIntent = Intent(applicationContext, TimerService::class.java)
        registerReceiver(updateTime, IntentFilter(TimerService.TIMER_UPDATED))


        walkNameHere = intent.getStringExtra("walk").toString()




   //     database = Firebase.database("https://kotlin2feb-default-rtdb.europe-west1.firebasedatabase.app").reference
        database = Firebase.database("https://fire1-95766-default-rtdb.europe-west1.firebasedatabase.app").reference

  /*      binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)*/



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map4) as SupportMapFragment
        mapFragment.getMapAsync(this)

  //      readMapCoordinates()

    }

    override fun onStart() {
        super.onStart()
      //  startStopTimer()
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
     //   startStopTimer()
        return super.onCreateView(name, context, attrs)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
  //      startStopTimer()
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onActivityReenter(resultCode: Int, data: Intent?) {
  //      startStopTimer()
        super.onActivityReenter(resultCode, data)
    }

    override fun onResume() {
    //    startStopTimer()

        super.onResume()
    }

    override fun onBackPressed()
    {

        resetTimer()
        countSeconds = 0
  //      justOnce = true




        finish()
    }

  /*  fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }*/


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


  //  override fun onBackPressed()

    override fun onMapReady(googleMap: GoogleMap) {

//
    //    if (!oneTimerStarted){
            mMap = googleMap
   //     }



        // biblan 55.6753453411057, 13.069870992431357

        val biblan = LatLng(55.6753453411057, 13.069870992431357)
             mMap.addMarker(MarkerOptions().position(biblan).title("Lomma bibliotek"))
             mMap.moveCamera(CameraUpdateFactory.newLatLng(biblan))


        startStopTimer()

        justOnce = true

        coordinatesRead = false
        readMapCoordinates()

        // krasch ?
        if (!oneTimerStarted) {
            showWalk()
        }

        onMapReadyVar += 1


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
/*
    fun countdownSecond() {
        object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {

                fkip = LatLng(55.67734515844318, 13.976061398152657)
                fkip2 = LatLng(55.57734515844318, 13.876061398152657)

                if (justOnce) {
                   justOnce = false
                    showWalk()
                }
                countdownSecond()
            }
        }.start()
    }*/

    fun showWalk(){
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

        mMap.clear()

        var themarker = MarkerOptions().position(myPoswalk1).title("Fråga 1")
  //      var themarker = MarkerOptions().position(myPoswalk1).title("Fråga 1")
        mMap.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk2).title("Fråga 2")
        mMap.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk3).title("Fråga 3")
        mMap.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk4).title("Fråga 4")

        themarker = MarkerOptions().position(myPoswalk5).title("Fråga 5")
        mMap.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk6).title("Fråga 6")
        mMap.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk7).title("Fråga 7")
        mMap.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk8).title("Fråga 8")
        mMap.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk9).title("Fråga 9")
        mMap.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk10).title("Fråga 10")
        mMap.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk11).title("Fråga 11")
        mMap.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk12).title("Fråga 12")
        mMap.addMarker(themarker)

        var camMove = CameraUpdateFactory.newLatLngZoom(myPoswalk1, 15.0F)

        mMap.moveCamera(camMove)
    }



    fun readMapCoordinates() {

        readFromFirebase += 1

        var count = 0

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


            if (walkNameHere == "malin") {
                database.child("QuizWalks").child("maps").child("malin").child(letter).get()
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

        }else{
            database.child("QuizWalks").child("maps").child(walkNameHere).child(letter).get()
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

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver()
    {
        override fun onReceive(context: Context?, intent: Intent) {
            time = intent.getDoubleExtra(TimerService.TIMER_EXTRA, 0.0)

            fkip = LatLng(55.67734515844318, 13.976061398152657)
            fkip2 = LatLng(55.57734515844318, 13.876061398152657)

            countSeconds += 1

            // first time and then every 10 seconds
       //     if ((countSeconds == 1) || ((countSeconds % 10) == 1))


          if ((countSeconds % 4) == 2)
            {
                if ((justOnce) && (coordinatesRead)) {
                    justOnce = false
                    showWalk()
                }
            }

   /*         if (justOnce) {
                if ((countSeconds % 5) == 4)
               {

                    justOnce = false
                    showWalk()
                }
            }*/


        }
    }

    private fun getTimeStringFromDouble(time: Double): String{
        val resultInt = time.roundToInt()
        val hours = resultInt % 86488 / 3600
        val minutes = resultInt % 86488 % 3600 / 60
        val seconds = resultInt % 86488 % 3600 % 60

        return makeTimeString(hours, minutes, seconds)
    }

    private fun makeTimeString(hour: Int, min: Int, sec: Int): String = String.format("%02d:%02d:%02d", hour, min, sec)

    private fun resetTimer(){
        stopTimer()
        time = 0.0
        countSeconds = 0


    // testar
    //    justOnce = true
    }

    private fun startStopTimer(){
    //    if (!oneTimerStarted) {
            if (timerStarted)
                stopTimer()
            else
                startTimer()
    //    }
    }

    private fun startTimer() {
    //    if (!oneTimerStarted) {
            oneTimerStarted = true
            serviceIntent.putExtra(TimerService.TIMER_EXTRA, time)
            startService(serviceIntent)

            //     binding.startStopBtn.icon =
            timerStarted = true
   //     }
    }

    private fun stopTimer() {
        serviceIntent.putExtra(TimerService.TIMER_EXTRA, time)
        stopService(serviceIntent)

        //     binding.startStopBtn.icon =
        timerStarted = false
    }
}