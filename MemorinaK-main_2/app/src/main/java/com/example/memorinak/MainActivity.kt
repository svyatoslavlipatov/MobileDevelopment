package com.example.memorinak

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import android.widget.Toast
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private val cardImages = listOf(
        R.drawable.cat,
        R.drawable.dog,
        R.drawable.frog,
        R.drawable.panda,
        R.drawable.pig,
        R.drawable.pinguin,
        R.drawable.tiger,
        R.drawable.rabbit
    )

    private var visibleCardCount = cardImages.size * 2
    private val backImage = R.drawable.rubashka
    private val cards = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(applicationContext)
        layout.orientation = LinearLayout.VERTICAL

        val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.weight = 1.toFloat()

        // Создаем список пар карт
        for (image in cardImages) {
            cards.add(image)
            cards.add(image)
        }

        // Перемешиваем пары карт
        cards.shuffle()

        val rows = Array(4) { LinearLayout(applicationContext) }

        for ((index, card) in cards.withIndex()) {
            val imageView = createCard(card)
            rows[index / 4].addView(imageView)
        }

        for (row in rows) {
            layout.addView(row)
        }

        setContentView(layout)
    }

    private fun createCard(image: Int): ImageView {
        return ImageView(applicationContext).apply {
            setImageResource(backImage)
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            setOnClickListener(cardClickListener)
            tag = image
        }
    }

    private var firstCard: ImageView? = null
    private var secondCard: ImageView? = null

    private val cardClickListener = View.OnClickListener { view ->
        if (view is ImageView) {
            flipCard(view)
        }
    }

    private fun flipCard(card: ImageView) {
        if (firstCard == null) {
            firstCard = card
            card.setImageResource(card.tag as Int) // Пикча из tag
        } else if (secondCard == null && card != firstCard) {
            secondCard = card
            card.setImageResource(card.tag as Int)
            checkMatch()
        }
    }

    private fun checkMatch() {
        GlobalScope.launch(Dispatchers.Main) {
            delay(1000)

            if (firstCard?.drawable?.constantState == secondCard?.drawable?.constantState) {
                // Пара совпала ура
                firstCard?.visibility = View.INVISIBLE
                secondCard?.visibility = View.INVISIBLE
                visibleCardCount -= 2

                if (visibleCardCount == 0) {
                    showWinMessage()
                }
            } else {
                // Пара не совпала эх, переворачиваем обратно карточку
                firstCard?.setImageResource(backImage)
                secondCard?.setImageResource(backImage)
            }

            // Сброс карт
            firstCard = null
            secondCard = null
        }
    }

    private fun showWinMessage() {
        val toast = Toast.makeText(applicationContext, "Урааа! Победаа!", Toast.LENGTH_LONG)
        val toastLayout = toast.view as LinearLayout?
        val toastTV = toastLayout?.getChildAt(0) as TextView?
        toastTV?.textSize = 50.toFloat()
        toast.show()
    }

}