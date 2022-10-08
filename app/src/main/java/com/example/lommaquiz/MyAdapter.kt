package com.example.lommaquiz

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(var mContext: Context) : RecyclerView.Adapter<HappyViewHolder>() {
    var names = mutableListOf<String>()
    var changeInQuiz = false

    fun change(changeQuiz : Boolean){
        changeInQuiz = changeQuiz
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HappyViewHolder {
        val vh = HappyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_in_quiz_list2, parent, false))
        return vh
    }

    override fun getItemCount(): Int {
        return names.size
    }

    override fun onBindViewHolder(holder: HappyViewHolder, position: Int){
        holder.radTextview.text = names[position]

        holder.radTextview.setOnClickListener {
      // har jag råkat att ta bort något ?      QuizPositionSelect = position

            if (changeInQuiz) {
                val intent = Intent(mContext, CreateQuiz::class.java)
                intent.putExtra("from", "MyAdaptor")
                intent.putExtra("quizName", holder.radTextview.text)
                mContext.startActivity(intent)
            }else{
                val intent = Intent(mContext, showMap::class.java)
                intent.putExtra("walk", holder.radTextview.text)
                mContext.startActivity(intent)
            }


        }
    }

}

class HappyViewHolder (view: View) : RecyclerView.ViewHolder(view){
  //  var radTextview = view.findViewById<TextView>(R.id.QuizLineTV)
  var radTextview = view.findViewById<TextView>(R.id.listQuizBtn)
}

