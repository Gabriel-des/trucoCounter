package com.example.trucocounter.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.trucocounter.model.Player
import com.example.trucocounter.model.PlayerPoints
import com.example.trucocounter.R
import com.example.trucocounter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var playerOne = Player("Player One")
    private var playerTwo = Player("Player Two")

    private val changeNamesLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            if (data != null) {
                val updatedPlayerOne = IntentCompat.getSerializableExtra(data, "Player one", Player::class.java)
                val updatedPlayerTwo = IntentCompat.getSerializableExtra(data, "Player two", Player::class.java)
                
                if (updatedPlayerOne != null) playerOne = updatedPlayerOne
                if (updatedPlayerTwo != null) playerTwo = updatedPlayerTwo
                
                updateUI()
            }
        }
    }

    companion object {
        private const val MAX_SCORE = 12
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupClickListeners()
        updateUI()
    }

    private fun setupClickListeners() {
        binding.btPlusOnePlayerOne.setOnClickListener { addPointsToPlayer(playerOne, PlayerPoints.ONE) }
        binding.btPlusTreePlayerOne.setOnClickListener { addPointsToPlayer(playerOne, PlayerPoints.THREE) }
        binding.btPlusSixPlayerOne.setOnClickListener { addPointsToPlayer(playerOne, PlayerPoints.SIX) }
        binding.btPlusNinePlayerOne.setOnClickListener { addPointsToPlayer(playerOne, PlayerPoints.NINE) }
        binding.btPlusTwelvePlayerOne.setOnClickListener { addPointsToPlayer(playerOne, PlayerPoints.TWELVE) }

        binding.btPlusOnePlayerTwo.setOnClickListener { addPointsToPlayer(playerTwo, PlayerPoints.ONE) }
        binding.btPlusTreePlayerTwo.setOnClickListener { addPointsToPlayer(playerTwo, PlayerPoints.THREE) }
        binding.btPlusSixPlayerTwo.setOnClickListener { addPointsToPlayer(playerTwo, PlayerPoints.SIX) }
        binding.btPlusNinePlayerTwo.setOnClickListener { addPointsToPlayer(playerTwo, PlayerPoints.NINE) }
        binding.btPlusTwelvePlayerTwo.setOnClickListener { addPointsToPlayer(playerTwo, PlayerPoints.TWELVE) }

        binding.btMatchHistory.setOnClickListener { showMatchHistory() }
        binding.btCleanHistory.setOnClickListener { cleanMatchHistory() }
        binding.btTellNames.setOnClickListener { changePlayerNames() }
    }

    private fun addPointsToPlayer(player: Player, points: PlayerPoints) {
        player.score += points.value
        updateUI()
        checkScoreBoard(player)
    }

    private fun checkScoreBoard(player: Player) {
        if (player.score >= MAX_SCORE) {
            player.wins += 1
            val builder = AlertDialog.Builder(this)
            builder
                .setMessage("${player.name} wins this match!")
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
            resetMatch()
        }
    }

    private fun resetMatch() {
        playerOne.score = 0
        playerTwo.score = 0
        updateUI()
    }

    private fun updateUI() {
        binding.tvPlayerOneName.text = playerOne.name
        binding.tvPlayerOnePoints.text = playerOne.score.toString()
        
        binding.tvPlayerTwoName.text = playerTwo.name
        binding.tvPlayerTwoPoints.text = playerTwo.score.toString()
    }

    fun showMatchHistory() {
        val intent = Intent(this, matchHistoryActivity::class.java).apply {
            putExtra("Player one", playerOne)
            putExtra("Player two", playerTwo)
        }
        startActivity(intent)
    }

    fun cleanMatchHistory() {
        playerOne.wins = 0
        playerTwo.wins = 0
        resetMatch()

        Toast.makeText(
            this,
            "Match history was cleaned",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun changePlayerNames() {
        val intent = Intent(this, changePlayersNamesActivity::class.java).apply {
            putExtra("Player one", playerOne)
            putExtra("Player two", playerTwo)
        }
        changeNamesLauncher.launch(intent)
    }
}
