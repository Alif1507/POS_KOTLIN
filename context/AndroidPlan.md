# Android Project Plan: KasirKeren POS
**Jetpack Compose · Kotlin · Material 3**
*Base API: `https://kasirbackend.maw07.web.id/`*

---

## 1. Project Overview

Build a native Android cashier (POS) app that mirrors the KasirKeren web frontend design and behavior. The app is optimized for fast item selection and checkout, daily revenue monitoring, inventory CRUD with CSV bulk import, and transaction history review. It consumes the KasirKeren FastAPI backend and follows the **"Digital Concierge"** design philosophy — clarity, high-end editorial aesthetics, and reduced cognitive load.

---

## 2. Tech Stack

| Layer | Choice | Reason |
|---|---|---|
| UI | Jetpack Compose + Material 3 | Modern declarative UI, great animation support |
| Language | Kotlin | Official Android language |
| Navigation | Navigation Compose | Type-safe nav graph, matches web route structure |
| HTTP | Retrofit 2 + OkHttp | Mature, annotation-driven, easy interceptors |
| JSON | Gson + Retrofit converter | Deserialize API snake_case responses |
| State | ViewModel + StateFlow | Lifecycle-aware reactive state |
| DI | Hilt | Compile-time DI, minimal boilerplate |
| Image loading | Coil | Compose-native, async, placeholder support |
| Charts | Vico (`compose-m3`) | Indigo-themed bar chart for 7-day revenue |
| Payment WebView | `AndroidView` + `WebViewClient` | Midtrans Snap redirect flow |
| CSV import | System file picker + Multipart upload | Delegates parsing to backend |
| Font | Manrope (bundled in `res/font/`) | Exact match to web frontend |
| Session | `EncryptedSharedPreferences` | Persists auth session across launches |
| Toast/Snackbar | `SnackbarHost` + `LaunchedEffect` | Mirrors web `react-hot-toast` feedback |
| Min SDK | 26 (Android 8) | Covers ~95% of active devices |
| Target SDK | 35 | Latest stable |

---

## 3. Folder Structure

```
app/
└── src/main/
    ├── java/com/kasirkeren/app/
    │   ├── auth/
    │   │   ├── AuthSession.kt             # EncryptedSharedPrefs session store
    │   │   └── AuthViewModel.kt           # Login logic + session state
    │   ├── data/
    │   │   ├── api/
    │   │   │   ├── ApiService.kt          # All Retrofit endpoints
    │   │   │   └── RetrofitClient.kt      # OkHttp + Retrofit setup
    │   │   ├── model/
    │   │   │   ├── Item.kt
    │   │   │   ├── Transaction.kt
    │   │   │   ├── Dashboard.kt
    │   │   │   └── Payment.kt
    │   │   └── repository/
    │   │       ├── ItemRepository.kt
    │   │       ├── TransactionRepository.kt
    │   │       ├── DashboardRepository.kt
    │   │       └── PaymentRepository.kt
    │   ├── di/
    │   │   └── AppModule.kt               # Hilt module
    │   ├── ui/
    │   │   ├── theme/
    │   │   │   ├── Color.kt               # Indigo palette + semantic colors
    │   │   │   ├── Type.kt                # Manrope typography scale
    │   │   │   ├── Shape.kt               # Rounded corner tokens
    │   │   │   └── Theme.kt               # MaterialTheme wrapper
    │   │   ├── components/
    │   │   │   ├── AppShell.kt            # NavHost + BottomBar + TopBar wrapper
    │   │   │   ├── BottomNavBar.kt        # Frosted tab bar (mobile)
    │   │   │   ├── KasirTopBar.kt         # Sticky top app bar
    │   │   │   ├── ItemCard.kt            # Product card with stock badge
    │   │   │   ├── StatusBadge.kt         # Success / Pending / Failed pill
    │   │   │   ├── CartBottomSheet.kt     # Mobile cart sheet modal
    │   │   │   ├── PopupModal.kt          # Unified modal (full + sheet modes)
    │   │   │   ├── SkeletonLoader.kt      # Loading placeholder shimmer
    │   │   │   └── EmptyState.kt          # Illustration + copy for empty lists
    │   │   ├── login/
    │   │   │   └── LoginScreen.kt
    │   │   ├── dashboard/
    │   │   │   ├── DashboardScreen.kt
    │   │   │   └── DashboardViewModel.kt
    │   │   ├── pos/
    │   │   │   ├── PosScreen.kt
    │   │   │   ├── PosViewModel.kt        # Holds shared cart state
    │   │   │   └── CartScreen.kt
    │   │   ├── items/
    │   │   │   ├── ItemListScreen.kt
    │   │   │   ├── ItemFormModal.kt       # Add / Edit modal sheet
    │   │   │   ├── CsvImportResult.kt     # Import summary bottom sheet
    │   │   │   └── ItemViewModel.kt
    │   │   ├── history/
    │   │   │   ├── HistoryScreen.kt
    │   │   │   ├── HistoryDetailModal.kt  # Transaction detail popup
    │   │   │   └── HistoryViewModel.kt
    │   │   └── payment/
    │   │       ├── PaymentMethodSheet.kt  # Tunai / Midtrans chooser
    │   │       ├── MidtransWebView.kt     # WebView screen for Snap
    │   │       └── PaymentViewModel.kt
    │   └── MainActivity.kt
    └── res/
        ├── font/
        │   ├── manrope_regular.ttf
        │   ├── manrope_medium.ttf
        │   ├── manrope_semibold.ttf
        │   ├── manrope_bold.ttf
        │   └── manrope_extrabold.ttf
        └── values/
            └── strings.xml
```

---

## 4. Design System Implementation

### 4.1 Color Palette (`Color.kt`)

Mirrors the web frontend's CSS custom properties exactly.

```kotlin
// Primary
val IndigoDeep       = Color(0xFF2E44A7)  // --color-primary
val IndigoMedium     = Color(0xFF3D55C5)  // hover / gradient end
val IndigoLight      = Color(0xFFEEF1FB)  // active pill background

// Surfaces
val SlateBg          = Color(0xFFF8FAFC)  // bg-slate-50
val SurfaceWhite     = Color(0xFFFFFFFF)  // bg-white cards

// Semantic
val GreenSuccess     = Color(0xFF10B981)  // success / in stock
val GreenSuccessBg   = Color(0xFFD1FAE5)  // success badge background
val AmberWarning     = Color(0xFFF59E0B)  // pending / low stock
val AmberWarningBg   = Color(0xFFFEF3C7)  // amber badge background
val RedError         = Color(0xFFEF4444)  // error / out of stock
val RedErrorBg       = Color(0xFFFEE2E2)  // red badge background

// Text
val TextPrimary      = Color(0xFF0F172A)  // slate-900
val TextSecondary    = Color(0xFF64748B)  // slate-500
val TextMuted        = Color(0xFF94A3B8)  // slate-400
```

### 4.2 Typography (`Type.kt`)

```kotlin
val ManropeFamily = FontFamily(
    Font(R.font.manrope_regular,   FontWeight.Normal),
    Font(R.font.manrope_medium,    FontWeight.Medium),
    Font(R.font.manrope_semibold,  FontWeight.SemiBold),
    Font(R.font.manrope_bold,      FontWeight.Bold),
    Font(R.font.manrope_extrabold, FontWeight.ExtraBold),
)

val AppTypography = Typography(
    // Page titles — "History", "Dashboard" (text-4xl, tracking-tight)
    headlineLarge = TextStyle(
        fontFamily = ManropeFamily, fontWeight = FontWeight.ExtraBold,
        fontSize = 34.sp, letterSpacing = (-0.5).sp
    ),
    // Section headers
    headlineMedium = TextStyle(
        fontFamily = ManropeFamily, fontWeight = FontWeight.Bold,
        fontSize = 24.sp, letterSpacing = (-0.3).sp
    ),
    // Card titles / item names
    titleMedium = TextStyle(
        fontFamily = ManropeFamily, fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp, letterSpacing = 0.sp
    ),
    // Body text
    bodyMedium = TextStyle(
        fontFamily = ManropeFamily, fontWeight = FontWeight.Normal,
        fontSize = 14.sp, lineHeight = 22.sp
    ),
    // Metadata labels — uppercase, wide tracking (e.g. "CATATAN OPERASIONAL")
    labelSmall = TextStyle(
        fontFamily = ManropeFamily, fontWeight = FontWeight.SemiBold,
        fontSize = 10.sp, letterSpacing = 1.5.sp
    ),
    // Price / revenue figures — bold, prominent
    titleLarge = TextStyle(
        fontFamily = ManropeFamily, fontWeight = FontWeight.ExtraBold,
        fontSize = 28.sp, letterSpacing = (-0.5).sp
    ),
)
```

### 4.3 Shapes (`Shape.kt`)

```kotlin
val AppShapes = Shapes(
    small      = RoundedCornerShape(8.dp),   // inputs, chips
    medium     = RoundedCornerShape(16.dp),  // rounded-2xl — cards
    large      = RoundedCornerShape(24.dp),  // rounded-3xl — hero cards, modals
    extraLarge = RoundedCornerShape(32.dp)   // bottom sheets top corners
)
```

### 4.4 Motion & Animation

All interactive elements mirror the web app's `active:scale-95` tactile feedback and `animate-fade-in` / `animate-slide-up` patterns.

```kotlin
// Reusable press-scale modifier (active:scale-95)
fun Modifier.pressScale(): Modifier = composed {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )
    this
        .graphicsLayer { scaleX = scale; scaleY = scale }
        .clickable(interactionSource = interactionSource, indication = null) {}
}

// Slide-up entry for modal sheets (animate-modal-sheet)
AnimatedVisibility(
    visible = show,
    enter = slideInVertically(initialOffsetY = { it }) + fadeIn(tween(250)),
    exit  = slideOutVertically(targetOffsetY  = { it }) + fadeOut(tween(200))
) { /* sheet content */ }

// Fade-in for screen content (animate-fade-in)
AnimatedVisibility(
    visible = contentLoaded,
    enter = fadeIn(tween(300)) + expandVertically()
) { /* screen content */ }

// Modal pop-in (animate-modal-pop)
AnimatedVisibility(
    visible = dialogVisible,
    enter = scaleIn(initialScale = 0.92f) + fadeIn(tween(200)),
    exit  = scaleOut(targetScale  = 0.92f) + fadeOut(tween(150))
) { /* dialog content */ }
```

Accessibility: detect `Settings.Global.TRANSITION_ANIMATION_SCALE == 0` and skip all animations when the user has enabled reduced motion in system settings.

### 4.5 Key Component Patterns

**Cards:**
```kotlin
Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = Modifier.pressScale()
)
```

**Status Badge (`StatusBadge.kt`):**
```kotlin
@Composable
fun StatusBadge(status: String) {
    val (bg, fg, label) = when (status.lowercase()) {
        "success"  -> Triple(GreenSuccessBg, GreenSuccess,  "Sukses")
        "pending"  -> Triple(AmberWarningBg, AmberWarning,  "Pending")
        else       -> Triple(RedErrorBg,     RedError,      "Gagal")
    }
    Surface(color = bg, shape = RoundedCornerShape(999.dp)) {
        Text(label, color = fg, style = MaterialTheme.typography.labelSmall,
             modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
    }
}
```

**Stock Badge (on ItemCard):**
```kotlin
val stockBadge = when {
    item.stock == 0  -> Triple(RedErrorBg,     RedError,    "Habis")
    item.stock <= 5  -> Triple(AmberWarningBg, AmberWarning,"Stok Rendah")
    else             -> Triple(GreenSuccessBg, GreenSuccess, "${item.stock}")
}
```

**Bottom Navigation Bar (`BottomNavBar.kt`):**
```kotlin
NavigationBar(
    containerColor = SurfaceWhite.copy(alpha = 0.92f),
    modifier = Modifier.fillMaxWidth().shadow(elevation = 8.dp)
    // API 31+: add Modifier.graphicsLayer { renderEffect = BlurEffect(20f, 20f) }
) {
    navItems.forEach { item ->
        NavigationBarItem(
            selected = currentRoute == item.route,
            onClick  = { navController.navigate(item.route) },
            icon     = { Icon(item.icon, contentDescription = item.label) },
            label    = { Text(item.label, style = MaterialTheme.typography.labelSmall) },
            colors   = NavigationBarItemDefaults.colors(
                selectedIconColor   = IndigoDeep,
                selectedTextColor   = IndigoDeep,
                indicatorColor      = IndigoLight,   // soft indigo pill
                unselectedIconColor = TextSecondary,
                unselectedTextColor = TextSecondary,
            )
        )
    }
}
```

**Skeleton Loader (`SkeletonLoader.kt`):**
```kotlin
// Shimmer effect replaces spinners during list/card loading
val shimmerColors = listOf(Color(0xFFE2E8F0), Color(0xFFF8FAFC), Color(0xFFE2E8F0))
val transition = rememberInfiniteTransition()
val translateX by transition.animateFloat(
    initialValue = -1000f, targetValue = 1000f,
    animationSpec = infiniteRepeatable(tween(1200, easing = LinearEasing))
)
Box(modifier = Modifier
    .background(Brush.horizontalGradient(shimmerColors, startX = translateX))
    .clip(RoundedCornerShape(12.dp))
)
```

**Unified Modal (`PopupModal.kt`):**

Mirrors the web `PopupModal` component — supports center-dialog and bottom-sheet modes, handles back-press dismiss, and blocks background scroll.

```kotlin
@Composable
fun PopupModal(
    visible: Boolean,
    onDismiss: () -> Unit,
    sheetMode: Boolean = false,
    content: @Composable ColumnScope.() -> Unit
) {
    if (!visible) return
    BackHandler(onBack = onDismiss)
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        val shape = if (sheetMode)
            RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        else RoundedCornerShape(24.dp)

        Surface(
            shape = shape,
            color = SurfaceWhite,
            modifier = Modifier
                .fillMaxWidth()
                .let { if (sheetMode) it.wrapContentHeight() else it.padding(24.dp) }
        ) {
            Column(modifier = Modifier.padding(24.dp), content = content)
        }
    }
}
```

---

## 5. Authentication

Mirrors the web frontend's `AuthContext` with the same credentials and session persistence model.

### 5.1 Session Storage (`AuthSession.kt`)
```kotlin
const val VALID_USERNAME = "adminKasir"
const val VALID_PASSWORD = "kasirkeren"

class AuthSession @Inject constructor(@ApplicationContext ctx: Context) {
    private val prefs = EncryptedSharedPreferences.create(
        ctx, "kasir_auth_session",
        MasterKey.Builder(ctx).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    fun isLoggedIn(): Boolean = prefs.getBoolean("logged_in", false)
    fun login()  { prefs.edit().putBoolean("logged_in", true).apply() }
    fun logout() { prefs.edit().clear().apply() }
}
```

### 5.2 Login Screen (`LoginScreen.kt`)

Matches web `/login` page behavior exactly:

- `OutlinedTextField` for username + password (Manrope, IndigoDeep focus color, `VisualTransformation` for password field)
- Submit button shows **"Memproses..."** + `CircularProgressIndicator` for 300ms to match web loading state feel
- Wrong credentials → `SnackbarHostState.showSnackbar("Username atau password salah")`
- Success → `authSession.login()` → navigate to `"dashboard"` with `popUpTo("login") { inclusive = true }`
- If already logged in on launch: start directly at `"dashboard"` (mirrors `GuestRoute`)

### 5.3 Navigation Guard

```kotlin
// Mirrors ProtectedRoute and GuestRoute from the web app
val isLoggedIn by authViewModel.isLoggedIn.collectAsState()

composable("login") {
    if (isLoggedIn) {
        LaunchedEffect(Unit) {
            navController.navigate("dashboard") { popUpTo("login") { inclusive = true } }
        }
    } else LoginScreen()
}
// All protected screens check isLoggedIn and redirect to "login" if false
```

---

## 6. Navigation Graph

```kotlin
NavHost(startDestination = if (isLoggedIn) "dashboard" else "login") {
    composable("login")              { LoginScreen() }
    composable("dashboard")          { DashboardScreen() }
    composable("pos")                { PosScreen() }
    composable("cart")               { CartScreen() }
    composable("payment/{orderId}")  { MidtransWebView(...) }
    composable("items")              { ItemListScreen() }
    composable("history")            { HistoryScreen() }
}
```

Bottom nav tabs: **Dashboard · POS · Items · History**. User icon in top bar triggers logout confirmation → `authSession.logout()` → navigate to `"login"`.

---

## 7. API Integration

### 7.1 Retrofit Setup (`RetrofitClient.kt`)
```kotlin
private const val BASE_URL = "https://kasirbackend.maw07.web.id/"

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build())
    .build()
```

### 7.2 ApiService Interface (`ApiService.kt`)
```kotlin
interface ApiService {

    @GET("/")
    suspend fun health(): HealthResponse

    // Items
    @GET("api/items")
    suspend fun getItems(): List<Item>

    @GET("api/items/{id}")
    suspend fun getItem(@Path("id") id: Int): Item

    @POST("api/items")
    suspend fun createItem(@Body body: ItemCreate): Item

    @PUT("api/items/{id}")
    suspend fun updateItem(@Path("id") id: Int, @Body body: ItemUpdate): Item

    @DELETE("api/items/{id}")
    suspend fun deleteItem(@Path("id") id: Int): MessageResponse

    @Multipart
    @POST("api/items/upload-image")
    suspend fun uploadImage(@Part file: MultipartBody.Part): ImageUrlResponse

    @Multipart
    @POST("api/items/import-csv")
    suspend fun importCsv(@Part file: MultipartBody.Part): CsvImportResponse

    // Transactions
    @GET("api/transactions")
    suspend fun getTransactions(): List<Transaction>

    @GET("api/transactions/{id}")
    suspend fun getTransaction(@Path("id") id: Int): Transaction

    @POST("api/transactions")
    suspend fun createTransaction(@Body body: TransactionCreate): CreateTransactionResponse

    // Dashboard
    @GET("api/dashboard/today")
    suspend fun getDashboardToday(): DashboardToday

    @GET("api/dashboard/chart")
    suspend fun getDashboardChart(): List<ChartEntry>

    // Payment
    @POST("api/payment/create-token")
    suspend fun createPaymentToken(@Body body: PaymentTokenRequest): PaymentTokenResponse

    @POST("api/payment/notification")
    suspend fun paymentNotification(@Body body: Map<String, String>): MessageResponse

    @POST("api/payment/success/{orderId}")
    suspend fun manualSuccess(
        @Path("orderId") orderId: String,
        @Query("payment_type") paymentType: String = "Tunai"
    ): MessageResponse
}
```

### 7.3 Data Models (`model/`)

```kotlin
// Item.kt
data class Item(
    val id: Int,
    val name: String,
    val price: Double,
    @SerializedName("purchase_price") val purchasePrice: Double,
    val stock: Int,
    val category: String?,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("is_active") val isActive: Boolean,
    @SerializedName("created_at") val createdAt: String,
)
data class ItemCreate(
    val name: String, val price: Double,
    @SerializedName("purchase_price") val purchasePrice: Double,
    val stock: Int, val category: String? = null,
    @SerializedName("image_url") val imageUrl: String? = null,
)
data class ItemUpdate(
    val name: String? = null, val price: Double? = null,
    @SerializedName("purchase_price") val purchasePrice: Double? = null,
    val stock: Int? = null,
    @SerializedName("is_active") val isActive: Boolean? = null,
)

// Transaction.kt
data class Transaction(
    val id: Int,
    @SerializedName("order_id") val orderId: String,
    @SerializedName("total_amount") val totalAmount: Double,
    val status: String,
    @SerializedName("payment_type") val paymentType: String?,
    @SerializedName("transaction_items") val items: List<TransactionItem>,
    @SerializedName("created_at") val createdAt: String,
)
data class TransactionItem(
    val id: Int, val name: String, val price: Double,
    @SerializedName("purchase_price") val purchasePrice: Double,
    val quantity: Int,
)
data class TransactionCreate(
    @SerializedName("total_amount") val totalAmount: Double,
    val items: List<CartItemPayload>,
)
data class CartItemPayload(
    val id: Int, val name: String, val price: Double,
    @SerializedName("purchase_price") val purchasePrice: Double,
    val quantity: Int,
)
data class CreateTransactionResponse(
    @SerializedName("order_id") val orderId: String,
    @SerializedName("transaction_id") val transactionId: Int,
)

// Dashboard.kt
data class DashboardToday(
    @SerializedName("total_revenue") val totalRevenue: Double,
    @SerializedName("total_profit") val totalProfit: Double,
    @SerializedName("total_orders") val totalOrders: Int,
    @SerializedName("top_items") val topItems: List<TopItem>,
)
data class TopItem(val name: String, val qty: Int)
data class ChartEntry(val date: String, val revenue: Double)

// Payment.kt
data class PaymentTokenRequest(
    @SerializedName("order_id") val orderId: String,
    @SerializedName("item_details") val itemDetails: List<PaymentItemDetail>,
)
data class PaymentItemDetail(
    val id: String, val name: String, val price: Double, val quantity: Int,
)
data class PaymentTokenResponse(
    @SerializedName("snap_token") val snapToken: String,
    @SerializedName("redirect_url") val redirectUrl: String,
)
data class CsvImportResponse(
    @SerializedName("total_rows") val totalRows: Int,
    @SerializedName("processed_rows") val processedRows: Int,
    @SerializedName("created_count") val createdCount: Int,
    @SerializedName("updated_count") val updatedCount: Int,
    @SerializedName("failed_count") val failedCount: Int,
    val errors: List<CsvError>,
)
data class CsvError(val row: Int, val item: String, val reason: String)
data class MessageResponse(val message: String)
data class ImageUrlResponse(@SerializedName("image_url") val imageUrl: String)
data class HealthResponse(val message: String)
```

---

## 8. Screen-by-Screen Implementation

### 8.1 Login Screen

```
┌────────────────────────────────┐
│  [Logo / Brand mark]           │
│  KasirKeren                    │
│                                │
│  ┌──────────────────────────┐  │
│  │ Username                 │  │
│  └──────────────────────────┘  │
│  ┌──────────────────────────┐  │
│  │ Password          [hide] │  │
│  └──────────────────────────┘  │
│                                │
│  [ Masuk  /  Memproses... ⏳ ] │
└────────────────────────────────┘
```

- `OutlinedTextField` styled with IndigoDeep focus color and Manrope font
- Password field with `PasswordVisualTransformation` + toggle icon
- Tap **Masuk**: validate credentials, show 300ms loading state ("Memproses...")
- Wrong → Snackbar: `"Username atau password salah"`
- Success → `authSession.login()` → navigate `dashboard`, pop `login`

---

### 8.2 Dashboard Screen

**Data:** `GET /api/dashboard/today` · `GET /api/dashboard/chart`

```
┌─────────────────────────────────────┐
│  KasirKeren         [🔔]  [👤]     │  ← sticky KasirTopBar
├─────────────────────────────────────┤
│ ┌─────────────────────────────────┐ │
│ │ gradient(IndigoDeep→IndigoMed)  │ │  ← Revenue Hero Card
│ │  Pendapatan Hari Ini            │ │
│ │  Rp 13.000    ← ExtraBold 28sp  │ │
│ │  Laba: Rp 7.000  ·  2 Pesanan  │ │
│ └─────────────────────────────────┘ │
│                                     │
│ ┌────────┐ ┌────────┐ ┌──────────┐ │  ← KPI mini cards
│ │ Omzet  │ │ Laba   │ │ Pesanan  │ │
│ │Rp13.000│ │Rp7.000 │ │    2     │ │
│ └────────┘ └────────┘ └──────────┘ │
│                                     │
│ ┌─────────────────────────────────┐ │
│ │  7 hari terakhir (Vico bar)     │ │  ← indigo bars, id-ID weekday labels
│ └─────────────────────────────────┘ │
│                                     │
│  Produk Terlaris                    │
│  1. jeruk    ×2                     │
│  2. bengbeng ×1                     │
│                                     │
│                           [+]       │  ← FAB bottom-right (mobile)
└─────────────────────────────────────┘
```

Loading state → shimmer `SkeletonLoader` cards on hero + KPI + chart areas.
Error → Snackbar: `"Gagal memuat data. Coba lagi."`
Content → fade-in (`AnimatedVisibility` with `fadeIn(tween(300))`).

**Revenue Hero Card gradient:**
```kotlin
Box(modifier = Modifier
    .fillMaxWidth()
    .clip(RoundedCornerShape(24.dp))
    .background(Brush.linearGradient(listOf(IndigoDeep, IndigoMedium)))
    .padding(24.dp)
)
```

**Vico bar chart:**
```kotlin
Chart(
    chart = columnChart(columns = listOf(
        LineComponent(color = IndigoDeep.toArgb(), thicknessDp = 16f,
            shape = Shapes.roundedCornerShape(topLeft = 4.dp, topRight = 4.dp))
    )),
    chartModelProducer = ChartEntryModelProducer(
        chartData.mapIndexed { i, e -> entryOf(i.toFloat(), e.revenue.toFloat()) }
    ),
    // x-axis: short weekday in id-ID locale ("Sen", "Sel", ...)
)
```

---

### 8.3 POS Screen

**Data:** `GET /api/items`

```
┌─────────────────────────────────────┐
│  [🔍 Cari produk...]                │
│  [Semua] [Snack] [Minuman] [...]    │  ← FilterChip row (LazyRow)
├─────────────────────────────────────┤
│  ┌──────────┐  ┌──────────┐        │
│  │ [image]  │  │ [image]  │        │  ← LazyVerticalGrid (2 col)
│  │ Beng Beng│  │ Jeruk    │        │
│  │ Rp 3.000 │  │ Rp 2.000 │        │
│  │ [17 ●]   │  │ [Habis ●]│        │  ← stock badge
│  └──────────┘  └──────────┘        │
├─────────────────────────────────────┤
│  🛒 2 item · Rp 6.000  [Lihat Cart]│  ← floating cart summary bar
└─────────────────────────────────────┘
```

**Cart State (PosViewModel — scoped to NavGraph so it survives tab switches):**
```kotlin
data class CartEntry(val item: Item, val quantity: Int)

fun addToCart(item: Item) {
    val current = _cart.value[item.id]
    if (current != null && current.quantity >= item.stock) {
        _snackbar.value = "Stok tidak cukup untuk ${item.name}"
        return
    }
    _cart.update { map ->
        map + (item.id to CartEntry(item, (current?.quantity ?: 0) + 1))
    }
}
```

**ItemCard behavior:**
- `Modifier.pressScale()` on entire card
- `AsyncImage` (Coil) with `placeholder` + `error` fallback drawable
- Add button `enabled = item.stock > 0`, `alpha = 0.4f` when out of stock
- Tapping an out-of-stock card → Snackbar: `"Stok habis"`

---

### 8.4 Cart Screen / Bottom Sheet

```
┌─────────────────────────────────────┐
│  Keranjang Belanja           [✕]   │
├─────────────────────────────────────┤
│  Beng Beng                          │
│  Rp 3.000              [−] 2 [+] [🗑]│
│────────────────────────────────────  │
│  Jeruk                              │
│  Rp 2.000              [−] 1 [+] [🗑]│
├─────────────────────────────────────┤
│  Total                  Rp 8.000   │
│                                     │
│  [         Bayar Sekarang         ] │
└─────────────────────────────────────┘
```

- `SwipeToDismiss` on each row for quick removal
- `[−]` disabled at quantity 1 (tap → remove)
- `[+]` disabled when `quantity == item.stock`
- **"Bayar Sekarang"** → opens `PaymentMethodSheet`

---

### 8.5 Payment Method Sheet

```
┌─────────────────────────────────────┐
│  Pilih Metode Pembayaran     [✕]   │
├─────────────────────────────────────┤
│  ◉ Tunai (Cash)                    │
│  ○ Midtrans (Transfer / QRIS)      │
│                                     │
│  [         Konfirmasi Bayar        ]│
└─────────────────────────────────────┘
```

**Tunai flow:**
```
POST /api/transactions → { order_id }
POST /api/payment/success/{order_id}?payment_type=Tunai
→ clearCart() → Success screen → navigate Dashboard
```

**Midtrans flow:**
```
POST /api/transactions → { order_id }
POST /api/payment/create-token → { redirect_url }
→ navigate MidtransWebView(redirect_url)
→ WebViewClient detects finish/settlement URL
→ POST /api/payment/success/{order_id} (dev fallback)
→ clearCart() → Success screen → navigate Dashboard
```

**MidtransWebView:**
```kotlin
@Composable
fun MidtransWebView(redirectUrl: String, onResult: (Boolean) -> Unit) {
    var loading by remember { mutableStateOf(true) }
    Box {
        AndroidView(factory = { ctx ->
            WebView(ctx).apply {
                settings.javaScriptEnabled = true
                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView, url: String) { loading = false }
                    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                        return when {
                            "finish" in url || "settlement" in url -> { onResult(true);  true }
                            "error"  in url || "cancel"  in url   -> { onResult(false); true }
                            else -> false
                        }
                    }
                }
                loadUrl(redirectUrl)
            }
        }, modifier = Modifier.fillMaxSize())
        if (loading) LinearProgressIndicator(
            color = IndigoDeep,
            modifier = Modifier.fillMaxWidth().align(Alignment.TopCenter)
        )
    }
}
```

---

### 8.6 Item Management Screen

**Data:** `GET /api/items` · CRUD · image upload · CSV import

```
┌─────────────────────────────────────┐
│  Inventaris        [📥 CSV]  [+]   │
├─────────────────────────────────────┤
│  ┌───────────────────────────────┐  │
│  │[img] Beng Beng     Rp 3.000  │  │
│  │      [17 ●]           [✏️][🗑]│  │
│  └───────────────────────────────┘  │
│  ┌───────────────────────────────┐  │
│  │[img] Jeruk         Rp 2.000  │  │
│  │      [Habis ●]        [✏️][🗑]│  │
│  └───────────────────────────────┘  │
└─────────────────────────────────────┘
```

**ItemFormModal (Add / Edit) — bottom sheet via `PopupModal(sheetMode = true)`:**
- Fields: Nama, Harga Jual, Harga Beli, Stok, Kategori, Foto
- Inline form validation: shows helper text `"Harga jual tidak boleh kurang dari harga beli"` below field (mirrors web behavior)
- Numeric fields: `KeyboardType.Number`, filter out non-digit input, guard `min = 0`
- Image picker: `ActivityResultContracts.GetContent("image/*")` → upload to `POST /api/items/upload-image` → Coil preview
- Submit: `POST /api/items` (new) or `PUT /api/items/{id}` (edit)
- Delete: trash icon → `AlertDialog` confirmation → `DELETE /api/items/{id}` (soft delete)

**CSV Import UX:**
- Toolbar icon → `ActivityResultContracts.GetContent("text/*")` file picker
- Upload → `CircularProgressIndicator` in button while loading
- Result → `CsvImportResult` bottom sheet:

```
┌────────────────────────────────────────┐
│  Hasil Import CSV              [✕]    │
│  Total baris        : 20              │
│  Berhasil diproses  : 18              │
│  Dibuat baru        : 10              │
│  Diperbarui         : 8               │
│  Gagal              : 2               │
│                                        │
│  ⚠ Baris 4 – ABC Sweet Soy            │
│    Harga jual tidak boleh lebih kecil  │
│    dari harga beli                     │
│                                        │
│  (menampilkan 5 error pertama)         │
└────────────────────────────────────────┘
```

---

### 8.7 Transaction History Screen

**Data:** `GET /api/transactions`

```
┌─────────────────────────────────────┐
│  History                            │  ← headlineLarge (ExtraBold 34sp)
│  CATATAN OPERASIONAL                │  ← labelSmall (uppercase, tracking)
├─────────────────────────────────────┤
│  Hari Ini                           │  ← date group header
│  ┌───────────────────────────────┐  │
│  │ ORDER-1711...   [Sukses ●]    │  │
│  │ Tunai                Rp 13.000│  │
│  │ 14:32                         │  │
│  └───────────────────────────────┘  │
│  Kemarin                            │
│  ┌───────────────────────────────┐  │
│  │ ORDER-1710...   [Pending ●]   │  │
│  │ Midtrans              Rp 6.000│  │
│  └───────────────────────────────┘  │
└─────────────────────────────────────┘
```

**Date grouping:**
```kotlin
fun groupByDate(list: List<Transaction>): Map<String, List<Transaction>> {
    val today = LocalDate.now()
    return list.groupBy { tx ->
        val date = LocalDate.parse(tx.createdAt.take(10))
        when (date) {
            today            -> "Hari Ini"
            today.minusDays(1) -> "Kemarin"
            else             -> date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("id")))
        }
    }
}
```

**Detail Modal** — `PopupModal` (center dialog, not sheet):
- Order ID + status badge + payment type
- Item table: name · qty · unit price · subtotal
- Grand total row (ExtraBold)

**Empty state:** centered `EmptyState` composable with icon + `"Belum ada transaksi"`

---

## 9. UX Safeguards (mirrors web frontend)

| Safeguard | Implementation |
|---|---|
| Stock cap in cart | `addToCart()` blocks if `qty >= item.stock`, fires Snackbar |
| Price rule in form | Enable submit only if `harga jual >= harga beli` |
| Numeric min guard | Input filter: `filter { it.isDigit() }`, rejects negatives |
| Backend error surfacing | Parse `detail` from HTTP 400 body, show in Snackbar |
| Out-of-stock add button | `enabled = false`, `alpha = 0.4f` on button |
| Modal back-press | `BackHandler { onDismiss() }` inside all `PopupModal` calls |
| Cart survives tab switches | `PosViewModel` scoped to NavGraph via Hilt |
| Session persistence | `EncryptedSharedPreferences` survives process kill and relaunch |
| Notification icon | Rendered in top bar (UI only, no backend wiring — matches web) |

---

## 10. Error Handling Strategy

```kotlin
sealed class UiState<out T> {
    object Idle    : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

suspend fun <T> safeApiCall(call: suspend () -> T): UiState<T> = try {
    UiState.Success(call())
} catch (e: HttpException) {
    val detail = try {
        JSONObject(e.response()?.errorBody()?.string() ?: "").optString("detail")
    } catch (_: Exception) { null }
    UiState.Error(detail ?: "Terjadi kesalahan (${e.code()})")
} catch (e: IOException) {
    UiState.Error("Koneksi bermasalah. Periksa jaringan Anda.")
}
```

| HTTP | Handling |
|---|---|
| `400` | Parse `detail`, Snackbar in Indonesian |
| `404` | Inline error card + retry button |
| `422` | Map field errors to form helper text |
| Network / timeout | Snackbar + pull-to-refresh retry |

---

## 11. Formatting Utilities

```kotlin
// Rupiah (matches id-ID locale: "Rp13.000")
fun Double.toRupiah(): String =
    NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        .apply { maximumFractionDigits = 0 }.format(this)

// Timestamp display
fun String.toDisplayDate(): String {
    val parser    = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val formatter = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("id", "ID"))
    return try { formatter.format(parser.parse(this)!!) } catch (_: Exception) { this }
}

// Short weekday for chart x-axis (matches id-ID on web: "Sen", "Sel", ...)
fun String.toShortWeekday(): String {
    val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(this) ?: return this
    return SimpleDateFormat("EEE", Locale("id", "ID")).format(date)
}
```

---

## 12. Gradle Dependencies (`app/build.gradle.kts`)

```kotlin
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2024.09.00")
    implementation(composeBom)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.animation:animation")
    implementation("androidx.activity:activity-compose:1.9.0")

    implementation("androidx.navigation:navigation-compose:2.8.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.0")

    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-compiler:2.51.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("com.patrykandpatrick.vico:compose-m3:1.15.0")
    implementation("androidx.security:security-crypto:1.1.0-alpha06")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.0")
}
```

---

## 13. Permissions (`AndroidManifest.xml`)

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"
    android:maxSdkVersion="32" />
```

---

## 14. Complete Cashier Workflow (End-to-End)

```
App launch
  ├─ Session present? → Dashboard
  └─ No session      → LoginScreen (adminKasir / kasirkeren)

Dashboard
  ├─ GET /api/dashboard/today  → hero card + KPI mini cards
  ├─ GET /api/dashboard/chart  → 7-day indigo bar chart
  └─ Tap [+] FAB or POS tab   → POS screen

POS Screen
  ├─ GET /api/items → item grid (search + category chips)
  ├─ Tap item → cart updated (stock cap enforced)
  └─ Cart summary bar → tap "Lihat Cart"

Cart Screen
  ├─ Adjust qty / swipe-remove
  └─ "Bayar Sekarang" → PaymentMethodSheet

  ┌─ Tunai ─────────────────────────────────────────────────────────┐
  │  POST /api/transactions                                          │
  │  POST /api/payment/success/{order_id}?payment_type=Tunai         │
  │  ✓ clearCart() → Success screen → Dashboard                     │
  └──────────────────────────────────────────────────────────────────┘

  ┌─ Midtrans ──────────────────────────────────────────────────────┐
  │  POST /api/transactions → { order_id }                           │
  │  POST /api/payment/create-token → { redirect_url }              │
  │  MidtransWebView(redirect_url)                                   │
  │  Detect finish/settlement URL → POST /api/payment/success        │
  │  ✓ clearCart() → Success screen → Dashboard                     │
  └──────────────────────────────────────────────────────────────────┘

Items tab   → CRUD modals + image upload + CSV import + result sheet
History tab → Date-grouped list + detail modal per transaction
User icon   → Logout confirmation → authSession.logout() → Login
```

---

## 15. Development Phases

### Phase 1 — Foundation (Week 1)
- Project setup: Hilt, Retrofit, Compose BOM, Navigation, security-crypto
- `Color.kt`, `Type.kt` (Manrope), `Shape.kt`, `Theme.kt`
- All data models + `ApiService` + `RetrofitClient` + `safeApiCall`
- `AuthSession` + `LoginScreen` + nav guards

### Phase 2 — Core POS Flow (Week 2)
- Item grid: search, category chips, stock badges, `ItemCard`
- `PosViewModel` cart with stock protection + Snackbar feedback
- `CartScreen` / `CartBottomSheet` with qty controls + swipe-remove
- Cash (Tunai) payment flow end-to-end
- Dashboard today stats + skeleton loaders

### Phase 3 — Full Features (Week 3)
- 7-day Vico bar chart on Dashboard
- Midtrans WebView payment flow
- Item CRUD (`ItemFormModal`) + image upload
- Transaction history with date grouping + `HistoryDetailModal`
- `PopupModal` unified modal system

### Phase 4 — Polish (Week 4)
- CSV import + `CsvImportResult` sheet (first 5 errors shown)
- `SkeletonLoader` shimmer on all loading screens
- `EmptyState` components
- All error/retry states
- Pull-to-refresh on all list screens
- `Modifier.pressScale()` on all interactive elements
- `animate-fade-in` / `animate-slide-up` / `animate-modal-pop` on every transition
- Reduced-motion accessibility pass
- Final QA against design checklist

---

## 16. Key Design Checklist

- [ ] Manrope font in `res/font/`: Regular, Medium, SemiBold, Bold, ExtraBold
- [ ] `#2E44A7` IndigoDeep as primary everywhere (buttons, FAB, chart, active states)
- [ ] `rounded-2xl` (16dp) on item cards + list cards
- [ ] `rounded-3xl` (24dp) on hero card, modals, bottom sheets
- [ ] Bottom nav: frosted background (`alpha 0.92`) + indigo pill indicator + shadow
- [ ] `Modifier.pressScale()` (`active:scale-95`) on all cards and buttons
- [ ] `animate-fade-in` on screen content after load
- [ ] `animate-slide-up` on all bottom sheet entries
- [ ] `animate-modal-pop` on center dialog entries
- [ ] Revenue Hero Card: `LinearGradient(IndigoDeep → IndigoMedium)` with 24dp corners
- [ ] Status pills: text + 15%-opacity background (Green / Amber / Red)
- [ ] Stock badges: numeric-green / "Stok Rendah"-amber / "Habis"-red
- [ ] Metadata labels: `labelSmall` uppercase + 1.5sp letter spacing
- [ ] Price/revenue figures: `titleLarge` ExtraBold with `id-ID` Rupiah locale
- [ ] Snackbar (not Toast) for all error/success messages, in Indonesian
- [ ] `SkeletonLoader` shimmer replaces spinners on list/card screens
- [ ] `EmptyState` composable on History + Items when list is empty
- [ ] `BackHandler` inside every modal/sheet to dismiss on back press
- [ ] Notification icon in top bar (UI only, no backend)
- [ ] Reduced-motion: animations skipped when system accessibility scale = 0

---

*Plan version: March 2026 · KasirKeren Android · Aligned with React web frontend*