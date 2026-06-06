package com.example.trucocounter.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import androidx.core.os.BundleCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.trucocounter.R
import com.example.trucocounter.model.Player
import com.example.trucocounter.model.PlayerPoints
import com.example.trucocounter.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var playerOne = Player("")
    private var playerTwo = Player("")

    private val changeNamesLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            if (data != null) {
                val updatedPlayerOne = IntentCompat.getSerializableExtra(data, "Player one", Player::class.java)
                val updatedPlayerTwo = IntentCompat.getSerializableExtra(data, "Player two", Player::class.java)

                updatedPlayerOne?.let { playerOne.name = it.name }
                updatedPlayerTwo?.let { playerTwo.name = it.name }
                updateUI()
            }
        }
    }

    companion object {
        private const val MAX_SCORE = 12
        private const val KEY_PLAYER_ONE = "player_one_state"
        private const val KEY_PLAYER_TWO = "player_two_state"
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

        if (savedInstanceState != null) {
            BundleCompat.getSerializable(savedInstanceState, KEY_PLAYER_ONE, Player::class.java)?.let {
                playerOne = it
            }
            BundleCompat.getSerializable(savedInstanceState, KEY_PLAYER_TWO, Player::class.java)?.let {
                playerTwo = it
            }
        } else {
            playerOne = Player(getString(R.string.player_one))
            playerTwo = Player(getString(R.string.player_two))
        }

        setupClickListeners()
        updateUI()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY_PLAYER_ONE, playerOne)
        outState.putSerializable(KEY_PLAYER_TWO, playerTwo)
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
            MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.winner))
                .setMessage(getString(R.string.wins_this_match, player.name))
                .setCancelable(false)
                .setPositiveButton(getString(android.R.string.ok)) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
            resetMatch()
        }
    }

    private fun resetMatch() {
        playerOne.score = 0
        playerTwo.score = 0
        updateUI()
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI() {
        binding.tvPlayerOneName.text = "${playerOne.name}:"
        binding.tvPlayerOnePoints.text = playerOne.score.toString()
        
        binding.tvPlayerTwoName.text = "${playerTwo.name}:"
        binding.tvPlayerTwoPoints.text = playerTwo.score.toString()
    }

    fun showMatchHistory() {
        val intent = Intent(this, MatchHistoryActivity::class.java).apply {
            putExtra("Player one", playerOne)
            putExtra("Player two", playerTwo)
        }
        startActivity(intent)
    }

    fun cleanMatchHistory() {
        playerOne.wins = 0
        playerTwo.wins = 0
        resetMatch()
        Toast.makeText(this, getString(R.string.match_history_was_cleaned), Toast.LENGTH_SHORT).show()
    }

    fun changePlayerNames() {
        val intent = Intent(this, ChangePlayersNamesActivity::class.java).apply {
            putExtra("Player one", playerOne)
            putExtra("Player two", playerTwo)
        }
        changeNamesLauncher.launch(intent)
    }
}
