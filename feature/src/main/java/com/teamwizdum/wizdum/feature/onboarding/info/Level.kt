package com.teamwizdum.wizdum.feature.onboarding.info

enum class Level(val rating: String, val comment: String) {
    HIGH("⭐⭐⭐", "전문가처럼 활용하기"),
    MEDIUM("⭐⭐", "심화 발전하기"),
    LOW("⭐", "기초다지기");

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