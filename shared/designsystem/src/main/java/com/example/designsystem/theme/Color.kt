package com.example.designsystem.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color

val BaseYellow = Color(0xFFFFDD2D)
val AmberContainer = Color(0xFFFFD54F)
val LightSurfaceGray = Color(0xFFF3F4F7)
val DarkSurfaceGray = Color(0xFF1E1E1E)
val White = Color(0xFFFFFFFF)
val DarkGray = Color(0xFF333333)
val MediumGray = Color(0xFF757575)

val SuccessGreenLight = Color(0xFFE8F5E8)
val SuccessGreenDark = Color(0xFF1B5E20)
val SuccessGreenIconLight = Color(0xFF2E7D32)
val SuccessGreenIconDark = Color(0xFF81C784)

val ErrorRedLight = Color(0xFFFFEBEE)
val ErrorRedDark = Color(0xFFB71C1C)
val ErrorRedIconLight = Color(0xFFC62828)
val ErrorRedIconDark = Color(0xFFEF5350)

val ColorScheme.customOnBackgroundColor : Color
    get() = MediumGray

val ColorScheme.learnDeckColor: Color
    get() = DarkSurfaceGray

val ColorScheme.repeatDeckColor: Color
    get() = LightSurfaceGray

val ColorScheme.learnDeckTextColor: Color
    get() = White

val ColorScheme.repeatDeckTextColor: Color
    get() = DarkGray

data class ActionButtonColors(
    val container : Color,
    val icon : Color
)