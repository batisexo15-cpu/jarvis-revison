package app.jarvis.asistente.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.jarvis.asistente.ui.chat.ChatScreen
import app.jarvis.asistente.ui.chat.ChatViewModel
import app.jarvis.asistente.ui.personalization.PersonalizationScreen
import app.jarvis.asistente.ui.settings.SettingsScreen
import app.jarvis.asistente.ui.settings.SettingsViewModel
import app.jarvis.asistente.ui.theme.JarvisCian
import app.jarvis.asistente.ui.theme.JarvisFondo
import app.jarvis.asistente.ui.theme.JarvisPanel
import app.jarvis.asistente.ui.theme.JarvisTextoSuave

private enum class Destino(val ruta: String, val etiqueta: String) {
    CHAT("chat", "CHAT"),
    PERSONALIDAD("personalidad", "PERSONALIDAD"),
    AJUSTES("ajustes", "AJUSTES"),
}

@Composable
fun JarvisApp() {
    // Ambos ViewModels se crean a nivel de la actividad: así el chat y los ajustes
    // se conservan al cambiar de pestaña y al rotar la pantalla.
    val chatViewModel: ChatViewModel = viewModel()
    val settingsViewModel: SettingsViewModel = viewModel()

    val navController = rememberNavController()
    val reactorAnimado by settingsViewModel.reactorAnimado.collectAsStateWithLifecycle()
    val rutaActual = navController.currentBackStackEntryAsState().value?.destination?.route

    // Con el teclado abierto se oculta la barra inferior para dejar espacio al chat.
    val tecladoVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0

    Column(
        Modifier
            .fillMaxSize()
            .background(JarvisFondo)
            .statusBarsPadding(),
    ) {
        NavHost(
            navController = navController,
            startDestination = Destino.CHAT.ruta,
            modifier = Modifier.weight(1f),
        ) {
            composable(Destino.CHAT.ruta) {
                ChatScreen(viewModel = chatViewModel, reactorAnimado = reactorAnimado)
            }
            composable(Destino.PERSONALIDAD.ruta) {
                PersonalizationScreen()
            }
            composable(Destino.AJUSTES.ruta) {
                SettingsScreen(
                    reactorAnimado = reactorAnimado,
                    onReactorAnimadoCambia = settingsViewModel::setReactorAnimado,
                )
            }
        }

        if (!tecladoVisible) {
            BarraInferior(rutaActual = rutaActual) { destino ->
                navController.navigate(destino.ruta) {
                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    }
}

@Composable
private fun BarraInferior(rutaActual: String?, onSeleccion: (Destino) -> Unit) {
    Column(Modifier.fillMaxWidth()) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(JarvisCian.copy(alpha = 0.3f)),
        )
        Row(
            Modifier
                .fillMaxWidth()
                .background(JarvisPanel)
                .navigationBarsPadding(),
        ) {
            Destino.entries.forEach { destino ->
                val seleccionado = rutaActual == destino.ruta
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(role = Role.Tab, onClick = { onSeleccion(destino) })
                        .padding(vertical = 14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Box(
                        Modifier
                            .width(32.dp)
                            .height(2.dp)
                            .background(if (seleccionado) JarvisCian else Color.Transparent),
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = destino.etiqueta,
                        color = if (seleccionado) JarvisCian else JarvisTextoSuave,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Normal,
                    )
                }
            }
        }
    }
}
