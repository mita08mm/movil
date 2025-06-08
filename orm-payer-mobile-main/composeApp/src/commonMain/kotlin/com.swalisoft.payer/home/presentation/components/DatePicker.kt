package com.swalisoft.payer.home.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Dialog
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState

fun getCurrentDateTime(): LocalDateTime {
    val instant = Clock.System.now()
    return instant.toLocalDateTime(TimeZone.currentSystemDefault())
}

fun getTomorrowDateTime(): LocalDateTime {
    val today = getCurrentDateTime()
    return LocalDateTime(
        year = today.year,
        month = today.month,
        dayOfMonth = today.dayOfMonth + 1,
        hour = today.hour,
        minute = today.minute,
        second = today.second,
        nanosecond = today.nanosecond
    ).handleMonthOverflow()
}

private fun LocalDateTime.handleMonthOverflow(): LocalDateTime {
    if (dayOfMonth <= numberOfDaysInMonth()) return this
    val newMonth = month.next()
    val newYear = if (newMonth == Month.JANUARY) year + 1 else year
    return LocalDateTime(
        year = newYear,
        month = newMonth,
        dayOfMonth = 1,
        hour = hour,
        minute = minute,
        second = second,
        nanosecond = nanosecond
    )
}

private fun LocalDateTime.numberOfDaysInMonth(): Int {
    return when (month) {
        Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> 30
        Month.FEBRUARY -> if (year.isLeapYear()) 29 else 28
        else -> 31
    }
}

private fun Int.isLeapYear(): Boolean {
    return (this % 4 == 0 && (this % 100 != 0 || this % 400 == 0))
}

private fun Month.next(): Month {
    return Month.entries[(this.ordinal + 1) % Month.entries.size]
}

private fun isValidDay(day: Int, month: Month, year: Int): Boolean {
    return when (month) {
        Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> day <= 30
        Month.FEBRUARY -> {
            if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
                day <= 29
            } else {
                day <= 28
            }
        }
        else -> day <= 31
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePicker(
    selectedDateTime: LocalDateTime,
    onDateTimeSelected: (LocalDateTime) -> Unit,
    modifier: Modifier = Modifier,
    //chatgpt
    //onDismiss: () -> Unit,
    //
    yearsRange: IntRange = (getCurrentDateTime().year)..(getCurrentDateTime().year + 10),
    label: String = "Fecha y hora"
) {
    var showDialog by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    // Formateamos la fecha y hora para mostrarla
    val dateTimeText = remember(selectedDateTime) {
        "${selectedDateTime.dayOfMonth.toString().padStart(2, '0')}/" +
                "${selectedDateTime.monthNumber.toString().padStart(2, '0')}/" +
                "${selectedDateTime.year} " +
                "${selectedDateTime.hour.toString().padStart(2, '0')}:" +
                "${selectedDateTime.minute.toString().padStart(2, '0')}"
    }

    OutlinedTextField(
        value = dateTimeText,
        onValueChange = { },
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { showDialog = true }
            ),
        readOnly = true,
        label = { Text(label) },
        /*trailingIcon = {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null, // Omitimos para evitar accesibilidad duplicada
                modifier = Modifier.size(24.dp)
            )
        },*/
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Seleccionar fecha y hora",
                modifier = Modifier.clickable { showDialog = true }
            )
        },
        interactionSource = interactionSource,
        //enabled = false // Deshabilita el TextField pero mantiene el estilo
    )

    if (showDialog) {
        DateTimePickerDialog(
            selectedDateTime = selectedDateTime,
            onDateTimeSelected = { newDateTime ->
                onDateTimeSelected(newDateTime)
                showDialog = false
            },
            onDismiss = { showDialog = false },
            yearsRange = yearsRange
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePickerDialog(
    selectedDateTime: LocalDateTime,
    onDateTimeSelected: (LocalDateTime) -> Unit,
    onDismiss: () -> Unit,
    yearsRange: IntRange
) {
    // Estados internos:
    var day by remember { mutableStateOf(selectedDateTime.dayOfMonth) }
    var month by remember { mutableStateOf(selectedDateTime.month) }
    var year by remember { mutableStateOf(selectedDateTime.year) }
    var hour by remember { mutableStateOf(selectedDateTime.hour) }
    var minute by remember { mutableStateOf(selectedDateTime.minute) }

    // Flags para mostrar/ocultar cada DropdownMenu:
    var dayExpanded by remember { mutableStateOf(false) }
    var monthExpanded by remember { mutableStateOf(false) }
    var yearExpanded by remember { mutableStateOf(false) }
    var hourExpanded by remember { mutableStateOf(false) }
    var minuteExpanded by remember { mutableStateOf(false) }

    // Tamaños de cada TextField, para darles el mismo ancho al DropdownMenu:
    var dayFieldSize by remember { mutableStateOf(Size.Zero) }
    var monthFieldSize by remember { mutableStateOf(Size.Zero) }
    var yearFieldSize by remember { mutableStateOf(Size.Zero) }
    var hourFieldSize by remember { mutableStateOf(Size.Zero) }
    var minuteFieldSize by remember { mutableStateOf(Size.Zero) }

    val density = LocalDensity.current

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Seleccionar fecha y hora",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                // ——— Primera fila: Día | Mes | Año ———
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Día (1/3 del ancho)
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Día:")
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = day.toString(),
                            onValueChange = { },
                            readOnly = true,
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Seleccionar día",
                                    modifier = Modifier.clickable { dayExpanded = true }
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .onGloballyPositioned { coordinates ->
                                    dayFieldSize = coordinates.size.toSize()
                                }
                                .clickable { dayExpanded = true }
                        )
                        DropdownMenu(
                            expanded = dayExpanded,
                            onDismissRequest = { dayExpanded = false },
                            modifier = Modifier
                                .width(with(density) { dayFieldSize.width.toDp() })
                                .heightIn(max = 150.dp)
                        ) {
                            val maxDay = LocalDateTime(year, month, 1, 0, 0).numberOfDaysInMonth()
                            (1..maxDay).forEach { d ->
                                DropdownMenuItem(
                                    text = { Text(text = d.toString(), textAlign = TextAlign.Center) },
                                    onClick = {
                                        day = d
                                        dayExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    // Mes (1/3 del ancho)
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Mes:")
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = month.name.take(3),
                            onValueChange = { },
                            readOnly = true,
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Seleccionar mes",
                                    modifier = Modifier.clickable { monthExpanded = true }
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .onGloballyPositioned { coordinates ->
                                    monthFieldSize = coordinates.size.toSize()
                                }
                                .clickable { monthExpanded = true }
                        )
                        DropdownMenu(
                            expanded = monthExpanded,
                            onDismissRequest = { monthExpanded = false },
                            modifier = Modifier
                                .width(with(density) { dayFieldSize.width.toDp() })
                                .heightIn(max = 150.dp)
                        ) {
                            Month.entries.forEach { m ->
                                DropdownMenuItem(
                                    text = { Text(text=m.name.take(3), textAlign = TextAlign.Center) },
                                    onClick = {
                                        month = m
                                        monthExpanded = false
                                        // Ajusta "day" si el día actual excede el nuevo mes
                                        val maxDayNew = LocalDateTime(year, month, 1, 0, 0).numberOfDaysInMonth()
                                        if (day > maxDayNew) {
                                            day = maxDayNew
                                        }
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Año (1/3 del ancho)
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Año:")
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = year.toString(),
                            onValueChange = { },
                            readOnly = true,
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Seleccionar año",
                                    modifier = Modifier.clickable { yearExpanded = true }
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .onGloballyPositioned { coordinates ->
                                    yearFieldSize = coordinates.size.toSize()
                                }
                                .clickable { yearExpanded = true }
                        )
                        DropdownMenu(
                            expanded = yearExpanded,
                            onDismissRequest = { yearExpanded = false },
                            modifier = Modifier
                                .width(with(density) { yearFieldSize.width.toDp() })
                                .heightIn(max = 150.dp)
                        ) {
                            yearsRange.forEach { y ->
                                DropdownMenuItem(
                                    text = { Text(text=y.toString(), textAlign = TextAlign.Center) },
                                    onClick = {
                                        year = y
                                        yearExpanded = false
                                        // Ajusta día si el día supera los días del mes para el año nuevo
                                        val maxDayNew = LocalDateTime(year, month, 1, 0, 0).numberOfDaysInMonth()
                                        if (day > maxDayNew) {
                                            day = maxDayNew
                                        }
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ——— Segunda fila: Hora | Minuto ———
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Hora (1/2 del ancho)
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Hora:")
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = hour.toString().padStart(2, '0'),
                            onValueChange = { },
                            readOnly = true,
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Seleccionar hora",
                                    modifier = Modifier.clickable { hourExpanded = true }
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .onGloballyPositioned { coordinates ->
                                    hourFieldSize = coordinates.size.toSize()
                                }
                                .clickable { hourExpanded = true }
                        )
                        DropdownMenu(
                            expanded = hourExpanded,
                            onDismissRequest = { hourExpanded = false },
                            modifier = Modifier
                                .width(with(density) { hourFieldSize.width.toDp() })
                                .heightIn(max = 150.dp)
                        ) {
                            (0..23).forEach { h ->
                                DropdownMenuItem(
                                    text = { Text(text=h.toString().padStart(2, '0'), textAlign = TextAlign.Center) },
                                    onClick = {
                                        hour = h
                                        hourExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    // Minuto (1/2 del ancho)
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Minuto:")
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = minute.toString().padStart(2, '0'),
                            onValueChange = { },
                            readOnly = true,
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Seleccionar minuto",
                                    modifier = Modifier.clickable { minuteExpanded = true }
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .onGloballyPositioned { coordinates ->
                                    minuteFieldSize = coordinates.size.toSize()
                                }
                                .clickable { minuteExpanded = true }
                        )
                        DropdownMenu(
                            expanded = minuteExpanded,
                            onDismissRequest = { minuteExpanded = false },
                            modifier = Modifier
                                .width(with(density) { hourFieldSize.width.toDp() })
                                .heightIn(max = 150.dp)
                        ) {
                            (0..59).forEach { m ->
                                DropdownMenuItem(
                                    text = { Text(m.toString().padStart(2, '0'), textAlign = TextAlign.Center) },
                                    onClick = {
                                        minute = m
                                        minuteExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // ——— Botones de Confirmar y Cancelar ———
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    ) {
                        Text("Cancelar")
                    }

                    Button(
                        onClick = {
                            onDateTimeSelected(LocalDateTime(year, month, day, hour, minute, 0))
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Confirmar")
                    }
                }
            }
        }
    }
}