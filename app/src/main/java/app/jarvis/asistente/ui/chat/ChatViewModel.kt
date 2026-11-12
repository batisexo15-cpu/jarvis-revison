package app.jarvis.asistente.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.jarvis.asistente.ui.components.ReactorEstado
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class Autor { USUARIO, JARVIS }

data class MensajeChat(val id: Long, val autor: Autor, val texto: String)

/**
 * Etapa 1: el chat es solo de prueba. La respuesta de JARVIS es un texto fijo y claramente
 * rotulado; no hay inteligencia ni red. La IA real se conecta en la Etapa 2.
 * Los mensajes viven solo en memoria (se pierden al cerrar la app). La persistencia llega en la Etapa 3.
 */
class ChatViewModel : ViewModel() {

    private var siguienteId = 1L
    private var pendientes = 0

    private val _mensajes = MutableStateFlow(
        listOf(MensajeChat(0L, Autor.JARVIS, BIENVENIDA)),
    )
    val mensajes: StateFlow<List<MensajeChat>> = _mensajes.asStateFlow()

    private val _estado = MutableStateFlow(ReactorEstado.REPOSO)
    val estado: StateFlow<ReactorEstado> = _estado.asStateFlow()

    fun enviar(texto: String) {
        val limpio = texto.trim()
        if (limpio.isEmpty()) return

        agregar(Autor.USUARIO, limpio)
        pendientes++
        _estado.value = ReactorEstado.PENSANDO

        viewModelScope.launch {
            delay(700) // Simula "pensar" para ver el reactor en ese estado
            agregar(Autor.JARVIS, RESPUESTA_DE_PRUEBA)
            pendientes--
            if (pendientes == 0) _estado.value = ReactorEstado.REPOSO
        }
    }

    private fun agregar(autor: Autor, texto: String) {
        _mensajes.update { it + MensajeChat(siguienteId++, autor, texto) }
    }

    private companion object {
        const val BIENVENIDA =
            "JARVIS en línea. Modo de prueba: la inteligencia artificial aún no está conectada (Etapa 2)."
        const val RESPUESTA_DE_PRUEBA =
            "[Modo de prueba] Recibí tu mensaje, pero todavía no estoy conectado a la IA. Eso llega en la Etapa 2."
    }
}
