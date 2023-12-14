package com.dhandev.rekapin.presentation.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhandev.rekapin.R
import com.dhandev.rekapin.ui.theme.BlueMain
import com.dhandev.rekapin.ui.theme.BlueSecondary
import com.dhandev.rekapin.ui.theme.RekapinTheme
import com.dhandev.rekapin.ui.theme.raleway

@Composable
fun SwitchButton(
    modifier: Modifier = Modifier,
    icon: Int,
    title: Int,
    switchState: Boolean,
    showSwitch: Boolean = true,
    onSwitchButtonClick: () -> Unit
) {
    val checked = remember { mutableStateOf(switchState) }
    val interactionSource = remember { MutableInteractionSource() }
    Surface(
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, BlueSecondary),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 16.dp)
            .height(60.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    checked.value = !checked.value
                    onSwitchButtonClick()
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = painterResource(icon),
                contentDescription = stringResource(id = R.string.category_image_desc)
            )
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(title),
                style = raleway(fontSize = 16, weight = FontWeight.Medium)
            )
            if (showSwitch){
                Switch(
                    checked = checked.value,
                    onCheckedChange = { checked.value = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = BlueMain,
                    )
                )
            }

        }
    }
}

@Preview
@Composable
fun PreviewSwitch(){
    RekapinTheme {
        Surface {
            SwitchButton(
                icon = R.drawable.ic_payment,
                title = R.string.category_payment,
                switchState = false,
                onSwitchButtonClick = {}
            )
        }
    }
}