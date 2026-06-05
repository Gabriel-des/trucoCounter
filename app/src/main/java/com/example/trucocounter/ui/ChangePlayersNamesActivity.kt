package com.example.trucocounter.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import androidx.core.os.BundleCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.trucocounter.R
import com.example.trucocounter.databinding.ActivityChangePlayersNamesBinding
import com.example.trucocounter.model.Player

class ChangePlayersNamesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePlayersNamesBinding
    
    private var playerOne: Player? = null
    private var playerTwo: Player? = null

    companion object {
        private const val KEY_P1 = "p1_state"
        private const val KEY_P2 = "p2_state"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChangePlayersNamesBinding.inflate(layoutInflater)
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

            binding.etPlayerOneEditName.setText(playerOne?.name)
            binding.etPlayerTwoEditName.setText(playerTwo?.name)
        }

        setupClickListeners()
        setupPlayersNames()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY_P1, playerOne)
        outState.putSerializable(KEY_P2, playerTwo)
    }

    private fun setupClickListeners() {
        binding.btConfirmChangePlayersName.setOnClickListener { confirmChangeName() }
    }

    private fun setupPlayersNames() {
        binding.etPlayerOneEditName.setText(playerOne?.name)
        binding.etPlayerTwoEditName.setText(playerTwo?.name)
    }

    private fun confirmChangeName() {
        val nameOne = binding.etPlayerOneEditName.text.toString().trim()
        val nameTwo = binding.etPlayerTwoEditName.text.toString().trim()

        if (nameOne.isEmpty() || nameTwo.isEmpty()) {
            Toast.makeText(this, getString(R.string.names_cannot_be_empty), Toast.LENGTH_SHORT).show()
            return
        }

        playerOne?.name = nameOne
        playerTwo?.name = nameTwo
        
        intent.putExtra("Player one", playerOne)
        intent.putExtra("Player two", playerTwo)
        
        setResult(RESULT_OK, intent)
        finish()
    }
}
