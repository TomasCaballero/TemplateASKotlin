# Parcial TP3 - Template Android Kotlin

Este es un template completo para el examen de Android con Kotlin. Incluye todas las tecnologías y librerías necesarias según los temas del parcial.

## Temas Incluidos en el Template

✅ **Hilt** - Dependency Injection
✅ **Dagger** - Framework base de Hilt
✅ **Room** - Base de datos local (SQLite ORM)
✅ **KSP** - Kotlin Symbol Processing (para Room y Hilt)
✅ **Coil** - Carga de imágenes
✅ **Retrofit** - Networking/API REST
✅ **Firebase** - Backend as a Service (Auth, Firestore, Storage, Analytics)
✅ **Generics** - Tipos genéricos en Kotlin
✅ **ViewModels** - Gestión de estado UI
✅ **Fuentes** - Google Fonts
✅ **Componentes** - Componentes reutilizables de Compose
✅ **Navegación** - Navigation Compose

## Estructura del Proyecto

```
app/
├── src/main/
│   ├── java/com/example/parcialtp3/
│   │   ├── ParcialApp.kt                    # Application class con @HiltAndroidApp
│   │   ├── MainActivity.kt                   # Activity principal con @AndroidEntryPoint
│   │   │
│   │   ├── di/
│   │   │   └── AppModule.kt                  # Módulo de Hilt con @Module y @Provides
│   │   │
│   │   ├── data/
│   │   │   ├── local/
│   │   │   │   ├── AppDatabase.kt            # Room Database con @Database
│   │   │   │   ├── dao/
│   │   │   │   │   └── ExampleDao.kt         # DAO con @Dao, @Query, @Insert, etc.
│   │   │   │   └── entities/
│   │   │   │       └── ExampleEntity.kt      # Entity con @Entity, @PrimaryKey
│   │   │   ├── remote/
│   │   │   │   ├── api/
│   │   │   │   │   └── ApiService.kt         # Retrofit API con @GET, @POST, etc.
│   │   │   │   └── dto/
│   │   │   │       └── ExampleDto.kt         # DTOs con @SerializedName
│   │   │   └── repository/
│   │   │       └── ExampleRepositoryImpl.kt  # Implementación del Repository
│   │   │
│   │   ├── domain/
│   │   │   ├── model/
│   │   │   │   └── ExampleModel.kt           # Modelo de dominio con Generics
│   │   │   └── repository/
│   │   │       └── ExampleRepository.kt      # Interface del Repository
│   │   │
│   │   ├── presentation/
│   │   │   ├── screens/
│   │   │   │   ├── HomeScreen.kt             # Pantalla principal
│   │   │   │   ├── CreateScreen.kt           # Pantalla de creación
│   │   │   │   └── DetailScreen.kt           # Pantalla de detalle
│   │   │   ├── components/
│   │   │   │   ├── ExampleCard.kt            # Componente reutilizable
│   │   │   │   ├── LoadingIndicator.kt       # Componente de carga
│   │   │   │   └── ErrorMessage.kt           # Componente de error
│   │   │   └── viewmodels/
│   │   │       └── ExampleViewModel.kt       # ViewModel con @HiltViewModel
│   │   │
│   │   ├── navigation/
│   │   │   └── NavGraph.kt                   # Navegación con NavHost
│   │   │
│   │   └── ui/theme/
│   │       ├── Color.kt                      # Colores del tema
│   │       ├── Theme.kt                      # Configuración del tema Material 3
│   │       └── Type.kt                       # Tipografía con Google Fonts
│   │
│   ├── res/
│   │   └── values/
│   │       ├── font_certs.xml                # Certificados para Google Fonts
│   │       └── preloaded_fonts.xml           # Fuentes precargadas
│   │
│   └── AndroidManifest.xml                   # Manifest con permisos
│
├── google-services.json                      # Configuración de Firebase (EJEMPLO)
├── build.gradle.kts                          # Dependencias del módulo
└── gradle/libs.versions.toml                 # Versiones centralizadas
```

## Tecnologías y Configuración

### 1. Hilt (Dependency Injection)

**Configuración:**
- Plugin en `build.gradle.kts`: `id("com.google.dagger.hilt.android")`
- Dependencias: `hilt-android`, `hilt-compiler` (KSP)

**Anotaciones clave:**
- `@HiltAndroidApp` - En Application class
- `@AndroidEntryPoint` - En Activities/Fragments
- `@HiltViewModel` - En ViewModels
- `@Module` - Marca módulos de DI
- `@InstallIn(SingletonComponent::class)` - Alcance del módulo
- `@Provides` - Provee dependencias
- `@Singleton` - Singleton en toda la app
- `@Inject` - Inyección de constructor

**Ejemplo de uso:**
```kotlin
// En ViewModel
@HiltViewModel
class ExampleViewModel @Inject constructor(
    private val repository: ExampleRepository
) : ViewModel()

// En AppModule
@Provides
@Singleton
fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
    return Room.databaseBuilder(...)
}
```

### 2. Room (Database)

**Configuración:**
- Plugin KSP: `id("com.google.devtools.ksp")`
- Dependencias: `room-runtime`, `room-ktx`, `room-compiler` (KSP)

**Anotaciones clave:**
- `@Database` - Marca la clase Database
- `@Entity` - Marca una tabla
- `@Dao` - Marca Data Access Object
- `@PrimaryKey` - Clave primaria
- `@ColumnInfo` - Nombre de columna
- `@Query` - Consultas SQL
- `@Insert`, `@Update`, `@Delete` - Operaciones CRUD

**Ejemplo de uso:**
```kotlin
@Entity(tableName = "examples")
data class ExampleEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "title") val title: String
)

@Dao
interface ExampleDao {
    @Query("SELECT * FROM examples")
    fun getAllExamples(): Flow<List<ExampleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(example: ExampleEntity): Long
}
```

### 3. Retrofit (Networking)

**Configuración:**
- Dependencias: `retrofit`, `converter-gson`, `okhttp`, `logging-interceptor`

**Anotaciones clave:**
- `@GET`, `@POST`, `@PUT`, `@DELETE` - Métodos HTTP
- `@Path` - Parámetro en URL
- `@Query` - Query parameter
- `@Body` - Request body
- `@Header` - HTTP header

**Ejemplo de uso:**
```kotlin
interface ApiService {
    @GET("examples")
    suspend fun getExamples(): Response<List<ExampleDto>>

    @POST("examples")
    suspend fun createExample(@Body example: ExampleDto): Response<ExampleDto>
}
```

### 4. Coil (Image Loading)

**Configuración:**
- Dependencias: `coil-compose`, `coil-network-okhttp`

**Uso:**
```kotlin
AsyncImage(
    model = imageUrl,
    contentDescription = "Description",
    modifier = Modifier.size(100.dp)
)
```

### 5. Firebase

**Configuración:**
- Plugin: `id("com.google.gms.google-services")`
- Dependencias: `firebase-bom`, `firebase-auth`, `firebase-firestore`, `firebase-storage`, `firebase-analytics`
- **IMPORTANTE:** Reemplazar `app/google-services.json` con el archivo real de tu proyecto Firebase

**Servicios incluidos:**
- **Authentication** - Login/registro
- **Firestore** - Base de datos NoSQL en la nube
- **Storage** - Almacenamiento de archivos
- **Analytics** - Análisis de uso

**Ejemplo de uso:**
```kotlin
// Firestore
firestore.collection("examples")
    .document(id)
    .set(data)
    .await()

// Auth
FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
```

### 6. Navigation Compose

**Configuración:**
- Dependencia: `navigation-compose`, `hilt-navigation-compose`

**Ejemplo de uso:**
```kotlin
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail/{id}") {
        fun createRoute(id: Long) = "detail/$id"
    }
}

NavHost(navController, startDestination = Screen.Home.route) {
    composable(Screen.Home.route) { HomeScreen() }
    composable(
        route = Screen.Detail.route,
        arguments = listOf(navArgument("id") { type = NavType.LongType })
    ) { DetailScreen() }
}
```

### 7. Google Fonts

**Configuración:**
- Dependencia: `ui-text-google-fonts`
- Certificados en `res/values/font_certs.xml`

**Ejemplo de uso:**
```kotlin
val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val fontName = GoogleFont("Roboto")
val fontFamily = FontFamily(
    Font(googleFont = fontName, fontProvider = provider)
)
```

### 8. ViewModels

**Configuración:**
- Dependencia: `lifecycle-viewmodel-ktx`
- Con Hilt: `@HiltViewModel`

**Ejemplo de uso:**
```kotlin
@HiltViewModel
class ExampleViewModel @Inject constructor(
    private val repository: ExampleRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun loadData() {
        viewModelScope.launch {
            repository.getData().collect { data ->
                _uiState.update { it.copy(data = data) }
            }
        }
    }
}
```

### 9. Generics

**Ejemplo incluido:**
```kotlin
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

fun <T> Result<T>.getDataOrNull(): T? {
    return when (this) {
        is Result.Success -> data
        else -> null
    }
}
```

## Arquitectura

El proyecto sigue **Clean Architecture** con tres capas:

1. **Data Layer** (`data/`)
   - Local (Room): Entities, DAOs, Database
   - Remote (Retrofit): API Service, DTOs
   - Repository Implementation

2. **Domain Layer** (`domain/`)
   - Models: Objetos de dominio
   - Repository Interfaces

3. **Presentation Layer** (`presentation/`)
   - Screens: Composables de pantallas
   - ViewModels: Lógica de UI
   - Components: Componentes reutilizables

## Patrones Implementados

- **Repository Pattern** - Abstracción de fuentes de datos
- **MVVM** - Model-View-ViewModel
- **Dependency Injection** - Hilt/Dagger
- **State Management** - StateFlow/Flow
- **Single Source of Truth** - Room como fuente única

## Cómo Usar Este Template

### Para empezar un nuevo ejercicio:

1. **Modifica los modelos de datos:**
   - Edita `ExampleEntity.kt` según tus necesidades
   - Actualiza `ExampleModel.kt` para tu dominio
   - Modifica `ExampleDto.kt` para tus API responses

2. **Actualiza el DAO:**
   - Agrega queries específicas en `ExampleDao.kt`

3. **Configura Retrofit:**
   - Cambia la `baseUrl` en `AppModule.kt`
   - Actualiza `ApiService.kt` con tus endpoints

4. **Implementa tu lógica:**
   - Modifica `ExampleRepository` y `ExampleRepositoryImpl`
   - Actualiza `ExampleViewModel` con tu lógica de negocio

5. **Diseña tus pantallas:**
   - Edita las screens en `presentation/screens/`
   - Crea componentes reutilizables en `presentation/components/`

### Para usar Firebase:

1. Ve a [Firebase Console](https://console.firebase.google.com/)
2. Crea un nuevo proyecto
3. Agrega una app Android con el package name `com.example.parcialtp3`
4. Descarga el archivo `google-services.json`
5. **Reemplaza** el archivo actual en `app/google-services.json`

## Versiones de Librerías

```toml
kotlin = "2.1.10"
agp = "8.12.3"
compose = "2024.09.00"
hilt = "2.57.1"
room = "2.8.2"
retrofit = "2.11.0"
coil = "3.2.0"
navigation = "2.9.4"
firebase-bom = "33.7.0"
```

## Permisos Incluidos

- `INTERNET` - Para Retrofit y Firebase
- `ACCESS_NETWORK_STATE` - Estado de red
- `CAMERA` - Cámara (si se necesita)
- `READ_MEDIA_IMAGES` - Galería (Android 13+)
- `READ_EXTERNAL_STORAGE` - Lectura (Android <13)
- `WRITE_EXTERNAL_STORAGE` - Escritura (Android <13)

## Comandos Útiles

```bash
# Build del proyecto
./gradlew build

# Limpiar build
./gradlew clean

# Instalar en dispositivo
./gradlew installDebug

# Ver dependencias
./gradlew :app:dependencies
```

## Recursos Adicionales

- [Documentación Oficial de Hilt](https://dagger.dev/hilt/)
- [Documentación de Room](https://developer.android.com/training/data-storage/room)
- [Retrofit Documentation](https://square.github.io/retrofit/)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Firebase Documentation](https://firebase.google.com/docs)

## Notas Importantes

⚠️ **Firebase:** El archivo `google-services.json` incluido es de EJEMPLO. Debes reemplazarlo con tu propio archivo de Firebase.

⚠️ **API URL:** La URL base en `AppModule.kt` es de ejemplo (`https://api.example.com/`). Cámbiala por tu API real.

⚠️ **Package Name:** Si cambias el package name, actualiza también:
- `namespace` en `build.gradle.kts`
- `applicationId` en `build.gradle.kts`
- Estructura de carpetas en `src/main/java/`
- `google-services.json` (generar uno nuevo)

## Subir a GitHub

Este proyecto incluye archivos de configuración de Git:

- **`.gitignore`** - Archivo completo que excluye archivos temporales, builds, y archivos sensibles
- **`.gitattributes`** - Configuración para line endings y tipos de archivo
- **`GIT_INSTRUCTIONS.md`** - Guía detallada de cómo subir el proyecto a GitHub

### Quick Start para Git:

```bash
# Inicializar repositorio
git init

# Agregar archivos
git add .

# Primer commit
git commit -m "Initial commit: Android Template para parcial"

# Conectar con GitHub (reemplaza TU_USUARIO)
git remote add origin https://github.com/TU_USUARIO/parcial-tp3-android-template.git

# Subir
git push -u origin main
```

Ver **`GIT_INSTRUCTIONS.md`** para instrucciones detalladas.

## Estructura de Archivos del Proyecto

```
Parcial/
├── .gitignore                   # Configuración de Git
├── .gitattributes               # Atributos de Git
├── README.md                    # Este archivo
├── GIT_INSTRUCTIONS.md          # Instrucciones de Git
├── Temas.md                     # Temas del parcial
├── build.gradle.kts             # Build raíz
├── settings.gradle.kts          # Configuración de Gradle
├── gradle.properties            # Propiedades de Gradle
├── gradle/
│   ├── libs.versions.toml       # Versiones de dependencias
│   └── wrapper/                 # Gradle wrapper
└── app/
    ├── google-services.json     # Firebase (EJEMPLO)
    ├── build.gradle.kts         # Build del módulo
    ├── proguard-rules.pro       # Reglas de ProGuard
    └── src/                     # Código fuente
```

## Licencia

Template educativo para el curso de Android - ORT Uruguay

---

**Buena suerte en el parcial!** 🚀

## Recursos de Ayuda

- 📚 [README.md](README.md) - Documentación principal del template
- 🔧 [GIT_INSTRUCTIONS.md](GIT_INSTRUCTIONS.md) - Cómo usar Git y subir a GitHub
- 📝 [Temas.md](Temas.md) - Lista de temas del parcial
