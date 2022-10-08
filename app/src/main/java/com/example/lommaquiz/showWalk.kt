package com.example.lommaquiz

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationRequest
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.webkit.GeolocationPermissions
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.lommaquiz.databinding.ActivityShowWalkBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


/*
class showWalk : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_walk)
    }
}*/

abstract class showWalk : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerDragListener {

    abstract val googleMap: GoogleMap

    // class showWalk : AppCompatActivity(){
    private lateinit var database: DatabaseReference

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    private lateinit var mMap: GoogleMap
    private lateinit var map: GoogleMap

    private lateinit var binding: ActivityShowWalkBinding

    private lateinit var fkip: LatLng
    private lateinit var monas: LatLng



    var mediaPlayer: MediaPlayer? = null
    var Mylocation = LatLng(55.611203, 13.094412)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        database = Firebase.database("https://fire1-95766-default-rtdb.europe-west1.firebasedatabase.app").reference

        binding = ActivityShowWalkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            doLocationPermission()
        } else {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->

        //            binding.latTV.text = "0"
         //           binding.longTV.text = "0"

                    if (location != null) {

                        Log.i(
                            "MIN",
                            "MY LOCATION " + location!!.latitude.toString() + " longitude " + location!!.longitude.toString()
                        )
                    }

                }
        }

        fkip = LatLng(55.67734515844318, 13.976061398152657)
        monas = LatLng(55.675699664601616, 13.965890462255302)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map2) as SupportMapFragment
        mapFragment.getMapAsync(this)

        countdownSecond()

        countdownTwoSecond()

        readFromFirebase += 1
        database.child("QuizWalks").child("maps").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            //  for (snapchild in it.children) {
            var i = 0
            mapNames.clear()
            for (snapchild in it.children) {

                var tempshop = snapchild.getValue()!!

                if (snapchild.key == "Walk1") {
                    Log.i("MIN", "WALK1 " + tempshop.toString())
                }

                if (snapchild.key == "Walk2") {
                    Log.i("MIN", "WALK2 " + tempshop.toString())
                }

                mapNames.add(snapchild.key.toString())
                i += 1

            }

        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }


    }



    fun showWalk(){


        //      var themarker = MarkerOptions().position(fkip).title("Marker in Sydney").snippet("Titta lite text")
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

        var themarker = MarkerOptions().position(myPoswalk1).title("Marker in Sydney").snippet("Titta lite text")
        mMap.addMarker(themarker)
        //      mMap.moveCamera(CameraUpdateFactory.newLatLng(fkip))

        themarker = MarkerOptions().position(myPoswalk2).title("Marker in Sydney").snippet("Titta lite text")
        mMap.addMarker(themarker)
        //   mMap.moveCamera(CameraUpdateFactory.newLatLng(fkip2))
        themarker = MarkerOptions().position(myPoswalk3).title("Marker in Sydney").snippet("Titta lite text")
        mMap.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk4).title("Marker in Sydney").snippet("Titta lite text")
        mMap.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk5).title("Marker in Sydney").snippet("Titta lite text")
        mMap.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk6).title("Marker in Sydney").snippet("Titta lite text")
        mMap.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk7).title("Marker in Sydney").snippet("Titta lite text")
        mMap.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk8).title("Marker in Sydney").snippet("Titta lite text")
        mMap.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk9).title("Marker in Sydney").snippet("Titta lite text")
        mMap.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk10).title("Marker in Sydney").snippet("Titta lite text")
        mMap.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk11).title("Marker in Sydney").snippet("Titta lite text")
        mMap.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk12).title("Marker in Sydney").snippet("Titta lite text")
        mMap.addMarker(themarker)



        var camMove = CameraUpdateFactory.newLatLngZoom(myPoswalk1, 20.0F)

        mMap.moveCamera(camMove)

    }

    fun playSound(start : Boolean) {

        var resId = getResources().getIdentifier(
            R.raw.tal.toString(),
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

    fun readMapCoordinates(){
        readFromFirebase += 1

        var count = 0
        //     for (i in 1..12) {
        var letter : String = "A"

        Log.i("MIN", "mapname "+ mapNames[0])

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

            database.child("QuizWalks").child("maps").child(mapNames[0]).child(letter).get()
                .addOnSuccessListener {
                    //         database.child("QuizWalks").child("maps").child("Walk1").child("A").get()
                    //             .addOnSuccessListener {

                    val tempCoordinatelist = mutableListOf<mapCoordinates>()

                    for (snapchild in it.children) {

                        var tempshop = snapchild.getValue()!!


                        if (snapchild.key == "posLatitude") {
                            //                         binding.latTV.text = snapchild.value.toString()
     //                       binding.latTV.text = tempshop.toString()
     //                       coordinatesLatitude[i] = tempshop.toString().toFloat()

                            //   binding.latTV.text = "HEJ"
                            Log.i("MIN", "LAT " + tempshop.toString())
                        }

                        if (snapchild.key == "posLongitude") {
                            //                binding.longTV.text = snapchild.value.toString()
     //                       binding.longTV.text = tempshop.toString()
      //                      coordinatesLongtitude[i] = tempshop.toString().toFloat()

                            //     binding.longTV.text = "SVEJS"
                            Log.i("MIN", "LONG " + tempshop.toString())
                        }

                        count += 1

                    }

                }.addOnFailureListener {
                    Log.e("firebase", "Error getting data", it)
                }

            //      database.child("QuizWalks").child("maps").child("Walk1").child("B").get()
            //          .addOnSuccessListener {
        }

      /*  binding.clearBtn.setOnClickListener{
            for (i in 0..11)
            {
                questionFound[i] = false
            }
        }*/

    }

    fun countdownSecond() {
        object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
           //     getLastKnownLocation()

                showWalk()

                countdownSecond()
            }
        }.start()
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        mMap = googleMap
        return super.onCreateView(name, context, attrs)
    }


    fun countdownTwoSecond() {
        object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {


                readMapCoordinates()
                countdownTwoSecond()
            }
        }.start()
    }

}

