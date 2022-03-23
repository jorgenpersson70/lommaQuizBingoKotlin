package com.example.lommaquiz

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView


// class HomeScreenRecyclerAdapter(var mContext:Context) : RecyclerView.Adapter<HomeScreenRecyclerAdapter.ViewHolder>()

class MyAdapter(var mContext: Context) : RecyclerView.Adapter<HappyViewHolder>() {
    var names = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HappyViewHolder {
        val vh = HappyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_in_quiz_list, parent, false))
        return vh
    }

    override fun getItemCount(): Int {
        return names.size
    }

    override fun onBindViewHolder(holder: HappyViewHolder, position: Int){
        holder.radTextview.text = names[position]

        holder.radTextview.setOnClickListener {
            QuizPositionSelect = position

            val intent = Intent(mContext, showQuestions::class.java)
            intent.putExtra("walk", holder.radTextview.text)
            mContext.startActivity(intent)

         //   mContext.startActivity(Intent(mContext, showQuestions::class.java))

         /*
          val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("walk", "Walk1")

                startActivity(intent)
         */

        }






    }

}

class HappyViewHolder (view: View) : RecyclerView.ViewHolder(view){
    var radTextview = view.findViewById<TextView>(R.id.QuizLineTV)
}

