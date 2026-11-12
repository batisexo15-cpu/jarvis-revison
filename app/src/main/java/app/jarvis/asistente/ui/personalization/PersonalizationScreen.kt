package app.jarvis.asistente.ui.personalization

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.jarvis.asistente.ui.components.SeccionPendiente
import app.jarvis.asistente.ui.components.TituloPantalla

/**
 * Esqueleto de "Personalidad y personalización". Nada aquí funciona todavía a propósito:
 * cada sección está marcada como pendiente hasta la Etapa 3.
 */
@Composable
fun PersonalizationScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        TituloPantalla(
            titulo = "PERSONALIDAD Y PERSONALIZACIÓN",
            subtitulo = "Aquí podrás ver y controlar lo que JARVIS sabe de ti. " +
                "Distinguirá lo que confirmes de lo que solo sea una suposición.",
        )
        SeccionPendiente(
            titulo = "Nombre del asistente",
            descripcion = "Hoy se llama JARVIS. Podrás cambiarlo.",
            etapa = "ETAPA 3",
        )
        SeccionPendiente(
            titulo = "Personalidad",
            descripcion = "Elige cómo se comporta y cómo se comunica contigo.",
            etapa = "ETAPA 3",
        )
        SeccionPendiente(
            titulo = "Estilo y extensión de respuestas",
            descripcion = "Respuestas breves o detalladas, formales o cercanas.",
            etapa = "ETAPA 3",
        )
        SeccionPendiente(
            titulo = "Lo que recuerda de ti",
            descripcion = "Consulta, edita o elimina cada recuerdo.",
            etapa = "ETAPA 3",
        )
        SeccionPendiente(
            titulo = "Qué puede guardar",
            descripcion = "Controla qué tipo de información se permite recordar.",
            etapa = "ETAPA 3",
        )
    }
}
