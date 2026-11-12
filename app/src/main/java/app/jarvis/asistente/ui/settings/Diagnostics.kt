package app.jarvis.asistente.ui.settings

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.speech.RecognitionService
import android.speech.SpeechRecognizer
import app.jarvis.asistente.BuildConfig

/** Una línea del panel de diagnóstico: etiqueta, valor y si la comprobación pasó (null = informativo). */
data class Diagnostico(val etiqueta: String, val valor: String, val ok: Boolean? = null)

/** Datos del dispositivo (movido desde la Etapa 0 sin cambios de lógica). */
fun recolectarDiagnosticos(context: Context): List<Diagnostico> {
    val pm = context.packageManager

    val gms = try {
        pm.getPackageInfo("com.google.android.gms", 0)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }

    val reconocimientoDisponible = SpeechRecognizer.isRecognitionAvailable(context)
    val servicios = pm.queryIntentServices(Intent(RecognitionService.SERVICE_INTERFACE), 0)
    val motoresTts = pm.queryIntentServices(Intent("android.intent.action.TTS_SERVICE"), 0)

    val mem = ActivityManager.MemoryInfo()
    (context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).getMemoryInfo(mem)
    val ramTotalMb = mem.totalMem / (1024 * 1024)
    val ramLibreMb = mem.availMem / (1024 * 1024)

    return listOf(
        Diagnostico("Versión de la app", BuildConfig.VERSION_NAME + " (build " + BuildConfig.VERSION_CODE + ")"),
        Diagnostico("Dispositivo", "${Build.MANUFACTURER} ${Build.MODEL}"),
        Diagnostico("Android", "${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})"),
        Diagnostico("RAM total / libre", "$ramTotalMb MB / $ramLibreMb MB"),
        Diagnostico("Servicios de Google (GMS)", if (gms) "Instalados" else "No encontrados", gms),
        Diagnostico(
            "Reconocimiento de voz",
            if (reconocimientoDisponible) "Disponible" else "No disponible",
            reconocimientoDisponible,
        ),
        Diagnostico(
            "Servicios de reconocimiento",
            servicios.joinToString { it.serviceInfo.packageName }.ifEmpty { "Ninguno" },
        ),
        Diagnostico(
            "Motores de voz (TTS)",
            motoresTts.joinToString { it.serviceInfo.packageName }.ifEmpty { "Ninguno" },
            motoresTts.isNotEmpty(),
        ),
    )
}
