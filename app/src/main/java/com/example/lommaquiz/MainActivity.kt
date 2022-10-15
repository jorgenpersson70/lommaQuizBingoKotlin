package com.example.lommaquiz

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.location.LocationRequest
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.CountDownTimer
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.lommaquiz.databinding.ActivityMainBinding
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.IOException


//If background location access is essential for your app, keep in mind that Android preserves device
// battery life by setting background location limits on devices that run Android 8.0 (API level 26)
// and higher. On these versions of Android, if your app is running in the background, it can receive
// location updates only a few times each hour. Learn more about background location limits.

// public abstract ComponentName startForegroundService (Intent service)
// stopService(Intent)
// The system allows apps to call Context.startForegroundService() even while the app is in the
// background. However, the app must call that service's startForeground() method within five seconds
// after the service is created.
// Solution: Call startForeground() in onCreate() for the Service which you use
// Context.startForegroundService()


// bara för test, bort sen. fundera hur vi skall hantera att testar får hitta alla koordinater
var debuggHomeTest = false
var debuggHomeTestGrey = false


// hemma 55.6773742895471, 13.075809329729376
// indu slättängs 55.67786126949259, 13.07625457640789
// 55.67806392395678, 13.076372593599787
// 55.6784601557932, 13.076549619407539
// 55.678221207309505, 13.07454869145862
// 55.67836639136799, 13.074232190807628
// 55.67848132836547, 13.0739425122457
// 55.67700224488378, 13.075830787337244
// 55.67707786400454, 13.075879067097567
// 55.677180705774184, 13.07591661802226
// 55.67726237404571, 13.075970262200395
// 55.67735009162894, 13.075991719871649

// biblan 55.6753453411057, 13.069870992431357

// koordinater skolan
// lektion 55.61155661499599, 12.994391125764585
// entre 55.61122483307453, 12.994186150366396





// min AIzaSyBknoMXiCPbVK_8nTehEylCk5z7CZZOM0g
// bill android:value="AIzaSyCUZG-0v3c4XS8ETFAReDiQfsiCSp6JU1c" />

// AIzaSyC3RwBupXyFdul5XtIAWjDsF9f8ogyLam4

var coordinatesLatitude: ArrayList<Float> = ArrayList(mutableListOf(0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f))
var coordinatesLongitude: ArrayList<Float> = ArrayList(mutableListOf(0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f))

var BingoValueB = ArrayList(mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15))
var BingoValueI = ArrayList(mutableListOf(16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30))
var BingoValueN = ArrayList(mutableListOf(31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45))
var BingoValueG = ArrayList(mutableListOf(46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60))
var BingoValueO = ArrayList(mutableListOf(61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75))


//var mapNames: ArrayList<String> = ArrayList(mutableListOf("","",""))
var mapNames = ArrayList<String>()

var myPos = LatLng(0.0, 0.0)
var hitCount = 0

var readFromFirebase = 0
var takeAwayButtonToChooseRunda = false
var takeAwayButtonStartBingo = true
var showBingoGrattis = false

//class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerDragListener {
class MainActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    private lateinit var binding: ActivityMainBinding

    var mediaPlayer: MediaPlayer? = null
    var Mylocation = LatLng(55.611203, 13.994412)

    private lateinit var fkip: LatLng
    private lateinit var fkip2: LatLng
    private lateinit var monas: LatLng




   // var frontPic = findViewById<ImageView>(R.id.frontImage)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        database =
            Firebase.database("https://fire1-95766-default-rtdb.europe-west1.firebasedatabase.app").reference

        //  FirebaseStorage storage = FirebaseStorage.getInstance();

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        BingoValueB.shuffle()
        BingoValueI.shuffle()
        BingoValueN.shuffle()
        BingoValueG.shuffle()
        BingoValueO.shuffle()


        var myWidth = Resources.getSystem().getDisplayMetrics().widthPixels
        var myHeight = Resources.getSystem().getDisplayMetrics().heightPixels
        Log.i("MIN", "LAT " + myWidth.toString())
        Log.i("MIN", "LAT " + myHeight.toString())


        var frontPic = binding.frontImage

        // I got in some problems when I used the same image for different sizes
        if (myWidth > 1080) {
            frontPic.setImageDrawable(getDrawable(R.drawable.bibl4))
        }
        else{
            frontPic.setImageDrawable(getDrawable(R.drawable.lomma_bibl3))
        }

        val getLogIn = LoginAuthFirebase()
        val loggedIn = getLogIn.isBingocLoggedIn()
     //   if (LoginAuthFirebase().isBingocLoggedIn())
        if (loggedIn){
            binding.createBingoBtn.visibility = View.VISIBLE
        }else{
            binding.createBingoBtn.visibility = View.INVISIBLE
        }

        if (BingoPasswordFound){
            binding.chooseQuiz.visibility = View.VISIBLE
        }else{
            binding.chooseQuiz.visibility = View.INVISIBLE
        }

        if (showBingoGrattis){
            binding.grattisIV.visibility = View.VISIBLE
        }else{
            binding.grattisIV.visibility = View.INVISIBLE
        }

   /*     if (takeAwayButtonToChooseRunda){
            binding.SeeWalksBtn.visibility = View.INVISIBLE
        }else{
            binding.SeeWalksBtn.visibility = View.VISIBLE
        }

        if (takeAwayButtonStartBingo){
            binding.chooseQuiz.visibility = View.INVISIBLE
        }else{
            binding.chooseQuiz.visibility = View.VISIBLE
        }*/

        // jag tror att det var showWalk
        binding.SeeWalksBtn.setOnClickListener {
            val intent = Intent(this, showWalk::class.java)
            showBingoGrattis = false

         //  intent.putExtra("from", "MainActivity")
            startActivity(intent)
        }

   /*  vad är vad   binding.SeeWalksBtn.setOnClickListener {
            val intent = Intent(this, choseSeeWalks::class.java)

            startActivity(intent)
        }*/

        binding.chooseQuizBtn.setOnClickListener {
            val intent = Intent(this, ListOfQuizes::class.java)
            showBingoGrattis = false
            intent.putExtra("from", "MainActivity")
            startActivity(intent)
        }

        binding.loginMainBtn.setOnClickListener {
            val intent = Intent(this, login::class.java)
            showBingoGrattis = false
            startActivity(intent)
            /*        val intent = Intent(this, newfront::class.java)
            startActivity(intent)*/
        }

        binding.chooseQuiz.setOnClickListener {
            val intent = Intent(this, showMap::class.java)
            showBingoGrattis = false
            clearPrevNumbers()
            intent.putExtra("walk", "Bingo")
            startActivity(intent)
        }

        binding.createBingoBtn.setOnClickListener {
            val intent = Intent(this, CreateBingo::class.java)
            showBingoGrattis = false
            startActivity(intent)
        }

        binding.infoQuizBtn.setOnClickListener {
            val intent = Intent(this, AboutQuiz::class.java)
            showBingoGrattis = false
            startActivity(intent)
        }

        binding.infoBingoBtn.setOnClickListener {
            val intent = Intent(this, AboutBingo::class.java)
            showBingoGrattis = false
            startActivity(intent)
        }

        if (!checkInternetAccess()) {
            val snack = Snackbar.make(
                findViewById(android.R.id.content),
                "Inget internet",
                Snackbar.LENGTH_INDEFINITE
            )
            snack.setAction("OK")
            {
            }
            snack.show()
        }

        doLocationPermission()

        countdownSecond()

        countdownTwoSecond()

        readFromFirebase += 1
        database.child("QuizWalks").child("maps").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            //  for (snapchild in it.children) {
            var i = 0
            for (snapchild in it.children) {
                var tempshop = snapchild.getValue()!!
                mapNames.add(snapchild.key.toString())
                i += 1
            }
        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)
        }

        binding.SeeWalksBtn.setOnClickListener {
            val intent = Intent(this, choseSeeWalks::class.java)

            startActivity(intent)
        }
// testar
        readMapCoordinates()
        loadQuizNames()

    }

    override fun onPostResume() {
        super.onPostResume()
        if (takeAwayButtonToChooseRunda){
            binding.SeeWalksBtn.visibility = View.INVISIBLE
        }else{
            binding.SeeWalksBtn.visibility = View.VISIBLE
        }

        if (takeAwayButtonStartBingo){
            binding.chooseQuiz.visibility = View.INVISIBLE
        }else{
            binding.chooseQuiz.visibility = View.VISIBLE
        }

        if (showBingoGrattis){
            binding.grattisIV.visibility = View.VISIBLE
        }else{
            binding.grattisIV.visibility = View.INVISIBLE
        }

    /*    if (BingoPasswordFound){
            binding.chooseQuiz.visibility = View.VISIBLE
        }else{
            binding.chooseQuiz.visibility = View.INVISIBLE
        }*/

    }

    override fun onRestart() {
        super.onRestart()

        val getLogIn = LoginAuthFirebase()
        val loggedIn = getLogIn.isBingocLoggedIn()

        if (loggedIn){
            binding.createBingoBtn.visibility = View.VISIBLE
        }else{
            binding.createBingoBtn.visibility = View.INVISIBLE
        }

        if (showBingoGrattis){
            binding.grattisIV.visibility = View.VISIBLE
        }else{
            binding.grattisIV.visibility = View.INVISIBLE
        }
    }

    fun readMapCoordinates() {
        readFromFirebase += 1
        var count = 0
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

            database.child("QuizWalks").child("maps").child("Walk1").child(letter).get()
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

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

    fun playSound(start: Boolean) {

        var resId = getResources().getIdentifier(
            R.raw.intro_quiz_kotlin.toString(),
            "raw", this?.packageName
        )

        if (start) {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(this, resId)
                mediaPlayer!!.start()
            } else {
                mediaPlayer!!.seekTo(0)
                mediaPlayer!!.start()
            }
        } else {

            if (mediaPlayer != null) {
                mediaPlayer!!.pause()
            }
        }
    }

    fun doLocationPermission() {
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
                }
                else -> {
                    // No location access granted.
                    Log.i("PIAXDEBUG", "PERMISSION DENY!!")
                }
            }
        }

        // ...

        // Before you perform the actual permission request, check whether your app
        // already has the permissions, and whether your app needs to show a permission
        // rationale dialog. For more details, see Request permissions.
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    fun playUrl() {
        val mediaPlayer = MediaPlayer()
        mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build())
        try
        {
            mediaPlayer.setDataSource("https://firebasestorage.googleapis.com/v0/b/fire1-95766.appspot.com/o/framling_tal.mp3?alt=media&token=b2f813f1-bae6-44d6-aba5-b2e5eb5dcff1")
            mediaPlayer.prepare()
            mediaPlayer.start()
        } catch (e: IOException)
        {
            e.printStackTrace()
        }

        Log.v(TAG,"Music is streaming")
    }

    fun countdownSecond() {
        object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                countdownSecond()
            }
        }.start()
    }

    fun countdownTwoSecond() {
        object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                countdownTwoSecond()
            }
        }.start()
    }



    fun checkInternetAccess(): Boolean {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i(TAG, "checkInternetAccess.INTERNET_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i(TAG, "checkInternetAccess.INTERNET_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i(TAG, "checkInternetAccess.INTERNET_ETHERNET")
                    return true
                }
            }
        }
        Log.i(TAG, "checkInternetAccess.NO_INTERNET_ACCESS")
        return false
    }




    fun loadQuizNames() {
        var i = 0
        readFromFirebase += 1

        database.child("QuizWalks").child("QuizNameList").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            //  for (snapchild in it.children) {

            quizNameListID.clear()
            countQuizWalks2 += 0
            for (snapchild in it.children) {

                var tempshop = snapchild.getValue()!!

                quizNameListID.add(snapchild.key.toString())
                Log.i("MIN", "QuiznameID " + snapchild.key.toString())
                //   mapNames.add("Walk1")
                i += 1
                countQuizWalks2 += 1
            }
            //     quizNameListID.add("testa")
            Log.i("MIN", "count walks"+ countQuizWalks2.toString())
            quizNameList.clear()
            quizNameListUser.clear()

            for (m in 0..(countQuizWalks2-1)) {
                database.child("QuizWalks").child("QuizNameList").child(quizNameListID[m].toString()).get()
                    .addOnSuccessListener {
                        Log.i("firebase", "Got value ${it.value}")

                        var i = 0

                        for (snapchild in it.children) {

                            var tempshop = snapchild.getValue()!!

                            if (snapchild.key == "name") {
                                Log.i("MIN", "name " + tempshop.toString())
                                quizNameList.add(tempshop.toString())

                                //          minAdapter.names.add(tempshop.toString())

                                //                       minAdapter.notifyDataSetChanged()
                            }
                            if (snapchild.key == "user") {
                                Log.i("MIN", "user " + tempshop.toString())
                                quizNameListUser.add(tempshop.toString())
                                Log.i("MIN", "user " + tempshop.toString())
                            }

                        }
                        //           finich = true
                    }
            }
            finich = true


        }.addOnFailureListener {
            Log.e("firebase", "Error getting data", it)

        }
    }

}

