package me.reflect.todo.ui.screen.core.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import me.reflect.todo.domain.core.model.enums.Importance
import me.reflect.todo.domain.core.model.enums.Status
import me.reflect.todo.domain.core.model.enums.Type
import me.reflect.todo.domain.core.model.enums.Urgency
import me.reflect.todo.ui.screen.core.ToDoViewModel

@Composable
fun TaskItem(taskItemModel: TaskItemModel, viewModel: ToDoViewModel) {
    val task by taskItemModel.state.collectAsState()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .height(200.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val (nameRef, descRef, priorityRef, deadlineRef, membersRef) = createRefs()
            Text(text = run {
                when (task.type) {
                    Type.FIX -> "Фикс: "
                    Type.NEW -> "Улучшение: "
                    Type.FEATURE -> "Фича: "
                } + task.name
            }, fontSize = 20.sp, fontWeight = FontWeight.Bold, maxLines = 2, modifier = Modifier.constrainAs(nameRef) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            })
            Text(text = task.description, fontSize = 12.sp, fontWeight = FontWeight.Normal, lineHeight = 14.sp, modifier = Modifier.constrainAs(descRef) {
                top.linkTo(nameRef.bottom)
                start.linkTo(parent.start)
            })

            Column(modifier = Modifier.constrainAs(priorityRef) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
            }) {
                when (task.urgency) {
                    Urgency.URGENT -> {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Filled.Notifications, contentDescription = "", modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "Срочное", fontSize = 12.sp)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                    else -> {}
                }
                when (task.importance) {
                    Importance.IMPORTANT -> {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Filled.Star, contentDescription = "", modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "Важное", fontSize = 12.sp)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                    else -> {}
                }
                when (task.status) {
                    Status.OPENED -> Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Filled.Warning, contentDescription = "", modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Открытое", fontSize = 12.sp)
                    }
                    Status.CLOSED -> Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = "", modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Выполнено", fontSize = 12.sp)
                    }
                    Status.IN_WORK -> Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Filled.Build, contentDescription = "", modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "В работе", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}