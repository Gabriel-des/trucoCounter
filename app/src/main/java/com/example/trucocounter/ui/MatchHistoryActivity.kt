package com.example.trucocounter.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.trucocounter.databinding.ActivityMatchHistoryBinding
import com.example.trucocounter.model.Player

class MatchHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMatchHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMatchHistoryBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setPlayersWins()
    }

    @SuppressLint("SetTextI18n")
    private fun setPlayersWins() {
        val playerOne = IntentCompat.getSerializableExtra(intent, "Player one", Player::class.java)
        val playerTwo = IntentCompat.getSerializableExtra(intent, "Player two", Player::class.java)

        binding.tvPlayerOneWinsMatches.text = "${playerOne?.name}: ${playerOne?.wins}"
        binding.tvPlayerTwoWinsMatches.text = "${playerTwo?.name}: ${playerTwo?.wins}"
    }
}