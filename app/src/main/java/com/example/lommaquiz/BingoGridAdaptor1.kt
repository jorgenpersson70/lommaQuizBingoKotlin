package com.example.lommaquiz

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


var bingoNumber2 = 0



// Adapter
class BingoGridAdaptor1() : RecyclerView.Adapter<HappyViewHolder4>() {
    var names = mutableListOf<String>()

    private lateinit var database: DatabaseReference

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HappyViewHolder4 {
        val vh = HappyViewHolder4(LayoutInflater.from(parent.context).inflate(R.layout.bingo_row, parent, false))

        database = Firebase.database("https://fire1-95766-default-rtdb.europe-west1.firebasedatabase.app").reference

        return vh
    }

    override fun getItemCount(): Int {
        return 25
    }

    override fun onBindViewHolder(holder: HappyViewHolder4, position: Int){
        var test = 0

        var row = bingoNumber2 / 5

        if ((bingoNumber2 % 5) == 0) {
            test = BingoValueB[row]
        }
        if ((bingoNumber2 % 5) == 1) {
            test = BingoValueI[row]
        }
        if ((bingoNumber2 % 5) == 2) {
            test = BingoValueN[row]
        }
        if ((bingoNumber2 % 5) == 3) {
            test = BingoValueG[row]
        }
        if ((bingoNumber2 % 5) == 4) {
            test = BingoValueO[row]
        }

        holder.radTextview.text = test.toString()
        holder.radTextview.setBackgroundColor(Color.CYAN)
        bingoNumber2 = bingoNumber2 + 1

        Log.i("bingo", test.toString())

        if (debuggHomeTestGrey) {
            // för test      holder.itemView.setOnClickListener {
            if (CheckIfValueIsClickable(position, 1)) {
                holder.radTextview.setBackgroundColor(Color.GRAY)
                cellsThatAreGrayChart1[position] = 1
                if (checkIfBingo()) {
                    database.child("BingoPlayerWinner").child(bingoName).child("TheWinner")
                        .setValue(youAreplayer.toString())
                }
            }
        }else{
            holder.itemView.setOnClickListener {
                if (CheckIfValueIsClickable(position, 1)) {
                    holder.radTextview.setBackgroundColor(Color.GRAY)
                    cellsThatAreGrayChart1[position] = 1
                    if (checkIfBingo()) {
                        database.child("BingoPlayerWinner").child(bingoName).child("TheWinner")
                            .setValue(youAreplayer.toString())
                    }
                }
            }
        }
 //       }
        if (cellsThatAreGrayChart1[position] == 1){
            holder.radTextview.setBackgroundColor(Color.GRAY)
        }
    }

}



class HappyViewHolder4 (view: View) : RecyclerView.ViewHolder(view){
    var radTextview = view.findViewById<TextView>(R.id.nyTV)
}

class BingoGridAdaptor2() : RecyclerView.Adapter<HappyViewHolder3>() {
    var names = mutableListOf<String>()
    private lateinit var database: DatabaseReference

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HappyViewHolder3 {
        val vh = HappyViewHolder3(LayoutInflater.from(parent.context).inflate(R.layout.bingo_row, parent, false))
        database = Firebase.database("https://fire1-95766-default-rtdb.europe-west1.firebasedatabase.app").reference
        return vh
    }

    override fun getItemCount(): Int {
        return 25
    }

    override fun onBindViewHolder(holder: HappyViewHolder3, position: Int){
        var test = 0

        var row = bingoNumber2 / 5

        if ((bingoNumber2 % 5) == 0) {
            test = BingoValueB[row]
        }
        if ((bingoNumber2 % 5) == 1) {
            test = BingoValueI[row]
        }
        if ((bingoNumber2 % 5) == 2) {
            test = BingoValueN[row]
        }
        if ((bingoNumber2 % 5) == 3) {
            test = BingoValueG[row]
        }
        if ((bingoNumber2 % 5) == 4) {
            test = BingoValueO[row]
        }

        holder.radTextview.text = test.toString()
        holder.radTextview.setBackgroundColor(Color.GREEN)
        bingoNumber2 = bingoNumber2 + 1

        if (debuggHomeTestGrey) {
            // för test      holder.itemView.setOnClickListener {
            if (CheckIfValueIsClickable(position, 3)) {
                holder.radTextview.setBackgroundColor(Color.GRAY)
                cellsThatAreGrayChart3[position] = 1
                if (checkIfBingo()) {
                    database.child("BingoPlayerWinner").child(bingoName).child("TheWinner")
                        .setValue(youAreplayer.toString())
                }
                //       }
            }
        }else{
            holder.itemView.setOnClickListener {
                if (CheckIfValueIsClickable(position, 3)) {
                    holder.radTextview.setBackgroundColor(Color.GRAY)
                    cellsThatAreGrayChart3[position] = 1
                    if (checkIfBingo()) {
                        database.child("BingoPlayerWinner").child(bingoName).child("TheWinner")
                            .setValue(youAreplayer.toString())
                    }
                }
            }
        }

        if (cellsThatAreGrayChart3[position] == 1){
            holder.radTextview.setBackgroundColor(Color.GRAY)
        }
    }

}

class HappyViewHolder3 (view: View) : RecyclerView.ViewHolder(view) {
    var radTextview = view.findViewById<TextView>(R.id.nyTV)
}

class BingoGridAdaptor3() : RecyclerView.Adapter<HappyViewHolder2>() {
    var names = mutableListOf<String>()
    private lateinit var database: DatabaseReference

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HappyViewHolder2 {
        val vh = HappyViewHolder2(LayoutInflater.from(parent.context).inflate(R.layout.bingo_row, parent, false))
        database = Firebase.database("https://fire1-95766-default-rtdb.europe-west1.firebasedatabase.app").reference
        return vh
    }

    override fun getItemCount(): Int {
        return 25
    }

    override fun onBindViewHolder(holder: HappyViewHolder2, position: Int){
        var test = 0

        var row = bingoNumber2 / 5

        if ((bingoNumber2 % 5) == 0) {
            test = BingoValueB[row]
        }
        if ((bingoNumber2 % 5) == 1) {
            test = BingoValueI[row]
        }
        if ((bingoNumber2 % 5) == 2) {
            test = BingoValueN[row]
        }
        if ((bingoNumber2 % 5) == 3) {
            test = BingoValueG[row]
        }
        if ((bingoNumber2 % 5) == 4) {
            test = BingoValueO[row]
        }

        holder.radTextview.text = test.toString()
        holder.radTextview.setBackgroundColor(Color.YELLOW)
        bingoNumber2 = bingoNumber2 + 1

        if (debuggHomeTestGrey) {
            // för test      holder.itemView.setOnClickListener {
            if (CheckIfValueIsClickable(position, 2)) {
                holder.radTextview.setBackgroundColor(Color.GRAY)
                cellsThatAreGrayChart2[position] = 1
                if (checkIfBingo()) {
                    database.child("BingoPlayerWinner").child(bingoName).child("TheWinner")
                        .setValue(youAreplayer.toString())
                }
                //          }
            }
        }else{
            holder.itemView.setOnClickListener {
                if (CheckIfValueIsClickable(position, 2)) {
                    holder.radTextview.setBackgroundColor(Color.GRAY)
                    cellsThatAreGrayChart2[position] = 1
                    if (checkIfBingo()) {
                        database.child("BingoPlayerWinner").child(bingoName).child("TheWinner")
                            .setValue(youAreplayer.toString())
                    }
                }
            }
        }

        if (cellsThatAreGrayChart2[position] == 1){
            holder.radTextview.setBackgroundColor(Color.GRAY)
        }
    }

}

class HappyViewHolder2 (view: View) : RecyclerView.ViewHolder(view){
    var radTextview = view.findViewById<TextView>(R.id.nyTV)
}

fun CheckIfValueIsClickable(position: Int, chart: Int) : Boolean{
    //  var u = 0
    var tempPosition = position


    if (chart == 2){
        tempPosition = tempPosition + 25
    }

    if (chart == 3){
        tempPosition = tempPosition + 50
    }

    var row = tempPosition / 5
    var column = tempPosition % 5

        for (r in 0..34) {
            when (column) {
                1 -> {
                    if (prevNumbers[r] == BingoValueI[row]) {
                        return true
                    }
                }
                2 -> {
                    if (prevNumbers[r] == BingoValueN[row]) {
                        return true
                    }
                }
                3 -> {
                    if (prevNumbers[r] == BingoValueG[row]) {
                        return true
                    }
                }
                4 -> {
                    if (prevNumbers[r] == BingoValueO[row]) {
                        return true
                    }
                }
                else -> {
                    if (prevNumbers[r] == BingoValueB[row]) {
                        return true
                    }
                }
            }
        }

    return false
}

fun checkIfBingo2() {
  /*  if ((waitToTheLastToShowLooser) && walkfiniched()) {
        self.proofOfWinnerAlert.isHidden = false
        self.winnerTV.isHidden = false
        self.winnerTV.text = "Deltagare " + WinnerIs + " har fått BINGO"
        proofOfWinnerAlert.text = "OBS! När du trycker NÄSTA så kan du inte se bingobrickorna igen"
    }*/
}

// remember. If I start the same walk over and over and winner is written to database, there will be no alert.
fun checkIfBingo():Boolean{
    var countInRow1 = 0
    var countInRow2 = 0
    var countInRow3 = 0
    var countInRow4 = 0
    var countInRow5 = 0
    var weHaveAWinner = false

 /*   if ((waitToTheLastToShowLooser) && walkfiniched()){
        self.proofOfWinnerAlert.isHidden = false
        self.winnerTV.isHidden = false
        self.winnerTV.text = "Deltagare " + WinnerIs + " har fått BINGO"
        proofOfWinnerAlert.text = "OBS! När du trycker NÄSTA så kan du inte se bingobrickorna igen"
    }*/


    for (i in 0..4){
        if (cellsThatAreGrayChart1[i] != 0){
            countInRow1 += 1
        }
    }
    for (i in 5..9){
        if (cellsThatAreGrayChart1[i] != 0){
            countInRow2 += 1
        }
    }
    for (i in 10..14){
        if (cellsThatAreGrayChart1[i] != 0){
            countInRow3 += 1
        }
    }
    for (i in 15..19){
        if (cellsThatAreGrayChart1[i] != 0){
            countInRow4 += 1
        }
    }
    for (i in 20..24){
        if (cellsThatAreGrayChart1[i] != 0){
            countInRow5 += 1
        }
    }

    if ((countInRow1 == 5) || (countInRow2 == 5) || (countInRow3 == 5) || (countInRow4 == 5) || (countInRow5 == 5)){
        print("vinnare")
        weHaveAWinner = true
    }

    countInRow1 = 0
    countInRow2 = 0
    countInRow3 = 0
    countInRow4 = 0
    countInRow5 = 0

    for (i in 0..4){
        if (cellsThatAreGrayChart2[i] != 0){
            countInRow1 += 1
        }
    }
    for (i in 5..9){
        if (cellsThatAreGrayChart2[i] != 0){
            countInRow2 += 1
        }
    }
    for (i in 10..14){
        if (cellsThatAreGrayChart2[i] != 0){
            countInRow3 += 1
        }
    }
    for (i in 15..19){
        if (cellsThatAreGrayChart2[i] != 0){
            countInRow4 += 1
        }
    }
    for (i in 20..24){
        if (cellsThatAreGrayChart2[i] != 0){
            countInRow5 += 1
        }
    }

    if ((countInRow1 == 5) || (countInRow2 == 5) || (countInRow3 == 5) || (countInRow4 == 5) || (countInRow5 == 5)){
        print("vinnare")
        weHaveAWinner = true
    }

    countInRow1 = 0
    countInRow2 = 0
    countInRow3 = 0
    countInRow4 = 0
    countInRow5 = 0

    for (i in 0..4){
        if (cellsThatAreGrayChart3[i] != 0){
            countInRow1 += 1
        }
    }
    for (i in 5..9){
        if (cellsThatAreGrayChart3[i] != 0){
            countInRow2 += 1
        }
    }
    for (i in 10..14){
        if (cellsThatAreGrayChart3[i] != 0){
            countInRow3 += 1
        }
    }
    for (i in 15..19){
        if (cellsThatAreGrayChart3[i] != 0){
            countInRow4 += 1
        }
    }
    for (i in 20..24){
        if (cellsThatAreGrayChart3[i] != 0){
            countInRow5 += 1
        }
    }

    if ((countInRow1 == 5) || (countInRow2 == 5) || (countInRow3 == 5) || (countInRow4 == 5) || (countInRow5 == 5)){
        print("vinnare")
        weHaveAWinner = true
    }

    /// column
    if ((cellsThatAreGrayChart1[0] != 0) && (cellsThatAreGrayChart1[5] != 0) && (cellsThatAreGrayChart1[10] != 0) && (cellsThatAreGrayChart1[15] != 0) && (cellsThatAreGrayChart1[20] != 0)){
        print("vinnare")
        weHaveAWinner = true
    }

    if ((cellsThatAreGrayChart1[0+1] != 0) && (cellsThatAreGrayChart1[5+1] != 0) && (cellsThatAreGrayChart1[10+1] != 0) && (cellsThatAreGrayChart1[15+1] != 0) && (cellsThatAreGrayChart1[20+1] != 0)){
        print("vinnare")
        weHaveAWinner = true
    }

    if ((cellsThatAreGrayChart1[0+2] != 0) && (cellsThatAreGrayChart1[5+2] != 0) && (cellsThatAreGrayChart1[10+2] != 0) && (cellsThatAreGrayChart1[15+2] != 0) && (cellsThatAreGrayChart1[20+2] != 0)){
        print("vinnare")
        weHaveAWinner = true
    }

    if ((cellsThatAreGrayChart1[0+3] != 0) && (cellsThatAreGrayChart1[5+3] != 0) && (cellsThatAreGrayChart1[10+3] != 0) && (cellsThatAreGrayChart1[15+3] != 0) && (cellsThatAreGrayChart1[20+3] != 0)){
        print("vinnare")
        weHaveAWinner = true
    }

    if ((cellsThatAreGrayChart1[0+4] != 0) && (cellsThatAreGrayChart1[5+4] != 0) && (cellsThatAreGrayChart1[10+4] != 0) && (cellsThatAreGrayChart1[15+4] != 0) && (cellsThatAreGrayChart1[20+4] != 0)){
        print("vinnare")
        weHaveAWinner = true
    }
    ///

    if ((cellsThatAreGrayChart2[0] != 0) && (cellsThatAreGrayChart2[5] != 0) && (cellsThatAreGrayChart2[10] != 0) && (cellsThatAreGrayChart2[15] != 0) && (cellsThatAreGrayChart2[20] != 0)){
        print("vinnare")
        weHaveAWinner = true
    }

    if ((cellsThatAreGrayChart2[0+1] != 0) && (cellsThatAreGrayChart2[5+1] != 0) && (cellsThatAreGrayChart2[10+1] != 0) && (cellsThatAreGrayChart2[15+1] != 0) && (cellsThatAreGrayChart2[20+1] != 0)){
        print("vinnare")
        weHaveAWinner = true
    }

    if ((cellsThatAreGrayChart2[0+2] != 0) && (cellsThatAreGrayChart2[5+2] != 0) && (cellsThatAreGrayChart2[10+2] != 0) && (cellsThatAreGrayChart2[15+2] != 0) && (cellsThatAreGrayChart2[20+2] != 0)){
        print("vinnare")
        weHaveAWinner = true
    }

    if ((cellsThatAreGrayChart2[0+3] != 0) && (cellsThatAreGrayChart2[5+3] != 0) && (cellsThatAreGrayChart2[10+3] != 0) && (cellsThatAreGrayChart2[15+3] != 0) && (cellsThatAreGrayChart2[20+3] != 0)){
        print("vinnare")
        weHaveAWinner = true
    }

    if ((cellsThatAreGrayChart2[0+4] != 0) && (cellsThatAreGrayChart2[5+4] != 0) && (cellsThatAreGrayChart2[10+4] != 0) && (cellsThatAreGrayChart2[15+4] != 0) && (cellsThatAreGrayChart2[20+4] != 0)){
        print("vinnare")
        weHaveAWinner = true
    }
    ///

    if ((cellsThatAreGrayChart3[0] != 0) && (cellsThatAreGrayChart3[5] != 0) && (cellsThatAreGrayChart3[10] != 0) && (cellsThatAreGrayChart3[15] != 0) && (cellsThatAreGrayChart3[20] != 0)){
        print("vinnare")
        weHaveAWinner = true
    }

    if ((cellsThatAreGrayChart3[0+1] != 0) && (cellsThatAreGrayChart3[5+1] != 0) && (cellsThatAreGrayChart3[10+1] != 0) && (cellsThatAreGrayChart3[15+1] != 0) && (cellsThatAreGrayChart3[20+1] != 0)){
        print("vinnare")
        weHaveAWinner = true
    }

    if ((cellsThatAreGrayChart3[0+2] != 0) && (cellsThatAreGrayChart3[5+2] != 0) && (cellsThatAreGrayChart3[10+2] != 0) && (cellsThatAreGrayChart3[15+2] != 0) && (cellsThatAreGrayChart3[20+2] != 0)){
        print("vinnare")
        weHaveAWinner = true
    }

    if ((cellsThatAreGrayChart3[0+3] != 0) && (cellsThatAreGrayChart3[5+3] != 0) && (cellsThatAreGrayChart3[10+3] != 0) && (cellsThatAreGrayChart3[15+3] != 0) && (cellsThatAreGrayChart3[20+3] != 0)){
        print("vinnare")
        weHaveAWinner = true
    }

    if ((cellsThatAreGrayChart3[0+4] != 0) && (cellsThatAreGrayChart3[5+4] != 0) && (cellsThatAreGrayChart3[10+4] != 0) && (cellsThatAreGrayChart3[15+4] != 0) && (cellsThatAreGrayChart3[20+4] != 0)){
        print("vinnare")
        weHaveAWinner = true
    }

    // diagonalerna
    if ((cellsThatAreGrayChart1[0] != 0) && (cellsThatAreGrayChart1[5+1] != 0) && (cellsThatAreGrayChart1[10+2] != 0) && (cellsThatAreGrayChart1[15+3] != 0) && (cellsThatAreGrayChart1[20+4] != 0)){
        print("vinnare")
        weHaveAWinner = true
    }

    if ((cellsThatAreGrayChart1[0+4] != 0) && (cellsThatAreGrayChart1[5+3] != 0) && (cellsThatAreGrayChart1[10+2] != 0) && (cellsThatAreGrayChart1[15+1] != 0) && (cellsThatAreGrayChart1[20+0] != 0)){
        print("vinnare")
        weHaveAWinner = true
    }

    if ((cellsThatAreGrayChart2[0] != 0) && (cellsThatAreGrayChart2[5+1] != 0) && (cellsThatAreGrayChart2[10+2] != 0) && (cellsThatAreGrayChart2[15+3] != 0) && (cellsThatAreGrayChart2[20+4] != 0)){
        print("vinnare")
        weHaveAWinner = true
    }

    if ((cellsThatAreGrayChart2[0+4] != 0) && (cellsThatAreGrayChart2[5+3] != 0) && (cellsThatAreGrayChart2[10+2] != 0) && (cellsThatAreGrayChart2[15+1] != 0) && (cellsThatAreGrayChart2[20+0] != 0)){
        print("vinnare")
        weHaveAWinner = true
    }

    if ((cellsThatAreGrayChart3[0] != 0) && (cellsThatAreGrayChart3[5+1] != 0) && (cellsThatAreGrayChart3[10+2] != 0) && (cellsThatAreGrayChart3[15+3] != 0) && (cellsThatAreGrayChart3[20+4] != 0)){
        print("vinnare")
        weHaveAWinner = true
    }

    if ((cellsThatAreGrayChart3[0+4] != 0) && (cellsThatAreGrayChart3[5+3] != 0) && (cellsThatAreGrayChart3[10+2] != 0) && (cellsThatAreGrayChart3[15+1] != 0) && (cellsThatAreGrayChart3[20+0] != 0)){
        print("vinnare")
        weHaveAWinner = true
    }

    if (weHaveAWinner){
        weHaveAWinner = true
    }
    return  weHaveAWinner
}


