package app.jarvis.asistente.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.jarvis.asistente.ui.components.SeccionPendiente
import app.jarvis.asistente.ui.components.TituloPantalla
import app.jarvis.asistente.ui.components.hudPanel
import app.jarvis.asistente.ui.theme.JarvisAviso
import app.jarvis.asistente.ui.theme.JarvisCian
import app.jarvis.asistente.ui.theme.JarvisFondo
import app.jarvis.asistente.ui.theme.JarvisOk
import app.jarvis.asistente.ui.theme.JarvisPanel
import app.jarvis.asistente.ui.theme.JarvisTexto
import app.jarvis.asistente.ui.theme.JarvisTextoSuave

@Composable
fun SettingsScreen(
    reactorAnimado: Boolean,
    onReactorAnimadoCambia: (Boolean) -> Unit,
) {
    val context = LocalContext.current
    val diagnosticos = remember { recolectarDiagnosticos(context) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        TituloPantalla(titulo = "AJUSTES")

        // Único ajuste funcional de la Etapa 1
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .hudPanel()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    text = "Reactor animado",
                    color = JarvisTexto,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Desactívalo para ahorrar batería.",
                    color = JarvisTextoSuave,
                    fontSize = 12.sp,
                )
            }
            Switch(
                checked = reactorAnimado,
                onCheckedChange = onReactorAnimadoCambia,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = JarvisFondo,
                    checkedTrackColor = JarvisCian,
                    uncheckedThumbColor = JarvisTextoSuave,
                    uncheckedTrackColor = JarvisPanel,
                    uncheckedBorderColor = JarvisCian.copy(alpha = 0.4f),
                ),
            )
        }

        SeccionPendiente(
            titulo = "Clave de API",
            descripcion = "La escribirás aquí y se guardará cifrada en el teléfono con Android Keystore.",
            etapa = "ETAPA 2",
        )
        SeccionPendiente(
            titulo = "Consumo de IA",
            descripcion = "Contador informativo de mensajes y tokens enviados.",
            etapa = "ETAPA 2",
        )

        // Diagnóstico de la Etapa 0, conservado
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .hudPanel()
                .padding(14.dp),
        ) {
            Text(
                text = "DIAGNÓSTICO DEL DISPOSITIVO",
                color = JarvisCian,
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace,
            )
            Spacer(Modifier.height(8.dp))
            diagnosticos.forEach { d ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = d.etiqueta,
                        color = JarvisTextoSuave,
                        fontSize = 13.sp,
                        modifier = Modifier.weight(1f),
                    )
                    Text(
                        text = d.valor,
                        color = when (d.ok) {
                            true -> JarvisOk
                            false -> JarvisAviso
                            null -> JarvisTexto
                        },
                        fontSize = 13.sp,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}
