package app.jarvis.asistente.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.currentStateAsState
import app.jarvis.asistente.ui.theme.JarvisCian
import kotlin.math.sin

/** Estado visual del reactor. HABLANDO se usará desde la Etapa 4 (respuestas habladas). */
enum class ReactorEstado { REPOSO, PENSANDO, HABLANDO }

/**
 * Reactor animado de la cabecera.
 *
 * Ahorro de batería: la animación solo corre si [animado] es true Y la app está en primer plano
 * (estado RESUMED). En cualquier otro caso se dibuja estático y no consume fotogramas.
 */
@Composable
fun ArcReactor(
    estado: ReactorEstado,
    animado: Boolean,
    modifier: Modifier = Modifier,
    tamano: Dp = 96.dp,
) {
    val cicloDeVida by LocalLifecycleOwner.current.lifecycle.currentStateAsState()
    val activo = animado && cicloDeVida.isAtLeast(Lifecycle.State.RESUMED)
    val estadoActual by rememberUpdatedState(estado)

    var angulo by remember { mutableFloatStateOf(0f) }
    var fase by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(activo) {
        if (activo) {
            var anterior = withFrameNanos { it }
            while (true) {
                val ahora = withFrameNanos { it }
                val dt = (ahora - anterior) / 1_000_000_000f
                anterior = ahora
                val vueltasPorSegundo = when (estadoActual) {
                    ReactorEstado.REPOSO -> 0.08f
                    ReactorEstado.PENSANDO -> 0.5f
                    ReactorEstado.HABLANDO -> 0.25f
                }
                angulo = (angulo + dt * 360f * vueltasPorSegundo) % 360f
                fase = (fase + dt) % 1000f
            }
        }
    }

    Canvas(modifier.size(tamano)) {
        val c = center
        val r = size.minDimension / 2f
        val trazoFino = 1.5.dp.toPx()
        val trazo = 3.dp.toPx()

        // Anillo exterior fijo
        drawCircle(
            color = JarvisCian.copy(alpha = 0.25f),
            radius = r * 0.95f,
            center = c,
            style = Stroke(width = trazoFino),
        )

        // Tres arcos que giran en un sentido
        val rExterior = r * 0.78f
        for (i in 0 until 3) {
            drawArc(
                color = JarvisCian.copy(alpha = 0.85f),
                startAngle = angulo + i * 120f,
                sweepAngle = 70f,
                useCenter = false,
                topLeft = Offset(c.x - rExterior, c.y - rExterior),
                size = Size(rExterior * 2f, rExterior * 2f),
                style = Stroke(width = trazo, cap = StrokeCap.Round),
            )
        }

        // Dos arcos interiores que giran al revés
        val rInterior = r * 0.55f
        for (i in 0 until 2) {
            drawArc(
                color = JarvisCian.copy(alpha = 0.6f),
                startAngle = -angulo * 1.5f + i * 180f,
                sweepAngle = 100f,
                useCenter = false,
                topLeft = Offset(c.x - rInterior, c.y - rInterior),
                size = Size(rInterior * 2f, rInterior * 2f),
                style = Stroke(width = trazoFino * 1.5f, cap = StrokeCap.Round),
            )
        }

        // Núcleo con pulso
        val velocidadPulso = when (estadoActual) {
            ReactorEstado.REPOSO -> 1.5f
            ReactorEstado.PENSANDO -> 6f
            ReactorEstado.HABLANDO -> 9f
        }
        val nucleo = r * 0.2f * (1f + 0.1f * sin(fase * velocidadPulso))
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    JarvisCian.copy(alpha = 0.9f),
                    JarvisCian.copy(alpha = 0.35f),
                    Color.Transparent,
                ),
                center = c,
                radius = nucleo * 2.6f,
            ),
            radius = nucleo * 2.6f,
            center = c,
        )
        drawCircle(color = Color.White.copy(alpha = 0.9f), radius = nucleo * 0.6f, center = c)
    }
}
