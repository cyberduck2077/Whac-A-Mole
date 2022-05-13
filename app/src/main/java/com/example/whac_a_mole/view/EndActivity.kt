package com.example.whac_a_mole.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.whac_a_mole.R
import kotlin.properties.Delegates

class EndActivity : AppCompatActivity() {

    lateinit var score_txt:TextView
    lateinit var btn_toStart:Button
    lateinit var star1:View
    lateinit var star2:View
    lateinit var star3:View

    var score by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end)

        score = 0

        initView()



    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        val intent = getIntent()
        score = intent.getIntExtra("score", 0)

        val st = getString(R.string.your_score)
        score_txt.text = "${st} $score"


        //показываем звездочки
        if (score>12)
            star2.isVisible = true
        if(score>20)
            star3.isVisible =true
    }

    private fun initView() {
        score_txt = findViewById(R.id.gameover_score_txt_id)
        btn_toStart = findViewById(R.id.tryagain_btn_id)
        btn_toStart.setOnClickListener{
            val intent = Intent(this,GameScreenActivity::class.java)
            startActivity(intent)
        }

        star1 = findViewById(R.id.stars1_view_id)
        star2 = findViewById(R.id.stars2_view_id)
        star3 = findViewById(R.id.stars3_view_id)

    }
}

