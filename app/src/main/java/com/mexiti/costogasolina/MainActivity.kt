package com.mexiti.costogasolina

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mexiti.costogasolina.ui.theme.CostoGasolinaTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CostoGasolinaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.LightGray
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        CostGasLayout("Android")
                    }
                }
            }
        }
    }
}

@Composable
fun CostGasLayout(name: String) {
    var precioGasolina by remember {
        mutableStateOf("")
    }
    var cantLitro by remember {
        mutableStateOf("")
    }
    var propina by remember {
        mutableStateOf("")
    }
    var estadoSwitch by remember {
        mutableStateOf(false)
    }

    val precioLitro = precioGasolina.toDoubleOrNull() ?:0.0
    val cantLitrosNum = cantLitro.toDoubleOrNull() ?:0.0
    val propinaNum = propina.toDoubleOrNull() ?:0.0
    val total = calcularMonto(precioLitro, cantLitrosNum, propinaNum, estadoSwitch)   //Debe ser en Double



    Column {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.calcular_monto),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp

            )
        Spacer(modifier = Modifier.padding(13.dp))
       EditNumberField(
           modifier = Modifier.fillMaxWidth(),
           label = R.string.ingresa_gasolina,
           leadingIcon = R.drawable.money_gas ,
           keyboardsOptions = KeyboardOptions.Default.copy(
               keyboardType = KeyboardType.Number,
               imeAction = ImeAction.Next
           ),
           value = precioGasolina,
           onValueChanged = {precioGasolina = it}
       )
        EditNumberField(
            modifier = Modifier.fillMaxWidth(),
            label = R.string.ingresa_litros,
            leadingIcon = R.drawable.gasstation ,
            keyboardsOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            value = cantLitro,
            onValueChanged = {cantLitro = it}
        )
        EditNumberField(
            modifier = Modifier.fillMaxWidth(),
            label = R.string.propina,
            leadingIcon = R.drawable.propina ,
            keyboardsOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            value = propina,
            onValueChanged = {propina = it}
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "¿Deseas agregar la propina?",
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Switch(
                checked = estadoSwitch,
                onCheckedChange =  {estadoSwitch = it}
            )
        }
        Text(
            text = "Monto Total: $total",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )

    }

}

@Composable
fun EditNumberField(
    @StringRes label: Int,
    @DrawableRes leadingIcon: Int,
    keyboardsOptions:KeyboardOptions,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
){
    TextField(
        label = { Text(text = stringResource(id = label))  },
        value = value,
        singleLine = true,
        leadingIcon = { Icon(painter = painterResource(id = leadingIcon) , contentDescription = null) },
        keyboardOptions = keyboardsOptions,
        modifier = modifier,
        onValueChange = onValueChanged
    )

}

private fun calcularMonto(precio: Double, cantLitros:Double, propina:Double, estado:Boolean ):String{
    var monto = 0.0
    if(estado == true){
        monto = (precio * cantLitros) + propina
    }
    else{
        monto = (precio * cantLitros)
    }
    return NumberFormat.getCurrencyInstance().format(monto)
}

@Preview(showBackground = true)
@Composable
fun CostGasLayoutPreview() {
    CostoGasolinaTheme {
        CostGasLayout("Android")
    }
}

