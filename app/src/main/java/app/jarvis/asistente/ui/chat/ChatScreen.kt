package app.jarvis.asistente.ui.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.jarvis.asistente.ui.components.ArcReactor
import app.jarvis.asistente.ui.components.ReactorEstado
import app.jarvis.asistente.ui.components.hudPanel
import app.jarvis.asistente.ui.theme.JarvisCian
import app.jarvis.asistente.ui.theme.JarvisTexto
import app.jarvis.asistente.ui.theme.JarvisTextoSuave

@Composable
fun ChatScreen(
    viewModel: ChatViewModel,
    reactorAnimado: Boolean,
) {
    val mensajes by viewModel.mensajes.collectAsStateWithLifecycle()
    val estado by viewModel.estado.collectAsStateWithLifecycle()
    var texto by rememberSaveable { mutableStateOf("") }
    val listState = rememberLazyListState()

    // Al llegar un mensaje nuevo, baja hasta el final
    LaunchedEffect(mensajes.size) {
        listState.animateScrollToItem(mensajes.lastIndex)
    }

    val enviar = {
        if (texto.isNotBlank()) {
            viewModel.enviar(texto)
            texto = ""
        }
    }

    // imePadding: sube todo el contenido por encima del teclado
    Column(Modifier.fillMaxSize().imePadding()) {
        CabeceraChat(estado = estado, reactorAnimado = reactorAnimado)

        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(mensajes, key = { it.id }) { mensaje ->
                Burbuja(mensaje)
            }
        }

        BarraEntrada(
            texto = texto,
            onTextoCambia = { texto = it },
            onEnviar = enviar,
        )
    }
}

@Composable
private fun CabeceraChat(estado: ReactorEstado, reactorAnimado: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ArcReactor(estado = estado, animado = reactorAnimado, tamano = 72.dp)
        Spacer(Modifier.width(16.dp))
        Column {
            Text(
                text = "JARVIS",
                color = JarvisCian,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
            )
            Text(
                text = when (estado) {
                    ReactorEstado.REPOSO -> "EN LÍNEA · MODO DE PRUEBA"
                    ReactorEstado.PENSANDO -> "PROCESANDO…"
                    ReactorEstado.HABLANDO -> "RESPONDIENDO…"
                },
                color = JarvisTextoSuave,
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace,
            )
        }
    }
}

@Composable
private fun Burbuja(mensaje: MensajeChat) {
    val esUsuario = mensaje.autor == Autor.USUARIO
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = if (esUsuario) Alignment.CenterEnd else Alignment.CenterStart,
    ) {
        Text(
            text = mensaje.texto,
            color = JarvisTexto,
            fontSize = 15.sp,
            modifier = Modifier
                .widthIn(max = 320.dp)
                .hudPanel(resaltado = esUsuario)
                .padding(12.dp),
        )
    }
}

@Composable
private fun BarraEntrada(
    texto: String,
    onTextoCambia: (String) -> Unit,
    onEnviar: () -> Unit,
) {
    val puedeEnviar = texto.isNotBlank()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OutlinedTextField(
            value = texto,
            onValueChange = onTextoCambia,
            modifier = Modifier.weight(1f),
            placeholder = { Text("Escribe a JARVIS…") },
            maxLines = 4,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions(onSend = { onEnviar() }),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = JarvisTexto,
                unfocusedTextColor = JarvisTexto,
                cursorColor = JarvisCian,
                focusedBorderColor = JarvisCian,
                unfocusedBorderColor = JarvisCian.copy(alpha = 0.35f),
                focusedPlaceholderColor = JarvisTextoSuave,
                unfocusedPlaceholderColor = JarvisTextoSuave,
            ),
        )
        // Micrófono: se muestra apagado a propósito. Se activa en la Etapa 4.
        Text(
            text = "MIC",
            color = JarvisTextoSuave,
            fontSize = 12.sp,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier
                .alpha(0.4f)
                .padding(horizontal = 8.dp),
        )
        Text(
            text = "ENVIAR",
            color = if (puedeEnviar) JarvisCian else JarvisTextoSuave,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier
                .clickable(enabled = puedeEnviar, role = Role.Button, onClick = onEnviar)
                .padding(horizontal = 8.dp, vertical = 12.dp),
        )
    }
}
