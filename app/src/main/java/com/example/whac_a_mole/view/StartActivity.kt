package com.example.whac_a_mole.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.whac_a_mole.R
import com.example.whac_a_mole.SharedPreference
import kotlin.properties.Delegates


class StartActivity : AppCompatActivity() {

    private lateinit var btn_play:Button
    private lateinit var txt_max_score:TextView
    private lateinit var btn_resetScore:Button

    private lateinit var six_btn:RadioButton
    private lateinit var seven_btn:RadioButton
    private lateinit var eight_btn:RadioButton
    private lateinit var nine_btn:RadioButton

    var size by Delegates.notNull<Int>()

    lateinit var sharedPreference: SharedPreference

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        sharedPreference = SharedPreference(this)

        size = 9

        initView()

        txt_max_score.text = "Max score: ${sharedPreference.getValueInt("score").toString()}"

        initListeners()


    }

    @SuppressLint("SetTextI18n")
    private fun initListeners() {
        btn_play.setOnClickListener {
            val  intent: Intent = Intent(this, GameScreenActivity::class.java)
            intent.putExtra("size", size)
            startActivity(intent)
        }

        btn_resetScore.setOnClickListener{
            sharedPreference.save("score",0)
            txt_max_score.text = "Max score: 0"
            Toast.makeText(this, "Best score reset!",Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        txt_max_score.text = "Max score: ${sharedPreference.getValueInt("score").toString()}"
    }

    private fun initView() {
        btn_play = findViewById(R.id.play_btn_id)
        txt_max_score = findViewById(R.id.max_score_txt_id)
        btn_resetScore = findViewById(R.id.reset_score_btn_id)

        six_btn = findViewById(R.id.radio_six)
        six_btn.setOnClickListener{
            size = 6
        }

        seven_btn = findViewById(R.id.radio_seven)
        seven_btn.setOnClickListener{
            size = 7
        }

        eight_btn = findViewById(R.id.radio_eight)
        eight_btn.setOnClickListener{
            size = 8
        }

        nine_btn = findViewById(R.id.radio_nine)
        nine_btn.setOnClickListener {
            size=9
        }
    }

}


