package com.example.lommaquiz


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lommaquiz.databinding.ActivityMainBinding
import com.example.lommaquiz.databinding.ActivityNewfrontBinding
import com.google.android.gms.maps.model.LatLng

var coordinatesLatitudeDiff: ArrayList<Float> = ArrayList(mutableListOf(0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f))
var coordinatesLongitudeDiff: ArrayList<Float> = ArrayList(mutableListOf(0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f))


class newfront : AppCompatActivity() {
    private lateinit var binding: ActivityNewfrontBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewfrontBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.aktPosLatTV.text = myPos.latitude.toString()
        binding.aktPosLongTV.text = myPos.longitude.toString()

        binding.pos1LatTV.text = coordinatesLatitude[0].toString()
        binding.pos1LongTV.text = coordinatesLongitude[0].toString()
        binding.pos2LatTV.text = coordinatesLatitude[1].toString()
        binding.pos2LongTV.text = coordinatesLongitude[1].toString()
        binding.pos3LatTV.text = coordinatesLatitude[2].toString()
        binding.pos3LongTV.text = coordinatesLongitude[2].toString()
        binding.pos4LatTV.text = coordinatesLatitude[3].toString()
        binding.pos4LongTV.text = coordinatesLongitude[3].toString()
        binding.pos5LatTV.text = coordinatesLatitude[4].toString()
        binding.pos5LongTV.text = coordinatesLongitude[4].toString()
        binding.pos6LatTV.text = coordinatesLatitude[5].toString()
        binding.pos6LongTV.text = coordinatesLongitude[5].toString()
        binding.pos7LatTV.text = coordinatesLatitude[6].toString()
        binding.pos7LongTV.text = coordinatesLongitude[6].toString()
        binding.pos8LatTV.text = coordinatesLatitude[7].toString()
        binding.pos8LongTV.text = coordinatesLongitude[7].toString()
        binding.pos9LatTV.text = coordinatesLatitude[8].toString()
        binding.pos9LongTV.text = coordinatesLongitude[8].toString()
        binding.pos10LatTV.text = coordinatesLatitude[9].toString()
        binding.pos10LongTV.text = coordinatesLongitude[9].toString()
        binding.pos11LatTV.text = coordinatesLatitude[10].toString()
        binding.pos11LongTV.text = coordinatesLongitude[10].toString()
        binding.pos12LatTV.text = coordinatesLatitude[11].toString()
        binding.pos12LongTV.text = coordinatesLongitude[11].toString()

        for (i in 0..11)
        {
            coordinatesLatitudeDiff[i] = (myPos.latitude.toFloat() - coordinatesLatitude[i].toFloat()) * 100000.0f
            coordinatesLongitudeDiff[i] = (myPos.longitude.toFloat() - coordinatesLongitude[i].toFloat()) * 100000.0f
        }

        binding.diffpos1LatTV.text = coordinatesLatitudeDiff[0].toString()
        binding.diffpos1LongTV.text = coordinatesLongitudeDiff[0].toString()
        binding.diffpos2LatTV.text = coordinatesLatitudeDiff[1].toString()
        binding.diffpos2LongTV.text = coordinatesLongitudeDiff[1].toString()
        binding.diffpos3LatTV.text = coordinatesLatitudeDiff[2].toString()
        binding.diffpos3LongTV.text = coordinatesLongitudeDiff[2].toString()
        binding.diffpos4LatTV.text = coordinatesLatitudeDiff[3].toString()
        binding.diffpos4LongTV.text = coordinatesLongitudeDiff[3].toString()
        binding.diffpos5LatTV.text = coordinatesLatitudeDiff[4].toString()
        binding.diffpos5LongTV.text = coordinatesLongitudeDiff[4].toString()
        binding.diffpos6LatTV.text = coordinatesLatitudeDiff[5].toString()
        binding.diffpos6LongTV.text = coordinatesLongitudeDiff[5].toString()
        binding.diffpos7LatTV.text = coordinatesLatitudeDiff[6].toString()
        binding.diffpos7LongTV.text = coordinatesLongitudeDiff[6].toString()
        binding.diffpos8LatTV.text = coordinatesLatitudeDiff[7].toString()
        binding.diffpos8LongTV.text = coordinatesLongitudeDiff[7].toString()
        binding.diffpos9LatTV.text = coordinatesLatitudeDiff[8].toString()
        binding.diffpos9LongTV.text = coordinatesLongitudeDiff[8].toString()
        binding.diffpos10LatTV.text = coordinatesLatitudeDiff[9].toString()
        binding.diffpos10LongTV.text = coordinatesLongitudeDiff[9].toString()
        binding.diffpos11LatTV.text = coordinatesLatitudeDiff[10].toString()
        binding.diffpos11LongTV.text = coordinatesLongitudeDiff[10].toString()
        binding.diffpos12LatTV.text = coordinatesLatitudeDiff[11].toString()
        binding.diffpos12LongTV.text = coordinatesLongitudeDiff[11].toString()

    }
}