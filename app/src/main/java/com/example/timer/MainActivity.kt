package com.example.timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var countDownTimer: CountDownTimer? = null
    private var timerDuration: Long = 60000

    // pauseOffset = timerDuration - timeLeft
    private var pauseOffSet: Long = 0
    private var tvTimer: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvTimer = findViewById(R.id.tvTimer)
        val btnStart: Button = findViewById(R.id.btnStart)
        val btnReset: Button = findViewById(R.id.btnReset)
        val btnPause: Button = findViewById(R.id.btnPause)

        tvTimer?.text = (timerDuration / 10000).toString()
        btnStart.setOnClickListener {
            startTimer(pauseOffSet)
        }
        btnPause.setOnClickListener {
            pauseTimer()
        }
        btnReset.setOnClickListener {
            resetTimer()
        }

    }

    private fun resetTimer() {
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
            tvTimer?.text = (timerDuration / 1000).toString()
            countDownTimer = null
            pauseOffSet = 0

        }
    }

    private fun pauseTimer() {
        TODO("Not yet implemented")
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
        }
    }

    private fun startTimer(pauseOffSet: Long) {
        countDownTimer = object : CountDownTimer(timerDuration - pauseOffSet, 1000) {
            /**
             * This method is called every countDownInterval
             */
            override fun onTick(millisUntilFinished: Long) {
                TODO("Not yet implemented")
                pauseOffSet = timerDuration - millisUntilFinished
                tvTimer?.text = (millisUntilFinished / 1000).toString()

            }

            override fun onFinish() {
                TODO("Not yet implemented")
                Toast.makeText(this@MainActivity, "Timer is finished", Toast.LENGTH_SHORT).show()
            }

        }.start()
    }
}