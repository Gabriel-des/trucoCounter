package com.example.trucocounter.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.trucocounter.databinding.ActivityChangePlayersNamesBinding
import com.example.trucocounter.model.Player

class ChangePlayersNamesActivity : AppCompatActivity() {

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

        setupUI()
        setupClickListeners()
    }

    private fun setupUI() {
        binding.etPlayerOneEditName.setText(playerOne?.name)
        binding.etPlayerTwoEditName.setText(playerTwo?.name)

        updateLabels()
    }

    private fun setupClickListeners() {
        binding.btConfirmChangePlayersName.setOnClickListener { confirmChangeName() }
    }

    private fun confirmChangeName() {
        val nameOne = binding.etPlayerOneEditName.text.toString().trim()
        val nameTwo = binding.etPlayerTwoEditName.text.toString().trim()

        if (nameOne.isEmpty() || nameTwo.isEmpty()) {
            Toast.makeText(this, "Names cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        playerOne?.name = nameOne
        playerTwo?.name = nameTwo
        
        intent.putExtra("Player one", playerOne)
        intent.putExtra("Player two", playerTwo)
        
        setResult(RESULT_OK, intent)
        finish()
    }

    @SuppressLint("SetTextI18n")
    private fun updateLabels() {
        binding.tvPlayerOneName.text = "Player One:"
        binding.tvPlayerTwoName.text = "Player Two:"
    }
}
