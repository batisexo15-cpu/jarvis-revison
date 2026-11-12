package app.jarvis.asistente.ui.settings

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException

// Un único DataStore para los ajustes simples de la app.
private val Context.ajustesDataStore by preferencesDataStore(name = "ajustes")

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val claveReactor = booleanPreferencesKey("reactor_animado")

    /** Por defecto el reactor está animado. */
    val reactorAnimado: StateFlow<Boolean> = application.ajustesDataStore.data
        .catch { error ->
            if (error is IOException) emit(emptyPreferences()) else throw error
        }
        .map { it[claveReactor] ?: true }
        .stateIn(viewModelScope, SharingStarted.Eagerly, true)

    fun setReactorAnimado(valor: Boolean) {
        viewModelScope.launch {
            getApplication<Application>().ajustesDataStore.edit { it[claveReactor] = valor }
        }
    }
}
