### Shared Elements Navigation Setup

Source: https://developer.android.com/develop/ui/compose/animation/shared-elements/navigation

Demonstrates how to set up a navigation host with shared elements using SharedTransitionLayout. This example shows navigation between a home screen and details screen with shared element transitions for images and text.

```APIDOC
## Shared Elements Navigation Setup

### Description
Configures a navigation host with shared element transitions using SharedTransitionLayout and NavHost. Enables animated transitions between composables when navigating.

### Key Components

#### SharedTransitionLayout
- Container that manages shared element transitions across multiple composables
- Required parent for all screens that use shared elements
- Provides SharedTransitionScope to child composables

#### NavHost Configuration
- **navController** - Controls navigation between destinations
- **startDestination** - Initial screen to display ("home")
- **routes** - Define navigation destinations with optional arguments

### Route Definitions

#### Home Route
- **route**: "home"
- **description**: Displays list of items with shared images and text

#### Details Route
- **route**: "details/{item}"
- **parameters**:
  - **item** (IntType) - Required - Index of the selected item
- **description**: Shows detailed view of selected item with shared element animations

### Navigation Flow
1. User starts on home screen displaying list of items
2. User clicks item, triggering navigation to "details/{itemIndex}"
3. Shared elements (image and text) animate from home to details screen
4. User can navigate back with predictive back gesture
5. Shared elements animate back to original positions

### Implementation Pattern
```kotlin
SharedTransitionLayout {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") { /* HomeScreen */ }
        composable("details/{item}", arguments = listOf(
            navArgument("item") { type = NavType.IntType }
        )) { /* DetailsScreen */ }
    }
}
```
```

--------------------------------

### ConstraintLayout Basic Example

Source: https://developer.android.com/develop/ui/compose/layouts/constraintlayout?rec=CjRodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYm9tEAIYCSABKAIwEjoDMy43

Complete example demonstrating how to use ConstraintLayout with multiple composables. Shows how to create references, apply constraints using constrainAs(), and link composables relative to each other with margins.

```APIDOC
## ConstraintLayout Basic Example

### Description
A practical example showing how to create a ConstraintLayout with a Button and Text composable, constraining them relative to each other and the parent.

### Code Example

```kotlin
@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout {
        // Create references for the composables to constrain
        val (button, text) = createRefs()

        Button(
            onClick = { /* Do something */ },
            // Assign reference "button" to the Button composable
            // and constrain it to the top of the ConstraintLayout
            modifier = Modifier.constrainAs(button) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text("Button")
        }

        // Assign reference "text" to the Text composable
        // and constrain it to the bottom of the Button composable
        Text(
            "Text",
            Modifier.constrainAs(text) {
                top.linkTo(button.bottom, margin = 16.dp)
            }
        )
    }
}
```

### Explanation

#### Step 1: Create References
```kotlin
val (button, text) = createRefs()
```
Creates two references to identify the Button and Text composables within the layout.

#### Step 2: Constrain Button
```kotlin
modifier = Modifier.constrainAs(button) {
    top.linkTo(parent.top, margin = 16.dp)
}
```
Constrains the Button's top edge to the parent's top edge with a 16.dp margin.

#### Step 3: Constrain Text
```kotlin
Modifier.constrainAs(text) {
    top.linkTo(button.bottom, margin = 16.dp)
}
```
Constrains the Text's top edge to the Button's bottom edge with a 16.dp margin.

### Result
- Button positioned 16.dp from the top of the ConstraintLayout
- Text positioned 16.dp below the Button
- Clean, flat hierarchy without nested layouts

### File Reference
- Source: ConstraintLayoutSnippets.kt
```

--------------------------------

### Verify NavHost Start Destination with Compose UI Test

Source: https://developer.android.com/develop/ui/compose/navigation

This JUnit test class demonstrates how to set up a 'TestNavHostController' and 'ComposeNavigator' to test the 'AppNavHost'. The 'setupAppNavHost' method initializes the test environment, and 'appNavHost_verifyStartDestination' asserts that the correct start destination is displayed on the screen.

```Kotlin
class NavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            AppNavHost(navController = navController)
        }
    }

    // Unit test
    @Test
    fun appNavHost_verifyStartDestination() {
        composeTestRule
            .onNodeWithContentDescription("Start Screen")
            .assertIsDisplayed()
    }
}
```

--------------------------------

### Scaffold Component Setup

Source: https://developer.android.com/develop/ui/compose/quick-guides/content/create-scaffold?hl=en

Complete implementation example of the Scaffold composable with top app bar, bottom app bar, and floating action button. This demonstrates how to structure a complex UI using Material Design guidelines and manage internal state interactions.

```APIDOC
## Scaffold Composable Implementation

### Description
The Scaffold composable provides a standardized platform for building complex user interfaces according to Material Design guidelines. It holds together different parts of the UI such as app bars and floating action buttons, giving apps a coherent look and feel.

### Component
Scaffold

### Parameters
- **topBar** (Composable) - Optional - The app bar displayed across the top of the screen
- **bottomBar** (Composable) - Optional - The app bar displayed across the bottom of the screen
- **floatingActionButton** (Composable) - Optional - A button that hovers over the bottom-right corner for key actions
- **content** (Composable with innerPadding) - Required - The main content area that receives innerPadding for proper spacing

### Implementation Example
```kotlin
@Composable
fun ScaffoldExample() {
    var presses by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Top app bar")
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Bottom app bar",
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { presses++ }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "This is an example of a scaffold."
            )
        }
    }
}
```

### Key Features
- **topBar**: Displays an app bar across the top of the screen
- **bottomBar**: Displays an app bar across the bottom of the screen
- **floatingActionButton**: Provides a floating button for primary actions
- **innerPadding**: Automatically calculated padding passed to content to avoid overlap with app bars
- **State Management**: Supports internal state management for interactive components

### Version Requirements
- Minimum SDK: API level 21 or higher
- Compose BOM: 2026.03.00 or higher

### Dependencies
```kotlin
implementation(platform("androidx.compose:compose-bom:2026.03.00"))
```
```

--------------------------------

### CompositionLocalProvider Example - LocalContentColor

Source: https://developer.android.com/develop/ui/compose/compositionlocal?rec=CkZodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZGVzaWduc3lzdGVtcy9hbmF0b215EAEYCSABKAUwHjoDMy43

Practical example demonstrating CompositionLocalProvider with LocalContentColor. Shows how to provide different content colors at different levels of the composition tree and how nested providers override parent values.

```APIDOC
## CompositionLocalProvider Example - LocalContentColor

### Description
LocalContentColor is a CompositionLocal that contains the preferred content color for text and iconography, ensuring contrast against the current background. This example demonstrates providing different values for different parts of the Composition using nested CompositionLocalProviders.

### Example Code

```kotlin
@Composable
fun CompositionLocalExample() {
    MaterialTheme {
        // Surface provides contentColorFor(MaterialTheme.colorScheme.surface) by default
        // This is to automatically make text and other content contrast to the background
        // correctly.
        Surface {
            Column {
                Text("Uses Surface's provided content color")
                CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.primary) {
                    Text("Primary color provided by LocalContentColor")
                    Text("This Text also uses primary as textColor")
                    CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.error) {
                        DescendantExample()
                    }
                }
            }
        }
    }
}

@Composable
fun DescendantExample() {
    // CompositionLocalProviders also work across composable functions
    Text("This Text uses the error color now")
}
```

### Key Points
- **Surface** automatically provides appropriate content color for its background
- **First CompositionLocalProvider** overrides content color to primary for its descendants
- **Nested CompositionLocalProvider** further overrides to error color for DescendantExample
- CompositionLocalProvider scoping works across composable function boundaries
- Each Text composable automatically uses the nearest provided LocalContentColor value
```

--------------------------------

### Complete Custom State Example in Kotlin

Source: https://developer.android.com/develop/ui/compose/styles/state-animations

Full example showing how to use custom state management in a Compose application. Demonstrates creating a StyleStateKeySample composable that uses the MediaPlayer with state-based styling, allowing dynamic style changes when the state parameter is updated.

```Kotlin
@Composable
fun StyleStateKeySample() {
    // Using the extension function to change the border color to green while playing
    val style = Style {
        borderColor(Color.Gray)
        playerPlaying {
            animate {
                borderColor(Color.Green)
            }
        }
        playerPaused {
            animate {
                borderColor(Color.Blue)
            }
        }
    }
    val styleState = remember { MutableStyleState(null) }
    styleState[playerStateKey] = PlayerState.Playing

    // Using the style in a composable that sets the state -> notice if you change the state parameter, the style changes. You can link this up to an ViewModel and change the state from there too.
    MediaPlayer(url = "https://example.com/media/video",
        style = style,
        state = PlayerState.Stopped)
}
```

--------------------------------

### ConstraintLayout Example - Button and Text

Source: https://developer.android.com/develop/ui/compose/layouts/constraintlayout?rec=CkJodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdG91Y2gtaW5wdXQvZm9jdXMQAhgJIAEoAzAmOgMzLjc

Complete example demonstrating how to use ConstraintLayout to position a Button and Text composable relative to each other with margins.

```APIDOC
## Example: ConstraintLayout with Button and Text

### Description
This example shows how to constrain a Button to the top of the ConstraintLayout and a Text composable to the bottom of the Button.

### Code Example
```kotlin
@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout {
        // Create references for the composables to constrain
        val (button, text) = createRefs()

        Button(
            onClick = { /* Do something */ },
            // Assign reference "button" to the Button composable
            // and constrain it to the top of the ConstraintLayout
            modifier = Modifier.constrainAs(button) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text("Button")
        }

        // Assign reference "text" to the Text composable
        // and constrain it to the bottom of the Button composable
        Text(
            "Text",
            Modifier.constrainAs(text) {
                top.linkTo(button.bottom, margin = 16.dp)
            }
        )
    }
}
```

### Constraint Details
- **Button**: Top edge linked to parent top with 16.dp margin
- **Text**: Top edge linked to button bottom with 16.dp margin

### Result
The Button appears at the top of the ConstraintLayout, and the Text appears 16.dp below the Button.
```

--------------------------------

### LocalContentColor - Practical Example

Source: https://developer.android.com/develop/ui/compose/compositionlocal?rec=CkFodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdG9vbGluZy9wcmV2aWV3cxABGAkgASgFMBs6AzMuNw

Practical example demonstrating CompositionLocalProvider with LocalContentColor to manage text and iconography colors at different levels of the UI tree. Shows how nested providers override parent values.

```APIDOC
## LocalContentColor CompositionLocal Example

### Description
LocalContentColor is a CompositionLocal that contains the preferred content color for text and iconography, ensuring proper contrast against the current background. This example demonstrates nested CompositionLocalProvider usage to override content colors at different hierarchy levels.

### Example Implementation
```kotlin
@Composable
fun CompositionLocalExample() {
    MaterialTheme {
        // Surface provides contentColorFor(MaterialTheme.colorScheme.surface) by default
        // This automatically makes text and other content contrast correctly
        Surface {
            Column {
                Text("Uses Surface's provided content color")
                CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.primary) {
                    Text("Primary color provided by LocalContentColor")
                    Text("This Text also uses primary as textColor")
                    CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.error) {
                        DescendantExample()
                    }
                }
            }
        }
    }
}

@Composable
fun DescendantExample() {
    // CompositionLocalProviders work across composable function boundaries
    Text("This Text uses the error color now")
}
```

### Key Points
- Surface automatically provides appropriate content color for contrast
- First CompositionLocalProvider overrides with primary color
- Nested CompositionLocalProvider overrides with error color
- Nested providers take precedence over parent providers
- Values propagate to descendants across composable function boundaries
```

--------------------------------

### Activity-Level ViewModel Setup in Compose

Source: https://developer.android.com/develop/ui/compose/migrate/other-considerations?rec=CkJodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYW5pbWF0aW9uL3Rlc3RpbmcQAhgJIAEoAjAYOgMzLjc

Demonstrates the complete setup of a Compose activity with ViewModel integration. The GreetingActivity sets content using setContent and MaterialTheme, displaying multiple GreetingScreen composables. Both screens receive different userId parameters but share the same GreetingViewModel instance due to activity-level scoping.

```Kotlin
class GreetingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Column {
                    GreetingScreen("user1")
                    GreetingScreen("user2")
                }
            }
        }
    }
}
```

--------------------------------

### Complete Example: Animated List Items

Source: https://developer.android.com/develop/ui/compose/lists?rec=CkdodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdG9vbGluZy9lZGl0b3ItYWN0aW9ucxADGAkgASgDMCE6AzMuNw

A complete implementation example showing how to create a lazy column with animated transitions for items being added, removed, or reordered. Demonstrates best practices for combining item keys with the animateItem modifier.

```APIDOC
## Animated Items in Lazy Lists - Complete Example

### Description
Implement a composable function that displays a list of strings with animated transitions when items are added, removed, or reordered.

### Function Signature
```kotlin
@Composable
fun ListAnimatedItems(
    items: List<String>,
    modifier: Modifier = Modifier
)
```

### Parameters
- **items** (List<String>) - Required - List of strings to display
- **modifier** (Modifier) - Optional - Modifier for the LazyColumn container

### Implementation
```kotlin
@Composable
fun ListAnimatedItems(
    items: List<String>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier) {
        // Use a unique key per item, so that animations work as expected.
        items(items, key = { it }) {
            ListItem(
                headlineContent = { Text(it) },
                modifier = Modifier
                    .animateItem(
                        // Optionally add custom animation specs
                    )
                    .fillParentMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 0.dp),
            )
        }
    }
}
```

### Features
- **Unique Keys**: Uses item string as key for animation tracking
- **Animated Transitions**: Automatically animates additions, removals, and reorderings
- **ListItem Composable**: Uses Material Design ListItem for consistent styling
- **Responsive Layout**: Fills parent width with horizontal padding

### Best Practices
- Always provide unique keys for each item
- Use animateItem modifier on the item content wrapper
- Customize animation specs only when default behavior is insufficient
- Ensure keys remain stable across data-set changes
```

--------------------------------

### Configure NavHost and Start Destination

Source: https://developer.android.com/develop/ui/compose/migrate/migration-scenarios/navigation

Sets up the NavHost component which links the NavController to the navigation graph and defines the initial screen.

```kotlin
@Composable
fun SampleApp() {
    val navController = rememberNavController()

    SampleNavHost(navController = navController)
}

@Composable
fun SampleNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = First) {
        // ...
    }
}
```

--------------------------------

### Basic NavigableSupportingPaneScaffold Setup in Kotlin

Source: https://developer.android.com/develop/ui/compose/layouts/adaptive/build-a-supporting-pane-layout

Demonstrates the basic initialization of a NavigableSupportingPaneScaffold using a scaffold navigator and defining the main and supporting panes.

```Kotlin
NavigableSupportingPaneScaffold(
    navigator = scaffoldNavigator,
    mainPane = { /*...*/ },
    supportingPane = { /*...*/ },
)
```

--------------------------------

### EXAMPLE Navigation Drawer Integration

Source: https://developer.android.com/develop/ui/compose/system/predictive-back-progress

An example of using PredictiveBackHandler to animate a navigation drawer's translation based on the user's back gesture progress.

```APIDOC
## EXAMPLE Navigation Drawer Integration

### Description
Demonstrates how to update the translationX of a drawer and use velocity tracking for smooth completion/cancellation based on back gesture progress.

### Method
Implementation Logic

### Endpoint
Custom Implementation

### Parameters
#### Logic Components
- **progress** (Flow<BackEventCompat>) - The stream of back events.
- **translationX** (State) - The UI state being updated by the gesture.
- **velocityTracker** (VelocityTracker) - Used to determine final animation speed.

### Request Example
PredictiveBackHandler(true) { progress ->
  try {
    progress.collect { event ->
      drawerTranslationX = calculateOffset(event.progress)
    }
  } catch (e: CancellationException) {
    resetDrawer()
  }
}

### Response
#### Success Response
- **UI Update** (Animation) - The drawer smoothly opens or closes based on gesture completion and velocity.
```

--------------------------------

### GET /spring

Source: https://developer.android.com/develop/ui/compose/animation/customize?rec=CkJodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYW5pbWF0aW9uL3Rlc3RpbmcQAxgJIAEoATANOgMzLjc

Creates a physics-based animation between start and end values using damping and stiffness parameters.

```APIDOC
## GET /spring

### Description
Creates a physics-based animation between start and end values. It handles interruptions smoothly by guaranteeing the continuity of velocity when target values change.

### Method
GET (Kotlin DSL)

### Endpoint
androidx.compose.animation.core.spring

### Parameters
#### Arguments
- **dampingRatio** (Float) - Optional - Defines how bouncy the spring should be. Default: Spring.DampingRatioNoBouncy.
- **stiffness** (Float) - Optional - Defines how fast the spring should move toward the end value. Default: Spring.StiffnessMedium.
- **visibilityThreshold** (T) - Optional - Specifies when the animation should be considered finished.

### Request Example
{
  "dampingRatio": "Spring.DampingRatioHighBouncy",
  "stiffness": "Spring.StiffnessMedium"
}

### Response
#### Success Response (200)
- **spec** (SpringSpec) - A physics-based animation specification.

#### Response Example
{
  "type": "SpringSpec",
  "dampingRatio": 0.2,
  "stiffness": 400.0
}
```

--------------------------------

### GET /animation/infinite-transition

Source: https://developer.android.com/develop/ui/compose/animation/value-based?rec=CkNodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYW5pbWF0aW9uL2FkdmFuY2VkEAEYCSABKAUwHzoDMy43

Creates an InfiniteTransition that manages animations starting immediately upon composition and running indefinitely.

```APIDOC
## GET /animation/infinite-transition

### Description
Creates an instance of InfiniteTransition which holds one or more child animations. These animations start as soon as they enter the composition and continue until they are removed.

### Method
GET

### Endpoint
rememberInfiniteTransition(label)

### Parameters
#### Query Parameters
- **label** (String) - Optional - A label used to differentiate this transition in inspection tools.

### Request Example
{
  "label": "infinite"
}

### Response
#### Success Response (200)
- **infiniteTransition** (InfiniteTransition) - The state object managing the infinite animations.

#### Response Example
{
  "status": "active",
  "type": "InfiniteTransition"
}
```

--------------------------------

### Implement Navigation UI Test with TestNavHostController

Source: https://developer.android.com/develop/ui/compose/navigation?rec=CjZodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2Uvc2V0dXAQARgJIAEoAjAPOgMzLjc

A complete test class setup using TestNavHostController to verify the start destination of an application's navigation graph.

```kotlin
class NavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            AppNavHost(navController = navController)
        }
    }

    @Test
    fun appNavHost_verifyStartDestination() {
        composeTestRule
            .onNodeWithContentDescription("Start Screen")
            .assertIsDisplayed()
    }
}
```

--------------------------------

### Switch Component - Basic Implementation

Source: https://developer.android.com/develop/ui/compose/quick-guides/content/add-toggle-switch?hl=en

Demonstrates the minimal implementation of the Switch composable with state management. This example shows how to create a basic toggleable switch that responds to user interactions.

```APIDOC
## Switch Component - Basic Implementation

### Description
Implements a minimal Switch composable with state management for toggling between checked and unchecked states.

### Dependencies
```
implementation(platform("androidx.compose:compose-bom:2026.03.00"))
```

### Version Compatibility
- **Minimum SDK**: API level 21 or higher

### Parameters
#### Required Parameters
- **checked** (Boolean) - Required - The current state of the switch (true = checked, false = unchecked)
- **onCheckedChange** (Function) - Required - Callback invoked when the switch state changes

#### Optional Parameters
- **enabled** (Boolean) - Optional - Whether the switch is enabled or disabled (default: true)
- **colors** (SwitchColors) - Optional - Custom colors for thumb and track
- **thumbContent** (Composable) - Optional - Custom composable for the thumb appearance

### Code Example
```kotlin
@Composable
fun SwitchMinimalExample() {
    var checked by remember { mutableStateOf(true) }

    Switch(
        checked = checked,
        onCheckedChange = {
            checked = it
        }
    )
}
```

### Usage Notes
- The Switch component consists of two parts: the thumb (draggable part) and the track (background)
- Users can drag the thumb left/right or tap to toggle the state
- Use for toggling settings, enabling/disabling features, or selecting options
```

--------------------------------

### Implement NavigationSuiteScaffold with Navigation Items

Source: https://developer.android.com/develop/ui/compose/layouts/adaptive/build-adaptive-navigation

This example demonstrates how to set up the `NavigationSuiteScaffold` by defining its `navigationSuiteItems` parameter. It iterates through the `AppDestinations` enum to create individual navigation items, specifying their icon, label, selection state, and click behavior.

```Kotlin
NavigationSuiteScaffold(
    navigationSuiteItems = {
        AppDestinations.entries.forEach {
            item(
                icon = {
                    Icon(
                        it.icon,
                        contentDescription = stringResource(it.contentDescription)
                    )
                },
                label = { Text(stringResource(it.label)) },
                selected = it == currentDestination,
                onClick = { currentDestination = it }
            )
        }
    }
) {
    // TODO: Destination content.
}
```

--------------------------------

### ConstraintLayout Setup and Dependencies

Source: https://developer.android.com/develop/ui/compose/layouts/constraintlayout?rec=CkJodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdG91Y2gtaW5wdXQvZm9jdXMQAhgJIAEoAzAmOgMzLjc

Instructions for adding the ConstraintLayout Compose dependency to your project and understanding the versioning requirements.

```APIDOC
## ConstraintLayout Setup

### Description
Configures the ConstraintLayout library for use in Jetpack Compose projects.

### Dependency
Add the following dependency to your `build.gradle` file (in addition to standard Compose setup):

```gradle
implementation "androidx.constraintlayout:constraintlayout-compose:$constraintlayout_compose_version"
```

### Important Notes
- The `constraintLayout-compose` artifact has different versioning than Jetpack Compose
- Check the latest version on the ConstraintLayout release page
- This dependency is separate from the standard Compose dependencies
```

--------------------------------

### GET /livedata/observeAsState

Source: https://developer.android.com/develop/ui/compose/state?rec=CkBodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdGV4dC91c2VyLWlucHV0EAIYCSABKAQwJDoDMy43

Starts observing a LiveData object and represents its values via Compose State.

```APIDOC
## GET /livedata/observeAsState

### Description
Extension function that starts observing a LiveData and transforms its values into Compose State, triggering recomposition on changes.

### Method
GET

### Endpoint
androidx.compose.runtime.livedata.observeAsState

### Parameters
#### Path Parameters
- **liveData** (LiveData<T>) - Required - The LiveData instance to observe.

### Request Example
{
  "implementation": "androidx.compose.runtime:runtime-livedata:1.10.5"
}

### Response
#### Success Response (200)
- **state** (State<T>) - A Compose State object representing the current LiveData value.
```

--------------------------------

### GET /livedata/observeAsState

Source: https://developer.android.com/develop/ui/compose/state?rec=CjpodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbGlicmFyaWVzEAIYCSABKAQwJDoDMy43

Starts observing a LiveData object and represents its values via Compose State.

```APIDOC
## GET /livedata/observeAsState

### Description
Transforms an Android LiveData stream into a Compose State object to enable reactive UI updates.

### Method
GET (Extension Function)

### Endpoint
LiveData<T>.observeAsState()

### Parameters
#### Request Body
- **initial** (T) - Optional - The initial value to use if the LiveData is empty.

### Request Example
{
  "dependency": "androidx.compose.runtime:runtime-livedata:1.10.5"
}

### Response
#### Success Response (200)
- **State<T>** (Object) - The current value of the LiveData observed as state.
```

--------------------------------

### SearchResult Composable Example

Source: https://developer.android.com/develop/ui/compose/layouts/basics?rec=CkdodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdG9vbGluZy9lZGl0b3ItYWN0aW9ucxABGAkgASgGMBY6AzMuNw

Demonstrates a practical example of the Compose layout model using a SearchResult composable with Row, Image, Column, and Text components showing how the measurement and placement cascade through the UI tree.

```APIDOC
## SearchResult Composable

### Description
A composable function that demonstrates the layout model with a Row containing an Image and a Column with two Text elements.

### Code
```kotlin
@Composable
fun SearchResult() {
    Row {
        Image(
            // ...
        )
        Column {
            Text(
                // ...
            )
            Text(
                // ...
            )
        }
    }
}
```

### Layout Measurement Order
1. Root Row node is asked to measure
2. Row asks Image (first child) to measure
3. Image (leaf node) reports size and placement
4. Row asks Column (second child) to measure
5. Column asks first Text child to measure
6. First Text (leaf node) reports size and placement
7. Column asks second Text child to measure
8. Second Text (leaf node) reports size and placement
9. Column determines its own size and placement
10. Row determines its own size and placement

### Components
- **Row**: Container that arranges children horizontally
- **Image**: Leaf node displaying an image
- **Column**: Container that arranges children vertically
- **Text**: Leaf nodes displaying text content
```

--------------------------------

### Initialize Compose Entry Point in ComponentActivity

Source: https://developer.android.com/develop/ui/compose/migrate/migration-scenarios/navigation

Replaces the traditional View-based layout setup (setContentView) with the Compose setContent block to host the main app composable.

```kotlin
class SampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView<ActivitySampleBinding>(this, R.layout.activity_sample)
        setContent {
            SampleApp(/* ... */)
        }
    }
}
```

--------------------------------

### Setup MainActivity, Data Class, and Basic Composable Preview

Source: https://developer.android.com/develop/ui/compose/tutorial

This code sets up the main activity to display a MessageCard Composable. It defines a data class Message to hold message content and includes a basic MessageCard Composable for displaying text. A @Preview Composable is also provided to enable design-time preview of the MessageCard.

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MessageCard(Message("Android", "Jetpack Compose"))
        }
    }
}

data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: Message) {
    Text(text = msg.author)
    Text(text = msg.body)
}

@Preview
@Composable
fun PreviewMessageCard() {
    MessageCard(
        msg = Message("Lexi", "Hey, take a look at Jetpack Compose, it's great!")
    )
}
```

--------------------------------

### Migrate Fragment UI to Composable Destinations in NavHost

Source: https://developer.android.com/develop/ui/compose/migrate/migration-scenarios/navigation?rec=CjtodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbmF2aWdhdGlvbhACGAkgASgLMBg6AzMuNw

This example illustrates the process of migrating UI from a Fragment's `ComposeView` to directly defining composable screens within the `NavHost`. It shows how to extract screen composables and integrate them into the navigation graph.

```kotlin
class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                // FirstScreen(...) 
            }
        }
    }
}

@Composable
fun SampleNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = First) {
        composable<First> {
            FirstScreen(/* ... */) 
        }
        composable<Second> {
            SecondScreen(/* ... */)
        }
        // ...
    }
}
```

--------------------------------

### GET /rememberInfiniteTransition

Source: https://developer.android.com/develop/ui/compose/animation/value-based?rec=CkpodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdG9vbGluZy9hbmltYXRpb24tcHJldmlldxADGAkgASgCMBs6AzMuNw

Creates an InfiniteTransition that manages child animations which start immediately and run indefinitely.

```APIDOC
## GET /rememberInfiniteTransition

### Description
Creates an instance of InfiniteTransition that holds one or more child animations. These animations start running as soon as they enter the composition and do not stop unless they are removed.

### Method
GET

### Endpoint
/rememberInfiniteTransition

### Parameters
#### Query Parameters
- **label** (String) - Required - A label used to identify the animation in the Animation Inspector.

### Request Example
val infiniteTransition = rememberInfiniteTransition(label = "infinite")

### Response
#### Success Response (200)
- **InfiniteTransition** (Object) - A state object that can be used to create child animations like animateColor or animateFloat.
```

--------------------------------

### Activity Result API - rememberLauncherForActivityResult()

Source: https://developer.android.com/develop/ui/compose/libraries?rec=CldodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbWlncmF0ZS9taWdyYXRpb24tc2NlbmFyaW9zL25hdmlnYXRpb24QAhgJIAEoBDAbOgMzLjc

The rememberLauncherForActivityResult() API allows you to get a result from an activity in your composable. This example demonstrates using the GetContent() contract to load an image from the device.

```APIDOC
## GET Activity Result

### Description
Use `rememberLauncherForActivityResult()` to retrieve results from activities within composables. Supports any `ActivityResultContract` subclass.

### Method
Composable Function

### Function Signature
```
@Composable
fun rememberLauncherForActivityResult(
    contract: ActivityResultContract<I, O>,
    onResult: (O) -> Unit
): ActivityResultLauncher<I>
```

### Parameters
- **contract** (ActivityResultContract) - Required - The contract defining the request and result types (e.g., GetContent, RequestPermission)
- **onResult** (lambda) - Required - Callback invoked when the activity returns with a result

### Request Example
```kotlin
@Composable
fun GetContentExample() {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }
    Column {
        Button(onClick = { launcher.launch("image/*") }) {
            Text(text = "Load Image")
        }
        Image(
            painter = rememberAsyncImagePainter(imageUri),
            contentDescription = "My Image"
        )
    }
}
```

### Response
- **Result** (Generic type O) - The result returned by the activity contract

### Important Notes
- Launch requests only from user interactions (e.g., onClick listeners)
- Do NOT launch from inside the composable body - causes runtime error
- Use `LaunchedEffect` or `DisposableEffect` for post-composition launches
- Supports custom `ActivityResultContract` implementations

### Error Handling
- **Runtime Error**: Launching outside of user interaction or effect blocks
- **Null Result**: User cancels the activity without selecting content
```

--------------------------------

### Start animation immediately with MutableTransitionState in Compose

Source: https://developer.android.com/develop/ui/compose/animation/value-based?rec=CkNodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYW5pbWF0aW9uL2FkdmFuY2VkEAEYCSABKAIwGDoDMy43

This code shows how to use `MutableTransitionState` to define an initial state different from the first target state. This is useful for starting an animation as soon as the composable enters the composition, for example, animating from a collapsed to an expanded state immediately upon display.

```kotlin
var currentState = remember { MutableTransitionState(BoxState.Collapsed) }
currentState.targetState = BoxState.Expanded
val transition = rememberTransition(currentState, label = "box state")
```

--------------------------------

### Implement a Basic Scaffold with App Bars and FAB

Source: https://developer.android.com/develop/ui/compose/quick-guides/content/create-scaffold?hl=en

A complete example of a Scaffold containing a TopAppBar, BottomAppBar, and a FloatingActionButton. It demonstrates using the innerPadding parameter to correctly position the main content and managing internal state for a counter.

```Kotlin
@Composable
fun ScaffoldExample() {
	var presses by remember { mutableIntStateOf(0) }

	Scaffold(
		topBar = {
			TopAppBar(
				colors = topAppBarColors(
					containerColor = MaterialTheme.colorScheme.primaryContainer,
					titleContentColor = MaterialTheme.colorScheme.primary,
				),
				title = {
					Text("Top app bar")
				}
			)
		},
		bottomBar = {
			BottomAppBar(
				containerColor = MaterialTheme.colorScheme.primaryContainer,
				contentColor = MaterialTheme.colorScheme.primary,
			) {
				Text(
					modifier = Modifier
						.fillMaxWidth(),
					textAlign = TextAlign.Center,
					text = "Bottom app bar",
				)
			}
		},
		floatingActionButton = {
			FloatingActionButton(onClick = { presses++ }) {
				Icon(Icons.Default.Add, contentDescription = "Add")
			}
		}
	) { innerPadding ->
		Column(
			modifier = Modifier
				.padding(innerPadding),
			verticalArrangement = Arrangement.spacedBy(16.dp),
		) {
			Text(
				modifier = Modifier.padding(8.dp),
				text = """
					This is an example of a scaffold. It uses the Scaffold composable's parameters to create a screen with a simple top app bar, bottom app bar, and floating action button.

					It also contains some basic inner content, such as this text.

					You have pressed the floating action button $presses times.
				""".trimIndent(),
			)
		}
	}
}
```

--------------------------------

### Apply custom Indication and InteractionSource to a Composable in Compose

Source: https://developer.android.com/develop/ui/compose/touch-input/focus/react-to-focus

This example shows how to integrate a custom `Indication` (like `MyHighlightIndication`) and a `MutableInteractionSource` with a `clickable` modifier. This setup enables a Composable to display the custom focus visual cues defined previously.

```kotlin
var interactionSource = remember { MutableInteractionSource() }

Card(
    modifier = Modifier
        .clickable(
            interactionSource = interactionSource,
            indication = MyHighlightIndication,
            enabled = true,
            onClick = { }
        )
) {
    Text("hello")
}
```

--------------------------------

### Implement an AssistChip with leading icon

Source: https://developer.android.com/develop/ui/compose/quick-guides/content/create-chip

The AssistChip provides a way to nudge users toward a task. This example demonstrates using a leading icon and handling click events.

```Kotlin
@Composable
fun AssistChipExample() {
	AssistChip(
		onClick = { Log.d("Assist chip", "hello world") },
		label = { Text("Assist chip") },
		leadingIcon = {
			Icon(
				Icons.Filled.Settings,
				contentDescription = "Localized description",
				Modifier.size(AssistChipDefaults.IconSize)
			)
		}
	)
}
```

--------------------------------

### GET /focusable

Source: https://developer.android.com/develop/ui/compose/touch-input/focus/change-focus-behavior

Makes the

--------------------------------

### Check WindowAreaCapability Status Before Starting Operation

Source: https://developer.android.com/develop/ui/compose/layouts/adaptive/foldables/support-foldable-display-modes

Verify the capability status before attempting to start a display mode operation. This when expression handles four status states: UNSUPPORTED (not available on device), UNAVAILABLE (temporarily unavailable), AVAILABLE (ready to enable), and ACTIVE (already enabled). Check the status to determine appropriate user feedback or next actions.

```Kotlin
when (capabilityStatus) {
    WindowAreaCapability.Status.WINDOW_AREA_STATUS_UNSUPPORTED -> {
      // The selected display mode is not supported on this device.
    }
    WindowAreaCapability.Status.WINDOW_AREA_STATUS_UNAVAILABLE -> {
      // The selected display mode is not available.
    }
    WindowAreaCapability.Status.WINDOW_AREA_STATUS_AVAILABLE -> {
      // The selected display mode is available and can be enabled.
    }
    WindowAreaCapability.Status.WINDOW_AREA_STATUS_ACTIVE -> {
      // The selected display mode is already active.
    }
    else -> {
    }
}
```

--------------------------------

### Semantics Tree Log Output Example

Source: https://developer.android.com/develop/ui/compose/testing/debug

An example of the log output generated by the printToLog function. It displays node indices, layout bounds, and semantic attributes like OnClick actions and Text values.

```text
Node #1 at (...)px
 |-Node #2 at (...)px
   OnClick = '...'
   MergeDescendants = 'true'
    |-Node #3 at (...)px
    | Text = 'Hi'
    |-Node #5 at (83.0, 86.0, 191.0, 135.0)px
      Text = 'There'
```

--------------------------------

### GET /tween

Source: https://developer.android.com/develop/ui/compose/animation/customize?rec=CkJodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYW5pbWF0aW9uL3Rlc3RpbmcQAxgJIAEoATANOgMzLjc

Animates between start and end values over a specified duration using an easing curve.

```APIDOC
## GET /tween

### Description
Animates between start and end values over the specified durationMillis using an easing curve. Short for 'between'.

### Method
GET (Kotlin DSL)

### Endpoint
androidx.compose.animation.core.tween

### Parameters
#### Arguments
- **durationMillis** (Int) - Optional - The duration of the animation in milliseconds.
- **delayMillis** (Int) - Optional - The amount of time to postpone the start of the animation.
- **easing** (Easing) - Optional - The easing curve to use for the animation.

### Request Example
{
  "durationMillis": 300,
  "delayMillis": 50,
  "easing": "LinearOutSlowInEasing"
}

### Response
#### Success Response (200)
- **spec** (TweenSpec) - A duration-based animation specification.

#### Response Example
{
  "type": "TweenSpec",
  "durationMillis": 300,
  "delayMillis": 50,
  "easing": "CubicBezierEasing"
}
```

--------------------------------

### rememberSaveable TextField Example

Source: https://developer.android.com/develop/ui/compose/state?rec=CkpodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvcGVyZm9ybWFuY2UvYmVzdHByYWN0aWNlcxABGAkgASgEMCQ6AzMuNw

Practical example demonstrating how to use rememberSaveable to store TextField state with TextFieldValue.Saver, ensuring the user's typed query persists across recomposition and activity recreation.

```APIDOC
## rememberSaveable with TextField

### Description
Example implementation showing how to use `rememberSaveable` to store and manage TextField state, including text content and cursor selection position.

### Use Case
Persist user input in a TextField across recomposition, configuration changes, and process death.

### Code Example
```kotlin
var userTypedQuery by rememberSaveable(
    typedQuery,
    stateSaver = TextFieldValue.Saver
) {
    mutableStateOf(
        TextFieldValue(
            text = typedQuery,
            selection = TextRange(typedQuery.length)
        )
    )
}
```

### Parameters Explained
- **typedQuery** (String) - Input parameter that triggers cache invalidation when changed
- **stateSaver** (Saver) - Uses `TextFieldValue.Saver` to serialize the TextField state into the Bundle
- **init** (lambda) - Creates a `mutableStateOf` with:
  - **text**: The initial query string
  - **selection**: TextRange positioned at the end of the text (cursor position)

### State Behavior
- When `typedQuery` changes, the cached `userTypedQuery` is invalidated
- The TextField state (text and cursor position) persists across recomposition
- State survives activity recreation and system process death

### Related File
- **StateOverviewSnippets.kt** - Contains this code example
```

--------------------------------

### Gesture Modifiers for Arbitrary Composables

Source: https://developer.android.com/develop/ui/compose/touch-input/pointer-input/understand-gestures?rec=CltodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdG91Y2gtaW5wdXQvc2Nyb2xsL25lc3RlZC1zY3JvbGwtbW9kaWZpZXJzEAEYCSABKAIwGzoDMy43

Guide to applying gesture modifiers to any composable to handle specific gesture types. Covers tap, scroll, drag, and multi-touch gesture modifiers with examples of common use cases.

```APIDOC
## Gesture Modifiers

### Description
Apply gesture modifiers to arbitrary composables to add gesture handling capabilities. These modifiers provide enhanced functionality beyond raw pointer events, including semantic information and visual feedback.

### Tap and Press Modifiers
- **clickable** - Handle single taps and presses
- **combinedClickable** - Handle multiple click types (single, double, long-press)
- **selectable** - Handle selection gestures
- **toggleable** - Handle toggle state changes
- **triStateToggleable** - Handle three-state toggle interactions

### Scroll Modifiers
- **horizontalScroll** - Enable horizontal scrolling
- **verticalScroll** - Enable vertical scrolling
- **scrollable** - Generic scrolling modifier for custom scroll behavior

### Drag and Swipe Modifiers
- **draggable** - Handle dragging gestures
- **swipeable** - Handle swiping gestures

### Multi-Touch Modifiers
- **transformable** - Handle panning, rotating, and zooming gestures

### Usage Example
```kotlin
Box(Modifier.clickable { /* Handle tap */ }) { 
    Text("Tap me!") 
}

Column(Modifier.verticalScroll(rememberScrollState())) {
    // Scrollable content
}
```

### Advantages Over Raw Pointer Events
- Automatic semantic information
- Visual indications on interactions
- Hovering and focus support
- Keyboard support
- Well-tested implementations
```

--------------------------------

### Configure Balanced LineBreak Strategy for Small Screens

Source: https://developer.android.com/develop/ui/compose/text/style-paragraph?rec=CkBodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdGV4dC9zdHlsZS10ZXh0EAEYCSABKAIwGToDMy43

Demonstrates how to create a custom LineBreak configuration using the Balanced strategy to optimize text display on small screens like watches. The example shows a Text composable with a 200.dp width constraint and compares it with default line breaking behavior.

```Kotlin
TextSample(
    samples = mapOf(
        "Balanced" to {
            val smallScreenAdaptedParagraph =
                LineBreak.Paragraph.copy(strategy = LineBreak.Strategy.Balanced)
            Text(
                text = SAMPLE_LONG_TEXT,
                modifier = Modifier
                    .width(200.dp)
                    .border(BorderStroke(1.dp, Color.Gray)),
                fontSize = 14.sp,
                style = TextStyle.Default.copy(
                    lineBreak = smallScreenAdaptedParagraph
                )
            )
        },
        "Default" to {
            Text(
                text = SAMPLE_LONG_TEXT,
                modifier = Modifier
                    .width(200.dp)
                    .border(BorderStroke(1.dp, Color.Gray)),
                fontSize = 14.sp,
                style = TextStyle.Default
            )
        }
    )
)
```

--------------------------------

### Switch Component - Key Parameters Reference

Source: https://developer.android.com/develop/ui/compose/quick-guides/content/add-toggle-switch?hl=en

Complete reference guide for all Switch component parameters, including basic and advanced options for controlling behavior, appearance, and customization.

```APIDOC
## Switch Component - Key Parameters Reference

### Description
Complete reference of all available parameters for the Switch composable component.

### Basic Parameters
- **checked** (Boolean) - Required - The initial state of the switch (true = checked, false = unchecked)
- **onCheckedChange** (Function: (Boolean) -> Unit) - Required - Callback invoked when the switch state changes, receives the new state as parameter
- **enabled** (Boolean) - Optional - Whether the switch is enabled or disabled (default: true). Disabled switches cannot be interacted with
- **colors** (SwitchColors) - Optional - The colors used for the switch thumb and track

### Advanced Parameters
- **thumbContent** (Composable?) - Optional - Custom composable content displayed inside the thumb. Use this to add icons or other visual elements. Set to null to use default thumb appearance
- **modifier** (Modifier) - Optional - Modifier for styling and layout customization

### Component Structure
- **Thumb**: The draggable circular part of the switch that moves left/right
- **Track**: The background rectangular area behind the thumb

### Interaction Methods
- Users can drag the thumb left or right to toggle the state
- Users can tap the switch to toggle between checked and unchecked
- Disabled switches do not respond to user interactions

### Use Cases
- Toggle a setting on or off
- Enable or disable a feature
- Select between two options
- Preference controls in settings screens
```

--------------------------------

### Create Small Top App Bar with Scaffold in Jetpack Compose

Source: https://developer.android.com/develop/ui/compose/components/app-bars

Implements a basic small top app bar using the TopAppBar composable within a Scaffold. The app bar displays a title with Material Design colors and does not respond to scrolling. This example demonstrates the minimal setup required for a non-scrollable top app bar with custom theming.

```Kotlin
@Composable
fun SmallTopAppBarExample() {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Small Top App Bar")
                }
            )
        },
    ) { innerPadding ->
        ScrollContent(innerPadding)
    }
}
```

--------------------------------

### Create Linear Determinate Progress Indicator with Coroutine

Source: https://developer.android.com/develop/ui/compose/quick-guides/content/create-progress-indicator

Implement a linear progress indicator that displays exact completion percentage. Uses a coroutine scope to update progress values without blocking the UI thread. The example includes a button to start loading and displays the progress bar only when loading is active.

```Kotlin
@Composable
fun LinearDeterminateIndicator() {
    var currentProgress by remember { mutableFloatStateOf(0f) }
    var loading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope() // Create a coroutine scope

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(onClick = {
            loading = true
            scope.launch {
                loadProgress { progress ->
                    currentProgress = progress
                }
                loading = false // Reset loading when the coroutine finishes
            }
        }, enabled = !loading) {
            Text("Start loading")
        }

        if (loading) {
            LinearProgressIndicator(
                progress = { currentProgress },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
```

--------------------------------

### Create a Basic Compose UI Test with ComposeTestRule

Source: https://developer.android.com/develop/ui/compose/testing?rec=CkJodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYW5pbWF0aW9uL3Rlc3RpbmcQARgJIAEoBDAYOgMzLjc

This example demonstrates a typical UI test structure for Jetpack Compose. It uses `createComposeRule()` to set up the testing environment, sets Compose content, performs a click action on a node with specific text, and then asserts that another node is displayed.

```Kotlin
class MyComposeTest {

    @get:Rule val composeTestRule = createComposeRule()
    // use createAndroidComposeRule<YourActivity>() if you need access to
    // an activity

    @Test
    fun myTest() {
        // Start the app
        composeTestRule.setContent {
            MyAppTheme {
                MainScreen(uiState = fakeUiState, /*...*/)
            }
        }

        composeTestRule.onNodeWithText("Continue").performClick()

        composeTestRule.onNodeWithText("Welcome").assertIsDisplayed()
    }
}
```

--------------------------------

### GET /layout/guidelines

Source: https://developer.android.com/develop/ui/compose/layouts/constraintlayout?rec=CjRodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYm9tEAIYCSABKAMwJjoDMy43

Creates visual helpers (guidelines) at specific dp or percentage offsets within a ConstraintLayout for positioning elements.

```APIDOC
## GET /layout/guidelines

### Description
Creates visual helpers for positioning elements at specific dp or percentage offsets inside the parent composable.

### Method
GET

### Endpoint
/layout/guidelines

### Parameters
#### Query Parameters
- **offset** (Float/Dp) - Required - The position of the guideline (percentage as float or fixed dp).
- **orientation** (String) - Required - The side to create the guideline from (start, end, top, bottom).

### Request Example
{
  "offset": 0.1,
  "orientation": "start"
}

### Response
#### Success Response (200)
- **guideline** (Reference) - A reference that can be used in the Modifier.constrainAs() block.

#### Response Example
{
  "guidelineId": "startGuideline",
  "type": "vertical"
}
```

--------------------------------

### Basic Modal Navigation Drawer Setup

Source: https://developer.android.com/develop/ui/compose/components/drawer

Demonstrates how to create a basic modal navigation drawer with drawer content and screen content. The ModalNavigationDrawer composable provides a slide-in menu that appears over other content with NavigationDrawerItem components for menu items.

```APIDOC
## ModalNavigationDrawer - Basic Implementation

### Description
Creates a modal navigation drawer that slides in from the side, appearing over screen content. Provides a drawerContent slot for menu items and a main content area.

### Component
ModalNavigationDrawer

### Parameters
- **drawerContent** (Composable) - Required - Lambda that defines the drawer's content, typically containing ModalDrawerSheet and NavigationDrawerItem components
- **content** (Composable) - Required - Lambda that defines the main screen content displayed behind the drawer

### Code Example
```kotlin
ModalNavigationDrawer(
    drawerContent = {
        ModalDrawerSheet {
            Text("Drawer title", modifier = Modifier.padding(16.dp))
            HorizontalDivider()
            NavigationDrawerItem(
                label = { Text(text = "Drawer Item") },
                selected = false,
                onClick = { /*TODO*/ }
            )
            // ...other drawer items
        }
    }
) {
    // Screen content
}
```

### Key Components
- **ModalDrawerSheet** - Container for drawer content
- **NavigationDrawerItem** - Individual menu item with label, selection state, and click handler
- **HorizontalDivider** - Visual separator between drawer sections
```

--------------------------------

### Start Compose Transition with an initial state using MutableTransitionState

Source: https://developer.android.com/develop/ui/compose/animation/value-based?rec=CkJodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYW5pbWF0aW9uL3Rvb2xpbmcQARgJIAEoATAMOgMzLjc

This code shows how to use `MutableTransitionState` with `rememberTransition` to define an initial state that differs from the first target state. This is useful for immediately starting an animation as soon as a composable enters the composition.

```kotlin
// Start in collapsed state and immediately animate to expanded
var currentState = remember { MutableTransitionState(BoxState.Collapsed) }
currentState.targetState = BoxState.Expanded
val transition = rememberTransition(currentState, label = "box state")
```

--------------------------------

### Basic ConstraintLayout implementation in Compose

Source: https://developer.android.com/develop/ui/compose/layouts/constraintlayout

This example demonstrates how to create references for composables using createRefs() and apply constraints using the constrainAs modifier to position a Button and Text relative to each other and the parent.

```Kotlin
@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout {
        // Create references for the composables to constrain
        val (button, text) = createRefs()

        Button(
            onClick = { /* Do something */ },
            // Assign reference "button" to the Button composable
            // and constrain it to the top of the ConstraintLayout
            modifier = Modifier.constrainAs(button) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text("Button")
        }

        // Assign reference "text" to the Text composable
        // and constrain it to the bottom of the Button composable
        Text(
            "Text",
            Modifier.constrainAs(text) {
                top.linkTo(button.bottom, margin = 16.dp)
            }
        )
    }
}
```

--------------------------------

### Initialize Compose UI in MainActivity

Source: https://developer.android.com/develop/ui/compose/tutorial?hl=de

Shows how to set up the main activity to display the Compose UI hierarchy. The onCreate method uses setContent to apply the theme and display the Conversation composable with sample data. This is the entry point for the Compose-based UI.

```Kotlin
class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
       setContent {
           ComposeTutorialTheme {
               Conversation(SampleData.conversationSample)
           }
       }
   }
}
```

--------------------------------

### SavedStateHandle API - Compose State

Source: https://developer.android.com/develop/ui/compose/state-saving

Demonstrates using SavedStateHandle.saveable() API to store and restore UI element state as MutableState across activity and process recreation with minimal code setup. Includes example of storing TextField input.

```APIDOC
## SavedStateHandle.saveable() - Compose State

### Description
Use the saveable API of SavedStateHandle to read and write UI element state as MutableState, enabling state survival across activity and process recreation.

### Method
Compose State API

### API Signature
```kotlin
var state by savedStateHandle.saveable(stateSaver = CustomSaver) {
    mutableStateOf(initialValue)
}
```

### Parameters
- **stateSaver** (Saver) - Optional - Custom saver for non-primitive types (e.g., TextFieldValue.Saver)
- **initialValue** - Required - Lambda returning initial MutableState value

### Features
- **Primitive Type Support**: Supports primitive types out of the box
- **Custom Savers**: Accepts stateSaver parameter for custom type serialization
- **Minimal Setup**: Requires minimal code compared to manual state management

### Request Example
```kotlin
class ConversationViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var message by savedStateHandle.saveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
        private set

    fun update(newMessage: TextFieldValue) {
        message = newMessage
    }
}
```

### Response Example
```kotlin
val viewModel = ConversationViewModel(SavedStateHandle())

@Composable
fun UserInput() {
    TextField(
        value = viewModel.message,
        onValueChange = { viewModel.update(it) }
    )
}
```

### Use Cases
- Storing TextField input values
- Preserving user selections
- Maintaining form state across configuration changes
- Restoring transient UI state after process recreation
```

--------------------------------

### Skippable and Restartable Composable Example

Source: https://developer.android.com/develop/ui/compose/performance/stability/diagnose

Example of a composable function marked as both restartable and skippable with stable parameters. This is the preferred state as it allows Compose to skip recomposition when parameters haven't changed, improving performance.

```Kotlin
restartable skippable scheme("[androidx.compose.ui.UiComposable]") fun SnackCollection(
  stable snackCollection: SnackCollection
  stable onSnackClick: Function1<Long, Unit>
  stable modifier: Modifier? = @static Companion
  stable index: Int = @static 0
  stable highlight: Boolean = @static true
)
```

--------------------------------

### Using retain to Manage ExoPlayer in Compose

Source: https://developer.android.com/develop/ui/compose/state-lifespans

Demonstrates how to use the `retain` API to manage a media player instance (ExoPlayer) across configuration changes without interrupting playback. The example uses the application context to avoid memory leaks and shows the basic pattern for retaining non-serializable objects in a composable function.

```Kotlin
@Composable
fun MediaPlayer() {
    // Use the application context to avoid a memory leak
    val applicationContext = LocalContext.current.applicationContext
    val exoPlayer = retain { ExoPlayer.Builder(applicationContext).apply { /* ... */ }.build() }
    // ...
}
```

--------------------------------

### Small Top App Bar Implementation

Source: https://developer.android.com/develop/ui/compose/components/app-bars

This example demonstrates how to create a basic small top app bar using the `TopAppBar` composable with a title and custom colors. It does not include scroll behavior, meaning it remains static during scrolling.

```APIDOC
## Composable TopAppBar (Small)

### Description
This composable creates a small top app bar, typically used for screens that do not require extensive navigation or actions. It provides a container for a title and can be customized with colors.

### Method
Composable

### Endpoint
`TopAppBar`

### Parameters
#### Request Body (Composable Parameters)
- **colors** (`TopAppBarColors`) - Optional - Defines the colors used for the app bar's container and title content.
- **title** (`@Composable () -> Unit`) - Required - The content displayed as the title of the app bar.

### Request Example
```kotlin
@Composable
fun SmallTopAppBarExample() {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Small Top App Bar")
                }
            )
        },
    ) { innerPadding ->
        ScrollContent(innerPadding)
    }
}
```

### Response
#### Success Response (UI Render)
- **UI Component** (Composable) - A rendered small top app bar with the specified title and colors.

#### Response Example
(No direct JSON response, but a visual UI component is rendered)
```
// Visual representation of a small top app bar with "Small Top App Bar" title
// and primaryContainer background, primary title color.
```
```

--------------------------------

### Compose Compiler Stability Analysis Guide

Source: https://developer.android.com/develop/ui/compose/performance/stability/diagnose

Comprehensive guide explaining how the Jetpack Compose compiler determines class stability, why collection interfaces are marked as unstable, and best practices for ensuring stable types in Compose applications.

```APIDOC
## Jetpack Compose Compiler Stability Analysis

### Description
Guide to understanding how the Jetpack Compose compiler analyzes and marks classes as stable or unstable, with focus on collection types and immutability guarantees.

### Stability Determination Rules

#### Stable Types
The following types are considered stable by the Compose compiler:
- Primitive types (Int, Long, Float, Double, Boolean, Char, Byte, Short)
- String (immutable)
- Immutable value types with stable properties
- Concrete immutable collection implementations

#### Unstable Types
The following types are marked as unstable:
- Mutable collection interfaces: Set, List, Map
- Mutable collection implementations: MutableSet, MutableList, MutableMap
- Classes with mutable fields
- Generic types with unstable type parameters

### Collection Interface Instability

#### Problem
Standard collection interfaces (Set, List, Map) are conceptually immutable but may have mutable implementations:

```kotlin
// Declared as immutable Set, but implementation is mutable
val set: Set<String> = mutableSetOf("foo")

// Declared as immutable List, but implementation is mutable
val list: List<String> = mutableListOf("bar")
```

#### Why This Matters
The Compose compiler only sees the declared type, not the runtime implementation. Since it cannot guarantee immutability, it marks such fields as unstable, which may trigger unnecessary recompositions.

### Best Practices

#### Use Concrete Immutable Types
- Replace `Set<T>` with `ImmutableSet<T>` from kotlinx.collections.immutable
- Replace `List<T>` with `ImmutableList<T>` from kotlinx.collections.immutable
- Replace `Map<K, V>` with `ImmutableMap<K, V>` from kotlinx.collections.immutable

#### Example: Stable Snack Class
```kotlin
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.immutableSetOf

data class Snack(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val price: Long,
    val tagline: String = "",
    val tags: ImmutableSet<String> = immutableSetOf()
)
```

#### Accessing Stability Reports
Stability reports are generated in the `classes.txt` file within the module's build output, providing detailed analysis of each class's stability status.
```

--------------------------------

### Basic Window Insets Setup with safeDrawingPadding

Source: https://developer.android.com/develop/ui/compose/system/insets-ui

Demonstrates the most basic method of applying window insets to the content of an entire app using the safeDrawingPadding modifier. This ensures interactable elements don't overlap with system UI but prevents the app from drawing behind the system UI.

```APIDOC
## Window Insets Setup

### Description
Apply safe drawing window insets as padding around the entire content of the app to prevent content from being obscured by system UI elements.

### Implementation
```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
        Box(Modifier.safeDrawingPadding()) {
            // the rest of the app
        }
    }
}
```

### Key Points
- Uses `enableEdgeToEdge()` to enable edge-to-edge rendering
- `Modifier.safeDrawingPadding()` applies safe drawing insets as padding on all 4 sides
- Ensures interactable elements don't overlap with system UI
- Prevents app content from drawing behind system UI
- For edge-to-edge effects, fine-tune inset application on a screen-by-screen basis
```

--------------------------------

### Implementing PreviewParameterProvider

Source: https://developer.android.com/develop/ui/compose/tooling/previews

Create a class that implements PreviewParameterProvider to supply sample data as a sequence for @PreviewParameter annotated composables.

```APIDOC
## Implementing `PreviewParameterProvider`

### Description
To provide the sample data for `@PreviewParameter`, you must create a class that implements `PreviewParameterProvider<T>` (where `T` is the data type) and overrides the `values` property to return a `Sequence` of your sample data.

### Code Example
```kotlin
class UserPreviewParameterProvider : PreviewParameterProvider<User> {
    override val values = sequenceOf(
        User("Elise"),
        User("Frank"),
        User("Julia")
    )
}
```
```

--------------------------------

### Compare CompositingStrategy with Canvas Clipping Behavior

Source: https://developer.android.com/develop/ui/compose/graphics/draw/modifiers?rec=CkBodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdGV4dC9zdHlsZS10ZXh0EAMYCSABKAIwIDoDMy43

Illustrates the difference between default graphicsLayer behavior and offscreen compositing strategy. The first Canvas example shows that default graphicsLayer does not clip content outside bounds, while the second example demonstrates how alpha usage creates an offscreen buffer that clips content to the specified region.

```Kotlin
@Composable
fun CompositingStrategyExamples() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Canvas(
            modifier = Modifier
                .graphicsLayer()
                .size(100.dp)
                .border(2.dp, color = Color.Blue)
        ) {
            drawRect(color = Color.Magenta, size = Size(200.dp.toPx(), 200.dp.toPx()))
        }

        Spacer(modifier = Modifier.size(300.dp))

        Canvas(
            modifier = Modifier
        ) {
        }
    }
}
```

--------------------------------

### Migrate Simple Screen from XML to Jetpack Compose

Source: https://developer.android.com/develop/ui/compose/migrate/strategy

This example demonstrates the conversion of a vertical layout containing text elements and a button from XML to a Compose Column. It highlights the mapping of layout weights, string resources, and Material Design typography styles.

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

  <TextView
      android:id="@+id/title_text"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:text="@string/title"
      android:textAppearance="?attr/textAppearanceHeadline2" />

  <TextView
      android:id="@+id/subtitle_text"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:text="@string/subtitle"
      android:textAppearance="?attr/textAppearanceHeadline6" />

  <TextView
      android:id="@+id/body_text"
      android:layout_width="wrap_content"
      android:layout_height="0dp"
      android:layout_weight="1"
      android:text="@string/body"
      android:textAppearance="?attr/textAppearanceBody1" />

  <Button
      android:id="@+id/confirm_button"
      android:layout_width="match_parent"
      android:layout_height="wrap_content"
      android:text="@string/confirm"/>
</LinearLayout>
```

```kotlin
@Composable
fun SimpleScreen() {
    Column(Modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.title),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = stringResource(R.string.subtitle),
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = stringResource(R.string.body),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = { /* Handle click */ }, Modifier.fillMaxWidth()) {
            Text(text = stringResource(R.string.confirm))
        }
    }
}
```

--------------------------------

### CALL /rememberLauncherForActivityResult

Source: https://developer.android.com/develop/ui/compose/libraries

Registers a request to start an activity for result, returning a launcher that survives recomposition and handles cleanup automatically.

```APIDOC
## CALL /rememberLauncherForActivityResult

### Description
Registers a request to start an activity for result, returning a launcher that can be used to start the process. This API ensures that the launcher is only initialized once and survives recomposition.

### Method
@Composable

### Endpoint
rememberLauncherForActivityResult(contract, onResult)

### Parameters
#### Arguments
- **contract** (ActivityResultContract) - Required - The contract defining the input and output types for the activity.
- **onResult** (Lambda) - Required - Callback invoked once the user returns to the launching activity with a result.

### Request Example
{
  "contract": "ActivityResultContracts.GetContent()",
  "onResult": "{ uri: Uri? -> imageUri = uri }"
}

### Response
#### Success Response
- **launcher** (ActivityResultLauncher) - An object used to trigger the activity request via the .launch() method.

### Response Example
{
  "launcher": "ActivityResultLauncher<String>"
}
```

--------------------------------

### Demonstrate Modifier Order Significance in Kotlin

Source: https://developer.android.com/develop/ui/compose/modifiers?rec=Cj9odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbGF5b3V0cy9iYXNpY3MQARgJIAEoAjAoOgMzLjc

Illustrates how the sequence of modifier functions affects the final UI result. The first example makes the padded area clickable, while the second example restricts clickability to the content inside the padding.

```kotlin
@Composable
fun ArtistCard(/*...*/) {
    val padding = 16.dp
    Column(
        Modifier
            .clickable(onClick = onClick)
            .padding(padding)
            .fillMaxWidth()
    ) {
        // rest of the implementation
    }
}
```

```kotlin
@Composable
fun ArtistCard(/*...*/) {
    val padding = 16.dp
    Column(
        Modifier
            .padding(padding)
            .clickable(onClick = onClick)
            .fillMaxWidth()
    ) {
        // rest of the implementation
    }
}
```

--------------------------------

### Example DiagnosticComposeException Stack Trace Output

Source: https://developer.android.com/develop/ui/compose/tooling/stacktraces

This example shows the format of a DiagnosticComposeException when ComposeStackTraceMode.SourceInformation is active. It illustrates how the composition stack is appended to the suppressed exception list, providing detailed context for crashes within composables.

```plaintext
java.lang.IllegalStateException: Test layout error
    at <original trace>
Suppressed: androidx.compose.runtime.DiagnosticComposeException:
Composition stack when thrown:
    at ReusableComposeNode(Composables.kt:<unknown line>)
    at Layout(Layout.kt:79)
    at <lambda>(TempErrorsTest.kt:164)
    at <lambda>(BoxWithConstraints.kt:66)
    at ReusableContentHost(Composables.kt:164)
    at <lambda>(SubcomposeLayout.kt:514)
    at SubcomposeLayout(SubcomposeLayout.kt:114)
    at SubcomposeLayout(SubcomposeLayout.kt:80)
    at BoxWithConstraints(BoxWithConstraints.kt:64)
    at SubcomposeLayoutErrorComposable(TempErrorsTest.kt:164)
    at <lambda>(TempErrorsTest.kt:86)
    at Content(ComposeView.android.kt:430)
    at <lambda>(ComposeView.android.kt:249)
    at CompositionLocalProvider(CompositionLocal.kt:364)
    at ProvideCommonCompositionLocals(CompositionLocals.kt:193)
    at <lambda>(AndroidCompositionLocals.android.kt:113)
    at CompositionLocalProvider(CompositionLocal.kt:364)
    at ProvideAndroidCompositionLocals(AndroidCompositionLocals.android.kt:102)
    at <lambda>(Wrapper.android.kt:141)
    at CompositionLocalProvider(CompositionLocal.kt:384)
    at <lambda>(Wrapper.android.kt:140)
```

--------------------------------

### Material Design 2 Color System Setup

Source: https://developer.android.com/develop/ui/compose/designsystems/material2-material3?rec=CkVodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZGVzaWduc3lzdGVtcy9jdXN0b20QAhgJIAEoCDATOgMzLjc

Create light and dark color schemes using M2's lightColors and darkColors functions. This approach uses the Colors class and is replaced by the ColorScheme class in M3.

```Kotlin
import androidx.compose.material.lightColors
import androidx.compose.material.darkColors

val AppLightColors = lightColors(
    // M2 light Color parameters
)
val AppDarkColors = darkColors(
    // M2 dark Color parameters
)
val AppColors = if (darkTheme) {
    AppDarkColors
} else {
    AppLightColors
}
```

--------------------------------

### Duration-based Animation with tween

Source: https://developer.android.com/develop/ui/compose/animation/customize?rec=CkJodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYW5pbWF0aW9uL3Rlc3RpbmcQAxgJIAEoAjAdOgMzLjc

`tween` animates between start and end values over a specified duration using an easing curve. It can also include a delay before the animation starts.

```APIDOC
## AnimationSpec: tween

### Description
The `tween` `AnimationSpec` (short for "between") animates values over a specified `durationMillis` using an easing curve. It allows for a `delayMillis` to postpone the animation's start.

### Method
N/A (Conceptual API parameter)

### Endpoint
N/A (Used within Compose animation functions)

### Parameters
#### Request Body
- **durationMillis** (Int) - Required - The total duration of the animation in milliseconds.
- **delayMillis** (Int) - Optional - The delay before the animation starts in milliseconds. Default is 0.
- **easing** (Easing) - Optional - The easing curve to use for the animation. Default is `FastOutSlowInEasing`.

### Request Example
```kotlin
val value by animateFloatAsState(
    targetValue = 1f,
    animationSpec = tween(
        durationMillis = 300,
        delayMillis = 50,
        easing = LinearOutSlowInEasing
    ),
    label = "tween delay"
)
```

### Response
#### Success Response (N/A)
The `tween` spec defines the animation's timing and curve.

#### Response Example
N/A
```

--------------------------------

### Draw ImageBitmap in Compose DrawScope

Source: https://developer.android.com/develop/ui/compose/graphics/draw/overview

This example demonstrates how to load an `ImageBitmap` from application resources and draw it onto a `Canvas` using the `DrawScope.drawImage` function. It requires an image resource (e.g., `R.drawable.dog`) to be available in your project.

```Kotlin
val dogImage = ImageBitmap.imageResource(id = R.drawable.dog)

Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
    drawImage(dogImage)
})
```

--------------------------------

### App Bar Setup and Dependencies

Source: https://developer.android.com/develop/ui/compose/quick-guides/content/display-app-bar?hl=en

Configuration requirements for implementing app bars in Jetpack Compose projects. Includes minimum SDK requirements and necessary Compose dependencies.

```APIDOC
## Version Compatibility and Setup

### Minimum Requirements
- **Minimum SDK**: API level 21 or higher
- **Target Framework**: Jetpack Compose

### Required Dependencies

#### Kotlin (build.gradle.kts)
```kotlin
implementation(platform("androidx.compose:compose-bom:2026.03.00"))
```

#### Groovy (build.gradle)
```groovy
implementation platform('androidx.compose:compose-bom:2026.03.00')
```

### Compose BOM Benefits
- Manages version compatibility across Compose libraries
- Ensures all Compose artifacts work together
- Simplifies dependency management

### Additional Compose Libraries (typically included via BOM)
- `androidx.compose.material3:material3` - Material Design 3 components
- `androidx.compose.foundation:foundation` - Foundation components
- `androidx.compose.ui:ui` - Core UI components

### Project Configuration
1. Set minSdk to 21 or higher in build.gradle
2. Add Compose BOM dependency
3. Add specific Compose libraries as needed
4. Ensure Kotlin version compatibility (1.8.0+)

### Verification
Confirm setup by importing and using TopAppBar, CenterAlignedTopAppBar, or BottomAppBar composables without errors.
```

--------------------------------

### Implement PreviewParameterProvider for Sample Data in Compose

Source: https://developer.android.com/develop/ui/compose/tooling/previews

Create a class that implements PreviewParameterProvider to supply sample data for @PreviewParameter. The values property should return a sequence of the data objects to be used in the previews.

```kotlin
class UserPreviewParameterProvider : PreviewParameterProvider<User> {
    override val values = sequenceOf(
        User("Elise"),
        User("Frank"),
        User("Julia")
    )
}
```

--------------------------------

### Initialize GoogleFont Provider with Credentials

Source: https://developer.android.com/develop/ui/compose/text/fonts

Create a GoogleFont.Provider instance with the necessary credentials for Google Fonts authentication. The provider requires the authority, package name, and certificate hashes to verify the identity of the font provider service.

```Kotlin
val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)
```

--------------------------------

### Implement State-Driven UI with HelloContent

Source: https://developer.android.com/develop/ui/compose/state?rec=Cj1odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2Uvc2lkZS1lZmZlY3RzEAEYCSABKAQwJDoDMy43

A complete example of a composable function that uses a delegated state property to track user input in an OutlinedTextField and conditionally display a greeting.

```Kotlin
@Composable
fun HelloContent() {
    Column(modifier = Modifier.padding(16.dp)) {
        var name by remember { mutableStateOf("") }
        if (name.isNotEmpty()) {
            Text(
                text = "Hello, $name!",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )
    }
}
```

--------------------------------

### Display 'Hello world!' text with Jetpack Compose

Source: https://developer.android.com/develop/ui/compose/tutorial

This snippet demonstrates how to display a simple 'Hello world!' text using the `Text` composable function within an Android `Activity`'s `setContent` block. It shows the basic setup for a Jetpack Compose UI by importing necessary components and defining the activity's layout.

```kotlin
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Text("Hello world!")
        }
    }
}
```

--------------------------------

### Implementing Navigation Drawer in M2 vs M3

Source: https://developer.android.com/develop/ui/compose/designsystems/material2-material3?rec=CkhodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZGVzaWduc3lzdGVtcy9tYXRlcmlhbDMQARgJIAEoCDATOgMzLjc

Illustrates the migration from M2's integrated drawer parameters in Scaffold to M3's ModalNavigationDrawer wrapper approach.

```kotlin
import androidx.compose.material.DrawerValue
import
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState

val scaffoldState = rememberScaffoldState(
    drawerState = rememberDrawerState(DrawerValue.Closed)
)
val scope = rememberCoroutineScope()

Scaffold(
    scaffoldState = scaffoldState,
    drawerContent = { … },
    drawerGesturesEnabled = …,
    drawerShape = …,
    drawerElevation = …,
    drawerBackgroundColor = …,
    drawerContentColor = …,
    drawerScrimColor = …,
    content = {
        …
        scope.launch {
            scaffoldState.drawerState.open()
        }
    }
)
```

```kotlin
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState

val drawerState = rememberDrawerState(DrawerValue.Closed)
val scope = rememberCoroutineScope()

ModalNavigationDrawer(
    drawerState = drawerState,
    drawerContent = {
        ModalDrawerSheet(
            drawerShape = …,
            drawerTonalElevation = …,
            drawerContainerColor = …,
            drawerContentColor = …,
            content = { … }
        )
    },
    gesturesEnabled = …,
    scrimColor = …,
    content = {
        Scaffold(
            content = {
                …
                scope.launch {
                    drawerState.open()
                }
            }
        )
    }
)
```

--------------------------------

### Create physics-based animation with spring

Source: https://developer.android.com/develop/ui/compose/animation/customize

This example shows how to use the spring AnimationSpec to create a physics-based animation, allowing for customization of dampingRatio and stiffness for natural-feeling transitions.

```Kotlin
val value by animateFloatAsState(
    targetValue = 1f,
    animationSpec = spring(
        dampingRatio = Spring.DampingRatioHighBouncy,
        stiffness = Spring.StiffnessMedium
    ),
    label = "spring spec"
)
```

--------------------------------

### Wrap Material Components for Replaced Theme Systems

Source: https://developer.android.com/develop/ui/compose/designsystems/custom

Explains how to create a wrapper for Material components to ensure they use custom typography and shapes from a replaced theme system, including the use of ProvideTextStyle.

```kotlin
@Composable
fun ReplacementButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        shape = ReplacementTheme.shapes.component,
        onClick = onClick,
        modifier = modifier,
        content = {
            ProvideTextStyle(
                value = ReplacementTheme.typography.body
            ) {
                content()
            }
        }
    )
}

@Composable
fun ReplacementApp() {
    ReplacementTheme {
        /*...*/
        ReplacementButton(onClick = { /* ... */ }) {
            /* ... */
        }
    }
}
```

--------------------------------

### GET /windowAreaInfos

Source: https://developer.android.com/develop/ui/compose/layouts/adaptive/foldables/support-foldable-display-modes

Retrieves information and capability status for available window areas on the device.

```APIDOC
## GET /windowAreaInfos

### Description
Queries the list of available WindowAreaInfo objects to determine the status of specific display capabilities like rear-facing screens.

### Method
GET

### Endpoint
/windowAreaInfos

### Parameters
#### Query Parameters
- **type** (WindowAreaInfo.Type) - Optional - The type of display area to filter for (e.g., TYPE_REAR_FACING).

### Request Example
{
  "query": "windowAreaInfos"
}

### Response
#### Success Response (200)
- **capabilityStatus** (WindowAreaCapability.Status) - The availability status of the display mode (AVAILABLE, UNAVAILABLE, UNSUPPORTED, or ACTIVE).

#### Response Example
{
  "capabilityStatus": "WINDOW_AREA_STATUS_AVAILABLE"
}
```

--------------------------------

### Basic Composable Structure with Column and Text

Source: https://developer.android.com/develop/ui/compose/lifecycle

Demonstrates a simple composable function that uses Column layout to arrange multiple Text composables. This example shows the basic structure of how composables are organized in the Composition tree.

```Kotlin
@Composable
fun MyComposable() {
    Column {
        Text("Hello")
        Text("World")
    }
}
```

--------------------------------

### GET /compose/responsive-constraints

Source: https://developer.android.com/develop/ui/compose/layouts/basics?rec=CkdodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdG9vbGluZy9lZGl0b3ItYWN0aW9ucxABGAkgASgGMCM6AzMuNw

Utilizing BoxWithConstraints to retrieve measurement constraints from the parent to design adaptive layouts.

```APIDOC
## GET /compose/responsive-constraints

### Description
Provides access to minHeight, maxWidth, and other constraints within a content lambda to facilitate responsive design.

### Method
GET

### Endpoint
/compose/responsive-constraints

### Parameters
#### Path Parameters
- **scope** (string) - Required - The BoxWithConstraints scope.

### Request Example
{
  "query": "current_constraints"
}

### Response
#### Success Response (200)
- **minHeight** (dp) - Minimum height constraint.
- **maxWidth** (dp) - Maximum width constraint.

#### Response Example
{
  "minHeight": "300dp",
  "maxWidth": "600dp",
  "orientation": "landscape"
}
```

--------------------------------

### Implement Default Function Arguments in Kotlin

Source: https://developer.android.com/develop/ui/compose/kotlin?rec=CjlodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbGF5ZXJpbmcQARgJIAEoBDAIOgMzLjc

This section illustrates the use of default arguments in Kotlin functions, a feature that reduces the need for overloaded functions. The Java example shows a less efficient approach requiring multiple function definitions for optional parameters, while the Kotlin example demonstrates a single, more concise function definition.

```Java
// We don't need to do this in Kotlin!
void drawSquare(int sideLength) { }

void drawSquare(int sideLength, int thickness) { }

void drawSquare(int sideLength, int thickness, Color edgeColor) { }
JavaSnippets.java
```

```Kotlin
fun drawSquare(
    sideLength: Int,
    thickness: Int = 2,
    edgeColor: Color = Color.Black
) {
}
KotlinSnippets.kt
```

--------------------------------

### Create Responsive Card Layout with BoxWithConstraints in Jetpack Compose

Source: https://developer.android.com/develop/ui/compose/layouts/adaptive/support-different-display-sizes

This example shows how to use `BoxWithConstraints` to create a responsive card composable that adapts its content based on the available width. If the `maxWidth` is less than 400dp, content is arranged in a column; otherwise, it's arranged in a row, demonstrating how to change 'what' is shown based on display space.

```kotlin
@Composable
fun Card(/* ... */) {
    BoxWithConstraints {
        if (maxWidth < 400.dp) {
            Column {
                Image(/* ... */)
                Title(/* ... */)
            }
        } else {
            Row {
                Column {
                    Title(/* ... */)
                    Description(/* ... */)
                }
                Image(/* ... */)
            }
        }
    }
}
```

--------------------------------

### Implement Adaptive Layout using Window Size Classes in Kotlin

Source: https://developer.android.com/develop/ui/compose/layouts/adaptive/support-different-display-sizes

Demonstrates how to use Window Size Classes to determine UI components' visibility based on available screen height. It uses the currentWindowAdaptiveInfo to fetch window metrics and passes a boolean flag to a child composable.

```Kotlin
@Composable
fun MyApp(
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo(supportLargeAndXLargeWidth = true).windowSizeClass
) {
    // Decide whether to show the top app bar based on window size class.
    val showTopAppBar = windowSizeClass.isHeightAtLeastBreakpoint(WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND)

    // MyScreen logic is based on the showTopAppBar boolean flag.
    MyScreen(
        showTopAppBar = showTopAppBar,
        /* ... */
    )
}
```

--------------------------------

### Create Dismissible Scrim with pointerInput and detectTapGestures

Source: https://developer.android.com/develop/ui/compose/touch-input/pointer-input/tap-and-press

Shows how to create a semi-transparent background that dismisses a screen on tap using low-level pointer input, while also handling accessibility and keyboard events.

```Kotlin
@Composable
private fun Scrim(onClose: () -> Unit, modifier: Modifier = Modifier) {
    val strClose = stringResource(R.string.close)
    Box(
        modifier
            // handle pointer input
            .pointerInput(onClose) { detectTapGestures { onClose() } }
            // handle accessibility services
            .semantics(mergeDescendants = true) {
                contentDescription = strClose
                onClick {
                    onClose()
                    true
                }
            }
            // handle physical keyboard input
            .onKeyEvent {
                if (it.key == Key.Escape) {
                    onClose()
                    true
                } else {
                    false
                }
            }
            // draw scrim
            .background(Color.DarkGray.copy(alpha = 0.75f))
    )
}
```

--------------------------------

### Enable Hyphenation with Hyphens.Auto in Jetpack Compose

Source: https://developer.android.com/develop/ui/compose/text/style-paragraph?rec=CkBodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdGV4dC9zdHlsZS10ZXh0EAEYCSABKAIwGToDMy43

Demonstrates how to enable and disable hyphenation in Jetpack Compose Text composables. Shows two examples: one with Hyphens.None (default, no hyphenation) and one with Hyphens.Auto (hyphenation enabled). Both examples use a constrained width to trigger line breaking and hyphenation behavior.

```Kotlin
TextSample(
    samples = mapOf(
        "Hyphens - None" to {
            Text(
                text = SAMPLE_LONG_TEXT,
                modifier = Modifier
                    .width(130.dp)
                    .border(BorderStroke(1.dp, Color.Gray)),
                fontSize = 14.sp,
                style = TextStyle.Default.copy(
                    lineBreak = LineBreak.Paragraph,
                    hyphens = Hyphens.None
                )
            )
        },
        "Hyphens - Auto" to {
            Text(
                text = SAMPLE_LONG_TEXT,
                modifier = Modifier
                    .width(130.dp)
                    .border(BorderStroke(1.dp, Color.Gray)),
                fontSize = 14.sp,
                style = TextStyle.Default.copy(
                    lineBreak = LineBreak.Paragraph,
                    hyphens = Hyphens.Auto
                )
            )
        }
    )
)
```

--------------------------------

### GET /compose/layout-model

Source: https://developer.android.com/develop/ui/compose/layouts/basics?rec=CkdodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdG9vbGluZy9lZGl0b3ItYWN0aW9ucxABGAkgASgGMCM6AzMuNw

Explains the single-pass measurement and placement process in the Compose UI tree where parents measure children before being sized themselves.

```APIDOC
## GET /compose/layout-model

### Description
In the layout model, the UI tree is laid out in a single pass. Each node measures itself and its children recursively, passing size constraints down and placement instructions back up.

### Method
GET

### Endpoint
/compose/layout-model

### Parameters
#### Request Body
- **ui_tree** (object) - Required - The hierarchy of Composable nodes (e.g., Row, Column, Image).

### Request Example
{
  "root": "Row",
  "children": [
    { "type": "Image" },
    { "type": "Column", "children": ["Text", "Text"] }
  ]
}

### Response
#### Success Response (200)
- **layout_status** (string) - The result of the single-pass measurement.
- **resolved_sizes** (object) - The calculated dimensions for each node.

#### Response Example
{
  "status": "success",
  "pass": "single-pass",
  "nodes_processed": 5
}
```

--------------------------------

### Implement Android Content Capture with ActivityResultLauncher

Source: https://developer.android.com/develop/ui/compose/touch-input/stylus-input/create-a-note-taking-app

This Kotlin example demonstrates the full flow for initiating content capture in an Android note-taking app. It uses `registerForActivityResult` with `StartActivityForResult` to launch the `ACTION_LAUNCH_CAPTURE_CONTENT_ACTIVITY_FOR_NOTE` intent and handles the `CAPTURE_CONTENT_FOR_NOTE_SUCCESS` result to retrieve the captured content's URI. A Composable `CaptureButton` is included to trigger the capture.

```kotlin
private val startForResult =
registerForActivityResult(  ActivityResultContracts.StartActivityForResult()) {
 result: ActivityResult ->  if (result.resultCode ==
Intent.CAPTURE_CONTENT_FOR_NOTE_SUCCESS) {  val uri = result.data?.data  // Use
the URI to paste the captured content into the note.  }  } override fun
onCreate(savedInstanceState: Bundle?) {  super.onCreate(savedInstanceState)
setContent {  NotesTheme {  Surface(color =
MaterialTheme.colorScheme.background) {  CaptureButton(  onClick = {
Log.i("ContentCapture", "Launching intent...")
startForResult.launch(Intent(ACTION_LAUNCH_CAPTURE_CONTENT_ACTIVITY_FOR_NOTE))
})  }  }  } } @Composable fun CaptureButton(onClick: () -> Unit) {
Button(onClick = onClick)
 {Text("Capture Content")} }
```

--------------------------------

### Transition with AnimatedVisibility and AnimatedContent

Source: https://developer.android.com/develop/ui/compose/animation/value-based?rec=CkRodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYW5pbWF0aW9uL2N1c3RvbWl6ZRABGAkgASgCMBg6AzMuNw

Demonstrates how to use AnimatedVisibility and AnimatedContent as extension functions of Transition. This pattern allows you to hoist enter, exit, and sizeTransform animations into the Transition and observe state changes from outside the composable.

```APIDOC
## Transition.AnimatedVisibility and Transition.AnimatedContent

### Description
Extension functions that allow AnimatedVisibility and AnimatedContent to be used as part of a Transition. These functions derive their targetState from the parent Transition and trigger enter, exit, and sizeTransform animations when the Transition's targetState changes. This pattern enables hoisting animations out of the composable and observing state changes from outside.

### Usage Pattern

#### Create a Transition
```kotlin
var selected by remember { mutableStateOf(false) }
val transition = updateTransition(selected, label = "selected state")
```

#### Define Animated Values
```kotlin
val borderColor by transition.animateColor(label = "border color") { isSelected ->
    if (isSelected) Color.Magenta else Color.White
}
val elevation by transition.animateDp(label = "elevation") { isSelected ->
    if (isSelected) 10.dp else 2.dp
}
```

#### Use AnimatedVisibility Extension
```kotlin
transition.AnimatedVisibility(
    visible = { targetSelected -> targetSelected },
    enter = expandVertically(),
    exit = shrinkVertically()
) {
    Text(text = "It is fine today.")
}
```

#### Use AnimatedContent Extension
```kotlin
transition.AnimatedContent { targetState ->
    if (targetState) {
        Text(text = "Selected")
    } else {
        Icon(imageVector = Icons.Default.Phone, contentDescription = "Phone")
    }
}
```

### Parameters
- **visible** (lambda) - Required - Lambda that converts the parent transition's target state into a boolean
- **enter** (EnterTransition) - Optional - Animation for entering elements (e.g., expandVertically())
- **exit** (ExitTransition) - Optional - Animation for exiting elements (e.g., shrinkVertically())
- **targetState** (T) - Required - The target state from the parent Transition

### Benefits
- Hoist animations out of AnimatedVisibility/AnimatedContent internals
- Observe state changes from outside the composable
- Centralize animation logic with other transition animations
- Reuse animation definitions across multiple animated elements
```

--------------------------------

### GET /ink/brushes/styles

Source: https://developer.android.com/develop/ui/compose/touch-input/stylus-input/ink-api-modules

Retrieves brush configurations and factory-provided styles for stroke rendering.

```APIDOC
## GET /ink/brushes/styles

### Description
Provides access to Brush definitions and StockBrushes factory functions to create specific styles like markers or highlighters.

### Method
GET

### Endpoint
/ink/brushes/styles

### Parameters
#### Query Parameters
- **family** (string) - Optional - The specific BrushFamily to retrieve (e.g., 'marker', 'highlighter').

### Request Example
GET /ink/brushes/styles?family=marker

### Response
#### Success Response (200)
- **brush** (Brush) - Object containing base color, base size, and BrushFamily style data.

#### Response Example
{
  "brushFamily": "Marker",
  "baseColor": "#FF0000",
  "baseSize": 5.0
}
```

--------------------------------

### Launch Activity from Compose with Intent

Source: https://developer.android.com/develop/ui/compose/migrate/interoperability-apis/views-in-compose?rec=CkFodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdG9vbGluZy9wcmV2aWV3cxADGAkgASgEMBc6AzMuNw

Implement activity launching from Compose by following the data-down-events-up pattern. Define a callback in the composable that receives user interaction events, then execute startActivity from the Activity context. This maintains separation of concerns between UI and business logic.

```Kotlin
class OtherInteractionsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ExampleComposable(data, onButtonClick = {
                    startActivity(Intent(this, MyActivity::class.java))
                })
            }
        }
    }
}

@Composable
fun ExampleComposable(data: DataExample, onButtonClick: () -> Unit) {
    Button(onClick = onButtonClick) {
        Text(data.title)
    }
}
```

--------------------------------

### GET /rxjava/subscribeAsState

Source: https://developer.android.com/develop/ui/compose/state?rec=CkBodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdGV4dC91c2VyLWlucHV0EAIYCSABKAQwJDoDMy43

Transforms RxJava2 or RxJava3 reactive streams into Compose State.

```APIDOC
## GET /rxjava/subscribeAsState

### Description
Extension functions that transform RxJava reactive streams (Single, Observable, Completable) into Compose State for UI observation.

### Method
GET

### Endpoint
androidx.compose.runtime.rxjava2.subscribeAsState

### Parameters
#### Path Parameters
- **stream** (Observable<T>) - Required - The RxJava stream to subscribe to.

### Request Example
{
  "implementation": "androidx.compose.runtime:runtime-rxjava2:1.10.5"
}

### Response
#### Success Response (200)
- **state** (State<T>) - A Compose State object reflecting the latest stream emission.
```

--------------------------------

### GET /modifier/paddingFromBaseline

Source: https://developer.android.com/develop/ui/compose/modifiers?rec=Cj9odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbGF5b3V0cy9jdXN0b20QAxgJIAEoATAKOgMzLjc

Adds padding specifically relative to the text baseline of a composable.

```APIDOC
## GET /modifier/paddingFromBaseline

### Description
Adds padding above a text baseline such that a specific distance is achieved from the top of the layout to the baseline.

### Method
GET

### Endpoint
Modifier.paddingFromBaseline(top, bottom)

### Parameters
#### Path Parameters
- **top** (Dp) - Optional - Distance from the top of the layout to the baseline.
- **bottom** (Dp) - Optional - Distance from the baseline to the bottom of the layout.

### Request Example
{
  "modifier": "Modifier.paddingFromBaseline(top = 50.dp)"
}

### Response
#### Success Response (200)
- **Modifier** (Object) - The resulting modifier instance.
```

--------------------------------

### Initialize state holders with remember and keys

Source: https://developer.android.com/develop/ui/compose/state

Demonstrates the pattern of using a helper function to initialize a plain state holder class that is recreated only when its dependencies (like window size) change.

```Kotlin
@Composable
private fun rememberMyAppState(
    windowSizeClass: WindowSizeClass
): MyAppState {
    return remember(windowSizeClass) {
        MyAppState(windowSizeClass)
    }
}

@Stable
class MyAppState(
    private val windowSizeClass: WindowSizeClass
) { /* ... */ }
```

--------------------------------

### GET /pluralStringResource

Source: https://developer.android.com/develop/ui/compose/resources?rec=CkhodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZ3JhcGhpY3MvaW1hZ2VzL2NvbXBhcmUQAhgJIAEoAzATOgMzLjc

Loads a plural resource with a certain quantity. This is currently an experimental API in Compose.

```APIDOC
## GET pluralStringResource\n\n### Description\nRetrieves a plural string resource based on a specific quantity.\n\n### Method\nGET (Compose Function)\n\n### Endpoint\npluralStringResource(id: Int, count: Int, varargs formatArgs: Any)\n\n### Parameters\n#### Path Parameters\n- **id** (Int) - Required - The resource ID of the plurals resource.\n- **count** (Int) - Required - The quantity used to select the appropriate plural string (one, other, etc.).\n\n#### Query Parameters\n- **formatArgs** (Any) - Optional - Arguments to be inserted into the plural string placeholders.\n\n### Request Example\npluralStringResource(R.plurals.runtime_format, quantity, quantity)\n\n### Response\n#### Success Response (200)\n- **text** (String) - The resolved plural string value.
```

--------------------------------

### Phase 2: Layout - Measurement and Placement

Source: https://developer.android.com/develop/ui/compose/phases?rec=Ck1odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZ3JhcGhpY3MvaW1hZ2VzL29wdGltaXphdGlvbhADGAkgASgFMBs6AzMuNw

Explains the layout phase which consists of measurement and placement steps. State reads during layout affect the layout and drawing phases. This example demonstrates using state in the placement step via the offset modifier.

```APIDOC
## Layout Phase

### Description
The layout phase consists of two steps: measurement and placement. State reads during each of these steps affect the layout and potentially the drawing phase. When state changes, Compose UI schedules the layout phase and runs the drawing phase if size or position has changed.

### Layout Phase Steps
1. **Measurement**: Runs the measure lambda passed to `Layout` composable, `MeasureScope.measure` method, etc.
2. **Placement**: Runs the placement block of `layout` function, lambda block of `Modifier.offset { … }`, etc.

### Code Example
```kotlin
var offsetX by remember { mutableStateOf(8.dp) }
Text(
    text = "Hello",
    modifier = Modifier.offset {
        // The `offsetX` state is read in the placement step
        // of the layout phase when the offset is calculated.
        // Changes in `offsetX` restart the layout.
        IntOffset(offsetX.roundToPx(), 0)
    }
)
```

### Performance Impact
- State changes trigger layout phase only (skips composition)
- Drawing phase runs if size or position changes
- More efficient than composition phase for position/size changes
```

--------------------------------

### GET /semantics/matcher

Source: https://developer.android.com/develop/ui/compose/semantics

Retrieves and matches UI nodes based on their semantic properties for testing assertions.

```APIDOC
## GET /semantics/matcher

### Description
Uses the SemanticsMatcher to find specific UI nodes within the testing framework based on their semantic roles or values.

### Method
SemanticsMatcher.expectValue

### Endpoint
SemanticsMatcher.expectValue(property, value)

### Parameters
#### Query Parameters
- **property** (SemanticsPropertyKey) - Required - The semantic property to look for (e.g., Role).
- **value** (Any) - Required - The expected value of the property (e.g., Role.Switch).

### Request Example
{
  "property": "SemanticsProperties.Role",
  "expected": "Role.Switch"
}

### Response
#### Success Response (200)
- **node** (SemanticsNodeInteraction) - The matched UI node ready for interaction or assertion.
```

--------------------------------

### Implement SizeMode.Responsive with Multiple Predefined Sizes

Source: https://developer.android.com/develop/ui/compose/glance/build-ui

Creates a GlanceAppWidget with responsive layout support by defining three predefined sizes (SMALL_SQUARE, HORIZONTAL_RECTANGLE, BIG_SQUARE) and adaptive content that adjusts based on the current size. The provideContent method is called for each defined size, allowing conditional rendering of UI elements based on LocalSize.current.

```Kotlin
class MyAppWidget : GlanceAppWidget() {

    companion object {
        private val SMALL_SQUARE = DpSize(100.dp, 100.dp)
        private val HORIZONTAL_RECTANGLE = DpSize(250.dp, 100.dp)
        private val BIG_SQUARE = DpSize(250.dp, 250.dp)
    }

    override val sizeMode = SizeMode.Responsive(
        setOf(
            SMALL_SQUARE,
            HORIZONTAL_RECTANGLE,
            BIG_SQUARE
        )
    )

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        // ...

        provideContent {
            MyContent()
        }
    }

    @Composable
    private fun MyContent() {
        // Size will be one of the sizes defined above.
        val size = LocalSize.current
        Column {
            if (size.height >= BIG_SQUARE.height) {
                Text(text = "Where to?", modifier = GlanceModifier.padding(12.dp))
            }
            Row(horizontalAlignment = Alignment.CenterHorizontally) {
                Button()
                Button()
                if (size.width >= HORIZONTAL_RECTANGLE.width) {
                    Button("School")
                }
            }
            if (size.height >= BIG_SQUARE.height) {
                Text(text = "provided by X")
            }
        }
    }
}
```

--------------------------------

### GET /rxjava/subscribeAsState

Source: https://developer.android.com/develop/ui/compose/state?rec=CjpodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbGlicmFyaWVzEAIYCSABKAQwJDoDMy43

Transforms RxJava2 or RxJava3 reactive streams into Compose State objects.

```APIDOC
## GET /rxjava/subscribeAsState

### Description
Extension functions that transform RxJava reactive streams (Single, Observable, Completable) into Compose State.

### Method
GET (Extension Function)

### Endpoint
Observable<T>.subscribeAsState()

### Request Example
{
  "dependencies": [
    "androidx.compose.runtime:runtime-rxjava2:1.10.5",
    "androidx.compose.runtime:runtime-rxjava3:1.10.5"
  ]
}

### Response
#### Success Response (200)
- **State<T>** (Object) - The stream's emitted values represented as Compose State.
```

--------------------------------

### Customizing AnimatedVisibility Enter and Exit Transitions

Source: https://developer.android.com/develop/ui/compose/animation/composables-modifiers

This example shows how to customize the enter and exit transitions for `AnimatedVisibility` using `EnterTransition` and `ExitTransition` objects. It combines `slideInVertically`, `expandVertically`, and `fadeIn` for entry, and `slideOutVertically`, `shrinkVertically`, and `fadeOut` for exit, demonstrating how to create complex animation sequences.

```kotlin
var visible by remember { mutableStateOf(true) }
val density = LocalDensity.current
AnimatedVisibility(
    visible = visible,
    enter = slideInVertically {
        // Slide in from 40 dp from the top.
        with(density) { -40.dp.roundToPx() }
    } + expandVertically(
        // Expand from the top.
        expandFrom = Alignment.Top
    ) + fadeIn(
        // Fade in with the initial alpha of 0.3f.
        initialAlpha = 0.3f
    ),
    exit = slideOutVertically() + shrinkVertically() + fadeOut()
) {
    Text(
        "Hello",
        Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}
```

--------------------------------

### GET /modifier/offset

Source: https://developer.android.com/develop/ui/compose/modifiers?rec=Cj9odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbGF5b3V0cy9jdXN0b20QAxgJIAEoATAKOgMzLjc

Positions a layout relative to its original position without changing its measurements.

```APIDOC
## GET /modifier/offset

### Description
Applies an offset in the x and y axes. Unlike padding, adding an offset does not change the measurements of the composable. `offset` respects layout direction (LTR/RTL), while `absoluteOffset` does not.

### Method
GET

### Endpoint
Modifier.offset(x, y)

### Parameters
#### Path Parameters
- **x** (Dp) - Optional - Horizontal offset.
- **y** (Dp) - Optional - Vertical offset.

### Request Example
{
  "modifier": "Modifier.offset(x = 4.dp)"
}

### Response
#### Success Response (200)
- **Modifier** (Object) - The resulting modifier instance.
```

--------------------------------

### Implement a Theme Entry Point Composable

Source: https://developer.android.com/develop/ui/compose/designsystems/anatomy

Construct the theme systems and provide them to the hierarchy using CompositionLocalProvider. This function serves as the primary API for applying the theme to a UI tree.

```kotlin
@Composable
fun Theme(
    /* ... */
    content: @Composable () -> Unit
) {
    val colorSystem = ColorSystem(
        color = Color(0xFF3DDC84),
        gradient = listOf(Color.White, Color(0xFFD7EFFF))
    )
    val typographySystem = TypographySystem(
        fontFamily = FontFamily.Monospace,
        textStyle = TextStyle(fontSize = 18.sp)
    )
    val customSystem = CustomSystem(
        value1 = 1000,
        value2 = "Custom system"
    )
    /* ... */
    CompositionLocalProvider(
        LocalColorSystem provides colorSystem,
        LocalTypographySystem provides typographySystem,
        LocalCustomSystem provides customSystem,
        /* ... */
        content = content
    )
}
```

--------------------------------

### Examples of Various Actions in Compose UI Tests

Source: https://developer.android.com/develop/ui/compose/testing/apis?rec=CkZodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYWNjZXNzaWJpbGl0eS90ZXN0aW5nEAIYCSABKAIwHDoDMy43

This snippet provides a list of common actions that can be performed on UI nodes in Compose tests. These actions include simulating clicks, performing semantics-specific actions, injecting key press events, and executing gestures like swiping.

```Kotlin
performClick(),
performSemanticsAction(key),
performKeyPress(keyEvent),
performGesture { swipeLeft() }
```

--------------------------------

### Basic Top App Bar Usage in Android Compose (M2 vs M3)

Source: https://developer.android.com/develop/ui/compose/designsystems/material2-material3?rec=CkdodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZGVzaWduc3lzdGVtcy9tYXRlcmlhbBACGAkgASgHMBk6AzMuNw

Demonstrates the basic import and usage of the `TopAppBar` composable in both Material 2 and Material 3. The primary difference lies in the import package (`androidx.compose.material` vs `androidx.compose.material3`).

```kotlin
import androidx.compose.material.TopAppBar

TopAppBar(…)

```

```kotlin
import androidx.compose.material3.TopAppBar

TopAppBar(…)

```

--------------------------------

### Animated List Example

Source: https://developer.android.com/develop/ui/compose/lists?rec=CkdodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdG9vbGluZy9lZGl0b3ItYWN0aW9ucxADGAkgASgCMBo6AzMuNw

A complete Composable function demonstrating how to animate items in a LazyColumn when they are added, removed, or reordered.

```APIDOC
## PUT /api/users/{userId}

### Description
Updates an existing user's information. Requires the user's ID in the path and the fields to update in the request body.

### Method
PUT

### Endpoint
/api/users/{userId}

### Parameters
#### Path Parameters
- **userId** (string) - Required - The unique identifier of the user to update.

#### Request Body
- **email** (string) - Optional - The updated email address for the user.
- **password** (string) - Optional - The updated password for the user.

### Request Example
```json
{
  "email": "john.doe.updated@example.com"
}
```

### Response
#### Success Response (200)
- **id** (string) - The unique identifier for the updated user.
- **username** (string) - The username of the updated user.
- **email** (string) - The updated email address of the user.

#### Response Example
```json
{
  "id": "user-12345",
  "username": "johndoe",
  "email": "john.doe.updated@example.com"
}
```
```

--------------------------------

### GET /CompositionLocal.current

Source: https://developer.android.com/develop/ui/compose/compositionlocal?rec=Cl9odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbWlncmF0ZS9pbnRlcm9wZXJhYmlsaXR5LWFwaXMvdmlld3MtaW4tY29tcG9zZRADGAkgASgJMC06AzMuNw

Retrieves the current value of a CompositionLocal provided by the nearest ancestor provider.

```APIDOC
## GET CompositionLocal.current

### Description
Accesses the value currently bound to the CompositionLocal key in the local scope.

### Method
PROPERTY_GETTER

### Endpoint
CompositionLocal<T>.current

### Parameters
#### Path Parameters
- **CompositionLocal** (Key) - Required - The local key instance being queried.

### Request Example
```kotlin
val currentElevations = LocalElevations.current
```

### Response
#### Success Response (200)
- **value** (T) - The value provided by the nearest CompositionLocalProvider, or the default value if none exists.
```

--------------------------------

### GET /CompositionLocal.current

Source: https://developer.android.com/develop/ui/compose/compositionlocal?rec=CkZodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZGVzaWduc3lzdGVtcy9hbmF0b215EAEYCSABKAkwLToDMy43

Retrieves the current value of a CompositionLocal from the nearest provider in the composition tree.

```APIDOC
## PROPERTY CompositionLocal.current

### Description
Returns the value provided by the nearest CompositionLocalProvider. If no provider exists, it returns the default value from the factory.

### Method
GETTER

### Endpoint
[CompositionLocalInstance].current

### Parameters
#### Path Parameters
- **instance** (CompositionLocal) - Required - The specific local key instance being accessed.

### Request Example
```kotlin
@Composable
fun MyCard() {
    val elevation = LocalElevations.current.card
}
```

### Response
#### Success Response (200)
- **value** (T) - The current value associated with the CompositionLocal key.
```

--------------------------------

### Material Design 3 Color System Setup

Source: https://developer.android.com/develop/ui/compose/designsystems/material2-material3?rec=CkVodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZGVzaWduc3lzdGVtcy9jdXN0b20QAhgJIAEoCDATOgMzLjc

Create light and dark color schemes using M3's lightColorScheme and darkColorScheme functions. This replaces the M2 Colors class with the new ColorScheme class, which has significantly different parameters and structure.

```Kotlin
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme

val AppLightColorScheme = lightColorScheme(
    // M3 light Color parameters
)
val AppDarkColorScheme = darkColorScheme(
    // M3 dark Color parameters
)
val AppColorScheme = if (darkTheme) {
    AppDarkColorScheme
} else {
    AppLightColorScheme
}
```

--------------------------------

### Create Responsive Layouts with BoxWithConstraints (Kotlin)

Source: https://developer.android.com/develop/ui/compose/layouts/basics

This Kotlin example illustrates the use of BoxWithConstraints to access the parent's measurement constraints, such as minHeight and maxWidth. This pattern enables developers to create adaptive layouts that respond dynamically to different screen configurations and sizes.

```kotlin
@Composable
fun WithConstraintsComposable() {
    BoxWithConstraints {
        Text("My minHeight is $minHeight while my maxWidth is $maxWidth")
    }
}
```

--------------------------------

### GET /CompositionLocal/current

Source: https://developer.android.com/develop/ui/compose/compositionlocal?rec=Cj5odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZG9jdW1lbnRhdGlvbhABGAkgASgFMB46AzMuNw

Retrieves the current value of a CompositionLocal instance from the nearest ancestor provider.

```APIDOC
## GET /CompositionLocal/current

### Description
Accesses the value provided by the closest CompositionLocalProvider in the UI hierarchy. If no provider is found, it returns a default value.

### Method
GET

### Endpoint
[CompositionLocal].current

### Parameters
#### Path Parameters
- **CompositionLocal** (StaticCompositionLocal/DynamicCompositionLocal) - Required - The specific local object to query (e.g., LocalColorScheme).

### Request Example
{
  "access": "MaterialTheme.colorScheme.primary",
  "underlying_call": "LocalColorScheme.current.primary"
}

### Response
#### Success Response (200)
- **value** (T) - The current value of the type associated with the CompositionLocal.

### Response Example
{
  "value": "Color(0xFF6200EE)",
  "type": "androidx.compose.ui.graphics.Color"
}
```

--------------------------------

### Implement List-Detail Layout with ListDetailPaneScaffold

Source: https://developer.android.com/develop/ui/compose/layouts/adaptive/canonical-layouts?hl=pt-br

Demonstrates how to use the ListDetailPaneScaffold from the Material 3 Adaptive API to create a responsive list-detail interface. It utilizes a navigator to manage state and transitions between panes based on window size classes.

```Kotlin
@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun MyListDetailPaneScaffold() {
    val navigator = rememberListDetailPaneScaffoldNavigator()
    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            // Listing Pane
        },
        detailPane = {
            // Details Pane
        }
    )
}
```

--------------------------------

### Multi-Instance System UI Support

Source: https://developer.android.com/develop/ui/compose/layouts/adaptive/support-desktop-windowing

Enable multi-instance support for an app in desktop windowing by setting a specific property, allowing the system UI to facilitate multiple instances.

```APIDOC
## SET PROPERTY_SUPPORTS_MULTI_INSTANCE_SYSTEM_UI

### Description
Android 15 introduces this property, which apps can set to specify that system UI should be shown for the app to allow it to be launched as multiple instances. This highly increases user productivity in desktop windowing environments.

### Method
SET (Property/Flag)

### Endpoint
N/A

### Parameters
#### Path Parameters
None

#### Query Parameters
None

#### Request Body
None

### Request Example
N/A

### Response
#### Success Response (200)
N/A

#### Response Example
N/A
```

--------------------------------

### GET /theme/current

Source: https://developer.android.com/develop/ui/compose/designsystems/anatomy?rec=Cj5odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZG9jdW1lbnRhdGlvbhADGAkgASgEMBw6AzMuNw

A convenience object used to retrieve the current theme values from the composition local.

```APIDOC
## GET /theme/current

### Description
An object providing static access to theme properties, ensuring consistency and ease of use across the application.

### Method
GET

### Endpoint
Theme.[systemProperty]

### Parameters
#### Path Parameters
- **systemProperty** (String) - Required - The specific system to access (e.g., colorSystem, typographySystem).

### Request Example
val primaryColor = Theme.colorSystem.color

### Response
#### Success Response (200)
- **value** (Object) - Returns the current value from the nearest CompositionLocalProvider in the hierarchy.
```

--------------------------------

### tween AnimationSpec

Source: https://developer.android.com/develop/ui/compose/animation/customize?rec=CkJodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYW5pbWF0aW9uL3Rlc3RpbmcQAxgJIAEoBDAgOgMzLjc

Animates between start and end values over a specified duration using an easing curve.

```APIDOC
## tween AnimationSpec

### Description
Animates between start and end values over a fixed duration using an easing curve. The name is derived from 'between'.

### Method
tween(durationMillis, delayMillis, easing)

### Parameters
#### Arguments
- **durationMillis** (Int) - Optional - The total duration of the animation in milliseconds. Defaults to 300ms.
- **delayMillis** (Int) - Optional - The amount of time to postpone the start of the animation. Defaults to 0.
- **easing** (Easing) - Optional - The easing curve (e.g., FastOutSlowInEasing) used to interpolate between values.

### Request Example
{
  "animationSpec": "tween(durationMillis = 300, delayMillis = 50, easing = LinearOutSlowInEasing)"
}

### Response
#### Success Response
- **AnimationSpec** (Object) - A duration-based animation specification.
```

--------------------------------

### Flow Layout Responsive Design Patterns

Source: https://developer.android.com/develop/ui/compose/layouts/flow?rec=CkJodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdG91Y2gtaW5wdXQvZm9jdXMQARgJIAEoBTAqOgMzLjc

Demonstrates techniques for building responsive layouts using Flow layouts combined with item weights and fractional sizing for flexible, adaptive designs.

```APIDOC
## Flow Layout Responsive Design Patterns

### Description
Flow layouts enable responsive design by combining maxItemsInEach* parameters with Modifier.weight() and fractional sizing. Content automatically adapts without being cut off.

### Pattern 1: Using Modifier.weight() with FlowRow
```kotlin
@Composable
private fun ResponsiveFlowRowWithWeight() {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        maxItemsInEachRow = 2
    ) {
        ChipItem(
            "Item 1",
            modifier = Modifier.weight(1f)
        )
        ChipItem(
            "Item 2",
            modifier = Modifier.weight(1f)
        )
    }
}
```
Items expand to fill available width equally.

### Pattern 2: Fractional Sizing
```kotlin
@Composable
private fun FlowRowWithFractionalSizing() {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        ChipItem(
            "Item 1",
            modifier = Modifier.fillMaxWidth(0.5f)
        )
        ChipItem(
            "Item 2",
            modifier = Modifier.fillMaxWidth(0.5f)
        )
    }
}
```
Items take up a fraction of available width.

### Pattern 3: fillMaxColumnWidth() and fillMaxRowHeight()
```kotlin
@Composable
private fun FlowRowWithFillModifiers() {
    FlowRow(
        modifier = Modifier.padding(8.dp),
        maxItemsInEachRow = 3
    ) {
        ChipItem(
            "Item 1",
            modifier = Modifier.fillMaxColumnWidth()
        )
        ChipItem(
            "Item 2",
            modifier = Modifier.fillMaxColumnWidth()
        )
    }
}
```
Items fill the maximum width of their column.

### Benefits
- Content never cut off due to size constraints
- Automatic adaptation to different screen sizes
- Flexible layouts that expand/contract as needed
- Combines with maxItemsInEach* for precise control
```

--------------------------------

### FUNCTION tween

Source: https://developer.android.com/develop/ui/compose/animation/customize?rec=Ck5odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdG9vbGluZy9pdGVyYXRpdmUtZGV2ZWxvcG1lbnQQARgJIAEoBDAgOgMzLjc

Animates between start and end values over a specified duration using an easing curve.

```APIDOC
## FUNCTION tween\n\n### Description\ntween animates between start and end values over the specified durationMillis using an easing curve. It is short for 'between'.\n\n### Method\nFUNCTION\n\n### Endpoint\ntween(durationMillis, delayMillis, easing)\n\n### Parameters\n#### Function Parameters\n- **durationMillis** (Int) - Optional - Duration of the animation in milliseconds. Default: DefaultDurationMillis.\n- **delayMillis** (Int) - Optional - Amount of time to postpone the start of the animation. Default: 0.\n- **easing** (Easing) - Optional - The easing curve to use for the animation. Default: FastOutSlowInEasing.\n\n### Request Example\n{\n  "animationSpec": "tween(durationMillis = 300, delayMillis = 50, easing = LinearOutSlowInEasing)"\n}\n\n### Response\n#### Success Response (200)\n- **spec** (AnimationSpec) - A duration-based animation specification.
```

--------------------------------

### Configure FlexBox dependencies

Source: https://developer.android.com/develop/ui/compose/layouts/adaptive/flexbox/get-started

Add the foundation layout library to your project using Version Catalogs (toml) and the app-level Gradle build file (kts).

```toml
[versions]
compose = "1.11.0-beta02"

[libraries]
androidx-compose-foundation-layout = { group = "androidx.compose.foundation", name = "foundation-layout", version.ref = "compose" }
```

```kotlin
dependencies {
    implementation(libs.androidx.compose.foundation.layout)
}
```

--------------------------------

### Implement Accessible Button Colors in Material 3

Source: https://developer.android.com/develop/ui/compose/designsystems/material3

This example compares accessible and inaccessible color configurations for Buttons. It shows how to pair primary colors with their corresponding 'on' roles to ensure sufficient contrast ratios.

```kotlin
// ✅ Button with sufficient contrast ratio
Button(
    onClick = { },
    colors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    )
) {
}

// ❌ Button with poor contrast ratio
Button(
    onClick = { },
    colors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
        contentColor = MaterialTheme.colorScheme.primaryContainer
    )
) {
}
```

--------------------------------

### GET /MaterialTheme

Source: https://developer.android.com/develop/ui/compose/compositionlocal?rec=Cj5odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZG9jdW1lbnRhdGlvbhABGAkgASgCMAs6AzMuNw

Retrieves the current theme-related CompositionLocal values such as color scheme, typography, and shapes.

```APIDOC
## GET /MaterialTheme

### Description
An object that provides access to the current `colorScheme`, `typography`, and `shapes` defined in the nearest MaterialTheme ancestor.

### Method
GET

### Endpoint
/MaterialTheme

### Parameters
#### Query Parameters
- **attribute** (String) - Optional - The specific theme property to access: 'colorScheme', 'typography', or 'shapes'.

### Request Example
{
  "access": "MaterialTheme.colorScheme.primary"
}

### Response
#### Success Response (200)
- **colorScheme** (ColorScheme) - The current set of Material 3 colors.
- **typography** (Typography) - The current set of Material 3 text styles.
- **shapes** (Shapes) - The current set of Material 3 shapes.
```

--------------------------------

### GET rememberScrollState

Source: https://developer.android.com/develop/ui/compose/touch-input/scroll/scroll-modifiers

Creates and remembers a ScrollState to manage and observe the scroll position of a scrollable composable.

```APIDOC
## GET rememberScrollState

### Description
The ScrollState lets you change the scroll position or get its current state. It is used in conjunction with verticalScroll or horizontalScroll.

### Method
COMPOSABLE_FUNCTION

### Endpoint
rememberScrollState(initial)

### Parameters
#### Query Parameters
- **initial** (Int) - Optional - The initial scroll position in pixels. Defaults to 0.

### Request Example
{
  "usage": "val state = rememberScrollState()"
}

### Response
#### Success Response (200)
- **state** (ScrollState) - An object used to control or observe scrolling.

#### Response Example
{
  "value": 0,
  "maxValue": 500
}
```

--------------------------------

### POST /theme/provider

Source: https://developer.android.com/develop/ui/compose/designsystems/anatomy?rec=Cj5odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZG9jdW1lbnRhdGlvbhADGAkgASgEMBw6AzMuNw

The entry point function that constructs theme system instances and provides them to the UI hierarchy.

```APIDOC
## POST /theme/provider

### Description
The primary Composable function that initializes theme systems and uses CompositionLocalProvider to pass values down the tree.

### Method
COMPOSABLE

### Endpoint
Theme(content: @Composable () -> Unit)

### Parameters
#### Request Body
- **content** (Composable Function) - Required - The UI content that will consume the theme values.

### Request Example
@Composable
fun Theme(content: @Composable () -> Unit) {
    val colorSystem = ColorSystem(color = Color(0xFF3DDC84), gradient = listOf(Color.White, Color(0xFFD7EFFF)))
    CompositionLocalProvider(
        LocalColorSystem provides colorSystem,
        content = content
    )
}

### Response
#### Success Response (200)
- **CompositionLocal** (Context) - Provides scoped theme data to all nested composables.
```

--------------------------------

### Define Color instances in Compose

Source: https://developer.android.com/develop/ui/compose/designsystems/material

Examples of creating Color objects using hex values or specific RGB float values in Jetpack Compose.

```kotlin
val Red = Color(0xffff0000)
val Blue = Color(red = 0f, green = 0f, blue = 1f)
```

--------------------------------

### Implement Material 3 Buttons and Extended FAB

Source: https://developer.android.com/develop/ui/compose/designsystems/material3

Examples of various Material 3 button types including standard buttons, Extended Floating Action Buttons for high emphasis, and TextButtons for low emphasis actions.

```kotlin
// Standard/Filled Button
Button(onClick = { /*..*/ }) {
    Text(text = "My Button")
}

// Extended Floating Action Button
ExtendedFloatingActionButton(
    onClick = { /*..*/ },
    modifier = Modifier
) {
    Icon(
        imageVector = Icons.Default.Edit,
        contentDescription = stringResource(id = R.string.edit),
    )
    Text(
        text = stringResource(id = R.string.add_entry),
    )
}

// Text Button
TextButton(onClick = { /*..*/ }) {
    Text(text = stringResource(id = R.string.replated_articles))
}
```

--------------------------------

### GET /pointerInput/awaitEachGesture

Source: https://developer.android.com/develop/ui/compose/touch-input/pointer-input/understand-gestures?rec=CltodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdG91Y2gtaW5wdXQvc2Nyb2xsL25lc3RlZC1zY3JvbGwtbW9kaWZpZXJzEAEYCSABKAIwGzoDMy43

A helper method that restarts its block when all pointers are lifted, indicating the gesture is completed.

```APIDOC
## GET /pointerInput/awaitEachGesture

### Description
Simplifies the gesture loop by automatically handling the reset logic when all pointers are released.

### Method
SUSPEND

### Endpoint
Modifier.pointerInput(key) { awaitEachGesture { ... } }

### Parameters
#### Request Body
- **block** (SuspendFunction) - Required - The pointer event handling logic to execute per gesture.

### Request Example
{
  "logic": "awaitFirstDown().also { it.consume() }"
}

### Response
#### Success Response (200)
- **Unit** (void) - Executes the block for each discrete gesture detected.
```

--------------------------------

### Create Alternating Grid Layout with FlowRow

Source: https://developer.android.com/develop/ui/compose/layouts/flow

Shows how to create a responsive grid where item sizes alternate. In this example, two items share a row using weights, while every third item occupies the full width of its row using fillMaxWidth().

```Kotlin
FlowRow(
    modifier = Modifier.padding(4.dp),
    horizontalArrangement = Arrangement.spacedBy(4.dp),
    maxItemsInEachRow = 2
) {
    val itemModifier = Modifier
        .padding(4.dp)
        .height(80.dp)
        .clip(RoundedCornerShape(8.dp))
        .background(Color.Blue)
    repeat(6) { item ->
        // if the item is the third item, don't use weight modifier, but rather fillMaxWidth
        if ((item + 1) % 3 == 0) {
            Spacer(modifier = itemModifier.fillMaxWidth())
        } else {
            Spacer(modifier = itemModifier.weight(0.5f))
        }
    }
}
```

--------------------------------

### GET /pointerInput/multiTouchCalculations

Source: https://developer.android.com/develop/ui/compose/touch-input/pointer-input/understand-gestures?rec=CltodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdG91Y2gtaW5wdXQvc2Nyb2xsL25lc3RlZC1zY3JvbGwtbW9kaWZpZXJzEAEYCSABKAIwGzoDMy43

Utility methods for calculating transformations like pan, zoom, and rotation during multi-touch events.

```APIDOC
## GET /pointerInput/multiTouchCalculations

### Description
Provides mathematical helpers for complex multi-pointer gestures when standard transform detectors are insufficient.

### Method
FUNCTION

### Endpoint
PointerEvent.calculate[Method]()

### Parameters
#### Helper Methods
- **calculateCentroid** (Offset) - Returns the center point of all pointers.
- **calculateCentroidSize** (Float) - Returns the average distance of pointers from the centroid.
- **calculatePan** (Offset) - Returns the movement of the centroid.
- **calculateRotation** (Float) - Returns the rotation angle in degrees.
- **calculateZoom** (Float) - Returns the scale factor change.

### Response Example
{
  "centroid": "Offset(x, y)",
  "zoom": 1.05
}
```

--------------------------------

### Apply Color and Size Animations

Source: https://developer.android.com/develop/ui/compose/tutorial?hl=fa

Illustrates how to enhance UI transitions using animateColorAsState for background color shifts and animateContentSize for smooth container resizing. These animations respond automatically to state changes in the composable.

```Kotlin
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize

@Composable
fun MessageCard(msg: Message) {
    var isExpanded by remember { mutableStateOf(false) }
    
    val surfaceColor by animateColorAsState(
        if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
    )

    Surface(
        shape = MaterialTheme.shapes.medium,
        shadowElevation = 1.dp,
        color = surfaceColor,
        modifier = Modifier.animateContentSize().padding(1.dp)
    ) {
        Text(
            text = msg.body,
            modifier = Modifier.padding(all = 4.dp),
            maxLines = if (isExpanded) Int.MAX_VALUE else 1,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
```

--------------------------------

### Import and Basic Scaffold Setup in Material 2 and Material 3

Source: https://developer.android.com/develop/ui/compose/designsystems/material2-material3?rec=CkVodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZGVzaWduc3lzdGVtcy9jdXN0b20QAhgJIAEoCDATOgMzLjc

Demonstrates the basic import statements and Scaffold composable structure for both Material 2 and Material 3. The main layout composable is named Scaffold in both versions, but the import packages differ between androidx.compose.material and androidx.compose.material3.

```Kotlin (Material 2)
import androidx.compose.material.Scaffold

Scaffold(
    // M2 scaffold parameters
)
```

```Kotlin (Material 3)
import androidx.compose.material3.Scaffold

Scaffold(
    // M3 scaffold parameters
)
```

--------------------------------

### GET /modifier/fillMaxHeight

Source: https://developer.android.com/develop/ui/compose/modifiers?rec=Cj9odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbGF5b3V0cy9jdXN0b20QAxgJIAEoATAKOgMzLjc

Forces a child layout to fill the maximum height allowed by its parent constraints.

```APIDOC
## GET /modifier/fillMaxHeight

### Description
Makes the child layout fill all available height allowed by the parent. Similar modifiers include `fillMaxSize` and `fillMaxWidth`.

### Method
GET

### Endpoint
Modifier.fillMaxHeight(fraction)

### Parameters
#### Path Parameters
- **fraction** (Float) - Optional - The fraction of the maximum height to occupy (default is 1.0).

### Request Example
{
  "modifier": "Modifier.fillMaxHeight()"
}

### Response
#### Success Response (200)
- **Modifier** (Object) - The resulting modifier instance.
```

--------------------------------

### Create Equal Grid Layout with FlowRow and Weights

Source: https://developer.android.com/develop/ui/compose/layouts/flow

Demonstrates how to create a grid of equally sized items using FlowRow. It uses maxItemsInEachRow to define columns and Modifier.weight(1f) to ensure items expand to fill the available space in each row.

```Kotlin
val rows = 3
val columns = 3
FlowRow(
    modifier = Modifier.padding(4.dp),
    horizontalArrangement = Arrangement.spacedBy(4.dp),
    maxItemsInEachRow = rows
) {
    val itemModifier = Modifier
        .padding(4.dp)
        .height(80.dp)
        .weight(1f)
        .clip(RoundedCornerShape(8.dp))
        .background(MaterialColors.Blue200)
    repeat(rows * columns) {
        Spacer(modifier = itemModifier)
    }
}
```

--------------------------------

### GET /Theme/Properties

Source: https://developer.android.com/develop/ui/compose/designsystems/anatomy?rec=Cj5odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZG9jdW1lbnRhdGlvbhADGAkgASgGMCM6AzMuNw

Accesses the current theme values from the composition tree using a convenience singleton object.

```APIDOC
## GET /Theme/Properties

### Description
A singleton object providing static access to the current theme values via CompositionLocals.

### Method
GET (Property Accessor)

### Endpoint
Theme.[property]

### Parameters
#### Path Parameters
- **property** (String) - Required - The specific system to access (colorSystem, typographySystem, or customSystem).

### Request Example
{
  "property": "colorSystem"
}

### Response
#### Success Response (200)
- **value** (Object) - The current instance of the requested theme system class.

### Response Example
{
  "color": "0xFF3DDC84",
  "gradient": ["0xFFFFFFFF", "0xFFD7EFFF"]
}
```

--------------------------------

### Basic Snackbar Implementation in Jetpack Compose

Source: https://developer.android.com/develop/ui/compose/components/snackbar

Creates a basic snackbar within a Scaffold layout using SnackbarHost and SnackbarHostState. The example demonstrates how to use rememberCoroutineScope to launch a coroutine that displays the snackbar when a FloatingActionButton is clicked.

```Kotlin
val scope = rememberCoroutineScope()
val snackbarHostState = remember { SnackbarHostState() }
Scaffold(
    snackbarHost = {
        SnackbarHost(hostState = snackbarHostState)
    },
    floatingActionButton = {
        ExtendedFloatingActionButton(
            text = { Text("Show snackbar") },
            icon = { Icon(Icons.Filled.Image, contentDescription = "") },
            onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar("Snackbar")
                }
            }
        )
    }
) { contentPadding ->
    // Screen content
}
```

--------------------------------

### GET LocalContext.current

Source: https://developer.android.com/develop/ui/compose/migrate/interoperability-apis/views-in-compose?rec=CkFodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvY29tcG9zaXRpb25sb2NhbBACGAkgASgEMBU6AzMuNw

Accesses the current Android Context from within a composable function using CompositionLocal.

```APIDOC
## GET LocalContext.current\n\n### Description\nRetrieves the current Android Context provided by the nearest CompositionLocalProvider in the UI tree.\n\n### Method\nGET\n\n### Endpoint\nLocalContext.current\n\n### Response\n#### Success Response (200)\n- **context** (Context) - The current Android framework Context instance.\n\n### Response Example\n{\n  "context": "android.content.Context"\n}
```

--------------------------------

### Create NavigationSuiteScaffold with Navigation Items

Source: https://developer.android.com/develop/ui/compose/layouts/adaptive/build-adaptive-navigation

Build the main navigation scaffold by defining navigation suite items using the navigationSuiteItems parameter. Each item represents a top-level destination with icon, label, selection state, and click handler.

```APIDOC
## NavigationSuiteScaffold Setup

### Description
Create a NavigationSuiteScaffold composable with navigation items defined for each destination. The scaffold automatically displays the appropriate navigation UI (bar or rail) based on window size.

### Method
Composable function

### Parameters
- **navigationSuiteItems** (NavigationSuiteScope) - Required - Lambda defining navigation items

### Navigation Items Configuration
```kotlin
NavigationSuiteScaffold(
    navigationSuiteItems = {
        AppDestinations.entries.forEach {
            item(
                icon = {
                    Icon(
                        it.icon,
                        contentDescription = stringResource(it.contentDescription)
                    )
                },
                label = { Text(stringResource(it.label)) },
                selected = it == currentDestination,
                onClick = { currentDestination = it }
            )
        }
    }
) {
    // Destination content goes here
}
```

### Item Parameters
- **icon** (Composable) - Required - Icon composable for the navigation item
- **label** (Composable) - Required - Text label for the navigation item
- **selected** (Boolean) - Required - Whether this item is currently selected
- **onClick** (Lambda) - Required - Callback when the item is clicked

### Behavior
- Iterates through all AppDestinations entries
- Creates a navigation item for each destination
- Highlights the selected item based on currentDestination state
- Updates currentDestination when an item is clicked
```

--------------------------------

### Launch a Service with Glance actionStartService

Source: https://developer.android.com/develop/ui/compose/glance/user-interaction

Shows how to start an Android Service from a Glance App Widget on user interaction. It utilizes the `actionStartService` method, specifying the target Service class and optionally defining if it's a foreground service, to trigger service execution upon a button click.

```kotlin
@Composable
fun MyButton() {
    // ..
    Button(
        text = "Sync",
        onClick = actionStartService<SyncService>(
            isForegroundService = true // define how the service is launched
        )
    )
}
```

--------------------------------

### GET /modifier/layout

Source: https://developer.android.com/develop/ui/compose/layouts/custom?rec=Ck9odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbGF5b3V0cy9pbnRyaW5zaWMtbWVhc3VyZW1lbnRzEAEYCSABKAIwHToDMy43

The base lambda used to modify how an element is measured and laid out by intercepting constraints.

```APIDOC
## GET /modifier/layout

### Description
The core lambda for custom layout logic, providing access to the measurable element and incoming constraints.

### Method
GET

### Endpoint
/modifier/layout

### Parameters
#### Request Body
- **measurable** (Measurable) - Required - The element that can be measured.
- **constraints** (Constraints) - Required - The incoming constraints from the parent layout.

### Request Example
{
  "measurable": "measurable",
  "constraints": "constraints"
}

### Response
#### Success Response (200)
- **layout** (MeasureResult) - A result object containing the width, height, and placement logic.

#### Response Example
{
  "width": "Int",
  "height": "Int",
  "placement": "lambda"
}
```

--------------------------------

### NavigationSuiteScaffold - Adaptive Navigation

Source: https://developer.android.com/develop/ui/compose/navigation?rec=CldodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbWlncmF0ZS9taWdyYXRpb24tc2NlbmFyaW9zL25hdmlnYXRpb24QARgJIAEoAzAjOgMzLjc

Build adaptive navigation UI that automatically displays appropriate navigation components based on window size class. Shows bottom navigation for compact screens and navigation rail for medium and expanded screens.

```APIDOC
## NavigationSuiteScaffold - Adaptive Navigation

### Description
Provides adaptive navigation UI that responds to different screen sizes and device configurations.

### Method
Composable Function

### Endpoint
NavigationSuiteScaffold()

### Parameters
#### Composable Parameters
- **WindowSizeClass** (WindowSizeClass) - Required - Current window size class for the device
- **navigationSuiteItems** (List<NavigationSuiteItem>) - Required - Navigation items to display

### Behavior
- **Compact screens**: Displays bottom navigation bar
- **Medium screens**: Displays navigation rail
- **Expanded screens**: Displays navigation rail

### Related Composables for Adaptive Layouts
- **ListDetailPaneScaffold** - For list-detail canonical layouts
- **SupportingPaneScaffold** - For supporting pane canonical layouts

### Usage Notes
NavigationSuiteScaffold handles primary navigation. For complex adaptive layouts, combine with ListDetailPaneScaffold or SupportingPaneScaffold as needed.
```

--------------------------------

### GET /painterResource

Source: https://developer.android.com/develop/ui/compose/resources?rec=CkhodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZ3JhcGhpY3MvaW1hZ2VzL2NvbXBhcmUQAhgJIAEoAzATOgMzLjc

Loads a vector drawable or rasterized asset format like PNG from the application resources.

```APIDOC
## GET painterResource

### Description
Decodes and parses the content of a resource on the main thread to load vector drawables or rasterized asset formats.

### Method
GET

### Endpoint
painterResource(id: Int)

### Parameters
#### Path Parameters
- **id** (Int) - Required - The resource ID of the drawable (e.g., R.drawable.ic_logo).

### Request Example
```kotlin
painterResource(id = R.drawable.ic_logo)
```

### Response
#### Success Response (200)
- **Painter** (Painter) - A painter object used for drawing in Image or Icon composables.

#### Response Example
```kotlin
Icon(
    painter = painterResource(id = R.drawable.ic_logo),
    contentDescription = null
)
```
```

--------------------------------

### GET Modifier.layout

Source: https://developer.android.com/develop/ui/compose/layouts/custom?rec=Ck9odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbGF5b3V0cy9pbnRyaW5zaWMtbWVhc3VyZW1lbnRzEAEYCSABKAEwEDoDMy43

The core lambda used to modify how a composable element is measured and laid out on the screen.

```APIDOC
## GET Modifier.layout

### Description
Provides access to the measurable element and its incoming constraints to manually define measurement and placement logic.

### Method
GET

### Endpoint
layout(measurable, constraints)

### Parameters
#### Path Parameters
- **measurable** (Measurable) - Required - The element to be measured.
- **constraints** (Constraints) - Required - The incoming constraints from the parent layout.

### Request Example
{
  "usage": "layout { measurable, constraints -> ... }"
}

### Response
#### Success Response (200)
- **layout** (MeasureResult) - A result object containing the width, height, and placement logic for the element.

#### Response Example
{
  "width": "Int",
  "height": "Int",
  "placement": "placeable.placeRelative(x, y)"
}
```

--------------------------------

### Elevation Overlays - Automatic Application with Surface

Source: https://developer.android.com/develop/ui/compose/designsystems/material?rec=ClJodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZGVzaWduc3lzdGVtcy9tYXRlcmlhbDItbWF0ZXJpYWwzEAEYCSABKAUwGjoDMy43

Demonstrates how `Surface` and other Material composables automatically apply elevation overlays in dark themes, lightening their backgrounds based on their elevation.

```APIDOC
## Elevation Overlays - Automatic Application

### Description
Material Design surfaces in dark themes with higher elevations receive elevation overlays, which lighten their background. The `Surface` composable applies these overlays automatically when using dark colors.

### Method
N/A (Compose UI Component Usage)

### Endpoint
N/A

### Parameters
#### Request Body
- **elevation** (androidx.compose.ui.unit.Dp) - Required - The elevation of the surface.
- **color** (androidx.compose.ui.graphics.Color) - Required - The background color of the surface. This color will be adjusted for elevation.

### Request Example
```kotlin
Surface(
    elevation = 2.dp,
    color = MaterialTheme.colors.surface // color will be adjusted for elevation
) { /*...*/ }
```

### Response
#### Success Response (UI Rendering)
- **N/A** - The UI component renders with the specified elevation and color, with the color automatically adjusted for dark theme elevation overlays.

#### Response Example
N/A
```

--------------------------------

### Organize Multiple Keyboard Shortcut Groups in Kotlin

Source: https://developer.android.com/develop/ui/compose/touch-input/keyboard-input/keyboard-shortcuts-helper

Shows how to create and organize multiple KeyboardShortcutGroup objects within onProvideKeyboardShortcuts() to categorize shortcuts by use case. This example demonstrates two groups: 'Cursor movement' and 'Message editing', each containing relevant shortcuts that are displayed as separate categories in the Keyboard Shortcuts Helper interface.

```Kotlin
override fun onProvideKeyboardShortcuts(
    data: MutableList<KeyboardShortcutGroup>?,
    menu: Menu?,
    deviceId: Int
) {
    val cursorMovement = KeyboardShortcutGroup(
        "Cursor movement",
        listOf(
            KeyboardShortcutInfo("Up", KeyEvent.KEYCODE_P, KeyEvent.META_CTRL_ON),
            KeyboardShortcutInfo("Down", KeyEvent.KEYCODE_N, KeyEvent.META_CTRL_ON),
            KeyboardShortcutInfo("Forward", KeyEvent.KEYCODE_F, KeyEvent.META_CTRL_ON),
            KeyboardShortcutInfo("Backward", KeyEvent.KEYCODE_B, KeyEvent.META_CTRL_ON),
        )
    )

    val messageEdit = KeyboardShortcutGroup(
        "Message editing",
        listOf(
            KeyboardShortcutInfo("Select All", KeyEvent.KEYCODE_A, KeyEvent.META_CTRL_ON),
            KeyboardShortcutInfo("Send a message", KeyEvent.KEYCODE_ENTER, KeyEvent.META_SHIFT_ON)
        )
    )

    data?.add(cursorMovement)
    data?.add(messageEdit)
}
```

--------------------------------

### GET WindowMetrics

Source: https://developer.android.com/develop/ui/compose/layouts/adaptive/support-multi-window-mode?hl=th

Retrieves the bounds of the application window for the current or maximum potential windowing state.

```APIDOC
## GET WindowMetrics

### Description
Returns the WindowMetrics for the current windowing state or the largest potential windowing state of the system. Replaces deprecated Display.getSize() and Display.getMetrics().

### Method
GET

### Endpoint
WindowManager.getCurrentWindowMetrics()

### Parameters
#### Path Parameters
- **N/A** (void) - No path parameters.

#### Request Body
- **N/A** (void) - No request body.

### Request Example
val windowMetrics = context.getSystemService(WindowManager::class.java).currentWindowMetrics

### Response
#### Success Response (200)
- **bounds** (Rect) - The size and position of the window.
- **windowInsets** (WindowInsets) - The insets for the window.
```

--------------------------------

### Implement Dark Theme Switching in Jetpack Compose

Source: https://developer.android.com/develop/ui/compose/designsystems/material?rec=ClJodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZGVzaWduc3lzdGVtcy9tYXRlcmlhbDItbWF0ZXJpYWwzEAEYCSABKAUwKjoDMy43

Explains how to create a custom theme wrapper that toggles between LightColors and DarkColors based on the system theme or a boolean parameter. It also shows how to check the current theme state to swap assets dynamically.

```Kotlin
@Composable
fun MyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColors else LightColors,
        /*...*/
        content = content
    )
}

// Checking theme state
val isLightTheme = MaterialTheme.colors.isLight
Icon(
    painterResource(
        id = if (isLightTheme) {
            R.drawable.ic_sun_24
        } else {
            R.drawable.ic_moon_24
        }
    ),
    contentDescription = "Theme"
)
```

--------------------------------

### Retrieve a Formatted String Resource in Jetpack Compose

Source: https://developer.android.com/develop/ui/compose/resources?rec=CjtodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdGV4dC9mb250cxABGAkgASgDMBM6AzMuNw

Illustrates using `stringResource` with positional arguments to format a string dynamically. The example shows how to pass multiple arguments to a string defined with placeholders like `%1$s` and `%2$d` in `res/values/strings.xml`.

```xml
<string name="congratulate">Happy %1$s %2$d</string>
```

```kotlin
Text(
    text = stringResource(R.string.congratulate, "New Year", 2021)
)
```

--------------------------------

### Basic Slider Implementation

Source: https://developer.android.com/develop/ui/compose/components/slider

A straightforward continuous slider that allows users to select any value between 0.0 and 1.0. This example demonstrates the minimal required parameters for the Slider composable.

```APIDOC
## Slider Composable - Basic Implementation

### Description
Creates a basic continuous slider allowing users to select any value within the default range of 0.0 to 1.0.

### Composable
`Slider`

### Key Parameters
- **value** (Float) - Required - The current value of the slider
- **onValueChange** (lambda: (Float) -> Unit) - Required - Callback invoked when the slider value changes
- **enabled** (Boolean) - Optional - Indicates if the user can interact with the slider (default: true)

### Component Structure
- **Track** - Horizontal bar representing the range of values
- **Thumb** - Draggable control element for value selection
- **Tick marks** - Optional visual markers along the track

### Code Example
```kotlin
@Preview
@Composable
fun SliderMinimalExample() {
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    Column {
        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it }
        )
        Text(text = sliderPosition.toString())
    }
}
```

### Use Cases
- Adjust settings with range values (volume, brightness)
- Filter data in graphs (price ranges)
- Collect user input (ratings, reviews)
```

--------------------------------

### GET MaterialTheme.colors

Source: https://developer.android.com/develop/ui/compose/designsystems/material?rec=Cj5odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZGVzaWduc3lzdGVtcxABGAkgASgFMCo6AzMuNw

Retrieves the current color palette from the nearest MaterialTheme provider in the composition tree.

```APIDOC
## GET MaterialTheme.colors

### Description
Accesses the current Colors instance provided to the MaterialTheme composable at the current point in the UI tree.

### Method
GET

### Endpoint
MaterialTheme.colors

### Parameters
#### Path Parameters
- **None** - This is a property access within the composition context.

### Request Example
{
  "action": "Access current theme primary color"
}

### Response
#### Success Response (200)
- **primary** (Color) - The primary color of the current theme.
- **secondary** (Color) - The secondary color of the current theme.

### Response Example
{
  "primary": "#6200EE",
  "secondary": "#03DAC6"
}
```

--------------------------------

### Set up Scaffold with setContent in Kotlin

Source: https://developer.android.com/develop/ui/compose/migrate/migration-scenarios/coordinator-layout

Obtain a reference to the ComposeView and call setContent to establish Scaffold as the root composable. This Kotlin code initializes the Compose environment and sets up the basic Scaffold structure with proper sizing modifiers.

```Kotlin
composeView.setContent {
    Scaffold(Modifier.fillMaxSize()) { contentPadding ->
        // Scaffold contents
        // ...
    }
}
```

--------------------------------

### Complete Animated List Implementation

Source: https://developer.android.com/develop/ui/compose/lists?rec=Cj1odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2Uvc3RhdGUtc2F2aW5nEAMYCSABKAMwGzoDMy43

A full Composable example demonstrating a LazyColumn that uses unique keys and the animateItem modifier to handle list transitions for adding, removing, or reordering strings.

```Kotlin
@Composable
fun ListAnimatedItems(
    items: List<String>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier) {
        // Use a unique key per item, so that animations work as expected.
        items(items, key = { it }) {
            ListItem(
                headlineContent = { Text(it) },
                modifier = Modifier
                    .animateItem()
                    .fillParentMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 0.dp),
            )
        }
    }
}
```

--------------------------------

### GET /LocalContentColor

Source: https://developer.android.com/develop/ui/compose/compositionlocal?rec=Cj5odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZG9jdW1lbnRhdGlvbhABGAkgASgCMAs6AzMuNw

Accesses the preferred content color for text and iconography to ensure contrast against the background.

```APIDOC
## GET /LocalContentColor

### Description
Retrieves the current preferred color for content. This is automatically updated by components like Surface to maintain accessibility.

### Method
GET

### Endpoint
/LocalContentColor

### Parameters
#### Path Parameters
- **current** (Property) - Required - Accesses the value provided by the nearest ancestor.

### Request Example
{
  "property": "LocalContentColor.current"
}

### Response
#### Success Response (200)
- **color** (Color) - The Color value currently assigned to the content scope.
```

--------------------------------

### Transition with AnimatedVisibility and AnimatedContent

Source: https://developer.android.com/develop/ui/compose/animation/value-based?rec=Ck5odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdG9vbGluZy9pdGVyYXRpdmUtZGV2ZWxvcG1lbnQQAhgJIAEoBTAfOgMzLjc

Demonstrates how to use AnimatedVisibility and AnimatedContent as extension functions of Transition. This approach hoists enter, exit, and sizeTransform animations, allowing you to observe state changes from outside the composables and trigger animations based on the parent transition's targetState.

```APIDOC
## Transition.AnimatedVisibility and Transition.AnimatedContent

### Description
Extension functions that allow AnimatedVisibility and AnimatedContent to be used as part of a Transition, hoisting their animations and enabling external observation of state changes.

### Usage Pattern

#### Create a Transition
```kotlin
var selected by remember { mutableStateOf(false) }
val transition = updateTransition(selected, label = "selected state")
```

#### Animate Values
```kotlin
val borderColor by transition.animateColor(label = "border color") { isSelected ->
    if (isSelected) Color.Magenta else Color.White
}
val elevation by transition.animateDp(label = "elevation") { isSelected ->
    if (isSelected) 10.dp else 2.dp
}
```

#### Use AnimatedVisibility Extension
```kotlin
transition.AnimatedVisibility(
    visible = { targetSelected -> targetSelected },
    enter = expandVertically(),
    exit = shrinkVertically()
) {
    Text(text = "It is fine today.")
}
```

#### Use AnimatedContent Extension
```kotlin
transition.AnimatedContent { targetState ->
    if (targetState) {
        Text(text = "Selected")
    } else {
        Icon(imageVector = Icons.Default.Phone, contentDescription = "Phone")
    }
}
```

### Key Features
- **Hoisted Animations**: All enter, exit, and sizeTransform animations are hoisted into the Transition
- **External Observation**: Observe AnimatedVisibility/AnimatedContent state changes from outside
- **Lambda-based Visibility**: Uses a lambda converting parent transition's target state to boolean instead of a boolean visible parameter
- **Coordinated Animations**: Multiple animations can be coordinated through a single Transition instance

### Benefits
- Centralized animation control
- Better state management
- Ability to observe animation state externally
- Cleaner separation of concerns
```

--------------------------------

### Elevation Overlays - Custom Scenarios with LocalElevationOverlay

Source: https://developer.android.com/develop/ui/compose/designsystems/material?rec=ClJodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZGVzaWduc3lzdGVtcy9tYXRlcmlhbDItbWF0ZXJpYWwzEAEYCSABKAUwGjoDMy43

Shows how to manually apply elevation overlays using `LocalElevationOverlay` for custom composables that do not directly involve a `Surface`.

```APIDOC
## Elevation Overlays - Custom Scenarios

### Description
For custom scenarios that don’t involve a `Surface`, use `LocalElevationOverlay`, a `CompositionLocal` containing the `ElevationOverlay` used by `Surface` components, to manually apply elevation overlays.

### Method
N/A (Compose UI Utility Usage)

### Endpoint
N/A

### Parameters
#### Request Body
- **color** (androidx.compose.ui.graphics.Color) - Required - The base color to which the overlay should be applied.
- **elevation** (androidx.compose.ui.unit.Dp) - Required - The elevation value to use for the overlay calculation.

### Request Example
```kotlin
val color = MaterialTheme.colors.surface
val elevation = 4.dp
val overlaidColor = LocalElevationOverlay.current?.apply(
    color, elevation
)
```

### Response
#### Success Response (UI Rendering)
- **overlaidColor** (androidx.compose.ui.graphics.Color) - The color with the elevation overlay applied.

#### Response Example
N/A
```

--------------------------------

### Implement Navigation Drawer in Material 2 and Material 3

Source: https://developer.android.com/develop/ui/compose/designsystems/material2-material3?rec=CkdodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZGVzaWduc3lzdGVtcy9tYXRlcmlhbBACGAkgASgIMBM6AzMuNw

Compares drawer implementations. Material 2 includes drawer parameters directly in the Scaffold, while Material 3 requires wrapping the Scaffold inside a 'ModalNavigationDrawer' and using 'ModalDrawerSheet' for the content.

```Kotlin (Material 2)
import androidx.compose.material.DrawerValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState

val scaffoldState = rememberScaffoldState(
    drawerState = rememberDrawerState(DrawerValue.Closed)
)
val scope = rememberCoroutineScope()

Scaffold(
    scaffoldState = scaffoldState,
    drawerContent = { ... },
    drawerGesturesEnabled = ...,
    drawerShape = ...,
    drawerElevation = ...,
    drawerBackgroundColor = ...,
    drawerContentColor = ...,
    drawerScrimColor = ...,
    content = {
        ...
        scope.launch {
            scaffoldState.drawerState.open()
        }
    }
)
```

```Kotlin (Material 3)
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState

val drawerState = rememberDrawerState(DrawerValue.Closed)
val scope = rememberCoroutineScope()

ModalNavigationDrawer(
    drawerState = drawerState,
    drawerContent = {
        ModalDrawerSheet(
            drawerShape = ...,
            drawerTonalElevation = ...,
            drawerContainerColor = ...,
            drawerContentColor = ...,
            content = { ... }
        )
    },
    gesturesEnabled = ...,
    scrimColor = ...,
    content = {
        Scaffold(
            content = {
                ...
                scope.launch {
                    drawerState.open()
                }
            }
        )
    }
)
```

--------------------------------

### Configure HorizontalPager Content Padding for Page Alignment

Source: https://developer.android.com/develop/ui/compose/layouts/pager

Demonstrates three approaches to align pages using contentPadding in HorizontalPager: start padding aligns pages toward the end, equal horizontal padding centers pages, and end padding aligns pages toward the start. The pagerState manages pagination state with a fixed page count.

```Kotlin
val pagerState = rememberPagerState(pageCount = {
    4
})
HorizontalPager(
    state = pagerState,
    contentPadding = PaddingValues(start = 64.dp),
) { page ->
    // page content
}
```

```Kotlin
val pagerState = rememberPagerState(pageCount = {
    4
})
HorizontalPager(
    state = pagerState,
    contentPadding = PaddingValues(horizontal = 32.dp),
) { page ->
    // page content
}
```

```Kotlin
val pagerState = rememberPagerState(pageCount = {
    4
})
HorizontalPager(
    state = pagerState,
    contentPadding = PaddingValues(end = 64.dp),
) { page ->
    // page content
}
```

--------------------------------

### UI State Persistence Strategy Guide

Source: https://developer.android.com/develop/ui/compose/state-saving?rec=CjZodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbGlzdHMQAhgJIAEoBTAXOgMzLjc

Provides guidance on selecting the appropriate API for persisting UI state based on whether the state is used in UI logic or business logic, and whether configuration changes or process death need to be handled.

```APIDOC
## UI State Persistence Strategy

### Description
Guide for choosing between rememberSaveable and SavedStateHandle based on state location and persistence requirements.

### State Persistence Decision Matrix

| Event | UI Logic | Business Logic in ViewModel |
|-------|----------|-----------------------------|
| Configuration changes | rememberSaveable | Automatic |
| System-initiated process death | rememberSaveable | SavedStateHandle |

### API Selection Guidelines

#### Use rememberSaveable When:
- State is used in UI logic (Compose composables)
- You need to handle configuration changes
- You need to handle system-initiated process death
- State is small and UI-specific

#### Use SavedStateHandle When:
- State is held in a ViewModel
- State is used in business logic
- You need to handle system-initiated process death
- State needs to be shared across multiple UI components

### Best Practices
- Store only the minimum necessary data to restore UI state (e.g., IDs rather than full objects)
- Use SavedStateHandle for small amounts of UI state in ViewModels
- Fetch heavy data (like profile details) from the data layer using stored IDs
- Combine bundle APIs with other storage mechanisms for complete state management

### Example Scenario
```kotlin
// Business logic state in ViewModel - use SavedStateHandle
class ChannelViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val savedFilterType: StateFlow<ChannelsFilterType> = 
        savedStateHandle.getStateFlow("filter_key", ChannelsFilterType.ALL_CHANNELS)
}

// UI logic state in Compose - use rememberSaveable
@Composable
fun ChannelListScreen() {
    var scrollPosition by rememberSaveable { mutableStateOf(0) }
}
```
```

--------------------------------

### Optimize State Reads - Best Practices

Source: https://developer.android.com/develop/ui/compose/phases?rec=Ck1odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZ3JhcGhpY3MvaW1hZ2VzL29wdGltaXphdGlvbhADGAkgASgFMBs6AzMuNw

Provides guidance on minimizing work by reading state in the appropriate phase. Includes a suboptimal example of parallax scrolling and explains why reading state in composition phase causes unnecessary recomposition overhead.

```APIDOC
## Optimize State Reads

### Description
Because Compose performs localized state read tracking, you can minimize the amount of work performed by reading each state in an appropriate phase. Strategic state read placement is critical for performance optimization.

### Suboptimal Pattern - State Read in Composition Phase
```kotlin
Box {
    val listState = rememberLazyListState()

    Image(
        // ...
        // Non-optimal implementation!
        Modifier.offset(
            with(LocalDensity.current) {
                // State read of firstVisibleItemScrollOffset in composition
                (listState.firstVisibleItemScrollOffset / 2).toDp()
            }
        )
    )

    LazyColumn(state = listState) {
        // ...
    }
}
```

### Why This Is Suboptimal
- State read occurs in **composition phase**
- Every scroll event changes `firstVisibleItemScrollOffset`
- Each scroll triggers full recomposition of entire Box content
- Content is reevaluated, measured, laid out, and drawn
- Only the **position** changes, not the content itself

### Optimization Strategy
- Move state read to **layout phase** using `Modifier.offset { … }` lambda
- Only layout and drawing phases will be triggered on scroll
- Composition phase is skipped entirely
- Significantly improves scroll performance

### Key Principle
Read state in the **latest phase** where it's needed:
1. **Drawing phase** - For visual-only changes (fastest)
2. **Layout phase** - For position/size changes (medium)
3. **Composition phase** - For content/structure changes (slowest)
```

--------------------------------

### Animate Color and Content Size Transitions

Source: https://developer.android.com/develop/ui/compose/tutorial?hl=hi

Shows how to use animateColorAsState for smooth color transitions and animateContentSize to animate layout changes when content visibility changes.

```Kotlin
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize

val surfaceColor by animateColorAsState(
    if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
)

Surface(
    shape = MaterialTheme.shapes.medium,
    shadowElevation = 1.dp,
    color = surfaceColor,
    modifier = Modifier.animateContentSize().padding(1.dp)
) {
    Text(
        text = msg.body,
        modifier = Modifier.padding(all = 4.dp),
        maxLines = if (isExpanded) Int.MAX_VALUE else 1,
        style = MaterialTheme.typography.bodyMedium
    )
}
```

--------------------------------

### Implement a Swipe-to-Dismiss/Update Composable for a Todo Item in Kotlin

Source: https://developer.android.com/develop/ui/compose/touch-input/user-interactions/swipe-to-dismiss

This Composable function `TodoListItem` integrates `SwipeToDismissBox` to enable swipe interactions on a to-do item. Swiping from start to end toggles the item's 'done' status, while swiping from end to start removes it. The background content is customized to display relevant icons (checkbox or delete) based on the swipe direction.

```kotlin
@Composable
fun TodoListItem(
    todoItem: TodoItem,
    onToggleDone: (TodoItem) -> Unit,
    onRemove: (TodoItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == StartToEnd) onToggleDone(todoItem)
            else if (it == EndToStart) onRemove(todoItem)
            // Reset item when toggling done status
            it != StartToEnd
        }
    )

    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        modifier = modifier.fillMaxSize(),
        backgroundContent = {
            when (swipeToDismissBoxState.dismissDirection) {
                StartToEnd -> {
                    Icon(
                        if (todoItem.isItemDone) Icons.Default.CheckBox else Icons.Default.CheckBoxOutlineBlank,
                        contentDescription = if (todoItem.isItemDone) "Done" else "Not done",
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Blue)
                            .wrapContentSize(Alignment.CenterStart)
                            .padding(12.dp),
                        tint = Color.White
                    )
                }
                EndToStart -> {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Remove item",
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Red)
                            .wrapContentSize(Alignment.CenterEnd)
                            .padding(12.dp),
                        tint = Color.White
                    )
                }
                Settled -> {}
            }
        }
    ) {
        ListItem(
            headlineContent = { Text(todoItem.itemDescription) },
            supportingContent = { Text("swipe me to update or remove.") }
        )
    }
}
```

--------------------------------

### DrawScope.drawLine()

Source: https://developer.android.com/develop/ui/compose/graphics/draw/overview?rec=CkhodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbGF5b3V0cy9hbGlnbm1lbnQtbGluZXMQARgJIAEoAzAhOgMzLjc

Draws a diagonal line on the canvas by specifying start and end offsets, demonstrating the coordinate system.

```APIDOC
## DrawScope.drawLine()

### Description
Draws a line between two points on the canvas. The origin (0,0) is at the top-left, x increases right, y increases down.

### Method
Function Call

### Endpoint
DrawScope.drawLine(start: Offset, end: Offset, color: Color)

### Parameters
#### Path Parameters
- No Path Parameters

#### Query Parameters
- No Query Parameters

#### Request Body
- **start** (Offset) - Required - The starting point of the line.
- **end** (Offset) - Required - The ending point of the line.
- **color** (Color) - Required - The color of the line.

### Request Example
```
Canvas(modifier = Modifier.fillMaxSize()) {
    val canvasWidth = size.width
    val canvasHeight = size.height
    drawLine(
        start = Offset(x = canvasWidth, y = 0f),
        end = Offset(x = 0f, y = canvasHeight),
        color = Color.Blue
    )
}
```

### Response
#### Success Response (200)
The line is drawn on the canvas.

#### Response Example
No direct response object for drawing operations.
```

--------------------------------

### Modal Navigation Drawer with State Management

Source: https://developer.android.com/develop/ui/compose/components/drawer

Demonstrates how to manage drawer state using DrawerState and CoroutineScope. Includes opening and closing the drawer programmatically in response to UI events like button clicks.

```APIDOC
## ModalNavigationDrawer - State Control

### Description
Manages drawer open/close state using DrawerState and CoroutineScope. Provides programmatic control over drawer behavior through suspending functions.

### Component
ModalNavigationDrawer with DrawerState

### Parameters
- **drawerState** (DrawerState) - Required - State object controlling drawer open/close behavior
- **drawerContent** (Composable) - Required - Lambda defining drawer content
- **content** (Composable) - Required - Lambda defining screen content with Scaffold

### DrawerState Properties and Functions
- **open()** - Suspending function to open the drawer
- **close()** - Suspending function to close the drawer
- **isClosed** - Boolean property indicating if drawer is closed
- **isOpen** - Boolean property indicating if drawer is open

### Code Example
```kotlin
val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
val scope = rememberCoroutineScope()
ModalNavigationDrawer(
    drawerState = drawerState,
    drawerContent = {
        ModalDrawerSheet { /* Drawer content */ }
    },
) {
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Show drawer") },
                icon = { Icon(Icons.Filled.Add, contentDescription = "") },
                onClick = {
                    scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                }
            )
        }
    ) { contentPadding ->
        // Screen content
    }
}
```

### Key Concepts
- **rememberDrawerState()** - Creates and remembers DrawerState across recompositions
- **rememberCoroutineScope()** - Provides CoroutineScope for launching suspending functions
- **scope.launch** - Launches coroutine to call open()/close() functions
- **DrawerValue.Closed** - Initial state constant for closed drawer
```

--------------------------------

### GET /compose/top-app-bar

Source: https://developer.android.com/develop/ui/compose/migrate/migration-scenarios/coordinator-layout?rec=ClpodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbWlncmF0ZS9taWdyYXRpb24tc2NlbmFyaW9zL3JlY3ljbGVyLXZpZXcQAhgJIAEoBjArOgMzLjc

Configures a collapsing and expanding TopAppBar using scroll behaviors and nested scroll connections within a Scaffold.

```APIDOC
## GET /compose/top-app-bar

### Description
Implements a toolbar that reacts to scroll events, collapsing or expanding based on the user's interaction with scrollable content.

### Method
GET

### Endpoint
/compose/top-app-bar

### Parameters
#### Path Parameters
- **scrollBehavior** (TopAppBarScrollBehavior) - Required - Created via TopAppBarDefaults.enterAlwaysScrollBehavior() or exitUntilCollapsedScrollBehavior().

#### Request Body
- **modifier** (Modifier) - Required - Must include nestedScroll(scrollBehavior.nestedScrollConnection) on the Scaffold to receive scroll events.

### Request Example
{
  "scrollBehavior": "TopAppBarDefaults.enterAlwaysScrollBehavior()",
  "modifier": "Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)"
}

### Response
#### Success Response (200)
- **UI** (Composable) - A rendered Scaffold with a reactive TopAppBar that collapses/expands on scroll.

### Response Example
{
  "status": "success",
  "component": "TopAppBar"
}
```

--------------------------------

### GET /LocalElevationOverlay

Source: https://developer.android.com/develop/ui/compose/designsystems/material?rec=Ck1odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbWlncmF0ZS9vdGhlci1jb25zaWRlcmF0aW9ucxACGAkgASgFMAs6AzMuNw

Applies elevation overlays to surfaces in dark themes, lightening their background based on elevation level.

```APIDOC
## GET /LocalElevationOverlay

### Description
Handles elevation overlays for dark theme surfaces to communicate depth.

### Method
GET

### Endpoint
/LocalElevationOverlay

### Parameters
#### Path Parameters
- **elevation** (Dp) - Required - The elevation level of the surface.

### Request Example
{
  "elevation": "4.dp"
}

### Response
#### Success Response (200)
- **overlaidColor** (Color) - The color adjusted for elevation.

#### Response Example
{
  "overlaidColor": "#FF121212"
}
```

--------------------------------

### Responsive Layouts with BoxWithConstraints

Source: https://developer.android.com/develop/ui/compose/layouts/basics?rec=CkdodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdG9vbGluZy9lZGl0b3ItYWN0aW9ucxABGAkgASgGMBY6AzMuNw

Explains how to create responsive layouts that adapt to different screen orientations and form factors using BoxWithConstraints to access measurement constraints from the parent.

```APIDOC
## Responsive Layouts

### Description
Layouts should be designed considering different screen orientations and form factor sizes. Compose provides mechanisms to facilitate adapting composable layouts to various screen configurations.

### BoxWithConstraints

#### Purpose
Allows you to access measurement constraints from the parent and design layouts accordingly.

#### How It Works
- Provides measurement constraints in the scope of the content lambda
- Enables composing different layouts for different screen configurations
- Allows responsive design based on available space

#### Code Example
```kotlin
@Composable
fun WithConstraintsComposable() {
    BoxWithConstraints {
        Text("My minHeight is $minHeight while my maxWidth is $maxWidth")
    }
}
```

#### Available Constraints
- **minHeight**: Minimum height available from parent
- **maxWidth**: Maximum width available from parent
- **minWidth**: Minimum width available from parent
- **maxHeight**: Maximum height available from parent

### Use Cases
- Adapting layouts for portrait vs landscape orientation
- Adjusting layouts for different device form factors (phone, tablet, foldable)
- Creating responsive designs that scale with available space
- Conditional layout composition based on screen dimensions

### Related Documentation
- Compose gestures documentation: For scrollable layouts
- Compose lists documentation: For lists and lazy lists
```

--------------------------------

### GET /runtime/remember

Source: https://developer.android.com/develop/ui/compose/state

Caches a value in the Composition to avoid expensive recalculations during recomposition, with support for key-based invalidation.

```APIDOC
## GET /runtime/remember

### Description
Stores the result of a calculation in the Composition to survive recompositions. If keys are provided, the cache is invalidated and the calculation is re-executed when any key changes.

### Method
GET

### Endpoint
androidx.compose.runtime.remember

### Parameters
#### Query Parameters
- **key1** (Any) - Optional - An input to the calculation. If it changes, the cache is invalidated.
- **key2** (Any) - Optional - Additional input for invalidation.

### Request Example
{
  "key1": "avatarRes",
  "calculation": "{ ShaderBrush(...) }"
}

### Response
#### Success Response (200)
- **value** (T) - The cached or newly calculated value.
```

--------------------------------

### GET /activity-result

Source: https://developer.android.com/develop/ui/compose/libraries?rec=CldodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbWlncmF0ZS9taWdyYXRpb24tc2NlbmFyaW9zL25hdmlnYXRpb24QAhgJIAEoAzAVOgMzLjc

Explains how to use rememberLauncherForActivityResult to handle external activity results, such as picking an image, directly within a composable function.

```APIDOC
## GET /activity-result

### Description
Registers a request to start an activity for result within a Composable. This API ensures the launcher survives recomposition and is cleaned up properly.

### Method
Composable Function

### Endpoint
androidx.activity.compose.rememberLauncherForActivityResult

### Parameters
#### Path Parameters
- **contract** (ActivityResultContract) - Required - The contract defining the input type and output result type.
- **onResult** (Lambda) - Required - A callback invoked when the activity result is returned.

### Request Example
{
  "contract": "ActivityResultContracts.GetContent()",
  "launch_input": "image/*"
}

### Response
#### Success Response (200)
- **result** (Uri?) - The URI of the content selected by the user, or null if cancelled.

#### Response Example
{
  "uri": "content://media/external/images/media/123"
}
```

--------------------------------

### GET /animatedVectorResource

Source: https://developer.android.com/develop/ui/compose/resources?rec=CkhodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZ3JhcGhpY3MvaW1hZ2VzL2NvbXBhcmUQAhgJIAEoAzATOgMzLjc

Loads an animated vector drawable XML and returns an AnimatedImageVector instance for use with a painter.

```APIDOC
## GET animatedVectorResource

### Description
Loads an animated vector drawable XML resource and returns an instance of AnimatedImageVector.

### Method
GET

### Endpoint
AnimatedImageVector.animatedVectorResource(id: Int)

### Parameters
#### Path Parameters
- **id** (Int) - Required - The resource ID of the animated vector drawable.

### Request Example
```kotlin
AnimatedImageVector.animatedVectorResource(R.drawable.ic_hourglass_animated)
```

### Response
#### Success Response (200)
- **AnimatedImageVector** (AnimatedImageVector) - The loaded animated vector instance.

#### Response Example
```kotlin
val image = AnimatedImageVector.animatedVectorResource(R.drawable.ic_hourglass_animated)
val painter = rememberAnimatedVectorPainter(image, atEnd = false)
```
```

--------------------------------

### Scaffold Import and Basic Usage (M2 vs M3)

Source: https://developer.android.com/develop/ui/compose/designsystems/material2-material3?rec=CkVodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZGVzaWduc3lzdGVtcy9jdXN0b20QAhgJIAEoBzAKOgMzLjc

Compares the basic import and instantiation of the `Scaffold` composable in Material 2 and Material 3.

```APIDOC
## Scaffold Import and Basic Usage

### Description
The `Scaffold` composable exists in both Material 2 (`androidx.compose.material`) and Material 3 (`androidx.compose.material3`), but their import paths and parameter sets differ.

### Material 2 Usage
```kotlin
import androidx.compose.material.Scaffold

Scaffold(
    // M2 scaffold parameters
)
```

### Material 3 Usage
```kotlin
import androidx.compose.material3.Scaffold

Scaffold(
    // M3 scaffold parameters
)
```
```

--------------------------------

### Create Basic Compose UI Test with ComposeTestRule

Source: https://developer.android.com/develop/ui/compose/testing?rec=CkZodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYWNjZXNzaWJpbGl0eS90ZXN0aW5nEAEYCSABKAQwGDoDMy43

Demonstrates a typical Compose UI test structure using createComposeRule(). The test sets Compose content, performs user interactions like clicks, and verifies UI state with assertions. Use createAndroidComposeRule if you need access to an activity instance.

```kotlin
// file: app/src/androidTest/java/com/package/MyComposeTest.kt

class MyComposeTest {

    @get:Rule val composeTestRule = createComposeRule()
    // use createAndroidComposeRule<YourActivity>() if you need access to
    // an activity

    @Test
    fun myTest() {
        // Start the app
        composeTestRule.setContent {
            MyAppTheme {
                MainScreen(uiState = fakeUiState, /*...*/)
            }
        }

        composeTestRule.onNodeWithText("Continue").performClick()

        composeTestRule.onNodeWithText("Welcome").assertIsDisplayed()
    }
}
```

--------------------------------

### GET /stringResource

Source: https://developer.android.com/develop/ui/compose/resources?rec=CkhodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZ3JhcGhpY3MvaW1hZ2VzL2NvbXBhcmUQAhgJIAEoAzATOgMzLjc

Retrieves a string resource from the XML files, supporting both static strings and positional formatting.

```APIDOC
## GET stringResource\n\n### Description\nRetrieves a string statically defined in your XML resources (e.g., strings.xml).\n\n### Method\nGET (Compose Function)\n\n### Endpoint\nstringResource(id: Int, varargs formatArgs: Any)\n\n### Parameters\n#### Path Parameters\n- **id** (Int) - Required - The resource ID of the string (e.g., R.string.name).\n\n#### Query Parameters\n- **formatArgs** (Any) - Optional - Positional arguments for string formatting (e.g., %1$s, %2$d).\n\n### Request Example\nstringResource(R.string.congratulate, "New Year", 2021)\n\n### Response\n#### Success Response (200)\n- **text** (String) - The resolved string value from resources.
```

--------------------------------

### Create Bottom App Bar with Actions and FAB in Jetpack Compose

Source: https://developer.android.com/develop/ui/compose/components/app-bars

Shows how to configure a BottomAppBar within a Scaffold. The example includes multiple action icons on the left and a FloatingActionButton on the right, utilizing standard Material 3 slot parameters.

```Kotlin
@Composable
fun BottomAppBarExample() {
    Scaffold(
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(Icons.Filled.Check, contentDescription = "Localized description")
                    }
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = "Localized description",
                        )
                    }
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            Icons.Filled.Mic,
                            contentDescription = "Localized description",
                        )
                    }
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            Icons.Filled.Image,
                            contentDescription = "Localized description",
                        )
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { /* do something */ },
                        containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                    ) {
                        Icon(Icons.Filled.Add, "Localized description")
                    }
                }
            )
        },
    ) { innerPadding ->
        Text(
            modifier = Modifier.padding(innerPadding),
            text = "Example of a scaffold with a bottom app bar."
        )
    }
}
```

--------------------------------

### Custom Linear Gradient Brush with Tile Mode

Source: https://developer.android.com/develop/ui/compose/graphics/draw/brush

Demonstrates how to create a custom brush by extending ShaderBrush and implementing createShader to define a linear gradient with custom tile mode and size calculations. This example divides the drawing area by 4 to repeat the pattern multiple times.

```APIDOC
## Custom Linear Gradient Brush

### Description
Create a custom brush by extending ShaderBrush and implementing the createShader function to define a linear gradient with custom tile mode and size calculations.

### Method
Compose UI Modifier Extension

### Endpoint
ShaderBrush.createShader(size: Size): Shader

### Parameters
#### Method Parameters
- **size** (Size) - Required - The size of the drawing area used to calculate gradient dimensions

### Request Example
```kotlin
val listColors = listOf(Color.Yellow, Color.Red, Color.Blue)
val customBrush = remember {
    object : ShaderBrush() {
        override fun createShader(size: Size): Shader {
            return LinearGradientShader(
                colors = listColors,
                from = Offset.Zero,
                to = Offset(size.width / 4f, 0f),
                tileMode = TileMode.Mirror
            )
        }
    }
}
Box(
    modifier = Modifier
        .requiredSize(200.dp)
        .background(customBrush)
)
```

### Response
#### Success Response
- **Shader** (Shader) - A LinearGradientShader object that defines the brush pattern
- **Pattern Repetition** (Int) - Pattern repeats 4 times across the drawing area
- **Tile Mode** (TileMode) - Mirror mode applied for seamless pattern repetition
```

--------------------------------

### GET /resources/dimensions-colors

Source: https://developer.android.com/develop/ui/compose/resources?rec=CklodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZ3JhcGhpY3MvaW1hZ2VzL21hdGVyaWFsEAEYCSABKAMwEzoDMy43

Retrieves dimension (dp/sp) or color values from XML resource files for UI styling.

```APIDOC
## GET dimensionResource / colorResource

### Description
Retrieves dimension values (as Dp) or color values from XML resources. Note: colorResource flattens color state lists.

### Method
GET

### Endpoint
dimensionResource(id) / colorResource(id)

### Parameters
#### Path Parameters
- **id** (Int) - Required - The resource ID (e.g., R.dimen.padding_small or R.color.purple_200)

### Request Example
val padding = dimensionResource(R.dimen.padding_small)

### Response
#### Success Response (200)
- **value** (Dp|Color) - The resolved dimension or color object.

#### Response Example
8.dp
```

--------------------------------

### Define Guidelines in Jetpack Compose ConstraintLayout

Source: https://developer.android.com/develop/ui/compose/layouts/constraintlayout

This snippet illustrates how to create different types of guidelines (start, end, top, bottom) within a `ConstraintLayout` in Jetpack Compose. Guidelines are visual helpers used to position elements at specific `dp` or percentage offsets from the parent.

```kotlin
ConstraintLayout {
    // Create guideline from the start of the parent at 10% the width of the Composable
    val startGuideline = createGuidelineFromStart(0.1f)
    // Create guideline from the end of the parent at 10% the width of the Composable
    val endGuideline = createGuidelineFromEnd(0.1f)
    //  Create guideline from 16 dp from the top of the parent
    val topGuideline = createGuidelineFromTop(16.dp)
    //  Create guideline from 16 dp from the bottom of the parent
    val bottomGuideline = createGuidelineFromBottom(16.dp)
}
```

--------------------------------

### Create Advanced Dialog with Image and Action Buttons

Source: https://developer.android.com/develop/ui/compose/components/dialog

A more complex Dialog implementation featuring an image, descriptive text, and a row of action buttons (Dismiss and Confirm). This example uses Column and Row layouts within a Card to organize custom content manually.

```Kotlin
@Composable
fun DialogWithImage(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    painter: Painter,
    imageDescription: String,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(375.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painter,
                    contentDescription = imageDescription,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(160.dp)
                )
                Text(
                    text = "This is a dialog with buttons and an image.",
                    modifier = Modifier.padding(16.dp),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Dismiss")
                    }
                    TextButton(
                        onClick = { onConfirmation() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Confirm")
                    }
                }
            }
        }
    }
}
```

--------------------------------

### GET /resources/plurals

Source: https://developer.android.com/develop/ui/compose/resources?rec=CklodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZ3JhcGhpY3MvaW1hZ2VzL21hdGVyaWFsEAEYCSABKAMwEzoDMy43

Loads a plural string resource based on a specific quantity for localized count-dependent text.

```APIDOC
## GET pluralStringResource

### Description
Retrieves a plural string resource from XML based on a provided quantity. (Experimental API)

### Method
GET

### Endpoint
pluralStringResource(id, quantity, *formatArgs)

### Parameters
#### Path Parameters
- **id** (Int) - Required - The resource ID (e.g., R.plurals.runtime_format)
- **quantity** (Int) - Required - The count used to select the plural form (one, other, etc.)

#### Query Parameters
- **formatArgs** (Any) - Optional - Arguments to be inserted into the plural string placeholders.

### Request Example
pluralStringResource(R.plurals.runtime_format, 10, 10)

### Response
#### Success Response (200)
- **text** (String) - The resolved plural string.

#### Response Example
"10 minutes"
```

--------------------------------

### GET /compose/navigation-drawer

Source: https://developer.android.com/develop/ui/compose/migrate/migration-scenarios/coordinator-layout?rec=ClpodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbWlncmF0ZS9taWdyYXRpb24tc2NlbmFyaW9zL3JlY3ljbGVyLXZpZXcQAhgJIAEoBjArOgMzLjc

Implements a modal navigation drawer with customizable content and a main screen area using the ModalNavigationDrawer composable.

```APIDOC
## GET /compose/navigation-drawer

### Description
Displays a navigation drawer that slides in from the side, containing navigation items and a header, using the ModalNavigationDrawer component.

### Method
GET

### Endpoint
/compose/navigation-drawer

### Parameters
#### Request Body
- **drawerContent** (Composable) - Required - The content to be displayed inside the drawer, typically a ModalDrawerSheet.
- **content** (Composable) - Required - The main screen content, typically a Scaffold.

### Request Example
{
  "drawerContent": "ModalDrawerSheet { Text(\"Title\"); NavigationDrawerItem(...) }",
  "content": "Scaffold { ... }"
}

### Response
#### Success Response (200)
- **UI** (Composable) - A ModalNavigationDrawer containing the specified drawer and screen content.

### Response Example
{
  "status": "success",
  "component": "ModalNavigationDrawer"
}
```

--------------------------------

### Using Modifiers in Layouts

Source: https://developer.android.com/develop/ui/compose/layouts/basics?rec=CkdodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdG9vbGluZy9lZGl0b3ItYWN0aW9ucxABGAkgASgGMBY6AzMuNw

Demonstrates how to use modifier chains to customize composable layouts, including examples of clickable, padding, fillMaxWidth, and size modifiers applied to an ArtistCard composable.

```APIDOC
## Modifiers in Layouts

### Description
Modifiers are essential for customizing your layout. They can be chained together to decorate or augment composables with multiple effects.

### Code Example
```kotlin
@Composable
fun ArtistCardModifiers(
    artist: Artist,
    onClick: () -> Unit
) {
    val padding = 16.dp
    Column(
        Modifier
            .clickable(onClick = onClick)
            .padding(padding)
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) { /*...*/ }
        Spacer(Modifier.size(padding))
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        ) { /*...*/ }
    }
}
```

### Common Modifier Functions

#### clickable
- **Purpose**: Makes a composable react to user input
- **Effect**: Shows a ripple animation on interaction
- **Usage**: `Modifier.clickable(onClick = onClick)`

#### padding
- **Purpose**: Puts space around an element
- **Effect**: Adds internal spacing
- **Usage**: `Modifier.padding(16.dp)`

#### fillMaxWidth
- **Purpose**: Makes the composable fill the maximum width given by parent
- **Effect**: Expands horizontally to available space
- **Usage**: `Modifier.fillMaxWidth()`

#### size
- **Purpose**: Specifies an element's preferred width and height
- **Effect**: Sets explicit dimensions
- **Usage**: `Modifier.size(16.dp)`

### Modifier Chaining
Modifiers are applied in order, allowing you to combine multiple effects on a single composable.
```

--------------------------------

### GET /resources/strings

Source: https://developer.android.com/develop/ui/compose/resources?rec=CklodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZ3JhcGhpY3MvaW1hZ2VzL21hdGVyaWFsEAEYCSABKAMwEzoDMy43

Retrieves a string resource from the XML definitions, supporting both static strings and positional formatting.

```APIDOC
## GET stringResource

### Description
Retrieves a string statically defined in XML resources. Supports positional formatting for dynamic content.

### Method
GET

### Endpoint
stringResource(id, *formatArgs)

### Parameters
#### Path Parameters
- **id** (Int) - Required - The resource ID (e.g., R.string.compose)

#### Query Parameters
- **formatArgs** (Any) - Optional - Positional arguments for string formatting (e.g., %1$s)

### Request Example
stringResource(R.string.congratulate, "New Year", 2021)

### Response
#### Success Response (200)
- **text** (String) - The resolved and formatted string value.

#### Response Example
"Happy New Year 2021"
```

--------------------------------

### Navigate with User ID Argument

Source: https://developer.android.com/develop/ui/compose/navigation

Pass only the minimum necessary information (such as a unique identifier) when navigating to a new destination, rather than passing complex data objects. This example demonstrates navigating to a Profile destination with a user ID argument.

```Kotlin
// Pass only the user ID when navigating to a new destination as argument
navController.navigate(Profile(id = "user1234"))
```

--------------------------------

### rememberCoroutineScope - Basic Usage

Source: https://developer.android.com/develop/ui/compose/kotlin?rec=Cj1odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2Uvc2lkZS1lZmZlY3RzEAIYCSABKAQwGzoDMy43

Demonstrates how to create a CoroutineScope that follows a composable's lifecycle using rememberCoroutineScope. This example shows how to launch a coroutine in a button click handler to perform sequential suspend operations like animating scroll and loading data.

```APIDOC
## rememberCoroutineScope - Basic Usage

### Description
Creates a CoroutineScope that follows the composable's lifecycle, allowing safe creation of coroutines in event handlers and calling Compose suspend APIs.

### Function Signature
```kotlin
@Composable
fun rememberCoroutineScope(): CoroutineScope
```

### Usage
Use rememberCoroutineScope to launch coroutines from event handlers like button clicks. Coroutines execute sequentially by default, so suspend functions will block subsequent operations until they complete.

### Code Example
```kotlin
val composableScope = rememberCoroutineScope()
Button(
    onClick = {
        composableScope.launch {
            scrollState.animateScrollTo(0) // Suspend function
            viewModel.loadData()
        }
    }
) { /* Button content */ }
```

### Key Points
- Returns a CoroutineScope tied to the composable's lifecycle
- Safe to use within event handlers
- Coroutines execute sequentially by default
- Suspend functions pause execution until they return
- Automatically cancelled when the composable leaves composition
```

--------------------------------

### GET /Modifier.offset

Source: https://developer.android.com/develop/ui/compose/modifiers-list

Applies a positional offset to the content in density-independent pixels (dp) or via a dynamic lambda.

```APIDOC
## GET /Modifier.offset

### Description
Offset the content by specific X and Y coordinates. This does not affect the measurement of the element, only its placement.

### Method
GET

### Endpoint
Modifier.offset(x: Dp, y: Dp)

### Parameters
#### Query Parameters
- **x** (Dp) - Required - The horizontal offset applied to the element.
- **y** (Dp) - Required - The vertical offset applied to the element.

### Request Example
{
  "x": "10.dp",
  "y": "20.dp"
}

### Response
#### Success Response (200)
- **modifier** (Modifier) - A modifier that offsets the layout content.
```

--------------------------------

### CompositionLocalProvider - Providing Values

Source: https://developer.android.com/develop/ui/compose/compositionlocal?rec=CkFodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdG9vbGluZy9wcmV2aWV3cxABGAkgASgFMBs6AzMuNw

Explains how to use CompositionLocalProvider to supply new values to CompositionLocal instances at different levels of the UI tree. Demonstrates the provides infix function and how Compose recomposes affected parts when values change.

```APIDOC
## CompositionLocalProvider API

### Description
CompositionLocalProvider is used to provide new values to CompositionLocal instances. It uses the provides infix function to associate a CompositionLocal key with a value. When a new value is provided, Compose automatically recomposes parts of the Composition that read the CompositionLocal.

### Syntax
```kotlin
CompositionLocalProvider(LocalCompositionLocal provides value) {
    // content lambda - descendants can access the provided value
}
```

### Parameters
- **LocalCompositionLocal**: The CompositionLocal key to provide a value for
- **value**: The new value to associate with the CompositionLocal
- **content**: Lambda containing composables that will have access to the provided value

### Scoping Behavior
- Values are scoped to the content lambda and its descendants
- Multiple CompositionLocalProvider blocks can be nested
- Inner providers override outer providers for the same CompositionLocal
- The current value corresponds to the closest ancestor-provided value

### Recomposition
- When a CompositionLocal value changes, only the composables that read that CompositionLocal are recomposed
- This provides efficient, targeted recomposition
```

--------------------------------

### CompositingStrategy Examples: Offscreen vs Default

Source: https://developer.android.com/develop/ui/compose/graphics/draw/modifiers?rec=CkRodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2Uvc3lzdGVtL3Rlc3QtY3V0b3V0cxABGAkgASgCMCA6AzMuNw

Compares the behavior of graphicsLayer with and without CompositingStrategy.Offscreen. Demonstrates how the default strategy (isolation only) differs from Offscreen strategy (rasterization with clipping) in terms of content clipping and rendering.

```APIDOC
## CompositingStrategy Examples: Offscreen vs Default

### Description
Illustrates the differences between default graphicsLayer behavior and CompositingStrategy.Offscreen. The default strategy provides isolation for draw invalidations without rasterization, while Offscreen creates a separate buffer and clips content to the specified bounds.

### Default graphicsLayer Behavior
- **Purpose**: Isolation only - prevents external draw invalidations from re-recording composable instructions
- **Clipping**: Does NOT clip content to specified size bounds
- **Rendering**: Drawing commands execute in the original destination
- **Use Case**: Performance optimization for stable content

### CompositingStrategy.Offscreen Behavior
- **Purpose**: Rasterization and isolation - creates offscreen buffer for content
- **Clipping**: DOES clip content to specified size bounds
- **Rendering**: Drawing commands execute in offscreen buffer, then composited to destination
- **Use Case**: Complex effects, blend modes, and visual masking

### Implementation Pattern - Default Strategy
```kotlin
Canvas(
    modifier = Modifier
        .graphicsLayer()
        .size(100.dp)
        .border(2.dp, color = Color.Blue)
) {
    // Drawing commands NOT clipped to 100.dp size
    drawRect(color = Color.Magenta, size = Size(200.dp.toPx(), 200.dp.toPx()))
}
```

### Implementation Pattern - Offscreen Strategy
```kotlin
Canvas(
    modifier = Modifier
        .graphicsLayer {
            compositingStrategy = CompositingStrategy.Offscreen
        }
        .size(100.dp)
        .border(2.dp, color = Color.Blue)
) {
    // Drawing commands ARE clipped to 100.dp size
    drawRect(color = Color.Magenta, size = Size(200.dp.toPx(), 200.dp.toPx()))
}
```

### Key Differences
| Aspect | Default graphicsLayer | CompositingStrategy.Offscreen |
|--------|----------------------|-------------------------------|
| Clipping | No | Yes |
| Rasterization | No | Yes |
| Offscreen Buffer | No | Yes |
| Isolation | Yes | Yes |
| Performance Impact | Minimal | Moderate |
| Use with Alpha | Not recommended | Recommended |
| Use with BlendMode | Limited | Full support |

### Performance Considerations
- Default strategy: Minimal overhead, suitable for stable content
- Offscreen strategy: Creates texture allocation, use when visual effects require it
- Offscreen textures sized to drawing area dimensions
```

--------------------------------

### Initialize ComposeView using View Binding in Kotlin

Source: https://developer.android.com/develop/ui/compose/migrate/interoperability-apis/compose-in-views

This example shows how to use Android View Binding to safely access a ComposeView defined in XML. It provides a more robust and type-safe alternative to findViewById for setting up Compose content in a Fragment.

```kotlin
class ExampleFragment : Fragment() {

    private var _binding: FragmentExampleBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExampleBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.composeView.apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                // In Compose world
                MaterialTheme {
                    Text("Hello Compose!")
                }
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
```

--------------------------------

### Measure Text and Draw Background in Compose

Source: https://developer.android.com/develop/ui/compose/graphics/draw/overview

This example shows how to measure text using `TextMeasurer` to determine its size, allowing you to draw a background behind it. It utilizes `Modifier.drawWithCache` for performance optimization and sets a fixed width constraint for the text measurement.

```Kotlin
val textMeasurer = rememberTextMeasurer()

Spacer(
    modifier = Modifier
        .drawWithCache {
            val measuredText =
                textMeasurer.measure(
                    AnnotatedString(longTextSample),
                    constraints = Constraints.fixedWidth((size.width * 2f / 3f).toInt()),
                    style = TextStyle(fontSize = 18.sp)
                )

            onDrawBehind {
                drawRect(pinkColor, size = measuredText.size.toSize())
                drawText(measuredText)
            }
        }
        .fillMaxSize()
)
```

--------------------------------

### Declare Multi-Instance System UI Support

Source: https://developer.android.com/develop/ui/compose/layouts/adaptive/support-multi-window-mode?hl=it

Use the PROPERTY_SUPPORTS_MULTI_INSTANCE_SYSTEM_UI property (API 35+) to signal that the system UI should expose controls for creating multiple instances.

```xml
<property
    android:name="android.window.PROPERTY_SUPPORTS_MULTI_INSTANCE_SYSTEM_UI"
    android:value="true" />
```

--------------------------------

### GET /modifier/size

Source: https://developer.android.com/develop/ui/compose/modifiers?rec=Cj9odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbGF5b3V0cy9jdXN0b20QAxgJIAEoATAKOgMzLjc

Sets the dimensions of a composable. Use size to respect parent constraints and requiredSize to override them.

```APIDOC
## GET /modifier/size

### Description
Sets the width and height of a composable. The `size` modifier is subject to parent constraints, while `requiredSize` forces the dimensions regardless of incoming constraints.

### Method
GET

### Endpoint
Modifier.size(width, height) / Modifier.requiredSize(size)

### Parameters
#### Path Parameters
- **width** (Dp) - Optional - The desired width of the layout.
- **height** (Dp) - Optional - The desired height of the layout.
- **size** (Dp) - Optional - A single value to set both width and height.

### Request Example
{
  "modifier": "Modifier.requiredSize(150.dp)"
}

### Response
#### Success Response (200)
- **Modifier** (Object) - The resulting modifier instance applied to the LayoutNode.
```

--------------------------------

### GET /lazy-layout/spacing

Source: https://developer.android.com/develop/ui/compose/lists?rec=ClpodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbWlncmF0ZS9taWdyYXRpb24tc2NlbmFyaW9zL3JlY3ljbGVyLXZpZXcQARgJIAEoATAMOgMzLjc

Defines the spacing between items in a lazy layout using Arrangement.spacedBy for vertical or horizontal arrangements.

```APIDOC
## GET /lazy-layout/spacing

### Description
Adds spacing between items in LazyColumn, LazyRow, or LazyVerticalGrid. Grids support both vertical and horizontal spacing.

### Method
GET

### Endpoint
Arrangement.spacedBy(space: Dp)

### Parameters
#### Path Parameters
- **space** (Dp) - Required - The amount of space to add between each item.

### Request Example
LazyColumn(
    verticalArrangement = Arrangement.spacedBy(4.dp),
) {
    // items
}

### Response
#### Success Response (200)
- **layout** (Composable) - The lazy layout with specified item spacing applied.
```

--------------------------------

### Implement Basic Checkbox in Jetpack Compose

Source: https://developer.android.com/develop/ui/compose/components/checkbox

A minimal example demonstrating how to use the Checkbox composable. It uses a boolean state to track whether the box is checked and updates the UI accordingly via the onCheckedChange callback.

```Kotlin
@Composable
fun CheckboxMinimalExample() {
    var checked by remember { mutableStateOf(true) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            "Minimal checkbox"
        )
        Checkbox(
            checked = checked,
            onCheckedChange = { checked = it }
        )
    }

    Text(
        if (checked) "Checkbox is checked" else "Checkbox is unchecked"
    )
}
```

--------------------------------

### Multipreview Templates Overview

Source: https://developer.android.com/develop/ui/compose/tooling/previews

An introduction to built-in Multipreview API templates like @PreviewScreenSizes, @PreviewFontScales, @PreviewLightDark, and @PreviewDynamicColors for common UI preview scenarios.

```APIDOC
## Multipreview Templates Overview

### Description
`androidx.compose.ui:ui-tooling-preview` 1.6.0-alpha01+ introduces Multipreview API templates: `@PreviewScreenSizes`, `@PreviewFontScales`, `@PreviewLightDark`, and `@PreviewDynamicColors`. These allow you to preview your Compose UI in common scenarios with a single annotation.

### Usage
These are built-in annotations that can be directly applied to a Composable function.
```

--------------------------------

### GET Placeable[AlignmentLine]

Source: https://developer.android.com/develop/ui/compose/layouts/alignment-lines?rec=CkdodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZ3JhcGhpY3MvZHJhdy9vdmVydmlldxADGAkgASgFMCg6AzMuNw

Retrieves the pixel position of a specific alignment line from a measured Placeable instance.

```APIDOC
## GET Placeable[AlignmentLine]

### Description
An operator function used during the measurement phase to read the value of a specific AlignmentLine provided by a child layout.

### Method
OPERATOR_GET

### Endpoint
placeable[alignmentLine: AlignmentLine]

### Parameters
#### Path Parameters
- **alignmentLine** (AlignmentLine) - Required - The alignment line to query, such as FirstBaseline or LastBaseline.

### Request Example
```kotlin
val firstBaseline = placeable[FirstBaseline]
```

### Response
#### Success Response (200)
- **position** (Int) - The pixel position of the alignment line. Returns AlignmentLine.Unspecified if the line is not provided.

#### Response Example
```kotlin
32 // Pixel value
```
```

--------------------------------

### Chip Dependencies and Version Compatibility

Source: https://developer.android.com/develop/ui/compose/quick-guides/content/create-chip

Setup requirements for implementing Chip components in Android Jetpack Compose projects, including minimum SDK version and required dependencies.

```APIDOC
## Chip Dependencies and Version Compatibility

### Description
This guide covers the setup requirements for implementing Chip components in Android Jetpack Compose, including minimum SDK version and dependency configuration.

### Version Compatibility
- **Minimum SDK**: API level 21 or higher required
- **Compose BOM Version**: 2026.03.00

### Dependencies

#### Kotlin DSL (build.gradle.kts)
```kotlin
implementation(platform("androidx.compose:compose-bom:2026.03.00"))
```

#### Groovy DSL (build.gradle)
```groovy
implementation platform('androidx.compose:compose-bom:2026.03.00')
```

### Required Configuration
- Set project minSDK to API level 21 or higher in build.gradle
- Include the androidx.compose BOM (Bill of Materials) for dependency management
- Use the specified Compose BOM version for compatibility

### Benefits of Using BOM
- Centralized version management
- Ensures compatible versions of all Compose libraries
- Simplifies dependency declarations
- Reduces version conflicts

### Additional Notes
- The BOM approach eliminates the need to specify individual library versions
- All Compose libraries referenced will use the version specified in the BOM
- Update the BOM version when upgrading Compose libraries
```

--------------------------------

### Untitled

Source: https://developer.android.com/develop/ui/compose/modifiers-list

No description

--------------------------------

### GET /textMeasurer/measure

Source: https://developer.android.com/develop/ui/compose/graphics/draw/overview?rec=CkRodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZ3JhcGhpY3MvZHJhdy9icnVzaBACGAkgASgDMCE6AzMuNw

Calculates the size and layout of a string based on specific constraints, styles, and overflow settings.

```APIDOC
## GET /textMeasurer/measure

### Description
Measures the dimensions of a string given layout constraints and styling. This is essential for drawing backgrounds or handling text overflow.

### Method
GET

### Endpoint
/textMeasurer/measure

### Parameters
#### Request Body
- **text** (AnnotatedString) - Required - The text to be measured.
- **constraints** (Constraints) - Required - The width and height limits for the text.
- **style** (TextStyle) - Optional - Font size, weight, and other styling properties.
- **overflow** (TextOverflow) - Optional - How to handle text that exceeds constraints.

### Request Example
{
  "text": "longTextSample",
  "constraints": "Constraints.fixed(width, height)",
  "overflow": "TextOverflow.Ellipsis"
}

### Response
#### Success Response (200)
- **size** (IntSize) - The calculated width and height of the text area.
```

--------------------------------

### Use Spacer with window inset height in LazyColumn

Source: https://developer.android.com/develop/ui/compose/system/insets-ui

This example shows how to use Modifier.windowInsetsBottomHeight to create a Spacer that matches the height of the system bars at the bottom of a list. It also uses imePadding to handle keyboard visibility.

```kotlin
LazyColumn(
    Modifier.imePadding()
) {
    // Other content
    item {
        Spacer(
            Modifier.windowInsetsBottomHeight(
                WindowInsets.systemBars
            )
        )
    }
}
```

--------------------------------

### Apply Resource Providers to Glance Composables

Source: https://developer.android.com/develop/ui/compose/glance/build-ui

Demonstrates using resource providers like ImageProvider and GlanceModifier overloads to apply resources directly to Composables. This pattern reduces RemoteViews size and enables dynamic resources. Examples show background color application and image resource usage.

```Kotlin
Column(
    modifier = GlanceModifier.background(R.color.default_widget_background)
) { /**...*/ }

Image(
    provider = ImageProvider(R.drawable.ic_logo),
    contentDescription = "My image",
)
```

--------------------------------

### Apply multiple DeviceConfigurationOverrides using then() in Compose tests

Source: https://developer.android.com/develop/ui/compose/testing/common-patterns

This example shows how to combine multiple `DeviceConfigurationOverride` instances using the `then()` operator. It applies both a font scale adjustment and a font weight adjustment to a `Text` Composable, simulating a custom text appearance to ensure accessibility and design consistency.

```kotlin
composeTestRule.setContent {
    DeviceConfigurationOverride(
        DeviceConfigurationOverride.FontScale(1.5f) then
            DeviceConfigurationOverride.FontWeightAdjustment(200)
    ) {
        Text(text = "text with increased scale and weight")
    }
}
```

--------------------------------

### Create Theme System Composition Locals

Source: https://developer.android.com/develop/ui/compose/designsystems/anatomy

Define static CompositionLocal instances to provide theme system classes implicitly throughout the composition tree. Use staticCompositionLocalOf for values that rarely change to improve performance.

```kotlin
val LocalColorSystem = staticCompositionLocalOf {
    ColorSystem(
        color = Color.Unspecified,
        gradient = emptyList()
    )
}

val LocalTypographySystem = staticCompositionLocalOf {
    TypographySystem(
        fontFamily = FontFamily.Default,
        textStyle = TextStyle.Default
    )
}

val LocalCustomSystem = staticCompositionLocalOf {
    CustomSystem(
        value1 = 0,
        value2 = ""
    )
}
```

--------------------------------

### Compose Greeting Function with Live Edit Styling

Source: https://developer.android.com/develop/ui/compose/tooling/iterative-development?rec=CkRodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYW5pbWF0aW9uL2N1c3RvbWl6ZRACGAkgASgDMB86AzMuNw

Example Composable function demonstrating Live Edit-compatible code changes. Shows a Text composable with padding and background color modifiers that can be edited in real-time. This function is designed to be modified during development without requiring full app recompilation.

```Kotlin
@Composable
fun Greeting(name: String) {
    Text(
        text = "Hello $name!",
        Modifier
            .padding(80.dp) // Outer padding; outside background
            .background(color = Color.Cyan) // Solid element background color
            .padding(16.dp) // Inner padding; inside background, around text
    )
}
```

--------------------------------

### Implement Basic Modal Navigation Drawer

Source: https://developer.android.com/develop/ui/compose/quick-guides/content/create-navigation-drawer

Create a standard modal drawer using ModalNavigationDrawer and ModalDrawerSheet to host navigation items and dividers within a Compose UI.

```Kotlin
ModalNavigationDrawer(  
    drawerContent = {  
        ModalDrawerSheet {  
            Text("Drawer title", modifier = Modifier.padding(16.dp))  
            HorizontalDivider()  
            NavigationDrawerItem(  
                label = { Text(text = "Drawer Item") },  
                selected = false,  
                onClick = { /*TODO*/ }  
            )  
            // ...other drawer items  
        }  
    }  
) {  
    // Screen content  
}
```

--------------------------------

### GET /focusProperties

Source: https://developer.android.com/develop/ui/compose/touch-input/focus/change-focus-behavior

Configures focus properties for the element. The topmost modifier in the chain takes precedence over subsequent ones.

```APIDOC
## GET /focusProperties

### Description
Specifies focus properties for the component, such as navigation targets (left, right, up, down) or focusability. Precedence is determined by modifier order; the outermost modifier overrides inner ones.

### Method
GET

### Endpoint
Modifier.focusProperties

### Parameters
#### Request Body
- **scope** (FocusPropertiesScope) - Required - A lambda where properties like 'right', 'left', 'canFocus' are set.

### Request Example
{
  "code": "Modifier.focusProperties { right = item1 }"
}

### Response
#### Success Response (200)
- **Modifier** (Modifier) - The modified element with focus properties applied.
```

--------------------------------

### Implement onProvideKeyboardShortcuts with Single Group in Kotlin

Source: https://developer.android.com/develop/ui/compose/touch-input/keyboard-input/keyboard-shortcuts-helper

Demonstrates how to override the onProvideKeyboardShortcuts() method in a ComponentActivity to provide a single group of keyboard shortcuts. This example creates a 'Cursor movement' group containing four shortcuts (Up, Down, Forward, Backward) using KeyboardShortcutInfo objects and adds them to the mutable list parameter.

```Kotlin
class MainActivity : ComponentActivity() {
    // Activity codes such as overridden onStart method.

    override fun onProvideKeyboardShortcuts(
        data: MutableList<KeyboardShortcutGroup>?,
        menu: Menu?,
        deviceId: Int
    ) {
        val shortcutGroup = KeyboardShortcutGroup(
            "Cursor movement",
            listOf(
                KeyboardShortcutInfo("Up", KeyEvent.KEYCODE_P, KeyEvent.META_CTRL_ON),
                KeyboardShortcutInfo("Down", KeyEvent.KEYCODE_N, KeyEvent.META_CTRL_ON),
                KeyboardShortcutInfo("Forward", KeyEvent.KEYCODE_F, KeyEvent.META_CTRL_ON),
                KeyboardShortcutInfo("Backward", KeyEvent.KEYCODE_B, KeyEvent.META_CTRL_ON),
            )
        )
        data?.add(shortcutGroup)
    }
}
```

--------------------------------

### GET /compose/foundation/rememberLazyListState

Source: https://developer.android.com/develop/ui/compose/state-saving?rec=CjpodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbGlmZWN5Y2xlEAMYCSABKAUwFzoDMy43

A specialized API for preserving the scroll position of LazyColumn or LazyRow components using a custom Saver.

```APIDOC
## GET /compose/foundation/rememberLazyListState

### Description
Creates and remembers a LazyListState that is stored via rememberSaveable to persist scroll offsets.

### Method
GET (Composable Function)

### Endpoint
androidx.compose.foundation.lazy.rememberLazyListState

### Parameters
#### Query Parameters
- **initialFirstVisibleItemIndex** (Int) - Optional - Initial scroll index (default 0).
- **initialFirstVisibleItemScrollOffset** (Int) - Optional - Initial scroll offset (default 0).

### Request Example
```kotlin
val listState = rememberLazyListState(
    initialFirstVisibleItemIndex = 0
)
```

### Response
#### Success Response (200)
- **LazyListState** (Object) - The state object containing current scroll position.

#### Response Example
{
  "firstVisibleItemIndex": 5,
  "firstVisibleItemScrollOffset": 120
}
```

--------------------------------

### Implement a custom InputTransformation interface in Compose

Source: https://developer.android.com/develop/ui/compose/text/user-input?rec=Cj1odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYXJjaGl0ZWN0dXJlEAMYCSABKAMwFzoDMy43

This example provides the basic structure for creating a custom `InputTransformation` by implementing the interface and overriding the `transformInput` method, which operates within the `TextFieldBuffer` scope.

```kotlin
class CustomInputTransformation : InputTransformation {
    override fun TextFieldBuffer.transformInput() {
    }
}
```

--------------------------------

### POST startActivity

Source: https://developer.android.com/develop/ui/compose/migrate/interoperability-apis/views-in-compose?rec=CkFodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvY29tcG9zaXRpb25sb2NhbBACGAkgASgEMBU6AzMuNw

Triggers an Android framework action to launch a new Activity from a Compose event handler.

```APIDOC
## POST startActivity\n\n### Description\nLaunches a different activity using the standard Android Intent system, typically triggered by UI events like button clicks.\n\n### Method\nPOST\n\n### Endpoint\nContext.startActivity(intent)\n\n### Parameters\n#### Request Body\n- **intent** (Intent) - Required - The Intent object defining the target Activity and any extra data.\n\n### Request Example\n{\n  "action": "android.intent.action.VIEW",\n  "target": "MyActivity"\n}
```

--------------------------------

### GET /colorResource

Source: https://developer.android.com/develop/ui/compose/resources?rec=CkhodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZ3JhcGhpY3MvaW1hZ2VzL2NvbXBhcmUQAhgJIAEoAzATOgMzLjc

Retrieves a color resource from an XML resource file. Note that this flattens color state lists.

```APIDOC
## GET colorResource\n\n### Description\nRetrieves a color from the XML resource files. It is generally recommended to use MaterialTheme colors instead of hard-coded resource colors where possible.\n\n### Method\nGET (Compose Function)\n\n### Endpoint\ncolorResource(id: Int)\n\n### Parameters\n#### Path Parameters\n- **id** (Int) - Required - The resource ID of the color.\n\n### Request Example\ncolorResource(R.color.purple_200)\n\n### Response\n#### Success Response (200)\n- **color** (Color) - The resolved Color object for use in Compose UI.
```

--------------------------------

### Handling isLight Parameter in M3 ColorScheme

Source: https://developer.android.com/develop/ui/compose/designsystems/material2-material3

Demonstrates how to migrate logic dependent on the M2 Colors.isLight parameter to Material Design 3, where ColorScheme does not include this parameter. The example shows modeling this information at the theme level.

```APIDOC
## CONCEPT: Handling isLight Parameter in M3 ColorScheme

### Description
Unlike the M2 `Colors` class, the M3 `ColorScheme` class doesn't include an `isLight` parameter. In general, you should try and model whatever needs this information at the theme level. This section demonstrates how to adapt your theme logic to handle light/dark mode without relying on `isLight` directly from the `ColorScheme`.

### Method
N/A (Conceptual Migration)

### Endpoint
N/A (Theme-level adaptation)

### Parameters
N/A

### Request Body
N/A

### Request Example
N/A

### Response
N/A

### Response Example
N/A

### M2 Implementation Example
```kotlin
import androidx.compose.material.lightColors
import androidx.compose.material.darkColors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.isSystemInDarkTheme

@Composable
private fun AppTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colors = if (darkTheme) darkColors(…) else lightColors(…)
  MaterialTheme(
      colors = colors,
      content = content
  )
}

@Composable
fun AppComposable() {
    AppTheme {
        val cardElevation = if (MaterialTheme.colors.isLight) 0.dp else 4.dp
        // …
    }
}
```

### M3 Implementation Example
```kotlin
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.isSystemInDarkTheme

val LocalCardElevation = staticCompositionLocalOf { Dp.Unspecified }
@Composable
private fun AppTheme(
   darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
   val cardElevation = if (darkTheme) 4.dp else 0.dp
    CompositionLocalProvider(LocalCardElevation provides cardElevation) {
        val colorScheme = if (darkTheme) darkColorScheme(…) else lightColorScheme(…)
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }
}

@Composable
fun AppComposable() {
    AppTheme {
        val cardElevation = LocalCardElevation.current
        // …
    }
}
```
```

--------------------------------

### Use Compose BOM with Version Catalog

Source: https://developer.android.com/develop/ui/compose/bom

Shows how to define the Compose BOM in a version catalog file, allowing centralized version management. The BOM is imported in the module's build.gradle while other Compose library versions are omitted from the catalog.

```TOML
[libraries]
androidx-compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "androidxComposeBom" }
androidx-compose-foundation = { group = "androidx.compose.foundation", name = "foundation" }
```

--------------------------------

### GET /dimensionResource

Source: https://developer.android.com/develop/ui/compose/resources?rec=CkhodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZ3JhcGhpY3MvaW1hZ2VzL2NvbXBhcmUQAhgJIAEoAzATOgMzLjc

Retrieves a dimension resource (dp or sp) from an XML resource file for use in Compose layouts.

```APIDOC
## GET dimensionResource\n\n### Description\nRetrieves a dimension value from the XML resource files (e.g., dimens.xml).\n\n### Method\nGET (Compose Function)\n\n### Endpoint\ndimensionResource(id: Int)\n\n### Parameters\n#### Path Parameters\n- **id** (Int) - Required - The resource ID of the dimension.\n\n### Request Example\nval padding = dimensionResource(R.dimen.padding_small)\n\n### Response\n#### Success Response (200)\n- **dimension** (Dp) - The resolved dimension value in Density-independent Pixels.
```

--------------------------------

### Implement Scaffold with Top Bar, Bottom Bar, and Floating Action Button

Source: https://developer.android.com/develop/ui/compose/components/scaffold

Creates a complete Scaffold composable with a top app bar, bottom app bar, and floating action button. The Scaffold manages PaddingValues passed to the content lambda, ensuring proper spacing and layout constraints. This example demonstrates Material Design guidelines for app structure in Jetpack Compose.

```Kotlin
@Composable
fun ScaffoldExample() {
    var presses by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Top app bar")
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Bottom app bar",
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { presses++ }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text =
                """
                    This is an example of a scaffold. It uses the Scaffold composable's parameters to create a screen with a simple top app bar, bottom app bar, and floating action button.

                    It also contains some basic inner content, such as this text.

                    You have pressed the floating action button $presses times.
                """.trimIndent(),
            )
        }
    }
}
```

--------------------------------

### GET dimensionResource

Source: https://developer.android.com/develop/ui/compose/resources

Retrieves dimension values (dp or sp) from XML resource files for use in modifiers and layouts.

```APIDOC
## GET dimensionResource

### Description
Retrieves dimensions from a resource XML file. This allows sharing dimension constants between XML layouts and Compose.

### Method
GET

### Endpoint
dimensionResource(id: Int)

### Parameters
#### Path Parameters
- **id** (Int) - Required - The resource ID of the dimension (e.g., R.dimen.padding_small).

### Request Example
val smallPadding = dimensionResource(R.dimen.padding_small)
Modifier.padding(smallPadding)

### Response
#### Success Response (200)
- **value** (Dp) - The resolved dimension value in Density-independent Pixels.
```

--------------------------------

### GET /theme/CustomTheme/properties

Source: https://developer.android.com/develop/ui/compose/designsystems/custom?rec=Cj5odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZGVzaWduc3lzdGVtcxACGAkgASgFMBY6AzMuNw

Retrieves the current values of the custom design system from the composition local context using the CustomTheme singleton object.

```APIDOC
## GET /theme/CustomTheme/properties

### Description
Accesses the current theme values for colors, typography, and elevation using the CustomTheme singleton object within a Composable scope.

### Method
GET

### Endpoint
CustomTheme.{property}

### Parameters
#### Path Parameters
- **property** (string) - Required - The design system category to access: 'colors', 'typography', or 'elevation'.

### Request Example
CustomTheme.colors.content

### Response
#### Success Response (200)
- **colors** (CustomColors) - The current color system tokens.
- **typography** (CustomTypography) - The current typography system tokens.
- **elevation** (CustomElevation) - The current elevation system tokens.
```

--------------------------------

### GET Modifier.firstBaselineToTop

Source: https://developer.android.com/develop/ui/compose/layouts/custom?rec=Ck9odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbGF5b3V0cy9pbnRyaW5zaWMtbWVhc3VyZW1lbnRzEAEYCSABKAEwEDoDMy43

A custom layout modifier that adjusts the distance from the top of the container to the first baseline of a text element.

```APIDOC
## GET Modifier.firstBaselineToTop

### Description
Modifies the layout to set a specific distance between the top of the layout and the first baseline of the text content.

### Method
GET

### Endpoint
Modifier.firstBaselineToTop(firstBaselineToTop: Dp)

### Parameters
#### Path Parameters
- **firstBaselineToTop** (Dp) - Required - The desired distance from the top boundary to the first baseline of the text.

### Request Example
{
  "modifier": "Modifier.firstBaselineToTop(32.dp)"
}

### Response
#### Success Response (200)
- **placeable** (Placeable) - The measured and positioned element with adjusted height and Y-offset.

#### Response Example
{
  "width": "placeable.width",
  "height": "placeable.height + placeableY"
}
```

--------------------------------

### Navigate from Compose using Fragment Navigation Controller

Source: https://developer.android.com/develop/ui/compose/navigation?rec=CjZodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2Uvc2V0dXAQARgJIAEoAzAaOgMzLjc

Demonstrates how to bridge Compose UI with the Fragment-based Navigation component by exposing navigation events as lambdas to the Fragment.

```Kotlin
@Composable
fun MyScreen(onNavigate: (Int) -> Unit) {
    Button(onClick = { onNavigate(R.id.nav_profile) }) { /* ... */ }
}

// In Fragment
override fun onCreateView( /* ... */ ) {
    setContent {
        MyScreen(onNavigate = { dest -> findNavController().navigate(dest) })
    }
}
```

--------------------------------

### Basic Modifier Usage

Source: https://developer.android.com/develop/ui/compose/modifiers?rec=Cj9odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbGF5b3V0cy9iYXNpY3MQARgJIAEoAjAZOgMzLjc

Demonstrates how to create and apply a basic modifier to a composable. This example shows how to use the padding modifier to add space around elements within a Column composable.

```APIDOC
## Basic Modifier Usage

### Description
Shows how to create a modifier by calling functions on the `Modifier` class and applying it to a composable.

### Usage
Modifiers are standard Kotlin objects that allow you to decorate or augment a composable by changing its size, layout, behavior, and appearance.

### Code Example
```kotlin
@Composable
private fun Greeting(name: String) {
    Column(modifier = Modifier.padding(24.dp)) {
        Text(text = "Hello,")
        Text(text = name)
    }
}
```

### Key Points
- Modifiers are created by calling functions on the `Modifier` class
- Apply modifiers to composables via the `modifier` parameter
- `padding(24.dp)` adds 24 density-independent pixels of space around the element
```

--------------------------------

### Update all or conditional GlanceAppWidget instances

Source: https://developer.android.com/develop/ui/compose/glance/glance-app-widget

This example illustrates using GlanceAppWidget extension functions for more convenient updates. `updateAll(context)` updates every placed instance of the widget, while `updateIf<State>(context)` allows updating instances selectively based on a provided state predicate.

```kotlin
// Updates all placed instances of MyAppWidget
MyAppWidget().updateAll(context)

// Iterate over all placed instances of MyAppWidget and update if the state of
// the instance matches the given predicate
MyAppWidget().updateIf<State>(context) { state ->
    state == State.Completed
}
```

--------------------------------

### Capture Composable Content to Bitmap Using GraphicsLayer

Source: https://developer.android.com/develop/ui/compose/graphics/draw/modifiers

Demonstrates how to render a composable's contents to a Bitmap using rememberGraphicsLayer() (available in Compose 1.7.0-alpha07+). The example uses drawWithContent() and graphicsLayer.record() to capture drawing commands, then converts the graphics layer to an ImageBitmap. This enables saving composable content to disk or sharing it.

```Kotlin
val coroutineScope = rememberCoroutineScope()
val graphicsLayer = rememberGraphicsLayer()
Box(
    modifier = Modifier
        .drawWithContent {
            // call record to capture the content in the graphics layer
            graphicsLayer.record {
                // draw the contents of the composable into the graphics layer
                this@drawWithContent.drawContent()
            }
            // draw the graphics layer on the visible canvas
            drawLayer(graphicsLayer)
        }
        .clickable {
            coroutineScope.launch {
                val bitmap = graphicsLayer.toImageBitmap()
                // do something with the newly acquired bitmap
            }
        }
        .background(Color.White)
) {
    Text("Hello Android", fontSize = 26.sp)
}
```

--------------------------------

### Implement Responsive Layouts using BoxWithConstraints

Source: https://developer.android.com/develop/ui/compose/layouts/basics?rec=CjdodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2Uva290bGluEAIYCSABKAYwFjoDMy43

Uses BoxWithConstraints to access measurement constraints from

--------------------------------

### Compose Compiler Stability Report for Snack Class

Source: https://developer.android.com/develop/ui/compose/performance/stability/diagnose

Example output from the classes.txt report generated by the Compose compiler, showing the stability status of class properties.

```text
unstable class Snack {
  stable val id: Long
  stable val name: String
  stable val imageUrl: String
  stable val price: Long
  stable val tagline: String
  unstable val tags: Set<String>
  <runtime stability> = Unstable
}
```

--------------------------------

### Implementing a Filled Card in Android Compose

Source: https://developer.android.com/develop/ui/compose/components/card

This example illustrates how to create a filled `Card` by customizing its `colors` property. It sets the `containerColor` to `MaterialTheme.colorScheme.surfaceVariant` and applies a fixed size modifier, demonstrating how to change the card's background appearance.

```kotlin
@Composable
fun FilledCardExample() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .size(width = 240.dp, height = 100.dp)
    ) {
        Text(
            text = "Filled",
            modifier = Modifier
                .padding(16.dp),
            textAlign = TextAlign.Center,
        )
    }
}
```

--------------------------------

### GET /material3/card

Source: https://developer.android.com/develop/ui/compose/designsystems/material3?rec=CjRodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYm9tEAEYCSABKAUwFjoDMy43

Configures a Material 3 Card component with custom colors and elevation settings using CardDefaults.

```APIDOC
## GET /material3/card\n\n### Description\nCustomize a Card component's theming including colors and elevation interfaces to provide personalization and flexibility.\n\n### Method\nGET\n\n### Endpoint\n/material3/card\n\n### Parameters\n#### Request Body\n- **colors** (CardColors) - Optional - Defines containerColor, contentColor, disabledContainerColor, and disabledContentColor.\n- **elevation** (CardElevation) - Optional - Defines elevation for default, pressed, and focused states.\n\n### Request Example\n{\n  \"colors\": \"CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)\",\n  \"elevation\": \"CardDefaults.cardElevation(defaultElevation = 8.dp)\"\n}\n\n### Response\n#### Success Response (200)\n- **Composable** (UI) - The rendered Material 3 Card component.\n\n#### Response Example\n{\n  \"status\": \"rendered\",\n  \"component\": \"Card\"\n}
```

--------------------------------

### ConstraintLayout Setup and Dependencies

Source: https://developer.android.com/develop/ui/compose/layouts/constraintlayout?rec=CjRodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYm9tEAIYCSABKAIwEjoDMy43

Instructions for adding ConstraintLayout to your Compose project. This shows the required dependency configuration in build.gradle to enable ConstraintLayout functionality in Jetpack Compose.

```APIDOC
## ConstraintLayout Dependency Setup

### Description
Adds the ConstraintLayout Compose library to your project for creating complex layouts with relative positioning.

### Dependency Configuration

#### Gradle Build Configuration
Add the following to your `build.gradle` file:

```gradle
implementation "androidx.constraintlayout:constraintlayout-compose:$constraintlayout_compose_version"
```

### Notes
- The `constraintLayout-compose` artifact uses different versioning than Jetpack Compose
- Check the latest version on the ConstraintLayout release page
- This dependency is in addition to the standard Compose setup

### Usage Context
Required before using `ConstraintLayout` composable in your Compose UI code.
```

--------------------------------

### Customize Gradient Color Distribution with colorStops in Compose

Source: https://developer.android.com/develop/ui/compose/graphics/draw/brush

This example shows how to use `colorStops` to control the distribution of colors in a horizontal gradient. `colorStops` are defined as an array of pairs, where each pair specifies a fractional offset (between 0.0f and 1.0f) and its corresponding color. The gradient is then applied to a `Box` composable's background.

```kotlin
val colorStops = arrayOf(
    0.0f to Color.Yellow,
    0.2f to Color.Red,
    1f to Color.Blue
)
Box(
    modifier = Modifier
        .requiredSize(200.dp)
        .background(Brush.horizontalGradient(colorStops = colorStops))
)
```

--------------------------------

### Specify Default Launch Bounds

Source: https://developer.android.com/develop/ui/compose/layouts/adaptive/support-desktop-windowing

Customize app launches by specifying a default size and position for an activity when it is launched in desktop windowing.

```APIDOC
## SET ActivityOptions#setLaunchBounds()

### Description
Not all apps, even if resizable, need a large window to offer user value. You can use this method to specify a default size and position when an activity is launched, customizing app launches and transitions from desktop windowing to full screen.

### Method
SET (Method Call)

### Endpoint
ActivityOptions#setLaunchBounds()

### Parameters
#### Path Parameters
None

#### Query Parameters
None

#### Request Body
- **bounds** (Rect) - Required - A `Rect` object defining the default size and position for the activity window.

### Request Example
N/A

### Response
#### Success Response (200)
N/A

#### Response Example
N/A
```

--------------------------------

### GET /compose/transition/animate

Source: https://developer.android.com/develop/ui/compose/animation/value-based?rec=CkJodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYW5pbWF0aW9uL3Rvb2xpbmcQARgJIAEoBTAfOgMzLjc

Defines a child animation within a transition to animate properties like Rect, Dp, or Color.

```APIDOC
## GET /compose/transition/animate

### Description
Extension functions (animateRect, animateDp, animateColor, etc.) that define a child animation in a transition. These return values that update every frame.

### Method
GET

### Endpoint
/compose/transition/animate[Type]

### Parameters
#### Path Parameters
- **label** (String) - Required - Label for the specific property animation.
- **transitionSpec** (AnimationSpec) - Optional - Defines the animation curve (e.g., spring, tween) for specific state changes.

### Request Example
{
  "label": "border width",
  "targetValue": "1.dp"
}

### Response
#### Success Response (200)
- **value** (State<T>) - The current animated value of the property.

#### Response Example
{
  "value": "10.dp"
}
```

--------------------------------

### Physics-based Animation with spring

Source: https://developer.android.com/develop/ui/compose/animation/customize?rec=CkJodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYW5pbWF0aW9uL3Rlc3RpbmcQAxgJIAEoAjAdOgMzLjc

`spring` creates a physics-based animation between start and end values, offering smooth interruptions. It is configured with `dampingRatio` and `stiffness`.

```APIDOC
## AnimationSpec: spring

### Description
The `spring` `AnimationSpec` creates a physics-based animation, simulating a spring's behavior between start and end values. It handles interruptions more smoothly than duration-based specs due to its velocity continuity.

### Method
N/A (Conceptual API parameter)

### Endpoint
N/A (Used within Compose animation functions)

### Parameters
#### Request Body
- **dampingRatio** (Spring.DampingRatio) - Optional - Defines the bounciness of the spring. Default is `Spring.DampingRatioNoBouncy`.
- **stiffness** (Spring.Stiffness) - Optional - Defines how fast the spring moves towards the end value. Default is `Spring.StiffnessMedium`.

### Request Example
```kotlin
val value by animateFloatAsState(
    targetValue = 1f,
    animationSpec = spring(
        dampingRatio = Spring.DampingRatioHighBouncy,
        stiffness = Spring.StiffnessMedium
    ),
    label = "spring spec"
)
```

### Response
#### Success Response (N/A)
The `spring` spec configures the animation's physics.

#### Response Example
N/A
```

--------------------------------

### GET /Theme

Source: https://developer.android.com/develop/ui/compose/designsystems/anatomy

A convenience object used to access the current theme values from anywhere within the composition tree using static properties.

```APIDOC
## GET /Theme

### Description
Accessing theme systems is done using an object with convenience properties. The properties retrieve the current value from the corresponding CompositionLocal.

### Method
Object Property Access

### Endpoint
Theme.[property]

### Parameters
#### Path Parameters
- **colorSystem** (ColorSystem) - Optional - Accesses the current color system values.
- **typographySystem** (TypographySystem) - Optional - Accesses the current typography system values.
- **customSystem** (CustomSystem) - Optional - Accesses the current custom system values.

### Request Example
val primaryColor = Theme.colorSystem.color
val mainStyle = Theme.typographySystem.textStyle

### Response
#### Success Response (200)
- **value** (Multiple) - Returns the current value of the requested theme system property based on the nearest CompositionLocalProvider.
```

--------------------------------

### Define FontFamily with GoogleFont

Source: https://developer.android.com/develop/ui/compose/text/fonts

Create a FontFamily object using GoogleFont and the initialized provider. This basic example defines a FontFamily for the 'Lobster Two' font that will be downloaded from Google Fonts.

```Kotlin
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.Font

val fontName = GoogleFont("Lobster Two")

val fontFamily = FontFamily(
    Font(googleFont = fontName, fontProvider = provider)
)
```

--------------------------------

### ConstraintLayout Basic Usage

Source: https://developer.android.com/develop/ui/compose/layouts/constraintlayout?rec=CkJodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdG91Y2gtaW5wdXQvZm9jdXMQAhgJIAEoAzAaOgMzLjc

Create a ConstraintLayout composable with references and constraints using the DSL. This example demonstrates positioning a Button and Text composable relative to each other using the constrainAs modifier.

```APIDOC
## ConstraintLayout Basic Example

### Description
Demonstrates how to use ConstraintLayout with the Compose DSL to position composables relative to each other and the parent layout.

### Implementation Steps
1. Create references for each composable using `createRefs()` or `createRefFor()`
2. Apply constraints using the `constrainAs()` modifier
3. Specify constraints using `linkTo()` or other constraint methods
4. Use the `parent` reference to constrain towards the ConstraintLayout itself

### Code Example
```kotlin
@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout {
        // Create references for the composables to constrain
        val (button, text) = createRefs()

        Button(
            onClick = { /* Do something */ },
            // Assign reference "button" to the Button composable
            // and constrain it to the top of the ConstraintLayout
            modifier = Modifier.constrainAs(button) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text("Button")
        }

        // Assign reference "text" to the Text composable
        // and constrain it to the bottom of the Button composable
        Text(
            "Text",
            Modifier.constrainAs(text) {
                top.linkTo(button.bottom, margin = 16.dp)
            }
        )
    }
}
```

### Result
The Button is positioned at the top of the ConstraintLayout with a 16.dp margin, and the Text is positioned below the Button, also with a 16.dp margin.
```

--------------------------------

### Implementing function overloading in Java

Source: https://developer.android.com/develop/ui/compose/kotlin?rec=Cj1odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbWVudGFsLW1vZGVsEAEYCSABKAUwHToDMy43

Demonstrates the traditional Java approach of using multiple method signatures to provide optional parameters, a pattern that Kotlin simplifies with default arguments.

```Java
void drawSquare(int sideLength) { }

void drawSquare(int sideLength, int thickness) { }

void drawSquare(int sideLength, int thickness, Color edgeColor) { }
```

--------------------------------

### spring AnimationSpec

Source: https://developer.android.com/develop/ui/compose/animation/customize?rec=CkJodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYW5pbWF0aW9uL3Rlc3RpbmcQAxgJIAEoBDAgOgMzLjc

Creates a physics-based animation between start and end values. It handles interruptions smoothly by maintaining velocity continuity.

```APIDOC
## spring AnimationSpec

### Description
Creates a physics-based animation between start and end values. It is the default AnimationSpec for many Compose animation APIs because it guarantees velocity continuity when target values change during an active animation.

### Method
spring(dampingRatio, stiffness, visibilityThreshold)

### Parameters
#### Arguments
- **dampingRatio** (Float) - Optional - Defines how bouncy the spring should be. Defaults to Spring.DampingRatioNoBouncy.
- **stiffness** (Float) - Optional - Defines how fast the spring should move toward the end value. Defaults to Spring.StiffnessMedium.
- **visibilityThreshold** (T) - Optional - Specifies when the animation should be considered close enough to the target value to stop.

### Request Example
{
  "animationSpec": "spring(dampingRatio = Spring.DampingRatioHighBouncy, stiffness = Spring.StiffnessMedium)"
}

### Response
#### Success Response
- **AnimationSpec** (Object) - A physics-based animation specification used to drive state changes.
```

--------------------------------

### Testing Mixed Compose and Views UI with createAndroidComposeRule

Source: https://developer.android.com/develop/ui/compose/migrate/other-considerations

Demonstrates how to test activities or fragments that use both Compose and View components together. Uses createAndroidComposeRule instead of ActivityScenarioRule to integrate ComposeTestRule with ActivityScenarioRule, enabling unified testing of both UI frameworks. The example retrieves a string resource and asserts that a Compose node displays it.

```Kotlin
class MyActivityTest {
    @Rule
    @JvmField
    val composeTestRule = createAndroidComposeRule<MyActivity>()

    @Test
    fun testGreeting() {
        val greeting = InstrumentationRegistry.getInstrumentation()
            .targetContext.resources.getString(R.string.greeting)

        composeTestRule.onNodeWithText(greeting).assertIsDisplayed()
    }
}
```

--------------------------------

### FUNCTION spring

Source: https://developer.android.com/develop/ui/compose/animation/customize?rec=Ck5odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdG9vbGluZy9pdGVyYXRpdmUtZGV2ZWxvcG1lbnQQARgJIAEoBDAgOgMzLjc

Creates a physics-based animation between start and end values. It handles interruptions smoothly by maintaining velocity continuity.

```APIDOC
## FUNCTION spring\n\n### Description\nCreates a physics-based animation between start and end values. It is the default spec for many Compose animation APIs because it guarantees velocity continuity when target values change during an active animation.\n\n### Method\nFUNCTION\n\n### Endpoint\nspring(dampingRatio, stiffness, visibilityThreshold)\n\n### Parameters\n#### Function Parameters\n- **dampingRatio** (Float) - Optional - Defines how bouncy the spring should be. Default: Spring.DampingRatioNoBouncy.\n- **stiffness** (Float) - Optional - Defines how fast the spring should move toward the end value. Default: Spring.StiffnessMedium.\n- **visibilityThreshold** (T) - Optional - The threshold at which the animation is considered close enough to the target to stop.\n\n### Request Example\n{\n  "animationSpec": "spring(dampingRatio = Spring.DampingRatioHighBouncy, stiffness = Spring.StiffnessMedium)"\n}\n\n### Response\n#### Success Response (200)\n- **spec** (AnimationSpec) - A physics-based animation specification.
```

--------------------------------

### Migrate Theme Color and isLight from M2 to M3

Source: https://developer.android.com/develop/ui/compose/designsystems/material2-material3?rec=CkhodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZGVzaWduc3lzdGVtcy9tYXRlcmlhbDMQARgJIAEoCTAZOgMzLjc

Demonstrates how to handle theme-level color information. In M3, the isLight property is removed, requiring manual modeling of theme-specific values using CompositionLocal.

```Kotlin (M2)
import androidx.compose.material.lightColors
import androidx.compose.material.darkColors
import androidx.compose.material.MaterialTheme

@Composable
private fun AppTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colors = if (darkTheme) darkColors() else lightColors()
  MaterialTheme(
      colors = colors,
      content = content
  )
}

@Composable
fun AppComposable() {
    AppTheme {
        val cardElevation = if (MaterialTheme.colors.isLight) 0.dp else 4.dp
    }
}
```

```Kotlin (M3)
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.MaterialTheme

val LocalCardElevation = staticCompositionLocalOf { Dp.Unspecified }
@Composable
private fun AppTheme(
   darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
   val cardElevation = if (darkTheme) 4.dp else 0.dp
    CompositionLocalProvider(LocalCardElevation provides cardElevation) {
        val colorScheme = if (darkTheme) darkColorScheme() else lightColorScheme()
        MaterialTheme(
            colorScheme = colorScheme,
            content = content
        )
    }
}

@Composable
fun AppComposable() {
    AppTheme {
        val cardElevation = LocalCardElevation.current
    }
}
```

--------------------------------

### GET Modifier.scrollableArea

Source: https://developer.android.com/develop/ui/compose/touch-input/scroll/scroll-modifiers

A fundamental building block for creating custom scrollable containers. It provides a higher-level abstraction over the scrollable modifier.

```APIDOC
## GET Modifier.scrollableArea

### Description
Handles common requirements like gesture delta interpretation, content clipping, and overscroll effects for custom scrollable implementations.

### Method
COMPOSABLE_MODIFIER

### Endpoint
Modifier.scrollableArea(state, orientation, enabled, ...)

### Parameters
#### Path Parameters
- **state** (ScrollableState) - Required - The state object for the scrollable area.
- **orientation** (Orientation) - Required - The axis along which scrolling is allowed.

### Request Example
{
  "usage": "Modifier.scrollableArea(state = customState, orientation = Orientation.Vertical)"
}

### Response
#### Success Response (200)
- **Modifier** (Modifier) - A modifier that handles complex scroll gestures and effects.

#### Response Example
{
  "type": "ScrollableAreaModifier"
}
```

--------------------------------

### Apply TextStyle from MaterialTheme Typography

Source: https://developer.android.com/develop/ui/compose/designsystems/material

Demonstrates how to access and apply TextStyle elements from MaterialTheme.typography to Text composables. This example retrieves the subtitle2 style and applies it to a Text composable.

```Kotlin
Text(
    text = "Subtitle2 styled",
    style = MaterialTheme.typography.subtitle2
)
```

--------------------------------

### GET stringResource

Source: https://developer.android.com/develop/ui/compose/resources

Retrieves a string statically defined in XML resources. Supports positional formatting for dynamic content insertion.

```APIDOC
## GET stringResource

### Description
Retrieves a string statically defined in XML resources. This is the standard way to handle localized text in Compose.

### Method
GET

### Endpoint
stringResource(id: Int, varargs formatArgs: Any)

### Parameters
#### Path Parameters
- **id** (Int) - Required - The resource ID (e.g., R.string.name).
- **formatArgs** (Any) - Optional - Variable arguments for positional formatting (e.g., %1$s).

### Request Example
// Simple string
stringResource(R.string.compose)

// Formatted string
stringResource(R.string.congratulate, "New Year", 2021)

### Response
#### Success Response (200)
- **value** (String) - The resolved string from the resources folder.
```

--------------------------------

### User-Initiated Split-Screen Implementation

Source: https://developer.android.com/develop/ui/compose/layouts/adaptive/support-multi-window-mode?hl=th

Documentation for enabling user-initiated split-screen mode through the Recents screen interface, allowing users to manually split their screen between two applications.

```APIDOC
## User-Initiated Split-Screen Mode

### Description
Enables users to manually initiate split-screen mode through the Recents screen interface.

### Implementation Steps
1. Open the Recents screen
2. Swipe an app into view
3. Press the app icon in the app's title bar
4. Select the **Split screen** menu option
5. Select another app from the Recents screen

### Exiting Split-Screen
Users exit split-screen mode by dragging the window divider to the edge of the screen (up, down, left, or right).

### Requirements
- Minimum Android 7.0 (API level 24) for split-screen support
- Activity must have `resizeableActivity="true"` (default) to support split-screen
- Proper `minWidth` and `minHeight` configuration for small screen devices on Android 12+
```

--------------------------------

### Handle Video Playback in Multi-Window Mode with Lifecycle Events

Source: https://developer.android.com/develop/ui/compose/layouts/adaptive/support-multi-window-mode

Demonstrates the recommended approach for managing video playback across lifecycle events in multi-window mode. On Android 9 and lower, activities may not be in RESUMED state while visible, so video apps should start playback on ON_START and pause on ON_STOP rather than using ON_PAUSE. This ensures continuous playback when the app is visible but not topmost.

```Java
@Override
public void onStart() {
    super.onStart();
    // Resume video playback
    if (videoView != null) {
        videoView.resume();
    }
}

@Override
public void onStop() {
    super.onStop();
    // Pause video playback
    if (videoView != null) {
        videoView.pause();
    }
}
```

--------------------------------

### GET /modifiers/weight

Source: https://developer.android.com/develop/ui/compose/modifiers?rec=Cj9odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbGF5b3V0cy9iYXNpY3MQARgJIAEoAjAZOgMzLjc

A modifier available in RowScope and ColumnScope to distribute available space among children based on a relative weight.

```APIDOC
## GET Modifier.weight()

### Description
Sets the weight of a child within a Row or Column to determine how much of the remaining space it should occupy relative to its siblings.

### Method
GET

### Endpoint
RowScope.weight / ColumnScope.weight

### Parameters
#### Path Parameters
- **scope** (RowScope|ColumnScope) - Required - The scope provided by the parent Row or Column.

#### Query Parameters
- **weight** (Float) - Required - The proportional weight to assign to this element.
- **fill** (Boolean) - Optional - Whether the element should fill the space (default is true).

#### Request Body
- N/A

### Request Example
{
  "weight": 2.0,
  "fill": true
}

### Response
#### Success Response (200)
- **modifier** (Modifier) - The modifier instance with weight properties applied.

#### Response Example
{
  "status": "success",
  "assignedWeight": 2.0
}
```

--------------------------------

### Implement a basic SuggestionChip for dynamic hints

Source: https://developer.android.com/develop/ui/compose/components/chip

This snippet shows the implementation of a SuggestionChip, which is used to present informational hints or suggested actions to the user. It features a simple label and an onClick callback for interactivity.

```kotlin
@Composable
fun SuggestionChipExample() {
    SuggestionChip(
        onClick = { Log.d("Suggestion chip", "hello world") },
        label = { Text("Suggestion chip") }
    )
}
```

--------------------------------

### Navigation Components for Adaptive Layouts

Source: https://developer.android.com/develop/ui/compose/designsystems/material3?rec=CjRodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYm9tEAEYCSABKAUwDDoDMy43

Comprehensive guide to Material Design 3 navigation components including NavigationBar, NavigationRail, and NavigationDrawer, optimized for different device sizes and screen orientations.

```APIDOC
## Navigation Components

### Description
Material Design 3 provides adaptive navigation components designed for different screen sizes and device types. Choose the appropriate component based on your target device.

### NavigationBar
Optimal for compact devices with 5 or fewer destinations.

**Use Cases:**
- Mobile phones in portrait mode
- Compact devices
- 5 or fewer navigation destinations

**Implementation:**
```kotlin
NavigationBar(modifier = Modifier.fillMaxWidth()) {
    Destinations.entries.forEach { replyDestination ->
        NavigationBarItem(
            selected = selectedDestination == replyDestination,
            onClick = { /* navigate */ },
            icon = { /* icon */ }
        )
    }
}
```

### NavigationRail
Designed for small-to-medium tablets or phones in landscape mode.

**Use Cases:**
- Tablets in portrait mode
- Phones in landscape orientation
- Improved ergonomics for larger screens

**Implementation:**
```kotlin
NavigationRail(
    modifier = Modifier.fillMaxHeight()
) {
    Destinations.entries.forEach { replyDestination ->
        NavigationRailItem(
            selected = selectedDestination == replyDestination,
            onClick = { /* navigate */ },
            icon = { /* icon */ }
        )
    }
}
```

### NavigationDrawer
Ideal for medium-to-large tablets with sufficient space for detailed content.

**Variants:**
- **PermanentNavigationDrawer** - Always visible
- **ModalNavigationDrawer** - Toggleable drawer

**Implementation:**
```kotlin
PermanentNavigationDrawer(
    modifier = Modifier.fillMaxHeight(),
    drawerContent = {
        Destinations.entries.forEach { replyDestination ->
            NavigationRailItem(
                selected = selectedDestination == replyDestination,
                onClick = { /* navigate */ },
                icon = { /* icon */ },
                label = { /* label */ }
            )
        }
    }
) {
    /* main content */
}
```

### Device Size Guidelines
| Component | Device Type | Screen Size | Orientation |
|-----------|------------|-------------|-------------|
| NavigationBar | Mobile | Compact | Portrait |
| NavigationRail | Tablet/Phone | Small-Medium | Landscape |
| NavigationDrawer | Tablet | Medium-Large | Any |

### Best Practices
- Use NavigationBar for mobile-first designs
- Implement NavigationRail for landscape support
- Combine NavigationDrawer with NavigationRail for tablets
- Enhance user experience through adaptive navigation
- Improve ergonomics and reachability across devices
```

--------------------------------

### Import Compose BOM from Version Catalog in Kotlin

Source: https://developer.android.com/develop/ui/compose/bom

Demonstrates how to import the Compose BOM from a version catalog in the module's build.gradle file using Kotlin syntax. The BOM is applied to both implementation and androidTestImplementation configurations.

```Kotlin
dependencies {
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)

    // import Compose dependencies as usual
}
```

--------------------------------

### GET /Paging/collectAsLazyPagingItems

Source: https://developer.android.com/develop/ui/compose/lists?rec=CkdodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdG9vbGluZy9lZGl0b3ItYWN0aW9ucxADGAkgASgDMCE6AzMuNw

Collects values from a Flow of PagingData and represents them as LazyPagingItems for use in Lazy layouts like LazyColumn or LazyRow.

```APIDOC
## GET /Paging/collectAsLazyPagingItems

### Description
An extension function that collects PagingData from a Flow and converts it into a state object compatible with Lazy layouts.

### Method
GET

### Endpoint
/Paging/collectAsLazyPagingItems

### Parameters
#### Query Parameters
- **pager** (Pager) - Required - The Pager object providing the flow of data.

### Request Example
{
  "pager": "Pager<Int, Message>"
}

### Response
#### Success Response (200)
- **lazyPagingItems** (LazyPagingItems) - A state object used to access paged items and their loading state.
```

--------------------------------

### Apply Clipping and Shapes using graphicsLayer and Modifier.clip

Source: https://developer.android.com/develop/ui/compose/graphics/draw/modifiers

This example compares two methods for clipping content to a shape. It shows how to use the clip and shape properties inside a graphicsLayer block and how to use the Modifier.clip extension function for the same purpose.

```Kotlin
Column(modifier = Modifier.padding(16.dp)) {
    Box(
        modifier = Modifier
            .size(200.dp)
            .graphicsLayer {
                clip = true
                shape = CircleShape
            }
            .background(Color(0xFFF06292))
    ) {
        Text(
            "Hello Compose",
            style = TextStyle(color = Color.Black, fontSize = 46.sp),
            modifier = Modifier.align(Alignment.Center)
        )
    }
    Box(
        modifier = Modifier
            .size(200.dp)
            .clip(CircleShape)
            .background(Color(0xFF4DB6AC))
    )
}
```

--------------------------------

### GET WindowManager#getCurrentWindowMetrics()

Source: https://developer.android.com/develop/ui/compose/layouts/adaptive/adaptive-dos-and-donts

Retrieves the current window metrics, including bounds and density, replacing deprecated Display APIs like getSize() and getMetrics().

```APIDOC
## GET WindowManager#getCurrentWindowMetrics()

### Description
Retrieves the size and density of the application window. This is the recommended replacement for deprecated Display APIs to ensure correct measurement of the app window across different device states.

### Method
GET

### Endpoint
WindowManager#getCurrentWindowMetrics()

### Parameters
#### Path Parameters
- None

### Request Example
{}

### Response
#### Success Response (200)
- **bounds** (Rect) - The size of the app window.
- **density** (float) - The display density.

### Response Example
{
  "bounds": "Rect(0, 0 - 1080, 2400)",
  "density": 2.625
}
```

--------------------------------

### Handle IME and Navigation Bars using RectRulers.innermostOf

Source: https://developer.android.com/develop/ui/compose/system/evaluate-rulers

This example shows how to handle the on-screen keyboard (IME) by combining multiple rulers. By using RectRulers.innermostOf, the content aligns to the innermost boundary of both the navigation bars and the active IME.

```Kotlin
@Composable
fun FitInsideWithImeDemo(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .fitInside(
                RectRulers.innermostOf(
                    WindowInsetsRulers.NavigationBars.current,
                    WindowInsetsRulers.Ime.current
                )
            )
    ) {
        TextField(
            value = "Demo IME Insets",
            onValueChange = {},
            modifier = modifier.align(Alignment.BottomStart).fillMaxWidth()
        )
    }
}
```

--------------------------------

### Map Composable Destinations and Extract from Fragments

Source: https://developer.android.com/develop/ui/compose/migrate/migration-scenarios/navigation

Demonstrates the transition of screen logic from Fragment-based ComposeView containers into NavHost composable destinations.

```kotlin
class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                // FirstScreen(...) EXTRACT FROM HERE
            }
        }
    }
}

@Composable
fun SampleNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = First) {
        composable<First> {
            FirstScreen(/* ... */) // EXTRACT TO HERE
        }
        composable<Second> {
            SecondScreen(/* ... */)
        }
        // ...
    }
}
```

--------------------------------

### GET /lazy-list/state

Source: https://developer.android.com/develop/ui/compose/lists?rec=CkdodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdG9vbGluZy9lZGl0b3ItYWN0aW9ucxADGAkgASgBMAw6AzMuNw

Retrieves and monitors the scroll state of a Lazy component, allowing for UI reactions based on scroll position.

```APIDOC
## GET /lazy-list/state

### Description
Hoists the LazyListState to observe properties like the first visible item index and scroll offset.

### Method
GET

### Endpoint
androidx.compose.foundation.lazy.rememberLazyListState

### Parameters
#### Query Parameters
- **initialFirstVisibleItemIndex** (Int) - Optional - The initial scroll position index.
- **initialFirstVisibleItemScrollOffset** (Int) - Optional - The initial scroll offset.

### Request Example
{
  "state": "val listState = rememberLazyListState()"
}

### Response
#### Success Response (200)
- **firstVisibleItemIndex** (Int) - The index of the first item currently visible on screen.
- **firstVisibleItemScrollOffset** (Int) - The scroll offset of the first visible item.
- **layoutInfo** (LazyListLayoutInfo) - Detailed information about all currently displayed items.
```

--------------------------------

### Recommended: Encapsulating RememberObserver with `remember` in Kotlin

Source: https://developer.android.com/develop/ui/compose/state-callbacks

This Kotlin example demonstrates the recommended pattern for managing stateful objects that require `RememberObserver` functionality. It encapsulates the `RememberObserver` implementation within a `remember` block, preventing its public exposure and ensuring proper initialization and teardown via `initialize()` and `teardown()` methods.

```kotlin
abstract class MyManager

class MyComposeManager : MyManager() {
    // Callers that construct this object must manually call initialize and teardown
    fun initialize() { /*...*/ }
    fun teardown() { /*...*/ }
}

@Composable
fun rememberMyManager(): MyManager {
    // Protect the RememberObserver implementation by never exposing it outside the library
    return remember {
        object : RememberObserver {
            val manager = MyComposeManager()
            override fun onRemembered() = manager.initialize()
            override fun onForgotten() = manager.teardown()
            override fun onAbandoned() { /* Nothing to do if manager hasn't initialized */ }
        }
    }.manager
}
```

--------------------------------

### GET /LazyListState

Source: https://developer.android.com/develop/ui/compose/lists?rec=CjdodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvcGhhc2VzEAIYCSABKAMwIToDMy43

API for hoisting and observing the scroll state of Lazy components to react to layout changes and scroll positions.

```APIDOC
## GET /LazyListState

### Description
Hoists the state of a Lazy list to allow developers to monitor the first visible item index, scroll offsets, and overall layout information.

### Method
GET

### Endpoint
/androidx.compose.foundation.lazy.LazyListState

### Parameters
#### Path Parameters
- **firstVisibleItemIndex** (Int) - Required - The index of the first item currently visible on screen.
- **firstVisibleItemScrollOffset** (Int) - Required - The scroll offset of the first visible item in pixels.

#### Query Parameters
- **layoutInfo** (LazyListLayoutInfo) - Optional - Provides detailed information about all items currently displayed.

### Request Example
{
  "state": "rememberLazyListState()",
  "observation": "derivedStateOf { listState.firstVisibleItemIndex > 0 }"
}

### Response
#### Success Response (200)
- **isScrolled** (Boolean) - Returns true if the user has scrolled past the first item.

#### Response Example
{
  "firstVisibleItemIndex": 5,
  "isScrolled": true
}
```

--------------------------------

### Import Compose BOM from Version Catalog in Groovy

Source: https://developer.android.com/develop/ui/compose/bom

Demonstrates how to import the Compose BOM from a version catalog in the module's build.gradle file using Groovy syntax. The BOM is applied to both implementation and androidTestImplementation configurations.

```Groovy
dependencies {
    Dependency composeBom = platform(libs.androidx.compose.bom)
    implementation composeBom
    androidTestImplementation composeBom

    // import Compose dependencies as usual
}
```

--------------------------------

### GET /androidx.compose.material.Scaffold

Source: https://developer.android.com/develop/ui/compose/layouts/basics?rec=CjdodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2Uva290bGluEAIYCSABKAYwFjoDMy43

Scaffold implements the basic Material Design visual layout structure, providing slots for common top-level components like AppBars and FABs.

```APIDOC
## GET /androidx.compose.material.Scaffold

### Description
A slot-based API that simplifies the implementation of standard Material Design screen structures.

### Method
GET

### Endpoint
/androidx.compose.material.Scaffold

### Parameters
#### Request Body
- **topBar** (Composable) - Optional - Slot for a TopAppBar.
- **bottomBar** (Composable) - Optional - Slot for a BottomAppBar.
- **floatingActionButton** (Composable) - Optional - Slot for a FloatingActionButton.
- **drawerContent** (Composable) - Optional - Slot for a navigation drawer.
- **content** (Composable) - Required - The main content of the screen.

### Request Example
{
  "topBar": "{ TopAppBar() }",
  "content": "{ padding -> LazyColumn(contentPadding = padding) { ... } }"
}

### Response
#### Success Response (200)
- **UI** (Composable) - The structured Material Design layout.

#### Response Example
{
  "layout": "Scaffold",
  "slots_filled": ["topBar", "content"]
}
```

--------------------------------

### Select Multiple Nodes in Compose UI Tests

Source: https://developer.android.com/develop/ui/compose/testing/apis?rec=CkZodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYWNjZXNzaWJpbGl0eS90ZXN0aW5nEAIYCSABKAIwHDoDMy43

This snippet illustrates how to select multiple UI nodes using composeTestRule.onAllNodes() with a SemanticsMatcher. An example is provided demonstrating the use of hasText() which is equivalent to onAllNodesWithText().

```Kotlin
composeTestRule
    .onAllNodes(<<SemanticsMatcher>>): SemanticsNodeInteractionCollection
```

```Kotlin
composeTestRule
    .onAllNodes(hasText("Button")) // Equivalent to onAllNodesWithText("Button")
```

--------------------------------

### Custom Elevation Overlays using LocalElevationOverlay

Source: https://developer.android.com/develop/ui/compose/designsystems/material?rec=CkhodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYWNjZXNzaWJpbGl0eS9zZW1hbnRpY3MQAhgJIAEoBTAaOgMzLjc

Shows how to manually apply elevation overlays for custom scenarios not involving a Surface composable, by using the `LocalElevationOverlay` CompositionLocal.

```APIDOC
## UI Component Custom Elevation Overlays

### Description
For custom UI elements that do not use a `Surface` composable, you can manually apply elevation overlays using `LocalElevationOverlay`. This `CompositionLocal` provides the `ElevationOverlay` used by `Surface` components.

### Method
UI Component Styling

### Endpoint
`LocalElevationOverlay.current?.apply`

### Parameters
#### Request Body
- **color** (Color) - Required - The base color to which the elevation overlay should be applied.
- **elevation** (Dp) - Required - The elevation level to use for the overlay calculation.

### Request Example
```kotlin
val color = MaterialTheme.colors.surface
val elevation = 4.dp
val overlaidColor = LocalElevationOverlay.current?.apply(
    color, elevation
)
```

### Response
#### Success Response (200)
- **overlaidColor** (Color) - The calculated color with the elevation overlay applied.

#### Response Example
```kotlin
// overlaidColor will be a lighter shade of 'color' if in a dark theme and elevation > 0.
```
```

--------------------------------

### Apply Shape from MaterialTheme to Surface

Source: https://developer.android.com/develop/ui/compose/designsystems/material

Demonstrates how to access and apply Shape elements from MaterialTheme.shapes to composable components. This example retrieves the medium shape and applies it to a Surface composable.

```Kotlin
Surface(
    shape = MaterialTheme.shapes.medium, /*...*/
) {
    /*...*/
}
```

--------------------------------

### Pass explicit parameters as an alternative to CompositionLocal

Source: https://developer.android.com/develop/ui/compose/compositionlocal?rec=CkZodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZGVzaWduc3lzdGVtcy9hbmF0b215EAEYCSABKAkwLToDMy43

This example illustrates the best practice of passing explicit parameters to composables instead of using CompositionLocal. It promotes decoupling by ensuring composables only receive the specific data they need.

```kotlin
@Composable
fun MyComposable(myViewModel: MyViewModel = viewModel()) {
    // Pass only what the descendant needs
    MyDescendant(myViewModel.data)
}

// Don't pass the whole object! Just what the descendant needs.
@Composable
fun MyDescendant(data: DataToDisplay) {
    // Display data
}
```

--------------------------------

### Adding Color and Size Animations

Source: https://developer.android.com/develop/ui/compose/tutorial?hl=es-419

Shows how to implement smooth transitions for background colors using animateColorAsState and automatic layout size changes using the animateContentSize modifier.

```Kotlin
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize

// surfaceColor will be updated gradually from one color to the other
val surfaceColor by animateColorAsState(
    if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
)

Surface(
    shape = MaterialTheme.shapes.medium,
    shadowElevation = 1.dp,
    color = surfaceColor,
    // animateContentSize will change the Surface size gradually
    modifier = Modifier.animateContentSize().padding(1.dp)
) {
    Text(
        text = msg.body,
        modifier = Modifier.padding(all = 4.dp),
        maxLines = if (isExpanded) Int.MAX_VALUE else 1,
        style = MaterialTheme.typography.bodyMedium
    )
}
```

--------------------------------

### Group Multiple Modifiers into a Reusable Extension

Source: https://developer.android.com/develop/ui/compose/custom-modifiers

Shows how to combine several modifiers into a single extension function to avoid repetition. This example bundles padding, clipping, and background styling into a single 'myBackground' modifier.

```kotlin
fun Modifier.myBackground(color: Color) = padding(16.dp)
    .clip(RoundedCornerShape(8.dp))
    .background(color)
```

--------------------------------

### GET /LocalContentAlpha

Source: https://developer.android.com/develop/ui/compose/designsystems/material?rec=Ck1odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbWlncmF0ZS9vdGhlci1jb25zaWRlcmF0aW9ucxACGAkgASgFMAs6AzMuNw

Manages content emphasis by providing different levels of opacity (high, medium, disabled) to a hierarchy of composables.

```APIDOC
## GET /LocalContentAlpha

### Description
Provides a content alpha value for a hierarchy to communicate importance and provide visual hierarchy.

### Method
GET

### Endpoint
/LocalContentAlpha

### Parameters
#### CompositionLocal
- **LocalContentAlpha** (ContentAlpha) - Required - The alpha level to apply (high, medium, disabled).

### Request Example
{
  "provides": "ContentAlpha.medium"
}

### Response
#### Success Response (200)
- **alpha** (Float) - The applied opacity value.

#### Response Example
{
  "alpha": 0.38
}
```

--------------------------------

### POST /operations/presentOnArea

Source: https://developer.android.com/develop/ui/compose/layouts/adaptive/foldables/support-foldable-display-modes

Initiates dual-screen mode by presenting content on an additional display area.

```APIDOC
## POST /operations/presentOnArea

### Description
Triggers the OPERATION_PRESENT_ON_AREA operation to start dual-screen mode functionality.

### Method
POST

### Endpoint
/operations/presentOnArea

### Parameters
#### Request Body
- **operation** (String) - Required - The operation constant: OPERATION_PRESENT_ON_AREA.

### Request Example
{
  "operation": "OPERATION_PRESENT_ON_AREA"
}

### Response
#### Success Response (200)
- **windowAreaSession** (WindowAreaSession) - An interface representing the active window area feature session.

#### Response Example
{
  "sessionStatus": "ACTIVE"
}
```

--------------------------------

### Use transition with AnimatedVisibility and AnimatedContent

Source: https://developer.android.com/develop/ui/compose/animation/value-based

Demonstrates using AnimatedVisibility and AnimatedContent as extension functions of the Transition API. This approach allows developers to hoist enter/exit animations and synchronize them with other transition-based properties like color and elevation.

```Kotlin
var selected by remember { mutableStateOf(false) }
// Animates changes when `selected` is changed.
val transition = updateTransition(selected, label = "selected state")
val borderColor by transition.animateColor(label = "border color") { isSelected ->
    if (isSelected) Color.Magenta else Color.White
}
val elevation by transition.animateDp(label = "elevation") { isSelected ->
    if (isSelected) 10.dp else 2.dp
}
Surface(
    onClick = { selected = !selected },
    shape = RoundedCornerShape(8.dp),
    border = BorderStroke(2.dp, borderColor),
    shadowElevation = elevation
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Hello, world!")
        // AnimatedVisibility as a part of the transition.
        transition.AnimatedVisibility(
            visible = { targetSelected -> targetSelected },
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Text(text = "It is fine today.")
        }
        // AnimatedContent as a part of the transition.
        transition.AnimatedContent { targetState ->
            if (targetState) {
                Text(text = "Selected")
            } else {
                Icon(imageVector = Icons.Default.Phone, contentDescription = "Phone")
            }
        }
    }
}
```

--------------------------------

### GET /material3/button

Source: https://developer.android.com/develop/ui/compose/designsystems/material3?rec=CjRodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYm9tEAEYCSABKAUwFjoDMy43

Configures a Material 3 Button component with accessible color roles to ensure sufficient contrast ratios.

```APIDOC
## GET /material3/button\n\n### Description\nCustomizes Button colors using ButtonDefaults while adhering to accessibility standards for color contrast.\n\n### Method\nGET\n\n### Endpoint\n/material3/button\n\n### Parameters\n#### Request Body\n- **colors** (ButtonColors) - Optional - Defines containerColor and contentColor roles.\n- **onClick** (Lambda) - Required - The action to perform when the button is clicked.\n\n### Request Example\n{\n  \"containerColor\": \"MaterialTheme.colorScheme.primary\",\n  \"contentColor\": \"MaterialTheme.colorScheme.onPrimary\"\n}\n\n### Response\n#### Success Response (200)\n- **Composable** (UI) - The rendered Material 3 Button component.\n\n#### Response Example\n{\n  \"status\": \"rendered\",\n  \"component\": \"Button\"\n}
```

--------------------------------

### Concurrent Coroutines - Parallel Execution

Source: https://developer.android.com/develop/ui/compose/kotlin?rec=Cj1odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2Uvc2lkZS1lZmZlY3RzEAIYCSABKAQwGzoDMy43

Shows how to execute multiple operations concurrently by launching separate coroutines instead of sequential execution. This example demonstrates launching two independent coroutines for scrolling and data loading in parallel.

```APIDOC
## Concurrent Coroutines - Parallel Execution

### Description
Demonstrates how to execute code concurrently by creating multiple coroutines for independent work items instead of relying on sequential execution.

### Concept
Coroutines execute sequentially by default. To parallelize operations, create a new coroutine for each independent task using separate launch calls.

### Code Example
```kotlin
val composableScope = rememberCoroutineScope()
Button(
    onClick = {
        // Launch two independent coroutines for parallel execution
        composableScope.launch {
            scrollState.animateScrollTo(0)
        }
        composableScope.launch {
            viewModel.loadData()
        }
    }
) { /* Button content */ }
```

### Key Points
- Each launch call creates a new independent coroutine
- Multiple coroutines execute concurrently
- Ideal for parallelizing independent operations
- Each coroutine can have its own suspend function calls
- Improves UI responsiveness by avoiding blocking operations
```

--------------------------------

### POST /theme/CustomTheme

Source: https://developer.android.com/develop/ui/compose/designsystems/custom?rec=Cj5odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZGVzaWduc3lzdGVtcxACGAkgASgFMBY6AzMuNw

Initializes the custom design system by providing specific color, typography, and elevation instances via CompositionLocalProvider to the UI tree.

```APIDOC
## POST /theme/CustomTheme

### Description
Initializes the custom design system by providing specific color, typography, and elevation instances via CompositionLocalProvider to the UI tree.

### Method
POST (Composable)

### Endpoint
CustomTheme(content)

### Parameters
#### Request Body
- **content** (@Composable () -> Unit) - Required - The UI content to be wrapped by the custom theme.

### Request Example
{
  "content": "@Composable { AppContent() }"
}

### Response
#### Success Response (200)
- **CompositionLocal** (Context) - Updates the local composition context with CustomColors, CustomTypography, and CustomElevation.
```

--------------------------------

### DecayAnimation

Source: https://developer.android.com/develop/ui/compose/animation/value-based?rec=CkJodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYW5pbWF0aW9uL3Rvb2xpbmcQARgJIAEoAjAbOgMzLjc

An animation engine that calculates its target value based on starting conditions and a decay specification, often used for fling gestures.

```APIDOC
## DecayAnimation

### Description
Calculates values based on initial velocity and slows down over time using a DecayAnimationSpec.

### Method
CONSTRUCTOR

### Endpoint
androidx.compose.animation.core.DecayAnimation

### Parameters
#### Request Body
- **animationSpec** (DecayAnimationSpec) - Required - The specification for the decay (e.g., splineBasedDecay).
- **typeConverter** (TwoWayConverter) - Required - Converts the value type to an AnimationVector.
- **initialValue** (T) - Required - The starting value of the animation.
- **initialVelocity** (T) - Required - The starting velocity vector.

### Request Example
{
  "animationSpec": "exponentialDecay()",
  "typeConverter": "Float.VectorConverter",
  "initialValue": 0.0,
  "initialVelocity": 2000.0
}

### Response
#### Success Response
- **animationValue** (T) - The calculated value at a specific play time as the velocity decays.
```

--------------------------------

### Demonstrate GraphicsLayer isolation and clipping behavior

Source: https://developer.android.com/develop/ui/compose/graphics/draw/modifiers?rec=CkBodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdGV4dC9zdHlsZS10ZXh0EAMYCSABKAEwEToDMy43

This example shows the default behavior of graphicsLayer in Compose, where drawing instructions that exceed the composable's bounds are not clipped unless an offscreen buffer is explicitly or implicitly allocated.

```kotlin
@Composable
fun CompositingStrategyExamples() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        // Does not clip content even with a graphics layer usage here.
        Canvas(
            modifier = Modifier
                .graphicsLayer()
                .size(100.dp)
                .border(2.dp, color = Color.Blue)
        ) {
            // Drawing a size of 200 dp here outside the bounds
            drawRect(color = Color.Magenta, size = Size(200.dp.toPx(), 200.dp.toPx()))
        }

        Spacer(modifier = Modifier.size(300.dp))
    }
}
```

--------------------------------

### GRADLE implementation

Source: https://developer.android.com/develop/ui/compose/designsystems/material3?rec=ClJodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZGVzaWduc3lzdGVtcy9tYXRlcmlhbDItbWF0ZXJpYWwzEAIYCSABKAUwDDoDMy43

Add the Material 3 dependency to your project's build.gradle file to enable Material You components and theming.

```APIDOC
## GRADLE implementation

### Description
Adds the Material 3 library to the project to enable M3 components and theming.

### Method
DEPENDENCY

### Endpoint
androidx.compose.material3:material3

### Parameters
#### Path Parameters
- **material3_version** (string) - Required - The version of the Material 3 library to include.

### Request Example
implementation "androidx.compose.material3:material3:$material3_version"

### Response
#### Success Response (200)
- **Library** (Artifact) - Material 3 library components available for import.
```

--------------------------------

### GET colorResource

Source: https://developer.android.com/develop/ui/compose/resources

Retrieves color values from XML resource files. Note that it flattens color state lists into static colors.

```APIDOC
## GET colorResource

### Description
Retrieves colors from a resource XML file. While functional, it is recommended to use MaterialTheme colors for better design system integration.

### Method
GET

### Endpoint
colorResource(id: Int)

### Parameters
#### Path Parameters
- **id** (Int) - Required - The resource ID of the color (e.g., R.color.purple_200).

### Request Example
HorizontalDivider(color = colorResource(R.color.purple_200))

### Response
#### Success Response (200)
- **value** (Color) - The resolved Compose Color object.
```

--------------------------------

### Implement a simple UI screen using Jetpack Compose

Source: https://developer.android.com/develop/ui/compose/migrate/strategy?rec=Cl9odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbWlncmF0ZS9pbnRlcm9wZXJhYmlsaXR5LWFwaXMvY29tcG9zZS1pbi12aWV3cxACGAkgASgBMAs6AzMuNw

This Kotlin Composable function demonstrates how to recreate the UI defined in the XML example using Jetpack Compose. It utilizes standard Compose UI elements like Column, Text, Spacer, and Button to achieve a similar vertical layout, showcasing a direct migration approach.

```kotlin
@Composable
fun SimpleScreen() {
    Column(Modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.title),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = stringResource(R.string.subtitle),
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = stringResource(R.string.body),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = { /* Handle click */ }, Modifier.fillMaxWidth()) {
            Text(text = stringResource(R.string.confirm))
        }
    }
}
```

--------------------------------

### GET pluralStringResource

Source: https://developer.android.com/develop/ui/compose/resources

Loads a plural resource based on a specific quantity. This API is currently experimental and handles grammatical variations for numbers.

```APIDOC
## GET pluralStringResource

### Description
Use the pluralStringResource API to load a plural with a certain quantity. If the string includes formatting, the count must be passed twice.

### Method
GET

### Endpoint
pluralStringResource(id: Int, count: Int, varargs formatArgs: Any)

### Parameters
#### Path Parameters
- **id** (Int) - Required - The resource ID of the plurals element.
- **count** (Int) - Required - The quantity used to determine which plural string to use.
- **formatArgs** (Any) - Optional - Arguments to be inserted into the string placeholders.

### Request Example
pluralStringResource(
    R.plurals.runtime_format,
    quantity,
    quantity
)

### Response
#### Success Response (200)
- **value** (String) - The resolved plural string based on the provided quantity.
```

--------------------------------

### Implement Dark Theme with MaterialTheme in Compose

Source: https://developer.android.com/develop/ui/compose/designsystems/material

Shows how to create a custom theme composable (`MyTheme`) that dynamically switches between `DarkColors` and `LightColors` based on a `darkTheme` parameter. This parameter can default to the system's dark theme setting using `isSystemInDarkTheme()`, allowing the application to adapt its appearance.

```kotlin
@Composable
fun MyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColors else LightColors,
        /*...*/
        content = content
    )
}
```

--------------------------------

### Define Custom Typography with FontFamily and TextStyle

Source: https://developer.android.com/develop/ui/compose/designsystems/material

Demonstrates how to create a custom Typography configuration with custom FontFamily and TextStyle definitions. This example defines a Raleway font family with multiple weights and applies it to heading and body text styles, then passes it to MaterialTheme.

```Kotlin
val raleway = FontFamily(
    Font(R.font.raleway_regular),
    Font(R.font.raleway_medium, FontWeight.W500),
    Font(R.font.raleway_semibold, FontWeight.SemiBold)
)

val myTypography = Typography(
    h1 = TextStyle(
        fontFamily = raleway,
        fontWeight = FontWeight.W300,
        fontSize = 96.sp
    ),
    body1 = TextStyle(
        fontFamily = raleway,
        fontWeight = FontWeight.W600,
        fontSize = 16.sp
    )
    /*...*/
)
MaterialTheme(typography = myTypography, /*...*/) {
    /*...*/
}
```

--------------------------------

### Using Custom Components with Replaced Subsystems

Source: https://developer.android.com/develop/ui/compose/designsystems/custom?rec=Cj5odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZGVzaWduc3lzdGVtcxACGAkgASgFMBM6AzMuNw

Demonstrates how to wrap Material components when one or more theming subsystems have been replaced. This pattern ensures replacement values are properly applied to Material components, including handling of CompositionLocal values through provider functions.

```APIDOC
## Using Material Components with Replaced Subsystems

### Description
Wrap Material components to apply replacement theming subsystem values. This is necessary when Material subsystems have been replaced, as Material components may otherwise use unwanted Material defaults.

### Use Case
When you have replaced Material subsystems and need to ensure Material components use your custom values instead of Material defaults.

### Implementation Pattern

#### Step 1: Create Wrapper Component with Replacement Values
```kotlin
@Composable
fun ReplacementButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        shape = ReplacementTheme.shapes.component,
        onClick = onClick,
        modifier = modifier,
        content = {
            ProvideTextStyle(
                value = ReplacementTheme.typography.body
            ) {
                content()
            }
        }
    )
}
```

#### Step 2: Use Replacement Component in App
```kotlin
@Composable
fun ReplacementApp() {
    ReplacementTheme {
        /*...*/
        ReplacementButton(onClick = { /* ... */ }) {
            /* ... */
        }
    }
}
```

### Key Considerations

#### CompositionLocal Handling
Not all Material component parameters are exposed as direct properties. For CompositionLocal values (like `LocalTextStyle`), wrap content lambdas using provider functions:
- Use `ProvideTextStyle()` for text styling
- Use similar provider patterns for other CompositionLocal values

#### Parameters
- **onClick** (lambda) - Required - Callback invoked when button is clicked
- **modifier** (Modifier) - Optional - Modifier for styling and layout
- **content** (composable lambda) - Required - Button content composable

### Benefits
- Ensures consistent application of replacement subsystem values
- Maintains Material component functionality
- Handles CompositionLocal propagation correctly
- Allows gradual component wrapping as needed
```

--------------------------------

### Integrate Selectable List with Dynamic Top App Bar

Source: https://developer.android.com/develop/ui/compose/components/app-bars-dynamic

Demonstrates a complete example combining a selectable list with a dynamic top app bar using Scaffold layout. The composable manages item selection state with long-click to toggle selection and single-click for other actions. Uses LazyColumn for efficient list rendering and rememberSaveable to persist selection state.

```Kotlin
@Composable
private fun AppBarMultiSelectionExample(
    modifier: Modifier = Modifier,
) {
    val listItems by remember { mutableStateOf(listOf(1, 2, 3, 4, 5, 6)) }
    var selectedItems by rememberSaveable { mutableStateOf(setOf<Int>()) }

    Scaffold(
        modifier = modifier,
        topBar = { AppBarSelectionActions(selectedItems) }
    ) { innerPadding ->
        LazyColumn(contentPadding = innerPadding) {
            itemsIndexed(listItems) { _, index ->
                val isItemSelected = selectedItems.contains(index)
                ListItemSelectable(
                    selected = isItemSelected,
                    Modifier
                        .combinedClickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                /* click action */
                            },
                            onLongClick = {
                                if (isItemSelected) selectedItems -= index else selectedItems += index
                            }
                        )
                )
            }
        }
    }
}
```

--------------------------------

### Provide a CompositionLocal Value using CompositionLocalProvider in Compose

Source: https://developer.android.com/develop/ui/compose/compositionlocal

This example shows how to use `CompositionLocalProvider` to bind a calculated `Elevations` value to `LocalElevations` based on the system's dark theme status. The provided value becomes accessible to all composables within the `CompositionLocalProvider`'s content lambda.

```kotlin
class MyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Calculate elevations based on the system theme
            val elevations = if (isSystemInDarkTheme()) {
                Elevations(card = 1.dp, default = 1.dp)
            } else {
                Elevations(card = 0.dp, default = 0.dp)
            }

            // Bind elevation as the value for LocalElevations
            CompositionLocalProvider(LocalElevations provides elevations) {
                // ... Content goes here ...
                // This part of Composition will see the `elevations` instance
                // when accessing LocalElevations.current
            }
        }
    }
}
```

--------------------------------

### GET /androidx.compose.ui.Modifier

Source: https://developer.android.com/develop/ui/compose/layouts/basics?rec=CjdodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2Uva290bGluEAIYCSABKAYwFjoDMy43

Modifiers allow you to decorate or augment composables. They are essential for customizing layout, behavior, and appearance through chained function calls.

```APIDOC
## GET /androidx.compose.ui.Modifier

### Description
Chains multiple modifier functions to customize a composable's layout, interaction, and sizing.

### Method
GET

### Endpoint
/androidx.compose.ui.Modifier

### Parameters
#### Query Parameters
- **clickable** (Function) - Optional - Makes a composable react to user input and shows a ripple.
- **padding** (Dp) - Optional - Puts space around an element.
- **fillMaxWidth** (Float) - Optional - Makes the composable fill the maximum width given by its parent.
- **size** (Dp) - Optional - Specifies an element's preferred width and height.

### Request Example
{
  "modifier": "Modifier.clickable(onClick = {}).padding(16.dp).fillMaxWidth()"
}

### Response
#### Success Response (200)
- **Modifier** (Object) - The augmented modifier instance.

#### Response Example
{
  "status": "applied"
}
```

--------------------------------

### Compose API: AnimatedImageVector

Source: https://developer.android.com/develop/ui/compose/resources?rec=CjRodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYm9tEAMYCSABKAMwEzoDMy43

This section details how to load and display animated vector drawables using `AnimatedImageVector.animatedVectorResource` to get an `AnimatedImageVector` instance, and `rememberAnimatedVectorPainter` to create a `Painter` for display.

```APIDOC
## Compose API: AnimatedImageVector

### Description
To use animated vector drawables, first load the XML resource using `AnimatedImageVector.animatedVectorResource`. Then, create a `Painter` for display with `rememberAnimatedVectorPainter`, which allows controlling the animation state (e.g., `atEnd`).

### Method
Function calls: `AnimatedImageVector.animatedVectorResource(id: Int)`, `rememberAnimatedVectorPainter(image: AnimatedImageVector, atEnd: Boolean)`

### Endpoint
`androidx.compose.animation.graphics.res.AnimatedImageVector.animatedVectorResource`
`androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter`

### Parameters
#### Path Parameters
- No path parameters.

#### Query Parameters
- No query parameters.

#### Request Body
- No request body.

### Request Example
```kotlin
val image =
    AnimatedImageVector.animatedVectorResource(R.drawable.ic_hourglass_animated)
val atEnd by remember { mutableStateOf(false) }
Icon(
    painter = rememberAnimatedVectorPainter(image, atEnd),
    contentDescription = null // decorative element
)
```

### Response
#### Success Response (Painter)
- `AnimatedImageVector.animatedVectorResource` returns an `AnimatedImageVector`.
- `rememberAnimatedVectorPainter` returns a `Painter` instance for the animated graphic.

#### Response Example
```kotlin
// The Painter instance is used directly within a Composable.
// No direct JSON response for this API.
```
```

--------------------------------

### Initialize NavController in App Composable

Source: https://developer.android.com/develop/ui/compose/migrate/migration-scenarios/navigation

Creates and remembers a NavController instance, serving as the single source of truth for navigation state and back stack management.

```kotlin
@Composable
fun SampleApp() {
    val navController = rememberNavController()
    // ...
}
```

--------------------------------

### GET /BoxWithConstraints

Source: https://developer.android.com/develop/ui/compose/layouts/basics?rec=CkVodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdGV4dC9zdHlsZS1wYXJhZ3JhcGgQAhgJIAEoBjAWOgMzLjc

A layout composable that provides measurement constraints in its content scope to facilitate adapting layouts to various screen configurations.

```APIDOC
## GET /BoxWithConstraints

### Description
Provides parent-imposed measurement constraints (minHeight, maxWidth, etc.) to its content lambda for responsive design.

### Method
GET

### Endpoint
/BoxWithConstraints

### Parameters
#### Request Body
- **content** (Lambda) - Required - A composable lambda with access to BoxWithConstraintsScope.

### Request Example
{
  "content": "@Composable BoxWithConstraintsScope.() -> Unit"
}

### Response
#### Success Response (200)
- **minHeight** (Dp) - The minimum height constraint.
- **maxWidth** (Dp) - The maximum width constraint.
```

--------------------------------

### MaterialTheme Composable Setup

Source: https://developer.android.com/develop/ui/compose/designsystems/material?rec=CjtodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbmF2aWdhdGlvbhABGAkgASgFMCo6AzMuNw

Initialize and configure the MaterialTheme composable with color, typography, and shape attributes. This is the foundation for theming your Jetpack Compose application with Material Design 2 principles.

```APIDOC
## MaterialTheme Composable

### Description
The MaterialTheme composable is the primary entry point for applying Material Design 2 theming to your Jetpack Compose application. It accepts color, typography, and shape parameters that are automatically reflected across all Material Design components.

### Usage
```kotlin
MaterialTheme(
    colors = // Color configuration
    typography = // Typography configuration
    shapes = // Shape configuration
) {
    // app content
}
```

### Parameters
- **colors** (Colors) - Optional - The color palette for the theme (light or dark)
- **typography** (Typography) - Optional - Text styles and typography settings
- **shapes** (Shapes) - Optional - Shape definitions for components
- **content** (Composable) - Required - The composable content to be themed

### Key Features
- Automatically applies theme attributes to all Material Design components
- Supports nested MaterialTheme composables for localized theme overrides
- Enables dark theme support through conditional color selection
- Provides centralized theme management for the entire application
```

--------------------------------

### MyBasicColumn - Custom Column Implementation

Source: https://developer.android.com/develop/ui/compose/layouts/custom?rec=CkhodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbGF5b3V0cy9hbGlnbm1lbnQtbGluZXMQAhgJIAEoATAQOgMzLjc

A practical example implementing a basic version of the Column layout composable using the Layout composable. This demonstrates the standard pattern for creating custom layouts that arrange children vertically with proper constraint handling and positioning.

```APIDOC
## MyBasicColumn - Custom Column Layout

### Description
Implements a basic Column layout that arranges child composables vertically. Children are measured with parent constraints and positioned sequentially from top to bottom, tracking the y-coordinate as items are placed.

### Implementation
```kotlin
@Composable
fun MyBasicColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        // Don't constrain child views further, measure them with given constraints
        // List of measured children
        val placeables = measurables.map { measurable ->
            // Measure each child
            measurable.measure(constraints)
        }

        // Set the size of the layout as big as it can
        layout(constraints.maxWidth, constraints.maxHeight) {
            // Track the y coordinate we have placed children up to
            var yPosition = 0

            // Place children in the parent layout
            placeables.forEach { placeable ->
                // Position item on the screen
                placeable.placeRelative(x = 0, y = yPosition)

                // Record the y coordinate placed up to
                yPosition += placeable.height
            }
        }
    }
}
```

### Parameters
- **modifier** (Modifier) - Optional - Modifier to apply to the column layout
- **content** (@Composable () -> Unit) - Required - Composable lambda containing child elements to arrange vertically

### Behavior
- Children are measured with parent constraints without additional restrictions
- Layout size is set to maximum available width and height
- Children are positioned vertically starting at y=0
- Each child is placed at x=0 with incrementing y-position based on previous child heights
- Uses placeRelative() to respect layout direction (LTR/RTL)

### Usage Example
```kotlin
@Composable
fun CallingComposable(modifier: Modifier = Modifier) {
    MyBasicColumn(modifier.padding(8.dp)) {
        Text("MyBasicColumn")
        Text("places items")
        Text("vertically.")
        Text("We've done it by hand!")
    }
}
```
```

--------------------------------

### Shared Element Transitions Best Practices

Source: https://developer.android.com/develop/ui/compose/animation/shared-elements

Guidelines for implementing shared element transitions effectively, including modifier ordering, unique key management, and manual visibility control.

```APIDOC
## Shared Element Transitions Best Practices

### Modifier Ordering
Apply shared element modifiers in the correct order for proper animation behavior:

```kotlin
Image(
  painter = painterResource(id = imageId),
  contentDescription = "Image",
  modifier = Modifier
    .size(200.dp)
    .sharedElement(state = sharedContentState)  // Apply after sizing
    .clip(RoundedCornerShape(8.dp))  // Apply clipping after shared element
)
```

### Unique Keys
Ensure each shared element has a unique, consistent key:

```kotlin
val sharedKey = "snack-image-${snackId}"  // Include unique identifier
val state = rememberSharedContentState(key = sharedKey)
```

### Manual Visibility Management
Control shared element visibility manually when needed:

```kotlin
Box(
  modifier = Modifier.sharedElement(
    state = sharedContentState,
    skipToLookahead = manualVisibilityControl  // Manual control flag
  )
)
```

### Important Considerations
- **Clipping and Overlays**: Understand how clipping affects shared element rendering
- **Scope Requirements**: All shared elements must be within SharedTransitionScope
- **Animation Timing**: Coordinate timing across multiple shared elements
- **Current Limitations**: Review platform limitations before implementation

### Common Patterns
- Use consistent key naming conventions
- Apply modifiers in logical order (size → shared element → clip)
- Test visibility transitions on target devices
- Monitor performance with multiple shared elements
```

--------------------------------

### GET AsyncImage (Coil)

Source: https://developer.android.com/develop/ui/compose/graphics/images/loading?rec=Ck5odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZ3JhcGhpY3MvaW1hZ2VzL2N1c3RvbXBhaW50ZXIQAxgJIAEoAjAaOgMzLjc

Loads an image from the internet asynchronously using the Coil library. It handles networking logic and caching automatically.

```APIDOC
## GET AsyncImage

### Description
A composable that executes an image request asynchronously and displays the result. It is provided by the Coil library.

### Method
GET

### Endpoint
AsyncImage(model: Any?, contentDescription: String?)

### Parameters
#### Request Body
- **model** (Any) - Required - The data source for the image, typically a URL string (e.g., "https://example.com/image.jpg").
- **contentDescription** (String) - Required - A description of the image content for accessibility (TalkBack). Set to null for decorative images.

### Request Example
AsyncImage(
    model = "https://example.com/image.jpg",
    contentDescription = "Translated description of what the image contains"
)

### Response
#### Success Response (200)
- **Composable** (UI) - Renders the downloaded image into the UI layout once the network request completes.
```

--------------------------------

### Implement Sticky Headers in LazyColumn

Source: https://developer.android.com/develop/ui/compose/lists?rec=CjlodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbGF5ZXJpbmcQAhgJIAEoAzAhOgMzLjc

Demonstrates the use of the experimental stickyHeader function within a LazyColumn to pin headers to the top of the viewport. It includes a simple header implementation and a more complex example using grouped data from a Map.

```Kotlin
@Composable
fun ListWithHeader(items: List<Item>) {
    LazyColumn {
        stickyHeader {
            Header()
        }

        items(items) { item ->
            ItemRow(item)
        }
    }
}
```

```Kotlin

val grouped = contacts.groupBy { it.firstName[0] }

@Composable
fun ContactsList(grouped: Map<Char, List<Contact>>) {
    LazyColumn {
        grouped.forEach { (initial, contactsForInitial) ->
            stickyHeader {
                CharacterHeader(initial)
            }

            items(contactsForInitial) { contact ->
                ContactListItem(contact)
            }
        }
    }
}
```

--------------------------------

### Implement Dial Time Picker

Source: https://developer.android.com/develop/ui/compose/components/time-pickers

This snippet demonstrates how to implement a basic dial-style time picker using the `TimePicker` composable. It initializes the picker with the current time and provides buttons for confirmation and dismissal.

```APIDOC
## Composable: TimePicker (Dial Style)

### Description
The `TimePicker` composable provides a visual dial interface for users to select a specific time. It is typically used within a dialog and requires a `TimePickerState` to manage the selected time.

### Composable Name
`TimePicker`

### Parameters
#### State Parameters (for `rememberTimePickerState`)
- **initialHour** (Int) - Required - The hour to initially display in the time picker (0-23).
- **initialMinute** (Int) - Required - The minute to initially display in the time picker (0-59).
- **is24Hour** (Boolean) - Required - A boolean indicating whether the time picker should use a 24-hour format.

#### Composable Parameters (for `TimePicker`)
- **state** (TimePickerState) - Required - The state object that holds and controls the selected time.

### Request Example
```kotlin
@Composable
fun DialExample(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    Column {
        TimePicker(
            state = timePickerState,
        )
        Button(onClick = onDismiss) {
            Text("Dismiss picker")
        }
        Button(onClick = onConfirm) {
            Text("Confirm selection")
        }
    }
}
```

### Response
#### State Update
The `TimePickerState` object is updated with the user's selection.
- **hour** (Int) - The hour selected by the user (0-23).
- **minute** (Int) - The minute selected by the user (0-59).

#### Response Example
The selected time is accessible via the `timePickerState` properties.
```json
{
  "selectedHour": "timePickerState.hour",
  "selectedMinute": "timePickerState.minute"
}
```
```

--------------------------------

### GET /MaterialTheme/Extensions

Source: https://developer.android.com/develop/ui/compose/designsystems/custom?rec=ClJodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZGVzaWduc3lzdGVtcy9tYXRlcmlhbDItbWF0ZXJpYWwzEAMYCSABKAUwFjoDMy43

Extends the existing MaterialTheme color, typography, and shape sets with additional values using Kotlin extension properties.

```APIDOC
## GET /MaterialTheme/Extensions\n\n### Description\nAdds custom properties to the standard MaterialTheme systems (ColorScheme, Typography, Shapes) to maintain consistency with Material usage APIs.\n\n### Method\nGET\n\n### Endpoint\n/MaterialTheme/Extensions\n\n### Parameters\n#### Path Parameters\n- **system** (string) - Required - The Material system to extend (e.g., colorScheme, typography, or shapes).\n\n### Request Example\n{\n  "extension_type": "ColorScheme",\n  "property_name": "snackbarAction"\n}\n\n### Response\n#### Success Response (200)\n- **value** (Object) - The custom themed value (Color, TextStyle, or Shape) returned based on the current theme state.\n\n#### Response Example\n{\n  "color": "#FF0000"\n}
```

--------------------------------

### LiveData to State Conversion - observeAsState()

Source: https://developer.android.com/develop/ui/compose/state?rec=CjpodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbGlmZWN5Y2xlEAEYCSABKAQwJDoDMy43

Starts observing a LiveData object and represents its values via Compose State. Enables seamless integration of LiveData with Compose composables. Requires androidx.compose.runtime:runtime-livedata dependency.

```APIDOC
## LiveData State Observation

### Description
Starts observing a LiveData object and represents its values via Compose State.

### Function
`observeAsState()`

### Purpose
Convert LiveData to Compose State for use in composables.

### Dependencies
```gradle
dependencies {
    implementation("androidx.compose.runtime:runtime-livedata:1.10.5")
}
```

### Usage
- Observes LiveData changes
- Transforms LiveData values into Compose State
- Enables automatic recomposition on value changes

### Type Conversion
- Extension function on LiveData<T>
- Must be called within a composable
- Converts LiveData<T> to State<T>
```

--------------------------------

### POST /Theme

Source: https://developer.android.com/develop/ui/compose/designsystems/anatomy

The primary entry point for applying a design system. It constructs theme system instances and provides them to the composition tree via CompositionLocalProvider.

```APIDOC
## POST /Theme

### Description
The theme function is the entry point and primary API. It constructs instances of the theme system CompositionLocals and provides them to the composition tree with CompositionLocalProvider.

### Method
@Composable Function

### Endpoint
Theme(content: @Composable () -> Unit)

### Parameters
#### Request Body
- **content** (@Composable () -> Unit) - Required - The UI hierarchy that will consume the theme values.

### Request Example
@Composable
fun MyApp() {
	Theme {
		// UI components here
	}
}

### Response
#### Success Response (200)
- **CompositionLocalProvider** (Object) - Provides LocalColorSystem, LocalTypographySystem, and LocalCustomSystem to the hierarchy.
```

--------------------------------

### Request Keyboard Focus with FocusRequester

Source: https://developer.android.com/develop/ui/compose/touch-input/focus/change-focus-behavior

Explains how to programmatically request keyboard focus on a specific composable in response to user interactions. This is essential for guiding users through forms or complex input workflows.

```APIDOC
## Request Focus on Composable

### Description
Programmatically move keyboard focus to a specific composable using `FocusRequester` and its `requestFocus()` method.

### Setup
First, associate a `FocusRequester` object with the target composable:

```kotlin
val focusRequester = remember { FocusRequester() }
var text by remember { mutableStateOf("") }

TextField(
    value = text,
    onValueChange = { text = it },
    modifier = Modifier.focusRequester(focusRequester)
)
```

### Request Focus
Call `requestFocus()` outside of composable context to avoid re-execution on recomposition:

```kotlin
val focusRequester = remember { FocusRequester() }
var text by remember { mutableStateOf("") }

TextField(
    value = text,
    onValueChange = { text = it },
    modifier = Modifier.focusRequester(focusRequester)
)

Button(onClick = { focusRequester.requestFocus() }) {
    Text("Request focus on TextField")
}
```

### Parameters
- **focusRequester** (FocusRequester) - Object associated with the target composable via `Modifier.focusRequester()`

### Methods
- **requestFocus()** - Sends a focus request to move keyboard focus to the associated composable

### Important Notes
- Must be called outside of composable context to prevent re-execution during recomposition
- Typically invoked in event handlers like button clicks or lifecycle callbacks
- Useful for form workflows where focus should move to the next field after validation
```

--------------------------------

### Material Components Overview

Source: https://developer.android.com/develop/ui/compose/designsystems/material3?rec=CjRodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYm9tEAEYCSABKAUwDDoDMy43

Introduction to Material Design 3 components including buttons, floating action buttons, and text buttons. These pre-built components follow Material Theming and provide multiple emphasis levels.

```APIDOC
## Material Components

### Description
Material Design 3 provides a rich set of pre-built components that follow Material Theming guidelines. Components are available in multiple emphasis levels for different use cases.

### Basic Button
Simple filled button for standard actions:

```kotlin
Button(onClick = { /* action */ }) {
    Text(text = "My Button")
}
```

### Extended Floating Action Button
Highest emphasis action button with icon and text:

```kotlin
ExtendedFloatingActionButton(
    onClick = { /* action */ },
    modifier = Modifier
) {
    Icon(
        imageVector = Icons.Default.Edit,
        contentDescription = stringResource(id = R.string.edit)
    )
    Text(
        text = stringResource(id = R.string.add_entry)
    )
}
```

### Filled Button
High emphasis action button:

```kotlin
Button(onClick = { /* action */ }) {
    Text(text = stringResource(id = R.string.view_entry))
}
```

### Text Button
Low emphasis action button:

```kotlin
TextButton(onClick = { /* action */ }) {
    Text(text = stringResource(id = R.string.related_articles))
}
```

### Component Emphasis Levels
- **Extended Floating Action Button** - Highest emphasis
- **Filled Button** - High emphasis
- **Text Button** - Low emphasis

### Available Component Suites
- Buttons (multiple variants)
- App bars
- Navigation components
- Cards
- Chips
- And many more...
```

--------------------------------

### Custom Drawing Modifier Implementation

Source: https://developer.android.com/develop/ui/compose/graphics/draw/modifiers?rec=CkRodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2Uvc3lzdGVtL3Rlc3QtY3V0b3V0cxABGAkgASgCMCI6AzMuNw

Details on how to implement a custom `DrawModifier` in Jetpack Compose, including an example of a vertically flipping modifier and its application.

```APIDOC
## Custom Drawing Modifier Implementation

### Description
To create your own custom modifier, implement the `DrawModifier` interface. This gives you access to a `ContentDrawScope`, which is the same as what is exposed when using `Modifier.drawWithContent()`. You can then extract common drawing operations to custom drawing modifiers to clean up the code and provide convenient wrappers; for example, `Modifier.background()` is a convenient `DrawModifier`.

### Interface
`DrawModifier`

### Key Concepts
-   `ContentDrawScope`: Provides drawing capabilities within the modifier.
-   `Modifier.drawWithContent()`: A standard modifier that exposes `ContentDrawScope`.
-   `scale(scaleX, scaleY)`: A `ContentDrawScope` function for applying scaling transformations.
-   `this@draw.drawContent()`: Renders the content of the composable being modified.

### Example: Flipped Modifier Implementation
```kotlin
class FlippedModifier : DrawModifier {
    override fun ContentDrawScope.draw() {
        scale(1f, -1f) {
            this@draw.drawContent()
        }
    }
}

fun Modifier.flipped() = this.then(FlippedModifier())
```

### Example: Applying the Flipped Modifier
```kotlin
Text(
    "Hello Compose!",
    modifier = Modifier
        .flipped()
)
```
```

--------------------------------

### Illustrate UI tree structure with nested layouts in Compose

Source: https://developer.android.com/develop/ui/compose/layouts/basics

This composable function provides an example of a nested layout structure using 'Row' and 'Column' to represent a search result. It serves to illustrate the UI tree and the layout model's measurement and placement phases.

```kotlin
@Composable
fun SearchResult() {
    Row {
        Image(
            // ...
        )
        Column {
            Text(
                // ...
            )
            Text(
                // ...
            )
        }
    }
}
```

--------------------------------

### Customize NavigationSuiteScaffold Layout Type by Window Size (Kotlin)

Source: https://developer.android.com/develop/ui/compose/layouts/adaptive/build-adaptive-navigation

This example demonstrates how to override the default adaptive behavior of NavigationSuiteScaffold to control the navigation UI type based on window size classes. It forces a NavigationDrawer for expanded windows while falling back to default behavior for smaller sizes.

```kotlin
val adaptiveInfo = currentWindowAdaptiveInfo()
val customNavSuiteType = with(adaptiveInfo) {
    if (windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_EXPANDED_LOWER_BOUND)) {
        NavigationSuiteType.NavigationDrawer
    } else {
        NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(adaptiveInfo)
    }
}

NavigationSuiteScaffold(
    navigationSuiteItems = { /* ... */ },
    layoutType = customNavSuiteType,
) {
    // Content...
}
```

--------------------------------

### Guidelines API

Source: https://developer.android.com/develop/ui/compose/layouts/constraintlayout?rec=CjhodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbGF5b3V0cxACGAkgASgDMCY6AzMuNw

Guidelines are visual helpers for positioning composables at specific distances or percentages within a parent. ConstraintLayout supports four types of guidelines: top, bottom, start, and end.

```APIDOC
## Guidelines

### Description
Guidelines are invisible reference lines used to position composables at specific distances or percentages from the parent edges. They help create consistent spacing and alignment in layouts.

### Types of Guidelines

#### Vertical Guidelines
- **createGuidelineFromStart(fraction: Float)** - Creates a vertical guideline at a percentage of the parent width from the start edge
- **createGuidelineFromEnd(fraction: Float)** - Creates a vertical guideline at a percentage of the parent width from the end edge

#### Horizontal Guidelines
- **createGuidelineFromTop(dp: Dp)** - Creates a horizontal guideline at a fixed distance from the top edge
- **createGuidelineFromBottom(dp: Dp)** - Creates a horizontal guideline at a fixed distance from the bottom edge

### Parameters
- **fraction** (Float) - Required for percentage-based guidelines - Value between 0.0 and 1.0 representing percentage of parent dimension
- **dp** (Dp) - Required for distance-based guidelines - Fixed distance from the parent edge

### Usage Example
```kotlin
ConstraintLayout {
    // Create guideline from the start of the parent at 10% the width of the Composable
    val startGuideline = createGuidelineFromStart(0.1f)
    
    // Create guideline from the end of the parent at 10% the width of the Composable
    val endGuideline = createGuidelineFromEnd(0.1f)
    
    // Create guideline from 16 dp from the top of the parent
    val topGuideline = createGuidelineFromTop(16.dp)
    
    // Create guideline from 16 dp from the bottom of the parent
    val bottomGuideline = createGuidelineFromBottom(16.dp)
}
```

### Constraint Usage
Guidelines can be used in `Modifier.constrainAs()` blocks to position composables relative to the guideline.

### Notes
- Consider using `Spacer` composable with `Row` and `Column` as an alternative approach
- Guidelines are invisible and do not affect rendering, only layout positioning
```

--------------------------------

### Handle Edge Swipe to Dismiss with Modifier.edgeSwipeToDismiss

Source: https://developer.android.com/develop/ui/compose/modifiers-list

Enables a swipe-to-dismiss gesture starting from the edge of the viewport. Requires a SwipeToDismissBoxState to manage the dismissal state.

```Kotlin
Modifier.edgeSwipeToDismiss(
	swipeToDismissBoxState: SwipeToDismissBoxState,
	edgeWidth: Dp
)
```

--------------------------------

### Implement Dark and Light Themes with MaterialTheme in Compose

Source: https://developer.android.com/develop/ui/compose/designsystems/material?rec=CjtodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdGV4dC9mb250cxADGAkgASgFMBo6AzMuNw

This snippet illustrates how to create a custom `MyTheme` composable that dynamically switches between `DarkColors` and `LightColors` based on the system's dark theme setting. It demonstrates wrapping `MaterialTheme` to provide different color schemes for the UI.

```kotlin
@Composable
fun MyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColors else LightColors,
        /*...*/
        content = content
    )
}

```

--------------------------------

### Implement Property Delegation in Kotlin

Source: https://developer.android.com/develop/ui/compose/kotlin?rec=Cj1odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbWVudGFsLW1vZGVsEAEYCSABKAQwFzoDMy43

Demonstrates how to use the 'by' syntax to delegate property access to a function. This allows property values to be computed dynamically at runtime.

```kotlin
class DelegatingClass {
    var name: String by nameGetterFunction()
}

val myDC = DelegatingClass()
println("The name property is: " + myDC.name)
```

--------------------------------

### FUNCTION rememberInfiniteTransition

Source: https://developer.android.com/develop/ui/compose/animation/value-based?rec=CkNodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYW5pbWF0aW9uL2FkdmFuY2VkEAEYCSABKAEwDDoDMy43

Creates an InfiniteTransition which manages one or more child animations. These animations start as soon as they enter the composition and do not stop unless removed.

```APIDOC
## COMPOSABLE rememberInfiniteTransition

### Description
Creates an instance of InfiniteTransition that holds child animations like animateColor, animateFloat, or animateValue. Animations run indefinitely until removed from composition.

### Method
COMPOSABLE

### Endpoint
rememberInfiniteTransition(label: String)

### Parameters
#### Path Parameters
- **label** (String) - Required - A label for inspecting the animation in Tooling.

### Request Example
{
  "label": "infinite"
}

### Response
#### Success Response (200)
- **InfiniteTransition** (Object) - The transition object used to create child animations.

#### Response Example
{
  "type": "InfiniteTransition",
  "state": "Running"
}
```

--------------------------------

### Replacing Material Subsystems with Custom Implementation

Source: https://developer.android.com/develop/ui/compose/designsystems/custom?rec=Cj5odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZGVzaWduc3lzdGVtcxACGAkgASgFMBM6AzMuNw

Shows how to replace one or more Material theming subsystems (Colors, Typography, Shapes) with custom implementations while keeping others. This approach uses CompositionLocal providers and custom theme objects to manage replacement values alongside Material defaults.

```APIDOC
## Replacing Material Subsystems

### Description
Replace one or more Material theming systems (Colors, Typography, Shapes) with custom implementations while maintaining the others from MaterialTheme.

### Use Case
When you need complete control over specific theming aspects like typography and shapes, but want to keep Material's color system or other defaults.

### Implementation Pattern

#### Step 1: Define Custom Subsystem Data Classes
```kotlin
@Immutable
data class ReplacementTypography(
    val body: TextStyle,
    val title: TextStyle
)

@Immutable
data class ReplacementShapes(
    val component: Shape,
    val surface: Shape
)
```

#### Step 2: Create CompositionLocal Providers
```kotlin
val LocalReplacementTypography = staticCompositionLocalOf {
    ReplacementTypography(
        body = TextStyle.Default,
        title = TextStyle.Default
    )
}

val LocalReplacementShapes = staticCompositionLocalOf {
    ReplacementShapes(
        component = RoundedCornerShape(ZeroCornerSize),
        surface = RoundedCornerShape(ZeroCornerSize)
    )
}
```

#### Step 3: Create Theme Composable
```kotlin
@Composable
fun ReplacementTheme(
    /* ... */
    content: @Composable () -> Unit
) {
    val replacementTypography = ReplacementTypography(
        body = TextStyle(fontSize = 16.sp),
        title = TextStyle(fontSize = 32.sp)
    )
    val replacementShapes = ReplacementShapes(
        component = RoundedCornerShape(percent = 50),
        surface = RoundedCornerShape(size = 40.dp)
    )
    CompositionLocalProvider(
        LocalReplacementTypography provides replacementTypography,
        LocalReplacementShapes provides replacementShapes
    ) {
        MaterialTheme(
            /* colors = ... */
            content = content
        )
    }
}
```

#### Step 4: Create Theme Object for Access
```kotlin
object ReplacementTheme {
    val typography: ReplacementTypography
        @Composable
        get() = LocalReplacementTypography.current
    val shapes: ReplacementShapes
        @Composable
        get() = LocalReplacementShapes.current
}
```

### Parameters
- **content** (composable lambda) - Required - Theme content composable

### Access Pattern
```kotlin
// Access replacement values
ReplacementTheme.typography.body
ReplacementTheme.shapes.component
```
```

--------------------------------

### GET Modifier.verticalScroll

Source: https://developer.android.com/develop/ui/compose/touch-input/scroll/scroll-modifiers

Enables vertical scrolling on a composable when its content is larger than its maximum height constraints. It handles the translation and offsetting of contents automatically.

```APIDOC
## GET Modifier.verticalScroll

### Description
The verticalScroll modifier provides the simplest way to allow the user to scroll an element vertically when the bounds of its contents are larger than its maximum size constraints.

### Method
COMPOSABLE_MODIFIER

### Endpoint
Modifier.verticalScroll(state, enabled, flingBehavior, reverseScrolling)

### Parameters
#### Path Parameters
- **state** (ScrollState) - Required - State of the scroll, created via rememberScrollState().

#### Query Parameters
- **enabled** (Boolean) - Optional - Whether the scroll is enabled. Defaults to true.
- **reverseScrolling** (Boolean) - Optional - Reverse the direction of scrolling. Defaults to false.

### Request Example
{
  "modifier": "Modifier.verticalScroll(rememberScrollState())"
}

### Response
#### Success Response (200)
- **Modifier** (Modifier) - Returns a modified element that supports vertical scrolling.

#### Response Example
{
  "status": "success",
  "type": "VerticalScrollModifier"
}
```

--------------------------------

### Preview Composable with ViewModel in Android Compose

Source: https://developer.android.com/develop/ui/compose/tooling/previews

This example demonstrates how to structure composables to enable previewing when working with `ViewModels`. It shows a pattern where the `ViewModel` is consumed by a wrapper composable, and a separate, stateless composable is created for previewing with sample data, avoiding `ViewModel` coupling in previews.

```kotlin
@Composable
fun AuthorScreen(viewModel: AuthorViewModel = viewModel()) {
  AuthorScreen(
    name = viewModel.authorName,
    // ViewModel sends the network requests and makes posts available as a state
    posts = viewModel.posts
  )
}

@Composable
fun AuthorScreen(
  name: NameLabel,
  posts: PostsList
) {
  // ...
}

@Preview
@Composable
fun AuthorScreenPreview(
  // You can use some sample data to preview your composable without the need to construct the ViewModel
  name: String = sampleAuthor.name,
  posts: List<Post> = samplePosts[sampleAuthor]
) {
  AuthorScreen(
      name = NameLabel(name),
      posts = PostsList(posts)
  )
}
```

--------------------------------

### Launch Activity on a Specific External Display (Kotlin)

Source: https://developer.android.com/develop/ui/compose/layouts/adaptive/support-connected-displays

This code illustrates how to launch a new activity on a designated external display. It first identifies an available external display using DisplayManager and then uses ActivityOptions.makeBasic() with launchDisplayId (available from API level 26) to direct the Intent to that specific display. It includes a fallback for when no external display is found.

```kotlin
// Get DisplayManager and find the first external display.
val displayManager = getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
val externalDisplayId = displayManager.displays
    .firstOrNull { it.displayId != Display.DEFAULT_DISPLAY }
    ?.displayId

// If an external display is found, launch the activity on it.
if (externalDisplayId != null) {
    val intent = Intent(this, MySecondaryActivity::class.java)
    val options = ActivityOptions.makeBasic()
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        options.launchDisplayId = externalDisplayId
    }
    startActivity(intent, options.toBundle())
} else {
    // Optionally, handle the case where no external display is connected.
}
```

--------------------------------

### GET /SavedStateHandle/getStateFlow

Source: https://developer.android.com/develop/ui/compose/state-saving?rec=CjpodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbGlmZWN5Y2xlEAMYCSABKAUwFzoDMy43

Retrieves a read-only StateFlow that emits values associated with a specific key. It requires an initial value to be provided if the key does not exist.

```APIDOC
## GET /SavedStateHandle/getStateFlow

### Description
Retrieves a read-only StateFlow that emits the current value and subsequent updates for a given key from the SavedStateHandle.

### Method
GET

### Endpoint
SavedStateHandle/getStateFlow

### Parameters
#### Path Parameters
- **key** (String) - Required - The key to associate with the state.
- **initialValue** (T) - Required - The initial value to emit if no value is found for the key.

### Request Example
{
  "key": "ChannelFilterKey",
  "initialValue": "ALL_CHANNELS"
}

### Response
#### Success Response (200)
- **StateFlow<T>** (Object) - A flow that emits the latest state value.

#### Response Example
{
  "value": "ALL_CHANNELS"
}
```

--------------------------------

### Basic Vertical Pager in Compose

Source: https://developer.android.com/develop/ui/compose/layouts/pager

This example shows how to implement a basic vertical pager using VerticalPager. Similar to HorizontalPager, it uses rememberPagerState to manage state and displays 10 pages that scroll vertically.

```kotlin
val pagerState = rememberPagerState(pageCount = {
    10
})
VerticalPager(state = pagerState) { page ->
    // Our page content
    Text(
        text = "Page: $page",
        modifier = Modifier.fillMaxWidth()
    )
}
```

--------------------------------

### GET /compose/viewModel

Source: https://developer.android.com/develop/ui/compose/migrate/other-considerations?rec=Cj5odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZG9jdW1lbnRhdGlvbhACGAkgASgDMCc6AzMuNw

Accesses an existing ViewModel or creates a new one within a composable. It is automatically scoped to the nearest ViewModelStoreOwner such as an Activity or Fragment.

```APIDOC
## GET /compose/viewModel

### Description
Retrieves a ViewModel instance from the current composition. ViewModels should be accessed at screen-level composables and never passed down to children.

### Method
GET

### Endpoint
viewModel()

### Parameters
#### Query Parameters
- **factory** (ViewModelProvider.Factory) - Optional - A custom factory used to instantiate the ViewModel if it does not already exist.

### Request Example
{
  "usage": "viewModel(factory = GreetingViewModelFactory(userId))"
}

### Response
#### Success Response (200)
- **viewModel** (ViewModel) - The retrieved or newly created ViewModel instance.

#### Response Example
{
  "
```

--------------------------------

### Adaptive Navigation UI Components

Source: https://developer.android.com/develop/ui/compose/navigation?rec=CldodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbWlncmF0ZS9taWdyYXRpb24tc2NlbmFyaW9zL25hdmlnYXRpb24QARgJIAEoBjAkOgMzLjc

Use NavigationSuiteScaffold to display appropriate navigation UI based on WindowSizeClass. For compact screens it shows a bottom navigation bar, while medium and expanded screens display a navigation rail. Combine with ListDetailPaneScaffold and SupportingPaneScaffold for adaptive layouts.

```APIDOC
## Adaptive Navigation UI Components

### Description
NavigationSuiteScaffold automatically adapts the navigation UI based on the current device screen size class, providing optimal navigation patterns for different form factors.

### Method
Composable Configuration

### Endpoint
NavigationSuiteScaffold(windowSizeClass: WindowSizeClass)

### Parameters
#### Composable Parameters
- **windowSizeClass** (WindowSizeClass) - Required - Current window size class determining navigation UI type

#### Navigation UI Types
- **Compact Screens** - Bottom navigation bar
- **Medium Screens** - Navigation rail
- **Expanded Screens** - Navigation rail

#### Related Composables
- **ListDetailPaneScaffold** - For list-detail canonical layouts
- **SupportingPaneScaffold** - For supporting pane canonical layouts

### Response
#### Success Response
- Appropriate navigation UI rendered based on WindowSizeClass
- Seamless adaptation between navigation bar and rail as screen size changes
- Support for adaptive layouts with specialized composables

### Notes
- NavigationSuiteScaffold handles primary navigation
- Use ListDetailPaneScaffold and SupportingPaneScaffold for complex adaptive layouts
- Refer to Build adaptive layouts documentation for detailed implementation
```

--------------------------------

### Implement Custom Page Indicator for Compose Pager

Source: https://developer.android.com/develop/ui/compose/layouts/pager

This example shows how to create a custom page indicator, such as a series of circles, for a Compose Pager. It utilizes `PagerState.currentPage` to determine which page is currently selected and updates the visual appearance (e.g., color) of the corresponding indicator.

```kotlin
val pagerState = rememberPagerState(pageCount = {
    4
})
HorizontalPager(
    state = pagerState,
    modifier = Modifier.fillMaxSize()
) { page ->
    // Our page content
    Text(
        text = "Page: $page",
    )
}
Row(
    Modifier
        .wrapContentHeight()
        .fillMaxWidth()
        .align(Alignment.BottomCenter)
        .padding(bottom = 8.dp),
    horizontalArrangement = Arrangement.Center
) {
    repeat(pagerState.pageCount) { iteration ->
        val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
        Box(
            modifier = Modifier
                .padding(2.dp)
                .clip(CircleShape)
                .background(color)
                .size(16.dp)
        )
    }
}
```

--------------------------------

### GET /paging/collectAsLazyPagingItems

Source: https://developer.android.com/develop/ui/compose/lists?rec=CkdodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdG9vbGluZy9lZGl0b3ItYWN0aW9ucxADGAkgASgBMAw6AzMuNw

Collects data from a Pager flow into LazyPagingItems for use in Lazy layouts. This enables efficient loading of large datasets in chunks.

```APIDOC
## GET /paging/collectAsLazyPagingItems

### Description
Extension function that collects a Flow of PagingData and transforms it into a state object that can be used by Lazy layouts.

### Method
GET

### Endpoint
/paging/collectAsLazyPagingItems

### Parameters
#### Query Parameters
- **pager** (Pager) - Required - The Pager instance providing the data flow.

### Request Example
{
  "pager": "Pager<Int, Message>"
}

### Response
#### Success Response (200)
- **lazyPagingItems** (LazyPagingItems) - A state object containing the paged items and loading status.

#### Response Example
{
  "itemCount": 100,
  "loadState": "Loading"
}
```

--------------------------------

### GET /stickyHeader

Source: https://developer.android.com/develop/ui/compose/lists?rec=CjdodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvcGhhc2VzEAIYCSABKAMwIToDMy43

Experimental function used within a LazyColumn to create headers that remain pinned to the top of the viewport until pushed by the next header.

```APIDOC
## GET /stickyHeader

### Description
Provides a mechanism to implement the 'sticky header' pattern in lists, commonly used for grouped data like contact lists.

### Method
GET

### Endpoint
/androidx.compose.foundation.lazy.LazyColumnScope/stickyHeader

### Parameters
#### Path Parameters
- **key** (Any) - Optional - A stable and unique key representing the item.
- **contentType** (Any) - Optional - The type of the content of this item.

#### Request Body
- **content** (Composable) - Required - The UI content to be displayed as the sticky header.

### Request Example
{
  "context": "LazyColumnScope",
  "function": "stickyHeader",
  "content": "CharacterHeader(initial)"
}

### Response
#### Success Response (200)
- **UI_Component** (Composable) - A header that sticks to the top of the LazyColumn during scroll.

#### Response Example
{
  "status": "rendered",
  "behavior": "sticky"
}
```

--------------------------------

### Create CompositionLocal Providers for Theme Systems

Source: https://developer.android.com/develop/ui/compose/designsystems/custom?rec=CjVodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdGV4dBACGAkgASgCMAY6AzMuNw

Establishes staticCompositionLocalOf instances for each custom theme system (colors, typography, elevation). These composition locals provide default values and enable theme values to be accessed anywhere in the composable hierarchy without explicit parameter passing.

```Kotlin
val LocalCustomColors = staticCompositionLocalOf {
    CustomColors(
        content = Color.Unspecified,
        component = Color.Unspecified,
        background = emptyList()
    )
}
val LocalCustomTypography = staticCompositionLocalOf {
    CustomTypography(
        body = TextStyle.Default,
        title = TextStyle.Default
    )
}
val LocalCustomElevation = staticCompositionLocalOf {
    CustomElevation(
        default = Dp.Unspecified,
        pressed = Dp.Unspecified
    )
}
```

--------------------------------

### Implement Top App Bar Navigation Icon in Compose

Source: https://developer.android.com/develop/ui/compose/components/app-bars-navigate

This example demonstrates how to create a `CenterAlignedTopAppBar` with a functional navigation icon in Jetpack Compose. The `TopBarNavigationExample` composable accepts a `navigateBack` lambda, which is invoked when the back arrow icon is pressed. The second code block shows how to integrate this composable within a `NavHost` by passing `navController.popBackStack()` to handle navigation.

```Kotlin
@Composable
fun TopBarNavigationExample(
    navigateBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Navigation example",
                    )
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Text(
            "Click the back button to pop from the back stack.",
            modifier = Modifier.padding(innerPadding),
        )
    }
}
```

```Kotlin
NavHost(navController, startDestination = "home") {
    composable("topBarNavigationExample") {
        TopBarNavigationExample{ navController.popBackStack() }
    }
    // Other destinations...
}
```

--------------------------------

### Create a CompositionLocal

Source: https://developer.android.com/develop/ui/compose/compositionlocal?rec=Cj5odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZG9jdW1lbnRhdGlvbhABGAkgASgJMC06AzMuNw

Learn how to create CompositionLocal instances using compositionLocalOf and staticCompositionLocalOf APIs. compositionLocalOf tracks reads and only recomposes affected content, while staticCompositionLocalOf recomposes the entire content lambda when values change, offering better performance for static values.

```APIDOC
## CompositionLocal Creation APIs

### compositionLocalOf
**Purpose**: Create a CompositionLocal that tracks reads and invalidates only the content that reads its current value during recomposition.

**Usage**: Use when the value is likely to change and you want fine-grained recomposition control.

**Example**:
```kotlin
data class Elevations(val card: Dp = 0.dp, val default: Dp = 0.dp)

// Define a CompositionLocal global object with a default
// This instance can be accessed by all composables in the app
val LocalElevations = compositionLocalOf { Elevations() }
```

### staticCompositionLocalOf
**Purpose**: Create a CompositionLocal where reads are not tracked by Compose. Changing the value causes the entirety of the content lambda to be recomposed.

**Usage**: Use when the value is highly unlikely to change or will never change to get performance benefits.

**Performance Consideration**: Provides better performance for static values that don't change frequently, as it avoids the overhead of tracking reads across the composition tree.
```

--------------------------------

### GET /androidx.compose.foundation.layout.BoxWithConstraints

Source: https://developer.android.com/develop/ui/compose/layouts/basics?rec=CjdodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2Uva290bGluEAIYCSABKAYwFjoDMy43

A layout composable that provides its measurement constraints in the content scope, enabling the creation of responsive layouts based on available space.

```APIDOC
## GET /androidx.compose.foundation.layout.BoxWithConstraints

### Description
Provides measurement constraints (minHeight, maxWidth, etc.) from the parent to design layouts for different screen configurations.

### Method
GET

### Endpoint
/androidx.compose.foundation.layout.BoxWithConstraints

### Parameters
#### Request Body
- **content** (Composable) - Required - A lambda providing access to measurement constraints like minHeight and maxWidth.

### Request Example
{
  "content": "{ Text(\"My maxWidth is $maxWidth\") }"
}

### Response
#### Success Response (200)
- **UI** (Composable) - A responsive layout container.

#### Response Example
{
  "constraints": {
    "maxWidth": "400dp",
    "minHeight": "100dp"
  }
}
```

--------------------------------

### MaterialTheme Composable Setup

Source: https://developer.android.com/develop/ui/compose/designsystems/material?rec=CjtodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdGV4dC9mb250cxADGAkgASgFMBo6AzMuNw

Initialize Material Design theming in your Jetpack Compose application using the MaterialTheme composable. This is the foundation for applying consistent styling across all Material Design components in your app.

```APIDOC
## MaterialTheme Composable

### Description
Sets up Material Design theming for your Compose application by configuring colors, typography, and shapes that are automatically applied to all Material components.

### Usage
```kotlin
MaterialTheme(
    colors = // Color palette (light or dark)
    typography = // Text styles
    shapes = // Shape definitions
) {
    // Your app content here
}
```

### Parameters
- **colors** (Colors) - Optional - The color palette for the theme (light or dark colors)
- **typography** (Typography) - Optional - Text styles and typography settings
- **shapes** (Shapes) - Optional - Shape definitions for components
- **content** (Composable) - Required - The composable content to apply the theme to

### Key Features
- Automatically applies theme to all Material Design components
- Supports nested MaterialTheme composables for theme overlays
- Changes to theme parameters are reflected in all child components
- Enables dark theme support through conditional color palettes
```

--------------------------------

### Create Assist Chip with Leading Icon in Jetpack Compose

Source: https://developer.android.com/develop/ui/compose/components/chip

Demonstrates how to implement an AssistChip composable with a leading icon and onClick callback. The AssistChip provides a straightforward way to create chips that guide users during tasks, with support for displaying icons on the left side of the chip.

```Kotlin
@Composable
fun AssistChipExample() {
    AssistChip(
        onClick = { Log.d("Assist chip", "hello world") },
        label = { Text("Assist chip") },
        leadingIcon = {
            Icon(
                Icons.Filled.Settings,
                contentDescription = "Localized description",
                Modifier.size(AssistChipDefaults.IconSize)
            )
        }
    )
}
```

--------------------------------

### Animate Text Color in Compose

Source: https://developer.android.com/develop/ui/compose/animation/quick-guide

This example demonstrates animating text color using `rememberInfiniteTransition` and `animateColor`. The `BasicText` composable's `color` lambda is used to apply the animated color, creating a smooth color transition.

```kotlin
val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
val animatedColor by infiniteTransition.animateColor(
    initialValue = Color(0xFF60DDAD),
    targetValue = Color(0xFF4285F4),
    animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse),
    label = "color"
)

BasicText(
    text = "Hello Compose",
    color = {
        animatedColor
    }
    // ...
)
```

--------------------------------

### Start Animations Immediately with MutableTransitionState in Jetpack Compose

Source: https://developer.android.com/develop/ui/compose/animation/value-based

Shows how to use MutableTransitionState to trigger an animation immediately upon composition by setting a target state different from the initial state.

```kotlin
// Start in collapsed state and immediately animate to expanded
var currentState = remember { MutableTransitionState(BoxState.Collapsed) }
currentState.targetState = BoxState.Expanded
val transition = rememberTransition(currentState, label = "box state")
// ……
```

--------------------------------

### Specifying Keys in LazyColumn for Efficient List Recomposition in Jetpack Compose

Source: https://developer.android.com/develop/ui/compose/lifecycle

This example demonstrates how to provide stable keys when using LazyColumn for displaying lists. The items DSL accepts a 'key' parameter, allowing developers to specify a unique identifier for each item. This ensures that LazyColumn can efficiently track item changes, additions, and removals, optimizing recomposition and maintaining scroll state.

```kotlin
@Composable
fun MoviesScreenLazy(movies: List<Movie>) {
    LazyColumn {
        items(movies, key = { movie -> movie.id }) { movie ->
            MovieOverview(movie)
        }
    }
}
```

--------------------------------

### GET TextMeasurer.measure

Source: https://developer.android.com/develop/ui/compose/graphics/draw/overview?rec=Cj9odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbGF5b3V0cy9jdXN0b20QAhgJIAEoAzAhOgMzLjc

Calculates the size and layout of text based on specific constraints and styles. Essential for determining background sizes or handling overflow.

```APIDOC
## GET TextMeasurer.measure

### Description
Measures text dimensions considering font size, letter spacing, and layout constraints.

### Method
GET

### Endpoint
TextMeasurer.measure

### Parameters
#### Request Body
- **text** (AnnotatedString) - Required - The text to be measured.
- **constraints** (Constraints) - Optional - The width and height limits for the text.
- **style** (TextStyle) - Optional - The styling applied to the text (e.g., fontSize).
- **overflow** (TextOverflow) - Optional - How visual overflow should be handled.

### Request Example
{
  "text": "longTextSample",
  "constraints": "Constraints.fixed(width, height)",
  "overflow": "TextOverflow.Ellipsis"
}

### Response
#### Success Response (200)
- **measuredText** (TextLayoutResult) - Object containing the calculated size and layout properties.

#### Response Example
{
  "size": "IntSize(width, height)",
  "hasVisualOverflow": true
}
```

--------------------------------

### Enable Nested Scrolling Interop between CoordinatorLayout and ComposeView

Source: https://developer.android.com/develop/ui/compose/touch-input/scroll/nested-scroll-modifiers

This example shows the XML layout configuration for a CoordinatorLayout hosting a ComposeView, followed by the Kotlin implementation to bridge nested scroll events using rememberNestedScrollInteropConnection and the nestedScroll modifier.

```xml
<androidx.coordinatorlayout.widget.CoordinatorLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.google.android.material.appbar.AppBarLayout
        android:id="@+id/app_bar"
        android:layout_width="match_parent"
        android:layout_height="100dp"
        android:fitsSystemWindows="true">

        <com.google.android.material.appbar.CollapsingToolbarLayout
            android:id="@+id/collapsing_toolbar_layout"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:fitsSystemWindows="true"
            app:layout_scrollFlags="scroll|exitUntilCollapsed">

            <!--...-->

        </com.google.android.material.appbar.CollapsingToolbarLayout>

    </com.google.android.material.appbar.AppBarLayout>

    <androidx.compose.ui.platform.ComposeView
        android:id="@+id/compose_view"
        app:layout_behavior="@string/appbar_scrolling_view_behavior"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

</androidx.coordinatorlayout.widget.CoordinatorLayout>
```

```kotlin
open class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<ComposeView>(R.id.compose_view).apply {
            setContent {
                val nestedScrollInterop = rememberNestedScrollInteropConnection()
                // Add the nested scroll connection to your top level @Composable element
                // using the nestedScroll modifier.
                LazyColumn(modifier = Modifier.nestedScroll(nestedScrollInterop)) {
                    items(20) { item ->
                        Box(
                            modifier = Modifier
                                .padding(16.dp)
                                .height(56.dp)
                                .fillMaxWidth()
                                .background(Color.Gray),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(item.toString())
                        }
                    }
                }
            }
        }
    }
}
```

--------------------------------

### Utilize Default Arguments in Jetpack Compose Composables

Source: https://developer.android.com/develop/ui/compose/kotlin?rec=CjlodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbGF5ZXJpbmcQARgJIAEoBDAIOgMzLjc

This section demonstrates how Jetpack Compose composables leverage default arguments for concise and readable code. The first Kotlin example shows a simple Text composable relying on defaults, while the second illustrates the verbose alternative of explicitly setting default values, highlighting the benefits of the idiomatic approach.

```Kotlin
Text(text = "Hello, Android!")
KotlinSnippets.kt
```

```Kotlin
Text(
    text = "Hello, Android!",
    color = Color.Unspecified,
    fontSize = TextUnit.Unspecified,
    letterSpacing = TextUnit.Unspecified,
    overflow = TextOverflow.Clip
)
KotlinSnippets.kt
```

--------------------------------

### CompositingStrategy.Offscreen with BlendMode

Source: https://developer.android.com/develop/ui/compose/graphics/draw/modifiers?rec=CkRodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2Uvc3lzdGVtL3Rlc3QtY3V0b3V0cxABGAkgASgCMCA6AzMuNw

Demonstrates how to use CompositingStrategy.Offscreen with an Image composable to apply BlendMode operations correctly. This example shows how to clip an image to a circular path and apply a clear blend mode to create a visual indicator without affecting underlying content.

```APIDOC
## CompositingStrategy.Offscreen with BlendMode

### Description
Applies offscreen compositing strategy to an Image composable to enable proper BlendMode operations. The offscreen texture isolates the blend mode effects to only the composable's contents, preventing them from affecting layers beneath.

### Usage Context
GraphicsLayer Modifier with CompositingStrategy

### Key Components
- **Image Composable**: Displays content with background gradient
- **graphicsLayer Modifier**: Applies CompositingStrategy.Offscreen
- **drawWithCache**: Executes drawing commands with clipping and blend modes
- **BlendMode.Clear**: Removes pixels from the offscreen texture only

### Implementation Pattern
```kotlin
Image(
    painter = painterResource(id = R.drawable.dog),
    contentDescription = "Dog",
    contentScale = ContentScale.Crop,
    modifier = Modifier
        .size(120.dp)
        .aspectRatio(1f)
        .background(Brush.linearGradient(...))
        .padding(8.dp)
        .graphicsLayer {
            compositingStrategy = CompositingStrategy.Offscreen
        }
        .drawWithCache { ... }
)
```

### Parameters
- **compositingStrategy** (CompositingStrategy) - Set to CompositingStrategy.Offscreen to enable offscreen rendering
- **blendMode** (BlendMode) - BlendMode.Clear to remove pixels from the offscreen texture

### Benefits
- Isolates blend mode effects to the composable only
- Prevents blend operations from affecting underlying content
- Enables correct alpha-based blending behavior
- Supports complex visual effects like masking and clipping

### Important Notes
- Offscreen textures are sized to match the drawing area
- Drawing commands are automatically clipped to the offscreen region
- Without Offscreen strategy, BlendMode.Clear would affect all pixels in the destination
- Essential for proper rendering when using alpha-based blend modes
```

--------------------------------

### State Management Patterns - Stateful vs Stateless

Source: https://developer.android.com/develop/ui/compose/state?rec=CjpodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbGlmZWN5Y2xlEAEYCSABKAQwJDoDMy43

Guidelines for designing composables with internal state versus stateless composables. Covers state hoisting patterns, reusability considerations, and best practices for exposing both stateful and stateless versions of composables.

```APIDOC
## Stateful vs Stateless Composables

### Stateful Composables

#### Description
Composables that use `remember` to store objects, creating internal state.

#### Characteristics
- Hold and modify state internally
- Use `remember` for state storage
- Less reusable
- Harder to test
- Convenient for callers that don't need state control

#### Example Pattern
- HelloContent composable with internal name state
- Caller doesn't need to manage state
- Simplified API for simple use cases

### Stateless Composables

#### Description
Composables that don't hold any internal state.

#### Characteristics
- No internal state management
- Achieved through state hoisting
- More reusable
- Easier to test
- Necessary for callers needing state control

### State Hoisting

#### Definition
Moving state up to a parent composable to make child composables stateless.

#### Benefits
- Increases composable reusability
- Improves testability
- Enables state sharing between composables
- Follows single responsibility principle

### Best Practice
Expose both stateful and stateless versions:
- Stateful version: Convenient for simple use cases
- Stateless version: Required for advanced state control
```

--------------------------------

### Switch Component - Custom Thumb

Source: https://developer.android.com/develop/ui/compose/quick-guides/content/add-toggle-switch?hl=en

Shows how to customize the switch thumb with a custom icon. This example demonstrates using the thumbContent parameter to display a check icon when the switch is in the checked state.

```APIDOC
## Switch Component - Custom Thumb

### Description
Implements a Switch with a custom icon displayed in the thumb when checked. The thumbContent parameter accepts any composable for customization.

### Parameters
#### Required Parameters
- **checked** (Boolean) - Required - The current state of the switch
- **onCheckedChange** (Function) - Required - Callback invoked when the switch state changes

#### Customization Parameters
- **thumbContent** (Composable?) - Optional - Custom composable displayed in the thumb when checked (null when unchecked)

### Code Example
```kotlin
@Composable
fun SwitchWithIconExample() {
    var checked by remember { mutableStateOf(true) }

    Switch(
        checked = checked,
        onCheckedChange = {
            checked = it
        },
        thumbContent = if (checked) {
            {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    modifier = Modifier.size(SwitchDefaults.IconSize),
                )
            }
        } else {
            null
        }
    )
}
```

### Behavior
- When checked: Displays a check icon inside the thumb
- When unchecked: Shows standard thumb appearance without icon
- The thumbContent lambda receives the current checked state for conditional rendering
```

--------------------------------

### Rotate Transformation

Source: https://developer.android.com/develop/ui/compose/graphics/draw/overview?rec=CkRodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZ3JhcGhpY3MvZHJhdy9icnVzaBACGAkgASgBMA86AzMuNw

Demonstrates how to use DrawScope.rotate() to rotate drawing operations around a pivot point. Includes example of rotating a rectangle 45 degrees.

```APIDOC
## Rotate Transformation

### Description
Use DrawScope.rotate() to rotate drawing operations around a pivot point by a specified number of degrees.

### Method
rotate(degrees: Float, pivot: Offset = center, block: DrawScope.() -> Unit)

### Parameters
- **degrees** (Float) - Rotation angle in degrees (clockwise)
- **pivot** (Offset) - Point around which to rotate (default: center)
- **block** (DrawScope lambda) - Drawing operations to rotate

### Example: Rotate Rectangle 45 Degrees

```kotlin
Canvas(modifier = Modifier.fillMaxSize()) {
    rotate(degrees = 45F) {
        drawRect(
            color = Color.Gray,
            topLeft = Offset(x = size.width / 3F, y = size.height / 3F),
            size = size / 3F
        )
    }
}
```

### Notes
- Rotation is performed around the pivot point (default is center)
- Positive degrees rotate clockwise
- All drawing operations within the lambda are rotated
```

--------------------------------

### Apply Content Alpha for Visual Hierarchy in Jetpack Compose

Source: https://developer.android.com/develop/ui/compose/designsystems/material?rec=CkBodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdGV4dC9zdHlsZS10ZXh0EAIYCSABKAUwGjoDMy43

This example shows how to use CompositionLocalProvider with LocalContentAlpha to adjust the emphasis of content within a composable hierarchy. It allows nested Text and Icon components to automatically apply different levels of opacity (e.g., medium, disabled) based on Material Design guidelines for visual hierarchy.

```kotlin
// By default, both Icon & Text use the combination of LocalContentColor &
// LocalContentAlpha. De-emphasize content by setting content alpha
CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
    Text(
        // ...
    )
}
CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled) {
    Icon(
        // ...
    )
    Text(
        // ...
    )
}
Material2Snippets.kt
```

--------------------------------

### Provide values to CompositionLocal using CompositionLocalProvider

Source: https://developer.android.com/develop/ui/compose/compositionlocal?rec=Cj5odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZG9jdW1lbnRhdGlvbhABGAkgASgFMBs6AzMuNw

Binds values to CompositionLocal instances for a given UI hierarchy using the CompositionLocalProvider composable and the provides infix function. This example shows how to calculate elevation values based on system theme and provide them to the LocalElevations CompositionLocal for all descendant composables.

```Kotlin
// MyActivity.kt file

class MyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Calculate elevations based on the system theme
            val elevations = if (isSystemInDarkTheme()) {
                Elevations(card = 1.dp, default = 1.dp)
            } else {
                Elevations(card = 0.dp, default = 0.dp)
            }

            // Bind elevation as the value for LocalElevations
            CompositionLocalProvider(LocalElevations provides elevations) {
                // ... Content goes here ...
                // This part of Composition will see the `elevations` instance
                // when accessing LocalElevations.current
            }
        }
    }
}
```

--------------------------------

### Define Interaction-Based Styles for Custom Composables

Source: https://developer.android.com/develop/ui/compose/styles/state-animations

Shows how to define a Style object that modifies visual properties based on interaction states. In this example, a linear gradient background is applied specifically when the button is in the 'pressed' state.

```Kotlin
@Preview
@Composable
fun LoginButton() {
    val loginButtonStyle = Style {
        pressed {
            background(
                Brush.linearGradient(
                    listOf(Color.Magenta, Color.Red)
                )
            )
        }
    }
    GradientButton(onClick = {
        // Login logic
    }, style = loginButtonStyle) {
        BaseText("Login")
    }
}
```

--------------------------------

### POST /animation/decay

Source: https://developer.android.com/develop/ui/compose/animation/value-based?rec=CkNodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYW5pbWF0aW9uL2FkdmFuY2VkEAEYCSABKAIwGzoDMy43

An animation engine that calculates values based on starting conditions and a decay specification, typically used for slowing elements to a stop after gestures.

```APIDOC
## POST /animation/decay

### Description
Calculates its target value based on starting conditions set by initial velocity and value, slowing down over time using a DecayAnimationSpec.

### Method
POST

### Endpoint
/animation/decay

### Parameters
#### Request Body
- **animationSpec** (DecayAnimationSpec) - Required - The specification defining the decay behavior.
- **typeConverter** (TwoWayConverter) - Required - Converts the value type to a vector.
- **initialValue** (T) - Required - The starting value of the animation.
- **initialVelocity** (T) - Required - The starting velocity vector.

### Request Example
{
  "animationSpec": "exponentialDecay()",
  "initialValue": 0.0,
  "initialVelocity": 2000.0
}

### Response
#### Success Response (200)
- **animationValue** (T) - The calculated value at the specific play time as the animation decays.

#### Response Example
{
  "animationValue": 1200.0
}
```

--------------------------------

### AnimationSpec: snap

Source: https://developer.android.com/develop/ui/compose/animation/customize?rec=Ck5odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdG9vbGluZy9pdGVyYXRpdmUtZGV2ZWxvcG1lbnQQARgJIAEoAjAdOgMzLjc

A special `AnimationSpec` that immediately switches the animated value to its end value. An optional `delayMillis` can be specified to delay the start of this immediate switch.

```APIDOC
## AnimationSpec: `snap`

### Description
`snap` is a special `AnimationSpec` that immediately switches the value to the end value. You can specify `delayMillis` in order to delay the start of the animation.

### Usage Example
```kotlin
val value by animateFloatAsState(
    targetValue = 1f,
    animationSpec = snap(delayMillis = 50),
    label = "snap spec"
)
```
```

--------------------------------

### ListDetailPaneScaffold - List-Detail Layout Implementation

Source: https://developer.android.com/develop/ui/compose/layouts/adaptive/canonical-layouts?hl=zh-tw

Implements a responsive list-detail layout using ListDetailPaneScaffold that automatically handles pane logic based on window size classes. Supports showing both panes on expanded displays and single panes on compact displays with back navigation support.

```APIDOC
## ListDetailPaneScaffold

### Description
A high-level composable that simplifies the implementation of list-detail layouts by automatically handling pane logic based on window size classes and supporting navigation between panes.

### Purpose
Creates a responsive two-pane layout that adapts to different window sizes, showing both list and detail panes on expanded displays, or individual panes on compact/medium displays.

### Key Components

#### rememberListDetailPaneScaffoldNavigator()
- **Type**: Composable Function
- **Returns**: ListDetailPaneScaffoldNavigator
- **Purpose**: Creates and remembers a navigator to manage navigation between the list and detail panes
- **Usage**: Required to manage scaffold state and navigation logic

#### ListDetailPaneScaffold Parameters
- **directive** (PaneScaffoldDirective) - Required - Defines the layout directive from navigator
- **value** (ScaffoldValue) - Required - Current scaffold value from navigator
- **listPane** (Composable) - Required - Displays the list of items
- **detailPane** (Composable) - Required - Displays the content of a selected item

### Implementation Example
```kotlin
@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun MyListDetailPaneScaffold() {
    val navigator = rememberListDetailPaneScaffoldNavigator()
    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            // Listing Pane
        },
        detailPane = {
            // Details Pane
        }
    )
}
```

### State Management
- Hoist all state including current window size class and selected list item details
- Ensures unidirectional data flow and correct rendering across all composables
- Use BackHandler for small window sizes to remove detail pane and display list pane

### Responsive Behavior
- **Expanded Width**: Shows both list and detail panes simultaneously
- **Medium Width**: Shows either list or detail pane
- **Compact Width**: Shows either list or detail pane with back navigation

### Related Resources
- Build a list-detail layout developer guide
- list-detail-compose sample
```

--------------------------------

### Create a Small Floating Action Button with Custom Colors in Android Compose

Source: https://developer.android.com/develop/ui/compose/components/fab

This example shows how to implement a smaller FAB using 'SmallFloatingActionButton'. It also demonstrates customizing the button's 'containerColor' and 'contentColor' using 'MaterialTheme'.

```Kotlin
@Composable
fun SmallExample(onClick: () -> Unit) {
    SmallFloatingActionButton(
        onClick = { onClick() },
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.secondary
    ) {
        Icon(Icons.Filled.Add, "Small floating action button.")
    }
}
```

--------------------------------

### Make a Composable clickable with padding excluded in Jetpack Compose

Source: https://developer.android.com/develop/ui/compose/modifiers

This example demonstrates applying padding *before* making a Column clickable. In this case, only the content area inside the padding will be clickable, and the padded space itself will not react to user input.

```kotlin
@Composable
fun ArtistCard(/*...*/) {
    val padding = 16.dp
    Column(
        Modifier
            .padding(padding)
            .clickable(onClick = onClick)
            .fillMaxWidth()
    ) {
        // rest of the implementation
    }
}
```

--------------------------------

### GET SavedStateHandle.saveable()

Source: https://developer.android.com/develop/ui/compose/state-saving?rec=CjZodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2Uvc3RhdGUQAhgJIAEoBTAXOgMzLjc

The saveable API of SavedStateHandle allows UI element state to be read and written as MutableState, ensuring it survives activity and process recreation.

```APIDOC
## GET SavedStateHandle.saveable()

### Description
Reads and writes UI element state as MutableState, so it survives activity and process recreation with minimal code setup. It supports primitive types and custom savers.

### Method
GET

### Endpoint
SavedStateHandle.saveable(stateSaver, init)

### Parameters
#### Path Parameters
- **stateSaver** (Saver) - Optional - A custom saver parameter to handle complex objects, similar to rememberSaveable().

#### Request Body
- **init** (Lambda) - Required - A lambda that returns the initial MutableState.

### Request Example
{
  "implementation": "var message by savedStateHandle.saveable(stateSaver = TextFieldValue.Saver) {\n    mutableStateOf(TextFieldValue(\"\"))\n}"
}

### Response
#### Success Response (200)
- **MutableState** (Object) - A state object that is automatically persisted and restored by the system.

#### Response Example
{
  "value": "TextFieldValue",
  "isRestored": true
}
```

--------------------------------

### Launch Concurrent Coroutines for Parallel Execution

Source: https://developer.android.com/develop/ui/compose/kotlin

Shows how to execute multiple independent tasks concurrently by launching separate coroutines within the same scope. In this example, scrolling to the top and loading data happen in parallel by creating two distinct coroutines instead of chaining suspend functions sequentially. This improves responsiveness by not blocking one operation on another.

```Kotlin
// Create a CoroutineScope that follows this composable's lifecycle
val composableScope = rememberCoroutineScope()
Button( // ...
    onClick = {
        // Scroll to the top and load data in parallel by creating a new
        // coroutine per independent work to do
        composableScope.launch {
            scrollState.animateScrollTo(0)
        }
        composableScope.launch {
            viewModel.loadData()
        }
    }
) { /* ... */ }
```

--------------------------------

### Stateful vs Stateless Composables

Source: https://developer.android.com/develop/ui/compose/state?rec=CjdodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvcGhhc2VzEAEYCSABKAQwJDoDMy43

Guidelines for designing reusable composables using state hoisting. Explains the difference between stateful composables (using remember) and stateless composables, and best practices for exposing both versions.

```APIDOC
## Stateful vs Stateless Composables

### Description
Composables can be designed as either stateful (holding internal state) or stateless (receiving state as parameters). Best practice is to expose both versions for maximum reusability.

### Stateful Composable
A composable that uses `remember` to store an object creates internal state, making it stateful.

```kotlin
@Composable
fun HelloContent() {
    var name by remember { mutableStateOf("") }
    
    Column {
        TextField(
            value = name,
            onValueChange = { name = it }
        )
        Text("Hello $name")
    }
}
```

### Stateless Composable
A composable that doesn't hold any state, receiving all state as parameters.

```kotlin
@Composable
fun HelloContent(
    name: String,
    onNameChange: (String) -> Unit
) {
    Column {
        TextField(
            value = name,
            onValueChange = onNameChange
        )
        Text("Hello $name")
    }
}
```

### State Hoisting Pattern
Expose both stateful and stateless versions:

```kotlin
// Stateless version (reusable)
@Composable
fun HelloContent(
    name: String,
    onNameChange: (String) -> Unit
) {
    // Implementation
}

// Stateful wrapper (convenient)
@Composable
fun HelloContentStateful() {
    var name by remember { mutableStateOf("") }
    HelloContent(
        name = name,
        onNameChange = { name = it }
    )
}
```

### Advantages
- **Stateless**: More reusable, easier to test, supports state hoisting
- **Stateful**: Convenient for callers that don't need to manage state

### Best Practices
- Prefer stateless composables for library and reusable components
- Use state hoisting to lift state to the appropriate level
- Provide stateful wrappers for convenience when needed
```

--------------------------------

### Basic CompositionLocal Usage with Theme Colors

Source: https://developer.android.com/develop/ui/compose/compositionlocal

Demonstrates accessing theme colors through CompositionLocal without passing them as explicit parameters. Shows how MyApp provides theme information at the root level, and SomeTextLabel accesses colors from descendants in the hierarchy. This pattern eliminates the need to thread color parameters through every composable function.

```Kotlin
@Composable
fun MyApp() {
    // Theme information tends to be defined near the root of the application
    val colors = colors()
}

// Some composable deep in the hierarchy
@Composable
fun SomeTextLabel(labelText: String) {
    Text(
        text = labelText,
        color = colors.onPrimary // ← need to access colors here
    )
}
```

--------------------------------

### GET animate*AsState

Source: https://developer.android.com/develop/ui/compose/animation/value-based?rec=CkRodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYW5pbWF0aW9uL2N1c3RvbWl6ZRABGAkgASgCMBs6AzMuNw

The animate*AsState functions are the simplest animation APIs in Compose for animating a single value from its current state to a target value.

```APIDOC
## GET animate*AsState\n\n### Description\nAnimates a single value to a target value. The API starts animation from the current value to the specified target value automatically.\n\n### Method\nComposable Function\n\n### Endpoint\nandroidx.compose.animation.core.animate*AsState\n\n### Parameters\n#### Path Parameters\n- None\n\n#### Query Parameters\n- None\n\n#### Request Body\n- **targetValue** (T) - Required - The value to animate towards.\n- **animationSpec** (AnimationSpec) - Optional - The specification used to customize the animation.\n- **label** (String) - Optional - A label for the animation to be displayed in the Animation Inspector.\n- **finishedListener** ((T) -> Unit) - Optional - A listener notified when the animation is finished.\n\n### Request Example\n{\n  "targetValue": "1f",\n  "label": "alphaAnimation"\n}\n\n### Response\n#### Success Response (200)\n- **State<T>** (Object) - A State object whose value is updated every frame during the animation.\n\n#### Response Example\n{\n  "value": 0.75\n}
```

--------------------------------

### Interact with a UI element by text in Jetpack Compose tests

Source: https://developer.android.com/develop/ui/compose/accessibility/semantics

This example shows how to perform a click action on a UI element in a Jetpack Compose test by matching its displayed text. The testing framework, by default, uses the merged Semantics tree, allowing interaction with composite elements like a Button based on its visible text content.

```kotlin
composeTestRule.onNodeWithText("Like").performClick()
```

--------------------------------

### Build Hierarchical UI using Kotlin DSLs

Source: https://developer.android.com/develop/ui/compose/kotlin?rec=Cj1odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2Uvc2lkZS1lZmZlY3RzEAIYCSABKAQwFzoDMy43

Leverage type-safe builders and function literals with receivers to create readable UI structures. Examples include LazyColumn for lists and Canvas for custom drawing operations.

```kotlin
@Composable
fun MessageList(messages: List<Message>) {
    LazyColumn {
        item {
            Text("Message List")
        }

        items(messages) { message ->
            Message(message)
        }
    }
}

Canvas(Modifier.size(120.dp)) {
    drawRect(color = Color.Gray)

    inset(10.0f, 12.0f) {
        val quadrantSize = size / 2.0f

        drawRect(
            size = quadrantSize,
            color = Color.Red
        )

        rotate(45.0f) {
            drawRect(size = quadrantSize, color = Color.Blue)
        }
    }
}
```

--------------------------------

### Filter User Input with InputTransformation

Source: https://developer.android.com/develop/ui/compose/text/user-input

Examples of limiting input length using built-in filters and creating custom transformations to allow only specific character types.

```Kotlin
// Built-in max length filter
TextField(
    state = rememberTextFieldState(),
    lineLimits = TextFieldLineLimits.SingleLine,
    inputTransformation = InputTransformation.maxLength(10)
)

// Custom digit-only transformation
class DigitOnlyInputTransformation : InputTransformation {
    override fun TextFieldBuffer.transformInput() {
        if (!asCharSequence().isDigitsOnly()) {
            revertAllChanges()
        }
    }
}
```

--------------------------------

### LiveData Integration with observeAsState

Source: https://developer.android.com/develop/ui/compose/state?rec=CjdodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvcGhhc2VzEAEYCSABKAQwJDoDMy43

Starts observing a LiveData object and represents its values via Compose State. Enables seamless integration of LiveData-based ViewModels with Compose UI. Requires androidx.compose.runtime:runtime-livedata dependency.

```APIDOC
## LiveData State Observation - observeAsState()

### Description
Starts observing a LiveData object and represents its values via Compose State, enabling automatic recomposition when LiveData values change.

### Function Signature
```kotlin
fun <R> LiveData<R>.observeAsState(
    initial: R? = null
): State<R?>
```

### Parameters
- **initial** (R?) - Optional - The initial value for the State before LiveData emits

### Dependencies
```gradle
dependencies {
    implementation("androidx.compose.runtime:runtime-livedata:1.10.5")
}
```

### Usage Example
```kotlin
@Composable
fun MyComposable(viewModel: MyViewModel) {
    val state: State<Data?> = viewModel.liveData.observeAsState()
    state.value?.let { data ->
        Text(data.toString())
    }
}
```

### Notes
- Converts LiveData to Compose State for automatic recomposition
- Must be called within a Composable function
- Requires androidx.compose.runtime:runtime-livedata:1.10.5
```

--------------------------------

### Java Overloaded Functions for Optional Parameters

Source: https://developer.android.com/develop/ui/compose/kotlin?rec=CmRodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdG91Y2gtaW5wdXQvdXNlci1pbnRlcmFjdGlvbnMvaGFuZGxpbmctaW50ZXJhY3Rpb25zEAIYCSABKAUwHToDMy43

This Java example demonstrates the traditional approach of using multiple overloaded functions to handle optional parameters, which is often verbose and less efficient compared to Kotlin's default arguments.

```java
// We don't need to do this in Kotlin!
void drawSquare(int sideLength) { }

void drawSquare(int sideLength, int thickness) { }

void drawSquare(int sideLength, int thickness, Color edgeColor) { }
JavaSnippets.java
```

--------------------------------

### Implementing Clickable Components with Modifier.clickable

Source: https://developer.android.com/develop/ui/compose/touch-input/user-interactions/handling-interactions

This example shows how to use `Modifier.clickable` to create a component that handles hover, focus, and press interactions at a higher abstraction level. It replaces `hoverable` and `focusable` modifiers, simplifying the implementation while still allowing the use of `MutableInteractionSource` to observe these interactions and apply effects like ripples.

```kotlin
// This InteractionSource will emit hover, focus, and press interactions
val interactionSource = remember { MutableInteractionSource() }
Box(
    Modifier
        .size(100.dp)
        .clickable(
            onClick = {},
            interactionSource = interactionSource,

            // Also show a ripple effect
            indication = ripple()
        ),
    contentAlignment = Alignment.Center
) {
    Text("Hello!")
}
```

--------------------------------

### Set up InProgressStrokes Composable

Source: https://developer.android.com/develop/ui/compose/touch-input/stylus-input/ink-api-draw-stroke

Initialize the InProgressStrokes composable with a default brush, a callback to get the next brush, and a callback to handle finished strokes. This component manages the wet ink layer for live drawing interactions.

```Kotlin
InProgressStrokes(
  defaultBrush = currentBrush,
  nextBrush = onGetNextBrush,
  onStrokesFinished = onStrokesFinished,
)
```

--------------------------------

### FUN Modifier.firstBaselineToTop

Source: https://developer.android.com/develop/ui/compose/layouts/alignment-lines?rec=CkdodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZ3JhcGhpY3MvZHJhdy9vdmVydmlldxADGAkgASgFMCg6AzMuNw

A custom layout modifier that adds padding to a composable starting from its first baseline. It ensures the first baseline is positioned at a specific distance from the top.

```APIDOC
## FUN Modifier.firstBaselineToTop

### Description
A custom layout modifier that reads the FirstBaseline of a composable to add padding relative to that baseline.

### Method
KOTLIN_EXT

### Endpoint
Modifier.firstBaselineToTop(firstBaselineToTop: Dp)

### Parameters
#### Path Parameters
- **firstBaselineToTop** (Dp) - Required - The desired distance from the top of the layout to the first baseline.

### Request Example
```kotlin
Text("Hi there!", Modifier.firstBaselineToTop(32.dp))
```

### Response
#### Success Response (200)
- **layout** (MeasureResult) - Returns a layout with modified height and relative placement of the child based on the baseline calculation.

#### Response Example
```kotlin
layout(placeable.width, height) {
    placeable.placeRelative(0, placeableY)
}
```
```

--------------------------------

### Implement a Basic Supporting Pane Layout in Compose

Source: https://developer.android.com/develop/ui/compose/layouts/adaptive/canonical-layouts?hl=de

This snippet demonstrates a minimal implementation of a supporting pane layout using the `SupportingPaneScaffold` composable. It automatically manages pane visibility and layout based on window size classes, displaying panes side-by-side on large screens or hiding the supporting pane on smaller screens. It utilizes `rememberSupportingPaneScaffoldNavigator` to control pane state and defines `mainPane` and `supportingPane` content areas.

```kotlin
@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun MySupportingPaneScaffold() {
    // Creates and remembers a navigator to control pane visibility and navigation
    val navigator = rememberSupportingPaneScaffoldNavigator()
    SupportingPaneScaffold(
        // Directive and value help control pane visibility based on screen size and state
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        mainPane = {
            // Main Pane for the primary content
        },
        supportingPane = {
            //Supporting Pane for supplementary content
        }
    )
}
```

--------------------------------

### Example Composable with MutableState and Conditional UI in Jetpack Compose

Source: https://developer.android.com/develop/ui/compose/state

This `HelloContent` composable demonstrates state management using `remember` and `mutableStateOf` for a text input. It dynamically updates the UI by conditionally displaying a greeting based on the input's emptiness. This showcases how state drives UI changes.

```kotlin
@Composable
fun HelloContent() {
    Column(modifier = Modifier.padding(16.dp)) {
        var name by remember { mutableStateOf("") }
        if (name.isNotEmpty()) {
            Text(
                text = "Hello, $name!",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )
    }
}

```

--------------------------------

### Implementing sharedBounds for Container Transitions

Source: https://developer.android.com/develop/ui/compose/animation/shared-elements

This example demonstrates how to use Modifier.sharedBounds to animate a transition between a Row-based layout and a Column-based layout. It uses a shared key to link the states and defines enter/exit fades with a scaling resize mode.

```kotlin
@Composable
private fun MainContent(
    onShowDetails: () -> Unit,
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    with(sharedTransitionScope) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .sharedBounds(
                    rememberSharedContentState(key = "bounds"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    enter = fadeIn(),
                    exit = fadeOut(),
                    resizeMode = SharedTransitionScope.ResizeMode.scaleToBounds()
                )
                // ...
        ) {
            // ...
        }
    }
}

@Composable
private fun DetailsContent(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    with(sharedTransitionScope) {
        Column(
            modifier = Modifier
                .padding(top = 200.dp, start = 16.dp, end = 16.dp)
                .sharedBounds(
                    rememberSharedContentState(key = "bounds"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    enter = fadeIn(),
                    exit = fadeOut(),
                    resizeMode = SharedTransitionScope.ResizeMode.scaleToBounds()
                )
                // ...

        ) {
            // ...
        }
    }
}
```

--------------------------------

### Open Keyboard Shortcuts Helper Programmatically

Source: https://developer.android.com/develop/ui/compose/touch-input/keyboard-input/keyboard-shortcuts-helper

This snippet demonstrates how to call `requestShowKeyboardShortcuts()` on the current Activity to display the system's keyboard shortcuts helper. It's typically triggered by a user tapping a button or pressing the Enter key.

```APIDOC
## METHOD requestShowKeyboardShortcuts()

### Description
Opens the system's Keyboard Shortcuts Helper screen for the current activity. This method is typically invoked in response to a user action, such as tapping a button or pressing a specific key.

### Method
`Activity.requestShowKeyboardShortcuts()`

### Endpoint
N/A (Method call)

### Parameters
#### Path Parameters
N/A

#### Query Parameters
N/A

#### Request Body
N/A

### Request Example
N/A

### Code Example (Kotlin)
```kotlin
val activity = LocalActivity.current

Button(onClick = { activity.requestShowKeyboardShortcuts() }) {
    Text(text = "Show keyboard shortcuts")
}
```

### Response
#### Success Response (N/A)
This method does not return a value. It triggers a UI action.

#### Response Example
N/A
```

--------------------------------

### SUSPEND Animatable.animateTo

Source: https://developer.android.com/develop/ui/compose/animation/value-based?rec=CkNodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYW5pbWF0aW9uL2FkdmFuY2VkEAEYCSABKAEwDDoDMy43

A coroutine-based API for animating a single value. It ensures consistent continuation and mutual exclusiveness by cancelling ongoing animations when a new one starts.

```APIDOC
## SUSPEND animateTo

### Description
Animates the current value to a target value. This is a suspend function and must be called within a coroutine scope like LaunchedEffect.

### Method
SUSPEND

### Endpoint
Animatable.animateTo(targetValue, animationSpec, initialVelocity, block)

### Parameters
#### Request Body
- **targetValue** (T) - Required - The value to animate towards.
- **animationSpec** (AnimationSpec) - Optional - The specification for the animation (e.g., tween, spring).
- **initialVelocity** (T) - Optional - The velocity to start the animation with.

### Request Example
{
  "targetValue": "Color.Green",
  "animationSpec": "tween(durationMillis = 1000)"
}

### Response
#### Success Response (200)
- **AnimationResult** (Object) - Contains information about why the animation ended (e.g., reached target or was interrupted).

#### Response Example
{
  "endReason": "Finished",
  "endValue": "Color.Green"
}
```

--------------------------------

### Apply duration and delay with tween

Source: https://developer.android.com/develop/ui/compose/animation/customize

This snippet illustrates the use of tween to define an animation with a specific duration and a start delay, along with a custom easing curve.

```Kotlin
val value by animateFloatAsState(
    targetValue = 1f,
    animationSpec = tween(
        durationMillis = 300,
        delayMillis = 50,
        easing = LinearOutSlowInEasing
    ),
    label = "tween delay"
)
```

--------------------------------

### Configure SizeMode.Exact for Responsive AppWidget Layout

Source: https://developer.android.com/develop/ui/compose/glance/build-ui

Demonstrates implementing SizeMode.Exact in a GlanceAppWidget to enable exact layout requests that recreate content when AppWidget size changes. The example shows conditional rendering of a button based on available width using LocalSize.current. This approach provides flexibility but may cause performance issues and UI jumps with complex content.

```Kotlin
class MyAppWidget : GlanceAppWidget() {

    override val sizeMode = SizeMode.Exact

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        // ...

        provideContent {
            MyContent()
        }
    }

    @Composable
    private fun MyContent() {
        // Size will be the size of the AppWidget
        val size = LocalSize.current
        Column {
            Text(text = "Where to?", modifier = GlanceModifier.padding(12.dp))
            Row(horizontalAlignment = Alignment.CenterHorizontally) {
                Button()
                Button()
                if (size.width > 250.dp) {
                    Button("School")
                }
            }
        }
    }
}
```

--------------------------------

### Design System Implementation Approaches

Source: https://developer.android.com/develop/ui/compose/designsystems/custom?rec=CjpodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvcmVzb3VyY2VzEAMYCSABKAIwBjoDMy43

Overview of three main approaches for implementing design systems in Jetpack Compose: extending Material Theming, replacing Material subsystems, or implementing a fully custom design system.

```APIDOC
## Design System Implementation Approaches

### Overview
Jetpack Compose provides flexibility to implement custom design systems using public APIs. Choose the approach that best fits your design requirements.

### Approach 1: Extend Material Theming
**Description**: Add additional theming values to MaterialTheme while keeping existing Material systems.

**When to use**:
- You want to use Material Design as a base
- You need additional custom colors, typography, or shapes
- You want consistency with Material Design patterns

**Implementation**:
- Use extension properties on ColorScheme, Typography, or Shapes
- Define custom CompositionLocals for extended values
- Wrap MaterialTheme with custom theme composable

### Approach 2: Replace Material Subsystems
**Description**: Replace one or more Material systems (Colors, Typography, or Shapes) with custom implementations while keeping others.

**When to use**:
- You need custom implementations for specific systems
- You want to keep some Material systems as-is
- You need fine-grained control over certain aspects

**Replaceable Systems**:
- Colors (ColorScheme)
- Typography
- Shapes

### Approach 3: Implement Fully Custom Design System
**Description**: Replace MaterialTheme entirely with a custom design system implementation.

**When to use**:
- You have a completely custom design language
- You don't want to depend on Material Design
- You need full control over all theming aspects

**Considerations**:
- Built on the same public APIs as Material
- Requires more implementation work
- Can still use Material components if needed

### Using Material Components with Custom Design Systems
**Note**: It's possible to use Material components with custom design systems, but requires careful consideration of the approach taken to ensure compatibility.
```

--------------------------------

### Initialize WindowManager in onCreate() using Kotlin Flow

Source: https://developer.android.com/develop/ui/compose/layouts/adaptive/foldables/support-foldable-display-modes

Initialize the WindowAreaController and set up Flow-based observation of window area info in the onCreate() method. This Kotlin approach uses lifecycleScope and Flow to reactively monitor rear-facing display availability and capability status. It filters for TYPE_REAR_FACING window areas and updates the capability status.

```Kotlin
displayExecutor = ContextCompat.getMainExecutor(this)
windowAreaController = WindowAreaController.getOrCreate()

lifecycleScope.launch(Dispatchers.Main) {
    lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
        windowAreaController.windowAreaInfos
            .map { info -> info.firstOrNull { it.type == WindowAreaInfo.Type.TYPE_REAR_FACING } }
            .onEach { info -> windowAreaInfo = info }
            .map { it?.getCapability(operation)?.status ?: WindowAreaCapability.Status.WINDOW_AREA_STATUS_UNSUPPORTED }
            .distinctUntilChanged()
            .collect {
                capabilityStatus = it
            }
    }
}
```

--------------------------------

### GET MaterialTheme.colors

Source: https://developer.android.com/develop/ui/compose/designsystems/material?rec=CjtodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdGV4dC9mb250cxADGAkgASgFMBo6AzMuNw

Retrieve the current theme colors from the MaterialTheme composable. This allows components to access theme colors dynamically based on the current theme context.

```APIDOC
## GET MaterialTheme.colors

### Description
Access the Colors instance from the current MaterialTheme composable context. This retrieves the active color palette (light or dark) for use in your composables.

### Method
GET (Property Access)

### Endpoint
MaterialTheme.colors

### Returns
- **Colors** - The color palette from the current MaterialTheme context

### Usage Example
```kotlin
Text(
    text = "Hello theming",
    color = MaterialTheme.colors.primary
)
```

### Available Color Properties
- **primary** - Primary brand color
- **secondary** - Secondary brand color
- **background** - Background color
- **surface** - Surface color
- **error** - Error color
- **onPrimary** - Color for content on primary
- **onSecondary** - Color for content on secondary
- **onBackground** - Color for content on background
- **onSurface** - Color for content on surface
- **onError** - Color for content on error

### Important Notes
- Must be called within a MaterialTheme composable context
- In nested MaterialTheme composables, retrieves colors from the nearest parent theme
- Returns the appropriate palette based on current theme (light/dark)
- Changes to theme colors are automatically reflected in all components using this property
```

--------------------------------

### Compositing Strategy Examples with Graphics Layer

Source: https://developer.android.com/develop/ui/compose/graphics/draw/modifiers?rec=Cj9odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2Uvc3lzdGVtL2N1dG91dHMQARgJIAEoATAROgMzLjc

Compares two Canvas implementations to illustrate the differences between default graphics layer behavior and offscreen compositing. The first Canvas demonstrates that graphicsLayer without offscreen strategy does not clip content beyond its bounds. The second Canvas (incomplete in source) would show how alpha usage creates an offscreen buffer for proper content clipping and rendering.

```Kotlin
@Composable
fun CompositingStrategyExamples() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        // Does not clip content even with a graphics layer usage here. By default, graphicsLayer
        // does not allocate + rasterize content into a separate layer but instead is used
        // for isolation. That is draw invalidations made outside of this graphicsLayer will not
        // re-record the drawing instructions in this composable as they have not changed
        Canvas(
            modifier = Modifier
                .graphicsLayer()
                .size(100.dp) // Note size of 100 dp here
                .border(2.dp, color = Color.Blue)
        ) {
            // ... and drawing a size of 200 dp here outside the bounds
            drawRect(color = Color.Magenta, size = Size(200.dp.toPx(), 200.dp.toPx()))
        }

        Spacer(modifier = Modifier.size(300.dp))

        /* Clips content as alpha usage here creates an offscreen buffer to rasterize content
        into first then draws to the original destination */
        Canvas(
            modifier = Modifier
        )
    }
}
```

--------------------------------

### Compose Text Composable with Default Arguments

Source: https://developer.android.com/develop/ui/compose/kotlin?rec=CkFodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvY29tcG9zaXRpb25sb2NhbBADGAkgASgEMBc6AzMuNw

Shows how Jetpack Compose uses default arguments to create simple, readable composable functions. The first example demonstrates the minimal required call, while the second shows the equivalent verbose version with explicit default values. This illustrates how default arguments improve code clarity and self-documentation.

```Kotlin
// Simple and self-documenting
Text(text = "Hello, Android!")

// Equivalent but verbose
Text(
    text = "Hello, Android!",
    color = Color.Unspecified,
    fontSize = TextUnit.Unspecified,
    letterSpacing = TextUnit.Unspecified,
    overflow = TextOverflow.Clip
)
```

--------------------------------

### Manage Dialog Visibility State in Jetpack Compose

Source: https://developer.android.com/develop/ui/compose/components/dialog

This example demonstrates how to trigger a dialog from a parent composable using a mutable state variable. It shows how to pass state-resetting logic into the dialog's dismissal and confirmation callbacks.

```Kotlin
@Composable
fun DialogExamples() {
    // ...
    val openAlertDialog = remember { mutableStateOf(false) }

    // ...
        when {
            // ...
            openAlertDialog.value -> {
                AlertDialogExample(
                    onDismissRequest = { openAlertDialog.value = false },
                    onConfirmation = {
                        openAlertDialog.value = false
                        println("Confirmation registered") // Add logic here to handle confirmation.
                    },
                    dialogTitle = "Alert dialog example",
                    dialogText = "This is an example of an alert dialog with buttons.",
                    icon = Icons.Default.Info
                )
            }
        }
    }
}
```

--------------------------------

### Layout Constraints and Child Behavior

Source: https://developer.android.com/develop/ui/compose/modifiers?rec=Cj9odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbGF5b3V0cy9jdXN0b20QAxgJIAEoBDAeOgMzLjc

Understanding how Compose's constraint-based layout system works. Learn how parents pass constraints to children, how children should respect constraints, and techniques to override this behavior.

```APIDOC
## Layout Constraints and Child Behavior

### Description
Jetpack Compose uses a constraint-based layout system where parents pass constraints to children. This document explains how constraints work and how to override them when needed.

### Constraint System Overview

#### Normal Behavior
- Parent passes constraints to children
- Children should respect the constraints from their parent
- Layout system enforces constraint compliance

#### Overriding Constraints
There are several ways to bypass normal constraint behavior:

1. **RequiredSize Modifier**
   - Pass `requiredSize` directly to child
   - Overrides constraints received from parent
   - Example: `Modifier.requiredSize(150.dp)`

2. **Custom Layout**
   - Implement custom layout with different constraint behavior
   - Provides full control over constraint propagation

### Hidden Overflow Behavior

When a child does not respect its constraints:
- The layout system hides this from the parent
- Parent sees child's width/height as if coerced to constraints
- Child is centered within parent's allocated space by default
- Use `wrapContentSize` modifier to override centering

### Example: Oversized Child
```kotlin
Row(
    modifier = Modifier.size(width = 400.dp, height = 100.dp)
) {
    Image(
        modifier = Modifier.requiredSize(150.dp)  // Overrides parent constraint
    )
}
```

In this example:
- Parent Row has height of 100.dp
- Image has requiredSize of 150.dp
- Image will be 150.dp tall, centered within Row
- Parent sees Image as respecting 100.dp constraint

### Best Practices
- Children should normally respect parent constraints
- Use constraint-override techniques only when UI requirements demand it
- Consider using custom layouts for complex constraint scenarios
```

--------------------------------

### Modify Existing Brush Properties in Compose

Source: https://developer.android.com/develop/ui/compose/touch-input/stylus-input/ink-api-brush-apis

This example illustrates how to modify an existing brush by creating a copy with updated properties. The `copyWithComposeColor()` method allows you to change specific attributes, such as the color, while retaining other properties of the original brush.

```kotlin
val redBrush = Brush.createWithComposeColor(
  family = StockBrushes.pressurePen(),
  colorIntArgb = Color.RED,
  size = 5F,
  epsilon = 0.1F
)

val blueBrush = redBrush.copyWithComposeColor(color = Color.BLUE)
```

--------------------------------

### CompositionLocalProvider for Custom Values

Source: https://developer.android.com/develop/ui/compose/compositionlocal

Demonstrates using CompositionLocalProvider with the provides infix function to supply custom values for CompositionLocal instances at different tree levels. Shows how LocalContentColor can be overridden at multiple hierarchy levels, with nested providers taking precedence. Illustrates that CompositionLocalProvider works across composable function boundaries and triggers recomposition when values change.

```Kotlin
@Composable
fun CompositionLocalExample() {
    MaterialTheme {
        // Surface provides contentColorFor(MaterialTheme.colorScheme.surface) by default
        // This is to automatically make text and other content contrast to the background
        // correctly.
        Surface {
            Column {
                Text("Uses Surface's provided content color")
                CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.primary) {
                    Text("Primary color provided by LocalContentColor")
                    Text("This Text also uses primary as textColor")
                    CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.error) {
                        DescendantExample()
                    }
                }
            }
        }
    }
}

@Composable
fun DescendantExample() {
    // CompositionLocalProviders also work across composable functions
    Text("This Text uses the error color now")
}
```

--------------------------------

### Apply Custom Colors to Jetpack Compose Switch

Source: https://developer.android.com/develop/ui/compose/quick-guides/content/add-toggle-switch?authuser=09&hl=en

This example illustrates how to customize the colors of a `Switch`'s thumb and track using the `colors` parameter. It leverages `SwitchDefaults.colors` to define distinct colors for both checked and unchecked states, integrating with the Material Theme's color scheme for a consistent look.

```Kotlin
@Composable
fun SwitchWithCustomColors() {
    var checked by remember { mutableStateOf(true) }

    Switch(
        checked = checked,
        onCheckedChange = {
            checked = it
        },
        colors = SwitchDefaults.colors(
            checkedThumbColor = MaterialTheme.colorScheme.primary,
            checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
            uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
            uncheckedTrackColor = MaterialTheme.colorScheme.secondaryContainer
        )
    )
}
```

--------------------------------

### Image Bitmap Brush Application

Source: https://developer.android.com/develop/ui/compose/graphics/draw/brush

Shows how to load an ImageBitmap and create an ImageShader brush for use with backgrounds, text styling, and canvas drawing operations.

```APIDOC
## Image Bitmap Brush

### Description
Load an ImageBitmap and create an ImageShader brush for applying image textures to backgrounds, text, and canvas drawings.

### Method
Compose UI Modifier Extension

### Endpoint
ShaderBrush(ImageShader(ImageBitmap)): ShaderBrush

### Parameters
#### Method Parameters
- **ImageBitmap** (ImageBitmap) - Required - The image resource to use as brush texture

### Request Example
```kotlin
val imageBrush =
    ShaderBrush(ImageShader(ImageBitmap.imageResource(id = R.drawable.dog)))

// Use ImageShader Brush with background
Box(
    modifier = Modifier
        .requiredSize(200.dp)
        .background(imageBrush)
)

// Use ImageShader Brush with TextStyle
Text(
    text = "Hello Android!",
    style = TextStyle(
        brush = imageBrush,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 36.sp
    )
)

// Use ImageShader Brush with DrawScope#drawCircle()
Canvas(onDraw = {
    drawCircle(imageBrush)
}, modifier = Modifier.size(200.dp))
```

### Response
#### Success Response
- **Background Brush** (Brush) - Image texture applied to Box background
- **Text Brush** (Brush) - Image texture applied to text rendering
- **Canvas Brush** (Brush) - Image texture applied to canvas circle drawing
- **Pixel Rendering** (Boolean) - Text pixels rendered using ImageBitmap texture
```

--------------------------------

### Custom Layout Patterns and Best Practices

Source: https://developer.android.com/develop/ui/compose/layouts/custom

Overview of patterns and best practices for implementing custom layouts in Jetpack Compose, including comparison with the View system and recommendations for layout implementation.

```APIDOC
## Custom Layout Patterns

### Description
Guidelines and patterns for creating effective custom layouts in Jetpack Compose, with comparison to traditional View system approaches.

### Compose vs View System

#### View System (Traditional)
- Extend ViewGroup class
- Implement measure() function
- Implement layout() function
- Override onDraw() for rendering

#### Compose Approach
- Write a @Composable function
- Use Layout composable
- Provide measurePolicy lambda
- Declarative and functional

### Standard Custom Layout Pattern

1. **Create @Composable Function**
   - Accept modifier parameter
   - Accept content lambda for children
   - Optional: Accept layout-specific parameters

2. **Use Layout Composable**
   - Pass modifier to Layout
   - Pass content lambda to Layout
   - Implement measurePolicy

3. **Implement MeasurePolicy**
   - Map measurables to measure each child
   - Call layout() with calculated dimensions
   - Position children using placeRelative() or place()

4. **Handle Constraints**
   - Don't over-constrain children
   - Pass parent constraints to children
   - Respect min/max constraints appropriately

### Key Considerations
- **Constraint Propagation**: Consider which constraints to pass to children
- **Size Calculation**: Determine layout size based on children and constraints
- **Positioning Logic**: Implement positioning algorithm (vertical, horizontal, grid, etc.)
- **Layout Direction**: Use placeRelative() for RTL support
- **Performance**: Minimize recompositions and measurements

### Related Concepts
- **Intrinsic Measurements**: Query child information before actual measurement
- **Layout Modifier**: For single composable layout changes
- **Higher-Level Layouts**: Column, Row, Box built on Layout composable
```

--------------------------------

### Implement Custom remember Factory with State Persistence

Source: https://developer.android.com/develop/ui/compose/state-lifespans

This example demonstrates how to create a custom 'remember' function for an ImageState object. It utilizes rememberSaveable with a custom Saver to ensure the state is persisted across recompositions and process death, while providing a clean API for consumers.

```kotlin
@Composable
fun rememberImageState(
    imageUri: String,
    initialZoom: Float = 1f,
    initialPanX: Int = 0,
    initialPanY: Int = 0
): ImageState {
    return rememberSaveable(imageUri, saver = ImageState.Saver) {
        ImageState(
            imageUri, initialZoom, initialPanX, initialPanY
        )
    }
}

data class ImageState(
    val imageUri: String,
    val zoom: Float,
    val panX: Int,
    val panY: Int
) {
    object Saver : androidx.compose.runtime.saveable.Saver<ImageState, Any> by listSaver(
        save = { listOf(it.imageUri, it.zoom, it.panX, it.panY) },
        restore = { ImageState(it[0] as String, it[1] as Float, it[2] as Int, it[3] as Int) }
    )
}
```

--------------------------------

### TargetBasedAnimation: Manual Time Control

Source: https://developer.android.com/develop/ui/compose/animation/value-based?rec=CltodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdG91Y2gtaW5wdXQvcG9pbnRlci1pbnB1dC9kcmFnLXN3aXBlLWZsaW5nEAMYCSABKAUwHzoDMy43

Demonstrates how to use `TargetBasedAnimation` to manually control animation playback based on frame time, allowing for explicit start and target values.

```APIDOC
## API Component: TargetBasedAnimation

### Description
`TargetBasedAnimation` is a low-level animation API that allows for manual control over an animation's play time. It requires an `animationSpec`, a `typeConverter`, an `initialValue`, and a `targetValue`. The animation progresses from the `initialValue` to the `targetValue` over time, which is controlled externally.

### Usage
Use `TargetBasedAnimation` when you need precise, manual control over the animation's progression, often driven by frame time or other external signals. It's stateless and serves as a calculation engine.

### Parameters
- **animationSpec** (AnimationSpec) - Required - Defines the animation curve and duration (e.g., `tween`, `spring`).
- **typeConverter** (TwoWayConverter<T, V>) - Required - Converts between the animated value type (T) and its vector representation (V).
- **initialValue** (T) - Required - The starting value of the animation.
- **targetValue** (T) - Required - The ending value of the animation.

### Example
```kotlin
val anim = remember {
    TargetBasedAnimation(
        animationSpec = tween(200),
        typeConverter = Float.VectorConverter,
        initialValue = 200f,
        targetValue = 1000f
    )
}
var playTime by remember { mutableLongStateOf(0L) }

LaunchedEffect(anim) {
    val startTime = withFrameNanos { it }

    do {
        playTime = withFrameNanos { it } - startTime
        val animationValue = anim.getValueFromNanos(playTime)
    } while (someCustomCondition())
}
```
```

--------------------------------

### CONSTRUCT DecayAnimation

Source: https://developer.android.com/develop/ui/compose/animation/value-based?rec=CkNodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYW5pbWF0aW9uL2FkdmFuY2VkEAEYCSABKAEwDDoDMy43

An animation engine that calculates its target value based on starting conditions and a decay specification, typically used to slow elements to a stop after a fling gesture.

```APIDOC
## CONSTRUCT DecayAnimation

### Description
Unlike target-based animations, this engine calculates its target value based on initial velocity and value, slowing down over time according to a decay spec.

### Method
CONSTRUCTOR

### Endpoint
DecayAnimation<T, V : AnimationVector>

### Parameters
#### Constructor Parameters
- **animationSpec** (DecayAnimationSpec<T>) - Required - Defines the decay behavior (e.g., exponential decay).
- **typeConverter** (TwoWayConverter<T, V>) - Required - Converter between the value type and a vector.
- **initialValue** (T) - Required - The starting value of the decay.
- **initialVelocity** (T) - Required - The starting velocity vector.

### Request Example
{
  "animationSpec": "exponentialDecay()",
  "initialValue": 0.0,
  "initialVelocity": 1000.0
}

### Response
#### Success Response (200)
- **targetValue** (T) - The calculated end point where the animation will naturally come to a stop.

#### Response Example
{
  "targetValue": 1250.0
}
```

--------------------------------

### Basic Composable Function with Multiple Text Elements

Source: https://developer.android.com/develop/ui/compose/layouts/basics

Demonstrates a simple composable function that emits multiple UI elements. This example shows an ArtistCard composable that displays two Text elements without explicit layout guidance, which results in elements being stacked on top of each other. This illustrates the need for layout containers to properly arrange UI elements in Compose.

```Kotlin
@Composable
fun ArtistCard() {
    Text("Alfred Sisley")
    Text("3 minutes ago")
}
```

--------------------------------

### Combining Pointer Input with Coroutine Animations

Source: https://developer.android.com/develop/ui/compose/kotlin?rec=Cj9odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbGF5b3V0cy9iYXNpY3MQAhgJIAEoBDAIOgMzLjc

An advanced example using the pointerInput modifier and Animatable API. It uses a nested coroutineScope to handle continuous user tap events and trigger asynchronous position animations.

```kotlin
@Composable
fun MoveBoxWhereTapped() {
    // Creates an `Animatable` to animate Offset and `remember` it.
    val animatedOffset = remember {
        Animatable(Offset(0f, 0f), Offset.VectorConverter)
    }

    Box(
        // The pointerInput modifier takes a suspend block of code
        Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                // Create a new CoroutineScope to be able to create new
                // coroutines inside a suspend function
                coroutineScope {
                    while (true) {
                        // Wait for the user to tap on the screen and animate
                        // in the same block
                        awaitPointerEventScope {
                            val offset = awaitFirstDown().position

                            // Launch a new coroutine to asynchronously animate to
                            // where the user tapped on the screen
                            launch {
                                // Animate to the pressed position
                                animatedOffset.animateTo(offset)
                            }
                        }
                    }
                }
            }
    ) {
        Text("Tap anywhere", Modifier.align(Alignment.Center))
        Box(
            Modifier
                .offset {
                    // Use the animated offset as the offset of this Box
                    IntOffset(
                        animatedOffset.value.x.roundToInt(),
                        animatedOffset.value.y.roundToInt()
                    )
                }
                .size(40.dp)
                .background(Color(0xff3c1361), CircleShape)
        )
    }
```

--------------------------------

### GET /compose/runtime/rememberSaveable

Source: https://developer.android.com/develop/ui/compose/state-saving?rec=CjpodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbGlmZWN5Y2xlEAMYCSABKAUwFzoDMy43

Retains state across activity and process recreation by storing values in a Bundle. It is used for state hoisted in the UI or plain state holder classes.

```APIDOC
## GET /compose/runtime/rememberSaveable

### Description
Stores UI element state in a Bundle through the saved instance state mechanism. It survives configuration changes and system-initiated process death.

### Method
GET (Composable Function)

### Endpoint
androidx.compose.runtime.saveable.rememberSaveable

### Parameters
#### Query Parameters
- **saver** (Saver) - Optional - Custom mechanism to store and restore non-primitive types.
- **init** (Lambda) - Required - A lambda that returns the initial state value.

### Request Example
```kotlin
@Composable
fun ChatBubble(message: Message) {
    var showDetails by rememberSaveable { mutableStateOf(false) }
    // ... UI logic
}
```

### Response
#### Success Response (200)
- **state** (MutableState<T>) - A state object that persists across recreation.

#### Response Example
{
  "value": true
}
```

--------------------------------

### Launch an Activity with Glance actionStartActivity

Source: https://developer.android.com/develop/ui/compose/glance/user-interaction

Demonstrates how to launch an Android Activity when a user interacts with a UI component in a Glance App Widget. It uses the `actionStartActivity` function, providing the target Activity class, to create a clickable element that navigates to the specified activity.

```kotlin
@Composable
fun MyContent() {
    // ..
    Button(
        text = "Go Home",
        onClick = actionStartActivity<MyActivity>()
    )
}
```

--------------------------------

### Apply Elevation Overlays with Surface Composable

Source: https://developer.android.com/develop/ui/compose/designsystems/material

Demonstrates how the Surface composable automatically applies elevation overlays in dark themes. The overlay lightens the surface color based on its elevation value, creating visual hierarchy. This example shows a Surface with 2.dp elevation that will have its color automatically adjusted.

```Kotlin
Surface(
    elevation = 2.dp,
    color = MaterialTheme.colors.surface, // color will be adjusted for elevation
    /*...*/
) { /*...*/ }
```

--------------------------------

### GET /modifiers/matchParentSize

Source: https://developer.android.com/develop/ui/compose/modifiers?rec=Cj9odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbGF5b3V0cy9iYXNpY3MQARgJIAEoAjAZOgMzLjc

A modifier available within BoxScope that allows a child to match the parent Box's size without influencing the Box's own size calculation.

```APIDOC
## GET Modifier.matchParentSize()

### Description
Sizes the element to match the size of the parent Box after all other children have been measured. This modifier is only available to direct children of a Box.

### Method
GET

### Endpoint
androidx.compose.foundation.layout.BoxScope.matchParentSize

### Parameters
#### Path Parameters
- **scope** (BoxScope) - Required - The scope provided by the parent Box composable.

#### Query Parameters
- N/A

#### Request Body
- N/A

### Request Example
{
  "modifier": "Modifier.matchParentSize()"
}

### Response
#### Success Response (200)
- **modifier** (Modifier) - The resulting modifier instance applied to the child.

#### Response Example
{
  "status": "applied",
  "type": "ParentDataModifier"
}
```

--------------------------------

### Share View System State with Compose using mutableStateOf

Source: https://developer.android.com/develop/ui/compose/migrate/other-considerations

Illustrates how to manage state when the View system is the source of truth. The example wraps the View state in mutableStateOf to make it thread-safe for Compose, allowing a TextField in Compose to update a TextView in the View system.

```kotlin
class CustomViewGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    // Source of truth in the View system as mutableStateOf
    // to make it thread-safe for Compose
    private var text by mutableStateOf("")

    private val textView: TextView

    init {
        orientation = VERTICAL

        textView = TextView(context)
        val composeView = ComposeView(context).apply {
            setContent {
                MaterialTheme {
                    TextField(value = text, onValueChange = { updateState(it) })
                }
            }
        }

        addView(textView)
        addView(composeView)
    }

    // Update both the source of truth and the TextView
    private fun updateState(newValue: String) {
        text = newValue
        textView.text = newValue
    }
}
```

--------------------------------

### Common Built-in Modifiers

Source: https://developer.android.com/develop/ui/compose/modifiers?rec=Cj9odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbGF5b3V0cy9iYXNpY3MQARgJIAEoAjAZOgMzLjc

Reference guide for commonly used built-in modifiers in Jetpack Compose. Includes modifiers for adjusting layout, size, spacing, and user interactions.

```APIDOC
## Common Built-in Modifiers

### Description
Jetpack Compose provides a list of built-in modifiers to help you decorate or augment a composable. These are some of the most commonly used modifiers for adjusting layouts.

### Modifier Categories

#### Size and Layout Modifiers
- **fillMaxWidth()** - Makes the composable fill the maximum width available from its parent
- **fillMaxHeight()** - Makes the composable fill the maximum height available from its parent
- **fillMaxSize()** - Makes the composable fill both maximum width and height
- **size(width, height)** - Sets explicit width and height dimensions
- **width(size)** - Sets explicit width dimension
- **height(size)** - Sets explicit height dimension

#### Spacing Modifiers
- **padding(all)** - Adds space around all sides of the element
- **padding(horizontal, vertical)** - Adds space with separate horizontal and vertical values
- **padding(start, top, end, bottom)** - Adds space with individual control for each side

#### Positioning Modifiers
- **offset(x, y)** - Offsets the composable by the specified x and y distances

#### Interaction Modifiers
- **clickable(onClick)** - Makes the composable respond to click events
- **scrollable()** - Makes the composable scrollable
- **draggable()** - Makes the composable draggable
- **zoomable()** - Makes the composable zoomable

#### Accessibility Modifiers
- **semantics { }** - Adds semantic information for accessibility labels and descriptions

### Usage Notes
Modifiers can be chained together and their order matters. Apply modifiers in the order that produces your desired behavior.
```

--------------------------------

### PROVIDE /Theme/CompositionLocals

Source: https://developer.android.com/develop/ui/compose/designsystems/anatomy?rec=Cj5odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZG9jdW1lbnRhdGlvbhADGAkgASgGMCM6AzMuNw

Creates CompositionLocal keys to store and retrieve theme system instances within the composition tree.

```APIDOC
## PROVIDE /Theme/CompositionLocals

### Description
Static composition locals used to implicitly pass theme data through the composition tree.

### Method
staticCompositionLocalOf

### Endpoint
LocalColorSystem, LocalTypographySystem, LocalCustomSystem

### Parameters
#### Request Body
- **defaultFactory** (Lambda) - Required - Provides fallback values (e.g., Color.Unspecified) if no provider is found.

### Request Example
{
  "function": "staticCompositionLocalOf",
  "fallback": "ColorSystem(color = Color.Unspecified)"
}

### Response
#### Success Response (200)
- **CompositionLocal** (Key) - A key used with CompositionLocalProvider to inject theme values.
```

--------------------------------

### Define Custom Typography

Source: https://developer.android.com/develop/ui/compose/designsystems/material3?rec=ClJodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZGVzaWduc3lzdGVtcy9tYXRlcmlhbDItbWF0ZXJpYWwzEAIYCSABKAUwDDoDMy43

Create a custom Typography configuration by instantiating the M3 Typography class with customized TextStyle properties. This example demonstrates how to define a reduced set of typography styles with custom font weights, sizes, and line heights.

```APIDOC
## POST /compose/typography/define

### Description
Define a custom Typography configuration for Material Design 3 by creating a Typography instance with customized TextStyle properties. You can override default values for font weight, font family, font style, size, line height, letter spacing, and baseline shift.

### Method
Configuration Definition

### Parameters

#### TextStyle Properties
- **fontWeight** (FontWeight) - Optional - The weight of the font (e.g., FontWeight.SemiBold, FontWeight.Normal)
- **fontSize** (Sp) - Optional - The size of the text in scaled pixels (sp)
- **lineHeight** (Sp) - Optional - The height of each line of text
- **letterSpacing** (Sp) - Optional - The spacing between letters
- **fontFamily** (FontFamily) - Optional - The font family to use (e.g., FontFamily.SansSerif)
- **fontStyle** (FontStyle) - Optional - The style of the font (e.g., FontStyle.Italic)
- **baselineShift** (BaselineShift) - Optional - Shift the baseline (e.g., BaselineShift.Subscript)

### Request Example - Basic Typography Definition
```kotlin
val replyTypography = Typography(
    titleLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    )
)
```

### Request Example - Customized TextStyle
```kotlin
bodyLarge = TextStyle(
    fontWeight = FontWeight.Normal,
    fontFamily = FontFamily.SansSerif,
    fontStyle = FontStyle.Italic,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.15.sp,
    baselineShift = BaselineShift.Subscript
)
```

### Response
#### Success Response
- **Typography** (Object) - A configured Typography object ready to be passed to MaterialTheme

### Usage Notes
- You do not need to define all 15 default styles; define only the styles your product requires
- Omit parameters you do not want to customize; defaults will be used
- Pass the defined Typography to MaterialTheme via the `typography` parameter
```

--------------------------------

### Create Alternating Grid Sizes in FlowRow

Source: https://developer.android.com/develop/ui/compose/layouts/flow?rec=CkJodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdG91Y2gtaW5wdXQvZm9jdXMQAxgJIAEoBDAqOgMzLjc

Shows how to create a layout with alternating item widths by conditionally applying Modifier.fillMaxWidth() or Modifier.weight(). This example places two items per row, with every third item spanning the full width.

```kotlin
FlowRow(
    modifier = Modifier.padding(4.dp),
    horizontalArrangement = Arrangement.spacedBy(4.dp),
    maxItemsInEachRow = 2
) {
    val itemModifier = Modifier
        .padding(4.dp)
        .height(80.dp)
        .clip(RoundedCornerShape(8.dp))
        .background(Color.Blue)
    repeat(6) { item ->
        if ((item + 1) % 3 == 0) {
            Spacer(modifier = itemModifier.fillMaxWidth())
        } else {
            Spacer(modifier = itemModifier.weight(0.5f))
        }
    }
}
```

--------------------------------

### Use Custom PressIconButton Composable

Source: https://developer.android.com/develop/ui/compose/touch-input/user-interactions/handling-interactions

Demonstrates how to instantiate and use the custom PressIconButton component with a shopping cart icon and 'Add to cart' text. Shows the minimal setup required to leverage the custom button's press-triggered icon display functionality.

```Kotlin
PressIconButton(
    onClick = {},
    icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = null) },
    text = { Text("Add to cart") }
)
```

--------------------------------

### ViewModel State Management for Business Logic

Source: https://developer.android.com/develop/ui/compose/state-saving

Explains how to use ViewModel for managing UI state that is tied to business logic. ViewModel automatically handles configuration changes by maintaining state in memory across activity recreation, though it does not survive system-initiated process death.

```APIDOC
## ViewModel State Management

### Description
Managing UI state hoisted to ViewModel when required by business logic. ViewModel provides automatic handling of configuration changes and maintains state across activity recreation.

### Key Benefits
- **Configuration Change Handling**: State is preserved when activity is destroyed and recreated
- **Memory Persistence**: ViewModel instance remains in memory during configuration changes
- **Automatic Reattachment**: Old ViewModel instance is automatically attached to new activity instance

### Important Notes
- **Architectural Decision**: Hoist UI state to ViewModel because it makes architectural sense, not just for configuration change handling
- **Process Death Limitation**: ViewModel does NOT survive system-initiated process death
- **Saved State Module**: Use SavedStateHandle from Saved State module for process death survival

### Configuration Change Flow
1. Configuration change occurs (e.g., device rotation)
2. Activity is destroyed and recreated
3. UI state in ViewModel remains in memory
4. Old ViewModel instance attaches to new activity
5. UI state is immediately available without restoration delay
```

--------------------------------

### Consolidate Nested Actions into Container Semantics

Source: https://developer.android.com/develop/ui/compose/accessibility/semantics

This example shows how to move actions from individual child components into a single action menu on a parent container. It uses clearAndSetSemantics on children to prevent redundant traversal by assistive technologies like Switch Access.

```Kotlin
ArticleListItemRow(
    modifier = Modifier
        .semantics {
            customActions = listOf(
                CustomAccessibilityAction(
                    label = "Open article",
                    action = {
                        openArticle()
                        true
                    }
                ),
                CustomAccessibilityAction(
                    label = "Add to bookmarks",
                    action = {
                        addToBookmarks()
                        true
                    }
                ),
            )
        }
) {
    Article(
        modifier = Modifier.clearAndSetSemantics { },
        onClick = openArticle,
    )
    BookmarkButton(
        modifier = Modifier.clearAndSetSemantics { },
        onClick = addToBookmarks,
    )
}
```

--------------------------------

### Perform Assertions on a Single Node in Compose UI Tests

Source: https://developer.android.com/develop/ui/compose/testing/apis?rec=CkZodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYWNjZXNzaWJpbGl0eS90ZXN0aW5nEAIYCSABKAIwHDoDMy43

This snippet demonstrates how to perform assertions on a single UI node using assert() after selecting it with a finder. It shows examples of using a single SemanticsMatcher and combining multiple matchers with or for more complex conditions.

```Kotlin
// Single matcher:
composeTestRule
    .onNode(matcher)
    .assert(hasText("Button")) // hasText is a SemanticsMatcher

// Multiple matchers can use and / or
composeTestRule
    .onNode(matcher).assert(hasText("Button") or hasText("Button2"))
```

--------------------------------

### Clear TextFieldState text in Compose

Source: https://developer.android.com/develop/ui/compose/text/user-input?rec=Cj1odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYXJjaGl0ZWN0dXJlEAMYCSABKAMwFzoDMy43

This example demonstrates the `clearText` method of `TextFieldState` to remove all text content from the `TextField` and reset the selection to the beginning, effectively making the field empty.

```kotlin
textFieldState.clearText()
// textFieldState.text :
// textFieldState.selection : TextRange(0, 0)
```

--------------------------------

### POST /Theme

Source: https://developer.android.com/develop/ui/compose/designsystems/anatomy?rec=Cj5odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZG9jdW1lbnRhdGlvbhADGAkgASgGMCM6AzMuNw

The primary entry point function for a Compose theme. It initializes theme systems and provides them to the composition tree using CompositionLocalProvider.

```APIDOC
## POST /Theme

### Description
The entry point function for a custom design system. It constructs instances of theme systems and scopes them to the UI hierarchy.

### Method
@Composable Function

### Endpoint
Theme(content: @Composable () -> Unit)

### Parameters
#### Request Body
- **content** (Composable Lambda) - Required - The UI content that will consume the theme values.

### Request Example
{
  "content": "@Composable () -> Unit"
}

### Response
#### Success Response (200)
- **CompositionLocalProvider** (Provider) - Provides LocalColorSystem, LocalTypographySystem, and LocalCustomSystem to the hierarchy.

### Response Example
{
  "status": "Theme applied to hierarchy"
}
```

--------------------------------

### DisposableEffect with LifecycleObserver

Source: https://developer.android.com/develop/ui/compose/side-effects?rec=CjdodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2Uva290bGluEAEYCSABKAMwCDoDMy43

Example of using DisposableEffect to manage a lifecycle observer. It uses a key to restart the effect if the lifecycleOwner changes and rememberUpdatedState to prevent unnecessary restarts from callback changes.

```kotlin
@Composable
fun HomeScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onStart: () -> Unit, // Send the 'started' analytics event
    onStop: () -> Unit // Send the 'stopped' analytics event
) {
    // These values never change in Composition
    val currentOnStart by rememberUpdatedState(onStart)
    val currentOnStop by rememberUpdatedState(onStop)

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            /* ... */
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}
```

--------------------------------

### Add Paging and Compose BOM Dependencies

Source: https://developer.android.com/develop/ui/compose/quick-guides/content/lazily-load-list?hl=en

This snippet demonstrates how to add the necessary dependencies for using the Paging library with Jetpack Compose. It includes the Compose Bill of Materials (BOM) for consistent version management and the paging-compose artifact for Paging integration.

```Kotlin
implementation(platform("androidx.compose:compose-bom:2026.03.00"))
  implementation("androidx.paging:paging-compose:3.3.0-alpha05")
```

```Groovy
implementation platform('androidx.compose:compose-bom:2026.03.00')
  implementation "androidx.paging:paging-compose:3.3.0-alpha05"
```

--------------------------------

### Format Phone Number with OutputTransformation

Source: https://developer.android.com/develop/ui/compose/text/user-input

This example shows how to create an OutputTransformation to format a phone number. It inserts parentheses and dashes at specific positions in the TextFieldBuffer based on the input length. This allows for displaying the number in a user-friendly format as the user types.

```kotlin
class PhoneNumberOutputTransformation : OutputTransformation {
    override fun TextFieldBuffer.transformOutput() {
        if (length > 0) insert(0, "(")
        if (length > 4) insert(4, ")")
        if (length > 8) insert(8, "-")
    }
}
```

--------------------------------

### Apply Linear Gradient Brush to Text in Jetpack Compose

Source: https://developer.android.com/develop/ui/compose/text/style-text

This example shows how to apply a linear gradient brush to an entire Text composable using the experimental Brush API within TextStyle. It defines a list of colors to create a visually appealing gradient effect on the text.

```Kotlin
val gradientColors = listOf(Cyan, LightBlue, Purple /*...*/)

Text(
    text = text,
    style = TextStyle(
        brush = Brush.linearGradient(
            colors = gradientColors
        )
    )
)
```

--------------------------------

### Animate item changes in Lazy lists

Source: https://developer.android.com/develop/ui/compose/lists?rec=CjdodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvcGhhc2VzEAIYCSABKAMwIToDMy43

Explains how to use the animateItem modifier to enable automatic animations for item reordering. Includes examples for default and custom animation specifications.

```Kotlin
LazyColumn {
    items(books, key = { it.id }) {
        Row(Modifier.animateItem()) {
            // ...
        }
    }
}

// Custom animation specification
LazyColumn {
    items(books, key = { it.id }) {
        Row(
            Modifier.animateItem(
                fadeInSpec = tween(durationMillis = 250),
                fadeOutSpec = tween(durationMillis = 100),
                placementSpec = spring(stiffness = Spring.StiffnessLow, dampingRatio = Spring.DampingRatioMediumBouncy)
            )
        ) {
            // ...
        }
    }
}
```

--------------------------------

### Nested State Definitions for Combined Interaction States in Compose

Source: https://developer.android.com/develop/ui/compose/styles/state-animations

Demonstrates creating nested state definitions to handle multiple simultaneous interaction states. The example shows a button that responds differently when both hovered and pressed, versus when only pressed. This allows device-specific behavior where hover is available on desktop but not on touch devices.

```Kotlin
@Composable
private fun OpenButton_CombinedStates() {
    BaseButton(
        style = outlinedButtonStyle then {
            background(Color.White)
            hovered {
                // light purple
                background(lightPurple)
                pressed {
                    // When running on a device that can hover, whilst hovering and then pressing the button this would be invoked
                    background(lightOrange)
                }
            }
            pressed {
                // when running on a device without a mouse attached, this would be invoked as you wouldn't be in a hovered state only
                background(lightRed)
            }
            focused {
                background(lightBlue)
            }
        },
        onClick = {  },
        content = {
            BaseText("Open in Studio", style = {
                contentColor(Color.Black)
                fontSize(26.sp)
                textAlign(TextAlign.Center)
            })
        }
    )
}
```

--------------------------------

### Define custom FontFamily from local font resources in Compose

Source: https://developer.android.com/develop/ui/compose/text/fonts

This example shows how to create a custom FontFamily object in Compose by referencing font files located in the res/font folder. It demonstrates associating different font weights and styles (e.g., Light, Normal, Italic, Medium, Bold) with specific font resources to build a comprehensive font family.

```kotlin
val firaSansFamily = FontFamily(
    Font(R.font.firasans_light, FontWeight.Light),
    Font(R.font.firasans_regular, FontWeight.Normal),
    Font(R.font.firasans_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.firasans_medium, FontWeight.Medium),
    Font(R.font.firasans_bold, FontWeight.Bold)
)
```

--------------------------------

### Preview Composables for Layout Verification

Source: https://developer.android.com/develop/ui/compose/layouts/custom?rec=CjpodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbW9kaWZpZXJzEAMYCSABKAIwKToDMy43

Provides preview composables to verify the custom firstBaselineToTop modifier works correctly by comparing it with standard padding. These previews demonstrate the visual difference between baseline-based and standard padding approaches.

```APIDOC
## Preview Composables

### Description
Preview functions to visually verify the firstBaselineToTop modifier behavior and compare it with standard padding.

### TextWithPaddingToBaselinePreview
```kotlin
@Preview
@Composable
fun TextWithPaddingToBaselinePreview() {
    MyApplicationTheme {
        Text("Hi there!", Modifier.firstBaselineToTop(32.dp))
    }
}
```

### TextWithNormalPaddingPreview
```kotlin
@Preview
@Composable
fun TextWithNormalPaddingPreview() {
    MyApplicationTheme {
        Text("Hi there!", Modifier.padding(top = 32.dp))
    }
}
```

### Parameters
- **Text** (String) - Required - The text content to display ("Hi there!")
- **Modifier** (Modifier) - Required - The modifier to apply (either firstBaselineToTop or padding)
- **firstBaselineToTop** (Dp) - Required - Distance value set to 32.dp
- **padding(top)** (Dp) - Required - Standard padding value set to 32.dp

### Usage
Both preview functions are decorated with `@Preview` annotation to enable preview rendering in Android Studio. They demonstrate the visual difference between baseline-based positioning and standard padding.
```

--------------------------------

### Generate Color Schemes

Source: https://developer.android.com/develop/ui/compose/designsystems/material3?rec=CkVodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZGVzaWduc3lzdGVtcy9jdXN0b20QARgJIAEoBDAYOgMzLjc

Learn how to generate custom color schemes using Material Theme Builder and implement them in your Compose application. The generated files include Color.kt for theme colors and Theme.kt for light/dark color scheme setup.

```APIDOC
## Color Scheme Generation

### Description
Generate Material Design 3 color schemes using Material Theme Builder tool. This produces two main files: Color.kt containing all theme colors for light and dark variants, and Theme.kt containing the color scheme setup and app theme composable.

### Generated Files

#### Color.kt
Contains all color definitions for light and dark themes with Material Design 3 color roles.

```kotlin
val md_theme_light_primary = Color(0xFF476810)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFFC7F089)

val md_theme_dark_primary = Color(0xFFACD370)
val md_theme_dark_onPrimary = Color(0xFF213600)
val md_theme_dark_primaryContainer = Color(0xFF324F00)
```

#### Theme.kt
Sets up light and dark color schemes and provides the app theme composable.

```kotlin
private val LightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer
)

private val DarkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer
)

@Composable
fun ReplyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (!darkTheme) LightColorScheme else DarkColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
```

### Key Features
- Automatic generation of light and dark color variants
- All Material Design 3 color roles pre-configured
- System theme detection using `isSystemInDarkTheme()`
- Ready-to-use theme composable for wrapping app content
```

--------------------------------

### Setting Up Jetpack Compose UI in MainActivity

Source: https://developer.android.com/develop/ui/compose/tutorial?hl=vi

Illustrates how to initialize and display a Jetpack Compose UI within an Android MainActivity. The setContent block is used to define the root Composable (ComposeTutorialTheme wrapping Conversation) that will be rendered on the screen.

```kotlin
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
// Assume ComposeTutorialTheme, Conversation, SampleData exist
// For example:
// @Composable fun ComposeTutorialTheme(content: @Composable () -> Unit) { /* ... */ }
// @Composable fun Conversation(messages: List<Message>) { /* ... */ }
// object SampleData { val conversationSample: List<Message> = listOf(...) }

class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
       setContent {
           ComposeTutorialTheme {
               Conversation(SampleData.conversationSample)
           }
       }
   }
}
```

--------------------------------

### Compositing Strategy Examples with Canvas and Graphics Layer

Source: https://developer.android.com/develop/ui/compose/graphics/draw/modifiers?rec=CkRodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2Uvc3lzdGVtL3Rlc3QtY3V0b3V0cxABGAkgASgCMB46AzMuNw

Compares graphicsLayer usage with and without offscreen compositing. Shows how default graphicsLayer behavior does not clip content or create offscreen buffers, while using alpha or CompositingStrategy.Offscreen creates an offscreen buffer that clips content to the drawing area bounds.

```Kotlin
@Composable
fun CompositingStrategyExamples() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Canvas(
            modifier = Modifier
                .graphicsLayer()
                .size(100.dp)
                .border(2.dp, color = Color.Blue)
        ) {
            drawRect(color = Color.Magenta, size = Size(200.dp.toPx(), 200.dp.toPx()))
        }

        Spacer(modifier = Modifier.size(300.dp))

        Canvas(
            modifier = Modifier
        )
    }
}
```

--------------------------------

### Customize a Rich Tooltip with Actions in Android Compose

Source: https://developer.android.com/develop/ui/compose/components/tooltip

This example shows how to customize a rich tooltip in Android Compose by adding a title, custom actions (like a dismiss button), and managing its state manually. It uses `TooltipBox` and `RichTooltip` to display information for an `IconButton`.

```kotlin
@Composable
fun AdvancedRichTooltipExample(
    modifier: Modifier = Modifier,
    richTooltipSubheadText: String = "Custom Rich Tooltip",
    richTooltipText: String = "Rich tooltips support multiple lines of informational text.",
    richTooltipActionText: String = "Dismiss"
) {
    val tooltipState = rememberTooltipState()
    val coroutineScope = rememberCoroutineScope()

    TooltipBox(
        modifier = modifier,
        positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider(),
        tooltip = {
            RichTooltip(
                title = { Text(richTooltipSubheadText) },
                action = {
                    Row {
                        TextButton(onClick = {
                            coroutineScope.launch {
                                tooltipState.dismiss()
                            }
                        }) {
                            Text(richTooltipActionText)
                        }
                    }
                }
            ) {
                Text(richTooltipText)
            }
        },
        state = tooltipState
    ) {
        IconButton(onClick = {
            coroutineScope.launch {
                tooltipState.show()
            }
        }) {
            Icon(
                imageVector = Icons.Filled.Camera,
                contentDescription = "Open camera"
            )
        }
    }
}
```

--------------------------------

### Scaffold and Snackbar Integration (M2 vs M3)

Source: https://developer.android.com/develop/ui/compose/designsystems/material2-material3?rec=CkVodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZGVzaWduc3lzdGVtcy9jdXN0b20QAhgJIAEoBzAKOgMzLjc

Explains the change in Snackbar integration with `Scaffold`, moving from `ScaffoldState` in M2 to `SnackbarHostState` and `SnackbarHost` in M3. Also notes the split of `SnackbarData`.

```APIDOC
## Scaffold and Snackbar Integration

### Description
In Material 2, `ScaffoldState` was used to manage and show snackbars. In Material 3, `ScaffoldState` no longer exists for this purpose; instead, `SnackbarHostState` and `SnackbarHost` are used directly with the `Scaffold`'s `snackbarHost` parameter. The `SnackbarData` class in M2 has also been split into `SnackbarData` and `SnackbarVisuals` in M3.

### Material 2 Usage
```kotlin
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope

val scaffoldState = rememberScaffoldState()
val scope = rememberCoroutineScope()

Scaffold(
    scaffoldState = scaffoldState,
    content = {
        …
        scope.launch {
            scaffoldState.snackbarHostState.showSnackbar(…)
        }
    }
)
```

### Material 3 Usage
```kotlin
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope

val snackbarHostState = remember { SnackbarHostState() }
val scope = rememberCoroutineScope()

Scaffold(
    snackbarHost = { SnackbarHost(snackbarHostState) },
    content = {
        …
        scope.launch {
            snackbarHostState.showSnackbar(…)
        }
    }
)
```
```

--------------------------------

### Applying Custom Drawing Modifiers

Source: https://developer.android.com/develop/ui/compose/graphics/draw/modifiers?rec=CkpodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZ3JhcGhpY3MvaW1hZ2VzL2N1c3RvbWl6ZRABGAkgASgCMB46AzMuNw

Apply custom drawing modifiers to composable elements. This example shows how to use the flipped modifier on a Text composable to vertically flip its content.

```APIDOC
## Modifier Application Pattern

### Description
Apply custom drawing modifiers to composable elements using the modifier parameter.

### Method
Composable Element Configuration

### Endpoint
Composable(modifier: Modifier = Modifier)

### Parameters
#### Modifier Parameters
- **modifier** (Modifier) - Optional - The modifier chain to apply to the composable

### Request Example
```kotlin
Text(
    "Hello Compose!",
    modifier = Modifier
        .flipped()
)
```

### Response
#### Visual Output
- Text content is vertically flipped (inverted on Y-axis)
- All child drawing operations are transformed by the modifier

### Chaining Modifiers
Multiple modifiers can be chained together:
```kotlin
Text(
    "Hello Compose!",
    modifier = Modifier
        .background(Color.White)
        .flipped()
        .padding(16.dp)
)
```

### Notes
- Modifier order matters - modifiers are applied in sequence
- Custom modifiers integrate seamlessly with built-in modifiers
- Use then() to combine modifiers in extension functions
```

--------------------------------

### Share State Between Modifier.Node Instances Using Delegation

Source: https://developer.android.com/develop/ui/compose/custom-modifiers

This example illustrates how to share common state across multiple Modifier.Node instances by using delegation. It shows a ClickableNode delegating to FocusableNode and IndicationNode to share interactionData, promoting code reuse and state management.

```kotlin
class ClickableNode : DelegatingNode() {
    val interactionData = InteractionData()
    val focusableNode = delegate(
        FocusableNode(interactionData)
    )
    val indicationNode = delegate(
        IndicationNode(interactionData)
    )
}
```

--------------------------------

### Create Replacement Theme with Custom Systems in Jetpack Compose

Source: https://developer.android.com/develop/ui/compose/designsystems/custom?rec=CjpodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvcmVzb3VyY2VzEAMYCSABKAUwFjoDMy43

Implements a ReplacementTheme composable that provides custom Typography and Shapes through CompositionLocalProvider while wrapping MaterialTheme. Includes a companion object with computed properties to access the custom systems, allowing components to retrieve replacement values while maintaining Material color defaults.

```Kotlin
@Composable
fun ReplacementTheme(
    /* ... */
    content: @Composable () -> Unit
) {
    val replacementTypography = ReplacementTypography(
        body = TextStyle(fontSize = 16.sp),
        title = TextStyle(fontSize = 32.sp)
    )
    val replacementShapes = ReplacementShapes(
        component = RoundedCornerShape(percent = 50),
        surface = RoundedCornerShape(size = 40.dp)
    )
    CompositionLocalProvider(
        LocalReplacementTypography provides replacementTypography,
        LocalReplacementShapes provides replacementShapes
    ) {
        MaterialTheme(
            /* colors = ... */
            content = content
        )
    }
}

// Use with eg. ReplacementTheme.typography.body
object ReplacementTheme {
    val typography: ReplacementTypography
        @Composable
        get() = LocalReplacementTypography.current
    val shapes: ReplacementShapes
        @Composable
        get() = LocalReplacementShapes.current
}
```

--------------------------------

### Applying Custom Drawing Modifier to Composables

Source: https://developer.android.com/develop/ui/compose/graphics/draw/modifiers?rec=CkRodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2Uvc3lzdGVtL3Rlc3QtY3V0b3V0cxABGAkgASgBMBE6AzMuNw

Apply custom drawing modifiers to composable elements such as Text. This example shows how to use the flipped modifier created from the DrawModifier implementation.

```APIDOC
## Composable with Custom Modifier

### Description
Apply custom drawing modifiers to composable elements to transform their visual appearance.

### Composable Function
Text

### Parameters
- **text** (String) - Required - The text content to display
- **modifier** (Modifier) - Optional - Modifier chain including custom drawing modifiers

### Usage Example
```kotlin
Text(
    "Hello Compose!",
    modifier = Modifier
        .flipped()
)
```

### Modifier Chain
Custom modifiers can be chained with other modifiers using the dot notation. The flipped() modifier vertically flips the content.

### Result
The Text composable will be rendered with a vertical flip transformation applied.
```

--------------------------------

### Initialize Side Effects in RememberObserver

Source: https://developer.android.com/develop/ui/compose/state-callbacks

Demonstrates the correct way to initialize side effects within the onRemembered callback instead of the constructor. This ensures that work only starts when the composition is applied, preventing orphaned jobs and memory leaks.

```kotlin
class MyComposeObject : RememberObserver {
    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)

    init {
        // Not recommended: This will cause work to begin during composition instead of
        // with other effects. Move this into onRemembered().
        coroutineScope.launch { loadData() }
    }

    override fun onRemembered() {
        // Recommended: Move any cancellable or effect-driven work into the onRemembered
        // callback. If implementing RetainObserver, this should go in onRetained.
        coroutineScope.launch { loadData() }
    }

    private suspend fun loadData() { /* ... */ }

    // ...
}
```

--------------------------------

### Create Custom Button for Replaced Material Systems

Source: https://developer.android.com/develop/ui/compose/designsystems/custom?rec=CjpodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvcmVzb3VyY2VzEAMYCSABKAUwEzoDMy43

Explains how to wrap Material components to consume values from a replaced theme system. It highlights the use of ProvideTextStyle to ensure custom typography is applied to the component's internal content.

```kotlin
@Composable
fun ReplacementButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        shape = ReplacementTheme.shapes.component,
        onClick = onClick,
        modifier = modifier,
        content = {
            ProvideTextStyle(
                value = ReplacementTheme.typography.body
            ) {
                content()
            }
        }
    )
}

@Composable
fun ReplacementApp() {
    ReplacementTheme {
        ReplacementButton(onClick = { /* ... */ }) {
            /* ... */
        }
    }
}
```

--------------------------------

### Main Axis Alignment - justifyContent

Source: https://developer.android.com/develop/ui/compose/layouts/adaptive/flexbox/container-behavior

Distributes items along the main axis using justifyContent. Supports Start, Center, End, SpaceBetween, SpaceAround, and SpaceEvenly alignment options.

```APIDOC
## justifyContent() - Main Axis Distribution

### Description
Distributes items along the main axis and handles extra space allocation. Behavior varies based on the direction setting.

### Method
Configuration Function

### Function Signature
```
justifyContent(value: FlexJustifyContent)
```

### Parameters
#### Function Parameters
- **value** (FlexJustifyContent) - Required - The justification strategy for the main axis

### Accepted Values
- **Start** - Items aligned to the start of the main axis
- **Center** - Items centered along the main axis
- **End** - Items aligned to the end of the main axis
- **SpaceBetween** - Items distributed with equal space between them
- **SpaceAround** - Items distributed with equal space around them
- **SpaceEvenly** - Items distributed with equal space between and around them

### Request Example
```kotlin
FlexBox(
    config = {
        direction(FlexDirection.Row)
        justifyContent(FlexJustifyContent.SpaceBetween)
    }
) {
    // items distributed with space between
}
```

### Response
Applies the specified justification to items along the main axis.
```

--------------------------------

### COMPOSABLE AssistChip

Source: https://developer.android.com/develop/ui/compose/quick-guides/content/create-chip?authuser=09&hl=en

The AssistChip composable provides a way to create an assist chip that guides the user during a task. It features a leadingIcon parameter for displaying an icon on the left side of the chip.

```APIDOC
## COMPOSABLE AssistChip

### Description
Guides the user during a task, often appearing as a temporary UI element in response to user input.

### Method
COMPOSABLE

### Endpoint
androidx.compose.material3.AssistChip

### Parameters
#### Request Body
- **onClick** (Function) - Required - Callback invoked when the chip is clicked.
- **label** (Composable) - Required - The text or label to display inside the chip.
- **leadingIcon** (Composable) - Optional - Icon displayed at the start of the chip, typically using AssistChipDefaults.IconSize.

### Request Example
{
  "onClick": "{ Log.d(\"Assist chip\", \"hello world\") }",
  "label": "{ Text(\"Assist chip\") }",
  "leadingIcon": "{ Icon(Icons.Filled.Settings, ...) }"
}

### Response
#### Success Response (200)
- **UI Component** (Composable) - Renders an interactive Assist Chip element.
```

--------------------------------

### Adaptive Navigation UI Components

Source: https://developer.android.com/develop/ui/compose/navigation?rec=CldodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbWlncmF0ZS9taWdyYXRpb24tc2NlbmFyaW9zL25hdmlnYXRpb24QARgJIAEoAzAaOgMzLjc

This section describes Compose components designed for building adaptive navigation UIs, including `NavigationSuiteScaffold` for top-level navigation and `ListDetailPaneScaffold` and `SupportingPaneScaffold` for common adaptive layouts.

```APIDOC
## Adaptive Navigation UI Components

### Description
Compose offers specialized scaffolds for building adaptive layouts that respond to different screen sizes. `NavigationSuiteScaffold` provides adaptive top-level navigation, while `ListDetailPaneScaffold` and `SupportingPaneScaffold` address common adaptive layout patterns.

### Usage / API Details
#### `NavigationSuiteScaffold`
- **Purpose**: Displays appropriate top-level navigation (bottom navigation bar for compact, navigation rail for medium/expanded) based on `WindowSizeClass`.

#### `ListDetailPaneScaffold`
- **Purpose**: Designed for implementing list-detail canonical layouts.

#### `SupportingPaneScaffold`
- **Purpose**: Designed for implementing supporting pane canonical layouts.

### Code Example
(No direct code example provided in the source text for these components, but their usage is implied by their description.)
```

--------------------------------

### Create Custom Alignment Lines with LayoutModifier

Source: https://developer.android.com/develop/ui/compose/layouts/alignment-lines?rec=Ck9odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbGF5b3V0cy9pbnRyaW5zaWMtbWVhc3VyZW1lbnRzEAIYCSABKAUwHToDMy43

Demonstrates how to create a custom LayoutModifier that reads alignment lines from measurable composables. This example shows the firstBaselineToTop modifier which reads the FirstBaseline alignment line to add padding relative to the baseline position.

```APIDOC
## Custom LayoutModifier with AlignmentLine

### Description
Creates a custom modifier that reads alignment lines from a composable during the measurement phase and uses them to position the composable with padding relative to the alignment line.

### Method
Modifier Extension Function

### Function Signature
```kotlin
fun Modifier.firstBaselineToTop(firstBaselineToTop: Dp): Modifier
```

### Parameters
#### Function Parameters
- **firstBaselineToTop** (Dp) - Required - The desired distance from the top of the layout to the first baseline of the text

### Implementation Details
- Uses `layout` modifier to access the measurement phase
- Calls `measurable.measure(constraints)` to measure the composable
- Reads the `FirstBaseline` alignment line using `placeable[FirstBaseline]`
- Validates that the alignment line is specified using `check()`
- Calculates the Y position by subtracting the baseline from the desired distance
- Adjusts the total height to accommodate the padding
- Places the composable at the calculated position using `placeable.placeRelative()`

### Code Example
```kotlin
fun Modifier.firstBaselineToTop(
    firstBaselineToTop: Dp,
) = layout { measurable, constraints ->
    // Measure the composable
    val placeable = measurable.measure(constraints)

    // Check the composable has a first baseline
    check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
    val firstBaseline = placeable[FirstBaseline]

    // Height of the composable with padding - first baseline
    val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
    val height = placeable.height + placeableY
    layout(placeable.width, height) {
        // Where the composable gets placed
        placeable.placeRelative(0, placeableY)
    }
}
```

### Usage Example
```kotlin
@Preview
@Composable
private fun TextWithPaddingToBaseline() {
    MaterialTheme {
        Text("Hi there!", Modifier.firstBaselineToTop(32.dp))
    }
}
```

### Key Concepts
- **AlignmentLine**: A custom alignment line that can be used by parent layouts to align children
- **Placeable.get operator**: Used to read alignment line values after measuring (e.g., `placeable[FirstBaseline]`)
- **FirstBaseline**: Built-in alignment line exposed by BasicText and Text composables
- **LastBaseline**: Another built-in alignment line for text baselines
- **Measurement Phase**: When alignment lines are read and layout calculations occur

### Notes
- The `firstBaselineToTop` modifier shown is for educational purposes
- Jetpack Compose provides a built-in `paddingFrom` modifier for specifying padding relative to any alignment line
- Always validate that alignment lines are specified before using them
```

--------------------------------

### Migrate XML Visibility and Backgrounds to Compose

Source: https://developer.android.com/develop/ui/compose/migrate/migrate-xml-views-to-jetpack-compose

Shows the conversion of XML visibility states and background color resources into Compose conditional logic and background modifiers.

```Kotlin
// XML: android:visibility="gone"
if (visible) {
    Text("This component is only rendered if visible is true")
}

// XML: android:background="@color/white"
Modifier.background(colorResource(R.color.white))
```

--------------------------------

### Handle Interaction States with Style Parameters in Compose

Source: https://developer.android.com/develop/ui/compose/styles/state-animations

Demonstrates modifying UI properties like background and borderColor in response to interaction states. The example shows a BaseButton that changes to light purple on hover and light blue on focus. Uses the Styles API with hovered and focused state blocks to apply conditional styling.

```Kotlin
@Preview
@Composable
private fun OpenButton() {
    BaseButton(
        style = outlinedButtonStyle then {
            background(Color.White)
            hovered {
                background(lightPurple)
                border(2.dp, lightPurple)
            }
            focused {
                background(lightBlue)
            }
        },
        onClick = {  },
        content = {
            BaseText("Open in Studio", style = {
                contentColor(Color.Black)
                fontSize(26.sp)
                textAlign(TextAlign.Center)
            })
        }
    )
}
```

--------------------------------

### Complete Supporting Pane Scaffold with Navigation Logic in Kotlin

Source: https://developer.android.com/develop/ui/compose/layouts/adaptive/build-a-supporting-pane-layout

A full implementation showing how to manage pane visibility using scaffoldNavigator.scaffoldValue, trigger navigation via coroutine scopes, and implement custom back navigation behavior.

```Kotlin
val scaffoldNavigator = rememberSupportingPaneScaffoldNavigator()
val scope = rememberCoroutineScope()
val backNavigationBehavior = BackNavigationBehavior.PopUntilScaffoldValueChange

NavigableSupportingPaneScaffold(
    navigator = scaffoldNavigator,
    mainPane = {
        AnimatedPane(
            modifier = Modifier
                .safeContentPadding()
                .background(Color.Red)
        ) {
            if (scaffoldNavigator.scaffoldValue[SupportingPaneScaffoldRole.Supporting] == PaneAdaptedValue.Hidden) {
                Button(
                    modifier = Modifier
                        .wrapContentSize(),
                    onClick = {
                        scope.launch {
                            scaffoldNavigator.navigateTo(SupportingPaneScaffoldRole.Supporting)
                        }
                    }
                ) {
                    Text("Show supporting pane")
                }
            } else {
                Text("Supporting pane is shown")
            }
        }
    },
    supportingPane = {
        AnimatedPane(modifier = Modifier.safeContentPadding()) {
            Column {
                // Allow users to dismiss the supporting pane. Use back navigation to
                // hide an expanded supporting pane.
                if (scaffoldNavigator.scaffoldValue[SupportingPaneScaffoldRole.Supporting] == PaneAdaptedValue.Expanded) {
                    // Material design principles promote the usage of a right-aligned
                    // close (X) button.
                    IconButton(
                        modifier =  Modifier.align(Alignment.End).padding(16.dp),
                        onClick = {
                            scope.launch {
                                scaffoldNavigator.navigateBack(backNavigationBehavior)
                            }
                        }
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }
                Text("Supporting pane")
            }

        }
    }
)
```

--------------------------------

### Non-optimal State Read in Composition

Source: https://developer.android.com/develop/ui/compose/phases

An example of a suboptimal implementation where scroll state is read during composition. This causes the entire Box and its children to recompose, measure, and layout on every scroll event.

```Kotlin
Box {
    val listState = rememberLazyListState()

    Image(
        // ...
        // Non-optimal implementation!
        Modifier.offset(
            with(LocalDensity.current) {
                // State read of firstVisibleItemScrollOffset in composition
                (listState.firstVisibleItemScrollOffset / 2).toDp()
            }
        )
    )

    LazyColumn(state = listState) {
        // ...
    }
}
```

--------------------------------

### Migrate Buttons, Icon Buttons, and FABs from M2 to M3 in Compose

Source: https://developer.android.com/develop/ui/compose/designsystems/material2-material3

This example shows the migration of various button types, including standard buttons, icon buttons, and Floating Action Buttons (FABs), from Material Design 2 to Material Design 3. While the composable names remain largely the same, the import packages change from androidx.compose.material to androidx.compose.material3.

```kotlin
import androidx.compose.material.Button
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.IconButton
import androidx.compose.material.IconToggleButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.TextButton

// M2 buttons
Button(…)
OutlinedButton(…)
TextButton(…)
// M2 icon buttons
IconButton(…)
IconToggleButton(…)
// M2 FABs
FloatingActionButton(…)
ExtendedFloatingActionButton(…)
```

```kotlin
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TextButton

// M3 buttons
Button(…)
OutlinedButton(…)
TextButton(…)
// M3 icon buttons
IconButton(…)
IconToggleButton(…)
// M3 FABs
FloatingActionButton(…)
ExtendedFloatingActionButton(…)
```

--------------------------------

### Managing lifecycle events with DisposableEffect

Source: https://developer.android.com/develop/ui/compose/side-effects?rec=CjdodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2Uva290bGluEAEYCSABKAMwCDoDMy43

This example illustrates how to use `DisposableEffect` to register and unregister a `LifecycleEventObserver`. It ensures that the observer is properly added when the composable enters the composition and removed when it leaves or when the `lifecycleOwner` key changes, preventing resource leaks.

```kotlin
@Composable
fun HomeScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onStart: () -> Unit, // Send the 'started' analytics event
    onStop: () -> Unit // Send the 'stopped' analytics event
) {
    // Safely update the current lambdas when a new one is provided
    val currentOnStart by rememberUpdatedState(onStart)
    val currentOnStop by rememberUpdatedState(onStop)

    // If `lifecycleOwner` changes, dispose and reset the effect
    DisposableEffect(lifecycleOwner) {
        // Create an observer that triggers our remembered callbacks
        // for sending analytics events
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                currentOnStart()
            } else if (event == Lifecycle.Event.ON_STOP) {
                currentOnStop()
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    /* Home screen content */
}
```

--------------------------------

### Basic TextField Migration from Value-Based to State-Based

Source: https://developer.android.com/develop/ui/compose/text/migrate-state-based

Demonstrates the simplest migration pattern from value-based to state-based TextField. The old approach uses mutableStateOf and onValueChange callbacks, while the new approach uses rememberTextFieldState() and replaces singleLine with TextFieldLineLimits.SingleLine. This is the foundational pattern for all TextField migrations.

```Kotlin
@Composable
fun OldSimpleTextField() {
    var state by rememberSaveable { mutableStateOf("") }
    TextField(
        value = state,
        onValueChange = { state = it },
        singleLine = true,
    )
}
```

```Kotlin
@Composable
fun NewSimpleTextField() {
    TextField(
        state = rememberTextFieldState(),
        lineLimits = TextFieldLineLimits.SingleLine
    )
}
```

--------------------------------

### Get External Display Information (Kotlin)

Source: https://developer.android.com/develop/ui/compose/layouts/adaptive/support-connected-displays

This snippet demonstrates how to use the DisplayManager system service to retrieve a list of all connected displays. It then filters this list to identify and isolate external displays by excluding the Display.DEFAULT_DISPLAY, which represents the device's built-in screen.

```kotlin
val displayManager = getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
val displays = displayManager.getDisplays()
// The default display is 0. External displays have other IDs.
val externalDisplays = displays.filter { it.displayId != Display.DEFAULT_DISPLAY }
```

--------------------------------

### POST /layout/barriers

Source: https://developer.android.com/develop/ui/compose/layouts/constraintlayout?rec=CjRodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYm9tEAIYCSABKAMwJjoDMy43

Creates a virtual guideline (barrier) based on the most extreme widget among a set of provided references on a specified side.

```APIDOC
## POST /layout/barriers

### Description
Creates a virtual guideline based on the most extreme widget on the specified side among multiple composable references.

### Method
POST

### Endpoint
/layout/barriers

### Parameters
#### Request Body
- **side** (String) - Required - The side of the barrier (top, bottom, end, start).
- **references** (Array<String>) - Required - The list of composable reference IDs that make up the barrier.

### Request Example
{
  "side": "top",
  "references": ["button", "text"]
}

### Response
#### Success Response (200)
- **barrier** (Reference) - A reference to the created barrier for use in constraints.

#### Response Example
{
  "barrierId": "topBarrier",
  "elementsCount": 2
}
```

--------------------------------

### fillMaxColumnWidth() Modifier Usage

Source: https://developer.android.com/develop/ui/compose/layouts/flow?rec=CkJodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdG91Y2gtaW5wdXQvZm9jdXMQARgJIAEoBDALOgMzLjc

Demonstrates how to use Modifier.fillMaxColumnWidth() within a FlowColumn to ensure all items in the same column have uniform width. This example displays a list of Android desserts with consistent column widths.

```APIDOC
## Modifier.fillMaxColumnWidth()

### Description
Applies uniform width to all items within the same column of a FlowColumn layout. Items will expand to match the width of the widest item in their column.

### Usage Context
Used within FlowColumn or similar flow-based layouts to standardize item dimensions.

### Parameters
None - this is a modifier extension function with no parameters.

### Example Implementation
```kotlin
FlowColumn(
    Modifier
        .padding(20.dp)
        .fillMaxHeight()
        .fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp),
    maxItemsInEachColumn = 5,
) {
    repeat(listDesserts.size) {
        Box(
            Modifier
                .fillMaxColumnWidth()
                .border(1.dp, Color.DarkGray, RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            Text(
                text = listDesserts[it],
                fontSize = 18.sp,
                modifier = Modifier.padding(3.dp)
            )
        }
    }
}
```

### Behavior
- When applied: All items in the same column expand to match the width of the widest item
- When not applied: Items maintain their natural width and may wrap differently
- Works in conjunction with FlowColumn's maxItemsInEachColumn parameter

### Related Modifiers
- `Modifier.fillMaxRowHeight()` - Equivalent modifier for FlowRow layouts
- `Modifier.fillMaxWidth()` - Fills available width in parent
- `Modifier.fillMaxHeight()` - Fills available height in parent
```

--------------------------------

### Wrap NavHost in a Testable AppNavHost Composable

Source: https://developer.android.com/develop/ui/compose/navigation

This composable 'AppNavHost' wraps the application's 'NavHost', accepting a 'NavHostController' as a parameter. This pattern allows the 'NavHost' to be easily injected and tested in isolation by providing a mock or test controller.

```Kotlin
@Composable
fun AppNavHost(navController: NavHostController){
  NavHost(navController = navController){ ... }
}
```

--------------------------------

### Preview Composable Using FirstBaselineToTop Modifier in Kotlin

Source: https://developer.android.com/develop/ui/compose/layouts/alignment-lines

A preview composable that demonstrates the usage of the firstBaselineToTop modifier on a Text composable. It wraps the Text in MaterialTheme and applies the custom modifier with a 32.dp padding from the first baseline. This example shows how to apply custom alignment line modifiers in practice.

```Kotlin
@Preview
@Composable
private fun TextWithPaddingToBaseline() {
    MaterialTheme {
        Text("Hi there!", Modifier.firstBaselineToTop(32.dp))
    }
}
```

--------------------------------

### Custom Design System Approaches

Source: https://developer.android.com/develop/ui/compose/designsystems/custom

Overview of three main approaches for implementing custom design systems in Jetpack Compose, from extending Material Theming to implementing fully custom systems.

```APIDOC
## Custom Design System Approaches

### Description
Three main strategies for creating custom design systems in Jetpack Compose, each with different levels of customization and Material integration.

### Approach 1: Extend MaterialTheme
**Use Case**: Adding custom theming values while keeping Material as foundation

**Method**:
- Add extension properties to ColorScheme, Typography, and Shapes
- Maintain full Material component compatibility
- Simple implementation with minimal code

**Example**:
```kotlin
val ColorScheme.customColor: Color
    @Composable
    get() = /* custom color logic */
```

### Approach 2: Replace Material Subsystems
**Use Case**: Customizing specific systems while keeping others

**Replaceable Systems**:
- **Colors** - Custom ColorScheme implementation
- **Typography** - Custom TextStyle definitions
- **Shapes** - Custom Shape implementations

**Method**:
- Create custom implementations for selected systems
- Keep other Material systems unchanged
- Use with Material components

**Considerations**:
- Ensure custom implementations follow Material guidelines
- Test compatibility with Material components
- Maintain consistency across replaced systems

### Approach 3: Implement Fully Custom Design System
**Use Case**: Complete design system replacement

**Method**:
- Replace MaterialTheme entirely
- Create custom theme composable
- Implement all theming systems from scratch
- Build custom components if needed

**Considerations**:
- Highest flexibility and customization
- Requires more implementation work
- Can still use Material components with custom theme
- Need to handle all theming aspects

### Using Material Components with Custom Design Systems
**Important Considerations**:
- Material components expect Material theme structure
- Ensure custom theme provides required values
- Test component behavior with custom theme
- May need to wrap or adapt components
- Maintain accessibility standards

### Related Resources
- Material Design 3 in Compose
- Migrate from Material 2 to Material 3 in Compose
- Anatomy of a theme in Compose
```

--------------------------------

### Create Zero-Parameter Custom Modifier with Fixed Padding in Compose

Source: https://developer.android.com/develop/ui/compose/custom-modifiers

This example shows how to implement a custom modifier with no parameters using `Modifier.Node`. It applies a fixed amount of padding to a composable and does not require a data class for the element as it never needs to update.

```kotlin
fun Modifier.fixedPadding() = this then FixedPaddingElement

data object FixedPaddingElement : ModifierNodeElement<FixedPaddingNode>() {
    override fun create() = FixedPaddingNode()
    override fun update(node: FixedPaddingNode) {}
}

class FixedPaddingNode : LayoutModifierNode, Modifier.Node() {
    private val PADDING = 16.dp

    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        val paddingPx = PADDING.roundToPx()
        val horizontal = paddingPx * 2
        val vertical = paddingPx * 2

        val placeable = measurable.measure(constraints.offset(-horizontal, -vertical))

        val width = constraints.constrainWidth(placeable.width + horizontal)
        val height = constraints.constrainHeight(placeable.height + vertical)
        return layout(width, height) {
            placeable.place(paddingPx, paddingPx)
        }
    }
}
```

--------------------------------

### Compose Stability Configuration File

Source: https://developer.android.com/develop/ui/compose/performance/stability/fix

Plain text configuration file specifying classes to consider stable at compile time. Supports comments, single wildcards (*), and double wildcards (**) for package matching. Each class should be on a separate row.

```Text
// Consider LocalDateTime stable
java.time.LocalDateTime
// Consider my datalayer stable
com.datalayer.*
// Consider my datalayer and all submodules stable
com.datalayer.**
// Consider my generic type stable based off it's first type parameter only
com.example.GenericClass<*,_>
```

--------------------------------

### pointerInput with Animations - Advanced Coroutine Usage

Source: https://developer.android.com/develop/ui/compose/kotlin?rec=Cj1odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2Uvc2lkZS1lZmZlY3RzEAIYCSABKAQwGzoDMy43

Demonstrates combining the pointerInput modifier with animation APIs using coroutines. This advanced example shows how to handle user tap events and animate an element's position in response, using nested coroutine scopes and suspend functions.

```APIDOC
## pointerInput with Animations - Advanced Coroutine Usage

### Description
Combines pointerInput modifier with animation APIs to create interactive animations that respond to user input. Demonstrates nested coroutine scopes and handling pointer events asynchronously.

### Function Signature
```kotlin
@Composable
fun MoveBoxWhereTapped()
```

### Key Components
- **Animatable**: Animates Offset values and is remembered across recompositions
- **pointerInput modifier**: Accepts a suspend block for handling pointer events
- **coroutineScope**: Creates a new scope within a suspend function for launching coroutines
- **awaitPointerEventScope**: Suspend function for waiting on pointer events
- **awaitFirstDown()**: Waits for user tap and returns position

### Code Example
```kotlin
@Composable
fun MoveBoxWhereTapped() {
    val animatedOffset = remember {
        Animatable(Offset(0f, 0f), Offset.VectorConverter)
    }

    Box(
        Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                coroutineScope {
                    while (true) {
                        awaitPointerEventScope {
                            val offset = awaitFirstDown().position
                            launch {
                                animatedOffset.animateTo(offset)
                            }
                        }
                    }
                }
            }
    ) {
        Text("Tap anywhere", Modifier.align(Alignment.Center))
        Box(
            Modifier
                .offset {
                    IntOffset(
                        animatedOffset.value.x.roundToInt(),
                        animatedOffset.value.y.roundToInt()
                    )
                }
                .size(40.dp)
                .background(Color(0xff3c1361), CircleShape)
        )
    }
}
```

### Key Points
- pointerInput modifier takes a suspend block
- coroutineScope creates a new scope within suspend functions
- awaitPointerEventScope waits for pointer events
- launch creates new coroutines for animations
- Animatable handles smooth position transitions
- Infinite loop continues listening for taps
```

--------------------------------

### Tween Animation Specification

Source: https://developer.android.com/develop/ui/compose/animation/customize?rec=CkJodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYW5pbWF0aW9uL3Rlc3RpbmcQAxgJIAEoBjAqOgMzLjc

Create duration-based animations between start and end values using an easing curve. Tween animations allow you to specify duration, delay, and custom easing functions.

```APIDOC
## Tween Animation

### Description
The `tween` AnimationSpec animates between start and end values over a specified duration using an easing curve. The name "tween" comes from the concept of tweening - animating between two values.

### Method
tween(durationMillis, delayMillis, easing)

### Parameters
#### Configuration Parameters
- **durationMillis** (Int) - Required - Duration of the animation in milliseconds
- **delayMillis** (Int) - Optional - Delay before animation starts in milliseconds. Default: 0
- **easing** (Easing) - Optional - Easing function to apply to the animation. Default: LinearEasing

### Easing Functions
- **LinearEasing** - Constant speed throughout animation
- **FastOutSlowInEasing** - Starts fast, ends slow
- **LinearOutSlowInEasing** - Linear start, slow end
- **FastOutLinearInEasing** - Fast start, linear end
- **CubicBezierEasing** - Custom cubic bezier curve

### Request Example
```kotlin
val value by animateFloatAsState(
    targetValue = 1f,
    animationSpec = tween(
        durationMillis = 300,
        delayMillis = 50,
        easing = LinearOutSlowInEasing
    ),
    label = "tween delay"
)
```

### Use Cases
- Predictable, time-based animations
- Animations with specific duration requirements
- Animations with delayed start
- Custom easing curves for specific visual effects
```

--------------------------------

### Determine Window Size Class with Material 3 Adaptive Library

Source: https://developer.android.com/develop/ui/compose/layouts/adaptive/foldables/trifolds-and-landscape-foldables

This snippet demonstrates how to use the Material 3 adaptive library to retrieve window information and handle different window size classes such as Expanded, Medium, and Compact.

```Kotlin
val adaptiveInfo = currentWindowAdaptiveInfo()
val windowSizeClass = adaptiveInfo.windowSizeClass

when {
  windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_EXPANDED_LOWER_BOUND) -> // Expanded
  windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND) -> // Medium
  else -> // Compact
}
```

--------------------------------

### GET /runtime/rememberCoroutineScope

Source: https://developer.android.com/develop/ui/compose/kotlin?rec=Cj9odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvbGF5b3V0cy9iYXNpY3MQAhgJIAEoBTAdOgMzLjc

Returns a CoroutineScope bound to the composable's lifecycle. This scope is used to launch coroutines in event handlers and call suspend APIs safely within the UI layer.

```APIDOC
## GET /runtime/rememberCoroutineScope

### Description
Returns a CoroutineScope bound to the point in the composition where it's called. The scope will be cancelled when the call leaves the composition.

### Method
GET

### Endpoint
/runtime/rememberCoroutineScope

### Parameters
#### Request Body
- **None**

### Request Example
{
  "action": "rememberCoroutineScope()"
}

### Response
#### Success Response (200)
- **scope** (CoroutineScope) - A lifecycle-aware scope for launching coroutines.

#### Response Example
{
  "type": "CoroutineScope",
  "lifecycle": "Composable"
}
```

--------------------------------

### GET painterResource

Source: https://developer.android.com/develop/ui/compose/graphics/images/loading?rec=Ck5odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZ3JhcGhpY3MvaW1hZ2VzL2N1c3RvbXBhaW50ZXIQAxgJIAEoAjAaOgMzLjc

Loads a graphic resource from the disk, supporting formats like PNG, JPEG, WEBP, and Vector resources. It is used within the Image composable to render local assets.

```APIDOC
## GET painterResource

### Description
Loads a drawable resource or vector resource from the application's disk resources using a resource ID.

### Method
GET

### Endpoint
painterResource(id: Int)

### Parameters
#### Path Parameters
- **id** (Int) - Required - The resource ID of the drawable (e.g., R.drawable.image_name).

### Request Example
Image(
    painter = painterResource(id = R.drawable.dog),
    contentDescription = stringResource(id = R.string.dog_content_description)
)

### Response
#### Success Response (200)
- **Painter** (Object) - Returns a Painter object that can be used by the Image composable to draw the resource on screen.
```

--------------------------------

### Create CompositionLocal Providers for Custom Theme Systems

Source: https://developer.android.com/develop/ui/compose/designsystems/custom?rec=CjpodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvcmVzb3VyY2VzEAMYCSABKAYwGToDMy43

Establishes CompositionLocal instances for custom Typography and Shapes using staticCompositionLocalOf. Provides default values with TextStyle.Default and RoundedCornerShape with zero corner sizes as fallbacks.

```Kotlin
val LocalReplacementTypography = staticCompositionLocalOf {
    ReplacementTypography(
        body = TextStyle.Default,
        title = TextStyle.Default
    )
}
val LocalReplacementShapes = staticCompositionLocalOf {
    ReplacementShapes(
        component = RoundedCornerShape(ZeroCornerSize),
        surface = RoundedCornerShape(ZeroCornerSize)
    )
}
```

--------------------------------

### Scaffold and Navigation Drawer Integration (M2 vs M3)

Source: https://developer.android.com/develop/ui/compose/designsystems/material2-material3?rec=CkVodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZGVzaWduc3lzdGVtcy9jdXN0b20QAhgJIAEoBzAKOgMzLjc

Details the removal of `drawer*` parameters from M3 `Scaffold` and the new approach using `ModalNavigationDrawer` for implementing navigation drawers.

```APIDOC
## Scaffold and Navigation Drawer Integration

### Description
All `drawer*` parameters (e.g., `drawerShape`, `drawerContent`) have been removed from the Material 3 `Scaffold`. To implement a navigation drawer in M3, `ModalNavigationDrawer` should be used as a separate composable, typically wrapping the `Scaffold`.

### Material 2 Usage
```kotlin
import androidx.compose.material.DrawerValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope

val scaffoldState = rememberScaffoldState(
    drawerState = rememberDrawerState(DrawerValue.Closed)
)
val scope = rememberCoroutineScope()

Scaffold(
    scaffoldState = scaffoldState,
    drawerContent = { … },
    drawerGesturesEnabled = …,
    drawerShape = …,
    drawerElevation = …,
    drawerBackgroundColor = …,
    drawerContentColor = …,
    drawerScrimColor = …,
    content = {
        …
        scope.launch {
            scaffoldState.drawerState.open()
        }
    }
)
```

### Material 3 Usage
```kotlin
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope

val drawerState = rememberDrawerState(DrawerValue.Closed)
val scope = rememberCoroutineScope()

ModalNavigationDrawer(
    drawerState = drawerState,
    drawerContent = {
        ModalDrawerSheet(
            drawerShape = …,
            drawerTonalElevation = …,
            drawerContainerColor = …,
            drawerContentColor = …,
            content = { … }
        )
    },
    gesturesEnabled = …,
    scrimColor = …,
    content = {
        Scaffold(
            content = {
                …
                scope.launch {
                    drawerState.open()
                }
            }
        )
    }
)
```
```

--------------------------------

### Not Recommended: Public RememberObserver Implementation in Kotlin

Source: https://developer.android.com/develop/ui/compose/state-callbacks

This Kotlin example illustrates an anti-pattern where `RememberObserver` is exposed publicly. Directly exposing constructors or factory functions for `RememberObserver` implementations can lead to unexpected behavior and multiple invocations if the object is remembered incorrectly.

```kotlin
abstract class MyManager

// Not Recommended: Exposing a public constructor (even implicitly) for an object implementing
// RememberObserver can cause unexpected invocations if it is remembered multiple times.
class MyComposeManager : MyManager(), RememberObserver { /* ... */ }

// Not Recommended: The return type may be an implementation of RememberObserver and should be
// remembered explicitly.
fun createFoo(): MyManager = MyComposeManager()
```

--------------------------------

### Implement GlanceAppWidget and provideContent in Kotlin

Source: https://developer.android.com/develop/ui/compose/glance/create-app-widget

Defines the widget's content and data loading logic. The provideGlance method is used to load data (ideally off the main thread) and provide the UI via the provideContent block.

```kotlin
class MyAppWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {

        // In this method, load data needed to render the AppWidget.
        // Use `withContext` to switch to another thread for long running
        // operations.

        provideContent {
            // create your AppWidget here
            Text("Hello World")
        }
    }
}
```

--------------------------------

### Encapsulate and reuse transition animations in Jetpack Compose

Source: https://developer.android.com/develop/ui/compose/animation/value-based?rec=CkRodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvYW5pbWF0aW9uL2N1c3RvbWl6ZRABGAkgASgCMBg6AzMuNw

This example demonstrates how to encapsulate transition logic into a reusable class and function. It separates animation implementation from the UI, making complex animations manageable and shareable. The `AnimatingBox` composable takes a `BoxState` and uses `updateTransitionData` to manage its animated properties (color and size). Dependencies include Compose UI and Animation libraries.

```kotlin
enum class BoxState { Collapsed, Expanded }

@Composable
fun AnimatingBox(boxState: BoxState) {
    val transitionData = updateTransitionData(boxState)
    // UI tree
    Box(
        modifier = Modifier
            .background(transitionData.color)
            .size(transitionData.size)
    )
}

// Holds the animation values.
private class TransitionData(
    color: State<Color>,
    size: State<Dp>
) {
    val color by color
    val size by size
}

// Create a Transition and return its animation values.
@Composable
private fun updateTransitionData(boxState: BoxState): TransitionData {
    val transition = updateTransition(boxState, label = "box state")
    val color = transition.animateColor(label = "color") { state ->
        when (state) {
            BoxState.Collapsed -> Color.Gray
            BoxState.Expanded -> Color.Red
        }
    }
    val size = transition.animateDp(label = "size") { state ->
        when (state) {
            BoxState.Collapsed -> 64.dp
            BoxState.Expanded -> 128.dp
        }
    }
    return remember(transition) { TransitionData(color, size) }
}
AnimationSnippets.kt
```

--------------------------------

### Define a Plain State Holder Class in Kotlin

Source: https://developer.android.com/develop/ui/compose/state-hoisting

An example of a plain state holder class, LazyListState, which manages the scroll position and provides methods to manipulate the UI state of a LazyColumn or LazyRow.

```kotlin
@Stable
class LazyListState constructor(
    firstVisibleItemIndex: Int = 0,
    firstVisibleItemScrollOffset: Int = 0
) : ScrollableState {
    /**
     *   The holder class for the current scroll position.
     */
    private val scrollPosition = LazyListScrollPosition(
        firstVisibleItemIndex, firstVisibleItemScrollOffset
    )

    suspend fun scrollToItem(/*...*/) { /*...*/ }

    override suspend fun scroll() { /*...*/ }

    suspend fun animateScrollToItem() { /*...*/ }
}
```

--------------------------------

### Rotate Drawing Operations in Compose Canvas

Source: https://developer.android.com/develop/ui/compose/graphics/draw/overview

Rotates drawing operations around a pivot point. This example rotates a gray rectangle 45 degrees, positioned at one-third of the canvas width and height.

```Kotlin
Canvas(modifier = Modifier.fillMaxSize()) {
    rotate(degrees = 45F) {
        drawRect(
            color = Color.Gray,
            topLeft = Offset(x = size.width / 3F, y = size.height / 3F),
            size = size / 3F
        )
    }
}
```

--------------------------------

### DrawModifier Interface Implementation

Source: https://developer.android.com/develop/ui/compose/graphics/draw/modifiers?rec=Cj9odHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2Uvc3lzdGVtL2N1dG91dHMQARgJIAEoATAROgMzLjc

Demonstrates how to implement the DrawModifier interface to create custom drawing modifiers. This example shows a FlippedModifier that vertically flips content using scale transformations within the ContentDrawScope.

```APIDOC
## DrawModifier Interface

### Description
Implement the DrawModifier interface to create custom drawing modifiers that provide access to ContentDrawScope for custom drawing operations.

### Interface
DrawModifier

### Key Components
- **ContentDrawScope** - Provides drawing capabilities and access to the content
- **draw()** - Override this function to implement custom drawing logic

### Implementation Example
```kotlin
class FlippedModifier : DrawModifier {
    override fun ContentDrawScope.draw() {
        scale(1f, -1f) {
            this@draw.drawContent()
        }
    }
}

fun Modifier.flipped() = this.then(FlippedModifier())
```

### Usage
Apply the custom modifier to any composable element:
```kotlin
Text(
    "Hello Compose!",
    modifier = Modifier
        .flipped()
)
```

### Parameters
#### ContentDrawScope Methods
- **drawContent()** - Draws the content of the composable
- **scale(scaleX, scaleY, block)** - Applies scaling transformation

### Response
The custom modifier transforms the visual rendering of the composable element according to the implemented drawing logic.
```

--------------------------------

### Apply Fractional Sizing in FlowRow using Jetpack Compose

Source: https://developer.android.com/develop/ui/compose/layouts/flow?rec=CkJodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdG91Y2gtaW5wdXQvZm9jdXMQARgJIAEoAjAJOgMzLjc

Demonstrates how Modifier.fillMaxWidth(fraction) calculates width based on the entire container size in a FlowRow, unlike standard Rows where it uses remaining space. This example includes a mix of fixed width, fractional width, and weighted items.

```Kotlin
FlowRow(\n    modifier = Modifier.padding(4.dp),\n    horizontalArrangement = Arrangement.spacedBy(4.dp),\n    maxItemsInEachRow = 3\n) {\n    val itemModifier = Modifier\n        .clip(RoundedCornerShape(8.dp))\n    Box(\n        modifier = itemModifier\n            .height(200.dp)\n            .width(60.dp)\n            .background(Color.Red)\n    )\n    Box(\n        modifier = itemModifier\n            .height(200.dp)\n            .fillMaxWidth(0.7f)\n            .background(Color.Blue)\n    )\n    Box(\n        modifier = itemModifier\n            .height(200.dp)\n            .weight(1f)\n            .background(Color.Magenta)\n    )\n}
```

--------------------------------

### Configure Multi-Instance Support in Manifest

Source: https://developer.android.com/develop/ui/compose/layouts/adaptive/support-multi-window-mode?hl=it

Enable an activity to be resized and support multiple instances by setting attributes in the AndroidManifest.xml.

```xml
<activity
    android:name=".MyActivity"
    android:resizeableActivity="true">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity>
```

--------------------------------

### Calculate Intercept Bounds for Drawing Offsets

Source: https://developer.android.com/develop/ui/compose/touch-input/user-interactions/handling-interactions

Determines the start and end coordinate offsets for a drawing operation based on width and height bounds. It calculates the intercept point relative to the center of the component.

```kotlin
// Figure out which intercept lies within bounds
val intercept = if (abs(y) <= abs(height)) {
    Offset(width, y)
} else {
    Offset(x, height)
}

// Convert back to offsets from 0,0
val start = intercept + size.center
val end = Offset(size.width - start.x, size.height - start.y)
return start to end
```

--------------------------------

### Implement a Small Top App Bar in Jetpack Compose

Source: https://developer.android.com/develop/ui/compose/quick-guides/content/display-app-bar?hl=en

Demonstrates how to create a basic top app bar using the TopAppBar composable. It sets custom container and title colors and is integrated within a Scaffold layout.

```Kotlin
@Composable
fun SmallTopAppBarExample() {
	Scaffold(
		topBar = {
			TopAppBar(
				colors = TopAppBarDefaults.topAppBarColors(
					containerColor = MaterialTheme.colorScheme.primaryContainer,
					titleContentColor = MaterialTheme.colorScheme.primary,
				),
				title = {
					Text("Small Top App Bar")
				}
			)
		},
	) { innerPadding ->
		ScrollContent(innerPadding)
	}
}
```

--------------------------------

### Cross Axis Alignment - alignItems

Source: https://developer.android.com/develop/ui/compose/layouts/adaptive/flexbox/container-behavior

Aligns items along the cross axis within a single line using alignItems. Supports Start, End, Center, Stretch, and Baseline alignment options.

```APIDOC
## alignItems() - Cross Axis Alignment

### Description
Aligns items along the cross axis within a single line. Individual items can override this behavior using the alignSelf modifier.

### Method
Configuration Function

### Function Signature
```
alignItems(value: FlexAlignItems)
```

### Parameters
#### Function Parameters
- **value** (FlexAlignItems) - Required - The alignment strategy for the cross axis

### Accepted Values
- **Start** - Items aligned to the start of the cross axis
- **End** - Items aligned to the end of the cross axis
- **Center** - Items centered along the cross axis
- **Stretch** - Items stretched to fill the cross axis
- **Baseline** - Items aligned to their baseline

### Request Example
```kotlin
FlexBox(
    config = {
        direction(FlexDirection.Row)
        alignItems(FlexAlignItems.Center)
    }
) {
    // items centered on cross axis
}
```

### Response
Applies the specified alignment to items along the cross axis. Can be overridden per-item using alignSelf modifier.
```

--------------------------------

### Synchronize multiple properties with updateTransition

Source: https://developer.android.com/develop/ui/compose/animation/quick-guide

This example uses the updateTransition API to drive multiple property animations (Rect and Dp) from a single state change. This approach is ideal for managing complex transitions where multiple UI elements must animate in sync based on a shared state.

```kotlin
var currentState by remember { mutableStateOf(BoxState.Collapsed) }
val transition = updateTransition(currentState, label = "transition")

val rect by transition.animateRect(label = "rect") { state ->
    when (state) {
        BoxState.Collapsed -> Rect(0f, 0f, 100f, 100f)
        BoxState.Expanded -> Rect(100f, 100f, 300f, 300f)
    }
}
val borderWidth by transition.animateDp(label = "borderWidth") { state ->
    when (state) {
        BoxState.Collapsed -> 1.dp
        BoxState.Expanded -> 0.dp
    }
}
```

--------------------------------

### Implement Shared Element Transitions with NavHost

Source: https://developer.android.com/develop/ui/compose/animation/shared-elements/navigation

This snippet shows a complete implementation of shared element transitions between a list screen and a detail screen. It utilizes SharedTransitionLayout to wrap the NavHost and passes the required scopes to child composables to synchronize animations.

```kotlin
@Preview
@Composable
fun SharedElement_PredictiveBack() {
    SharedTransitionLayout {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = "home"
        ) {
            composable("home") {
                HomeScreen(
                    this@SharedTransitionLayout,
                    this@composable,
                    { navController.navigate("details/$it") }
                )
            }
            composable(
                "details/{item}",
                arguments = listOf(navArgument("item") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("item")
                val snack = listSnacks[id!!]
                DetailsScreen(
                    id,
                    snack,
                    this@SharedTransitionLayout,
                    this@composable,
                    {
                        navController.navigate("home")
                    }
                )
            }
        }
    }
}

@Composable
fun DetailsScreen(
    id: Int,
    snack: Snack,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onBackPressed: () -> Unit
) {
    with(sharedTransitionScope) {
        Column(
            Modifier
                .fillMaxSize()
                .clickable {
                    onBackPressed()
                }
        ) {
            Image(
                painterResource(id = snack.image),
                contentDescription = snack.description,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .sharedElement(
                        sharedTransitionScope.rememberSharedContentState(key = "image-$id"),
                        animatedVisibilityScope = animatedContentScope
                    )
                    .aspectRatio(1f)
                    .fillMaxWidth()
            )
            Text(
                snack.name, fontSize = 18.sp,
                modifier =
                Modifier
                    .sharedElement(
                        sharedTransitionScope.rememberSharedContentState(key = "text-$id"),
                        animatedVisibilityScope = animatedContentScope
                    )
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun HomeScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onItemClick: (Int) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(listSnacks) { index, item ->
            Row(
                Modifier.clickable {
                    onItemClick(index)
                }
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                with(sharedTransitionScope) {
                    Image(
                        painterResource(id = item.image),
                        contentDescription = item.description,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .sharedElement(
                                sharedTransitionScope.rememberSharedContentState(key = "image-$index"),
                                animatedVisibilityScope = animatedContentScope
                            )
                            .size(100.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        item.name, fontSize = 18.sp,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .sharedElement(
                                sharedTransitionScope.rememberSharedContentState(key = "text-$index"),
                                animatedVisibilityScope = animatedContentScope,
                            )
                    )
                }
            }
        }
    }
}

data class Snack(
    val name: String,
    val description: String,
    @DrawableRes val image: Int
)
```

--------------------------------

### Collect WindowLayoutInfo using RxJava Observables

Source: https://developer.android.com/develop/ui/compose/layouts/adaptive/foldables/make-your-app-fold-aware

Shows how to utilize the androidx.window:window-rxjava2 or rxjava3 artifacts to observe window layout changes as an RxJava Observable. The snippet covers creating the observable, subscribing on the main thread, and disposing of the subscription during the activity lifecycle.

```kotlin
class RxActivity: AppCompatActivity {

    private lateinit var binding: ActivityRxBinding

    private var disposable: Disposable? = null
    private lateinit var observable: Observable<WindowLayoutInfo>

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);

       binding = ActivitySplitLayoutBinding.inflate(getLayoutInflater());
       setContentView(binding.getRoot());

        // Create a new observable.
        observable = WindowInfoTracker.getOrCreate(this@RxActivity)
            .windowLayoutInfoObservable(this@RxActivity)
   }

   @Override
   protected void onStart() {
       super.onStart();

        // Subscribe to receive WindowLayoutInfo updates.
        disposable?.dispose()
        disposable = observable
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { newLayoutInfo ->
            // Use newLayoutInfo to update the layout.
        }
   }

   @Override
   protected void onStop() {
       super.onStop();

        // Dispose of the WindowLayoutInfo observable.
        disposable?.dispose()
   }
}
```

--------------------------------

### Example of an Unstable Class in Compose

Source: https://developer.android.com/develop/ui/compose/performance/stability/fix

This snippet illustrates a class that the Compose compiler would consider unstable, specifically due to the use of a mutable `Set` for its `tags` property, which prevents the compiler from guaranteeing immutability.

```kotlin
unstable class Snack {
  …
  unstable val tags: Set<String>
  …
}
```

--------------------------------

### Hide and Show System Bars using WindowInsetsControllerCompat

Source: https://developer.android.com/develop/ui/compose/system/setup-e2e

Use WindowInsetsControllerCompat to hide or show system bars for immersive content experiences such as videos or maps. The hide() method conceals system bars while show() reveals them.

```Kotlin
val windowInsetsController =
    WindowCompat.getInsetsController(window, window.decorView)

// Hide the system bars.
windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())

// Show the system bars.
windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
```

--------------------------------

### Implement TopAppBar scroll behavior in M2 and M3 Compose

Source: https://developer.android.com/develop/ui/compose/designsystems/material2-material3

This example illustrates how to handle scroll behavior for TopAppBar in both Material Design 2 and Material Design 3. M2 requires manual elevation changes based on scroll state, while M3 introduces the scrollBehavior parameter with TopAppBarDefaults.pinnedScrollBehavior() and Modifier.nestedScroll for integrated scrolling effects.

```kotlin
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar

val state = rememberLazyListState()
val isAtTop by remember {
    derivedStateOf {
        state.firstVisibleItemIndex == 0 && state.firstVisibleItemScrollOffset == 0
    }
}

Scaffold(
    topBar = {
        TopAppBar(
            elevation = if (isAtTop) {
                0.dp
            } else {
                AppBarDefaults.TopAppBarElevation
            },
            …
        )
    },
    content = {
        LazyColumn(state = state) { … }
    }
)
```

```kotlin
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults

val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
        TopAppBar(
            scrollBehavior = scrollBehavior,
            …
        )
    },
    content = {
        LazyColumn { … }
    }
)
```

--------------------------------

### DrawModifier Interface Implementation

Source: https://developer.android.com/develop/ui/compose/graphics/draw/modifiers?rec=CkpodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZ3JhcGhpY3MvaW1hZ2VzL2N1c3RvbWl6ZRABGAkgASgBMBE6AzMuNw

Demonstrates how to implement the DrawModifier interface to create a custom modifier that applies vertical flipping to Compose content. This example shows the basic structure for creating reusable drawing modifiers.

```APIDOC
## DrawModifier Interface

### Description
Implement the DrawModifier interface to create custom drawing modifiers that access ContentDrawScope for drawing operations.

### Interface
DrawModifier

### Methods
- **draw()** - Required method that receives ContentDrawScope as receiver. Implement custom drawing logic within this method.

### Implementation Example
```kotlin
class FlippedModifier : DrawModifier {
    override fun ContentDrawScope.draw() {
        scale(1f, -1f) {
            this@draw.drawContent()
        }
    }
}

fun Modifier.flipped() = this.then(FlippedModifier())
```

### Parameters
- **scale(scaleX, scaleY)** (Float, Float) - Required - Transformation parameters for scaling content
- **drawContent()** (Function) - Required - Draws the original content within the transformation scope

### Usage
Apply the custom modifier to any Compose UI element:
```kotlin
Text(
    "Hello Compose!",
    modifier = Modifier
        .flipped()
)
```

### Response
The custom modifier transforms the content according to the implementation. In this example, content is vertically flipped using a scale transformation with negative Y-axis value.
```

--------------------------------

### Configure State-based Compose TextField for Multiple Autofill Content Types

Source: https://developer.android.com/develop/ui/compose/text/autofill

This Kotlin example illustrates how to set up Autofill for a TextField that utilizes rememberTextFieldState() for its internal state management. By applying the semantics modifier, it combines ContentType.Username and ContentType.EmailAddress to inform the Autofill service about the field's expected data types.

```kotlin
TextField(
    state = rememberTextFieldState(),
    modifier = Modifier.semantics {
        contentType = ContentType.Username + ContentType.EmailAddress
    }
)
```

--------------------------------

### Apply Custom Style Extension Property in Compose UI

Source: https://developer.android.com/develop/ui/compose/styles/fundamentals

This example demonstrates how to apply a custom style extension property, 'outlinedBackground', within a Style definition. It shows how the custom function can be used just like built-in style properties to create a composite style.

```kotlin
val customExtensionStyle = Style {
    outlinedBackground(Color.Blue)
}
```

--------------------------------

### Higher-Order Functions and Lambda Expressions

Source: https://developer.android.com/develop/ui/compose/kotlin?rec=CjdodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvcGhhc2VzEAMYCSABKAQwCDoDMy43

Demonstrates how to use higher-order functions that accept lambda expressions as parameters. Shows both explicit function references and inline lambda definitions for event handlers like Button onClick callbacks.

```APIDOC
## Higher-Order Functions and Lambda Expressions

### Description
Kotlin supports higher-order functions that receive other functions as parameters. Compose leverages this pattern extensively, particularly with composable functions that accept lambda parameters for event handling and content definition.

### Concept
Higher-order functions pair naturally with lambda expressions, which are inline function definitions. Instead of defining a function elsewhere and passing a reference, you can define the function directly where it's needed.

### Usage Pattern

#### Explicit Function Reference
```kotlin
Button(
    onClick = myClickFunction
)
```

#### Inline Lambda Expression
```kotlin
Button(
    onClick = {
        // do something
        // do something else
    }
)
```

### Key Points
- Higher-order functions accept functions as parameters
- Lambda expressions allow inline function definitions
- Use lambdas when a function is only needed once
- Common in Compose for event handlers and content callbacks
```

--------------------------------

### Rotation Transformations

Source: https://developer.android.com/develop/ui/compose/graphics/draw/modifiers?rec=CkpodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvZ3JhcGhpY3MvaW1hZ2VzL2N1c3RvbWl6ZRABGAkgASgBMBE6AzMuNw

Apply 3D rotations to composables using rotationX, rotationY, and rotationZ properties. Rotation values are specified in degrees (0-360). This example demonstrates rotating an Image composable on all three axes.

```APIDOC
## Rotation Transformations

### Description
Apply 3D rotations to composables using the graphicsLayer modifier. Set rotationX to rotate horizontally, rotationY to rotate vertically, and rotationZ to rotate on the Z axis (standard rotation).

### Properties
- **rotationX** (Float) - Horizontal rotation in degrees (0-360)
- **rotationY** (Float) - Vertical rotation in degrees (0-360)
- **rotationZ** (Float) - Z-axis rotation in degrees (0-360)

### Code Example
```kotlin
Image(
    painter = painterResource(id = R.drawable.sunset),
    contentDescription = "Sunset",
    modifier = Modifier
        .graphicsLayer {
            this.rotationX = 90f
            this.rotationY = 275f
            this.rotationZ = 180f
        }
)
```

### Notes
- All rotation values are specified in degrees
- Rotations are applied around the transform origin (default: center)
- Can be combined with other graphics layer properties
```

--------------------------------

### Verbose Jetpack Compose Text Element with Explicit Defaults

Source: https://developer.android.com/develop/ui/compose/kotlin?rec=CmRodHRwczovL2RldmVsb3Blci5hbmRyb2lkLmNvbS9kZXZlbG9wL3VpL2NvbXBvc2UvdG91Y2gtaW5wdXQvdXNlci1pbnRlcmFjdGlvbnMvaGFuZGxpbmctaW50ZXJhY3Rpb25zEAIYCSABKAUwHToDMy43

This example illustrates a more verbose way to define a `Text` composable, explicitly setting parameters that would otherwise use their default values. While functional, it highlights how default arguments simplify code and improve readability by avoiding unnecessary explicit declarations.

```kotlin
Text(
    text = "Hello, Android!",
    color = Color.Unspecified,
    fontSize = TextUnit.Unspecified,
    letterSpacing = TextUnit.Unspecified,
    overflow = TextOverflow.Clip
)
KotlinSnippets.kt
```