package com.dhandev.rekapin.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.dhandev.rekapin.data.model.CategoryItem
import com.dhandev.rekapin.ui.theme.BlueSecondary
import com.dhandev.rekapin.ui.theme.raleway

@Composable
fun DropdownView(
    category: List<CategoryItem>,
    modifier: Modifier = Modifier,
    title: String,
    value: (CategoryItem)-> Unit
    ) {
    //TODO: CHANGE WITH DATA FROM DATABASE
    var mExpanded by remember { mutableStateOf(false) }
    var mSelectedIndex by remember { mutableIntStateOf(0) }
    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }
    val borderColor = if (mExpanded) BlueSecondary else Color.Gray
    value.invoke(category[mSelectedIndex])

    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.padding(bottom = 6.dp),
            text = title,
            style = raleway(fontSize = 14, weight = FontWeight.Normal)
        )
        OutlinedTextField(
            enabled = false,
            value = category[mSelectedIndex].name,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    // This value is used to assign to
                    // the DropDown the same width
                    mTextFieldSize = coordinates.size.toSize()
                }
                .clickable { mExpanded = !mExpanded },
            leadingIcon = {
                Image(
                    painter = painterResource(id = category[mSelectedIndex].icon),
                    contentDescription = null
                )
            },
            trailingIcon = {
                Icon(
                    icon, "contentDescription",
                    Modifier.clickable { mExpanded = !mExpanded },
                    tint = Color.Gray
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = borderColor,
                disabledTextColor = Color.Black
            )
        )

        // Create a drop-down menu with list of cities,
        // when clicked, set the Text Field text as the city selected
        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = { mExpanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
                .fillMaxWidth()
                .background(Color.White)
        ) {
            category.forEachIndexed { index, categoryItem ->
                Column {
                    DropdownMenuItem(
                        text = { Text(text = categoryItem.name) },
                        onClick = {
                            mSelectedIndex = index
                            mExpanded = false
                        },
                        leadingIcon = {
                            Image(
                                painter = painterResource(id = categoryItem.icon),
                                contentDescription = null
                            )
                        }
                    )
                    if (index != category.size-1){
                        Divider(modifier.fillMaxWidth().height(1.dp).padding(horizontal = 8.dp))
                    }
                }
            }
        }
    }
}