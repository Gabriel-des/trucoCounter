package com.example.trucocounter.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import androidx.core.os.BundleCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.trucocounter.databinding.ActivityMatchHistoryBinding
import com.example.trucocounter.model.Player

class MatchHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMatchHistoryBinding
    private var playerOne: Player? = null
    private var playerTwo: Player? = null

    companion object {
        private const val KEY_P1 = "p1_history"
        private const val KEY_P2 = "p2_history"
    }

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

        if (savedInstanceState != null) {
            playerOne = BundleCompat.getSerializable(savedInstanceState, KEY_P1, Player::class.java)
            playerTwo = BundleCompat.getSerializable(savedInstanceState, KEY_P2, Player::class.java)
        } else {
            playerOne = IntentCompat.getSerializableExtra(intent, "Player one", Player::class.java)
            playerTwo = IntentCompat.getSerializableExtra(intent, "Player two", Player::class.java)
        }

        displayHistory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY_P1, playerOne)
        outState.putSerializable(KEY_P2, playerTwo)
    }

    @SuppressLint("SetTextI18n")
    private fun displayHistory() {
        binding.tvPlayerOneWinsMatches.text = "${playerOne?.name}: ${playerOne?.wins}"
        binding.tvPlayerTwoWinsMatches.text = "${playerTwo?.name}: ${playerTwo?.wins}"
    }
}
