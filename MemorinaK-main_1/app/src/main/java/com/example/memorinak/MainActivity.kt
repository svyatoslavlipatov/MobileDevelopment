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

    private val cards = mutableListOf<ImageView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(applicationContext)
        layout.orientation = LinearLayout.VERTICAL

        val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.weight = 1.toFloat()

        // Создаем пары карт
        for (i in 0 until cardImages.size) {
            val card1 = createCard()
            val card2 = createCard()

            cards.add(card1)
            cards.add(card2)
        }

        // Перемешиваем карты
        cards.shuffle()

        // Распределяем карты по рядам
        val rows = Array(4) { LinearLayout(applicationContext) }

        for ((index, card) in cards.withIndex()) {
            val row: Int = index / 4
            rows[row].addView(card)
        }

        for (row in rows) {
            layout.addView(row)
        }

        setContentView(layout)
    }

    private fun createCard(): ImageView {
        return ImageView(applicationContext).apply {
            setImageResource(backImage)
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            setOnClickListener(cardClickListener)
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
            // Первая карта в паре
            firstCard = card
            card.setImageResource(cardImages[cards.indexOf(card) / 2])
        } else if (secondCard == null && card != firstCard) {
            // Вторая карта в паре
            secondCard = card
            card.setImageResource(cardImages[cards.indexOf(card) / 2])

            // Проверяем совпадение пары карт
            checkMatch()
        }
    }

    private fun checkMatch() {
        GlobalScope.launch(Dispatchers.Main) {
            delay(1000)

            if (firstCard?.drawable?.constantState == secondCard?.drawable?.constantState) {
                // Пара совпала
                firstCard?.visibility = View.INVISIBLE
                secondCard?.visibility = View.INVISIBLE
                visibleCardCount -= 2 // Уменьшаем счетчик видимых карт

                if (visibleCardCount == 0) {
                    showWinMessage()
                }
            } else {
                // Пара не совпала, переворачиваем обратно
                firstCard?.setImageResource(backImage)
                secondCard?.setImageResource(backImage)
            }

            // Сбрасываем выбранные карты
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