# Etapa 1: Pruebas en el FOSSiBOT F110L

## Instalar la nueva versión
1. Compila como en la Etapa 0 (subir los archivos nuevos al repositorio → pestaña Actions → descargar el APK).
2. Instala sobre la anterior. **Si Android dice que la app entra en conflicto o no se pudo instalar**,
   desinstala JARVIS primero (no hay datos que perder todavía) y vuelve a instalar.
3. Al abrir, la versión (Ajustes → Diagnóstico) debe decir `0.1.0-etapa1` si compilaste sin etiqueta
   con el flujo original, o el nombre de tu etiqueta.

## Lista de verificación

| # | Prueba | Resultado esperado |
|---|---|---|
| 1 | Abrir la app | Pestaña Chat, reactor girando, mensaje de bienvenida de JARVIS |
| 2 | Escribir un mensaje y pulsar ENVIAR (o el botón de enviar del teclado) | Aparece tu burbuja; el reactor acelera ~1 s; JARVIS responde con "[Modo de prueba]…" |
| 3 | Mandar 8–10 mensajes seguidos | La lista baja sola y se desplaza con fluidez, sin tirones |
| 4 | Abrir el teclado | La barra inferior se oculta, la caja de texto queda visible sobre el teclado |
| 5 | Cambiar a Personalidad y a Ajustes y volver a Chat | Los mensajes siguen ahí |
| 6 | Personalidad | 5 secciones, todas marcadas "ETAPA 3", ninguna con controles falsos |
| 7 | Ajustes → apagar "Reactor animado" | El reactor queda quieto en el Chat |
| 8 | Cerrar la app del todo y abrirla | El interruptor mantiene su estado |
| 9 | Ajustes → Diagnóstico | Misma información de la Etapa 0 |
| 10 | Rotar el teléfono | No se cierra; el chat se conserva |
| 11 | Enviar la app a segundo plano 10 s y volver | El reactor reanuda la animación |
| 12 | Barras del sistema (hora, batería, navegación) | Iconos visibles (claros) sobre el fondo oscuro |

## Qué enviarme
- Resultado de cada prueba (aprobada / no aprobada).
- Captura del Chat y de Ajustes.
- Si algo falla: qué hiciste, qué pasó y una captura.
- Si falla la compilación: las últimas ~40 líneas del log de Actions.
