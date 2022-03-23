package com.example.lommaquiz

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationRequest
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.example.lommaquiz.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

// min AIzaSyBknoMXiCPbVK_8nTehEylCk5z7CZZOM0g
// bill android:value="AIzaSyCUZG-0v3c4XS8ETFAReDiQfsiCSp6JU1c" />

// AIzaSyC3RwBupXyFdul5XtIAWjDsF9f8ogyLam4

var coordinatesLatitude: ArrayList<Float> = ArrayList(mutableListOf(0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f))
var coordinatesLongitude: ArrayList<Float> = ArrayList(mutableListOf(0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f))
var questionFound: ArrayList<Boolean> = ArrayList(mutableListOf(false,false,false,false,false,false,false,false,false,false,false,false))

//var mapNames: ArrayList<String> = ArrayList(mutableListOf("","",""))
var mapNames = ArrayList<String>()

var myPos = LatLng(0.0, 0.0)
var hitCount = 0


class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerDragListener {

    private lateinit var database: DatabaseReference

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMainBinding

    var mediaPlayer : MediaPlayer? = null
    var Mylocation = LatLng(55.611203, 13.994412)

    private lateinit var fkip: LatLng
    private lateinit var fkip2: LatLng
    private lateinit var monas: LatLng
    //fkip = LatLng(55.67734515844318, 13.976061398152657)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

 //       database = Firebase.database("https://kotlin2feb-default-rtdb.europe-west1.firebasedatabase.app").reference


        database = Firebase.database("https://fire1-95766-default-rtdb.europe-west1.firebasedatabase.app").reference


        binding = ActivityMainBinding.inflate(layoutInflater)
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
                .addOnSuccessListener { location : Location? ->

                    binding.latTV.text = "0"
                    binding.longTV.text = "0"

                    if (location != null) {

                        Log.i("MIN", "MY LOCATION " + location!!.latitude.toString() + " longitude " + location!!.longitude.toString())
                    }

                }
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.markerButton.setOnClickListener {
            // 55.6112032506648, 12.994412054721224
            val minc = LatLng(55.6112032506648, 12.994412054721224)

            var themarker = MarkerOptions().position(minc).title("Minc")
            mMap.addMarker(themarker)

            var camMove = CameraUpdateFactory.newLatLngZoom(minc, 20.0F)

            mMap.moveCamera(camMove)
        }

        binding.placeraBtn.setOnClickListener {
            getLastKnownLocation()

            var themarker = MarkerOptions().position(Mylocation).title("Här är vi")
            mMap.addMarker(themarker)

            var camMove = CameraUpdateFactory.newLatLngZoom(Mylocation, 20.0F)

            mMap.moveCamera(camMove)
        }
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

                if (snapchild.key == "Walk3") {
                    Log.i("MIN", "WALK2 " + tempshop.toString())
                }

                mapNames.add(snapchild.key.toString())
             //   mapNames.add("Walk1")
                i += 1

            }

        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }



        binding.saveBtn.setOnClickListener {

            var i = 0

            i = binding.coordinateNbrET.text.toString().toInt()

            var letter : String = ""

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

            getLastKnownLocation()

            var shopref = database.child("QuizWalks").child("maps").child("Walk1").child(letter).child("posLatitude")

            shopref.setValue(myPos.latitude.toString())
            var shopref2 = database.child("QuizWalks").child("maps").child("Walk1").child(letter).child("posLongitude")

            shopref2.setValue(myPos.longitude.toString())

        }

        binding.SeeWalksBtn.setOnClickListener {
            val intent = Intent(this, choseSeeWalks::class.java)

            startActivity(intent)
        }
    }

    fun readMapCoordinates(){
        var count = 0
        //     for (i in 1..12) {
        var letter : String = "A"

// va fan        Log.i("MIN", "mapname "+ mapNames[0])

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

        //    55.67792024926141, 13.076291213315503

     //       database.child("QuizWalks").child("maps").child(mapNames[0]).child(letter).get()
     //           .addOnSuccessListener {
            database.child("QuizWalks").child("maps").child("Walk5").child(letter).get()
                .addOnSuccessListener {

                    val tempCoordinatelist = mutableListOf<mapCoordinates>()

                    for (snapchild in it.children) {

                        var tempshop = snapchild.getValue()!!


                        if (snapchild.key == "posLatitude") {
                            //                         binding.latTV.text = snapchild.value.toString()
                            binding.latTV.text = tempshop.toString()
                            coordinatesLatitude[i] = tempshop.toString().toFloat()

                            //   binding.latTV.text = "HEJ"
                            Log.i("MIN", "LAT " + tempshop.toString())
                        }

                        if (snapchild.key == "posLongitude") {
                            //                binding.longTV.text = snapchild.value.toString()
                            binding.longTV.text = tempshop.toString()
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

        binding.clearBtn.setOnClickListener{
              for (i in 0..11)
              {
                  questionFound[i] = false
              }
          }

    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
        //     Mylocation.latitude = 55.611203
        //          Mylocation.longitude = 12.994412

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
                        binding.showHits.text = hitCount.toString()
                    }

                }

                binding.visaIZonTV.text = diffenLat.toString()
                binding.visaIZonTV2.text = diffenLong.toString()


            }
        }
    }
/*
    fun showWalk(){
        for (i in 0..11) {
            val myPoswalk = LatLng(coordinatesLatitude[i].toDouble(), coordinatesLongitude[i].toDouble())

            var themarker = MarkerOptions().position( myPoswalk).title("Minc")
            mMap.addMarker(themarker)

            var camMove = CameraUpdateFactory.newLatLngZoom( myPoswalk, 20.0F)

            mMap.moveCamera(camMove)
        }
    }*/
fun showWalk(){
  //  binding.latNyTV.text = coordinatesLatitude[0].toString()
  //  binding.longNyTV.text = coordinatesLongitude[0].toString()

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

/*
private fun startLocationUpdates() {
    fusedLocationClient.requestLocationUpdates(locationRequest,
        locationCallback,
        Looper.getMainLooper())
}*/


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

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //      mMap.setMyLocationEnabled(true);
        fkip = LatLng(55.67734515844318, 13.976061398152657)
        fkip2 = LatLng(55.57734515844318, 13.876061398152657)

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
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        mMap.isMyLocationEnabled = true

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        var themarker = MarkerOptions().position(fkip).title("Marker in Sydney").snippet("Titta lite text")

        mMap.addMarker(themarker)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(fkip))

        themarker = MarkerOptions().position(fkip2).title("Marker in Sydney").snippet("Titta lite text")

        mMap.addMarker(themarker)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(fkip2))

        var camMove = CameraUpdateFactory.newLatLngZoom(fkip, 10.0F)

        mMap.moveCamera(camMove)




    }

    override fun onMarkerClick(clickmarker: Marker): Boolean {
        Log.i("PIAXDEBUG", "CLICK ON MARKER " + clickmarker.title)

        clickmarker.showInfoWindow()

        return true
    }

    override fun onInfoWindowClick(clickmarker: Marker) {
        Log.i("PIAXDEBUG", "CLICK ON INFO WINDOW " + clickmarker.title)

        // T.ex öppna detail vy

        clickmarker.hideInfoWindow()
    }

    override fun onMarkerDrag(p0: Marker) {
    }

    override fun onMarkerDragEnd(p0: Marker) {
    }

    override fun onMarkerDragStart(p0: Marker) {
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
                getLastKnownLocation()

                showWalk()

                countdownSecond()
            }
        }.start()
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