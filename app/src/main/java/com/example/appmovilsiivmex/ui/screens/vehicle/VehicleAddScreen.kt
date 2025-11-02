package com.example.appmovilsiivmex.ui.screens.vehicle

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.DriveFileRenameOutline
import androidx.compose.material.icons.outlined.Numbers
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appmovilsiivmex.R
import com.example.appmovilsiivmex.ui.theme.ColorAzulOscuro
import com.example.appmovilsiivmex.ui.theme.ColorGris

@Preview(showBackground = true)
@Composable
fun VehicleAddScreen(
    viewModel: VehicleViewModel = viewModel(),
    onSubmit: (VehicleUiState) -> Unit = {}
) {
    val ui by viewModel.uiState.collectAsState()
    val focus = LocalFocusManager.current

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(8.dp))
        Illustration(resId = R.drawable.addvehicle_illustration)
        Spacer(Modifier.height(16.dp))

        TitleAndSubtitle(
            title = "Agregar Vehículo",
            subtitle = "Por favor agrega la información antes de continuar"
        )

        Spacer(Modifier.height(16.dp))

        // Placa (mayúsculas, máscara simple)
        PlateInput(
            value = ui.plate,
            onValueChange = viewModel::onPlateChange,
            placeholder = "Placa",
            imeAction = ImeAction.Next
        )

        Spacer(Modifier.height(12.dp))

        // Nombre para su vehículo
        FilledInput(
            value = ui.nickname,
            onValueChange = viewModel::onNicknameChange,
            placeholder = "Nombre para su vehículo",
            leading = { Icon(Icons.Outlined.DriveFileRenameOutline, null, tint = ColorGris) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )

        Spacer(Modifier.height(12.dp))

        // Año (numérico)
        FilledInput(
            value = ui.year,
            onValueChange = viewModel::onYearChange,
            placeholder = "Año",
            leading = { Icon(Icons.Outlined.CalendarMonth, null, tint = ColorGris) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )

        Spacer(Modifier.height(12.dp))

        // Marca
        FilledInput(
            value = ui.brand,
            onValueChange = viewModel::onBrandChange,
            placeholder = "Marca",
            leading = { Icon(Icons.Outlined.DirectionsCar, null, tint = ColorGris) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focus.clearFocus() })
        )

        Spacer(Modifier.height(18.dp))

        // Holograma
        Text("Holograma", color = ColorGris, fontSize = 14.sp, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        HologramChips(
            options = listOf("E", "00", "0", "1", "2"),
            selected = ui.hologram,
            onSelected = viewModel::onHologramChange
        )

        Spacer(Modifier.height(24.dp))

        PrimaryButton(
            text = "Registrar",
            isLoading = ui.isLoading,
            onClick = { viewModel.submit(onSuccess = { onSubmit(ui) }) }
        )
    }
}

/* ---------- Subcomposables ---------- */

@Composable
private fun Illustration(resId: Int) {
    Image(
        painter = painterResource(resId),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.4f),
        contentScale = ContentScale.Fit
    )
}

@Composable
private fun TitleAndSubtitle(title: String, subtitle: String) {
    Text(
        title,
        color = ColorAzulOscuro,
        fontSize = 32.sp,
        textAlign = TextAlign.Start,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.fillMaxWidth(),
    )
    Spacer(Modifier.height(6.dp))
    Text(
        subtitle,
        color = ColorGris,
        fontSize = 14.sp,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun FilledInput(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    leading: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    isError: Boolean = false,
    supportingText: String? = null
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = Color(0xFF9CA3AF)) },
        leadingIcon = leading,
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp),
        shape = RoundedCornerShape(14.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFFF2F3F5),
            focusedContainerColor = Color(0xFFF2F3F5),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = ColorAzulOscuro,
            focusedTextColor = ColorAzulOscuro,
            unfocusedTextColor = Color.Black
        ),
        isError = isError
    )
    if (supportingText != null && isError) {
        Spacer(Modifier.height(4.dp))
        Text(supportingText, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
    }
}

@Composable
private fun PlateInput(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    imeAction: ImeAction
) {
    var tf by remember(value) {
        mutableStateOf(TextFieldValue(value, selection = TextRange(value.length)))
    }
    TextField(
        value = tf,
        onValueChange = { new ->
            val cleaned = new.text.uppercase().filter { it.isLetterOrDigit() || it == '-' }
            val limited = cleaned.take(8) // ej. "ABC-1234" o "ABC123"
            tf = new.copy(text = limited, selection = TextRange(limited.length))
            onValueChange(limited)
        },
        placeholder = { Text(placeholder, color = Color(0xFF9CA3AF)) },
        leadingIcon = { Icon(Icons.Outlined.Numbers, null, tint = ColorGris) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Ascii,
            imeAction = imeAction
        ),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp),
        shape = RoundedCornerShape(14.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFFF2F3F5),
            focusedContainerColor = Color(0xFFF2F3F5),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = ColorAzulOscuro,
            focusedTextColor = ColorAzulOscuro,
            unfocusedTextColor = Color.Black
        )
    )
}

@Composable
private fun HologramChips(
    options: List<String>,
    selected: String,
    onSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally), // centra el grupo
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        options.forEach { opt ->
            CircleChip(
                label = opt,
                selected = selected == opt,
                onClick = { onSelected(opt) }
            )
        }
    }
}

@Composable
private fun CircleChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val bg = if (selected) ColorAzulOscuro else Color(0xFFE8EAED)
    val fg = if (selected) Color.White else Color.Black

    Surface(
        color = bg,
        shape = CircleShape,
        shadowElevation = 0.dp,
        tonalElevation = 0.dp,
        // sin border = sin contorno negro
        modifier = Modifier
            .size(42.dp)
            .clickable { onClick() }
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = label,
                color = fg,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                maxLines = 1
            )
        }
    }
}


@Composable
private fun PrimaryButton(
    text: String,
    isLoading: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = ColorAzulOscuro)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(22.dp),
                color = Color.White,
                strokeWidth = 2.dp
            )
        } else {
            Text(text, fontSize = 16.sp, color = Color.White)
        }
    }
}
