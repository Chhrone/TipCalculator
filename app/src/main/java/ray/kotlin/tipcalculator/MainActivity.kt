@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package ray.kotlin.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ray.kotlin.tipcalculator.ui.theme.TipCalculatorTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                TipAppLayout()
            }
        }
    }
}

@Composable
fun TipAppLayout() {
    var billAmount by remember {
        mutableStateOf("")
    }
    var percentage by remember {
        mutableStateOf("")
    }

    val tipPercentage = percentage.toDoubleOrNull() ?: 0.0
    val bill = billAmount.toDoubleOrNull() ?: 0.0

    val tip = calculateTip(bill = bill, tipPercentage = tipPercentage)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.calculate_tip),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        InputField(
            label = "Bill Amount",
            value = billAmount,
            onValueChange = { billAmount = it },
            imeAction = ImeAction.Next
        )
        InputField(
            label = "Tip Percentage",
            value = percentage,
            onValueChange = { percentage = it },
            imeAction = ImeAction.Done
        )
        Text(
            text = stringResource(R.string.tip_amount, tip),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun InputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    imeAction: ImeAction
) {
    var inputValue by remember { mutableStateOf(value) }

    TextField(
        value = inputValue,
        onValueChange = {
            inputValue = it
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = imeAction),
        keyboardActions = KeyboardActions(
            onDone = {
                onValueChange(inputValue)
            }
        ),
        label = { Text(text = label) },
        modifier = Modifier
            .width(300.dp)
            .padding(vertical = 16.dp)
    )
}

private fun calculateTip(bill: Double, tipPercentage: Double): String {
    val tip = bill * (tipPercentage / 100)
    return NumberFormat.getCurrencyInstance().format(tip)
}





@Preview(showBackground = true,
    showSystemUi = true)
@Composable
fun GreetingPreview() {
    TipCalculatorTheme {
        TipAppLayout()
    }
}