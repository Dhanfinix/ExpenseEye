package com.dhandev.rekapin.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun NumberSelector(
    range: IntRange,
    selectedNumber: MutableState<Int>
) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    Box {
        LazyColumn(state = listState, modifier = Modifier.height(70.dp)) {
            items(Int.MAX_VALUE) { index ->
                val number = range.first + index % range.count()
                Text(
                    text = number.toString(),
                    fontSize = 24.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedNumber.value = number
                            coroutineScope.launch {
                                listState.animateScrollToItem(number - range.first)
                            }
                        },
                    textAlign = TextAlign.Center
                )
            }
        }

        // Highlight the selected number
//        Text(
//            text = selectedNumber.value.toString(),
//            fontSize = 24.sp,
//            color = Color.Red,
//            modifier = Modifier
//                .align(Alignment.Center)
//                .padding(16.dp),
//            textAlign = TextAlign.Center
//        )
    }
}

@Composable
@Preview
fun MyScreen() {
    val selectedNumber = remember { mutableStateOf(1) }
    NumberSelector(range = 1..28, selectedNumber = selectedNumber)
}
