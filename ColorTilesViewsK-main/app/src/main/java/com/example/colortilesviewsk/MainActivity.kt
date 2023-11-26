package com.example.colortilesviewsk

import android.R.attr.x
import android.R.attr.y
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

// тип для координат
data class Coord(val x: Int, val y: Int)

class MainActivity : AppCompatActivity() {

    lateinit var tiles: Array<Array<View>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tiles = Array(4) { i ->
            Array(4) { j ->
                findViewById<View>(resources.getIdentifier("t${i}${j}", "id", packageName))
            }
        }

        initField()
    }

    fun getCoordFromString(s: String): Coord {
        val x = s[0].toString().toInt()
        val y = s[1].toString().toInt()
        return Coord(x, y)
    }

    fun changeColor(view: View) {
        val brightColor = resources.getColor(R.color.bright)
        val darkColor = resources.getColor(R.color.dark)
        val drawable = view.background as ColorDrawable
        if (drawable.color == brightColor) {
            view.setBackgroundColor(darkColor)
        } else {
            view.setBackgroundColor(brightColor)
        }
    }

    fun onClick(v: View) {
        val coord = getCoordFromString(v.tag.toString())
        changeColor(v)

        for (i in 0..3) {
            changeColor(tiles[coord.x][i])
            changeColor(tiles[i][coord.y])
        }

        if (checkVictory()) {
            Toast.makeText(this, "Ураа, вы выиграли!", Toast.LENGTH_SHORT).show()
        }
    }

    fun checkVictory(): Boolean {
        val firstTileColor = (tiles[0][0].background as ColorDrawable).color
        for (i in 0..3) {
            for (j in 0..3) {
                val currentColor = (tiles[i][j].background as ColorDrawable).color
                if (currentColor != firstTileColor) {
                    return false
                }
            }
        }

        return true
    }

    fun initField() {
        for (i in 0..3) {
            for (j in 0..3) {
                val randomColor = (0..1).random()
                val color = if (randomColor == 0) {
                    resources.getColor(R.color.dark)
                } else {
                    resources.getColor(R.color.bright)
                }
                tiles[i][j].setBackgroundColor(color)
            }
        }
    }
}