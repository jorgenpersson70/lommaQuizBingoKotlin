package com.example.lommaquiz

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

var bingoNumber3 = 0

// ValuesToPresentBRead

// Adapter
class BingoAllNbrAdaptor : RecyclerView.Adapter<HappyViewHolder5>() {
    var names = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HappyViewHolder5 {
        val vh = HappyViewHolder5(LayoutInflater.from(parent.context).inflate(R.layout.all_values_rv, parent, false))
        return vh
    }

    override fun getItemCount(): Int {
        return 35
    }

    override fun onBindViewHolder(holder: HappyViewHolder5, position: Int){
        var test = 0

        var row = bingoNumber3 / 5

   /*     if ((bingoNumber3 % 5) == 0) {
            test = ValuesToPresentBRead[row]
        }
        if ((bingoNumber3 % 5) == 1) {
            test = ValuesToPresentIRead[row]
        }
        if ((bingoNumber3 % 5) == 2) {
            test = ValuesToPresentNRead[row]
        }
        if ((bingoNumber3 % 5) == 3) {
            test = ValuesToPresentGRead[row]
        }
        if ((bingoNumber3 % 5) == 4) {
            test = ValuesToPresentORead[row]
        }*/
   // funkade     test = NumbersToShow[bingoNumber3]
test = prevNumbers[bingoNumber3]
        holder.radTextview.text = test.toString()
        holder.radTextview.setBackgroundColor(Color.WHITE)
        bingoNumber3 = bingoNumber3 + 1

        Log.i("bingo", test.toString())
    }

}

class HappyViewHolder5 (view: View) : RecyclerView.ViewHolder(view){
    var radTextview = view.findViewById<TextView>(R.id.bingoAllValuesRowTV)
}