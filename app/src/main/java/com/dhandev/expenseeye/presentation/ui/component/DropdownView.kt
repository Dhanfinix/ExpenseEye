package com.dhandev.expenseeye.presentation.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dhandev.expenseeye.ui.theme.raleway
import com.dhandev.expenseeye.utils.Constants

@Composable
fun DropdownView(modifier: Modifier = Modifier, title: String) {
    //TODO: CHANGE WITH DATA FROM DATABASE
    val options = Constants.categoryOutcomeName
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableIntStateOf(0) }
    Text(
        text = title,
        style = raleway(fontSize = 14, weight = FontWeight.Normal)
    )
    NonFocusableOutlinedTextField(
        value = options[selectedIndex],
        onClick = { expanded = true }
    )
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        options.forEachIndexed { index, s ->
            DropdownMenuItem(
                text = { Text(text = s) },
                onClick = {
                    selectedIndex = index
                    expanded = false
                })
        }
    }
}