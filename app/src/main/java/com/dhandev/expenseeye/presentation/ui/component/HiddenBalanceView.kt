
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhandev.expenseeye.ui.theme.BlueSecondary
import com.dhandev.expenseeye.ui.theme.ExpenseEyeTheme

@Composable
fun HiddenBalanceView(
    modifier: Modifier = Modifier
){
    val gradient = Brush.linearGradient(
        colors = listOf(Color.Black, Color.Gray),
        start = Offset(100f, 0f),
        end = Offset(0f, 100f)
    )
    val gradient2 = Brush.linearGradient(
        colors = listOf(Color.Gray, Color.Black),
        start = Offset(100f, 0f),
        end = Offset(0f, 100f)
    )
    LazyRow(
        modifier = modifier.padding(top = 10.dp, end = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ){
        items(7){
            Surface(
                modifier = Modifier.size(30.dp).clip(CircleShape).background(brush = if (it % 2 == 0) gradient else gradient2),
                shape = CircleShape,
                color = Color.Transparent
            ){}
        }
    }
}

@Preview
@Composable
fun PreviewHiddenBalance(){
    ExpenseEyeTheme {
        Surface(
            color = Color.White
        ) {
            HiddenBalanceView()
        }
    }
}