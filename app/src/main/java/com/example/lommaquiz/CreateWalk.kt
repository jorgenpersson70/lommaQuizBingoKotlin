package com.example.lommaquiz

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.example.lommaquiz.databinding.ActivityCreateWalkBinding
import com.example.lommaquiz.databinding.ActivityMainBinding
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

var latitude = 0.0f
var longitude = 0.0f
var saveCoordsNumber = 0

class CreateWalk : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mMap10: GoogleMap
    private lateinit var binding: ActivityCreateWalkBinding

    private lateinit var fkip: LatLng
    private lateinit var database: DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateWalkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Firebase.database("https://fire1-95766-default-rtdb.europe-west1.firebasedatabase.app").reference


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

 /*    onödig tror jag
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

                    //              binding.latTV.text = "0"
                    //             binding.longTV.text = "0"

                    if (location != null) {

                        Log.i("MIN", "MY LOCATION " + location!!.latitude.toString() + " longitude " + location!!.longitude.toString())
                    }

                }
        }*/



        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map10) as SupportMapFragment
        mapFragment.getMapAsync(this)

        countdownSecond()

        binding.saveCoordBtn.setOnClickListener {
   //         var masterLoggedIn = false
    //        var authPassedForUser = false
        //
            //    if (masterLoggedIn){

            if (writePermission){
                if (saveCoordsNumber<12) {
                    writeCoords()
                }
                else{
                    binding.statusSaveTV.text = "Alla 12 är sparade"

                    // Jag tror att det är korrekt att göra så
                    var logout = LoginAuthFirebase()
                    logout.logout()
                }
            }
            else{
                binding.statusSaveTV.text = "Du har inte skrivrättighet"
            }
        }

        if (testerLoggedIn){
            binding.showLongTV.visibility = View.VISIBLE
            binding.showLatTV.visibility = View.VISIBLE
        }else{
            binding.showLongTV.visibility = View.INVISIBLE
            binding.showLatTV.visibility = View.INVISIBLE
        }

        binding.infoTroubleTV2.visibility = View.INVISIBLE
        if (questionUser == ""){
            // Then soneone creates a walk for bingo
            binding.walkNameTV.visibility = View.VISIBLE
        }else{
            binding.walkNameTV.visibility = View.INVISIBLE
        }

    }

fun writeCoords(){
    var walkNameSave = ""
    var childCharArray = ArrayList(mutableListOf("A","B","C","D","E","F","G","H","I","J","K","L"))

    var ChildChar = childCharArray[saveCoordsNumber]
    var questNumber = (saveCoordsNumber+1).toString()

    if (questionUser == ""){
        if (binding.walkNameTV.text.toString() == ""){
            binding.infoTroubleTV2.visibility = View.VISIBLE
            binding.infoTroubleTV2.text = "Skriv namn på runda"
            return
        }

        if (binding.walkNameTV.text.toString().length > 15){
            binding.infoTroubleTV2.visibility = View.VISIBLE
            binding.infoTroubleTV2.text = "Namn på runda högst 15 tecken"
            return
        }
        walkNameSave = binding.walkNameTV.text.toString()
    }else{
        walkNameSave = questionUser
    }

    var shopref = database.child("QuizWalks").child("maps").child(walkNameSave).child(ChildChar).child("posLatitude")
    shopref.setValue(latitude)
    shopref = database.child("QuizWalks").child("maps").child(walkNameSave).child(ChildChar).child("posLongitude")
    shopref.setValue(longitude)

    saveCoordsNumber+=1
    binding.statusSaveTV.text = "Koordinat "+saveCoordsNumber.toString()+" sparad"
    if (saveCoordsNumber > 11) {
        binding.statusSaveTV.text = "Alla 12 är sparade"
    }

    fkip = LatLng(latitude.toDouble(), longitude.toDouble())
    var themarker = MarkerOptions().position(fkip).title("Fråga "+questNumber)
    mMap10.addMarker(themarker)
}



    fun countdownSecond() {
        object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                checkNearQuestion()
              //  showWalk()
                    countdownSecond()
            }
        }.start()
    }


    fun showWalk(){

        coordinatesLatitude[0] = 55.66621808535218f
            coordinatesLongitude[0] = 13.067237112053705f

        fkip = LatLng(55.66621808535218, 13.067237112053705)


                var themarker = MarkerOptions().position(fkip).title("Fråga 1")
                mMap10.addMarker(themarker)


        var camMove = CameraUpdateFactory.newLatLngZoom(fkip, 15.0F)

        mMap10.moveCamera(camMove)

    }

    fun checkNearQuestion() {
   //     binding.showLongTV.text = "hej"
   //     binding.showLatTV.text = "hej"

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // jag testar att plocka in denna nedan
            //    doLocationPermission()
            return
        }else {
       //     fusedLocationClient.lastLocation
        //        .addOnSuccessListener { location ->
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    if (location == null) {
                        binding.infoTroubleTV2.visibility = View.VISIBLE
                        binding.infoTroubleTV2.text = "Är access to my location off ?"
                    }
                    if (location != null) {


                        val minc = LatLng(location!!.latitude, location!!.longitude)

                        latitude = location!!.latitude.toFloat()
                        longitude = location!!.longitude.toFloat()

                        myPos = LatLng(location!!.latitude, location!!.longitude)



                        var camMove = CameraUpdateFactory.newLatLngZoom(minc, 20.0F)

               //         var themarker = MarkerOptions().position(myPos).title("Här är du")
                //        mMap10.addMarker(themarker)

                        // kanske
                        mMap10.moveCamera(camMove)

                        binding.showLongTV.text = location!!.longitude.toString()
                        binding.showLatTV.text = location!!.latitude.toString()

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

                    }
                    else{
                        countern += 1

                    }
                }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap10 = googleMap
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

        // testar

  //      showWalk()
        
        mMap10.isMyLocationEnabled = true
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