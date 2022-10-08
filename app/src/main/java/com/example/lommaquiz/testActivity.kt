package com.example.lommaquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import com.example.lommaquiz.databinding.ActivityMainBinding
import com.example.lommaquiz.databinding.ActivityTestBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

private lateinit var mMap55: GoogleMap

class testActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityTestBinding
    var walkNameHere = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        walkNameHere = intent.getStringExtra("walk").toString()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map55) as SupportMapFragment
        mapFragment.getMapAsync(this)

  //      binding.chooseThisWalkBtn

        binding.ChooseWalkBtn55.setOnClickListener {
            walkName = walkNameHere
            finish()
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap55 = googleMap

        val biblan = LatLng(55.6753453411057, 13.069870992431357)
        mMap55.addMarker(MarkerOptions().position(biblan).title("Lomma bibliotek"))
        mMap55.moveCamera(CameraUpdateFactory.newLatLng(biblan))

 //       showWalk()
        startTimer()
    }

    fun startTimer() {
        object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                showWalk()
            }
        }.start()
    }

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

        mMap55.clear()

  //      var themarker = MarkerOptions().position(myPoswalk1).title("Fråga 1").snippet("Fråga 1")
        var themarker = MarkerOptions().position(myPoswalk1).title("Fråga 1")
        mMap55.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk2).title("Fråga 2")
        mMap55.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk3).title("Fråga 3")
        mMap55.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk4).title("Fråga 4")
        mMap55.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk5).title("Fråga 5")
        mMap55.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk6).title("Fråga 6")
        mMap55.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk7).title("Fråga 7")
        mMap55.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk8).title("Fråga 8")
        mMap55.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk9).title("Fråga 9")
        mMap55.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk10).title("Fråga 10")
        mMap55.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk11).title("Fråga 11")
        mMap55.addMarker(themarker)

        themarker = MarkerOptions().position(myPoswalk12).title("Fråga 12")
        mMap55.addMarker(themarker)

        var camMove = CameraUpdateFactory.newLatLngZoom(myPoswalk1, 15.0F)

        mMap55.moveCamera(camMove)
    }




}