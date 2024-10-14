package com.example.simplerxapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.simplerxapp.ui.composables.ChaptersListView
import com.example.simplerxapp.ui.composables.SubjectsListView
import com.example.simplerxapp.ui.navigation.RootNavigation
import com.example.simplerxapp.ui.theme.SimpleRxAppTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            val navController = rememberNavController()

            SimpleRxAppTheme {
                NavHost(
                    modifier = Modifier.fillMaxSize(),
                    navController = navController,
                    startDestination = RootNavigation.SubjectsDestination
                ) {
                    composable<RootNavigation.SubjectsDestination> {
                        SubjectsListView(
                            viewModel = viewModel,
                            navigateToChapters = {
                                navController.navigate(RootNavigation.ChapterDestination(it.id))
                            }
                        )
                    }

                    composable<RootNavigation.ChapterDestination> { navBackStackEntry ->
                        val navArg = navBackStackEntry.toRoute<RootNavigation.ChapterDestination>()
                        ChaptersListView(
                            subjectId = navArg.subjectId,
                            viewModel = viewModel,
                            onBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}