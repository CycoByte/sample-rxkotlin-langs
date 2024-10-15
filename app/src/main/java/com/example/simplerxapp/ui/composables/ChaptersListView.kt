package com.example.simplerxapp.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.simplerxapp.R
import com.example.simplerxapp.models.ChapterModel
import com.example.simplerxapp.ui.MainViewModel
import com.example.simplerxapp.ui.composables.containers.MainContentHolder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChaptersListView(
    subjectId: String,
    viewModel: MainViewModel,
    onBack: () -> Unit
) {

    LaunchedEffect(subjectId) {
        viewModel.loadChaptersOfSubject(subjectId)
    }

    val uiState by remember { viewModel.chaptersUiStateSF }.collectAsState()
    val remoteUiState by remember { viewModel.chaptersRemoteUiStateSF }.collectAsState()

    var chaptersList: List<ChapterModel> by remember {
        mutableStateOf(emptyList())
    }

    LaunchedEffect(uiState) {
        when {
            uiState.isSuccess -> chaptersList = uiState.getOrNull() ?: listOf()
        }
    }

    MainContentHolder(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.chapters_for_subject_arg, subjectId),
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                navigationIcon = {
                    IconButton(onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },

        uiState = remoteUiState
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            items(chaptersList) {
                ChapterView(
                    modifier = Modifier
                        .fillMaxWidth(),
                    chapter = it
                )
            }

            if (chaptersList.isEmpty() && !uiState.isLoading) {
                item {
                    Text(
                        modifier = Modifier.padding(24.dp),
                        text = stringResource(R.string.no_chapters_found)
                    )
                }
            }
        }
    }
}