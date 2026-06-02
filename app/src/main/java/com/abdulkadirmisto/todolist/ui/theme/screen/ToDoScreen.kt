package com.abdulkadirmisto.todolist.ui.theme.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abdulkadirmisto.todolist.model.Task
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(
    isDarkMode: Boolean,
    onToggleTheme: () -> Unit
) {
    var showDialog = remember { mutableStateOf(false) }
    var taskText = remember { mutableStateOf("") }
    var taskDate = remember { mutableStateOf("") }
    var taskTime = remember { mutableStateOf("") }
    val tasks = remember { mutableStateListOf<Task>() }

    var showDatePicker = remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    var showTimePicker = remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState(is24Hour = false)


    Box( modifier = Modifier
        .fillMaxSize()
        .background(color = if (isDarkMode) Color.Black else Color.White)) {

        Column {

            TopAppBar(
                title = { Text("To Do App") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    IconButton(onClick = onToggleTheme) {
                        Icon(
                            imageVector = if (isDarkMode)
                                Icons.Default.DarkMode
                            else
                                Icons.Default.LightMode,
                            contentDescription = "Theme",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )

            // Short Tasks
            val sortedTasks = tasks.sortedBy { task ->

                val format = SimpleDateFormat(
                    "dd/MM/yyyy h:mm a",
                    Locale.getDefault()
                )

                try {
                    format.parse("${task.date} ${task.time}")
                } catch (e: Exception) {
                    null
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(sortedTasks) { task ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (task.isDone.value)
                                MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f)
                            else
                                MaterialTheme.colorScheme.surface
                        )
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            IconButton(
                                onClick = { task.isDone.value = !task.isDone.value }
                            ) {
                                Icon(
                                    imageVector = if (task.isDone.value)
                                        Icons.Default.CheckCircle
                                    else
                                        Icons.Default.RadioButtonUnchecked,
                                    contentDescription = "Done",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }

                            Column(modifier = Modifier.weight(1f)) {

                                Text(
                                    text = task.title,
                                    textDecoration = if (task.isDone.value)
                                        TextDecoration.LineThrough
                                    else null,
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                                Text(
                                    "📅 ${task.date}",
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                )

                                Text(
                                    "⏰ ${task.time}",
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                )
                            }

                            IconButton(
                                onClick = { tasks.remove(task) }
                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = Color.Red
                                )
                            }
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { showDialog.value = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }

        // DIALOG
        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                confirmButton = {
                    Button(
                        onClick = {
                            tasks.add(Task(taskText.value, taskDate.value, taskTime.value))
                            taskText.value = ""
                            taskDate.value = ""
                            taskTime.value = ""
                            showDialog.value = false
                        }
                    ) {
                        Text("Save")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog.value = false }) {
                        Text("Cancel")
                    }
                },
                title = { Text("Add New Task") },
                text = {
                    Column {

                        OutlinedTextField(
                            value = taskText.value,
                            onValueChange = { taskText.value = it },
                            placeholder = { Text("Task Name") },
                            shape = RoundedCornerShape(15.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                                cursorColor = MaterialTheme.colorScheme.primary
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = { showDatePicker.value = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Text(if (taskDate.value.isEmpty()) "Select Date" else taskDate.value)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = { showTimePicker.value = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Text(if (taskTime.value.isEmpty()) "Select Time" else taskTime.value)
                        }
                    }
                }
            )
        }

        // DATE PICKER
        if (showDatePicker.value) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker.value = false },
                confirmButton = {
                    Button(onClick = {
                        val millis = datePickerState.selectedDateMillis
                        millis?.let {
                            taskDate.value = formattedDate.format(Date(it))
                        }
                        showDatePicker.value = false
                    }) {
                        Text("OK")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        // TIME PICKER
        if (showTimePicker.value) {
            AlertDialog(
                onDismissRequest = { showTimePicker.value = false },
                confirmButton = {
                    Button(onClick = {
                        val hour = timePickerState.hour
                        val minute = timePickerState.minute
                        val amPm = if (hour < 12) "AM" else "PM"
                        val displayHour = if (hour % 12 == 0) 12 else hour % 12

                        taskTime.value =
                            "$displayHour:${minute.toString().padStart(2, '0')} $amPm"

                        showTimePicker.value = false
                    }) {
                        Text("OK")
                    }
                },
                text = {
                    TimePicker(state = timePickerState)
                }
            )
        }
    }
}