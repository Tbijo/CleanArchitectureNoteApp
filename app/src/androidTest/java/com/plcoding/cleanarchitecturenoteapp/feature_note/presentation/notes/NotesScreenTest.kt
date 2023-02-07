package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.plcoding.cleanarchitecturenoteapp.core.util.TestTags
import com.plcoding.cleanarchitecturenoteapp.di.AppModule
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.MainActivity
import com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.util.Screen
import com.plcoding.cleanarchitecturenoteapp.presentation.ui.theme.CleanArchitectureNoteAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

// This Test Case uses an Android Component in this case Composable
// Therefore it is a Instrumented test since it will communicate with another class in this case ViewModel
// It will be put inside androidTest directory
@HiltAndroidTest // DO NOT FORGET ANNOTATION
@UninstallModules(AppModule::class) // To not have conflict in dependencies
class NotesScreenTest {

    // Instrumented tests can have many dependencies so we use Dagger Hilt to inject them
    // We can inject our normal dependencies from our normal DI module but our normal module has
    // production dependencies like DB and API we do not want the real DB and Repository in test cases.
    // We will create a test module to override the production dependencies. (TestAppModule)

    // Then we will create an Application class (HiltTestRunner) but for testing.
    // And add the package path of the class app/build.gradle under testInstrumentationRunner

    // To inject dependencies in this Test Case we need to define rules.
    // The rules will give us specific behaviour for our Test Cases.
        // example an Activity rule to help us do Activity stuff like sending Intents, receiving Intent results.
        // A Hilt rule gives us the behaviour to inject dependencies.

    // With this rule we can inject our dependencies
    @get:Rule(order = 0) // To apply rules in a specific order
    val hiltRule = HiltAndroidRule(this)

    // With this rule we can simulate clicks, simulate swipes, make assertion on views, find views/composables
    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>() // specify the activity

    @ExperimentalAnimationApi
    @Before
    fun setUp() {
        // First to inject the dependencies
        hiltRule.inject()
        // Set up the screen, set the content
        composeRule.setContent {
            val navController = rememberNavController()
            CleanArchitectureNoteAppTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.NotesScreen.route
                ) {
                    composable(route = Screen.NotesScreen.route) {
                        NotesScreen(navController = navController)
                    }
                }
            }
        }
    }

    // In instrumented tests backticks are not allowed.

    // Testing whether order section will be visible
    @Test
    fun clickToggleOrderSection_isVisible() {
        // On launch make sure that the order section is not visible
        // Nodes are composables.
        // To get order section we need to tag it because it has no content description nor a text.
        // Tags are relevant only for testing. Add .testTag("") in the compose. modifier.

        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertDoesNotExist() // should not be displayed

        // Here we should use String Resources but we need context.
        // To get context: val context = ApplicationProvider.getApplicationContext<Context>()
        composeRule.onNodeWithContentDescription("Sort").performClick() // click on Icon Button to display order section

        // Check whether it is displayed
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertIsDisplayed()
    }

}