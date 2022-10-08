package com.example.lommaquiz

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.lommaquiz.databinding.ActivityCreateQuizBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.util.*


var quizAnswer1_create : ArrayList<String> = ArrayList(mutableListOf("","","","","","","","","","","",""))
var quizAnswer2_create : ArrayList<String> = ArrayList(mutableListOf("","","","","","","","","","","",""))
var quizAnswer3_create : ArrayList<String> = ArrayList(mutableListOf("","","","","","","","","","","",""))
var quizCorrectAnswer_create : ArrayList<String> = ArrayList(mutableListOf("","","","","","","","","","","",""))
var quizQuestion_create : ArrayList<String> = ArrayList(mutableListOf("","","","","","","","","","","",""))
var quizURLString_create : ArrayList<String> = ArrayList(mutableListOf("","","","","","","","","","","",""))


var questionNumber = 1
var useURL = false
var correctAnswer = 1

class CreateQuiz : AppCompatActivity() {
    private lateinit var binding: ActivityCreateQuizBinding
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database =
            Firebase.database("https://fire1-95766-default-rtdb.europe-west1.firebasedatabase.app").reference

        binding = ActivityCreateQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var quizName = questionUser+"s Quiz"

        var FromWhere = intent.getStringExtra("from").toString()

   /*     if (FromWhere == "choseSeeWalks"){

        }*/

        // If we come from Adaptor, then we want to change a quiz and we are logged as admin with highest status
        if (FromWhere == "MyAdaptor"){
            var quizNameGet = intent.getStringExtra("quizName").toString()
            quizName = quizNameGet
        }




        questionNumber = 1
        useURL = false
        correctAnswer = 1
        binding.saveQuestionBtn.text = "SPARA"

        // If it is not created, we create an empty one

  //      binding.quizNameET.text

        if (questionUser == "adminmaster"){
            binding.createNewQuizBtn.visibility = View.VISIBLE
            binding.seeImageBtn.visibility  = View.VISIBLE
            binding.createQuizAnswerTVURL.visibility = View.VISIBLE

            var editable = Editable.Factory.getInstance().newEditable(quizName)
            binding.quizNameET.text = editable
            binding.quizNameET.isEnabled = false

            isQuizCreated()
            //     var quizName = questionUser+"s Quiz"
            readQuestions(quizName)
            countdownSecond2()
        }
        else{
            binding.createNewQuizBtn.visibility = View.INVISIBLE
            binding.seeImageBtn.visibility  = View.INVISIBLE
            binding.createQuizAnswerTVURL.visibility = View.INVISIBLE

         //   binding.quizNameET.inputType
 /*           binding.createNewQuizBtn.setFocusable(false)
            binding.createNewQuizBtn.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
            binding.createNewQuizBtn.setClickable(false);*/

     //       var editable = Editable.Factory.getInstance().newEditable(questionUser+"s Quiz")
            var editable = Editable.Factory.getInstance().newEditable(quizName)

            binding.quizNameET.text = editable
            binding.quizNameET.isEnabled = false

            isQuizCreated()
       //     var quizName = questionUser+"s Quiz"
            readQuestions(quizName)
            countdownSecond2()
        }

        // Only possible when adminmaster is in
        binding.createNewQuizBtn.setOnClickListener {
            writeQuizName(binding.quizNameET.text.toString())
            quizName = binding.quizNameET.text.toString()
            isQuizCreated()
            readQuestions(binding.quizNameET.text.toString())
            countdownSecond2()
        }



        binding.createQuizQuestionNbrTV.text = "Fråga "+questionNumber.toString()
        binding.saveQuestionBtn.setOnClickListener {
    //        writeQuizName()
            binding.saveQuestionBtn.setBackgroundColor(Color.GRAY)



            binding.createQuizAnswerBtn1.setBackgroundColor(Color.WHITE)
            binding.createQuizAnswerBtn2.setBackgroundColor(Color.WHITE)
            binding.createQuizAnswerBtn3.setBackgroundColor(Color.WHITE)



      /*      if (questionNumber < 13) {
                binding.createQuizQuestionNbrTV.text = "Fråga " + questionNumber.toString()
            }*/
   //         var quizName = questionUser+"s Quiz"

            if (questionNumber < 13){
                saveQuestion(quizName)
            }

            if (questionNumber < 12){
                //saveQuestion()
                questionNumber += 1
                binding.createQuizQuestionNbrTV.text = "Fråga "+questionNumber.toString()
               // if (questionNumber < 13) {
                    countdownSecond()
                    updateScreen()
              //  }
            } else{
        //        questionNumber += 1
                binding.saveQuestionBtn.text = "KLAR"
            }
    /*        if (questionNumber == 14) {
                finish()
            }*/
        }


        binding.createQuizAnswerBtn1.setOnClickListener {
            binding.createQuizAnswerBtn1.setBackgroundColor(Color.GRAY)
            binding.createQuizAnswerBtn2.setBackgroundColor(Color.WHITE)
            binding.createQuizAnswerBtn3.setBackgroundColor(Color.WHITE)
            correctAnswer = 1
        }

        binding.createQuizAnswerBtn2.setOnClickListener {
            binding.createQuizAnswerBtn2.setBackgroundColor(Color.GRAY)
            binding.createQuizAnswerBtn1.setBackgroundColor(Color.WHITE)
            binding.createQuizAnswerBtn3.setBackgroundColor(Color.WHITE)
            correctAnswer = 2
        }

        binding.createQuizAnswerBtn3.setOnClickListener {
            binding.createQuizAnswerBtn3.setBackgroundColor(Color.GRAY)
            binding.createQuizAnswerBtn1.setBackgroundColor(Color.WHITE)
            binding.createQuizAnswerBtn2.setBackgroundColor(Color.WHITE)
            correctAnswer = 3
        }

        binding.createQuizUseURLBtn.setOnClickListener {


            binding.createQuizUseURLBtn.setBackgroundColor(Color.GRAY)
            binding.createQuizNotUseURLBtn.setBackgroundColor(Color.WHITE)
            useURL = true

        }

        binding.createQuizNotUseURLBtn.setOnClickListener {
            binding.createQuizNotUseURLBtn.setBackgroundColor(Color.GRAY)
            binding.createQuizUseURLBtn.setBackgroundColor(Color.WHITE)
            useURL = false
        }

        binding.seeImageBtn.setOnClickListener {
            val intent = Intent(this, SeeImage::class.java)
            intent.putExtra("URL", binding.createQuizAnswerTVURL.text.toString())
            startActivity(intent)


    //        intent.putExtra("QuizName", QuizName)
    //        intent.putExtra("URL", quizURLString[showMarker-1])

        }

    }

    fun updateScreen(){
   //     questionNumber
  //      binding.createQuizAnswerTV1.sett = quizAnswer1_create[questionNumber]

        /*

        var editable = Editable.Factory.getInstance().newEditable(snapchild.value.toString())
                        binding.newGroupNameET.text = editable
                        binding.oldGroupNameTV.text = binding.newGroupNameET.text

         */

        var editable = Editable.Factory.getInstance().newEditable(quizAnswer1_create[questionNumber-1])
        binding.createQuizAnswerTV1.text = editable
        editable = Editable.Factory.getInstance().newEditable(quizAnswer2_create[questionNumber-1])
        binding.createQuizAnswerTV2.text = editable
        editable = Editable.Factory.getInstance().newEditable(quizAnswer3_create[questionNumber-1])
        binding.createQuizAnswerTV3.text = editable

    //    binding.createQuizAnswerTV2.text = quizAnswer2_create[questionNumber]

  //      binding.createQuizAnswerTV3.text = quizAnswer3_create[questionNumber]

        if (quizCorrectAnswer_create[questionNumber-1] == "1") {
            binding.createQuizAnswerBtn1.setBackgroundColor(Color.GRAY)
            correctAnswer = 1
        }
        if (quizCorrectAnswer_create[questionNumber-1] == "2") {
            binding.createQuizAnswerBtn2.setBackgroundColor(Color.GRAY)
            correctAnswer = 2
        }
        if (quizCorrectAnswer_create[questionNumber-1] == "3") {

            binding.createQuizAnswerBtn3.setBackgroundColor(Color.GRAY)
            correctAnswer = 3
        }

        editable = Editable.Factory.getInstance().newEditable(quizQuestion_create[questionNumber-1])
        binding.questionTVSave.text = editable

   //     binding.questionTVSave.text = quizQuestion_create[questionNumber]

  //      useURL = false
   //     correctAnswer = 1

        editable = Editable.Factory.getInstance().newEditable(quizURLString_create[questionNumber-1])
        binding.createQuizAnswerTVURL.text = editable

   /*     if (editable.toString() == "") {
            useURL = false
        }
        else{
            useURL = true
        }*/

   /*     if (binding.createQuizAnswerTVURL.text.toString() == "") {
            useURL = false
        }
        else{
            useURL = true
        }*/

    }

    fun readQuestions(QuizNameString:String){

        var questionStringText = ""
        var questionStringNumber = ""

        for (i in 1..12) {
            when (i) {
                1 -> {
                    questionStringNumber = "Fråga 1"
                    questionStringText = "Fråga A"
                }
                2 -> {
                    questionStringNumber = "Fråga 2"
                    questionStringText = "Fråga B"
                }
                3 -> {
                    questionStringNumber = "Fråga 3"
                    questionStringText = "Fråga C"
                }
                4 -> {
                    questionStringNumber = "Fråga 4"
                    questionStringText = "Fråga D"
                }
                5 -> {
                    questionStringNumber = "Fråga 5"
                    questionStringText = "Fråga E"
                }
                6 -> {
                    questionStringNumber = "Fråga 6"
                    questionStringText = "Fråga F"
                }
                7 -> {
                    questionStringNumber = "Fråga 7"
                    questionStringText = "Fråga G"
                }
                8 -> {
                    questionStringNumber = "Fråga 8"
                    questionStringText = "Fråga H"
                }
                9 -> {
                    questionStringNumber = "Fråga 9"
                    questionStringText = "Fråga I"
                }
                10 -> {
                    questionStringNumber = "Fråga 10"
                    questionStringText = "Fråga J"
                }
                11 -> {
                    questionStringNumber = "Fråga 11"
                    questionStringText = "Fråga K"
                }
                12 -> {
                    questionStringNumber = "Fråga 12"
                    questionStringText = "Fråga L"
                }
                else -> {
                    // testar
                    questionStringNumber = "Fråga 12"
                    questionStringText = "Fråga L"
                }
            }
            //     test.text = questionStringNumber

 //           readFromFirebase += 1
            //     database.child("QuizWalks").child("QuizNames").child(QuizName).child("Fråga A").get().addOnSuccessListener {

            var quizName = questionUser+"s Quiz"
            quizName = QuizNameString

            //     var shopref = database.child("QuizWalks").child("QuizNames").child(quizName)
       //     database.child("QuizWalks").child("QuizNames").child(quizName).get()

            database.child("QuizWalks").child("QuizNames").child(quizName).child(questionStringText)
                .get().addOnSuccessListener {
                    Log.i("firebase", "Got value ${it.value}")

                    var i = 0
                    for (snapchild in it.children) {

                        //   var tempshop = snapchild.getValue<question>()!!
                        //         var tempshop = DataSnapshot<question>

                        var tempshop = it.getValue<question>()

                        var questionNumber = 0

                        when (it.key) {
                            "Fråga A" -> {
                                questionNumber = 0
                            }
                            "Fråga B" -> {
                                questionNumber = 1
                            }
                            "Fråga C" -> {
                                questionNumber = 2
                            }
                            "Fråga D" -> {
                                questionNumber = 3
                            }
                            "Fråga E" -> {
                                questionNumber = 4
                            }
                            "Fråga F" -> {
                                questionNumber = 5
                            }
                            "Fråga G" -> {
                                questionNumber = 6
                            }
                            "Fråga H" -> {
                                questionNumber = 7
                            }
                            "Fråga I" -> {
                                questionNumber = 8
                            }
                            "Fråga J" -> {
                                questionNumber = 9
                            }
                            "Fråga K" -> {
                                questionNumber = 10
                            }
                            "Fråga L" -> {
                                questionNumber = 11
                            }
                            else -> {
                                // testar
                                questionNumber = 11

                            }
                        }

                        //        if (it.key == "Fråga A")
                        //      {
                        if (snapchild.key == "Answer 1") {
                            Log.i("MIN", "Answer 1 " + snapchild.value.toString())
                            quizAnswer1_create[questionNumber] = snapchild.getValue().toString()

                        }
                        if (snapchild.key == "Answer 2") {
                            Log.i("MIN", "Answer 2 " + snapchild.value.toString())
                            quizAnswer2_create[questionNumber] = snapchild.getValue().toString()

                        }
                        if (snapchild.key == "Answer 3") {
                            Log.i("MIN", "Answer 3 " + snapchild.value.toString())
                            quizAnswer3_create[questionNumber] = snapchild.getValue().toString()

                        }
                        if (snapchild.key == "Correct Answer") {
                            Log.i("MIN", "Correct Answer " + snapchild.value.toString())
                            quizCorrectAnswer_create[questionNumber] = snapchild.getValue().toString()
                        }
                        if (snapchild.key == "Fråga") {
                            Log.i("MIN", "Question " + snapchild.value.toString())
                            quizQuestion_create[questionNumber] = snapchild.getValue().toString()

                        }
                        if (snapchild.key == "URLString") {
                            Log.i("MIN", "URLString " + snapchild.value.toString())
                            quizURLString_create[questionNumber] = snapchild.getValue().toString()

                        }
                    }

                }.addOnFailureListener {
                    Log.e("firebase", "Error getting data", it)
                }

        }
    }

    fun countdownSecond() {
        object : CountDownTimer(1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                binding.saveQuestionBtn.setBackgroundColor(Color.WHITE)
            }
        }.start()
    }

    fun countdownSecond2() {
        object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                updateScreen()
            }
        }.start()
    }

    fun writeQuizName(QuizNameString:String){
  //      var childCharArray = ArrayList(mutableListOf("A","B","C","D","E","F","G","H","I","J","K","L"))
        var childQuestionArray = ArrayList(mutableListOf("Fråga A","Fråga B","Fråga C","Fråga D","Fråga E","Fråga F","Fråga G","Fråga H","Fråga I","Fråga J","Fråga K","Fråga L"))


  //      var quizName = questionUser+"s Quiz"
        var quizName = QuizNameString

        var shopref = database.child("QuizWalks").child("QuizNameList").push()

        shopref.child("name").setValue(quizName)
        shopref.child("user").setValue(loggedIn)

        for (i in 0..11) {
            createEmptyQuiz(childQuestionArray[i],QuizNameString)
        }

    }

    fun createEmptyQuiz(QuestionString:String,QuizNameString:String){
      //  var quizName = questionUser+"s Quiz"
        var quizName = QuizNameString

        var shopref = database.child("QuizWalks").child("QuizNames").child(quizName).child(QuestionString).child("Answer 1")

        shopref.setValue("svar 1")
        shopref = database.child("QuizWalks").child("QuizNames").child(quizName).child(QuestionString).child("Answer 2")

        shopref.setValue("svar 2")

        shopref = database.child("QuizWalks").child("QuizNames").child(quizName).child(QuestionString).child("Answer 3")

        shopref.setValue("svar 3")

        shopref = database.child("QuizWalks").child("QuizNames").child(quizName).child(QuestionString).child("Correct Answer")

        shopref.setValue("1")

        shopref = database.child("QuizWalks").child("QuizNames").child(quizName).child(QuestionString).child("Fråga")

        shopref.setValue("fråga")

        shopref = database.child("QuizWalks").child("QuizNames").child(quizName).child(QuestionString).child("URLString")

        shopref.setValue("")
    }

    fun saveQuestion(QuizNameString:String){
        var childQuestionArray = ArrayList(mutableListOf("Fråga A","Fråga B","Fråga C","Fråga D","Fråga E","Fråga F","Fråga G","Fråga H","Fråga I","Fråga J","Fråga K","Fråga L"))

        createEmptyQuiz2(childQuestionArray[questionNumber-1], QuizNameString)
    }

    fun createEmptyQuiz2(QuestionString:String,QuizNameString:String){
     //   var quizName = questionUser+"s Quiz"
        var quizName = QuizNameString

        var shopref = database.child("QuizWalks").child("QuizNames").child(quizName).child(QuestionString).child("Answer 1")

        shopref.setValue(binding.createQuizAnswerTV1.text.toString())
        shopref = database.child("QuizWalks").child("QuizNames").child(quizName).child(QuestionString).child("Answer 2")

        shopref.setValue(binding.createQuizAnswerTV2.text.toString())

        shopref = database.child("QuizWalks").child("QuizNames").child(quizName).child(QuestionString).child("Answer 3")

        shopref.setValue(binding.createQuizAnswerTV3.text.toString())

        shopref = database.child("QuizWalks").child("QuizNames").child(quizName).child(QuestionString).child("Correct Answer")


        if (correctAnswer == 1) {
            shopref.setValue("1")
        }
        if (correctAnswer == 2) {
            shopref.setValue("2")
        }
        if (correctAnswer == 3) {
            shopref.setValue("3")
        }

   /*     var color = (binding.createQuizAnswerBtn1.getBackground() as ColorDrawable).color
        if (color == Color.GRAY) {
            shopref.setValue("1")
        }
        color = (binding.createQuizAnswerBtn2.getBackground() as ColorDrawable).color
        if (color == Color.GRAY) {
            shopref.setValue("2")
        }
        color = (binding.createQuizAnswerBtn3.getBackground() as ColorDrawable).color
        if (color == Color.GRAY) {
            shopref.setValue("3")
        }*/

        shopref = database.child("QuizWalks").child("QuizNames").child(quizName).child(QuestionString).child("Fråga")

        shopref.setValue(binding.questionTVSave.text.toString())

  //      color = (binding.createQuizURLBtn.getBackground() as ColorDrawable).color
        if (useURL) {
            shopref =
                database.child("QuizWalks").child("QuizNames").child(quizName).child(QuestionString).child("URLString")
           //         .child(binding.createQuizAnswerTVURL.text.toString())

            shopref.setValue(binding.createQuizAnswerTVURL.text.toString())
        }
        else
        {
            shopref =
                database.child("QuizWalks").child("QuizNames").child(quizName).child(QuestionString).child("URLString")

            shopref.setValue("")
        }
    }

    fun isQuizCreated(){

            var quizName = questionUser+"s Quiz"

       //     var shopref = database.child("QuizWalks").child("QuizNames").child(quizName)
        database.child("QuizWalks").child("QuizNames").child(quizName).get()
            .addOnSuccessListener {
                Log.i("firebase", "Got value ${it.value}")


                for (snapchild in it.children) {
                // Ok, so it is created
                }

            }.addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
                writeQuizName(quizName)
            }

    }

    fun writeToCertainGroups(inString: String) {
        var shopref = database.child("Meddelande").child("Alla Grupper")

        shopref = database.child("Sparade Meddelanden").child("Utvalda").push()

  //      shopref.setValue(inString+"/////"+currentDate+": " +binding.AdminET.text)
    }

}