package com.example.parcialtp3.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.parcialtp3.domain.model.ExampleModel
import com.example.parcialtp3.presentation.components.ExampleCard
import com.example.parcialtp3.presentation.viewmodels.ExampleViewModel

/**
 * Home Screen showing list of examples
 * Uses Hilt to inject ViewModel
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: ExampleViewModel = hiltViewModel(),
    onNavigateToCreate: () -> Unit,
    onNavigateToDetail: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Parcial Template") },
                actions = {
                    IconButton(onClick = { viewModel.syncExamples() }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Sync with API"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToCreate) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add new item"
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                uiState.error != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Error: ${uiState.error}",
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.loadExamples() }) {
                            Text("Retry")
                        }
                    }
                }

                uiState.examples.isEmpty() -> {
                    Text(
                        text = "No items found.\nTap + to create one!",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = uiState.examples,
                            key = { it.id }
                        ) { example ->
                            ExampleCard(
                                example = example,
                                onClick = { onNavigateToDetail(example.id) },
                                onToggleComplete = { viewModel.toggleCompletion(example) },
                                onDelete = { viewModel.deleteExample(example) }
                            )
                        }
                    }
                }
            }
        }
    }
}
