package com.example.portraitlandscapepresentk2023

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var adapter: ArrayAdapter<CharSequence>
    lateinit var iv: ImageView
    var currentImagePosition: Int = 0
    var currentImageResource: Int = R.drawable.squarecat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = ArrayAdapter.createFromResource(this, R.array.pictures, R.layout.item)
        val spinner = findViewById<Spinner>(R.id.pictures_list)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this

        iv = findViewById<ImageView>(R.id.picture)
        iv.setImageResource(currentImageResource)
    }


    fun onChangePictureClick(v: View) {
        currentImagePosition = (currentImagePosition + 1) % adapter.count
        currentImageResource = when (currentImagePosition) {
            0 -> R.drawable.car1
            1 -> R.drawable.car2
            2 -> R.drawable.car3
            else -> R.drawable.squarecat
        }
        iv.setImageResource(currentImageResource)
    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        currentImagePosition = position
        currentImageResource = when (position) {
            0 -> R.drawable.car1
            1 -> R.drawable.car2
            2 -> R.drawable.car3
            else -> R.drawable.squarecat
        }
        iv.setImageResource(currentImageResource)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(this, "не выбран элемент", Toast.LENGTH_SHORT ).show()
    }
}