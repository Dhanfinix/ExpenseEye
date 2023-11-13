package com.dhandev.expenseeye.presentation.create

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhandev.expenseeye.R
import com.dhandev.expenseeye.navigation.NavigationDestination
import com.dhandev.expenseeye.presentation.ui.component.TabView
import com.dhandev.expenseeye.ui.theme.BlueSecondary
import com.dhandev.expenseeye.ui.theme.DarkGray
import com.dhandev.expenseeye.ui.theme.ExpenseEyeTheme
import com.dhandev.expenseeye.ui.theme.raleway

object CreateDestination : NavigationDestination {
    override val route: String = "createEdit"
    override val titleRes: Int = R.string.create
}

@Composable
fun CreateScreen(
    modifier: Modifier = Modifier
) {
    Box(modifier.fillMaxSize()) {
        TabView()
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(
                containerColor = BlueSecondary,
                contentColor = DarkGray
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_save_24),
                contentDescription = stringResource(id = R.string.save))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = R.string.save),
                style = raleway(
                    fontSize = 16,
                    weight = FontWeight.Medium
                ),
                color = DarkGray
            )
        }
    }
}

@Preview
@Composable
fun PreviewCreate() {
    ExpenseEyeTheme {
        Surface {
            CreateScreen()
        }
    }
}