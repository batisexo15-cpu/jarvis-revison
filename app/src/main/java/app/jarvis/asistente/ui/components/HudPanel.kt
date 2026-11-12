package app.jarvis.asistente.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.jarvis.asistente.ui.theme.JarvisAviso
import app.jarvis.asistente.ui.theme.JarvisCian
import app.jarvis.asistente.ui.theme.JarvisPanel
import app.jarvis.asistente.ui.theme.JarvisTexto
import app.jarvis.asistente.ui.theme.JarvisTextoSuave

/** Panel con esquinas cortadas y borde cian, el "marco" visual de toda la app. */
fun Modifier.hudPanel(resaltado: Boolean = false): Modifier {
    val forma = CutCornerShape(topStart = 14.dp, bottomEnd = 14.dp)
    return this
        .background(JarvisPanel, forma)
        .border(1.dp, JarvisCian.copy(alpha = if (resaltado) 0.7f else 0.3f), forma)
}

/** Título de pantalla con subtítulo opcional. */
@Composable
fun TituloPantalla(titulo: String, subtitulo: String? = null) {
    Column(Modifier.fillMaxWidth()) {
        Text(
            text = titulo,
            color = JarvisCian,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
        )
        if (subtitulo != null) {
            Spacer(Modifier.height(6.dp))
            Text(text = subtitulo, color = JarvisTextoSuave, fontSize = 13.sp)
        }
    }
}

/**
 * Sección que todavía no está implementada. Se muestra de forma honesta como "pendiente",
 * sin controles que parezcan funcionar.
 */
@Composable
fun SeccionPendiente(titulo: String, descripcion: String, etapa: String) {
    Column(
        Modifier
            .fillMaxWidth()
            .hudPanel()
            .padding(14.dp),
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = titulo,
                color = JarvisTexto,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                text = etapa,
                color = JarvisAviso,
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace,
            )
        }
        Spacer(Modifier.height(6.dp))
        Text(text = descripcion, color = JarvisTextoSuave, fontSize = 12.sp)
    }
}
