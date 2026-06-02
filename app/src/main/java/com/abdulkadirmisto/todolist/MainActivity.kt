package com.abdulkadirmisto.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.abdulkadirmisto.todolist.ui.theme.ToDoListTheme
import com.abdulkadirmisto.todolist.ui.theme.screen.TodoScreen

// Define light and dark color schemes
private val LightColors = lightColorScheme(
    primary = Color(0xFF3F51B5),
    secondary = Color(0xFF03DAC6),
    tertiary = Color(0xFFFFB74D),

    background = Color(0xFFFBE9E7),
    surface = Color(0xFFF5F5F6),

    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color(0xFF1C1C1C),
    onSurface = Color(0xFF1C1C1C)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF7986CB),
    secondary = Color(0xFF26C6DA),
    tertiary = Color(0xFFFFB74D),

    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),

    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            var isDarkMode = remember { mutableStateOf(false) }

            val colors = if (isDarkMode.value) DarkColors else LightColors

            MaterialTheme(colorScheme = colors) {
                TodoScreen(
                    isDarkMode = isDarkMode.value,
                    onToggleTheme = { isDarkMode.value = !isDarkMode.value }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ToDoListTheme {
    }
}