package com.dhandev.rekapin.presentation.landing

import androidx.compose.animation.core.animateOffset
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhandev.rekapin.R
import com.dhandev.rekapin.navigation.NavigationDestination
import com.dhandev.rekapin.ui.theme.BlueMain
import com.dhandev.rekapin.ui.theme.BlueSecondary
import com.dhandev.rekapin.ui.theme.RekapinTheme
import com.dhandev.rekapin.ui.theme.raleway
import kotlinx.coroutines.delay

object AfterSplashDestination : NavigationDestination {
    override val route: String = "afterSplash"
    override val titleRes: Int = R.string.after_splash
}
@Composable
fun AfterSplash(
    modifier : Modifier = Modifier,
    goToHome: ()->Unit
){
    val isAnimated = remember { mutableStateOf(false) }

    val transition = updateTransition(targetState = isAnimated.value, label = "transition")

    val logoOffset by transition.animateOffset(
        transitionSpec = {
            if (this.targetState) {
                tween(1000) // launch duration
            } else {
                tween(1500) // land duration
            }
        },
        label = "logo offset"
    ) { animated ->
        if (animated) Offset(-10f, 0f) else Offset(0f, 0f)
    }

    val nameOffset by transition.animateOffset(
        transitionSpec = {
            if (this.targetState) {
                tween(1000) // launch duration
            } else {
                tween(1500) // land duration
            }
        },
        label = "name offset"
    ) { animated ->
        if (animated) Offset(20f, 0f) else Offset(0f, 0f)
    }

    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_icon_app),
            contentDescription = "Logo",
            modifier = Modifier
                .size(150.dp)
                .offset(logoOffset.x.dp, logoOffset.y.dp)
        )

        Spacer(modifier = Modifier
            .height(120.dp)
            .width(10.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color = BlueSecondary)
            .offset(x = (-20).dp)
        )
        Text(
            text = stringResource(id = R.string.app_name),
            modifier = Modifier
                .offset(nameOffset.x.dp, nameOffset.y.dp)
                .padding(end = 30.dp),
            style = raleway(fontSize = 30, weight = FontWeight.SemiBold),
            color = BlueMain
        )
    }

    LaunchedEffect(Unit){
        isAnimated.value = true
        delay(1750)
        goToHome()
    }
}

@Composable
@Preview
fun PreviewAfterSplash(){
    RekapinTheme {
        Surface {
            AfterSplash {

            }
        }
    }
}