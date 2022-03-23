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

        database.child("QuizWalks").child("maps").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            //  for (snapchild in it.children) {
            var i = 0
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



  /*      binding.saveBtn.setOnClickListener {

            var i = 0

            i = binding.coordinateNbrET.text.toString().toInt()

            var letter: String = ""

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

            getLastKnownLocation()

            var shopref = database.child("QuizWalks").child("maps").child("Walk1").child(letter)
                .child("posLatitude")

            shopref.setValue(myPos.latitude.toString())
            var shopref2 = database.child("QuizWalks").child("maps").child("Walk1").child(letter)
                .child("posLongitude")

            shopref2.setValue(myPos.longitude.toString())

        }*/

       /* binding.SeeWalksBtn.setOnClickListener {
            val intent = Intent(this, choseSeeWalks::class.java)

            startActivity(intent)
        }*/
    }

    fun showWalk(){
        for (i in 0..11) {
            val myPoswalk = LatLng(coordinatesLatitude[i].toDouble(), coordinatesLongitude[i].toDouble())


    //        var themarker = MarkerOptions().position( myPoswalk).title("Minc")
            var themarker = MarkerOptions().position(fkip).title("Minc")

            mMap.addMarker(themarker)

         //   var camMove = CameraUpdateFactory.newLatLngZoom( myPoswalk, 20.0F)
            var camMove = CameraUpdateFactory.newLatLngZoom( fkip, 20.0F)

            mMap.moveCamera(camMove)
        }
    }
/*
    fun getLastKnownLocation() {
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
        fusedLocationClient.lastLocation
            .addOnSuccessListener{ location ->
                if (location != null) {

                    val minc = LatLng(location!!.latitude, location!!.longitude)

                    myPos = LatLng(location!!.latitude, location!!.longitude)

                    var themarker = MarkerOptions().position(minc).title("Minc")
                    mMap.addMarker(themarker)

                    var camMove = CameraUpdateFactory.newLatLngZoom(minc, 20.0F)

                    mMap.moveCamera(camMove)

                    var latitudeMin = coordinatesLatitude[0] - 0.00000001
                    var longitudeMin = coordinatesLongitude[0] - 0.00000001
                    var latitudeMax = coordinatesLatitude[0] + 0.00000001
                    var longitudeMax = coordinatesLongitude[0] + 0.00000001


                    var currentLatitude = myPos.latitude.toFloat()
                    var currentLongitude = myPos.longitude.toFloat()


                    var diffenLat = (currentLatitude - coordinatesLatitude[0]) * 20000
                    var diffenLong = (currentLongitude - coordinatesLongitude[0]) * 20000

                    for (i in 0..11) {
                        diffenLat = (currentLatitude - coordinatesLatitude[i]) * 20000
                        diffenLong = (currentLongitude - coordinatesLongitude[i]) * 20000

                        if ((diffenLat < 10) && (diffenLong < 10) && (questionFound[i] == false))
                        {
                            questionFound[i] = true
                            playSound(true)
                        }

                    }

                }
            }
    }*/

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
    /*
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }*/
/*
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        mMap.setOnMarkerClickListener(this)
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
        mMap.isMyLocationEnabled = true


        var themarker = MarkerOptions().position(fkip).title("Marker in Sydney").snippet("Titta lite text")

        mMap.addMarker(themarker)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(fkip))
    }*/

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

   /* override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val markerFkip = MarkerOptions()
            .position(fkip)
            .title("FKIP")
        val markerMonas = MarkerOptions()
            .position(monas)
            .title("Monas")

        map.addMarker(markerFkip)
        map.addMarker(markerMonas)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(monas, 11.6f))

        val fromFKIP = fkip.latitude.toString() + "," + fkip.longitude.toString()
        val toMonas = monas.latitude.toString() + "," + monas.longitude.toString()


        val apiServices = RetrofitClient.apiServices(this)
        apiServices.getDirection(fromFKIP, toMonas, "AIzaSyBknoMXiCPbVK_8nTehEylCk5z7CZZOM0g")
            .enqueue(object : GeolocationPermissions.Callback<DirectionResponses> {
                override fun onResponse(call: Call<DirectionResponses>, response: Response<DirectionResponses>) {
                    drawPolyline(response)
                    Log.d("bisa dong oke", response.message())
                }

                override fun onFailure(call: Call<DirectionResponses>, t: Throwable) {
                   // Log.e("anjir error", t.localizedMessage)
                //    Log.e("anjir error", t.localizedMessage)
                }
            })

    }

    private fun drawPolyline(response: Response<DirectionResponses>) {
        val shape = response.body()?.routes?.get(0)?.overviewPolyline?.points
        val polyline = PolylineOptions()
            .addAll(PolyUtil.decode(shape))
            .width(8f)
            .color(Color.RED)
        map.addPolyline(polyline)
    }

    private interface ApiServices {
        @GET("maps/api/directions/json")
        fun getDirection(@Query("origin") origin: String,
                         @Query("destination") destination: String,
                         @Query("key") apiKey: String): Call<DirectionResponses>
    }

    private object RetrofitClient {
        fun apiServices(context: Context): ApiServices {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(context.resources.getString(R.string.base_url))
                .build()

            return retrofit.create<ApiServices>(ApiServices::class.java)
        }
    }
    */

}

/*

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var fkip: LatLng
    private lateinit var monas: LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    /*    fkip = LatLng(-6.3037978, 106.8693713)
        monas = LatLng(-6.1890511, 106.8251573)*/

        fkip = LatLng(55.67734515844318, 13.076061398152657)
        monas = LatLng(55.675699664601616, 13.065890462255302)

        val mapFragment = maps_view as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val markerFkip = MarkerOptions()
                .position(fkip)
                .title("FKIP")
        val markerMonas = MarkerOptions()
                .position(monas)
                .title("Monas")

        map.addMarker(markerFkip)
        map.addMarker(markerMonas)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(monas, 11.6f))

        val fromFKIP = fkip.latitude.toString() + "," + fkip.longitude.toString()
        val toMonas = monas.latitude.toString() + "," + monas.longitude.toString()

        val apiServices = RetrofitClient.apiServices(this)
        apiServices.getDirection(fromFKIP, toMonas, getString(R.string.api_key))
                .enqueue(object : Callback<DirectionResponses> {
                    override fun onResponse(call: Call<DirectionResponses>, response: Response<DirectionResponses>) {
                        drawPolyline(response)
                        Log.d("bisa dong oke", response.message())
                    }

                    override fun onFailure(call: Call<DirectionResponses>, t: Throwable) {
                        Log.e("anjir error", t.localizedMessage)
                    }
                })

    }

    private fun drawPolyline(response: Response<DirectionResponses>) {
        val shape = response.body()?.routes?.get(0)?.overviewPolyline?.points
        val polyline = PolylineOptions()
                .addAll(PolyUtil.decode(shape))
                .width(8f)
                .color(Color.RED)
        map.addPolyline(polyline)
    }

    private interface ApiServices {
        @GET("maps/api/directions/json")
        fun getDirection(@Query("origin") origin: String,
                         @Query("destination") destination: String,
                         @Query("key") apiKey: String): Call<DirectionResponses>
    }

    private object RetrofitClient {
        fun apiServices(context: Context): ApiServices {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(context.resources.getString(R.string.base_url))
                    .build()

            return retrofit.create<ApiServices>(ApiServices::class.java)
        }
    }
}

 */