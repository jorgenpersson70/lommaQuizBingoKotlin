package com.example.lommaquiz

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
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

var justOnce = true
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var database: DatabaseReference

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private lateinit var fkip: LatLng
    private lateinit var fkip2: LatLng

    private lateinit var walkName : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        walkName = intent.getStringExtra("walk").toString()

   //     database = Firebase.database("https://kotlin2feb-default-rtdb.europe-west1.firebasedatabase.app").reference
        database = Firebase.database("https://fire1-95766-default-rtdb.europe-west1.firebasedatabase.app").reference

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map4) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.drawMapBtn.setOnClickListener {
            val minc = LatLng(55.6112032506648, 12.994412054721224)

            var themarker = MarkerOptions().position(minc).title("Minc")
            mMap.addMarker(themarker)

            var camMove = CameraUpdateFactory.newLatLngZoom(minc, 15.0F)

            mMap.moveCamera(camMove)
        }

        readMapCoordinates()
        countdownSecond()


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

      /*  mMap.setOnMarkerClickListener(this@activity_maps)
        mMap.setOnInfoWindowClickListener(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }

        mMap.isMyLocationEnabled = true*/

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
             mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
             mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
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

    fun countdownSecond() {
        object : CountDownTimer(5000, 1000) {
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
    }

    fun showWalk(){
        binding.latNyTV.text = coordinatesLatitude[0].toString()
        binding.longNyTV.text = coordinatesLongitude[0].toString()

   /*     for (i in 0..1) {
            val myPoswalk = LatLng(coordinatesLatitude[i].toDouble(), coordinatesLongitude[i].toDouble())

            var themarker = MarkerOptions().position( myPoswalk).title("Minc")
            mMap.addMarker(themarker)

            var camMove = CameraUpdateFactory.newLatLngZoom( myPoswalk, 30.0F)

            mMap.moveCamera(camMove)
        }*/

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



        var camMove = CameraUpdateFactory.newLatLngZoom(myPoswalk1, 15.0F)

        mMap.moveCamera(camMove)

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

            //walkName
   //         database.child("QuizWalks").child("maps").child(mapNames[0]).child(letter).get()
   //             .addOnSuccessListener {
            database.child("QuizWalks").child("maps").child(walkName).child(letter).get()
                .addOnSuccessListener {
                    //         database.child("QuizWalks").child("maps").child("Walk1").child("A").get()
                    //             .addOnSuccessListener {

                    val tempCoordinatelist = mutableListOf<mapCoordinates>()

                    for (snapchild in it.children) {

                        var tempshop = snapchild.getValue()!!


                        if (snapchild.key == "posLatitude") {
                            //                         binding.latTV.text = snapchild.value.toString()
                            //                         binding.latTV.text = tempshop.toString()
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

            //      database.child("QuizWalks").child("maps").child("Walk1").child("B").get()
            //          .addOnSuccessListener {
        }
    }
}