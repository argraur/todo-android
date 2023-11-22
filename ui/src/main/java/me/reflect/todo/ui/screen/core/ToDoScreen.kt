package me.reflect.todo.ui.screen.core

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import me.reflect.todo.data.core.model.Task
import me.reflect.todo.data.core.model.enums.Status
import me.reflect.todo.data.core.model.enums.Type
import me.reflect.todo.ui.R
import me.reflect.todo.ui.screen.core.tasks.TaskEditDialog
import me.reflect.todo.ui.screen.core.tasks.TaskItem
import me.reflect.todo.ui.screen.core.tasks.TaskItemModel
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ToDoScreen(viewModel: ToDoViewModel = koinViewModel()) {
    val context = LocalContext.current
    val density = LocalDensity.current

    val uiState by viewModel.uiState.collectAsState()
    val tasks by viewModel.tasks.collectAsState(initial = listOf())

    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.isLoading,
        onRefresh = {
            viewModel.refreshRepository()
        }
    )

    var searchQuery by remember { mutableStateOf("") }

    if (uiState.isDialogOpen) {
        TaskEditDialog(
            task = Task(),
            onDismissRequest = { viewModel.toggleTaskEditDialog() },
            onSaveRequest = { task ->
                viewModel.addTask(task)
                viewModel.toggleTaskEditDialog()
            }
        )
    }

    if (uiState.isError) {
        Toast.makeText(context, uiState.errorMsg, Toast.LENGTH_LONG).show()
        viewModel.endError()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            MediumTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                windowInsets = WindowInsets(0.dp),
                title = {
                    Text(
                        text = stringResource(id = R.string.todo_title),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.toggleSearchBar()
                        Toast.makeText(context, "Task search button clicked!", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = "Search tasks")
                    }
                    IconButton(onClick = {
                        Toast.makeText(context, "Task filter button clicked!", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(imageVector = Icons.Filled.List, contentDescription = "Filter tasks")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.toggleTaskEditDialog() }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add new task")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        ConstraintLayout(modifier = Modifier
            .fillMaxSize()
            .padding(top = innerPadding.calculateTopPadding())
            .pullRefresh(pullRefreshState))
        {
            val (column, refreshIndicator, cool) = createRefs()
            if (tasks.isEmpty()) {
                Text(
                    text = "Задач нет. Чётко",
                    fontWeight = FontWeight.Normal,
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 14.sp,
                    modifier = Modifier.constrainAs(cool) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    })
            }
            Column(modifier = Modifier
                .fillMaxSize()
                .constrainAs(column) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }) {
                AnimatedVisibility(visible = uiState.searchVisible, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp)) {
                    DockedSearchBar(
                        query = searchQuery,
                        onQueryChange = { searchQuery = it },
                        onSearch = {},
                        active = false,
                        onActiveChange = {},
                        placeholder = { Text(text = "Поиск по задачам") },
                        leadingIcon = { Icon(Icons.Filled.Search, "Search") },
                        shape = RoundedCornerShape(12.dp),
                        content = {}
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    FilterChip(
                        trailingIcon = { Icon(imageVector = Icons.Default.Star, contentDescription = "All")},
                        leadingIcon = {
                            AnimatedVisibility(visible = uiState.filterMode == FilterMode.ALL) {
                                Icon(Icons.Filled.Done, "Selected")
                            }
                        },
                        selected = uiState.filterMode == FilterMode.ALL,
                        onClick = { viewModel.toggleFilterMode(FilterMode.ALL) },
                        label = { Text(text = "Все") }
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    FilterChip(
                        trailingIcon = { Icon(imageVector = Icons.Default.Add, contentDescription = "New")},
                        leadingIcon = {
                            AnimatedVisibility(visible = uiState.filterMode == FilterMode.NEW) {
                                Icon(Icons.Filled.Done, "Selected")
                            }
                        },
                        selected = uiState.filterMode == FilterMode.NEW,
                        onClick = { viewModel.toggleFilterMode(FilterMode.NEW) },
                        label = { Text(text = "Новые") }
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    FilterChip(
                        trailingIcon = { Icon(imageVector = Icons.Default.Close, contentDescription = "Closed")},
                        leadingIcon = {
                            AnimatedVisibility(visible = uiState.filterMode == FilterMode.CLOSED) {
                                Icon(Icons.Filled.Done, "Selected")
                            }
                        },
                        selected = uiState.filterMode == FilterMode.CLOSED,
                        onClick = { viewModel.toggleFilterMode(FilterMode.CLOSED) },
                        label = { Text(text = "Закрытые") }
                    )
                }
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(
                        items = filterTasks(tasks,
                            filterMode = uiState.filterMode,
                            isSearchEnabled = uiState.searchVisible,
                            searchQuery = searchQuery
                        ),
                        key = { it.id },
                    ) {task ->
                        var visible by remember { mutableStateOf(true) }
                        val dismissState = rememberDismissState(
                            confirmValueChange = {
                                if (it == DismissValue.DismissedToStart) {
                                    visible = false
                                    viewModel.removeTask(task)
                                }
                                true
                            }
                        )

                        AnimatedVisibility(
                            visible = visible,
                            enter = slideInVertically {
                                with(density) {
                                    -40.dp.roundToPx()
                                }
                            } + expandVertically(
                                expandFrom = Alignment.Top
                            ) + fadeIn(
                                initialAlpha = 0.3f
                            ),
                            exit = fadeOut()
                        ) {
                            SwipeToDismiss(
                                state = dismissState,
                                directions = setOf(DismissDirection.EndToStart),
                                background = {
                                    val deleteColor = if (isSystemInDarkTheme()) Color(186, 0, 13) else Color(255,121,97)
                                    val color by animateColorAsState(
                                        when (dismissState.targetValue) {
                                            DismissValue.Default -> Color.Transparent
                                            DismissValue.DismissedToStart -> deleteColor
                                            else -> Color.Transparent
                                        }, label = "swipe_color_animation"
                                    )

                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 12.dp, vertical = 6.dp)
                                            .height(200.dp),
                                        colors = CardDefaults.cardColors(containerColor = color)
                                    ) {
                                        ConstraintLayout(modifier = Modifier
                                            .fillMaxSize()
                                            .padding(horizontal = 12.dp)
                                        ) {
                                            val (deleteIcon) = createRefs()
                                            Icon(
                                                imageVector = Icons.Filled.Delete,
                                                contentDescription = "Delete",
                                                modifier = Modifier.constrainAs(deleteIcon) {
                                                    top.linkTo(parent.top)
                                                    bottom.linkTo(parent.bottom)
                                                    end.linkTo(parent.end)
                                                }
                                            )
                                        }
                                    }
                                },
                                dismissContent = {
                                    TaskItem(taskItemModel = TaskItemModel(task), viewModel = viewModel)
                                }
                            )
                        }
                    }
                }
            }

            PullRefreshIndicator(
                refreshing = uiState.isLoading,
                state = pullRefreshState,
                modifier = Modifier.constrainAs(refreshIndicator) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
        }
    }
}

fun filterTasks(tasks: List<Task>, filterMode: FilterMode, isSearchEnabled: Boolean, searchQuery: String): List<Task> {
    val filtered = when (filterMode) {
        FilterMode.CLOSED -> tasks.filter { it.closed || it.status == Status.CLOSED }
        FilterMode.NEW -> tasks.filter { it.type == Type.NEW }
        else -> tasks
    }

    return if (isSearchEnabled && searchQuery.isNotEmpty())
        filtered.filter {
            it.name.contains(searchQuery, true)
        }
    else
        filtered
}

enum class FilterMode {
    ALL,
    NEW,
    CLOSED,
    NONE
}