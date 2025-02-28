package com.teamwizdum.wizdum.feature.onboarding.info

enum class Level(val rating: String, val comment: String) {
    HIGH("⭐⭐⭐", "극강의 몰입 모드 ON!"),
    MEDIUM("⭐⭐", "적당히 강하게!"),
    LOW("⭐", "부담 없이 가볍게!");

    companion object {
        fun fromString(level: String): Level {
            return when (level.uppercase()) {
                "HIGH" -> HIGH
                "MEDIUM" -> MEDIUM
                "LOW" -> LOW
                else -> LOW
            }
        }
    }
}