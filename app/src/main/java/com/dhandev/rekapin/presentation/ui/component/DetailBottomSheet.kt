package com.dhandev.rekapin.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dhandev.rekapin.R
import com.dhandev.rekapin.data.model.TransactionItemModel
import com.dhandev.rekapin.ui.theme.BlueSecondary
import com.dhandev.rekapin.ui.theme.MyGreen
import com.dhandev.rekapin.ui.theme.MyRed
import com.dhandev.rekapin.ui.theme.raleway
import com.dhandev.rekapin.utils.CategoryUtil
import com.dhandev.rekapin.utils.DateUtil
import com.dhandev.rekapin.utils.StringUtil
import com.dhandev.rekapin.utils.TransactionCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailBottomSheet(
    modifier: Modifier = Modifier,
    scaffoldState: BottomSheetScaffoldState,
    data: TransactionItemModel,
    onDelete:()->Unit,
    onUpdate:()->Unit
) {
    val context = LocalContext.current
    val (categoryImage, categoryName) = CategoryUtil.getCategory(data.category)
    val openAlertDialog = remember { mutableStateOf(false) }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(150.dp),
                    painter = painterResource(id = categoryImage),
                    contentDescription = TransactionCategory.values()[categoryName].toString()
                )
                Text(text = data.title, style = raleway(fontSize = 24, weight = FontWeight.Bold))
                Text(
                    text = data.category,
                    style = raleway(fontSize = 20, weight = FontWeight.Normal)
                )
                Text(
                    text = DateUtil.millisToFullDate(data.dateInMillis),
                    style = raleway(fontSize = 14, weight = FontWeight.Normal),
                    textAlign = TextAlign.Center
                )

                Row(
                    modifier = Modifier
                        .padding(top = 34.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.total_transaction),
                        style = raleway(fontSize = 14, weight = FontWeight.SemiBold)
                    )
                    Text(
                        text = StringUtil.formatRpWithSign(data.total.toString(), data.isExpense),
                        style = raleway(fontSize = 24, weight = FontWeight.Bold),
                        color = if (data.isExpense) MyRed else MyGreen
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(top = 34.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            onUpdate()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BlueSecondary,
                            contentColor = Color.DarkGray
                        )
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_edit),
                            contentDescription = stringResource(id = R.string.edit)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(id = R.string.edit),
                            style = raleway(
                                fontSize = 16,
                                weight = FontWeight.Medium
                            ),
                            color = Color.DarkGray
                        )
                    }
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            openAlertDialog.value = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MyRed,
                            contentColor = Color.White
                        ),
                        enabled = !(data.id == 1 && data.title == "Initial balance")
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete),
                            contentDescription = stringResource(id = R.string.delete)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(id = R.string.delete),
                            style = raleway(
                                fontSize = 16,
                                weight = FontWeight.Medium
                            ),
                            color = Color.White
                        )
                    }
                }
            }
        }) {}

    if (openAlertDialog.value){
        MyAlertDialog(
            onDismissRequest = { openAlertDialog.value = false },
            onConfirmation = { onDelete() },
            dialogTitle = stringResource(R.string.delete_transaction),
            dialogText = stringResource(R.string.delete_transaction_confrimation),
            icon = Icons.Filled.Delete
        )
    }
}