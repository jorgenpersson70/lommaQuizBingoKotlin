package com.example.lommaquiz

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.example.lommaquiz.databinding.ActivityShowMapBinding
import com.example.lommaquiz.databinding.ActivityShowWalkBinding
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
import com.google.firebase.ktx.Firebase

//import com.google.android.gms.location.*

//import com.google.android.gms.maps.model.Marker


// You are in a Fragment and you can't use this@MapsActivity. Use requireActivity(). Keep attention if the Fragment is not attached to the Activity, it will throw IllegalStateException
// A fragment has access to the activity it is attached to through its property activity this why (activity as MainActivity) works

var first = true
// class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerDragListener {
class showMap : AppCompatActivity(), OnMapReadyCallback {
//class showMap : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerDragListener {

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
        setContentView(R.layout.activity_show_map)

        database = Firebase.database("https://fire1-95766-default-rtdb.europe-west1.firebasedatabase.app").reference

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        QuizName = intent.getStringExtra("walk").toString()

        fkip = LatLng(55.67734515844318, 13.976061398152657)
        monas = LatLng(55.675699664601616, 13.965890462255302)

        readMapCoordinates()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map9) as SupportMapFragment
        mapFragment.getMapAsync(this)

        countdownSecond()

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap3 = googleMap
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
        mMap3.addMarker(themarker)

 /*       themarker = MarkerOptions().position(myPoswalk2).title("Marker in Sydney").snippet("Titta lite text")
        mMap3.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk3).title("Marker in Sydney").snippet("Titta lite text")
        mMap3.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk4).title("Marker in Sydney").snippet("Titta lite text")
        mMap3.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk5).title("Marker in Sydney").snippet("Titta lite text")
        mMap3.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk6).title("Marker in Sydney").snippet("Titta lite text")
        mMap3.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk7).title("Marker in Sydney").snippet("Titta lite text")
        mMap3.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk8).title("Marker in Sydney").snippet("Titta lite text")
        mMap3.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk9).title("Marker in Sydney").snippet("Titta lite text")
        mMap3.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk10).title("Marker in Sydney").snippet("Titta lite text")
        mMap3.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk11).title("Marker in Sydney").snippet("Titta lite text")
        mMap3.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk12).title("Marker in Sydney").snippet("Titta lite text")
        mMap3.addMarker(themarker)*/

        var camMove = CameraUpdateFactory.newLatLngZoom(myPoswalk1, 16.0F)

        mMap3.moveCamera(camMove)

    }

    fun countdownSecond() {
        object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                getLastKnownLocation()

                if (first) {
   //                 first = false
                    showWalk()
                }

                countdownSecond()
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

    fun readMapCoordinates() {
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

    fun getLastKnownLocation() {
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
        fusedLocationClient.lastLocation
            .addOnSuccessListener{ location ->
                if (location != null) {
                    // use your location object
                    // get latitude , longitude and other info from this
                    //              binding.latTV.text = "1"
                    //              binding.longTV.text = "1"
                    //                binding.latTV.text = location!!.latitude.toFloat().toString()
                    //              binding.longTV.text = location!!.longitude.toFloat().toString()

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


                    var currentLatitude : Float = myPos.latitude.toFloat()
                    var currentLongitude: Float = myPos.longitude.toFloat()

                    var diffenLat : Float = (currentLatitude - coordinatesLatitude[0]) * 10.0f
                    var diffenLong : Float = (currentLongitude - coordinatesLongitude[0]) * 10.0f

                    for (i in 0..11) {
                        diffenLat = (currentLatitude - coordinatesLatitude[i]) * 100000.0f

                        if (diffenLat < 0){
                            diffenLat *= -1
                        }
                        diffenLong = (currentLongitude - coordinatesLongitude[i]) * 100000.0f
                        if (diffenLong < 0){
                            diffenLong *= -1
                        }

                        if ((diffenLat < 30) && (diffenLong < 30) && (questionFound[i] == false))
                        {
                            questionFound[i] = true
                            playSound(true)
                            hitCount += 1
                        }
                    }
                }
            }
    }
}