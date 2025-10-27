# Instrucciones para Subir el Proyecto a GitHub

## Configuración Inicial de Git (solo la primera vez)

Si no has configurado Git antes, ejecuta estos comandos:

```bash
git config --global user.name "Tu Nombre"
git config --global user.email "tu@email.com"
```

## Opción 1: Crear un Nuevo Repositorio en GitHub

### 1. Crear el repositorio en GitHub
1. Ve a [GitHub](https://github.com) y haz login
2. Haz clic en el botón **"+"** (arriba a la derecha) y selecciona **"New repository"**
3. Nombre del repositorio: `parcial-tp3-android-template` (o el que prefieras)
4. Descripción: `Template para el parcial de Android con Kotlin - ORT`
5. Elige **Public** o **Private** según prefieras
6. **NO marques** "Initialize this repository with a README" (ya tenemos uno)
7. Haz clic en **"Create repository"**

### 2. Conectar tu proyecto local con GitHub

Desde la carpeta del proyecto (`/Parcial`), ejecuta:

```bash
# Inicializar Git (si no está inicializado)
git init

# Agregar todos los archivos
git add .

# Hacer el primer commit
git commit -m "Initial commit: Android Template con todas las tecnologías del parcial"

# Conectar con el repositorio remoto (reemplaza TU_USUARIO con tu usuario de GitHub)
git remote add origin https://github.com/TU_USUARIO/parcial-tp3-android-template.git

# Cambiar el nombre de la rama a main (opcional, si estás en master)
git branch -M main

# Subir los cambios
git push -u origin main
```

## Opción 2: Clonar un Repositorio Existente

Si ya tienes el repositorio creado en GitHub:

```bash
# Clonar el repositorio
git clone https://github.com/TU_USUARIO/parcial-tp3-android-template.git

# Entrar a la carpeta
cd parcial-tp3-android-template

# Copiar los archivos del proyecto aquí
# Luego hacer commit y push
git add .
git commit -m "Agregar template de Android"
git push
```

## Comandos Git Útiles

### Ver el estado de los archivos
```bash
git status
```

### Agregar cambios
```bash
# Agregar todos los archivos modificados
git add .

# Agregar un archivo específico
git add ruta/al/archivo.kt
```

### Hacer commit
```bash
# Commit con mensaje descriptivo
git commit -m "Descripción de los cambios"
```

### Subir cambios a GitHub
```bash
# Subir al repositorio remoto
git push

# Primera vez o especificar rama
git push -u origin main
```

### Ver el historial de commits
```bash
git log

# Versión más compacta
git log --oneline
```

### Ver diferencias
```bash
# Ver cambios no commiteados
git diff

# Ver diferencias de un archivo específico
git diff nombre_archivo.kt
```

### Deshacer cambios

```bash
# Descartar cambios en un archivo (¡cuidado! se pierden los cambios)
git checkout -- nombre_archivo.kt

# Quitar archivo del staging area
git reset HEAD nombre_archivo.kt

# Volver al último commit (¡cuidado! se pierden los cambios)
git reset --hard HEAD
```

### Crear una rama (branch)
```bash
# Crear y cambiar a una nueva rama
git checkout -b nombre-de-la-rama

# Ver todas las ramas
git branch

# Cambiar de rama
git checkout nombre-de-la-rama

# Subir la rama a GitHub
git push -u origin nombre-de-la-rama
```

### Actualizar desde GitHub
```bash
# Obtener últimos cambios
git pull
```

## Workflow Recomendado para el Examen

### Durante el desarrollo:

```bash
# 1. Ver qué archivos cambiaron
git status

# 2. Agregar los cambios
git add .

# 3. Hacer commit con mensaje descriptivo
git commit -m "feat: Implementar listado de productos con Room y Retrofit"

# 4. Subir a GitHub (backup)
git push
```

### Mensajes de commit recomendados:

- `feat: Descripción` - Nueva funcionalidad
- `fix: Descripción` - Corrección de errores
- `refactor: Descripción` - Refactorización de código
- `style: Descripción` - Cambios de estilo/formato
- `docs: Descripción` - Documentación
- `test: Descripción` - Tests

**Ejemplos:**
```bash
git commit -m "feat: Agregar Entity y DAO para productos"
git commit -m "feat: Implementar navegación entre pantallas"
git commit -m "fix: Corregir error en la consulta de Room"
git commit -m "feat: Integrar Firebase Authentication"
git commit -m "refactor: Mejorar estructura del ViewModel"
```

## Ignorar Archivos Adicionales

Si necesitas ignorar más archivos, edita el archivo `.gitignore` y agrega las rutas o patrones:

```bash
# Ejemplo: ignorar un directorio específico
mi_carpeta_temporal/

# Ejemplo: ignorar archivos con extensión específica
*.backup

# Ejemplo: ignorar archivo específico
config_secreto.json
```

Luego:
```bash
git add .gitignore
git commit -m "docs: Actualizar .gitignore"
git push
```

## ⚠️ IMPORTANTE: Firebase

El archivo `app/google-services.json` incluido es de **EJEMPLO**.

### Si usas Firebase REAL en el examen:

1. **Descarga tu `google-services.json` real** desde Firebase Console
2. **Reemplaza** el archivo en `app/google-services.json`
3. **Edita `.gitignore`** y descomenta la línea:
   ```
   # app/google-services.json  ← Quitar el # para que se ignore
   ```
4. **NO subas tu archivo real de Firebase a GitHub** (puede tener información sensible)

### Para verificar que no está en Git:
```bash
# Ver si el archivo está siendo ignorado
git status

# Si aparece como modificado, agregarlo al .gitignore
# Si no aparece, está correctamente ignorado
```

## Verificar que Todo Está Correcto

Antes de subir, verifica:

```bash
# Ver qué archivos se van a subir
git status

# Ver si hay archivos que no deberían subirse
git ls-files

# Ver el contenido del último commit
git show
```

## Colaboración (si trabajas en equipo)

```bash
# Obtener cambios de otros colaboradores
git pull

# Resolver conflictos si los hay
# (editar archivos manualmente y luego)
git add archivo_con_conflicto.kt
git commit -m "merge: Resolver conflictos"
git push
```

## Solución de Problemas Comunes

### Error: "remote origin already exists"
```bash
git remote remove origin
git remote add origin https://github.com/TU_USUARIO/tu-repo.git
```

### Error: "Updates were rejected"
```bash
# Obtener cambios primero
git pull origin main --rebase

# Luego subir
git push
```

### Olvidé hacer un commit antes de hacer cambios
```bash
# Guardar cambios temporalmente
git stash

# Hacer lo que necesites hacer

# Recuperar los cambios
git stash pop
```

## Ver tu Repositorio en GitHub

Una vez subido, tu repositorio estará en:
```
https://github.com/TU_USUARIO/parcial-tp3-android-template
```

## Clonar el Repositorio en Otra Computadora

Si necesitas trabajar en otra computadora:

```bash
git clone https://github.com/TU_USUARIO/parcial-tp3-android-template.git
cd parcial-tp3-android-template
```

Luego abre el proyecto en Android Studio.

---

## 🎯 Quick Reference (Referencia Rápida)

```bash
# Configuración inicial
git init
git remote add origin https://github.com/TU_USUARIO/repo.git

# Workflow normal
git status              # Ver cambios
git add .               # Agregar todo
git commit -m "mensaje" # Guardar cambios
git push                # Subir a GitHub

# Actualizar
git pull                # Obtener cambios de GitHub

# Ver historial
git log --oneline       # Ver commits
```

---

**¡Listo!** Con esto puedes subir y gestionar tu proyecto en GitHub. 🚀
