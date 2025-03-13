package com.example.simplerxapp.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.simplerxapp.R
import com.example.simplerxapp.models.SubjectModel
import com.example.simplerxapp.ui.ActionIntent
import com.example.simplerxapp.ui.MainViewModel
import com.example.simplerxapp.ui.composables.containers.MainContentHolder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectsListView(
    viewModel: MainViewModel,
    navigateToChapters: (SubjectModel) -> Unit
) {

    val context = LocalContext.current
    val uiState by remember { viewModel.subjectsUiStateSF }.collectAsState()
    val apiUiState by remember { viewModel.subjectsRemoteUiStateSF }.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadAllSubjects()
    }

    var subjectsList: List<SubjectModel> by remember {
        mutableStateOf(emptyList())
    }

    LaunchedEffect(uiState) {
        when {
            uiState.isSuccess -> subjectsList = uiState.getOrNull() ?: listOf()
        }
    }

    MainContentHolder(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.subjects),
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.onUserIntent(ActionIntent.ReloadData)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null
                        )
                    }

                    IconButton(
                        onClick = {
                            viewModel.onUserIntent(ActionIntent.RestoreDatabaseBackup(context))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Build,
                            contentDescription = null
                        )
                    }

                    IconButton(
                        onClick = {
                            viewModel.onUserIntent(ActionIntent.CreateDatabaseBackup(context))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        uiState = apiUiState
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            items(subjectsList) {
                SubjectView(
                    modifier = Modifier
                        .clickable {
                            navigateToChapters(it)
                        }
                        .fillMaxWidth(),
                    subjectModel = it
                )
            }
        }
    }
}