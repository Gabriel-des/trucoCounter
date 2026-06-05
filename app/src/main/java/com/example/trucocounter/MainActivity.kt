package com.example.trucocounter

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.trucocounter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var scorePlayerOne = 0
    private var scorePlayerTwo = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btPlusOnePlayerOne.setOnClickListener { addPointsToPlayer(PlayerPoints.ONE, Player.ONE) }
        binding.btPlusTreePlayerOne.setOnClickListener { addPointsToPlayer(PlayerPoints.THREE, Player.ONE) }
        binding.btPlusSixPlayerOne.setOnClickListener { addPointsToPlayer(PlayerPoints.SIX, Player.ONE) }
        binding.btPlusNinePlayerOne.setOnClickListener { addPointsToPlayer(PlayerPoints.NINE, Player.ONE) }
        binding.btPlusTwelvePlayerOne.setOnClickListener { addPointsToPlayer(PlayerPoints.TWELVE, Player.ONE) }

        binding.btPlusOnePlayerTwo.setOnClickListener { addPointsToPlayer(PlayerPoints.ONE, Player.TWO) }
        binding.btPlusTreePlayerTwo.setOnClickListener { addPointsToPlayer(PlayerPoints.THREE, Player.TWO) }
        binding.btPlusSixPlayerTwo.setOnClickListener { addPointsToPlayer(PlayerPoints.SIX, Player.TWO) }
        binding.btPlusNinePlayerTwo.setOnClickListener { addPointsToPlayer(PlayerPoints.NINE, Player.TWO) }
        binding.btPlusTwelvePlayerTwo.setOnClickListener { addPointsToPlayer(PlayerPoints.TWELVE, Player.TWO) }
    }

    private fun addPointsToPlayer(points: PlayerPoints, player: Player) {
        if (player == Player.ONE) {
            scorePlayerOne += points.value
            binding.tvPlayerOnePoints.text = scorePlayerOne.toString()
        } else {
            scorePlayerTwo += points.value
            binding.tvPlayerTwoPoints.text = scorePlayerTwo.toString()
        }

        checkScoreBoard()
    }

    private fun checkScoreBoard() {
        TODO("Not yet implemented")
    }
}
