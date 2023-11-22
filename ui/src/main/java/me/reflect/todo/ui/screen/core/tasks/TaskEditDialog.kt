package me.reflect.todo.ui.screen.core.tasks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import me.reflect.todo.data.core.model.Task
import me.reflect.todo.data.core.model.enums.Importance
import me.reflect.todo.data.core.model.enums.Type
import me.reflect.todo.data.core.model.enums.Urgency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditDialog(task: Task, onDismissRequest: () -> Unit, onSaveRequest: (task: Task) -> Unit) {
    var name by remember { mutableStateOf(task.name) }
    var description by remember { mutableStateOf(task.description) }
    var urgency by remember { mutableStateOf(task.urgency) }
    var type by remember { mutableStateOf(task.type) }
    var status by remember { mutableStateOf(task.status) }
    var importance by remember { mutableStateOf(task.importance) }
    var typeMenuExpanded by remember { mutableStateOf(false) }

    AlertDialog(modifier = Modifier.width(400.dp), onDismissRequest = onDismissRequest) {
        Card {
            ConstraintLayout(modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 24.dp)) {
                val (topBarRef, editRef) = createRefs()
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(topBarRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }) {
                    Text(
                        text = "Новая задача",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                    Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                        IconButton(onClick = onDismissRequest) {
                            Icon(imageVector = Icons.Filled.Close, contentDescription = "Close")
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        IconButton(onClick = { onSaveRequest(Task(name = name, description = description, urgency = urgency, type = type, status = status, importance = importance)) }) {
                            Icon(imageVector = Icons.Filled.Done, contentDescription = "")
                        }
                    }
                }

                Column(modifier = Modifier
                    .constrainAs(editRef) {
                        top.linkTo(topBarRef.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(top = 12.dp)) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        placeholder = { Text("Название задачи") },
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        placeholder = { Text("Описание") },
                        maxLines = 5
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Срочная")
                        Spacer(modifier = Modifier.width(12.dp))
                        Switch(checked = urgency == Urgency.URGENT, onCheckedChange = {
                            urgency = if (urgency == Urgency.URGENT) {
                                Urgency.NOT_URGENT
                            } else {
                                Urgency.URGENT
                            }
                        })
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Важная")
                        Spacer(modifier = Modifier.width(12.dp))
                        Switch(
                            modifier = Modifier,
                            checked = importance == Importance.IMPORTANT, onCheckedChange = {
                            importance = if (importance == Importance.IMPORTANT) {
                                Importance.NOT_IMPORTANT
                            } else {
                                Importance.IMPORTANT
                            }
                        })
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Тип задачи")
                        Spacer(modifier = Modifier.width(12.dp))
                        ExposedDropdownMenuBox(expanded = typeMenuExpanded, onExpandedChange = { typeMenuExpanded = !typeMenuExpanded }) {
                            OutlinedTextField(
                                value = when (type) {
                                    Type.FEATURE -> "Фича"
                                    Type.NEW -> "Улучшение"
                                    Type.FIX -> "Фикс"
                                    else -> ""
                                },
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = typeMenuExpanded) },
                                modifier = Modifier.menuAnchor()
                            )

                            ExposedDropdownMenu(expanded = typeMenuExpanded, onDismissRequest = { typeMenuExpanded = false }) {
                                DropdownMenuItem(text = { Text(text = "Фикс") }, onClick = { type = Type.FIX })
                                DropdownMenuItem(text = { Text(text = "Фича") }, onClick = { type = Type.FEATURE })
                                DropdownMenuItem(text = { Text(text = "Улучшение") }, onClick = { type = Type.NEW })
                            }
                        }
                    }
                }
            }
        }
    }
}