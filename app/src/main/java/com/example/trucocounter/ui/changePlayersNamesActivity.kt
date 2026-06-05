package com.example.trucocounter.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.trucocounter.R
import com.example.trucocounter.databinding.ActivityChangePlayersNamesBinding
import com.example.trucocounter.model.Player

class changePlayersNamesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePlayersNamesBinding
    private val playerOne by lazy { IntentCompat.getSerializableExtra(intent, "Player one", Player::class.java) }
    private val playerTwo by lazy { IntentCompat.getSerializableExtra(intent, "Player two", Player::class.java) }

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

        setupClickListeners()
        setPlayersNames()
    }

    private fun setupClickListeners() {
        binding.btConfirmChangePlayersName.setOnClickListener { confirmChangeName() }
    }

    private fun confirmChangeName() {
        playerOne?.name = binding.etPlayerOneEditName.text.toString()
        playerTwo?.name = binding.etPlayerTwoEditName.text.toString()
        
        intent.putExtra("Player one", playerOne)
        intent.putExtra("Player two", playerTwo)
        
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun setPlayersNames() {
        binding.tvPlayerOneName.text = playerOne?.name
        binding.tvPlayerTwoName.text = playerTwo?.name
    }
}
