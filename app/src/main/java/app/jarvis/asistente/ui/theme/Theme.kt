package app.jarvis.asistente.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Paleta JARVIS (cian). Todos los colores de la app salen de aquí.
val JarvisFondo = Color(0xFF05080F)
val JarvisPanel = Color(0xFF0A1420)
val JarvisCian = Color(0xFF00E5FF)
val JarvisTexto = Color(0xFFD6F6FF)
val JarvisTextoSuave = Color(0xFF7FA6B3)
val JarvisOk = Color(0xFF3DDC97)
val JarvisAviso = Color(0xFFFFB74D)

private val EsquemaOscuro = darkColorScheme(
    primary = JarvisCian,
    background = JarvisFondo,
    surface = JarvisPanel,
    onPrimary = JarvisFondo,
    onBackground = JarvisTexto,
    onSurface = JarvisTexto,
)

@Composable
fun JarvisTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = EsquemaOscuro, content = content)
}
