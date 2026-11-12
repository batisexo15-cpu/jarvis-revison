# Etapa 0: Guía paso a paso y pruebas

## A. Subir el proyecto a GitHub

1. En GitHub: **New repository** → nombre `jarvis-android` → **Private** → *sin* README ni .gitignore → Create.
2. En la página del repositorio vacío, pulsa **uploading an existing file**.
3. Descomprime el ZIP en tu computadora y arrastra **el contenido de la carpeta `jarvis-android`**
   (no la carpeta en sí). Verifica que se vean `app/`, `docs/`, `.github/`, `build.gradle.kts`, etc.
   - Si `.github` no aparece al arrastrar (carpeta oculta), actívala en el explorador
     (Windows: Vista → Elementos ocultos).
4. Commit directamente a la rama **main**.

## B. Primera compilación

1. Pestaña **Actions** → si pide habilitar workflows, acéptalo.
2. Abre **Compilar APK** → debería estar ejecutándose por el commit (si no: **Run workflow**).
3. Espera de 5 a 10 minutos (la primera vez descarga dependencias).
4. Si termina en verde: baja a **Artifacts** y descarga `jarvis-apk` (ZIP con el `.apk`).
5. Si termina en rojo: abre el paso que falló, copia las últimas ~40 líneas del log y envíamelas.

## C. Publicar como versión (opcional)

Crea una etiqueta `etapa-0` (Releases → Draft a new release → Tag: `etapa-0`).
El APK quedará adjunto a esa Release y se podrá descargar directo desde el teléfono.

## D. Instalar en el FOSSiBOT F110L

1. Descarga el APK en el teléfono (desde la Release, o pasa el ZIP del artifact y extráelo).
2. Al abrirlo, Android pedirá permitir "instalar apps desconocidas" para ese navegador/administrador de archivos.
3. Instala. Si Play Protect avisa de una app sin verificar, es normal en apps propias: *Instalar de todos modos*.

## E. Lista de verificación (aprobado / no aprobado)

| # | Prueba | Resultado esperado |
|---|---|---|
| 1 | Abrir la app | Pantalla oscura "JARVIS en línea", sin cierres |
| 2 | Versión mostrada | Coincide con la etiqueta (o `dev-xxxxxxx`) |
| 3 | Dispositivo / Android | FOSSiBOT F110L / 15 (API 35) |
| 4 | Servicios de Google | "Instalados" en verde |
| 5 | Reconocimiento de voz | "Disponible" en verde |
| 6 | Motores de voz (TTS) | Al menos uno listado |
| 7 | Rotar y volver a abrir | No se cierra |
| 8 | RAM tras 5 min | Ajustes → Apps → JARVIS: debe ser baja (decenas de MB) |

**Envíame:** una captura de la pantalla de diagnóstico y el resultado de cada prueba.
La Etapa 0 solo se da por terminada cuando tú confirmes que pasó.
