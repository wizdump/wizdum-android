package com.teamwizdum.wizdum.feature.common.enums

enum class Level(
    val starCount: Int,
    val comment: String
) {
    HIGH(3, "전문가처럼 활용하기"),
    MEDIUM(2, "심화 발전하기"),
    LOW(1, "기초다지기");

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