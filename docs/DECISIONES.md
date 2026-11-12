# Decisiones técnicas

Registro de decisiones del proyecto. Si una decisión cambia, se agrega una entrada nueva explicando el motivo.

## D-001 · Plataforma y lenguaje
Kotlin + Jetpack Compose, app nativa Android. minSdk 26 (Android 8), targetSdk 35 (Android 15).
Motivo: el teléfono usa Android 15; minSdk 26 evita código de compatibilidad antiguo.

## D-002 · Arquitectura aprobada
Room (sobre SQLite), Hilt, Ktor/Retrofit con Kotlinx Serialization, DataStore, GitHub Actions.
Se incorporan **cada una en la etapa que la necesita**, no todas al inicio.
Estado: DataStore (Etapa 1). Pendientes: Ktor/Retrofit (Etapa 2), Room (Etapa 3), Hilt (cuando haya capa de datos).

## D-003 · Proveedor de IA inicial
Google AI Studio (API de Gemini), detrás de una interfaz abstracta `LlmProvider`
para poder añadir otros proveedores (OpenAI, Claude/Anthropic) sin reescribir la app.
Se implementa en la Etapa 2.

## D-004 · Clave de API (Opción A)
El usuario escribe su propia clave en Ajustes. Se guarda cifrada con Android Keystore.
Nunca va dentro del código ni del repositorio. Se implementa en la Etapa 2.

## D-005 · Control de gasto
Sin límites ni alertas por dinero. Solo un contador informativo de mensajes/tokens en Ajustes (Etapa 2).

## D-006 · Compilación
GitHub Actions compila el APK. No se usa `gradlew` (el wrapper no puede generarse sin ejecutar Gradle);
el flujo instala Gradle 8.9 directamente. Si luego se abre el proyecto en Android Studio, este generará el wrapper.

## D-007 · Firma del APK
Sin secrets configurados: firma de depuración (válida para pruebas).
Con secrets: firma con clave propia, necesaria para actualizar sin desinstalar.
Importante: si se cambia de firma, Android exige desinstalar la app anterior.

## D-008 · Identificador de la app
`app.jarvis.asistente`. No debe cambiarse después de instalar, o Android lo tratará como otra app.

## D-009 · Estilo visual (Etapa 1)
Cian sobre fondo casi negro, tipografía monoespaciada para títulos, paneles con esquinas cortadas.
Todos los colores salen de `ui/theme/Theme.kt`.

## D-010 · Navegación (Etapa 1)
Tres pestañas: Chat, Personalidad, Ajustes. `navigation-compose` con rutas de texto.
Barra inferior propia (sin iconos): evita la librería de iconos extendidos, que agrega varios MB a la app.
Se oculta con el teclado abierto para dar espacio al chat.

## D-011 · Reactor animado (Etapa 1)
Dibujado con Canvas, una sola animación. Solo corre si el interruptor de Ajustes está activo
y la app está en primer plano. El interruptor se guarda con DataStore (por defecto: activado).

## D-012 · Honestidad de la interfaz (Etapa 1)
Nada que no funcione debe parecer que funciona. Las secciones pendientes se marcan con su etapa
y el chat responde con un texto fijo rotulado "[Modo de prueba]" hasta conectar la IA (Etapa 2).

## D-013 · Alcance de los ViewModels (Etapa 1)
`ChatViewModel` y `SettingsViewModel` se crean a nivel de la actividad para que el chat
no se pierda al cambiar de pestaña ni al rotar. Los mensajes siguen siendo solo en memoria hasta la Etapa 3.
