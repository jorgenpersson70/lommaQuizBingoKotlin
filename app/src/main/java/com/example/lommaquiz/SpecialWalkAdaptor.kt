package com.example.lommaquiz

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

var selectedWalk = ""

class SpecialWalkAdaptor(var mContext: Context) : RecyclerView.Adapter<HappyViewHolder6>() {
    var names = mutableListOf<String>()
    var marked = mutableListOf<Boolean>()
    var markedPosition = 0

    var putExtraString = ""

    var changeInQuiz = false

    fun change(changeQuiz : Boolean){
        changeInQuiz = changeQuiz
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HappyViewHolder6 {
        val vh = HappyViewHolder6(LayoutInflater.from(parent.context).inflate(R.layout.row_in_special_walk_list2, parent, false))
        return vh
    }

    override fun getItemCount(): Int {
        return names.size
    }

    override fun onBindViewHolder(holder: HappyViewHolder6, position: Int){
        holder.radTextview.text = names[position]

     /*   for (i in 0..names.size-1){
            marked[i] = false
        }
        if (marked[position]) {
            holder.radTextview.setBackgroundColor(android.graphics.Color.YELLOW)
        }*/

        if (markedPosition == position){
            holder.radTextview.setBackgroundColor(android.graphics.Color.YELLOW)
        }else{
            holder.radTextview.setBackgroundColor(android.graphics.Color.WHITE)
        }

        holder.radTextview.setOnClickListener {

            markedPosition = position
            selectedWalk = names[position]
     //       marked[position] = true

            holder.radTextview.setBackgroundColor(android.graphics.Color.YELLOW)

        //    finich
    /*        if (changeInQuiz) {
                val intent = Intent(mContext, CreateQuiz::class.java)
                intent.putExtra("from", "MyAdaptor")
                intent.putExtra("quizName", holder.radTextview.text)
                mContext.startActivity(intent)
            }*/

            // This makes redraw of every line, takes away yellow from previous choice
            notifyDataSetChanged()
        }

    }

}

class HappyViewHolder6 (view: View) : RecyclerView.ViewHolder(view){
  //  var radTextview = view.findViewById<TextView>(R.id.specialWalkTV)
  var radTextview = view.findViewById<Button>(R.id.listWalksBtn)
}