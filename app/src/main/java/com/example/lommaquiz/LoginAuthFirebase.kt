package com.example.lommaquiz

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.lommaquiz.databinding.ActivityLoginAuthFirebaseBinding
import com.example.lommaquiz.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

var masterLoggedIn = false
var authPassedForUser = false
var writePermission = false

class LoginAuthFirebase : AppCompatActivity() {

    private lateinit var binding: ActivityLoginAuthFirebaseBinding

 //   private lateinit var auth: FirebaseAuth
   var auth: FirebaseAuth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginAuthFirebaseBinding.inflate(layoutInflater)
        setContentView(binding.root)




        // Initialize Firebase Auth
        auth = Firebase.auth

        val currentUser = auth.currentUser
        if(currentUser != null){
            //           reload();
            print("inte inloggad")
        }


        binding.AuthLoginBtn.setOnClickListener {
            val email = binding.authEmail.text.toString()
            val password = binding.authPassword.text.toString()
            signIn(email, password)
        }

        binding.AuthLogoutBtn.setOnClickListener {
            logout()
            binding.loginStatusTV.text = "Du är utloggad"
        }

        binding.createLoginBingoBtn.setOnClickListener{
            forceBingocLogin()
            questionUser = ""
            binding.loginStatusTV.text = "Du är inloggad för att skapa bingobrickor. tryck back, back, välj SKAPA BRICKOR"
        }

        binding.createWalkLoginBtn.setOnClickListener {
            forceRundacLogin()
            questionUser = ""
            binding.loginStatusTV.text = "Du är inloggad för att skapa runda. Tryck back, back, välj promenad, skapa ny runda "
        }
    }

    private fun signIn(email: String, password: String) {


        var activity = null

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    val user = auth.currentUser

                } else {
                    binding.loginStatusTV.text = "Inloggning misslyckades"
                    print("gick inte")
                }

                if (!task.isSuccessful) {

                    binding.loginStatusTV.text = "Inloggning misslyckades"
                }
            }

        if (auth.currentUser?.email == "jorgen_raby@hotmail.com"){
            print("hej")
            masterLoggedIn = true
            writePermission = true
            binding.loginStatusTV.text = "Du är inloggad med skrivrättighet, högsta behörighet"
        }

        if (auth.currentUser?.email == "jorgen@icloud.com"){
            print("hej")
            authPassedForUser = true
            writePermission = true
            binding.loginStatusTV.text = "Du är inloggad med skrivrättighet"
        }



    }
/*  if (Auth.auth().currentUser?.email == "bingou@icloud.com"){
        showStatusTV.text = "Inloggad för att gå BINGORUNDA"
    }
    if (Auth.auth().currentUser?.email == "bingoc@icloud.com"){
        showStatusTV.text = "Inloggad för att skapa BINGORUNDA, tryck Back och Back välj SKAPA BINGORUNDA"
    }
    if (Auth.auth().currentUser?.email == "rundac@icloud.com"){
        showStatusTV.text = "Inloggad för att skapa runda, tryck Back och Back och VÄLJ PROMENAD"
    }*/


    private fun forceBingocLogin() {
        var email = "bingoc@icloud.com"
        var password = "bingoc"
      // krasch om anropet inte är från här  binding.loginStatusTV.text = "Du är inloggad för att skapa bingobrickor"

        signIn(email, password)
    }

    // This is executed when someone log in with a password for bingo-walk. They must get permission to write to firebase, thats why

    fun forceBingouLogin() {
        var email = "bingou@icloud.com"
        var password = "bingou"
        // krasch om anropet inte är från här  binding.loginStatusTV.text = "Du är inloggad för att gå bingorunda"

        signIn(email, password)
    }



    private fun forceRundacLogin() {
        var email = "rundac@icloud.com"
        var password = "rundac"

        // jag tror att detta är rätt
        authPassedForUser = true
        writePermission = true

        // krasch om anropet inte är från här   binding.loginStatusTV.text = "Du är inloggad för att skapa runda"

        signIn(email, password)
    }


    fun login(email : String, password : String)
    {
        val auth = Firebase.auth

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {

                print("1")
            } else {

                binding.loginStatusTV.text = "Inloggning misslyckades"
                print("2")
            }

        }
    }

    fun isBingocLoggedIn():Boolean {
        if (auth.currentUser != null) {
            var email = auth.currentUser!!.email.toString()
            return (email == "bingoc@icloud.com")
        } else {
            return false
        }
    }

    fun isRundacLoggedIn():Boolean {
        if (auth.currentUser != null) {
            var email = auth.currentUser!!.email.toString()
            return (email == "rundac@icloud.com")
        } else {
            return false
        }
    }

    fun logout()
    {
        val auth = Firebase.auth
        auth.signOut()

        masterLoggedIn = false
        authPassedForUser = false
        writePermission = false
    }


}
