package com.example.whac_a_mole.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.whac_a_mole.R
import com.example.whac_a_mole.SharedPreference
import kotlin.properties.Delegates
import kotlin.random.Random


class GameScreenActivity : AppCompatActivity() {

    lateinit var tableLayout:TableLayout
    lateinit var timer_txt:TextView
    lateinit var score_txt:TextView
    lateinit var mainArray:ArrayList<ImageView>

    var ROWS by Delegates.notNull<Int>()
    var COLUMNS by Delegates.notNull<Int>()
    var count_holes by Delegates.notNull<Int>()
    var position by Delegates.notNull<Int>()

    var score by Delegates.notNull<Int>()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_screen)

        val sharedPreference:SharedPreference= SharedPreference(this)

        score = sharedPreference.getValueInt("score")

        count_holes = intent.getIntExtra("size", 0)

        ROWS = if(count_holes==6) 2 else 3

        COLUMNS = 3
        position = Random.nextInt(0,count_holes-1)

        score = 0

        mainArray = ArrayList<ImageView>()

        initViews()
        initTimer(sharedPreference)

        initTable()

        moleInit()

    }

    private fun moleInit() {
        val timer = object: CountDownTimer(30000, 500) {

            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {

                mainArray.get(position).setImageResource(R.drawable.ic_hole_test)
                mainArray.get(position).isClickable=false

                position = Random.nextInt(0,count_holes)

                mainArray.get(position).setImageResource(R.drawable.mole)
                mainArray.get(position).isClickable = true

            }

            override fun onFinish()
            {Log.d("TTT", "finish")}
        }
        timer.start()



    }

    @SuppressLint("SetTextI18n")
    private fun initTable() {
        for (i in 0 until ROWS) {

            val tableRow = TableRow(this)


            //задаем параметры для строки таблицы
            tableRow.layoutParams = LinearLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT)


            //заполняем строку таблицы
            for (j in 0 until COLUMNS) {


                //условие для последней строки даблицы
                if(i==ROWS-1 && j==count_holes-6)
                    break

                val imageView = ImageView(this)
                imageView.setImageResource(R.drawable.ic_hole_test)

                //присваиваем каждому элементу в таблице обработчик нажатий
                val _onClick:View.OnClickListener = View.OnClickListener {

                    if(it.isClickable) {
                        imageView.setImageResource(R.drawable.ic_hole_test)
                        score_txt.setText("Score: ${score++}")
                    }
                }

                imageView.setOnClickListener(_onClick)
                imageView.isClickable = false

                mainArray.add(imageView)//заполняем массив строки
                tableRow.addView(imageView, j)
            }

            tableLayout.addView(tableRow, i)


        }
    }



    private fun initViews() {
        tableLayout = findViewById<View>(R.id.table_id) as TableLayout
        timer_txt = findViewById(R.id.timer_tv_id)
        score_txt = findViewById(R.id.score_tv_id)
    }

    private fun initTimer(sharedPreference: SharedPreference) {

        val timer = object: CountDownTimer(30000, 1000) {

            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                timer_txt.setText("Time: ${millisUntilFinished / 1000}")

            }

            override fun onFinish() {
                Log.d("TTT", "finish")

                sharedPreference.save("score",score)
                val intent = Intent(this@GameScreenActivity, EndActivity::class.java).putExtra("score",score)
                startActivity(intent)


            }
        }
        timer.start()
    }
}

