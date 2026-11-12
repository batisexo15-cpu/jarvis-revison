# JARVIS Android

Asistente personal de IA para Android (FOSSiBOT F110L, Android 15).

## Estado

| Etapa | Descripción | Estado |
|---|---|---|
| 0 | Preparación del proyecto y primer APK | **Aprobada** (probada en el teléfono) |
| 1 | Interfaz futurista | **En prueba** (código entregado, pendiente de compilar y probar) |
| 2 | Conexión con la IA y conversación real | Pendiente |
| 3 | Memoria y personalización | Pendiente |
| 4 | Voz y respuestas habladas | Pendiente |
| 5 | Activación por voz "Hola, JARVIS" | Pendiente |
| 6 | Recordatorios y notificaciones | Pendiente |
| 7 | Funciones del teléfono | Pendiente |
| 8 | Optimización y corrección de errores | Pendiente |
| 9 | Servidor adicional opcional | Pendiente |

## Estructura actual (Etapa 1)

```
.
├── settings.gradle.kts, build.gradle.kts, gradle.properties, .gitignore
├── .github/workflows/build-apk.yml      # Compila el APK automáticamente
├── app/
│   ├── build.gradle.kts                 # Configuración y dependencias
│   └── src/main/
│       ├── AndroidManifest.xml
│       └── java/app/jarvis/asistente/
│           ├── MainActivity.kt          # Punto de entrada
│           └── ui/
│               ├── theme/Theme.kt       # Colores y tema
│               ├── components/          # ArcReactor (reactor animado), HudPanel (marcos y secciones)
│               ├── navigation/JarvisApp.kt   # Pestañas y barra inferior
│               ├── chat/                # ChatScreen, ChatViewModel (modo de prueba)
│               ├── personalization/     # "Personalidad y personalización" (esqueleto)
│               └── settings/            # Ajustes, SettingsViewModel (DataStore), Diagnostics
└── docs/
    ├── DECISIONES.md
    ├── PRUEBAS_ETAPA_0.md
    └── PRUEBAS_ETAPA_1.md
```

## Compilar y probar
Ver `docs/PRUEBAS_ETAPA_0.md` (subida a GitHub y compilación) y `docs/PRUEBAS_ETAPA_1.md` (lista de pruebas).
