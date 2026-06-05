package com.example.trucocounter.model

import java.io.Serializable

data class Player(
    var name: String,
    var score: Int = 0,
    var wins: Int = 0
) : Serializable
