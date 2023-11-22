package me.reflect.todo.ui.screen.core.tasks

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
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
            Text(text = task.name, fontSize = 24.sp, fontWeight = FontWeight.Bold, maxLines = 1,modifier = Modifier.constrainAs(nameRef) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            })
            Text(text = task.description, fontSize = 12.sp, fontWeight = FontWeight.Normal, modifier = Modifier.constrainAs(descRef) {
                top.linkTo(nameRef.bottom)
                start.linkTo(parent.start)
            })
        }
    }
}